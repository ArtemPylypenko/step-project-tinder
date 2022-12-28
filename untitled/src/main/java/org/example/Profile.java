package org.example;

public class Profile {
    private String name;
    private String imgURL;
    private final int id;


    public Profile(String name, String imgURL, int id) {
        this.name = name;
        this.imgURL = imgURL;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }

    public int getId() {
        return id;
    }
}
