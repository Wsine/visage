package life.visage.visage;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;


/**
 * Created by Brian on 2015/10/5.
 */
public class PhotoRecyclerAdapter extends RecyclerView.Adapter<PhotoRecyclerAdapter.ViewHolder> {
    private int mColumnWidth;
    private ArrayList<String> mImagePath;

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity) parent.getContext()).getLayoutInflater();
        final View rootView = inflater.inflate(R.layout.photo_recycler_item, parent, false);
        rootView.setLayoutParams(new GridView.LayoutParams(mColumnWidth, mColumnWidth));

        return new ViewHolder(rootView);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bindData(position);
    }

    @Override
    public int getItemCount() {
        return mImagePath.size();
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
            mImageView.setImageBitmap(ThumbnailUtils.extractThumbnail(BitmapFactory.decodeFile(mImagePath.get(position)),
                    mColumnWidth, mColumnWidth));
        }
    }

    public PhotoRecyclerAdapter(int width, ArrayList<String> imagePath) {
        mColumnWidth = width;
        mImagePath = imagePath;
    }


}
