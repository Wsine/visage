package life.visage.visage;

import java.util.ArrayList;

class Album {
    /**
     * The absolute path of the album cover image file.
     */
    private String cover;
    /**
     * The name of the album
     */
    private String name;
    /**
     * The list of absolute path of image files in the album.
     */
    private ArrayList<String> list;

    public Album(String name, ArrayList<String> list) {
        this.name = name;
        this.list = list;
        this.cover = list.get(list.size()-1);
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getList() {
        return list;
    }

    public void setList(ArrayList<String> list) {
        this.list = list;
    }
}