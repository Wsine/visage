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
import butterknife.BindString;
import butterknife.ButterKnife;

public class TabTagsFragment extends Fragment implements RecyclerItemClickListener.OnItemClickListener {
    @BindDimen(R.dimen.album_width) int albumColumnWidth;
    ArrayList<Album> mAlbums = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAlbums = ImageStore.getAllCategories(getContext());
        // TODO: get album list by categories
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_albums, container, false);
        ButterKnife.bind(this, view);

        RecyclerView mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_albums);
        final GridAutofitLayoutManager mGridLayoutManager =
                new GridAutofitLayoutManager(getActivity(), albumColumnWidth);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mGridLayoutManager);
        mRecyclerView.addItemDecoration(new GridAutofitLayoutManager.GridSpacingDecoration(
                getResources().getDimensionPixelSize(R.dimen.grid_spacing)));
        mRecyclerView.setAdapter(new AlbumsRecyclerAdapter(mAlbums));
        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this.getActivity(), this));

        return view;
    }

    @Override
    public void onItemClick(View childView, int position) {
        FragmentManager mFragmentManager = getActivity().getSupportFragmentManager();
        Fragment fragment = new CollectionFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Utils.COLLECTION_TYPE, Utils.TYPE_TAGS);
        bundle.putString(Utils.COLLECTION_NAME, mAlbums.get(position).getName());
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
