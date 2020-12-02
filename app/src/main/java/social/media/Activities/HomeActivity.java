package social.media.Activities;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import social.media.DataStorage;
import social.media.Models.Post;
import social.media.R;
import social.media.Adapters.ViewPagerAdapter;
import social.media.databinding.HomeActivityBinding;

public class HomeActivity extends AppCompatActivity {
    //Binding
    public static HomeActivityBinding binding;
    public static FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);
        binding = HomeActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.viewpager2.setAdapter(new ViewPagerAdapter(this));
        binding.viewpager2.setOffscreenPageLimit(5);

        binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch(tab.getPosition()){
                    case 0: {
                        binding.appBarLayout.setExpanded(true,true);
                        tab.setIcon(R.drawable.ic_home_selected);
                        break;
                    }
                    case 1: {
                        binding.appBarLayout.setExpanded(false,true);
                        tab.setIcon(R.drawable.ic_video_selected);
                        break;
                    }
                    case 2: {
                        binding.appBarLayout.setExpanded(false,true);
                        tab.setIcon(R.drawable.ic_heart_selected);
                        break;
                    }
                    case 3: {
                        binding.appBarLayout.setExpanded(false,true);
                        tab.setIcon(R.drawable.ic_games_selected);
                        break;
                    }
                    case 4: {
                        binding.appBarLayout.setExpanded(false,true);
                        tab.setIcon(R.drawable.ic_notifications_selected);
                        break;
                    }
                    case 5: {
                        binding.appBarLayout.setExpanded(false,true);
                        int tabIconColor = ContextCompat.getColor(HomeActivity.this, R.color.tabSelectedIconColor);
                        tab.getIcon().setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);
                        break;
                    }
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

                switch(tab.getPosition()){
                    case 0: {
                        tab.setIcon(R.drawable.ic_home);
                        int tabIconColor = ContextCompat.getColor(HomeActivity.this, R.color.tabUnselectedIconColor);
                        tab.getIcon().setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);
                        break;
                    }
                    case 1: {
                        tab.setIcon(R.drawable.ic_video);
                        int tabIconColor = ContextCompat.getColor(HomeActivity.this, R.color.tabUnselectedIconColor);
                        tab.getIcon().setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);
                        break;
                    }
                    case 2: {
                        tab.setIcon(R.drawable.ic_heart);
                        int tabIconColor = ContextCompat.getColor(HomeActivity.this, R.color.tabUnselectedIconColor);
                        tab.getIcon().setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);
                        break;
                    }
                    case 3: {
                        tab.setIcon(R.drawable.ic_games);
                        int tabIconColor = ContextCompat.getColor(HomeActivity.this, R.color.tabUnselectedIconColor);
                        tab.getIcon().setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);
                        break;
                    }
                    case 4: {
                        tab.setIcon(R.drawable.ic_notifications);
                        int tabIconColor = ContextCompat.getColor(HomeActivity.this, R.color.tabUnselectedIconColor);
                        tab.getIcon().setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);
                        break;
                    }
                    case 5: {
                        tab.setIcon(R.drawable.ic_menu);
                        int tabIconColor = ContextCompat.getColor(HomeActivity.this, R.color.tabUnselectedIconColor);
                        tab.getIcon().setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);
                        break;
                    }
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(
                binding.tabLayout, binding.viewpager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {

                switch(position){
                    case 0: {
                        tab.setIcon(R.drawable.ic_home);
                        break;
                    }
                    case 1: {
                        tab.setIcon(R.drawable.ic_video);
                        break;
                    }
                    case 2: {
                        tab.setIcon(R.drawable.ic_heart);
                        break;
                    }
                    case 3: {
                        tab.setIcon(R.drawable.ic_games);
                        break;
                    }
                    case 4: {
                        tab.setIcon(R.drawable.ic_notifications);
                        break;
                    }
                    case 5: {
                        tab.setIcon(R.drawable.ic_menu);
                        break;
                    }
                }
            }
         });
        tabLayoutMediator.attach();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void addPost(View view){
        Map<String, Integer> numberOfReacts = new HashMap<>();
        numberOfReacts.put("Like", 0);
        numberOfReacts.put("Love", 0);
        numberOfReacts.put("Haha", 0);
        numberOfReacts.put("Wow", 0);
        numberOfReacts.put("Sad", 0);
        numberOfReacts.put("Angry", 0);

        String text = "Lorem ipsum, or Lorem ipsum, or lipsum as it is sometimes known, is dummy text used in laying out print, graphic or web designs. The passage is attributed to an unknown typesetter in the 15th century who is thought to have scrambled parts of Cicero's De Finibus Bonorum et Malorum for use in a type specimen book.as it is sometimes known, is dummy text used in laying out print, graphic or web designs. The passage is attributed to an unknown typesetter in the 15th century who is thought to have scrambled parts of Cicero's De Finibus Bonorum et Malorum for use in a type specimen book.  ";
        String username = DataStorage.getUser().getUsername();
        int numberOfReactsCounter = 0;
        int numberOfComments = 0;
        String authorUID = DataStorage.getDocumentName();

        Post post = new Post(numberOfComments, numberOfReactsCounter, numberOfReacts, username, text, authorUID);
        db.collection("PostsCollection").add(post);

    }
}
//        // --ViewModel declaration
//        viewModel = new ViewModelProvider(this,
//                new ViewModelProvider.AndroidViewModelFactory(this.getApplication()))
//                .get(ViewModel.class);

