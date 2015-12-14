package life.visage.visage;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.BindDimen;
import butterknife.ButterKnife;

public class AlbumsRecyclerAdapter extends RecyclerView.Adapter<AlbumsRecyclerAdapter.ViewHolder> {
    private ArrayList<Album> mAlbums;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity) parent.getContext()).getLayoutInflater();
        View view = inflater.inflate(R.layout.album_tile, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bindData(position);
    }

    @Override
    public int getItemCount() {
        return mAlbums.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.album_image) ImageView albumImage;
        @Bind(R.id.album_title) TextView albumTitle;
        @BindDimen(R.dimen.album_width) int albumWidth;

        public ViewHolder (View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void bindData(int position) {
            Picasso.with(albumImage.getContext())
                    .load(mAlbums.get(position).getCover())
                    .resize(albumWidth, albumWidth)
                    .placeholder(R.drawable.placeholder)
                    .centerCrop()
                    .into(albumImage);
            albumTitle.setText(mAlbums.get(position).getName());
            albumTitle.getBackground().setAlpha(90);
        }
    }

    public AlbumsRecyclerAdapter(ArrayList<Album> albums) {
        mAlbums = albums;
    }
}

