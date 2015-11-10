package life.visage.visage;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tags, container, false);
    }
}
