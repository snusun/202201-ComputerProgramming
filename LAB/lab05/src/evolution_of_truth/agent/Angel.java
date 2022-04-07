package evolution_of_truth.agent;

import evolution_of_truth.Match;
import population.Individual;

public class Angel extends Agent {
    public Angel() { super("Angel");}

    @Override
    public int choice(int twicePreviousOpponentChoice, int previousOpponentChoice){
        return Match.COOPERATE;
    }

    @Override
    public Individual clone(){
        return new Angel();
    }
}
