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

public class TabAlbumsFragment extends Fragment implements RecyclerItemClickListener.OnItemClickListener {
    final static String name = "Albums";
    ArrayList<Album> mAlbums = new ArrayList<>();
    private static TabAlbumsFragment instance;

    private TabAlbumsFragment() {
        // do nothing
    }

    static public TabAlbumsFragment getInstance() {
        if (instance == null) {
            instance = new TabAlbumsFragment();
        }

        return instance;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAlbums = ImageStore.getAllAlbums(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        int mColumnWidth = getResources().getDimensionPixelSize(R.dimen.album_width);

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_albums, container, false);

        RecyclerView mRecyclerView = (RecyclerView) v.findViewById(R.id.recycler_albums);
        final GridAutofitLayoutManager mGridLayoutManager =
                new GridAutofitLayoutManager(getActivity(), mColumnWidth);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mGridLayoutManager);
        mRecyclerView.addItemDecoration(new GridSpacingDecoration(
                getResources().getDimensionPixelSize(R.dimen.grid_spacing)));
        mRecyclerView.setAdapter(new AlbumRecyclerAdapter(mAlbums));
        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this.getActivity(), this));

        return v;
    }

    @Override
    public void onItemClick(View childView, int position) {
        FragmentManager mFragmentManager = getActivity().getSupportFragmentManager();
        Fragment fragment = new CollectionFragment();
        Bundle bundle = new Bundle();
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
