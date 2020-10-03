package social.media;

import com.google.firebase.firestore.QuerySnapshot;

import social.media.Models.User;

public class DataStorage {

    private static String documentName;
    private static User user;
    private static QuerySnapshot queryDocumentSnapshots;

    public static QuerySnapshot getQueryDocumentSnapshots() {
        return queryDocumentSnapshots;
    }

    public static void setQueryDocumentSnapshots(QuerySnapshot queryDocumentSnapshots) {
        DataStorage.queryDocumentSnapshots = queryDocumentSnapshots;
    }

    public static String getDocumentName() {
        return documentName;
    }

    public static void setDocumentName(String documentName) {
        DataStorage.documentName = documentName;
    }

    public static void setUser(User user){
        DataStorage.user = user;
    }
    public static void setIsStillLoading(boolean bool){
    }

    public static User getUser(){
        return user;
    }


}
