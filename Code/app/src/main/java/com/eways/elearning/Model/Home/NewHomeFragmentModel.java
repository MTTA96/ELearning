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
        if (loai) {
            mData.child(SupportKeysList.CHILD_KHOAHOC).child(SupportKeysList.CHILD_KHOAHOC_TIMGIASU).child(SupportKeysList.CHILD_KHOAHOC_CHUAHOANTAT).addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    KhoaHoc kh = dataSnapshot.getValue(KhoaHoc.class);
                    for (String linhVuc : kh.getLinhVuc()) {
                        if (linhVuc.equals(linhVuc1)) {
                            listKhoaHoc1.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                        }
                        if (linhVuc.equals(linhVuc2)) {
                            listKhoaHoc2.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                        }
                        if (linhVuc.equals(linhVuc3)) {
                            listKhoaHoc3.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
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
                            listKhoaHoc1.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                        }
                        if (linhVuc.equals(linhVuc2)) {
                            listKhoaHoc2.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                        }
                        if (linhVuc.equals(linhVuc3)) {
                            listKhoaHoc3.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
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
