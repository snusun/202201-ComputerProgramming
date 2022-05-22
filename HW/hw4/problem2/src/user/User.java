package user;

import utils.ErrorCode;
import utils.Config;
import utils.Pair;
import java.util.Map;
import java.util.TreeMap;

import java.io.File;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.FileWriter;


public class User {
    private String userId;
    private int totalCoin;

    public String DATA_FOLDER;

    public Map<Pair<Integer, Integer>, Integer> bettingIdMap; // matchId, bettingOption, bettingId

    public Map<Integer, Integer> matchCoinMap; // matchId, coin // 차후 server에서 변경이 필요할 수 있음

    public User(String userId, String dataFolder){
        this.userId = userId;
        this.totalCoin = Config.COIN_PER_USER;
        bettingIdMap = new TreeMap();
        matchCoinMap = new TreeMap<>();
        this.DATA_FOLDER = dataFolder;
    }

    public Map<Pair<Integer, Integer>, Integer> getBettingIdList(){
        return this.bettingIdMap;
    }

    public void updateBettingIdMap(int matchId, int bettingOption, int bettingId){
        Pair<Integer, Integer> betInfo = new Pair<>(matchId, bettingOption);
        bettingIdMap.put(betInfo, bettingId);
    }

    public int getTotalCoin(){
        return totalCoin;
    }

    public void receiveCoin(int coin){
        totalCoin += coin;
    }

    public int bet(int matchId, int bettingOption, int coin){
        // TODO Problem 2-2
        //System.out.println("[USER] bet method: " + matchId + " " + bettingOption + " " + coin);
        if(coin<=0) return ErrorCode.NEGATIVE_BETTING;

        matchCoinMap.putIfAbsent(matchId, 0);
        if(matchCoinMap.get(matchId)+coin > 15000) return ErrorCode.OVER_MAX_BETTING;
        //if() 한 매치에 15000을 넘으면 안됨 ErrorCode.OVER_MAX_BETTING; Betting Book + New Bettings를 모두 고려해야합니다.

        if(getTotalCoin() < coin) return ErrorCode.NOT_ENOUGH_COINS;

        // IO ERROR
        File file = new File(DATA_FOLDER + "Users/"+userId+"/newBettings.txt");
        String bettingContent = matchId + "|" + bettingOption + "|" + coin+"\n";

        // betting 처리
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(file, true));
            bw.append(bettingContent);
            bw.close();
//            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
//            System.out.println("[USER] betting content: " + bettingContent );
//            writer.write(bettingContent);
//            writer.close();
        } catch (IOException e) {
            return ErrorCode.IO_ERROR;
        }

        // betting 하면 total coin 뺍니까..? 네
        totalCoin -= coin;
        // match coin id 추가
        matchCoinMap.put(matchId, matchCoinMap.get(matchId)+coin);
        bettingIdMap.put(new Pair<>(matchId, bettingOption), -1);

        return ErrorCode.SUCCESS;
    }

    public int updateBettingId(int matchId, int bettingOption, int newBettingId){
        // TODO Problem 2-2
        bettingIdMap.put(new Pair<>(matchId, bettingOption), newBettingId);

		return 0;
    }
}
