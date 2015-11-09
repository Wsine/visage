package life.visage.visage;

import android.app.Activity;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;

import java.util.ArrayList;

/**
 * Created by Brian on 2015/10/21.
 */
public class Utils {
    private Utils() {
        // to make this class uninstantiable
    }

    static public ArrayList<String> getAllShownImagesPath(Activity activity) {
        Uri uri;
        Cursor cursor;
        int column_index_data;
        ArrayList<String> listOfAllImages = new ArrayList<>();
        String absolutePathOfImage;
        uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

        String[] projection = {MediaStore.MediaColumns.DATA,
                    MediaStore.Images.Media.BUCKET_DISPLAY_NAME};
        cursor = activity.getContentResolver().query(uri, projection, null, null, null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    column_index_data = cursor.getColumnIndexOrThrow(
                            MediaStore.Images.Thumbnails.DATA);
                    absolutePathOfImage = "file:///" + cursor.getString(column_index_data);
                    listOfAllImages.add(absolutePathOfImage);
                } while (cursor.moveToNext());
            }
            cursor.close();
        }

        return listOfAllImages;
    }
}
