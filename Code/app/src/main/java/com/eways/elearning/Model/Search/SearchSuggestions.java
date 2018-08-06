package com.eways.elearning.Model.Search;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by zzzzz on 5/27/2018.
 */

public class SearchSuggestions {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("subject_name")
    @Expose
    private String subjectName;
    @SerializedName("image")
    @Expose
    private String image;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}
