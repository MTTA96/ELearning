package com.eways.elearning.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ADMIN on 7/6/2018.
 */

public class SubjectSpec {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("video")
    @Expose
    private String video;
    @SerializedName("image")
    @Expose
    private List<Image> image = null;

    public SubjectSpec(Integer id, String title, String video, List<Image> image) {
        this.id = id;
        this.title = title;
        this.video = video;
        this.image = image;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public List<Image> getImage() {
        return image;
    }

    public void setImage(List<Image> image) {
        this.image = image;
    }
}
