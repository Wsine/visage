package life.visage.visage;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

public class PhotoDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);

        ViewPager mViewPager = (ViewPager) findViewById(R.id.photo_pager);
        mViewPager.setAdapter(new PhotoPagerAdapter(
                getSupportFragmentManager(), getIntent().getStringArrayListExtra(Utils.PHOTO_PATH_LIST)));
        mViewPager.setCurrentItem(getIntent().getIntExtra(Utils.CURRENT_POSITION, 1));
    }

}
