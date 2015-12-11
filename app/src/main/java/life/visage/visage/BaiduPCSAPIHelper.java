package life.visage.visage;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.baidu.oauth.BaiduOAuth;
import com.baidu.pcs.BaiduPCSActionInfo;
import com.baidu.pcs.BaiduPCSClient;
import com.baidu.pcs.BaiduPCSStatusListener;

/**
 * Created by West on 2015/12/1.
 */
public class BaiduPCSAPIHelper {
    // the handler
    private Handler mbUiThreadHandler = null;
    // the oauth (test account's oauth, it can be overwrited by login() )
    private String mbOauth = "23.56ead64c536be397579f79b809151d75.2592000.1452357428.1669412745-238347";
    // the api key
    /*
     * should instead of our app_key, but this is useable
     */
    private final static String mbApiKey = "L6g70tBRRIXLsY0Z3HwKqlRE";
    // the default root folder
    private final static String mbRootPath = "/apps/pcstest_oauth";
    // the needed Context for which Activity constructing the BaiduPCSAPIHelper
    private Context mContext = null;

    /*
     * Construction for BaiduPCSService
     */
    BaiduPCSAPIHelper() {
        mbUiThreadHandler = new Handler();
    }

    /*
     *  Input: Activity's context for which constucting BaiduPCSAPIHelper
     */
    BaiduPCSAPIHelper(Context context) {
        mbUiThreadHandler = new Handler();
        mContext = context;
    }

