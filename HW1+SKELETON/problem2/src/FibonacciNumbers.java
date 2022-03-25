import java.util.*;

public class FibonacciNumbers {
    static int[] fibo;

    public static void printFibonacciNumbers(int n) {
        // DO NOT change the skeleton code.
        // You can add codes anywhere you want.
        if(n==1) {
            System.out.println(0);
            System.out.println("sum : 0");
        }

        fibo = new int[n];
        fibo[0] = 0;
        fibo[1] = 1;
        int sum=0;

        for(int i=2; i<n; i++){
            fibo[i] = fibo[i-1] + fibo[i-2];
        }

        for(int f : fibo){
            System.out.print(f + " ");
            sum+=f;
        }
        System.out.println();
        if(n > 5){
            String str = Integer.toString(sum);
            System.out.println("sum : " + str.substring(str.length()-5));
        } else System.out.println("sum : " + sum);

    }
}
