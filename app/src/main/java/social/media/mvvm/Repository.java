package social.media.mvvm;

import android.app.Application;

import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import social.media.DataStorage;
import social.media.Models.Post;

public class Repository {

    private static FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference collectionReference = db.collection("SocialMediaUsers");
    private MutableLiveData<DocumentSnapshot> documentSnapshotMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<List<Post>> postsSnapshotMutableLiveData = new MutableLiveData<>();

    public Repository(Application application){
        // -- Initial value of main document mutable live data
        collectionReference.document(DataStorage.getDocumentName()).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        documentSnapshotMutableLiveData.postValue(documentSnapshot);
                    }
                });

        // -- listen to main user document changes
        collectionReference.document(DataStorage.getDocumentName()).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                documentSnapshotMutableLiveData.postValue(documentSnapshot);
            }
        });

        // -- Initial value of POSTS mutable live data
        collectionReference.document(DataStorage.getDocumentName()).collection("posts")
                .whereEqualTo("owner", DataStorage.getDocumentName()).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<Post> postList = new ArrayList<>();
                        for(DocumentSnapshot documentSnapshot : queryDocumentSnapshots){
                            Post post = documentSnapshot.toObject(Post.class);
                            postList.add(post);
                        }
                        postsSnapshotMutableLiveData.postValue(postList);



                    }
                });

        // -- listen to subcollection - "Posts" changes
        collectionReference.document(DataStorage.getDocumentName()).collection("posts")
                .whereEqualTo("owner", DataStorage.getDocumentName())
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

                        List<Post> updatedPostsList = new ArrayList<>();

                        for(DocumentSnapshot documentSnapshot : queryDocumentSnapshots){
                            updatedPostsList.add(documentSnapshot.toObject(Post.class));
                        }
                        postsSnapshotMutableLiveData.postValue(updatedPostsList);
                    }
                });

    }

    public MutableLiveData<DocumentSnapshot> getLiveData(){
        return documentSnapshotMutableLiveData;
    }

    public MutableLiveData<List<Post>> getLivePostsData(){
        return postsSnapshotMutableLiveData;
    }


    public void updatePost(final String documentID){

        collectionReference.document(DataStorage.getDocumentName()).collection("likedPosts")
                .document(documentID).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if(documentSnapshot.exists()) {
                            // -- unlike a post
                            collectionReference.document(DataStorage.getDocumentName()).collection("posts").document(documentID)
                                    .update("likes", FieldValue.arrayRemove(DataStorage.getUser().getUsername()));
                            collectionReference.document(DataStorage.getDocumentName()).collection("likedPosts").document(documentID).delete();

                        }
                        else{
                            // -- like a post
                            collectionReference.document(DataStorage.getDocumentName()).collection("posts").document(documentID)
                                    .update("likes", FieldValue.arrayUnion(DataStorage.getUser().getUsername()));
                            Map<String, Boolean> liked = new HashMap<>();
                            liked.put("liked", true);
                            collectionReference.document(DataStorage.getDocumentName()).collection("likedPosts").document(documentID).set(liked);
                            /////////////
                            Map<String, Boolean> field = new HashMap<>();
                            field.put("liked", true);
                            collectionReference.document(DataStorage.getDocumentName()).collection("likedPosts").document(documentID).set(field);
                        }
                    }
                });

    }
}
