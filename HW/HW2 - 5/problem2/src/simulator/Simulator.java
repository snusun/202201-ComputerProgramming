package relay.simulator;

import relay.map.Map;
import relay.player.Player;
import relay.player.animal.Animal;
import relay.player.human.Human;

import java.util.ArrayList;

public class Simulator {
    final static int maxTeamPlayerNum = 4;
    private final ArrayList<Player> humanPlayers;
    private final ArrayList<Player> animalPlayers;
    private final Map map;
    private boolean raceFinish = false; // For testbench
    public String[] raceLogForEval = new String[200]; // For evaluation, don't remove

    private Player currentHuman;
    private Player currentAnimal;

    private boolean humanThrowUp = false;
    private boolean animalThrowUp = false;

    public Simulator(ArrayList<Player> humanPlayers, ArrayList<Player> animalPlayers, Map map) {
        //TODO: Problem 2.3

        //for ascending order of position
        java.util.Collections.sort(humanPlayers);
        java.util.Collections.sort(animalPlayers);
        this.humanPlayers = humanPlayers;
        this.animalPlayers = animalPlayers;
        this.map = map;

        // Update map, currentHuman, currentAnimal attributes of this class
        currentHuman = humanPlayers.get(0);
        currentAnimal = animalPlayers.get(0);
        humanPlayers.get(0).setCurrentPlayer(true);
        animalPlayers.get(0).setCurrentPlayer(true);

        // Human/AnimalPlayerNum and NextPlayerPosition
        for (int i = 0; i < humanPlayers.size(); i++) {
            Player humanPlayer = humanPlayers.get(i);
            humanPlayer.setPlayerNum(i + 1);
            if (i < humanPlayers.size() - 1) {
                humanPlayer.setNextPlayerPosition(humanPlayers.get(i + 1).getPosition());
            } else {
                humanPlayer.setNextPlayerPosition(map.getMapEnd());
            }
        }

        for (int i = 0; i < animalPlayers.size(); i++) {
            Player animalPlayer = animalPlayers.get(i);
            animalPlayer.setPlayerNum(i + 1);
            if (i < animalPlayers.size() - 1) {
                animalPlayer.setNextPlayerPosition(animalPlayers.get(i + 1).getPosition());
            } else {
                animalPlayer.setNextPlayerPosition(map.getMapEnd());
            }
        }
    }


    public void snapshot() {
        //TODO: Problem 2.3
        for (int i = 0; i < humanPlayers.size(); i++) {
            Player humanPlayer = humanPlayers.get(i);
            if (humanPlayer.getCurrentPlayer() && humanPlayer.getPosition() == humanPlayer.getNextPlayerPosition()) {
                if (i < humanPlayers.size() - 1) {
                    if(humanThrowUp) {break;}
                    currentHuman = humanPlayers.get(i + 1);
                    humanPlayer.passBaton(humanPlayers.get(i + 1));
                } else {
                    humanPlayer.setCurrentPlayer(false);
                }
                break;
            }
        }

        for (int i = 0; i < animalPlayers.size(); i++) {
            Player animalPlayer = animalPlayers.get(i);
            if (animalPlayer.getCurrentPlayer() && animalPlayer.getPosition() == animalPlayer.getNextPlayerPosition()) {
                if (i < animalPlayers.size() - 1) {
                    if(animalThrowUp) {break;}
                    currentAnimal = animalPlayers.get(i + 1);
                    animalPlayer.passBaton(animalPlayers.get(i + 1));
                } else {
                    animalPlayer.setCurrentPlayer(false);
                }
                break;
            }
        }
    }


    public Message talk(int time) {
        Message msg = new Message(currentHuman, currentAnimal, map, time);
        raceLogForEval[time] = msg.toString();
        return msg;
    }

    public boolean getRaceFinish() {
        return raceFinish;
    }


    public void simulate() {
        //TODO: Problem 2.3
        int time = 0;
        if(isError()){
            raceLogForEval[0] = "[ERROR] Team building error";
            raceFinish = true;
            return;
        }

        Message msg = talk(time);
        currentHuman.hear(talk(time));
        raceLogForEval[time] = msg.toString();
        //System.out.println("Simulator: " + msg);

        while (!raceFinish) {
            time++;
            currentHuman.move();
            //System.out.println(currentHuman.getPosition());
            currentAnimal.move();
            //System.out.println(currentAnimal.getPosition());

            snapshot();
            msg = talk(time);
            //System.out.println("Simulator: " + msg);
            currentHuman.hear(msg);
            raceLogForEval[time] = msg.toString();

            if (currentHuman.getThrowUp() && currentAnimal.getThrowUp()) {
                raceFinish = true;
            } else if (currentHuman.getPosition() == map.getMapEnd() || currentAnimal.getPosition() == map.getMapEnd()) {
                raceFinish = true;
            }
        }
    }

    public boolean isError() {
        if (humanPlayers.size() > maxTeamPlayerNum || animalPlayers.size() > maxTeamPlayerNum) {
            return true;
        }

        for (Player human : humanPlayers) {
            if (human instanceof Animal) {
                return true;
            }
        }

        for (Player animal : animalPlayers) {
            if (animal instanceof Human) {
                return true;
            }
        }

        return !(humanPlayers.get(0).getPosition() == 0 && animalPlayers.get(0).getPosition() == 0);
    }
}
