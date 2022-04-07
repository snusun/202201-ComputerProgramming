package evolution_of_truth.agent;

import population.Individual;

abstract public class Agent extends Individual{
    private int score;
    String name;

    public Agent() {
        score = 0;
    }
    public Agent (String name) {
        this.name = name;
    }

    public int sortKey(){
        return getScore();
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    abstract public int choice(int twicePreviousOpponentChoice, int previousOpponentChoice);

    @Override
    public String toString(){
        return name + ": " + getScore();
    }
}

