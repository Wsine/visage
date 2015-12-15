package life.visage.visage;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import butterknife.BindDimen;
import butterknife.ButterKnife;

public class SearchResultsFragment extends Fragment
        implements RecyclerItemClickListener.OnItemClickListener {
    private ArrayList<Photo> resultPhotoList = new ArrayList<>();
    @BindDimen(R.dimen.thumbnail_width) int mColumnWidth;
    @BindDimen(R.dimen.grid_spacing) int grid_spacing;

    public SearchResultsFragment() {
        // required
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_result, container, false);
        RecyclerView mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_results);
        ButterKnife.bind(this, view);

        resultPhotoList = ImageStore.searchPhotos(
                getActivity(), getArguments().getString(Utils.SEARCH_QUERY));

        GridAutofitLayoutManager mGridLayoutManager =
                new GridAutofitLayoutManager(getActivity(), mColumnWidth);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mGridLayoutManager);
        mRecyclerView.addItemDecoration(new GridAutofitLayoutManager.GridSpacingDecoration(grid_spacing));
        PhotoRecyclerAdapter mAdapter = new PhotoRecyclerAdapter(mColumnWidth, resultPhotoList);

        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), this));

        return view;
    }

    @Override
    public void onItemClick(View childView, int position) {
        startActivity(new Intent(getActivity(), PhotoDetailActivity.class)
                .putParcelableArrayListExtra(Tag.PHOTO_LIST, resultPhotoList)
                .putExtra(Tag.CURRENT_POSITION, position));
    }

    @Override
    public void onItemLongPress(View childView, int position) {

    }
}
