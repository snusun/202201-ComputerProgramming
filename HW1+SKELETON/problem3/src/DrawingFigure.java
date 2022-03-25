public class DrawingFigure {
    public static void drawFigure(int n) {
        // DO NOT change the skeleton code.
        // You can add codes anywhere you want.
        //char c = (char)(1 +'0');
        if(n==1) {
            System.out.println(1);
            return;
        }
        // n*2-1 + n*2 - 2  = n*2 + n*2 - 1 -2 = 4*n - 3

        for(int i=1; i<=n; i++){
            for(int j=1; j<=i; j++){
                if(j==1){
                    for(int k=0; k<2*(n-i); k++){
                        System.out.print(" ");
                    }
                }
                if(i==1){
                    System.out.print(j);
                    for(int k=0; k<2*(n-i); k++){
                        System.out.print(" ");
                    }
                } else {
                    if(j>=10) {
                        System.out.print(j%10+ " ");
                    }
                    else System.out.print(j+ " ");
                }
            }

            for(int j=i-1; j>=1; j--) {
                if(j==1) {
                    System.out.print(j);
                    for(int k=0; k<2*(n-i); k++){
                        System.out.print(" ");
                    }
                } else {
                    if(j>=10) {
                        System.out.print(j%10+ " ");
                    }
                    else System.out.print(j+ " ");
                }
            }
            System.out.println();
        }

        for(int i=n-1; i>=1; i--){
            for(int j=1; j<=i; j++){
                if(j==1){
                    for(int k=0; k<2*(n-i); k++){
                        System.out.print(" ");
                    }
                }
                if(i==1){
                    System.out.print(j);
                    for(int k=0; k<2*(n-i); k++){
                        System.out.print(" ");
                    }
                } else {
                    if(j>=10) {
                        System.out.print(j%10+ " ");
                    }
                    else System.out.print(j+ " ");
                }
            }

            for(int j=i-1; j>=1; j--) {
                if(j==1) {
                    System.out.print(j);
                    for(int k=0; k<2*(n-i); k++){
                        System.out.print(" ");
                    }
                } else {
                    if(j>=10) {
                        System.out.print(j%10+ " ");
                    }
                    else System.out.print(j+ " ");
                }
            }
            System.out.println();
        }
    }
}
