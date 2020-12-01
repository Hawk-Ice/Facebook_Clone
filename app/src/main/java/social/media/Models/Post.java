package social.media.Models;

import com.google.firebase.firestore.DocumentId;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import social.media.Models.Like;

public class Post {

    @DocumentId
    private String id;
    private int numberOfComments;
    private Map<String,String> latestComment;
    private int numberOfReactsCounter;
    private Map<String, Integer> numberOfReacts;
    private String authorAvatar;

    public Post(int numberOfComments, int numberOfReactsCounter, Map<String, Integer> numberOfReacts, String username, String text, String authorUID) {
        this.numberOfComments = numberOfComments;
        this.numberOfReactsCounter = numberOfReactsCounter;
        this.numberOfReacts = numberOfReacts;
        this.username = username;
        this.text = text;
        this.authorUID = authorUID;
    }

    private String username;
    private String text;
    private String authorUID;

    public Post(){

    }

    public String getAuthorAvatar() {
        return authorAvatar;
    }

    public int getNumberOfComments() {
        return numberOfComments;
    }

    public String getUsername() {
        return username;
    }

    public String getText() {
        return text;
    }

    public String getAuthorUID() {
        return authorUID;
    }

    public Map<String, Integer> getNumberOfReacts() {
        return numberOfReacts;
    }

    public String getId() {
        return id;
    }

    public int getNumberOfReactsCounter() {
       return numberOfReactsCounter;
    }

    public Map<String,String> getLatestComment() {
        return latestComment;
    }
}
