package relay.player.human;

import relay.map.Map;
import relay.player.Player;
import relay.player.Throwable;
import relay.simulator.Message;

public class Runner extends Human implements Throwable {
    private boolean isThrowUp = false;

    public Runner(Map map) {
        this(0, map);
    }

    public Runner(double position, Map map) {
        super(position, 2, map);
    }

    @Override
    public void hear(Message message) {
        //TODO: Problem 2.2
        double humanPosition = message.getCurrentHuman().getPosition();
        double animalPosition = message.getCurrentAnimal().getPosition();
        double distanceToAnimal = humanPosition - animalPosition;
        double distanceToBoundary = message.getCurrentHuman().getEyesight().getDistanceToBoundary(humanPosition);
        double distanceToNextPlayer = message.getCurrentHuman().getEyesight().getDistanceToNextPlayer(humanPosition);
        double dis = distanceToBoundary < distanceToNextPlayer ? distanceToBoundary : distanceToNextPlayer;
        double range = message.getCurrentHuman().getEyesight().getRange();
        if (distanceToAnimal > -2 && distanceToAnimal < 2 && dis < range) {
            setVelocity(2.5);
        }
    }

    @Override
    public void move() {
        //TODO: Problem 2.2
        if(isThrowUp) throwUp(getPosition(), getMap());
        //System.out.println("get Throw up: " + getThrowUp());
        //System.out.println(isThrowUp);
        if(getThrowUp()) return;

        double disToNext = getEyesight().getDistanceToBoundary(getPosition()) < getEyesight().getDistanceToNextPlayer(getPosition())
                ? getEyesight().getDistanceToBoundary(getPosition()) : getEyesight().getDistanceToNextPlayer(getPosition());

        if(getMap().getOnWater(getPosition())){
            //System.out.println("getOnWater");
            if(disToNext < getEyesight().getRange()){
                setVelocity(1);
            } else {
                if(getPosition() < getMap().getWaterStart()) {
                    setPosition(getPosition() + getMovableDistance(getVelocity()));
                }
                isThrowUp = true;
                return;
            }
        } else {
            setVelocity(getVelocity());
        }

        setPosition(getPosition() + getMovableDistance(getVelocity()));
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
        return playerNumber + " human player, runner";
    }

    public boolean throwUp(double position, Map map) {
        //TODO: Problem 2.2
        double dis = getEyesight().getDistanceToBoundary(position) < getEyesight().getDistanceToNextPlayer(position)
                ? getEyesight().getDistanceToBoundary(position) : getEyesight().getDistanceToNextPlayer(position);
        if (dis < getEyesight().getRange()) {
            return false;
        } else return isThrowUp && position == map.getWaterStart();
    }

    @Override
    public boolean getThrowUp() {
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

