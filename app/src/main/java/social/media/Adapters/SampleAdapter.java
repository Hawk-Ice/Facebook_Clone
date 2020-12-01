package social.media.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.github.pgreze.reactions.ReactionPopup;
import com.github.pgreze.reactions.ReactionsConfig;
import com.github.pgreze.reactions.ReactionsConfigBuilder;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

import de.hdodenhof.circleimageview.CircleImageView;
import kotlin.jvm.functions.Function1;
import social.media.DataStorage;
import social.media.Fragments.HomeFragment;
import social.media.Models.Post;
import social.media.R;

public class SampleAdapter extends FirestoreRecyclerAdapter<Post, SampleAdapter.PostViewHolder> {
    public static FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference collectionReference = db.collection("SocialMediaUsers");
    public static CollectionReference usersCollection = db.collection("UsersCollection");
    public static CollectionReference feedsCollection = db.collection("FeedsCollection");
    private List<Post> posts;
    private static OnItemClickInterface onItemClickInterface;
    private static Context context;
    private StorageReference mStorageRef = FirebaseStorage.getInstance().getReference();
    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public SampleAdapter(@NonNull FirestoreRecyclerOptions<Post> options) {
        super(options);
    }
    public void setAdapterContext(Context context){
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull final PostViewHolder postViewHolder, int i, @NonNull Post post) {
        Glide.with(context)
            .load(post.getAuthorAvatar())
            .placeholder(R.drawable.ic_placeholder_avatar)
            .into(postViewHolder.profileImage);
        postViewHolder.owner.setText(post.getUsername());
        postViewHolder.body.setText(post.getText());
        // -- if NO reacts and No comments
        if(post.getNumberOfReactsCounter()==0 && post.getNumberOfComments()==0) {
            // reactionLayout = whole comment and reactions segment
            postViewHolder.reactionLayout.setVisibility(View.GONE);
            postViewHolder.latestCommentLayout.setVisibility(View.GONE);
        }
        // -- if NO Comments
        else if(post.getNumberOfReactsCounter()!=0 && post.getNumberOfComments()==0){
            postViewHolder.reactionLayout.setVisibility(View.VISIBLE);
            postViewHolder.latestCommentLayout.setVisibility(View.GONE);
            postViewHolder.postsComments.setVisibility(View.GONE);
            postViewHolder.postlikes.setVisibility(View.VISIBLE);
            postViewHolder.postlikes.setText(String.valueOf(post.getNumberOfReactsCounter()));
        }
        // -- if NO Reacts
        else if(post.getNumberOfComments()!= 0 && post.getNumberOfReactsCounter()==0){
            postViewHolder.reactionLayout.setVisibility(View.VISIBLE);
            postViewHolder.postlikes.setVisibility(View.GONE);
            postViewHolder.postsComments.setVisibility(View.VISIBLE);
            postViewHolder.postsComments.setText(post.getNumberOfComments()+" Comments");

            postViewHolder.latestCommentLayout.setVisibility(View.VISIBLE);
            postViewHolder.latestCommentText.setText(post.getLatestComment().get("commentText"));
            postViewHolder.latestCommentAuthor.setText(post.getLatestComment().get("commenter"));
            Glide.with(context)
                    .load(post.getLatestComment().get("commenterProfilePicture"))
                    .placeholder(R.drawable.ic_placeholder_avatar)
                    .into(postViewHolder.latestCommentUserImage);

            Glide.with(context)
                    .load(DataStorage.getUser().getUserProfilePicture())
                    .placeholder(R.drawable.ic_placeholder_avatar)
                    .into(postViewHolder.writeCommentLayoutUserImage);
        }
        // -- if theres Reacts and Comments
        else{
            postViewHolder.reactionLayout.setVisibility(View.VISIBLE);
            postViewHolder.postlikes.setVisibility(View.VISIBLE);
            postViewHolder.postlikes.setText(String.valueOf(post.getNumberOfReactsCounter()));
            postViewHolder.postsComments.setVisibility(View.VISIBLE);
            postViewHolder.postsComments.setText(post.getNumberOfComments()+" Comments");

            postViewHolder.latestCommentLayout.setVisibility(View.VISIBLE);
            postViewHolder.latestCommentLayout.setVisibility(View.VISIBLE);
            postViewHolder.latestCommentText.setText(post.getLatestComment().get("commentText"));
            postViewHolder.latestCommentAuthor.setText(post.getLatestComment().get("commenter"));
            Glide.with(context)
                    .load(post.getLatestComment().get("commenterProfilePicture"))
                    .placeholder(R.drawable.ic_placeholder_avatar)
                    .into(postViewHolder.latestCommentUserImage);

            Glide.with(context)
                    .load(DataStorage.getUser().getUserProfilePicture())
                    .placeholder(R.drawable.ic_placeholder_avatar)
                    .into(postViewHolder.writeCommentLayoutUserImage);
        }


//        else{
////            getThreeHighest(post.getNumberOfReacts(), postViewHolder, 1);
//            postViewHolder.postlikes.setVisibility(View.VISIBLE);
//            postViewHolder.postlikes.setText(String.valueOf(post.getNumberOfReactsCounter()));
//        }
//        if(post.getNumberOfComments()==0){
//            postViewHolder.postsComments.setVisibility(View.GONE);
//            postViewHolder.latestCommentLayout.setVisibility(View.GONE);
//        }
       

//        db.collection("ReactsCollection").document(DataStorage.getDocumentName()).collection("ReactedPosts")
//                .document(getSnapshots().getSnapshot(postViewHolder.getAdapterPosition()).getId())
//                .get()
//                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//                    @Override
//                    public void onSuccess(DocumentSnapshot documentSnapshot) {
//
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                    }
//                });


