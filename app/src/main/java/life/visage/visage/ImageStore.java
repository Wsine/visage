package life.visage.visage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Dealing with image query stuff, synced with MediaStore regularly.
 */
public class ImageStore {
    private static final String FILE_SLASH = "file:///";

    private ImageStore() {
        // private constructor of no use
    }

    public static ArrayList<Photo> searchPhotos(Context context, String str) {
        ArrayList<Photo> listOfPhotos = new ArrayList<>();

        String[] projection = {
                ImageColumns.DATA,
                ImageColumns.DATE_TAKEN};

        String selection = ImageColumns.CATEGORY + " LIKE ?";
        String[] selectionArgs = {"%"+str+"%"};
        Cursor cursor = OpenHelper.getInstance(context).query(
                projection,
                selection,
                selectionArgs,
                ImageColumns.DATE_TAKEN
        );
        while (cursor.moveToNext()) {
            String path = cursor.getString(cursor.getColumnIndex(ImageColumns.DATA));
            Long date = cursor.getLong(cursor.getColumnIndexOrThrow(ImageColumns.DATE_TAKEN));
            Photo photo = new Photo(path);
            photo.setDate(date);
            listOfPhotos.add(photo);
        }
        cursor.close();

        return listOfPhotos;
    }

    public static ArrayList<Photo> getAllPhotos(Context context) {
        ArrayList<Photo> listOfPhotos = new ArrayList<>();
        String[] projection = {
                MediaStore.Images.ImageColumns.DATA,
                MediaStore.Images.ImageColumns.DATE_TAKEN};
        Cursor cursor = MediaStore.Images.Media.query(
                context.getContentResolver(),
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                projection,
                null, null, MediaStore.Images.ImageColumns.DATE_TAKEN
        );
        while (cursor.moveToNext()) {
            String path = FILE_SLASH + cursor.getString(cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA));
            Long date = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Images.ImageColumns.DATE_TAKEN));
            Photo photo = new Photo(path);
            photo.setDate(date);
            listOfPhotos.add(photo);
        }
        cursor.close();
        return listOfPhotos;
    }

    public static ArrayList<Photo> getAllPCS(Context context) {
        ArrayList<Photo> listOfPhotos = new ArrayList<>();
        String[] projection = {
                MediaStore.Images.ImageColumns.DATA,
                MediaStore.Images.ImageColumns.DATE_TAKEN};
        Cursor cursor = MediaStore.Images.Media.query(
                context.getContentResolver(),
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                projection,
                null, null, MediaStore.Images.ImageColumns.DATE_TAKEN
        );
        while (cursor.moveToNext()) {
            String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA));
            Long date = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Images.ImageColumns.DATE_TAKEN));
            Photo photo = new Photo(path);
            photo.setDate(date);
            listOfPhotos.add(photo);
        }
        cursor.close();
        return listOfPhotos;
    }

    public static ArrayList<Album> getAllAlbums(Context context) {
        ArrayList<Album> albumList = new ArrayList<>();
        Set<String> albumNameList = new LinkedHashSet<>();
        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

        String BUCKET_DISPLAY_NAME = MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME;
        String[] projection = {BUCKET_DISPLAY_NAME};

        Cursor cursor = MediaStore.Images.Media.query(
                context.getContentResolver(), uri, projection, null, null, BUCKET_DISPLAY_NAME);
        while (cursor.moveToNext()) {
            albumNameList.add(cursor.getString(cursor.getColumnIndexOrThrow(BUCKET_DISPLAY_NAME)));
        }
        cursor.close();

        for (String name : albumNameList) {
            albumList.add(new Album(name, getPhotosInAlbum(context, name)));
        }
        return albumList;
    }

    // for debug
    public static void testLocalDB(Context context) {
        Log.i("CATEGORY", "testLocalDB called!");

        String[] projection = {ImageColumns.DATA, ImageColumns.DATE_TAKEN, ImageColumns.NAME, ImageColumns.CATEGORY};
        Cursor cursor = OpenHelper.getInstance(context).query(projection, null, null, ImageColumns.DATE_TAKEN);

        while (cursor.moveToNext()) {
            String category = cursor.getString(cursor.getColumnIndexOrThrow(ImageColumns.CATEGORY));
            String name = cursor.getString(cursor.getColumnIndexOrThrow(ImageColumns.NAME));
            String data = cursor.getString(cursor.getColumnIndexOrThrow(ImageColumns.DATA));
            Long date = cursor.getLong(cursor.getColumnIndexOrThrow(ImageColumns.DATE_TAKEN));
            Log.i("DB", name + " " + data + " " + category + " " + date);
        }
        cursor.close();

    }
    // end debug

    public static ArrayList<Album> getAllCategories(Context context) {
        Log.i("CATEGORY", "getAllCategories called!");
        ArrayList<Album> albumList = new ArrayList<>();
        Set<String> categoryList = new LinkedHashSet<>();

        String[] projection = {ImageColumns.CATEGORY};
        Cursor cursor = OpenHelper.getInstance(context).query(projection, null, null, ImageColumns.CATEGORY);

        while (cursor.moveToNext()) {
            String category = cursor.getString(cursor.getColumnIndexOrThrow(ImageColumns.CATEGORY));
            if (category != null) {
                categoryList.add(category);
                Log.i("CATEGORY", category);
            } else {
                categoryList.add(Utils.OTHERS);
            }
        }
        cursor.close();

        for (String category : categoryList) {
            albumList.add(new Album(category, getPhotosInCategory(context, category)));
        }
        return albumList;
    }

    public static ArrayList<Photo> getPhotosInCategory(Context context, String category) {
        ArrayList<Photo> listOfPhotos = new ArrayList<>();

        String[] projection = {
                ImageColumns.DATA,
                ImageColumns.DATE_TAKEN};


        String selection = ImageColumns.CATEGORY + " = ?";
        String[] selectionArgs = {category};
        if (category.equals(Utils.OTHERS)) {
            selection = ImageColumns.CATEGORY + " is null";
            selectionArgs = null;
        }
        Cursor cursor = OpenHelper.getInstance(context).query(
                projection,
                selection,
                selectionArgs,
                ImageColumns.DATE_TAKEN
        );
        while (cursor.moveToNext()) {
            String path = cursor.getString(cursor.getColumnIndex(ImageColumns.DATA));
            Long date = cursor.getLong(cursor.getColumnIndexOrThrow(ImageColumns.DATE_TAKEN));
            Photo photo = new Photo(path);
            photo.setDate(date);
            listOfPhotos.add(photo);
        }
        cursor.close();

        return listOfPhotos;
    }

    public static ArrayList<Photo> getPhotosInAlbum(Context context, String albumName) {
        ArrayList<Photo> listOfPhotos = new ArrayList<>();

        String[] projection = {
                MediaStore.Images.ImageColumns.DATA,
                MediaStore.Images.ImageColumns.DATE_TAKEN};
        String selection = MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME + " = ?";
        String[] selectioArgs = {albumName};

        Cursor cursor = MediaStore.Images.Media.query(
                context.getContentResolver(),
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                projection,
                selection,
                selectioArgs,
                MediaStore.Images.ImageColumns.DATE_TAKEN
        );
        while (cursor.moveToNext()) {
            String path = FILE_SLASH + cursor.getString(cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA));
            Long date = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Images.ImageColumns.DATE_TAKEN));
            Photo photo = new Photo(path);
            photo.setDate(date);
            listOfPhotos.add(photo);
        }
        cursor.close();

        return listOfPhotos;
    }

    public interface ImageColumns {
        /**
         * The primary key.
         * <P>Type: INTEGER</P>
         * <P>Constant Values: "_id"</P>
         */
        public static final String _ID = "_id";

        /**
         * The absolute path for the image file,
         * can be loaded into ImageView directly with Picasso.
         * <P>Type: TEXT</P>
         * <P>Constant Values: "data"</P>
         */
        public static final String DATA = "data";

        /**
         * The filename of the file
         * <P>Type: TEXT</P>
         * <P>Constant Values: "name"</P>
         */
        public static final String NAME = "name";

        public static final String DATE_TAKEN = "datetaken";

        /**
         * The category of the image.
         * <P>Format: see <a>https://www.projectoxford.ai/images/bright/vision/examples/86categories.txt</a></P>
         * <P>Type: TEXT</P>
         * <P>Constant Values: "category"</P>
         */
        public static final String CATEGORY = "category";

        /**
         * The faces ids containing in an image.
         * <P>Format: different ids separated with commas, for example:
         * "c5c24a82-6845-4031-9d5d-978df9175426, 65d083d4-9447-47d1-af30-b626144bf0fb, ..."</P>
         * <P>Type: TEXT</P>
         * <P>Constant Value: event</P>
         */
        public static final String FACE_IDS = "face_ids";

        /**
         * The event that a photo belongs to, like wedding, party on sunday, etc.
         * <P>Type: TEXT</P>
         * <P>Constant Value: "event</P>
         */
        public static final String EVENT = "event";
    }


    /**
     * Helper class to deal with the database stuff.
     */
    public static class OpenHelper extends SQLiteOpenHelper {
        private static final String DB_NAME = "ImageStore.db";
        private static final String TABLE_NAME = "image_extended_meta";
        private static final int DB_VERSION = 4;

        private static final String CREATE_TABLE =
                "CREATE TABLE IF NOT EXISTS" + " " + TABLE_NAME + "(" +
                ImageColumns._ID             + " " + "INTEGER PRIMARY KEY AUTOINCREMENT," +
                ImageColumns.DATA            + " " + "TEXT NOT NULL," +
                ImageColumns.NAME            + " " + "TEXT NOT NULL," +
                ImageColumns.DATE_TAKEN      + " " + "INTEGER," +
                ImageColumns.CATEGORY        + " " + "TEXT," +
                ImageColumns.EVENT           + " " + "TEXT," +
                ImageColumns.FACE_IDS        + " " + "TEXT);";
        private static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;



        private static OpenHelper mInstance;
        private static Context mContext;

        private OpenHelper(Context context) {
            super(context, DB_NAME, null, DB_VERSION);
            mContext = context;
        }

        /**
         * Get an instance of the SQLiteOpenHelper
         * @param context, the proper context
         * @return OpenHelper instance.
         */
        public static OpenHelper getInstance(Context context) {
            if (mInstance == null) {
                mInstance = new OpenHelper(context);
            }
            return mInstance;
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_TABLE);
            syncWithMediaStore(db);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL(DROP_TABLE);
            onCreate(db);
        }

        /**
         * Synced with the system MediaStore.
         */
        private static void syncWithMediaStore(SQLiteDatabase db) {
            Cursor msCursor = MediaStore.Images.Media.query(
                    mContext.getContentResolver(),
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    null,
                    null,
                    null
            );

            while (msCursor.moveToNext()) {
                String data = FILE_SLASH + msCursor.getString(
                        msCursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA));
                String name = msCursor.getString(
                        msCursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DISPLAY_NAME));
                Long datetaken = msCursor.getLong(
                        msCursor.getColumnIndexOrThrow(MediaStore.Images.ImageColumns.DATE_TAKEN)
                );
                ContentValues values = new ContentValues();
                values.put(ImageColumns.DATA, data);
                values.put(ImageColumns.NAME, name);
                values.put(ImageColumns.DATE_TAKEN, datetaken);
                db.insert(TABLE_NAME, null, values);
            }
        }

        /**
         * Synced with the cloud database.
         */
        public void syncWithCloud() {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder().url(Utils.CLOUD_API).build();
                    try {
                        Response response = client.newCall(request).execute();
                        JSONArray jsonArray  = new JSONArray(response.body().string());
                        SQLiteDatabase db = getWritableDatabase();
                        for (int i = 0; i < jsonArray.length(); ++i) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String name = jsonObject.getString(ImageColumns.NAME);
                            String category = jsonObject.getString(ImageColumns.CATEGORY);
                            ContentValues values = new ContentValues();
                            values.put(ImageColumns.CATEGORY, category);
                            String whereClause = ImageColumns.NAME + "=?";
                            String[] whereArgs = {name};
                            Log.i("CLOUD", name + " " + category);
                            db.update(TABLE_NAME, values, whereClause, whereArgs);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }

        /**
         * A wrapper for SQLiteDatabase.query().
         * @param projection A list of which columns to return. Passing null will return all columns.
         * @param selection A filter declaring which rows to return, formatted as an SQL WHERE
         *                  clause (excluding the WHERE itself). Passing null will return all rows
         *                  for the given table.
         * @param selectionArgs You may include ?s in selection, which will be replaced by the
         *                      values from selectionArgs, in order that they appear in the
         *                      selection. The values will be bound as Strings.
         * @param orderBy How to order the rows, formatted as an SQL ORDER BY clause (excluding
         *                the ORDER BY itself). Passing null will use the default sort order,
         *                which may be unordered.
         * @return A Cursor object, which is positioned before the first entry. Note that Cursors
         * are not synchronized, see the documentation for more details.
         */
        public Cursor query(String[] projection,
                            String selection, String[] selectionArgs, String orderBy) {
            SQLiteDatabase db = getReadableDatabase();
            return db.query(TABLE_NAME, projection, selection, selectionArgs, null, null, orderBy);
        }

        /**
         * Insert ContentValues into the database
         * @param values, ContentValuese to be inserted into the database.
         * @return The row id of the inerted entry.
         */
        public long insert(ContentValues values) {
            SQLiteDatabase db = getWritableDatabase();
            long id = db.insert(TABLE_NAME, null, values);
            db.close();
            return id;
        }
    }
}
