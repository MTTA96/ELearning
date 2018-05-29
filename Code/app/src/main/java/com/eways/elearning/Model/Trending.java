package com.eways.elearning.Model;

/**
 * Created by ADMIN on 5/28/2018.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Trending {

    @SerializedName("SubjectName")
    @Expose
    private String subjectName;
    @SerializedName("IdSubject")
    @Expose
    private String idSubject;
    @SerializedName("Img")
    @Expose
    private String img;

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getIdSubject() {
        return idSubject;
    }

    public void setIdSubject(String idSubject) {
        this.idSubject = idSubject;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

}
