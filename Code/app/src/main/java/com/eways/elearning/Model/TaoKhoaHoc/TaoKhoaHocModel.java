package com.eways.elearning.Model.TaoKhoaHoc;

import android.app.Activity;

import com.eways.elearning.DataModel.KhoaHoc.KhoaHoc;
import com.eways.elearning.Presenter.TaoKhoaHoc.TaoKhoaHocPresenterImp;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by yowin on 19/11/2017.
 */

public class TaoKhoaHocModel implements TaoKhoaHocModelImp {

    TaoKhoaHocPresenterImp taoKhoaHocPresenterImp;
    FirebaseDatabase mData ;
    FirebaseAuth mAuth;


    public TaoKhoaHocModel(TaoKhoaHocPresenterImp taoKhoaHocPresenterImp) {
        this.taoKhoaHocPresenterImp = taoKhoaHocPresenterImp;
    }

    @Override
    public void postKhoaHoc(KhoaHoc khoaHoc,boolean loai, Activity activity) {
        mData=FirebaseDatabase.getInstance(FirebaseApp.initializeApp(activity));
        if(loai == false)
        {
            mData.getReference().child("KhoaHoc").child("KhoaHocTimGiaSu").child("ChuaHoanTat").push().setValue(khoaHoc);
        }
        else
        {
            mData.getReference().child("KhoaHoc").child("KhoaHocTimHocVien").child("ChuaHoanTat").push().setValue(khoaHoc);
        }
    }
}
