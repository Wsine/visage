package life.visage.visage;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class TabLayoutPagerAdapter extends FragmentPagerAdapter {
    final static int NUM_PAGES = 3;
    Context mContext;

    public TabLayoutPagerAdapter(FragmentManager mFragmentManager, Context context) {
        super(mFragmentManager);
        mContext = context;
    }

    @Override
    public int getCount() {
        return NUM_PAGES;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = new TabAlbumsFragment();
        switch (position) {
            case 0: fragment = new TabAllPhotosFragment(); break;
            case 1: fragment = new TabAlbumsFragment(); break;
            case 2: fragment = new TabTagsFragment(); break;
        }
        return fragment;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        Resources resources = mContext.getResources();
        String title = resources.getString(R.string.tab_albums);
        switch (position) {
            case 0: title = resources.getString(R.string.tab_all_photos); break;
            case 1: title = resources.getString(R.string.tab_albums); break;
            case 2: title = resources.getString(R.string.tab_tags); break;
        }
        return title;
    }

    public static int getMiddlePosition() {
        return NUM_PAGES/2;
    }
}
