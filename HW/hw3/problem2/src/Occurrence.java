public class Occurrence implements Comparable<Occurrence>{
    Post post;
    int occur;
    int contentWords;
    Occurrence(Post post, int occur){
        this.post = post;
        this.occur = occur;
        this.contentWords = post.getContentWordNum();
    }

    @Override
    public int compareTo(Occurrence o) {
        if(this.occur==o.occur){
            return o.contentWords-this.contentWords;
        } return o.occur-this.occur;
    }
}
