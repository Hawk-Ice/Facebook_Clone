package social.media.mvvm;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.firestore.DocumentSnapshot;

import java.util.List;

import social.media.DataStorage;
import social.media.Models.Post;

public class ViewModel extends AndroidViewModel {

    Repository repository;
    private MutableLiveData<DocumentSnapshot> documentSnapshotMutableLiveData;
    private MutableLiveData<List<Post>> postsSnapshotMutableLiveData;
    private MutableLiveData<List<Post>> postsMutableLiveData;

    public ViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
        documentSnapshotMutableLiveData = repository.getLiveData();
        postsSnapshotMutableLiveData = repository.getLivePostsData();
        postsMutableLiveData = DataStorage.getUser().getLivePostList();
    }

    public MutableLiveData<DocumentSnapshot> getLiveData(){
        return documentSnapshotMutableLiveData;
    }
    public MutableLiveData<List<Post>> getLivePostsData(){
        return postsSnapshotMutableLiveData;
    }

    public MutableLiveData<List<Post>> getPostsMutableLiveData() {
        return postsMutableLiveData;
    }

    public void updatePost(String documentID){
        repository.updatePost(documentID);
    }

}
