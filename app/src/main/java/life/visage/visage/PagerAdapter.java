package life.visage.visage;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Brian on 2015/10/5.
 */
public class PagerAdapter extends FragmentPagerAdapter {
    final static int NUM_PAGES = 3;

    public PagerAdapter(FragmentManager mFragmentManager) {
        super(mFragmentManager);
    }

    @Override
    public int getCount() {
        return NUM_PAGES;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: return EventsFragment.getInstance();
            case 1: return AlbumsFragment.getInstance();
            case 2: return TagsFragment.getInstance();
        }

        return AlbumsFragment.getInstance();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0: return EventsFragment.name;
            case 1: return AlbumsFragment.name;
            case 2: return TagsFragment.name;
        }
        return "Albums";
    }
}
