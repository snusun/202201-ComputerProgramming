public class CardGameSimulator {
	private static final Player[] players = new Player[2];

	public static void simulateCardGame(String inputA, String inputB) {
		// DO NOT change the skeleton code.
		// You can add codes anywhere you want.
		Player A = new Player("A", inputA);
		Player B = new Player("B", inputB);
	}

	private static void printLoseMessage(Player player) {
		System.out.printf("Player %s loses the game!\n", player);
	}
}

class Player {
	private String name;
	private Card[] deck;

	public void playCard(Card card) {
		System.out.printf("Player %s: %s\n", name, card);
	}

	@Override
	public String toString() {
		return name;
	}

	Player(String name, String deck){
		this.name = name;
		String[] d = deck.split(" ");
		this.deck = new Card[d.length];
		for(int i=0; i<d.length; i++){
			this.deck[i] = new Card(d[0].charAt(0), d[1].charAt(1));
		}
	}
}

class Card {
	private char number; // int
	private char letter;

	@Override
	public String toString() {
		return "" + number + letter;
	}

	Card(char number, char letter){
		this.number = number;
		this.letter = letter;
	}
}
