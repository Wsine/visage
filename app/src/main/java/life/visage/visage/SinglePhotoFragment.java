package life.visage.visage;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class SinglePhotoFragment extends android.support.v4.app.Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        String pathName = getArguments().getString(Tag.PHOTO_PATH);
        View view = inflater.inflate(R.layout.photo_detail, container, false);
        ImageView mImageView = (ImageView) view.findViewById(R.id.photo_detail);
        Picasso.with(mImageView.getContext()).load(pathName).into(mImageView);

        return view;
    }
}
