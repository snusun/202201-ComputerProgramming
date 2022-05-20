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

    public Map<Pair<Integer, Integer>, Integer> bettingIdMap;

    public User(String userId, String dataFolder){
        this.userId = userId;
        this.totalCoin = Config.COIN_PER_USER;
        bettingIdMap = new TreeMap();
        this.DATA_FOLDER = dataFolder;
    }

    public Map<Pair<Integer, Integer>, Integer> getBettingIdList(){
        return this.bettingIdMap;
    }

    public int getTotalCoin(){
        return totalCoin;
    }

    public void receiveCoin(int coin){
        totalCoin += coin;
    }

    public int bet(int matchId, int bettingOption, int coin){
        // TODO Problem 2-2
        if(coin<=0) return ErrorCode.NEGATIVE_BETTING;
        //if() 한 매치에 15000을 넘으면 안됨 ErrorCode.OVER_MAX_BETTING; Betting Book + New Bettings를 모두 고려해야합니다.
        if(getTotalCoin() < coin) return ErrorCode.NOT_ENOUGH_COINS;

        // IO ERROR

        // betting 처리
        // betting 하면 total coin 뺍니까..?


        return ErrorCode.SUCCESS;
    }

    public int updateBettingId(int matchId, int bettingOption, int newBettingId){
        // TODO Problem 2-2

		return 0;
    }
}
