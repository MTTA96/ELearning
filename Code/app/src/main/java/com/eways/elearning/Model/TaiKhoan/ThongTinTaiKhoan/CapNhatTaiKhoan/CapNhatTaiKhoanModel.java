package com.eways.elearning.Model.TaiKhoan.ThongTinTaiKhoan.CapNhatTaiKhoan;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.util.Log;

import com.eways.elearning.DataModel.TaiKhoan.TaiKhoan;
import com.eways.elearning.Presenter.TaiKhoan.CapNhatTaiKhoan.CapNhatTaiKhoanPresenterImp;
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
    public FirebaseAuth mAuth;
    public FirebaseDatabase mData;
    public CapNhatTaiKhoanPresenterImp capNhatTaiKhoanPresenterImp;
    public FirebaseStorage storage;
    public Activity activityModel;
    public FirebaseDatabase mGetData;

    public CapNhatTaiKhoanModel(CapNhatTaiKhoanPresenterImp capNhatTaiKhoanPresenterImp) {
        this.capNhatTaiKhoanPresenterImp = capNhatTaiKhoanPresenterImp;
    }

    @Override
    public void CapNhatTaiKhoan(final TaiKhoan taiKhoan, final Activity activity, byte[] data_mt, final byte[] data_ms,final byte[] data_avarta) {
        activityModel=activity;
        mAuth=FirebaseAuth.getInstance(FirebaseApp.initializeApp(activity));
        mData=FirebaseDatabase.getInstance(FirebaseApp.initializeApp(activity));
        mGetData=FirebaseDatabase.getInstance(FirebaseApp.initializeApp(activity));

        //up hinh tai lieu xac minh
        storage=FirebaseStorage.getInstance(FirebaseApp.initializeApp(activity));
        StorageReference storageRef = storage.getReferenceFromUrl("gs://elearning-da847.appspot.com");
        final StorageReference TaiLieuXacMinh_mt = storageRef.child(taiKhoan.getId()+"_mt.jpg");
        final StorageReference TaiLieuXacMinh_ms = storageRef.child(taiKhoan.getId()+"_ms.jpg");
        final StorageReference Avarta=storageRef.child(taiKhoan.getId()+"_avarta.jpg");
        UploadTask uploadTask = TaiLieuXacMinh_mt.putBytes(data_mt);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("LoiCN1",e.toString());
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
                Log.d("LoiCN2",e.toString());
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                String downloadUrl = taskSnapshot.getDownloadUrl().toString();
                taiKhoan.setTailieuxacminh_ms(downloadUrl);
                mData.getReference().child("TaiKhoan").child(taiKhoan.getId()).setValue(taiKhoan);
            }
        });
        UploadTask uploadTask2 = Avarta.putBytes(data_avarta);
        uploadTask2.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("LoiCN1",e.toString());
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                String downloadUrl = taskSnapshot.getDownloadUrl().toString();
                taiKhoan.setAvatar(downloadUrl);
                mData.getReference().child("TaiKhoan").child(taiKhoan.getId()).setValue(taiKhoan);
            }
        });

        //Lay data cua tai khoan
        mGetData.getReference().child("TaiKhoan").orderByKey().equalTo(taiKhoan.getId().toString().trim()).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                capNhatTaiKhoanPresenterImp.KetQuaCapNhat(SupportKeysList.TAG_CAPNHATTHANHCONG,dataSnapshot.getValue(TaiKhoan.class),activityModel);
                return;
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
    public void CapNhatTaiKhoanGiaSu(String idUser, final Activity activity) {
        mData=FirebaseDatabase.getInstance(FirebaseApp.initializeApp(activity));
        mData.getReference().child("TaiKhoan").child(idUser).child("taiKhoanGiaSu").setValue(true).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isComplete()){
                    capNhatTaiKhoanPresenterImp.KetQuaCapNhatTaiKhoanGiaSu("CapNhatTaiKhoanGiaSuThanhCong", activity);
                }
            }
        });
    }


}
