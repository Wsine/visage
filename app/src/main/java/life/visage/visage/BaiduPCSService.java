package life.visage.visage;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by West on 2015/12/10.
 */
public class BaiduPCSService extends Service {
    ArrayList<Photo> photoList = new ArrayList<>();
    private BaiduPCSAPIHelper baiduPCSAPIHelper = new BaiduPCSAPIHelper();
    public Handler handler = new Handler();
    public Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (Utils.hasWifi(getApplicationContext())) {
                (new Thread(new Runnable() {
                    @Override
                    public void run() {
                        photoList = ImageStore.getAllPCS(getApplicationContext());
                        for (Photo photo : photoList) {
                            Log.d("myDebug", photo.getPath());
                            baiduPCSAPIHelper.cloudmatchupload(photo.getPath());
                        }
                    }
                })).start();
            } else {
                Log.d(Tag.LOG_WIFI, "No wifi available!");
            }
            handler.postDelayed(runnable, 60 * 60 * 1000);
        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(Tag.LOG_SERVICE, "BaiduPCSService created.");
        handler.post(runnable);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(Tag.LOG_SERVICE, "BaiduPCSService started.");
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(Tag.LOG_SERVICE, "BaiduPCSService destroyed.");
    }
}
