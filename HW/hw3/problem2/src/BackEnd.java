import java.io.*;
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
                    String filePath = postPath + "/" + eachFile;
                    //String password = "";
                    try {
                        File doc = new File(filePath);
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

        return posts;
    }

    public LinkedList<Occurrence> search(String command){
        LinkedList<Occurrence> posts = new LinkedList<>();

        String postPath = serverStorageDir;
        File dirPath = new File(postPath);
        File[] dirList = dirPath.listFiles();

        for(File dir: dirList){
            if(dir.isDirectory()){
                String path = dir.getPath() + "/post";
                File file = new File(path);

                if (file.isDirectory()) {
                    String[] files = file.list();


                    for(String eachFile: files) {
                        String filePath = path + "/" + eachFile;
                        //String password = "";
                        try {
                            File doc = new File(filePath);
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
                                else if(cnt>3) content+=line + " "; // + "\n";
                                cnt++;
                            }

                            int occur = countOccurrence(command, title, content);
                            //System.out.println("occur: " + occur);

                            if(occur>0){
                                //System.out.println("fileID" + fileID);
                                Post post = new Post(fileID, Post.parseDateTimeString(date, formatter),
                                        advertising, title, content);
                                posts.add(new Occurrence(post, occur));
                                //posts.add(post);
                                // occur 확인하고 add 하기
                            }

                        } catch (Exception ignored) { }
                    }
                }

            }
        }

        Collections.sort(posts);

        return posts;
    }

    public int countOccurrence(String cmd, String title, String content){ // 중복 거르기
        int occur=0;
        LinkedList<Integer> dupIdx = new LinkedList<>();
        String[] keywords = cmd.split(" ");
        String[] titleWords = title.split(" ");
        String[] contentWords = content.split(" ");

        for(int i=0; i<keywords.length-1; i++){
            for(int j=i+1; j<keywords.length; j++){
                if(keywords[i].equals(keywords[j])){
                    dupIdx.add(j);
                }
            }
        }

        for(int i=1; i<keywords.length; i++){
            String keyword = keywords[i];
            if(dupIdx.contains(i)) continue;
            for(String t: titleWords){
                if(keyword.equals(t)) occur++;
            }
            for(String c: contentWords){
                //System.out.println(c);
                if(keyword.equals(c)) occur++;
            }
        }

        return occur;
    }


}
