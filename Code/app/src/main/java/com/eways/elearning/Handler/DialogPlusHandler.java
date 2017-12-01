package com.eways.elearning.Handler;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.eways.elearning.DataModel.LayHinhModel;
import com.eways.elearning.Manifest;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.DialogPlusBuilder;
import com.orhanobut.dialogplus.GridHolder;
import com.orhanobut.dialogplus.OnItemClickListener;

import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

/**
 * Created by ADMIN on 11/30/2017.
 */

public class DialogPlusHandler {
    Activity activity;
    ArrayAdapter arrayAdapter;

    int requestCode;
    String[] permissions;
    int[] grantResults;

    int resultCode;
    Intent data;
    ImageView imageView;

    private int REQUEST_CODE_CAMERA=1;
    private int REQUEST_CODE_GALLERY=2;

    public DialogPlusHandler(Activity activity, ArrayAdapter arrayAdapter) {
        this.activity = activity;
        this.arrayAdapter = arrayAdapter;
    }

    public void ShowDialogChonHinh(){
        DialogPlusBuilder dialog = DialogPlus.newDialog(activity);
        dialog.setContentHolder(new GridHolder(2));
        dialog.setContentHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setAdapter(arrayAdapter);
        dialog.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(DialogPlus dialog, Object item, View view, int position) {
                if (position==0)
                    ActivityCompat.requestPermissions(activity,new String[]{android.Manifest.permission.CAMERA},REQUEST_CODE_CAMERA);
            }
        });
        DialogPlus dialogPlus=dialog.create();
        dialogPlus.show();
    }

//    @Override
//    public void onItemClick(DialogPlus dialog, Object item, View view, int position) {
//        if (position==0){
//            ActivityCompat.requestPermissions(activity,new String[]{android.Manifest.permission.CAMERA},REQUEST_CODE_CAMERA);
//        }
//    }

    public void onRequestPermissionResult(int requestCode, String[] permissions, int[] grantResults){
        if(requestCode==REQUEST_CODE_CAMERA && grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            activity.startActivityForResult(intent,REQUEST_CODE_CAMERA);
        }
        this.requestCode=requestCode;
        this.permissions=permissions;
        this.grantResults=grantResults;
    }
    public void onActivityResult(int requestCode,int resultCode,Intent data, ImageView imageView){
        if (requestCode == REQUEST_CODE_CAMERA && resultCode == RESULT_OK && data!= null){
            Bitmap bitmap=(Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(bitmap);
        }
        this.requestCode=requestCode;
        this.resultCode=resultCode;
        this.data=data;
    }
}
