package com.eways.elearning.Model.TaiKhoan;

/**
 * Created by zzzzz on 3/18/2018.
 */
import com.eways.elearning.Handler.Other.DataCallBack;
import com.eways.elearning.Network.BaseResponse;
import com.eways.elearning.Network.UserServicesImp;
import com.eways.elearning.Util.ApiUtils;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class User {

    @SerializedName("Uid")
    @Expose
    private String uid;
    @SerializedName("Avatar")
    @Expose
    private String avatar;
    @SerializedName("FirstName")
    @Expose
    private String firstName;
    @SerializedName("LastName")
    @Expose
    private String lastName;
    @SerializedName("Sex")
    @Expose
    private String sex;
    @SerializedName("Birthday")
    @Expose
    private String birthday;
    @SerializedName("Email")
    @Expose
    private String email;
    @SerializedName("Phone")
    @Expose
    private String phone;
    @SerializedName("Skype")
    @Expose
    private String skype;
    @SerializedName("Address")
    @Expose
    private String address;
    @SerializedName("Degree")
    @Expose
    private String degree;
    @SerializedName("Career")
    @Expose
    private String career;
    @SerializedName("Status")
    @Expose
    private String status;
    @SerializedName("Verification")
    @Expose
    private String verification;
    @SerializedName("Authorization")
    @Expose
    private String authorization;
    @SerializedName("DateRegisted")
    @Expose
    private String dateRegisted;

    public String getSkype() {
        return skype;
    }

    public void setSkype(String skype) {
        this.skype = skype;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getCareer() {
        return career;
    }

    public void setCareer(String career) {
        this.career = career;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getVerification() {
        return verification;
    }

    public void setVerification(String verification) {
        this.verification = verification;
    }

    public String getAuthorization() {
        return authorization;
    }

    public void setAuthorization(String authorization) {
        this.authorization = authorization;
    }

    public String getDateRegisted() {
        return dateRegisted;
    }

    public void setDateRegisted(String dateRegisted) {
        this.dateRegisted = dateRegisted;
    }

    /** Methods*/

    public static void signUp(String jsonData, final DataCallBack dataCallBack) {
        UserServicesImp userServices = ApiUtils.userServices();
        userServices.signUp(jsonData).enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if (response.body().getErrorCode() == 200)
                    if (response.body().getStatus() == "Success") {
                        dataCallBack.dataCallBack("Success", null);
                        return;
                    }
                dataCallBack.dataCallBack("Failed", null);
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {

            }
        });
    }
}

