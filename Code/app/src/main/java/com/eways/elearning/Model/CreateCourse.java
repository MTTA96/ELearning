package com.eways.elearning.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ADMIN on 7/27/2018.
 */

public class CreateCourse {
    @SerializedName("IdRequisitionCourse")
    @Expose
    private String idRequisitionCourse;
    @SerializedName("UidStudent")
    @Expose
    private String uidStudent;
    @SerializedName("UidTutor")
    @Expose
    private String uidTutor;
    @SerializedName("SentDate")
    @Expose
    private String sentDate;
    @SerializedName("NameCourse")
    @Expose
    private String nameCourse;
    @SerializedName("IdSubject")
    @Expose
    private String idSubject;
    @SerializedName("Tuition")
    @Expose
    private String tuition;
    @SerializedName("Address")
    @Expose
    private String address;
    @SerializedName("NumberOfSession")
    @Expose
    private String numberOfSession;
    @SerializedName("TimePerSession")
    @Expose
    private String timePerSession;
    @SerializedName("StudentNumber")
    @Expose
    private String studentNumber;
    @SerializedName("Schedule")
    @Expose
    private String schedule;
    @SerializedName("Description")
    @Expose
    private String description;
    @SerializedName("TutorConfirm")
    @Expose
    private String tutorConfirm;
    @SerializedName("Comment")
    @Expose
    private String comment;

    public String getIdRequisitionCourse() {
        return idRequisitionCourse;
    }

    public void setIdRequisitionCourse(String idRequisitionCourse) {
        this.idRequisitionCourse = idRequisitionCourse;
    }

    public String getUidStudent() {
        return uidStudent;
    }

    public void setUidStudent(String uidStudent) {
        this.uidStudent = uidStudent;
    }

    public String getUidTutor() {
        return uidTutor;
    }

    public void setUidTutor(String uidTutor) {
        this.uidTutor = uidTutor;
    }

    public String getSentDate() {
        return sentDate;
    }

    public void setSentDate(String sentDate) {
        this.sentDate = sentDate;
    }

    public String getNameCourse() {
        return nameCourse;
    }

    public void setNameCourse(String nameCourse) {
        this.nameCourse = nameCourse;
    }

    public String getIdSubject() {
        return idSubject;
    }

    public void setIdSubject(String idSubject) {
        this.idSubject = idSubject;
    }

    public String getTuition() {
        return tuition;
    }

    public void setTuition(String tuition) {
        this.tuition = tuition;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNumberOfSession() {
        return numberOfSession;
    }

    public void setNumberOfSession(String numberOfSession) {
        this.numberOfSession = numberOfSession;
    }

    public String getTimePerSession() {
        return timePerSession;
    }

    public void setTimePerSession(String timePerSession) {
        this.timePerSession = timePerSession;
    }

    public String getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTutorConfirm() {
        return tutorConfirm;
    }

    public void setTutorConfirm(String tutorConfirm) {
        this.tutorConfirm = tutorConfirm;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
