package life.visage.visage;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Brian on 2015/10/5.
 */
public class TabLayoutPagerAdapter extends FragmentPagerAdapter {
    final static int NUM_PAGES = 3;

    public TabLayoutPagerAdapter(FragmentManager mFragmentManager) {
        super(mFragmentManager);
    }

    @Override
    public int getCount() {
        return NUM_PAGES;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: return TabEventsFragment.getInstance();
            case 1: return TabAlbumsFragment.getInstance();
            case 2: return TabTagsFragment.getInstance();
        }

        return TabAlbumsFragment.getInstance();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0: return TabEventsFragment.name;
            case 1: return TabAlbumsFragment.name;
            case 2: return TabTagsFragment.name;
        }
        return "Albums";
    }
}
