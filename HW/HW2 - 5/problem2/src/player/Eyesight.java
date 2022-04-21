package relay.player;

import relay.map.Map;

public class Eyesight {
    private static final double range = 3;
    private double nextPlayerPosition;	
    private Map map;

    public Eyesight(Map map){
            this.map = map;
        }
	public void setNextPlayerPosition(double position){
        nextPlayerPosition=position;
    }
    public double getRange(){return range;}

    public double getDistanceToBoundary(double playerPosition){
        //TODO: Problem 2.1
        boolean isInWater = map.getOnWater(playerPosition);
        double dis = range;
        if(isInWater){
            dis = map.getWaterEnd() - playerPosition;
        } else {
            if(playerPosition < map.getWaterStart()) {
                dis = map.getWaterStart() - playerPosition;
            } else {
                dis = map.getMapEnd() - playerPosition;
            }
        }
        return dis < range ? dis : range;
    }
    public double getDistanceToNextPlayer(double playerPosition){
        //TODO: Problem 2.1
        double dis = nextPlayerPosition - playerPosition;
        return dis < range ? dis : range;
    }

}
