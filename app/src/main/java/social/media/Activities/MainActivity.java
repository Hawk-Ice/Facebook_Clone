package social.media.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import social.media.DataStorage;
import social.media.Models.User;
import social.media.R;
import social.media.databinding.ActivityMainBinding;
import social.media.mvvm.ViewModel;

public class MainActivity extends AppCompatActivity {


    ActivityMainBinding binding;
    ViewModel viewModel;
    public static FirebaseFirestore db = FirebaseFirestore.getInstance();
    public static FirebaseDatabase firebase = FirebaseDatabase.getInstance();
    public static CollectionReference usersCollection = db.collection("UsersCollection");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


    }


    //On Click Submit
    public void login(View view) {
//        final String email = binding.email.getText().toString();
//        final String password = binding.passwordText.getText().toString();

        final String email = "renzlontac@gmail.com";
        final String password = "123";

        if (email.length() > 0 && password.length() > 0) {
            usersCollection.whereEqualTo("email", email).whereEqualTo("password",password).get()
                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            // -- just putting on the storage class
                            DataStorage.setDocumentName(email);
                            final User user = queryDocumentSnapshots.getDocuments().get(0).toObject(User.class);
                            Log.d("qwe","name: "+ user.getUsername());
                            DataStorage.setUser(user);
                            // -- going to the next activity || bad practice
                            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                            startActivity(intent);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                        }
                    });
        }
        else{
            Toast.makeText(this, "Enter a valid name and password", Toast.LENGTH_SHORT).show();
        }
    }

    // -- button createUser
    public void createUser(View view) {
        final User user = new User(binding.nameCreate.getText().toString(), binding.emailCreate.getText().toString(), binding.passwordCreate.getText().toString());
        firebase.getReference().child("Users").push().child("info").push().push().setValue(user);
//        if (binding.nameCreate.getText().toString().length() > 0 && binding.passwordCreate.getText().toString().length() > 0) {
//
//        }
//        else{
//            Toast.makeText(this, "Enter a valid name and password", Toast.LENGTH_SHORT).show();
//        }
    }
}
