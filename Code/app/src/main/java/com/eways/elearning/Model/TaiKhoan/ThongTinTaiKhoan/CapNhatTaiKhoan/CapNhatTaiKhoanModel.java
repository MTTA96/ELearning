package com.eways.elearning.Model.TaiKhoan.ThongTinTaiKhoan.CapNhatTaiKhoan;

import android.app.Activity;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;

import com.eways.elearning.DataModel.TaiKhoan;
import com.eways.elearning.Presenter.TaiKhoan.CapNhatTaiKhoan.CapNhatTaiKhoanPresenterImp;
import com.eways.elearning.Presenter.TaiKhoan.CapNhatTaiKhoan.TruyenHinhTaiLieuXacMinh.TruyenHinhTaiLieuXacMinh;
import com.eways.elearning.Presenter.TaiKhoan.CapNhatTaiKhoan.TruyenHinhTaiLieuXacMinh.TruyenHinhTaiLieuXacMinhImp;
import com.eways.elearning.Util.SupportKeysList;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

/**
 * Created by ADMIN on 11/19/2017.
 */

public class CapNhatTaiKhoanModel implements CapNhatTaiKhoanModelImp, ChildEventListener {
    FirebaseAuth mAuth;
    FirebaseDatabase mData;
    CapNhatTaiKhoanPresenterImp capNhatTaiKhoanPresenterImp;
    FirebaseStorage storage;
    Activity activityModel;

    public String uriNhanHinhMT;
    public String uriNhanHinhMS;
    TruyenHinhTaiLieuXacMinhImp truyenHinhTaiLieuXacMinhImp=new TruyenHinhTaiLieuXacMinh(this);

    public CapNhatTaiKhoanModel(CapNhatTaiKhoanPresenterImp capNhatTaiKhoanPresenterImp) {
        this.capNhatTaiKhoanPresenterImp = capNhatTaiKhoanPresenterImp;

    }

    @Override
    public void CapNhatTaiKhoan(final TaiKhoan taiKhoan, final Activity activity, byte[] data_mt, byte[] data_ms) {
        TaiKhoan taiKhoanTemp;
        activityModel=activity;
        mAuth=FirebaseAuth.getInstance(FirebaseApp.initializeApp(activity));
        mData=FirebaseDatabase.getInstance(FirebaseApp.initializeApp(activity));
        //up hinh tai lieu xac minh
        storage=FirebaseStorage.getInstance(FirebaseApp.initializeApp(activity));
        StorageReference storageRef = storage.getReferenceFromUrl("gs://elearning-da847.appspot.com");
        StorageReference TaiLieuXacMinh_mt = storageRef.child(taiKhoan.getId()+"_mt.jpg");
        StorageReference TaiLieuXacMinh_ms = storageRef.child(taiKhoan.getId()+"_ms.jpg");
        UploadTask uploadTask = TaiLieuXacMinh_mt.putBytes(data_mt);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                String downloadUrl = taskSnapshot.getDownloadUrl().toString();
                taiKhoan.setTailieuxacminh_mt(downloadUrl);
                mData.getReference().child("TaiKhoan").child(taiKhoan.getId()).setValue(taiKhoan);
            }
        });

        //luu hinh len firebase storage mat sau
        UploadTask uploadTask1=TaiLieuXacMinh_ms.putBytes(data_ms);
        uploadTask1.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                String downloadUrl = taskSnapshot.getDownloadUrl().toString();
                taiKhoan.setTailieuxacminh_ms(downloadUrl);
                mData.getReference().child("TaiKhoan").child(taiKhoan.getId()).setValue(taiKhoan);
            }
        });

        //Lay data cua tai khoan
        mData.getReference().child("TaiKhoan").orderByKey().equalTo(taiKhoan.getId()).addChildEventListener(this);
    }

    @Override
    public void NhanHinhTaiLieuXacMinhMT(String uri) {
        uriNhanHinhMT=uri;
    }
    @Override
    public void NhanHinhTaiLieuXacMinhMS(String uri) {
        uriNhanHinhMS=uri;
    }

    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        capNhatTaiKhoanPresenterImp.KetQuaCapNhat(SupportKeysList.TAG_CAPNHATTHANHCONG,dataSnapshot.getValue(TaiKhoan.class),activityModel);
    }

    @Override
    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void onChildRemoved(DataSnapshot dataSnapshot) {

    }

    @Override
    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }
}
