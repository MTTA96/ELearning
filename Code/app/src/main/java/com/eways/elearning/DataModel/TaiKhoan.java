package com.eways.elearning.DataModel;

/**
 * Created by zzzzz on 8/28/2017.
 */

public class TaiKhoan {
    String Email;
    String Password;

    public TaiKhoan(String email, String password) {
        Email = email;
        Password = password;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}
