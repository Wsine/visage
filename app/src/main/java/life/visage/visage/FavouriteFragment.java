package life.visage.visage;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class FavouriteFragment extends Fragment {
    private static final int mColumnWidth = 171;

    private int spanCount = 5;

    public FavouriteFragment() {
        // required default constructor
    }

    public String getTitle() {
        return getResources().getString(R.string.title_fragment_favourite);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_favourite, container, false);
        Toolbar mToolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(mToolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(getTitle());

        RecyclerView mRecyclerView = (RecyclerView) root.findViewById(R.id.recycler_favourite);
        final GridAutofitLayoutManager mGridLayoutManager =
                new GridAutofitLayoutManager(root.getContext(), mColumnWidth);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mGridLayoutManager);
        mRecyclerView.addItemDecoration(new GridSpacingDecoration(
                getResources().getDimensionPixelSize(R.dimen.grid_spacing)));

        final PhotoRecyclerAdapter mAdapter = new PhotoRecyclerAdapter(mColumnWidth, Utils.getAllShownImagesPath(getActivity()));
        mRecyclerView.setAdapter(mAdapter);

        return root;
    }
}

