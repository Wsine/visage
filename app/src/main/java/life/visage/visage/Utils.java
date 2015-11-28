package life.visage.visage;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by Brian on 2015/10/21.
 */
public class Utils {
    public static String PHOTO_COLLECTION_LIST = "PHOTO_COLLECTION_LIST";
    public static String PHOTO_PATH_LIST = "PHOTO_PATH_LIST";
    public static String COLLECTION_NAME  = "COLLECTION_NAME";
    public static String CURRENT_POSITION = "CURRENT_POSITION";

    private Utils() {
        // to make this class uninstantiable
    }

    public static Set<String> getAllAlbumNames(Activity activity) {
        Set<String> listOfAllAlbumNames = new LinkedHashSet<>();
        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String COLUMN = MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME;
        Cursor cursor = activity.getContentResolver().query(uri, new String[] {COLUMN}, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                listOfAllAlbumNames.add(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN)));
            }
            cursor.close();
        }
        return listOfAllAlbumNames;
    }

    public static ArrayList<String> getPhotosInAlbum(Context context, String albumName) {
        ArrayList<String> listOfPhotos = new ArrayList<>();
        Cursor cursor = MediaStore.Images.Media.query(
                context.getContentResolver(),
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[] {MediaStore.Images.Media.DATA},
                MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME + " = ?",
                new String[] {albumName},
                MediaStore.Images.ImageColumns.DATE_TAKEN
        );
        if (cursor != null) {
            while (cursor.moveToNext()) {
                listOfPhotos.add("file:///" + cursor.getString(cursor.getColumnIndex(MediaStore.Images.Thumbnails.DATA)));
            }
        }
        return listOfPhotos;
    }

    static public ArrayList<String> getAllShownImagesPath(Activity activity) {
        Uri URIS[] = {MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            MediaStore.Images.Media.INTERNAL_CONTENT_URI};
        String[] projection = {MediaStore.MediaColumns.DATA,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME};

        ArrayList<String> listOfAllImages = new ArrayList<>();
        for (Uri uri : URIS) {
            int column_index_data;
            String absolutePathOfImage;
            Cursor cursor = activity.getContentResolver().query(uri, projection, null, null, null);
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
        }
        return listOfAllImages;
    }
}
