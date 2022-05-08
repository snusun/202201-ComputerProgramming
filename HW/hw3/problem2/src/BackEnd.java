import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class BackEnd extends ServerResourceAccessible {
    // Use getServerStorageDir() as a default directory
    // TODO sub-program 1 ~ 4 :
    // Create helper functions to support FrontEnd class
    String serverStorageDir = getServerStorageDir();
    private final static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

    public boolean authentication(String id, String passwd) { // 예외처리도 하기
        String passwordPath = serverStorageDir + id + "/password.txt";
        String password = "";
        try {
            File doc = new File(passwordPath);
            BufferedReader obj = new BufferedReader(new FileReader(doc));
            String content;
            while ((content = obj.readLine()) != null)
                password += content;
        } catch (Exception ignored) {}

        return passwd.equals(password);
    }

    public void post(String id, Post post){
        String postPath = serverStorageDir;
        File file = new File(postPath);
        File[] dirList = file.listFiles();

        int maxID=0;
        for(File dir: dirList){
            if(dir.isDirectory()){
                String path = dir.getPath() + "/post";
                File postFile = new File(path);
                String[] postNames = postFile.list();

                if(postNames.length!=0){
                    for(String s : postNames){
                        String fileIDstr = s.replace(".txt", "");
                        int fileID = Integer.parseInt(fileIDstr);
                        if(maxID < fileID) maxID = fileID;
                    }
                }
            }
        }

        maxID++;

        File newFile = new File(serverStorageDir + id + "/post" + "/" + maxID + ".txt");
        post.setId(maxID);
        String date = post.getDate();

        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(newFile));
            bw.write(date);
            bw.write("\n");
            bw.write(post.getTitle());
            bw.write("\n");
            bw.write(post.getAdvertising());
            bw.write("\n");
            bw.write("\n");
            bw.write(post.getContent());
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public LinkedList<Post> recommend(String id){
        LinkedList<String> friends = new LinkedList<>();
        LinkedList<Post> posts = new LinkedList<>();

        String friendFilePath = serverStorageDir + id + "/friend.txt";
        try {
            File doc = new File(friendFilePath);
            BufferedReader obj = new BufferedReader(new FileReader(doc));
            String friendID;
            while ((friendID = obj.readLine()) != null)
                friends.add(friendID);
        } catch (Exception ignored) {}

        for(String f: friends){
            String postPath = serverStorageDir + f + "/post";
            File file = new File(postPath);
            if (file.isDirectory()) {
                String[] files = file.list();

                for(String eachFile: files) {
                    String passwordPath = postPath + "/" + eachFile;
                    //String password = "";
                    try {
                        File doc = new File(passwordPath);
                        BufferedReader obj = new BufferedReader(new FileReader(doc));

                        String fileIDstr = eachFile.replace(".txt", "");
                        int fileID = Integer.parseInt(fileIDstr);

                        String date="";
                        String title="";
                        String advertising="";
                        String content="";
                        String line;

                        int cnt=0;
                        while ((line = obj.readLine()) != null){
                            if(cnt==0) date = line;
                            else if(cnt==1) title = line;
                            else if(cnt==2) advertising = line;
                            else if(cnt>3) content+=line;
                            cnt++;
                        }

                        Post post = new Post(fileID, Post.parseDateTimeString(date, formatter),
                                advertising, title, content);

                        posts.add(post);
                    } catch (Exception ignored) { }
                }
            }
        }

        Collections.sort(posts);

//        for(Post p: posts){
//            System.out.println(p.toString());
//        }

        return posts;
    }


}