        // Check if author reacted to the current post
//        db.collection("PostsCollection").document(post.getId()).collection("Reactions")
//                .whereEqualTo("reactionBy", DataStorage.getDocumentName())
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if (!task.getResult().isEmpty()) {
//                            String reaction = (String) task.getResult().getDocuments().get(0).get("reaction");
//                            if(reaction.equals("Like")){
//                                postViewHolder.likeTextView.setText("Like");
//                                postViewHolder.likeTextView.setTextColor(Color.parseColor("#006BBF"));
//                                postViewHolder.likeIcon.setImageResource(R.drawable.ic_like_selected);
//                            }else if(reaction.equals("Love")){
//                                postViewHolder.likeTextView.setText("Love");
//                                postViewHolder.likeTextView.setTextColor(Color.parseColor("#FFFF0000"));
//                                postViewHolder.likeIcon.setImageResource(R.drawable.ic_fb_love);
//                            }else if(reaction.equals("Haha")){
//                                postViewHolder.likeTextView.setText("Haha");
//                                postViewHolder.likeTextView.setTextColor(Color.parseColor("#FFFFC107"));
//                                postViewHolder.likeIcon.setImageResource(R.drawable.ic_fb_haha);
//                            }else if(reaction.equals("Wow")){
//                                postViewHolder.likeTextView.setText("Wow");
//                                postViewHolder.likeTextView.setTextColor(Color.parseColor("#FFFFC107"));
//                                postViewHolder.likeIcon.setImageResource(R.drawable.ic_fb_wow);
//                            }else if(reaction.equals("Sad")){
//                                postViewHolder.likeTextView.setText("Sad");
//                                postViewHolder.likeTextView.setTextColor(Color.parseColor("#FFFFC107"));
//                                postViewHolder.likeIcon.setImageResource(R.drawable.ic_fb_sad);
//                            }
//                            else{
//                                postViewHolder.likeTextView.setText("Angry");
//                                postViewHolder.likeTextView.setTextColor(Color.parseColor("#FFFF5722"));
//                                postViewHolder.likeIcon.setImageResource(R.drawable.ic_fb_angry);
//                            }
//                        } else {
//                        }
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Toast.makeText(null, "Error", Toast.LENGTH_SHORT).show();
//                    }
//                });


    }




