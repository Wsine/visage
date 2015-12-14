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

public class SearchResultsFragment extends Fragment
        implements RecyclerItemClickListener.OnItemClickListener {
    private ArrayList<String> imagePath = new ArrayList<>();
    private ArrayList<Photo> resultPhotoList = new ArrayList<>();
    private static final int mColumnWidth = 171;

    public SearchResultsFragment() {
        // required
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_result, container, false);
        RecyclerView mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_results);

        resultPhotoList = ImageStore.searchPhotos(getActivity(), getArguments().getString(Utils.SEARCH_QUERY));
        for (Photo photo : resultPhotoList) {
            imagePath.add(photo.getPath());
        }

        GridAutofitLayoutManager mGridLayoutManager =
                new GridAutofitLayoutManager(getActivity(), mColumnWidth);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mGridLayoutManager);
        mRecyclerView.addItemDecoration(new GridAutofitLayoutManager.GridSpacingDecoration(
                getResources().getDimensionPixelSize(R.dimen.grid_spacing)));
        PhotoRecyclerAdapter mAdapter = new PhotoRecyclerAdapter(mColumnWidth, resultPhotoList);

        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), this));

        return view;
    }

    @Override
    public void onItemClick(View childView, int position) {
        startActivity(new Intent(getActivity(), PhotoDetailActivity.class)
                .putStringArrayListExtra(Utils.PHOTO_PATH_LIST, imagePath)
                .putExtra(Utils.CURRENT_POSITION, position));
    }

    @Override
    public void onItemLongPress(View childView, int position) {

    }
}
