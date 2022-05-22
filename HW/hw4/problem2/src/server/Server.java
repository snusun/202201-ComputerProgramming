package server;

import user.User;

import match.Betting;
import match.Match;

import utils.ErrorCode;
import utils.Pair;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.*;
import java.text.SimpleDateFormat;

public class Server {
    public String currentTime;
    private Map<Integer, Match> matchList;
    private Map<String, User> userList;

    private SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm");

    private String DATA_FOLDER = "data/";

    public Server(String currentTime) {
        this.matchList = getMatchList();
        this.userList = getUserList();
        this.currentTime = currentTime;
    }

    public Server(String currentTime, String data_folder) {
        this.DATA_FOLDER = data_folder;
        this.matchList = getMatchList();
        this.userList = getUserList();
        this.currentTime = currentTime;
    }

    public void setCurrentTime(String currentTime) {
        this.currentTime = currentTime;
    }

    public Map<Integer, Match> getMatchList() {
        // Save match info
        Map<Integer, Match> matchList = new TreeMap();
        File matchRootDirFile = new File(String.format("%s/Matches", DATA_FOLDER));
        File[] sportsDirFiles = matchRootDirFile.listFiles();
        if (sportsDirFiles == null) {
            // No such directory or IO exception
            return new TreeMap<Integer, Match>();
        }
        for (File sportsDirFile : sportsDirFiles) {
            String sportsType = sportsDirFile.getName();
            if (sportsDirFile.isDirectory()) {
                for (File matchDir : sportsDirFile.listFiles()) {
                    if (matchDir.isDirectory()) {
                        for (File matchFile : matchDir.listFiles()) {

                            if (matchFile.getName().endsWith("_info.txt")) {
                                int matchId = Integer.parseInt(matchFile.getName().substring(0, matchFile.getName().length() - 9));

                                String matchInfo = null;
                                try {
                                    matchInfo = Files.readString(Paths.get(matchFile.getAbsolutePath()));
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                String[] infos = matchInfo.split("\\|");
                                int numBets = Integer.parseInt(infos[4]);
                                double[] currentOdds = new double[numBets];
                                for (int i = 0; i < numBets; i++) {
                                    currentOdds[i] = Double.parseDouble(infos[5 + i]);
                                }

                                matchList.put(matchId, new Match(
                                                matchId,
                                                sportsType,
                                                infos[0], // home team
                                                infos[1], // away team
                                                infos[3], // match time
                                                infos[2], // location
                                                numBets, // the number of possible betting sides
                                                currentOdds
                                        )
                                );
                            }
                        }
                    }
                }
            }
        }
        return matchList;
    }

    public Map<String, User> getUserList() {
        // Save match info
        if (userList == null) {
            userList = new TreeMap();
        }
        File userRootDirFile = new File(String.format("%s/Users", DATA_FOLDER));
        File[] userRootDirFiles = userRootDirFile.listFiles();
        if (userRootDirFiles == null) {
            // No such directory or IO exception
            return null;
        }
        for (File userDirFile : userRootDirFiles) {
            String userId = userDirFile.getName();
            if (userList.get(userId) == null) {
                userList.put(userId, new User(userId, DATA_FOLDER));
            }
        }

        return userList;
    }

    private Match searchMatch(int matchId) {
        return matchList.get(matchId);
    }

    private User searchUser(String userId) {
        return userList.get(userId);
    }

    private long compareTimes(String time1, String time2) {
        try {
            long time1ToLong = formatter.parse(time1).getTime();
            long time2ToLong = formatter.parse(time2).getTime();

            return Long.compare(time1ToLong, time2ToLong);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    public List<Match> search(Map<String, Object> searchConditions, String sortCriteria) {
        // TODO Problem 2-1
        List<Match> searchResult = new LinkedList<>();
        List<Match> matches = new LinkedList<>(matchList.values());

        // search by search condition
        for (Match match : matches) {
            boolean proper = true;
            for (String key : searchConditions.keySet()) {
                Object value = searchConditions.get(key);
                switch (key) { // 하나라도 틀리면 profer = false
                    case "sports":
                        if (!match.sportsType.equals(value.toString())) {
                            proper = false;
                        }
                        break;
                    case "time":
                        if (compareTimes(value.toString(), match.matchTime) > -1) {
                            proper = false;
                        }
                        break;
                    case "club":
                        String[] keywords = value.toString().split(" ");
                        String[] homeTeam = match.homeTeam.split(" ");
                        String[] awayTeam = match.awayTeam.split(" ");
                        boolean homeSame = false;
                        boolean awaySame = false;
                        for (String home : homeTeam) {
                            homeSame = false;
                            for (String keyword : keywords) {
                                if (home.equals(keyword)) {
                                    homeSame = true;
                                    break;
                                }
                            }
                            if (!homeSame) break;
                        }

                        for (String away : awayTeam) {
                            awaySame = false;
                            for (String keyword : keywords) {
                                if (away.equals(keyword)) {
                                    awaySame = true;
                                    break;
                                }
                            }
                            if (!awaySame) break;
                        }

                        if (!homeSame && !awaySame) proper = false;
                        break;
                    case "odds":
                        boolean larger = false;
                        for (double odd : match.currentOdds) {
                            if (odd > Double.parseDouble(value.toString())) {
                                larger = true;
                                break;
                            }
                        }
                        if (!larger) {
                            proper = false;
                        }
                        break;
                }
            }
            if (proper) searchResult.add(match);
        }

        // sort by sortCriteria
        if (sortCriteria != null) {
            switch (sortCriteria) {
                case "sports":
                    searchResult.sort(new Comparator<Match>() {
                        @Override
                        public int compare(Match o1, Match o2) {
                            if (o1.sportsType.equals(o2.sportsType)) {
                                return o1.matchId - o2.matchId;
                            } else {
                                return o1.sportsType.compareTo(o2.sportsType);
                            }
                        }
                    });
                    break;
                case "club":
                    searchResult.sort(new Comparator<Match>() {
                        @Override
                        public int compare(Match o1, Match o2) {
                            if (!o1.homeTeam.equals(o2.homeTeam)) {
                                return o1.homeTeam.compareTo(o2.homeTeam);
                            } else if (!o1.awayTeam.equals(o2.awayTeam)) {
                                return o1.awayTeam.compareTo(o2.awayTeam);
                            } else {
                                return o1.matchId - o2.matchId;
                            }
                        }
                    });
                    break;
                case "time":
                    searchResult.sort(new Comparator<Match>() {
                        @Override
                        public int compare(Match o1, Match o2) {
                            if (compareTimes(o1.matchTime, o2.matchTime) == 0) {
                                return o1.matchId - o2.matchId;
                            } else {
                                return (int) compareTimes(o1.matchTime, o2.matchTime);
                            }
                        }
                    });
                    break;
                case "odds":
                    searchResult.sort(new Comparator<Match>() {
                        @Override
                        public int compare(Match o1, Match o2) {
                            double largestO1 = 0;
                            double largestO2 = 0;
                            for (double odd : o1.currentOdds) {
                                if (odd > largestO1) largestO1 = odd;
                            }
                            for (double odd : o2.currentOdds) {
                                if (odd > largestO2) largestO2 = odd;
                            }
                            return Double.compare(largestO1, largestO2);
                        }
                    });
                    break;
                default:
                    searchResult.sort(new Comparator<Match>() {
                        @Override
                        public int compare(Match o1, Match o2) {
                            return o1.matchId - o2.matchId;
                        }
                    });
            }
        }

        return searchResult;
    }

    public int collectBettings() {
        // TODO Problem 2-2

        // userList
        List<String> userList = new LinkedList<>(getUserList().keySet());
        Map<Integer, List<Betting>> bettingInfo = new TreeMap<>(); // matchId, Betting

        // ascending order
        userList.sort(new Comparator<String>() {
            @Override
            public int compare(String s1, String s2) {
                return s1.compareTo(s2);
            }
        });

        // traverse
        for (String userId : userList) {
            String filePath = DATA_FOLDER + "Users/" + userId + "/newBettings.txt";
            //System.out.println(filePath);
            File file = new File(filePath);
            User user = searchUser(userId);
            try {
                BufferedReader inFile = new BufferedReader(new FileReader(file));
                String sLine = null;
                while ((sLine = inFile.readLine()) != null) {
                    String[] info = sLine.split("\\|");
                    int matchId = Integer.parseInt(info[0]);
                    int bettingOption = Integer.parseInt(info[1]);
                    int coinsBet = Integer.parseInt(info[2]);

                    // not valid
                    Match match = searchMatch(matchId);

                    if (match == null) { // MATCH_NOT_FOUND
                        user.updateBettingId(matchId, bettingOption, ErrorCode.MATCH_NOT_FOUND);
                        user.receiveCoin(coinsBet);
                        user.matchCoinMap.put(matchId, user.matchCoinMap.get(matchId)-coinsBet);
                    } else if (match.numBets <= bettingOption) { // INVALID_BETTING
                        user.updateBettingId(matchId, bettingOption, ErrorCode.INVALID_BETTING);
                        user.receiveCoin(coinsBet);
                        user.matchCoinMap.put(matchId, user.matchCoinMap.get(matchId)-coinsBet);
                    } else if (compareTimes(currentTime, match.matchTime) > -1) { // LATE_BETTING
                        user.updateBettingId(matchId, bettingOption, ErrorCode.LATE_BETTING);
                        user.receiveCoin(coinsBet);
                        user.matchCoinMap.put(matchId, user.matchCoinMap.get(matchId)-coinsBet);
                    } else { // valid
                        List<Betting> bettings = bettingInfo.get(matchId);
                        List<Betting> bettingBook = getBettingBook(matchId);
                        int alreadyBet = 0;
                        if (bettings == null) {
                            List<Betting> newBettings = new LinkedList<>();
                            newBettings.add(new Betting(userId, matchId, bettingOption, coinsBet));
                            bettingInfo.put(matchId, newBettings);
                            user.updateBettingId(matchId, bettingOption, alreadyBet+ 1);
                            match.incrementCoin(bettingOption, coinsBet);
                            match.totalBets++;
                        } else {
                            boolean isExist = false;
                            for (int i = 0; i < bettings.size(); i++) {
                                Betting betting = bettings.get(i);
                                if (betting.userId.equals(userId) && betting.betNumber == bettingOption) {
                                    betting.coin += coinsBet;
                                    isExist = true;
                                    int newId = 0;
                                    user.updateBettingId(matchId, bettingOption, alreadyBet+ i + 1);
                                    match.incrementCoin(bettingOption, coinsBet);
                                }
                            }
                            if (!isExist) {
                                bettings.add(new Betting(userId, matchId, bettingOption, coinsBet));
                                user.updateBettingId(matchId, bettingOption, alreadyBet + bettings.size());
                                match.incrementCoin(bettingOption, coinsBet);
                                match.totalBets++;
                            }
                        }
                    }
                }

            } catch (IOException e) {
                continue;
                //return ErrorCode.IO_ERROR;
            }

            // newBettings.txt 삭제
            file.delete();
        }

        for (int matchId : bettingInfo.keySet()) {
            List<Betting> bettingBook = getBettingBook(matchId);

            if (bettingBook == null) {
                // file 열고 list에 잇는 betting 적기
                Match match = searchMatch(matchId);
                String filePath = DATA_FOLDER + "/Matches/" + match.sportsType + "/" + match.matchId + "/" + match.matchId + "_bettingBook.txt";
                try {
                    BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true));
                    String bets = "";
                    for (Betting betting : bettingInfo.get(matchId)) {
                        bets += betting.userId + "|" + betting.betNumber + "|" + betting.coin + "\n";
                    }
                    writer.append(bets);
                    writer.close();
                } catch (IOException e) {
                    return ErrorCode.IO_ERROR;
                }
            } else {
                for (Betting betting : bettingInfo.get(matchId)) {
                    boolean isExist = false;
                    //if (bettingBook != null) {
                    for (Betting betInBook : bettingBook) {
                        if (betting.userId.equals(betInBook.userId) && betting.betNumber==betInBook.betNumber) {
                            betInBook.coin += betting.coin;
                            isExist = true;
                            searchMatch(matchId).totalBets--;
                            break;
                        }
                    }
                    if (!isExist) {
                        bettingBook.add(betting);
                    }
                }

                Match match = searchMatch(matchId);
                String filePath = DATA_FOLDER + "/Matches/" + match.sportsType + "/" + match.matchId + "/" + match.matchId + "_bettingBook.txt";
                File file = new File(filePath);
                try {
                    BufferedWriter writer = new BufferedWriter(new FileWriter(file));
                    String bets = "";
                    for (Betting betting : bettingBook) {
                        bets += betting.userId + "|" + betting.betNumber + "|" + betting.coin + "\n";
                    }
                    writer.write(bets);
                    writer.close();
                } catch (IOException e) {
                    return ErrorCode.IO_ERROR;
                }
            }
        }

        // betting info
        // info update <- current odd & total betting update

        for (Match match : matchList.values()) {
            String infoPath = DATA_FOLDER + "/Matches/" + match.sportsType + "/" + match.matchId + "/" + match.matchId + "_info.txt";
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(infoPath));
                String matchInfo = match.homeTeam + "|" + match.awayTeam + "|" + match.location + "|" + match.matchTime + "|" +
                        match.numBets + "|";
                for (double odd : match.currentOdds) {
                    odd = Math.round(odd*100)/100.0;
                    DecimalFormat formatter = new DecimalFormat("0.00");
                    String ret = formatter.format(odd);
                    matchInfo += ret + "|";
                }
                matchInfo += match.totalBets;
                writer.write(matchInfo);
                writer.close();
            } catch (IOException e) {
                continue;
            }
        }

