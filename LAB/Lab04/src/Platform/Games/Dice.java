package Platform.Games;

public class Dice {
    public int playGame() {
        int userDice = (int) (Math.random() * 100);
        int opponentDice = (int) (Math.random() * 100);
        System.out.println(userDice + " " + opponentDice);
        return Integer.compare(userDice, opponentDice);
    }
}
