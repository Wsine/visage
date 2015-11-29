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
}
