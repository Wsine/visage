package life.visage.visage;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class FavouriteFragment extends Fragment implements RecyclerItemClickListener.OnItemClickListener{
    private ArrayList<String> imagePath;
    private static final int mColumnWidth = 171;

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

        imagePath = Utils.getAllShownImagesPath(getActivity());
        final PhotoRecyclerAdapter mAdapter = new PhotoRecyclerAdapter(mColumnWidth, imagePath);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this.getActivity(), this));
        return root;
    }

    @Override
    public void onItemClick(View childView, int position) {
        startActivity(new Intent(getContext(), PhotoActivity.class)
                .putStringArrayListExtra("KEY", imagePath)
                .putExtra("CURRENT", position));
    }

    @Override
    public void onItemLongPress(View childView, int position) {

    }
}

