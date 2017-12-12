package com.eways.elearning.Model.TimKiemKhoaHoc;

import com.eways.elearning.DataModel.KhoaHoc.CustomModelKhoaHoc;
import com.eways.elearning.DataModel.KhoaHoc.KhoaHoc;
import com.eways.elearning.Presenter.TimKiemKhoaHoc.KetQuaTimKiemKhoaHocFragmentPresenterImp;
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
 * Created by yowin on 25/11/2017.
 */

public class KetQuaTimKiemFragmentModel implements KetQuatimKiemFragmentModelImp {

    KetQuaTimKiemKhoaHocFragmentPresenterImp ketQuaTimKiemKhoaHocFragmentPresenterImp;
    DatabaseReference mData = FirebaseDatabase.getInstance().getReference();
    ArrayList<CustomModelKhoaHoc> khoaHocChinhXac = new ArrayList<>();
    ArrayList<CustomModelKhoaHoc> khoaHocGanChinhXac = new ArrayList<>();
    int countTemp = 0;

    public KetQuaTimKiemFragmentModel(KetQuaTimKiemKhoaHocFragmentPresenterImp ketQuaTimKiemKhoaHocFragmentPresenterImp) {
        this.ketQuaTimKiemKhoaHocFragmentPresenterImp = ketQuaTimKiemKhoaHocFragmentPresenterImp;
    }

