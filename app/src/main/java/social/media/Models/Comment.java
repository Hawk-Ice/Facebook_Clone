package social.media.Models;

import com.google.firebase.firestore.DocumentId;

public class Comment {

    @DocumentId
    private String id;

    private String commentText;
    private String commenter;
    private String authorProfilePicture = "";

    public Comment() {
    }

    public String getAuthorProfilePicture() {
        return authorProfilePicture;
    }

    public String getCommentText() {
        return commentText;
    }

    public String getCommenter() {
        return commenter;
    }


}
