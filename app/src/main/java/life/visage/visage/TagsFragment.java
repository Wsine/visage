package life.visage.visage;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class TagsFragment extends Fragment {
    final static String name = "Tags";
    private static TagsFragment instance;

    private TagsFragment() {
        // Required empty public constructor
    }

    public static TagsFragment getInstance() {
        if (instance == null) {
            instance = new TagsFragment();
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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tags, container, false);
    }
}
