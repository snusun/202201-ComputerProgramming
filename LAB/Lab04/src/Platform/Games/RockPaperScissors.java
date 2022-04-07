package Platform.Games;

import java.util.Scanner;

public class RockPaperScissors {
    public int playGame() {
        String[] rps = {"scissor", "rock", "paper"};
        Scanner scanner = new Scanner(System.in);
        String user = scanner.next();
        String opponent = rps[(int) (Math.random() * 3)];
        if (!(user.equals(rps[0]) || user.equals(rps[1]) || user.equals(rps[2]))) {
            System.out.println(user + " " + opponent);
            return -1;
        }


        System.out.println(user + " " + opponent);
        if (user.equals(opponent)) {
            return 0;
        }
        if (user.equals(rps[0])) {
            if (opponent.equals(rps[1])) return -1;
            else return 1;
        } else if (user.equals(rps[1])) {
            if (opponent.equals(rps[2])) return -1;
            else return 1;
        } else if (user.equals(rps[2])) {
            if (opponent.equals(rps[0])) return -1;
            else return 1;
        }
        return -1;
    }
}
