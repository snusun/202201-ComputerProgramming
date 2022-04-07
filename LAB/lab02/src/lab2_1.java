import java.util.Scanner;

public class lab2_1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int num = scanner.nextInt();
        //System.out.println(num);

        String[] arr = new String[num];
        for(int i=0; i<num; i++){
            arr[i] = scanner.next();
        }

        for(String s : arr){
            System.out.print(s + " ");
        }
        System.out.println();

        for(int i=0; i<num; i++){
            System.out.print(arr[num-1-i] + " ");
        }
        System.out.println();
    }
}
