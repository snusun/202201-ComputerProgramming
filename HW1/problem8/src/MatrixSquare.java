public class MatrixSquare {
    public static void printSquaredMatrix(int[][] matrix) {
        // DO NOT change the skeleton code.
        // You can add codes anywhere you want.

        int[][] ans = new int[matrix.length][matrix[0].length];

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                ans[i][j] = 0;
                for (int k = 0; k < matrix.length; k++) {
                    ans[i][j] += matrix[i][k] * matrix[k][j];
                }
                if(j== matrix[0].length-1){
                    System.out.print(ans[i][j]);
                }
                else System.out.print(ans[i][j] + " ");
            }
            System.out.println();
        }
    }
}
