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

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainFragment extends Fragment {
    @Bind(R.id.pager) ViewPager mViewPager;
    @Bind(R.id.tabLayout) TabLayout mTabLayout;

    public MainFragment() {
        // required default constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, view);

        // when restore from other fragment, resume the actionbar title back to R.string.app_name
        Toolbar mToolbar     = (Toolbar) getActivity().findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.app_name);

        mViewPager.setAdapter(new TabLayoutPagerAdapter(getChildFragmentManager(), getActivity()));
        mTabLayout.setupWithViewPager(mViewPager);
        mViewPager.setCurrentItem(TabLayoutPagerAdapter.getMiddlePosition());

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
