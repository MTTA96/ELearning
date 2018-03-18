package com.eways.elearning.Data.Model.KhoaHoc.TimKiemKhoaHoc;

import com.eways.elearning.Data.DataModel.KhoaHoc.CustomModelKhoaHoc;
import com.eways.elearning.Data.DataModel.KhoaHoc.KhoaHoc;
import com.eways.elearning.Presenter.TimKiemKhoaHoc.TimKiemTheoMonPresenterImp;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.Normalizer;
import java.util.ArrayList;

/**
 * Created by yowin on 12/12/2017.
 */

public class TimKiemTheoMonModel implements TimKiemTheoMonModelImp {

    DatabaseReference mData = FirebaseDatabase.getInstance().getReference();
    TimKiemTheoMonPresenterImp timKiemTheoMonPresenterImp;
    ArrayList<CustomModelKhoaHoc> listKhoaHoc = new ArrayList<>();

    public TimKiemTheoMonModel(TimKiemTheoMonPresenterImp timKiemTheoMonPresenterImp) {
        this.timKiemTheoMonPresenterImp = timKiemTheoMonPresenterImp;
    }

    @Override
    public void getListKhoaHoc(final String mon, boolean loai) {
       if(loai)
       {
            mData.child("KhoaHoc").child("KhoaHocTimGiaSu").child("ChuaHoanTat").addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    KhoaHoc kh = dataSnapshot.getValue(KhoaHoc.class);
                    try {
                        if(URLEncoder.encode(removeDiacriticalMarks(mon),"utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(kh.getMon().get(0)),"utf-8")))
                        {
                            KhoaHoc khoaHoc=new KhoaHoc();
                            khoaHoc=dataSnapshot.getValue(KhoaHoc.class);
                            listKhoaHoc.add(new CustomModelKhoaHoc(khoaHoc.getAvatar(),khoaHoc.getRating(),khoaHoc.getHoTen(),khoaHoc.getNguoiDang(),khoaHoc.getSoBuoiHoc(),khoaHoc.getSoLuongHocVien(),khoaHoc.getGioiTinh(),khoaHoc.getNgayDang(),khoaHoc.getGioDang(),khoaHoc.getThoiLuongBuoiHoc(),khoaHoc.getHocPhi(),khoaHoc.getThongTinKhac(),khoaHoc.getBangCap(),khoaHoc.getMon(),khoaHoc.getLinhVuc(),khoaHoc.getLichHoc(),khoaHoc.getDiaDiem(),khoaHoc.getDanhSachYeuCau(),dataSnapshot.getKey()));
                            timKiemTheoMonPresenterImp.nhanListKhoaHoc(listKhoaHoc);
                        }
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
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
           mData.child("KhoaHoc").child("KhoaHocTimHocVien").child("ChuaHoanTat").addChildEventListener(new ChildEventListener() {
               @Override
               public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                   KhoaHoc kh = dataSnapshot.getValue(KhoaHoc.class);
                   try {
                       if(URLEncoder.encode(removeDiacriticalMarks(mon),"utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(kh.getMon().get(0)),"utf-8")))
                       {
                           KhoaHoc khoaHoc=new KhoaHoc();
                           khoaHoc=dataSnapshot.getValue(KhoaHoc.class);
                           listKhoaHoc.add(new CustomModelKhoaHoc(khoaHoc.getAvatar(),khoaHoc.getRating(),khoaHoc.getHoTen(),khoaHoc.getNguoiDang(),khoaHoc.getSoBuoiHoc(),khoaHoc.getSoLuongHocVien(),khoaHoc.getGioiTinh(),khoaHoc.getNgayDang(),khoaHoc.getGioDang(),khoaHoc.getThoiLuongBuoiHoc(),khoaHoc.getHocPhi(),khoaHoc.getThongTinKhac(),khoaHoc.getBangCap(),khoaHoc.getMon(),khoaHoc.getLinhVuc(),khoaHoc.getLichHoc(),khoaHoc.getDiaDiem(),khoaHoc.getDanhSachYeuCau(),dataSnapshot.getKey()));
                           timKiemTheoMonPresenterImp.nhanListKhoaHoc(listKhoaHoc);
                       }
                   } catch (UnsupportedEncodingException e) {
                       e.printStackTrace();
                   }
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
    //Gỡ dấu

    public static String removeDiacriticalMarks(String string) {
        return Normalizer.normalize(string, Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
    }
}
