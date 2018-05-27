package com.eways.elearning.Utils;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.provider.MediaStore;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.eways.elearning.Utils.Handler.ImageHandler;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.DialogPlusBuilder;
import com.orhanobut.dialogplus.GridHolder;
import com.orhanobut.dialogplus.OnItemClickListener;

/**
 * Created by ADMIN on 5/26/2018.
 */

public class DialogPlusHandler {
    Activity activity;
    ArrayAdapter arrayAdapter;
    DialogPlus dialogPlus;

    ImageHandler imageHandler;

    public static final int REQUEST_CODE_CAMERA=1;
    public static final int REQUEST_CODE_GALLERY=2;

    public DialogPlusHandler(Activity activity, ArrayAdapter arrayAdapter) {
        this.activity = activity;
        this.arrayAdapter = arrayAdapter;

        imageHandler = new ImageHandler(activity);
    }

    public void ShowDiglogPlus(){
        DialogPlusBuilder dialogPlusBuilder = DialogPlus.newDialog(activity);
        dialogPlusBuilder.setContentHolder(new GridHolder(2));
        dialogPlusBuilder.setContentHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        dialogPlusBuilder.setAdapter(arrayAdapter);

        dialogPlusBuilder.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(DialogPlus dialog, Object item, View view, int position) {
                switch (position){
                    case 0:

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            activity.requestPermissions(new String[]{Manifest.permission.CAMERA},REQUEST_CODE_CAMERA);
                        }
                        break;
                    case 1:
                        Intent intent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                        activity.startActivityForResult(intent,REQUEST_CODE_GALLERY);
                }
            }
        });

        dialogPlus = dialogPlusBuilder.create();
        dialogPlus.show();
    }

    public void dissMissDialog(){
        dialogPlus.dismiss();
    }

}
