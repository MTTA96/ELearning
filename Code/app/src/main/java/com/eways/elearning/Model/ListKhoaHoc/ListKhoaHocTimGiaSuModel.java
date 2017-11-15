package com.eways.elearning.Model.ListKhoaHoc;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.eways.elearning.DataModel.KhoaHoc.CustomModelKhoaHoc;
import com.eways.elearning.Handler.Other.DataCallBack;
import com.eways.elearning.Handler.Other.GetData;
import com.eways.elearning.Presenter.ListKhoaHoc.ListKhoaHocTimGiaSuPresenterImp;
import com.eways.elearning.Util.ServerUrl;
import com.eways.elearning.Util.SupportKeysList;

import java.util.ArrayList;

/**
 * Created by yowin on 09/11/2017.
 */

public class ListKhoaHocTimGiaSuModel implements ListKhoaHocTimGiaSuImpModel,DataCallBack {

    ListKhoaHocTimGiaSuPresenterImp listKhoaHocTimGiaSuPresenterImp;
//    DatabaseReference mData = FirebaseDatabase.getInstance().getReference();
//    ArrayList<KhoaHocChuaHoanTat> listKhoaHoc =new ArrayList<>();
//
    public ListKhoaHocTimGiaSuModel(ListKhoaHocTimGiaSuPresenterImp listKhoaHocTimGiaSuPresenterImp) {
        this.listKhoaHocTimGiaSuPresenterImp = listKhoaHocTimGiaSuPresenterImp;
    }

    /**Dùng NodeJS
     * implements DataCallBack khi dùng*/
    @Override
    public void getDanhSachKhoaHocTimGiaSu() {
        new GetData(this).execute(SupportKeysList.API_GET_KHOAHOC,ServerUrl.ApiGetKhoaHocGS,"GET");
    }

    @Override
    public void KetQua(String result, @Nullable Bundle bundle) {
        ArrayList<CustomModelKhoaHoc> khoaHocArrayList = (ArrayList<CustomModelKhoaHoc>) bundle.getSerializable(SupportKeysList.API_GET_KHOAHOC);
        listKhoaHocTimGiaSuPresenterImp.nhanDanhSachKhoaHoc(khoaHocArrayList);
    }

    /**Dùng code Firebase*/
//    @Override
//    public void getDanhSachKhoaHocTimGiaSu() {
//        mData.child(SupportKeysList.CHILD_KHOAHOC).child(SupportKeysList.CHILD_KHOAHOC_TIMGIASU).child(SupportKeysList.CHILD_KHOAHOC_CHUAHOANTAT).addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//                KhoaHocChuaHoanTat kh = dataSnapshot.getValue(KhoaHocChuaHoanTat.class);
//                listKhoaHoc.add(kh);
//                listKhoaHocTimGiaSuPresenterImp.nhanDanhSachKhoaHoc(listKhoaHoc);
//            }
//
//            @Override
//            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onChildRemoved(DataSnapshot dataSnapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//    }
}
