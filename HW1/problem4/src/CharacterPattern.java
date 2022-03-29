public class CharacterPattern {
    public static void searchCharPattern(String str) {
        // DO NOT change the skeleton code.
        // You can add codes anywhere you want.
        StringBuilder ans = new StringBuilder();
        for(int i=1; i<str.length()-1; i++){
            char prev = str.charAt(i-1);
            char now = str.charAt(i);
            char next = str.charAt(i+1);
            if(prev+1==now && now+1==next){
                ans.append(str.charAt(i));
            }
            if(Character.isUpperCase(now) && prev-32==now && next-32==now){
                ans.append(str.charAt(i));
            }
        }
        System.out.println(ans);
    }
}
