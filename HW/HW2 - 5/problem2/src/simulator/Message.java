package relay.simulator;

import relay.map.Map;
import relay.player.Player;

public class Message {
    private Player currentHuman;
    private Player currentAnimal;
    private double humanPosition;
    private double animalPosition;
    private Map map;
    private int time;
    private boolean isAssigned=false;


    public Message(Player human, Player animal, Map map, int time) {
        this.currentHuman = human;
        this.currentAnimal = animal;
        this.humanPosition = human.getPosition();
        this.animalPosition = animal.getPosition();
        this.map = map;
        this.time = time;
        isAssigned = true;
    }

    public Player getCurrentHuman(){
        return currentHuman;
    }

    public Player getCurrentAnimal(){
        return currentAnimal;
    }

    public Map getMap(){
        return map;
    }

    public double getDistance() {
        //TODO: Problem 2.1
        return currentHuman.getPosition() > currentAnimal.getPosition() ?
                currentHuman.getPosition() - currentAnimal.getPosition() :
                currentAnimal.getPosition() - currentHuman.getPosition();
    }

    @Override
    public String toString() {
        //TODO: Problem 2.1
            boolean isThrowUpHuman = currentHuman.getThrowUp();
            boolean isThrowUpAnimal = currentAnimal.getThrowUp();
            //double humanPosition = currentHuman.getPosition();
            //double animalPosition = currentAnimal.getPosition();
            if(isThrowUpHuman && isThrowUpAnimal) {
                return time+": [FINISH] Both teams throw up the race";
            } else if(humanPosition == 0 && animalPosition == 0){
                return time+": [READY] Human team : "+ currentHuman.toCustomString() +" / Animal team : " + currentAnimal.toCustomString() + " are at 0";
            } else if(humanPosition == map.getMapEnd() && animalPosition == map.getMapEnd()){
                return time+": [FINISH] Both teams reach the goal at the same time"; // both end point
            } else if(humanPosition == map.getMapEnd()){
                return time+": [FINISH] Human team wins"; // human reach
            } else if(animalPosition == map.getMapEnd()){
                return time+": [FINISH] Animal team wins"; // animal reach
            } else {
                return time+": [RUNNING] Human team : " + currentHuman.toCustomString() + " is at " + currentHuman.getPosition() +
                        " / Animal team : " + currentAnimal.toCustomString() + " is at " + currentAnimal.getPosition(); // middle of race
            }
    }
}
