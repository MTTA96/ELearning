package com.eways.elearning.Model.Account;

import android.os.Bundle;
import android.util.Log;

import com.eways.elearning.Interfaces.DataCallBack;
import com.eways.elearning.Interfaces.DataCallback.Subject.FavSubjectWithCoursesCallBack;
import com.eways.elearning.Interfaces.DataCallback.User.SendRequestCallback;
import com.eways.elearning.Interfaces.DataCallback.User.TopTutorsCallBack;
import com.eways.elearning.Interfaces.DataCallback.User.UserCallBack;
import com.eways.elearning.Model.Request;
import com.eways.elearning.Model.Subject.FavoriteSubjectWithCourses;
import com.eways.elearning.Network.ApiUtils;
import com.eways.elearning.Network.Responses.BaseResponse;
import com.eways.elearning.Network.Responses.User.SignInResponse;
import com.eways.elearning.Network.Responses.User.UserBaseResponse;
import com.eways.elearning.Network.Responses.User.UserFavoriteSubjectResponse;
import com.eways.elearning.Network.Responses.User.UserListResponse;
import com.eways.elearning.Network.Services.ELearningServicesImp;
import com.eways.elearning.Network.Services.UserServicesImp;
import com.eways.elearning.R;
import com.eways.elearning.Utils.SharedPreferences.SharedPrefSupportKeys;
import com.eways.elearning.Utils.SupportKeys;
import com.eways.elearning.Utils.params.GlobalParams;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ADMIN on 3/25/2018.
 */

public class User {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("avatar")
    @Expose
    private String avatar;
    @SerializedName("first_name")
    @Expose
    private String firstName;
    @SerializedName("last_name")
    @Expose
    private String lastName;
    @SerializedName("sex")
    @Expose
    private String sex;
    @SerializedName("birthday")
    @Expose
    private String birthday;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("skype")
    @Expose
    private String skype;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("degree")
    @Expose
    private String degree;
    @SerializedName("career")
    @Expose
    private String career;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("verification")
    @Expose
    private String verification;
    @SerializedName("authorization")
    @Expose
    private String authorization;
    @SerializedName("date_registered")
    @Expose
    private String dateRegistered;

