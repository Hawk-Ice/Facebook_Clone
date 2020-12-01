package social.media.Fragments;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import social.media.Adapters.CommentsListAdapter;
import social.media.DataStorage;
import social.media.Models.Comment;
import social.media.R;

public class CommentsBottomSheet extends BottomSheetDialogFragment {
//    private BottomSheetListener mListener;

    public static FirebaseFirestore db = FirebaseFirestore.getInstance();
    public static CollectionReference postsCollection = db.collection("PostsCollection");
    CommentsListAdapter commentsListAdapter;
    String postID = "";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Query query = postsCollection.document(postID).collection("Comments");
        FirestoreRecyclerOptions<Comment> options = new FirestoreRecyclerOptions.Builder<Comment>()
                .setQuery(query, Comment.class)
                .build();
        // -- setup recyclerview
        commentsListAdapter = new CommentsListAdapter(options);
        commentsListAdapter.setAdapterContext(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fb_comments_layout, container, false);
        RecyclerView commentsRecyclerView = v.findViewById(R.id.commentsRecyclerView);
        commentsRecyclerView.setHasFixedSize(true);
        commentsRecyclerView.setAdapter(commentsListAdapter);
        return v;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        BottomSheetDialog bottomSheetDialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        bottomSheetDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                BottomSheetDialog dialogc = (BottomSheetDialog) dialog;
                // When using AndroidX the resource can be found at com.google.android.material.R.id.design_bottom_sheet
                FrameLayout bottomSheet = dialogc.findViewById(com.google.android.material.R.id.design_bottom_sheet);
                ViewGroup.LayoutParams layoutParams = bottomSheet.getLayoutParams();
                layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT;
                bottomSheet.setLayoutParams(layoutParams);
                BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
//            bottomSheetBehavior.setPeekHeight(Resources.getSystem().getDisplayMetrics().heightPixels);
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            }

        });
        return bottomSheetDialog;
    }
    @Override
    public void onStart() {
        super.onStart();
        commentsListAdapter.startListening();
    }
    @Override
    public void onStop() {
        super.onStop();
        commentsListAdapter.stopListening();
    }

    public CommentsBottomSheet(String postID) {
        this.postID = postID;
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        getFragmentManager().beginTransaction().remove(this);
    }
}