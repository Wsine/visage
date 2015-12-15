package life.visage.visage;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

/**
 * Created by Brian on 11/29/2015.
 */
class Photo implements Parcelable {
    private String path;
    private Long dateTaken;

    public String getPath() {
        return this.path;
    }

    public Long getDate() {
        return this.dateTaken;
    }

    public Photo(String path, Long dateTaken) {
        this.path = path;
        this.dateTaken = dateTaken;
    }

    /**
     * CAUTION: The order of reading from Parcel must match
     * the order of writing in the writeToParcel() method below!
     * DON'T MODIFY THIS UNLESS YOU KNOW WHAT YOU ARE DOING.
     */
    public Photo(Parcel in) {
        this.path = in.readString();
        this.dateTaken = in.readLong();
    }

    /**
     * CAUTION: The order of reading from Parcel must match
     * the order of reading in the constructor Photo(Parcel in) method above!
     * DON'T MODIFY THIS UNLESS YOU KNOW WHAT YOU ARE DOING.
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        Log.i(Tag.LOG_PHOTO, "writeToParcel: " + flags);
        dest.writeString(path);
        dest.writeLong(dateTaken);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Photo createFromParcel(Parcel in){
            return new Photo(in);
        }
        public Photo[] newArray(int size) {
            return new Photo[size];
        }
    };
}
