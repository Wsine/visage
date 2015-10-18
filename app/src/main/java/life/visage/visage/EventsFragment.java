package life.visage.visage;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class EventsFragment extends Fragment {
    private static final int mColumnWidth = 171;


    public EventsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_events, container, false);

        RecyclerView mRecyclerView = (RecyclerView) v.findViewById(R.id.recycler_allphotos);
        final GridAutofitLayoutManager mGridLayoutManager =
                new GridAutofitLayoutManager(getActivity(), mColumnWidth);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mGridLayoutManager);
        mRecyclerView.addItemDecoration(new GridSpacingDecoration(
                getResources().getDimensionPixelSize(R.dimen.grid_spacing)));

        final PhotoRecyclerAdapter mAdapter = new PhotoRecyclerAdapter(mColumnWidth, mThumbIds);

        List<SectionedRecyclerViewAdapter.Section> sections =
                new ArrayList<SectionedRecyclerViewAdapter.Section>();
        //Sections
        sections.add(new SectionedRecyclerViewAdapter.Section(0,"October, 2015"));
        sections.add(new SectionedRecyclerViewAdapter.Section(5,"June, 2015"));
        sections.add(new SectionedRecyclerViewAdapter.Section(12,"December, 2014"));
        sections.add(new SectionedRecyclerViewAdapter.Section(14,"November, 2013"));
        sections.add(new SectionedRecyclerViewAdapter.Section(20, "July, 2013"));

        //Add your adapter to the sectionAdapter
        SectionedRecyclerViewAdapter.Section[] dummy =
                new SectionedRecyclerViewAdapter.Section[sections.size()];
        final SectionedRecyclerViewAdapter mSectionedAdapter = new
                SectionedRecyclerViewAdapter(
                    getActivity(), R.layout.section, R.id.section_text,mAdapter);

        mSectionedAdapter.setSections(sections.toArray(dummy));

        mGridLayoutManager.setSpanSizeLookup(new GridAutofitLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return mSectionedAdapter.isSectionHeaderPosition(position) ? mGridLayoutManager.getSpanCount() : 1;
            }
        });

        mRecyclerView.setAdapter(mSectionedAdapter);

        return v;
    }

    // references to our images
    private Integer[] mThumbIds = {
            R.drawable.sample_2, R.drawable.sample_3,
            R.drawable.sample_4, R.drawable.sample_5,
            R.drawable.sample_6, R.drawable.sample_7,
            R.drawable.sample_0, R.drawable.sample_1,
            R.drawable.sample_2, R.drawable.sample_3,
            R.drawable.sample_4, R.drawable.sample_5,
            R.drawable.sample_6, R.drawable.sample_7,
            R.drawable.sample_0, R.drawable.sample_1,
            R.drawable.sample_2, R.drawable.sample_3,
            R.drawable.sample_4, R.drawable.sample_5,
            R.drawable.sample_6, R.drawable.sample_7,
            R.drawable.sample_2, R.drawable.sample_3,
            R.drawable.sample_4, R.drawable.sample_5,
            R.drawable.sample_6, R.drawable.sample_7,
            R.drawable.sample_0, R.drawable.sample_1,
            R.drawable.sample_2, R.drawable.sample_3,
            R.drawable.sample_4, R.drawable.sample_5,
            R.drawable.sample_6, R.drawable.sample_7,
            R.drawable.sample_0, R.drawable.sample_1,
            R.drawable.sample_2, R.drawable.sample_3,
            R.drawable.sample_4, R.drawable.sample_5
    };
}
