public class CardGameSimulator {
    private static final Player[] players = new Player[2];
    private static Card[] deckA;
    private static Card[] deckB;
    private static Card prevCard;

    public static void simulateCardGame(String inputA, String inputB) {
        // DO NOT change the skeleton code.
        // You can add codes anywhere you want.
        Player A = new Player("A", inputA);
        Player B = new Player("B", inputB);

        deckA = A.getDeck();
        deckB = B.getDeck();
        int i=0;
        for (; i < 20; i++) {
            if (i == 0) { // A first
                prevCard = findMaxCard();
                A.playCard(prevCard);
            } else {
                int turn = i % 2;
                //System.out.println(prevCard);
                Card currCard = findNextCard(prevCard, turn);
                if (currCard.getNumber()=='0') break;
                if (turn == 0) {
                    A.playCard(currCard);
                } else {
                    B.playCard(currCard);
                }
                prevCard = currCard;
            }
        }
        if(i%2==0){
            printLoseMessage(A);
        } else {
            printLoseMessage(B);
        }
    }

    private static void printLoseMessage(Player player) {
        System.out.printf("Player %s loses the game!\n", player);
    }

    private static Card findMaxCard() {
        Card maxCard = new Card('0', 'E');
        for (Card card : deckA) {
            if (maxCard.getNumber() < card.getNumber()) {
                maxCard = card;
            } else if (maxCard.getNumber() == card.getNumber()) {
                if (maxCard.getLetter() > card.getLetter()) {
                    maxCard = card;
                }
            }
        }
        for (int j = 0; j < deckA.length; j++) {
            if (maxCard.getNumber() == deckA[j].getNumber() && maxCard.getLetter() == deckA[j].getLetter()) {
                deckA[j] = new Card('0', 'E');
                break;
            }
        }
        return maxCard;
    }

    private static Card findNextCard(Card prevCard, int turn) {
        Card[] deck;
        Card nextCard = new Card('0', 'E');
        if (turn == 0) {
            deck = deckA;
        } else {
            deck = deckB;
        }

        for (Card card : deck) {
            if (prevCard.getNumber() == card.getNumber()) {
                if (nextCard.getLetter() > card.getLetter()) {
                    nextCard = card;
                }
            }
        }
        if (nextCard.getNumber() == '0') {
            for (Card card : deck) {
                if (prevCard.getLetter() == card.getLetter()) {
                    if (nextCard.getNumber() < card.getNumber()) {
                        nextCard = card;
                    }
                }
            }
        }

        if (nextCard.getNumber() != '0') {
            for (int i = 0; i < deck.length; i++) {
                if(turn == 0) {
                    if (nextCard.equals(deckA[i])) {
                        deckA[i] = new Card('0', 'E');
                        break;
                    }
                } else {
                    if (nextCard.equals(deckB[i])) {
                        deckB[i] = new Card('0', 'E');
                        break;
                    }
                }
            }

        }
        return nextCard;
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

    Player(String name, String deck) {
        this.name = name;
        String[] d = deck.split(" ");
        this.deck = new Card[d.length];
        for (int i = 0; i < d.length; i++) {
            this.deck[i] = new Card(d[i].charAt(0), d[i].charAt(1));
        }
    }

    public Card[] getDeck() {
        return deck;
    }
}

class Card {
    private char number; // int
    private char letter;

    @Override
    public String toString() {
        return "" + number + letter;
    }

    Card(char number, char letter) {
        this.number = number;
        this.letter = letter;
    }

    public char getNumber() {
        return number;
    }

    public char getLetter() {
        return letter;
    }
}
