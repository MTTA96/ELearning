package com.eways.elearning.Model.ListKhoaHoc;

import com.eways.elearning.DataModel.KhoaHoc.CustomModelKhoaHoc;
import com.eways.elearning.DataModel.KhoaHoc.KhoaHoc;
import com.eways.elearning.Presenter.ListKhoaHoc.ListKhoaHocTimGiaSuPresenterImp;
import com.eways.elearning.Util.SupportKeysList;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * Created by yowin on 09/11/2017.
 */

public class ListKhoaHocTimGiaSuModel implements ListKhoaHocTimGiaSuImpModel {

    ListKhoaHocTimGiaSuPresenterImp listKhoaHocTimGiaSuPresenterImp;
    DatabaseReference mData = FirebaseDatabase.getInstance().getReference();
    ArrayList<CustomModelKhoaHoc> listKhoaHoc = new ArrayList<>();
//
    public ListKhoaHocTimGiaSuModel(ListKhoaHocTimGiaSuPresenterImp listKhoaHocTimGiaSuPresenterImp) {
        this.listKhoaHocTimGiaSuPresenterImp = listKhoaHocTimGiaSuPresenterImp;
    }

    /**Dùng NodeJS
     * implements DataCallBack khi dùng*/
//    @Override
//    public void getDanhSachKhoaHocTimGiaSu() {
//        new GetData(this).execute(SupportKeysList.API_GET_KHOAHOC,ServerUrl.ApiGetKhoaHocGS,"GET");
//    }
//
//    @Override
//    public void KetQua(String result, @Nullable Bundle bundle) {
//        ArrayList<CustomModelKhoaHoc> khoaHocArrayList = (ArrayList<CustomModelKhoaHoc>) bundle.getSerializable(SupportKeysList.API_GET_KHOAHOC);
//        listKhoaHocTimGiaSuPresenterImp.nhanDanhSachKhoaHoc(khoaHocArrayList);
//    }

    /**Dùng code Firebase
     * implements DataCallBack ở view*/
    @Override
    public void getDanhSachKhoaHocTimGiaSu() {
        mData.child(SupportKeysList.CHILD_KHOAHOC).child(SupportKeysList.CHILD_KHOAHOC_TIMGIASU).child(SupportKeysList.CHILD_KHOAHOC_CHUAHOANTAT).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                KhoaHoc kh = dataSnapshot.getValue(KhoaHoc.class);
                CustomModelKhoaHoc ckh = new CustomModelKhoaHoc(kh.getHoTen(),kh.getNguoiDang(),kh.getAvatar(),kh.getLichHoc().getThoiGian(),kh.getRating(),kh.getHocPhi(),kh.getMon());
                listKhoaHoc.add(ckh);
                listKhoaHocTimGiaSuPresenterImp.nhanDanhSachKhoaHoc(listKhoaHoc);
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
