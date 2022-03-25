public class DecreasingString {
    public static void printLongestDecreasingSubstringLength(String inputString) {
        // DO NOT change the skeleton code.
        // You can add codes anywhere you want.
        if(inputString.length()==1){
            System.out.println(1);
            return;
        }

        int max=1;
        int cnt=1;
        for(int i=0;i<inputString.length()-1;i++){
            if(inputString.charAt(i) > inputString.charAt(i+1)){
                cnt++;
            }
            else {
                cnt = 1;
            }
            if(max<cnt) max = cnt;
        }
        System.out.println(max);
    }
}
