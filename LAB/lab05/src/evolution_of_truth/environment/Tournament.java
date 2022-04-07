package evolution_of_truth.environment;

import evolution_of_truth.Match;
import evolution_of_truth.MistakeMatch;
import evolution_of_truth.agent.*;
import population.Individual;
import population.Population;

public class Tournament {
    Population agentPopulation;

    public Tournament() {
        agentPopulation = new Population();
        for (int i=0; i<15; i++){
            agentPopulation.addIndividual(new Copykitten());
            //agentPopulation.addIndividual(new Angel());
        }
        for(int i=0; i<5; i++){
            agentPopulation.addIndividual(new Devil());
        }
        for(int i=0; i<5; i++){
            agentPopulation.addIndividual(new Copycat());
        }
    }

    private MistakeMatch[] createAllMatches(){
    //private Match[] createAllMatches(){
        int n = agentPopulation.size();
        Individual[] agents = agentPopulation.getIndividuals();
        MistakeMatch[] mistakeMatches = new MistakeMatch[n*(n-1)/2];

        //Match[] matches = new Match[n*(n-1)/2];
        int index =0;
        for(int i=0; i<n; i++){
            for(int j=1+i; j<n; j++){
                mistakeMatches[index++] = new MistakeMatch((Agent) agents[i], (Agent) agents[j]);
                //matches[index++] = new Match((Agent) agents[i], (Agent) agents[j]);
            }
        }
        return mistakeMatches;
    }

    public void playAllGames(int numRounds){
        MistakeMatch[] mistakeMatches = createAllMatches();

        //Match[] matches = createAllMatches();
        for(int i=0; i<numRounds; i++){
            for(MistakeMatch mistakeMatch: mistakeMatches){
                mistakeMatch.playGame();
            }
        }
    }

    public void describe(){
        Individual[] agents = agentPopulation.getIndividuals();
        for(Individual _agent: agents){
            Agent agent = (Agent)_agent;
            System.out.print(agent.toString() + " / ");
        }
        System.out.println();
    }

    public void evolvePopulation() {
        agentPopulation.toNextGeneration(5);
    }

    public void resetAgents() {
        Individual[] agents = agentPopulation.getIndividuals();
        for(Individual _agent: agents){
            Agent agent = (Agent) _agent;
            agent.setScore(0);
        }
    }
}
