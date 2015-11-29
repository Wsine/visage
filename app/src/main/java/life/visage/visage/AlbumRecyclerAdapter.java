package life.visage.visage;

import android.app.Activity;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AlbumRecyclerAdapter extends RecyclerView.Adapter<AlbumRecyclerAdapter.ViewHolder> {
    private ArrayList<Album> mAlbums;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity) parent.getContext()).getLayoutInflater();
        final View v = inflater.inflate(R.layout.album_tile, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bindData(position);
    }

    @Override
    public int getItemCount() {
        return mAlbums.size();
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView mImageView;
        TextView mTextView;

        public ViewHolder (View root) {
            super(root);
            mImageView = (ImageView) root.findViewById(R.id.album_image);
            mTextView = (TextView) root.findViewById(R.id.album_title);
        }

        public void bindData(int position) {
            int width = mImageView.getResources().getDimensionPixelSize(R.dimen.album_width);
            Picasso.with(mImageView.getContext())
                    .load(mAlbums.get(position).getCover())
                    .resize(width, width)
                    .placeholder(R.drawable.placeholder)
                    .centerCrop()
                    .into(mImageView);
            mTextView.setText(mAlbums.get(position).getName());
            mTextView.getBackground().setAlpha(90);
        }
    }

    public AlbumRecyclerAdapter(ArrayList<Album> albums) {
        mAlbums = albums;
    }
}
