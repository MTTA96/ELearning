package com.eways.elearning.Model;

/**
 * Created by ADMIN on 5/20/2018.
 */

import android.os.Bundle;
import android.util.Log;

import com.eways.elearning.Interfaces.DataCallBack;
import com.eways.elearning.Network.ListResponse;
import com.eways.elearning.Utils.Api.ApiUtils;
import com.eways.elearning.Utils.Api.CourseServicesImp;
import com.eways.elearning.Utils.SupportKey;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Course {

    @SerializedName("IdCourse")
    @Expose
    private String idCourse;
    @SerializedName("Uid")
    @Expose
    private String uid;
    @SerializedName("CourseType")
    @Expose
    private String courseType;
    @SerializedName("CreatedDate")
    @Expose
    private String createdDate;
    @SerializedName("Status")
    @Expose
    private String status;
    @SerializedName("IdSubject")
    @Expose
    private String idSubject;
    @SerializedName("Tuition")
    @Expose
    private String tuition;
    @SerializedName("Address")
    @Expose
    private String address;
    @SerializedName("NumberOfSessions")
    @Expose
    private String numberOfSessions;
    @SerializedName("TimePerSession")
    @Expose
    private String timePerSession;
    @SerializedName("TimeStart")
    @Expose
    private String timeStart;
    @SerializedName("TimeEnd")
    @Expose
    private String timeEnd;
    @SerializedName("StudentNumber")
    @Expose
    private String studentNumber;
    @SerializedName("Schedule")
    @Expose
    private String schedule;
    @SerializedName("Description")
    @Expose
    private String description;
    @SerializedName("LastName")
    @Expose
    private String lastName;
    @SerializedName("FirstName")
    @Expose
    private String firstName;
    @SerializedName("SubjectName")
    @Expose
    private String subjectName;

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

    public String getCourseType() {
        return courseType;
    }

    public void setCourseType(String courseType) {
        this.courseType = courseType;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getNumberOfSessions() {
        return numberOfSessions;
    }

    public void setNumberOfSessions(String numberOfSessions) {
        this.numberOfSessions = numberOfSessions;
    }

    public String getTimePerSession() {
        return timePerSession;
    }

    public void setTimePerSession(String timePerSession) {
        this.timePerSession = timePerSession;
    }

    public String getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(String timeStart) {
        this.timeStart = timeStart;
    }

    public String getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(String timeEnd) {
        this.timeEnd = timeEnd;
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

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    /** METHODS */
    /** Search course */
    public static void searchCourses(String keyWord, String filters, final DataCallBack dataCallBack) {
        CourseServicesImp courseServicesImp = ApiUtils.courseServices();
        String condition = "{\"TutorName\":\"" + keyWord + "\",\"SubjectName\":\"" + keyWord + "\",\"CourseType\":\""+ 0 +"\"}";
        courseServicesImp.getCourseSearch(condition).enqueue(new Callback<ListResponse>() {
            @Override
            public void onResponse(Call<ListResponse> call, Response<ListResponse> response) {
                Log.d("searchCourses:", call.request().toString());
                // Handle errors
                if (!response.isSuccessful()) {
                    Log.d("searchCourse:", " Connect Failed");
                    dataCallBack.dataCallBack(SupportKey.FAILED_CODE, null);
                    return;
                }

                // Get data success
                // Prepare data
                Bundle bundle = new Bundle();
                bundle.putSerializable(null, (Serializable) response.body().getListC());
                dataCallBack.dataCallBack(SupportKey.SUCCESS_CODE, bundle);
            }

            @Override
            public void onFailure(Call<ListResponse> call, Throwable t) {
                Log.d("searchCourse:", "Search failed");
                dataCallBack.dataCallBack(SupportKey.FAILED_CODE, null);
            }
        });
    }
}