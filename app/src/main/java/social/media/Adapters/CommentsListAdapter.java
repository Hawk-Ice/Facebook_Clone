package social.media.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import de.hdodenhof.circleimageview.CircleImageView;
import social.media.Models.Comment;
import social.media.Models.Post;
import social.media.R;

public class CommentsListAdapter extends FirestoreRecyclerAdapter<Comment, CommentsListAdapter.CommentViewHolder> {

    private Context context;

    public CommentsListAdapter(@NonNull FirestoreRecyclerOptions<Comment> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull CommentsListAdapter.CommentViewHolder commentViewHolder, int i, @NonNull Comment comment) {
         if(comment.getAuthorProfilePicture().length()>0){
            Glide.with(context)
                    .load(comment.getAuthorProfilePicture())
                    .placeholder(R.drawable.ic_placeholder_avatar)
                    .into(commentViewHolder.commentUserImage);
        }
        commentViewHolder.commentAuthor.setText(comment.getCommenter());
        commentViewHolder.commentText.setText(comment.getCommentText());
    }

    @NonNull
    @Override
    public CommentsListAdapter.CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comments_item_layout,
                parent, false);
        return new CommentsListAdapter.CommentViewHolder(view);
    }

    public class CommentViewHolder extends RecyclerView.ViewHolder {
        CircleImageView commentUserImage;
        TextView commentAuthor;
        TextView commentText;

        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);

            commentUserImage = itemView.findViewById(R.id.commentUserImage);
            commentAuthor = itemView.findViewById(R.id.commentAuthor);
            commentText = itemView.findViewById(R.id.commentText);
        }
    }
    public void setAdapterContext(Context context){
        this.context = context;
    }
}
