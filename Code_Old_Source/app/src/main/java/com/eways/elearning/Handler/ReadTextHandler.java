package com.eways.elearning.Handler;

import android.app.Activity;
import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by ADMIN on 4/21/2018.
 */

public class ReadTextHandler {
    Activity mActivity;
    public ReadTextHandler(Activity mActivity) {
        this.mActivity = mActivity;
    }

    public String readTxt(String fileName){
        String mystring = null;
        try {
            InputStream in= mActivity.getAssets().open(fileName);
            int size=in.available();
            byte[] buffer=new byte[size];
            in.read(buffer);
            mystring = new String(buffer);
            in.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return mystring;
    }

}
