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
            case 0: return new EventsFragment();
            case 1: return new AlbumsFragment();
            case 2: return new PeopleFragment();
        }

        return new AlbumsFragment();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0: return "Events";
            case 1: return "Albums";
            case 2: return "People";
        }
        return "AlbumsFragment";
    }
}
