package life.visage.visage;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class CollectionFragment extends Fragment implements RecyclerItemClickListener.OnItemClickListener {
    List<SectionedRecyclerViewAdapter.Section> mSectionTitles = new ArrayList<>();
    private ArrayList<String> pathList = new ArrayList<>();
    private static final int mColumnWidth = 171;
    private String COLLECTION_NAME;
    private String COLLECTIION_TYPE = Utils.TYPE_TAGS;

    public CollectionFragment() {
        // required default constructor
    }

    public String getTitle() {
        COLLECTION_NAME = getArguments().getString(Utils.COLLECTION_NAME);
        return COLLECTION_NAME;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_collection, container, false);
        Toolbar mToolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(mToolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(getTitle());

        RecyclerView mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_collection);
        final GridAutofitLayoutManager mGridLayoutManager =
                new GridAutofitLayoutManager(view.getContext(), mColumnWidth);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mGridLayoutManager);
        mRecyclerView.addItemDecoration(new GridSpacingDecoration(
                getResources().getDimensionPixelSize(R.dimen.grid_spacing)));

        COLLECTIION_TYPE = getArguments().getString(Utils.COLLECTION_TYPE);
        ArrayList<Photo> photoList;
        if (COLLECTIION_TYPE.equals(Utils.TYPE_TAGS)) {
            photoList = ImageStore.getPhotosInCategory(getContext(), COLLECTION_NAME);
        } else {
            photoList = ImageStore.getPhotosInAlbum(getContext(), COLLECTION_NAME);
        }
        for (Photo photo : photoList) {
            pathList.add(photo.getPath());
        }
        getSectionTitles(photoList);

        PhotoRecyclerAdapter mAdapter = new PhotoRecyclerAdapter(mColumnWidth, photoList);

        SectionedRecyclerViewAdapter.Section[] dummy =
                new SectionedRecyclerViewAdapter.Section[mSectionTitles.size()];
        final SectionedRecyclerViewAdapter mSectionedAdapter = new SectionedRecyclerViewAdapter(
                getActivity(), R.layout.section, R.id.section_text, mAdapter);
        mSectionedAdapter.setSections(mSectionTitles.toArray(dummy));

        mGridLayoutManager.setSpanSizeLookup(new GridAutofitLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return mSectionedAdapter.isSectionHeaderPosition(position) ? mGridLayoutManager.getSpanCount() : 1;
            }
        });

        mRecyclerView.setAdapter(mSectionedAdapter);
        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this.getActivity(), this));
        return view;
    }

    @Override
    public void onItemClick(View childView, int position) {
        position = getRealPosition(position);
        if (position >= 0) { // in case the section title was click
            startActivity(new Intent(getContext(), PhotoActivity.class)
                    .putStringArrayListExtra(Utils.PHOTO_PATH_LIST, pathList)
                    .putExtra(Utils.CURRENT_POSITION, position));
        }
    }

    @Override
    public void onItemLongPress(View childView, int position) {

    }

    private void getSectionTitles(ArrayList<Photo> photoList) {
        Map<String, Integer> titleList = new HashMap<>();
        Locale locale = getContext().getResources().getConfiguration().locale;
        SimpleDateFormat formatter = new SimpleDateFormat("MMMMM, yyyy", locale);

        for (int i = 0; i < photoList.size(); i++) {
            String date = formatter.format(photoList.get(i).getDate());
            if (titleList.get(date) == null || titleList.get(date) > i) {
                titleList.put(date, i);
            }
        }

        // convert to ArrayList and sort by value
        ArrayList<Map.Entry<String, Integer>> sorted = new ArrayList(titleList.entrySet());
        Collections.sort(sorted, new Comparator<Map.Entry<String, Integer>>() {
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return o1.getValue().compareTo(o2.getValue());
            }
        });

        for (Map.Entry<String, Integer> entry : sorted) {
            mSectionTitles.add(new SectionedRecyclerViewAdapter.Section(entry.getValue(), entry.getKey()));
        }
    }

    private int getRealPosition(int position) {
        int realPosition = position;
        for (int i = 0; i < mSectionTitles.size(); i++) {
            int titlePosition = mSectionTitles.get(i).getFirstPosition()+i;
            if (position == titlePosition) {
                return -1;
            } else if (position > titlePosition) {
                realPosition -= 1;
            }
        }
        return realPosition;
    }
}