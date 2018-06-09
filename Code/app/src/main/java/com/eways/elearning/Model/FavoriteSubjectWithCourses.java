package com.eways.elearning.Model;

import com.eways.elearning.Model.Course;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by zzzzz on 6/2/2018.
 */

public class FavoriteSubjectWithCourses {
    @SerializedName("Subject")
    @Expose
    private String subject;
    @SerializedName("listCourses")
    @Expose
    private ArrayList<Course> listCourses = null;

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public ArrayList<Course> getListCourses() {
        return listCourses;
    }

    public void setListCourses(ArrayList<Course> listCourses) {
        this.listCourses = listCourses;
    }
}
