package com.eways.elearning.Network;


import com.eways.elearning.Network.Services.ELearningServicesImp;
import com.eways.elearning.Network.Services.UserServicesImp;

/**
 * Created by zzzzz on 3/13/2018.
 */

public class ApiUtils {
    public static final String BASE_URL = ServerUrl.ServerAPIURL;

    /** ETutor services */
    public static ELearningServicesImp eLearningServices() {
        return RetrofitClient.getClient(BASE_URL).create(ELearningServicesImp.class);
    }

    /** User services */
    public static UserServicesImp userServices() {
        return RetrofitClient.getClient(BASE_URL).create(UserServicesImp.class);
    }

}
