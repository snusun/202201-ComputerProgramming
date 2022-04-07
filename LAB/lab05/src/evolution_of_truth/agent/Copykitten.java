package evolution_of_truth.agent;

import evolution_of_truth.Match;
import population.Individual;

public class Copykitten extends Agent{
    public Copykitten() {
        super("Copykitten");
    }

    @Override
    public int choice(int twicePreviousOpponentChoice, int previousOpponentChoice){
        if(twicePreviousOpponentChoice== Match.CHEAT && previousOpponentChoice== Match.CHEAT){
            return Match.CHEAT;
        }
        return Match.COOPERATE;
    }

    @Override
    public Individual clone(){
        return new Copykitten();
    }
}
