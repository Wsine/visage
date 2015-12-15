package life.visage.visage;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.util.ArrayList;

/**
 * Collection, representing a collection of images, for example:
 *   - photos belong to the same category,
 *   - user-defined album,
 *   - photos tagged with the same tag,
 *   - photos that describing the same event, like wedding, party, etc.
 */
class Collection implements Parcelable {
    // The absolute path of the collection cover image file. with "file://" prefix
    // default: the last image in the photo list.
    private String cover;
    // the title of the collection
    private String title;

    // The list of photos in the collection, must be non-empty
    private ArrayList<Photo> photoArrayList;


    public ArrayList<Photo> getPhotoArrayList() {
        return photoArrayList;
    }

    public void setPhotoArrayList(ArrayList<Photo> photoArrayList) {
        this.photoArrayList = photoArrayList;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Collection(String title, ArrayList<Photo> photoArrayList) {
        this.title = title;
        this.photoArrayList = photoArrayList;
        this.cover = photoArrayList.get(photoArrayList.size()-1).getPath();
    }

    /**
     * CAUTION: The order of reading from Parcel must match
     * the order of writing in the writeToParcel() method below!
     * DON'T MODIFY THIS UNLESS YOU KNOW WHAT YOU ARE DOING.
     */
    public Collection(Parcel in) {
        this.cover = in.readString();
        this.title = in.readString();
        this.photoArrayList = new ArrayList<>();
        in.readList(this.photoArrayList, null);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * CAUTION: The order of reading from Parcel must match the order of
     * reading in the constructor Collection(Parcel in) method above!
     * DON'T MODIFY THIS UNLESS YOU KNOW WHAT YOU ARE DOING.
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        Log.i(Tag.LOG_COLLECTION, "writeToParcel: " + flags);
        dest.writeString(cover);
        dest.writeString(title);
        dest.writeList(photoArrayList);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        @Override
        public Collection createFromParcel(Parcel source) {
            Log.i(Tag.LOG_COLLECTION, "createFromParcel: ");
            return new Collection(source);
        }

        @Override
        public Collection[] newArray(int size) {
            return new Collection[size];
        }
    };
}
