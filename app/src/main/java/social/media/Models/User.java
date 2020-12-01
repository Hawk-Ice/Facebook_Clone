package social.media.Models;

import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import social.media.Models.Post;

public class User {

    // -- kelangan sakto ung name nung field sa pojo attributes
    private String username;
    private String email;
    private String status;
    private Date lastOnline;
    private String userProfilePicture;

    public String getEmail() {
        return email;
    }

    private String password;



    private List<Post> postList = new ArrayList<>();
    private List<String> friends;



    private MutableLiveData<List<Post>> livePostList = new MutableLiveData<>();

    public MutableLiveData<List<Post>> getLivePostList() {
        return livePostList;
    }


    public User(){

    }

    public void setFriends(List<String> friends) {
        this.friends = friends;
    }

    public User(String email, String name, String password){
        this.email = email;
        this.username = name;
        this.password = password;
    }

    public List<String> getFriends(){
        return friends;
    }
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getUserProfilePicture() {
        return userProfilePicture;
    }

    // -- converts the hashmap to List<Post>
    public List<Post> getPosts() {
        if(postList!=null){
            return postList;
        }
        return null;
    }
    public void addPost(Post post){
        postList.add(post);
    }
    public void setPosts(List<Post> postList){
        this.postList = postList;
        this.livePostList.postValue(postList);
    }
}
