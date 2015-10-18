package life.visage.visage;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class ExploreFragment extends Fragment {
    private int spanCount = 5;

    public ExploreFragment() {
        // required default constructor
    }

    public String getTitle() {
        return getResources().getString(R.string.title_fragment_explore);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_explore, container, false);
        Toolbar mToolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(mToolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(getTitle());

        RecyclerView mRecyclerView = (RecyclerView) root.findViewById(R.id.recycler_explore_tags);
        StaggeredGridLayoutManager mLayoutManager =
                new StaggeredGridLayoutManager(spanCount, StaggeredGridLayoutManager.HORIZONTAL);

        //mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);


        ArrayList<String> tags = new ArrayList<>();
        tags.add("dog");
        tags.add("beach");
        tags.add("outdoor");
        tags.add("building");
        tags.add("sky");
        tags.add("food");
        tags.add("screenshot");
        tags.add("flight");
        tags.add("cup");
        tags.add("apple");
        tags.add("lamp");
        tags.add("sport");
        tags.add("mouse");
        tags.add("sport");
        tags.add("keyboard");
        tags.add("tea");
        tags.add("tree");
        tags.add("flower");
        tags.add("people");
        tags.add("family");
        tags.add("book");
        tags.add("desk");
        tags.add("chiar");
        tags.add("umbrella");
        tags.add("sport");
        tags.add("backpack");
        tags.add("computer");
        tags.add("flight");
        tags.add("pancake");
        tags.add("door");
        tags.add("mouse");
        tags.add("fruit");
        tags.add("lamp");

        mRecyclerView.setAdapter(new TagRecyclerAdapter(tags));

        int mColumnWidth = 171;
        RecyclerView mPhotosRecyclerView =
                (RecyclerView) root.findViewById(R.id.recycler_explore_photos);
        final GridAutofitLayoutManager mGridLayoutManager =
                new GridAutofitLayoutManager(getActivity(), mColumnWidth);

        mPhotosRecyclerView.setHasFixedSize(true);
        mPhotosRecyclerView.setLayoutManager(mGridLayoutManager);
        mPhotosRecyclerView.addItemDecoration(new GridSpacingDecoration(
                getResources().getDimensionPixelSize(R.dimen.grid_spacing)));

        final PhotoRecyclerAdapter mAdapter = new PhotoRecyclerAdapter(mColumnWidth, mThumbIds);
        mPhotosRecyclerView.setAdapter(mAdapter);

        return root;
    }

    // references to our images
    private Integer[] mThumbIds = {
            R.drawable.sample_2, R.drawable.sample_3,
            R.drawable.sample_4, R.drawable.sample_5,
            R.drawable.sample_6, R.drawable.sample_7,
            R.drawable.sample_0, R.drawable.sample_1,
            R.drawable.sample_2, R.drawable.sample_3,
            R.drawable.sample_4, R.drawable.sample_5,
            R.drawable.sample_6, R.drawable.sample_7,
            R.drawable.sample_0, R.drawable.sample_1,
            R.drawable.sample_2, R.drawable.sample_3,
            R.drawable.sample_4, R.drawable.sample_5,
            R.drawable.sample_6, R.drawable.sample_7,
            R.drawable.sample_2, R.drawable.sample_3,
            R.drawable.sample_4, R.drawable.sample_5,
            R.drawable.sample_6, R.drawable.sample_7,
            R.drawable.sample_0, R.drawable.sample_1,
            R.drawable.sample_2, R.drawable.sample_3,
            R.drawable.sample_4, R.drawable.sample_5,
            R.drawable.sample_6, R.drawable.sample_7,
            R.drawable.sample_0, R.drawable.sample_1,
            R.drawable.sample_2, R.drawable.sample_3,
            R.drawable.sample_4, R.drawable.sample_5
    };

}

