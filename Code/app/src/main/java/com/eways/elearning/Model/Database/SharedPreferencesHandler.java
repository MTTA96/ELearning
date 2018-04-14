package com.eways.elearning.Model.Database;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by zzzzz on 10/8/2017.
 */

public class SharedPreferencesHandler {
    private SharedPreferences sharedPreferences;
    private  SharedPreferences.Editor editor;
    private Context context;

    private final String KEY_FIRST_RUN = "first_run";
    private final String KEY_UID="Uid";
    private final String KEY_EMAIL="email";
    private final String KEY_FIRSTNAME="firstname";
    private final String KEY_LASTNAME="lastname";
    private final String KEY_AVATAR ="avatar";
    private final String KEY_DA_DANGNHAP="dadangnhap";

    public SharedPreferencesHandler(Context context,String tenFile) {
        this.context = context;

        sharedPreferences = context.getSharedPreferences(tenFile,Context.MODE_PRIVATE);
        editor=sharedPreferences.edit();
    }

    //Constructor for the first time using this app
    public SharedPreferencesHandler(){
        setUID("");
        setEmail("");
        setLastName("");
        setFirstName("");
        setAvatar("");
        setAvatar("");
        setDaDangNhap(false);
    }

    public boolean checkFirstRun(){
        return sharedPreferences.contains(KEY_FIRST_RUN);
    }
    public void setFirstRun(){
        editor.putBoolean(KEY_FIRST_RUN, true);
        editor.commit();
    }

    public String getUID(){
        return sharedPreferences.getString(KEY_UID,null);
    }
    public void setUID(String Id){
        editor.putString(KEY_UID,Id);
        editor.commit();
    }

    //Key tên tài khoản
    public String getFirstName(){
        return sharedPreferences.getString(KEY_FIRSTNAME, null);
    }

    public void setFirstName(String firstName){
        editor.putString(KEY_FIRSTNAME, firstName);
        editor.commit();
    }

    public String getLastName(){
        return sharedPreferences.getString(KEY_LASTNAME, null);
    }

    public void setLastName(String lastName){
        editor.putString(KEY_LASTNAME, lastName);
        editor.commit();
    }

    public String getEmail(){
        return sharedPreferences.getString(KEY_EMAIL, null);
    }

    public void setEmail(String email){
        editor.putString(KEY_EMAIL, email);
        editor.commit();
    }

    public String getAvatar() {
        return KEY_AVATAR;
    }

    public void setAvatar(String avatar) {
        editor.putString(KEY_AVATAR, avatar).commit();
    }

    //Key đăng nhập
    public boolean getDaDangNhap(){
        return sharedPreferences.getBoolean(KEY_DA_DANGNHAP, false);
    }

    public void setDaDangNhap(boolean status){
        editor.putBoolean(KEY_DA_DANGNHAP, status);
        editor.commit();
    }


    public void DangNhapThanhCong(String id, String email, String ho, String ten, String avatar, boolean isLoggedIn){
        setUID(id);
        setEmail(email);
        setLastName(ho);
        setFirstName(ten);
        setAvatar(avatar);
        setDaDangNhap(isLoggedIn);
    }

    public void DangXuat(){
        setUID("");
        setEmail("");
        setLastName("");
        setFirstName("");
        setAvatar("");
        setAvatar("");
        setDaDangNhap(false);
    }
}
