package life.visage.visage;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

public class AlbumsFragment extends Fragment implements RecyclerItemClickListener.OnItemClickListener {
    final static String name = "Albums";
    private static AlbumsFragment instance;

    private AlbumsFragment() {
        // do nothing
    }

    static public AlbumsFragment getInstance() {
        if (instance == null) {
            instance = new AlbumsFragment();
        }

        return instance;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

        ArrayList<Album> mAlbums = new ArrayList<>();
        mAlbums.add(new Album(R.drawable.family, "Family"));
        mAlbums.add(new Album(R.drawable.cat, "Cat"));
        mAlbums.add(new Album(R.drawable.dog, "Dog"));
        mAlbums.add(new Album(R.drawable.travelling, "Travelling"));
        mAlbums.add(new Album(R.drawable.landscape, "Landscape"));
        mAlbums.add(new Album(R.drawable.tea, "Tea"));
        mAlbums.add(new Album(R.drawable.sport, "Sport"));
        mAlbums.add(new Album(R.drawable.food, "Food"));

        mRecyclerView.setAdapter(new AlbumRecyclerAdapter(mAlbums));
        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this.getActivity(), this));

        return v;
    }

    @Override
    public void onItemClick(View childView, int position) {
        Toast.makeText(getContext(), "Clicked!", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onItemLongPress(View childView, int position) {

    }
}
