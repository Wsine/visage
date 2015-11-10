package life.visage.visage;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by Brian on 11/10/2015.
 */
public class PhotoPagerAdapter extends FragmentPagerAdapter{
    private ArrayList<String> imagePath;

    public PhotoPagerAdapter(FragmentManager mFragmentManager, ArrayList<String> pathName) {
        super(mFragmentManager);
        imagePath = pathName;
    }

    @Override
    public Fragment getItem(int position) {
        return SinglePhotoFragment.newInstance(imagePath.get(position));
    }

    @Override
    public int getCount() {
        return imagePath.size();
    }
}