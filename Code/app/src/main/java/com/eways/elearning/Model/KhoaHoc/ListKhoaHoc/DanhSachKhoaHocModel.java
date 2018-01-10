package com.eways.elearning.Model.KhoaHoc.ListKhoaHoc;

import com.eways.elearning.DataModel.KhoaHoc.CustomModelKhoaHoc;
import com.eways.elearning.DataModel.KhoaHoc.KhoaHoc;
import com.eways.elearning.DataModel.TaiKhoan.TaiKhoan;
import com.eways.elearning.Presenter.ListKhoaHoc.DanhSachKhoaHocPresenterImp;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * Created by yowin on 10/01/2018.
 */

public class DanhSachKhoaHocModel implements DanhSachKhoaHocModelImp {

    DanhSachKhoaHocPresenterImp danhSachKhoaHocPresenterImp;
    DatabaseReference mData = FirebaseDatabase.getInstance().getReference();
    ArrayList<CustomModelKhoaHoc> danhSachKhoaHoc = new ArrayList<>();

    public DanhSachKhoaHocModel(DanhSachKhoaHocPresenterImp danhSachKhoaHocPresenterImp) {
        this.danhSachKhoaHocPresenterImp = danhSachKhoaHocPresenterImp;
    }

    @Override
    public void getDanhSachKhoaHoc(final String linhVuc, boolean loai) {

        if(loai == false)
        {
            mData.child("KhoaHoc").child("KhoaHocTimGiaSu").child("KhoaHocChuaHoanTat").addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    final KhoaHoc khoaHoc = dataSnapshot.getValue(KhoaHoc.class);
                    mData.child("TaiKhoan").orderByKey().equalTo(khoaHoc.getNguoiDang().toString().trim()).addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                            CustomModelKhoaHoc ckh = new CustomModelKhoaHoc(dataSnapshot.getValue(TaiKhoan.class).getAvatar(),dataSnapshot.getValue(TaiKhoan.class).getRating(),dataSnapshot.getValue(TaiKhoan.class).getHo()+dataSnapshot.getValue(TaiKhoan.class).getTen(),khoaHoc.getNguoiDang(),khoaHoc.getSoBuoiHoc(),khoaHoc.getSoLuongHocVien(),khoaHoc.getGioiTinh(),khoaHoc.getNgayDang(),khoaHoc.getGioDang(),khoaHoc.getThoiLuongBuoiHoc(),khoaHoc.getHocPhi(),khoaHoc.getThongTinKhac(),khoaHoc.getBangCap(),khoaHoc.getMon(),khoaHoc.getLinhVuc(),khoaHoc.getLichHoc(),khoaHoc.getDiaDiem(),khoaHoc.getDanhSachYeuCau(),dataSnapshot.getKey());
                            for(String linhvuc : ckh.getLinhVuc())
                            {
                                if(linhvuc.equals(linhVuc))
                                {

                                    danhSachKhoaHoc.add(ckh);
                                }
                            }
                            danhSachKhoaHocPresenterImp.nhanDanhSachKhoaHoc(danhSachKhoaHoc);
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
        else
        {
            mData.child("KhoaHoc").child("KhoaHocTimHocVien").child("KhoaHocChuaHoanTat").addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    final KhoaHoc khoaHoc = dataSnapshot.getValue(KhoaHoc.class);
                    mData.child("TaiKhoan").orderByKey().equalTo(khoaHoc.getNguoiDang().toString().trim()).addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                            CustomModelKhoaHoc ckh = new CustomModelKhoaHoc(dataSnapshot.getValue(TaiKhoan.class).getAvatar(),dataSnapshot.getValue(TaiKhoan.class).getRating(),dataSnapshot.getValue(TaiKhoan.class).getHo()+dataSnapshot.getValue(TaiKhoan.class).getTen(),khoaHoc.getNguoiDang(),khoaHoc.getSoBuoiHoc(),khoaHoc.getSoLuongHocVien(),khoaHoc.getGioiTinh(),khoaHoc.getNgayDang(),khoaHoc.getGioDang(),khoaHoc.getThoiLuongBuoiHoc(),khoaHoc.getHocPhi(),khoaHoc.getThongTinKhac(),khoaHoc.getBangCap(),khoaHoc.getMon(),khoaHoc.getLinhVuc(),khoaHoc.getLichHoc(),khoaHoc.getDiaDiem(),khoaHoc.getDanhSachYeuCau(),dataSnapshot.getKey());
                            for(String linhvuc : ckh.getLinhVuc())
                            {
                                if(linhvuc.equals(linhVuc))
                                {

                                    danhSachKhoaHoc.add(ckh);
                                }
                            }
                            danhSachKhoaHocPresenterImp.nhanDanhSachKhoaHoc(danhSachKhoaHoc);
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
