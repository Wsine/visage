package life.visage.visage;

import android.os.FileObserver;
import android.util.Log;

/**
 * Created by West on 2015/12/1.
 */
public class MyFileObserver extends FileObserver {

    private String watchingDir = null;
    private BaiduPCSAPIHelper myBaiduPCSAPIHelper = null;

    public MyFileObserver(String path, BaiduPCSAPIHelper baiduPCSAPIHelper) {
        super(path, FileObserver.CREATE | FileObserver.DELETE | FileObserver.MODIFY);
        watchingDir = path;
        myBaiduPCSAPIHelper = baiduPCSAPIHelper;
    }

    public MyFileObserver(String path, int mask) {
        super(path, mask);
    }

    @Override
    public void onEvent(int event, String path) {
        final int action = event & FileObserver.ALL_EVENTS;
        switch (action) {
            case FileObserver.CREATE:
                Log.d("myDebug", "event: 文件或目录被创建, path: " + path);
                break;
            case FileObserver.DELETE:
                Log.d("myDebug", "event: 文件或目录被删除, path: " + path);
                break;
            case FileObserver.MODIFY:
                Log.d("myDebug", "event: 文件或目录被修改, path: " + path);
                myBaiduPCSAPIHelper.cloudmatchupload(watchingDir + "/" + path);
                break;
        }
    }
}
