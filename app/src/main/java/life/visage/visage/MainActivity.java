package life.visage.visage;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    private ActionBarDrawerToggle mDrawerToggle;
    private FragmentManager mFragmentManager;

    @Bind(R.id.drawerLayout) DrawerLayout mDrawerLayout;
    @Bind(R.id.navigation) NavigationView mNavigationView;
    @Bind(R.id.toolbar) Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);

        mFragmentManager = getSupportFragmentManager();
        mFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, new MainFragment()).commit();

        // TODO: for navigation drawer
        mNavigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.drawer_favourite: {
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
                        mDrawerLayout.closeDrawer(GravityCompat.START);
                        return false;
                    }
                });

        // Set the drawer toggle as the DrawerListener
        mDrawerToggle = new ActionBarDrawerToggle(
                this,
                mDrawerLayout,
                R.string.openDrawerContentDescRes,
                R.string.closeDrawerContentDescRes);
        mDrawerToggle.syncState();
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        handleIntent(getIntent());
        startBaiduPCSService();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            SearchResultsFragment fragment = new SearchResultsFragment();
            Bundle bundle = new Bundle();
            bundle.putString(Utils.SEARCH_QUERY, query);
            fragment.setArguments(bundle);
            mFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .addToBackStack(query)
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

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

    private void startBaiduPCSService() {
        startService(new Intent(MainActivity.this, BaiduPCSService.class));
        Log.i(Logger.SERVICE, "BaiduPCSService started!");
    }
}
