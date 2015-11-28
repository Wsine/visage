package life.visage.visage;

import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.SimpleCursorTreeAdapter;

public class TabTagsFragment extends Fragment {
    final static String name = "Tags";
    private static TabTagsFragment instance;

    private TabTagsFragment() {
        // Required empty public constructor
    }

    public static TabTagsFragment getInstance() {
        if (instance == null) {
            instance = new TabTagsFragment();
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
        View view = inflater.inflate(R.layout.fragment_tags, container, false);
        ListView list = (ListView) view.findViewById(R.id.album_list);

        String[] projection = {
                MediaStore.MediaColumns._ID,
                MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME,
                MediaStore.Images.ImageColumns.BUCKET_ID
        };
        String[] from = {
                MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME,
                MediaStore.Images.ImageColumns.BUCKET_ID
        };
        int[] to = {
                R.id.bucket_display_name,
                R.id.display_name
        };
        list.setAdapter(new SimpleCursorAdapter(
                getActivity(),
                R.layout.album_entry,
                getActivity().getContentResolver().query(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        projection,
                        null, null, null),
                from,
                to,
                SimpleCursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER));

        return view;
    }
}
