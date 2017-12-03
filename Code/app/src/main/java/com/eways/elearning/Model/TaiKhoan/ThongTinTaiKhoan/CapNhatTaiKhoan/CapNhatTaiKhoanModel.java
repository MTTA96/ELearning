package com.eways.elearning.Model.TaiKhoan.ThongTinTaiKhoan.CapNhatTaiKhoan;

import android.app.Activity;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.eways.elearning.DataModel.TaiKhoan;
import com.eways.elearning.Presenter.TaiKhoan.CapNhatTaiKhoan.CapNhatTaiKhoanPresenterImp;
import com.eways.elearning.Presenter.TaiKhoan.CapNhatTaiKhoan.TruyenHinhTaiLieuXacMinh.TruyenHinhTaiLieuXacMinh;
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

public class CapNhatTaiKhoanModel implements CapNhatTaiKhoanModelImp {
    FirebaseAuth mAuth;
    FirebaseDatabase mData;
    CapNhatTaiKhoanPresenterImp capNhatTaiKhoanPresenterImp;
    FirebaseStorage storage;

    public String uriNhanHinhMT;
    public String uriNhanHinhMS;
    TruyenHinhTaiLieuXacMinh truyenHinhTaiLieuXacMinh=new TruyenHinhTaiLieuXacMinh(this);

    public CapNhatTaiKhoanModel(CapNhatTaiKhoanPresenterImp capNhatTaiKhoanPresenterImp) {
        this.capNhatTaiKhoanPresenterImp = capNhatTaiKhoanPresenterImp;

    }

    @Override
    public void CapNhatTaiKhoan(final TaiKhoan taiKhoan, final Activity activity, byte[] data_mt, byte[] data_ms) {
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
                Uri downloadUrl = taskSnapshot.getDownloadUrl();
                truyenHinhTaiLieuXacMinh.TruyenHinhMT(String.valueOf(downloadUrl));
            }
        });

        UploadTask uploadTask1=TaiLieuXacMinh_ms.putBytes(data_ms);
        uploadTask1.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Uri downloadUrl = taskSnapshot.getDownloadUrl();
                truyenHinhTaiLieuXacMinh.TruyenHinhMS(String.valueOf(downloadUrl));
            }
        });
        taiKhoan.setTailieuxacminh_mt(uriNhanHinhMT);
        taiKhoan.setTailieuxacminh_ms(uriNhanHinhMS);
        mData.getReference().child("TaiKhoan").child(taiKhoan.getId()).setValue(taiKhoan);
        mData.getReference().child("TaiKhoan").orderByKey().equalTo(taiKhoan.getId()).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                capNhatTaiKhoanPresenterImp.KetQuaCapNhat(SupportKeysList.TAG_CAPNHATTHANHCONG,dataSnapshot.getValue(TaiKhoan.class),activity);
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
        });
    }

    @Override
    public void NhanHinhTaiLieuXacMinhMT(String uri) {
        uriNhanHinhMT=uri;
    }
    @Override
    public void NhanHinhTaiLieuXacMinhMS(String uri) {
        uriNhanHinhMS=uri;
    }
}
