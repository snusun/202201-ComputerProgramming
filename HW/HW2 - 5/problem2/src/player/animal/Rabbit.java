package relay.player.animal;

import relay.map.Map;
import relay.player.Player;
import relay.player.Throwable;

public class Rabbit extends Animal implements Throwable {
    private boolean isThrowUp = false;

    public Rabbit(Map map){this(0, map);}
    public Rabbit(double position, Map map) {
        super(position,3,map);
    }

    @Override
    public void move() {
        //TODO: Problem 2.2
        if(isThrowUp) throwUp(getPosition(), getMap());
        //System.out.println(isThrowUp);
        //setPosition(getPosition() + getVelocity());
        if(getThrowUp()) {return;}
        double movableDistance = getMovableDistance(getVelocity());
        if(getPosition() + movableDistance == getMap().getWaterStart()){
            setPosition(getPosition() + movableDistance);
            isThrowUp = true;
            //System.out.println(isThrowUp);
            // throwUp(getPosition(), getMap());
            // wait or give up
        } else {
            setPosition(getPosition() + movableDistance);
        }
    }

    @Override
    public String toCustomString() {
        //TODO: Problem 2.2
        String playerNumber;
        switch (getPlayerNum() % 10) {
            case 1:
                playerNumber = getPlayerNum() + "st";
                break;
            case 2:
                playerNumber = getPlayerNum() + "nd";
                break;
            case 3:
                playerNumber = getPlayerNum() + "rd";
                break;
            default:
                playerNumber = getPlayerNum() + "th";
                break;
        }
        return playerNumber + " animal player, rabbit";
    }

    public boolean throwUp(double position, Map map){
        //TODO: Problem 2.2
        return /*isThrowUp &&*/ map.getOnWater(position);
        //return map.getWaterStart() - position >= 0 && map.getWaterStart() - position < getVelocity();
    }

    @Override
    public boolean getThrowUp(){
        //TODO: Problem 2.2
        return throwUp(getPosition(), getMap());
    }

    @Override
    public int compareTo(Player o) {
        double val = this.getPosition() - o.getPosition();
        if(val==0) return 0;
        else if(val > 0) return 1;
        else return -1;
    }
}
