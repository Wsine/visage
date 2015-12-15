package life.visage.visage;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class PhotoDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);

        ViewPager mViewPager = (ViewPager) findViewById(R.id.photo_pager);
        ArrayList<Photo> photoList = getIntent().getParcelableArrayListExtra(Tag.PHOTO_LIST);
        mViewPager.setAdapter(new PhotoPagerAdapter(getSupportFragmentManager(), photoList));
        mViewPager.setCurrentItem(getIntent().getIntExtra(Tag.CURRENT_POSITION, 1));
    }

}
