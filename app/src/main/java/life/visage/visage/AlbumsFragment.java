package life.visage.visage;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class AlbumsFragment extends Fragment {
    public AlbumsFragment() {
        // do nothing
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        int mColumnWidth = (int) getResources().getDimensionPixelSize(R.dimen.album_width);

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

        return v;
    }
}
