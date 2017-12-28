package com.eways.elearning.Handler;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.eways.elearning.View.Activity.MainActivity;
import com.eways.elearning.View.Fragment.TaiKhoan.ThongTinTaiKhoan.CapNhatTaiKhoan.CapNhatThongTinTaiKhoanFragment;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.DialogPlusBuilder;
import com.orhanobut.dialogplus.GridHolder;
import com.orhanobut.dialogplus.OnItemClickListener;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Random;

import static android.app.Activity.RESULT_OK;

/**
 * Created by ADMIN on 11/30/2017.
 */

public class DialogPlusHandler {
    Activity activity;
    ArrayAdapter arrayAdapter;
    CapNhatThongTinTaiKhoanFragment fragment;

    int requestCode;
    String[] permissions;
    int[] grantResults;

    Bitmap bitmap;

    ImageHandler imageHandler;

    public int resultCode;
    public Intent data;
    public ImageView hinhMatTruoc;
    public ImageView hinhMatSau;
    public ImageView avarta;

    public static int vitrichon;
    private String selectedImagePath;
    private String filemanagerstring;
    private DialogPlus dialogPlus;

    public static int REQUEST_CODE_CAMERA=1;
    public static int REQUEST_CODE_GALLERY=2;

//    public DialogPlusHandler(Activity activity, ArrayAdapter arrayAdapter) {
//        this.activity = activity;
//        this.arrayAdapter = arrayAdapter;
//    }

    public DialogPlusHandler(Activity activity, ArrayAdapter arrayAdapter, CapNhatThongTinTaiKhoanFragment fragment) {
        this.activity = activity;
        this.arrayAdapter = arrayAdapter;
        this.fragment = fragment;
    }

    public void ShowDialogChonHinh(int vitrichon){
        DialogPlusBuilder dialog = dialog = DialogPlus.newDialog(activity);
        dialog.setContentHolder(new GridHolder(2));
        dialog.setContentHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setAdapter(arrayAdapter);

        dialog.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(DialogPlus dialog, Object item, View view, int position) {
                if (position==0){
                    fragment.requestPermissions(new String[]{android.Manifest.permission.CAMERA},REQUEST_CODE_CAMERA);
                }
                if (position==1){
                    Intent intent=new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                    fragment.startActivityForResult(intent,REQUEST_CODE_GALLERY);
                }

            }
        });
        dialogPlus=dialog.create();
        dialogPlus.show();
        this.vitrichon=vitrichon;
    }

    public void dissMissDialog(){
        dialogPlus.dismiss();
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
            fragment.startActivityForResult(intent,REQUEST_CODE_CAMERA);
        }
        this.requestCode=requestCode;
        this.permissions=permissions;
        this.grantResults=grantResults;
    }
    public void onActivityResult(int requestCode,int resultCode,Intent data,ImageView hinhMatTruoc,ImageView hinhMatSau,ImageView avatar){
        ImageHandler imageHandler=new ImageHandler(activity);
        if (requestCode == REQUEST_CODE_CAMERA && resultCode == RESULT_OK && data!= null){
            bitmap=(Bitmap) data.getExtras().get("data");
            if (vitrichon==0)
                hinhMatTruoc.setImageBitmap(bitmap);
            else{
                if (vitrichon==1)
                    hinhMatSau.setImageBitmap(bitmap);
                else
                    avatar.setImageBitmap(bitmap);
            }
        }
        if (requestCode == REQUEST_CODE_GALLERY && resultCode == RESULT_OK && data != null) {
            if (vitrichon==0)
                imageHandler.loadImageRound(String.valueOf(data.getData()) ,hinhMatTruoc);
            else{
                if (vitrichon==1)
                    imageHandler.loadImageRound(String.valueOf(data.getData()),hinhMatSau);
                else
                    imageHandler.loadImageRound(String.valueOf(data.getData()),avatar);
            }

        }
        this.requestCode=requestCode;
        this.resultCode=resultCode;
        this.data=data;
        this.hinhMatTruoc=hinhMatTruoc;
        this.hinhMatSau=hinhMatSau;
    }
}