    public interface OnItemClickInterface{
        void onReactionSelected(String documentID, String action);
        void onReactionReselected(String documentID, String action);
        void onReactionUnselected(String documentID);
        void onReactionLayoutClicked(String postID);
    }

    public void setOnItemClickInterface(OnItemClickInterface onItemClickInterface) {
        this.onItemClickInterface = onItemClickInterface;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_item_layout,
                parent, false);
        return new PostViewHolder(view);
    }

    public class PostViewHolder extends RecyclerView.ViewHolder {
        TextView owner;
        TextView body;
        TextView postlikes;
        TextView postsComments;
        TextView latestCommentText;
        TextView latestCommentAuthor;
        LinearLayout latestCommentLayout;
        RelativeLayout likeButtonLayout;
        RelativeLayout commentButtonLayout;
        RelativeLayout shareButtonLayout;
        RelativeLayout reactionLayout;
        ImageView likeIcon;
        TextView likeTextView;
        CircleImageView firstReact;
        CircleImageView secondReact;
        CircleImageView thirdReact;

        CircleImageView profileImage;
        CircleImageView latestCommentUserImage;
        CircleImageView writeCommentLayoutUserImage;


        public PostViewHolder(@NonNull final View itemView) {
            super(itemView);
            owner = itemView.findViewById(R.id.textViewOwner);
            body = itemView.findViewById(R.id.textViewBody);
            profileImage = itemView.findViewById(R.id.profileImage);
            writeCommentLayoutUserImage = itemView.findViewById(R.id.writeCommentLayoutUserImage);

            postlikes = itemView.findViewById(R.id.postslikes);
            postsComments = itemView.findViewById(R.id.postsComments);
            latestCommentUserImage = itemView.findViewById(R.id.latestCommentUserImage);
            latestCommentText = itemView.findViewById(R.id.latestCommentText);
            latestCommentAuthor = itemView.findViewById(R.id.latestCommentAuthor);
            latestCommentLayout = itemView.findViewById(R.id.latestCommentLayout);

            likeButtonLayout = itemView.findViewById(R.id.likeButtonLayout);
            likeIcon = itemView.findViewById(R.id.like_icon);
            likeTextView = itemView.findViewById(R.id.like_textview);

            commentButtonLayout = itemView.findViewById(R.id.commentButtonLayout);
            shareButtonLayout = itemView.findViewById(R.id.shareButtonLayout);
            firstReact = itemView.findViewById(R.id.firstReaction);
            secondReact = itemView.findViewById(R.id.secondReaction);
            thirdReact = itemView.findViewById(R.id.thirdReaction);
            reactionLayout = itemView.findViewById(R.id.reactionLayout);

//            db.collection("ReactsCollection").document(DataStorage.getDocumentName()).collection("ReactedPosts")
////                    .document(getSnapshots().getSnapshot(getAdapterPosition()).getId())
////                    .get()
////                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
////                        @Override
////                        public void onSuccess(DocumentSnapshot documentSnapshot) {
////
////                        }
////                    })
////                    .addOnFailureListener(new OnFailureListener() {
////                        @Override
////                        public void onFailure(@NonNull Exception e) {
////                        }
////                    });

//            likeButtonLayout.setOnLongClickListener(new View.OnLongClickListener() {
//                @Override
//                public boolean onLongClick(View v) {
//                    final String[] strings = {
//                            "Like", "Love", "Haha", "Wow", "Sad", "Angry"
//                    };
//
//                    ReactionsConfig config = new ReactionsConfigBuilder(v.getContext())
//                            .withReactions(new int[]{
//                                    R.drawable.ic_fb_like,
//                                    R.drawable.ic_fb_love,
//                                    R.drawable.ic_fb_haha,
//                                    R.drawable.ic_fb_wow,
//                                    R.drawable.ic_fb_sad,
//                                    R.drawable.ic_fb_angry
//                            })
//                            .withReactionTexts(new Function1<Integer, CharSequence>() {
//                                @Override
//                                public CharSequence invoke(Integer position) {
//                                    return strings[position];
//                                }
//                            })
//                            .build();
//
//                    ReactionPopup popup = new ReactionPopup(v.getContext(), config, new Function1<Integer, Boolean>() {
//                        @Override
//                        public Boolean invoke(Integer position) {
//                            // -- if a reaction is selected for the first time
//                            if(likeTextView.getCurrentTextColor()==Color.BLACK){
//                                switch (position){
//                                    case -1:
////                                        if(likeTextView.getCurrentTextColor()!=Color.BLACK){
////                                            likeTextView.setText("Like");
////                                            likeTextView.setTextColor(Color.BLACK);
////                                            likeIcon.setImageResource(R.drawable.ic_likebutton);
////                                        }
//                                        break;
//                                    case 0:
//                                        likeTextView.setText("Like");
//                                        likeTextView.setTextColor(Color.parseColor("#006BBF"));
//                                        likeIcon.setImageResource(R.drawable.ic_like_selected);
//                                        onItemClickInterface.onReactionSelected(getSnapshots().get(getAdapterPosition()).getId(), "Like");
//                                        break;
//                                    case 1:
//                                        likeTextView.setText("Love");
//                                        likeTextView.setTextColor(Color.parseColor("#FFFF0000"));
//                                        likeIcon.setImageResource(R.drawable.ic_fb_love);
//                                        onItemClickInterface.onReactionSelected(getSnapshots().get(getAdapterPosition()).getId(), "Love");
//                                        break;
//                                    case 2:
//                                        likeTextView.setText("Haha");
//                                        likeTextView.setTextColor(Color.parseColor("#FFFFC107"));
//                                        likeIcon.setImageResource(R.drawable.ic_fb_haha);
//                                        onItemClickInterface.onReactionSelected(getSnapshots().get(getAdapterPosition()).getId(), "Haha");
//                                        break;
//                                    case 3:
//                                        likeTextView.setText("Wow");
//                                        likeTextView.setTextColor(Color.parseColor("#FFFFC107"));
//                                        likeIcon.setImageResource(R.drawable.ic_fb_wow);
//                                        onItemClickInterface.onReactionSelected(getSnapshots().get(getAdapterPosition()).getId(), "Wow");
//                                        break;
//                                    case 4:
//                                        likeTextView.setText("Sad");
//                                        likeTextView.setTextColor(Color.parseColor("#FFFFC107"));
//                                        likeIcon.setImageResource(R.drawable.ic_fb_sad);
//                                        onItemClickInterface.onReactionSelected(getSnapshots().get(getAdapterPosition()).getId(), "Sad");
//                                        break;
//                                    case 5:
//                                        likeTextView.setText("Angry");
//                                        likeTextView.setTextColor(Color.parseColor("#FFFF5722"));
//                                        likeIcon.setImageResource(R.drawable.ic_fb_angry);
//                                        onItemClickInterface.onReactionSelected(getSnapshots().get(getAdapterPosition()).getId(), "Angry");
//                                        break;
//                                }
//                            }
//                            // -- if reaction is reselected
//                            else{
//                                switch (position){
//                                    case -1:
//                                        likeTextView.setText("Like");
//                                        likeTextView.setTextColor(Color.BLACK);
//                                        likeIcon.setImageResource(R.drawable.ic_likebutton);
//                                        onItemClickInterface.onReactionUnselected(getSnapshots().get(getAdapterPosition()).getId());
//                                        // -- remove document of likes onUnselectedReaction
//                                        break;
//                                    case 0:
//                                        likeTextView.setText("Like");
//                                        likeTextView.setTextColor(Color.parseColor("#006BBF"));
//                                        likeIcon.setImageResource(R.drawable.ic_like_selected);
//                                        onItemClickInterface.onReactionReselected(getSnapshots().get(getAdapterPosition()).getId(), "Like");
//                                        break;
//                                    case 1:
//                                        likeTextView.setText("Love");
//                                        likeTextView.setTextColor(Color.parseColor("#FFFF0000"));
//                                        likeIcon.setImageResource(R.drawable.ic_fb_love);
//                                        onItemClickInterface.onReactionReselected(getSnapshots().get(getAdapterPosition()).getId(), "Love");
//                                        break;
//                                    case 2:
//                                        likeTextView.setText("Haha");
//                                        likeTextView.setTextColor(Color.parseColor("#FFFFC107"));
//                                        likeIcon.setImageResource(R.drawable.ic_fb_haha);
//                                        onItemClickInterface.onReactionReselected(getSnapshots().get(getAdapterPosition()).getId(), "Haha");
//                                        break;
//                                    case 3:
//                                        likeTextView.setText("Wow");
//                                        likeTextView.setTextColor(Color.parseColor("#FFFFC107"));
//                                        likeIcon.setImageResource(R.drawable.ic_fb_wow);
//                                        onItemClickInterface.onReactionReselected(getSnapshots().get(getAdapterPosition()).getId(), "Wow");
//                                        break;
//                                    case 4:
//                                        likeTextView.setText("Sad");
//                                        likeTextView.setTextColor(Color.parseColor("#FFFFC107"));
//                                        likeIcon.setImageResource(R.drawable.ic_fb_sad);
//                                        onItemClickInterface.onReactionReselected(getSnapshots().get(getAdapterPosition()).getId(), "Sad");
//                                        break;
//                                    case 5:
//                                        likeTextView.setText("Angry");
//                                        likeTextView.setTextColor(Color.parseColor("#FFFF5722"));
//                                        likeIcon.setImageResource(R.drawable.ic_fb_angry);
//                                        onItemClickInterface.onReactionReselected(getSnapshots().get(getAdapterPosition()).getId(), "Angry");
//                                        break;
//                                }
//                            }
//                            return true; // true is closing popup, false is requesting a new selection
//                        }
//                    });
//                    likeButtonLayout.setOnTouchListener(popup);
//                    return false;
//                }
//            });



            reactionLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickInterface.onReactionLayoutClicked(getSnapshots().get(getAdapterPosition()).getId());
                }
            });
        }
    }



    public void setListsData(List<Post> posts){
        this.posts = posts;
        Log.d("ewq",""+posts.size());
        notifyDataSetChanged();
    }
    public static Map<String, Integer> getThreeHighest(Map<String, Integer> map, PostViewHolder postViewHolder, int ctr){
        int maxValueInMap=(Collections.max(map.values()));  // This will return max value in the Hashmap
        if(map.size()>2 && maxValueInMap!=0){
            for (Map.Entry<String, Integer> entry : map.entrySet()) {  // Iterate through hashmap
                if (entry.getValue()==maxValueInMap) {
                    if(ctr == 1){
                        if(entry.getKey().equals("Like")){
                            postViewHolder.firstReact.setImageResource(R.drawable.ic_fb_like);
                        }else if(entry.getKey().equals("Love")){
                            postViewHolder.firstReact.setImageResource(R.drawable.ic_fb_love);
                        }
                        else if(entry.getKey().equals("Haha")){
                            postViewHolder.firstReact.setImageResource(R.drawable.ic_fb_haha);
                        }
                        else if(entry.getKey().equals("Wow")){
                            postViewHolder.firstReact.setImageResource(R.drawable.ic_fb_wow);
                        }
                        else if(entry.getKey().equals("Sad")){
                            postViewHolder.firstReact.setImageResource(R.drawable.ic_fb_sad);
                        }
                        else if(entry.getKey().equals("Angry")){
                            postViewHolder.firstReact.setImageResource(R.drawable.ic_fb_angry);
                        }
                        postViewHolder.firstReact.setVisibility(View.VISIBLE);
                    }else if(ctr == 2){
                        if(entry.getKey().equals("Like")){
                            postViewHolder.secondReact.setImageResource(R.drawable.ic_fb_like);
                        }else if(entry.getKey().equals("Love")){
                            postViewHolder.secondReact.setImageResource(R.drawable.ic_fb_love);
                        }
                        else if(entry.getKey().equals("Haha")){
                            postViewHolder.secondReact.setImageResource(R.drawable.ic_fb_haha);
                        }
                        else if(entry.getKey().equals("Wow")){
                            postViewHolder.secondReact.setImageResource(R.drawable.ic_fb_wow);
                        }
                        else if(entry.getKey().equals("Sad")){
                            postViewHolder.secondReact.setImageResource(R.drawable.ic_fb_sad);
                        }
                        else if(entry.getKey().equals("Angry")){
                            postViewHolder.secondReact.setImageResource(R.drawable.ic_fb_angry);
                        }
                        postViewHolder.secondReact.setVisibility(View.VISIBLE);
                    } else if (ctr == 3) {
                        if(entry.getKey().equals("Like")){
                            postViewHolder.thirdReact.setImageResource(R.drawable.ic_fb_like);
                        }else if(entry.getKey().equals("Love")){
                            postViewHolder.thirdReact.setImageResource(R.drawable.ic_fb_love);
                        }
                        else if(entry.getKey().equals("Haha")){
                            postViewHolder.thirdReact.setImageResource(R.drawable.ic_fb_haha);
                        }
                        else if(entry.getKey().equals("Wow")){
                            postViewHolder.thirdReact.setImageResource(R.drawable.ic_fb_wow);
                        }
                        else if(entry.getKey().equals("Sad")){
                            postViewHolder.thirdReact.setImageResource(R.drawable.ic_fb_sad);
                        }
                        else if(entry.getKey().equals("Angry")){
                            postViewHolder.thirdReact.setImageResource(R.drawable.ic_fb_angry);
                        }
                        postViewHolder.thirdReact.setVisibility(View.VISIBLE);
                    }
                    ctr = ctr + 1;
                    // -- set the first Reaction to this type
                    map.remove(entry.getKey());
                    return getThreeHighest(map, postViewHolder,ctr);
                }
            }
        }
        return map;
    }
}




