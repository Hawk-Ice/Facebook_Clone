package social.media.Adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import social.media.Fragments.GamingFragment;
import social.media.Fragments.HomeFragment;
import social.media.Fragments.MatchesFragment;
import social.media.Fragments.MenuFragment;
import social.media.Fragments.NotificationsFragment;
import social.media.Fragments.WatchVideosFragment;

public class ViewPagerAdapter extends FragmentStateAdapter {
    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        switch(position){
            case 0:
                return new HomeFragment();
            case 1:
                return new WatchVideosFragment();
            case 2:
                return new MatchesFragment();
            case 3:
                return new GamingFragment();
            case 4:
                return new NotificationsFragment();
            default:
                return new MenuFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 6;
    }
}
