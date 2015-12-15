package life.visage.visage;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindDimen;
import butterknife.ButterKnife;


/**
 * Created by Brian on 2015/10/5.
 */
public class PhotoRecyclerAdapter extends RecyclerView.Adapter<PhotoRecyclerAdapter.ViewHolder> {
    @BindDimen(R.dimen.thumbnail_width) int mColumnWidth;
    private ArrayList<Photo> photoArrayList;

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity) parent.getContext()).getLayoutInflater();
        final View view = inflater.inflate(R.layout.photo_recycler_item, parent, false);
        ButterKnife.bind(this, view);

        view.setLayoutParams(new GridView.LayoutParams(mColumnWidth, mColumnWidth));
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bindData(position);
    }

    @Override
    public int getItemCount() {
        return photoArrayList.size();
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView mImageView;

        public ViewHolder(View v) {
            super(v);
            mImageView = (ImageView) v.findViewById(R.id.recycler_image);
        }

        public void bindData(int position) {
            Picasso.with(mImageView.getContext())
                    .load(photoArrayList.get(position).getPath())
                    .placeholder(R.drawable.placeholder)
                    .resize(mColumnWidth, mColumnWidth)
                    .centerCrop()
                    .into(mImageView);
        }
    }

    public PhotoRecyclerAdapter(int width, ArrayList<Photo> pathList) {
        mColumnWidth = width;
        photoArrayList = pathList;
    }
}
