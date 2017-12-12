package com.eways.elearning.Model.KhoaHoc.ThongTinNguoiDang.ThongTinKhoaHoc;

import android.app.Activity;

import com.eways.elearning.Presenter.KhoaHoc.ThongTinKhoaHoc.ThongTinKhoaHocPresenterImp;
import com.eways.elearning.Util.SupportKeysList;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by ADMIN on 12/11/2017.
 */

public class ThongTinKhoaHocModel implements ThongTinKhoaHocModelImp {
    ThongTinKhoaHocPresenterImp thongTinKhoaHocPresenterImp;
    FirebaseDatabase mData;

    public ThongTinKhoaHocModel(ThongTinKhoaHocPresenterImp thongTinKhoaHocPresenterImp) {
        this.thongTinKhoaHocPresenterImp = thongTinKhoaHocPresenterImp;
    }

    @Override
    public void LayDataKhoaHoc(Activity activity,String loaiBaiDang) {
        mData=FirebaseDatabase.getInstance(FirebaseApp.initializeApp(activity));
        if (loaiBaiDang.compareTo(SupportKeysList.GET_DATA_TIMGIASU)==0){
            mData.getReference().child("KhoaHoc").child("KhoaHocTimGiaSu").child("ChuaHoanTat").addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {

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
    }
}
