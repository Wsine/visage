package life.visage.visage;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;


public class MainActivity extends AppCompatActivity {
    private ActionBarDrawerToggle mDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initInstances();
    }

    private void initInstances() {
        final DrawerLayout mDrawerLayout        = (DrawerLayout) findViewById(R.id.drawerLayout);
        final FragmentManager mFragmentManager  = getSupportFragmentManager();
        NavigationView mNavigationView          = (NavigationView) findViewById(R.id.navigation);
        Toolbar mToolbar                        = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        mFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, new MainFragment()).commit();

        mNavigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        Fragment mFragment = null;
                        switch (menuItem.getItemId()) {
                            case R.id.drawer_explore: {
                                mFragment = new ExploreFragment();
                                break;
                            }
                            case R.id.drawer_favourite: {
                                mFragment = new FavouriteFragment();
                                break;
                            }
                            case R.id.drawer_people: {
                                break;
                            }
                            case R.id.drawer_notification: {
                                break;
                            }
                            case R.id.drawer_setting: {
                                break;
                            }
                            case R.id.drawer_feedback: {
                                break;
                            }
                            case R.id.drawer_about_help: {
                                break;
                            }
                        }

                        if (mFragment != null) {
                            mFragmentManager.beginTransaction()
                                    .replace(R.id.fragment_container, mFragment)
                                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                                    .addToBackStack(null)
                                    .commit();
                        }

                        mDrawerLayout.closeDrawer(Gravity.LEFT);
                        return false;
                    }
                }
        );

        // Set the drawer toggle as the DrawerListener
        mDrawerToggle = new ActionBarDrawerToggle(
                this,
                mDrawerLayout,
                R.string.openDrawerContentDescRes,
                R.string.closeDrawerContentDescRes);
        mDrawerToggle.syncState();
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        // Create global configuration and initialize ImageLoader with this config
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .showImageOnLoading(R.drawable.placeholder)
                .displayer(new FadeInBitmapDisplayer(500))
                .build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
                .defaultDisplayImageOptions(defaultOptions)
                .build();
        ImageLoader.getInstance().init(config);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchManager searchManager = (SearchManager) MainActivity.this.getSystemService(Context.SEARCH_SERVICE);

        SearchView searchView = null;
        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
        }
        if (searchView != null) {
            searchView.setQueryHint("search...");
            searchView.setSearchableInfo(searchManager.getSearchableInfo(MainActivity.this.getComponentName()));
        }

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle your other action bar items...

        return super.onOptionsItemSelected(item);
    }
}
