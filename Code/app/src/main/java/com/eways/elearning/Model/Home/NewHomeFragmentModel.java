package com.eways.elearning.Model.Home;

import com.eways.elearning.DataModel.KhoaHoc.CustomModelKhoaHoc;
import com.eways.elearning.DataModel.KhoaHoc.KhoaHoc;
import com.eways.elearning.Presenter.Home.NewHomeFragmentPresenterImp;
import com.eways.elearning.Util.SupportKeysList;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * Created by yowin on 02/12/2017.
 */

public class NewHomeFragmentModel implements NewHomeFragmentModelImp {

    NewHomeFragmentPresenterImp newHomeFragmentPresenterImp;
    DatabaseReference mData = FirebaseDatabase.getInstance().getReference();
    ArrayList<CustomModelKhoaHoc> listKhoaHoc1 = new ArrayList<>();
    ArrayList<CustomModelKhoaHoc> listKhoaHoc2 = new ArrayList<>();
    ArrayList<CustomModelKhoaHoc> listKhoaHoc3 = new ArrayList<>();

    public NewHomeFragmentModel(NewHomeFragmentPresenterImp newHomeFragmentPresenterImp) {
        this.newHomeFragmentPresenterImp = newHomeFragmentPresenterImp;
    }

    @Override
    public void getListkhoaHoc(boolean loai,final String linhVuc1, final String linhVuc2, final String linhVuc3) {
        listKhoaHoc1 = new ArrayList<>();
        listKhoaHoc2 = new ArrayList<>();
        listKhoaHoc3 = new ArrayList<>();
        if (loai) {
            mData.child(SupportKeysList.CHILD_KHOAHOC).child(SupportKeysList.CHILD_KHOAHOC_TIMGIASU).child(SupportKeysList.CHILD_KHOAHOC_CHUAHOANTAT).addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    KhoaHoc kh = dataSnapshot.getValue(KhoaHoc.class);
                    for (String linhVuc : kh.getLinhVuc()) {
                        if (linhVuc.equals(linhVuc1)) {
                            KhoaHoc khoaHoc=new KhoaHoc();
                            khoaHoc=dataSnapshot.getValue(KhoaHoc.class);
                            listKhoaHoc1.add(new CustomModelKhoaHoc(khoaHoc.getAvatar(),khoaHoc.getRating(),khoaHoc.getHoTen(),khoaHoc.getNguoiDang(),khoaHoc.getSoBuoiHoc(),khoaHoc.getSoLuongHocVien(),khoaHoc.getGioiTinh(),khoaHoc.getNgayDang(),khoaHoc.getGioDang(),khoaHoc.getThoiLuongBuoiHoc(),khoaHoc.getHocPhi(),khoaHoc.getThongTinKhac(),khoaHoc.getBangCap(),khoaHoc.getMon(),khoaHoc.getLinhVuc(),khoaHoc.getLichHoc(),khoaHoc.getDiaDiem(),khoaHoc.getDanhSachYeuCau(),dataSnapshot.getKey()));
                        }
                        if (linhVuc.equals(linhVuc2)) {
                            KhoaHoc khoaHoc=new KhoaHoc();
                            khoaHoc=dataSnapshot.getValue(KhoaHoc.class);
                            listKhoaHoc2.add(new CustomModelKhoaHoc(khoaHoc.getAvatar(),khoaHoc.getRating(),khoaHoc.getHoTen(),khoaHoc.getNguoiDang(),khoaHoc.getSoBuoiHoc(),khoaHoc.getSoLuongHocVien(),khoaHoc.getGioiTinh(),khoaHoc.getNgayDang(),khoaHoc.getGioDang(),khoaHoc.getThoiLuongBuoiHoc(),khoaHoc.getHocPhi(),khoaHoc.getThongTinKhac(),khoaHoc.getBangCap(),khoaHoc.getMon(),khoaHoc.getLinhVuc(),khoaHoc.getLichHoc(),khoaHoc.getDiaDiem(),khoaHoc.getDanhSachYeuCau(),dataSnapshot.getKey()));
                        }
                        if (linhVuc.equals(linhVuc3)) {
                            KhoaHoc khoaHoc=new KhoaHoc();
                            khoaHoc=dataSnapshot.getValue(KhoaHoc.class);
                            listKhoaHoc3.add(new CustomModelKhoaHoc(khoaHoc.getAvatar(),khoaHoc.getRating(),khoaHoc.getHoTen(),khoaHoc.getNguoiDang(),khoaHoc.getSoBuoiHoc(),khoaHoc.getSoLuongHocVien(),khoaHoc.getGioiTinh(),khoaHoc.getNgayDang(),khoaHoc.getGioDang(),khoaHoc.getThoiLuongBuoiHoc(),khoaHoc.getHocPhi(),khoaHoc.getThongTinKhac(),khoaHoc.getBangCap(),khoaHoc.getMon(),khoaHoc.getLinhVuc(),khoaHoc.getLichHoc(),khoaHoc.getDiaDiem(),khoaHoc.getDanhSachYeuCau(),dataSnapshot.getKey()));
                        }
                    }
                    newHomeFragmentPresenterImp.getListKhoaHoc(listKhoaHoc1, listKhoaHoc2, listKhoaHoc3);
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
            mData.child(SupportKeysList.CHILD_KHOAHOC).child(SupportKeysList.CHILD_KHOAHOC_TIMHOCVIEN).child(SupportKeysList.CHILD_KHOAHOC_CHUAHOANTAT).addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    KhoaHoc kh = dataSnapshot.getValue(KhoaHoc.class);
                    for (String linhVuc : kh.getLinhVuc()) {
                        if (linhVuc.equals(linhVuc1)) {
                            KhoaHoc khoaHoc=new KhoaHoc();
                            khoaHoc=dataSnapshot.getValue(KhoaHoc.class);
                            listKhoaHoc1.add(new CustomModelKhoaHoc(khoaHoc.getAvatar(),khoaHoc.getRating(),khoaHoc.getHoTen(),khoaHoc.getNguoiDang(),khoaHoc.getSoBuoiHoc(),khoaHoc.getSoLuongHocVien(),khoaHoc.getGioiTinh(),khoaHoc.getNgayDang(),khoaHoc.getGioDang(),khoaHoc.getThoiLuongBuoiHoc(),khoaHoc.getHocPhi(),khoaHoc.getThongTinKhac(),khoaHoc.getBangCap(),khoaHoc.getMon(),khoaHoc.getLinhVuc(),khoaHoc.getLichHoc(),khoaHoc.getDiaDiem(),khoaHoc.getDanhSachYeuCau(),dataSnapshot.getKey()));
                        }
                        if (linhVuc.equals(linhVuc2)) {
                            KhoaHoc khoaHoc=new KhoaHoc();
                            khoaHoc=dataSnapshot.getValue(KhoaHoc.class);
                            listKhoaHoc2.add(new CustomModelKhoaHoc(khoaHoc.getAvatar(),khoaHoc.getRating(),khoaHoc.getHoTen(),khoaHoc.getNguoiDang(),khoaHoc.getSoBuoiHoc(),khoaHoc.getSoLuongHocVien(),khoaHoc.getGioiTinh(),khoaHoc.getNgayDang(),khoaHoc.getGioDang(),khoaHoc.getThoiLuongBuoiHoc(),khoaHoc.getHocPhi(),khoaHoc.getThongTinKhac(),khoaHoc.getBangCap(),khoaHoc.getMon(),khoaHoc.getLinhVuc(),khoaHoc.getLichHoc(),khoaHoc.getDiaDiem(),khoaHoc.getDanhSachYeuCau(),dataSnapshot.getKey()));
                        }
                        if (linhVuc.equals(linhVuc3)) {
                            KhoaHoc khoaHoc=new KhoaHoc();
                            khoaHoc=dataSnapshot.getValue(KhoaHoc.class);
                            listKhoaHoc3.add(new CustomModelKhoaHoc(khoaHoc.getAvatar(),khoaHoc.getRating(),khoaHoc.getHoTen(),khoaHoc.getNguoiDang(),khoaHoc.getSoBuoiHoc(),khoaHoc.getSoLuongHocVien(),khoaHoc.getGioiTinh(),khoaHoc.getNgayDang(),khoaHoc.getGioDang(),khoaHoc.getThoiLuongBuoiHoc(),khoaHoc.getHocPhi(),khoaHoc.getThongTinKhac(),khoaHoc.getBangCap(),khoaHoc.getMon(),khoaHoc.getLinhVuc(),khoaHoc.getLichHoc(),khoaHoc.getDiaDiem(),khoaHoc.getDanhSachYeuCau(),dataSnapshot.getKey()));
                        }
                    }
                    newHomeFragmentPresenterImp.getListKhoaHoc(listKhoaHoc1, listKhoaHoc2, listKhoaHoc3);
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
