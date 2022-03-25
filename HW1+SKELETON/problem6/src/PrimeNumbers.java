public class PrimeNumbers {
    public static void printPrimeNumbers(int m, int n) {
        // DO NOT change the skeleton code.
        // You can add codes anywhere you want.
        for(int i = m; i <= n; i++) {
            isPrime(i);
        }
    }

    public static void isPrime(int num) {
        if(num < 2) return;
        if(num == 2) {
            System.out.println(num);
            return;
        }
        for(int i = 2; i <= Math.sqrt(num); i++) {
            if(num % i == 0) return;
        }
        System.out.print(num+ " ");

    }
}