//        viewModel.getPostsMutableLiveData().observe(this, new Observer<List<Post>>() {
//            @Override
//            public void onChanged(List<Post> posts) {
//                setUpRecyclerView();
//            }
//        });

//
//        // --ViewModel observe document changes // USER INFO
//        viewModel.getLiveData().observe(this, new Observer<DocumentSnapshot>() {
//            @Override
//            public void onChanged(DocumentSnapshot documentSnapshot) {
////                refreshPostsLists(documentSnapshot);
//            }
//        });
//
//        // --ViewModel observe user posts changes
//        viewModel.getLivePostsData().observe(this, new Observer<List<Post>>() {
//            @Override
//            public void onChanged(List<Post> posts) {
//                postsAdapter.setListsData(posts);
//            }
//        });
//
//        // -- setting on likeListener on the posts





////    private static class LoginAsyncTask extends AsyncTask<QuerySnapshot, Integer, List<Post>> {
////        private WeakReference<HomeActivity> activityWeakReference;
////        LoginAsyncTask(HomeActivity activity) {
////            activityWeakReference = new WeakReference<HomeActivity>(activity);
////        }
////        @Override
////        protected void onPreExecute() {
////            super.onPreExecute();
////            HomeActivity activity = activityWeakReference.get();
////            if (activity == null || activity.isFinishing()) {
////                return;
////            }
////            activity.binding.progressBar.setVisibility(View.VISIBLE);
////        }
////        @Override
////        protected List<Post> doInBackground(QuerySnapshot... querySnapshots) {
////
////            final String email = DataStorage.getDocumentName();
//////            while(user.isPostsAreStillNotRetrieved()){
////            // -- get all the posts of the user
////            feedsCollection.document(email).collection("Posts").get()
////                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
////                        @Override
////                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
////
////                            for(final DocumentSnapshot documentSnapshot1 : queryDocumentSnapshots){
////
////                                    // -- get Likes of each post
////                                    db.collection("FeedsCollection").document(email).collection("Posts")
////                                            .document(documentSnapshot1.getId()).collection("Likes").get()
////
////                                            .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
////                                                @Override
////                                                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
////                                                    Post post = documentSnapshot1.toObject(Post.class);
////
////                                                    List<Like> likeList = new ArrayList<>();
////                                                    for(DocumentSnapshot likeSnapshot : queryDocumentSnapshots){
////                                                        Like like = likeSnapshot.toObject(Like.class);
////                                                        Log.d("qwe","like: "+likeSnapshot.getId());
////                                                        likeList.add(like);
////                                                    }
////                                                    post.setLikes(likeList);
////                                                    Log.d("post","postLikes of "+post.getId()+" is "+post.getLikes().size());
////                                                    DataStorage.getUser().addPost(post);
////
////                                                }
////                                            });
////                                    // -- get comments
////
//////                                postList.add(post);
//////                                DataStorage.getUser().addPost(post);
////
////                            }
////                        }
////                    });
////
////            return DataStorage.getUser().getPosts();
////        }
////        @Override
////        protected void onProgressUpdate(Integer... values) {
////            super.onProgressUpdate(values);
////            HomeActivity activity = activityWeakReference.get();
////            if (activity == null || activity.isFinishing()) {
////                return;
////            }
////        }
////        @Override
////        protected void onPostExecute(List<Post> postList) {
////            super.onPostExecute(postList);
////            HomeActivity activity = activityWeakReference.get();
////            if (activity == null || activity.isFinishing()) {
////                return;
////            }
//
////            Toast.makeText(activity, s, Toast.LENGTH_SHORT).show();
////            activity.progressBar.setProgress(0);
//            activity.binding.progressBar.setVisibility(View.INVISIBLE);
//
//            Log.d("post","sizeqwe: "+DataStorage.getUser().getPosts().size());
////            postsAdapter.setListsData(postList);
//
//            // -- going to the next activity || bad practice
////            Intent intent = new Intent(activity, HomeActivity.class);
////            activity.startActivity(intent);
//
//        }
//    }






