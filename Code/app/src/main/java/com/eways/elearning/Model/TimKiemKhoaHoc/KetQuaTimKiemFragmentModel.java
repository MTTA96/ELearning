package com.eways.elearning.Model.TimKiemKhoaHoc;

import com.eways.elearning.DataModel.KhoaHoc.CustomModelKhoaHoc;
import com.eways.elearning.DataModel.KhoaHoc.KhoaHoc;
import com.eways.elearning.Presenter.TimKiemKhoaHoc.KetQuaTimKiemKhoaHocFragmentPresenterImp;
import com.eways.elearning.Util.SupportKeysList;
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

//        if (loai) {
//            if (khoaHoc.getLinhVuc().size() == 0 && khoaHoc.getMon().size() == 0) {
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
//                if (khoaHoc.getLinhVuc().size() == 0 && khoaHoc.getMon().size() != 0) {
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
//                if (khoaHoc.getLinhVuc().size() == 0 && khoaHoc.getMon().size() == 0) {
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
//                if (khoaHoc.getLinhVuc().size() == 0 && khoaHoc.getMon().size() != 0) {
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

        if (loai == true) {
            mData.child(SupportKeysList.CHILD_KHOAHOC).child(SupportKeysList.CHILD_KHOAHOC_TIMGIASU).child(SupportKeysList.CHILD_KHOAHOC_CHUAHOANTAT).addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    KhoaHoc kh = dataSnapshot.getValue(KhoaHoc.class);
                    if (khoaHoc.getMon() != null) {
                        for (String mon : kh.getMon()) {
                            try {
                                if (URLEncoder.encode(removeDiacriticalMarks(mon).toLowerCase(), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getMon().get(0)).toLowerCase(), "utf-8"))) {
                                    if (khoaHoc.getLinhVuc() != null) {
                                        for (String linhVuc : khoaHoc.getLinhVuc()) {
                                            if (URLEncoder.encode(removeDiacriticalMarks(linhVuc).toLowerCase(), "utf-8").contains(URLEncoder.encode(khoaHoc.getLinhVuc().get(0).toLowerCase(), "utf-8"))) {
                                                khoaHocGanChinhXac.add(new CustomModelKhoaHoc(kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                if (hocPhiTimKiem >= hocPhiDuoi && hocPhiTimKiem <= hocPhiTren) {
                                                    for (String buoi : khoaHoc.getLichHoc().getThoiGian()) {
                                                        for (String b : kh.getLichHoc().getThoiGian()) {
                                                            if (buoi.equals(b)) {
                                                                for (String ngay : khoaHoc.getLichHoc().getNgayHoc()) {
                                                                    for (String ng : kh.getLichHoc().getNgayHoc()) {
                                                                        if (ngay.equals(ng)) {
                                                                            countTemp++;
                                                                            if (countTemp == count) {
                                                                                khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
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
                                        ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHoc(khoaHocChinhXac, khoaHocGanChinhXac);
                                    } else //Khi chi co mon ma ko co linh vuc
                                    {
                                        khoaHocGanChinhXac.add(new CustomModelKhoaHoc(kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                        if (hocPhiTimKiem >= hocPhiDuoi && hocPhiTimKiem <= hocPhiTren) {
                                            for (String buoi : khoaHoc.getLichHoc().getThoiGian()) {
                                                for (String b : kh.getLichHoc().getThoiGian()) {
                                                    if (buoi.equals(b)) {
                                                        for (String ngay : khoaHoc.getLichHoc().getNgayHoc()) {
                                                            for (String ng : kh.getLichHoc().getNgayHoc()) {
                                                                if (ngay.equals(ng)) {
                                                                    countTemp++;
                                                                    if (countTemp == count) {
                                                                        khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                        ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHoc(khoaHocChinhXac, khoaHocGanChinhXac);
                                    }
                                }
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                        }
                    } else // Khi ko co mon ma co linh vuc
                    {
                        if (khoaHoc.getLinhVuc() != null) {
                            for (String linhVuc : khoaHoc.getLinhVuc()) {
                                try {
                                    if (URLEncoder.encode(removeDiacriticalMarks(linhVuc).toLowerCase(), "utf-8").contains(URLEncoder.encode(khoaHoc.getLinhVuc().get(0).toLowerCase(), "utf-8"))) {
                                        khoaHocGanChinhXac.add(new CustomModelKhoaHoc(kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                        if (hocPhiTimKiem >= hocPhiDuoi && hocPhiTimKiem <= hocPhiTren) {
                                            for (String buoi : khoaHoc.getLichHoc().getThoiGian()) {
                                                for (String b : kh.getLichHoc().getThoiGian()) {
                                                    if (buoi.equals(b)) {
                                                        for (String ngay : khoaHoc.getLichHoc().getNgayHoc()) {
                                                            for (String ng : kh.getLichHoc().getNgayHoc()) {
                                                                if (ngay.equals(ng)) {
                                                                    countTemp++;
                                                                    if (countTemp == count) {
                                                                        khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
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
                            ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHoc(khoaHocChinhXac, khoaHocGanChinhXac);
                        } else {
                            khoaHocGanChinhXac.add(new CustomModelKhoaHoc(kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                            if (hocPhiTimKiem >= hocPhiDuoi && hocPhiTimKiem <= hocPhiTren) {
                                for (String buoi : khoaHoc.getLichHoc().getThoiGian()) {
                                    for (String b : kh.getLichHoc().getThoiGian()) {
                                        if (buoi.equals(b)) {
                                            for (String ngay : khoaHoc.getLichHoc().getNgayHoc()) {
                                                for (String ng : kh.getLichHoc().getNgayHoc()) {
                                                    if (ngay.equals(ng)) {
                                                        countTemp++;
                                                        if (countTemp == count) {
                                                            khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHoc(khoaHocChinhXac, khoaHocGanChinhXac);
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
        } else {
            mData.child(SupportKeysList.CHILD_KHOAHOC).child(SupportKeysList.CHILD_KHOAHOC_TIMHOCVIEN).child(SupportKeysList.CHILD_KHOAHOC_CHUAHOANTAT).addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    KhoaHoc kh = dataSnapshot.getValue(KhoaHoc.class);
                    if (khoaHoc.getMon() != null) {
                        for (String mon : kh.getMon()) {
                            try {
                                if (URLEncoder.encode(removeDiacriticalMarks(mon).toLowerCase(), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getMon().get(0)).toLowerCase(), "utf-8"))) {
                                    if (khoaHoc.getLinhVuc() != null) {
                                        for (String linhVuc : khoaHoc.getLinhVuc()) {
                                            if (URLEncoder.encode(removeDiacriticalMarks(linhVuc).toLowerCase(), "utf-8").contains(URLEncoder.encode(khoaHoc.getLinhVuc().get(0).toLowerCase(), "utf-8"))) {
                                                khoaHocGanChinhXac.add(new CustomModelKhoaHoc(kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                if (hocPhiTimKiem >= hocPhiDuoi && hocPhiTimKiem <= hocPhiTren) {
                                                    for (String buoi : khoaHoc.getLichHoc().getThoiGian()) {
                                                        for (String b : kh.getLichHoc().getThoiGian()) {
                                                            if (buoi.equals(b)) {
                                                                for (String ngay : khoaHoc.getLichHoc().getNgayHoc()) {
                                                                    for (String ng : kh.getLichHoc().getNgayHoc()) {
                                                                        if (ngay.equals(ng)) {
                                                                            countTemp++;
                                                                            if (countTemp == count) {
                                                                                khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
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
                                        ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHoc(khoaHocChinhXac, khoaHocGanChinhXac);
                                    } else //Khi chi co mon ma ko co linh vuc
                                    {
                                        khoaHocGanChinhXac.add(new CustomModelKhoaHoc(kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                        if (hocPhiTimKiem >= hocPhiDuoi && hocPhiTimKiem <= hocPhiTren) {
                                            for (String buoi : khoaHoc.getLichHoc().getThoiGian()) {
                                                for (String b : kh.getLichHoc().getThoiGian()) {
                                                    if (buoi.equals(b)) {
                                                        for (String ngay : khoaHoc.getLichHoc().getNgayHoc()) {
                                                            for (String ng : kh.getLichHoc().getNgayHoc()) {
                                                                if (ngay.equals(ng)) {
                                                                    countTemp++;
                                                                    if (countTemp == count) {
                                                                        khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                        ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHoc(khoaHocChinhXac, khoaHocGanChinhXac);
                                    }
                                }
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                        }
                    } else // Khi ko co mon ma co linh vuc
                    {
                        if (khoaHoc.getLinhVuc() != null) {
                            for (String linhVuc : khoaHoc.getLinhVuc()) {
                                try {
                                    if (URLEncoder.encode(removeDiacriticalMarks(linhVuc).toLowerCase(), "utf-8").contains(URLEncoder.encode(khoaHoc.getLinhVuc().get(0).toLowerCase(), "utf-8"))) {
                                        khoaHocGanChinhXac.add(new CustomModelKhoaHoc(kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                        if (hocPhiTimKiem >= hocPhiDuoi && hocPhiTimKiem <= hocPhiTren) {
                                            for (String buoi : khoaHoc.getLichHoc().getThoiGian()) {
                                                for (String b : kh.getLichHoc().getThoiGian()) {
                                                    if (buoi.equals(b)) {
                                                        for (String ngay : khoaHoc.getLichHoc().getNgayHoc()) {
                                                            for (String ng : kh.getLichHoc().getNgayHoc()) {
                                                                if (ngay.equals(ng)) {
                                                                    countTemp++;
                                                                    if (countTemp == count) {
                                                                        khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
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
                            ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHoc(khoaHocChinhXac, khoaHocGanChinhXac);
                        } else {
                            khoaHocGanChinhXac.add(new CustomModelKhoaHoc(kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                            if (hocPhiTimKiem >= hocPhiDuoi && hocPhiTimKiem <= hocPhiTren) {
                                for (String buoi : khoaHoc.getLichHoc().getThoiGian()) {
                                    for (String b : kh.getLichHoc().getThoiGian()) {
                                        if (buoi.equals(b)) {
                                            for (String ngay : khoaHoc.getLichHoc().getNgayHoc()) {
                                                for (String ng : kh.getLichHoc().getNgayHoc()) {
                                                    if (ngay.equals(ng)) {
                                                        countTemp++;
                                                        if (countTemp == count) {
                                                            khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHoc(khoaHocChinhXac, khoaHocGanChinhXac);
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
