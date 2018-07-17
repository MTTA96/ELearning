package com.eways.elearning.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ADMIN on 6/17/2018.
 */

public class Request {

    @SerializedName("IdCourse")
    @Expose
    private String idCourse;
    @SerializedName("Uid")
    @Expose
    private String uid;
    @SerializedName("SentDate")
    @Expose
    private String sentDate;
    @SerializedName("Status")
    @Expose
    private String status;
    @SerializedName("Message")
    @Expose
    private String message;

    public Request(String idCourse, String uid, String sentDate, String status, String message) {
        this.idCourse = idCourse;
        this.uid = uid;
        this.sentDate = sentDate;
        this.status = status;
        this.message = message;
    }

    public String getIdCourse() {
        return idCourse;
    }

    public void setIdCourse(String idCourse) {
        this.idCourse = idCourse;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getSentDate() {
        return sentDate;
    }

    public void setSentDate(String sentDate) {
        this.sentDate = sentDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
