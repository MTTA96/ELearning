package com.eways.elearning.Handler;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by ADMIN on 3/18/2018.
 */

public class SharedPreferencesHandler {

    Context context;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    private final String KEY_ID = "id";
    private final String KEY_ACCOUNT_GMAIL = "account_gmail";
    private final String KEY_PHONE_NUMBER = "phone_number";

    public SharedPreferencesHandler(Context context, String fileName) {
        this.context = context;

        sharedPreferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    /*--- GET - SET ---*/

    public void setId(String id){
        editor.putString(KEY_ID, id);
        editor.commit();
    }

    public String getId(){
        return sharedPreferences.getString(KEY_ID, null);
    }

    public void setPhoneNumber(String phoneNumber) {
        editor.putString(KEY_PHONE_NUMBER, phoneNumber);
        editor.commit();
    }

    public String getPhoneNumber() {
        return sharedPreferences.getString(KEY_PHONE_NUMBER, null);
    }

}
