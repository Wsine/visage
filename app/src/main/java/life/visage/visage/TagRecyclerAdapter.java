package life.visage.visage;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;

public class TagRecyclerAdapter extends RecyclerView.Adapter<TagRecyclerAdapter.ViewHolder> {
    private ArrayList<String> mTags;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity) parent.getContext()).getLayoutInflater();
        View v = inflater.inflate(R.layout.button_explore_tag, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return mTags.size();
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder {
        Button mTagButton;

        public ViewHolder (View v) {
            super(v);
            mTagButton = (Button) v.findViewById(R.id.button_explore_tag);
            mTagButton.getBackground().setAlpha(95);
        }

        public void bind(int position) {
            mTagButton.setText("#" + mTags.get(position));
            if (position == 0) {
                mTagButton.setPressed(true);
            }
        }
    }

    public TagRecyclerAdapter(ArrayList<String> tags) {
        mTags = tags;
    }
}
