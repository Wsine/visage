package life.visage.visage;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class TabEventsFragment extends Fragment {
    static final String name = "Events";
    private static TabEventsFragment instance;
    private ArrayList<Photo> photoList = new ArrayList<>();
    private static final int mColumnWidth = 171;


    private TabEventsFragment() {
        // Required empty public constructor
    }

    public static TabEventsFragment getInstance() {
        if (instance == null) {
            instance = new TabEventsFragment();
        }

        return instance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_events, container, false);
        RecyclerView mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_allphotos);
        final GridAutofitLayoutManager mGridLayoutManager =
                new GridAutofitLayoutManager(getActivity(), mColumnWidth);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mGridLayoutManager);
        mRecyclerView.addItemDecoration(new GridSpacingDecoration(
                getResources().getDimensionPixelSize(R.dimen.grid_spacing)));

        photoList = ImageStore.getAllPhotos(getContext());

        Hashtable<String, Integer> titleList = new Hashtable<>();
        Locale locale = getContext().getResources().getConfiguration().locale;
        SimpleDateFormat formatter = new SimpleDateFormat("MMMMM, yyyy", locale);
        for (int i = 0; i < photoList.size(); i++) {
            String date = formatter.format(photoList.get(i).getDate());
            if (titleList.get(date) == null || titleList.get(date) > i) {
                titleList.put(date, i);
            }
        }

        // add section titles
        List<SectionedRecyclerViewAdapter.Section> sections = new ArrayList<>();
        Set<String> dateSet = titleList.keySet();
        for (String date : dateSet) {
            sections.add(new SectionedRecyclerViewAdapter.Section(titleList.get(date), date));
        }


        PhotoRecyclerAdapter mAdapter = new PhotoRecyclerAdapter(mColumnWidth, photoList);
        //Add your adapter to the sectionAdapter
        SectionedRecyclerViewAdapter.Section[] dummy =
                new SectionedRecyclerViewAdapter.Section[sections.size()];
        final SectionedRecyclerViewAdapter mSectionedAdapter = new SectionedRecyclerViewAdapter(
                getActivity(), R.layout.section, R.id.section_text, mAdapter);
        mSectionedAdapter.setSections(sections.toArray(dummy));


        mGridLayoutManager.setSpanSizeLookup(new GridAutofitLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return mSectionedAdapter.isSectionHeaderPosition(position) ? mGridLayoutManager.getSpanCount() : 1;
            }
        });

        mRecyclerView.setAdapter(mSectionedAdapter);

        return view;
    }
}