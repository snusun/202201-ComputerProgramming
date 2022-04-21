package relay.player.animal;

import relay.map.Map;
import relay.player.Player;
import relay.player.Swimmable;

public class Turtle extends Animal implements Swimmable {
    private double swimSpeed;

    public Turtle(Map map){this(0, map);}
    public Turtle(double position, Map map) {
        super(position,1,map);
        this.swimSpeed= 2.5;
    }

    @Override
    public void move() {
        //TODO: Problem 2.2 
		//Use swim method
        if(getMap().getOnWater(getPosition())){
            swim();
        } else {
            double dis = getMovableDistance(getVelocity());
            setPosition(getPosition() + dis);
        }
    }
    public void swim(){
        //TODO: Problem 2.2
        double dis = getMovableDistance(swimSpeed);
        setPosition(getPosition() + dis);
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
        return playerNumber + " animal player, turtle";
    }

    @Override
    public boolean getThrowUp(){return false;}

    @Override
    public int compareTo(Player o) {
        double val = this.getPosition() - o.getPosition();
        if(val==0) return 0;
        else if(val > 0) return 1;
        else return -1;
    }
}
