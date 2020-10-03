//package social.media;
//
//import android.content.Context;
//import android.content.res.ColorStateList;
//import android.graphics.Color;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.core.content.ContextCompat;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.google.android.gms.tasks.OnSuccessListener;
//import com.google.firebase.firestore.CollectionReference;
//import com.google.firebase.firestore.DocumentSnapshot;
//import com.google.firebase.firestore.FirebaseFirestore;
//import com.google.firebase.firestore.QuerySnapshot;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class PostsAdapter extends RecyclerView.Adapter {
//    public static FirebaseFirestore db = FirebaseFirestore.getInstance();
//    CollectionReference collectionReference = db.collection("SocialMediaUsers");
//    public static CollectionReference usersCollection = db.collection("UsersCollection");
//    public static CollectionReference feedsCollection = db.collection("FeedsCollection");
//    private List<Post> posts;
//    public static OnItemClickInterface onItemClickInterface;
//    private static Context context;
//
//    public PostsAdapter(List<Post> posts, Context context){
//        this.posts = posts;
//        this.context = context;
//    }
//
//    public interface OnItemClickInterface{
//        void onLike(int position);
//    }
//
//    public void setOnItemClickInterface(OnItemClickInterface onItemClickInterface) {
//        this.onItemClickInterface = onItemClickInterface;
//    }
//    private static final int VIEW_TYPE_EMPTY_LIST_PLACEHOLDER = 0;
//    private static final int VIEW_TYPE_OBJECT_VIEW = 1;
//
//    @Override
//    public int getItemViewType(int position) {
//        if (posts.isEmpty()) {
//            return VIEW_TYPE_EMPTY_LIST_PLACEHOLDER;
//        } else {
//            return VIEW_TYPE_OBJECT_VIEW;
//        }
//    }
//
//    @NonNull
//    @Override
//    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
//        View view;
//        if(viewType==0){
//            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.empty_layout,
//                    parent, false);
//            return new EmptyViewHolder(view);
//        }
//        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_item_layout,
//                parent, false);
//        return new PostViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
//        if (posts.isEmpty()) {
//            EmptyViewHolder viewHolder = (EmptyViewHolder) holder;
//        } else {
//            PostViewHolder viewHolder = (PostViewHolder) holder;
//            Post post = posts.get(position);
//            viewHolder.owner.setText(post.getUsername());
//            viewHolder.body.setText(post.getText());
//
//            getLikes(post, viewHolder, position);
//
//        }
//    }
//
//    private void getLikes(final Post post, final PostViewHolder viewHolder, int position) {
//        feedsCollection.document(DataStorage.getDocumentName()).collection("Posts")
//                .document(post.getId()).collection("Likes").get()
//                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//                    @Override
//                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
//
//                        viewHolder.postlikes.setText(String.valueOf(queryDocumentSnapshots.size()));
//                        List<String> likers = new ArrayList<>();
//                        for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots){
//                            likers.add((String) documentSnapshot.get("liker"));
//                        }
////                        for(Like like : post.getLikes()){
////                            likers.add(like.getLiker());
////                        }
//                        if(likers.contains(DataStorage.getUser().getEmail())){
//                            viewHolder.likebutton.setTextColor(ContextCompat.getColor(context, R.color.holoblue));
//                            viewHolder.likebutton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_like2, 0, 0, 0);
//                            viewHolder.likebutton.setTag(R.drawable.ic_like2);
//                        }
//                        else{
//                            viewHolder.likebutton.setTag(R.drawable.ic_like);
//                        }
//
//                    }
//                });
//    }
//
////    @Override
////    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
////        Post post = posts.get(position);
////        holder.owner.setText(post.getUsername());
////        holder.body.setText(post.getText());
////        holder.postlikes.setText(String.valueOf(post.getNumberOfLikes()));
////
////        List<String> likers = new ArrayList<>();
////        for(Like like : post.getLikes()){
////            likers.add(like.getLiker());
////        }
////        if(likers.contains(DataStorage.getUser().getName())){
////            holder.likebutton.setTextColor(Color.BLUE);
////            holder.likebutton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_like2, 0, 0, 0);
////            holder.likebutton.setTag(R.drawable.ic_like2);
////        }
////        else{
////            holder.likebutton.setTag(R.drawable.ic_like);
////        }
////    }
//
//    @Override
//    public int getItemCount() {
//        return posts.size();
//    }
//    public static class EmptyViewHolder extends RecyclerView.ViewHolder{
//
//        public EmptyViewHolder(@NonNull View itemView) {
//            super(itemView);
//        }
//    }
//
//    public static class PostViewHolder extends RecyclerView.ViewHolder {
//        TextView owner;
//        TextView body;
//        TextView postlikes;
//        Button likebutton;
//
//        public PostViewHolder(@NonNull final View itemView) {
//            super(itemView);
//
//            owner = itemView.findViewById(R.id.textViewOwner);
//            body = itemView.findViewById(R.id.textViewBody);
//            likebutton = itemView.findViewById(R.id.likebutton);
//            postlikes = itemView.findViewById(R.id.postslikes);
//
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
//        }
//    }
//
//    public void setListsData(List<Post> posts){
//        this.posts = posts;
//        Log.d("ewq",""+posts.size());
//        notifyDataSetChanged();
//    }
//
//}
