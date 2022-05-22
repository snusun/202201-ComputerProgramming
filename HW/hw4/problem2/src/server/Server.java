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
        // TODO betting book 참고해야할듯

        // userList
        List<String> userList = new LinkedList<>(getUserList().keySet());
        //Map<Pair<Integer, Integer>, List<Betting>> bettingInfo = new TreeMap<>(); // matchId, bettingOption, Betting
        Map<Integer, List<Betting>> bettingInfo = new TreeMap<>(); // matchId, Betting

        // ascending order
        userList.sort(new Comparator<String>() {
            @Override
            public int compare(String s1, String s2) {
                return s1.compareTo(s2);
            }
        });

//        for(String userId: userList){
//            System.out.println(userId);
//        }

        //int bettingNum = 0;

        // traverse
        for (String userId : userList) {
            //System.out.println("[SERVER] User Id: " + userId);
            String filePath = DATA_FOLDER + "Users/" + userId + "/newBettings.txt";
            //System.out.println(filePath);
            File file = new File(filePath);
            User user = searchUser(userId);
            try {
                BufferedReader inFile = new BufferedReader(new FileReader(file));
                String sLine = null;
                //System.out.println(userId);
                while ((sLine = inFile.readLine()) != null) {
                    //System.out.println(sLine);
                    String[] info = sLine.split("\\|");
                    int matchId = Integer.parseInt(info[0]);
                    int bettingOption = Integer.parseInt(info[1]);
                    int coinsBet = Integer.parseInt(info[2]);

                    // not valid
                        // bettingIdMap updated with the error code
                        // coin refund
                    // refund (error code 인 것들에 대해)
                    // matchCoinMap 도 갱신 (빼주기) (해당 유저가 한 match에 쓴 돈)
                    Match match = searchMatch(matchId);

                    if(match==null){ // MATCH_NOT_FOUND
                        user.updateBettingIdMap(matchId, bettingOption, ErrorCode.MATCH_NOT_FOUND);
                        user.receiveCoin(coinsBet);
                    } else if(match.numBets<=bettingOption){ // INVALID_BETTING
                        user.updateBettingIdMap(matchId, bettingOption, ErrorCode.INVALID_BETTING);
                        user.receiveCoin(coinsBet);
                    } else if(compareTimes(currentTime, match.matchTime)>-1){ // LATE_BETTING
                        user.updateBettingIdMap(matchId, bettingOption, ErrorCode.LATE_BETTING);
                        user.receiveCoin(coinsBet);
                    } else { // valid
                        // bettingInfo 에서 match id와 bettingOption으로 betting 찾기
                        // userId 같은게 잇으면 coin 추가, bettingIdMap에서 betting id 기존 걸로 쓰기
                        // 없으면 새로 만들기,  betting list 길이 + 1 해서 bettingIdMap에서 betting id set
                        // matchCoinMap 도 갱신 (해당 유저가 한 match에 쓴 돈)
                        // info update <- current odd & total betting update
                        List<Betting> bettings = bettingInfo.get(matchId);
                        if(bettings==null){
                            List<Betting> newBettings = new LinkedList<>();
                            newBettings.add(new Betting(userId, matchId, bettingOption, coinsBet));
                            bettingInfo.put(matchId, newBettings);
                            // id 1로 set bettingIdMap
                            user.updateBettingId(matchId, bettingOption, 1);
                            //bettingNum++;
                            match.incrementCoin(bettingOption, coinsBet);
                            match.totalBets++;
                        } else {
                            boolean isExist = false;
                            for(int i=0; i<bettings.size(); i++){
                            //for(Betting betting: bettings){
                                Betting betting = bettings.get(i);
                                if(betting.userId.equals(userId) && betting.betNumber==bettingOption){
                                    betting.coin += coinsBet;
                                    isExist = true;
                                    // idx + 1 로 id set bettingIdMap
                                    user.updateBettingId(matchId, bettingOption, i+1);
                                    match.incrementCoin(bettingOption, coinsBet);
                                }
                            }
                            if(!isExist) {
                                bettings.add(new Betting(userId, matchId, bettingOption, coinsBet));
                                // 길이로 id set bettingIdMap
                                user.updateBettingId(matchId, bettingOption, bettings.size());
                                //bettingInfo.put(matchId, newBettings);
                                //bettingNum++;
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

        // betting info
        // TODO update betting book
        // info update <- current odd & total betting update
        for(Match match: getMatchList().values()){
            String infoPath = DATA_FOLDER + "/Matches/" + match.sportsType + "/" + match.matchId + "/" + match.matchId + "_info.txt";
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(infoPath));
                String matchInfo = match.homeTeam + "|" + match.awayTeam + "|" + match.location + "|" + match.matchTime + "|" +
                        match.numBets + "|";
                for(double odd: match.currentOdds){
                    matchInfo += odd + "|";
                }
                matchInfo+=match.totalCoin;
                writer.write(matchInfo);
                writer.close();
            } catch (IOException e) {
                return ErrorCode.IO_ERROR;
            }
        }

        // betting book write
        // Map<Integer, List<Betting>> bettingInfo = new TreeMap<>(); // matchId, Betting

//        System.out.println("[SERVER] betting info");
//        for(int matchId: bettingInfo.keySet()){
//
//            System.out.println(bettingInfo.get(matchId));
//        }


        for(int matchId: bettingInfo.keySet()){
            // file 열고 list에 잇는 betting 적기
            Match match = searchMatch(matchId);
            String filePath = DATA_FOLDER + "/Matches/" + match.sportsType + "/" + match.matchId + "/" + match.matchId + "_bettingBook.txt";
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true));
                String bets = "";
                for(Betting betting: bettingInfo.get(matchId)){
                    bets+=betting.userId+"|" + betting.betNumber +"|"+betting.coin+"\n";
                }
                writer.append(bets);
                writer.close();
            } catch (IOException e) {
                return ErrorCode.IO_ERROR;
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
        //String matchInfo = null;
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
            e.printStackTrace();
        }

        return bettingBook;
    }

    public boolean settleMatch(int matchId, int winNumber) {
        // TODO Problem 2-3

        return true;
    }
}
