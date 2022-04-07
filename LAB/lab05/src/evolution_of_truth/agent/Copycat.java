package evolution_of_truth.agent;

import evolution_of_truth.Match;
import population.Individual;

public class Copycat extends Agent{
    public Copycat() {
        super("Copycat");
    }

    @Override
    public int choice(int twicePreviousOpponentChoice, int previousOpponentChoice){
        if(previousOpponentChoice==Match.UNDEFINED){
            return Match.COOPERATE;
        }
        return previousOpponentChoice;
    }

    @Override
    public Individual clone(){
        return new Copycat();
    }
}
