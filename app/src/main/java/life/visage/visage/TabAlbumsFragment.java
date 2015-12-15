package life.visage.visage;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.BindDimen;
import butterknife.ButterKnife;

public class TabAlbumsFragment extends Fragment
        implements RecyclerItemClickListener.OnItemClickListener {
    @BindDimen(R.dimen.album_width) int albumColumnWidth;
    @BindDimen(R.dimen.grid_spacing) int grid_spacing;
    @Bind(R.id.recycler_collections) RecyclerView mRecyclerView;

    ArrayList<Collection> albumList = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        albumList = ImageStore.getAllAlbums(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_albums, container, false);
        ButterKnife.bind(this, view);

        GridAutofitLayoutManager mGridLayoutManager =
                new GridAutofitLayoutManager(getActivity(), albumColumnWidth);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mGridLayoutManager);
        mRecyclerView.addItemDecoration(new GridAutofitLayoutManager.GridSpacingDecoration(grid_spacing));
        mRecyclerView.setAdapter(new CollectionsRecyclerAdapter(albumList));
        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this.getActivity(), this));

        return view;
    }

    @Override
    public void onItemClick(View childView, int position) {
        FragmentManager mFragmentManager = getActivity().getSupportFragmentManager();
        Fragment fragment = new CollectionFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(Tag.COLLECTION, albumList.get(position));
        fragment.setArguments(bundle);
        mFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onItemLongPress(View childView, int position) {

    }
}
