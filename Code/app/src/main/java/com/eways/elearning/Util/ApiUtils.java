package com.eways.elearning.Util;

import com.eways.elearning.Handler.Network.RetrofitClient;
import com.eways.elearning.Network.UserServicesImp;

/**
 * Created by zzzzz on 3/13/2018.
 */

public class ApiUtils {
    public static final String BASE_URL = ServerUrl.ServerUrl;

    public static UserServicesImp loginService() {
        return RetrofitClient.getClient(BASE_URL).create(UserServicesImp.class);
    }
}
