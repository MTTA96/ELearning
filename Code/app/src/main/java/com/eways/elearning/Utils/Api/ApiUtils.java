package com.eways.elearning.Utils.Api;


import com.eways.elearning.Network.RetrofitClient;
import com.eways.elearning.Network.UserServicesImp;
import com.eways.elearning.Utils.ServerUrl;

/**
 * Created by zzzzz on 3/13/2018.
 */

public class ApiUtils {
    public static final String BASE_URL = ServerUrl.ServerAPIURL;

    public static UserServicesImp userServices() {
        return RetrofitClient.getClient(BASE_URL).create(UserServicesImp.class);
    }

    public static CourseServicesImp courseServices() {
        return RetrofitClient.getClient(BASE_URL).create(CourseServicesImp.class);
    }
}
