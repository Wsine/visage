package life.visage.visage;

import android.content.Context;
import android.media.Image;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Brian on 2015/10/21.
 */
public class Utils {
    public static String PHOTO_COLLECTION_LIST = "PHOTO_COLLECTION_LIST";
    public static String PHOTO_PATH_LIST = "PHOTO_PATH_LIST";
    public static String COLLECTION_NAME  = "COLLECTION_NAME";
    public static String COLLECTION_TYPE = "COLLECTION_TYPE";
    public static String CURRENT_POSITION = "CURRENT_POSITION";
    public static String TYPE_TAGS = "TYPE_TAGS";
    public static String TYPE_ALBUMS = "TYPE_ALBUMS";
    public static String OTHERS = "others";

    public static String CLOUD_API = "http://mywsine.sinaapp.com/Visage/getDatabase2.php";

    private Utils() {
        // to make this class uninstantiable
    }

    public static boolean isWifi(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager)context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeInfo = connectivityManager.getActiveNetworkInfo();
        if (activeInfo != null && activeInfo.getType() == ConnectivityManager.TYPE_WIFI) {
            return true;
        }
        return false;
    }

    public static void testAPI(Context context) {
        ImageStore.OpenHelper.getInstance(context).syncWithCloud();
        ImageStore.testLocalDB(context);
    }
}
