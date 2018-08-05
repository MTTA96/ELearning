package com.eways.elearning.Network.Responses.User;

/**
 * Created by ADMIN on 3/18/2018.
 */

import com.eways.elearning.Model.Account.User;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class UserListResponse {
    @SerializedName("response")
    @Expose
    private ArrayList<User> userList = null;

    public ArrayList<User> getUserList() {
        return userList;
    }

    public void setUserList(ArrayList<User> userList) {
        this.userList = userList;
    }

}

