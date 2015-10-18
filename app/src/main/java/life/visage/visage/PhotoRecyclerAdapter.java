package life.visage.visage;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MultiAutoCompleteTextView;
import android.widget.PopupWindow;

import com.android.ex.chips.BaseRecipientAdapter;
import com.android.ex.chips.RecipientEditTextView;

/**
 * Created by Brian on 2015/10/5.
 */
public class PhotoRecyclerAdapter extends RecyclerView.Adapter<PhotoRecyclerAdapter.ViewHolder> {
    private int mColumnWidth;
    private Integer[] mThumbIds;

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        final LayoutInflater inflater = ((Activity) parent.getContext()).getLayoutInflater();
        ImageView mImageView = new ImageView(parent.getContext());
        mImageView.setLayoutParams(new GridView.LayoutParams(mColumnWidth, mColumnWidth));
        mImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        mImageView.setClickable(true);
        mImageView.setLongClickable(true);
        mImageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(final View v) {
                final PopupMenu popup = new PopupMenu(parent.getContext(), v);
                popup.getMenuInflater().inflate(R.menu.popup_menu, popup.getMenu());

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.popup_edit: break;
                            case R.id.popup_delete: {
                                new AlertDialog.Builder(parent.getContext())
                                        .setTitle("Delete photo")
                                        .setMessage("Are you sure you want to delte?")
                                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                // do nothing
                                            }
                                        })
                                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                // do nothing
                                            }
                                        })
                                        .show();
                                break;
                            }
                            case R.id.popup_share: {
                                break;
                            }
                        }
                        return false;
                    }
                });

                popup.show();
                return false;
            }
        });

        return new ViewHolder(mImageView);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bindData(position);
    }

    @Override
    public int getItemCount() {
        return mThumbIds.length;
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView mImageView;

        public ViewHolder (View v) {
            super(v);
            mImageView = (ImageView) v;
        }

        public void bindData(int position) {
            mImageView.setImageResource(mThumbIds[position]);
        }
    }

    public PhotoRecyclerAdapter(int width, Integer[] thumbIds) {
        mColumnWidth = width;
        mThumbIds = thumbIds;
    }
}