    /*
     * Set the parameter above
     */
    public void login() {
        BaiduOAuth oauthClient = new BaiduOAuth();
        oauthClient.startOAuth(mContext, mbApiKey, new String[]{"basic", "netdisk"},
                new BaiduOAuth.OAuthListener() {
            @Override
            public void onComplete(BaiduOAuth.BaiduOAuthResponse response) {
                if (response != null) {
                    mbOauth = response.getAccessToken();
                    //Toast.makeText(mContext, "Token: " + mbOauth + "    User name:" + response.getUserName(), Toast.LENGTH_SHORT).show();
                    Toast.makeText(mContext, response.getUserName() + "login successfully", Toast.LENGTH_SHORT).show();
                    Log.d("myDebug_Login", "Token: " + mbOauth + "    User name:" + response.getUserName());
                }
            }

            @Override
            public void onException(String msg) {
                Toast.makeText(mContext, "Login failed " + msg, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancel() {
                //Toast.makeText(getApplicationContext(), "Login cancelled", Toast.LENGTH_SHORT).show();
                // do nothing
            }
        });
    }

    /*
     * Return: the left space of the disk
     * error Return: 0
     * not run in background
     */
    public long getQuota() {
        if (mbOauth != null) {
            BaiduPCSClient api = new BaiduPCSClient();
            api.setAccessToken(mbOauth);
            BaiduPCSActionInfo.PCSQuotaResponse info = api.quota();
            if (info != null && info.status.errorCode == 0) {
                return info.total - info.used;
            } else {
                return 0;
            }
        } else {
            Toast.makeText(mContext, "Please login first", Toast.LENGTH_SHORT).show();
        }
        return 0;
    }

    /*
     * Input: uploadFileDir
     * Input Sample: /storage/sdcard0/DCIM/Camera/IMG_20151129_163844.jpg
     * Running in background
     */
    public void upload(String _uploadFileDir) {
        if (mbOauth != null) {
            final String uploadFileDir = _uploadFileDir;
            Thread workThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    BaiduPCSClient api = new BaiduPCSClient();
                    api.setAccessToken(mbOauth);
                    String[] temp = uploadFileDir.split("/");
                    String destinationDir = "/" + temp[temp.length - 1];
                    final BaiduPCSActionInfo.PCSFileInfoResponse response = api.uploadFile(uploadFileDir, mbRootPath + destinationDir, new BaiduPCSStatusListener() {
                        @Override
                        public void onProgress(long bytes, long total) {
                            final long bs = bytes;
                            final long tl = total;
                            mbUiThreadHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    Log.d("myDebug", "upload progress: " + bs + " / " + tl);
                                }
                            });
                        }

                        @Override
                        public long progressInterval() {
                            return 1000;
                        }
                    });
                    mbUiThreadHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            Log.d("myDebug", response.status.errorCode + " " + response.status.message + " " + response.commonFileInfo.blockList);
                        }
                    });
                }
            });
            workThread.start();
        } else {
            Toast.makeText(mContext, "Please login first", Toast.LENGTH_SHORT).show();
        }
    }

    /*
     * Input: fileName
     * Input Sample: IMG_20151129_163844.jpg
     * Running in background
     */
    public void delete(String _fileName) {
        if (mbOauth != null) {
            final String fileName = mbRootPath + "/" + _fileName;
            Thread workThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    BaiduPCSClient api = new BaiduPCSClient();
                    api.setAccessToken(mbOauth);
                    final BaiduPCSActionInfo.PCSSimplefiedResponse ret = api.deleteFile(fileName);
                    mbUiThreadHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            Log.d("myDebug", "Delete files: " + ret.errorCode + " " + ret.message);
                        }
                    });
                }
            });
            workThread.start();
        } else {
            Toast.makeText(mContext, "Please login first", Toast.LENGTH_SHORT).show();
        }
    }

    /*
     * Input Sample:
     *     _fileName = IMG_20151129_163844.jpg
     *     _destinationDir = /storage/sdcard0/DCIM/Camera/IMG_20151129_163844.jpg
     * Running in background
     */
    public void download(String _fileName, String _destinationDir) {
        if (mbOauth != null) {
            final String source = mbRootPath + "/" + _fileName;
            final String target = _destinationDir;
            Thread workThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    BaiduPCSClient api = new BaiduPCSClient();
                    api.setAccessToken(mbOauth);
                    final BaiduPCSActionInfo.PCSSimplefiedResponse ret = api.downloadFileFromStream(source, target, new BaiduPCSStatusListener() {
                        @Override
                        public void onProgress(long bytes, long total) {
                            final long bs = bytes;
                            final long tl = total;
                            mbUiThreadHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    Log.d("myDebug", "download progress: " + bs + " / " + tl);
                                }
                            });
                        }

                        @Override
                        public long progressInterval() {
                            return 500;
                        }
                    });
                    mbUiThreadHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            Log.d("myDebug", "Download files:  " + ret.errorCode + "   " + ret.message);
                        }
                    });
                }
            });
            workThread.start();
        } else {
            Toast.makeText(mContext, "Please login first", Toast.LENGTH_SHORT).show();
        }
    }

    /*
     * Input Sample:
     *     fileName = IMG_20151129_163844.jpg
     * Return isExist
     */
    public boolean search(String fileName) {
        if (mbOauth != null) {
            BaiduPCSClient api = new BaiduPCSClient();
            api.setAccessToken(mbOauth);
            BaiduPCSActionInfo.PCSListInfoResponse ret = api.search(mbRootPath, fileName, true);
            if (ret.status.errorCode == 0 && ret.list != null && !ret.list.isEmpty()) {
                return true;
            }
        } else {
            Toast.makeText(mContext, "Please login first", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    /*
     * Input Sample:
     *     fileName = /storage/sdcard0/DCIM/Camera/IMG_20151129_163844.jpg
     * Return isExist
     */
    public boolean cloudmatch(String fileName) {
        if (mbOauth != null) {
            BaiduPCSClient api = new BaiduPCSClient();
            api.setAccessToken(mbOauth);
            String[] temp = fileName.split("/");
            String matchDir = mbRootPath + "/" + temp[temp.length - 1];
            BaiduPCSActionInfo.PCSFileInfoResponse ret = api.cloudMatch(fileName, matchDir);
            Log.d("myDebug", "errorCode = " + ret.status.errorCode + " Message = " + ret.status.message);
            if (ret.status.errorCode == 0) {
                return true;
            }
        } else {
            Toast.makeText(mContext, "Please login first", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    /*
     * Input Sample:
     *     _fileOldName = IMG_20151129_163844.jpg
     *     _fileNewName = 448361_92115102_GMI.jpg
     * Running in background
     */
    public void rename(String _fileOldName, String _fileNewName) {
        if (mbOauth != null) {
            final String fileOldName = _fileOldName;
            final String fileNewName = _fileNewName;
            Thread workThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    BaiduPCSClient api = new BaiduPCSClient();
                    api.setAccessToken(mbOauth);
                    final BaiduPCSActionInfo.PCSFileFromToResponse ret = api.rename(fileOldName, fileNewName);
                    mbUiThreadHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            Log.d("myDebug", "Rename: " + ret.status.errorCode + " " + ret.status.message);
                        }
                    });
                }
            });
            workThread.start();
        } else {
            Toast.makeText(mContext, "Please login first", Toast.LENGTH_SHORT).show();
        }
    }

    /*
     * Description:
     *     Wonderful Function
     *     cloud match and upload if cloud match false
     * Input Sample:
     *     _uploadFileDir = /storage/sdcard0/DCIM/Camera/IMG_20151129_163844.jpg
     * Running in background
     */
    public void cloudmatchupload(String _uploadFileDir) {
        if (mbOauth != null) {
            final String uploadFileDir = _uploadFileDir;
            Thread workThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    BaiduPCSClient api = new BaiduPCSClient();
                    api.setAccessToken(mbOauth);
                    String[] temp = uploadFileDir.split("/");
                    String destinationDir = mbRootPath + "/" + temp[temp.length - 1];
                    final BaiduPCSActionInfo.PCSFileInfoResponse ret = api.cloudMatchAndUploadFile(uploadFileDir, destinationDir, new BaiduPCSStatusListener() {
                        @Override
                        public void onProgress(long bytes, long total) {
                            final long bs = bytes;
                            final long tl = total;
                            mbUiThreadHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    Log.d("myDebug", "upload progress: " + bs + " / " + tl);
                                }
                            });
                        }

                        @Override
                        public long progressInterval() {
                            return 1000;
                        }
                    });
                    mbUiThreadHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            Log.d("myDebug", "CloudMatchandUpload:  " + ret.status.errorCode + "\n" + ret.status.message);
                        }
                    });
                }
            });
            workThread.start();
        } else {
            Toast.makeText(mContext, "Please login first", Toast.LENGTH_SHORT).show();
        }
    }
}
