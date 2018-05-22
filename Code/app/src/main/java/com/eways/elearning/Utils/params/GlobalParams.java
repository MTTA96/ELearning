package com.eways.elearning.Utils.params;

import android.app.Application;

import com.google.gson.Gson;

/**
 * Created by ADMIN on 2/10/2018.
 */

public class GlobalParams extends Application {

    private static GlobalParams instance;
    private Gson gson;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        gson = new Gson();
    }

    /* GET - SET */

    public static GlobalParams getInstance() {
        return instance;
    }

    public Gson getGSon() {
        return gson;
    }

    public int get_resId_by_name(String name, String type){
        return instance.getResources().getIdentifier(name, type, instance.getPackageName());

    }
}