//    @Override
//    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
//        Post post = posts.get(position);
//        holder.owner.setText(post.getUsername());
//        holder.body.setText(post.getText());
//        holder.postlikes.setText(String.valueOf(post.getNumberOfLikes()));
//
//        List<String> likers = new ArrayList<>();
//        for(Like like : post.getLikes()){
//            likers.add(like.getLiker());
//        }
//        if(likers.contains(DataStorage.getUser().getName())){
//            holder.likebutton.setTextColor(Color.BLUE);
//            holder.likebutton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_like2, 0, 0, 0);
//            holder.likebutton.setTag(R.drawable.ic_like2);
//        }
//        else{
//            holder.likebutton.setTag(R.drawable.ic_like);
//        }
//    }


//            likeButtonLayout.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if(likeTextView.getCurrentTextColor()== Color.parseColor("#FF444444")){
//                        likeTextView.setTextColor(Color.parseColor("#006BBF"));
//                        likeIcon.setImageResource(R.drawable.ic_like_selected);
//                    }
//                    else{
//                        likeTextView.setTextColor(Color.parseColor("#FF444444"));
//                        likeIcon.setImageResource(R.drawable.ic_likebutton);
//                    }
//                }
//            });




//            likebutton.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    onItemClickInterface.onLike(getAdapterPosition());
////                    Drawable[] drawables = likebutton.getCompoundDrawables();
////                    Drawable leftDrawable = drawables[0];
////                    Drawable likeDrawable = likebutton.getResources().getDrawable(R.drawable.ic_like);
////                    Drawable like2Drawable = likebutton.getResources().getDrawable(R.drawable.ic_like2);
//
//                    if(((Integer)likebutton.getTag()).intValue()==R.drawable.ic_like2){
//                        likebutton.setCompoundDrawableTintList(ColorStateList.valueOf(Color.GRAY));
//                        likebutton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_like, 0, 0, 0);
//                        likebutton.setTextColor(Color.DKGRAY);
//
//                    }
//                    else{
//                        likebutton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_like2, 0, 0, 0);
//                        likebutton.setCompoundDrawableTintList(ColorStateList.valueOf(Color.BLUE));
//                        likebutton.setTextColor(Color.BLUE);
//                    }
//                }
//            });