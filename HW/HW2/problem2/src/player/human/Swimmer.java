package relay.player.human;

import relay.map.Map;
import relay.player.Player;
import relay.player.Swimmable;
import relay.simulator.Message;

public class Swimmer extends Human implements Swimmable {
    private double swimSpeed;
    private boolean isWarmUp = false;

    public Swimmer(Map map){this(0, map);}
    public Swimmer(double position, Map map) {
        super(position,1.5,map);
        this.swimSpeed= 2;
    }

    @Override
    public void hear(Message message) {
        //TODO: Problem 2.2
        if(!getMap().getOnWater(message.getCurrentHuman().getPosition())){
            double humanPosition = message.getCurrentHuman().getPosition();
            double animalPosition = message.getCurrentAnimal().getPosition();
            double distanceToAnimal = humanPosition - animalPosition;
            double distanceToBoundary = message.getCurrentHuman().getEyesight().getDistanceToBoundary(humanPosition);
            double distanceToNextPlayer = message.getCurrentHuman().getEyesight().getDistanceToNextPlayer(humanPosition);
            double dis = distanceToBoundary < distanceToNextPlayer ? distanceToBoundary : distanceToNextPlayer;
            double range = message.getCurrentHuman().getEyesight().getRange();
            if (distanceToAnimal > -2 && distanceToAnimal < 2 && dis < range) { // dis <= range ?
                setVelocity(2);
            }
        }
    }

    @Override
    public void move() {
        //TODO: Problem 2.2
		//Use swim method.
        if(getMap().getOnWater(getPosition())){
            swim();
        } else {
            double dis = getMovableDistance(getVelocity());
            setPosition(getPosition() + dis);
        }
    }
    public void swim(){
        //TODO: Problem 2.2
        if(getPosition()==getMap().getWaterStart() && !isWarmUp){
            isWarmUp = true;
        } else {
            double dis = getMovableDistance(swimSpeed);
            setPosition(getPosition() + dis);
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
        return playerNumber + " human player, swimmer";
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
