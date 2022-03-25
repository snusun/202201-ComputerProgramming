public class SquareTable {
    public static void printSquareTable(int n) {
        // DO NOT change the skeleton code.
        // You can add codes anywhere you want.
        int s = (int) Math.sqrt(n);
        for(int i=1; i<=s; i++){
            printOneSquare(i, i*i);
        }
    }

    private static void printOneSquare(int a, int b) {
        System.out.printf("%d times %d = %d\n", a, a, b);
    }
}
