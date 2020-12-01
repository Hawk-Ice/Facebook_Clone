package social.media.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import de.hdodenhof.circleimageview.CircleImageView;
import social.media.Models.User;
import social.media.R;

public class OnlineStatusAdapter extends FirestoreRecyclerAdapter<User, OnlineStatusAdapter.OnlineStatusViewHolder> {

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public OnlineStatusAdapter(@NonNull FirestoreRecyclerOptions<User> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull OnlineStatusViewHolder onlineStatusViewHolder, int i, @NonNull User user) {

    }

    @NonNull
    @Override
    public OnlineStatusViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.online_status_item,
                parent, false);
        return new OnlineStatusAdapter.OnlineStatusViewHolder(view);
    }

    public class OnlineStatusViewHolder extends RecyclerView.ViewHolder {
        CircleImageView userStatusImage;

        public OnlineStatusViewHolder(@NonNull View itemView) {
            super(itemView);

            userStatusImage = itemView.findViewById(R.id.userStatusImage);
        }
    }
}
