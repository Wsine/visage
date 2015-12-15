package life.visage.visage;

/**
 * A wrapper for tag-related String constants, including:
 *  - Fragment tags
 *  - Bundle tags
 *  - Log tags
 *  - ...
 * NOTED THAT THIS INTERFACE HAS NOTHING TO DO WITH THE PHOTOS' TAG.
 */
interface Tag {
    final public static String CURRENT_POSITION = "CURRENT_POSITION";
    final public static String PHOTO_PATH = "PHOTO_PATH";
    final public static String PHOTO_LIST = "PHOTO_LIST";
    final public static String COLLECTION = "COLLECTION";

    final public static String LOG_ACTIVITY = "ACTIVITY";
    final public static String LOG_SERVICE = "SERVICE";

    final public static String LOG_PHOTO = "PHOTO";
    final public static String LOG_CATEGORY = "CATEGORY";
    final public static String LOG_COLLECTION = "COLLECTION";

    final public static String LOG_WIFI = "visage.LOG_WIFI";
}
