package com.eways.elearning.DataModel.Other;

/**
 * Created by ADMIN on 11/30/2017.
 */

public class LayHinhModel {
    int idchon;
    int hinh;
    String content;

    public LayHinhModel(int idchon, int hinh, String content) {
        this.idchon = idchon;
        this.hinh = hinh;
        this.content = content;
    }

    public int getIdchon() {
        return idchon;
    }

    public void setIdchon(int idchon) {
        this.idchon = idchon;
    }

    public int getHinh() {
        return hinh;
    }

    public void setHinh(int hinh) {
        this.hinh = hinh;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
