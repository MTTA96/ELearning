package com.eways.elearning.View.Dialog;

import android.app.ProgressDialog;
import android.content.Context;

import com.eways.elearning.Handler.FragmentHandler;
import com.eways.elearning.R;

/**
 * Created by zzzzz on 12/29/2017.
 */

public class LoadingDialog extends ProgressDialog{
    private static LoadingDialog loadingDialog;

    private LoadingDialog(Context context) {
        super(context);
    }

    public static LoadingDialog getInstance(Context context){
        if (loadingDialog == null){
            loadingDialog = new LoadingDialog(context);
            loadingDialog.setMessage(context.getString(R.string.msg_loading));
            loadingDialog.setCanceledOnTouchOutside(false);
        }
        return loadingDialog;
    }

    public static void dismissDialog(){
        loadingDialog.dismiss();
    }
}
