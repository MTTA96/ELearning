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

    private final String KEY_ID="id";
    private final String KEY_EMAIL="email";
    private final String KEY_TEN_TAIKHOAN="tentaikhoan";
    private final String KEY_LOAI_TAIKHOAN="loaitaikhoan";
    private final String KEY_DA_DANGNHAP="dadangnhap";
    private final String KEY_AVARTA="avarta";

    public SharedPreferencesHandler(Context context,String tenFile) {
        this.context = context;

        sharedPreferences = context.getSharedPreferences(tenFile,Context.MODE_PRIVATE);
        editor=sharedPreferences.edit();
    }

    public String getID(){
        return sharedPreferences.getString(KEY_ID,null);
    }
    public void setID(String Id){
        editor.putString(KEY_ID,Id);
        editor.commit();
    }
    //Key tên tài khoản
    public String getTenTaiKhoan(){
        return sharedPreferences.getString(KEY_TEN_TAIKHOAN, null);
    }

    public void setTenTaiKhoan(String ten_tai_khoan){
        editor.putString(KEY_TEN_TAIKHOAN, ten_tai_khoan);
        editor.commit();
    }
    public String getEmail(){
        return sharedPreferences.getString(KEY_EMAIL, null);
    }

    public void setEmail(String email){
        editor.putString(KEY_EMAIL, email);
        editor.commit();
    }
    //Key đăng nhập
    public boolean getDaDangNhap(){
        return sharedPreferences.getBoolean(KEY_DA_DANGNHAP, false);
    }

    public void setDaDangNhap(boolean status){
        editor.putBoolean(KEY_DA_DANGNHAP, status);
        editor.commit();
    }
    //Key loại tài khoản
    public String getLoaiTaiKhoan(){
        return sharedPreferences.getString(KEY_LOAI_TAIKHOAN, "");
    }

    public void setLoaiTaiKhoan(String loaiTK){
        editor.putString(KEY_LOAI_TAIKHOAN, loaiTK);
        editor.commit();
    }
    //Key avarta
    public String getAvarta(){
        return sharedPreferences.getString(KEY_AVARTA, "");
    }

    public void setAvarta(String avarta){
        editor.putString(KEY_AVARTA, avarta);
        editor.commit();
    }
}
