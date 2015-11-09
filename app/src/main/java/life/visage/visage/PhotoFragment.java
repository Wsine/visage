package life.visage.visage;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * Created by Brian on 11/9/2015.
 */
public class PhotoFragment extends DialogFragment{
    static PhotoFragment newInstance(String pathName) {
        PhotoFragment photoFragment = new PhotoFragment();
        Bundle args = new Bundle();
        args.putString("PATH", pathName);
        photoFragment.setArguments(args);

        return photoFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        String pathName = getArguments().getString("PATH");
        View v = inflater.inflate(R.layout.photo_detail, container, false);
        ImageView mImageView = (ImageView) v.findViewById(R.id.photo_detail);

        Picasso.with(mImageView.getContext())
                .load(pathName)
                .placeholder(R.drawable.placeholder)
                .centerCrop()
                .into(mImageView);

        return v;
    }
}
