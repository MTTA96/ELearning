package com.eways.elearning.Network.Responses.Course;

import com.eways.elearning.Model.Course.Course;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by zzzzz on 7/1/2018.
 */

public class CourseRespsonse {

    @SerializedName("errorCode")
    @Expose
    private Integer errorCode;
    @SerializedName("result")
    @Expose
    private Course course = null;

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

}