    @Override
    public void getListKhoaHoc(final KhoaHoc khoaHoc, boolean loai) {
        final int count = cout(khoaHoc.getLichHoc().getNgayHoc().size()); //Nếu /2 mà <1 lấy 0 còn lại lấy /2 bt
        final double hocPhiTimKiem = Double.parseDouble(khoaHoc.getHocPhi());
        final double hocPhiTren = (hocPhiTimKiem + ((hocPhiTimKiem / 100) * 10));
        final double hocPhiDuoi = (hocPhiTimKiem - ((hocPhiTimKiem / 100) * 10));

        if(!loai)
        {
            if(khoaHoc.getMon() == null)
            {
                if(khoaHoc.getLinhVuc() == null )
                {
                    if(khoaHoc.getDiaDiem().getDayDu().isEmpty())
                    {
                        if(hocPhiTimKiem == 0)
                        {
                            if(khoaHoc.getGioiTinh().equals("Nam, Nữ"))
                            {
                                if(khoaHoc.getBangCap() == null) //Tất cả empty || có thể ngoại trừ buổi hoặc thứ
                                {
                                    mData.child("KhoaHoc").child("KhoaHocTimGiaSu").child("ChuaHoanTat").addChildEventListener(new ChildEventListener() {
                                        @Override
                                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                            KhoaHoc kh = dataSnapshot.getValue(KhoaHoc.class);
                                            khoaHocGanChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                            for(String buoi : kh.getLichHoc().getThoiGian())
                                            {
                                                for(String sbuoi : khoaHoc.getLichHoc().getThoiGian())
                                                {
                                                    if(sbuoi.equals(buoi))
                                                    {
                                                       for(String thu : kh.getLichHoc().getNgayHoc())
                                                       {
                                                           for(String sthu : khoaHoc.getLichHoc().getNgayHoc())
                                                           {
                                                               if (sthu.equals(thu)) {
                                                                   countTemp += 1;
                                                                   if (countTemp == count) {
                                                                       khoaHocChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                                   }
                                                               }
                                                           }
                                                       }
                                                    }
                                                }
                                            }
                                            ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHoc(khoaHocChinhXac,khoaHocGanChinhXac);
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
                                else //Tất cả empty ngoại trừ bằng cấp || có thể ngoại trừ buổi hoặc thứ
                                {
                                    mData.child("KhoaHoc").child("KhoaHocTimGiaSu").child("KhoaHocChuaHoanTat").addChildEventListener(new ChildEventListener() {
                                        @Override
                                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                            KhoaHoc kh = dataSnapshot.getValue(KhoaHoc.class);
                                            for(String bangcap : kh.getBangCap())
                                            {
                                                try
                                                {
                                                    if(URLEncoder.encode(removeDiacriticalMarks(bangcap),"utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getBangCap().get(0)).toLowerCase(),"utf-8")))
                                                    {
                                                        khoaHocGanChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                        for(String buoi : kh.getLichHoc().getThoiGian())
                                                        {
                                                            for(String sbuoi : khoaHoc.getLichHoc().getThoiGian())
                                                            {
                                                                if(sbuoi.equals(buoi))
                                                                {
                                                                    for(String thu : kh.getLichHoc().getNgayHoc())
                                                                    {
                                                                        for(String sthu : khoaHoc.getLichHoc().getNgayHoc())
                                                                        {
                                                                            if (sthu.equals(thu)) {
                                                                                countTemp += 1;
                                                                                if (countTemp == count) {
                                                                                    khoaHocChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                                catch (UnsupportedEncodingException e)
                                                {
                                                    e.printStackTrace();
                                                }
                                            }
                                            ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHoc(khoaHocChinhXac,khoaHocGanChinhXac);
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
                            else
                            {
                                if(khoaHoc.getBangCap() == null) // Tất cả empty hết ngoại trừ giới tính ||có thể ngoại trừ buổi hoặc thứ
                                {
                                    mData.child("KhoaHoc").child("KhoaHocTimGiaSu").child("ChuaHoanTat").addChildEventListener(new ChildEventListener() {
                                        @Override
                                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                            KhoaHoc kh = dataSnapshot.getValue(KhoaHoc.class);
                                            if(kh.getGioiTinh().equals(khoaHoc.getGioiTinh()))
                                            {
                                                khoaHocGanChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                for(String buoi : kh.getLichHoc().getThoiGian())
                                                {
                                                    for(String sbuoi : khoaHoc.getLichHoc().getThoiGian())
                                                    {
                                                        if(sbuoi.equals(buoi))
                                                        {
                                                            for(String thu : kh.getLichHoc().getNgayHoc())
                                                            {
                                                                for(String sthu : khoaHoc.getLichHoc().getNgayHoc())
                                                                {
                                                                    if (sthu.equals(thu)) {
                                                                        countTemp += 1;
                                                                        if (countTemp == count) {
                                                                            khoaHocChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                            ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHoc(khoaHocChinhXac,khoaHocGanChinhXac);
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
                                else // Tất cả empty hết ngoại trừ giới tinh, bằng cấp || có thể ngoại trừ buổi hoặc thứ
                                {
                                   mData.child("KhoaHoc").child("KhoaHocTimGiaSu").child("ChuaHoanTat").addChildEventListener(new ChildEventListener() {
                                       @Override
                                       public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                           KhoaHoc kh = dataSnapshot.getValue(KhoaHoc.class);
                                           for(String bangcap : kh.getBangCap())
                                           {
                                               try
                                               {
                                                   if (URLEncoder.encode(removeDiacriticalMarks(bangcap), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getBangCap().get(0)).toLowerCase(), "utf-8")))
                                                   {
                                                       khoaHocGanChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                       if(kh.getGioiTinh().equals(khoaHoc.getGioiTinh()))
                                                       {
                                                           for(String buoi : kh.getLichHoc().getThoiGian())
                                                           {
                                                               for(String sbuoi : khoaHoc.getLichHoc().getThoiGian())
                                                               {
                                                                   if(sbuoi.equals(buoi))
                                                                   {
                                                                       for(String thu : kh.getLichHoc().getNgayHoc())
                                                                       {
                                                                           for(String sthu : khoaHoc.getLichHoc().getNgayHoc())
                                                                           {
                                                                               if (sthu.equals(thu)) {
                                                                                   countTemp += 1;
                                                                                   if (countTemp == count) {
                                                                                       khoaHocChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                                                   }
                                                                               }
                                                                           }
                                                                       }
                                                                   }
                                                               }
                                                           }
                                                       }
                                                   }
                                               } catch (UnsupportedEncodingException e)
                                               {

                                                   e.printStackTrace();
                                               }
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
                        }
                        else
                        {
                            if(khoaHoc.getGioiTinh().equals("Nam, Nữ"))
                            {
                                if(khoaHoc.getBangCap() == null) // tất cả empty hết ngoại trừ học phí || có thể ngoại trừ buổi hoặc thứ
                                {
                                    mData.child("KhoaHoc").child("KhoaHocTimGiaSu").child("ChuaHoanTat").addChildEventListener(new ChildEventListener() {
                                        @Override
                                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                            KhoaHoc kh = dataSnapshot.getValue(KhoaHoc.class);
                                            if(Double.parseDouble(kh.getHocPhi())>=hocPhiDuoi && Double.parseDouble(kh.getHocPhi())<=hocPhiTren) {
                                                for (String buoi : kh.getLichHoc().getThoiGian()) {
                                                    for (String sbuoi : khoaHoc.getLichHoc().getThoiGian()) {
                                                        khoaHocGanChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                        if (sbuoi.equals(buoi)) {
                                                            for (String thu : kh.getLichHoc().getNgayHoc()) {
                                                                for (String sthu : khoaHoc.getLichHoc().getNgayHoc()) {
                                                                    if (sthu.equals(thu)) {
                                                                        countTemp += 1;
                                                                        if (countTemp == count) {
                                                                            khoaHocChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                            ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHoc(khoaHocChinhXac,khoaHocGanChinhXac);
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
                                else // Tất cả empty hết ngoại trừ học phí, bằng cấp || có thể ngoại trừ buổi hoặc thứ
                                {
                                    mData.child("KhoaHoc").child("KhoaHocTimGiaSu").child("ChuaHoanTat").addChildEventListener(new ChildEventListener() {
                                        @Override
                                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                            KhoaHoc kh = dataSnapshot.getValue(KhoaHoc.class);
                                            for(String bangcap : kh.getBangCap())
                                            {
                                                try
                                                {
                                                    if (URLEncoder.encode(removeDiacriticalMarks(bangcap), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getBangCap().get(0)).toLowerCase(), "utf-8")))
                                                    {
                                                        khoaHocGanChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                        if(Double.parseDouble(kh.getHocPhi())>=hocPhiDuoi && Double.parseDouble(kh.getHocPhi())<=hocPhiTren) {
                                                            for (String buoi : kh.getLichHoc().getThoiGian()) {
                                                                for (String sbuoi : khoaHoc.getLichHoc().getThoiGian()) {
                                                                    if (sbuoi.equals(buoi)) {
                                                                        for (String thu : kh.getLichHoc().getNgayHoc()) {
                                                                            for (String sthu : khoaHoc.getLichHoc().getNgayHoc()) {
                                                                                if (sthu.equals(thu)) {
                                                                                    countTemp += 1;
                                                                                    if (countTemp == count) {
                                                                                        khoaHocChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                                catch (UnsupportedEncodingException e)
                                                {
                                                    e.printStackTrace();
                                                }
                                            }
                                            ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHoc(khoaHocChinhXac,khoaHocGanChinhXac);
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
                            else
                            {
                                if(khoaHoc.getBangCap() == null) // Tất cả empty hết ngoại trừ học phí, giới tính || có thể ngoại trừ buổi hoặc thứ
                                {
                                    mData.child("KhoaHoc").child("KhoaHocTimGiaSu").child("ChuaHoanTat").addChildEventListener(new ChildEventListener() {
                                        @Override
                                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                            KhoaHoc kh = dataSnapshot.getValue(KhoaHoc.class);
                                            if(kh.getGioiTinh().equals(khoaHoc.getGioiTinh()))
                                            {
                                                khoaHocGanChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                if(Double.parseDouble(kh.getHocPhi())>=hocPhiDuoi && Double.parseDouble(kh.getHocPhi())<=hocPhiTren)
                                                {
                                                    for(String buoi : kh.getLichHoc().getThoiGian())
                                                    {
                                                        for(String sbuoi : khoaHoc.getLichHoc().getThoiGian())
                                                        {
                                                            if(sbuoi.equals(buoi))
                                                            {
                                                                for(String thu : kh.getLichHoc().getNgayHoc())
                                                                {
                                                                    for(String sthu : khoaHoc.getLichHoc().getNgayHoc())
                                                                    {
                                                                        if (sthu.equals(thu)) {
                                                                            countTemp += 1;
                                                                            if (countTemp == count) {
                                                                                khoaHocChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                            ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHoc(khoaHocChinhXac,khoaHocGanChinhXac);

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
                                else // Tất cả empty hết ngoại trừ học phí, giới tính, bằng cấp || có thể ngoại trừ buổi hoặc thứ
                                {
                                    mData.child("KhoaHoc").child("KhoaHocTimGiaSu").child("ChuaHoanTat").addChildEventListener(new ChildEventListener() {
                                        @Override
                                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                            KhoaHoc kh = dataSnapshot.getValue(KhoaHoc.class);
                                            if(kh.getGioiTinh().equals(khoaHoc.getGioiTinh()))
                                            {
                                                khoaHocGanChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                for(String bangcap : kh.getBangCap())
                                                {

                                                    try {
                                                        if(URLEncoder.encode(removeDiacriticalMarks(bangcap),"utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getBangCap().get(0)).toLowerCase(),"utf-8")))
                                                        {
                                                            if(Double.parseDouble(kh.getHocPhi())>=hocPhiDuoi && Double.parseDouble(kh.getHocPhi())<=hocPhiTren)
                                                            {
                                                                for(String buoi : kh.getLichHoc().getThoiGian())
                                                                {
                                                                    for(String sbuoi : khoaHoc.getLichHoc().getThoiGian())
                                                                    {
                                                                        if(sbuoi.equals(buoi))
                                                                        {
                                                                            for(String thu : kh.getLichHoc().getNgayHoc())
                                                                            {
                                                                                for(String sthu : khoaHoc.getLichHoc().getNgayHoc())
                                                                                {
                                                                                    if (sthu.equals(thu)) {
                                                                                        countTemp += 1;
                                                                                        if (countTemp == count) {
                                                                                            khoaHocChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                                                        }
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    } catch (UnsupportedEncodingException e) {
                                                        e.printStackTrace();
                                                    }
                                                }
                                            }
                                            ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHoc(khoaHocChinhXac,khoaHocGanChinhXac);
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
                    }
                    else
                    {
                        if(hocPhiTimKiem == 0)
                        {
                            if(khoaHoc.getGioiTinh().equals("Nam, Nữ"))
                            {
                                if(khoaHoc.getBangCap() == null)//Tất cả empty hết ngoại trừ Địa điểm || có thể ngoại trừ buổi hoặc thứ
                                {
                                    mData.child("KhoaHoc").child("KhoaHocTimGiaSu").child("ChuaHoanTat").addChildEventListener(new ChildEventListener() {
                                        @Override
                                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                            KhoaHoc kh = dataSnapshot.getValue(KhoaHoc.class);
                                            try {
                                                if(URLEncoder.encode(removeDiacriticalMarks(kh.getDiaDiem().getQuan()).toLowerCase(),"utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getDiaDiem().getDayDu()).toLowerCase(),"utf-8")))
                                                {
                                                    khoaHocGanChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                    for(String buoi : kh.getLichHoc().getThoiGian())
                                                    {
                                                        for(String sbuoi : khoaHoc.getLichHoc().getThoiGian())
                                                        {
                                                            if(sbuoi.equals(buoi))
                                                            {
                                                                for(String thu : kh.getLichHoc().getNgayHoc())
                                                                {
                                                                    for(String sthu : khoaHoc.getLichHoc().getNgayHoc())
                                                                    {
                                                                        if (sthu.equals(thu)) {
                                                                            countTemp += 1;
                                                                            if (countTemp == count) {
                                                                                khoaHocChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            } catch (UnsupportedEncodingException e) {
                                                e.printStackTrace();
                                            }
                                            ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHoc(khoaHocChinhXac,khoaHocGanChinhXac);
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
                                else//Tất cả empty hết ngoại trừ địa điểm , bằng cấp || có thể ngoại trừ buổi hoặc thứ
                                {
                                    mData.child("KhoaHoc").child("KhoaHocTimGiaSu").child("ChuaHoanTat").addChildEventListener(new ChildEventListener() {
                                        @Override
                                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                            KhoaHoc kh = dataSnapshot.getValue(KhoaHoc.class);
                                            try {
                                                if(URLEncoder.encode(removeDiacriticalMarks(kh.getDiaDiem().getQuan()).toLowerCase(),"utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getDiaDiem().getDayDu()).toLowerCase(),"utf-8")))
                                                {
                                                    for(String bangcap : kh.getBangCap())
                                                    {

                                                        if(URLEncoder.encode(removeDiacriticalMarks(bangcap),"utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getBangCap().get(0)).toLowerCase(),"utf-8")))
                                                        {
                                                            khoaHocGanChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                            for(String buoi : kh.getLichHoc().getThoiGian())
                                                            {
                                                                for(String sbuoi : khoaHoc.getLichHoc().getThoiGian())
                                                                {
                                                                    if(sbuoi.equals(buoi))
                                                                    {
                                                                        for(String thu : kh.getLichHoc().getNgayHoc())
                                                                        {
                                                                            for(String sthu : khoaHoc.getLichHoc().getNgayHoc())
                                                                            {
                                                                                if (sthu.equals(thu)) {
                                                                                    countTemp += 1;
                                                                                    if (countTemp == count) {
                                                                                        khoaHocChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            } catch (UnsupportedEncodingException e) {
                                                e.printStackTrace();
                                            }
                                            ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHoc(khoaHocChinhXac,khoaHocGanChinhXac);

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
                            else
                            {
                                if(khoaHoc.getBangCap() == null)//Tất cả empty ngoại trừ địa điểm, giới tính || có thể ngoại trừ buổi hoặc thứ
                                {
                                    mData.child("KhoaHoc").child("KhoaHocTimGiaSu").child("ChuaHoanTat").addChildEventListener(new ChildEventListener() {
                                        @Override
                                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                            KhoaHoc kh = dataSnapshot.getValue(KhoaHoc.class);
                                            try {
                                                if(URLEncoder.encode(removeDiacriticalMarks(kh.getDiaDiem().getQuan()).toLowerCase(),"utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getDiaDiem().getDayDu()).toLowerCase(),"utf-8")))
                                                {
                                                    if(kh.getGioiTinh().equals(khoaHoc.getGioiTinh()))
                                                    {
                                                        khoaHocGanChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                        for(String buoi : kh.getLichHoc().getThoiGian())
                                                        {
                                                            for(String sbuoi : khoaHoc.getLichHoc().getThoiGian())
                                                            {
                                                                if(sbuoi.equals(buoi))
                                                                {
                                                                    for(String thu : kh.getLichHoc().getNgayHoc())
                                                                    {
                                                                        for(String sthu : khoaHoc.getLichHoc().getNgayHoc())
                                                                        {
                                                                            if (sthu.equals(thu)) {
                                                                                countTemp += 1;
                                                                                if (countTemp == count) {
                                                                                    khoaHocChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            } catch (UnsupportedEncodingException e) {
                                                e.printStackTrace();
                                            }
                                            ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHoc(khoaHocChinhXac,khoaHocGanChinhXac);

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
                                else //tất cả empty ngoại trừ địa điểm, giới tính, bằng cấp || có thể ngoại trừ buổi hoặc thứ
                                {
                                    mData.child("KhoaHoc").child("KhoaHocTimGiaSu").child("ChuaHoanTat").addChildEventListener(new ChildEventListener() {
                                        @Override
                                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                            KhoaHoc kh = dataSnapshot.getValue(KhoaHoc.class);
                                            try {
                                                if(URLEncoder.encode(removeDiacriticalMarks(kh.getDiaDiem().getQuan()).toLowerCase(),"utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getDiaDiem().getDayDu()).toLowerCase(),"utf-8")))
                                                {
                                                    if(kh.getGioiTinh().equals(khoaHoc.getGioiTinh()))
                                                    {
                                                        for(String bangcap : kh.getBangCap())
                                                        {

                                                            if(URLEncoder.encode(removeDiacriticalMarks(bangcap),"utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getBangCap().get(0)).toLowerCase(),"utf-8")))
                                                            {
                                                                khoaHocGanChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                                for(String buoi : kh.getLichHoc().getThoiGian())
                                                                {
                                                                    for(String sbuoi : khoaHoc.getLichHoc().getThoiGian())
                                                                    {
                                                                        if(sbuoi.equals(buoi))
                                                                        {
                                                                            for(String thu : kh.getLichHoc().getNgayHoc())
                                                                            {
                                                                                for(String sthu : khoaHoc.getLichHoc().getNgayHoc())
                                                                                {
                                                                                    if (sthu.equals(thu)) {
                                                                                        countTemp += 1;
                                                                                        if (countTemp == count) {
                                                                                            khoaHocChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                                                        }
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            } catch (UnsupportedEncodingException e) {
                                                e.printStackTrace();
                                            }
                                            ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHoc(khoaHocChinhXac,khoaHocGanChinhXac);
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
                        else
                        {
                            if(khoaHoc.getGioiTinh().equals("Nam, Nữ"))
                            {
                                if(khoaHoc.getBangCap() == null)//Tất cả empty ngoại trừ địa điểm, học phí || có thể ngoại trừ buổi hoặc thứ
                                {
                                    mData.child("KhoaHoc").child("KhoaHocTimGiaSu").child("ChuaHoanTat").addChildEventListener(new ChildEventListener() {
                                        @Override
                                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                            KhoaHoc kh = dataSnapshot.getValue(KhoaHoc.class);

                                            try {
                                                if(URLEncoder.encode(removeDiacriticalMarks(kh.getDiaDiem().getQuan()).toLowerCase(),"utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getDiaDiem().getDayDu()).toLowerCase(),"utf-8")))
                                                {
                                                    if(Double.parseDouble(kh.getHocPhi())>=hocPhiDuoi && Double.parseDouble(kh.getHocPhi())<=hocPhiTren)
                                                    {
                                                        khoaHocGanChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                        for(String buoi : kh.getLichHoc().getThoiGian())
                                                        {
                                                            for(String sbuoi : khoaHoc.getLichHoc().getThoiGian())
                                                            {
                                                                if(sbuoi.equals(buoi))
                                                                {
                                                                    for(String thu : kh.getLichHoc().getNgayHoc())
                                                                    {
                                                                        for(String sthu : khoaHoc.getLichHoc().getNgayHoc())
                                                                        {
                                                                            if (sthu.equals(thu)) {
                                                                                countTemp += 1;
                                                                                if (countTemp == count) {
                                                                                    khoaHocChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            } catch (UnsupportedEncodingException e) {
                                                e.printStackTrace();
                                            }
                                            ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHoc(khoaHocChinhXac,khoaHocGanChinhXac);
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
                                else//Tất cả empty ngoại trừ địa điểm, học phí, bằng cấp || có thể ngoại trừ buổi hoặc thứ
                                {
                                    mData.child("KhoaHoc").child("KhoaHocTimGiaSu").child("ChuaHoanTat").addChildEventListener(new ChildEventListener() {
                                        @Override
                                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                            KhoaHoc kh = dataSnapshot.getValue(KhoaHoc.class);

                                            try {
                                                if(URLEncoder.encode(removeDiacriticalMarks(kh.getDiaDiem().getQuan()).toLowerCase(),"utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getDiaDiem().getDayDu()).toLowerCase(),"utf-8")))
                                                {
                                                    if(Double.parseDouble(kh.getHocPhi())>=hocPhiDuoi && Double.parseDouble(kh.getHocPhi())<=hocPhiTren)
                                                    {
                                                        for(String bangcap : kh.getBangCap())
                                                        {

                                                            if(URLEncoder.encode(removeDiacriticalMarks(bangcap),"utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getBangCap().get(0)).toLowerCase(),"utf-8")))
                                                            {
                                                                khoaHocGanChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                                for(String buoi : kh.getLichHoc().getThoiGian())
                                                                {
                                                                    for(String sbuoi : khoaHoc.getLichHoc().getThoiGian())
                                                                    {
                                                                        if(sbuoi.equals(buoi))
                                                                        {
                                                                            for(String thu : kh.getLichHoc().getNgayHoc())
                                                                            {
                                                                                for(String sthu : khoaHoc.getLichHoc().getNgayHoc())
                                                                                {
                                                                                    if (sthu.equals(thu)) {
                                                                                        countTemp += 1;
                                                                                        if (countTemp == count) {
                                                                                            khoaHocChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                                                        }
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            } catch (UnsupportedEncodingException e) {
                                                e.printStackTrace();
                                            }
                                            ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHoc(khoaHocChinhXac,khoaHocGanChinhXac);
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
                            else
                            {
                                if(khoaHoc.getBangCap() == null)//Tất cả empty ngoại trừ địa điểm, học phí, giới tính || có thể ngoại trừ buổi hoặc thứ
                                {
                                    mData.child("KhoaHoc").child("KhoaHocTimGiaSu").child("ChuaHoanTat").addChildEventListener(new ChildEventListener() {
                                        @Override
                                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                            KhoaHoc kh = dataSnapshot.getValue(KhoaHoc.class);

                                            try {
                                                if(URLEncoder.encode(removeDiacriticalMarks(kh.getDiaDiem().getQuan()).toLowerCase(),"utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getDiaDiem().getDayDu()).toLowerCase(),"utf-8")))
                                                {
                                                    if(Double.parseDouble(kh.getHocPhi())>=hocPhiDuoi && Double.parseDouble(kh.getHocPhi())<=hocPhiTren)
                                                    {
                                                        if(kh.getGioiTinh().equals(khoaHoc.getGioiTinh()))
                                                        {
                                                            khoaHocGanChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                            for(String buoi : kh.getLichHoc().getThoiGian())
                                                            {
                                                                for(String sbuoi : khoaHoc.getLichHoc().getThoiGian())
                                                                {
                                                                    if(sbuoi.equals(buoi))
                                                                    {
                                                                        for(String thu : kh.getLichHoc().getNgayHoc())
                                                                        {
                                                                            for(String sthu : khoaHoc.getLichHoc().getNgayHoc())
                                                                            {
                                                                                if (sthu.equals(thu)) {
                                                                                    countTemp += 1;
                                                                                    if (countTemp == count) {
                                                                                        khoaHocChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            } catch (UnsupportedEncodingException e) {
                                                e.printStackTrace();
                                            }
                                            ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHoc(khoaHocChinhXac,khoaHocGanChinhXac);
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
                                else//Tất cả empty ngoại trừ địa điểm, học phí, giới tính, bằng cấp ||có thể ngoại trừ buổi hoặc thứ
                                {
                                    mData.child("KhoaHoc").child("KhoaHocTimGiaSu").child("ChuaHoanTat").addChildEventListener(new ChildEventListener() {
                                        @Override
                                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                            KhoaHoc kh = dataSnapshot.getValue(KhoaHoc.class);

                                            try {
                                                if(URLEncoder.encode(removeDiacriticalMarks(kh.getDiaDiem().getQuan()).toLowerCase(),"utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getDiaDiem().getDayDu()).toLowerCase(),"utf-8")))
                                                {
                                                    if(kh.getGioiTinh().equals(khoaHoc.getGioiTinh()))
                                                    {
                                                        if(Double.parseDouble(kh.getHocPhi())>=hocPhiDuoi && Double.parseDouble(kh.getHocPhi())<=hocPhiTren)
                                                        {
                                                            for(String bangcap : kh.getBangCap())
                                                            {

                                                                if(URLEncoder.encode(removeDiacriticalMarks(bangcap),"utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getBangCap().get(0)).toLowerCase(),"utf-8")))
                                                                {
                                                                    khoaHocGanChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                                    for(String buoi : kh.getLichHoc().getThoiGian())
                                                                    {
                                                                        for(String sbuoi : khoaHoc.getLichHoc().getThoiGian())
                                                                        {
                                                                            if(sbuoi.equals(buoi))
                                                                            {
                                                                                for(String thu : kh.getLichHoc().getNgayHoc())
                                                                                {
                                                                                    for(String sthu : khoaHoc.getLichHoc().getNgayHoc())
                                                                                    {
                                                                                        if (sthu.equals(thu)) {
                                                                                            countTemp += 1;
                                                                                            if (countTemp == count) {
                                                                                                khoaHocChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                                                            }
                                                                                        }
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }

                                            } catch (UnsupportedEncodingException e) {
                                                e.printStackTrace();
                                            }
                                            ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHoc(khoaHocChinhXac,khoaHocGanChinhXac);
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
                    }
                }
                else
                {
                    if(khoaHoc.getDiaDiem().getDayDu().isEmpty())
                    {
                        if(hocPhiTimKiem == 0)
                        {
                            if(khoaHoc.getGioiTinh().equals("Nam, Nữ"))
                            {
                                if(khoaHoc.getBangCap() == null) //Tất cả empty ngoài trừ Lĩnh vực
                                {
                                    mData.child("KhoaHoc").child("KhoaHocTimGiaSu").child("ChuaHoanTat").addChildEventListener(new ChildEventListener() {
                                        @Override
                                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                            KhoaHoc kh = dataSnapshot.getValue(KhoaHoc.class);

                                            for(String linhVuc : kh.getLinhVuc())
                                            {
                                                try {
                                                    if(URLEncoder.encode(removeDiacriticalMarks(linhVuc),"utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getLinhVuc().get(0)).toLowerCase(),"utf-8")))
                                                    {
                                                        khoaHocGanChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                        for(String buoi : kh.getLichHoc().getThoiGian())
                                                        {
                                                            for(String sbuoi : khoaHoc.getLichHoc().getThoiGian())
                                                            {
                                                                if(sbuoi.equals(buoi))
                                                                {
                                                                    for(String thu : kh.getLichHoc().getNgayHoc())
                                                                    {
                                                                        for(String sthu : khoaHoc.getLichHoc().getNgayHoc())
                                                                        {
                                                                            if (sthu.equals(thu)) {
                                                                                countTemp += 1;
                                                                                if (countTemp == count) {
                                                                                    khoaHocChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                } catch (UnsupportedEncodingException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                            ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHoc(khoaHocChinhXac,khoaHocGanChinhXac);
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
                                else //Tất cả empty ngoại trừ Lịnh vực, bằng cấp
                                {
                                    mData.child("KhoaHoc").child("KhoaHocTimGiaSu").child("ChuaHoanTat").addChildEventListener(new ChildEventListener() {
                                        @Override
                                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                            KhoaHoc kh = dataSnapshot.getValue(KhoaHoc.class);

                                            for(String linhVuc : kh.getLinhVuc())
                                            {
                                                try {
                                                    if(URLEncoder.encode(removeDiacriticalMarks(linhVuc),"utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getLinhVuc().get(0)).toLowerCase(),"utf-8")))
                                                    {
                                                        for(String bangcap : kh.getBangCap())
                                                        {

                                                            if(URLEncoder.encode(removeDiacriticalMarks(bangcap),"utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getBangCap().get(0)).toLowerCase(),"utf-8")))
                                                            {
                                                                khoaHocGanChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                                for(String buoi : kh.getLichHoc().getThoiGian())
                                                                {
                                                                    for(String sbuoi : khoaHoc.getLichHoc().getThoiGian())
                                                                    {
                                                                        if(sbuoi.equals(buoi))
                                                                        {
                                                                            for(String thu : kh.getLichHoc().getNgayHoc())
                                                                            {
                                                                                for(String sthu : khoaHoc.getLichHoc().getNgayHoc())
                                                                                {
                                                                                    if (sthu.equals(thu)) {
                                                                                        countTemp += 1;
                                                                                        if (countTemp == count) {
                                                                                            khoaHocChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                                                        }
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                } catch (UnsupportedEncodingException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                            ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHoc(khoaHocChinhXac,khoaHocGanChinhXac);
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
                            else
                            {
                                if(khoaHoc.getBangCap() == null) //Tất cả empty ngoại trừ Lĩnh vực, giới tính
                                {
                                    mData.child("KhoaHoc").child("KhoaHocTimGiaSu").child("ChuaHoanTat").addChildEventListener(new ChildEventListener() {
                                        @Override
                                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                            KhoaHoc kh =dataSnapshot.getValue(KhoaHoc.class);
                                            for(String linhVuc : kh.getLinhVuc())
                                            {
                                                try {
                                                    if(URLEncoder.encode(removeDiacriticalMarks(linhVuc),"utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getLinhVuc().get(0)).toLowerCase(),"utf-8")))
                                                    {
                                                        if(kh.getGioiTinh().equals(khoaHoc.getGioiTinh()))
                                                        {
                                                            khoaHocGanChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                            for(String buoi : kh.getLichHoc().getThoiGian())
                                                            {
                                                                for(String sbuoi : khoaHoc.getLichHoc().getThoiGian())
                                                                {
                                                                    if(sbuoi.equals(buoi))
                                                                    {
                                                                        for(String thu : kh.getLichHoc().getNgayHoc())
                                                                        {
                                                                            for(String sthu : khoaHoc.getLichHoc().getNgayHoc())
                                                                            {
                                                                                if (sthu.equals(thu)) {
                                                                                    countTemp += 1;
                                                                                    if (countTemp == count) {
                                                                                        khoaHocChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                } catch (UnsupportedEncodingException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                            ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHoc(khoaHocChinhXac,khoaHocGanChinhXac);
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
                                else//tất cả empty ngoại trừ Lĩn vực, giới tính, bằng cấp
                                {
                                    mData.child("KhoaHoc").child("KhoaHocTimGiaSu").child("ChuaHoanTat").addChildEventListener(new ChildEventListener() {
                                        @Override
                                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                            KhoaHoc kh = dataSnapshot.getValue(KhoaHoc.class);

                                            for(String linhVuc : kh.getLinhVuc())
                                            {
                                                try {
                                                    if(URLEncoder.encode(removeDiacriticalMarks(linhVuc),"utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getLinhVuc().get(0)).toLowerCase(),"utf-8")))
                                                    {
                                                        for(String bangcap : kh.getBangCap())
                                                        {

                                                            if(URLEncoder.encode(removeDiacriticalMarks(bangcap),"utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getBangCap().get(0)).toLowerCase(),"utf-8")))
                                                            {
                                                                if(kh.getGioiTinh().equals(khoaHoc.getGioiTinh()))
                                                                {
                                                                    khoaHocGanChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                                    for(String buoi : kh.getLichHoc().getThoiGian())
                                                                    {
                                                                        for(String sbuoi : khoaHoc.getLichHoc().getThoiGian())
                                                                        {
                                                                            if(sbuoi.equals(buoi))
                                                                            {
                                                                                for(String thu : kh.getLichHoc().getNgayHoc())
                                                                                {
                                                                                    for(String sthu : khoaHoc.getLichHoc().getNgayHoc())
                                                                                    {
                                                                                        if (sthu.equals(thu)) {
                                                                                            countTemp += 1;
                                                                                            if (countTemp == count) {
                                                                                                khoaHocChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                                                            }
                                                                                        }
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                } catch (UnsupportedEncodingException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                            ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHoc(khoaHocChinhXac,khoaHocGanChinhXac);
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
                        else
                        {
                            if(khoaHoc.getGioiTinh().equals("Nam, Nữ"))
                            {
                                if(khoaHoc.getBangCap() == null)//Tất cả empty ngoại trừ Lĩnh vực, học phí
                                {

                                    mData.child("KhoaHoc").child("KhoaHocTimGiaSu").child("ChuaHoanTat").addChildEventListener(new ChildEventListener() {
                                        @Override
                                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                            KhoaHoc kh = dataSnapshot.getValue(KhoaHoc.class);

                                            for(String linhVuc : kh.getLinhVuc())
                                            {
                                                try {
                                                    if(URLEncoder.encode(removeDiacriticalMarks(linhVuc),"utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getLinhVuc().get(0)).toLowerCase(),"utf-8")))
                                                    {
                                                        if(Double.parseDouble(kh.getHocPhi())>=hocPhiDuoi && Double.parseDouble(kh.getHocPhi())<=hocPhiTren)
                                                        {
                                                            khoaHocGanChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                            for(String buoi : kh.getLichHoc().getThoiGian())
                                                            {
                                                                for(String sbuoi : khoaHoc.getLichHoc().getThoiGian())
                                                                {
                                                                    if(sbuoi.equals(buoi))
                                                                    {
                                                                        for(String thu : kh.getLichHoc().getNgayHoc())
                                                                        {
                                                                            for(String sthu : khoaHoc.getLichHoc().getNgayHoc())
                                                                            {
                                                                                if (sthu.equals(thu)) {
                                                                                    countTemp += 1;
                                                                                    if (countTemp == count) {
                                                                                        khoaHocChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                } catch (UnsupportedEncodingException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                            ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHoc(khoaHocChinhXac,khoaHocGanChinhXac);
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
                                else// Empty ngoại trừ Lĩnh vực, học phí, bằng cấp
                                {
                                    mData.child("KhoaHoc").child("KhoaHocTimGiaSu").child("ChuaHoanTat").addChildEventListener(new ChildEventListener() {
                                        @Override
                                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                            KhoaHoc kh =dataSnapshot.getValue(KhoaHoc.class);
                                            for(String linhVuc : kh.getLinhVuc())
                                            {
                                                try {
                                                    if(URLEncoder.encode(removeDiacriticalMarks(linhVuc),"utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getLinhVuc().get(0)).toLowerCase(),"utf-8")))
                                                    {
                                                        for(String bangcap : kh.getBangCap())
                                                        {

                                                            if(URLEncoder.encode(removeDiacriticalMarks(bangcap),"utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getBangCap().get(0)).toLowerCase(),"utf-8")))
                                                            {
                                                                if(Double.parseDouble(kh.getHocPhi())>=hocPhiDuoi && Double.parseDouble(kh.getHocPhi())<=hocPhiTren)
                                                                {
                                                                    khoaHocGanChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                                    for(String buoi : kh.getLichHoc().getThoiGian())
                                                                    {
                                                                        for(String sbuoi : khoaHoc.getLichHoc().getThoiGian())
                                                                        {
                                                                            if(sbuoi.equals(buoi))
                                                                            {
                                                                                for(String thu : kh.getLichHoc().getNgayHoc())
                                                                                {
                                                                                    for(String sthu : khoaHoc.getLichHoc().getNgayHoc())
                                                                                    {
                                                                                        if (sthu.equals(thu)) {
                                                                                            countTemp += 1;
                                                                                            if (countTemp == count) {
                                                                                                khoaHocChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                                                            }
                                                                                        }
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                } catch (UnsupportedEncodingException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                            ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHoc(khoaHocChinhXac,khoaHocGanChinhXac);
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
                            else
                            {
                                if(khoaHoc.getBangCap() == null)//Empty ngoại trừ Lĩnh vực, học phí, giới tính
                                {
                                    mData.child("KhoaHoc").child("KhoaHocTimGiaSu").child("ChuaHoanTat").addChildEventListener(new ChildEventListener() {
                                        @Override
                                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                            KhoaHoc kh = dataSnapshot.getValue(KhoaHoc.class);

                                            for(String linhVuc : kh.getLinhVuc())
                                            {
                                                try {
                                                    if(URLEncoder.encode(removeDiacriticalMarks(linhVuc),"utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getLinhVuc().get(0)).toLowerCase(),"utf-8")))
                                                    {
                                                        if(Double.parseDouble(kh.getHocPhi())>=hocPhiDuoi && Double.parseDouble(kh.getHocPhi())<=hocPhiTren)
                                                        {
                                                            if(kh.getGioiTinh().equals(khoaHoc.getGioiTinh()))
                                                            {
                                                                khoaHocGanChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                                for(String buoi : kh.getLichHoc().getThoiGian())
                                                                {
                                                                    for(String sbuoi : khoaHoc.getLichHoc().getThoiGian())
                                                                    {
                                                                        if(sbuoi.equals(buoi))
                                                                        {
                                                                            for(String thu : kh.getLichHoc().getNgayHoc())
                                                                            {
                                                                                for(String sthu : khoaHoc.getLichHoc().getNgayHoc())
                                                                                {
                                                                                    if (sthu.equals(thu)) {
                                                                                        countTemp += 1;
                                                                                        if (countTemp == count) {
                                                                                            khoaHocChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                                                        }
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                } catch (UnsupportedEncodingException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                            ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHoc(khoaHocChinhXac,khoaHocGanChinhXac);
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
                                else//Empty ngoại trừ Lĩnh vực, hco5 phí,giới tính, bằng cấp
                                {
                                    mData.child("KhoaHoc").child("KhoaHocTimGiaSu").child("ChuaHoanTat").addChildEventListener(new ChildEventListener() {
                                        @Override
                                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                            KhoaHoc kh = dataSnapshot.getValue(KhoaHoc.class);

                                            for(String linhVuc : kh.getLinhVuc())
                                            {
                                                try {
                                                    if(URLEncoder.encode(removeDiacriticalMarks(linhVuc),"utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getLinhVuc().get(0)).toLowerCase(),"utf-8")))
                                                    {
                                                        for(String bangcap : kh.getBangCap())
                                                        {

                                                            if(URLEncoder.encode(removeDiacriticalMarks(bangcap),"utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getBangCap().get(0)).toLowerCase(),"utf-8")))
                                                            {
                                                                if(Double.parseDouble(kh.getHocPhi())>=hocPhiDuoi && Double.parseDouble(kh.getHocPhi())<=hocPhiTren)
                                                                {
                                                                    if(kh.getGioiTinh().equals(khoaHoc.getGioiTinh()))
                                                                    {
                                                                        khoaHocGanChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                                        for(String buoi : kh.getLichHoc().getThoiGian())
                                                                        {
                                                                            for(String sbuoi : khoaHoc.getLichHoc().getThoiGian())
                                                                            {
                                                                                if(sbuoi.equals(buoi))
                                                                                {
                                                                                    for(String thu : kh.getLichHoc().getNgayHoc())
                                                                                    {
                                                                                        for(String sthu : khoaHoc.getLichHoc().getNgayHoc())
                                                                                        {
                                                                                            if (sthu.equals(thu)) {
                                                                                                countTemp += 1;
                                                                                                if (countTemp == count) {
                                                                                                    khoaHocChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                                                                }
                                                                                            }
                                                                                        }
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                } catch (UnsupportedEncodingException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                            ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHoc(khoaHocChinhXac,khoaHocGanChinhXac);
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
                    }
                    else
                    {
                        if(hocPhiTimKiem == 0)
                        {
                            if(khoaHoc.getGioiTinh().equals("Nam, Nữ"))
                            {
                                if(khoaHoc.getBangCap() == null)//Empty ngoại trừ Lĩnh vực, địa điểm
                                {
                                    mData.child("KhoaHoc").child("KhoaHocTimGiaSu").child("ChuaHoanTat").addChildEventListener(new ChildEventListener() {
                                        @Override
                                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                            KhoaHoc kh = dataSnapshot.getValue(KhoaHoc.class);
                                            for(String linhVuc : kh.getLinhVuc())
                                            {
                                                try {
                                                    if(URLEncoder.encode(removeDiacriticalMarks(linhVuc),"utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getLinhVuc().get(0)).toLowerCase(),"utf-8")))
                                                    {
                                                        if(URLEncoder.encode(removeDiacriticalMarks(kh.getDiaDiem().getQuan()).toLowerCase(),"utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getDiaDiem().getDayDu()).toLowerCase(),"utf-8")))
                                                        {
                                                            khoaHocGanChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                            for(String buoi : kh.getLichHoc().getThoiGian())
                                                            {
                                                                for(String sbuoi : khoaHoc.getLichHoc().getThoiGian())
                                                                {
                                                                    if(sbuoi.equals(buoi))
                                                                    {
                                                                        for(String thu : kh.getLichHoc().getNgayHoc())
                                                                        {
                                                                            for(String sthu : khoaHoc.getLichHoc().getNgayHoc())
                                                                            {
                                                                                if (sthu.equals(thu)) {
                                                                                    countTemp += 1;
                                                                                    if (countTemp == count) {
                                                                                        khoaHocChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                } catch (UnsupportedEncodingException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                            ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHoc(khoaHocChinhXac,khoaHocGanChinhXac);
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
                                else//Empty ngoại trừ lĩnh vực, địa điểm, bằng cấp
                                {
                                    mData.child("KhoaHoc").child("KhoaHocTimGiaSu").child("ChuaHoanTat").addChildEventListener(new ChildEventListener() {
                                        @Override
                                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                            KhoaHoc kh = dataSnapshot.getValue(KhoaHoc.class);
                                            for(String linhVuc : kh.getLinhVuc())
                                            {
                                                try {
                                                    if(URLEncoder.encode(removeDiacriticalMarks(linhVuc),"utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getLinhVuc().get(0)).toLowerCase(),"utf-8")))
                                                    {
                                                        for(String bangcap : kh.getBangCap())
                                                        {

                                                            if(URLEncoder.encode(removeDiacriticalMarks(bangcap),"utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getBangCap().get(0)).toLowerCase(),"utf-8")))
                                                            {
                                                                if(URLEncoder.encode(removeDiacriticalMarks(kh.getDiaDiem().getQuan()).toLowerCase(),"utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getDiaDiem().getDayDu()).toLowerCase(),"utf-8")))
                                                                {
                                                                    khoaHocGanChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                                    for(String buoi : kh.getLichHoc().getThoiGian())
                                                                    {
                                                                        for(String sbuoi : khoaHoc.getLichHoc().getThoiGian())
                                                                        {
                                                                            if(sbuoi.equals(buoi))
                                                                            {
                                                                                for(String thu : kh.getLichHoc().getNgayHoc())
                                                                                {
                                                                                    for(String sthu : khoaHoc.getLichHoc().getNgayHoc())
                                                                                    {
                                                                                        if (sthu.equals(thu)) {
                                                                                            countTemp += 1;
                                                                                            if (countTemp == count) {
                                                                                                khoaHocChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                                                            }
                                                                                        }
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                } catch (UnsupportedEncodingException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                            ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHoc(khoaHocChinhXac,khoaHocGanChinhXac);
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
                            else
                            {
                                if(khoaHoc.getBangCap() == null)//empty ngoại trừ lĩnh vực, điệm điểm, giới tính
                                {
                                    mData.child("KhoaHoc").child("KhoaHocTimGiaSu").child("ChuaHoanTat").addChildEventListener(new ChildEventListener() {
                                        @Override
                                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                            KhoaHoc kh = dataSnapshot.getValue(KhoaHoc.class);

                                            for(String linhVuc : kh.getLinhVuc())
                                            {
                                                try {
                                                    if(URLEncoder.encode(removeDiacriticalMarks(linhVuc),"utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getLinhVuc().get(0)).toLowerCase(),"utf-8")))
                                                    {
                                                        if(URLEncoder.encode(removeDiacriticalMarks(kh.getDiaDiem().getQuan()).toLowerCase(),"utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getDiaDiem().getDayDu()).toLowerCase(),"utf-8")))
                                                        {
                                                            if(kh.getGioiTinh().equals(khoaHoc.getGioiTinh()))
                                                            {
                                                                khoaHocGanChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                                for(String buoi : kh.getLichHoc().getThoiGian())
                                                                {
                                                                    for(String sbuoi : khoaHoc.getLichHoc().getThoiGian())
                                                                    {
                                                                        if(sbuoi.equals(buoi))
                                                                        {
                                                                            for(String thu : kh.getLichHoc().getNgayHoc())
                                                                            {
                                                                                for(String sthu : khoaHoc.getLichHoc().getNgayHoc())
                                                                                {
                                                                                    if (sthu.equals(thu)) {
                                                                                        countTemp += 1;
                                                                                        if (countTemp == count) {
                                                                                            khoaHocChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                                                        }
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                } catch (UnsupportedEncodingException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                            ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHoc(khoaHocChinhXac,khoaHocGanChinhXac);
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
                                else//empty ngoại trừ lĩnh vực, địa điểm, giới tính, bằng cấp
                                {
                                    mData.child("KhoaHoc").child("KhoaHocTimGiaSu").child("ChuaHoanTat").addChildEventListener(new ChildEventListener() {
                                        @Override
                                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                            KhoaHoc kh = dataSnapshot.getValue(KhoaHoc.class);

                                            for(String linhVuc : kh.getLinhVuc())
                                            {
                                                try {
                                                    if(URLEncoder.encode(removeDiacriticalMarks(linhVuc),"utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getLinhVuc().get(0)).toLowerCase(),"utf-8")))
                                                    {
                                                        for(String bangcap : kh.getBangCap())
                                                        {

                                                            if(URLEncoder.encode(removeDiacriticalMarks(bangcap),"utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getBangCap().get(0)).toLowerCase(),"utf-8")))
                                                            {
                                                                if(URLEncoder.encode(removeDiacriticalMarks(kh.getDiaDiem().getQuan()).toLowerCase(),"utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getDiaDiem().getDayDu()).toLowerCase(),"utf-8")))
                                                                {
                                                                    if(kh.getGioiTinh().equals(khoaHoc.getGioiTinh()))
                                                                    {
                                                                        khoaHocGanChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                                        for(String buoi : kh.getLichHoc().getThoiGian())
                                                                        {
                                                                            for(String sbuoi : khoaHoc.getLichHoc().getThoiGian())
                                                                            {
                                                                                if(sbuoi.equals(buoi))
                                                                                {
                                                                                    for(String thu : kh.getLichHoc().getNgayHoc())
                                                                                    {
                                                                                        for(String sthu : khoaHoc.getLichHoc().getNgayHoc())
                                                                                        {
                                                                                            if (sthu.equals(thu)) {
                                                                                                countTemp += 1;
                                                                                                if (countTemp == count) {
                                                                                                    khoaHocChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                                                                }
                                                                                            }
                                                                                        }
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                } catch (UnsupportedEncodingException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                            ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHoc(khoaHocChinhXac,khoaHocGanChinhXac);
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
                        else
                        {
                            if(khoaHoc.getGioiTinh().equals("Nam, Nữ"))
                            {
                                if(khoaHoc.getBangCap() == null)//Empty ngoại trừ Lĩnh vực, địa điểm, học phí
                                {
                                    mData.child("KhoaHoc").child("KhoaHocTimGiaSu").child("ChuaHoanTat").addChildEventListener(new ChildEventListener() {
                                        @Override
                                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                            KhoaHoc kh = dataSnapshot.getValue(KhoaHoc.class);
                                            for(String linhVuc : kh.getLinhVuc())
                                            {
                                                try {
                                                    if(URLEncoder.encode(removeDiacriticalMarks(linhVuc),"utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getLinhVuc().get(0)).toLowerCase(),"utf-8")))
                                                    {
                                                        if(Double.parseDouble(kh.getHocPhi())>=hocPhiDuoi && Double.parseDouble(kh.getHocPhi())<=hocPhiTren)
                                                        {
                                                            if(URLEncoder.encode(removeDiacriticalMarks(kh.getDiaDiem().getQuan()).toLowerCase(),"utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getDiaDiem().getDayDu()).toLowerCase(),"utf-8")))
                                                            {
                                                                khoaHocGanChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                                for(String buoi : kh.getLichHoc().getThoiGian())
                                                                {
                                                                    for(String sbuoi : khoaHoc.getLichHoc().getThoiGian())
                                                                    {
                                                                        if(sbuoi.equals(buoi))
                                                                        {
                                                                            for(String thu : kh.getLichHoc().getNgayHoc())
                                                                            {
                                                                                for(String sthu : khoaHoc.getLichHoc().getNgayHoc())
                                                                                {
                                                                                    if (sthu.equals(thu)) {
                                                                                        countTemp += 1;
                                                                                        if (countTemp == count) {
                                                                                            khoaHocChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                                                        }
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                } catch (UnsupportedEncodingException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                            ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHoc(khoaHocChinhXac,khoaHocGanChinhXac);
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
                                else//Empty ngoại trừ Lĩnh vực, địa điểm, học phí , bằng cấp
                                {
                                    mData.child("KhoaHoc").child("KhoaHocTimGiaSu").child("ChuaHoanTat").addChildEventListener(new ChildEventListener() {
                                        @Override
                                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                            KhoaHoc kh = dataSnapshot.getValue(KhoaHoc.class);
                                            for(String linhVuc : kh.getLinhVuc())
                                            {
                                                try {
                                                    if(URLEncoder.encode(removeDiacriticalMarks(linhVuc),"utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getLinhVuc().get(0)).toLowerCase(),"utf-8")))
                                                    {
                                                        for(String bangcap : kh.getBangCap())
                                                        {

                                                            if(URLEncoder.encode(removeDiacriticalMarks(bangcap),"utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getBangCap().get(0)).toLowerCase(),"utf-8")))
                                                            {
                                                                if(URLEncoder.encode(removeDiacriticalMarks(kh.getDiaDiem().getQuan()).toLowerCase(),"utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getDiaDiem().getDayDu()).toLowerCase(),"utf-8")))
                                                                {
                                                                    if(Double.parseDouble(kh.getHocPhi())>=hocPhiDuoi && Double.parseDouble(kh.getHocPhi())<=hocPhiTren)
                                                                    {
                                                                        khoaHocGanChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                                        for(String buoi : kh.getLichHoc().getThoiGian())
                                                                        {
                                                                            for(String sbuoi : khoaHoc.getLichHoc().getThoiGian())
                                                                            {
                                                                                if(sbuoi.equals(buoi))
                                                                                {
                                                                                    for(String thu : kh.getLichHoc().getNgayHoc())
                                                                                    {
                                                                                        for(String sthu : khoaHoc.getLichHoc().getNgayHoc())
                                                                                        {
                                                                                            if (sthu.equals(thu)) {
                                                                                                countTemp += 1;
                                                                                                if (countTemp == count) {
                                                                                                    khoaHocChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                                                                }
                                                                                            }
                                                                                        }
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                } catch (UnsupportedEncodingException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                            ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHoc(khoaHocChinhXac,khoaHocGanChinhXac);
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
                            else
                            {
                                if(khoaHoc.getBangCap() == null) //Empty ngoại trừ Lĩnh vực, địa điểm, học phí, giới tính
                                {
                                    mData.child("KhoaHoc").child("KhoaHocTimGiaSu").child("ChuaHoanTat").addChildEventListener(new ChildEventListener() {
                                        @Override
                                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                            KhoaHoc kh = dataSnapshot.getValue(KhoaHoc.class);
                                            for(String linhVuc : kh.getLinhVuc())
                                            {
                                                try {
                                                    if(URLEncoder.encode(removeDiacriticalMarks(linhVuc),"utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getLinhVuc().get(0)).toLowerCase(),"utf-8")))
                                                    {
                                                        if(URLEncoder.encode(removeDiacriticalMarks(kh.getDiaDiem().getQuan()).toLowerCase(),"utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getDiaDiem().getDayDu()).toLowerCase(),"utf-8")))
                                                        {
                                                            if(Double.parseDouble(kh.getHocPhi())>=hocPhiDuoi && Double.parseDouble(kh.getHocPhi())<=hocPhiTren)
                                                            {
                                                                if(kh.getGioiTinh().equals(khoaHoc.getGioiTinh()))
                                                                {
                                                                    khoaHocGanChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                                    for(String buoi : kh.getLichHoc().getThoiGian())
                                                                    {
                                                                        for(String sbuoi : khoaHoc.getLichHoc().getThoiGian())
                                                                        {
                                                                            if(sbuoi.equals(buoi))
                                                                            {
                                                                                for(String thu : kh.getLichHoc().getNgayHoc())
                                                                                {
                                                                                    for(String sthu : khoaHoc.getLichHoc().getNgayHoc())
                                                                                    {
                                                                                        if (sthu.equals(thu)) {
                                                                                            countTemp += 1;
                                                                                            if (countTemp == count) {
                                                                                                khoaHocChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                                                            }
                                                                                        }
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                } catch (UnsupportedEncodingException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                            ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHoc(khoaHocChinhXac,khoaHocGanChinhXac);
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
                                else//Empty ngoại trừ Lĩnh vực, địa điểm, học phí, giới tính,bằng cấp
                                {
                                    mData.child("KhoaHoc").child("KhoaHocTimGiaSu").child("ChuaHoanTat").addChildEventListener(new ChildEventListener() {
                                        @Override
                                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                            KhoaHoc kh = dataSnapshot.getValue(KhoaHoc.class);
                                            for(String linhVuc : kh.getLinhVuc())
                                            {
                                                try {
                                                    if(URLEncoder.encode(removeDiacriticalMarks(linhVuc),"utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getLinhVuc().get(0)).toLowerCase(),"utf-8")))
                                                    {
                                                        for(String bangcap : kh.getBangCap())
                                                        {

                                                            if(URLEncoder.encode(removeDiacriticalMarks(bangcap),"utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getBangCap().get(0)).toLowerCase(),"utf-8")))
                                                            {
                                                                if(URLEncoder.encode(removeDiacriticalMarks(kh.getDiaDiem().getQuan()).toLowerCase(),"utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getDiaDiem().getDayDu()).toLowerCase(),"utf-8")))
                                                                {
                                                                    if(Double.parseDouble(kh.getHocPhi())>=hocPhiDuoi && Double.parseDouble(kh.getHocPhi())<=hocPhiTren)
                                                                    {
                                                                       if(kh.getGioiTinh().equals(khoaHoc.getGioiTinh()))
                                                                       {
                                                                           khoaHocGanChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                                           for(String buoi : kh.getLichHoc().getThoiGian())
                                                                           {
                                                                               for(String sbuoi : khoaHoc.getLichHoc().getThoiGian())
                                                                               {
                                                                                   if(sbuoi.equals(buoi))
                                                                                   {
                                                                                       for(String thu : kh.getLichHoc().getNgayHoc())
                                                                                       {
                                                                                           for(String sthu : khoaHoc.getLichHoc().getNgayHoc())
                                                                                           {
                                                                                               if (sthu.equals(thu)) {
                                                                                                   countTemp += 1;
                                                                                                   if (countTemp == count) {
                                                                                                       khoaHocChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                                                                   }
                                                                                               }
                                                                                           }
                                                                                       }
                                                                                   }
                                                                               }
                                                                           }
                                                                       }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                } catch (UnsupportedEncodingException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                            ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHoc(khoaHocChinhXac,khoaHocGanChinhXac);
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
                    }
                }
            }
            else
            {
                if(khoaHoc.getLinhVuc() == null )
                {
                    if(khoaHoc.getDiaDiem().getDayDu().isEmpty())
                    {
                        if(hocPhiTimKiem == 0)
                        {
                            if(khoaHoc.getGioiTinh().equals("Nam, Nữ"))
                            {
                                if(khoaHoc.getBangCap() == null)//empty ngoại trừ môn
                                {
                                    mData.child("KhoaHoc").child("KhoaHocTimGiaSu").child("ChuaHoanTat").addChildEventListener(new ChildEventListener() {
                                        @Override
                                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                            KhoaHoc kh = dataSnapshot.getValue(KhoaHoc.class);
                                            for(String mon : kh.getMon())
                                            {
                                                try {
                                                    if(URLEncoder.encode(removeDiacriticalMarks(mon),"utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getMon().get(0)).toLowerCase(),"utf-8")))
                                                    {
                                                        khoaHocGanChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                        for(String buoi : kh.getLichHoc().getThoiGian())
                                                        {
                                                            for(String sbuoi : khoaHoc.getLichHoc().getThoiGian())
                                                            {
                                                                if(sbuoi.equals(buoi))
                                                                {
                                                                    for(String thu : kh.getLichHoc().getNgayHoc())
                                                                    {
                                                                        for(String sthu : khoaHoc.getLichHoc().getNgayHoc())
                                                                        {
                                                                            if (sthu.equals(thu)) {
                                                                                countTemp += 1;
                                                                                if (countTemp == count) {
                                                                                    khoaHocChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                } catch (UnsupportedEncodingException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                            ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHoc(khoaHocChinhXac,khoaHocGanChinhXac);
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
                                else//empty ngoại trừ môn, bnag82 cấp
                                {

                                }
                            }
                            else
                            {
                                if(khoaHoc.getBangCap() == null) //empty ngoại trừ môn,giới tính
                                {
                                    mData.child("KhoaHoc").child("KhoaHocTimGiaSu").child("ChuaHoanTat").addChildEventListener(new ChildEventListener() {
                                        @Override
                                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                            KhoaHoc kh = dataSnapshot.getValue(KhoaHoc.class);
                                            for (String mon : kh.getMon())
                                            {
                                                try {
                                                    if (URLEncoder.encode(removeDiacriticalMarks(mon), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getMon().get(0)).toLowerCase(), "utf-8")))
                                                    {
                                                        if(kh.getGioiTinh().equals(khoaHoc.getGioiTinh()))
                                                        {
                                                            khoaHocGanChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                            for(String buoi : kh.getLichHoc().getThoiGian())
                                                            {
                                                                for(String sbuoi : khoaHoc.getLichHoc().getThoiGian())
                                                                {
                                                                    if(sbuoi.equals(buoi))
                                                                    {
                                                                        for(String thu : kh.getLichHoc().getNgayHoc())
                                                                        {
                                                                            for(String sthu : khoaHoc.getLichHoc().getNgayHoc())
                                                                            {
                                                                                if (sthu.equals(thu)) {
                                                                                    countTemp += 1;
                                                                                    if (countTemp == count) {
                                                                                        khoaHocChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                } catch (UnsupportedEncodingException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                            ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHoc(khoaHocChinhXac,khoaHocGanChinhXac);
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
                                else//empty ngoại trừ môn,giới tính, bằng cấp
                                {
                                    mData.child("KhoaHoc").child("KhoaHocTimGiaSu").child("ChuaHoanTat").addChildEventListener(new ChildEventListener() {
                                        @Override
                                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                            KhoaHoc kh = dataSnapshot.getValue(KhoaHoc.class);
                                            for (String mon : kh.getMon())
                                            {
                                                try {
                                                    if (URLEncoder.encode(removeDiacriticalMarks(mon), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getMon().get(0)).toLowerCase(), "utf-8")))
                                                    {
                                                        for(String bangcap : kh.getBangCap())
                                                        {

                                                            if(URLEncoder.encode(removeDiacriticalMarks(bangcap),"utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getBangCap().get(0)).toLowerCase(),"utf-8")))
                                                            {
                                                                if(kh.getGioiTinh().equals(khoaHoc.getGioiTinh()))
                                                                {
                                                                    khoaHocGanChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                                    for(String buoi : kh.getLichHoc().getThoiGian())
                                                                    {
                                                                        for(String sbuoi : khoaHoc.getLichHoc().getThoiGian())
                                                                        {
                                                                            if(sbuoi.equals(buoi))
                                                                            {
                                                                                for(String thu : kh.getLichHoc().getNgayHoc())
                                                                                {
                                                                                    for(String sthu : khoaHoc.getLichHoc().getNgayHoc())
                                                                                    {
                                                                                        if (sthu.equals(thu)) {
                                                                                            countTemp += 1;
                                                                                            if (countTemp == count) {
                                                                                                khoaHocChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                                                            }
                                                                                        }
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                } catch (UnsupportedEncodingException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                            ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHoc(khoaHocChinhXac,khoaHocGanChinhXac);
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
                        else
                        {
                            if(khoaHoc.getGioiTinh().equals("Nam, Nữ"))
                            {
                                if(khoaHoc.getBangCap() == null)//empty ngoại trừ môn,học phí
                                {
                                    mData.child("KhoaHoc").child("KhoaHocTimGiaSu").child("ChuaHoanTat").addChildEventListener(new ChildEventListener() {
                                        @Override
                                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                            KhoaHoc kh = dataSnapshot.getValue(KhoaHoc.class);

                                            for (String mon : kh.getMon())
                                            {
                                                try {
                                                    if (URLEncoder.encode(removeDiacriticalMarks(mon), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getMon().get(0)).toLowerCase(), "utf-8")))
                                                    {
                                                        if(Double.parseDouble(kh.getHocPhi())>=hocPhiDuoi && Double.parseDouble(kh.getHocPhi())<=hocPhiTren)
                                                        {
                                                            khoaHocGanChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                            for(String buoi : kh.getLichHoc().getThoiGian())
                                                            {
                                                                for(String sbuoi : khoaHoc.getLichHoc().getThoiGian())
                                                                {
                                                                    if(sbuoi.equals(buoi))
                                                                    {
                                                                        for(String thu : kh.getLichHoc().getNgayHoc())
                                                                        {
                                                                            for(String sthu : khoaHoc.getLichHoc().getNgayHoc())
                                                                            {
                                                                                if (sthu.equals(thu)) {
                                                                                    countTemp += 1;
                                                                                    if (countTemp == count) {
                                                                                        khoaHocChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                } catch (UnsupportedEncodingException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                            ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHoc(khoaHocChinhXac,khoaHocGanChinhXac);
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
                                else//empty ngoại trừ môn,học phí,bằng cấp
                                {
                                    mData.child("KhoaHoc").child("KhoaHocTimGiaSu").child("ChuaHoanTat").addChildEventListener(new ChildEventListener() {
                                        @Override
                                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                            KhoaHoc kh = dataSnapshot.getValue(KhoaHoc.class);
                                            for (String mon : kh.getMon())
                                            {
                                                try {
                                                    if (URLEncoder.encode(removeDiacriticalMarks(mon), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getMon().get(0)).toLowerCase(), "utf-8")))
                                                    {
                                                        for(String bangcap : kh.getBangCap())
                                                        {

                                                            if(URLEncoder.encode(removeDiacriticalMarks(bangcap),"utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getBangCap().get(0)).toLowerCase(),"utf-8")))
                                                            {
                                                                if(Double.parseDouble(kh.getHocPhi())>=hocPhiDuoi && Double.parseDouble(kh.getHocPhi())<=hocPhiTren)
                                                                {
                                                                    khoaHocGanChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                                    for(String buoi : kh.getLichHoc().getThoiGian())
                                                                    {
                                                                        for(String sbuoi : khoaHoc.getLichHoc().getThoiGian())
                                                                        {
                                                                            if(sbuoi.equals(buoi))
                                                                            {
                                                                                for(String thu : kh.getLichHoc().getNgayHoc())
                                                                                {
                                                                                    for(String sthu : khoaHoc.getLichHoc().getNgayHoc())
                                                                                    {
                                                                                        if (sthu.equals(thu)) {
                                                                                            countTemp += 1;
                                                                                            if (countTemp == count) {
                                                                                                khoaHocChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                                                            }
                                                                                        }
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                } catch (UnsupportedEncodingException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                            ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHoc(khoaHocChinhXac,khoaHocGanChinhXac);
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
                            else
                            {
                                if(khoaHoc.getBangCap() == null)//empty ngoại trừ môn,học phí,giới tính
                                {
                                    mData.child("KhoaHoc").child("KhoaHocTimGiaSu").child("ChuaHoanTat").addChildEventListener(new ChildEventListener() {
                                        @Override
                                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                            KhoaHoc kh = dataSnapshot.getValue(KhoaHoc.class);
                                            for (String mon : kh.getMon())
                                            {
                                                try {
                                                    if (URLEncoder.encode(removeDiacriticalMarks(mon), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getMon().get(0)).toLowerCase(), "utf-8")))
                                                    {
                                                        if(Double.parseDouble(kh.getHocPhi())>=hocPhiDuoi && Double.parseDouble(kh.getHocPhi())<=hocPhiTren)
                                                        {
                                                            if(kh.getGioiTinh().equals(khoaHoc.getGioiTinh()))
                                                            {
                                                                khoaHocGanChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                                for(String buoi : kh.getLichHoc().getThoiGian())
                                                                {
                                                                    for(String sbuoi : khoaHoc.getLichHoc().getThoiGian())
                                                                    {
                                                                        if(sbuoi.equals(buoi))
                                                                        {
                                                                            for(String thu : kh.getLichHoc().getNgayHoc())
                                                                            {
                                                                                for(String sthu : khoaHoc.getLichHoc().getNgayHoc())
                                                                                {
                                                                                    if (sthu.equals(thu)) {
                                                                                        countTemp += 1;
                                                                                        if (countTemp == count) {
                                                                                            khoaHocChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                                                        }
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                } catch (UnsupportedEncodingException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                            ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHoc(khoaHocChinhXac,khoaHocGanChinhXac);
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
                                else//empty ngoại trừ môn,học phí,giới tinh, bằng cấp
                                {

                                }
                            }
                        }
                    }
                    else
                    {
                        if(hocPhiTimKiem == 0)
                        {
                            if(khoaHoc.getGioiTinh().equals("Nam, Nữ"))
                            {
                                if(khoaHoc.getBangCap() == null)//empty ngoại trừ môn,đại điểm
                                {
                                    mData.child("KhoaHoc").child("KhoaHocTimGiaSu").child("ChuaHoanTat").addChildEventListener(new ChildEventListener() {
                                        @Override
                                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                            KhoaHoc kh =dataSnapshot.getValue(KhoaHoc.class);
                                            for (String mon : kh.getMon())
                                            {
                                                try {
                                                    if (URLEncoder.encode(removeDiacriticalMarks(mon), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getMon().get(0)).toLowerCase(), "utf-8")))
                                                    {
                                                        if(URLEncoder.encode(removeDiacriticalMarks(kh.getDiaDiem().getQuan()).toLowerCase(),"utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getDiaDiem().getDayDu()).toLowerCase(),"utf-8")))
                                                        {
                                                            khoaHocGanChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                            for(String buoi : kh.getLichHoc().getThoiGian())
                                                            {
                                                                for(String sbuoi : khoaHoc.getLichHoc().getThoiGian())
                                                                {
                                                                    if(sbuoi.equals(buoi))
                                                                    {
                                                                        for(String thu : kh.getLichHoc().getNgayHoc())
                                                                        {
                                                                            for(String sthu : khoaHoc.getLichHoc().getNgayHoc())
                                                                            {
                                                                                if (sthu.equals(thu)) {
                                                                                    countTemp += 1;
                                                                                    if (countTemp == count) {
                                                                                        khoaHocChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                } catch (UnsupportedEncodingException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                            ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHoc(khoaHocChinhXac,khoaHocGanChinhXac);
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
                                else//empty ngoại trừ môn,đại điểm,bằng cấp
                                {
                                    mData.child("KhoaHoc").child("KhoaHocTimGiaSu").child("ChuaHoanTat").addChildEventListener(new ChildEventListener() {
                                        @Override
                                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                            KhoaHoc kh = dataSnapshot.getValue(KhoaHoc.class);
                                            for (String mon : kh.getMon())
                                            {
                                                try {
                                                    if (URLEncoder.encode(removeDiacriticalMarks(mon), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getMon().get(0)).toLowerCase(), "utf-8")))
                                                    {
                                                        for(String bangcap : kh.getBangCap())
                                                        {

                                                            if(URLEncoder.encode(removeDiacriticalMarks(bangcap),"utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getBangCap().get(0)).toLowerCase(),"utf-8")))
                                                            {
                                                                if(URLEncoder.encode(removeDiacriticalMarks(kh.getDiaDiem().getQuan()).toLowerCase(),"utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getDiaDiem().getDayDu()).toLowerCase(),"utf-8")))
                                                                {
                                                                    khoaHocGanChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                                    for(String buoi : kh.getLichHoc().getThoiGian())
                                                                    {
                                                                        for(String sbuoi : khoaHoc.getLichHoc().getThoiGian())
                                                                        {
                                                                            if(sbuoi.equals(buoi))
                                                                            {
                                                                                for(String thu : kh.getLichHoc().getNgayHoc())
                                                                                {
                                                                                    for(String sthu : khoaHoc.getLichHoc().getNgayHoc())
                                                                                    {
                                                                                        if (sthu.equals(thu)) {
                                                                                            countTemp += 1;
                                                                                            if (countTemp == count) {
                                                                                                khoaHocChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                                                            }
                                                                                        }
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                } catch (UnsupportedEncodingException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                            ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHoc(khoaHocChinhXac,khoaHocGanChinhXac);
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
                            else
                            {
                                if(khoaHoc.getBangCap() == null)//empty ngoại trừ môn,đại điểm, giới tính
                                {
                                    mData.child("KhoaHoc").child("KhoaHocTimGiaSu").child("ChuaHoanTat").addChildEventListener(new ChildEventListener() {
                                        @Override
                                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                            KhoaHoc kh = dataSnapshot.getValue(KhoaHoc.class);

                                            for (String mon : kh.getMon())
                                            {
                                                try {
                                                    if (URLEncoder.encode(removeDiacriticalMarks(mon), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getMon().get(0)).toLowerCase(), "utf-8")))
                                                    {
                                                        if(URLEncoder.encode(removeDiacriticalMarks(kh.getDiaDiem().getQuan()).toLowerCase(),"utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getDiaDiem().getDayDu()).toLowerCase(),"utf-8")))
                                                        {
                                                            if(kh.getGioiTinh().equals(khoaHoc.getGioiTinh()))
                                                            {
                                                                khoaHocGanChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                                for(String buoi : kh.getLichHoc().getThoiGian())
                                                                {
                                                                    for(String sbuoi : khoaHoc.getLichHoc().getThoiGian())
                                                                    {
                                                                        if(sbuoi.equals(buoi))
                                                                        {
                                                                            for(String thu : kh.getLichHoc().getNgayHoc())
                                                                            {
                                                                                for(String sthu : khoaHoc.getLichHoc().getNgayHoc())
                                                                                {
                                                                                    if (sthu.equals(thu)) {
                                                                                        countTemp += 1;
                                                                                        if (countTemp == count) {
                                                                                            khoaHocChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                                                        }
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                } catch (UnsupportedEncodingException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                            ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHoc(khoaHocChinhXac,khoaHocGanChinhXac);
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
                                else//empty ngoại trừ môn,đại điểm, giới tính,bằng cấp
                                {

                                }
                            }
                        }
                        else
                        {
                            if(khoaHoc.getGioiTinh().equals("Nam, Nữ"))
                            {
                                if(khoaHoc.getBangCap() == null)//empty ngoại trừ môn,đại điểm, học phí
                                {
                                    mData.child("KhoaHoc").child("KhoaHocTimGiaSu").child("ChuaHoanTat").addChildEventListener(new ChildEventListener() {
                                        @Override
                                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                            KhoaHoc kh = dataSnapshot.getValue(KhoaHoc.class);
                                            for (String mon : kh.getMon())
                                            {
                                                try {
                                                    if (URLEncoder.encode(removeDiacriticalMarks(mon), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getMon().get(0)).toLowerCase(), "utf-8")))
                                                    {
                                                        if(URLEncoder.encode(removeDiacriticalMarks(kh.getDiaDiem().getQuan()).toLowerCase(),"utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getDiaDiem().getDayDu()).toLowerCase(),"utf-8")))
                                                        {
                                                            if(Double.parseDouble(kh.getHocPhi())>=hocPhiDuoi && Double.parseDouble(kh.getHocPhi())<=hocPhiTren)
                                                            {
                                                                khoaHocGanChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                                for(String buoi : kh.getLichHoc().getThoiGian())
                                                                {
                                                                    for(String sbuoi : khoaHoc.getLichHoc().getThoiGian())
                                                                    {
                                                                        if(sbuoi.equals(buoi))
                                                                        {
                                                                            for(String thu : kh.getLichHoc().getNgayHoc())
                                                                            {
                                                                                for(String sthu : khoaHoc.getLichHoc().getNgayHoc())
                                                                                {
                                                                                    if (sthu.equals(thu)) {
                                                                                        countTemp += 1;
                                                                                        if (countTemp == count) {
                                                                                            khoaHocChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                                                        }
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                } catch (UnsupportedEncodingException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                            ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHoc(khoaHocChinhXac,khoaHocGanChinhXac);
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
                                else//empty ngoại trừ môn,đại điểm, học phí,bằng cấp
                                {
                                    mData.child("KhoaHoc").child("KhoaHocTimGiaSu").child("ChuaHoanTat").addChildEventListener(new ChildEventListener() {
                                        @Override
                                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                            KhoaHoc kh = dataSnapshot.getValue(KhoaHoc.class);
                                            for (String mon : kh.getMon())
                                            {
                                                try {
                                                    if (URLEncoder.encode(removeDiacriticalMarks(mon), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getMon().get(0)).toLowerCase(), "utf-8")))
                                                    {
                                                        for(String bangcap : kh.getBangCap())
                                                        {

                                                            if(URLEncoder.encode(removeDiacriticalMarks(bangcap),"utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getBangCap().get(0)).toLowerCase(),"utf-8")))
                                                            {
                                                                if(Double.parseDouble(kh.getHocPhi())>=hocPhiDuoi && Double.parseDouble(kh.getHocPhi())<=hocPhiTren)
                                                                {
                                                                    khoaHocGanChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                                    for(String buoi : kh.getLichHoc().getThoiGian())
                                                                    {
                                                                        for(String sbuoi : khoaHoc.getLichHoc().getThoiGian())
                                                                        {
                                                                            if(sbuoi.equals(buoi))
                                                                            {
                                                                                for(String thu : kh.getLichHoc().getNgayHoc())
                                                                                {
                                                                                    for(String sthu : khoaHoc.getLichHoc().getNgayHoc())
                                                                                    {
                                                                                        if (sthu.equals(thu)) {
                                                                                            countTemp += 1;
                                                                                            if (countTemp == count) {
                                                                                                khoaHocChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                                                            }
                                                                                        }
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                } catch (UnsupportedEncodingException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                            ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHoc(khoaHocChinhXac,khoaHocGanChinhXac);
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
                            else
                            {
                                if(khoaHoc.getBangCap() == null)//empty ngoại trừ môn,đại điểm, học phí,giới tính
                                {
                                    mData.child("KhoaHoc").child("KhoaHocTimGiaSu").child("ChuaHoanTat").addChildEventListener(new ChildEventListener() {
                                        @Override
                                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                            KhoaHoc kh = dataSnapshot.getValue(KhoaHoc.class);
                                            for (String mon : kh.getMon())
                                            {
                                                try {
                                                    if (URLEncoder.encode(removeDiacriticalMarks(mon), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getMon().get(0)).toLowerCase(), "utf-8")))
                                                    {
                                                        if(URLEncoder.encode(removeDiacriticalMarks(kh.getDiaDiem().getQuan()).toLowerCase(),"utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getDiaDiem().getDayDu()).toLowerCase(),"utf-8")))
                                                        {
                                                            if(Double.parseDouble(kh.getHocPhi())>=hocPhiDuoi && Double.parseDouble(kh.getHocPhi())<=hocPhiTren)
                                                            {
                                                                if(kh.getGioiTinh().equals(khoaHoc.getGioiTinh()))
                                                                {
                                                                    khoaHocGanChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                                    for(String buoi : kh.getLichHoc().getThoiGian())
                                                                    {
                                                                        for(String sbuoi : khoaHoc.getLichHoc().getThoiGian())
                                                                        {
                                                                            if(sbuoi.equals(buoi))
                                                                            {
                                                                                for(String thu : kh.getLichHoc().getNgayHoc())
                                                                                {
                                                                                    for(String sthu : khoaHoc.getLichHoc().getNgayHoc())
                                                                                    {
                                                                                        if (sthu.equals(thu)) {
                                                                                            countTemp += 1;
                                                                                            if (countTemp == count) {
                                                                                                khoaHocChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                                                            }
                                                                                        }
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                } catch (UnsupportedEncodingException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                            ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHoc(khoaHocChinhXac,khoaHocGanChinhXac);
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
                                else//empty ngoại trừ môn,đại điểm, học phí,giới tính,bằng cấp
                                {
                                    mData.child("KhoaHoc").child("KhoaHocTimGiaSu").child("ChuaHoanTat").addChildEventListener(new ChildEventListener() {
                                        @Override
                                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                            KhoaHoc kh = dataSnapshot.getValue(KhoaHoc.class);
                                            for (String mon : kh.getMon())
                                            {
                                                try {
                                                    if (URLEncoder.encode(removeDiacriticalMarks(mon), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getMon().get(0)).toLowerCase(), "utf-8")))
                                                    {
                                                        for(String bangcap : kh.getBangCap())
                                                        {

                                                            if(URLEncoder.encode(removeDiacriticalMarks(bangcap),"utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getBangCap().get(0)).toLowerCase(),"utf-8")))
                                                            {
                                                                if(URLEncoder.encode(removeDiacriticalMarks(kh.getDiaDiem().getQuan()).toLowerCase(),"utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getDiaDiem().getDayDu()).toLowerCase(),"utf-8")))
                                                                {
                                                                    if(Double.parseDouble(kh.getHocPhi())>=hocPhiDuoi && Double.parseDouble(kh.getHocPhi())<=hocPhiTren)
                                                                    {
                                                                        if(kh.getGioiTinh().equals(khoaHoc.getGioiTinh()))
                                                                        {
                                                                            khoaHocGanChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                                            for(String buoi : kh.getLichHoc().getThoiGian())
                                                                            {
                                                                                for(String sbuoi : khoaHoc.getLichHoc().getThoiGian())
                                                                                {
                                                                                    if(sbuoi.equals(buoi))
                                                                                    {
                                                                                        for(String thu : kh.getLichHoc().getNgayHoc())
                                                                                        {
                                                                                            for(String sthu : khoaHoc.getLichHoc().getNgayHoc())
                                                                                            {
                                                                                                if (sthu.equals(thu)) {
                                                                                                    countTemp += 1;
                                                                                                    if (countTemp == count) {
                                                                                                        khoaHocChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                                                                    }
                                                                                                }
                                                                                            }
                                                                                        }
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                } catch (UnsupportedEncodingException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                            ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHoc(khoaHocChinhXac,khoaHocGanChinhXac);
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
                    }
                }
                else
                {
                    if(khoaHoc.getDiaDiem().getDayDu().isEmpty())
                    {
                        if(hocPhiTimKiem == 0)
                        {
                            if(khoaHoc.getGioiTinh().equals("Nam, Nữ"))
                            {
                                if(khoaHoc.getBangCap() == null)//Empty ngoại trừ môn,Lĩnh vực
                                {
                                    mData.child("KhoaHoc").child("KhoaHocTimGiaSu").child("ChuaHoanTat").addChildEventListener(new ChildEventListener() {
                                        @Override
                                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                            KhoaHoc kh = dataSnapshot.getValue(KhoaHoc.class);
                                            for (String mon : kh.getMon())
                                            {
                                                try {
                                                    if (URLEncoder.encode(removeDiacriticalMarks(mon), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getMon().get(0)).toLowerCase(), "utf-8")))
                                                    {
                                                        for(String linhVuc : kh.getLinhVuc())
                                                        {
                                                            if(URLEncoder.encode(removeDiacriticalMarks(linhVuc),"utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getLinhVuc().get(0)).toLowerCase(),"utf-8")))
                                                            {
                                                                khoaHocGanChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                                for(String buoi : kh.getLichHoc().getThoiGian())
                                                                {
                                                                    for(String sbuoi : khoaHoc.getLichHoc().getThoiGian())
                                                                    {
                                                                        if(sbuoi.equals(buoi))
                                                                        {
                                                                            for(String thu : kh.getLichHoc().getNgayHoc())
                                                                            {
                                                                                for(String sthu : khoaHoc.getLichHoc().getNgayHoc())
                                                                                {
                                                                                    if (sthu.equals(thu)) {
                                                                                        countTemp += 1;
                                                                                        if (countTemp == count) {
                                                                                            khoaHocChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                                                        }
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                } catch (UnsupportedEncodingException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                            ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHoc(khoaHocChinhXac,khoaHocGanChinhXac);
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
                                else//Empty ngoại trừ môn,Lĩnh vực, bằng cấp
                                {
                                    mData.child("KhoaHoc").child("KhoaHocTimGiaSu").child("ChuaHoanTat").addChildEventListener(new ChildEventListener() {
                                        @Override
                                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                            KhoaHoc kh = dataSnapshot.getValue(KhoaHoc.class);
                                            for (String mon : kh.getMon())
                                            {
                                                try {
                                                    if (URLEncoder.encode(removeDiacriticalMarks(mon), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getMon().get(0)).toLowerCase(), "utf-8")))
                                                    {
                                                        for(String linhVuc : kh.getLinhVuc())
                                                        {
                                                            if(URLEncoder.encode(removeDiacriticalMarks(linhVuc),"utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getLinhVuc().get(0)).toLowerCase(),"utf-8")))
                                                            {
                                                                for(String bangcap : kh.getBangCap())
                                                                {

                                                                    if(URLEncoder.encode(removeDiacriticalMarks(bangcap),"utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getBangCap().get(0)).toLowerCase(),"utf-8")))
                                                                    {
                                                                        khoaHocGanChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                                        for(String buoi : kh.getLichHoc().getThoiGian())
                                                                        {
                                                                            for(String sbuoi : khoaHoc.getLichHoc().getThoiGian())
                                                                            {
                                                                                if(sbuoi.equals(buoi))
                                                                                {
                                                                                    for(String thu : kh.getLichHoc().getNgayHoc())
                                                                                    {
                                                                                        for(String sthu : khoaHoc.getLichHoc().getNgayHoc())
                                                                                        {
                                                                                            if (sthu.equals(thu)) {
                                                                                                countTemp += 1;
                                                                                                if (countTemp == count) {
                                                                                                    khoaHocChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                                                                }
                                                                                            }
                                                                                        }
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                } catch (UnsupportedEncodingException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                            ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHoc(khoaHocChinhXac,khoaHocGanChinhXac);
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
                            else
                            {
                                if(khoaHoc.getBangCap() == null)//Empty ngoại trừ môn,Lĩnh vực,giới tính
                                {
                                    mData.child("KhoaHoc").child("KhoaHocTimGiaSu").child("ChuaHoanTat").addChildEventListener(new ChildEventListener() {
                                        @Override
                                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                            KhoaHoc kh = dataSnapshot.getValue(KhoaHoc.class);

                                            for (String mon : kh.getMon())
                                            {
                                                try {
                                                    if (URLEncoder.encode(removeDiacriticalMarks(mon), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getMon().get(0)).toLowerCase(), "utf-8")))
                                                    {
                                                        for(String linhVuc : kh.getLinhVuc())
                                                        {
                                                            if(URLEncoder.encode(removeDiacriticalMarks(linhVuc),"utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getLinhVuc().get(0)).toLowerCase(),"utf-8")))
                                                            {
                                                                if(kh.getGioiTinh().equals(khoaHoc.getGioiTinh()))
                                                                {
                                                                    khoaHocGanChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                                    for(String buoi : kh.getLichHoc().getThoiGian())
                                                                    {
                                                                        for(String sbuoi : khoaHoc.getLichHoc().getThoiGian())
                                                                        {
                                                                            if(sbuoi.equals(buoi))
                                                                            {
                                                                                for(String thu : kh.getLichHoc().getNgayHoc())
                                                                                {
                                                                                    for(String sthu : khoaHoc.getLichHoc().getNgayHoc())
                                                                                    {
                                                                                        if (sthu.equals(thu)) {
                                                                                            countTemp += 1;
                                                                                            if (countTemp == count) {
                                                                                                khoaHocChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                                                            }
                                                                                        }
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                } catch (UnsupportedEncodingException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                            ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHoc(khoaHocChinhXac,khoaHocGanChinhXac);
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
                                else//Empty ngoại trừ môn,Lĩnh vực,giới tính,bằng cấp
                                {
                                    mData.child("KhoaHoc").child("KhoaHocTimGiaSu").child("ChuaHoanTat").addChildEventListener(new ChildEventListener() {
                                        @Override
                                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                            KhoaHoc kh = dataSnapshot.getValue(KhoaHoc.class);

                                            for (String mon : kh.getMon())
                                            {
                                                try {
                                                    if (URLEncoder.encode(removeDiacriticalMarks(mon), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getMon().get(0)).toLowerCase(), "utf-8")))
                                                    {
                                                        for(String linhVuc : kh.getLinhVuc())
                                                        {
                                                            if(URLEncoder.encode(removeDiacriticalMarks(linhVuc),"utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getLinhVuc().get(0)).toLowerCase(),"utf-8")))
                                                            {
                                                                for(String bangcap : kh.getBangCap())
                                                                {

                                                                    if(URLEncoder.encode(removeDiacriticalMarks(bangcap),"utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getBangCap().get(0)).toLowerCase(),"utf-8")))
                                                                    {
                                                                        if(kh.getGioiTinh().equals(khoaHoc.getGioiTinh()))
                                                                        {
                                                                            khoaHocGanChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                                            for(String buoi : kh.getLichHoc().getThoiGian())
                                                                            {
                                                                                for(String sbuoi : khoaHoc.getLichHoc().getThoiGian())
                                                                                {
                                                                                    if(sbuoi.equals(buoi))
                                                                                    {
                                                                                        for(String thu : kh.getLichHoc().getNgayHoc())
                                                                                        {
                                                                                            for(String sthu : khoaHoc.getLichHoc().getNgayHoc())
                                                                                            {
                                                                                                if (sthu.equals(thu)) {
                                                                                                    countTemp += 1;
                                                                                                    if (countTemp == count) {
                                                                                                        khoaHocChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                                                                    }
                                                                                                }
                                                                                            }
                                                                                        }
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                } catch (UnsupportedEncodingException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                            ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHoc(khoaHocChinhXac,khoaHocGanChinhXac);
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
                        else
                        {
                            if(khoaHoc.getGioiTinh().equals("Nam, Nữ"))
                            {
                                if(khoaHoc.getBangCap() == null)//empty ngoại trừ môn,Lĩnh vực,học phí
                                {
                                    mData.child("KhoaHoc").child("KhoaHocTimGiaSu").child("ChuaHoanTat").addChildEventListener(new ChildEventListener() {
                                        @Override
                                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                            KhoaHoc kh = dataSnapshot.getValue(KhoaHoc.class);
                                            for (String mon : kh.getMon())
                                            {
                                                try {
                                                    if (URLEncoder.encode(removeDiacriticalMarks(mon), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getMon().get(0)).toLowerCase(), "utf-8")))
                                                    {
                                                        for(String linhVuc : kh.getLinhVuc())
                                                        {
                                                            if(URLEncoder.encode(removeDiacriticalMarks(linhVuc),"utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getLinhVuc().get(0)).toLowerCase(),"utf-8")))
                                                            {
                                                                if(Double.parseDouble(kh.getHocPhi())>=hocPhiDuoi && Double.parseDouble(kh.getHocPhi())<=hocPhiTren)
                                                                {
                                                                    khoaHocGanChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                                    for(String buoi : kh.getLichHoc().getThoiGian())
                                                                    {
                                                                        for(String sbuoi : khoaHoc.getLichHoc().getThoiGian())
                                                                        {
                                                                            if(sbuoi.equals(buoi))
                                                                            {
                                                                                for(String thu : kh.getLichHoc().getNgayHoc())
                                                                                {
                                                                                    for(String sthu : khoaHoc.getLichHoc().getNgayHoc())
                                                                                    {
                                                                                        if (sthu.equals(thu)) {
                                                                                            countTemp += 1;
                                                                                            if (countTemp == count) {
                                                                                                khoaHocChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                                                            }
                                                                                        }
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                } catch (UnsupportedEncodingException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                            ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHoc(khoaHocChinhXac,khoaHocGanChinhXac);
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
                                else//empty ngoại trừ môn,Lĩnh vực,học phí, bằng cap71
                                {

                                }
                            }
                            else
                            {
                                if(khoaHoc.getBangCap() == null)//empty ngoại trừ môn,Lĩnh vực,học phí,giới tính
                                {
                                    mData.child("KhoaHoc").child("KhoaHocTimGiaSu").child("ChuaHoanTat").addChildEventListener(new ChildEventListener() {
                                        @Override
                                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                            KhoaHoc kh  = dataSnapshot.getValue(KhoaHoc.class);
                                            for (String mon : kh.getMon())
                                            {
                                                try {
                                                    if (URLEncoder.encode(removeDiacriticalMarks(mon), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getMon().get(0)).toLowerCase(), "utf-8")))
                                                    {
                                                        for(String linhVuc : kh.getLinhVuc())
                                                        {
                                                            if(URLEncoder.encode(removeDiacriticalMarks(linhVuc),"utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getLinhVuc().get(0)).toLowerCase(),"utf-8")))
                                                            {
                                                                if(Double.parseDouble(kh.getHocPhi())>=hocPhiDuoi && Double.parseDouble(kh.getHocPhi())<=hocPhiTren)
                                                                {
                                                                    if(kh.getGioiTinh().equals(khoaHoc.getGioiTinh()))
                                                                    {
                                                                        khoaHocGanChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                                        for(String buoi : kh.getLichHoc().getThoiGian())
                                                                        {
                                                                            for(String sbuoi : khoaHoc.getLichHoc().getThoiGian())
                                                                            {
                                                                                if(sbuoi.equals(buoi))
                                                                                {
                                                                                    for(String thu : kh.getLichHoc().getNgayHoc())
                                                                                    {
                                                                                        for(String sthu : khoaHoc.getLichHoc().getNgayHoc())
                                                                                        {
                                                                                            if (sthu.equals(thu)) {
                                                                                                countTemp += 1;
                                                                                                if (countTemp == count) {
                                                                                                    khoaHocChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                                                                }
                                                                                            }
                                                                                        }
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                } catch (UnsupportedEncodingException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                            ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHoc(khoaHocChinhXac,khoaHocGanChinhXac);
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
                                else//empty ngoại trừ môn,Lĩnh vực,học phí,giới tính,bằng cấp
                                {
                                    mData.child("KhoaHoc").child("KhoaHocTimGiaSu").child("ChuaHoanTat").addChildEventListener(new ChildEventListener() {
                                        @Override
                                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                            KhoaHoc kh = dataSnapshot.getValue(KhoaHoc.class);
                                            for (String mon : kh.getMon())
                                            {
                                                try {
                                                    if (URLEncoder.encode(removeDiacriticalMarks(mon), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getMon().get(0)).toLowerCase(), "utf-8")))
                                                    {
                                                        for(String linhVuc : kh.getLinhVuc())
                                                        {
                                                            if(URLEncoder.encode(removeDiacriticalMarks(linhVuc),"utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getLinhVuc().get(0)).toLowerCase(),"utf-8")))
                                                            {
                                                                for(String bangcap : kh.getBangCap())
                                                                {

                                                                    if(URLEncoder.encode(removeDiacriticalMarks(bangcap),"utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getBangCap().get(0)).toLowerCase(),"utf-8")))
                                                                    {
                                                                        if(Double.parseDouble(kh.getHocPhi())>=hocPhiDuoi && Double.parseDouble(kh.getHocPhi())<=hocPhiTren)
                                                                        {
                                                                            if(kh.getGioiTinh().equals(khoaHoc.getGioiTinh()))
                                                                            {
                                                                                khoaHocGanChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                                                for(String buoi : kh.getLichHoc().getThoiGian())
                                                                                {
                                                                                    for(String sbuoi : khoaHoc.getLichHoc().getThoiGian())
                                                                                    {
                                                                                        if(sbuoi.equals(buoi))
                                                                                        {
                                                                                            for(String thu : kh.getLichHoc().getNgayHoc())
                                                                                            {
                                                                                                for(String sthu : khoaHoc.getLichHoc().getNgayHoc())
                                                                                                {
                                                                                                    if (sthu.equals(thu)) {
                                                                                                        countTemp += 1;
                                                                                                        if (countTemp == count) {
                                                                                                            khoaHocChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                                                                        }
                                                                                                    }
                                                                                                }
                                                                                            }
                                                                                        }
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                } catch (UnsupportedEncodingException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                            ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHoc(khoaHocChinhXac,khoaHocGanChinhXac);
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
                    }
                    else
                    {
                        if(hocPhiTimKiem == 0)
                        {
                            if(khoaHoc.getGioiTinh().equals("Nam, Nữ"))
                            {
                                if(khoaHoc.getBangCap() == null)//Empty ngoài môn,Lĩnh vực, địa điểm
                                {
                                    mData.child("KhoaHoc").child("KhoaHocTimGiaSu").child("ChuaHoanTat").addChildEventListener(new ChildEventListener() {
                                        @Override
                                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                            KhoaHoc kh = dataSnapshot.getValue(KhoaHoc.class);
                                            for (String mon : kh.getMon())
                                            {
                                                try {
                                                    if (URLEncoder.encode(removeDiacriticalMarks(mon), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getMon().get(0)).toLowerCase(), "utf-8")))
                                                    {
                                                        for(String linhVuc : kh.getLinhVuc())
                                                        {
                                                            if(URLEncoder.encode(removeDiacriticalMarks(linhVuc),"utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getLinhVuc().get(0)).toLowerCase(),"utf-8")))
                                                            {
                                                                if(URLEncoder.encode(removeDiacriticalMarks(kh.getDiaDiem().getQuan()).toLowerCase(),"utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getDiaDiem().getDayDu()).toLowerCase(),"utf-8")))
                                                                {
                                                                    khoaHocGanChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                                    for(String buoi : kh.getLichHoc().getThoiGian())
                                                                    {
                                                                        for(String sbuoi : khoaHoc.getLichHoc().getThoiGian())
                                                                        {
                                                                            if(sbuoi.equals(buoi))
                                                                            {
                                                                                for(String thu : kh.getLichHoc().getNgayHoc())
                                                                                {
                                                                                    for(String sthu : khoaHoc.getLichHoc().getNgayHoc())
                                                                                    {
                                                                                        if (sthu.equals(thu)) {
                                                                                            countTemp += 1;
                                                                                            if (countTemp == count) {
                                                                                                khoaHocChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                                                            }
                                                                                        }
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                } catch (UnsupportedEncodingException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                            ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHoc(khoaHocChinhXac,khoaHocGanChinhXac);
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
                                else//Empty ngoài môn,Lĩnh vực, địa điểm, bằng cấp
                                {
                                    mData.child("KhoaHoc").child("KhoaHocTimGiaSu").child("ChuaHoanTat").addChildEventListener(new ChildEventListener() {
                                        @Override
                                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                            KhoaHoc kh = dataSnapshot.getValue(KhoaHoc.class);
                                            for (String mon : kh.getMon())
                                            {
                                                try {
                                                    if (URLEncoder.encode(removeDiacriticalMarks(mon), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getMon().get(0)).toLowerCase(), "utf-8")))
                                                    {
                                                        for(String linhVuc : kh.getLinhVuc())
                                                        {
                                                            if(URLEncoder.encode(removeDiacriticalMarks(linhVuc),"utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getLinhVuc().get(0)).toLowerCase(),"utf-8")))
                                                            {
                                                                for(String bangcap : kh.getBangCap())
                                                                {

                                                                    if(URLEncoder.encode(removeDiacriticalMarks(bangcap),"utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getBangCap().get(0)).toLowerCase(),"utf-8")))
                                                                    {
                                                                        if(URLEncoder.encode(removeDiacriticalMarks(kh.getDiaDiem().getQuan()).toLowerCase(),"utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getDiaDiem().getDayDu()).toLowerCase(),"utf-8")))
                                                                        {
                                                                            khoaHocGanChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                                            for(String buoi : kh.getLichHoc().getThoiGian())
                                                                            {
                                                                                for(String sbuoi : khoaHoc.getLichHoc().getThoiGian())
                                                                                {
                                                                                    if(sbuoi.equals(buoi))
                                                                                    {
                                                                                        for(String thu : kh.getLichHoc().getNgayHoc())
                                                                                        {
                                                                                            for(String sthu : khoaHoc.getLichHoc().getNgayHoc())
                                                                                            {
                                                                                                if (sthu.equals(thu)) {
                                                                                                    countTemp += 1;
                                                                                                    if (countTemp == count) {
                                                                                                        khoaHocChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                                                                    }
                                                                                                }
                                                                                            }
                                                                                        }
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                } catch (UnsupportedEncodingException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                            ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHoc(khoaHocChinhXac,khoaHocGanChinhXac);
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
                            else
                            {
                                if(khoaHoc.getBangCap() == null)//Empty ngoài môn,Lĩnh vực, địa điểm,giới tính
                                {
                                    mData.child("KhoaHoc").child("KhoaHocTimGiaSu").child("ChuaHoanTat").addChildEventListener(new ChildEventListener() {
                                        @Override
                                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                            KhoaHoc kh = dataSnapshot.getValue(KhoaHoc.class);
                                            for (String mon : kh.getMon())
                                            {
                                                try {
                                                    if (URLEncoder.encode(removeDiacriticalMarks(mon), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getMon().get(0)).toLowerCase(), "utf-8")))
                                                    {
                                                        for(String linhVuc : kh.getLinhVuc())
                                                        {
                                                            if(URLEncoder.encode(removeDiacriticalMarks(linhVuc),"utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getLinhVuc().get(0)).toLowerCase(),"utf-8")))
                                                            {
                                                                if(URLEncoder.encode(removeDiacriticalMarks(kh.getDiaDiem().getQuan()).toLowerCase(),"utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getDiaDiem().getDayDu()).toLowerCase(),"utf-8")))
                                                                {
                                                                    if(kh.getGioiTinh().equals(khoaHoc.getGioiTinh()))
                                                                    {
                                                                        khoaHocGanChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                                        for(String buoi : kh.getLichHoc().getThoiGian())
                                                                        {
                                                                            for(String sbuoi : khoaHoc.getLichHoc().getThoiGian())
                                                                            {
                                                                                if(sbuoi.equals(buoi))
                                                                                {
                                                                                    for(String thu : kh.getLichHoc().getNgayHoc())
                                                                                    {
                                                                                        for(String sthu : khoaHoc.getLichHoc().getNgayHoc())
                                                                                        {
                                                                                            if (sthu.equals(thu)) {
                                                                                                countTemp += 1;
                                                                                                if (countTemp == count) {
                                                                                                    khoaHocChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                                                                }
                                                                                            }
                                                                                        }
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                } catch (UnsupportedEncodingException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                            ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHoc(khoaHocChinhXac,khoaHocGanChinhXac);
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
                                else//Empty ngoài môn,Lĩnh vực, địa điểm,giới tính,bằng cấp
                                {
                                    mData.child("KhoaHoc").child("KhoaHocTimGiaSu").child("ChuaHoanTat").addChildEventListener(new ChildEventListener() {
                                        @Override
                                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                            KhoaHoc kh = dataSnapshot.getValue(KhoaHoc.class);
                                            for (String mon : kh.getMon())
                                            {
                                                try {
                                                    if (URLEncoder.encode(removeDiacriticalMarks(mon), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getMon().get(0)).toLowerCase(), "utf-8")))
                                                    {
                                                        for(String linhVuc : kh.getLinhVuc())
                                                        {
                                                            if(URLEncoder.encode(removeDiacriticalMarks(linhVuc),"utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getLinhVuc().get(0)).toLowerCase(),"utf-8")))
                                                            {
                                                                for(String bangcap : kh.getBangCap())
                                                                {

                                                                    if(URLEncoder.encode(removeDiacriticalMarks(bangcap),"utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getBangCap().get(0)).toLowerCase(),"utf-8")))
                                                                    {
                                                                        if(URLEncoder.encode(removeDiacriticalMarks(kh.getDiaDiem().getQuan()).toLowerCase(),"utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getDiaDiem().getDayDu()).toLowerCase(),"utf-8")))
                                                                        {
                                                                            if(kh.getGioiTinh().equals(khoaHoc.getGioiTinh()))
                                                                            {
                                                                                khoaHocGanChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                                                for(String buoi : kh.getLichHoc().getThoiGian())
                                                                                {
                                                                                    for(String sbuoi : khoaHoc.getLichHoc().getThoiGian())
                                                                                    {
                                                                                        if(sbuoi.equals(buoi))
                                                                                        {
                                                                                            for(String thu : kh.getLichHoc().getNgayHoc())
                                                                                            {
                                                                                                for(String sthu : khoaHoc.getLichHoc().getNgayHoc())
                                                                                                {
                                                                                                    if (sthu.equals(thu)) {
                                                                                                        countTemp += 1;
                                                                                                        if (countTemp == count) {
                                                                                                            khoaHocChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                                                                        }
                                                                                                    }
                                                                                                }
                                                                                            }
                                                                                        }
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                } catch (UnsupportedEncodingException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                            ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHoc(khoaHocChinhXac,khoaHocGanChinhXac);
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
                        else
                        {
                            if(khoaHoc.getGioiTinh().equals("Nam, Nữ"))
                            {
                                if(khoaHoc.getBangCap() == null)//Empty ngoài môn,Lĩnh vục, địa điểm,học phí
                                {
                                    mData.child("KhoaHoc").child("KhoaHocTimGiaSu").child("ChuaHoanTat").addChildEventListener(new ChildEventListener() {
                                        @Override
                                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                            KhoaHoc kh = dataSnapshot.getValue(KhoaHoc.class);

                                            for (String mon : kh.getMon())
                                            {
                                                try {
                                                    if (URLEncoder.encode(removeDiacriticalMarks(mon), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getMon().get(0)).toLowerCase(), "utf-8")))
                                                    {
                                                        for(String linhVuc : kh.getLinhVuc())
                                                        {
                                                            if(URLEncoder.encode(removeDiacriticalMarks(linhVuc),"utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getLinhVuc().get(0)).toLowerCase(),"utf-8")))
                                                            {
                                                                if(URLEncoder.encode(removeDiacriticalMarks(kh.getDiaDiem().getQuan()).toLowerCase(),"utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getDiaDiem().getDayDu()).toLowerCase(),"utf-8")))
                                                                {
                                                                    if(Double.parseDouble(kh.getHocPhi())>=hocPhiDuoi && Double.parseDouble(kh.getHocPhi())<=hocPhiTren)
                                                                    {
                                                                        khoaHocGanChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                                        for(String buoi : kh.getLichHoc().getThoiGian())
                                                                        {
                                                                            for(String sbuoi : khoaHoc.getLichHoc().getThoiGian())
                                                                            {
                                                                                if(sbuoi.equals(buoi))
                                                                                {
                                                                                    for(String thu : kh.getLichHoc().getNgayHoc())
                                                                                    {
                                                                                        for(String sthu : khoaHoc.getLichHoc().getNgayHoc())
                                                                                        {
                                                                                            if (sthu.equals(thu)) {
                                                                                                countTemp += 1;
                                                                                                if (countTemp == count) {
                                                                                                    khoaHocChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                                                                }
                                                                                            }
                                                                                        }
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                } catch (UnsupportedEncodingException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                            ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHoc(khoaHocChinhXac,khoaHocGanChinhXac);
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
                                else//Empty ngoài môn,Lĩnh vục, địa điểm,học phí, bằng cấp
                                {
                                    mData.child("KhoaHoc").child("KhoaHocTimGiaSu").child("ChuaHoanTat").addChildEventListener(new ChildEventListener() {
                                        @Override
                                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                            KhoaHoc kh = dataSnapshot.getValue(KhoaHoc.class);

                                            for (String mon : kh.getMon())
                                            {
                                                try {
                                                    if (URLEncoder.encode(removeDiacriticalMarks(mon), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getMon().get(0)).toLowerCase(), "utf-8")))
                                                    {
                                                        for(String linhVuc : kh.getLinhVuc())
                                                        {
                                                            if(URLEncoder.encode(removeDiacriticalMarks(linhVuc),"utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getLinhVuc().get(0)).toLowerCase(),"utf-8")))
                                                            {
                                                                for(String bangcap : kh.getBangCap())
                                                                {

                                                                    if(URLEncoder.encode(removeDiacriticalMarks(bangcap),"utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getBangCap().get(0)).toLowerCase(),"utf-8")))
                                                                    {
                                                                        if(URLEncoder.encode(removeDiacriticalMarks(kh.getDiaDiem().getQuan()).toLowerCase(),"utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getDiaDiem().getDayDu()).toLowerCase(),"utf-8")))
                                                                        {
                                                                            if(Double.parseDouble(kh.getHocPhi())>=hocPhiDuoi && Double.parseDouble(kh.getHocPhi())<=hocPhiTren)
                                                                            {
                                                                                khoaHocGanChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                                                for(String buoi : kh.getLichHoc().getThoiGian())
                                                                                {
                                                                                    for(String sbuoi : khoaHoc.getLichHoc().getThoiGian())
                                                                                    {
                                                                                        if(sbuoi.equals(buoi))
                                                                                        {
                                                                                            for(String thu : kh.getLichHoc().getNgayHoc())
                                                                                            {
                                                                                                for(String sthu : khoaHoc.getLichHoc().getNgayHoc())
                                                                                                {
                                                                                                    if (sthu.equals(thu)) {
                                                                                                        countTemp += 1;
                                                                                                        if (countTemp == count) {
                                                                                                            khoaHocChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                                                                        }
                                                                                                    }
                                                                                                }
                                                                                            }
                                                                                        }
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                } catch (UnsupportedEncodingException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                            ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHoc(khoaHocChinhXac,khoaHocGanChinhXac);
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
                            else
                            {
                                if(khoaHoc.getBangCap() == null)//Empty ngoài môn,Lĩnh vục, địa điểm,học phí,giới tính
                                {
                                    mData.child("KhoaHoc").child("KhoaHocTimGiaSu").child("ChuaHoanTat").addChildEventListener(new ChildEventListener() {
                                        @Override
                                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                            KhoaHoc kh  =dataSnapshot.getValue(KhoaHoc.class);
                                            for (String mon : kh.getMon())
                                            {
                                                try {
                                                    if (URLEncoder.encode(removeDiacriticalMarks(mon), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getMon().get(0)).toLowerCase(), "utf-8")))
                                                    {
                                                        for(String linhVuc : kh.getLinhVuc())
                                                        {
                                                            if(URLEncoder.encode(removeDiacriticalMarks(linhVuc),"utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getLinhVuc().get(0)).toLowerCase(),"utf-8")))
                                                            {
                                                                if(URLEncoder.encode(removeDiacriticalMarks(kh.getDiaDiem().getQuan()).toLowerCase(),"utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getDiaDiem().getDayDu()).toLowerCase(),"utf-8")))
                                                                {
                                                                    if(Double.parseDouble(kh.getHocPhi())>=hocPhiDuoi && Double.parseDouble(kh.getHocPhi())<=hocPhiTren)
                                                                    {
                                                                        if(kh.getGioiTinh().equals(khoaHoc.getGioiTinh()))
                                                                        {
                                                                            khoaHocGanChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                                            for(String buoi : kh.getLichHoc().getThoiGian())
                                                                            {
                                                                                for(String sbuoi : khoaHoc.getLichHoc().getThoiGian())
                                                                                {
                                                                                    if(sbuoi.equals(buoi))
                                                                                    {
                                                                                        for(String thu : kh.getLichHoc().getNgayHoc())
                                                                                        {
                                                                                            for(String sthu : khoaHoc.getLichHoc().getNgayHoc())
                                                                                            {
                                                                                                if (sthu.equals(thu)) {
                                                                                                    countTemp += 1;
                                                                                                    if (countTemp == count) {
                                                                                                        khoaHocChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                                                                    }
                                                                                                }
                                                                                            }
                                                                                        }
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                } catch (UnsupportedEncodingException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                            ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHoc(khoaHocChinhXac,khoaHocGanChinhXac);
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
                                else//Empty ngoài môn,Lĩnh vục, địa điểm,học phí,giới tính,bằng cấp
                                {
                                    mData.child("KhoaHoc").child("KhoaHocTimGiaSu").child("ChuaHoanTat").addChildEventListener(new ChildEventListener() {
                                        @Override
                                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                            KhoaHoc kh = dataSnapshot.getValue(KhoaHoc.class);

                                            for (String mon : kh.getMon())
                                            {
                                                try {
                                                    if (URLEncoder.encode(removeDiacriticalMarks(mon), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getMon().get(0)).toLowerCase(), "utf-8")))
                                                    {
                                                        for(String linhVuc : kh.getLinhVuc())
                                                        {
                                                            if(URLEncoder.encode(removeDiacriticalMarks(linhVuc),"utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getLinhVuc().get(0)).toLowerCase(),"utf-8")))
                                                            {
                                                                for(String bangcap : kh.getBangCap())
                                                                {

                                                                    if(URLEncoder.encode(removeDiacriticalMarks(bangcap),"utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getBangCap().get(0)).toLowerCase(),"utf-8")))
                                                                    {
                                                                        if(URLEncoder.encode(removeDiacriticalMarks(kh.getDiaDiem().getQuan()).toLowerCase(),"utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getDiaDiem().getDayDu()).toLowerCase(),"utf-8")))
                                                                        {
                                                                            if(Double.parseDouble(kh.getHocPhi())>=hocPhiDuoi && Double.parseDouble(kh.getHocPhi())<=hocPhiTren)
                                                                            {
                                                                                if(kh.getGioiTinh().equals(khoaHoc.getGioiTinh()))
                                                                                {
                                                                                    khoaHocGanChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                                                    for(String buoi : kh.getLichHoc().getThoiGian())
                                                                                    {
                                                                                        for(String sbuoi : khoaHoc.getLichHoc().getThoiGian())
                                                                                        {
                                                                                            if(sbuoi.equals(buoi))
                                                                                            {
                                                                                                for(String thu : kh.getLichHoc().getNgayHoc())
                                                                                                {
                                                                                                    for(String sthu : khoaHoc.getLichHoc().getNgayHoc())
                                                                                                    {
                                                                                                        if (sthu.equals(thu)) {
                                                                                                            countTemp += 1;
                                                                                                            if (countTemp == count) {
                                                                                                                khoaHocChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                                                                            }
                                                                                                        }
                                                                                                    }
                                                                                                }
                                                                                            }
                                                                                        }
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                } catch (UnsupportedEncodingException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                            ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHoc(khoaHocChinhXac,khoaHocGanChinhXac);
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
                    }
                }
            }
        }
        else
        {
            if(khoaHoc.getMon() == null)
            {
                if(khoaHoc.getLinhVuc() == null )
                {
                    if(khoaHoc.getDiaDiem().getDayDu().isEmpty())
                    {
                        if(hocPhiTimKiem == 0)
                        {
                            if(khoaHoc.getGioiTinh().equals("Nam, Nữ"))
                            {
                                if(khoaHoc.getBangCap() == null) //Tất cả empty || có thể ngoại trừ buổi hoặc thứ
                                {
                                    mData.child("KhoaHoc").child("KhoaHocTimHocVien").child("ChuaHoanTat").addChildEventListener(new ChildEventListener() {
                                        @Override
                                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                            KhoaHoc kh = dataSnapshot.getValue(KhoaHoc.class);
                                            khoaHocGanChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                            for(String buoi : kh.getLichHoc().getThoiGian())
                                            {
                                                for(String sbuoi : khoaHoc.getLichHoc().getThoiGian())
                                                {
                                                    if(sbuoi.equals(buoi))
                                                    {
                                                        for(String thu : kh.getLichHoc().getNgayHoc())
                                                        {
                                                            for(String sthu : khoaHoc.getLichHoc().getNgayHoc())
                                                            {
                                                                if (sthu.equals(thu)) {
                                                                    countTemp += 1;
                                                                    if (countTemp == count) {
                                                                        khoaHocChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                            ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHoc(khoaHocChinhXac,khoaHocGanChinhXac);
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
                                else //Tất cả empty ngoại trừ bằng cấp || có thể ngoại trừ buổi hoặc thứ
                                {
                                    mData.child("KhoaHoc").child("KhoaHocTimHocVien").child("KhoaHocChuaHoanTat").addChildEventListener(new ChildEventListener() {
                                        @Override
                                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                            KhoaHoc kh = dataSnapshot.getValue(KhoaHoc.class);
                                            for(String bangcap : kh.getBangCap())
                                            {
                                                try
                                                {
                                                    if(URLEncoder.encode(removeDiacriticalMarks(bangcap),"utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getBangCap().get(0)).toLowerCase(),"utf-8")))
                                                    {
                                                        khoaHocGanChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                        for(String buoi : kh.getLichHoc().getThoiGian())
                                                        {
                                                            for(String sbuoi : khoaHoc.getLichHoc().getThoiGian())
                                                            {
                                                                if(sbuoi.equals(buoi))
                                                                {
                                                                    for(String thu : kh.getLichHoc().getNgayHoc())
                                                                    {
                                                                        for(String sthu : khoaHoc.getLichHoc().getNgayHoc())
                                                                        {
                                                                            if (sthu.equals(thu)) {
                                                                                countTemp += 1;
                                                                                if (countTemp == count) {
                                                                                    khoaHocChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                                catch (UnsupportedEncodingException e)
                                                {
                                                    e.printStackTrace();
                                                }
                                            }
                                            ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHoc(khoaHocChinhXac,khoaHocGanChinhXac);
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
                            else
                            {
                                if(khoaHoc.getBangCap() == null) // Tất cả empty hết ngoại trừ giới tính ||có thể ngoại trừ buổi hoặc thứ
                                {
                                    mData.child("KhoaHoc").child("KhoaHocTimHocVien").child("ChuaHoanTat").addChildEventListener(new ChildEventListener() {
                                        @Override
                                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                            KhoaHoc kh = dataSnapshot.getValue(KhoaHoc.class);
                                            if(kh.getGioiTinh().equals(khoaHoc.getGioiTinh()))
                                            {
                                                khoaHocGanChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                for(String buoi : kh.getLichHoc().getThoiGian())
                                                {
                                                    for(String sbuoi : khoaHoc.getLichHoc().getThoiGian())
                                                    {
                                                        if(sbuoi.equals(buoi))
                                                        {
                                                            for(String thu : kh.getLichHoc().getNgayHoc())
                                                            {
                                                                for(String sthu : khoaHoc.getLichHoc().getNgayHoc())
                                                                {
                                                                    if (sthu.equals(thu)) {
                                                                        countTemp += 1;
                                                                        if (countTemp == count) {
                                                                            khoaHocChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                            ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHoc(khoaHocChinhXac,khoaHocGanChinhXac);
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
                                else // Tất cả empty hết ngoại trừ giới tinh, bằng cấp || có thể ngoại trừ buổi hoặc thứ
                                {
                                    mData.child("KhoaHoc").child("KhoaHocTimHocVien").child("ChuaHoanTat").addChildEventListener(new ChildEventListener() {
                                        @Override
                                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                            KhoaHoc kh = dataSnapshot.getValue(KhoaHoc.class);
                                            for(String bangcap : kh.getBangCap())
                                            {
                                                try
                                                {
                                                    if (URLEncoder.encode(removeDiacriticalMarks(bangcap), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getBangCap().get(0)).toLowerCase(), "utf-8")))
                                                    {
                                                        khoaHocGanChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                        if(kh.getGioiTinh().equals(khoaHoc.getGioiTinh()))
                                                        {
                                                            for(String buoi : kh.getLichHoc().getThoiGian())
                                                            {
                                                                for(String sbuoi : khoaHoc.getLichHoc().getThoiGian())
                                                                {
                                                                    if(sbuoi.equals(buoi))
                                                                    {
                                                                        for(String thu : kh.getLichHoc().getNgayHoc())
                                                                        {
                                                                            for(String sthu : khoaHoc.getLichHoc().getNgayHoc())
                                                                            {
                                                                                if (sthu.equals(thu)) {
                                                                                    countTemp += 1;
                                                                                    if (countTemp == count) {
                                                                                        khoaHocChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                } catch (UnsupportedEncodingException e)
                                                {

                                                    e.printStackTrace();
                                                }
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
                        }
                        else
                        {
                            if(khoaHoc.getGioiTinh().equals("Nam, Nữ"))
                            {
                                if(khoaHoc.getBangCap() == null) // tất cả empty hết ngoại trừ học phí || có thể ngoại trừ buổi hoặc thứ
                                {
                                    mData.child("KhoaHoc").child("KhoaHocTimHocVien").child("ChuaHoanTat").addChildEventListener(new ChildEventListener() {
                                        @Override
                                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                            KhoaHoc kh = dataSnapshot.getValue(KhoaHoc.class);
                                            if(Double.parseDouble(kh.getHocPhi())>=hocPhiDuoi && Double.parseDouble(kh.getHocPhi())<=hocPhiTren) {
                                                for (String buoi : kh.getLichHoc().getThoiGian()) {
                                                    for (String sbuoi : khoaHoc.getLichHoc().getThoiGian()) {
                                                        khoaHocGanChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                        if (sbuoi.equals(buoi)) {
                                                            for (String thu : kh.getLichHoc().getNgayHoc()) {
                                                                for (String sthu : khoaHoc.getLichHoc().getNgayHoc()) {
                                                                    if (sthu.equals(thu)) {
                                                                        countTemp += 1;
                                                                        if (countTemp == count) {
                                                                            khoaHocChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                            ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHoc(khoaHocChinhXac,khoaHocGanChinhXac);
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
                                else // Tất cả empty hết ngoại trừ học phí, bằng cấp || có thể ngoại trừ buổi hoặc thứ
                                {
                                    mData.child("KhoaHoc").child("KhoaHocTimHocVien").child("ChuaHoanTat").addChildEventListener(new ChildEventListener() {
                                        @Override
                                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                            KhoaHoc kh = dataSnapshot.getValue(KhoaHoc.class);
                                            for(String bangcap : kh.getBangCap())
                                            {
                                                try
                                                {
                                                    if (URLEncoder.encode(removeDiacriticalMarks(bangcap), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getBangCap().get(0)).toLowerCase(), "utf-8")))
                                                    {
                                                        khoaHocGanChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                        if(Double.parseDouble(kh.getHocPhi())>=hocPhiDuoi && Double.parseDouble(kh.getHocPhi())<=hocPhiTren) {
                                                            for (String buoi : kh.getLichHoc().getThoiGian()) {
                                                                for (String sbuoi : khoaHoc.getLichHoc().getThoiGian()) {
                                                                    if (sbuoi.equals(buoi)) {
                                                                        for (String thu : kh.getLichHoc().getNgayHoc()) {
                                                                            for (String sthu : khoaHoc.getLichHoc().getNgayHoc()) {
                                                                                if (sthu.equals(thu)) {
                                                                                    countTemp += 1;
                                                                                    if (countTemp == count) {
                                                                                        khoaHocChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                                catch (UnsupportedEncodingException e)
                                                {
                                                    e.printStackTrace();
                                                }
                                            }
                                            ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHoc(khoaHocChinhXac,khoaHocGanChinhXac);
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
                            else
                            {
                                if(khoaHoc.getBangCap() == null) // Tất cả empty hết ngoại trừ học phí, giới tính || có thể ngoại trừ buổi hoặc thứ
                                {
                                    mData.child("KhoaHoc").child("KhoaHocTimHocVien").child("ChuaHoanTat").addChildEventListener(new ChildEventListener() {
                                        @Override
                                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                            KhoaHoc kh = dataSnapshot.getValue(KhoaHoc.class);
                                            if(kh.getGioiTinh().equals(khoaHoc.getGioiTinh()))
                                            {
                                                khoaHocGanChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                if(Double.parseDouble(kh.getHocPhi())>=hocPhiDuoi && Double.parseDouble(kh.getHocPhi())<=hocPhiTren)
                                                {
                                                    for(String buoi : kh.getLichHoc().getThoiGian())
                                                    {
                                                        for(String sbuoi : khoaHoc.getLichHoc().getThoiGian())
                                                        {
                                                            if(sbuoi.equals(buoi))
                                                            {
                                                                for(String thu : kh.getLichHoc().getNgayHoc())
                                                                {
                                                                    for(String sthu : khoaHoc.getLichHoc().getNgayHoc())
                                                                    {
                                                                        if (sthu.equals(thu)) {
                                                                            countTemp += 1;
                                                                            if (countTemp == count) {
                                                                                khoaHocChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                            ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHoc(khoaHocChinhXac,khoaHocGanChinhXac);

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
                                else // Tất cả empty hết ngoại trừ học phí, giới tính, bằng cấp || có thể ngoại trừ buổi hoặc thứ
                                {
                                    mData.child("KhoaHoc").child("KhoaHocTimHocVien").child("ChuaHoanTat").addChildEventListener(new ChildEventListener() {
                                        @Override
                                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                            KhoaHoc kh = dataSnapshot.getValue(KhoaHoc.class);
                                            if(kh.getGioiTinh().equals(khoaHoc.getGioiTinh()))
                                            {
                                                khoaHocGanChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                for(String bangcap : kh.getBangCap())
                                                {

                                                    try {
                                                        if(URLEncoder.encode(removeDiacriticalMarks(bangcap),"utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getBangCap().get(0)).toLowerCase(),"utf-8")))
                                                        {
                                                            if(Double.parseDouble(kh.getHocPhi())>=hocPhiDuoi && Double.parseDouble(kh.getHocPhi())<=hocPhiTren)
                                                            {
                                                                for(String buoi : kh.getLichHoc().getThoiGian())
                                                                {
                                                                    for(String sbuoi : khoaHoc.getLichHoc().getThoiGian())
                                                                    {
                                                                        if(sbuoi.equals(buoi))
                                                                        {
                                                                            for(String thu : kh.getLichHoc().getNgayHoc())
                                                                            {
                                                                                for(String sthu : khoaHoc.getLichHoc().getNgayHoc())
                                                                                {
                                                                                    if (sthu.equals(thu)) {
                                                                                        countTemp += 1;
                                                                                        if (countTemp == count) {
                                                                                            khoaHocChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                                                        }
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    } catch (UnsupportedEncodingException e) {
                                                        e.printStackTrace();
                                                    }
                                                }
                                            }
                                            ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHoc(khoaHocChinhXac,khoaHocGanChinhXac);
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
                    }
                    else
                    {
                        if(hocPhiTimKiem == 0)
                        {
                            if(khoaHoc.getGioiTinh().equals("Nam, Nữ"))
                            {
                                if(khoaHoc.getBangCap() == null)//Tất cả empty hết ngoại trừ Địa điểm || có thể ngoại trừ buổi hoặc thứ
                                {
                                    mData.child("KhoaHoc").child("KhoaHocTimHocVien").child("ChuaHoanTat").addChildEventListener(new ChildEventListener() {
                                        @Override
                                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                            KhoaHoc kh = dataSnapshot.getValue(KhoaHoc.class);
                                            try {
                                                if(URLEncoder.encode(removeDiacriticalMarks(kh.getDiaDiem().getQuan()).toLowerCase(),"utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getDiaDiem().getDayDu()).toLowerCase(),"utf-8")))
                                                {
                                                    khoaHocGanChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                    for(String buoi : kh.getLichHoc().getThoiGian())
                                                    {
                                                        for(String sbuoi : khoaHoc.getLichHoc().getThoiGian())
                                                        {
                                                            if(sbuoi.equals(buoi))
                                                            {
                                                                for(String thu : kh.getLichHoc().getNgayHoc())
                                                                {
                                                                    for(String sthu : khoaHoc.getLichHoc().getNgayHoc())
                                                                    {
                                                                        if (sthu.equals(thu)) {
                                                                            countTemp += 1;
                                                                            if (countTemp == count) {
                                                                                khoaHocChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            } catch (UnsupportedEncodingException e) {
                                                e.printStackTrace();
                                            }
                                            ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHoc(khoaHocChinhXac,khoaHocGanChinhXac);
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
                                else//Tất cả empty hết ngoại trừ địa điểm , bằng cấp || có thể ngoại trừ buổi hoặc thứ
                                {
                                    mData.child("KhoaHoc").child("KhoaHocTimHocVien").child("ChuaHoanTat").addChildEventListener(new ChildEventListener() {
                                        @Override
                                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                            KhoaHoc kh = dataSnapshot.getValue(KhoaHoc.class);
                                            try {
                                                if(URLEncoder.encode(removeDiacriticalMarks(kh.getDiaDiem().getQuan()).toLowerCase(),"utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getDiaDiem().getDayDu()).toLowerCase(),"utf-8")))
                                                {
                                                    for(String bangcap : kh.getBangCap())
                                                    {

                                                        if(URLEncoder.encode(removeDiacriticalMarks(bangcap),"utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getBangCap().get(0)).toLowerCase(),"utf-8")))
                                                        {
                                                            khoaHocGanChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                            for(String buoi : kh.getLichHoc().getThoiGian())
                                                            {
                                                                for(String sbuoi : khoaHoc.getLichHoc().getThoiGian())
                                                                {
                                                                    if(sbuoi.equals(buoi))
                                                                    {
                                                                        for(String thu : kh.getLichHoc().getNgayHoc())
                                                                        {
                                                                            for(String sthu : khoaHoc.getLichHoc().getNgayHoc())
                                                                            {
                                                                                if (sthu.equals(thu)) {
                                                                                    countTemp += 1;
                                                                                    if (countTemp == count) {
                                                                                        khoaHocChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            } catch (UnsupportedEncodingException e) {
                                                e.printStackTrace();
                                            }
                                            ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHoc(khoaHocChinhXac,khoaHocGanChinhXac);

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
                            else
                            {
                                if(khoaHoc.getBangCap() == null)//Tất cả empty ngoại trừ địa điểm, giới tính || có thể ngoại trừ buổi hoặc thứ
                                {
                                    mData.child("KhoaHoc").child("KhoaHocTimHocVien").child("ChuaHoanTat").addChildEventListener(new ChildEventListener() {
                                        @Override
                                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                            KhoaHoc kh = dataSnapshot.getValue(KhoaHoc.class);
                                            try {
                                                if(URLEncoder.encode(removeDiacriticalMarks(kh.getDiaDiem().getQuan()).toLowerCase(),"utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getDiaDiem().getDayDu()).toLowerCase(),"utf-8")))
                                                {
                                                    if(kh.getGioiTinh().equals(khoaHoc.getGioiTinh()))
                                                    {
                                                        khoaHocGanChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                        for(String buoi : kh.getLichHoc().getThoiGian())
                                                        {
                                                            for(String sbuoi : khoaHoc.getLichHoc().getThoiGian())
                                                            {
                                                                if(sbuoi.equals(buoi))
                                                                {
                                                                    for(String thu : kh.getLichHoc().getNgayHoc())
                                                                    {
                                                                        for(String sthu : khoaHoc.getLichHoc().getNgayHoc())
                                                                        {
                                                                            if (sthu.equals(thu)) {
                                                                                countTemp += 1;
                                                                                if (countTemp == count) {
                                                                                    khoaHocChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            } catch (UnsupportedEncodingException e) {
                                                e.printStackTrace();
                                            }
                                            ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHoc(khoaHocChinhXac,khoaHocGanChinhXac);

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
                                else //tất cả empty ngoại trừ địa điểm, giới tính, bằng cấp || có thể ngoại trừ buổi hoặc thứ
                                {
                                    mData.child("KhoaHoc").child("KhoaHocTimHocVien").child("ChuaHoanTat").addChildEventListener(new ChildEventListener() {
                                        @Override
                                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                            KhoaHoc kh = dataSnapshot.getValue(KhoaHoc.class);
                                            try {
                                                if(URLEncoder.encode(removeDiacriticalMarks(kh.getDiaDiem().getQuan()).toLowerCase(),"utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getDiaDiem().getDayDu()).toLowerCase(),"utf-8")))
                                                {
                                                    if(kh.getGioiTinh().equals(khoaHoc.getGioiTinh()))
                                                    {
                                                        for(String bangcap : kh.getBangCap())
                                                        {

                                                            if(URLEncoder.encode(removeDiacriticalMarks(bangcap),"utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getBangCap().get(0)).toLowerCase(),"utf-8")))
                                                            {
                                                                khoaHocGanChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                                for(String buoi : kh.getLichHoc().getThoiGian())
                                                                {
                                                                    for(String sbuoi : khoaHoc.getLichHoc().getThoiGian())
                                                                    {
                                                                        if(sbuoi.equals(buoi))
                                                                        {
                                                                            for(String thu : kh.getLichHoc().getNgayHoc())
                                                                            {
                                                                                for(String sthu : khoaHoc.getLichHoc().getNgayHoc())
                                                                                {
                                                                                    if (sthu.equals(thu)) {
                                                                                        countTemp += 1;
                                                                                        if (countTemp == count) {
                                                                                            khoaHocChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                                                        }
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            } catch (UnsupportedEncodingException e) {
                                                e.printStackTrace();
                                            }
                                            ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHoc(khoaHocChinhXac,khoaHocGanChinhXac);
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
                        else
                        {
                            if(khoaHoc.getGioiTinh().equals("Nam, Nữ"))
                            {
                                if(khoaHoc.getBangCap() == null)//Tất cả empty ngoại trừ địa điểm, học phí || có thể ngoại trừ buổi hoặc thứ
                                {
                                    mData.child("KhoaHoc").child("KhoaHocTimHocVien").child("ChuaHoanTat").addChildEventListener(new ChildEventListener() {
                                        @Override
                                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                            KhoaHoc kh = dataSnapshot.getValue(KhoaHoc.class);

                                            try {
                                                if(URLEncoder.encode(removeDiacriticalMarks(kh.getDiaDiem().getQuan()).toLowerCase(),"utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getDiaDiem().getDayDu()).toLowerCase(),"utf-8")))
                                                {
                                                    if(Double.parseDouble(kh.getHocPhi())>=hocPhiDuoi && Double.parseDouble(kh.getHocPhi())<=hocPhiTren)
                                                    {
                                                        khoaHocGanChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                        for(String buoi : kh.getLichHoc().getThoiGian())
                                                        {
                                                            for(String sbuoi : khoaHoc.getLichHoc().getThoiGian())
                                                            {
                                                                if(sbuoi.equals(buoi))
                                                                {
                                                                    for(String thu : kh.getLichHoc().getNgayHoc())
                                                                    {
                                                                        for(String sthu : khoaHoc.getLichHoc().getNgayHoc())
                                                                        {
                                                                            if (sthu.equals(thu)) {
                                                                                countTemp += 1;
                                                                                if (countTemp == count) {
                                                                                    khoaHocChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            } catch (UnsupportedEncodingException e) {
                                                e.printStackTrace();
                                            }
                                            ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHoc(khoaHocChinhXac,khoaHocGanChinhXac);
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
                                else//Tất cả empty ngoại trừ địa điểm, học phí, bằng cấp || có thể ngoại trừ buổi hoặc thứ
                                {
                                    mData.child("KhoaHoc").child("KhoaHocTimHocVien").child("ChuaHoanTat").addChildEventListener(new ChildEventListener() {
                                        @Override
                                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                            KhoaHoc kh = dataSnapshot.getValue(KhoaHoc.class);

                                            try {
                                                if(URLEncoder.encode(removeDiacriticalMarks(kh.getDiaDiem().getQuan()).toLowerCase(),"utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getDiaDiem().getDayDu()).toLowerCase(),"utf-8")))
                                                {
                                                    if(Double.parseDouble(kh.getHocPhi())>=hocPhiDuoi && Double.parseDouble(kh.getHocPhi())<=hocPhiTren)
                                                    {
                                                        for(String bangcap : kh.getBangCap())
                                                        {

                                                            if(URLEncoder.encode(removeDiacriticalMarks(bangcap),"utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getBangCap().get(0)).toLowerCase(),"utf-8")))
                                                            {
                                                                khoaHocGanChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                                for(String buoi : kh.getLichHoc().getThoiGian())
                                                                {
                                                                    for(String sbuoi : khoaHoc.getLichHoc().getThoiGian())
                                                                    {
                                                                        if(sbuoi.equals(buoi))
                                                                        {
                                                                            for(String thu : kh.getLichHoc().getNgayHoc())
                                                                            {
                                                                                for(String sthu : khoaHoc.getLichHoc().getNgayHoc())
                                                                                {
                                                                                    if (sthu.equals(thu)) {
                                                                                        countTemp += 1;
                                                                                        if (countTemp == count) {
                                                                                            khoaHocChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                                                        }
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            } catch (UnsupportedEncodingException e) {
                                                e.printStackTrace();
                                            }
                                            ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHoc(khoaHocChinhXac,khoaHocGanChinhXac);
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
                            else
                            {
                                if(khoaHoc.getBangCap() == null)//Tất cả empty ngoại trừ địa điểm, học phí, giới tính || có thể ngoại trừ buổi hoặc thứ
                                {
                                    mData.child("KhoaHoc").child("KhoaHocTimHocVien").child("ChuaHoanTat").addChildEventListener(new ChildEventListener() {
                                        @Override
                                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                            KhoaHoc kh = dataSnapshot.getValue(KhoaHoc.class);

                                            try {
                                                if(URLEncoder.encode(removeDiacriticalMarks(kh.getDiaDiem().getQuan()).toLowerCase(),"utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getDiaDiem().getDayDu()).toLowerCase(),"utf-8")))
                                                {
                                                    if(Double.parseDouble(kh.getHocPhi())>=hocPhiDuoi && Double.parseDouble(kh.getHocPhi())<=hocPhiTren)
                                                    {
                                                        if(kh.getGioiTinh().equals(khoaHoc.getGioiTinh()))
                                                        {
                                                            khoaHocGanChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                            for(String buoi : kh.getLichHoc().getThoiGian())
                                                            {
                                                                for(String sbuoi : khoaHoc.getLichHoc().getThoiGian())
                                                                {
                                                                    if(sbuoi.equals(buoi))
                                                                    {
                                                                        for(String thu : kh.getLichHoc().getNgayHoc())
                                                                        {
                                                                            for(String sthu : khoaHoc.getLichHoc().getNgayHoc())
                                                                            {
                                                                                if (sthu.equals(thu)) {
                                                                                    countTemp += 1;
                                                                                    if (countTemp == count) {
                                                                                        khoaHocChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            } catch (UnsupportedEncodingException e) {
                                                e.printStackTrace();
                                            }
                                            ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHoc(khoaHocChinhXac,khoaHocGanChinhXac);
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
                                else//Tất cả empty ngoại trừ địa điểm, học phí, giới tính, bằng cấp ||có thể ngoại trừ buổi hoặc thứ
                                {
                                    mData.child("KhoaHoc").child("KhoaHocTimHocVien").child("ChuaHoanTat").addChildEventListener(new ChildEventListener() {
                                        @Override
                                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                            KhoaHoc kh = dataSnapshot.getValue(KhoaHoc.class);

                                            try {
                                                if(URLEncoder.encode(removeDiacriticalMarks(kh.getDiaDiem().getQuan()).toLowerCase(),"utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getDiaDiem().getDayDu()).toLowerCase(),"utf-8")))
                                                {
                                                    if(kh.getGioiTinh().equals(khoaHoc.getGioiTinh()))
                                                    {
                                                        if(Double.parseDouble(kh.getHocPhi())>=hocPhiDuoi && Double.parseDouble(kh.getHocPhi())<=hocPhiTren)
                                                        {
                                                            for(String bangcap : kh.getBangCap())
                                                            {

                                                                if(URLEncoder.encode(removeDiacriticalMarks(bangcap),"utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getBangCap().get(0)).toLowerCase(),"utf-8")))
                                                                {
                                                                    khoaHocGanChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                                    for(String buoi : kh.getLichHoc().getThoiGian())
                                                                    {
                                                                        for(String sbuoi : khoaHoc.getLichHoc().getThoiGian())
                                                                        {
                                                                            if(sbuoi.equals(buoi))
                                                                            {
                                                                                for(String thu : kh.getLichHoc().getNgayHoc())
                                                                                {
                                                                                    for(String sthu : khoaHoc.getLichHoc().getNgayHoc())
                                                                                    {
                                                                                        if (sthu.equals(thu)) {
                                                                                            countTemp += 1;
                                                                                            if (countTemp == count) {
                                                                                                khoaHocChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                                                            }
                                                                                        }
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }

                                            } catch (UnsupportedEncodingException e) {
                                                e.printStackTrace();
                                            }
                                            ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHoc(khoaHocChinhXac,khoaHocGanChinhXac);
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
                    }
                }
                else
                {
                    if(khoaHoc.getDiaDiem().getDayDu().isEmpty())
                    {
                        if(hocPhiTimKiem == 0)
                        {
                            if(khoaHoc.getGioiTinh().equals("Nam, Nữ"))
                            {
                                if(khoaHoc.getBangCap() == null) //Tất cả empty ngoài trừ Lĩnh vực
                                {
                                    mData.child("KhoaHoc").child("KhoaHocTimHocVien").child("ChuaHoanTat").addChildEventListener(new ChildEventListener() {
                                        @Override
                                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                            KhoaHoc kh = dataSnapshot.getValue(KhoaHoc.class);

                                            for(String linhVuc : kh.getLinhVuc())
                                            {
                                                try {
                                                    if(URLEncoder.encode(removeDiacriticalMarks(linhVuc),"utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getLinhVuc().get(0)).toLowerCase(),"utf-8")))
                                                    {
                                                        khoaHocGanChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                        for(String buoi : kh.getLichHoc().getThoiGian())
                                                        {
                                                            for(String sbuoi : khoaHoc.getLichHoc().getThoiGian())
                                                            {
                                                                if(sbuoi.equals(buoi))
                                                                {
                                                                    for(String thu : kh.getLichHoc().getNgayHoc())
                                                                    {
                                                                        for(String sthu : khoaHoc.getLichHoc().getNgayHoc())
                                                                        {
                                                                            if (sthu.equals(thu)) {
                                                                                countTemp += 1;
                                                                                if (countTemp == count) {
                                                                                    khoaHocChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                } catch (UnsupportedEncodingException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                            ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHoc(khoaHocChinhXac,khoaHocGanChinhXac);
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
                                else //Tất cả empty ngoại trừ Lịnh vực, bằng cấp
                                {
                                    mData.child("KhoaHoc").child("KhoaHocTimHocVien").child("ChuaHoanTat").addChildEventListener(new ChildEventListener() {
                                        @Override
                                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                            KhoaHoc kh = dataSnapshot.getValue(KhoaHoc.class);

                                            for(String linhVuc : kh.getLinhVuc())
                                            {
                                                try {
                                                    if(URLEncoder.encode(removeDiacriticalMarks(linhVuc),"utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getLinhVuc().get(0)).toLowerCase(),"utf-8")))
                                                    {
                                                        for(String bangcap : kh.getBangCap())
                                                        {

                                                            if(URLEncoder.encode(removeDiacriticalMarks(bangcap),"utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getBangCap().get(0)).toLowerCase(),"utf-8")))
                                                            {
                                                                khoaHocGanChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                                for(String buoi : kh.getLichHoc().getThoiGian())
                                                                {
                                                                    for(String sbuoi : khoaHoc.getLichHoc().getThoiGian())
                                                                    {
                                                                        if(sbuoi.equals(buoi))
                                                                        {
                                                                            for(String thu : kh.getLichHoc().getNgayHoc())
                                                                            {
                                                                                for(String sthu : khoaHoc.getLichHoc().getNgayHoc())
                                                                                {
                                                                                    if (sthu.equals(thu)) {
                                                                                        countTemp += 1;
                                                                                        if (countTemp == count) {
                                                                                            khoaHocChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                                                        }
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                } catch (UnsupportedEncodingException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                            ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHoc(khoaHocChinhXac,khoaHocGanChinhXac);
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
                            else
                            {
                                if(khoaHoc.getBangCap() == null) //Tất cả empty ngoại trừ Lĩnh vực, giới tính
                                {
                                    mData.child("KhoaHoc").child("KhoaHocTimHocVien").child("ChuaHoanTat").addChildEventListener(new ChildEventListener() {
                                        @Override
                                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                            KhoaHoc kh =dataSnapshot.getValue(KhoaHoc.class);
                                            for(String linhVuc : kh.getLinhVuc())
                                            {
                                                try {
                                                    if(URLEncoder.encode(removeDiacriticalMarks(linhVuc),"utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getLinhVuc().get(0)).toLowerCase(),"utf-8")))
                                                    {
                                                        if(kh.getGioiTinh().equals(khoaHoc.getGioiTinh()))
                                                        {
                                                            khoaHocGanChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                            for(String buoi : kh.getLichHoc().getThoiGian())
                                                            {
                                                                for(String sbuoi : khoaHoc.getLichHoc().getThoiGian())
                                                                {
                                                                    if(sbuoi.equals(buoi))
                                                                    {
                                                                        for(String thu : kh.getLichHoc().getNgayHoc())
                                                                        {
                                                                            for(String sthu : khoaHoc.getLichHoc().getNgayHoc())
                                                                            {
                                                                                if (sthu.equals(thu)) {
                                                                                    countTemp += 1;
                                                                                    if (countTemp == count) {
                                                                                        khoaHocChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                } catch (UnsupportedEncodingException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                            ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHoc(khoaHocChinhXac,khoaHocGanChinhXac);
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
                                else//tất cả empty ngoại trừ Lĩn vực, giới tính, bằng cấp
                                {
                                    mData.child("KhoaHoc").child("KhoaHocTimHocVien").child("ChuaHoanTat").addChildEventListener(new ChildEventListener() {
                                        @Override
                                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                            KhoaHoc kh = dataSnapshot.getValue(KhoaHoc.class);

                                            for(String linhVuc : kh.getLinhVuc())
                                            {
                                                try {
                                                    if(URLEncoder.encode(removeDiacriticalMarks(linhVuc),"utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getLinhVuc().get(0)).toLowerCase(),"utf-8")))
                                                    {
                                                        for(String bangcap : kh.getBangCap())
                                                        {

                                                            if(URLEncoder.encode(removeDiacriticalMarks(bangcap),"utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getBangCap().get(0)).toLowerCase(),"utf-8")))
                                                            {
                                                                if(kh.getGioiTinh().equals(khoaHoc.getGioiTinh()))
                                                                {
                                                                    khoaHocGanChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                                    for(String buoi : kh.getLichHoc().getThoiGian())
                                                                    {
                                                                        for(String sbuoi : khoaHoc.getLichHoc().getThoiGian())
                                                                        {
                                                                            if(sbuoi.equals(buoi))
                                                                            {
                                                                                for(String thu : kh.getLichHoc().getNgayHoc())
                                                                                {
                                                                                    for(String sthu : khoaHoc.getLichHoc().getNgayHoc())
                                                                                    {
                                                                                        if (sthu.equals(thu)) {
                                                                                            countTemp += 1;
                                                                                            if (countTemp == count) {
                                                                                                khoaHocChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                                                            }
                                                                                        }
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                } catch (UnsupportedEncodingException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                            ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHoc(khoaHocChinhXac,khoaHocGanChinhXac);
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
                        else
                        {
                            if(khoaHoc.getGioiTinh().equals("Nam, Nữ"))
                            {
                                if(khoaHoc.getBangCap() == null)//Tất cả empty ngoại trừ Lĩnh vực, học phí
                                {

                                    mData.child("KhoaHoc").child("KhoaHocTimHocVien").child("ChuaHoanTat").addChildEventListener(new ChildEventListener() {
                                        @Override
                                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                            KhoaHoc kh = dataSnapshot.getValue(KhoaHoc.class);

                                            for(String linhVuc : kh.getLinhVuc())
                                            {
                                                try {
                                                    if(URLEncoder.encode(removeDiacriticalMarks(linhVuc),"utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getLinhVuc().get(0)).toLowerCase(),"utf-8")))
                                                    {
                                                        if(Double.parseDouble(kh.getHocPhi())>=hocPhiDuoi && Double.parseDouble(kh.getHocPhi())<=hocPhiTren)
                                                        {
                                                            khoaHocGanChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                            for(String buoi : kh.getLichHoc().getThoiGian())
                                                            {
                                                                for(String sbuoi : khoaHoc.getLichHoc().getThoiGian())
                                                                {
                                                                    if(sbuoi.equals(buoi))
                                                                    {
                                                                        for(String thu : kh.getLichHoc().getNgayHoc())
                                                                        {
                                                                            for(String sthu : khoaHoc.getLichHoc().getNgayHoc())
                                                                            {
                                                                                if (sthu.equals(thu)) {
                                                                                    countTemp += 1;
                                                                                    if (countTemp == count) {
                                                                                        khoaHocChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                } catch (UnsupportedEncodingException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                            ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHoc(khoaHocChinhXac,khoaHocGanChinhXac);
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
                                else// Empty ngoại trừ Lĩnh vực, học phí, bằng cấp
                                {
                                    mData.child("KhoaHoc").child("KhoaHocTimHocVien").child("ChuaHoanTat").addChildEventListener(new ChildEventListener() {
                                        @Override
                                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                            KhoaHoc kh =dataSnapshot.getValue(KhoaHoc.class);
                                            for(String linhVuc : kh.getLinhVuc())
                                            {
                                                try {
                                                    if(URLEncoder.encode(removeDiacriticalMarks(linhVuc),"utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getLinhVuc().get(0)).toLowerCase(),"utf-8")))
                                                    {
                                                        for(String bangcap : kh.getBangCap())
                                                        {

                                                            if(URLEncoder.encode(removeDiacriticalMarks(bangcap),"utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getBangCap().get(0)).toLowerCase(),"utf-8")))
                                                            {
                                                                if(Double.parseDouble(kh.getHocPhi())>=hocPhiDuoi && Double.parseDouble(kh.getHocPhi())<=hocPhiTren)
                                                                {
                                                                    khoaHocGanChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                                    for(String buoi : kh.getLichHoc().getThoiGian())
                                                                    {
                                                                        for(String sbuoi : khoaHoc.getLichHoc().getThoiGian())
                                                                        {
                                                                            if(sbuoi.equals(buoi))
                                                                            {
                                                                                for(String thu : kh.getLichHoc().getNgayHoc())
                                                                                {
                                                                                    for(String sthu : khoaHoc.getLichHoc().getNgayHoc())
                                                                                    {
                                                                                        if (sthu.equals(thu)) {
                                                                                            countTemp += 1;
                                                                                            if (countTemp == count) {
                                                                                                khoaHocChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                                                            }
                                                                                        }
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                } catch (UnsupportedEncodingException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                            ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHoc(khoaHocChinhXac,khoaHocGanChinhXac);
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
                            else
                            {
                                if(khoaHoc.getBangCap() == null)//Empty ngoại trừ Lĩnh vực, học phí, giới tính
                                {
                                    mData.child("KhoaHoc").child("KhoaHocTimHocVien").child("ChuaHoanTat").addChildEventListener(new ChildEventListener() {
                                        @Override
                                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                            KhoaHoc kh = dataSnapshot.getValue(KhoaHoc.class);

                                            for(String linhVuc : kh.getLinhVuc())
                                            {
                                                try {
                                                    if(URLEncoder.encode(removeDiacriticalMarks(linhVuc),"utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getLinhVuc().get(0)).toLowerCase(),"utf-8")))
                                                    {
                                                        if(Double.parseDouble(kh.getHocPhi())>=hocPhiDuoi && Double.parseDouble(kh.getHocPhi())<=hocPhiTren)
                                                        {
                                                            if(kh.getGioiTinh().equals(khoaHoc.getGioiTinh()))
                                                            {
                                                                khoaHocGanChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                                for(String buoi : kh.getLichHoc().getThoiGian())
                                                                {
                                                                    for(String sbuoi : khoaHoc.getLichHoc().getThoiGian())
                                                                    {
                                                                        if(sbuoi.equals(buoi))
                                                                        {
                                                                            for(String thu : kh.getLichHoc().getNgayHoc())
                                                                            {
                                                                                for(String sthu : khoaHoc.getLichHoc().getNgayHoc())
                                                                                {
                                                                                    if (sthu.equals(thu)) {
                                                                                        countTemp += 1;
                                                                                        if (countTemp == count) {
                                                                                            khoaHocChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                                                        }
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                } catch (UnsupportedEncodingException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                            ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHoc(khoaHocChinhXac,khoaHocGanChinhXac);
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
                                else//Empty ngoại trừ Lĩnh vực, hco5 phí,giới tính, bằng cấp
                                {
                                    mData.child("KhoaHoc").child("KhoaHocTimHocVien").child("ChuaHoanTat").addChildEventListener(new ChildEventListener() {
                                        @Override
                                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                            KhoaHoc kh = dataSnapshot.getValue(KhoaHoc.class);

                                            for(String linhVuc : kh.getLinhVuc())
                                            {
                                                try {
                                                    if(URLEncoder.encode(removeDiacriticalMarks(linhVuc),"utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getLinhVuc().get(0)).toLowerCase(),"utf-8")))
                                                    {
                                                        for(String bangcap : kh.getBangCap())
                                                        {

                                                            if(URLEncoder.encode(removeDiacriticalMarks(bangcap),"utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getBangCap().get(0)).toLowerCase(),"utf-8")))
                                                            {
                                                                if(Double.parseDouble(kh.getHocPhi())>=hocPhiDuoi && Double.parseDouble(kh.getHocPhi())<=hocPhiTren)
                                                                {
                                                                    if(kh.getGioiTinh().equals(khoaHoc.getGioiTinh()))
                                                                    {
                                                                        khoaHocGanChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                                        for(String buoi : kh.getLichHoc().getThoiGian())
                                                                        {
                                                                            for(String sbuoi : khoaHoc.getLichHoc().getThoiGian())
                                                                            {
                                                                                if(sbuoi.equals(buoi))
                                                                                {
                                                                                    for(String thu : kh.getLichHoc().getNgayHoc())
                                                                                    {
                                                                                        for(String sthu : khoaHoc.getLichHoc().getNgayHoc())
                                                                                        {
                                                                                            if (sthu.equals(thu)) {
                                                                                                countTemp += 1;
                                                                                                if (countTemp == count) {
                                                                                                    khoaHocChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                                                                }
                                                                                            }
                                                                                        }
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                } catch (UnsupportedEncodingException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                            ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHoc(khoaHocChinhXac,khoaHocGanChinhXac);
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
                    }
                    else
                    {
                        if(hocPhiTimKiem == 0)
                        {
                            if(khoaHoc.getGioiTinh().equals("Nam, Nữ"))
                            {
                                if(khoaHoc.getBangCap() == null)//Empty ngoại trừ Lĩnh vực, địa điểm
                                {
                                    mData.child("KhoaHoc").child("KhoaHocTimHocVien").child("ChuaHoanTat").addChildEventListener(new ChildEventListener() {
                                        @Override
                                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                            KhoaHoc kh = dataSnapshot.getValue(KhoaHoc.class);
                                            for(String linhVuc : kh.getLinhVuc())
                                            {
                                                try {
                                                    if(URLEncoder.encode(removeDiacriticalMarks(linhVuc),"utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getLinhVuc().get(0)).toLowerCase(),"utf-8")))
                                                    {
                                                        if(URLEncoder.encode(removeDiacriticalMarks(kh.getDiaDiem().getQuan()).toLowerCase(),"utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getDiaDiem().getDayDu()).toLowerCase(),"utf-8")))
                                                        {
                                                            khoaHocGanChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                            for(String buoi : kh.getLichHoc().getThoiGian())
                                                            {
                                                                for(String sbuoi : khoaHoc.getLichHoc().getThoiGian())
                                                                {
                                                                    if(sbuoi.equals(buoi))
                                                                    {
                                                                        for(String thu : kh.getLichHoc().getNgayHoc())
                                                                        {
                                                                            for(String sthu : khoaHoc.getLichHoc().getNgayHoc())
                                                                            {
                                                                                if (sthu.equals(thu)) {
                                                                                    countTemp += 1;
                                                                                    if (countTemp == count) {
                                                                                        khoaHocChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                } catch (UnsupportedEncodingException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                            ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHoc(khoaHocChinhXac,khoaHocGanChinhXac);
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
                                else//Empty ngoại trừ lĩnh vực, địa điểm, bằng cấp
                                {
                                    mData.child("KhoaHoc").child("KhoaHocTimHocVien").child("ChuaHoanTat").addChildEventListener(new ChildEventListener() {
                                        @Override
                                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                            KhoaHoc kh = dataSnapshot.getValue(KhoaHoc.class);
                                            for(String linhVuc : kh.getLinhVuc())
                                            {
                                                try {
                                                    if(URLEncoder.encode(removeDiacriticalMarks(linhVuc),"utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getLinhVuc().get(0)).toLowerCase(),"utf-8")))
                                                    {
                                                        for(String bangcap : kh.getBangCap())
                                                        {

                                                            if(URLEncoder.encode(removeDiacriticalMarks(bangcap),"utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getBangCap().get(0)).toLowerCase(),"utf-8")))
                                                            {
                                                                if(URLEncoder.encode(removeDiacriticalMarks(kh.getDiaDiem().getQuan()).toLowerCase(),"utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getDiaDiem().getDayDu()).toLowerCase(),"utf-8")))
                                                                {
                                                                    khoaHocGanChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                                    for(String buoi : kh.getLichHoc().getThoiGian())
                                                                    {
                                                                        for(String sbuoi : khoaHoc.getLichHoc().getThoiGian())
                                                                        {
                                                                            if(sbuoi.equals(buoi))
                                                                            {
                                                                                for(String thu : kh.getLichHoc().getNgayHoc())
                                                                                {
                                                                                    for(String sthu : khoaHoc.getLichHoc().getNgayHoc())
                                                                                    {
                                                                                        if (sthu.equals(thu)) {
                                                                                            countTemp += 1;
                                                                                            if (countTemp == count) {
                                                                                                khoaHocChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                                                            }
                                                                                        }
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                } catch (UnsupportedEncodingException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                            ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHoc(khoaHocChinhXac,khoaHocGanChinhXac);
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
                            else
                            {
                                if(khoaHoc.getBangCap() == null)//empty ngoại trừ lĩnh vực, điệm điểm, giới tính
                                {
                                    mData.child("KhoaHoc").child("KhoaHocTimHocVien").child("ChuaHoanTat").addChildEventListener(new ChildEventListener() {
                                        @Override
                                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                            KhoaHoc kh = dataSnapshot.getValue(KhoaHoc.class);

                                            for(String linhVuc : kh.getLinhVuc())
                                            {
                                                try {
                                                    if(URLEncoder.encode(removeDiacriticalMarks(linhVuc),"utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getLinhVuc().get(0)).toLowerCase(),"utf-8")))
                                                    {
                                                        if(URLEncoder.encode(removeDiacriticalMarks(kh.getDiaDiem().getQuan()).toLowerCase(),"utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getDiaDiem().getDayDu()).toLowerCase(),"utf-8")))
                                                        {
                                                            if(kh.getGioiTinh().equals(khoaHoc.getGioiTinh()))
                                                            {
                                                                khoaHocGanChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                                for(String buoi : kh.getLichHoc().getThoiGian())
                                                                {
                                                                    for(String sbuoi : khoaHoc.getLichHoc().getThoiGian())
                                                                    {
                                                                        if(sbuoi.equals(buoi))
                                                                        {
                                                                            for(String thu : kh.getLichHoc().getNgayHoc())
                                                                            {
                                                                                for(String sthu : khoaHoc.getLichHoc().getNgayHoc())
                                                                                {
                                                                                    if (sthu.equals(thu)) {
                                                                                        countTemp += 1;
                                                                                        if (countTemp == count) {
                                                                                            khoaHocChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                                                        }
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                } catch (UnsupportedEncodingException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                            ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHoc(khoaHocChinhXac,khoaHocGanChinhXac);
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
                                else//empty ngoại trừ lĩnh vực, địa điểm, giới tính, bằng cấp
                                {
                                    mData.child("KhoaHoc").child("KhoaHocTimHocVien").child("ChuaHoanTat").addChildEventListener(new ChildEventListener() {
                                        @Override
                                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                            KhoaHoc kh = dataSnapshot.getValue(KhoaHoc.class);

                                            for(String linhVuc : kh.getLinhVuc())
                                            {
                                                try {
                                                    if(URLEncoder.encode(removeDiacriticalMarks(linhVuc),"utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getLinhVuc().get(0)).toLowerCase(),"utf-8")))
                                                    {
                                                        for(String bangcap : kh.getBangCap())
                                                        {

                                                            if(URLEncoder.encode(removeDiacriticalMarks(bangcap),"utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getBangCap().get(0)).toLowerCase(),"utf-8")))
                                                            {
                                                                if(URLEncoder.encode(removeDiacriticalMarks(kh.getDiaDiem().getQuan()).toLowerCase(),"utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getDiaDiem().getDayDu()).toLowerCase(),"utf-8")))
                                                                {
                                                                    if(kh.getGioiTinh().equals(khoaHoc.getGioiTinh()))
                                                                    {
                                                                        khoaHocGanChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                                        for(String buoi : kh.getLichHoc().getThoiGian())
                                                                        {
                                                                            for(String sbuoi : khoaHoc.getLichHoc().getThoiGian())
                                                                            {
                                                                                if(sbuoi.equals(buoi))
                                                                                {
                                                                                    for(String thu : kh.getLichHoc().getNgayHoc())
                                                                                    {
                                                                                        for(String sthu : khoaHoc.getLichHoc().getNgayHoc())
                                                                                        {
                                                                                            if (sthu.equals(thu)) {
                                                                                                countTemp += 1;
                                                                                                if (countTemp == count) {
                                                                                                    khoaHocChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                                                                }
                                                                                            }
                                                                                        }
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                } catch (UnsupportedEncodingException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                            ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHoc(khoaHocChinhXac,khoaHocGanChinhXac);
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
                        else
                        {
                            if(khoaHoc.getGioiTinh().equals("Nam, Nữ"))
                            {
                                if(khoaHoc.getBangCap() == null)//Empty ngoại trừ Lĩnh vực, địa điểm, học phí
                                {
                                    mData.child("KhoaHoc").child("KhoaHocTimHocVien").child("ChuaHoanTat").addChildEventListener(new ChildEventListener() {
                                        @Override
                                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                            KhoaHoc kh = dataSnapshot.getValue(KhoaHoc.class);
                                            for(String linhVuc : kh.getLinhVuc())
                                            {
                                                try {
                                                    if(URLEncoder.encode(removeDiacriticalMarks(linhVuc),"utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getLinhVuc().get(0)).toLowerCase(),"utf-8")))
                                                    {
                                                        if(Double.parseDouble(kh.getHocPhi())>=hocPhiDuoi && Double.parseDouble(kh.getHocPhi())<=hocPhiTren)
                                                        {
                                                            if(URLEncoder.encode(removeDiacriticalMarks(kh.getDiaDiem().getQuan()).toLowerCase(),"utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getDiaDiem().getDayDu()).toLowerCase(),"utf-8")))
                                                            {
                                                                khoaHocGanChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                                for(String buoi : kh.getLichHoc().getThoiGian())
                                                                {
                                                                    for(String sbuoi : khoaHoc.getLichHoc().getThoiGian())
                                                                    {
                                                                        if(sbuoi.equals(buoi))
                                                                        {
                                                                            for(String thu : kh.getLichHoc().getNgayHoc())
                                                                            {
                                                                                for(String sthu : khoaHoc.getLichHoc().getNgayHoc())
                                                                                {
                                                                                    if (sthu.equals(thu)) {
                                                                                        countTemp += 1;
                                                                                        if (countTemp == count) {
                                                                                            khoaHocChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                                                        }
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                } catch (UnsupportedEncodingException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                            ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHoc(khoaHocChinhXac,khoaHocGanChinhXac);
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
                                else//Empty ngoại trừ Lĩnh vực, địa điểm, học phí , bằng cấp
                                {
                                    mData.child("KhoaHoc").child("KhoaHocTimHocVien").child("ChuaHoanTat").addChildEventListener(new ChildEventListener() {
                                        @Override
                                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                            KhoaHoc kh = dataSnapshot.getValue(KhoaHoc.class);
                                            for(String linhVuc : kh.getLinhVuc())
                                            {
                                                try {
                                                    if(URLEncoder.encode(removeDiacriticalMarks(linhVuc),"utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getLinhVuc().get(0)).toLowerCase(),"utf-8")))
                                                    {
                                                        for(String bangcap : kh.getBangCap())
                                                        {

                                                            if(URLEncoder.encode(removeDiacriticalMarks(bangcap),"utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getBangCap().get(0)).toLowerCase(),"utf-8")))
                                                            {
                                                                if(URLEncoder.encode(removeDiacriticalMarks(kh.getDiaDiem().getQuan()).toLowerCase(),"utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getDiaDiem().getDayDu()).toLowerCase(),"utf-8")))
                                                                {
                                                                    if(Double.parseDouble(kh.getHocPhi())>=hocPhiDuoi && Double.parseDouble(kh.getHocPhi())<=hocPhiTren)
                                                                    {
                                                                        khoaHocGanChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                                        for(String buoi : kh.getLichHoc().getThoiGian())
                                                                        {
                                                                            for(String sbuoi : khoaHoc.getLichHoc().getThoiGian())
                                                                            {
                                                                                if(sbuoi.equals(buoi))
                                                                                {
                                                                                    for(String thu : kh.getLichHoc().getNgayHoc())
                                                                                    {
                                                                                        for(String sthu : khoaHoc.getLichHoc().getNgayHoc())
                                                                                        {
                                                                                            if (sthu.equals(thu)) {
                                                                                                countTemp += 1;
                                                                                                if (countTemp == count) {
                                                                                                    khoaHocChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                                                                }
                                                                                            }
                                                                                        }
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                } catch (UnsupportedEncodingException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                            ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHoc(khoaHocChinhXac,khoaHocGanChinhXac);
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
                            else
                            {
                                if(khoaHoc.getBangCap() == null) //Empty ngoại trừ Lĩnh vực, địa điểm, học phí, giới tính
                                {
                                    mData.child("KhoaHoc").child("KhoaHocTimHocVien").child("ChuaHoanTat").addChildEventListener(new ChildEventListener() {
                                        @Override
                                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                            KhoaHoc kh = dataSnapshot.getValue(KhoaHoc.class);
                                            for(String linhVuc : kh.getLinhVuc())
                                            {
                                                try {
                                                    if(URLEncoder.encode(removeDiacriticalMarks(linhVuc),"utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getLinhVuc().get(0)).toLowerCase(),"utf-8")))
                                                    {
                                                        if(URLEncoder.encode(removeDiacriticalMarks(kh.getDiaDiem().getQuan()).toLowerCase(),"utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getDiaDiem().getDayDu()).toLowerCase(),"utf-8")))
                                                        {
                                                            if(Double.parseDouble(kh.getHocPhi())>=hocPhiDuoi && Double.parseDouble(kh.getHocPhi())<=hocPhiTren)
                                                            {
                                                                if(kh.getGioiTinh().equals(khoaHoc.getGioiTinh()))
                                                                {
                                                                    khoaHocGanChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                                    for(String buoi : kh.getLichHoc().getThoiGian())
                                                                    {
                                                                        for(String sbuoi : khoaHoc.getLichHoc().getThoiGian())
                                                                        {
                                                                            if(sbuoi.equals(buoi))
                                                                            {
                                                                                for(String thu : kh.getLichHoc().getNgayHoc())
                                                                                {
                                                                                    for(String sthu : khoaHoc.getLichHoc().getNgayHoc())
                                                                                    {
                                                                                        if (sthu.equals(thu)) {
                                                                                            countTemp += 1;
                                                                                            if (countTemp == count) {
                                                                                                khoaHocChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                                                            }
                                                                                        }
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                } catch (UnsupportedEncodingException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                            ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHoc(khoaHocChinhXac,khoaHocGanChinhXac);
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
                                else//Empty ngoại trừ Lĩnh vực, địa điểm, học phí, giới tính,bằng cấp
                                {
                                    mData.child("KhoaHoc").child("KhoaHocTimHocVien").child("ChuaHoanTat").addChildEventListener(new ChildEventListener() {
                                        @Override
                                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                            KhoaHoc kh = dataSnapshot.getValue(KhoaHoc.class);
                                            for(String linhVuc : kh.getLinhVuc())
                                            {
                                                try {
                                                    if(URLEncoder.encode(removeDiacriticalMarks(linhVuc),"utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getLinhVuc().get(0)).toLowerCase(),"utf-8")))
                                                    {
                                                        for(String bangcap : kh.getBangCap())
                                                        {

                                                            if(URLEncoder.encode(removeDiacriticalMarks(bangcap),"utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getBangCap().get(0)).toLowerCase(),"utf-8")))
                                                            {
                                                                if(URLEncoder.encode(removeDiacriticalMarks(kh.getDiaDiem().getQuan()).toLowerCase(),"utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getDiaDiem().getDayDu()).toLowerCase(),"utf-8")))
                                                                {
                                                                    if(Double.parseDouble(kh.getHocPhi())>=hocPhiDuoi && Double.parseDouble(kh.getHocPhi())<=hocPhiTren)
                                                                    {
                                                                        if(kh.getGioiTinh().equals(khoaHoc.getGioiTinh()))
                                                                        {
                                                                            khoaHocGanChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                                            for(String buoi : kh.getLichHoc().getThoiGian())
                                                                            {
                                                                                for(String sbuoi : khoaHoc.getLichHoc().getThoiGian())
                                                                                {
                                                                                    if(sbuoi.equals(buoi))
                                                                                    {
                                                                                        for(String thu : kh.getLichHoc().getNgayHoc())
                                                                                        {
                                                                                            for(String sthu : khoaHoc.getLichHoc().getNgayHoc())
                                                                                            {
                                                                                                if (sthu.equals(thu)) {
                                                                                                    countTemp += 1;
                                                                                                    if (countTemp == count) {
                                                                                                        khoaHocChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                                                                    }
                                                                                                }
                                                                                            }
                                                                                        }
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                } catch (UnsupportedEncodingException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                            ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHoc(khoaHocChinhXac,khoaHocGanChinhXac);
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
                    }
                }
            }
            else
            {
                if(khoaHoc.getLinhVuc() == null )
                {
                    if(khoaHoc.getDiaDiem().getDayDu().isEmpty())
                    {
                        if(hocPhiTimKiem == 0)
                        {
                            if(khoaHoc.getGioiTinh().equals("Nam, Nữ"))
                            {
                                if(khoaHoc.getBangCap() == null)//empty ngoại trừ môn
                                {
                                    mData.child("KhoaHoc").child("KhoaHocTimHocVien").child("ChuaHoanTat").addChildEventListener(new ChildEventListener() {
                                        @Override
                                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                            KhoaHoc kh = dataSnapshot.getValue(KhoaHoc.class);
                                            for(String mon : kh.getMon())
                                            {
                                                try {
                                                    if(URLEncoder.encode(removeDiacriticalMarks(mon),"utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getMon().get(0)).toLowerCase(),"utf-8")))
                                                    {
                                                        khoaHocGanChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                        for(String buoi : kh.getLichHoc().getThoiGian())
                                                        {
                                                            for(String sbuoi : khoaHoc.getLichHoc().getThoiGian())
                                                            {
                                                                if(sbuoi.equals(buoi))
                                                                {
                                                                    for(String thu : kh.getLichHoc().getNgayHoc())
                                                                    {
                                                                        for(String sthu : khoaHoc.getLichHoc().getNgayHoc())
                                                                        {
                                                                            if (sthu.equals(thu)) {
                                                                                countTemp += 1;
                                                                                if (countTemp == count) {
                                                                                    khoaHocChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                } catch (UnsupportedEncodingException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                            ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHoc(khoaHocChinhXac,khoaHocGanChinhXac);
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
                                else//empty ngoại trừ môn, bnag82 cấp
                                {

                                }
                            }
                            else
                            {
                                if(khoaHoc.getBangCap() == null) //empty ngoại trừ môn,giới tính
                                {
                                    mData.child("KhoaHoc").child("KhoaHocTimHocVien").child("ChuaHoanTat").addChildEventListener(new ChildEventListener() {
                                        @Override
                                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                            KhoaHoc kh = dataSnapshot.getValue(KhoaHoc.class);
                                            for (String mon : kh.getMon())
                                            {
                                                try {
                                                    if (URLEncoder.encode(removeDiacriticalMarks(mon), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getMon().get(0)).toLowerCase(), "utf-8")))
                                                    {
                                                        if(kh.getGioiTinh().equals(khoaHoc.getGioiTinh()))
                                                        {
                                                            khoaHocGanChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                            for(String buoi : kh.getLichHoc().getThoiGian())
                                                            {
                                                                for(String sbuoi : khoaHoc.getLichHoc().getThoiGian())
                                                                {
                                                                    if(sbuoi.equals(buoi))
                                                                    {
                                                                        for(String thu : kh.getLichHoc().getNgayHoc())
                                                                        {
                                                                            for(String sthu : khoaHoc.getLichHoc().getNgayHoc())
                                                                            {
                                                                                if (sthu.equals(thu)) {
                                                                                    countTemp += 1;
                                                                                    if (countTemp == count) {
                                                                                        khoaHocChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                } catch (UnsupportedEncodingException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                            ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHoc(khoaHocChinhXac,khoaHocGanChinhXac);
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
                                else//empty ngoại trừ môn,giới tính, bằng cấp
                                {
                                    mData.child("KhoaHoc").child("KhoaHocTimHocVien").child("ChuaHoanTat").addChildEventListener(new ChildEventListener() {
                                        @Override
                                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                            KhoaHoc kh = dataSnapshot.getValue(KhoaHoc.class);
                                            for (String mon : kh.getMon())
                                            {
                                                try {
                                                    if (URLEncoder.encode(removeDiacriticalMarks(mon), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getMon().get(0)).toLowerCase(), "utf-8")))
                                                    {
                                                        for(String bangcap : kh.getBangCap())
                                                        {

                                                            if(URLEncoder.encode(removeDiacriticalMarks(bangcap),"utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getBangCap().get(0)).toLowerCase(),"utf-8")))
                                                            {
                                                                if(kh.getGioiTinh().equals(khoaHoc.getGioiTinh()))
                                                                {
                                                                    khoaHocGanChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                                    for(String buoi : kh.getLichHoc().getThoiGian())
                                                                    {
                                                                        for(String sbuoi : khoaHoc.getLichHoc().getThoiGian())
                                                                        {
                                                                            if(sbuoi.equals(buoi))
                                                                            {
                                                                                for(String thu : kh.getLichHoc().getNgayHoc())
                                                                                {
                                                                                    for(String sthu : khoaHoc.getLichHoc().getNgayHoc())
                                                                                    {
                                                                                        if (sthu.equals(thu)) {
                                                                                            countTemp += 1;
                                                                                            if (countTemp == count) {
                                                                                                khoaHocChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                                                            }
                                                                                        }
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                } catch (UnsupportedEncodingException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                            ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHoc(khoaHocChinhXac,khoaHocGanChinhXac);
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
                        else
                        {
                            if(khoaHoc.getGioiTinh().equals("Nam, Nữ"))
                            {
                                if(khoaHoc.getBangCap() == null)//empty ngoại trừ môn,học phí
                                {
                                    mData.child("KhoaHoc").child("KhoaHocTimHocVien").child("ChuaHoanTat").addChildEventListener(new ChildEventListener() {
                                        @Override
                                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                            KhoaHoc kh = dataSnapshot.getValue(KhoaHoc.class);

                                            for (String mon : kh.getMon())
                                            {
                                                try {
                                                    if (URLEncoder.encode(removeDiacriticalMarks(mon), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getMon().get(0)).toLowerCase(), "utf-8")))
                                                    {
                                                        if(Double.parseDouble(kh.getHocPhi())>=hocPhiDuoi && Double.parseDouble(kh.getHocPhi())<=hocPhiTren)
                                                        {
                                                            khoaHocGanChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                            for(String buoi : kh.getLichHoc().getThoiGian())
                                                            {
                                                                for(String sbuoi : khoaHoc.getLichHoc().getThoiGian())
                                                                {
                                                                    if(sbuoi.equals(buoi))
                                                                    {
                                                                        for(String thu : kh.getLichHoc().getNgayHoc())
                                                                        {
                                                                            for(String sthu : khoaHoc.getLichHoc().getNgayHoc())
                                                                            {
                                                                                if (sthu.equals(thu)) {
                                                                                    countTemp += 1;
                                                                                    if (countTemp == count) {
                                                                                        khoaHocChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                } catch (UnsupportedEncodingException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                            ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHoc(khoaHocChinhXac,khoaHocGanChinhXac);
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
                                else//empty ngoại trừ môn,học phí,bằng cấp
                                {
                                    mData.child("KhoaHoc").child("KhoaHocTimHocVien").child("ChuaHoanTat").addChildEventListener(new ChildEventListener() {
                                        @Override
                                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                            KhoaHoc kh = dataSnapshot.getValue(KhoaHoc.class);
                                            for (String mon : kh.getMon())
                                            {
                                                try {
                                                    if (URLEncoder.encode(removeDiacriticalMarks(mon), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getMon().get(0)).toLowerCase(), "utf-8")))
                                                    {
                                                        for(String bangcap : kh.getBangCap())
                                                        {

                                                            if(URLEncoder.encode(removeDiacriticalMarks(bangcap),"utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getBangCap().get(0)).toLowerCase(),"utf-8")))
                                                            {
                                                                if(Double.parseDouble(kh.getHocPhi())>=hocPhiDuoi && Double.parseDouble(kh.getHocPhi())<=hocPhiTren)
                                                                {
                                                                    khoaHocGanChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                                    for(String buoi : kh.getLichHoc().getThoiGian())
                                                                    {
                                                                        for(String sbuoi : khoaHoc.getLichHoc().getThoiGian())
                                                                        {
                                                                            if(sbuoi.equals(buoi))
                                                                            {
                                                                                for(String thu : kh.getLichHoc().getNgayHoc())
                                                                                {
                                                                                    for(String sthu : khoaHoc.getLichHoc().getNgayHoc())
                                                                                    {
                                                                                        if (sthu.equals(thu)) {
                                                                                            countTemp += 1;
                                                                                            if (countTemp == count) {
                                                                                                khoaHocChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                                                            }
                                                                                        }
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                } catch (UnsupportedEncodingException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                            ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHoc(khoaHocChinhXac,khoaHocGanChinhXac);
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
                            else
                            {
                                if(khoaHoc.getBangCap() == null)//empty ngoại trừ môn,học phí,giới tính
                                {
                                    mData.child("KhoaHoc").child("KhoaHocTimHocVien").child("ChuaHoanTat").addChildEventListener(new ChildEventListener() {
                                        @Override
                                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                            KhoaHoc kh = dataSnapshot.getValue(KhoaHoc.class);
                                            for (String mon : kh.getMon())
                                            {
                                                try {
                                                    if (URLEncoder.encode(removeDiacriticalMarks(mon), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getMon().get(0)).toLowerCase(), "utf-8")))
                                                    {
                                                        if(Double.parseDouble(kh.getHocPhi())>=hocPhiDuoi && Double.parseDouble(kh.getHocPhi())<=hocPhiTren)
                                                        {
                                                            if(kh.getGioiTinh().equals(khoaHoc.getGioiTinh()))
                                                            {
                                                                khoaHocGanChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                                for(String buoi : kh.getLichHoc().getThoiGian())
                                                                {
                                                                    for(String sbuoi : khoaHoc.getLichHoc().getThoiGian())
                                                                    {
                                                                        if(sbuoi.equals(buoi))
                                                                        {
                                                                            for(String thu : kh.getLichHoc().getNgayHoc())
                                                                            {
                                                                                for(String sthu : khoaHoc.getLichHoc().getNgayHoc())
                                                                                {
                                                                                    if (sthu.equals(thu)) {
                                                                                        countTemp += 1;
                                                                                        if (countTemp == count) {
                                                                                            khoaHocChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                                                        }
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                } catch (UnsupportedEncodingException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                            ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHoc(khoaHocChinhXac,khoaHocGanChinhXac);
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
                                else//empty ngoại trừ môn,học phí,giới tinh, bằng cấp
                                {

                                }
                            }
                        }
                    }
                    else
                    {
                        if(hocPhiTimKiem == 0)
                        {
                            if(khoaHoc.getGioiTinh().equals("Nam, Nữ"))
                            {
                                if(khoaHoc.getBangCap() == null)//empty ngoại trừ môn,đại điểm
                                {
                                    mData.child("KhoaHoc").child("KhoaHocTimHocVien").child("ChuaHoanTat").addChildEventListener(new ChildEventListener() {
                                        @Override
                                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                            KhoaHoc kh =dataSnapshot.getValue(KhoaHoc.class);
                                            for (String mon : kh.getMon())
                                            {
                                                try {
                                                    if (URLEncoder.encode(removeDiacriticalMarks(mon), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getMon().get(0)).toLowerCase(), "utf-8")))
                                                    {
                                                        if(URLEncoder.encode(removeDiacriticalMarks(kh.getDiaDiem().getQuan()).toLowerCase(),"utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getDiaDiem().getDayDu()).toLowerCase(),"utf-8")))
                                                        {
                                                            khoaHocGanChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                            for(String buoi : kh.getLichHoc().getThoiGian())
                                                            {
                                                                for(String sbuoi : khoaHoc.getLichHoc().getThoiGian())
                                                                {
                                                                    if(sbuoi.equals(buoi))
                                                                    {
                                                                        for(String thu : kh.getLichHoc().getNgayHoc())
                                                                        {
                                                                            for(String sthu : khoaHoc.getLichHoc().getNgayHoc())
                                                                            {
                                                                                if (sthu.equals(thu)) {
                                                                                    countTemp += 1;
                                                                                    if (countTemp == count) {
                                                                                        khoaHocChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                } catch (UnsupportedEncodingException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                            ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHoc(khoaHocChinhXac,khoaHocGanChinhXac);
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
                                else//empty ngoại trừ môn,đại điểm,bằng cấp
                                {
                                    mData.child("KhoaHoc").child("KhoaHocTimHocVien").child("ChuaHoanTat").addChildEventListener(new ChildEventListener() {
                                        @Override
                                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                            KhoaHoc kh = dataSnapshot.getValue(KhoaHoc.class);
                                            for (String mon : kh.getMon())
                                            {
                                                try {
                                                    if (URLEncoder.encode(removeDiacriticalMarks(mon), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getMon().get(0)).toLowerCase(), "utf-8")))
                                                    {
                                                        for(String bangcap : kh.getBangCap())
                                                        {

                                                            if(URLEncoder.encode(removeDiacriticalMarks(bangcap),"utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getBangCap().get(0)).toLowerCase(),"utf-8")))
                                                            {
                                                                if(URLEncoder.encode(removeDiacriticalMarks(kh.getDiaDiem().getQuan()).toLowerCase(),"utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getDiaDiem().getDayDu()).toLowerCase(),"utf-8")))
                                                                {
                                                                    khoaHocGanChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                                    for(String buoi : kh.getLichHoc().getThoiGian())
                                                                    {
                                                                        for(String sbuoi : khoaHoc.getLichHoc().getThoiGian())
                                                                        {
                                                                            if(sbuoi.equals(buoi))
                                                                            {
                                                                                for(String thu : kh.getLichHoc().getNgayHoc())
                                                                                {
                                                                                    for(String sthu : khoaHoc.getLichHoc().getNgayHoc())
                                                                                    {
                                                                                        if (sthu.equals(thu)) {
                                                                                            countTemp += 1;
                                                                                            if (countTemp == count) {
                                                                                                khoaHocChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                                                            }
                                                                                        }
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                } catch (UnsupportedEncodingException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                            ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHoc(khoaHocChinhXac,khoaHocGanChinhXac);
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
                            else
                            {
                                if(khoaHoc.getBangCap() == null)//empty ngoại trừ môn,đại điểm, giới tính
                                {
                                    mData.child("KhoaHoc").child("KhoaHocTimHocVien").child("ChuaHoanTat").addChildEventListener(new ChildEventListener() {
                                        @Override
                                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                            KhoaHoc kh = dataSnapshot.getValue(KhoaHoc.class);

                                            for (String mon : kh.getMon())
                                            {
                                                try {
                                                    if (URLEncoder.encode(removeDiacriticalMarks(mon), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getMon().get(0)).toLowerCase(), "utf-8")))
                                                    {
                                                        if(URLEncoder.encode(removeDiacriticalMarks(kh.getDiaDiem().getQuan()).toLowerCase(),"utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getDiaDiem().getDayDu()).toLowerCase(),"utf-8")))
                                                        {
                                                            if(kh.getGioiTinh().equals(khoaHoc.getGioiTinh()))
                                                            {
                                                                khoaHocGanChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                                for(String buoi : kh.getLichHoc().getThoiGian())
                                                                {
                                                                    for(String sbuoi : khoaHoc.getLichHoc().getThoiGian())
                                                                    {
                                                                        if(sbuoi.equals(buoi))
                                                                        {
                                                                            for(String thu : kh.getLichHoc().getNgayHoc())
                                                                            {
                                                                                for(String sthu : khoaHoc.getLichHoc().getNgayHoc())
                                                                                {
                                                                                    if (sthu.equals(thu)) {
                                                                                        countTemp += 1;
                                                                                        if (countTemp == count) {
                                                                                            khoaHocChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                                                        }
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                } catch (UnsupportedEncodingException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                            ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHoc(khoaHocChinhXac,khoaHocGanChinhXac);
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
                                else//empty ngoại trừ môn,đại điểm, giới tính,bằng cấp
                                {

                                }
                            }
                        }
                        else
                        {
                            if(khoaHoc.getGioiTinh().equals("Nam, Nữ"))
                            {
                                if(khoaHoc.getBangCap() == null)//empty ngoại trừ môn,đại điểm, học phí
                                {
                                    mData.child("KhoaHoc").child("KhoaHocTimHocVien").child("ChuaHoanTat").addChildEventListener(new ChildEventListener() {
                                        @Override
                                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                            KhoaHoc kh = dataSnapshot.getValue(KhoaHoc.class);
                                            for (String mon : kh.getMon())
                                            {
                                                try {
                                                    if (URLEncoder.encode(removeDiacriticalMarks(mon), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getMon().get(0)).toLowerCase(), "utf-8")))
                                                    {
                                                        if(URLEncoder.encode(removeDiacriticalMarks(kh.getDiaDiem().getQuan()).toLowerCase(),"utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getDiaDiem().getDayDu()).toLowerCase(),"utf-8")))
                                                        {
                                                            if(Double.parseDouble(kh.getHocPhi())>=hocPhiDuoi && Double.parseDouble(kh.getHocPhi())<=hocPhiTren)
                                                            {
                                                                khoaHocGanChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                                for(String buoi : kh.getLichHoc().getThoiGian())
                                                                {
                                                                    for(String sbuoi : khoaHoc.getLichHoc().getThoiGian())
                                                                    {
                                                                        if(sbuoi.equals(buoi))
                                                                        {
                                                                            for(String thu : kh.getLichHoc().getNgayHoc())
                                                                            {
                                                                                for(String sthu : khoaHoc.getLichHoc().getNgayHoc())
                                                                                {
                                                                                    if (sthu.equals(thu)) {
                                                                                        countTemp += 1;
                                                                                        if (countTemp == count) {
                                                                                            khoaHocChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                                                        }
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                } catch (UnsupportedEncodingException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                            ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHoc(khoaHocChinhXac,khoaHocGanChinhXac);
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
                                else//empty ngoại trừ môn,đại điểm, học phí,bằng cấp
                                {
                                    mData.child("KhoaHoc").child("KhoaHocTimHocVien").child("ChuaHoanTat").addChildEventListener(new ChildEventListener() {
                                        @Override
                                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                            KhoaHoc kh = dataSnapshot.getValue(KhoaHoc.class);
                                            for (String mon : kh.getMon())
                                            {
                                                try {
                                                    if (URLEncoder.encode(removeDiacriticalMarks(mon), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getMon().get(0)).toLowerCase(), "utf-8")))
                                                    {
                                                        for(String bangcap : kh.getBangCap())
                                                        {

                                                            if(URLEncoder.encode(removeDiacriticalMarks(bangcap),"utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getBangCap().get(0)).toLowerCase(),"utf-8")))
                                                            {
                                                                if(Double.parseDouble(kh.getHocPhi())>=hocPhiDuoi && Double.parseDouble(kh.getHocPhi())<=hocPhiTren)
                                                                {
                                                                    khoaHocGanChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                                    for(String buoi : kh.getLichHoc().getThoiGian())
                                                                    {
                                                                        for(String sbuoi : khoaHoc.getLichHoc().getThoiGian())
                                                                        {
                                                                            if(sbuoi.equals(buoi))
                                                                            {
                                                                                for(String thu : kh.getLichHoc().getNgayHoc())
                                                                                {
                                                                                    for(String sthu : khoaHoc.getLichHoc().getNgayHoc())
                                                                                    {
                                                                                        if (sthu.equals(thu)) {
                                                                                            countTemp += 1;
                                                                                            if (countTemp == count) {
                                                                                                khoaHocChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                                                            }
                                                                                        }
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                } catch (UnsupportedEncodingException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                            ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHoc(khoaHocChinhXac,khoaHocGanChinhXac);
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
                            else
                            {
                                if(khoaHoc.getBangCap() == null)//empty ngoại trừ môn,đại điểm, học phí,giới tính
                                {
                                    mData.child("KhoaHoc").child("KhoaHocTimHocVien").child("ChuaHoanTat").addChildEventListener(new ChildEventListener() {
                                        @Override
                                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                            KhoaHoc kh = dataSnapshot.getValue(KhoaHoc.class);
                                            for (String mon : kh.getMon())
                                            {
                                                try {
                                                    if (URLEncoder.encode(removeDiacriticalMarks(mon), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getMon().get(0)).toLowerCase(), "utf-8")))
                                                    {
                                                        if(URLEncoder.encode(removeDiacriticalMarks(kh.getDiaDiem().getQuan()).toLowerCase(),"utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getDiaDiem().getDayDu()).toLowerCase(),"utf-8")))
                                                        {
                                                            if(Double.parseDouble(kh.getHocPhi())>=hocPhiDuoi && Double.parseDouble(kh.getHocPhi())<=hocPhiTren)
                                                            {
                                                                if(kh.getGioiTinh().equals(khoaHoc.getGioiTinh()))
                                                                {
                                                                    khoaHocGanChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                                    for(String buoi : kh.getLichHoc().getThoiGian())
                                                                    {
                                                                        for(String sbuoi : khoaHoc.getLichHoc().getThoiGian())
                                                                        {
                                                                            if(sbuoi.equals(buoi))
                                                                            {
                                                                                for(String thu : kh.getLichHoc().getNgayHoc())
                                                                                {
                                                                                    for(String sthu : khoaHoc.getLichHoc().getNgayHoc())
                                                                                    {
                                                                                        if (sthu.equals(thu)) {
                                                                                            countTemp += 1;
                                                                                            if (countTemp == count) {
                                                                                                khoaHocChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                                                            }
                                                                                        }
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                } catch (UnsupportedEncodingException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                            ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHoc(khoaHocChinhXac,khoaHocGanChinhXac);
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
                                else//empty ngoại trừ môn,đại điểm, học phí,giới tính,bằng cấp
                                {
                                    mData.child("KhoaHoc").child("KhoaHocTimHocVien").child("ChuaHoanTat").addChildEventListener(new ChildEventListener() {
                                        @Override
                                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                            KhoaHoc kh = dataSnapshot.getValue(KhoaHoc.class);
                                            for (String mon : kh.getMon())
                                            {
                                                try {
                                                    if (URLEncoder.encode(removeDiacriticalMarks(mon), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getMon().get(0)).toLowerCase(), "utf-8")))
                                                    {
                                                        for(String bangcap : kh.getBangCap())
                                                        {

                                                            if(URLEncoder.encode(removeDiacriticalMarks(bangcap),"utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getBangCap().get(0)).toLowerCase(),"utf-8")))
                                                            {
                                                                if(URLEncoder.encode(removeDiacriticalMarks(kh.getDiaDiem().getQuan()).toLowerCase(),"utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getDiaDiem().getDayDu()).toLowerCase(),"utf-8")))
                                                                {
                                                                    if(Double.parseDouble(kh.getHocPhi())>=hocPhiDuoi && Double.parseDouble(kh.getHocPhi())<=hocPhiTren)
                                                                    {
                                                                        if(kh.getGioiTinh().equals(khoaHoc.getGioiTinh()))
                                                                        {
                                                                            khoaHocGanChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                                            for(String buoi : kh.getLichHoc().getThoiGian())
                                                                            {
                                                                                for(String sbuoi : khoaHoc.getLichHoc().getThoiGian())
                                                                                {
                                                                                    if(sbuoi.equals(buoi))
                                                                                    {
                                                                                        for(String thu : kh.getLichHoc().getNgayHoc())
                                                                                        {
                                                                                            for(String sthu : khoaHoc.getLichHoc().getNgayHoc())
                                                                                            {
                                                                                                if (sthu.equals(thu)) {
                                                                                                    countTemp += 1;
                                                                                                    if (countTemp == count) {
                                                                                                        khoaHocChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                                                                    }
                                                                                                }
                                                                                            }
                                                                                        }
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                } catch (UnsupportedEncodingException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                            ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHoc(khoaHocChinhXac,khoaHocGanChinhXac);
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
                    }
                }
                else
                {
                    if(khoaHoc.getDiaDiem().getDayDu().isEmpty())
                    {
                        if(hocPhiTimKiem == 0)
                        {
                            if(khoaHoc.getGioiTinh().equals("Nam, Nữ"))
                            {
                                if(khoaHoc.getBangCap() == null)//Empty ngoại trừ môn,Lĩnh vực
                                {
                                    mData.child("KhoaHoc").child("KhoaHocTimHocVien").child("ChuaHoanTat").addChildEventListener(new ChildEventListener() {
                                        @Override
                                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                            KhoaHoc kh = dataSnapshot.getValue(KhoaHoc.class);
                                            for (String mon : kh.getMon())
                                            {
                                                try {
                                                    if (URLEncoder.encode(removeDiacriticalMarks(mon), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getMon().get(0)).toLowerCase(), "utf-8")))
                                                    {
                                                        for(String linhVuc : kh.getLinhVuc())
                                                        {
                                                            if(URLEncoder.encode(removeDiacriticalMarks(linhVuc),"utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getLinhVuc().get(0)).toLowerCase(),"utf-8")))
                                                            {
                                                                khoaHocGanChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                                for(String buoi : kh.getLichHoc().getThoiGian())
                                                                {
                                                                    for(String sbuoi : khoaHoc.getLichHoc().getThoiGian())
                                                                    {
                                                                        if(sbuoi.equals(buoi))
                                                                        {
                                                                            for(String thu : kh.getLichHoc().getNgayHoc())
                                                                            {
                                                                                for(String sthu : khoaHoc.getLichHoc().getNgayHoc())
                                                                                {
                                                                                    if (sthu.equals(thu)) {
                                                                                        countTemp += 1;
                                                                                        if (countTemp == count) {
                                                                                            khoaHocChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                                                        }
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                } catch (UnsupportedEncodingException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                            ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHoc(khoaHocChinhXac,khoaHocGanChinhXac);
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
                                else//Empty ngoại trừ môn,Lĩnh vực, bằng cấp
                                {
                                    mData.child("KhoaHoc").child("KhoaHocTimHocVien").child("ChuaHoanTat").addChildEventListener(new ChildEventListener() {
                                        @Override
                                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                            KhoaHoc kh = dataSnapshot.getValue(KhoaHoc.class);
                                            for (String mon : kh.getMon())
                                            {
                                                try {
                                                    if (URLEncoder.encode(removeDiacriticalMarks(mon), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getMon().get(0)).toLowerCase(), "utf-8")))
                                                    {
                                                        for(String linhVuc : kh.getLinhVuc())
                                                        {
                                                            if(URLEncoder.encode(removeDiacriticalMarks(linhVuc),"utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getLinhVuc().get(0)).toLowerCase(),"utf-8")))
                                                            {
                                                                for(String bangcap : kh.getBangCap())
                                                                {

                                                                    if(URLEncoder.encode(removeDiacriticalMarks(bangcap),"utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getBangCap().get(0)).toLowerCase(),"utf-8")))
                                                                    {
                                                                        khoaHocGanChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                                        for(String buoi : kh.getLichHoc().getThoiGian())
                                                                        {
                                                                            for(String sbuoi : khoaHoc.getLichHoc().getThoiGian())
                                                                            {
                                                                                if(sbuoi.equals(buoi))
                                                                                {
                                                                                    for(String thu : kh.getLichHoc().getNgayHoc())
                                                                                    {
                                                                                        for(String sthu : khoaHoc.getLichHoc().getNgayHoc())
                                                                                        {
                                                                                            if (sthu.equals(thu)) {
                                                                                                countTemp += 1;
                                                                                                if (countTemp == count) {
                                                                                                    khoaHocChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                                                                }
                                                                                            }
                                                                                        }
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                } catch (UnsupportedEncodingException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                            ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHoc(khoaHocChinhXac,khoaHocGanChinhXac);
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
                            else
                            {
                                if(khoaHoc.getBangCap() == null)//Empty ngoại trừ môn,Lĩnh vực,giới tính
                                {
                                    mData.child("KhoaHoc").child("KhoaHocTimHocVien").child("ChuaHoanTat").addChildEventListener(new ChildEventListener() {
                                        @Override
                                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                            KhoaHoc kh = dataSnapshot.getValue(KhoaHoc.class);

                                            for (String mon : kh.getMon())
                                            {
                                                try {
                                                    if (URLEncoder.encode(removeDiacriticalMarks(mon), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getMon().get(0)).toLowerCase(), "utf-8")))
                                                    {
                                                        for(String linhVuc : kh.getLinhVuc())
                                                        {
                                                            if(URLEncoder.encode(removeDiacriticalMarks(linhVuc),"utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getLinhVuc().get(0)).toLowerCase(),"utf-8")))
                                                            {
                                                                if(kh.getGioiTinh().equals(khoaHoc.getGioiTinh()))
                                                                {
                                                                    khoaHocGanChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                                    for(String buoi : kh.getLichHoc().getThoiGian())
                                                                    {
                                                                        for(String sbuoi : khoaHoc.getLichHoc().getThoiGian())
                                                                        {
                                                                            if(sbuoi.equals(buoi))
                                                                            {
                                                                                for(String thu : kh.getLichHoc().getNgayHoc())
                                                                                {
                                                                                    for(String sthu : khoaHoc.getLichHoc().getNgayHoc())
                                                                                    {
                                                                                        if (sthu.equals(thu)) {
                                                                                            countTemp += 1;
                                                                                            if (countTemp == count) {
                                                                                                khoaHocChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                                                            }
                                                                                        }
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                } catch (UnsupportedEncodingException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                            ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHoc(khoaHocChinhXac,khoaHocGanChinhXac);
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
                                else//Empty ngoại trừ môn,Lĩnh vực,giới tính,bằng cấp
                                {
                                    mData.child("KhoaHoc").child("KhoaHocTimHocVien").child("ChuaHoanTat").addChildEventListener(new ChildEventListener() {
                                        @Override
                                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                            KhoaHoc kh = dataSnapshot.getValue(KhoaHoc.class);

                                            for (String mon : kh.getMon())
                                            {
                                                try {
                                                    if (URLEncoder.encode(removeDiacriticalMarks(mon), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getMon().get(0)).toLowerCase(), "utf-8")))
                                                    {
                                                        for(String linhVuc : kh.getLinhVuc())
                                                        {
                                                            if(URLEncoder.encode(removeDiacriticalMarks(linhVuc),"utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getLinhVuc().get(0)).toLowerCase(),"utf-8")))
                                                            {
                                                                for(String bangcap : kh.getBangCap())
                                                                {

                                                                    if(URLEncoder.encode(removeDiacriticalMarks(bangcap),"utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getBangCap().get(0)).toLowerCase(),"utf-8")))
                                                                    {
                                                                        if(kh.getGioiTinh().equals(khoaHoc.getGioiTinh()))
                                                                        {
                                                                            khoaHocGanChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                                            for(String buoi : kh.getLichHoc().getThoiGian())
                                                                            {
                                                                                for(String sbuoi : khoaHoc.getLichHoc().getThoiGian())
                                                                                {
                                                                                    if(sbuoi.equals(buoi))
                                                                                    {
                                                                                        for(String thu : kh.getLichHoc().getNgayHoc())
                                                                                        {
                                                                                            for(String sthu : khoaHoc.getLichHoc().getNgayHoc())
                                                                                            {
                                                                                                if (sthu.equals(thu)) {
                                                                                                    countTemp += 1;
                                                                                                    if (countTemp == count) {
                                                                                                        khoaHocChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                                                                    }
                                                                                                }
                                                                                            }
                                                                                        }
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                } catch (UnsupportedEncodingException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                            ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHoc(khoaHocChinhXac,khoaHocGanChinhXac);
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
                        else
                        {
                            if(khoaHoc.getGioiTinh().equals("Nam, Nữ"))
                            {
                                if(khoaHoc.getBangCap() == null)//empty ngoại trừ môn,Lĩnh vực,học phí
                                {
                                    mData.child("KhoaHoc").child("KhoaHocTimHocVien").child("ChuaHoanTat").addChildEventListener(new ChildEventListener() {
                                        @Override
                                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                            KhoaHoc kh = dataSnapshot.getValue(KhoaHoc.class);
                                            for (String mon : kh.getMon())
                                            {
                                                try {
                                                    if (URLEncoder.encode(removeDiacriticalMarks(mon), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getMon().get(0)).toLowerCase(), "utf-8")))
                                                    {
                                                        for(String linhVuc : kh.getLinhVuc())
                                                        {
                                                            if(URLEncoder.encode(removeDiacriticalMarks(linhVuc),"utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getLinhVuc().get(0)).toLowerCase(),"utf-8")))
                                                            {
                                                                if(Double.parseDouble(kh.getHocPhi())>=hocPhiDuoi && Double.parseDouble(kh.getHocPhi())<=hocPhiTren)
                                                                {
                                                                    khoaHocGanChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                                    for(String buoi : kh.getLichHoc().getThoiGian())
                                                                    {
                                                                        for(String sbuoi : khoaHoc.getLichHoc().getThoiGian())
                                                                        {
                                                                            if(sbuoi.equals(buoi))
                                                                            {
                                                                                for(String thu : kh.getLichHoc().getNgayHoc())
                                                                                {
                                                                                    for(String sthu : khoaHoc.getLichHoc().getNgayHoc())
                                                                                    {
                                                                                        if (sthu.equals(thu)) {
                                                                                            countTemp += 1;
                                                                                            if (countTemp == count) {
                                                                                                khoaHocChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                                                            }
                                                                                        }
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                } catch (UnsupportedEncodingException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                            ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHoc(khoaHocChinhXac,khoaHocGanChinhXac);
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
                                else//empty ngoại trừ môn,Lĩnh vực,học phí, bằng cap71
                                {

                                }
                            }
                            else
                            {
                                if(khoaHoc.getBangCap() == null)//empty ngoại trừ môn,Lĩnh vực,học phí,giới tính
                                {
                                    mData.child("KhoaHoc").child("KhoaHocTimHocVien").child("ChuaHoanTat").addChildEventListener(new ChildEventListener() {
                                        @Override
                                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                            KhoaHoc kh  = dataSnapshot.getValue(KhoaHoc.class);
                                            for (String mon : kh.getMon())
                                            {
                                                try {
                                                    if (URLEncoder.encode(removeDiacriticalMarks(mon), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getMon().get(0)).toLowerCase(), "utf-8")))
                                                    {
                                                        for(String linhVuc : kh.getLinhVuc())
                                                        {
                                                            if(URLEncoder.encode(removeDiacriticalMarks(linhVuc),"utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getLinhVuc().get(0)).toLowerCase(),"utf-8")))
                                                            {
                                                                if(Double.parseDouble(kh.getHocPhi())>=hocPhiDuoi && Double.parseDouble(kh.getHocPhi())<=hocPhiTren)
                                                                {
                                                                    if(kh.getGioiTinh().equals(khoaHoc.getGioiTinh()))
                                                                    {
                                                                        khoaHocGanChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                                        for(String buoi : kh.getLichHoc().getThoiGian())
                                                                        {
                                                                            for(String sbuoi : khoaHoc.getLichHoc().getThoiGian())
                                                                            {
                                                                                if(sbuoi.equals(buoi))
                                                                                {
                                                                                    for(String thu : kh.getLichHoc().getNgayHoc())
                                                                                    {
                                                                                        for(String sthu : khoaHoc.getLichHoc().getNgayHoc())
                                                                                        {
                                                                                            if (sthu.equals(thu)) {
                                                                                                countTemp += 1;
                                                                                                if (countTemp == count) {
                                                                                                    khoaHocChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                                                                }
                                                                                            }
                                                                                        }
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                } catch (UnsupportedEncodingException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                            ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHoc(khoaHocChinhXac,khoaHocGanChinhXac);
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
                                else//empty ngoại trừ môn,Lĩnh vực,học phí,giới tính,bằng cấp
                                {
                                    mData.child("KhoaHoc").child("KhoaHocTimHocVien").child("ChuaHoanTat").addChildEventListener(new ChildEventListener() {
                                        @Override
                                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                            KhoaHoc kh = dataSnapshot.getValue(KhoaHoc.class);
                                            for (String mon : kh.getMon())
                                            {
                                                try {
                                                    if (URLEncoder.encode(removeDiacriticalMarks(mon), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getMon().get(0)).toLowerCase(), "utf-8")))
                                                    {
                                                        for(String linhVuc : kh.getLinhVuc())
                                                        {
                                                            if(URLEncoder.encode(removeDiacriticalMarks(linhVuc),"utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getLinhVuc().get(0)).toLowerCase(),"utf-8")))
                                                            {
                                                                for(String bangcap : kh.getBangCap())
                                                                {

                                                                    if(URLEncoder.encode(removeDiacriticalMarks(bangcap),"utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getBangCap().get(0)).toLowerCase(),"utf-8")))
                                                                    {
                                                                        if(Double.parseDouble(kh.getHocPhi())>=hocPhiDuoi && Double.parseDouble(kh.getHocPhi())<=hocPhiTren)
                                                                        {
                                                                            if(kh.getGioiTinh().equals(khoaHoc.getGioiTinh()))
                                                                            {
                                                                                khoaHocGanChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                                                for(String buoi : kh.getLichHoc().getThoiGian())
                                                                                {
                                                                                    for(String sbuoi : khoaHoc.getLichHoc().getThoiGian())
                                                                                    {
                                                                                        if(sbuoi.equals(buoi))
                                                                                        {
                                                                                            for(String thu : kh.getLichHoc().getNgayHoc())
                                                                                            {
                                                                                                for(String sthu : khoaHoc.getLichHoc().getNgayHoc())
                                                                                                {
                                                                                                    if (sthu.equals(thu)) {
                                                                                                        countTemp += 1;
                                                                                                        if (countTemp == count) {
                                                                                                            khoaHocChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                                                                        }
                                                                                                    }
                                                                                                }
                                                                                            }
                                                                                        }
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                } catch (UnsupportedEncodingException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                            ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHoc(khoaHocChinhXac,khoaHocGanChinhXac);
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
                    }
                    else
                    {
                        if(hocPhiTimKiem == 0)
                        {
                            if(khoaHoc.getGioiTinh().equals("Nam, Nữ"))
                            {
                                if(khoaHoc.getBangCap() == null)//Empty ngoài môn,Lĩnh vực, địa điểm
                                {
                                    mData.child("KhoaHoc").child("KhoaHocTimHocVien").child("ChuaHoanTat").addChildEventListener(new ChildEventListener() {
                                        @Override
                                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                            KhoaHoc kh = dataSnapshot.getValue(KhoaHoc.class);
                                            for (String mon : kh.getMon())
                                            {
                                                try {
                                                    if (URLEncoder.encode(removeDiacriticalMarks(mon), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getMon().get(0)).toLowerCase(), "utf-8")))
                                                    {
                                                        for(String linhVuc : kh.getLinhVuc())
                                                        {
                                                            if(URLEncoder.encode(removeDiacriticalMarks(linhVuc),"utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getLinhVuc().get(0)).toLowerCase(),"utf-8")))
                                                            {
                                                                if(URLEncoder.encode(removeDiacriticalMarks(kh.getDiaDiem().getQuan()).toLowerCase(),"utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getDiaDiem().getDayDu()).toLowerCase(),"utf-8")))
                                                                {
                                                                    khoaHocGanChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                                    for(String buoi : kh.getLichHoc().getThoiGian())
                                                                    {
                                                                        for(String sbuoi : khoaHoc.getLichHoc().getThoiGian())
                                                                        {
                                                                            if(sbuoi.equals(buoi))
                                                                            {
                                                                                for(String thu : kh.getLichHoc().getNgayHoc())
                                                                                {
                                                                                    for(String sthu : khoaHoc.getLichHoc().getNgayHoc())
                                                                                    {
                                                                                        if (sthu.equals(thu)) {
                                                                                            countTemp += 1;
                                                                                            if (countTemp == count) {
                                                                                                khoaHocChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                                                            }
                                                                                        }
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                } catch (UnsupportedEncodingException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                            ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHoc(khoaHocChinhXac,khoaHocGanChinhXac);
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
                                else//Empty ngoài môn,Lĩnh vực, địa điểm, bằng cấp
                                {
                                    mData.child("KhoaHoc").child("KhoaHocTimHocVien").child("ChuaHoanTat").addChildEventListener(new ChildEventListener() {
                                        @Override
                                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                            KhoaHoc kh = dataSnapshot.getValue(KhoaHoc.class);
                                            for (String mon : kh.getMon())
                                            {
                                                try {
                                                    if (URLEncoder.encode(removeDiacriticalMarks(mon), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getMon().get(0)).toLowerCase(), "utf-8")))
                                                    {
                                                        for(String linhVuc : kh.getLinhVuc())
                                                        {
                                                            if(URLEncoder.encode(removeDiacriticalMarks(linhVuc),"utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getLinhVuc().get(0)).toLowerCase(),"utf-8")))
                                                            {
                                                                for(String bangcap : kh.getBangCap())
                                                                {

                                                                    if(URLEncoder.encode(removeDiacriticalMarks(bangcap),"utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getBangCap().get(0)).toLowerCase(),"utf-8")))
                                                                    {
                                                                        if(URLEncoder.encode(removeDiacriticalMarks(kh.getDiaDiem().getQuan()).toLowerCase(),"utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getDiaDiem().getDayDu()).toLowerCase(),"utf-8")))
                                                                        {
                                                                            khoaHocGanChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                                            for(String buoi : kh.getLichHoc().getThoiGian())
                                                                            {
                                                                                for(String sbuoi : khoaHoc.getLichHoc().getThoiGian())
                                                                                {
                                                                                    if(sbuoi.equals(buoi))
                                                                                    {
                                                                                        for(String thu : kh.getLichHoc().getNgayHoc())
                                                                                        {
                                                                                            for(String sthu : khoaHoc.getLichHoc().getNgayHoc())
                                                                                            {
                                                                                                if (sthu.equals(thu)) {
                                                                                                    countTemp += 1;
                                                                                                    if (countTemp == count) {
                                                                                                        khoaHocChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                                                                    }
                                                                                                }
                                                                                            }
                                                                                        }
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                } catch (UnsupportedEncodingException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                            ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHoc(khoaHocChinhXac,khoaHocGanChinhXac);
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
                            else
                            {
                                if(khoaHoc.getBangCap() == null)//Empty ngoài môn,Lĩnh vực, địa điểm,giới tính
                                {
                                    mData.child("KhoaHoc").child("KhoaHocTimHocVien").child("ChuaHoanTat").addChildEventListener(new ChildEventListener() {
                                        @Override
                                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                            KhoaHoc kh = dataSnapshot.getValue(KhoaHoc.class);
                                            for (String mon : kh.getMon())
                                            {
                                                try {
                                                    if (URLEncoder.encode(removeDiacriticalMarks(mon), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getMon().get(0)).toLowerCase(), "utf-8")))
                                                    {
                                                        for(String linhVuc : kh.getLinhVuc())
                                                        {
                                                            if(URLEncoder.encode(removeDiacriticalMarks(linhVuc),"utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getLinhVuc().get(0)).toLowerCase(),"utf-8")))
                                                            {
                                                                if(URLEncoder.encode(removeDiacriticalMarks(kh.getDiaDiem().getQuan()).toLowerCase(),"utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getDiaDiem().getDayDu()).toLowerCase(),"utf-8")))
                                                                {
                                                                    if(kh.getGioiTinh().equals(khoaHoc.getGioiTinh()))
                                                                    {
                                                                        khoaHocGanChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                                        for(String buoi : kh.getLichHoc().getThoiGian())
                                                                        {
                                                                            for(String sbuoi : khoaHoc.getLichHoc().getThoiGian())
                                                                            {
                                                                                if(sbuoi.equals(buoi))
                                                                                {
                                                                                    for(String thu : kh.getLichHoc().getNgayHoc())
                                                                                    {
                                                                                        for(String sthu : khoaHoc.getLichHoc().getNgayHoc())
                                                                                        {
                                                                                            if (sthu.equals(thu)) {
                                                                                                countTemp += 1;
                                                                                                if (countTemp == count) {
                                                                                                    khoaHocChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                                                                }
                                                                                            }
                                                                                        }
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                } catch (UnsupportedEncodingException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                            ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHoc(khoaHocChinhXac,khoaHocGanChinhXac);
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
                                else//Empty ngoài môn,Lĩnh vực, địa điểm,giới tính,bằng cấp
                                {
                                    mData.child("KhoaHoc").child("KhoaHocTimHocVien").child("ChuaHoanTat").addChildEventListener(new ChildEventListener() {
                                        @Override
                                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                            KhoaHoc kh = dataSnapshot.getValue(KhoaHoc.class);
                                            for (String mon : kh.getMon())
                                            {
                                                try {
                                                    if (URLEncoder.encode(removeDiacriticalMarks(mon), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getMon().get(0)).toLowerCase(), "utf-8")))
                                                    {
                                                        for(String linhVuc : kh.getLinhVuc())
                                                        {
                                                            if(URLEncoder.encode(removeDiacriticalMarks(linhVuc),"utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getLinhVuc().get(0)).toLowerCase(),"utf-8")))
                                                            {
                                                                for(String bangcap : kh.getBangCap())
                                                                {

                                                                    if(URLEncoder.encode(removeDiacriticalMarks(bangcap),"utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getBangCap().get(0)).toLowerCase(),"utf-8")))
                                                                    {
                                                                        if(URLEncoder.encode(removeDiacriticalMarks(kh.getDiaDiem().getQuan()).toLowerCase(),"utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getDiaDiem().getDayDu()).toLowerCase(),"utf-8")))
                                                                        {
                                                                            if(kh.getGioiTinh().equals(khoaHoc.getGioiTinh()))
                                                                            {
                                                                                khoaHocGanChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                                                for(String buoi : kh.getLichHoc().getThoiGian())
                                                                                {
                                                                                    for(String sbuoi : khoaHoc.getLichHoc().getThoiGian())
                                                                                    {
                                                                                        if(sbuoi.equals(buoi))
                                                                                        {
                                                                                            for(String thu : kh.getLichHoc().getNgayHoc())
                                                                                            {
                                                                                                for(String sthu : khoaHoc.getLichHoc().getNgayHoc())
                                                                                                {
                                                                                                    if (sthu.equals(thu)) {
                                                                                                        countTemp += 1;
                                                                                                        if (countTemp == count) {
                                                                                                            khoaHocChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                                                                        }
                                                                                                    }
                                                                                                }
                                                                                            }
                                                                                        }
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                } catch (UnsupportedEncodingException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                            ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHoc(khoaHocChinhXac,khoaHocGanChinhXac);
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
                        else
                        {
                            if(khoaHoc.getGioiTinh().equals("Nam, Nữ"))
                            {
                                if(khoaHoc.getBangCap() == null)//Empty ngoài môn,Lĩnh vục, địa điểm,học phí
                                {
                                    mData.child("KhoaHoc").child("KhoaHocTimHocVien").child("ChuaHoanTat").addChildEventListener(new ChildEventListener() {
                                        @Override
                                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                            KhoaHoc kh = dataSnapshot.getValue(KhoaHoc.class);

                                            for (String mon : kh.getMon())
                                            {
                                                try {
                                                    if (URLEncoder.encode(removeDiacriticalMarks(mon), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getMon().get(0)).toLowerCase(), "utf-8")))
                                                    {
                                                        for(String linhVuc : kh.getLinhVuc())
                                                        {
                                                            if(URLEncoder.encode(removeDiacriticalMarks(linhVuc),"utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getLinhVuc().get(0)).toLowerCase(),"utf-8")))
                                                            {
                                                                if(URLEncoder.encode(removeDiacriticalMarks(kh.getDiaDiem().getQuan()).toLowerCase(),"utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getDiaDiem().getDayDu()).toLowerCase(),"utf-8")))
                                                                {
                                                                    if(Double.parseDouble(kh.getHocPhi())>=hocPhiDuoi && Double.parseDouble(kh.getHocPhi())<=hocPhiTren)
                                                                    {
                                                                        khoaHocGanChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                                        for(String buoi : kh.getLichHoc().getThoiGian())
                                                                        {
                                                                            for(String sbuoi : khoaHoc.getLichHoc().getThoiGian())
                                                                            {
                                                                                if(sbuoi.equals(buoi))
                                                                                {
                                                                                    for(String thu : kh.getLichHoc().getNgayHoc())
                                                                                    {
                                                                                        for(String sthu : khoaHoc.getLichHoc().getNgayHoc())
                                                                                        {
                                                                                            if (sthu.equals(thu)) {
                                                                                                countTemp += 1;
                                                                                                if (countTemp == count) {
                                                                                                    khoaHocChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                                                                }
                                                                                            }
                                                                                        }
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                } catch (UnsupportedEncodingException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                            ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHoc(khoaHocChinhXac,khoaHocGanChinhXac);
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
                                else//Empty ngoài môn,Lĩnh vục, địa điểm,học phí, bằng cấp
                                {
                                    mData.child("KhoaHoc").child("KhoaHocTimHocVien").child("ChuaHoanTat").addChildEventListener(new ChildEventListener() {
                                        @Override
                                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                            KhoaHoc kh = dataSnapshot.getValue(KhoaHoc.class);

                                            for (String mon : kh.getMon())
                                            {
                                                try {
                                                    if (URLEncoder.encode(removeDiacriticalMarks(mon), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getMon().get(0)).toLowerCase(), "utf-8")))
                                                    {
                                                        for(String linhVuc : kh.getLinhVuc())
                                                        {
                                                            if(URLEncoder.encode(removeDiacriticalMarks(linhVuc),"utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getLinhVuc().get(0)).toLowerCase(),"utf-8")))
                                                            {
                                                                for(String bangcap : kh.getBangCap())
                                                                {

                                                                    if(URLEncoder.encode(removeDiacriticalMarks(bangcap),"utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getBangCap().get(0)).toLowerCase(),"utf-8")))
                                                                    {
                                                                        if(URLEncoder.encode(removeDiacriticalMarks(kh.getDiaDiem().getQuan()).toLowerCase(),"utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getDiaDiem().getDayDu()).toLowerCase(),"utf-8")))
                                                                        {
                                                                            if(Double.parseDouble(kh.getHocPhi())>=hocPhiDuoi && Double.parseDouble(kh.getHocPhi())<=hocPhiTren)
                                                                            {
                                                                                khoaHocGanChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                                                for(String buoi : kh.getLichHoc().getThoiGian())
                                                                                {
                                                                                    for(String sbuoi : khoaHoc.getLichHoc().getThoiGian())
                                                                                    {
                                                                                        if(sbuoi.equals(buoi))
                                                                                        {
                                                                                            for(String thu : kh.getLichHoc().getNgayHoc())
                                                                                            {
                                                                                                for(String sthu : khoaHoc.getLichHoc().getNgayHoc())
                                                                                                {
                                                                                                    if (sthu.equals(thu)) {
                                                                                                        countTemp += 1;
                                                                                                        if (countTemp == count) {
                                                                                                            khoaHocChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                                                                        }
                                                                                                    }
                                                                                                }
                                                                                            }
                                                                                        }
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                } catch (UnsupportedEncodingException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                            ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHoc(khoaHocChinhXac,khoaHocGanChinhXac);
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
                            else
                            {
                                if(khoaHoc.getBangCap() == null)//Empty ngoài môn,Lĩnh vục, địa điểm,học phí,giới tính
                                {
                                    mData.child("KhoaHoc").child("KhoaHocTimHocVien").child("ChuaHoanTat").addChildEventListener(new ChildEventListener() {
                                        @Override
                                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                            KhoaHoc kh  =dataSnapshot.getValue(KhoaHoc.class);
                                            for (String mon : kh.getMon())
                                            {
                                                try {
                                                    if (URLEncoder.encode(removeDiacriticalMarks(mon), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getMon().get(0)).toLowerCase(), "utf-8")))
                                                    {
                                                        for(String linhVuc : kh.getLinhVuc())
                                                        {
                                                            if(URLEncoder.encode(removeDiacriticalMarks(linhVuc),"utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getLinhVuc().get(0)).toLowerCase(),"utf-8")))
                                                            {
                                                                if(URLEncoder.encode(removeDiacriticalMarks(kh.getDiaDiem().getQuan()).toLowerCase(),"utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getDiaDiem().getDayDu()).toLowerCase(),"utf-8")))
                                                                {
                                                                    if(Double.parseDouble(kh.getHocPhi())>=hocPhiDuoi && Double.parseDouble(kh.getHocPhi())<=hocPhiTren)
                                                                    {
                                                                        if(kh.getGioiTinh().equals(khoaHoc.getGioiTinh()))
                                                                        {
                                                                            khoaHocGanChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                                            for(String buoi : kh.getLichHoc().getThoiGian())
                                                                            {
                                                                                for(String sbuoi : khoaHoc.getLichHoc().getThoiGian())
                                                                                {
                                                                                    if(sbuoi.equals(buoi))
                                                                                    {
                                                                                        for(String thu : kh.getLichHoc().getNgayHoc())
                                                                                        {
                                                                                            for(String sthu : khoaHoc.getLichHoc().getNgayHoc())
                                                                                            {
                                                                                                if (sthu.equals(thu)) {
                                                                                                    countTemp += 1;
                                                                                                    if (countTemp == count) {
                                                                                                        khoaHocChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                                                                    }
                                                                                                }
                                                                                            }
                                                                                        }
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                } catch (UnsupportedEncodingException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                            ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHoc(khoaHocChinhXac,khoaHocGanChinhXac);
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
                                else//Empty ngoài môn,Lĩnh vục, địa điểm,học phí,giới tính,bằng cấp
                                {
                                    mData.child("KhoaHoc").child("KhoaHocTimHocVien").child("ChuaHoanTat").addChildEventListener(new ChildEventListener() {
                                        @Override
                                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                            KhoaHoc kh = dataSnapshot.getValue(KhoaHoc.class);

                                            for (String mon : kh.getMon())
                                            {
                                                try {
                                                    if (URLEncoder.encode(removeDiacriticalMarks(mon), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getMon().get(0)).toLowerCase(), "utf-8")))
                                                    {
                                                        for(String linhVuc : kh.getLinhVuc())
                                                        {
                                                            if(URLEncoder.encode(removeDiacriticalMarks(linhVuc),"utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getLinhVuc().get(0)).toLowerCase(),"utf-8")))
                                                            {
                                                                for(String bangcap : kh.getBangCap())
                                                                {

                                                                    if(URLEncoder.encode(removeDiacriticalMarks(bangcap),"utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getBangCap().get(0)).toLowerCase(),"utf-8")))
                                                                    {
                                                                        if(URLEncoder.encode(removeDiacriticalMarks(kh.getDiaDiem().getQuan()).toLowerCase(),"utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getDiaDiem().getDayDu()).toLowerCase(),"utf-8")))
                                                                        {
                                                                            if(Double.parseDouble(kh.getHocPhi())>=hocPhiDuoi && Double.parseDouble(kh.getHocPhi())<=hocPhiTren)
                                                                            {
                                                                                if(kh.getGioiTinh().equals(khoaHoc.getGioiTinh()))
                                                                                {
                                                                                    khoaHocGanChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                                                    for(String buoi : kh.getLichHoc().getThoiGian())
                                                                                    {
                                                                                        for(String sbuoi : khoaHoc.getLichHoc().getThoiGian())
                                                                                        {
                                                                                            if(sbuoi.equals(buoi))
                                                                                            {
                                                                                                for(String thu : kh.getLichHoc().getNgayHoc())
                                                                                                {
                                                                                                    for(String sthu : khoaHoc.getLichHoc().getNgayHoc())
                                                                                                    {
                                                                                                        if (sthu.equals(thu)) {
                                                                                                            countTemp += 1;
                                                                                                            if (countTemp == count) {
                                                                                                                khoaHocChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                                                                            }
                                                                                                        }
                                                                                                    }
                                                                                                }
                                                                                            }
                                                                                        }
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                } catch (UnsupportedEncodingException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                            ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHoc(khoaHocChinhXac,khoaHocGanChinhXac);
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
                    }
                }
            }
        }

//        if (loai) {
//            if (khoaHoc.getLinhVuc() == null && khoaHoc.getMon().size() == 0) {
//                if (hocPhiTimKiem == 0 && khoaHoc.getDiaDiem().getDayDu().equals(null)) {
//                    mData.child("KhoaHoc").child("KhoaHocTimGiaSu").child("ChuaHoanTat").addChildEventListener(new ChildEventListener() {
//                        @Override
//                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//                            KhoaHoc kh = dataSnapshot.getValue(KhoaHoc.class);
//                            for (String buoi : kh.getLichHoc().getThoiGian()) {
//                                for (String sbuoi : khoaHoc.getLichHoc().getThoiGian()) {
//                                    if (buoi.equals(sbuoi)) {
//                                        khoaHocGanChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
//                                        if (khoaHoc.getGioiTinh().equals(kh.getGioiTinh())) {
//                                            for (String ngay : kh.getLichHoc().getNgayHoc())
//                                                for (String sngay : khoaHoc.getLichHoc().getNgayHoc())
//                                                    if (ngay.equals(sngay)) {
//                                                        tempCount += 1;
//                                                        if (tempCount == count) {
//                                                            khoaHocChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
//                                                        }
//                                                    }
//                                        }
//                                    }
//                                }
//                            }
//                            ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHoc(khoaHocChinhXac, khoaHocGanChinhXac);
//                        }
//
//                        @Override
//                        public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//
//                        }
//
//                        @Override
//                        public void onChildRemoved(DataSnapshot dataSnapshot) {
//
//                        }
//
//                        @Override
//                        public void onChildMoved(DataSnapshot dataSnapshot, String s) {
//
//                        }
//
//                        @Override
//                        public void onCancelled(DatabaseError databaseError) {
//
//                        }
//                    });//Tìm kiếm ko key (Chỉ lấy theo lịch học)
//                }
//                if (hocPhiTimKiem != 0 && khoaHoc.getDiaDiem().getDayDu() != null) {
//                    mData.child("KhoaHoc").child("KhoaHocTimGiaSu").child("ChuaHoanTat").addChildEventListener(new ChildEventListener() {
//                        @Override
//                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//                            KhoaHoc kh = dataSnapshot.getValue(KhoaHoc.class);
//                            if (Double.parseDouble(kh.getHocPhi()) >= hocPhiDuoi && Double.parseDouble(kh.getHocPhi()) <= hocPhiTren) {
//                                khoaHocGanChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
//                                if (khoaHoc.getGioiTinh().equals(kh.getGioiTinh())) {
//                                    if (URLEncoder.encode(removeDiacriticalMarks(kh.getDiaDiem().getQuan())).contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getDiaDiem().getDayDu())))) {
//                                        for (String buoi : kh.getLichHoc().getThoiGian()) {
//                                            for (String sbuoi : khoaHoc.getLichHoc().getThoiGian()) {
//                                                if (buoi.equals(sbuoi)) {
//                                                    for (String ngay : kh.getLichHoc().getNgayHoc())
//                                                        for (String sngay : khoaHoc.getLichHoc().getNgayHoc())
//                                                            if (ngay.equals(sngay)) {
//                                                                tempCount += 1;
//                                                                if (tempCount == count) {
//                                                                    khoaHocChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
//                                                                }
//                                                            }
//                                                }
//                                            }
//                                        }
//                                    }
//                                }
//                            }
//                            ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHoc(khoaHocChinhXac, khoaHocGanChinhXac);
//                        }
//
//                        @Override
//                        public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//
//                        }
//
//                        @Override
//                        public void onChildRemoved(DataSnapshot dataSnapshot) {
//
//                        }
//
//                        @Override
//                        public void onChildMoved(DataSnapshot dataSnapshot, String s) {
//
//                        }
//
//                        @Override
//                        public void onCancelled(DatabaseError databaseError) {
//
//                        }
//                    });//Tìm kiếm tất c
//                }
//                if (hocPhiTimKiem != 0 && khoaHoc.getDiaDiem().getDayDu().equals(null)) {
//                    mData.child("KhoaHoc").child("KhoaHocTimGiaSu").child("ChuaHoanTat").addChildEventListener(new ChildEventListener() {
//                        @Override
//                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//                            KhoaHoc kh = dataSnapshot.getValue(KhoaHoc.class);
//                            if (Double.parseDouble(kh.getHocPhi()) >= hocPhiDuoi && Double.parseDouble(kh.getHocPhi()) <= hocPhiTren) {
//                                khoaHocGanChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
//                                if (khoaHoc.getGioiTinh().equals(kh.getGioiTinh())) {
//                                    for (String buoi : kh.getLichHoc().getThoiGian()) {
//                                        for (String sbuoi : khoaHoc.getLichHoc().getThoiGian()) {
//                                            if (buoi.equals(sbuoi)) {
//                                                for (String ngay : kh.getLichHoc().getNgayHoc())
//                                                    for (String sngay : khoaHoc.getLichHoc().getNgayHoc())
//                                                        if (ngay.equals(sngay)) {
//                                                            tempCount += 1;
//                                                            if (tempCount == count) {
//                                                                khoaHocChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
//                                                            }
//                                                        }
//                                            }
//                                        }
//                                    }
//                                }
//                            }
//                            ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHoc(khoaHocChinhXac, khoaHocGanChinhXac);
//                        }
//
//                        @Override
//                        public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//
//                        }
//
//                        @Override
//                        public void onChildRemoved(DataSnapshot dataSnapshot) {
//
//                        }
//
//                        @Override
//                        public void onChildMoved(DataSnapshot dataSnapshot, String s) {
//
//                        }
//
//                        @Override
//                        public void onCancelled(DatabaseError databaseError) {
//
//                        }
//                    });
//                }
//                if (hocPhiTimKiem == 0 && khoaHoc.getDiaDiem().getDayDu() != null) {
//                    mData.child("KhoaHoc").child("KhoaHocTimGiaSu").child("ChuaHoanTat").addChildEventListener(new ChildEventListener() {
//                        @Override
//                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//                            KhoaHoc kh = dataSnapshot.getValue(KhoaHoc.class);
//                            if (URLEncoder.encode(removeDiacriticalMarks(kh.getDiaDiem().getQuan())).contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getDiaDiem().getDayDu())))) {
//                                khoaHocGanChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
//                                if (khoaHoc.getGioiTinh().equals(kh.getGioiTinh())) {
//                                    for (String buoi : kh.getLichHoc().getThoiGian()) {
//                                        for (String sbuoi : khoaHoc.getLichHoc().getThoiGian()) {
//                                            if (buoi.equals(sbuoi)) {
//                                                for (String ngay : kh.getLichHoc().getNgayHoc())
//                                                    for (String sngay : khoaHoc.getLichHoc().getNgayHoc())
//                                                        if (ngay.equals(sngay)) {
//                                                            tempCount += 1;
//                                                            if (tempCount == count) {
//                                                                khoaHocChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
//                                                            }
//                                                        }
//                                            }
//                                        }
//                                    }
//                                }
//                            }
//                            ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHoc(khoaHocChinhXac, khoaHocGanChinhXac);
//                        }
//
//                        @Override
//                        public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//
//                        }
//
//                        @Override
//                        public void onChildRemoved(DataSnapshot dataSnapshot) {
//
//                        }
//
//                        @Override
//                        public void onChildMoved(DataSnapshot dataSnapshot, String s) {
//
//                        }
//
//                        @Override
//                        public void onCancelled(DatabaseError databaseError) {
//
//                        }
//                    });
//                }
//
//            }
//            if (khoaHoc.getLinhVuc().size() != 0 && khoaHoc.getMon().size() == 0) {
//                if (hocPhiTimKiem == 0 && khoaHoc.getDiaDiem().getDayDu().equals(null)) {
//
//                    if (hocPhiTimKiem == 0 && khoaHoc.getDiaDiem().getDayDu().equals(null)) {
//                        mData.child("KhoaHoc").child("KhoaHocTimGiaSu").child("ChuaHoanTat").addChildEventListener(new ChildEventListener() {
//                            @Override
//                            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//
//                            }
//
//                            @Override
//                            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//
//                            }
//
//                            @Override
//                            public void onChildRemoved(DataSnapshot dataSnapshot) {
//
//                            }
//
//                            @Override
//                            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
//
//                            }
//
//                            @Override
//                            public void onCancelled(DatabaseError databaseError) {
//
//                            }
//                        })
//                    }
//                    if (hocPhiTimKiem != 0 && khoaHoc.getDiaDiem().getDayDu() != null) {
//
//                    }
//                    if (hocPhiTimKiem != 0 && khoaHoc.getDiaDiem().getDayDu().equals(null)) {
//
//                    }
//                    if (hocPhiTimKiem == 0 && khoaHoc.getDiaDiem().getDayDu() != null) {
//
//                    }
//                }
//                if (khoaHoc.getLinhVuc() == null && khoaHoc.getMon().size() != 0) {
//                    if (hocPhiTimKiem == 0 && khoaHoc.getDiaDiem().getDayDu().equals(null)) {
//
//                    }
//                    if (hocPhiTimKiem != 0 && khoaHoc.getDiaDiem().getDayDu() != null) {
//
//                    }
//                    if (hocPhiTimKiem != 0 && khoaHoc.getDiaDiem().getDayDu().equals(null)) {
//
//                    }
//                    if (hocPhiTimKiem == 0 && khoaHoc.getDiaDiem().getDayDu() != null) {
//
//                    }
//                }
//                if (khoaHoc.getLinhVuc().size() != 0 && khoaHoc.getMon().size() != 0) {
//                    if (hocPhiTimKiem == 0 && khoaHoc.getDiaDiem().getDayDu().equals(null)) {
//
//                    }
//                    if (hocPhiTimKiem != 0 && khoaHoc.getDiaDiem().getDayDu() != null) {
//
//                    }
//                    if (hocPhiTimKiem != 0 && khoaHoc.getDiaDiem().getDayDu().equals(null)) {
//
//                    }
//                    if (hocPhiTimKiem == 0 && khoaHoc.getDiaDiem().getDayDu() != null) {
//
//                    }
//                }
//            } else {
//                if (khoaHoc.getLinhVuc() == null && khoaHoc.getMon().size() == 0) {
//                    if (hocPhiTimKiem == 0 && khoaHoc.getDiaDiem().getDayDu().equals(null)) {
//
//                    }
//                    if (hocPhiTimKiem != 0 && khoaHoc.getDiaDiem().getDayDu() != null) {
//
//                    }
//                    if (hocPhiTimKiem != 0 && khoaHoc.getDiaDiem().getDayDu().equals(null)) {
//
//                    }
//                    if (hocPhiTimKiem == 0 && khoaHoc.getDiaDiem().getDayDu() != null) {
//
//                    }
//                }
//                if (khoaHoc.getLinhVuc().size() != 0 && khoaHoc.getMon().size() == 0) {
//                    if (hocPhiTimKiem == 0 && khoaHoc.getDiaDiem().getDayDu().equals(null)) {
//
//                    }
//                    if (hocPhiTimKiem != 0 && khoaHoc.getDiaDiem().getDayDu() != null) {
//
//                    }
//                    if (hocPhiTimKiem != 0 && khoaHoc.getDiaDiem().getDayDu().equals(null)) {
//
//                    }
//                    if (hocPhiTimKiem == 0 && khoaHoc.getDiaDiem().getDayDu() != null) {
//
//                    }
//                }
//                if (khoaHoc.getLinhVuc() == null && khoaHoc.getMon().size() != 0) {
//                    if (hocPhiTimKiem == 0 && khoaHoc.getDiaDiem().getDayDu().equals(null)) {
//
//                    }
//                    if (hocPhiTimKiem != 0 && khoaHoc.getDiaDiem().getDayDu() != null) {
//
//                    }
//                    if (hocPhiTimKiem != 0 && khoaHoc.getDiaDiem().getDayDu().equals(null)) {
//
//                    }
//                    if (hocPhiTimKiem == 0 && khoaHoc.getDiaDiem().getDayDu() != null) {
//
//                    }
//                }
//                if (khoaHoc.getLinhVuc().size() != 0 && khoaHoc.getMon().size() != 0) {
//                    if (hocPhiTimKiem == 0 && khoaHoc.getDiaDiem().getDayDu().equals(null)) {
//
//                    }
//                    if (hocPhiTimKiem != 0 && khoaHoc.getDiaDiem().getDayDu() != null) {
//
//                    }
//                    if (hocPhiTimKiem != 0 && khoaHoc.getDiaDiem().getDayDu().equals(null)) {
//
//                    }
//                    if (hocPhiTimKiem == 0 && khoaHoc.getDiaDiem().getDayDu() != null) {
//
//                    }
//                }
//            }

//        if (loai == true) {
//            mData.child(SupportKeysList.CHILD_KHOAHOC).child(SupportKeysList.CHILD_KHOAHOC_TIMGIASU).child(SupportKeysList.CHILD_KHOAHOC_CHUAHOANTAT).addChildEventListener(new ChildEventListener() {
//                @Override
//                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//                    KhoaHoc kh = dataSnapshot.getValue(KhoaHoc.class);
//                    if (khoaHoc.getMon() != null) {
//                        for (String mon : kh.getMon()) {
//                            try {
//                                if (URLEncoder.encode(removeDiacriticalMarks(mon).toLowerCase(), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getMon().get(0)).toLowerCase(), "utf-8"))) {
//                                    if (khoaHoc.getLinhVuc() != null) { //Có cả môn lẫn lĩnh vực
//                                        for (String linhVuc : khoaHoc.getLinhVuc()) {
//                                            if (URLEncoder.encode(removeDiacriticalMarks(linhVuc).toLowerCase(), "utf-8").contains(URLEncoder.encode(khoaHoc.getLinhVuc().get(0).toLowerCase(), "utf-8"))) {
//                                                khoaHocGanChinhXac.add(new CustomModelKhoaHoc(kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
//                                                if (URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getDiaDiem().getDayDu()).toLowerCase(), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(kh.getDiaDiem().getQuan()).toLowerCase(), "utf-8"))) {
//                                                    if (khoaHoc.getGioiTinh().equals("Nam, Nữ")) {
//                                                        if (hocPhiTimKiem >= hocPhiDuoi && hocPhiTimKiem <= hocPhiTren) {
//                                                            for (String buoi : khoaHoc.getLichHoc().getThoiGian()) {
//                                                                for (String b : kh.getLichHoc().getThoiGian()) {
//                                                                    if (buoi.equals(b)) {
//                                                                        for (String ngay : khoaHoc.getLichHoc().getNgayHoc()) {
//                                                                            for (String ng : kh.getLichHoc().getNgayHoc()) {
//                                                                                if (ngay.equals(ng)) {
//                                                                                    countTemp++;
//                                                                                    if (countTemp == count) {
//                                                                                        khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
//                                                                                    }
//                                                                                }
//                                                                            }
//                                                                        }
//                                                                    }
//                                                                }
//                                                            }
//                                                        }
//                                                    } else {
//                                                        if (kh.getGioiTinh().equals(khoaHoc.getGioiTinh())) {
//                                                            if (hocPhiTimKiem >= hocPhiDuoi && hocPhiTimKiem <= hocPhiTren) {
//                                                                for (String buoi : khoaHoc.getLichHoc().getThoiGian()) {
//                                                                    for (String b : kh.getLichHoc().getThoiGian()) {
//                                                                        if (buoi.equals(b)) {
//                                                                            for (String ngay : khoaHoc.getLichHoc().getNgayHoc()) {
//                                                                                for (String ng : kh.getLichHoc().getNgayHoc()) {
//                                                                                    if (ngay.equals(ng)) {
//                                                                                        countTemp++;
//                                                                                        if (countTemp == count) {
//                                                                                            khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
//                                                                                        }
//                                                                                    }
//                                                                                }
//                                                                            }
//                                                                        }
//                                                                    }
//                                                                }
//                                                            }
//                                                        }
//                                                    }
//
//                                                }
//                                            }
//                                        }
//                                        ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHoc(khoaHocChinhXac, khoaHocGanChinhXac);
//                                    } else //Khi chi co mon ma ko co linh vuc
//                                    {
//                                        khoaHocGanChinhXac.add(new CustomModelKhoaHoc(kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
//                                        if (URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getDiaDiem().getDayDu()).toLowerCase(), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(kh.getDiaDiem().getQuan()).toLowerCase(), "utf-8"))) {
//                                            if (khoaHoc.getGioiTinh().equals("Nam, Nữ")) {
//                                                if (hocPhiTimKiem >= hocPhiDuoi && hocPhiTimKiem <= hocPhiTren) {
//                                                    for (String buoi : khoaHoc.getLichHoc().getThoiGian()) {
//                                                        for (String b : kh.getLichHoc().getThoiGian()) {
//                                                            if (buoi.equals(b)) {
//                                                                for (String ngay : khoaHoc.getLichHoc().getNgayHoc()) {
//                                                                    for (String ng : kh.getLichHoc().getNgayHoc()) {
//                                                                        if (ngay.equals(ng)) {
//                                                                            countTemp++;
//                                                                            if (countTemp == count) {
//                                                                                khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
//                                                                            }
//                                                                        }
//                                                                    }
//                                                                }
//                                                            }
//                                                        }
//                                                    }
//                                                }
//
//                                            } else {
//                                                if (kh.getGioiTinh().equals(khoaHoc.getGioiTinh())) {
//                                                    if (hocPhiTimKiem >= hocPhiDuoi && hocPhiTimKiem <= hocPhiTren) {
//                                                        for (String buoi : khoaHoc.getLichHoc().getThoiGian()) {
//                                                            for (String b : kh.getLichHoc().getThoiGian()) {
//                                                                if (buoi.equals(b)) {
//                                                                    for (String ngay : khoaHoc.getLichHoc().getNgayHoc()) {
//                                                                        for (String ng : kh.getLichHoc().getNgayHoc()) {
//                                                                            if (ngay.equals(ng)) {
//                                                                                countTemp++;
//                                                                                if (countTemp == count) {
//                                                                                    khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
//                                                                                }
//                                                                            }
//                                                                        }
//                                                                    }
//                                                                }
//                                                            }
//                                                        }
//                                                    }
//                                                }
//                                            }
//                                        }
//                                        ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHoc(khoaHocChinhXac, khoaHocGanChinhXac);
//                                    }
//                                }
//                            } catch (UnsupportedEncodingException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    } else // Khi ko co mon ma co linh vuc
//                    {
//                        if (khoaHoc.getLinhVuc() != null) {
//                            for (String linhVuc : khoaHoc.getLinhVuc()) {
//                                try {
//                                    if (URLEncoder.encode(removeDiacriticalMarks(linhVuc).toLowerCase(), "utf-8").contains(URLEncoder.encode(khoaHoc.getLinhVuc().get(0).toLowerCase(), "utf-8"))) {
//                                        if (URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getDiaDiem().getDayDu()).toLowerCase(), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(kh.getDiaDiem().getQuan()).toLowerCase(), "utf-8"))) {
//                                            khoaHocGanChinhXac.add(new CustomModelKhoaHoc(kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
//                                            if (khoaHoc.getGioiTinh() != "Nam, Nữ") {
//                                                if (kh.getGioiTinh().equals(khoaHoc.getGioiTinh())) {
//                                                    if (hocPhiTimKiem >= hocPhiDuoi && hocPhiTimKiem <= hocPhiTren) {
//                                                        for (String buoi : khoaHoc.getLichHoc().getThoiGian()) {
//                                                            for (String b : kh.getLichHoc().getThoiGian()) {
//                                                                if (buoi.equals(b)) {
//                                                                    for (String ngay : khoaHoc.getLichHoc().getNgayHoc()) {
//                                                                        for (String ng : kh.getLichHoc().getNgayHoc()) {
//                                                                            if (ngay.equals(ng)) {
//                                                                                countTemp++;
//                                                                                if (countTemp == count) {
//                                                                                    khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
//                                                                                }
//                                                                            }
//                                                                        }
//                                                                    }
//                                                                }
//                                                            }
//                                                        }
//                                                    }
//                                                }
//                                            } else {
//                                                if (hocPhiTimKiem >= hocPhiDuoi && hocPhiTimKiem <= hocPhiTren) {
//                                                    for (String buoi : khoaHoc.getLichHoc().getThoiGian()) {
//                                                        for (String b : kh.getLichHoc().getThoiGian()) {
//                                                            if (buoi.equals(b)) {
//                                                                for (String ngay : khoaHoc.getLichHoc().getNgayHoc()) {
//                                                                    for (String ng : kh.getLichHoc().getNgayHoc()) {
//                                                                        if (ngay.equals(ng)) {
//                                                                            countTemp++;
//                                                                            if (countTemp == count) {
//                                                                                khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
//                                                                            }
//                                                                        }
//                                                                    }
//                                                                }
//                                                            }
//                                                        }
//                                                    }
//                                                }
//                                            }
//                                        }
//                                    }
//                                } catch (UnsupportedEncodingException e) {
//                                    e.printStackTrace();
//                                }
//                            }
//                            ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHoc(khoaHocChinhXac, khoaHocGanChinhXac);
//                        } else { //Kho vừa không có môn vừa không có lĩnh vực
//                            try {
//                                if (URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getDiaDiem().getDayDu()).toLowerCase(), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(kh.getDiaDiem().getQuan()).toLowerCase(), "utf-8"))) {
//                                    if (khoaHoc.getGioiTinh().equals("Nam, Nữ")) {
//                                        if (hocPhiTimKiem >= hocPhiDuoi && hocPhiTimKiem <= hocPhiTren) {
//                                            for (String buoi : khoaHoc.getLichHoc().getThoiGian()) {
//                                                for (String b : kh.getLichHoc().getThoiGian()) {
//                                                    if (buoi.equals(b)) {
//                                                        for (String ngay : khoaHoc.getLichHoc().getNgayHoc()) {
//                                                            for (String ng : kh.getLichHoc().getNgayHoc()) {
//                                                                if (ngay.equals(ng)) {
//                                                                    countTemp++;
//                                                                    if (countTemp == count) {
//                                                                        khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
//                                                                    }
//                                                                }
//                                                            }
//                                                        }
//                                                    }
//                                                }
//                                            }
//                                        }
//
//                                    } else {
//                                        if (kh.getGioiTinh().equals(khoaHoc.getGioiTinh())) {
//                                            if (hocPhiTimKiem >= hocPhiDuoi && hocPhiTimKiem <= hocPhiTren) {
//                                                for (String buoi : khoaHoc.getLichHoc().getThoiGian()) {
//                                                    for (String b : kh.getLichHoc().getThoiGian()) {
//                                                        if (buoi.equals(b)) {
//                                                            for (String ngay : khoaHoc.getLichHoc().getNgayHoc()) {
//                                                                for (String ng : kh.getLichHoc().getNgayHoc()) {
//                                                                    if (ngay.equals(ng)) {
//                                                                        countTemp++;
//                                                                        if (countTemp == count) {
//                                                                            khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
//                                                                        }
//                                                                    }
//                                                                }
//                                                            }
//                                                        }
//                                                    }
//                                                }
//                                            }
//                                        }
//                                    }
//                                }
//                            } catch (UnsupportedEncodingException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                        ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHoc(khoaHocChinhXac, khoaHocGanChinhXac);
//                    }
//                }
//
//                @Override
//                public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//
//                }
//
//                @Override
//                public void onChildRemoved(DataSnapshot dataSnapshot) {
//
//                }
//
//                @Override
//                public void onChildMoved(DataSnapshot dataSnapshot, String s) {
//
//                }
//
//                @Override
//                public void onCancelled(DatabaseError databaseError) {
//
//                }
//            });
//        } else {
//            mData.child(SupportKeysList.CHILD_KHOAHOC).child(SupportKeysList.CHILD_KHOAHOC_TIMHOCVIEN).child(SupportKeysList.CHILD_KHOAHOC_CHUAHOANTAT).addChildEventListener(new ChildEventListener() {
//                @Override
//                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//                    KhoaHoc kh = dataSnapshot.getValue(KhoaHoc.class);
//                    if (khoaHoc.getMon() != null) {
//                        for (String mon : kh.getMon()) {
//                            try {
//                                if (URLEncoder.encode(removeDiacriticalMarks(mon).toLowerCase(), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getMon().get(0)).toLowerCase(), "utf-8"))) {
//                                    if (khoaHoc.getLinhVuc() != null) { //Có cả môn lẫn lĩnh vực
//                                        for (String linhVuc : khoaHoc.getLinhVuc()) {
//                                            if (URLEncoder.encode(removeDiacriticalMarks(linhVuc).toLowerCase(), "utf-8").contains(URLEncoder.encode(khoaHoc.getLinhVuc().get(0).toLowerCase(), "utf-8"))) {
//                                                khoaHocGanChinhXac.add(new CustomModelKhoaHoc(kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
//                                                if (URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getDiaDiem().getDayDu()).toLowerCase(), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(kh.getDiaDiem().getQuan()).toLowerCase(), "utf-8"))) {
//                                                    if (khoaHoc.getGioiTinh().equals("Nam, Nữ")) {
//                                                        if (hocPhiTimKiem >= hocPhiDuoi && hocPhiTimKiem <= hocPhiTren) {
//                                                            for (String buoi : khoaHoc.getLichHoc().getThoiGian()) {
//                                                                for (String b : kh.getLichHoc().getThoiGian()) {
//                                                                    if (buoi.equals(b)) {
//                                                                        for (String ngay : khoaHoc.getLichHoc().getNgayHoc()) {
//                                                                            for (String ng : kh.getLichHoc().getNgayHoc()) {
//                                                                                if (ngay.equals(ng)) {
//                                                                                    countTemp++;
//                                                                                    if (countTemp == count) {
//                                                                                        khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
//                                                                                    }
//                                                                                }
//                                                                            }
//                                                                        }
//                                                                    }
//                                                                }
//                                                            }
//                                                        }
//                                                    } else {
//                                                        if (kh.getGioiTinh().equals(khoaHoc.getGioiTinh())) {
//                                                            if (hocPhiTimKiem >= hocPhiDuoi && hocPhiTimKiem <= hocPhiTren) {
//                                                                for (String buoi : khoaHoc.getLichHoc().getThoiGian()) {
//                                                                    for (String b : kh.getLichHoc().getThoiGian()) {
//                                                                        if (buoi.equals(b)) {
//                                                                            for (String ngay : khoaHoc.getLichHoc().getNgayHoc()) {
//                                                                                for (String ng : kh.getLichHoc().getNgayHoc()) {
//                                                                                    if (ngay.equals(ng)) {
//                                                                                        countTemp++;
//                                                                                        if (countTemp == count) {
//                                                                                            khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
//                                                                                        }
//                                                                                    }
//                                                                                }
//                                                                            }
//                                                                        }
//                                                                    }
//                                                                }
//                                                            }
//                                                        }
//                                                    }
//
//                                                }
//                                            }
//                                        }
//                                        ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHoc(khoaHocChinhXac, khoaHocGanChinhXac);
//                                    } else //Khi chi co mon ma ko co linh vuc
//                                    {
//                                        khoaHocGanChinhXac.add(new CustomModelKhoaHoc(kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
//                                        if (URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getDiaDiem().getDayDu()).toLowerCase(), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(kh.getDiaDiem().getQuan()).toLowerCase(), "utf-8"))) {
//                                            if (khoaHoc.getGioiTinh().equals("Nam, Nữ")) {
//                                                if (hocPhiTimKiem >= hocPhiDuoi && hocPhiTimKiem <= hocPhiTren) {
//                                                    for (String buoi : khoaHoc.getLichHoc().getThoiGian()) {
//                                                        for (String b : kh.getLichHoc().getThoiGian()) {
//                                                            if (buoi.equals(b)) {
//                                                                for (String ngay : khoaHoc.getLichHoc().getNgayHoc()) {
//                                                                    for (String ng : kh.getLichHoc().getNgayHoc()) {
//                                                                        if (ngay.equals(ng)) {
//                                                                            countTemp++;
//                                                                            if (countTemp == count) {
//                                                                                khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
//                                                                            }
//                                                                        }
//                                                                    }
//                                                                }
//                                                            }
//                                                        }
//                                                    }
//                                                }
//
//                                            } else {
//                                                if (kh.getGioiTinh().equals(khoaHoc.getGioiTinh())) {
//                                                    if (hocPhiTimKiem >= hocPhiDuoi && hocPhiTimKiem <= hocPhiTren) {
//                                                        for (String buoi : khoaHoc.getLichHoc().getThoiGian()) {
//                                                            for (String b : kh.getLichHoc().getThoiGian()) {
//                                                                if (buoi.equals(b)) {
//                                                                    for (String ngay : khoaHoc.getLichHoc().getNgayHoc()) {
//                                                                        for (String ng : kh.getLichHoc().getNgayHoc()) {
//                                                                            if (ngay.equals(ng)) {
//                                                                                countTemp++;
//                                                                                if (countTemp == count) {
//                                                                                    khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
//                                                                                }
//                                                                            }
//                                                                        }
//                                                                    }
//                                                                }
//                                                            }
//                                                        }
//                                                    }
//                                                }
//                                            }
//                                        }
//                                        ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHoc(khoaHocChinhXac, khoaHocGanChinhXac);
//                                    }
//                                }
//                            } catch (UnsupportedEncodingException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    } else // Khi ko co mon ma co linh vuc
//                    {
//                        if (khoaHoc.getLinhVuc() != null) {
//                            for (String linhVuc : khoaHoc.getLinhVuc()) {
//                                try {
//                                    if (URLEncoder.encode(removeDiacriticalMarks(linhVuc).toLowerCase(), "utf-8").contains(URLEncoder.encode(khoaHoc.getLinhVuc().get(0).toLowerCase(), "utf-8"))) {
//                                        if (URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getDiaDiem().getDayDu()).toLowerCase(), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(kh.getDiaDiem().getQuan()).toLowerCase(), "utf-8"))) {
//                                            khoaHocGanChinhXac.add(new CustomModelKhoaHoc(kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
//                                            if (khoaHoc.getGioiTinh() != "Nam, Nữ") {
//                                                if (kh.getGioiTinh().equals(khoaHoc.getGioiTinh())) {
//                                                    if (hocPhiTimKiem >= hocPhiDuoi && hocPhiTimKiem <= hocPhiTren) {
//                                                        for (String buoi : khoaHoc.getLichHoc().getThoiGian()) {
//                                                            for (String b : kh.getLichHoc().getThoiGian()) {
//                                                                if (buoi.equals(b)) {
//                                                                    for (String ngay : khoaHoc.getLichHoc().getNgayHoc()) {
//                                                                        for (String ng : kh.getLichHoc().getNgayHoc()) {
//                                                                            if (ngay.equals(ng)) {
//                                                                                countTemp++;
//                                                                                if (countTemp == count) {
//                                                                                    khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
//                                                                                }
//                                                                            }
//                                                                        }
//                                                                    }
//                                                                }
//                                                            }
//                                                        }
//                                                    }
//                                                }
//                                            } else {
//                                                if (hocPhiTimKiem >= hocPhiDuoi && hocPhiTimKiem <= hocPhiTren) {
//                                                    for (String buoi : khoaHoc.getLichHoc().getThoiGian()) {
//                                                        for (String b : kh.getLichHoc().getThoiGian()) {
//                                                            if (buoi.equals(b)) {
//                                                                for (String ngay : khoaHoc.getLichHoc().getNgayHoc()) {
//                                                                    for (String ng : kh.getLichHoc().getNgayHoc()) {
//                                                                        if (ngay.equals(ng)) {
//                                                                            countTemp++;
//                                                                            if (countTemp == count) {
//                                                                                khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
//                                                                            }
//                                                                        }
//                                                                    }
//                                                                }
//                                                            }
//                                                        }
//                                                    }
//                                                }
//                                            }
//                                        }
//                                    }
//                                } catch (UnsupportedEncodingException e) {
//                                    e.printStackTrace();
//                                }
//                            }
//                            ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHoc(khoaHocChinhXac, khoaHocGanChinhXac);
//                        } else { //Kho vừa không có môn vừa không có lĩnh vực
//                            try {
//                                if (URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getDiaDiem().getDayDu()).toLowerCase(), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(kh.getDiaDiem().getQuan()).toLowerCase(), "utf-8"))) {
//                                    if (khoaHoc.getGioiTinh().equals("Nam, Nữ")) {
//                                        if (hocPhiTimKiem >= hocPhiDuoi && hocPhiTimKiem <= hocPhiTren) {
//                                            for (String buoi : khoaHoc.getLichHoc().getThoiGian()) {
//                                                for (String b : kh.getLichHoc().getThoiGian()) {
//                                                    if (buoi.equals(b)) {
//                                                        for (String ngay : khoaHoc.getLichHoc().getNgayHoc()) {
//                                                            for (String ng : kh.getLichHoc().getNgayHoc()) {
//                                                                if (ngay.equals(ng)) {
//                                                                    countTemp++;
//                                                                    if (countTemp == count) {
//                                                                        khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
//                                                                    }
//                                                                }
//                                                            }
//                                                        }
//                                                    }
//                                                }
//                                            }
//                                        }
//
//                                    } else {
//                                        if (kh.getGioiTinh().equals(khoaHoc.getGioiTinh())) {
//                                            if (hocPhiTimKiem >= hocPhiDuoi && hocPhiTimKiem <= hocPhiTren) {
//                                                for (String buoi : khoaHoc.getLichHoc().getThoiGian()) {
//                                                    for (String b : kh.getLichHoc().getThoiGian()) {
//                                                        if (buoi.equals(b)) {
//                                                            for (String ngay : khoaHoc.getLichHoc().getNgayHoc()) {
//                                                                for (String ng : kh.getLichHoc().getNgayHoc()) {
//                                                                    if (ngay.equals(ng)) {
//                                                                        countTemp++;
//                                                                        if (countTemp == count) {
//                                                                            khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
//                                                                        }
//                                                                    }
//                                                                }
//                                                            }
//                                                        }
//                                                    }
//                                                }
//                                            }
//                                        }
//                                    }
//                                }
//                            } catch (UnsupportedEncodingException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                        ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHoc(khoaHocChinhXac, khoaHocGanChinhXac);
//                    }
//                }
//
//                @Override
//                public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//
//                }
//
//                @Override
//                public void onChildRemoved(DataSnapshot dataSnapshot) {
//
//                }
//
//                @Override
//                public void onChildMoved(DataSnapshot dataSnapshot, String s) {
//
//                }
//
//                @Override
//                public void onCancelled(DatabaseError databaseError) {
//
//                }
//            });
//        }
    }
//    }

    //Gỡ dấu

    public static String removeDiacriticalMarks(String string) {
        return Normalizer.normalize(string, Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
    }

    //
    public int cout(int x) {
        int count = 0;
        if ((x / 2) < 1) {
            count = 1;
        } else {
            count = x / 2;
        }
        return count;
    }
    //GetKhoaHocTheoLinhVuc
//    private void getListKhoaHocLinhVuc(KhoaHoc khoaHoc,String loai)
//    {
//        mData.child("KhoaHoc").child(loai).child("ChuaHoanTat").orderByChild("LinhVuc").
//    }
}
