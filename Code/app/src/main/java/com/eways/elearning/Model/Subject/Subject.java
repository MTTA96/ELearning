package com.eways.elearning.Model.Subject;

import android.util.Log;

import com.eways.elearning.Interfaces.DataCallback.Subject.TrendingSubjectCallBack;
import com.eways.elearning.Network.ApiUtils;
import com.eways.elearning.Network.Responses.User.TrendingSubjectResponse;
import com.eways.elearning.Network.Services.ELearningServicesImp;
import com.eways.elearning.Network.Subject.SubjectListCallBack;
import com.eways.elearning.Utils.SupportKeys;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by zzzzz on 5/31/2018.
 */

public class Subject {

    @SerializedName("SubjectName")
    @Expose
    private String subjectName;
    @SerializedName("IdSubject")
    @Expose
    private String idSubject;
    @SerializedName("Img")
    @Expose
    private String img;

    public Subject(String subjectName, String idSubject, String img) {
        this.subjectName = subjectName;
        this.idSubject = idSubject;
        this.img = img;
    }

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

    /** METHODS */

    /** Get subject list */
    public static void getSubjectList(final SubjectListCallBack subjectListCallBack) {

        ELearningServicesImp eLearningServicesImp = ApiUtils.eLearningServices();
        eLearningServicesImp.getSubjectList().enqueue(new Callback<ArrayList<Subject>>() {
            @Override
            public void onResponse(Call<ArrayList<Subject>> call, Response<ArrayList<Subject>> response) {
                // handle error
                if (!response.isSuccessful()) {
                    Log.d("subjectListModel:", "connect failed");
                    subjectListCallBack.subjectListCallBack(SupportKeys.FAILED_CODE, null);
                    return;
                }

                // Prepare data
                subjectListCallBack.subjectListCallBack(SupportKeys.SUCCESS_CODE, response.body());
            }

            @Override
            public void onFailure(Call<ArrayList<Subject>> call, Throwable t) {
                Log.d("subjectListModel:", t.getLocalizedMessage());
                subjectListCallBack.subjectListCallBack(SupportKeys.FAILED_CODE, null);
            }
        });

    }

    /** Get trending subjects */
    public static void getTrendingSubjects(final TrendingSubjectCallBack trendingSubjectCallBack) {
        ELearningServicesImp eLearningServicesImp = ApiUtils.eLearningServices();
        eLearningServicesImp.getTrendingSubjects().enqueue(new Callback<TrendingSubjectResponse>() {
            @Override
            public void onResponse(Call<TrendingSubjectResponse> call, Response<TrendingSubjectResponse> response) {
                // handle error
                if (!response.isSuccessful()) {
                    Log.d("CheckPhoneNumberModel:", "connect failed");
                    trendingSubjectCallBack.trendingSubjectsCallBack(SupportKeys.FAILED_CODE, null);
                    return;
                }

                // Prepare data
                trendingSubjectCallBack.trendingSubjectsCallBack(SupportKeys.SUCCESS_CODE, response.body().getTrendingList());
            }

            @Override
            public void onFailure(Call<TrendingSubjectResponse> call, Throwable t) {
                Log.d("CheckPhoneNumberModel:", t.getLocalizedMessage());
                trendingSubjectCallBack.trendingSubjectsCallBack(SupportKeys.FAILED_CODE, null);
            }
        });
    }

}
