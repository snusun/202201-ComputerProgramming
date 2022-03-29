public class HexNumberCounter {
    public static void countHexNumbers(int n) {
        // DO NOT change the skeleton code.
        // You can add codes anywhere you want.
        String hex = Integer.toHexString(n);
        System.out.println(hex);
        int[] cnt = new int[16];

        for (int i = 0; i < hex.length(); i++) {
            char c = hex.charAt(i);
            if (c >= 97 && c <= 102) {
                int idx = (int) c - 87;
                cnt[idx]++;
            } else {
                cnt[Character.getNumericValue(c)]++;
            }
        }

        for(int i=0; i<16; i++){
            if(cnt[i]==0){
                continue;
            }
            if(i<=9){
                printNumberCount((char)(i +'0'), cnt[i]);
            } else {
                printNumberCount((char)(i +87), cnt[i]);
            }
        }

    }

    private static void printNumberCount(char number, int count) {
        System.out.printf("%c: %d times\n", number, count);
    }
}