        return ErrorCode.SUCCESS;
    }

    public List<Betting> getBettingBook(int matchId) {
        // TODO Problem 2-2
        List<Betting> bettingBook = new LinkedList<>();
        Match match = searchMatch(matchId);
        String filePath = DATA_FOLDER + "/Matches/" + match.sportsType + "/" + matchId + "/" + matchId + "_bettingBook.txt";
        File file = new File(filePath);
        try {
            BufferedReader inFile = new BufferedReader(new FileReader(file));
            String sLine = null;
            while ((sLine = inFile.readLine()) != null) {
                String[] bettingInfo = sLine.split("\\|");
                for (int i = 0; i < bettingInfo.length / 3; i += 3) {
                    Betting betting = new Betting(bettingInfo[i], matchId, Integer.parseInt(bettingInfo[i + 1]), Integer.parseInt(bettingInfo[i + 2]));
                    bettingBook.add(betting);
                }
            }
        } catch (IOException e) {
            return null;
        }

        return bettingBook;
    }

    public boolean settleMatch(int matchId, int winNumber) {
        // TODO Problem 2-3
        Match match = searchMatch(matchId);

        // odd와 commition 계산
        //double odd = Math.round(match.currentOdds[winNumber]*100)/100.0;
        double odd = match.currentOdds[winNumber];
        double commission = 0.96;

        // betting book에서 이긴 사람들 refund 정수 round
        List<Betting> bettingBook = getBettingBook(matchId);
        if (bettingBook == null) return false;
        for (Betting betting : bettingBook) {
            if (betting.betNumber == winNumber) {
                User user = searchUser(betting.userId);
                int refund = (int) Math.floor(commission * odd * betting.coin);
                user.receiveCoin(refund);
                // matchCoinMap match id에 해당하는 coin 0으로 초기화
                user.matchCoinMap.put(matchId, 0);
            }
        }

        // info 초기화 currentOdds totalBets coins totalCoin
        for (int i = 0; i < match.numBets; i++) {
            match.coins[i] = 0;
            match.currentOdds[i] = 0;
        }
        match.totalCoin = 0;
        match.totalBets = 0;

        String infoPath = DATA_FOLDER + "/Matches/" + match.sportsType + "/" + match.matchId + "/" + match.matchId + "_info.txt";
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(infoPath));
            String matchInfo = match.homeTeam + "|" + match.awayTeam + "|" + match.location + "|" + match.matchTime + "|" +
                    match.numBets + "|";
            for (double newOdd : match.currentOdds) {
                matchInfo += newOdd + "|";
            }
            matchInfo += match.totalCoin;
            writer.write(matchInfo);
            writer.close();
        } catch (IOException e) {
            return false;
        }

        return true;
    }
}
