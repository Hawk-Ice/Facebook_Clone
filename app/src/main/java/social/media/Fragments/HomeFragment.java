package social.media.Fragments;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

import social.media.Adapters.OnlineStatusAdapter;
import social.media.DataStorage;
import social.media.Models.Post;
import social.media.Adapters.SampleAdapter;
import social.media.Models.User;
import social.media.databinding.FragmentHomeBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    FragmentHomeBinding binding;
    RecyclerView recyclerView;
    SampleAdapter postsAdapter;
    OnlineStatusAdapter onlineStatusAdapter;
    public static FirebaseFirestore db = FirebaseFirestore.getInstance();
    public static CollectionReference feedsCollection = db.collection("FeedsCollection");
    public static CollectionReference friendsCollection = db.collection("FriendsCollection");

    public HomeFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        // -- Query for posts
        Query postQuery = feedsCollection.document(DataStorage.getDocumentName()).collection("Posts");
        FirestoreRecyclerOptions<Post> postOptions = new FirestoreRecyclerOptions.Builder<Post>()
                .setQuery(postQuery, Post.class)
                .build();
        // -- Query for online friends
        Query onlineStatusQuery = friendsCollection.document(DataStorage.getDocumentName()).collection("Friends");
        FirestoreRecyclerOptions<User> userOptions = new FirestoreRecyclerOptions.Builder<User>()
                .setQuery(onlineStatusQuery, User.class)
                .build();

        // -- setup recyclerview
        onlineStatusAdapter = new OnlineStatusAdapter(userOptions);
//        onlineStatusAdapter.setAdapterContext(getActivity());

        postsAdapter = new SampleAdapter(postOptions);
        postsAdapter.setAdapterContext(getActivity());
        postsAdapter.setOnItemClickInterface(new SampleAdapter.OnItemClickInterface() {
            @Override
            public void onReactionSelected(String documentID, String action) {
//                Map<String, String> reaction = new HashMap<>();
//                reaction.put("reaction", action);
//                db.collection("PostsCollection").document(documentID).collection("Reactions").add(reaction);
            }

            @Override
            public void onReactionReselected(final String documentID, final String action) {
//                Map<String, String> reaction = new HashMap<>();
//                reaction.put("reaction", action);
//                db.collection("PostsCollection").document(documentID).collection("Reactions")
//                        .whereEqualTo("reactionBy", DataStorage.getDocumentName())
//                        .get()
//                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                            @Override
//                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                                if(!task.getResult().isEmpty()){
//                                    String reactionDocumentID = task.getResult().getDocuments().get(0).getId();
//                                    db.collection("PostsCollection").document(documentID).collection("Reactions").document(reactionDocumentID)
//                                            .update("reaction", action);
//                                }
//                            }
//                        });
            }

            @Override
            public void onReactionUnselected(final String documentID) {
//                db.collection("PostsCollection").document(documentID).collection("Reactions")
//                        .whereEqualTo("reactionBy", DataStorage.getDocumentName())
//                        .get()
//                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                            @Override
//                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                                if(!task.getResult().isEmpty()){
//                                    String reactionDocumentID = task.getResult().getDocuments().get(0).getId();
//                                    db.collection("PostsCollection").document(documentID).collection("Reactions").document(reactionDocumentID)
//                                            .delete();
//                                }
//                            }
//                        });
            }

            @Override
            public void onReactionLayoutClicked(String postID) {

                CommentsBottomSheet commentsBottomSheet = new CommentsBottomSheet(postID);
                commentsBottomSheet.show(getActivity().getSupportFragmentManager(), "Comment Bottom Sheet");
            }
        });
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        // -- look into this
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.recyclerView.setNestedScrollingEnabled(false);
        binding.recyclerView.setAdapter(postsAdapter);
        // -- online status
        binding.onlineFriendsRecyclerView.setHasFixedSize(true);
        binding.onlineFriendsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        binding.onlineFriendsRecyclerView.setNestedScrollingEnabled(false);
        binding.onlineFriendsRecyclerView.setAdapter(onlineStatusAdapter);

        return view;
    }
    @Override
    public void onStart() {
        super.onStart();
        postsAdapter.startListening();
        onlineStatusAdapter.startListening();
    }
    @Override
    public void onStop() {
        super.onStop();
        postsAdapter.stopListening();
        onlineStatusAdapter.stopListening();
    }
}