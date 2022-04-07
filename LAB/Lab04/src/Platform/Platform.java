package Platform;

import Platform.Games.Dice;
import Platform.Games.RockPaperScissors;
import java.util.Scanner;

public class Platform {
    private static int rounds = 1;
    public float run() {
        Scanner scanner = new Scanner(System.in);
        Dice dice = new Dice();
        RockPaperScissors rockPaperScissors = new RockPaperScissors();
        int game = scanner.nextInt();
        int sum = 0;
        if(game==0){
            for(int i=0; i<rounds; i++){
                int status = dice.playGame();
                if(status == 1) sum++;
            }
        } else if(game==1) {
            for(int i=0; i<rounds; i++){
                int status = rockPaperScissors.playGame();
                if(status == 1) sum++;
            }
        }
        return (sum / (float)rounds);
    }
    public void setRounds(int num) {
        if(rounds==1){
            rounds=num;
        }
    }

}
