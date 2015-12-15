package life.visage.visage;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by Brian on 11/10/2015.
 */
public class PhotoPagerAdapter extends FragmentPagerAdapter{
    private ArrayList<Photo> photoList;

    public PhotoPagerAdapter(FragmentManager mFragmentManager, ArrayList<Photo> photoList) {
        super(mFragmentManager);
        this.photoList = photoList;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = new SinglePhotoFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Tag.PHOTO_PATH, photoList.get(position).getPath());
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getCount() {
        return photoList.size();
    }
}
