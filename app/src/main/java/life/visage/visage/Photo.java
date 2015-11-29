package life.visage.visage;

/**
 * Created by Brian on 11/29/2015.
 */
public class Photo {
    private String path;
    private Long DATE_TAKEN;

    public Photo(String path) {
        this.path = path;
    }

    public String getPath() {
        return this.path;
    }

    public Long getDate() {
        return this.DATE_TAKEN;
    }

    public void setDate(Long date) {
        this.DATE_TAKEN = date;
    }
}
