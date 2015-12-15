package life.visage.visage;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Brian on 2015/10/21.
 * Home for homeless methods' and constants'.
 */
public class Utils {
    public static String PHOTO_PATH_LIST = "PHOTO_PATH_LIST";

    public static String CLOUD_API = "http://mywsine.sinaapp.com/Visage/getDatabase2.php";

    public static String SEARCH_QUERY = "SEARCH_QUERY";

    private Utils() {
        // to make this class non-instantiable
    }

    public static boolean hasWifi(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeInfo = connectivityManager.getActiveNetworkInfo();

        return (activeInfo != null) && (activeInfo.getType() == ConnectivityManager.TYPE_WIFI);
    }

    public static int getThumbnailWidth() {
        return 171;
    }
}
