package life.visage.visage;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public class CollectionFragment extends Fragment implements RecyclerItemClickListener.OnItemClickListener {
    private ArrayList<Photo> photoList = new ArrayList<>();
    private ArrayList<String> pathList = new ArrayList<>();
    private String COLLECTION_NAME;
    private static final int mColumnWidth = 171;

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

        RecyclerView mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_favourite);
        final GridAutofitLayoutManager mGridLayoutManager =
                new GridAutofitLayoutManager(view.getContext(), mColumnWidth);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mGridLayoutManager);
        mRecyclerView.addItemDecoration(new GridSpacingDecoration(
                getResources().getDimensionPixelSize(R.dimen.grid_spacing)));


        photoList = ImageStore.getPhotosInAlbum(getContext(), COLLECTION_NAME);

        Hashtable<String, Integer> titleList =new Hashtable<>();
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

    @Override
    public void onItemClick(View childView, int position) {
        startActivity(new Intent(getContext(), PhotoActivity.class)
                .putStringArrayListExtra(Utils.PHOTO_PATH_LIST, pathList)
                .putExtra(Utils.CURRENT_POSITION, position));
    }

    @Override
    public void onItemLongPress(View childView, int position) {

    }
}

