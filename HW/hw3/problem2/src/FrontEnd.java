import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;

public class FrontEnd {
    private UserInterface ui;
    private BackEnd backend;
    private User user;
    private final static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

    public FrontEnd(UserInterface ui, BackEnd backend) {
        this.ui = ui;
        this.backend = backend;
    }

    public boolean auth(String authInfo) {
        // TODO sub-problem 1
        String[] info = authInfo.split("\n");
        if (backend.authentication(info[0], info[1])) {
            user = new User(info[0], info[1]);
            return true;
        } else return false;
    }

    public void post(List titleContentList) {
        // TODO sub-problem 2
        LocalDateTime dateTime = LocalDateTime.now();
        Post post = new Post(0, dateTime, titleContentList.get(1).toString(),
                titleContentList.get(0).toString(), titleContentList.get(2).toString());
        backend.post(user.id, post);
    }

    public void recommend(int N) {
        // TODO sub-problem 3
        LinkedList<Post> posts = backend.recommend(user.id);

        if (posts.size() < N) {
            for (Post post : posts) {
                ui.println(post);
            }
        } else {
            for (int i = 0; i < N; i++) {
                ui.println(posts.get(i));
            }
        }
    }

    public void search(String command) {
        LinkedList<Occurrence> posts = backend.search(command);
        //System.out.println("개수: " + posts.size());
        if(posts.size()<10){
            for (Occurrence o : posts) {
                ui.println(o.post.getSummary());
            }
        } else {
            for (int i = 0; i < 10; i++) {
                ui.println(posts.get(i).post.getSummary());
            }
        }
    }

    User getUser() {
        return user;
    }
}
