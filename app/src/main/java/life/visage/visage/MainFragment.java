package life.visage.visage;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MainFragment extends Fragment {
    public MainFragment() {
        // required default constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);
        ViewPager mViewPager = (ViewPager) root.findViewById(R.id.pager);
        TabLayout mTabLayout = (TabLayout) root.findViewById(R.id.tabLayout);
        Toolbar mToolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(mToolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.app_name);

        mViewPager.setAdapter(new PagerAdapter(getChildFragmentManager()));
        mTabLayout.setupWithViewPager(mViewPager);
        mViewPager.setCurrentItem(1);

        return root;
    }
}