    public User() {
        this.password = "";
        this.avatar = "";
        this.firstName = "";
        this.lastName = "";
        this.sex = "";
        this.birthday = "";
        this.email = "";
        this.phone = "";
        this.skype = "";
        this.address = "";
        this.degree = "";
        this.career = "";
        this.status = "";
        this.verification = "";
        this.authorization = "";
        this.dateRegistered = "";
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getSkype() {
        return skype;
    }

    public void setSkype(String skype) {
        this.skype = skype;
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

    public String getDateRegistered() {
        return dateRegistered;
    }

    public void setDateRegistered(String dateRegistered) {
        this.dateRegistered = dateRegistered;
    }

    /** MARK: - METHODS */

    public static void getUserInfo(String uId, final UserCallBack userCallBack) {

        UserServicesImp userServices = ApiUtils.userServices();
        userServices.getUserDetails(uId).enqueue(new Callback<UserBaseResponse>() {
            @Override
            public void onResponse(Call<UserBaseResponse> call, Response<UserBaseResponse> response) {
                Log.d("User Details", call.request().toString());
                // handle errors
                Log.d("User Details:", call.request().toString());
                if (!response.isSuccessful()) {
                    Log.d("User Details:", "connect failed");
                    userCallBack.userCallBack(SupportKeys.FAILED_CODE, null);
                    return;
                }

                // Sign up success
                userCallBack.userCallBack(SupportKeys.SUCCESS_CODE, response.body().getTeacher());
            }

            @Override
            public void onFailure(Call<UserBaseResponse> call, Throwable t) {
                Log.d("User Details", call.request().toString() + "Error msg: " + t.getLocalizedMessage());
                userCallBack.userCallBack(SupportKeys.FAILED_CODE, null);
            }
        });

    }

    /** [START - Authentication] */
    /** Sign up*/
    public static void signUp(String jsonData, final DataCallBack dataCallBack) {
        UserServicesImp userServices = ApiUtils.userServices();
        userServices.signUp(jsonData).enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                // handle errors
                Log.d("signUpFirebase:", call.request().toString());
                if (!response.isSuccessful()) {
                    Log.d("signUp:", "connect failed");
                    dataCallBack.dataCallBack(SupportKeys.FAILED_CODE, null);
                    return;
                }

                // handle result
                if (Integer.parseInt(response.body().getStatus()) == 0) {
                    Log.d("signUp:", "sign up failed");
                    dataCallBack.dataCallBack(SupportKeys.FAILED_CODE, null);
                    return;
                }

                // Sign up success
                dataCallBack.dataCallBack(SupportKeys.SUCCESS_CODE, null);
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                Log.d("signUp:", t.getLocalizedMessage());
                dataCallBack.dataCallBack(SupportKeys.FAILED_CODE, null);
            }
        });
    }

    /** Sign in */
    public static void signIn(String userName, String password, final DataCallBack dataCallBack) {
        UserServicesImp userServicesImp = ApiUtils.userServices();
        userServicesImp.signIn(userName, password).enqueue(new Callback<SignInResponse>() {
            @Override
            public void onResponse(Call<SignInResponse> call, Response<SignInResponse> response) {
                Log.d("Sign in", call.request().toString());

                // handle error
                if (!response.isSuccessful()) {
                    Log.d("Sign in", "Connect failed");
                    dataCallBack.dataCallBack(SupportKeys.FAILED_CODE, null);
                    return;
                }

                // Connect success
                Bundle bundle = new Bundle();

                int status = Integer.parseInt(response.body().getStatus());
                if (status == 0) {
                        bundle.putString("uID", response.body().getUId());
                }

                bundle.putString(null, response.body().getStatus());
                dataCallBack.dataCallBack(SupportKeys.SUCCESS_CODE, bundle);

            }

            @Override
            public void onFailure(Call<SignInResponse> call, Throwable t) {
                Log.d("Sign in", "Connect failed");
                dataCallBack.dataCallBack(SupportKeys.FAILED_CODE, null);
            }
        });
    }

    /** Check phone's status to know if it existing in database when signing up */
    public static void checkPhoneNumber(String phoneNumber, final DataCallBack dataCallBack) {
        UserServicesImp userServicesImp = ApiUtils.userServices();
        userServicesImp.checkPhoneNumber(phoneNumber).enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {

                Log.d("CheckPhone: request -", call.request().toString());

                // handle error
                if (!response.isSuccessful()) {
                    Log.d("CheckPhoneNumberModel:", "connect failed");
                    dataCallBack.dataCallBack(SupportKeys.FAILED_CODE, null);
                    return;
                }

                // Prepare data
                Bundle bundle = new Bundle();
                bundle.putInt(null, Integer.parseInt(response.body().getStatus()));
                dataCallBack.dataCallBack(SupportKeys.SUCCESS_CODE, bundle);
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                Log.d("CheckPhone: request -", call.request().toString());
                Log.d("CheckPhoneNumberModel:", t.getLocalizedMessage());
                dataCallBack.dataCallBack(SupportKeys.FAILED_CODE, null);
            }
        });
    }

    /** [END - Authentication] */

    /** Get top tutors */
    public static void getTopTutors(final TopTutorsCallBack topTutorsCallBack) {
        ELearningServicesImp eLearningServicesImp = ApiUtils.eLearningServices();
        eLearningServicesImp.getTopTutors().enqueue(new Callback<UserListResponse>() {
            @Override
            public void onResponse(Call<UserListResponse> call, Response<UserListResponse> response) {
                // handle error
                if (!response.isSuccessful()) {
                    Log.d("Get top tutors model:", "connect failed");
                    topTutorsCallBack.topTutorCallBack(SupportKeys.FAILED_CODE, null);
                    return;
                }

                // Prepare data
                topTutorsCallBack.topTutorCallBack(SupportKeys.SUCCESS_CODE, response.body().getUserList());
            }

            @Override
            public void onFailure(Call<UserListResponse> call, Throwable t) {
                Log.d("Get top tutors model:", t.getLocalizedMessage());
                topTutorsCallBack.topTutorCallBack(SupportKeys.FAILED_CODE, null);
            }
        });
    }

    /** Get user's favorite subjects with courses */
    public static void getUserFavoriteSubjectsWithCourses(String uId, final FavSubjectWithCoursesCallBack favoriteSubjectWithCourseList) {
//        UserServicesImp userServicesImp = ApiUtils.userServices();
//        userServicesImp.getUserFavoriteSubjects(uId).enqueue(new Callback<UserFavoriteSubjectResponse>() {
//            @Override
//            public void onResponse(Call<UserFavoriteSubjectResponse> call, Response<UserFavoriteSubjectResponse> response) {
//                // handle error
//                if (!response.isSuccessful()) {
//                    Log.d("GetUserFavModel:", "connect failed");
//                    favoriteSubjectWithCourseList.favSubjectsCourseCallBack(SupportKeys.FAILED_CODE, null);
//                    return;
//                }
//
//                // Prepare data
//                favoriteSubjectWithCourseList.favSubjectsCourseCallBack(SupportKeys.SUCCESS_CODE, response.body().getFavSubjectWithCourseList());
//            }
//
//            @Override
//            public void onFailure(Call<UserFavoriteSubjectResponse> call, Throwable t) {
//                Log.d("GetUserFavModel:", t.getLocalizedMessage());
//                favoriteSubjectWithCourseList.favSubjectsCourseCallBack(SupportKeys.FAILED_CODE, null);
//            }
//        });
    }

    /** Update user favorite */
    public static void addFavorite(String uID, ArrayList<String> listFavorite, final FavSubjectWithCoursesCallBack favSubjectWithCoursesCallBack) {
//        UserServicesImp userServicesImp = ApiUtils.userServices();
//        userServicesImp.addUserFavoriteUrl(uID, listFavorite).enqueue(new Callback<BaseResponse>() {
//            @Override
//            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
//                // Handle error
//                if (!response.isSuccessful()) {
//                    Log.d("Update favorite to sv:", "Update failed!");
//                    favSubjectWithCoursesCallBack.favSubjectsCourseCallBack(SupportKeys.FAILED_CODE, null);
//                    return;
//                }
//
//                // Prepare data
//                if (Integer.parseInt(response.body().getStatus()) == 1) {
//                    Log.d("Update favorite to sv:", "Update success!");
//                    favSubjectWithCoursesCallBack.favSubjectsCourseCallBack(SupportKeys.SUCCESS_CODE, null);
//                }
//                else {
//                    Log.d("Update favorite to sv:", "Update failed!");
//                    favSubjectWithCoursesCallBack.favSubjectsCourseCallBack(SupportKeys.FAILED_CODE, null);
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<BaseResponse> call, Throwable t) {
//                Log.d("Update favorite to sv:", "Update failed!");
//                favSubjectWithCoursesCallBack.favSubjectsCourseCallBack(SupportKeys.FAILED_CODE, null);
//            }
//
//        });
    }

    /** Send request to tutor */

    public static void sendRequest(String jsonRequest, final SendRequestCallback requestCallBack) {

        UserServicesImp userServicesImp = ApiUtils.userServices();
        userServicesImp.sendRequest(jsonRequest).enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                // Handle error
                if (!response.isSuccessful()) {
                    Log.d("Send request to sv:", "Update failed!");
                    requestCallBack.sendRequestCallback(SupportKeys.FAILED_CODE, null);
                    return;
                }

                // Prepare data
                int status = Integer.parseInt(response.body().getStatus());

                if (status == 1)
                    requestCallBack.sendRequestCallback(SupportKeys.SUCCESS_CODE, null);
                else
                    requestCallBack.sendRequestCallback(SupportKeys.FAILED_CODE, null);
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                Log.d("Send request to sv:", "Update failed!");
                requestCallBack.sendRequestCallback(SupportKeys.FAILED_CODE, null);
            }

        });

    }

}

