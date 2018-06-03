package com.eways.elearning.Model;

/**
 * Created by ADMIN on 5/29/2018.
 */

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CourseHome {

    @SerializedName("Subject")
    @Expose
    private String subject;
    @SerializedName("listCourses")
    @Expose
    private List<Course> listCourses = null;

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public List<Course> getListCourses() {
        return listCourses;
    }

    public void setListCourses(List<Course> listCourses) {
        this.listCourses = listCourses;
    }

}
