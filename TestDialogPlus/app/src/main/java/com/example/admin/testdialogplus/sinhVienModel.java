package com.example.admin.testdialogplus;

/**
 * Created by ADMIN on 11/29/2017.
 */

public class sinhVienModel {
    int id;
    int image;
    String content;

    public sinhVienModel(int id, int image, String content) {
        this.id = id;
        this.image = image;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
