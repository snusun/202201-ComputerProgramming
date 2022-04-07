package evolution_of_truth;

import evolution_of_truth.agent.Agent;

public class Match {
    public static int CHEAT = 0;
    public static int COOPERATE = 1;
    public static int UNDEFINED = -1;

    private static int ruleMatrix[][][] = {
            {
                    {0, 0}, // A cheats, B cheats
                    {3, -1} // A cheats, B cooperates

            },
            {
                    {-1, 3}, // A cooperates, B cheats
                    {2, 2} // A cooperates, B cooperates
            }
    };

    Agent agentA, agentB;
    int twicePreviousChoiceA, twicePreviousChoiceB, previousChoiceA, previousChoiceB;

    public Match(Agent agentA, Agent agentB){
        this.agentA = agentA;
        this.agentB = agentB;
        previousChoiceA = UNDEFINED;
        previousChoiceB = UNDEFINED;
        twicePreviousChoiceA = UNDEFINED;
        twicePreviousChoiceB = UNDEFINED;
    }

    public void playGame(){
        int choiceA = agentA.choice(twicePreviousChoiceB, previousChoiceB);
        int choiceB = agentB.choice(twicePreviousChoiceA, previousChoiceA);

        agentA.setScore(agentA.getScore() + ruleMatrix[choiceA][choiceB][0]);
        agentB.setScore(agentB.getScore() + ruleMatrix[choiceA][choiceB][1]);

        twicePreviousChoiceA = previousChoiceA;
        twicePreviousChoiceB = previousChoiceB;
        previousChoiceA = choiceA;
        previousChoiceB = choiceB;
    }
}
