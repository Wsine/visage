package life.visage.visage;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
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

import butterknife.Bind;
import butterknife.BindDimen;
import butterknife.ButterKnife;

/**
 * TODO: should refactor the sectioning feature with a more robust library
 * <a>https://github.com/truizlop/SectionedRecyclerView</a>
 */
public class CollectionFragment extends Fragment
        implements RecyclerItemClickListener.OnItemClickListener {
    @BindDimen(R.dimen.thumbnail_width) int thumbnailWidth;
    @Bind(R.id.recycler_collection) RecyclerView mRecyclerView;

    private List<SectionedRecyclerViewAdapter.Section> mSectionTitles = new ArrayList<>();
    private Collection collection;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        collection = getArguments().getParcelable(Tag.COLLECTION);

        View view = inflater.inflate(R.layout.fragment_collection, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(collection.getTitle());
        ButterKnife.bind(this, view);

        final GridAutofitLayoutManager mGridLayoutManager =
                new GridAutofitLayoutManager(view.getContext(), thumbnailWidth);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mGridLayoutManager);
        mRecyclerView.addItemDecoration(new GridAutofitLayoutManager.GridSpacingDecoration(
                getResources().getDimensionPixelSize(R.dimen.grid_spacing)));

        setupSectionTitles();

        PhotoRecyclerAdapter mAdapter = new PhotoRecyclerAdapter(thumbnailWidth, collection.getPhotoArrayList());

        SectionedRecyclerViewAdapter.Section[] dummy =
                new SectionedRecyclerViewAdapter.Section[mSectionTitles.size()];
        final SectionedRecyclerViewAdapter mSectionedAdapter = new SectionedRecyclerViewAdapter(
                getActivity(), R.layout.section_title, R.id.section_text, mAdapter);
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
            startActivity(new Intent(getContext(), PhotoDetailActivity.class)
                    .putParcelableArrayListExtra(Tag.PHOTO_LIST, collection.getPhotoArrayList())
                    .putExtra(Tag.CURRENT_POSITION, position));
        }
    }

    @Override
    public void onItemLongPress(View childView, int position) {

    }

    // TODO: this method is ugly, should make it elegant
    private void setupSectionTitles() {
        ArrayList<Photo> photoList = collection.getPhotoArrayList();
        Map<String, Integer> titleList = new HashMap<>();
        Locale locale = getContext().getResources().getConfiguration().locale;
        SimpleDateFormat formatter = new SimpleDateFormat("MMM, yyyy");

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