package com.eways.elearning.Model.KhoaHoc.ThongTinNguoiDang.ThongTinKhoaHoc;

import android.app.Activity;

import com.eways.elearning.DataModel.KhoaHoc.KhoaHoc;
import com.eways.elearning.DataModel.TaiKhoan.TaiKhoan;
import com.eways.elearning.DataModel.ThongTinChiTietKhoaHoc;
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
    public void LayDataKhoaHoc(Activity activity, String loaiKhoaHoc , String idNguoiDang, final String idKhoaHoc) {
        mData = FirebaseDatabase.getInstance(FirebaseApp.initializeApp(activity));
        if (loaiKhoaHoc.compareTo(SupportKeysList.GET_DATA_TIMGIASU)==0){
            mData.getReference().child("TaiKhoan").orderByKey().equalTo(idNguoiDang.toString().trim()).addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    final TaiKhoan taiKhoan=dataSnapshot.getValue(TaiKhoan.class);
                    mData.getReference().child("KhoaHoc").child("KhoaHocTimGiaSu").child("ChuaHoanTat").orderByKey().equalTo(idKhoaHoc.toString().trim()).addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                            thongTinKhoaHocPresenterImp.NhanKetQuaThongTinKhoaHoc(new ThongTinChiTietKhoaHoc(taiKhoan,dataSnapshot.getValue(KhoaHoc.class)));
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
        if (loaiKhoaHoc.compareTo(SupportKeysList.GET_DATA_TIMHOCVIEN)==0){
            mData.getReference().child("TaiKhoan").orderByKey().equalTo(idNguoiDang).addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    final TaiKhoan taiKhoan=dataSnapshot.getValue(TaiKhoan.class);
                    mData.getReference().child("KhoaHoc").child("KhoaHocTimHocVien").child("ChuaHoanTat").orderByKey().equalTo(idKhoaHoc).addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                            thongTinKhoaHocPresenterImp.NhanKetQuaThongTinKhoaHoc(new ThongTinChiTietKhoaHoc(taiKhoan,dataSnapshot.getValue(KhoaHoc.class)));
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
