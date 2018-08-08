package com.eways.elearning.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ADMIN on 7/27/2018.
 */

public class CreateCourse {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("student_id")
    @Expose
    private String studentId;
    @SerializedName("tutor_id")
    @Expose
    private String tutorId;
    @SerializedName("sent_date")
    @Expose
    private String sentDate;
    @SerializedName("subject_id")
    @Expose
    private String subjectId;
    @SerializedName("tuition")
    @Expose
    private String tuition;
    @SerializedName("total_session")
    @Expose
    private String totalSession;
    @SerializedName("time_session")
    @Expose
    private String timeSession;
    @SerializedName("total_student")
    @Expose
    private String totalStudent;
    @SerializedName("schedule")
    @Expose
    private String schedule;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("comment")
    @Expose
    private String comment;

    public CreateCourse(String id, String studentId, String tutorId, String sentDate, String subjectId, String tuition, String totalSession, String timeSession, String totalStudent, String schedule, String description, String status, String comment) {
        this.id = id;
        this.studentId = studentId;
        this.tutorId = tutorId;
        this.sentDate = sentDate;
        this.subjectId = subjectId;
        this.tuition = tuition;
        this.totalSession = totalSession;
        this.timeSession = timeSession;
        this.totalStudent = totalStudent;
        this.schedule = schedule;
        this.description = description;
        this.status = status;
        this.comment = comment;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getTutorId() {
        return tutorId;
    }

    public void setTutorId(String tutorId) {
        this.tutorId = tutorId;
    }

    public String getSentDate() {
        return sentDate;
    }

    public void setSentDate(String sentDate) {
        this.sentDate = sentDate;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getTuition() {
        return tuition;
    }

    public void setTuition(String tuition) {
        this.tuition = tuition;
    }

    public String getTotalSession() {
        return totalSession;
    }

    public void setTotalSession(String totalSession) {
        this.totalSession = totalSession;
    }

    public String getTimeSession() {
        return timeSession;
    }

    public void setTimeSession(String timeSession) {
        this.timeSession = timeSession;
    }

    public String getTotalStudent() {
        return totalStudent;
    }

    public void setTotalStudent(String totalStudent) {
        this.totalStudent = totalStudent;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
