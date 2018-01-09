package com.eways.elearning.Model.KhoaHoc.TimKiemKhoaHoc;

import android.app.Activity;

import com.eways.elearning.DataModel.KhoaHoc.CustomModelKhoaHoc;
import com.eways.elearning.DataModel.KhoaHoc.KhoaHoc;
import com.eways.elearning.DataModel.TaiKhoan.TaiKhoan;
import com.eways.elearning.DataModel.TaiKhoan.TaiLieu.TaiLieuChuyenMon.BangCapTaiLieuChuyenMon;
import com.eways.elearning.DataModel.TaiKhoan.TaiLieu.TaiLieuChuyenMon.MonTaiLieuChuyenMon;
import com.eways.elearning.DataModel.TaiKhoan.TaiLieu.TaiLieuChuyenMon.TaiLieuChuyenMon;
import com.eways.elearning.Presenter.TimKiemKhoaHoc.KetQuaTimKiemKhoaHocFragmentPresenterImp;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
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
    FirebaseDatabase mData;
    ArrayList<CustomModelKhoaHoc> khoaHocChinhXac = new ArrayList<>();
    ArrayList<CustomModelKhoaHoc> khoaHocGanChinhXac = new ArrayList<>();
    ArrayList<String> listKeyGiaSu = new ArrayList<>();
    ArrayList<TaiKhoan> listGiaSu = new ArrayList<>();
    int countTemp = 0;

    public KetQuaTimKiemFragmentModel(KetQuaTimKiemKhoaHocFragmentPresenterImp ketQuaTimKiemKhoaHocFragmentPresenterImp) {
        this.ketQuaTimKiemKhoaHocFragmentPresenterImp = ketQuaTimKiemKhoaHocFragmentPresenterImp;
    }

    // Tìm kiếm học viên ( Xuất danh sách Khóa học tìm gia sư)
    @Override
    public void getListKhoaHoc(final KhoaHoc khoaHoc, boolean loai, final String mon, Activity activity) {
        int count = 0; //Nếu /2 mà <1 lấy 0 còn lại lấy /2 bt
        double hocPhiTimKiem = 0;
        double hocPhiTren = 0;
        double hocPhiDuoi = 0;
        mData = FirebaseDatabase.getInstance(FirebaseApp.initializeApp(activity));

        if (mon == null || mon == "") {
            if (khoaHoc.getLichHoc().getNgayHoc() != null || khoaHoc.getLichHoc().getNgayHoc().size() != 0) {
                count = cout(khoaHoc.getLichHoc().getNgayHoc().size()); //Nếu /2 mà <1 lấy 0 còn lại lấy /2 bt
                hocPhiTimKiem = Double.parseDouble(khoaHoc.getHocPhi());
                hocPhiTren = (hocPhiTimKiem + ((hocPhiTimKiem / 100) * 20));
                hocPhiDuoi = (hocPhiTimKiem - ((hocPhiTimKiem / 100) * 20));
            } else {
                count = 0; //Nếu /2 mà <1 lấy 0 còn lại lấy /2 bt
                hocPhiTimKiem = Double.parseDouble(khoaHoc.getHocPhi());
                hocPhiTren = (hocPhiTimKiem + ((hocPhiTimKiem / 100) * 20));
                hocPhiDuoi = (hocPhiTimKiem - ((hocPhiTimKiem / 100) * 20));
            }


            //Tìm học viên là ra bài tìm gia sư
            if (khoaHoc.getMon() != null)//1
            {
                if (hocPhiTimKiem != 0) //2 Mon!=null, HocPhi !=0
                {
                    if (khoaHoc.getGioiTinh() != "Nam, Nữ") //3 Mon!=null, HocPhi !=0,GioiTinh!=Nam, Nữ
                    {
                        if (khoaHoc.getBangCap() != null) //4 Mon!=null,HocPhi!=null,GioiTinh!=Nam, Nu,BangCap!=null
                        {
                            if (khoaHoc.getDiaDiem().getQuan() != "Khác") //5 Mon!=null,HocPhi!=null,GioiTinh!=Nam, Nu,BangCap!=null,Quan != "Khác"
                            {
                                final double finalHocPhiDuoi = hocPhiDuoi;
                                final double finalHocPhiTren = hocPhiTren;
                                final int finalCount = count;

                                mData.getReference().child("KhoaHoc").child("KhoaHocTimGiaSu").child("ChuaHoanTat").addChildEventListener(new ChildEventListener() {
                                    @Override
                                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                        int dem = 0; // Xác định độ chính xác của lịch học (if dem == finaleCount)
                                        KhoaHoc kh = dataSnapshot.getValue(KhoaHoc.class);
                                        for (String Mon : kh.getMon()) {
                                            try {
                                                //Môn
                                                if (URLEncoder.encode(removeDiacriticalMarks(Mon), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getMon().get(0)), "utf-8"))) {
                                                    //Lịch học
                                                    if (finalCount != 0) //Ngày học tìm kiếm != null
                                                    {
                                                        for (String ngayHoc : khoaHoc.getLichHoc().getNgayHoc()) {
                                                            for (String ngayhoc : kh.getLichHoc().getNgayHoc()) {
                                                                if (ngayhoc.equals(ngayHoc)) {
                                                                    dem++;
                                                                    if (dem == finalCount) // Độ chính xác lịch học đạt mức chấp nhận được
                                                                    {
                                                                        khoaHocGanChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));
                                                                    }
                                                                }
                                                            }
                                                        }
                                                        if (dem >= finalCount) // Độ chính xác lịch học chấp nhận được hoặc rất chính xác
                                                        {
                                                            if (khoaHoc.getLichHoc().getThoiGian().size() != 0) // Buổi != null
                                                            {
                                                                for (String buoiHoc : khoaHoc.getLichHoc().getThoiGian()) {
                                                                    for (String buoihoc : kh.getLichHoc().getThoiGian()) {
                                                                        if (buoiHoc.equals(buoihoc)) // Buổi
                                                                        {
                                                                            //Học phí
                                                                            if (Double.parseDouble(kh.getHocPhi()) >= finalHocPhiDuoi && Double.parseDouble(kh.getHocPhi()) <= finalHocPhiTren) {
                                                                                //Giới tính
                                                                                if (kh.getGioiTinh().equals(khoaHoc.getGioiTinh())) {
                                                                                    for (String bangcap : kh.getBangCap()) {
                                                                                        //Bằng cấp
                                                                                        if (URLEncoder.encode(removeDiacriticalMarks(bangcap), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getBangCap().get(0)), "utf-8"))) {
                                                                                            //Địa điểm
                                                                                            if (kh.getDiaDiem().getQuan().equals(khoaHoc.getDiaDiem().getQuan())) {
                                                                                                khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));

                                                                                            }
                                                                                        }
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            } else //Buổi == null
                                                            {
                                                                //Học phí
                                                                if (Double.parseDouble(kh.getHocPhi()) >= finalHocPhiDuoi && Double.parseDouble(kh.getHocPhi()) <= finalHocPhiTren) {
                                                                    //Giới tính
                                                                    if (kh.getGioiTinh().equals(khoaHoc.getGioiTinh())) {
                                                                        for (String bangcap : kh.getBangCap()) {
                                                                            //Bằng cấp
                                                                            if (URLEncoder.encode(removeDiacriticalMarks(bangcap), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getBangCap().get(0)), "utf-8"))) {
                                                                                //Địa điểm
                                                                                if (kh.getDiaDiem().getQuan().equals(khoaHoc.getDiaDiem().getQuan())) {
                                                                                    khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));

                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }

                                                        }
                                                    } else // Ngày học tìm kiếm == null
                                                    {
                                                        if (khoaHoc.getLichHoc().getThoiGian().size() != 0) // Buổi != null
                                                        {
                                                            for (String buoiHoc : khoaHoc.getLichHoc().getThoiGian()) {
                                                                for (String buoihoc : kh.getLichHoc().getThoiGian()) {
                                                                    if (buoiHoc.equals(buoihoc)) // Buổi
                                                                    {
                                                                        //Học phí
                                                                        if (Double.parseDouble(kh.getHocPhi()) >= finalHocPhiDuoi && Double.parseDouble(kh.getHocPhi()) <= finalHocPhiTren) {
                                                                            //Giới tính
                                                                            if (kh.getGioiTinh().equals(khoaHoc.getGioiTinh())) {
                                                                                for (String bangcap : kh.getBangCap()) {
                                                                                    //Bằng cấp
                                                                                    if (URLEncoder.encode(removeDiacriticalMarks(bangcap), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getBangCap().get(0)), "utf-8"))) {
                                                                                        //Địa điểm
                                                                                        if (kh.getDiaDiem().getQuan().equals(khoaHoc.getDiaDiem().getQuan())) {
                                                                                            khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));

                                                                                        }
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        } else //Buổi == null
                                                        {
                                                            //Học phí
                                                            if (Double.parseDouble(kh.getHocPhi()) >= finalHocPhiDuoi && Double.parseDouble(kh.getHocPhi()) <= finalHocPhiTren) {
                                                                //Giới tính
                                                                if (kh.getGioiTinh().equals(khoaHoc.getGioiTinh())) {
                                                                    for (String bangcap : kh.getBangCap()) {
                                                                        //Bằng cấp
                                                                        if (URLEncoder.encode(removeDiacriticalMarks(bangcap), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getBangCap().get(0)), "utf-8"))) {
                                                                            //Địa điểm
                                                                            if (kh.getDiaDiem().getQuan().equals(khoaHoc.getDiaDiem().getQuan())) {
                                                                                khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));

                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }


                                                }
                                                ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHoc(khoaHocChinhXac, khoaHocGanChinhXac);
                                            } catch (UnsupportedEncodingException e) {
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
                            } else //5 Mon!=null,HocPhi!=null,GioiTinh!=Nam, Nu,BangCap!=null, Quan = "Khác"
                            {
                                final double finalHocPhiDuoi = hocPhiDuoi;
                                final double finalHocPhiTren = hocPhiTren;
                                final int finalCount = count;

                                mData.getReference().child("KhoaHoc").child("KhoaHocTimGiaSu").child("ChuaHoanTat").addChildEventListener(new ChildEventListener() {
                                    @Override
                                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                        int dem = 0; // Xác định độ chính xác của lịch học (if dem == finaleCount)
                                        KhoaHoc kh = dataSnapshot.getValue(KhoaHoc.class);
                                        for (String Mon : kh.getMon()) {
                                            try {
                                                //Môn
                                                if (URLEncoder.encode(removeDiacriticalMarks(Mon), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getMon().get(0)), "utf-8"))) {
                                                    if (finalCount != 0) //Ngày học tìm kiếm != null
                                                    {
                                                        for (String ngayHoc : khoaHoc.getLichHoc().getNgayHoc()) {
                                                            for (String ngayhoc : kh.getLichHoc().getNgayHoc()) {
                                                                if (ngayhoc.equals(ngayHoc)) {
                                                                    dem++;
                                                                    if (dem == finalCount) // Độ chính xác lịch học đạt mức chấp nhận được
                                                                    {
                                                                        khoaHocGanChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));
                                                                    }
                                                                }
                                                            }
                                                        }
                                                        if (dem >= finalCount) // Độ chính xác lịch học chấp nhận được hoặc rất chính xác
                                                        {
                                                            if (khoaHoc.getLichHoc().getThoiGian().size() != 0) // Buổi != null
                                                            {
                                                                for (String buoiHoc : khoaHoc.getLichHoc().getThoiGian()) {
                                                                    for (String buoihoc : kh.getLichHoc().getThoiGian()) {
                                                                        if (buoiHoc.equals(buoihoc)) // Buổi
                                                                        {
                                                                            //Học phí
                                                                            if (Double.parseDouble(kh.getHocPhi()) >= finalHocPhiDuoi && Double.parseDouble(kh.getHocPhi()) <= finalHocPhiTren) {
                                                                                //Giới tính
                                                                                if (kh.getGioiTinh().equals(khoaHoc.getGioiTinh())) {
                                                                                    for (String bangcap : kh.getBangCap()) {
                                                                                        //Bằng cấp
                                                                                        if (URLEncoder.encode(removeDiacriticalMarks(bangcap), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getBangCap().get(0)), "utf-8"))) {
                                                                                            khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));
                                                                                        }
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            } else //Buổi == null
                                                            {
                                                                //Học phí
                                                                if (Double.parseDouble(kh.getHocPhi()) >= finalHocPhiDuoi && Double.parseDouble(kh.getHocPhi()) <= finalHocPhiTren) {
                                                                    //Giới tính
                                                                    if (kh.getGioiTinh().equals(khoaHoc.getGioiTinh())) {
                                                                        for (String bangcap : kh.getBangCap()) {
                                                                            //Bằng cấp
                                                                            if (URLEncoder.encode(removeDiacriticalMarks(bangcap), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getBangCap().get(0)), "utf-8"))) {
                                                                                khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    } else // Ngày học == null
                                                    {
                                                        if (khoaHoc.getLichHoc().getThoiGian().size() != 0) // Buổi != null
                                                        {
                                                            for (String buoiHoc : khoaHoc.getLichHoc().getThoiGian()) {
                                                                for (String buoihoc : kh.getLichHoc().getThoiGian()) {
                                                                    if (buoiHoc.equals(buoihoc)) // Buổi
                                                                    {
                                                                        //Học phí
                                                                        if (Double.parseDouble(kh.getHocPhi()) >= finalHocPhiDuoi && Double.parseDouble(kh.getHocPhi()) <= finalHocPhiTren) {
                                                                            //Giới tính
                                                                            if (kh.getGioiTinh().equals(khoaHoc.getGioiTinh())) {
                                                                                for (String bangcap : kh.getBangCap()) {
                                                                                    //Bằng cấp
                                                                                    if (URLEncoder.encode(removeDiacriticalMarks(bangcap), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getBangCap().get(0)), "utf-8"))) {
                                                                                        khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        } else //Buổi == null
                                                        {
                                                            //Học phí
                                                            if (Double.parseDouble(kh.getHocPhi()) >= finalHocPhiDuoi && Double.parseDouble(kh.getHocPhi()) <= finalHocPhiTren) {
                                                                //Giới tính
                                                                if (kh.getGioiTinh().equals(khoaHoc.getGioiTinh())) {
                                                                    for (String bangcap : kh.getBangCap()) {
                                                                        //Bằng cấp
                                                                        if (URLEncoder.encode(removeDiacriticalMarks(bangcap), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getBangCap().get(0)), "utf-8"))) {
                                                                            khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }

                                                }
                                                ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHoc(khoaHocChinhXac, khoaHocGanChinhXac);
                                            } catch (UnsupportedEncodingException e) {
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
                        } else//4 Mon!=null,HocPhi!=null,GioiTinh!=Nam, Nu,BangCap==null*
                        {
                            if (khoaHoc.getDiaDiem().getQuan() != "Khác") //5 Mon!=null,HocPhi!=null,GioiTinh!=Nam, Nu,BangCap==null,Quan != "Khác"
                            {
                                final double finalHocPhiDuoi = hocPhiDuoi;
                                final double finalHocPhiTren = hocPhiTren;
                                final int finalCount = count;
                                mData.getReference().child("KhoaHoc").child("KhoaHocTimGiaSu").child("ChuaHoanTat").addChildEventListener(new ChildEventListener() {
                                    @Override
                                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                        int dem = 0; // Xác định độ chính xác của lịch học (if dem == finaleCount)
                                        KhoaHoc kh = dataSnapshot.getValue(KhoaHoc.class);
                                        for (String Mon : khoaHoc.getMon()) {
                                            //Môn
                                            try {
                                                if (URLEncoder.encode(removeDiacriticalMarks(Mon), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getMon().get(0)), "utf-8"))) {
                                                    if (finalCount != 0) //Ngày học tìm kiếm != null
                                                    {
                                                        for (String ngayHoc : khoaHoc.getLichHoc().getNgayHoc()) {
                                                            for (String ngayhoc : kh.getLichHoc().getNgayHoc()) {
                                                                if (ngayhoc.equals(ngayHoc)) {
                                                                    dem++;
                                                                    if (dem == finalCount) // Độ chính xác lịch học đạt mức chấp nhận được
                                                                    {
                                                                        khoaHocGanChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));
                                                                    }
                                                                }
                                                            }
                                                        }
                                                        if (dem >= finalCount) // Độ chính xác lịch học chấp nhận được hoặc rất chính xác
                                                        {
                                                            if (khoaHoc.getLichHoc().getThoiGian().size() != 0) // Buổi != null
                                                            {
                                                                for (String buoiHoc : khoaHoc.getLichHoc().getThoiGian()) {
                                                                    for (String buoihoc : kh.getLichHoc().getThoiGian()) {
                                                                        if (buoiHoc.equals(buoihoc)) // Buổi
                                                                        {
                                                                            //Học phí
                                                                            if (Double.parseDouble(kh.getHocPhi()) >= finalHocPhiDuoi && Double.parseDouble(kh.getHocPhi()) <= finalHocPhiTren) {
                                                                                //Giới tính
                                                                                if (kh.getGioiTinh().equals(khoaHoc.getGioiTinh())) {
                                                                                    //Địa điểm
                                                                                    if (kh.getDiaDiem().getQuan().equals(khoaHoc.getDiaDiem().getQuan())) {
                                                                                        khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            } else //Buổi == null
                                                            {
                                                                //Học phí
                                                                if (Double.parseDouble(kh.getHocPhi()) >= finalHocPhiDuoi && Double.parseDouble(kh.getHocPhi()) <= finalHocPhiTren) {
                                                                    //Giới tính
                                                                    if (kh.getGioiTinh().equals(khoaHoc.getGioiTinh())) {
                                                                        //Địa điểm
                                                                        if (kh.getDiaDiem().getQuan().equals(khoaHoc.getDiaDiem().getQuan())) {
                                                                            khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    } else // Ngày học tìm kiếm == null
                                                    {
                                                        if (khoaHoc.getLichHoc().getThoiGian().size() != 0) // Buổi != null
                                                        {
                                                            for (String buoiHoc : khoaHoc.getLichHoc().getThoiGian()) {
                                                                for (String buoihoc : kh.getLichHoc().getThoiGian()) {
                                                                    if (buoiHoc.equals(buoihoc)) // Buổi
                                                                    {
                                                                        //Học phí
                                                                        if (Double.parseDouble(kh.getHocPhi()) >= finalHocPhiDuoi && Double.parseDouble(kh.getHocPhi()) <= finalHocPhiTren) {
                                                                            //Giới tính
                                                                            if (kh.getGioiTinh().equals(khoaHoc.getGioiTinh())) {
                                                                                //Địa điểm
                                                                                if (kh.getDiaDiem().getQuan().equals(khoaHoc.getDiaDiem().getQuan())) {
                                                                                    khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        } else //Buổi == null
                                                        {
                                                            //Học phí
                                                            if (Double.parseDouble(kh.getHocPhi()) >= finalHocPhiDuoi && Double.parseDouble(kh.getHocPhi()) <= finalHocPhiTren) {
                                                                //Giới tính
                                                                if (kh.getGioiTinh().equals(khoaHoc.getGioiTinh())) {
                                                                    //Địa điểm
                                                                    if (kh.getDiaDiem().getQuan().equals(khoaHoc.getDiaDiem().getQuan())) {
                                                                        khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }

                                                }
                                                ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHoc(khoaHocChinhXac, khoaHocGanChinhXac);
                                            } catch (UnsupportedEncodingException e) {
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
                            } else //5 Mon!=null,HocPhi!=null,GioiTinh!=Nam, Nu,BangCap==null, Quan = "Khác"
                            {
                                final double finalHocPhiDuoi = hocPhiDuoi;
                                final double finalHocPhiTren = hocPhiTren;
                                final int finalCount = count;
                                mData.getReference().child("KhoaHoc").child("KhoaHocTimGiaSu").child("ChuaHoanTat").addChildEventListener(new ChildEventListener() {
                                    @Override
                                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                        KhoaHoc kh = dataSnapshot.getValue(KhoaHoc.class);
                                        int dem = 0; // Xác định độ chính xác của lịch học (if dem == finaleCount)
                                        for (String Mon : kh.getMon()) {
                                            //Môn
                                            try {
                                                if (URLEncoder.encode(removeDiacriticalMarks(Mon), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getMon().get(0)), "utf-8"))) {
                                                    if (finalCount != 0) //Ngày học tìm kiếm != null
                                                    {
                                                        for (String ngayHoc : khoaHoc.getLichHoc().getNgayHoc()) {
                                                            for (String ngayhoc : kh.getLichHoc().getNgayHoc()) {
                                                                if (ngayhoc.equals(ngayHoc)) {
                                                                    dem++;
                                                                    if (dem == finalCount) // Độ chính xác lịch học đạt mức chấp nhận được
                                                                    {
                                                                        khoaHocGanChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));
                                                                    }
                                                                }
                                                            }
                                                        }
                                                        if (dem >= finalCount) // Độ chính xác lịch học chấp nhận được hoặc rất chính xác
                                                        {
                                                            if (khoaHoc.getLichHoc().getThoiGian().size() != 0) // Buổi != null
                                                            {
                                                                for (String buoiHoc : khoaHoc.getLichHoc().getThoiGian()) {
                                                                    for (String buoihoc : kh.getLichHoc().getThoiGian()) {
                                                                        if (buoiHoc.equals(buoihoc)) // Buổi
                                                                        {
                                                                            //Học phí
                                                                            if (Double.parseDouble(kh.getHocPhi()) >= finalHocPhiDuoi && Double.parseDouble(kh.getHocPhi()) <= finalHocPhiTren) {
                                                                                //Giới tính
                                                                                if (kh.getGioiTinh().equals(khoaHoc.getGioiTinh())) {
                                                                                    khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            } else //Buổi == null
                                                            {
                                                                //Học phí
                                                                if (Double.parseDouble(kh.getHocPhi()) >= finalHocPhiDuoi && Double.parseDouble(kh.getHocPhi()) <= finalHocPhiTren) {
                                                                    //Giới tính
                                                                    if (kh.getGioiTinh().equals(khoaHoc.getGioiTinh())) {
                                                                        khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    } else //Ngày học tìm kiếm == null
                                                    {
                                                        if (khoaHoc.getLichHoc().getThoiGian().size() != 0) // Buổi != null
                                                        {
                                                            for (String buoiHoc : khoaHoc.getLichHoc().getThoiGian()) {
                                                                for (String buoihoc : kh.getLichHoc().getThoiGian()) {
                                                                    if (buoiHoc.equals(buoihoc)) // Buổi
                                                                    {
                                                                        //Học phí
                                                                        if (Double.parseDouble(kh.getHocPhi()) >= finalHocPhiDuoi && Double.parseDouble(kh.getHocPhi()) <= finalHocPhiTren) {
                                                                            //Giới tính
                                                                            if (kh.getGioiTinh().equals(khoaHoc.getGioiTinh())) {
                                                                                khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        } else //Buổi == null
                                                        {
                                                            //Học phí
                                                            if (Double.parseDouble(kh.getHocPhi()) >= finalHocPhiDuoi && Double.parseDouble(kh.getHocPhi()) <= finalHocPhiTren) {
                                                                //Giới tính
                                                                if (kh.getGioiTinh().equals(khoaHoc.getGioiTinh())) {
                                                                    khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                                ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHoc(khoaHocChinhXac, khoaHocGanChinhXac);
                                            } catch (UnsupportedEncodingException e) {
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
                    } else //3 Mon!=null,HocPhi!=0,GioiTinh==Nam, Nữ
                    {
                        if (khoaHoc.getBangCap() != null) //4 Mon!=null,HocPhi!=0,GioiTinh==Nam, Nữ,BangCap!=null
                        {
                            if (khoaHoc.getDiaDiem().getQuan() != "Khác") //5 Mon!=null,HocPhi!=0,GioiTinh==Nam, Nữ,BangCap!=null,Quan != "Khác"
                            {
                                final double finalHocPhiDuoi = hocPhiDuoi;
                                final double finalHocPhiTren = hocPhiTren;
                                final int finalCount = count;
                                mData.getReference().child("KhoaHoc").child("KhoaHocTimGiaSu").child("ChuaHoanTat").addChildEventListener(new ChildEventListener() {
                                    @Override
                                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                        int dem = 0; // Xác định độ chính xác của lịch học (if dem == finaleCount)
                                        KhoaHoc kh = dataSnapshot.getValue(KhoaHoc.class);
                                        for (String Mon : kh.getMon()) {
                                            //Môn
                                            try {
                                                if (URLEncoder.encode(removeDiacriticalMarks(Mon), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getMon().get(0)), "utf-8"))) {
                                                    //Lịch học
                                                    if (finalCount != 0) //Ngày học tìm kiếm != null
                                                    {
                                                        for (String ngayHoc : khoaHoc.getLichHoc().getNgayHoc()) {
                                                            for (String ngayhoc : kh.getLichHoc().getNgayHoc()) {
                                                                if (ngayhoc.equals(ngayHoc)) {
                                                                    dem++;
                                                                    if (dem == finalCount) // Độ chính xác lịch học đạt mức chấp nhận được
                                                                    {
                                                                        khoaHocGanChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));
                                                                    }
                                                                }
                                                            }
                                                        }
                                                        if (dem >= finalCount) // Độ chính xác lịch học chấp nhận được hoặc rất chính xác
                                                        {
                                                            if (khoaHoc.getLichHoc().getThoiGian().size() != 0) // Buổi != null
                                                            {
                                                                for (String buoiHoc : khoaHoc.getLichHoc().getThoiGian()) {
                                                                    for (String buoihoc : kh.getLichHoc().getThoiGian()) {
                                                                        if (buoiHoc.equals(buoihoc)) // Buổi
                                                                        {
                                                                            //Học phí
                                                                            if (Double.parseDouble(kh.getHocPhi()) >= finalHocPhiDuoi && Double.parseDouble(kh.getHocPhi()) <= finalHocPhiTren) {
                                                                                for (String bangcap : kh.getBangCap()) {
                                                                                    //Bằng cấp
                                                                                    if (URLEncoder.encode(removeDiacriticalMarks(bangcap), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getBangCap().get(0)), "utf-8"))) {
                                                                                        //Địa điểm
                                                                                        if (kh.getDiaDiem().getQuan().equals(khoaHoc.getDiaDiem().getQuan())) {
                                                                                            khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));
                                                                                        }
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            } else //Buổi == null
                                                            {
                                                                //Học phí
                                                                if (Double.parseDouble(kh.getHocPhi()) >= finalHocPhiDuoi && Double.parseDouble(kh.getHocPhi()) <= finalHocPhiTren) {
                                                                    for (String bangcap : kh.getBangCap()) {
                                                                        //Bằng cấp
                                                                        if (URLEncoder.encode(removeDiacriticalMarks(bangcap), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getBangCap().get(0)), "utf-8"))) {
                                                                            //Địa điểm
                                                                            if (kh.getDiaDiem().getQuan().equals(khoaHoc.getDiaDiem().getQuan())) {
                                                                                khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    } else //Ngày học tìm kiếm == null
                                                    {
                                                        if (khoaHoc.getLichHoc().getThoiGian().size() != 0) // Buổi != null
                                                        {
                                                            for (String buoiHoc : khoaHoc.getLichHoc().getThoiGian()) {
                                                                for (String buoihoc : kh.getLichHoc().getThoiGian()) {
                                                                    if (buoiHoc.equals(buoihoc)) // Buổi
                                                                    {
                                                                        //Học phí
                                                                        if (Double.parseDouble(kh.getHocPhi()) >= finalHocPhiDuoi && Double.parseDouble(kh.getHocPhi()) <= finalHocPhiTren) {
                                                                            for (String bangcap : kh.getBangCap()) {
                                                                                //Bằng cấp
                                                                                if (URLEncoder.encode(removeDiacriticalMarks(bangcap), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getBangCap().get(0)), "utf-8"))) {
                                                                                    //Địa điểm
                                                                                    if (kh.getDiaDiem().getQuan().equals(khoaHoc.getDiaDiem().getQuan())) {
                                                                                        khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        } else //Buổi == null
                                                        {
                                                            //Học phí
                                                            if (Double.parseDouble(kh.getHocPhi()) >= finalHocPhiDuoi && Double.parseDouble(kh.getHocPhi()) <= finalHocPhiTren) {
                                                                for (String bangcap : kh.getBangCap()) {
                                                                    //Bằng cấp
                                                                    if (URLEncoder.encode(removeDiacriticalMarks(bangcap), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getBangCap().get(0)), "utf-8"))) {
                                                                        //Địa điểm
                                                                        if (kh.getDiaDiem().getQuan().equals(khoaHoc.getDiaDiem().getQuan())) {
                                                                            khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                                ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHoc(khoaHocChinhXac, khoaHocGanChinhXac);
                                            } catch (UnsupportedEncodingException e) {
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
                            } else //5 Mon!=null,HocPhi!=0,GioiTinh==Nam, Nữ,BangCap!=null, Quan = "Khác"
                            {
                                final double finalHocPhiDuoi = hocPhiDuoi;
                                final double finalHocPhiTren = hocPhiTren;
                                final int finalCount = count;
                                mData.getReference().child("KhoaHoc").child("KhoaHocTimGiaSu").child("ChuaHoanTat").addChildEventListener(new ChildEventListener() {
                                    @Override
                                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                        int dem = 0; // Xác định độ chính xác của lịch học (if dem == finaleCount)
                                        KhoaHoc kh = dataSnapshot.getValue(KhoaHoc.class);
                                        for (String Mon : kh.getMon()) {
                                            try {
                                                if (URLEncoder.encode(removeDiacriticalMarks(Mon), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getMon().get(0)), "utf-8"))) {
                                                    //Lịch học
                                                    if (finalCount != 0) //Ngày học tìm kiếm != null
                                                    {
                                                        for (String ngayHoc : khoaHoc.getLichHoc().getNgayHoc()) {
                                                            for (String ngayhoc : kh.getLichHoc().getNgayHoc()) {
                                                                if (ngayhoc.equals(ngayHoc)) {
                                                                    dem++;
                                                                    if (dem == finalCount) // Độ chính xác lịch học đạt mức chấp nhận được
                                                                    {
                                                                        khoaHocGanChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));
                                                                    }
                                                                }
                                                            }
                                                        }
                                                        if (dem >= finalCount) // Độ chính xác lịch học chấp nhận được hoặc rất chính xác
                                                        {
                                                            if (khoaHoc.getLichHoc().getThoiGian().size() != 0) // Buổi != null
                                                            {
                                                                for (String buoiHoc : khoaHoc.getLichHoc().getThoiGian()) {
                                                                    for (String buoihoc : kh.getLichHoc().getThoiGian()) {
                                                                        if (buoiHoc.equals(buoihoc)) // Buổi
                                                                        {
                                                                            //Học phí
                                                                            if (Double.parseDouble(kh.getHocPhi()) >= finalHocPhiDuoi && Double.parseDouble(kh.getHocPhi()) <= finalHocPhiTren) {
                                                                                for (String bangcap : kh.getBangCap()) {
                                                                                    //Bằng cấp
                                                                                    if (URLEncoder.encode(removeDiacriticalMarks(bangcap), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getBangCap().get(0)), "utf-8"))) {
                                                                                        khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            } else // Buổi == null
                                                            {
//Học phí
                                                                if (Double.parseDouble(kh.getHocPhi()) >= finalHocPhiDuoi && Double.parseDouble(kh.getHocPhi()) <= finalHocPhiTren) {
                                                                    for (String bangcap : kh.getBangCap()) {
                                                                        //Bằng cấp
                                                                        if (URLEncoder.encode(removeDiacriticalMarks(bangcap), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getBangCap().get(0)), "utf-8"))) {
                                                                            khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    } else // Ngày học tìm kiếm == null
                                                    {
                                                        if (khoaHoc.getLichHoc().getThoiGian().size() != 0) // Buổi != null
                                                        {
                                                            for (String buoiHoc : khoaHoc.getLichHoc().getThoiGian()) {
                                                                for (String buoihoc : kh.getLichHoc().getThoiGian()) {
                                                                    if (buoiHoc.equals(buoihoc)) // Buổi
                                                                    {
                                                                        //Học phí
                                                                        if (Double.parseDouble(kh.getHocPhi()) >= finalHocPhiDuoi && Double.parseDouble(kh.getHocPhi()) <= finalHocPhiTren) {
                                                                            for (String bangcap : kh.getBangCap()) {
                                                                                //Bằng cấp
                                                                                if (URLEncoder.encode(removeDiacriticalMarks(bangcap), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getBangCap().get(0)), "utf-8"))) {
                                                                                    khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        } else // Buổi == null
                                                        {
//Học phí
                                                            if (Double.parseDouble(kh.getHocPhi()) >= finalHocPhiDuoi && Double.parseDouble(kh.getHocPhi()) <= finalHocPhiTren) {
                                                                for (String bangcap : kh.getBangCap()) {
                                                                    //Bằng cấp
                                                                    if (URLEncoder.encode(removeDiacriticalMarks(bangcap), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getBangCap().get(0)), "utf-8"))) {
                                                                        khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                                ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHoc(khoaHocChinhXac, khoaHocGanChinhXac);
                                            } catch (UnsupportedEncodingException e) {
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
                        } else//4 Mon!=null,HocPhi!=0,GioiTinh==Nam, Nữ,BangCap==null
                        {
                            if (khoaHoc.getDiaDiem().getQuan() != "Khác") //5 Mon!=null,HocPhi!=0,GioiTinh==Nam, Nữ,BangCap==null,Quan != "Khác"
                            {
                                final double finalHocPhiDuoi = hocPhiDuoi;
                                final double finalHocPhiTren = hocPhiTren;
                                final int finalCount = count;
                                mData.getReference().child("KhoaHoc").child("KhoaHocTimGiaSu").child("ChuaHoanTat").addChildEventListener(new ChildEventListener() {
                                    @Override
                                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                        int dem = 0; // Xác định độ chính xác của lịch học (if dem == finaleCount)
                                        KhoaHoc kh = dataSnapshot.getValue(KhoaHoc.class);
                                        for (String Mon : kh.getMon()) {
                                            //Môn
                                            try {
                                                if (URLEncoder.encode(removeDiacriticalMarks(Mon), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getMon().get(0)), "utf-8"))) {
                                                    if (finalCount != 0) //Ngày học tìm kiếm != null
                                                    {
                                                        for (String ngayHoc : khoaHoc.getLichHoc().getNgayHoc()) {
                                                            for (String ngayhoc : kh.getLichHoc().getNgayHoc()) {
                                                                if (ngayhoc.equals(ngayHoc)) {
                                                                    dem++;
                                                                    if (dem == finalCount) // Độ chính xác lịch học đạt mức chấp nhận được
                                                                    {
                                                                        khoaHocGanChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));
                                                                    }
                                                                }
                                                            }
                                                        }
                                                        if (dem >= finalCount) // Độ chính xác lịch học chấp nhận được hoặc rất chính xác
                                                        {
                                                            if (khoaHoc.getLichHoc().getThoiGian().size() != 0) // Buổi != null
                                                            {
                                                                for (String buoiHoc : khoaHoc.getLichHoc().getThoiGian()) {
                                                                    for (String buoihoc : kh.getLichHoc().getThoiGian()) {
                                                                        if (buoiHoc.equals(buoihoc)) // Buổi
                                                                        {
                                                                            //Học phí
                                                                            if (Double.parseDouble(kh.getHocPhi()) >= finalHocPhiDuoi && Double.parseDouble(kh.getHocPhi()) <= finalHocPhiTren) {
                                                                                //Địa điểm
                                                                                if (kh.getDiaDiem().getQuan().equals(khoaHoc.getDiaDiem().getQuan())) {
                                                                                    khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));

                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            } else //Buổi == null
                                                            {
                                                                //Học phí
                                                                if (Double.parseDouble(kh.getHocPhi()) >= finalHocPhiDuoi && Double.parseDouble(kh.getHocPhi()) <= finalHocPhiTren) {
                                                                    //Địa điểm
                                                                    if (kh.getDiaDiem().getQuan().equals(khoaHoc.getDiaDiem().getQuan())) {
                                                                        khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));

                                                                    }
                                                                }
                                                            }
                                                        }
                                                    } else//Ngày học tìm kiếm == null
                                                    {
                                                        if (khoaHoc.getLichHoc().getThoiGian().size() != 0) // Buổi != null
                                                        {
                                                            for (String buoiHoc : khoaHoc.getLichHoc().getThoiGian()) {
                                                                for (String buoihoc : kh.getLichHoc().getThoiGian()) {
                                                                    if (buoiHoc.equals(buoihoc)) // Buổi
                                                                    {
                                                                        //Học phí
                                                                        if (Double.parseDouble(kh.getHocPhi()) >= finalHocPhiDuoi && Double.parseDouble(kh.getHocPhi()) <= finalHocPhiTren) {
                                                                            //Địa điểm
                                                                            if (kh.getDiaDiem().getQuan().equals(khoaHoc.getDiaDiem().getQuan())) {
                                                                                khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));

                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        } else //Buổi == null
                                                        {
                                                            //Học phí
                                                            if (Double.parseDouble(kh.getHocPhi()) >= finalHocPhiDuoi && Double.parseDouble(kh.getHocPhi()) <= finalHocPhiTren) {
                                                                //Địa điểm
                                                                if (kh.getDiaDiem().getQuan().equals(khoaHoc.getDiaDiem().getQuan())) {
                                                                    khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));

                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                                ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHoc(khoaHocChinhXac, khoaHocGanChinhXac);
                                            } catch (UnsupportedEncodingException e) {
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
                            } else //5 Mon!=null,HocPhi!=0,GioiTinh==Nam, Nữ,BangCap==null, Quan = "Khác"
                            {
                                final double finalHocPhiDuoi = hocPhiDuoi;
                                final double finalHocPhiTren = hocPhiTren;
                                final int finalCount = count;
                                mData.getReference().child("KhoaHoc").child("KhoaHocTimGiaSu").child("ChuaHoanTat").addChildEventListener(new ChildEventListener() {
                                    @Override
                                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                        int dem = 0; // Xác định độ chính xác của lịch học (if dem == finaleCount)
                                        KhoaHoc kh = dataSnapshot.getValue(KhoaHoc.class);
                                        for (String Mon : kh.getMon()) {
                                            //Môn
                                            try {
                                                if (URLEncoder.encode(removeDiacriticalMarks(Mon), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getMon().get(0)), "utf-8"))) {
                                                    //Lịch học
                                                    if (finalCount != 0) //Ngày học tìm kiếm != null
                                                    {
                                                        for (String ngayHoc : khoaHoc.getLichHoc().getNgayHoc()) {
                                                            for (String ngayhoc : kh.getLichHoc().getNgayHoc()) {
                                                                if (ngayhoc.equals(ngayHoc)) {
                                                                    dem++;
                                                                    if (dem == finalCount) // Độ chính xác lịch học đạt mức chấp nhận được
                                                                    {
                                                                        khoaHocGanChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));
                                                                    }
                                                                }
                                                            }
                                                        }
                                                        if (dem >= finalCount) // Độ chính xác lịch học chấp nhận được hoặc rất chính xác
                                                        {
                                                            if (khoaHoc.getLichHoc().getThoiGian().size() != 0) // Buổi != null
                                                            {
                                                                for (String buoiHoc : khoaHoc.getLichHoc().getThoiGian()) {
                                                                    for (String buoihoc : kh.getLichHoc().getThoiGian()) {
                                                                        if (buoiHoc.equals(buoihoc)) // Buổi
                                                                        {
                                                                            //Học phí
                                                                            if (Double.parseDouble(kh.getHocPhi()) >= finalHocPhiDuoi && Double.parseDouble(kh.getHocPhi()) <= finalHocPhiTren) {
                                                                                khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            } else //Buổi == null
                                                            {
                                                                //Học phí
                                                                if (Double.parseDouble(kh.getHocPhi()) >= finalHocPhiDuoi && Double.parseDouble(kh.getHocPhi()) <= finalHocPhiTren) {
                                                                    khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));
                                                                }
                                                            }
                                                        }
                                                    } else //Ngày học tìm kiếm == null
                                                    {
                                                        if (khoaHoc.getLichHoc().getThoiGian().size() != 0) // Buổi != null
                                                        {
                                                            for (String buoiHoc : khoaHoc.getLichHoc().getThoiGian()) {
                                                                for (String buoihoc : kh.getLichHoc().getThoiGian()) {
                                                                    if (buoiHoc.equals(buoihoc)) // Buổi
                                                                    {
                                                                        //Học phí
                                                                        if (Double.parseDouble(kh.getHocPhi()) >= finalHocPhiDuoi && Double.parseDouble(kh.getHocPhi()) <= finalHocPhiTren) {
                                                                            khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        } else //Buổi == null
                                                        {
                                                            //Học phí
                                                            if (Double.parseDouble(kh.getHocPhi()) >= finalHocPhiDuoi && Double.parseDouble(kh.getHocPhi()) <= finalHocPhiTren) {
                                                                khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));
                                                            }
                                                        }
                                                    }
                                                }
                                                ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHoc(khoaHocChinhXac, khoaHocGanChinhXac);
                                            } catch (UnsupportedEncodingException e) {
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
                } else//2 Mon!=null, HocPhi==0
                {
                    if (khoaHoc.getGioiTinh() != "Nam, Nữ") //3 Mon!=null, HocPhi==0,GioiTinh!=Nam, Nữ
                    {
                        if (khoaHoc.getBangCap() != null) //4 Mon!=null, HocPhi==0,GioiTinh!=Nam, Nu,BangCap!=null
                        {
                            if (khoaHoc.getDiaDiem().getQuan() != "Khác") //5 Mon!=null, HocPhi==0,GioiTinh!=Nam, Nu,BangCap!=null,Quan != "Khác"
                            {
                                final double finalHocPhiDuoi = hocPhiDuoi;
                                final double finalHocPhiTren = hocPhiTren;
                                final int finalCount = count;
                                mData.getReference().child("KhoaHoc").child("KhoaHocTimGiaSu").child("ChuaHoanTat").addChildEventListener(new ChildEventListener() {
                                    @Override
                                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                        int dem = 0; // Xác định độ chính xác của lịch học (if dem == finaleCount)
                                        KhoaHoc kh = dataSnapshot.getValue(KhoaHoc.class);
                                        for (String Mon : kh.getMon()) {
                                            //Môn
                                            try {
                                                if (URLEncoder.encode(removeDiacriticalMarks(Mon), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getMon().get(0)), "utf-8"))) {
                                                    //Lịch học
                                                    if (finalCount != 0) //Ngày học tìm kiếm != null
                                                    {
                                                        for (String ngayHoc : khoaHoc.getLichHoc().getNgayHoc()) {
                                                            for (String ngayhoc : kh.getLichHoc().getNgayHoc()) {
                                                                if (ngayhoc.equals(ngayHoc)) {
                                                                    dem++;
                                                                    if (dem == finalCount) // Độ chính xác lịch học đạt mức chấp nhận được
                                                                    {
                                                                        khoaHocGanChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));
                                                                    }
                                                                }
                                                            }
                                                        }
                                                        if (dem >= finalCount) // Độ chính xác lịch học chấp nhận được hoặc rất chính xác
                                                        {
                                                            if (khoaHoc.getLichHoc().getThoiGian().size() != 0) // Buổi != null
                                                            {
                                                                for (String buoiHoc : khoaHoc.getLichHoc().getThoiGian()) {
                                                                    for (String buoihoc : kh.getLichHoc().getThoiGian()) {
                                                                        if (buoiHoc.equals(buoihoc)) // Buổi
                                                                        {
                                                                            //Giới tính
                                                                            if (kh.getGioiTinh().equals(khoaHoc.getGioiTinh())) {
                                                                                for (String bangcap : kh.getBangCap()) {
                                                                                    //Bằng cấp
                                                                                    if (URLEncoder.encode(removeDiacriticalMarks(bangcap), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getBangCap().get(0)), "utf-8"))) {
                                                                                        //Địa điểm
                                                                                        if (kh.getDiaDiem().getQuan().equals(khoaHoc.getDiaDiem().getQuan())) {
                                                                                            khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));

                                                                                        }
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            } else //Buổi == null
                                                            {
                                                                //Giới tính
                                                                if (kh.getGioiTinh().equals(khoaHoc.getGioiTinh())) {
                                                                    for (String bangcap : kh.getBangCap()) {
                                                                        //Bằng cấp
                                                                        if (URLEncoder.encode(removeDiacriticalMarks(bangcap), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getBangCap().get(0)), "utf-8"))) {
                                                                            //Địa điểm
                                                                            if (kh.getDiaDiem().getQuan().equals(khoaHoc.getDiaDiem().getQuan())) {
                                                                                khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));

                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    } else  //Ngày học tìm kiếm == null
                                                    {
                                                        if (khoaHoc.getLichHoc().getThoiGian().size() != 0) // Buổi != null
                                                        {
                                                            for (String buoiHoc : khoaHoc.getLichHoc().getThoiGian()) {
                                                                for (String buoihoc : kh.getLichHoc().getThoiGian()) {
                                                                    if (buoiHoc.equals(buoihoc)) // Buổi
                                                                    {
                                                                        //Giới tính
                                                                        if (kh.getGioiTinh().equals(khoaHoc.getGioiTinh())) {
                                                                            for (String bangcap : kh.getBangCap()) {
                                                                                //Bằng cấp
                                                                                if (URLEncoder.encode(removeDiacriticalMarks(bangcap), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getBangCap().get(0)), "utf-8"))) {
                                                                                    //Địa điểm
                                                                                    if (kh.getDiaDiem().getQuan().equals(khoaHoc.getDiaDiem().getQuan())) {
                                                                                        khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));

                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        } else //Buổi == null
                                                        {
                                                            //Giới tính
                                                            if (kh.getGioiTinh().equals(khoaHoc.getGioiTinh())) {
                                                                for (String bangcap : kh.getBangCap()) {
                                                                    //Bằng cấp
                                                                    if (URLEncoder.encode(removeDiacriticalMarks(bangcap), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getBangCap().get(0)), "utf-8"))) {
                                                                        //Địa điểm
                                                                        if (kh.getDiaDiem().getQuan().equals(khoaHoc.getDiaDiem().getQuan())) {
                                                                            khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));

                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                                ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHoc(khoaHocChinhXac, khoaHocGanChinhXac);
                                            } catch (UnsupportedEncodingException e) {
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
                            } else //5 Mon!=null, HocPhi==0,GioiTinh!=Nam, Nu,BangCap!=null, Quan = "Khác"
                            {
                                final double finalHocPhiDuoi = hocPhiDuoi;
                                final double finalHocPhiTren = hocPhiTren;
                                final int finalCount = count;
                                mData.getReference().child("KhoaHoc").child("KhoaHocTimGiaSu").child("ChuaHoanTat").addChildEventListener(new ChildEventListener() {
                                    @Override
                                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                        int dem = 0; // Xác định độ chính xác của lịch học (if dem == finaleCount)
                                        KhoaHoc kh = dataSnapshot.getValue(KhoaHoc.class);
                                        for (String Mon : kh.getMon()) {
                                            //Môn
                                            try {
                                                if (URLEncoder.encode(removeDiacriticalMarks(Mon), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getMon().get(0)), "utf-8"))) {
                                                    //Lịch học
                                                    if (finalCount != 0) //Ngày học tìm kiếm != null
                                                    {
                                                        for (String ngayHoc : khoaHoc.getLichHoc().getNgayHoc()) {
                                                            for (String ngayhoc : kh.getLichHoc().getNgayHoc()) {
                                                                if (ngayhoc.equals(ngayHoc)) {
                                                                    dem++;
                                                                    if (dem == finalCount) // Độ chính xác lịch học đạt mức chấp nhận được
                                                                    {
                                                                        khoaHocGanChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));
                                                                    }
                                                                }
                                                            }
                                                        }
                                                        if (dem >= finalCount) // Độ chính xác lịch học chấp nhận được hoặc rất chính xác
                                                        {
                                                            if (khoaHoc.getLichHoc().getThoiGian().size() != 0) // Buổi != null
                                                            {
                                                                for (String buoiHoc : khoaHoc.getLichHoc().getThoiGian()) {
                                                                    for (String buoihoc : kh.getLichHoc().getThoiGian()) {
                                                                        if (buoiHoc.equals(buoihoc)) // Buổi
                                                                        {
                                                                            //Giới tính
                                                                            if (kh.getGioiTinh().equals(khoaHoc.getGioiTinh())) {
                                                                                for (String bangcap : kh.getBangCap()) {
                                                                                    //Bằng cấp
                                                                                    if (URLEncoder.encode(removeDiacriticalMarks(bangcap), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getBangCap().get(0)), "utf-8"))) {
                                                                                        khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));

                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            } else //Buổi == null
                                                            {
                                                                //Giới tính
                                                                if (kh.getGioiTinh().equals(khoaHoc.getGioiTinh())) {
                                                                    for (String bangcap : kh.getBangCap()) {
                                                                        //Bằng cấp
                                                                        if (URLEncoder.encode(removeDiacriticalMarks(bangcap), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getBangCap().get(0)), "utf-8"))) {
                                                                            khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));

                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    } else  //Ngày học tìm kiếm == null
                                                    {
                                                        if (khoaHoc.getLichHoc().getThoiGian().size() != 0) // Buổi != null
                                                        {
                                                            for (String buoiHoc : khoaHoc.getLichHoc().getThoiGian()) {
                                                                for (String buoihoc : kh.getLichHoc().getThoiGian()) {
                                                                    if (buoiHoc.equals(buoihoc)) // Buổi
                                                                    {
                                                                        //Giới tính
                                                                        if (kh.getGioiTinh().equals(khoaHoc.getGioiTinh())) {
                                                                            for (String bangcap : kh.getBangCap()) {
                                                                                //Bằng cấp
                                                                                if (URLEncoder.encode(removeDiacriticalMarks(bangcap), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getBangCap().get(0)), "utf-8"))) {
                                                                                    khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));

                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        } else //Buổi == null
                                                        {
                                                            //Giới tính
                                                            if (kh.getGioiTinh().equals(khoaHoc.getGioiTinh())) {
                                                                for (String bangcap : kh.getBangCap()) {
                                                                    //Bằng cấp
                                                                    if (URLEncoder.encode(removeDiacriticalMarks(bangcap), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getBangCap().get(0)), "utf-8"))) {
                                                                        khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));

                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                                ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHoc(khoaHocChinhXac, khoaHocGanChinhXac);
                                            } catch (UnsupportedEncodingException e) {
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
                        } else//4 Mon!=null, HocPhi==0,GioiTinh!=Nam, Nu,BangCap==null
                        {
                            if (khoaHoc.getDiaDiem().getQuan() != "Khác") //5 Mon!=null, HocPhi==0,GioiTinh!=Nam, Nu,BangCap==null,Quan != "Khác"
                            {
                                final double finalHocPhiDuoi = hocPhiDuoi;
                                final double finalHocPhiTren = hocPhiTren;
                                final int finalCount = count;
                                mData.getReference().child("KhoaHoc").child("KhoaHocTimGiaSu").child("ChuaHoanTat").addChildEventListener(new ChildEventListener() {
                                    @Override
                                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                        int dem = 0; // Xác định độ chính xác của lịch học (if dem == finaleCount)
                                        KhoaHoc kh = dataSnapshot.getValue(KhoaHoc.class);
                                        for (String Mon : kh.getMon()) {
                                            //Môn
                                            try {
                                                if (URLEncoder.encode(removeDiacriticalMarks(Mon), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getMon().get(0)), "utf-8"))) {
                                                    //Lịch học
                                                    if (finalCount != 0) //Ngày học tìm kiếm != null
                                                    {
                                                        for (String ngayHoc : khoaHoc.getLichHoc().getNgayHoc()) {
                                                            for (String ngayhoc : kh.getLichHoc().getNgayHoc()) {
                                                                if (ngayhoc.equals(ngayHoc)) {
                                                                    dem++;
                                                                    if (dem == finalCount) // Độ chính xác lịch học đạt mức chấp nhận được
                                                                    {
                                                                        khoaHocGanChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));
                                                                    }
                                                                }
                                                            }
                                                        }
                                                        if (dem >= finalCount) // Độ chính xác lịch học chấp nhận được hoặc rất chính xác
                                                        {
                                                            if (khoaHoc.getLichHoc().getThoiGian().size() != 0) // Buổi != null
                                                            {
                                                                for (String buoiHoc : khoaHoc.getLichHoc().getThoiGian()) {
                                                                    for (String buoihoc : kh.getLichHoc().getThoiGian()) {
                                                                        if (buoiHoc.equals(buoihoc)) // Buổi
                                                                        {
                                                                            //Giới tính
                                                                            if (kh.getGioiTinh().equals(khoaHoc.getGioiTinh())) {
                                                                                //Địa điểm
                                                                                if (kh.getDiaDiem().getQuan().equals(khoaHoc.getDiaDiem().getQuan())) {
                                                                                    khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));

                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            } else //Buổi == null
                                                            {
                                                                //Giới tính
                                                                if (kh.getGioiTinh().equals(khoaHoc.getGioiTinh())) {
                                                                    //Địa điểm
                                                                    if (kh.getDiaDiem().getQuan().equals(khoaHoc.getDiaDiem().getQuan())) {
                                                                        khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));

                                                                    }
                                                                }
                                                            }
                                                        }
                                                    } else  //Ngày học tìm kiếm == null
                                                    {
                                                        if (khoaHoc.getLichHoc().getThoiGian().size() != 0) // Buổi != null
                                                        {
                                                            for (String buoiHoc : khoaHoc.getLichHoc().getThoiGian()) {
                                                                for (String buoihoc : kh.getLichHoc().getThoiGian()) {
                                                                    if (buoiHoc.equals(buoihoc)) // Buổi
                                                                    {
                                                                        //Giới tính
                                                                        if (kh.getGioiTinh().equals(khoaHoc.getGioiTinh())) {
                                                                            //Địa điểm
                                                                            if (kh.getDiaDiem().getQuan().equals(khoaHoc.getDiaDiem().getQuan())) {
                                                                                khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));

                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        } else //Buổi == null
                                                        {
                                                            //Giới tính
                                                            if (kh.getGioiTinh().equals(khoaHoc.getGioiTinh())) {
                                                                //Địa điểm
                                                                if (kh.getDiaDiem().getQuan().equals(khoaHoc.getDiaDiem().getQuan())) {
                                                                    khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));

                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                                ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHoc(khoaHocChinhXac, khoaHocGanChinhXac);
                                            } catch (UnsupportedEncodingException e) {
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
                            } else //5 Mon!=null, HocPhi==0,GioiTinh!=Nam, Nu,BangCap==null, Quan = "Khác"
                            {
                                final double finalHocPhiDuoi = hocPhiDuoi;
                                final double finalHocPhiTren = hocPhiTren;
                                final int finalCount = count;
                                mData.getReference().child("KhoaHoc").child("KhoaHocTimGiaSu").child("ChuaHoanTat").addChildEventListener(new ChildEventListener() {
                                    @Override
                                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                        int dem = 0; // Xác định độ chính xác của lịch học (if dem == finaleCount)
                                        KhoaHoc kh = dataSnapshot.getValue(KhoaHoc.class);
                                        for (String Mon : kh.getMon()) {
                                            //Môn
                                            try {
                                                if (URLEncoder.encode(removeDiacriticalMarks(Mon), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getMon().get(0)), "utf-8"))) {
                                                    //Lịch học
                                                    if (finalCount != 0) //Ngày học tìm kiếm != null
                                                    {
                                                        for (String ngayHoc : khoaHoc.getLichHoc().getNgayHoc()) {
                                                            for (String ngayhoc : kh.getLichHoc().getNgayHoc()) {
                                                                if (ngayhoc.equals(ngayHoc)) {
                                                                    dem++;
                                                                    if (dem == finalCount) // Độ chính xác lịch học đạt mức chấp nhận được
                                                                    {
                                                                        khoaHocGanChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));
                                                                    }
                                                                }
                                                            }
                                                        }
                                                        if (dem >= finalCount) // Độ chính xác lịch học chấp nhận được hoặc rất chính xác
                                                        {
                                                            if (khoaHoc.getLichHoc().getThoiGian().size() != 0) // Buổi != null
                                                            {
                                                                for (String buoiHoc : khoaHoc.getLichHoc().getThoiGian()) {
                                                                    for (String buoihoc : kh.getLichHoc().getThoiGian()) {
                                                                        if (buoiHoc.equals(buoihoc)) // Buổi
                                                                        {
                                                                            //Giới tính
                                                                            if (kh.getGioiTinh().equals(khoaHoc.getGioiTinh())) {
                                                                                khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));

                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            } else //Buổi == null
                                                            {
//Giới tính
                                                                if (kh.getGioiTinh().equals(khoaHoc.getGioiTinh())) {
                                                                    khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));

                                                                }
                                                            }
                                                        }
                                                    } else //Ngày học tìm kiếm == null
                                                    {
                                                        if (khoaHoc.getLichHoc().getThoiGian().size() != 0) // Buổi != null
                                                        {
                                                            for (String buoiHoc : khoaHoc.getLichHoc().getThoiGian()) {
                                                                for (String buoihoc : kh.getLichHoc().getThoiGian()) {
                                                                    if (buoiHoc.equals(buoihoc)) // Buổi
                                                                    {
                                                                        //Giới tính
                                                                        if (kh.getGioiTinh().equals(khoaHoc.getGioiTinh())) {
                                                                            khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));

                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        } else //Buổi == null
                                                        {
                                                            //Giới tính
                                                            if (kh.getGioiTinh().equals(khoaHoc.getGioiTinh())) {
                                                                khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));

                                                            }
                                                        }
                                                    }
                                                }
                                                ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHoc(khoaHocChinhXac, khoaHocGanChinhXac);
                                            } catch (UnsupportedEncodingException e) {
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
                    } else //3 Mon!=null, HocPhi==0,GioiTinh==Nam, Nữ
                    {
                        if (khoaHoc.getBangCap() != null) //4 Mon!=null, HocPhi==0,GioiTinh==Nam, Nữ,BangCap!=null
                        {
                            if (khoaHoc.getDiaDiem().getQuan() != "Khác") //5 Mon!=null, HocPhi==0,GioiTinh==Nam, Nữ,BangCap!=null,Quan != "Khác"
                            {
                                final double finalHocPhiDuoi = hocPhiDuoi;
                                final double finalHocPhiTren = hocPhiTren;
                                final int finalCount = count;
                                mData.getReference().child("KhoaHoc").child("KhoaHocTimGiaSu").child("ChuaHoanTat").addChildEventListener(new ChildEventListener() {
                                    @Override
                                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                        int dem = 0; // Xác định độ chính xác của lịch học (if dem == finaleCount)
                                        KhoaHoc kh = dataSnapshot.getValue(KhoaHoc.class);
                                        for (String Mon : kh.getMon()) {
                                            //Môn
                                            try {
                                                if (URLEncoder.encode(removeDiacriticalMarks(Mon), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getMon().get(0)), "utf-8"))) {
                                                    //Lịch học
                                                    if (finalCount != 0) //Ngày học tìm kiếm != null
                                                    {
                                                        for (String ngayHoc : khoaHoc.getLichHoc().getNgayHoc()) {
                                                            for (String ngayhoc : kh.getLichHoc().getNgayHoc()) {
                                                                if (ngayhoc.equals(ngayHoc)) {
                                                                    dem++;
                                                                    if (dem == finalCount) // Độ chính xác lịch học đạt mức chấp nhận được
                                                                    {
                                                                        khoaHocGanChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));
                                                                    }
                                                                }
                                                            }
                                                        }
                                                        if (dem >= finalCount) // Độ chính xác lịch học chấp nhận được hoặc rất chính xác
                                                        {
                                                            if (khoaHoc.getLichHoc().getThoiGian().size() != 0) // Buổi != null
                                                            {
                                                                for (String buoiHoc : khoaHoc.getLichHoc().getThoiGian()) {
                                                                    for (String buoihoc : kh.getLichHoc().getThoiGian()) {
                                                                        if (buoiHoc.equals(buoihoc)) // Buổi
                                                                        {
                                                                            for (String bangcap : kh.getBangCap()) {
                                                                                //Bằng cấp
                                                                                if (URLEncoder.encode(removeDiacriticalMarks(bangcap), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getBangCap().get(0)), "utf-8"))) {
                                                                                    //Địa điểm
                                                                                    if (kh.getDiaDiem().getQuan().equals(khoaHoc.getDiaDiem().getQuan())) {
                                                                                        khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));

                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            } else //Buổi == null
                                                            {
                                                                for (String bangcap : kh.getBangCap()) {
                                                                    //Bằng cấp
                                                                    if (URLEncoder.encode(removeDiacriticalMarks(bangcap), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getBangCap().get(0)), "utf-8"))) {
                                                                        //Địa điểm
                                                                        if (kh.getDiaDiem().getQuan().equals(khoaHoc.getDiaDiem().getQuan())) {
                                                                            khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));

                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    } else //Ngày học tìm kiếm == null
                                                    {
                                                        if (khoaHoc.getLichHoc().getThoiGian().size() != 0) // Buổi != null
                                                        {
                                                            for (String buoiHoc : khoaHoc.getLichHoc().getThoiGian()) {
                                                                for (String buoihoc : kh.getLichHoc().getThoiGian()) {
                                                                    if (buoiHoc.equals(buoihoc)) // Buổi
                                                                    {
                                                                        for (String bangcap : kh.getBangCap()) {
                                                                            //Bằng cấp
                                                                            if (URLEncoder.encode(removeDiacriticalMarks(bangcap), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getBangCap().get(0)), "utf-8"))) {
                                                                                //Địa điểm
                                                                                if (kh.getDiaDiem().getQuan().equals(khoaHoc.getDiaDiem().getQuan())) {
                                                                                    khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));

                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        } else //Buổi == null
                                                        {
                                                            for (String bangcap : kh.getBangCap()) {
                                                                //Bằng cấp
                                                                if (URLEncoder.encode(removeDiacriticalMarks(bangcap), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getBangCap().get(0)), "utf-8"))) {
                                                                    //Địa điểm
                                                                    if (kh.getDiaDiem().getQuan().equals(khoaHoc.getDiaDiem().getQuan())) {
                                                                        khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));

                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                                ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHoc(khoaHocChinhXac, khoaHocGanChinhXac);
                                            } catch (UnsupportedEncodingException e) {
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
                            } else //5 Mon!=null, HocPhi==0,GioiTinh==Nam, Nữ,BangCap!=null, Quan = "Khác"
                            {
                                final double finalHocPhiDuoi = hocPhiDuoi;
                                final double finalHocPhiTren = hocPhiTren;
                                final int finalCount = count;
                                mData.getReference().child("KhoaHoc").child("KhoaHocTimGiaSu").child("ChuaHoanTat").addChildEventListener(new ChildEventListener() {
                                    @Override
                                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                        int dem = 0; // Xác định độ chính xác của lịch học (if dem == finaleCount)
                                        KhoaHoc kh = dataSnapshot.getValue(KhoaHoc.class);
                                        for (String Mon : kh.getMon()) {
                                            //Môn
                                            try {
                                                if (URLEncoder.encode(removeDiacriticalMarks(Mon), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getMon().get(0)), "utf-8"))) {
                                                    //Lịch học
                                                    if (finalCount != 0) //Ngày học tìm kiếm != null
                                                    {
                                                        for (String ngayHoc : khoaHoc.getLichHoc().getNgayHoc()) {
                                                            for (String ngayhoc : kh.getLichHoc().getNgayHoc()) {
                                                                if (ngayhoc.equals(ngayHoc)) {
                                                                    dem++;
                                                                    if (dem == finalCount) // Độ chính xác lịch học đạt mức chấp nhận được
                                                                    {
                                                                        khoaHocGanChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));
                                                                    }
                                                                }
                                                            }
                                                        }
                                                        if (dem >= finalCount) // Độ chính xác lịch học chấp nhận được hoặc rất chính xác
                                                        {
                                                            if (khoaHoc.getLichHoc().getThoiGian().size() != 0) // Buổi != null
                                                            {
                                                                for (String buoiHoc : khoaHoc.getLichHoc().getThoiGian()) {
                                                                    for (String buoihoc : kh.getLichHoc().getThoiGian()) {
                                                                        if (buoiHoc.equals(buoihoc)) // Buổi
                                                                        {
                                                                            for (String bangcap : kh.getBangCap()) {
                                                                                //Bằng cấp
                                                                                if (URLEncoder.encode(removeDiacriticalMarks(bangcap), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getBangCap().get(0)), "utf-8"))) {
                                                                                    khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));

                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            } else //Buổi == null
                                                            {
                                                                for (String bangcap : kh.getBangCap()) {
                                                                    //Bằng cấp
                                                                    if (URLEncoder.encode(removeDiacriticalMarks(bangcap), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getBangCap().get(0)), "utf-8"))) {
                                                                        khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));

                                                                    }
                                                                }
                                                            }
                                                        }
                                                    } else //Ngày học tìm kiếm ==null
                                                    {
                                                        if (khoaHoc.getLichHoc().getThoiGian().size() != 0) // Buổi != null
                                                        {
                                                            for (String buoiHoc : khoaHoc.getLichHoc().getThoiGian()) {
                                                                for (String buoihoc : kh.getLichHoc().getThoiGian()) {
                                                                    if (buoiHoc.equals(buoihoc)) // Buổi
                                                                    {
                                                                        for (String bangcap : kh.getBangCap()) {
                                                                            //Bằng cấp
                                                                            if (URLEncoder.encode(removeDiacriticalMarks(bangcap), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getBangCap().get(0)), "utf-8"))) {
                                                                                khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));

                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        } else //Buổi == null
                                                        {
                                                            for (String bangcap : kh.getBangCap()) {
                                                                //Bằng cấp
                                                                if (URLEncoder.encode(removeDiacriticalMarks(bangcap), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getBangCap().get(0)), "utf-8"))) {
                                                                    khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));

                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                                ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHoc(khoaHocChinhXac, khoaHocGanChinhXac);
                                            } catch (UnsupportedEncodingException e) {
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
                        } else//4 Mon!=null, HocPhi==0,GioiTinh==Nam, Nữ,BangCap==null
                        {
                            if (khoaHoc.getDiaDiem().getQuan() != "Khác") //5 Mon!=null, HocPhi==0,GioiTinh==Nam, Nữ,BangCap==null,Quan != "Khác"
                            {
                                final double finalHocPhiDuoi = hocPhiDuoi;
                                final double finalHocPhiTren = hocPhiTren;
                                final int finalCount = count;
                                mData.getReference().child("KhoaHoc").child("KhoaHocTimGiaSu").child("ChuaHoanTat").addChildEventListener(new ChildEventListener() {
                                    @Override
                                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                        int dem = 0; // Xác định độ chính xác của lịch học (if dem == finaleCount)
                                        KhoaHoc kh = dataSnapshot.getValue(KhoaHoc.class);
                                        for (String Mon : kh.getMon()) {
                                            try {
                                                if (URLEncoder.encode(removeDiacriticalMarks(Mon), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getMon().get(0)), "utf-8"))) {
                                                    //Lịch học
                                                    if (finalCount != 0) //Ngày học tìm kiếm != null
                                                    {
                                                        for (String ngayHoc : khoaHoc.getLichHoc().getNgayHoc()) {
                                                            for (String ngayhoc : kh.getLichHoc().getNgayHoc()) {
                                                                if (ngayhoc.equals(ngayHoc)) {
                                                                    dem++;
                                                                    if (dem == finalCount) // Độ chính xác lịch học đạt mức chấp nhận được
                                                                    {
                                                                        khoaHocGanChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));
                                                                    }
                                                                }
                                                            }
                                                        }
                                                        if (dem >= finalCount) // Độ chính xác lịch học chấp nhận được hoặc rất chính xác
                                                        {
                                                            if (khoaHoc.getLichHoc().getThoiGian().size() != 0) // Buổi != null
                                                            {
                                                                for (String buoiHoc : khoaHoc.getLichHoc().getThoiGian()) {
                                                                    for (String buoihoc : kh.getLichHoc().getThoiGian()) {
                                                                        if (buoiHoc.equals(buoihoc)) // Buổi
                                                                        {
                                                                            //Địa điểm
                                                                            if (kh.getDiaDiem().getQuan().equals(khoaHoc.getDiaDiem().getQuan())) {
                                                                                khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));

                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            } else //Buổi == null
                                                            {
//Địa điểm
                                                                if (kh.getDiaDiem().getQuan().equals(khoaHoc.getDiaDiem().getQuan())) {
                                                                    khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));

                                                                }
                                                            }
                                                        }
                                                    } else // Ngày học tìm kiếm == null
                                                    {
                                                        if (khoaHoc.getLichHoc().getThoiGian().size() != 0) // Buổi != null
                                                        {
                                                            for (String buoiHoc : khoaHoc.getLichHoc().getThoiGian()) {
                                                                for (String buoihoc : kh.getLichHoc().getThoiGian()) {
                                                                    if (buoiHoc.equals(buoihoc)) // Buổi
                                                                    {
                                                                        //Địa điểm
                                                                        if (kh.getDiaDiem().getQuan().equals(khoaHoc.getDiaDiem().getQuan())) {
                                                                            khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));

                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        } else //Buổi == null
                                                        {
//Địa điểm
                                                            if (kh.getDiaDiem().getQuan().equals(khoaHoc.getDiaDiem().getQuan())) {
                                                                khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));

                                                            }
                                                        }
                                                    }
                                                }
                                                ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHoc(khoaHocChinhXac, khoaHocGanChinhXac);
                                            } catch (UnsupportedEncodingException e) {
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
                            } else //5 Mon!=null, HocPhi==0,GioiTinh==Nam, Nữ,BangCap==null, Quan = "Khác"
                            {
                                final double finalHocPhiDuoi = hocPhiDuoi;
                                final double finalHocPhiTren = hocPhiTren;
                                final int finalCount = count;
                                mData.getReference().child("KhoaHoc").child("KhoaHocTimGiaSu").child("ChuaHoanTat").addChildEventListener(new ChildEventListener() {
                                    @Override
                                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                        int dem = 0; // Xác định độ chính xác của lịch học (if dem == finaleCount)
                                        KhoaHoc kh = dataSnapshot.getValue(KhoaHoc.class);
                                        for (String Mon : kh.getMon()) {
                                            //Môn
                                            try {
                                                if (URLEncoder.encode(removeDiacriticalMarks(Mon), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getMon().get(0)), "utf-8"))) {
                                                    //Lịch học
                                                    if (finalCount != 0) //Ngày học tìm kiếm != null
                                                    {
                                                        for (String ngayHoc : khoaHoc.getLichHoc().getNgayHoc()) {
                                                            for (String ngayhoc : kh.getLichHoc().getNgayHoc()) {
                                                                if (ngayhoc.equals(ngayHoc)) {
                                                                    dem++;
                                                                    if (dem == finalCount) // Độ chính xác lịch học đạt mức chấp nhận được
                                                                    {
                                                                        khoaHocGanChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));
                                                                    }
                                                                }
                                                            }
                                                        }
                                                        if (dem >= finalCount) // Độ chính xác lịch học chấp nhận được hoặc rất chính xác
                                                        {
                                                            if (khoaHoc.getLichHoc().getThoiGian().size() != 0) // Buổi != null
                                                            {
                                                                for (String buoiHoc : khoaHoc.getLichHoc().getThoiGian()) {
                                                                    for (String buoihoc : kh.getLichHoc().getThoiGian()) {
                                                                        if (buoiHoc.equals(buoihoc)) // Buổi
                                                                        {
                                                                            khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));

                                                                        }
                                                                    }
                                                                }
                                                            } else //Buổi == null
                                                            {
                                                                khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));

                                                            }
                                                        }
                                                    } else //Ngày học tìm kiếm == null
                                                    {
                                                        if (khoaHoc.getLichHoc().getThoiGian().size() != 0) // Buổi != null
                                                        {
                                                            for (String buoiHoc : khoaHoc.getLichHoc().getThoiGian()) {
                                                                for (String buoihoc : kh.getLichHoc().getThoiGian()) {
                                                                    if (buoiHoc.equals(buoihoc)) // Buổi
                                                                    {
                                                                        khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));

                                                                    }
                                                                }
                                                            }
                                                        } else //Buổi == null
                                                        {
                                                            khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));

                                                        }
                                                    }
                                                }
                                                ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHoc(khoaHocChinhXac, khoaHocGanChinhXac);
                                            } catch (UnsupportedEncodingException e) {
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
                }
            } else//1 Mon == null
            {
                if (hocPhiTimKiem != 0) //2 Mon == null, HocPhi !=0
                {
                    if (khoaHoc.getGioiTinh() != "Nam, Nữ") //3 Mon == null, HocPhi !=0,GioiTinh!=Nam, Nữ
                    {
                        if (khoaHoc.getBangCap() != null) //4 Mon == null,HocPhi!=null,GioiTinh!=Nam, Nu,BangCap!=null
                        {
                            if (khoaHoc.getDiaDiem().getQuan() != "Khác") //5 Mon == null,HocPhi!=null,GioiTinh!=Nam, Nu,BangCap!=null,Quan != "Khác"
                            {
                                final double finalHocPhiDuoi = hocPhiDuoi;
                                final double finalHocPhiTren = hocPhiTren;
                                final int finalCount = count;
                                mData.getReference().child("KhoaHoc").child("KhoaHocTimGiaSu").child("ChuaHoanTat").addChildEventListener(new ChildEventListener() {
                                    @Override
                                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                        int dem = 0; // Xác định độ chính xác của lịch học (if dem == finaleCount)
                                        KhoaHoc kh = dataSnapshot.getValue(KhoaHoc.class);
                                        for (String linhvuc : kh.getLinhVuc()) {

                                            try {
                                                //LinhVuc
                                                if (URLEncoder.encode(removeDiacriticalMarks(linhvuc), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getLinhVuc().get(0)), "utf-8"))) {

                                                    //Lịch học
                                                    if (finalCount != 0) //Ngày học tìm kiếm != null
                                                    {
                                                        for (String ngayHoc : khoaHoc.getLichHoc().getNgayHoc()) {
                                                            for (String ngayhoc : kh.getLichHoc().getNgayHoc()) {
                                                                if (ngayhoc.equals(ngayHoc)) {
                                                                    dem++;
                                                                    if (dem == finalCount) // Độ chính xác lịch học đạt mức chấp nhận được
                                                                    {
                                                                        khoaHocGanChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));
                                                                    }
                                                                }
                                                            }
                                                        }
                                                        if (dem >= finalCount) // Độ chính xác lịch học chấp nhận được hoặc rất chính xác
                                                        {
                                                            if (khoaHoc.getLichHoc().getThoiGian().size() != 0) // Buổi != null
                                                            {
                                                                for (String buoiHoc : khoaHoc.getLichHoc().getThoiGian()) {
                                                                    for (String buoihoc : kh.getLichHoc().getThoiGian()) {
                                                                        if (buoiHoc.equals(buoihoc)) // Buổi
                                                                        {
                                                                            //Học phí
                                                                            if (Double.parseDouble(kh.getHocPhi()) >= finalHocPhiDuoi && Double.parseDouble(kh.getHocPhi()) <= finalHocPhiTren) {
                                                                                //Giới tính
                                                                                if (kh.getGioiTinh().equals(khoaHoc.getGioiTinh())) {
                                                                                    for (String bangcap : kh.getBangCap()) {
                                                                                        //Bằng cấp
                                                                                        if (URLEncoder.encode(removeDiacriticalMarks(bangcap), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getBangCap().get(0)), "utf-8"))) {
                                                                                            //Địa điểm
                                                                                            if (kh.getDiaDiem().getQuan().equals(khoaHoc.getDiaDiem().getQuan())) {
                                                                                                khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));

                                                                                            }
                                                                                        }
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            } else //Buổi == null
                                                            {
//Học phí
                                                                if (Double.parseDouble(kh.getHocPhi()) >= finalHocPhiDuoi && Double.parseDouble(kh.getHocPhi()) <= finalHocPhiTren) {
                                                                    //Giới tính
                                                                    if (kh.getGioiTinh().equals(khoaHoc.getGioiTinh())) {
                                                                        for (String bangcap : kh.getBangCap()) {
                                                                            //Bằng cấp
                                                                            if (URLEncoder.encode(removeDiacriticalMarks(bangcap), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getBangCap().get(0)), "utf-8"))) {
                                                                                //Địa điểm
                                                                                if (kh.getDiaDiem().getQuan().equals(khoaHoc.getDiaDiem().getQuan())) {
                                                                                    khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));

                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    } else //Ngày học tìm kiếm == null
                                                    {
                                                        if (khoaHoc.getLichHoc().getThoiGian().size() != 0) // Buổi != null
                                                        {
                                                            for (String buoiHoc : khoaHoc.getLichHoc().getThoiGian()) {
                                                                for (String buoihoc : kh.getLichHoc().getThoiGian()) {
                                                                    if (buoiHoc.equals(buoihoc)) // Buổi
                                                                    {
                                                                        //Học phí
                                                                        if (Double.parseDouble(kh.getHocPhi()) >= finalHocPhiDuoi && Double.parseDouble(kh.getHocPhi()) <= finalHocPhiTren) {
                                                                            //Giới tính
                                                                            if (kh.getGioiTinh().equals(khoaHoc.getGioiTinh())) {
                                                                                for (String bangcap : kh.getBangCap()) {
                                                                                    //Bằng cấp
                                                                                    if (URLEncoder.encode(removeDiacriticalMarks(bangcap), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getBangCap().get(0)), "utf-8"))) {
                                                                                        //Địa điểm
                                                                                        if (kh.getDiaDiem().getQuan().equals(khoaHoc.getDiaDiem().getQuan())) {
                                                                                            khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));

                                                                                        }
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        } else //Buổi == null
                                                        {
//Học phí
                                                            if (Double.parseDouble(kh.getHocPhi()) >= finalHocPhiDuoi && Double.parseDouble(kh.getHocPhi()) <= finalHocPhiTren) {
                                                                //Giới tính
                                                                if (kh.getGioiTinh().equals(khoaHoc.getGioiTinh())) {
                                                                    for (String bangcap : kh.getBangCap()) {
                                                                        //Bằng cấp
                                                                        if (URLEncoder.encode(removeDiacriticalMarks(bangcap), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getBangCap().get(0)), "utf-8"))) {
                                                                            //Địa điểm
                                                                            if (kh.getDiaDiem().getQuan().equals(khoaHoc.getDiaDiem().getQuan())) {
                                                                                khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));

                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                                ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHoc(khoaHocChinhXac, khoaHocGanChinhXac);
                                            } catch (UnsupportedEncodingException e) {
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
                            } else //5 Mon == null,HocPhi!=null,GioiTinh!=Nam, Nu,BangCap!=null, Quan = "Khác"
                            {
                                final double finalHocPhiDuoi = hocPhiDuoi;
                                final double finalHocPhiTren = hocPhiTren;
                                final int finalCount = count;
                                mData.getReference().child("KhoaHoc").child("KhoaHocTimGiaSu").child("ChuaHoanTat").addChildEventListener(new ChildEventListener() {
                                    @Override
                                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                        int dem = 0; // Xác định độ chính xác của lịch học (if dem == finaleCount)
                                        KhoaHoc kh = dataSnapshot.getValue(KhoaHoc.class);
                                        for (String linhvuc : kh.getLinhVuc()) {
                                            //LinhVuc
                                            try {
                                                if (URLEncoder.encode(removeDiacriticalMarks(linhvuc), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getLinhVuc().get(0)), "utf-8"))) {
                                                    //Lịch học
                                                    if (finalCount != 0) //Ngày học tìm kiếm != null
                                                    {
                                                        for (String ngayHoc : khoaHoc.getLichHoc().getNgayHoc()) {
                                                            for (String ngayhoc : kh.getLichHoc().getNgayHoc()) {
                                                                if (ngayhoc.equals(ngayHoc)) {
                                                                    dem++;
                                                                    if (dem == finalCount) // Độ chính xác lịch học đạt mức chấp nhận được
                                                                    {
                                                                        khoaHocGanChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));
                                                                    }
                                                                }
                                                            }
                                                        }
                                                        if (dem >= finalCount) // Độ chính xác lịch học chấp nhận được hoặc rất chính xác
                                                        {
                                                            if (khoaHoc.getLichHoc().getThoiGian().size() != 0) // Buổi != null
                                                            {
                                                                for (String buoiHoc : khoaHoc.getLichHoc().getThoiGian()) {
                                                                    for (String buoihoc : kh.getLichHoc().getThoiGian()) {
                                                                        if (buoiHoc.equals(buoihoc)) // Buổi
                                                                        {
                                                                            //Học phí
                                                                            if (Double.parseDouble(kh.getHocPhi()) >= finalHocPhiDuoi && Double.parseDouble(kh.getHocPhi()) <= finalHocPhiTren) {
                                                                                //Giới tính
                                                                                if (kh.getGioiTinh().equals(khoaHoc.getGioiTinh())) {
                                                                                    for (String bangcap : kh.getBangCap()) {
                                                                                        //Bằng cấp
                                                                                        if (URLEncoder.encode(removeDiacriticalMarks(bangcap), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getBangCap().get(0)), "utf-8"))) {
                                                                                            khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));
                                                                                        }
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            } else //Buổi == null
                                                            {
//Học phí
                                                                if (Double.parseDouble(kh.getHocPhi()) >= finalHocPhiDuoi && Double.parseDouble(kh.getHocPhi()) <= finalHocPhiTren) {
                                                                    //Giới tính
                                                                    if (kh.getGioiTinh().equals(khoaHoc.getGioiTinh())) {
                                                                        for (String bangcap : kh.getBangCap()) {
                                                                            //Bằng cấp
                                                                            if (URLEncoder.encode(removeDiacriticalMarks(bangcap), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getBangCap().get(0)), "utf-8"))) {
                                                                                khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    } else //Ngày học tìm kiếm == null
                                                    {
                                                        if (khoaHoc.getLichHoc().getThoiGian().size() != 0) // Buổi != null
                                                        {
                                                            for (String buoiHoc : khoaHoc.getLichHoc().getThoiGian()) {
                                                                for (String buoihoc : kh.getLichHoc().getThoiGian()) {
                                                                    if (buoiHoc.equals(buoihoc)) // Buổi
                                                                    {
                                                                        //Học phí
                                                                        if (Double.parseDouble(kh.getHocPhi()) >= finalHocPhiDuoi && Double.parseDouble(kh.getHocPhi()) <= finalHocPhiTren) {
                                                                            //Giới tính
                                                                            if (kh.getGioiTinh().equals(khoaHoc.getGioiTinh())) {
                                                                                for (String bangcap : kh.getBangCap()) {
                                                                                    //Bằng cấp
                                                                                    if (URLEncoder.encode(removeDiacriticalMarks(bangcap), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getBangCap().get(0)), "utf-8"))) {
                                                                                        khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        } else //Buổi == null
                                                        {
//Học phí
                                                            if (Double.parseDouble(kh.getHocPhi()) >= finalHocPhiDuoi && Double.parseDouble(kh.getHocPhi()) <= finalHocPhiTren) {
                                                                //Giới tính
                                                                if (kh.getGioiTinh().equals(khoaHoc.getGioiTinh())) {
                                                                    for (String bangcap : kh.getBangCap()) {
                                                                        //Bằng cấp
                                                                        if (URLEncoder.encode(removeDiacriticalMarks(bangcap), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getBangCap().get(0)), "utf-8"))) {
                                                                            khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                                ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHoc(khoaHocChinhXac, khoaHocGanChinhXac);
                                            } catch (UnsupportedEncodingException e) {
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
                        } else//4 Mon == null,HocPhi!=null,GioiTinh!=Nam, Nu,BangCap==null
                        {
                            if (khoaHoc.getDiaDiem().getQuan() != "Khác") //5 Mon == null,HocPhi!=null,GioiTinh!=Nam, Nu,BangCap==null,Quan != "Khác"
                            {
                                final double finalHocPhiDuoi = hocPhiDuoi;
                                final double finalHocPhiTren = hocPhiTren;
                                final int finalCount = count;
                                mData.getReference().child("KhoaHoc").child("KhoaHocTimGiaSu").child("ChuaHoanTat").addChildEventListener(new ChildEventListener() {
                                    @Override
                                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                        int dem = 0; // Xác định độ chính xác của lịch học (if dem == finaleCount)
                                        KhoaHoc kh = dataSnapshot.getValue(KhoaHoc.class);
                                        for (String linhvuc : kh.getLinhVuc()) {
                                            //LinhVuc
                                            try {
                                                if (URLEncoder.encode(removeDiacriticalMarks(linhvuc), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getLinhVuc().get(0)), "utf-8"))) {
                                                    //Lịch học
                                                    if (finalCount != 0) //Ngày học tìm kiếm != null
                                                    {
                                                        for (String ngayHoc : khoaHoc.getLichHoc().getNgayHoc()) {
                                                            for (String ngayhoc : kh.getLichHoc().getNgayHoc()) {
                                                                if (ngayhoc.equals(ngayHoc)) {
                                                                    dem++;
                                                                    if (dem == finalCount) // Độ chính xác lịch học đạt mức chấp nhận được
                                                                    {
                                                                        khoaHocGanChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));
                                                                    }
                                                                }
                                                            }
                                                        }
                                                        if (dem >= finalCount) // Độ chính xác lịch học chấp nhận được hoặc rất chính xác
                                                        {
                                                            if (khoaHoc.getLichHoc().getThoiGian().size() != 0) // Buổi != null
                                                            {
                                                                for (String buoiHoc : khoaHoc.getLichHoc().getThoiGian()) {
                                                                    for (String buoihoc : kh.getLichHoc().getThoiGian()) {
                                                                        if (buoiHoc.equals(buoihoc)) // Buổi
                                                                        {
                                                                            //Học phí
                                                                            if (Double.parseDouble(kh.getHocPhi()) >= finalHocPhiDuoi && Double.parseDouble(kh.getHocPhi()) <= finalHocPhiTren) {
                                                                                //Giới tính
                                                                                if (kh.getGioiTinh().equals(khoaHoc.getGioiTinh())) {
                                                                                    //Địa điểm
                                                                                    if (kh.getDiaDiem().getQuan().equals(khoaHoc.getDiaDiem().getQuan())) {
                                                                                        khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));

                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            } else //Buoi = null
                                                            {
                                                                //Học phí
                                                                if (Double.parseDouble(kh.getHocPhi()) >= finalHocPhiDuoi && Double.parseDouble(kh.getHocPhi()) <= finalHocPhiTren) {
                                                                    //Giới tính
                                                                    if (kh.getGioiTinh().equals(khoaHoc.getGioiTinh())) {
                                                                        //Địa điểm
                                                                        if (kh.getDiaDiem().getQuan().equals(khoaHoc.getDiaDiem().getQuan())) {
                                                                            khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));

                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    } else//Ngày học tìm kiếm = null
                                                    {
                                                        if (khoaHoc.getLichHoc().getThoiGian().size() != 0) // Buổi != null
                                                        {
                                                            for (String buoiHoc : khoaHoc.getLichHoc().getThoiGian()) {
                                                                for (String buoihoc : kh.getLichHoc().getThoiGian()) {
                                                                    if (buoiHoc.equals(buoihoc)) // Buổi
                                                                    {
                                                                        //Học phí
                                                                        if (Double.parseDouble(kh.getHocPhi()) >= finalHocPhiDuoi && Double.parseDouble(kh.getHocPhi()) <= finalHocPhiTren) {
                                                                            //Giới tính
                                                                            if (kh.getGioiTinh().equals(khoaHoc.getGioiTinh())) {
                                                                                //Địa điểm
                                                                                if (kh.getDiaDiem().getQuan().equals(khoaHoc.getDiaDiem().getQuan())) {
                                                                                    khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));

                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        } else //Buoi = null
                                                        {
                                                            //Học phí
                                                            if (Double.parseDouble(kh.getHocPhi()) >= finalHocPhiDuoi && Double.parseDouble(kh.getHocPhi()) <= finalHocPhiTren) {
                                                                //Giới tính
                                                                if (kh.getGioiTinh().equals(khoaHoc.getGioiTinh())) {
                                                                    //Địa điểm
                                                                    if (kh.getDiaDiem().getQuan().equals(khoaHoc.getDiaDiem().getQuan())) {
                                                                        khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));

                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                                ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHoc(khoaHocChinhXac, khoaHocGanChinhXac);

                                            } catch (UnsupportedEncodingException e) {
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
                            } else //5 Mon == null,HocPhi!=null,GioiTinh!=Nam, Nu,BangCap==null, Quan = "Khác"
                            {
                                final double finalHocPhiDuoi = hocPhiDuoi;
                                final double finalHocPhiTren = hocPhiTren;
                                final int finalCount = count;
                                mData.getReference().child("KhoaHoc").child("KhoaHocTimGiaSu").child("ChuaHoanTat").addChildEventListener(new ChildEventListener() {
                                    @Override
                                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                        int dem = 0; // Xác định độ chính xác của lịch học (if dem == finaleCount)
                                        KhoaHoc kh = dataSnapshot.getValue(KhoaHoc.class);
                                        for (String linhvuc : kh.getLinhVuc()) {
                                            //LinhVuc
                                            try {
                                                if (URLEncoder.encode(removeDiacriticalMarks(linhvuc), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getLinhVuc().get(0)), "utf-8"))) {
//Lịch học
                                                    if (finalCount != 0) //Ngày học tìm kiếm != null
                                                    {
                                                        for (String ngayHoc : khoaHoc.getLichHoc().getNgayHoc()) {
                                                            for (String ngayhoc : kh.getLichHoc().getNgayHoc()) {
                                                                if (ngayhoc.equals(ngayHoc)) {
                                                                    dem++;
                                                                    if (dem == finalCount) // Độ chính xác lịch học đạt mức chấp nhận được
                                                                    {
                                                                        khoaHocGanChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));
                                                                    }
                                                                }
                                                            }
                                                        }
                                                        if (dem >= finalCount) // Độ chính xác lịch học chấp nhận được hoặc rất chính xác
                                                        {
                                                            if (khoaHoc.getLichHoc().getThoiGian().size() != 0) // Buổi != null
                                                            {
                                                                for (String buoiHoc : khoaHoc.getLichHoc().getThoiGian()) {
                                                                    for (String buoihoc : kh.getLichHoc().getThoiGian()) {
                                                                        if (buoiHoc.equals(buoihoc)) // Buổi
                                                                        {
                                                                            //Học phí
                                                                            if (Double.parseDouble(kh.getHocPhi()) >= finalHocPhiDuoi && Double.parseDouble(kh.getHocPhi()) <= finalHocPhiTren) {
                                                                                //Giới tính
                                                                                if (kh.getGioiTinh().equals(khoaHoc.getGioiTinh())) {
                                                                                    khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));

                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            } else//Buoi = null
                                                            {
                                                                //Học phí
                                                                if (Double.parseDouble(kh.getHocPhi()) >= finalHocPhiDuoi && Double.parseDouble(kh.getHocPhi()) <= finalHocPhiTren) {
                                                                    //Giới tính
                                                                    if (kh.getGioiTinh().equals(khoaHoc.getGioiTinh())) {
                                                                        khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));

                                                                    }
                                                                }
                                                            }
                                                        }
                                                    } else // Ngay = null
                                                    {
                                                        if (khoaHoc.getLichHoc().getThoiGian().size() != 0) // Buổi != null
                                                        {
                                                            for (String buoiHoc : khoaHoc.getLichHoc().getThoiGian()) {
                                                                for (String buoihoc : kh.getLichHoc().getThoiGian()) {
                                                                    if (buoiHoc.equals(buoihoc)) // Buổi
                                                                    {
                                                                        //Học phí
                                                                        if (Double.parseDouble(kh.getHocPhi()) >= finalHocPhiDuoi && Double.parseDouble(kh.getHocPhi()) <= finalHocPhiTren) {
                                                                            //Giới tính
                                                                            if (kh.getGioiTinh().equals(khoaHoc.getGioiTinh())) {
                                                                                khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));

                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        } else//Buoi = null
                                                        {
                                                            //Học phí
                                                            if (Double.parseDouble(kh.getHocPhi()) >= finalHocPhiDuoi && Double.parseDouble(kh.getHocPhi()) <= finalHocPhiTren) {
                                                                //Giới tính
                                                                if (kh.getGioiTinh().equals(khoaHoc.getGioiTinh())) {
                                                                    khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));

                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                                ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHoc(khoaHocChinhXac, khoaHocGanChinhXac);

                                            } catch (UnsupportedEncodingException e) {
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
                    } else //3 Mon == null,HocPhi!=0,GioiTinh==Nam, Nữ
                    {
                        if (khoaHoc.getBangCap() != null) //4Mon == null,HocPhi!=0,GioiTinh==Nam, Nữ,BangCap!=null
                        {
                            if (khoaHoc.getDiaDiem().getQuan() != "Khác") //5 Mon == null,HocPhi!=0,GioiTinh==Nam, Nữ,BangCap!=null,Quan != "Khác"
                            {
                                final double finalHocPhiDuoi = hocPhiDuoi;
                                final double finalHocPhiTren = hocPhiTren;
                                final int finalCount = count;
                                mData.getReference().child("KhoaHoc").child("KhoaHocTimGiaSu").child("ChuaHoanTat").addChildEventListener(new ChildEventListener() {
                                    @Override
                                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                        int dem = 0; // Xác định độ chính xác của lịch học (if dem == finaleCount)
                                        KhoaHoc kh = dataSnapshot.getValue(KhoaHoc.class);
                                        for (String linhvuc : kh.getLinhVuc()) {
                                            try {
                                                if (URLEncoder.encode(removeDiacriticalMarks(linhvuc), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getLinhVuc().get(0)), "utf-8"))) {
                                                    //Lịch học
                                                    if (finalCount != 0) //Ngày học tìm kiếm != null
                                                    {
                                                        for (String ngayHoc : khoaHoc.getLichHoc().getNgayHoc()) {
                                                            for (String ngayhoc : kh.getLichHoc().getNgayHoc()) {
                                                                if (ngayhoc.equals(ngayHoc)) {
                                                                    dem++;
                                                                    if (dem == finalCount) // Độ chính xác lịch học đạt mức chấp nhận được
                                                                    {
                                                                        khoaHocGanChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));
                                                                    }
                                                                }
                                                            }
                                                        }
                                                        if (dem >= finalCount) // Độ chính xác lịch học chấp nhận được hoặc rất chính xác
                                                        {
                                                            if (khoaHoc.getLichHoc().getThoiGian().size() != 0) // Buổi != null
                                                            {
                                                                for (String buoiHoc : khoaHoc.getLichHoc().getThoiGian()) {
                                                                    for (String buoihoc : kh.getLichHoc().getThoiGian()) {
                                                                        if (buoiHoc.equals(buoihoc)) // Buổi
                                                                        {
                                                                            //Học phí
                                                                            if (Double.parseDouble(kh.getHocPhi()) >= finalHocPhiDuoi && Double.parseDouble(kh.getHocPhi()) <= finalHocPhiTren) {
                                                                                for (String bangcap : kh.getBangCap()) {
                                                                                    //Bằng cấp
                                                                                    if (URLEncoder.encode(removeDiacriticalMarks(bangcap), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getBangCap().get(0)), "utf-8"))) {
                                                                                        //Địa điểm
                                                                                        if (kh.getDiaDiem().getQuan().equals(khoaHoc.getDiaDiem().getQuan())) {
                                                                                            khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));

                                                                                        }
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            } else //Buoi = null
                                                            {
                                                                //Học phí
                                                                if (Double.parseDouble(kh.getHocPhi()) >= finalHocPhiDuoi && Double.parseDouble(kh.getHocPhi()) <= finalHocPhiTren) {
                                                                    for (String bangcap : kh.getBangCap()) {
                                                                        //Bằng cấp
                                                                        if (URLEncoder.encode(removeDiacriticalMarks(bangcap), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getBangCap().get(0)), "utf-8"))) {
                                                                            //Địa điểm
                                                                            if (kh.getDiaDiem().getQuan().equals(khoaHoc.getDiaDiem().getQuan())) {
                                                                                khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));

                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    } else//Ngauy = null
                                                    {
                                                        if (khoaHoc.getLichHoc().getThoiGian().size() != 0) // Buổi != null
                                                        {
                                                            for (String buoiHoc : khoaHoc.getLichHoc().getThoiGian()) {
                                                                for (String buoihoc : kh.getLichHoc().getThoiGian()) {
                                                                    if (buoiHoc.equals(buoihoc)) // Buổi
                                                                    {
                                                                        //Học phí
                                                                        if (Double.parseDouble(kh.getHocPhi()) >= finalHocPhiDuoi && Double.parseDouble(kh.getHocPhi()) <= finalHocPhiTren) {
                                                                            for (String bangcap : kh.getBangCap()) {
                                                                                //Bằng cấp
                                                                                if (URLEncoder.encode(removeDiacriticalMarks(bangcap), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getBangCap().get(0)), "utf-8"))) {
                                                                                    //Địa điểm
                                                                                    if (kh.getDiaDiem().getQuan().equals(khoaHoc.getDiaDiem().getQuan())) {
                                                                                        khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));

                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        } else //Buoi = null
                                                        {
                                                            //Học phí
                                                            if (Double.parseDouble(kh.getHocPhi()) >= finalHocPhiDuoi && Double.parseDouble(kh.getHocPhi()) <= finalHocPhiTren) {
                                                                for (String bangcap : kh.getBangCap()) {
                                                                    //Bằng cấp
                                                                    if (URLEncoder.encode(removeDiacriticalMarks(bangcap), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getBangCap().get(0)), "utf-8"))) {
                                                                        //Địa điểm
                                                                        if (kh.getDiaDiem().getQuan().equals(khoaHoc.getDiaDiem().getQuan())) {
                                                                            khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));

                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                                ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHoc(khoaHocChinhXac, khoaHocGanChinhXac);

                                            } catch (UnsupportedEncodingException e) {
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
                            } else //5 Mon == null,HocPhi!=0,GioiTinh==Nam, Nữ,BangCap!=null, Quan = "Khác"
                            {
                                final double finalHocPhiDuoi = hocPhiDuoi;
                                final double finalHocPhiTren = hocPhiTren;
                                final int finalCount = count;
                                mData.getReference().child("KhoaHoc").child("KhoaHocTimGiaSu").child("ChuaHoanTat").addChildEventListener(new ChildEventListener() {
                                    @Override
                                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                        int dem = 0; // Xác định độ chính xác của lịch học (if dem == finaleCount)
                                        KhoaHoc kh = dataSnapshot.getValue(KhoaHoc.class);
                                        for (String linhvuc : kh.getLinhVuc()) {
                                            //LinhVuc
                                            try {
                                                if (URLEncoder.encode(removeDiacriticalMarks(linhvuc), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getLinhVuc().get(0)), "utf-8"))) {
//Lịch học
                                                    if (finalCount != 0) //Ngày học tìm kiếm != null
                                                    {
                                                        for (String ngayHoc : khoaHoc.getLichHoc().getNgayHoc()) {
                                                            for (String ngayhoc : kh.getLichHoc().getNgayHoc()) {
                                                                if (ngayhoc.equals(ngayHoc)) {
                                                                    dem++;
                                                                    if (dem == finalCount) // Độ chính xác lịch học đạt mức chấp nhận được
                                                                    {
                                                                        khoaHocGanChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));
                                                                    }
                                                                }
                                                            }
                                                        }
                                                        if (dem >= finalCount) // Độ chính xác lịch học chấp nhận được hoặc rất chính xác
                                                        {
                                                            if (khoaHoc.getLichHoc().getThoiGian().size() != 0) // Buổi != null
                                                            {
                                                                for (String buoiHoc : khoaHoc.getLichHoc().getThoiGian()) {
                                                                    for (String buoihoc : kh.getLichHoc().getThoiGian()) {
                                                                        if (buoiHoc.equals(buoihoc)) // Buổi
                                                                        {
                                                                            //Học phí
                                                                            if (Double.parseDouble(kh.getHocPhi()) >= finalHocPhiDuoi && Double.parseDouble(kh.getHocPhi()) <= finalHocPhiTren) {
                                                                                for (String bangcap : kh.getBangCap()) {
                                                                                    //Bằng cấp
                                                                                    if (URLEncoder.encode(removeDiacriticalMarks(bangcap), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getBangCap().get(0)), "utf-8"))) {
                                                                                        khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));

                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            } else //Buoi ==null
                                                            {
//Học phí
                                                                if (Double.parseDouble(kh.getHocPhi()) >= finalHocPhiDuoi && Double.parseDouble(kh.getHocPhi()) <= finalHocPhiTren) {
                                                                    for (String bangcap : kh.getBangCap()) {
                                                                        //Bằng cấp
                                                                        if (URLEncoder.encode(removeDiacriticalMarks(bangcap), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getBangCap().get(0)), "utf-8"))) {
                                                                            khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));

                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    } else //Ngay ==null
                                                    {
                                                        if (khoaHoc.getLichHoc().getThoiGian().size() != 0) // Buổi != null
                                                        {
                                                            for (String buoiHoc : khoaHoc.getLichHoc().getThoiGian()) {
                                                                for (String buoihoc : kh.getLichHoc().getThoiGian()) {
                                                                    if (buoiHoc.equals(buoihoc)) // Buổi
                                                                    {
                                                                        //Học phí
                                                                        if (Double.parseDouble(kh.getHocPhi()) >= finalHocPhiDuoi && Double.parseDouble(kh.getHocPhi()) <= finalHocPhiTren) {
                                                                            for (String bangcap : kh.getBangCap()) {
                                                                                //Bằng cấp
                                                                                if (URLEncoder.encode(removeDiacriticalMarks(bangcap), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getBangCap().get(0)), "utf-8"))) {
                                                                                    khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));

                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        } else //Buoi ==null
                                                        {
//Học phí
                                                            if (Double.parseDouble(kh.getHocPhi()) >= finalHocPhiDuoi && Double.parseDouble(kh.getHocPhi()) <= finalHocPhiTren) {
                                                                for (String bangcap : kh.getBangCap()) {
                                                                    //Bằng cấp
                                                                    if (URLEncoder.encode(removeDiacriticalMarks(bangcap), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getBangCap().get(0)), "utf-8"))) {
                                                                        khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));

                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                                ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHoc(khoaHocChinhXac, khoaHocGanChinhXac);

                                            } catch (UnsupportedEncodingException e) {
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
                        } else//4 Mon == null,HocPhi!=0,GioiTinh==Nam, Nữ,BangCap==null
                        {
                            if (khoaHoc.getDiaDiem().getQuan() != "Khác") //5 Mon == null,HocPhi!=0,GioiTinh==Nam, Nữ,BangCap==null,Quan != "Khác"
                            {
                                final double finalHocPhiDuoi = hocPhiDuoi;
                                final double finalHocPhiTren = hocPhiTren;
                                final int finalCount = count;
                                mData.getReference().child("KhoaHoc").child("KhoaHocTimGiaSu").child("ChuaHoanTat").addChildEventListener(new ChildEventListener() {
                                    @Override
                                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                        int dem = 0; // Xác định độ chính xác của lịch học (if dem == finaleCount)
                                        KhoaHoc kh = dataSnapshot.getValue(KhoaHoc.class);
                                        for (String linhvuc : kh.getLinhVuc()) {
                                            //LinhVuc
                                            try {
                                                if (URLEncoder.encode(removeDiacriticalMarks(linhvuc), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getLinhVuc().get(0)), "utf-8"))) {
                                                    //Lịch học
                                                    if (finalCount != 0) //Ngày học tìm kiếm != null
                                                    {
                                                        for (String ngayHoc : khoaHoc.getLichHoc().getNgayHoc()) {
                                                            for (String ngayhoc : kh.getLichHoc().getNgayHoc()) {
                                                                if (ngayhoc.equals(ngayHoc)) {
                                                                    dem++;
                                                                    if (dem == finalCount) // Độ chính xác lịch học đạt mức chấp nhận được
                                                                    {
                                                                        khoaHocGanChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));
                                                                    }
                                                                }
                                                            }
                                                        }
                                                        if (dem >= finalCount) // Độ chính xác lịch học chấp nhận được hoặc rất chính xác
                                                        {
                                                            if (khoaHoc.getLichHoc().getThoiGian().size() != 0) // Buổi != null
                                                            {
                                                                for (String buoiHoc : khoaHoc.getLichHoc().getThoiGian()) {
                                                                    for (String buoihoc : kh.getLichHoc().getThoiGian()) {
                                                                        if (buoiHoc.equals(buoihoc)) // Buổi
                                                                        {
                                                                            //Học phí
                                                                            if (Double.parseDouble(kh.getHocPhi()) >= finalHocPhiDuoi && Double.parseDouble(kh.getHocPhi()) <= finalHocPhiTren) {
                                                                                //Địa điểm
                                                                                if (kh.getDiaDiem().getQuan().equals(khoaHoc.getDiaDiem().getQuan())) {
                                                                                    khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));

                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            } else//Buoi ==null
                                                            {
//Học phí
                                                                if (Double.parseDouble(kh.getHocPhi()) >= finalHocPhiDuoi && Double.parseDouble(kh.getHocPhi()) <= finalHocPhiTren) {
                                                                    //Địa điểm
                                                                    if (kh.getDiaDiem().getQuan().equals(khoaHoc.getDiaDiem().getQuan())) {
                                                                        khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));

                                                                    }
                                                                }
                                                            }
                                                        }
                                                    } else//Ngay = null
                                                    {
                                                        if (khoaHoc.getLichHoc().getThoiGian().size() != 0) // Buổi != null
                                                        {
                                                            for (String buoiHoc : khoaHoc.getLichHoc().getThoiGian()) {
                                                                for (String buoihoc : kh.getLichHoc().getThoiGian()) {
                                                                    if (buoiHoc.equals(buoihoc)) // Buổi
                                                                    {
                                                                        //Học phí
                                                                        if (Double.parseDouble(kh.getHocPhi()) >= finalHocPhiDuoi && Double.parseDouble(kh.getHocPhi()) <= finalHocPhiTren) {
                                                                            //Địa điểm
                                                                            if (kh.getDiaDiem().getQuan().equals(khoaHoc.getDiaDiem().getQuan())) {
                                                                                khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));

                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        } else//Buoi ==null
                                                        {
//Học phí
                                                            if (Double.parseDouble(kh.getHocPhi()) >= finalHocPhiDuoi && Double.parseDouble(kh.getHocPhi()) <= finalHocPhiTren) {
                                                                //Địa điểm
                                                                if (kh.getDiaDiem().getQuan().equals(khoaHoc.getDiaDiem().getQuan())) {
                                                                    khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));

                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                                ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHoc(khoaHocChinhXac, khoaHocGanChinhXac);
                                            } catch (UnsupportedEncodingException e) {
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
                            } else //5 Mon == null,HocPhi!=0,GioiTinh==Nam, Nữ,BangCap==null, Quan = "Khác"
                            {
                                final double finalHocPhiDuoi = hocPhiDuoi;
                                final double finalHocPhiTren = hocPhiTren;
                                final int finalCount = count;
                                mData.getReference().child("KhoaHoc").child("KhoaHocTimGiaSu").child("ChuaHoanTat").addChildEventListener(new ChildEventListener() {
                                    @Override
                                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                        int dem = 0; // Xác định độ chính xác của lịch học (if dem == finaleCount)
                                        KhoaHoc kh = dataSnapshot.getValue(KhoaHoc.class);
                                        for (String linhvuc : kh.getLinhVuc()) {
                                            //LinhVuc
                                            try {
                                                if (URLEncoder.encode(removeDiacriticalMarks(linhvuc), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getLinhVuc().get(0)), "utf-8"))) {
                                                    //Lịch học
                                                    if (finalCount != 0) //Ngày học tìm kiếm != null
                                                    {
                                                        for (String ngayHoc : khoaHoc.getLichHoc().getNgayHoc()) {
                                                            for (String ngayhoc : kh.getLichHoc().getNgayHoc()) {
                                                                if (ngayhoc.equals(ngayHoc)) {
                                                                    dem++;
                                                                    if (dem == finalCount) // Độ chính xác lịch học đạt mức chấp nhận được
                                                                    {
                                                                        khoaHocGanChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));
                                                                    }
                                                                }
                                                            }
                                                        }
                                                        if (dem >= finalCount) // Độ chính xác lịch học chấp nhận được hoặc rất chính xác
                                                        {
                                                            if (khoaHoc.getLichHoc().getThoiGian().size() != 0) // Buổi != null
                                                            {
                                                                for (String buoiHoc : khoaHoc.getLichHoc().getThoiGian()) {
                                                                    for (String buoihoc : kh.getLichHoc().getThoiGian()) {
                                                                        if (buoiHoc.equals(buoihoc)) // Buổi
                                                                        {
                                                                            //Học phí
                                                                            if (Double.parseDouble(kh.getHocPhi()) >= finalHocPhiDuoi && Double.parseDouble(kh.getHocPhi()) <= finalHocPhiTren) {
                                                                                khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));

                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            } else//Buoi = null
                                                            {
//Học phí
                                                                if (Double.parseDouble(kh.getHocPhi()) >= finalHocPhiDuoi && Double.parseDouble(kh.getHocPhi()) <= finalHocPhiTren) {
                                                                    khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));

                                                                }
                                                            }
                                                        }
                                                    } else//Ngay == null
                                                    {
                                                        if (khoaHoc.getLichHoc().getThoiGian().size() != 0) // Buổi != null
                                                        {
                                                            for (String buoiHoc : khoaHoc.getLichHoc().getThoiGian()) {
                                                                for (String buoihoc : kh.getLichHoc().getThoiGian()) {
                                                                    if (buoiHoc.equals(buoihoc)) // Buổi
                                                                    {
                                                                        //Học phí
                                                                        if (Double.parseDouble(kh.getHocPhi()) >= finalHocPhiDuoi && Double.parseDouble(kh.getHocPhi()) <= finalHocPhiTren) {
                                                                            khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));

                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        } else//Buoi = null
                                                        {
//Học phí
                                                            if (Double.parseDouble(kh.getHocPhi()) >= finalHocPhiDuoi && Double.parseDouble(kh.getHocPhi()) <= finalHocPhiTren) {
                                                                khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));

                                                            }
                                                        }
                                                    }
                                                }
                                                ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHoc(khoaHocChinhXac, khoaHocGanChinhXac);

                                            } catch (UnsupportedEncodingException e) {
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
                } else//2 Mon == null, HocPhi==0
                {
                    if (khoaHoc.getGioiTinh() != "Nam, Nữ") //3 Mon == null, HocPhi==0,GioiTinh!=Nam, Nữ
                    {
                        if (khoaHoc.getBangCap() != null) //4 Mon == null, HocPhi==0,GioiTinh!=Nam, Nu,BangCap!=null
                        {
                            if (khoaHoc.getDiaDiem().getQuan() != "Khác") //5 Mon == null, HocPhi==0,GioiTinh!=Nam, Nu,BangCap!=null,Quan != "Khác"
                            {
                                final double finalHocPhiDuoi = hocPhiDuoi;
                                final double finalHocPhiTren = hocPhiTren;
                                final int finalCount = count;
                                mData.getReference().child("KhoaHoc").child("KhoaHocTimGiaSu").child("ChuaHoanTat").addChildEventListener(new ChildEventListener() {
                                    @Override
                                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                        int dem = 0; // Xác định độ chính xác của lịch học (if dem == finaleCount)
                                        KhoaHoc kh = dataSnapshot.getValue(KhoaHoc.class);
                                        for (String linhvuc : kh.getLinhVuc()) {
                                            //LinhVuc
                                            try {
                                                if (URLEncoder.encode(removeDiacriticalMarks(linhvuc), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getLinhVuc().get(0)), "utf-8"))) {
//Lịch học
                                                    if (finalCount != 0) //Ngày học tìm kiếm != null
                                                    {
                                                        for (String ngayHoc : khoaHoc.getLichHoc().getNgayHoc()) {
                                                            for (String ngayhoc : kh.getLichHoc().getNgayHoc()) {
                                                                if (ngayhoc.equals(ngayHoc)) {
                                                                    dem++;
                                                                    if (dem == finalCount) // Độ chính xác lịch học đạt mức chấp nhận được
                                                                    {
                                                                        khoaHocGanChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));
                                                                    }
                                                                }
                                                            }
                                                        }
                                                        if (dem >= finalCount) // Độ chính xác lịch học chấp nhận được hoặc rất chính xác
                                                        {
                                                            if (khoaHoc.getLichHoc().getThoiGian().size() != 0) // Buổi != null
                                                            {
                                                                for (String buoiHoc : khoaHoc.getLichHoc().getThoiGian()) {
                                                                    for (String buoihoc : kh.getLichHoc().getThoiGian()) {
                                                                        if (buoiHoc.equals(buoihoc)) // Buổi
                                                                        {
                                                                            //Giới tính
                                                                            if (kh.getGioiTinh().equals(khoaHoc.getGioiTinh())) {
                                                                                for (String bangcap : kh.getBangCap()) {
                                                                                    //Bằng cấp
                                                                                    if (URLEncoder.encode(removeDiacriticalMarks(bangcap), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getBangCap().get(0)), "utf-8"))) {
                                                                                        //Địa điểm
                                                                                        if (kh.getDiaDiem().getQuan().equals(khoaHoc.getDiaDiem().getQuan())) {
                                                                                            khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));

                                                                                        }
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            } else //Buoi == null
                                                            {
//Giới tính
                                                                if (kh.getGioiTinh().equals(khoaHoc.getGioiTinh())) {
                                                                    for (String bangcap : kh.getBangCap()) {
                                                                        //Bằng cấp
                                                                        if (URLEncoder.encode(removeDiacriticalMarks(bangcap), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getBangCap().get(0)), "utf-8"))) {
                                                                            //Địa điểm
                                                                            if (kh.getDiaDiem().getQuan().equals(khoaHoc.getDiaDiem().getQuan())) {
                                                                                khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));

                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    } else //Ngay == null
                                                    {
                                                        if (khoaHoc.getLichHoc().getThoiGian().size() != 0) // Buổi != null
                                                        {
                                                            for (String buoiHoc : khoaHoc.getLichHoc().getThoiGian()) {
                                                                for (String buoihoc : kh.getLichHoc().getThoiGian()) {
                                                                    if (buoiHoc.equals(buoihoc)) // Buổi
                                                                    {
                                                                        //Giới tính
                                                                        if (kh.getGioiTinh().equals(khoaHoc.getGioiTinh())) {
                                                                            for (String bangcap : kh.getBangCap()) {
                                                                                //Bằng cấp
                                                                                if (URLEncoder.encode(removeDiacriticalMarks(bangcap), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getBangCap().get(0)), "utf-8"))) {
                                                                                    //Địa điểm
                                                                                    if (kh.getDiaDiem().getQuan().equals(khoaHoc.getDiaDiem().getQuan())) {
                                                                                        khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));

                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        } else //Buoi == null
                                                        {
//Giới tính
                                                            if (kh.getGioiTinh().equals(khoaHoc.getGioiTinh())) {
                                                                for (String bangcap : kh.getBangCap()) {
                                                                    //Bằng cấp
                                                                    if (URLEncoder.encode(removeDiacriticalMarks(bangcap), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getBangCap().get(0)), "utf-8"))) {
                                                                        //Địa điểm
                                                                        if (kh.getDiaDiem().getQuan().equals(khoaHoc.getDiaDiem().getQuan())) {
                                                                            khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));

                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                                ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHoc(khoaHocChinhXac, khoaHocGanChinhXac);

                                            } catch (UnsupportedEncodingException e) {
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
                            } else //5 Mon == null, HocPhi==0,GioiTinh!=Nam, Nu,BangCap!=null, Quan = "Khác"
                            {
                                final double finalHocPhiDuoi = hocPhiDuoi;
                                final double finalHocPhiTren = hocPhiTren;
                                final int finalCount = count;
                                mData.getReference().child("KhoaHoc").child("KhoaHocTimGiaSu").child("ChuaHoanTat").addChildEventListener(new ChildEventListener() {
                                    @Override
                                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                        int dem = 0; // Xác định độ chính xác của lịch học (if dem == finaleCount)
                                        KhoaHoc kh = dataSnapshot.getValue(KhoaHoc.class);
                                        for (String linhvuc : kh.getLinhVuc()) {
                                            //LinhVuc
                                            try {
                                                if (URLEncoder.encode(removeDiacriticalMarks(linhvuc), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getLinhVuc().get(0)), "utf-8"))) {
//Lịch học
                                                    if (finalCount != 0) //Ngày học tìm kiếm != null
                                                    {
                                                        for (String ngayHoc : khoaHoc.getLichHoc().getNgayHoc()) {
                                                            for (String ngayhoc : kh.getLichHoc().getNgayHoc()) {
                                                                if (ngayhoc.equals(ngayHoc)) {
                                                                    dem++;
                                                                    if (dem == finalCount) // Độ chính xác lịch học đạt mức chấp nhận được
                                                                    {
                                                                        khoaHocGanChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));
                                                                    }
                                                                }
                                                            }
                                                        }
                                                        if (dem >= finalCount) // Độ chính xác lịch học chấp nhận được hoặc rất chính xác
                                                        {
                                                            if (khoaHoc.getLichHoc().getThoiGian().size() != 0) // Buổi != null
                                                            {
                                                                for (String buoiHoc : khoaHoc.getLichHoc().getThoiGian()) {
                                                                    for (String buoihoc : kh.getLichHoc().getThoiGian()) {
                                                                        if (buoiHoc.equals(buoihoc)) // Buổi
                                                                        {
                                                                            //Giới tính
                                                                            if (kh.getGioiTinh().equals(khoaHoc.getGioiTinh())) {
                                                                                for (String bangcap : kh.getBangCap()) {
                                                                                    //Bằng cấp
                                                                                    if (URLEncoder.encode(removeDiacriticalMarks(bangcap), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getBangCap().get(0)), "utf-8"))) {
                                                                                        khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));

                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            } else //Buoi == null
                                                            {
//Giới tính
                                                                if (kh.getGioiTinh().equals(khoaHoc.getGioiTinh())) {
                                                                    for (String bangcap : kh.getBangCap()) {
                                                                        //Bằng cấp
                                                                        if (URLEncoder.encode(removeDiacriticalMarks(bangcap), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getBangCap().get(0)), "utf-8"))) {
                                                                            khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));

                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    } else //Ngay == null
                                                    {
                                                        if (khoaHoc.getLichHoc().getThoiGian().size() != 0) // Buổi != null
                                                        {
                                                            for (String buoiHoc : khoaHoc.getLichHoc().getThoiGian()) {
                                                                for (String buoihoc : kh.getLichHoc().getThoiGian()) {
                                                                    if (buoiHoc.equals(buoihoc)) // Buổi
                                                                    {
                                                                        //Giới tính
                                                                        if (kh.getGioiTinh().equals(khoaHoc.getGioiTinh())) {
                                                                            for (String bangcap : kh.getBangCap()) {
                                                                                //Bằng cấp
                                                                                if (URLEncoder.encode(removeDiacriticalMarks(bangcap), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getBangCap().get(0)), "utf-8"))) {
                                                                                    khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));

                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        } else //Buoi == null
                                                        {
//Giới tính
                                                            if (kh.getGioiTinh().equals(khoaHoc.getGioiTinh())) {
                                                                for (String bangcap : kh.getBangCap()) {
                                                                    //Bằng cấp
                                                                    if (URLEncoder.encode(removeDiacriticalMarks(bangcap), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getBangCap().get(0)), "utf-8"))) {
                                                                        khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));

                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                                ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHoc(khoaHocChinhXac, khoaHocGanChinhXac);

                                            } catch (UnsupportedEncodingException e) {
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
                        } else//4 Mon == null, HocPhi==0,GioiTinh!=Nam, Nu,BangCap==null
                        {
                            if (khoaHoc.getDiaDiem().getQuan() != "Khác") //5 Mon == null, HocPhi==0,GioiTinh!=Nam, Nu,BangCap==null,Quan != "Khác"
                            {
                                final double finalHocPhiDuoi = hocPhiDuoi;
                                final double finalHocPhiTren = hocPhiTren;
                                final int finalCount = count;
                                mData.getReference().child("KhoaHoc").child("KhoaHocTimGiaSu").child("ChuaHoanTat").addChildEventListener(new ChildEventListener() {
                                    @Override
                                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                        int dem = 0; // Xác định độ chính xác của lịch học (if dem == finaleCount)
                                        KhoaHoc kh = dataSnapshot.getValue(KhoaHoc.class);
                                        for (String linhvuc : kh.getLinhVuc()) {
                                            //LinhVuc
                                            try {
                                                if (URLEncoder.encode(removeDiacriticalMarks(linhvuc), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getLinhVuc().get(0)), "utf-8"))) {
                                                    //Lịch học
                                                    if (finalCount != 0) //Ngày học tìm kiếm != null
                                                    {
                                                        for (String ngayHoc : khoaHoc.getLichHoc().getNgayHoc()) {
                                                            for (String ngayhoc : kh.getLichHoc().getNgayHoc()) {
                                                                if (ngayhoc.equals(ngayHoc)) {
                                                                    dem++;
                                                                    if (dem == finalCount) // Độ chính xác lịch học đạt mức chấp nhận được
                                                                    {
                                                                        khoaHocGanChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));
                                                                    }
                                                                }
                                                            }
                                                        }
                                                        if (dem >= finalCount) // Độ chính xác lịch học chấp nhận được hoặc rất chính xác
                                                        {
                                                            if (khoaHoc.getLichHoc().getThoiGian().size() != 0) // Buổi != null
                                                            {
                                                                for (String buoiHoc : khoaHoc.getLichHoc().getThoiGian()) {
                                                                    for (String buoihoc : kh.getLichHoc().getThoiGian()) {
                                                                        if (buoiHoc.equals(buoihoc)) // Buổi
                                                                        {
                                                                            //Giới tính
                                                                            if (kh.getGioiTinh().equals(khoaHoc.getGioiTinh())) {
                                                                                //Địa điểm
                                                                                if (kh.getDiaDiem().getQuan().equals(khoaHoc.getDiaDiem().getQuan())) {
                                                                                    khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));

                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            } else //Buoi == null
                                                            {
//Giới tính
                                                                if (kh.getGioiTinh().equals(khoaHoc.getGioiTinh())) {
                                                                    //Địa điểm
                                                                    if (kh.getDiaDiem().getQuan().equals(khoaHoc.getDiaDiem().getQuan())) {
                                                                        khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));

                                                                    }
                                                                }
                                                            }
                                                        }
                                                    } else // Ngay == null
                                                    {
                                                        if (khoaHoc.getLichHoc().getThoiGian().size() != 0) // Buổi != null
                                                        {
                                                            for (String buoiHoc : khoaHoc.getLichHoc().getThoiGian()) {
                                                                for (String buoihoc : kh.getLichHoc().getThoiGian()) {
                                                                    if (buoiHoc.equals(buoihoc)) // Buổi
                                                                    {
                                                                        //Giới tính
                                                                        if (kh.getGioiTinh().equals(khoaHoc.getGioiTinh())) {
                                                                            //Địa điểm
                                                                            if (kh.getDiaDiem().getQuan().equals(khoaHoc.getDiaDiem().getQuan())) {
                                                                                khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));

                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        } else //Buoi == null
                                                        {
//Giới tính
                                                            if (kh.getGioiTinh().equals(khoaHoc.getGioiTinh())) {
                                                                //Địa điểm
                                                                if (kh.getDiaDiem().getQuan().equals(khoaHoc.getDiaDiem().getQuan())) {
                                                                    khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));

                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                                ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHoc(khoaHocChinhXac, khoaHocGanChinhXac);

                                            } catch (UnsupportedEncodingException e) {
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
                            } else //5 Mon == null, HocPhi==0,GioiTinh!=Nam, Nu,BangCap==null, Quan = "Khác"
                            {
                                final double finalHocPhiDuoi = hocPhiDuoi;
                                final double finalHocPhiTren = hocPhiTren;
                                final int finalCount = count;
                                mData.getReference().child("KhoaHoc").child("KhoaHocTimGiaSu").child("ChuaHoanTat").addChildEventListener(new ChildEventListener() {
                                    @Override
                                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                        int dem = 0; // Xác định độ chính xác của lịch học (if dem == finaleCount)
                                        KhoaHoc kh = dataSnapshot.getValue(KhoaHoc.class);
                                        for (String linhvuc : kh.getLinhVuc()) {
                                            try {
                                                if (URLEncoder.encode(removeDiacriticalMarks(linhvuc), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getLinhVuc().get(0)), "utf-8"))) {
//Lịch học
                                                    if (finalCount != 0) //Ngày học tìm kiếm != null
                                                    {
                                                        for (String ngayHoc : khoaHoc.getLichHoc().getNgayHoc()) {
                                                            for (String ngayhoc : kh.getLichHoc().getNgayHoc()) {
                                                                if (ngayhoc.equals(ngayHoc)) {
                                                                    dem++;
                                                                    if (dem == finalCount) // Độ chính xác lịch học đạt mức chấp nhận được
                                                                    {
                                                                        khoaHocGanChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));
                                                                    }
                                                                }
                                                            }
                                                        }
                                                        if (dem >= finalCount) // Độ chính xác lịch học chấp nhận được hoặc rất chính xác
                                                        {
                                                            if (khoaHoc.getLichHoc().getThoiGian().size() != 0) // Buổi != null
                                                            {
                                                                for (String buoiHoc : khoaHoc.getLichHoc().getThoiGian()) {
                                                                    for (String buoihoc : kh.getLichHoc().getThoiGian()) {
                                                                        if (buoiHoc.equals(buoihoc)) // Buổi
                                                                        {
                                                                            //Giới tính
                                                                            if (kh.getGioiTinh().equals(khoaHoc.getGioiTinh())) {
                                                                                khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));

                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            } else //Buoi == null
                                                            {
//Giới tính
                                                                if (kh.getGioiTinh().equals(khoaHoc.getGioiTinh())) {
                                                                    khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));

                                                                }
                                                            }
                                                        }
                                                    } else //Ngay == null
                                                    {
                                                        if (khoaHoc.getLichHoc().getThoiGian().size() != 0) // Buổi != null
                                                        {
                                                            for (String buoiHoc : khoaHoc.getLichHoc().getThoiGian()) {
                                                                for (String buoihoc : kh.getLichHoc().getThoiGian()) {
                                                                    if (buoiHoc.equals(buoihoc)) // Buổi
                                                                    {
                                                                        //Giới tính
                                                                        if (kh.getGioiTinh().equals(khoaHoc.getGioiTinh())) {
                                                                            khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));

                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        } else //Buoi == null
                                                        {
//Giới tính
                                                            if (kh.getGioiTinh().equals(khoaHoc.getGioiTinh())) {
                                                                khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));

                                                            }
                                                        }
                                                    }
                                                }
                                                ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHoc(khoaHocChinhXac, khoaHocGanChinhXac);

                                            } catch (UnsupportedEncodingException e) {
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
                    } else //3 Mon == null, HocPhi==0,GioiTinh==Nam, Nữ
                    {
                        if (khoaHoc.getBangCap() != null) //4 Mon == null, HocPhi==0,GioiTinh==Nam, Nữ,BangCap!=null
                        {
                            if (khoaHoc.getDiaDiem().getQuan() != "Khác") //5 Mon == null, HocPhi==0,GioiTinh==Nam, Nữ,BangCap!=null,Quan != "Khác"
                            {
                                final double finalHocPhiDuoi = hocPhiDuoi;
                                final double finalHocPhiTren = hocPhiTren;
                                final int finalCount = count;
                                mData.getReference().child("KhoaHoc").child("KhoaHocTimGiaSu").child("ChuaHoanTat").addChildEventListener(new ChildEventListener() {
                                    @Override
                                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                        int dem = 0; // Xác định độ chính xác của lịch học (if dem == finaleCount)
                                        KhoaHoc kh = dataSnapshot.getValue(KhoaHoc.class);
                                        for (String linhvuc : kh.getLinhVuc()) {
                                            //LinhVuc
                                            try {
                                                if (URLEncoder.encode(removeDiacriticalMarks(linhvuc), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getLinhVuc().get(0)), "utf-8"))) {
//Lịch học
                                                    if (finalCount != 0) //Ngày học tìm kiếm != null
                                                    {
                                                        for (String ngayHoc : khoaHoc.getLichHoc().getNgayHoc()) {
                                                            for (String ngayhoc : kh.getLichHoc().getNgayHoc()) {
                                                                if (ngayhoc.equals(ngayHoc)) {
                                                                    dem++;
                                                                    if (dem == finalCount) // Độ chính xác lịch học đạt mức chấp nhận được
                                                                    {
                                                                        khoaHocGanChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));
                                                                    }
                                                                }
                                                            }
                                                        }
                                                        if (dem >= finalCount) // Độ chính xác lịch học chấp nhận được hoặc rất chính xác
                                                        {
                                                            if (khoaHoc.getLichHoc().getThoiGian().size() != 0) // Buổi != null
                                                            {
                                                                for (String buoiHoc : khoaHoc.getLichHoc().getThoiGian()) {
                                                                    for (String buoihoc : kh.getLichHoc().getThoiGian()) {
                                                                        if (buoiHoc.equals(buoihoc)) // Buổi
                                                                        {
                                                                            for (String bangcap : kh.getBangCap()) {
                                                                                //Bằng cấp
                                                                                if (URLEncoder.encode(removeDiacriticalMarks(bangcap), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getBangCap().get(0)), "utf-8"))) {
                                                                                    //Địa điểm
                                                                                    if (kh.getDiaDiem().getQuan().equals(khoaHoc.getDiaDiem().getQuan())) {
                                                                                        khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));

                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            } else //Buoi == null
                                                            {
                                                                for (String bangcap : kh.getBangCap()) {
                                                                    //Bằng cấp
                                                                    if (URLEncoder.encode(removeDiacriticalMarks(bangcap), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getBangCap().get(0)), "utf-8"))) {
                                                                        //Địa điểm
                                                                        if (kh.getDiaDiem().getQuan().equals(khoaHoc.getDiaDiem().getQuan())) {
                                                                            khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));

                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    } else //Ngay == null
                                                    {
                                                        if (khoaHoc.getLichHoc().getThoiGian().size() != 0) // Buổi != null
                                                        {
                                                            for (String buoiHoc : khoaHoc.getLichHoc().getThoiGian()) {
                                                                for (String buoihoc : kh.getLichHoc().getThoiGian()) {
                                                                    if (buoiHoc.equals(buoihoc)) // Buổi
                                                                    {
                                                                        for (String bangcap : kh.getBangCap()) {
                                                                            //Bằng cấp
                                                                            if (URLEncoder.encode(removeDiacriticalMarks(bangcap), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getBangCap().get(0)), "utf-8"))) {
                                                                                //Địa điểm
                                                                                if (kh.getDiaDiem().getQuan().equals(khoaHoc.getDiaDiem().getQuan())) {
                                                                                    khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));

                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        } else //Buoi == null
                                                        {
                                                            for (String bangcap : kh.getBangCap()) {
                                                                //Bằng cấp
                                                                if (URLEncoder.encode(removeDiacriticalMarks(bangcap), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getBangCap().get(0)), "utf-8"))) {
                                                                    //Địa điểm
                                                                    if (kh.getDiaDiem().getQuan().equals(khoaHoc.getDiaDiem().getQuan())) {
                                                                        khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));

                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                                ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHoc(khoaHocChinhXac, khoaHocGanChinhXac);

                                            } catch (UnsupportedEncodingException e) {
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
                            } else //5 Mon == null, HocPhi==0,GioiTinh==Nam, Nữ,BangCap!=null, Quan = "Khác"
                            {
                                final double finalHocPhiDuoi = hocPhiDuoi;
                                final double finalHocPhiTren = hocPhiTren;
                                final int finalCount = count;
                                mData.getReference().child("KhoaHoc").child("KhoaHocTimGiaSu").child("ChuaHoanTat").addChildEventListener(new ChildEventListener() {
                                    @Override
                                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                        int dem = 0; // Xác định độ chính xác của lịch học (if dem == finaleCount)
                                        KhoaHoc kh = dataSnapshot.getValue(KhoaHoc.class);
                                        for (String linhvuc : kh.getLinhVuc()) {
                                            //LinhVuc
                                            try {
                                                if (URLEncoder.encode(removeDiacriticalMarks(linhvuc), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getLinhVuc().get(0)), "utf-8"))) {
//Lịch học
                                                    if (finalCount != 0) //Ngày học tìm kiếm != null
                                                    {
                                                        for (String ngayHoc : khoaHoc.getLichHoc().getNgayHoc()) {
                                                            for (String ngayhoc : kh.getLichHoc().getNgayHoc()) {
                                                                if (ngayhoc.equals(ngayHoc)) {
                                                                    dem++;
                                                                    if (dem == finalCount) // Độ chính xác lịch học đạt mức chấp nhận được
                                                                    {
                                                                        khoaHocGanChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));
                                                                    }
                                                                }
                                                            }
                                                        }
                                                        if (dem >= finalCount) // Độ chính xác lịch học chấp nhận được hoặc rất chính xác
                                                        {
                                                            if (khoaHoc.getLichHoc().getThoiGian().size() != 0) // Buổi != null
                                                            {
                                                                for (String buoiHoc : khoaHoc.getLichHoc().getThoiGian()) {
                                                                    for (String buoihoc : kh.getLichHoc().getThoiGian()) {
                                                                        if (buoiHoc.equals(buoihoc)) // Buổi
                                                                        {
                                                                            for (String bangcap : kh.getBangCap()) {
                                                                                //Bằng cấp
                                                                                if (URLEncoder.encode(removeDiacriticalMarks(bangcap), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getBangCap().get(0)), "utf-8"))) {
                                                                                    khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));

                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            } else //Buoi == null
                                                            {
                                                                for (String bangcap : kh.getBangCap()) {
                                                                    //Bằng cấp
                                                                    if (URLEncoder.encode(removeDiacriticalMarks(bangcap), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getBangCap().get(0)), "utf-8"))) {
                                                                        khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));

                                                                    }
                                                                }
                                                            }
                                                        }
                                                    } else //Ngay == null
                                                    {
                                                        if (khoaHoc.getLichHoc().getThoiGian().size() != 0) // Buổi != null
                                                        {
                                                            for (String buoiHoc : khoaHoc.getLichHoc().getThoiGian()) {
                                                                for (String buoihoc : kh.getLichHoc().getThoiGian()) {
                                                                    if (buoiHoc.equals(buoihoc)) // Buổi
                                                                    {
                                                                        for (String bangcap : kh.getBangCap()) {
                                                                            //Bằng cấp
                                                                            if (URLEncoder.encode(removeDiacriticalMarks(bangcap), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getBangCap().get(0)), "utf-8"))) {
                                                                                khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));

                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        } else //Buoi == null
                                                        {
                                                            for (String bangcap : kh.getBangCap()) {
                                                                //Bằng cấp
                                                                if (URLEncoder.encode(removeDiacriticalMarks(bangcap), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getBangCap().get(0)), "utf-8"))) {
                                                                    khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));

                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                                ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHoc(khoaHocChinhXac, khoaHocGanChinhXac);
                                            } catch (UnsupportedEncodingException e) {
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
                        } else//4 Mon == null, HocPhi==0,GioiTinh==Nam, Nữ,BangCap==null
                        {
                            if (khoaHoc.getDiaDiem().getQuan() != "Khác") //5 Mon == null, HocPhi==0,GioiTinh==Nam, Nữ,BangCap==null,Quan != "Khác"
                            {
                                final double finalHocPhiDuoi = hocPhiDuoi;
                                final double finalHocPhiTren = hocPhiTren;
                                final int finalCount = count;
                                mData.getReference().child("KhoaHoc").child("KhoaHocTimGiaSu").child("ChuaHoanTat").addChildEventListener(new ChildEventListener() {
                                    @Override
                                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                        int dem = 0; // Xác định độ chính xác của lịch học (if dem == finaleCount)
                                        KhoaHoc kh = dataSnapshot.getValue(KhoaHoc.class);
                                        for (String linhvuc : kh.getLinhVuc()) {
                                            //LinhVuc
                                            try {
                                                if (URLEncoder.encode(removeDiacriticalMarks(linhvuc), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getLinhVuc().get(0)), "utf-8"))) {
//Lịch học
                                                    if (finalCount != 0) //Ngày học tìm kiếm != null
                                                    {
                                                        for (String ngayHoc : khoaHoc.getLichHoc().getNgayHoc()) {
                                                            for (String ngayhoc : kh.getLichHoc().getNgayHoc()) {
                                                                if (ngayhoc.equals(ngayHoc)) {
                                                                    dem++;
                                                                    if (dem == finalCount) // Độ chính xác lịch học đạt mức chấp nhận được
                                                                    {
                                                                        khoaHocGanChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));
                                                                    }
                                                                }
                                                            }
                                                        }
                                                        if (dem >= finalCount) // Độ chính xác lịch học chấp nhận được hoặc rất chính xác
                                                        {
                                                            if (khoaHoc.getLichHoc().getThoiGian().size() != 0) // Buổi != null
                                                            {
                                                                for (String buoiHoc : khoaHoc.getLichHoc().getThoiGian()) {
                                                                    for (String buoihoc : kh.getLichHoc().getThoiGian()) {
                                                                        if (buoiHoc.equals(buoihoc)) // Buổi
                                                                        {
                                                                            //Địa điểm
                                                                            if (kh.getDiaDiem().getQuan().equals(khoaHoc.getDiaDiem().getQuan())) {
                                                                                khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));

                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            } else //Buoi == null
                                                            {
//Địa điểm
                                                                if (kh.getDiaDiem().getQuan().equals(khoaHoc.getDiaDiem().getQuan())) {
                                                                    khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));

                                                                }
                                                            }
                                                        }
                                                    } else //Ngay == null
                                                    {
                                                        if (khoaHoc.getLichHoc().getThoiGian().size() != 0) // Buổi != null
                                                        {
                                                            for (String buoiHoc : khoaHoc.getLichHoc().getThoiGian()) {
                                                                for (String buoihoc : kh.getLichHoc().getThoiGian()) {
                                                                    if (buoiHoc.equals(buoihoc)) // Buổi
                                                                    {
                                                                        //Địa điểm
                                                                        if (kh.getDiaDiem().getQuan().equals(khoaHoc.getDiaDiem().getQuan())) {
                                                                            khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));

                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        } else //Buoi == null
                                                        {
//Địa điểm
                                                            if (kh.getDiaDiem().getQuan().equals(khoaHoc.getDiaDiem().getQuan())) {
                                                                khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));

                                                            }
                                                        }
                                                    }
                                                }
                                                ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHoc(khoaHocChinhXac, khoaHocGanChinhXac);

                                            } catch (UnsupportedEncodingException e) {
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
                            } else //5 Mon == null, HocPhi==0,GioiTinh==Nam, Nữ,BangCap==null, Quan = "Khác"
                            {
                                final double finalHocPhiDuoi = hocPhiDuoi;
                                final double finalHocPhiTren = hocPhiTren;
                                final int finalCount = count;
                                mData.getReference().child("KhoaHoc").child("KhoaHocTimGiaSu").child("ChuaHoanTat").addChildEventListener(new ChildEventListener() {
                                    @Override
                                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                        int dem = 0; // Xác định độ chính xác của lịch học (if dem == finaleCount)
                                        KhoaHoc kh = dataSnapshot.getValue(KhoaHoc.class);
                                        for (String linhvuc : kh.getLinhVuc()) {
                                            //LinhVuc
                                            try {
                                                if (URLEncoder.encode(removeDiacriticalMarks(linhvuc), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getLinhVuc().get(0)), "utf-8"))) {
//Lịch học
                                                    if (finalCount != 0) //Ngày học tìm kiếm != null
                                                    {
                                                        for (String ngayHoc : khoaHoc.getLichHoc().getNgayHoc()) {
                                                            for (String ngayhoc : kh.getLichHoc().getNgayHoc()) {
                                                                if (ngayhoc.equals(ngayHoc)) {
                                                                    dem++;
                                                                    if (dem == finalCount) // Độ chính xác lịch học đạt mức chấp nhận được
                                                                    {
                                                                        khoaHocGanChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));
                                                                    }
                                                                }
                                                            }
                                                        }
                                                        if (dem >= finalCount) // Độ chính xác lịch học chấp nhận được hoặc rất chính xác
                                                        {
                                                            if (khoaHoc.getLichHoc().getThoiGian().size() != 0) // Buổi != null
                                                            {
                                                                for (String buoiHoc : khoaHoc.getLichHoc().getThoiGian()) {
                                                                    for (String buoihoc : kh.getLichHoc().getThoiGian()) {
                                                                        if (buoiHoc.equals(buoihoc)) // Buổi
                                                                        {
                                                                            khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));

                                                                        }
                                                                    }
                                                                }
                                                            } else //Buoi == null
                                                            {
                                                                khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));

                                                            }
                                                        }
                                                    } else //Buoi == null
                                                    {
                                                        if (khoaHoc.getLichHoc().getThoiGian().size() != 0) // Buổi != null
                                                        {
                                                            for (String buoiHoc : khoaHoc.getLichHoc().getThoiGian()) {
                                                                for (String buoihoc : kh.getLichHoc().getThoiGian()) {
                                                                    if (buoiHoc.equals(buoihoc)) // Buổi
                                                                    {
                                                                        khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));

                                                                    }
                                                                }
                                                            }
                                                        } else //Buoi == null
                                                        {
                                                            khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));

                                                        }
                                                    }
                                                }
                                                ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHoc(khoaHocChinhXac, khoaHocGanChinhXac);

                                            } catch (UnsupportedEncodingException e) {
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
                }
            }
        } else {
            mData.getReference().child("KhoaHoc").child("KhoaHocTimHocVien").child("ChuaHoanTat").addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    KhoaHoc kh = dataSnapshot.getValue(KhoaHoc.class);
                    if (kh.getMon() != null || kh.getMon().size() >= 0) {
                        for (String smon : kh.getMon()) {
                            try {
                                if (URLEncoder.encode(removeDiacriticalMarks(mon), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(smon), "utf-8"))) {
                                    khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));
                                    khoaHocGanChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));
                                    ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHoc(khoaHocChinhXac, khoaHocGanChinhXac);
                                }
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
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

    //Tìm kiếm gia sư - khóa học ( Khóa học tìm học viên)
    @Override
    public void getListKhoaHocGiaSu(final KhoaHoc khoaHoc, Activity activity) {
        int count = 0; //Nếu /2 mà <1 lấy 0 còn lại lấy /2 bt
        double hocPhiTimKiem = 0;
        double hocPhiTren = 0;
        double hocPhiDuoi = 0;
        mData = FirebaseDatabase.getInstance(FirebaseApp.initializeApp(activity));

        if (khoaHoc.getLichHoc().getNgayHoc() != null || khoaHoc.getLichHoc().getNgayHoc().size() != 0) {
            count = cout(khoaHoc.getLichHoc().getNgayHoc().size()); //Nếu /2 mà <1 lấy 0 còn lại lấy /2 bt
            hocPhiTimKiem = Double.parseDouble(khoaHoc.getHocPhi());
            hocPhiTren = (hocPhiTimKiem + ((hocPhiTimKiem / 100) * 20));
            hocPhiDuoi = (hocPhiTimKiem - ((hocPhiTimKiem / 100) * 20));
        } else {
            count = 0; //Nếu /2 mà <1 lấy 0 còn lại lấy /2 bt
            hocPhiTimKiem = Double.parseDouble(khoaHoc.getHocPhi());
            hocPhiTren = (hocPhiTimKiem + ((hocPhiTimKiem / 100) * 20));
            hocPhiDuoi = (hocPhiTimKiem - ((hocPhiTimKiem / 100) * 20));
        }

        if (khoaHoc.getMon() != null)//1
        {
            if (hocPhiTimKiem != 0) //2 Mon!=null, HocPhi !=0
            {
                if (khoaHoc.getGioiTinh() != "Nam, Nữ") //3 Mon!=null, HocPhi !=0,GioiTinh!=Nam, Nữ
                {
                    if (khoaHoc.getBangCap() != null) //4 Mon!=null,HocPhi!=null,GioiTinh!=Nam, Nu,BangCap!=null
                    {
                        if (khoaHoc.getDiaDiem().getQuan() != "Khác") //5 Mon!=null,HocPhi!=null,GioiTinh!=Nam, Nu,BangCap!=null,Quan != "Khác"
                        {
                            final double finalHocPhiDuoi = hocPhiDuoi;
                            final double finalHocPhiTren = hocPhiTren;
                            final int finalCount = count;

                            mData.getReference().child("KhoaHoc").child("KhoaHocTimHocVien").child("ChuaHoanTat").addChildEventListener(new ChildEventListener() {
                                @Override
                                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                    int dem = 0; // Xác định độ chính xác của lịch học (if dem == finaleCount)
                                    KhoaHoc kh = dataSnapshot.getValue(KhoaHoc.class);
                                    for (String Mon : kh.getMon()) {
                                        try {
                                            //Môn
                                            if (URLEncoder.encode(removeDiacriticalMarks(Mon), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getMon().get(0)), "utf-8"))) {
                                                //Lịch học
                                                if (finalCount != 0) //Ngày học tìm kiếm != null
                                                {
                                                    for (String ngayHoc : khoaHoc.getLichHoc().getNgayHoc()) {
                                                        for (String ngayhoc : kh.getLichHoc().getNgayHoc()) {
                                                            if (ngayhoc.equals(ngayHoc)) {
                                                                dem++;
                                                                if (dem == finalCount) // Độ chính xác lịch học đạt mức chấp nhận được
                                                                {
                                                                    khoaHocGanChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));
                                                                }
                                                            }
                                                        }
                                                    }
                                                    if (dem >= finalCount) // Độ chính xác lịch học chấp nhận được hoặc rất chính xác
                                                    {
                                                        if (khoaHoc.getLichHoc().getThoiGian().size() != 0) // Buổi != null
                                                        {
                                                            for (String buoiHoc : khoaHoc.getLichHoc().getThoiGian()) {
                                                                for (String buoihoc : kh.getLichHoc().getThoiGian()) {
                                                                    if (buoiHoc.equals(buoihoc)) // Buổi
                                                                    {
                                                                        //Học phí
                                                                        if (Double.parseDouble(kh.getHocPhi()) >= finalHocPhiDuoi && Double.parseDouble(kh.getHocPhi()) <= finalHocPhiTren) {
                                                                            //Giới tính
                                                                            if (kh.getGioiTinh().equals(khoaHoc.getGioiTinh())) {
                                                                                for (String bangcap : kh.getBangCap()) {
                                                                                    //Bằng cấp
                                                                                    if (URLEncoder.encode(removeDiacriticalMarks(bangcap), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getBangCap().get(0)), "utf-8"))) {
                                                                                        //Địa điểm
                                                                                        if (kh.getDiaDiem().getQuan().equals(khoaHoc.getDiaDiem().getQuan())) {
                                                                                            khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));

                                                                                        }
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        } else //Buổi == null
                                                        {
                                                            //Học phí
                                                            if (Double.parseDouble(kh.getHocPhi()) >= finalHocPhiDuoi && Double.parseDouble(kh.getHocPhi()) <= finalHocPhiTren) {
                                                                //Giới tính
                                                                if (kh.getGioiTinh().equals(khoaHoc.getGioiTinh())) {
                                                                    for (String bangcap : kh.getBangCap()) {
                                                                        //Bằng cấp
                                                                        if (URLEncoder.encode(removeDiacriticalMarks(bangcap), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getBangCap().get(0)), "utf-8"))) {
                                                                            //Địa điểm
                                                                            if (kh.getDiaDiem().getQuan().equals(khoaHoc.getDiaDiem().getQuan())) {
                                                                                khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));

                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }

                                                    }
                                                } else // Ngày học tìm kiếm == null
                                                {
                                                    if (khoaHoc.getLichHoc().getThoiGian().size() != 0) // Buổi != null
                                                    {
                                                        for (String buoiHoc : khoaHoc.getLichHoc().getThoiGian()) {
                                                            for (String buoihoc : kh.getLichHoc().getThoiGian()) {
                                                                if (buoiHoc.equals(buoihoc)) // Buổi
                                                                {
                                                                    //Học phí
                                                                    if (Double.parseDouble(kh.getHocPhi()) >= finalHocPhiDuoi && Double.parseDouble(kh.getHocPhi()) <= finalHocPhiTren) {
                                                                        //Giới tính
                                                                        if (kh.getGioiTinh().equals(khoaHoc.getGioiTinh())) {
                                                                            for (String bangcap : kh.getBangCap()) {
                                                                                //Bằng cấp
                                                                                if (URLEncoder.encode(removeDiacriticalMarks(bangcap), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getBangCap().get(0)), "utf-8"))) {
                                                                                    //Địa điểm
                                                                                    if (kh.getDiaDiem().getQuan().equals(khoaHoc.getDiaDiem().getQuan())) {
                                                                                        khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));

                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    } else //Buổi == null
                                                    {
                                                        //Học phí
                                                        if (Double.parseDouble(kh.getHocPhi()) >= finalHocPhiDuoi && Double.parseDouble(kh.getHocPhi()) <= finalHocPhiTren) {
                                                            //Giới tính
                                                            if (kh.getGioiTinh().equals(khoaHoc.getGioiTinh())) {
                                                                for (String bangcap : kh.getBangCap()) {
                                                                    //Bằng cấp
                                                                    if (URLEncoder.encode(removeDiacriticalMarks(bangcap), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getBangCap().get(0)), "utf-8"))) {
                                                                        //Địa điểm
                                                                        if (kh.getDiaDiem().getQuan().equals(khoaHoc.getDiaDiem().getQuan())) {
                                                                            khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));

                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }


                                            }
                                            ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHocGiaSu(khoaHocChinhXac, khoaHocGanChinhXac);
                                        } catch (UnsupportedEncodingException e) {
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
                        } else //5 Mon!=null,HocPhi!=null,GioiTinh!=Nam, Nu,BangCap!=null, Quan = "Khác"
                        {
                            final double finalHocPhiDuoi = hocPhiDuoi;
                            final double finalHocPhiTren = hocPhiTren;
                            final int finalCount = count;

                            mData.getReference().child("KhoaHoc").child("KhoaHocTimHocVien").child("ChuaHoanTat").addChildEventListener(new ChildEventListener() {
                                @Override
                                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                    int dem = 0; // Xác định độ chính xác của lịch học (if dem == finaleCount)
                                    KhoaHoc kh = dataSnapshot.getValue(KhoaHoc.class);
                                    for (String Mon : kh.getMon()) {
                                        try {
                                            //Môn
                                            if (URLEncoder.encode(removeDiacriticalMarks(Mon), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getMon().get(0)), "utf-8"))) {
                                                if (finalCount != 0) //Ngày học tìm kiếm != null
                                                {
                                                    for (String ngayHoc : khoaHoc.getLichHoc().getNgayHoc()) {
                                                        for (String ngayhoc : kh.getLichHoc().getNgayHoc()) {
                                                            if (ngayhoc.equals(ngayHoc)) {
                                                                dem++;
                                                                if (dem == finalCount) // Độ chính xác lịch học đạt mức chấp nhận được
                                                                {
                                                                    khoaHocGanChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));
                                                                }
                                                            }
                                                        }
                                                    }
                                                    if (dem >= finalCount) // Độ chính xác lịch học chấp nhận được hoặc rất chính xác
                                                    {
                                                        if (khoaHoc.getLichHoc().getThoiGian().size() != 0)

                                                        {
                                                            for (String buoiHoc : khoaHoc.getLichHoc().getThoiGian()) {
                                                                for (String buoihoc : kh.getLichHoc().getThoiGian()) {
                                                                    if (buoiHoc.equals(buoihoc)) // Buổi
                                                                    {
                                                                        //Học phí
                                                                        if (Double.parseDouble(kh.getHocPhi()) >= finalHocPhiDuoi && Double.parseDouble(kh.getHocPhi()) <= finalHocPhiTren) {
                                                                            //Giới tính
                                                                            if (kh.getGioiTinh().equals(khoaHoc.getGioiTinh())) {
                                                                                for (String bangcap : kh.getBangCap()) {
                                                                                    //Bằng cấp
                                                                                    if (URLEncoder.encode(removeDiacriticalMarks(bangcap), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getBangCap().get(0)), "utf-8"))) {
                                                                                        khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        } else //Buổi == null
                                                        {
                                                            //Học phí
                                                            if (Double.parseDouble(kh.getHocPhi()) >= finalHocPhiDuoi && Double.parseDouble(kh.getHocPhi()) <= finalHocPhiTren) {
                                                                //Giới tính
                                                                if (kh.getGioiTinh().equals(khoaHoc.getGioiTinh())) {
                                                                    for (String bangcap : kh.getBangCap()) {
                                                                        //Bằng cấp
                                                                        if (URLEncoder.encode(removeDiacriticalMarks(bangcap), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getBangCap().get(0)), "utf-8"))) {
                                                                            khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                } else // Ngày học == null
                                                {
                                                    if (khoaHoc.getLichHoc().getThoiGian().size() != 0) // Buổi != null
                                                    {
                                                        for (String buoiHoc : khoaHoc.getLichHoc().getThoiGian()) {
                                                            for (String buoihoc : kh.getLichHoc().getThoiGian()) {
                                                                if (buoiHoc.equals(buoihoc)) // Buổi
                                                                {
                                                                    //Học phí
                                                                    if (Double.parseDouble(kh.getHocPhi()) >= finalHocPhiDuoi && Double.parseDouble(kh.getHocPhi()) <= finalHocPhiTren) {
                                                                        //Giới tính
                                                                        if (kh.getGioiTinh().equals(khoaHoc.getGioiTinh())) {
                                                                            for (String bangcap : kh.getBangCap()) {
                                                                                //Bằng cấp
                                                                                if (URLEncoder.encode(removeDiacriticalMarks(bangcap), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getBangCap().get(0)), "utf-8"))) {
                                                                                    khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    } else //Buổi == null
                                                    {
                                                        //Học phí
                                                        if (Double.parseDouble(kh.getHocPhi()) >= finalHocPhiDuoi && Double.parseDouble(kh.getHocPhi()) <= finalHocPhiTren) {
                                                            //Giới tính
                                                            if (kh.getGioiTinh().equals(khoaHoc.getGioiTinh())) {
                                                                for (String bangcap : kh.getBangCap()) {
                                                                    //Bằng cấp
                                                                    if (URLEncoder.encode(removeDiacriticalMarks(bangcap), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getBangCap().get(0)), "utf-8"))) {
                                                                        khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }

                                            }
                                            ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHocGiaSu(khoaHocChinhXac, khoaHocGanChinhXac);
                                        } catch (UnsupportedEncodingException e) {
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
                    } else//4 Mon!=null,HocPhi!=null,GioiTinh!=Nam, Nu,BangCap==null*
                    {
                        if (khoaHoc.getDiaDiem().getQuan() != "Khác") //5 Mon!=null,HocPhi!=null,GioiTinh!=Nam, Nu,BangCap==null,Quan != "Khác"
                        {
                            final double finalHocPhiDuoi = hocPhiDuoi;
                            final double finalHocPhiTren = hocPhiTren;
                            final int finalCount = count;
                            mData.getReference().child("KhoaHoc").child("KhoaHocTimHocVien").child("ChuaHoanTat").addChildEventListener(new ChildEventListener() {
                                @Override
                                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                    int dem = 0; // Xác định độ chính xác của lịch học (if dem == finaleCount)
                                    KhoaHoc kh = dataSnapshot.getValue(KhoaHoc.class);
                                    for (String Mon : khoaHoc.getMon()) {
                                        //Môn
                                        try {
                                            if (URLEncoder.encode(removeDiacriticalMarks(Mon), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getMon().get(0)), "utf-8"))) {
                                                if (finalCount != 0) //Ngày học tìm kiếm != null
                                                {
                                                    for (String ngayHoc : khoaHoc.getLichHoc().getNgayHoc()) {
                                                        for (String ngayhoc : kh.getLichHoc().getNgayHoc()) {
                                                            if (ngayhoc.equals(ngayHoc)) {
                                                                dem++;
                                                                if (dem == finalCount) // Độ chính xác lịch học đạt mức chấp nhận được
                                                                {
                                                                    khoaHocGanChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));
                                                                }
                                                            }
                                                        }
                                                    }
                                                    if (dem >= finalCount) // Độ chính xác lịch học chấp nhận được hoặc rất chính xác
                                                    {
                                                        if (khoaHoc.getLichHoc().getThoiGian().size() != 0) // Buổi != null
                                                        {
                                                            for (String buoiHoc : khoaHoc.getLichHoc().getThoiGian()) {
                                                                for (String buoihoc : kh.getLichHoc().getThoiGian()) {
                                                                    if (buoiHoc.equals(buoihoc)) // Buổi
                                                                    {
                                                                        //Học phí
                                                                        if (Double.parseDouble(kh.getHocPhi()) >= finalHocPhiDuoi && Double.parseDouble(kh.getHocPhi()) <= finalHocPhiTren) {
                                                                            //Giới tính
                                                                            if (kh.getGioiTinh().equals(khoaHoc.getGioiTinh())) {
                                                                                //Địa điểm
                                                                                if (kh.getDiaDiem().getQuan().equals(khoaHoc.getDiaDiem().getQuan())) {
                                                                                    khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        } else //Buổi == null
                                                        {
                                                            //Học phí
                                                            if (Double.parseDouble(kh.getHocPhi()) >= finalHocPhiDuoi && Double.parseDouble(kh.getHocPhi()) <= finalHocPhiTren) {
                                                                //Giới tính
                                                                if (kh.getGioiTinh().equals(khoaHoc.getGioiTinh())) {
                                                                    //Địa điểm
                                                                    if (kh.getDiaDiem().getQuan().equals(khoaHoc.getDiaDiem().getQuan())) {
                                                                        khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                } else // Ngày học tìm kiếm == null
                                                {
                                                    if (khoaHoc.getLichHoc().getThoiGian().size() != 0) // Buổi != null
                                                    {
                                                        for (String buoiHoc : khoaHoc.getLichHoc().getThoiGian()) {
                                                            for (String buoihoc : kh.getLichHoc().getThoiGian()) {
                                                                if (buoiHoc.equals(buoihoc)) // Buổi
                                                                {
                                                                    //Học phí
                                                                    if (Double.parseDouble(kh.getHocPhi()) >= finalHocPhiDuoi && Double.parseDouble(kh.getHocPhi()) <= finalHocPhiTren) {
                                                                        //Giới tính
                                                                        if (kh.getGioiTinh().equals(khoaHoc.getGioiTinh())) {
                                                                            //Địa điểm
                                                                            if (kh.getDiaDiem().getQuan().equals(khoaHoc.getDiaDiem().getQuan())) {
                                                                                khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    } else //Buổi == null
                                                    {
                                                        //Học phí
                                                        if (Double.parseDouble(kh.getHocPhi()) >= finalHocPhiDuoi && Double.parseDouble(kh.getHocPhi()) <= finalHocPhiTren) {
                                                            //Giới tính
                                                            if (kh.getGioiTinh().equals(khoaHoc.getGioiTinh())) {
                                                                //Địa điểm
                                                                if (kh.getDiaDiem().getQuan().equals(khoaHoc.getDiaDiem().getQuan())) {
                                                                    khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));
                                                                }
                                                            }
                                                        }
                                                    }
                                                }

                                            }
                                            ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHocGiaSu(khoaHocChinhXac, khoaHocGanChinhXac);
                                        } catch (UnsupportedEncodingException e) {
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
                        } else //5 Mon!=null,HocPhi!=null,GioiTinh!=Nam, Nu,BangCap==null, Quan = "Khác"
                        {
                            final double finalHocPhiDuoi = hocPhiDuoi;
                            final double finalHocPhiTren = hocPhiTren;
                            final int finalCount = count;
                            mData.getReference().child("KhoaHoc").child("KhoaHocTimHocVien").child("ChuaHoanTat").addChildEventListener(new ChildEventListener() {
                                @Override
                                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                    KhoaHoc kh = dataSnapshot.getValue(KhoaHoc.class);
                                    int dem = 0; // Xác định độ chính xác của lịch học (if dem == finaleCount)
                                    for (String Mon : kh.getMon()) {
                                        //Môn
                                        try {
                                            if (URLEncoder.encode(removeDiacriticalMarks(Mon), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getMon().get(0)), "utf-8"))) {
                                                if (finalCount != 0) //Ngày học tìm kiếm != null
                                                {
                                                    for (String ngayHoc : khoaHoc.getLichHoc().getNgayHoc()) {
                                                        for (String ngayhoc : kh.getLichHoc().getNgayHoc()) {
                                                            if (ngayhoc.equals(ngayHoc)) {
                                                                dem++;
                                                                if (dem == finalCount) // Độ chính xác lịch học đạt mức chấp nhận được
                                                                {
                                                                    khoaHocGanChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));
                                                                }
                                                            }
                                                        }
                                                    }
                                                    if (dem >= finalCount) // Độ chính xác lịch học chấp nhận được hoặc rất chính xác
                                                    {
                                                        if (khoaHoc.getLichHoc().getThoiGian().size() != 0) // Buổi != null
                                                        {
                                                            for (String buoiHoc : khoaHoc.getLichHoc().getThoiGian()) {
                                                                for (String buoihoc : kh.getLichHoc().getThoiGian()) {
                                                                    if (buoiHoc.equals(buoihoc)) // Buổi
                                                                    {
                                                                        //Học phí
                                                                        if (Double.parseDouble(kh.getHocPhi()) >= finalHocPhiDuoi && Double.parseDouble(kh.getHocPhi()) <= finalHocPhiTren) {
                                                                            //Giới tính
                                                                            if (kh.getGioiTinh().equals(khoaHoc.getGioiTinh())) {
                                                                                khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        } else //Buổi == null
                                                        {
                                                            //Học phí
                                                            if (Double.parseDouble(kh.getHocPhi()) >= finalHocPhiDuoi && Double.parseDouble(kh.getHocPhi()) <= finalHocPhiTren) {
                                                                //Giới tính
                                                                if (kh.getGioiTinh().equals(khoaHoc.getGioiTinh())) {
                                                                    khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));
                                                                }
                                                            }
                                                        }
                                                    }
                                                } else //Ngày học tìm kiếm == null
                                                {
                                                    if (khoaHoc.getLichHoc().getThoiGian().size() != 0) // Buổi != null
                                                    {
                                                        for (String buoiHoc : khoaHoc.getLichHoc().getThoiGian()) {
                                                            for (String buoihoc : kh.getLichHoc().getThoiGian()) {
                                                                if (buoiHoc.equals(buoihoc)) // Buổi
                                                                {
                                                                    //Học phí
                                                                    if (Double.parseDouble(kh.getHocPhi()) >= finalHocPhiDuoi && Double.parseDouble(kh.getHocPhi()) <= finalHocPhiTren) {
                                                                        //Giới tính
                                                                        if (kh.getGioiTinh().equals(khoaHoc.getGioiTinh())) {
                                                                            khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    } else //Buổi == null
                                                    {
                                                        //Học phí
                                                        if (Double.parseDouble(kh.getHocPhi()) >= finalHocPhiDuoi && Double.parseDouble(kh.getHocPhi()) <= finalHocPhiTren) {
                                                            //Giới tính
                                                            if (kh.getGioiTinh().equals(khoaHoc.getGioiTinh())) {
                                                                khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                            ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHocGiaSu(khoaHocChinhXac, khoaHocGanChinhXac);
                                        } catch (UnsupportedEncodingException e) {
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
                } else //3 Mon!=null,HocPhi!=0,GioiTinh==Nam, Nữ
                {
                    if (khoaHoc.getBangCap() != null) //4 Mon!=null,HocPhi!=0,GioiTinh==Nam, Nữ,BangCap!=null
                    {
                        if (khoaHoc.getDiaDiem().getQuan() != "Khác") //5 Mon!=null,HocPhi!=0,GioiTinh==Nam, Nữ,BangCap!=null,Quan != "Khác"
                        {
                            final double finalHocPhiDuoi = hocPhiDuoi;
                            final double finalHocPhiTren = hocPhiTren;
                            final int finalCount = count;
                            mData.getReference().child("KhoaHoc").child("KhoaHocTimHocVien").child("ChuaHoanTat").addChildEventListener(new ChildEventListener() {
                                @Override
                                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                    int dem = 0; // Xác định độ chính xác của lịch học (if dem == finaleCount)
                                    KhoaHoc kh = dataSnapshot.getValue(KhoaHoc.class);
                                    for (String Mon : kh.getMon()) {
                                        //Môn
                                        try {
                                            if (URLEncoder.encode(removeDiacriticalMarks(Mon), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getMon().get(0)), "utf-8"))) {
                                                //Lịch học
                                                if (finalCount != 0) //Ngày học tìm kiếm != null
                                                {
                                                    for (String ngayHoc : khoaHoc.getLichHoc().getNgayHoc()) {
                                                        for (String ngayhoc : kh.getLichHoc().getNgayHoc()) {
                                                            if (ngayhoc.equals(ngayHoc)) {
                                                                dem++;
                                                                if (dem == finalCount) // Độ chính xác lịch học đạt mức chấp nhận được
                                                                {
                                                                    khoaHocGanChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));
                                                                }
                                                            }
                                                        }
                                                    }
                                                    if (dem >= finalCount) // Độ chính xác lịch học chấp nhận được hoặc rất chính xác
                                                    {
                                                        if (khoaHoc.getLichHoc().getThoiGian().size() != 0) // Buổi != null
                                                        {
                                                            for (String buoiHoc : khoaHoc.getLichHoc().getThoiGian()) {
                                                                for (String buoihoc : kh.getLichHoc().getThoiGian()) {
                                                                    if (buoiHoc.equals(buoihoc)) // Buổi
                                                                    {
                                                                        //Học phí
                                                                        if (Double.parseDouble(kh.getHocPhi()) >= finalHocPhiDuoi && Double.parseDouble(kh.getHocPhi()) <= finalHocPhiTren) {
                                                                            for (String bangcap : kh.getBangCap()) {
                                                                                //Bằng cấp
                                                                                if (URLEncoder.encode(removeDiacriticalMarks(bangcap), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getBangCap().get(0)), "utf-8"))) {
                                                                                    //Địa điểm
                                                                                    if (kh.getDiaDiem().getQuan().equals(khoaHoc.getDiaDiem().getQuan())) {
                                                                                        khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        } else //Buổi == null
                                                        {
                                                            //Học phí
                                                            if (Double.parseDouble(kh.getHocPhi()) >= finalHocPhiDuoi && Double.parseDouble(kh.getHocPhi()) <= finalHocPhiTren) {
                                                                for (String bangcap : kh.getBangCap()) {
                                                                    //Bằng cấp
                                                                    if (URLEncoder.encode(removeDiacriticalMarks(bangcap), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getBangCap().get(0)), "utf-8"))) {
                                                                        //Địa điểm
                                                                        if (kh.getDiaDiem().getQuan().equals(khoaHoc.getDiaDiem().getQuan())) {
                                                                            khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                } else //Ngày học tìm kiếm == null
                                                {
                                                    if (khoaHoc.getLichHoc().getThoiGian().size() != 0) // Buổi != null
                                                    {
                                                        for (String buoiHoc : khoaHoc.getLichHoc().getThoiGian()) {
                                                            for (String buoihoc : kh.getLichHoc().getThoiGian()) {
                                                                if (buoiHoc.equals(buoihoc)) // Buổi
                                                                {
                                                                    //Học phí
                                                                    if (Double.parseDouble(kh.getHocPhi()) >= finalHocPhiDuoi && Double.parseDouble(kh.getHocPhi()) <= finalHocPhiTren) {
                                                                        for (String bangcap : kh.getBangCap()) {
                                                                            //Bằng cấp
                                                                            if (URLEncoder.encode(removeDiacriticalMarks(bangcap), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getBangCap().get(0)), "utf-8"))) {
                                                                                //Địa điểm
                                                                                if (kh.getDiaDiem().getQuan().equals(khoaHoc.getDiaDiem().getQuan())) {
                                                                                    khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    } else //Buổi == null
                                                    {
                                                        //Học phí
                                                        if (Double.parseDouble(kh.getHocPhi()) >= finalHocPhiDuoi && Double.parseDouble(kh.getHocPhi()) <= finalHocPhiTren) {
                                                            for (String bangcap : kh.getBangCap()) {
                                                                //Bằng cấp
                                                                if (URLEncoder.encode(removeDiacriticalMarks(bangcap), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getBangCap().get(0)), "utf-8"))) {
                                                                    //Địa điểm
                                                                    if (kh.getDiaDiem().getQuan().equals(khoaHoc.getDiaDiem().getQuan())) {
                                                                        khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                            ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHocGiaSu(khoaHocChinhXac, khoaHocGanChinhXac);
                                        } catch (UnsupportedEncodingException e) {
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
                        } else //5 Mon!=null,HocPhi!=0,GioiTinh==Nam, Nữ,BangCap!=null, Quan = "Khác"
                        {
                            final double finalHocPhiDuoi = hocPhiDuoi;
                            final double finalHocPhiTren = hocPhiTren;
                            final int finalCount = count;
                            mData.getReference().child("KhoaHoc").child("KhoaHocTimHocVien").child("ChuaHoanTat").addChildEventListener(new ChildEventListener() {
                                @Override
                                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                    int dem = 0; // Xác định độ chính xác của lịch học (if dem == finaleCount)
                                    KhoaHoc kh = dataSnapshot.getValue(KhoaHoc.class);
                                    for (String Mon : kh.getMon()) {
                                        try {
                                            if (URLEncoder.encode(removeDiacriticalMarks(Mon), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getMon().get(0)), "utf-8"))) {
                                                //Lịch học
                                                if (finalCount != 0) //Ngày học tìm kiếm != null
                                                {
                                                    for (String ngayHoc : khoaHoc.getLichHoc().getNgayHoc()) {
                                                        for (String ngayhoc : kh.getLichHoc().getNgayHoc()) {
                                                            if (ngayhoc.equals(ngayHoc)) {
                                                                dem++;
                                                                if (dem == finalCount) // Độ chính xác lịch học đạt mức chấp nhận được
                                                                {
                                                                    khoaHocGanChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));
                                                                }
                                                            }
                                                        }
                                                    }
                                                    if (dem >= finalCount) // Độ chính xác lịch học chấp nhận được hoặc rất chính xác
                                                    {
                                                        if (khoaHoc.getLichHoc().getThoiGian().size() != 0) // Buổi != null
                                                        {
                                                            for (String buoiHoc : khoaHoc.getLichHoc().getThoiGian()) {
                                                                for (String buoihoc : kh.getLichHoc().getThoiGian()) {
                                                                    if (buoiHoc.equals(buoihoc)) // Buổi
                                                                    {
                                                                        //Học phí
                                                                        if (Double.parseDouble(kh.getHocPhi()) >= finalHocPhiDuoi && Double.parseDouble(kh.getHocPhi()) <= finalHocPhiTren) {
                                                                            for (String bangcap : kh.getBangCap()) {
                                                                                //Bằng cấp
                                                                                if (URLEncoder.encode(removeDiacriticalMarks(bangcap), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getBangCap().get(0)), "utf-8"))) {
                                                                                    khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        } else // Buổi == null
                                                        {
//Học phí
                                                            if (Double.parseDouble(kh.getHocPhi()) >= finalHocPhiDuoi && Double.parseDouble(kh.getHocPhi()) <= finalHocPhiTren) {
                                                                for (String bangcap : kh.getBangCap()) {
                                                                    //Bằng cấp
                                                                    if (URLEncoder.encode(removeDiacriticalMarks(bangcap), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getBangCap().get(0)), "utf-8"))) {
                                                                        khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                } else // Ngày học tìm kiếm == null
                                                {
                                                    if (khoaHoc.getLichHoc().getThoiGian().size() != 0) // Buổi != null
                                                    {
                                                        for (String buoiHoc : khoaHoc.getLichHoc().getThoiGian()) {
                                                            for (String buoihoc : kh.getLichHoc().getThoiGian()) {
                                                                if (buoiHoc.equals(buoihoc)) // Buổi
                                                                {
                                                                    //Học phí
                                                                    if (Double.parseDouble(kh.getHocPhi()) >= finalHocPhiDuoi && Double.parseDouble(kh.getHocPhi()) <= finalHocPhiTren) {
                                                                        for (String bangcap : kh.getBangCap()) {
                                                                            //Bằng cấp
                                                                            if (URLEncoder.encode(removeDiacriticalMarks(bangcap), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getBangCap().get(0)), "utf-8"))) {
                                                                                khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    } else // Buổi == null
                                                    {
//Học phí
                                                        if (Double.parseDouble(kh.getHocPhi()) >= finalHocPhiDuoi && Double.parseDouble(kh.getHocPhi()) <= finalHocPhiTren) {
                                                            for (String bangcap : kh.getBangCap()) {
                                                                //Bằng cấp
                                                                if (URLEncoder.encode(removeDiacriticalMarks(bangcap), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getBangCap().get(0)), "utf-8"))) {
                                                                    khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                            ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHocGiaSu(khoaHocChinhXac, khoaHocGanChinhXac);
                                        } catch (UnsupportedEncodingException e) {
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
                    } else//4 Mon!=null,HocPhi!=0,GioiTinh==Nam, Nữ,BangCap==null
                    {
                        if (khoaHoc.getDiaDiem().getQuan() != "Khác") //5 Mon!=null,HocPhi!=0,GioiTinh==Nam, Nữ,BangCap==null,Quan != "Khác"
                        {
                            final double finalHocPhiDuoi = hocPhiDuoi;
                            final double finalHocPhiTren = hocPhiTren;
                            final int finalCount = count;
                            mData.getReference().child("KhoaHoc").child("KhoaHocTimHocVien").child("ChuaHoanTat").addChildEventListener(new ChildEventListener() {
                                @Override
                                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                    int dem = 0; // Xác định độ chính xác của lịch học (if dem == finaleCount)
                                    KhoaHoc kh = dataSnapshot.getValue(KhoaHoc.class);
                                    for (String Mon : kh.getMon()) {
                                        //Môn
                                        try {
                                            if (URLEncoder.encode(removeDiacriticalMarks(Mon), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getMon().get(0)), "utf-8"))) {
                                                if (finalCount != 0) //Ngày học tìm kiếm != null
                                                {
                                                    for (String ngayHoc : khoaHoc.getLichHoc().getNgayHoc()) {
                                                        for (String ngayhoc : kh.getLichHoc().getNgayHoc()) {
                                                            if (ngayhoc.equals(ngayHoc)) {
                                                                dem++;
                                                                if (dem == finalCount) // Độ chính xác lịch học đạt mức chấp nhận được
                                                                {
                                                                    khoaHocGanChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));
                                                                }
                                                            }
                                                        }
                                                    }
                                                    if (dem >= finalCount) // Độ chính xác lịch học chấp nhận được hoặc rất chính xác
                                                    {
                                                        if (khoaHoc.getLichHoc().getThoiGian().size() != 0) // Buổi != null
                                                        {
                                                            for (String buoiHoc : khoaHoc.getLichHoc().getThoiGian()) {
                                                                for (String buoihoc : kh.getLichHoc().getThoiGian()) {
                                                                    if (buoiHoc.equals(buoihoc)) // Buổi
                                                                    {
                                                                        //Học phí
                                                                        if (Double.parseDouble(kh.getHocPhi()) >= finalHocPhiDuoi && Double.parseDouble(kh.getHocPhi()) <= finalHocPhiTren) {
                                                                            //Địa điểm
                                                                            if (kh.getDiaDiem().getQuan().equals(khoaHoc.getDiaDiem().getQuan())) {
                                                                                khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));

                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        } else //Buổi == null
                                                        {
                                                            //Học phí
                                                            if (Double.parseDouble(kh.getHocPhi()) >= finalHocPhiDuoi && Double.parseDouble(kh.getHocPhi()) <= finalHocPhiTren) {
                                                                //Địa điểm
                                                                if (kh.getDiaDiem().getQuan().equals(khoaHoc.getDiaDiem().getQuan())) {
                                                                    khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));

                                                                }
                                                            }
                                                        }
                                                    }
                                                } else//Ngày học tìm kiếm == null
                                                {
                                                    if (khoaHoc.getLichHoc().getThoiGian().size() != 0) // Buổi != null
                                                    {
                                                        for (String buoiHoc : khoaHoc.getLichHoc().getThoiGian()) {
                                                            for (String buoihoc : kh.getLichHoc().getThoiGian()) {
                                                                if (buoiHoc.equals(buoihoc)) // Buổi
                                                                {
                                                                    //Học phí
                                                                    if (Double.parseDouble(kh.getHocPhi()) >= finalHocPhiDuoi && Double.parseDouble(kh.getHocPhi()) <= finalHocPhiTren) {
                                                                        //Địa điểm
                                                                        if (kh.getDiaDiem().getQuan().equals(khoaHoc.getDiaDiem().getQuan())) {
                                                                            khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));

                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    } else //Buổi == null
                                                    {
                                                        //Học phí
                                                        if (Double.parseDouble(kh.getHocPhi()) >= finalHocPhiDuoi && Double.parseDouble(kh.getHocPhi()) <= finalHocPhiTren) {
                                                            //Địa điểm
                                                            if (kh.getDiaDiem().getQuan().equals(khoaHoc.getDiaDiem().getQuan())) {
                                                                khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));

                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                            ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHocGiaSu(khoaHocChinhXac, khoaHocGanChinhXac);
                                        } catch (UnsupportedEncodingException e) {
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
                        } else //5 Mon!=null,HocPhi!=0,GioiTinh==Nam, Nữ,BangCap==null, Quan = "Khác"
                        {
                            final double finalHocPhiDuoi = hocPhiDuoi;
                            final double finalHocPhiTren = hocPhiTren;
                            final int finalCount = count;
                            mData.getReference().child("KhoaHoc").child("KhoaHocTimHocVien").child("ChuaHoanTat").addChildEventListener(new ChildEventListener() {
                                @Override
                                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                    int dem = 0; // Xác định độ chính xác của lịch học (if dem == finaleCount)
                                    KhoaHoc kh = dataSnapshot.getValue(KhoaHoc.class);
                                    for (String Mon : kh.getMon()) {
                                        //Môn
                                        try {
                                            if (URLEncoder.encode(removeDiacriticalMarks(Mon), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getMon().get(0)), "utf-8"))) {
                                                //Lịch học
                                                if (finalCount != 0) //Ngày học tìm kiếm != null
                                                {
                                                    for (String ngayHoc : khoaHoc.getLichHoc().getNgayHoc()) {
                                                        for (String ngayhoc : kh.getLichHoc().getNgayHoc()) {
                                                            if (ngayhoc.equals(ngayHoc)) {
                                                                dem++;
                                                                if (dem == finalCount) // Độ chính xác lịch học đạt mức chấp nhận được
                                                                {
                                                                    khoaHocGanChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));
                                                                }
                                                            }
                                                        }
                                                    }
                                                    if (dem >= finalCount) // Độ chính xác lịch học chấp nhận được hoặc rất chính xác
                                                    {
                                                        if (khoaHoc.getLichHoc().getThoiGian().size() != 0) // Buổi != null
                                                        {
                                                            for (String buoiHoc : khoaHoc.getLichHoc().getThoiGian()) {
                                                                for (String buoihoc : kh.getLichHoc().getThoiGian()) {
                                                                    if (buoiHoc.equals(buoihoc)) // Buổi
                                                                    {
                                                                        //Học phí
                                                                        if (Double.parseDouble(kh.getHocPhi()) >= finalHocPhiDuoi && Double.parseDouble(kh.getHocPhi()) <= finalHocPhiTren) {
                                                                            khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        } else //Buổi == null
                                                        {
                                                            //Học phí
                                                            if (Double.parseDouble(kh.getHocPhi()) >= finalHocPhiDuoi && Double.parseDouble(kh.getHocPhi()) <= finalHocPhiTren) {
                                                                khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));
                                                            }
                                                        }
                                                    }
                                                } else //Ngày học tìm kiếm == null
                                                {
                                                    if (khoaHoc.getLichHoc().getThoiGian().size() != 0) // Buổi != null
                                                    {
                                                        for (String buoiHoc : khoaHoc.getLichHoc().getThoiGian()) {
                                                            for (String buoihoc : kh.getLichHoc().getThoiGian()) {
                                                                if (buoiHoc.equals(buoihoc)) // Buổi
                                                                {
                                                                    //Học phí
                                                                    if (Double.parseDouble(kh.getHocPhi()) >= finalHocPhiDuoi && Double.parseDouble(kh.getHocPhi()) <= finalHocPhiTren) {
                                                                        khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    } else //Buổi == null
                                                    {
                                                        //Học phí
                                                        if (Double.parseDouble(kh.getHocPhi()) >= finalHocPhiDuoi && Double.parseDouble(kh.getHocPhi()) <= finalHocPhiTren) {
                                                            khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));
                                                        }
                                                    }
                                                }
                                            }
                                            ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHocGiaSu(khoaHocChinhXac, khoaHocGanChinhXac);
                                        } catch (UnsupportedEncodingException e) {
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
            } else//2 Mon!=null, HocPhi==0
            {
                if (khoaHoc.getGioiTinh() != "Nam, Nữ") //3 Mon!=null, HocPhi==0,GioiTinh!=Nam, Nữ
                {
                    if (khoaHoc.getBangCap() != null) //4 Mon!=null, HocPhi==0,GioiTinh!=Nam, Nu,BangCap!=null
                    {
                        if (khoaHoc.getDiaDiem().getQuan() != "Khác") //5 Mon!=null, HocPhi==0,GioiTinh!=Nam, Nu,BangCap!=null,Quan != "Khác"
                        {
                            final double finalHocPhiDuoi = hocPhiDuoi;
                            final double finalHocPhiTren = hocPhiTren;
                            final int finalCount = count;
                            mData.getReference().child("KhoaHoc").child("KhoaHocTimHocVien").child("ChuaHoanTat").addChildEventListener(new ChildEventListener() {
                                @Override
                                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                    int dem = 0; // Xác định độ chính xác của lịch học (if dem == finaleCount)
                                    KhoaHoc kh = dataSnapshot.getValue(KhoaHoc.class);
                                    for (String Mon : kh.getMon()) {
                                        //Môn
                                        try {
                                            if (URLEncoder.encode(removeDiacriticalMarks(Mon), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getMon().get(0)), "utf-8"))) {
                                                //Lịch học
                                                if (finalCount != 0) //Ngày học tìm kiếm != null
                                                {
                                                    for (String ngayHoc : khoaHoc.getLichHoc().getNgayHoc()) {
                                                        for (String ngayhoc : kh.getLichHoc().getNgayHoc()) {
                                                            if (ngayhoc.equals(ngayHoc)) {
                                                                dem++;
                                                                if (dem == finalCount) // Độ chính xác lịch học đạt mức chấp nhận được
                                                                {
                                                                    khoaHocGanChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));
                                                                }
                                                            }
                                                        }
                                                    }
                                                    if (dem >= finalCount) // Độ chính xác lịch học chấp nhận được hoặc rất chính xác
                                                    {
                                                        if (khoaHoc.getLichHoc().getThoiGian().size() != 0) // Buổi != null
                                                        {
                                                            for (String buoiHoc : khoaHoc.getLichHoc().getThoiGian()) {
                                                                for (String buoihoc : kh.getLichHoc().getThoiGian()) {
                                                                    if (buoiHoc.equals(buoihoc)) // Buổi
                                                                    {
                                                                        //Giới tính
                                                                        if (kh.getGioiTinh().equals(khoaHoc.getGioiTinh())) {
                                                                            for (String bangcap : kh.getBangCap()) {
                                                                                //Bằng cấp
                                                                                if (URLEncoder.encode(removeDiacriticalMarks(bangcap), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getBangCap().get(0)), "utf-8"))) {
                                                                                    //Địa điểm
                                                                                    if (kh.getDiaDiem().getQuan().equals(khoaHoc.getDiaDiem().getQuan())) {
                                                                                        khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));

                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        } else //Buổi == null
                                                        {
                                                            //Giới tính
                                                            if (kh.getGioiTinh().equals(khoaHoc.getGioiTinh())) {
                                                                for (String bangcap : kh.getBangCap()) {
                                                                    //Bằng cấp
                                                                    if (URLEncoder.encode(removeDiacriticalMarks(bangcap), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getBangCap().get(0)), "utf-8"))) {
                                                                        //Địa điểm
                                                                        if (kh.getDiaDiem().getQuan().equals(khoaHoc.getDiaDiem().getQuan())) {
                                                                            khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));

                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                } else  //Ngày học tìm kiếm == null
                                                {
                                                    if (khoaHoc.getLichHoc().getThoiGian().size() != 0) // Buổi != null
                                                    {
                                                        for (String buoiHoc : khoaHoc.getLichHoc().getThoiGian()) {
                                                            for (String buoihoc : kh.getLichHoc().getThoiGian()) {
                                                                if (buoiHoc.equals(buoihoc)) // Buổi
                                                                {
                                                                    //Giới tính
                                                                    if (kh.getGioiTinh().equals(khoaHoc.getGioiTinh())) {
                                                                        for (String bangcap : kh.getBangCap()) {
                                                                            //Bằng cấp
                                                                            if (URLEncoder.encode(removeDiacriticalMarks(bangcap), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getBangCap().get(0)), "utf-8"))) {
                                                                                //Địa điểm
                                                                                if (kh.getDiaDiem().getQuan().equals(khoaHoc.getDiaDiem().getQuan())) {
                                                                                    khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));

                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    } else //Buổi == null
                                                    {
                                                        //Giới tính
                                                        if (kh.getGioiTinh().equals(khoaHoc.getGioiTinh())) {
                                                            for (String bangcap : kh.getBangCap()) {
                                                                //Bằng cấp
                                                                if (URLEncoder.encode(removeDiacriticalMarks(bangcap), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getBangCap().get(0)), "utf-8"))) {
                                                                    //Địa điểm
                                                                    if (kh.getDiaDiem().getQuan().equals(khoaHoc.getDiaDiem().getQuan())) {
                                                                        khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));

                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                            ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHocGiaSu(khoaHocChinhXac, khoaHocGanChinhXac);
                                        } catch (UnsupportedEncodingException e) {
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
                        } else //5 Mon!=null, HocPhi==0,GioiTinh!=Nam, Nu,BangCap!=null, Quan = "Khác"
                        {
                            final double finalHocPhiDuoi = hocPhiDuoi;
                            final double finalHocPhiTren = hocPhiTren;
                            final int finalCount = count;
                            mData.getReference().child("KhoaHoc").child("KhoaHocTimHocVien").child("ChuaHoanTat").addChildEventListener(new ChildEventListener() {
                                @Override
                                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                    int dem = 0; // Xác định độ chính xác của lịch học (if dem == finaleCount)
                                    KhoaHoc kh = dataSnapshot.getValue(KhoaHoc.class);
                                    for (String Mon : kh.getMon()) {
                                        //Môn
                                        try {
                                            if (URLEncoder.encode(removeDiacriticalMarks(Mon), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getMon().get(0)), "utf-8"))) {
                                                //Lịch học
                                                if (finalCount != 0) //Ngày học tìm kiếm != null
                                                {
                                                    for (String ngayHoc : khoaHoc.getLichHoc().getNgayHoc()) {
                                                        for (String ngayhoc : kh.getLichHoc().getNgayHoc()) {
                                                            if (ngayhoc.equals(ngayHoc)) {
                                                                dem++;
                                                                if (dem == finalCount) // Độ chính xác lịch học đạt mức chấp nhận được
                                                                {
                                                                    khoaHocGanChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));
                                                                }
                                                            }
                                                        }
                                                    }
                                                    if (dem >= finalCount) // Độ chính xác lịch học chấp nhận được hoặc rất chính xác
                                                    {
                                                        if (khoaHoc.getLichHoc().getThoiGian().size() != 0) // Buổi != null
                                                        {
                                                            for (String buoiHoc : khoaHoc.getLichHoc().getThoiGian()) {
                                                                for (String buoihoc : kh.getLichHoc().getThoiGian()) {
                                                                    if (buoiHoc.equals(buoihoc)) // Buổi
                                                                    {
                                                                        //Giới tính
                                                                        if (kh.getGioiTinh().equals(khoaHoc.getGioiTinh())) {
                                                                            for (String bangcap : kh.getBangCap()) {
                                                                                //Bằng cấp
                                                                                if (URLEncoder.encode(removeDiacriticalMarks(bangcap), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getBangCap().get(0)), "utf-8"))) {
                                                                                    khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));

                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        } else //Buổi == null
                                                        {
                                                            //Giới tính
                                                            if (kh.getGioiTinh().equals(khoaHoc.getGioiTinh())) {
                                                                for (String bangcap : kh.getBangCap()) {
                                                                    //Bằng cấp
                                                                    if (URLEncoder.encode(removeDiacriticalMarks(bangcap), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getBangCap().get(0)), "utf-8"))) {
                                                                        khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));

                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                } else  //Ngày học tìm kiếm == null
                                                {
                                                    if (khoaHoc.getLichHoc().getThoiGian().size() != 0) // Buổi != null
                                                    {
                                                        for (String buoiHoc : khoaHoc.getLichHoc().getThoiGian()) {
                                                            for (String buoihoc : kh.getLichHoc().getThoiGian()) {
                                                                if (buoiHoc.equals(buoihoc)) // Buổi
                                                                {
                                                                    //Giới tính
                                                                    if (kh.getGioiTinh().equals(khoaHoc.getGioiTinh())) {
                                                                        for (String bangcap : kh.getBangCap()) {
                                                                            //Bằng cấp
                                                                            if (URLEncoder.encode(removeDiacriticalMarks(bangcap), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getBangCap().get(0)), "utf-8"))) {
                                                                                khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));

                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    } else //Buổi == null
                                                    {
                                                        //Giới tính
                                                        if (kh.getGioiTinh().equals(khoaHoc.getGioiTinh())) {
                                                            for (String bangcap : kh.getBangCap()) {
                                                                //Bằng cấp
                                                                if (URLEncoder.encode(removeDiacriticalMarks(bangcap), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getBangCap().get(0)), "utf-8"))) {
                                                                    khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));

                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                            ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHocGiaSu(khoaHocChinhXac, khoaHocGanChinhXac);
                                        } catch (UnsupportedEncodingException e) {
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
                    } else//4 Mon!=null, HocPhi==0,GioiTinh!=Nam, Nu,BangCap==null
                    {
                        if (khoaHoc.getDiaDiem().getQuan() != "Khác") //5 Mon!=null, HocPhi==0,GioiTinh!=Nam, Nu,BangCap==null,Quan != "Khác"
                        {
                            final double finalHocPhiDuoi = hocPhiDuoi;
                            final double finalHocPhiTren = hocPhiTren;
                            final int finalCount = count;
                            mData.getReference().child("KhoaHoc").child("KhoaHocTimHocVien").child("ChuaHoanTat").addChildEventListener(new ChildEventListener() {
                                @Override
                                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                    int dem = 0; // Xác định độ chính xác của lịch học (if dem == finaleCount)
                                    KhoaHoc kh = dataSnapshot.getValue(KhoaHoc.class);
                                    for (String Mon : kh.getMon()) {
                                        //Môn
                                        try {
                                            if (URLEncoder.encode(removeDiacriticalMarks(Mon), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getMon().get(0)), "utf-8"))) {
                                                //Lịch học
                                                if (finalCount != 0) //Ngày học tìm kiếm != null
                                                {
                                                    for (String ngayHoc : khoaHoc.getLichHoc().getNgayHoc()) {
                                                        for (String ngayhoc : kh.getLichHoc().getNgayHoc()) {
                                                            if (ngayhoc.equals(ngayHoc)) {
                                                                dem++;
                                                                if (dem == finalCount) // Độ chính xác lịch học đạt mức chấp nhận được
                                                                {
                                                                    khoaHocGanChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));
                                                                }
                                                            }
                                                        }
                                                    }
                                                    if (dem >= finalCount) // Độ chính xác lịch học chấp nhận được hoặc rất chính xác
                                                    {
                                                        if (khoaHoc.getLichHoc().getThoiGian().size() != 0) // Buổi != null
                                                        {
                                                            for (String buoiHoc : khoaHoc.getLichHoc().getThoiGian()) {
                                                                for (String buoihoc : kh.getLichHoc().getThoiGian()) {
                                                                    if (buoiHoc.equals(buoihoc)) // Buổi
                                                                    {
                                                                        //Giới tính
                                                                        if (kh.getGioiTinh().equals(khoaHoc.getGioiTinh())) {
                                                                            //Địa điểm
                                                                            if (kh.getDiaDiem().getQuan().equals(khoaHoc.getDiaDiem().getQuan())) {
                                                                                khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));

                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        } else //Buổi == null
                                                        {
                                                            //Giới tính
                                                            if (kh.getGioiTinh().equals(khoaHoc.getGioiTinh())) {
                                                                //Địa điểm
                                                                if (kh.getDiaDiem().getQuan().equals(khoaHoc.getDiaDiem().getQuan())) {
                                                                    khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));

                                                                }
                                                            }
                                                        }
                                                    }
                                                } else  //Ngày học tìm kiếm == null
                                                {
                                                    if (khoaHoc.getLichHoc().getThoiGian().size() != 0) // Buổi != null
                                                    {
                                                        for (String buoiHoc : khoaHoc.getLichHoc().getThoiGian()) {
                                                            for (String buoihoc : kh.getLichHoc().getThoiGian()) {
                                                                if (buoiHoc.equals(buoihoc)) // Buổi
                                                                {
                                                                    //Giới tính
                                                                    if (kh.getGioiTinh().equals(khoaHoc.getGioiTinh())) {
                                                                        //Địa điểm
                                                                        if (kh.getDiaDiem().getQuan().equals(khoaHoc.getDiaDiem().getQuan())) {
                                                                            khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));

                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    } else //Buổi == null
                                                    {
                                                        //Giới tính
                                                        if (kh.getGioiTinh().equals(khoaHoc.getGioiTinh())) {
                                                            //Địa điểm
                                                            if (kh.getDiaDiem().getQuan().equals(khoaHoc.getDiaDiem().getQuan())) {
                                                                khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));

                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                            ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHocGiaSu(khoaHocChinhXac, khoaHocGanChinhXac);
                                        } catch (UnsupportedEncodingException e) {
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
                        } else //5 Mon!=null, HocPhi==0,GioiTinh!=Nam, Nu,BangCap==null, Quan = "Khác"
                        {
                            final double finalHocPhiDuoi = hocPhiDuoi;
                            final double finalHocPhiTren = hocPhiTren;
                            final int finalCount = count;
                            mData.getReference().child("KhoaHoc").child("KhoaHocTimHocVien").child("ChuaHoanTat").addChildEventListener(new ChildEventListener() {
                                @Override
                                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                    int dem = 0; // Xác định độ chính xác của lịch học (if dem == finaleCount)
                                    KhoaHoc kh = dataSnapshot.getValue(KhoaHoc.class);
                                    for (String Mon : kh.getMon()) {
                                        //Môn
                                        try {
                                            if (URLEncoder.encode(removeDiacriticalMarks(Mon), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getMon().get(0)), "utf-8"))) {
                                                //Lịch học
                                                if (finalCount != 0) //Ngày học tìm kiếm != null
                                                {
                                                    for (String ngayHoc : khoaHoc.getLichHoc().getNgayHoc()) {
                                                        for (String ngayhoc : kh.getLichHoc().getNgayHoc()) {
                                                            if (ngayhoc.equals(ngayHoc)) {
                                                                dem++;
                                                                if (dem == finalCount) // Độ chính xác lịch học đạt mức chấp nhận được
                                                                {
                                                                    khoaHocGanChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));
                                                                }
                                                            }
                                                        }
                                                    }
                                                    if (dem >= finalCount) // Độ chính xác lịch học chấp nhận được hoặc rất chính xác
                                                    {
                                                        if (khoaHoc.getLichHoc().getThoiGian().size() != 0) // Buổi != null
                                                        {
                                                            for (String buoiHoc : khoaHoc.getLichHoc().getThoiGian()) {
                                                                for (String buoihoc : kh.getLichHoc().getThoiGian()) {
                                                                    if (buoiHoc.equals(buoihoc)) // Buổi
                                                                    {
                                                                        //Giới tính
                                                                        if (kh.getGioiTinh().equals(khoaHoc.getGioiTinh())) {
                                                                            khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));

                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        } else //Buổi == null
                                                        {
//Giới tính
                                                            if (kh.getGioiTinh().equals(khoaHoc.getGioiTinh())) {
                                                                khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));

                                                            }
                                                        }
                                                    }
                                                } else //Ngày học tìm kiếm == null
                                                {
                                                    if (khoaHoc.getLichHoc().getThoiGian().size() != 0) // Buổi != null
                                                    {
                                                        for (String buoiHoc : khoaHoc.getLichHoc().getThoiGian()) {
                                                            for (String buoihoc : kh.getLichHoc().getThoiGian()) {
                                                                if (buoiHoc.equals(buoihoc)) // Buổi
                                                                {
                                                                    //Giới tính
                                                                    if (kh.getGioiTinh().equals(khoaHoc.getGioiTinh())) {
                                                                        khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));

                                                                    }
                                                                }
                                                            }
                                                        }
                                                    } else //Buổi == null
                                                    {
                                                        //Giới tính
                                                        if (kh.getGioiTinh().equals(khoaHoc.getGioiTinh())) {
                                                            khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));

                                                        }
                                                    }
                                                }
                                            }
                                            ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHocGiaSu(khoaHocChinhXac, khoaHocGanChinhXac);
                                        } catch (UnsupportedEncodingException e) {
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
                } else //3 Mon!=null, HocPhi==0,GioiTinh==Nam, Nữ
                {
                    if (khoaHoc.getBangCap() != null) //4 Mon!=null, HocPhi==0,GioiTinh==Nam, Nữ,BangCap!=null
                    {
                        if (khoaHoc.getDiaDiem().getQuan() != "Khác") //5 Mon!=null, HocPhi==0,GioiTinh==Nam, Nữ,BangCap!=null,Quan != "Khác"
                        {
                            final double finalHocPhiDuoi = hocPhiDuoi;
                            final double finalHocPhiTren = hocPhiTren;
                            final int finalCount = count;
                            mData.getReference().child("KhoaHoc").child("KhoaHocTimHocVien").child("ChuaHoanTat").addChildEventListener(new ChildEventListener() {
                                @Override
                                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                    int dem = 0; // Xác định độ chính xác của lịch học (if dem == finaleCount)
                                    KhoaHoc kh = dataSnapshot.getValue(KhoaHoc.class);
                                    for (String Mon : kh.getMon()) {
                                        //Môn
                                        try {
                                            if (URLEncoder.encode(removeDiacriticalMarks(Mon), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getMon().get(0)), "utf-8"))) {
                                                //Lịch học
                                                if (finalCount != 0) //Ngày học tìm kiếm != null
                                                {
                                                    for (String ngayHoc : khoaHoc.getLichHoc().getNgayHoc()) {
                                                        for (String ngayhoc : kh.getLichHoc().getNgayHoc()) {
                                                            if (ngayhoc.equals(ngayHoc)) {
                                                                dem++;
                                                                if (dem == finalCount) // Độ chính xác lịch học đạt mức chấp nhận được
                                                                {
                                                                    khoaHocGanChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));
                                                                }
                                                            }
                                                        }
                                                    }
                                                    if (dem >= finalCount) // Độ chính xác lịch học chấp nhận được hoặc rất chính xác
                                                    {
                                                        if (khoaHoc.getLichHoc().getThoiGian().size() != 0) // Buổi != null
                                                        {
                                                            for (String buoiHoc : khoaHoc.getLichHoc().getThoiGian()) {
                                                                for (String buoihoc : kh.getLichHoc().getThoiGian()) {
                                                                    if (buoiHoc.equals(buoihoc)) // Buổi
                                                                    {
                                                                        for (String bangcap : kh.getBangCap()) {
                                                                            //Bằng cấp
                                                                            if (URLEncoder.encode(removeDiacriticalMarks(bangcap), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getBangCap().get(0)), "utf-8"))) {
                                                                                //Địa điểm
                                                                                if (kh.getDiaDiem().getQuan().equals(khoaHoc.getDiaDiem().getQuan())) {
                                                                                    khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));

                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        } else //Buổi == null
                                                        {
                                                            for (String bangcap : kh.getBangCap()) {
                                                                //Bằng cấp
                                                                if (URLEncoder.encode(removeDiacriticalMarks(bangcap), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getBangCap().get(0)), "utf-8"))) {
                                                                    //Địa điểm
                                                                    if (kh.getDiaDiem().getQuan().equals(khoaHoc.getDiaDiem().getQuan())) {
                                                                        khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));

                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                } else //Ngày học tìm kiếm == null
                                                {
                                                    if (khoaHoc.getLichHoc().getThoiGian().size() != 0) // Buổi != null
                                                    {
                                                        for (String buoiHoc : khoaHoc.getLichHoc().getThoiGian()) {
                                                            for (String buoihoc : kh.getLichHoc().getThoiGian()) {
                                                                if (buoiHoc.equals(buoihoc)) // Buổi
                                                                {
                                                                    for (String bangcap : kh.getBangCap()) {
                                                                        //Bằng cấp
                                                                        if (URLEncoder.encode(removeDiacriticalMarks(bangcap), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getBangCap().get(0)), "utf-8"))) {
                                                                            //Địa điểm
                                                                            if (kh.getDiaDiem().getQuan().equals(khoaHoc.getDiaDiem().getQuan())) {
                                                                                khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));

                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    } else //Buổi == null
                                                    {
                                                        for (String bangcap : kh.getBangCap()) {
                                                            //Bằng cấp
                                                            if (URLEncoder.encode(removeDiacriticalMarks(bangcap), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getBangCap().get(0)), "utf-8"))) {
                                                                //Địa điểm
                                                                if (kh.getDiaDiem().getQuan().equals(khoaHoc.getDiaDiem().getQuan())) {
                                                                    khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));

                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                            ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHocGiaSu(khoaHocChinhXac, khoaHocGanChinhXac);
                                        } catch (UnsupportedEncodingException e) {
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
                        } else //5 Mon!=null, HocPhi==0,GioiTinh==Nam, Nữ,BangCap!=null, Quan = "Khác"
                        {
                            final double finalHocPhiDuoi = hocPhiDuoi;
                            final double finalHocPhiTren = hocPhiTren;
                            final int finalCount = count;
                            mData.getReference().child("KhoaHoc").child("KhoaHocTimHocVien").child("ChuaHoanTat").addChildEventListener(new ChildEventListener() {
                                @Override
                                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                    int dem = 0; // Xác định độ chính xác của lịch học (if dem == finaleCount)
                                    KhoaHoc kh = dataSnapshot.getValue(KhoaHoc.class);
                                    for (String Mon : kh.getMon()) {
                                        //Môn
                                        try {
                                            if (URLEncoder.encode(removeDiacriticalMarks(Mon), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getMon().get(0)), "utf-8"))) {
                                                //Lịch học
                                                if (finalCount != 0) //Ngày học tìm kiếm != null
                                                {
                                                    for (String ngayHoc : khoaHoc.getLichHoc().getNgayHoc()) {
                                                        for (String ngayhoc : kh.getLichHoc().getNgayHoc()) {
                                                            if (ngayhoc.equals(ngayHoc)) {
                                                                dem++;
                                                                if (dem == finalCount) // Độ chính xác lịch học đạt mức chấp nhận được
                                                                {
                                                                    khoaHocGanChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));
                                                                }
                                                            }
                                                        }
                                                    }
                                                    if (dem >= finalCount) // Độ chính xác lịch học chấp nhận được hoặc rất chính xác
                                                    {
                                                        if (khoaHoc.getLichHoc().getThoiGian().size() != 0) // Buổi != null
                                                        {
                                                            for (String buoiHoc : khoaHoc.getLichHoc().getThoiGian()) {
                                                                for (String buoihoc : kh.getLichHoc().getThoiGian()) {
                                                                    if (buoiHoc.equals(buoihoc)) // Buổi
                                                                    {
                                                                        for (String bangcap : kh.getBangCap()) {
                                                                            //Bằng cấp
                                                                            if (URLEncoder.encode(removeDiacriticalMarks(bangcap), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getBangCap().get(0)), "utf-8"))) {
                                                                                khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));

                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        } else //Buổi == null
                                                        {
                                                            for (String bangcap : kh.getBangCap()) {
                                                                //Bằng cấp
                                                                if (URLEncoder.encode(removeDiacriticalMarks(bangcap), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getBangCap().get(0)), "utf-8"))) {
                                                                    khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));

                                                                }
                                                            }
                                                        }
                                                    }
                                                } else //Ngày học tìm kiếm ==null
                                                {
                                                    if (khoaHoc.getLichHoc().getThoiGian().size() != 0) // Buổi != null
                                                    {
                                                        for (String buoiHoc : khoaHoc.getLichHoc().getThoiGian()) {
                                                            for (String buoihoc : kh.getLichHoc().getThoiGian()) {
                                                                if (buoiHoc.equals(buoihoc)) // Buổi
                                                                {
                                                                    for (String bangcap : kh.getBangCap()) {
                                                                        //Bằng cấp
                                                                        if (URLEncoder.encode(removeDiacriticalMarks(bangcap), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getBangCap().get(0)), "utf-8"))) {
                                                                            khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));

                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    } else //Buổi == null
                                                    {
                                                        for (String bangcap : kh.getBangCap()) {
                                                            //Bằng cấp
                                                            if (URLEncoder.encode(removeDiacriticalMarks(bangcap), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getBangCap().get(0)), "utf-8"))) {
                                                                khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));

                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                            ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHocGiaSu(khoaHocChinhXac, khoaHocGanChinhXac);
                                        } catch (UnsupportedEncodingException e) {
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
                    } else//4 Mon!=null, HocPhi==0,GioiTinh==Nam, Nữ,BangCap==null
                    {
                        if (khoaHoc.getDiaDiem().getQuan() != "Khác") //5 Mon!=null, HocPhi==0,GioiTinh==Nam, Nữ,BangCap==null,Quan != "Khác"
                        {
                            final double finalHocPhiDuoi = hocPhiDuoi;
                            final double finalHocPhiTren = hocPhiTren;
                            final int finalCount = count;
                            mData.getReference().child("KhoaHoc").child("KhoaHocTimHocVien").child("ChuaHoanTat").addChildEventListener(new ChildEventListener() {
                                @Override
                                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                    int dem = 0; // Xác định độ chính xác của lịch học (if dem == finaleCount)
                                    KhoaHoc kh = dataSnapshot.getValue(KhoaHoc.class);
                                    for (String Mon : kh.getMon()) {
                                        try {
                                            if (URLEncoder.encode(removeDiacriticalMarks(Mon), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getMon().get(0)), "utf-8"))) {
                                                //Lịch học
                                                if (finalCount != 0) //Ngày học tìm kiếm != null
                                                {
                                                    for (String ngayHoc : khoaHoc.getLichHoc().getNgayHoc()) {
                                                        for (String ngayhoc : kh.getLichHoc().getNgayHoc()) {
                                                            if (ngayhoc.equals(ngayHoc)) {
                                                                dem++;
                                                                if (dem == finalCount) // Độ chính xác lịch học đạt mức chấp nhận được
                                                                {
                                                                    khoaHocGanChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));
                                                                }
                                                            }
                                                        }
                                                    }
                                                    if (dem >= finalCount) // Độ chính xác lịch học chấp nhận được hoặc rất chính xác
                                                    {
                                                        if (khoaHoc.getLichHoc().getThoiGian().size() != 0) // Buổi != null
                                                        {
                                                            for (String buoiHoc : khoaHoc.getLichHoc().getThoiGian()) {
                                                                for (String buoihoc : kh.getLichHoc().getThoiGian()) {
                                                                    if (buoiHoc.equals(buoihoc)) // Buổi
                                                                    {
                                                                        //Địa điểm
                                                                        if (kh.getDiaDiem().getQuan().equals(khoaHoc.getDiaDiem().getQuan())) {
                                                                            khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));

                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        } else //Buổi == null
                                                        {
//Địa điểm
                                                            if (kh.getDiaDiem().getQuan().equals(khoaHoc.getDiaDiem().getQuan())) {
                                                                khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));

                                                            }
                                                        }
                                                    }
                                                } else // Ngày học tìm kiếm == null
                                                {
                                                    if (khoaHoc.getLichHoc().getThoiGian().size() != 0) // Buổi != null
                                                    {
                                                        for (String buoiHoc : khoaHoc.getLichHoc().getThoiGian()) {
                                                            for (String buoihoc : kh.getLichHoc().getThoiGian()) {
                                                                if (buoiHoc.equals(buoihoc)) // Buổi
                                                                {
                                                                    //Địa điểm
                                                                    if (kh.getDiaDiem().getQuan().equals(khoaHoc.getDiaDiem().getQuan())) {
                                                                        khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));

                                                                    }
                                                                }
                                                            }
                                                        }
                                                    } else //Buổi == null
                                                    {
//Địa điểm
                                                        if (kh.getDiaDiem().getQuan().equals(khoaHoc.getDiaDiem().getQuan())) {
                                                            khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));

                                                        }
                                                    }
                                                }
                                            }
                                            ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHocGiaSu(khoaHocChinhXac, khoaHocGanChinhXac);
                                        } catch (UnsupportedEncodingException e) {
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
                        } else //5 Mon!=null, HocPhi==0,GioiTinh==Nam, Nữ,BangCap==null, Quan = "Khác"
                        {
                            final double finalHocPhiDuoi = hocPhiDuoi;
                            final double finalHocPhiTren = hocPhiTren;
                            final int finalCount = count;
                            mData.getReference().child("KhoaHoc").child("KhoaHocTimHocVien").child("ChuaHoanTat").addChildEventListener(new ChildEventListener() {
                                @Override
                                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                    int dem = 0; // Xác định độ chính xác của lịch học (if dem == finaleCount)
                                    KhoaHoc kh = dataSnapshot.getValue(KhoaHoc.class);
                                    for (String Mon : kh.getMon()) {
                                        //Môn
                                        try {
                                            if (URLEncoder.encode(removeDiacriticalMarks(Mon), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getMon().get(0)), "utf-8"))) {
                                                //Lịch học
                                                if (finalCount != 0) //Ngày học tìm kiếm != null
                                                {
                                                    for (String ngayHoc : khoaHoc.getLichHoc().getNgayHoc()) {
                                                        for (String ngayhoc : kh.getLichHoc().getNgayHoc()) {
                                                            if (ngayhoc.equals(ngayHoc)) {
                                                                dem++;
                                                                if (dem == finalCount) // Độ chính xác lịch học đạt mức chấp nhận được
                                                                {
                                                                    khoaHocGanChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));
                                                                }
                                                            }
                                                        }
                                                    }
                                                    if (dem >= finalCount) // Độ chính xác lịch học chấp nhận được hoặc rất chính xác
                                                    {
                                                        if (khoaHoc.getLichHoc().getThoiGian().size() != 0) // Buổi != null
                                                        {
                                                            for (String buoiHoc : khoaHoc.getLichHoc().getThoiGian()) {
                                                                for (String buoihoc : kh.getLichHoc().getThoiGian()) {
                                                                    if (buoiHoc.equals(buoihoc)) // Buổi
                                                                    {
                                                                        khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));

                                                                    }
                                                                }
                                                            }
                                                        } else //Buổi == null
                                                        {
                                                            khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));

                                                        }
                                                    }
                                                } else //Ngày học tìm kiếm == null
                                                {
                                                    if (khoaHoc.getLichHoc().getThoiGian().size() != 0) // Buổi != null
                                                    {
                                                        for (String buoiHoc : khoaHoc.getLichHoc().getThoiGian()) {
                                                            for (String buoihoc : kh.getLichHoc().getThoiGian()) {
                                                                if (buoiHoc.equals(buoihoc)) // Buổi
                                                                {
                                                                    khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));

                                                                }
                                                            }
                                                        }
                                                    } else //Buổi == null
                                                    {
                                                        khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));

                                                    }
                                                }
                                            }
                                            ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHocGiaSu(khoaHocChinhXac, khoaHocGanChinhXac);
                                        } catch (UnsupportedEncodingException e) {
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
            }
        } else//1 Mon == null
        {
            if (hocPhiTimKiem != 0) //2 Mon == null, HocPhi !=0
            {
                if (khoaHoc.getGioiTinh() != "Nam, Nữ") //3 Mon == null, HocPhi !=0,GioiTinh!=Nam, Nữ
                {
                    if (khoaHoc.getBangCap() != null) //4 Mon == null,HocPhi!=null,GioiTinh!=Nam, Nu,BangCap!=null
                    {
                        if (khoaHoc.getDiaDiem().getQuan() != "Khác") //5 Mon == null,HocPhi!=null,GioiTinh!=Nam, Nu,BangCap!=null,Quan != "Khác"
                        {
                            final double finalHocPhiDuoi = hocPhiDuoi;
                            final double finalHocPhiTren = hocPhiTren;
                            final int finalCount = count;
                            mData.getReference().child("KhoaHoc").child("KhoaHocTimHocVien").child("ChuaHoanTat").addChildEventListener(new ChildEventListener() {
                                @Override
                                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                    int dem = 0; // Xác định độ chính xác của lịch học (if dem == finaleCount)
                                    KhoaHoc kh = dataSnapshot.getValue(KhoaHoc.class);
                                    for (String linhvuc : kh.getLinhVuc()) {

                                        try {
                                            //LinhVuc
                                            if (URLEncoder.encode(removeDiacriticalMarks(linhvuc), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getLinhVuc().get(0)), "utf-8"))) {

                                                //Lịch học
                                                if (finalCount != 0) //Ngày học tìm kiếm != null
                                                {
                                                    for (String ngayHoc : khoaHoc.getLichHoc().getNgayHoc()) {
                                                        for (String ngayhoc : kh.getLichHoc().getNgayHoc()) {
                                                            if (ngayhoc.equals(ngayHoc)) {
                                                                dem++;
                                                                if (dem == finalCount) // Độ chính xác lịch học đạt mức chấp nhận được
                                                                {
                                                                    khoaHocGanChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));
                                                                }
                                                            }
                                                        }
                                                    }
                                                    if (dem >= finalCount) // Độ chính xác lịch học chấp nhận được hoặc rất chính xác
                                                    {
                                                        if (khoaHoc.getLichHoc().getThoiGian().size() != 0) // Buổi != null
                                                        {
                                                            for (String buoiHoc : khoaHoc.getLichHoc().getThoiGian()) {
                                                                for (String buoihoc : kh.getLichHoc().getThoiGian()) {
                                                                    if (buoiHoc.equals(buoihoc)) // Buổi
                                                                    {
                                                                        //Học phí
                                                                        if (Double.parseDouble(kh.getHocPhi()) >= finalHocPhiDuoi && Double.parseDouble(kh.getHocPhi()) <= finalHocPhiTren) {
                                                                            //Giới tính
                                                                            if (kh.getGioiTinh().equals(khoaHoc.getGioiTinh())) {
                                                                                for (String bangcap : kh.getBangCap()) {
                                                                                    //Bằng cấp
                                                                                    if (URLEncoder.encode(removeDiacriticalMarks(bangcap), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getBangCap().get(0)), "utf-8"))) {
                                                                                        //Địa điểm
                                                                                        if (kh.getDiaDiem().getQuan().equals(khoaHoc.getDiaDiem().getQuan())) {
                                                                                            khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));

                                                                                        }
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        } else //Buổi == null
                                                        {
//Học phí
                                                            if (Double.parseDouble(kh.getHocPhi()) >= finalHocPhiDuoi && Double.parseDouble(kh.getHocPhi()) <= finalHocPhiTren) {
                                                                //Giới tính
                                                                if (kh.getGioiTinh().equals(khoaHoc.getGioiTinh())) {
                                                                    for (String bangcap : kh.getBangCap()) {
                                                                        //Bằng cấp
                                                                        if (URLEncoder.encode(removeDiacriticalMarks(bangcap), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getBangCap().get(0)), "utf-8"))) {
                                                                            //Địa điểm
                                                                            if (kh.getDiaDiem().getQuan().equals(khoaHoc.getDiaDiem().getQuan())) {
                                                                                khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));

                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                } else //Ngày học tìm kiếm == null
                                                {
                                                    if (khoaHoc.getLichHoc().getThoiGian().size() != 0) // Buổi != null
                                                    {
                                                        for (String buoiHoc : khoaHoc.getLichHoc().getThoiGian()) {
                                                            for (String buoihoc : kh.getLichHoc().getThoiGian()) {
                                                                if (buoiHoc.equals(buoihoc)) // Buổi
                                                                {
                                                                    //Học phí
                                                                    if (Double.parseDouble(kh.getHocPhi()) >= finalHocPhiDuoi && Double.parseDouble(kh.getHocPhi()) <= finalHocPhiTren) {
                                                                        //Giới tính
                                                                        if (kh.getGioiTinh().equals(khoaHoc.getGioiTinh())) {
                                                                            for (String bangcap : kh.getBangCap()) {
                                                                                //Bằng cấp
                                                                                if (URLEncoder.encode(removeDiacriticalMarks(bangcap), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getBangCap().get(0)), "utf-8"))) {
                                                                                    //Địa điểm
                                                                                    if (kh.getDiaDiem().getQuan().equals(khoaHoc.getDiaDiem().getQuan())) {
                                                                                        khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));

                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    } else //Buổi == null
                                                    {
//Học phí
                                                        if (Double.parseDouble(kh.getHocPhi()) >= finalHocPhiDuoi && Double.parseDouble(kh.getHocPhi()) <= finalHocPhiTren) {
                                                            //Giới tính
                                                            if (kh.getGioiTinh().equals(khoaHoc.getGioiTinh())) {
                                                                for (String bangcap : kh.getBangCap()) {
                                                                    //Bằng cấp
                                                                    if (URLEncoder.encode(removeDiacriticalMarks(bangcap), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getBangCap().get(0)), "utf-8"))) {
                                                                        //Địa điểm
                                                                        if (kh.getDiaDiem().getQuan().equals(khoaHoc.getDiaDiem().getQuan())) {
                                                                            khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));

                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                            ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHocGiaSu(khoaHocChinhXac, khoaHocGanChinhXac);
                                        } catch (UnsupportedEncodingException e) {
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
                        } else //5 Mon == null,HocPhi!=null,GioiTinh!=Nam, Nu,BangCap!=null, Quan = "Khác"
                        {
                            final double finalHocPhiDuoi = hocPhiDuoi;
                            final double finalHocPhiTren = hocPhiTren;
                            final int finalCount = count;
                            mData.getReference().child("KhoaHoc").child("KhoaHocTimHocVien").child("ChuaHoanTat").addChildEventListener(new ChildEventListener() {
                                @Override
                                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                    int dem = 0; // Xác định độ chính xác của lịch học (if dem == finaleCount)
                                    KhoaHoc kh = dataSnapshot.getValue(KhoaHoc.class);
                                    for (String linhvuc : kh.getLinhVuc()) {
                                        //LinhVuc
                                        try {
                                            if (URLEncoder.encode(removeDiacriticalMarks(linhvuc), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getLinhVuc().get(0)), "utf-8"))) {
                                                //Lịch học
                                                if (finalCount != 0) //Ngày học tìm kiếm != null
                                                {
                                                    for (String ngayHoc : khoaHoc.getLichHoc().getNgayHoc()) {
                                                        for (String ngayhoc : kh.getLichHoc().getNgayHoc()) {
                                                            if (ngayhoc.equals(ngayHoc)) {
                                                                dem++;
                                                                if (dem == finalCount) // Độ chính xác lịch học đạt mức chấp nhận được
                                                                {
                                                                    khoaHocGanChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));
                                                                }
                                                            }
                                                        }
                                                    }
                                                    if (dem >= finalCount) // Độ chính xác lịch học chấp nhận được hoặc rất chính xác
                                                    {
                                                        if (khoaHoc.getLichHoc().getThoiGian().size() != 0) // Buổi != null
                                                        {
                                                            for (String buoiHoc : khoaHoc.getLichHoc().getThoiGian()) {
                                                                for (String buoihoc : kh.getLichHoc().getThoiGian()) {
                                                                    if (buoiHoc.equals(buoihoc)) // Buổi
                                                                    {
                                                                        //Học phí
                                                                        if (Double.parseDouble(kh.getHocPhi()) >= finalHocPhiDuoi && Double.parseDouble(kh.getHocPhi()) <= finalHocPhiTren) {
                                                                            //Giới tính
                                                                            if (kh.getGioiTinh().equals(khoaHoc.getGioiTinh())) {
                                                                                for (String bangcap : kh.getBangCap()) {
                                                                                    //Bằng cấp
                                                                                    if (URLEncoder.encode(removeDiacriticalMarks(bangcap), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getBangCap().get(0)), "utf-8"))) {
                                                                                        khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        } else //Buổi == null
                                                        {
//Học phí
                                                            if (Double.parseDouble(kh.getHocPhi()) >= finalHocPhiDuoi && Double.parseDouble(kh.getHocPhi()) <= finalHocPhiTren) {
                                                                //Giới tính
                                                                if (kh.getGioiTinh().equals(khoaHoc.getGioiTinh())) {
                                                                    for (String bangcap : kh.getBangCap()) {
                                                                        //Bằng cấp
                                                                        if (URLEncoder.encode(removeDiacriticalMarks(bangcap), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getBangCap().get(0)), "utf-8"))) {
                                                                            khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                } else //Ngày học tìm kiếm == null
                                                {
                                                    if (khoaHoc.getLichHoc().getThoiGian().size() != 0) // Buổi != null
                                                    {
                                                        for (String buoiHoc : khoaHoc.getLichHoc().getThoiGian()) {
                                                            for (String buoihoc : kh.getLichHoc().getThoiGian()) {
                                                                if (buoiHoc.equals(buoihoc)) // Buổi
                                                                {
                                                                    //Học phí
                                                                    if (Double.parseDouble(kh.getHocPhi()) >= finalHocPhiDuoi && Double.parseDouble(kh.getHocPhi()) <= finalHocPhiTren) {
                                                                        //Giới tính
                                                                        if (kh.getGioiTinh().equals(khoaHoc.getGioiTinh())) {
                                                                            for (String bangcap : kh.getBangCap()) {
                                                                                //Bằng cấp
                                                                                if (URLEncoder.encode(removeDiacriticalMarks(bangcap), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getBangCap().get(0)), "utf-8"))) {
                                                                                    khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    } else //Buổi == null
                                                    {
//Học phí
                                                        if (Double.parseDouble(kh.getHocPhi()) >= finalHocPhiDuoi && Double.parseDouble(kh.getHocPhi()) <= finalHocPhiTren) {
                                                            //Giới tính
                                                            if (kh.getGioiTinh().equals(khoaHoc.getGioiTinh())) {
                                                                for (String bangcap : kh.getBangCap()) {
                                                                    //Bằng cấp
                                                                    if (URLEncoder.encode(removeDiacriticalMarks(bangcap), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getBangCap().get(0)), "utf-8"))) {
                                                                        khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                            ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHocGiaSu(khoaHocChinhXac, khoaHocGanChinhXac);
                                        } catch (UnsupportedEncodingException e) {
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
                    } else//4 Mon == null,HocPhi!=null,GioiTinh!=Nam, Nu,BangCap==null
                    {
                        if (khoaHoc.getDiaDiem().getQuan() != "Khác") //5 Mon == null,HocPhi!=null,GioiTinh!=Nam, Nu,BangCap==null,Quan != "Khác"
                        {
                            final double finalHocPhiDuoi = hocPhiDuoi;
                            final double finalHocPhiTren = hocPhiTren;
                            final int finalCount = count;
                            mData.getReference().child("KhoaHoc").child("KhoaHocTimHocVien").child("ChuaHoanTat").addChildEventListener(new ChildEventListener() {
                                @Override
                                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                    int dem = 0; // Xác định độ chính xác của lịch học (if dem == finaleCount)
                                    KhoaHoc kh = dataSnapshot.getValue(KhoaHoc.class);
                                    for (String linhvuc : kh.getLinhVuc()) {
                                        //LinhVuc
                                        try {
                                            if (URLEncoder.encode(removeDiacriticalMarks(linhvuc), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getLinhVuc().get(0)), "utf-8"))) {
                                                //Lịch học
                                                if (finalCount != 0) //Ngày học tìm kiếm != null
                                                {
                                                    for (String ngayHoc : khoaHoc.getLichHoc().getNgayHoc()) {
                                                        for (String ngayhoc : kh.getLichHoc().getNgayHoc()) {
                                                            if (ngayhoc.equals(ngayHoc)) {
                                                                dem++;
                                                                if (dem == finalCount) // Độ chính xác lịch học đạt mức chấp nhận được
                                                                {
                                                                    khoaHocGanChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));
                                                                }
                                                            }
                                                        }
                                                    }
                                                    if (dem >= finalCount) // Độ chính xác lịch học chấp nhận được hoặc rất chính xác
                                                    {
                                                        if (khoaHoc.getLichHoc().getThoiGian().size() != 0) // Buổi != null
                                                        {
                                                            for (String buoiHoc : khoaHoc.getLichHoc().getThoiGian()) {
                                                                for (String buoihoc : kh.getLichHoc().getThoiGian()) {
                                                                    if (buoiHoc.equals(buoihoc)) // Buổi
                                                                    {
                                                                        //Học phí
                                                                        if (Double.parseDouble(kh.getHocPhi()) >= finalHocPhiDuoi && Double.parseDouble(kh.getHocPhi()) <= finalHocPhiTren) {
                                                                            //Giới tính
                                                                            if (kh.getGioiTinh().equals(khoaHoc.getGioiTinh())) {
                                                                                //Địa điểm
                                                                                if (kh.getDiaDiem().getQuan().equals(khoaHoc.getDiaDiem().getQuan())) {
                                                                                    khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));

                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        } else //Buoi = null
                                                        {
                                                            //Học phí
                                                            if (Double.parseDouble(kh.getHocPhi()) >= finalHocPhiDuoi && Double.parseDouble(kh.getHocPhi()) <= finalHocPhiTren) {
                                                                //Giới tính
                                                                if (kh.getGioiTinh().equals(khoaHoc.getGioiTinh())) {
                                                                    //Địa điểm
                                                                    if (kh.getDiaDiem().getQuan().equals(khoaHoc.getDiaDiem().getQuan())) {
                                                                        khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));

                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                } else//Ngày học tìm kiếm = null
                                                {
                                                    if (khoaHoc.getLichHoc().getThoiGian().size() != 0) // Buổi != null
                                                    {
                                                        for (String buoiHoc : khoaHoc.getLichHoc().getThoiGian()) {
                                                            for (String buoihoc : kh.getLichHoc().getThoiGian()) {
                                                                if (buoiHoc.equals(buoihoc)) // Buổi
                                                                {
                                                                    //Học phí
                                                                    if (Double.parseDouble(kh.getHocPhi()) >= finalHocPhiDuoi && Double.parseDouble(kh.getHocPhi()) <= finalHocPhiTren) {
                                                                        //Giới tính
                                                                        if (kh.getGioiTinh().equals(khoaHoc.getGioiTinh())) {
                                                                            //Địa điểm
                                                                            if (kh.getDiaDiem().getQuan().equals(khoaHoc.getDiaDiem().getQuan())) {
                                                                                khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));

                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    } else //Buoi = null
                                                    {
                                                        //Học phí
                                                        if (Double.parseDouble(kh.getHocPhi()) >= finalHocPhiDuoi && Double.parseDouble(kh.getHocPhi()) <= finalHocPhiTren) {
                                                            //Giới tính
                                                            if (kh.getGioiTinh().equals(khoaHoc.getGioiTinh())) {
                                                                //Địa điểm
                                                                if (kh.getDiaDiem().getQuan().equals(khoaHoc.getDiaDiem().getQuan())) {
                                                                    khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));

                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                            ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHocGiaSu(khoaHocChinhXac, khoaHocGanChinhXac);

                                        } catch (UnsupportedEncodingException e) {
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
                        } else //5 Mon == null,HocPhi!=null,GioiTinh!=Nam, Nu,BangCap==null, Quan = "Khác"
                        {
                            final double finalHocPhiDuoi = hocPhiDuoi;
                            final double finalHocPhiTren = hocPhiTren;
                            final int finalCount = count;
                            mData.getReference().child("KhoaHoc").child("KhoaHocTimHocVien").child("ChuaHoanTat").addChildEventListener(new ChildEventListener() {
                                @Override
                                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                    int dem = 0; // Xác định độ chính xác của lịch học (if dem == finaleCount)
                                    KhoaHoc kh = dataSnapshot.getValue(KhoaHoc.class);
                                    for (String linhvuc : kh.getLinhVuc()) {
                                        //LinhVuc
                                        try {
                                            if (URLEncoder.encode(removeDiacriticalMarks(linhvuc), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getLinhVuc().get(0)), "utf-8"))) {
//Lịch học
                                                if (finalCount != 0) //Ngày học tìm kiếm != null
                                                {
                                                    for (String ngayHoc : khoaHoc.getLichHoc().getNgayHoc()) {
                                                        for (String ngayhoc : kh.getLichHoc().getNgayHoc()) {
                                                            if (ngayhoc.equals(ngayHoc)) {
                                                                dem++;
                                                                if (dem == finalCount) // Độ chính xác lịch học đạt mức chấp nhận được
                                                                {
                                                                    khoaHocGanChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));
                                                                }
                                                            }
                                                        }
                                                    }
                                                    if (dem >= finalCount) // Độ chính xác lịch học chấp nhận được hoặc rất chính xác
                                                    {
                                                        if (khoaHoc.getLichHoc().getThoiGian().size() != 0) // Buổi != null
                                                        {
                                                            for (String buoiHoc : khoaHoc.getLichHoc().getThoiGian()) {
                                                                for (String buoihoc : kh.getLichHoc().getThoiGian()) {
                                                                    if (buoiHoc.equals(buoihoc)) // Buổi
                                                                    {
                                                                        //Học phí
                                                                        if (Double.parseDouble(kh.getHocPhi()) >= finalHocPhiDuoi && Double.parseDouble(kh.getHocPhi()) <= finalHocPhiTren) {
                                                                            //Giới tính
                                                                            if (kh.getGioiTinh().equals(khoaHoc.getGioiTinh())) {
                                                                                khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));

                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        } else//Buoi = null
                                                        {
                                                            //Học phí
                                                            if (Double.parseDouble(kh.getHocPhi()) >= finalHocPhiDuoi && Double.parseDouble(kh.getHocPhi()) <= finalHocPhiTren) {
                                                                //Giới tính
                                                                if (kh.getGioiTinh().equals(khoaHoc.getGioiTinh())) {
                                                                    khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));

                                                                }
                                                            }
                                                        }
                                                    }
                                                } else // Ngay = null
                                                {
                                                    if (khoaHoc.getLichHoc().getThoiGian().size() != 0) // Buổi != null
                                                    {
                                                        for (String buoiHoc : khoaHoc.getLichHoc().getThoiGian()) {
                                                            for (String buoihoc : kh.getLichHoc().getThoiGian()) {
                                                                if (buoiHoc.equals(buoihoc)) // Buổi
                                                                {
                                                                    //Học phí
                                                                    if (Double.parseDouble(kh.getHocPhi()) >= finalHocPhiDuoi && Double.parseDouble(kh.getHocPhi()) <= finalHocPhiTren) {
                                                                        //Giới tính
                                                                        if (kh.getGioiTinh().equals(khoaHoc.getGioiTinh())) {
                                                                            khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));

                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    } else//Buoi = null
                                                    {
                                                        //Học phí
                                                        if (Double.parseDouble(kh.getHocPhi()) >= finalHocPhiDuoi && Double.parseDouble(kh.getHocPhi()) <= finalHocPhiTren) {
                                                            //Giới tính
                                                            if (kh.getGioiTinh().equals(khoaHoc.getGioiTinh())) {
                                                                khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));

                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                            ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHocGiaSu(khoaHocChinhXac, khoaHocGanChinhXac);

                                        } catch (UnsupportedEncodingException e) {
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
                } else //3 Mon == null,HocPhi!=0,GioiTinh==Nam, Nữ
                {
                    if (khoaHoc.getBangCap() != null) //4Mon == null,HocPhi!=0,GioiTinh==Nam, Nữ,BangCap!=null
                    {
                        if (khoaHoc.getDiaDiem().getQuan() != "Khác") //5 Mon == null,HocPhi!=0,GioiTinh==Nam, Nữ,BangCap!=null,Quan != "Khác"
                        {
                            final double finalHocPhiDuoi = hocPhiDuoi;
                            final double finalHocPhiTren = hocPhiTren;
                            final int finalCount = count;
                            mData.getReference().child("KhoaHoc").child("KhoaHocTimHocVien").child("ChuaHoanTat").addChildEventListener(new ChildEventListener() {
                                @Override
                                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                    int dem = 0; // Xác định độ chính xác của lịch học (if dem == finaleCount)
                                    KhoaHoc kh = dataSnapshot.getValue(KhoaHoc.class);
                                    for (String linhvuc : kh.getLinhVuc()) {
                                        try {
                                            if (URLEncoder.encode(removeDiacriticalMarks(linhvuc), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getLinhVuc().get(0)), "utf-8"))) {
                                                //Lịch học
                                                if (finalCount != 0) //Ngày học tìm kiếm != null
                                                {
                                                    for (String ngayHoc : khoaHoc.getLichHoc().getNgayHoc()) {
                                                        for (String ngayhoc : kh.getLichHoc().getNgayHoc()) {
                                                            if (ngayhoc.equals(ngayHoc)) {
                                                                dem++;
                                                                if (dem == finalCount) // Độ chính xác lịch học đạt mức chấp nhận được
                                                                {
                                                                    khoaHocGanChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));
                                                                }
                                                            }
                                                        }
                                                    }
                                                    if (dem >= finalCount) // Độ chính xác lịch học chấp nhận được hoặc rất chính xác
                                                    {
                                                        if (khoaHoc.getLichHoc().getThoiGian().size() != 0) // Buổi != null
                                                        {
                                                            for (String buoiHoc : khoaHoc.getLichHoc().getThoiGian()) {
                                                                for (String buoihoc : kh.getLichHoc().getThoiGian()) {
                                                                    if (buoiHoc.equals(buoihoc)) // Buổi
                                                                    {
                                                                        //Học phí
                                                                        if (Double.parseDouble(kh.getHocPhi()) >= finalHocPhiDuoi && Double.parseDouble(kh.getHocPhi()) <= finalHocPhiTren) {
                                                                            for (String bangcap : kh.getBangCap()) {
                                                                                //Bằng cấp
                                                                                if (URLEncoder.encode(removeDiacriticalMarks(bangcap), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getBangCap().get(0)), "utf-8"))) {
                                                                                    //Địa điểm
                                                                                    if (kh.getDiaDiem().getQuan().equals(khoaHoc.getDiaDiem().getQuan())) {
                                                                                        khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));

                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        } else //Buoi = null
                                                        {
                                                            //Học phí
                                                            if (Double.parseDouble(kh.getHocPhi()) >= finalHocPhiDuoi && Double.parseDouble(kh.getHocPhi()) <= finalHocPhiTren) {
                                                                for (String bangcap : kh.getBangCap()) {
                                                                    //Bằng cấp
                                                                    if (URLEncoder.encode(removeDiacriticalMarks(bangcap), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getBangCap().get(0)), "utf-8"))) {
                                                                        //Địa điểm
                                                                        if (kh.getDiaDiem().getQuan().equals(khoaHoc.getDiaDiem().getQuan())) {
                                                                            khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));

                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                } else//Ngauy = null
                                                {
                                                    if (khoaHoc.getLichHoc().getThoiGian().size() != 0) // Buổi != null
                                                    {
                                                        for (String buoiHoc : khoaHoc.getLichHoc().getThoiGian()) {
                                                            for (String buoihoc : kh.getLichHoc().getThoiGian()) {
                                                                if (buoiHoc.equals(buoihoc)) // Buổi
                                                                {
                                                                    //Học phí
                                                                    if (Double.parseDouble(kh.getHocPhi()) >= finalHocPhiDuoi && Double.parseDouble(kh.getHocPhi()) <= finalHocPhiTren) {
                                                                        for (String bangcap : kh.getBangCap()) {
                                                                            //Bằng cấp
                                                                            if (URLEncoder.encode(removeDiacriticalMarks(bangcap), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getBangCap().get(0)), "utf-8"))) {
                                                                                //Địa điểm
                                                                                if (kh.getDiaDiem().getQuan().equals(khoaHoc.getDiaDiem().getQuan())) {
                                                                                    khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));

                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    } else //Buoi = null
                                                    {
                                                        //Học phí
                                                        if (Double.parseDouble(kh.getHocPhi()) >= finalHocPhiDuoi && Double.parseDouble(kh.getHocPhi()) <= finalHocPhiTren) {
                                                            for (String bangcap : kh.getBangCap()) {
                                                                //Bằng cấp
                                                                if (URLEncoder.encode(removeDiacriticalMarks(bangcap), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getBangCap().get(0)), "utf-8"))) {
                                                                    //Địa điểm
                                                                    if (kh.getDiaDiem().getQuan().equals(khoaHoc.getDiaDiem().getQuan())) {
                                                                        khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));

                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                            ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHocGiaSu(khoaHocChinhXac, khoaHocGanChinhXac);

                                        } catch (UnsupportedEncodingException e) {
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
                        } else //5 Mon == null,HocPhi!=0,GioiTinh==Nam, Nữ,BangCap!=null, Quan = "Khác"
                        {
                            final double finalHocPhiDuoi = hocPhiDuoi;
                            final double finalHocPhiTren = hocPhiTren;
                            final int finalCount = count;
                            mData.getReference().child("KhoaHoc").child("KhoaHocTimHocVien").child("ChuaHoanTat").addChildEventListener(new ChildEventListener() {
                                @Override
                                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                    int dem = 0; // Xác định độ chính xác của lịch học (if dem == finaleCount)
                                    KhoaHoc kh = dataSnapshot.getValue(KhoaHoc.class);
                                    for (String linhvuc : kh.getLinhVuc()) {
                                        //LinhVuc
                                        try {
                                            if (URLEncoder.encode(removeDiacriticalMarks(linhvuc), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getLinhVuc().get(0)), "utf-8"))) {
//Lịch học
                                                if (finalCount != 0) //Ngày học tìm kiếm != null
                                                {
                                                    for (String ngayHoc : khoaHoc.getLichHoc().getNgayHoc()) {
                                                        for (String ngayhoc : kh.getLichHoc().getNgayHoc()) {
                                                            if (ngayhoc.equals(ngayHoc)) {
                                                                dem++;
                                                                if (dem == finalCount) // Độ chính xác lịch học đạt mức chấp nhận được
                                                                {
                                                                    khoaHocGanChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));
                                                                }
                                                            }
                                                        }
                                                    }
                                                    if (dem >= finalCount) // Độ chính xác lịch học chấp nhận được hoặc rất chính xác
                                                    {
                                                        if (khoaHoc.getLichHoc().getThoiGian().size() != 0) // Buổi != null
                                                        {
                                                            for (String buoiHoc : khoaHoc.getLichHoc().getThoiGian()) {
                                                                for (String buoihoc : kh.getLichHoc().getThoiGian()) {
                                                                    if (buoiHoc.equals(buoihoc)) // Buổi
                                                                    {
                                                                        //Học phí
                                                                        if (Double.parseDouble(kh.getHocPhi()) >= finalHocPhiDuoi && Double.parseDouble(kh.getHocPhi()) <= finalHocPhiTren) {
                                                                            for (String bangcap : kh.getBangCap()) {
                                                                                //Bằng cấp
                                                                                if (URLEncoder.encode(removeDiacriticalMarks(bangcap), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getBangCap().get(0)), "utf-8"))) {
                                                                                    khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));

                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        } else //Buoi ==null
                                                        {
//Học phí
                                                            if (Double.parseDouble(kh.getHocPhi()) >= finalHocPhiDuoi && Double.parseDouble(kh.getHocPhi()) <= finalHocPhiTren) {
                                                                for (String bangcap : kh.getBangCap()) {
                                                                    //Bằng cấp
                                                                    if (URLEncoder.encode(removeDiacriticalMarks(bangcap), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getBangCap().get(0)), "utf-8"))) {
                                                                        khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));

                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                } else //Ngay ==null
                                                {
                                                    if (khoaHoc.getLichHoc().getThoiGian().size() != 0) // Buổi != null
                                                    {
                                                        for (String buoiHoc : khoaHoc.getLichHoc().getThoiGian()) {
                                                            for (String buoihoc : kh.getLichHoc().getThoiGian()) {
                                                                if (buoiHoc.equals(buoihoc)) // Buổi
                                                                {
                                                                    //Học phí
                                                                    if (Double.parseDouble(kh.getHocPhi()) >= finalHocPhiDuoi && Double.parseDouble(kh.getHocPhi()) <= finalHocPhiTren) {
                                                                        for (String bangcap : kh.getBangCap()) {
                                                                            //Bằng cấp
                                                                            if (URLEncoder.encode(removeDiacriticalMarks(bangcap), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getBangCap().get(0)), "utf-8"))) {
                                                                                khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));

                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    } else //Buoi ==null
                                                    {
//Học phí
                                                        if (Double.parseDouble(kh.getHocPhi()) >= finalHocPhiDuoi && Double.parseDouble(kh.getHocPhi()) <= finalHocPhiTren) {
                                                            for (String bangcap : kh.getBangCap()) {
                                                                //Bằng cấp
                                                                if (URLEncoder.encode(removeDiacriticalMarks(bangcap), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getBangCap().get(0)), "utf-8"))) {
                                                                    khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));

                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                            ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHocGiaSu(khoaHocChinhXac, khoaHocGanChinhXac);

                                        } catch (UnsupportedEncodingException e) {
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
                    } else//4 Mon == null,HocPhi!=0,GioiTinh==Nam, Nữ,BangCap==null
                    {
                        if (khoaHoc.getDiaDiem().getQuan() != "Khác") //5 Mon == null,HocPhi!=0,GioiTinh==Nam, Nữ,BangCap==null,Quan != "Khác"
                        {
                            final double finalHocPhiDuoi = hocPhiDuoi;
                            final double finalHocPhiTren = hocPhiTren;
                            final int finalCount = count;
                            mData.getReference().child("KhoaHoc").child("KhoaHocTimHocVien").child("ChuaHoanTat").addChildEventListener(new ChildEventListener() {
                                @Override
                                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                    int dem = 0; // Xác định độ chính xác của lịch học (if dem == finaleCount)
                                    KhoaHoc kh = dataSnapshot.getValue(KhoaHoc.class);
                                    for (String linhvuc : kh.getLinhVuc()) {
                                        //LinhVuc
                                        try {
                                            if (URLEncoder.encode(removeDiacriticalMarks(linhvuc), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getLinhVuc().get(0)), "utf-8"))) {
                                                //Lịch học
                                                if (finalCount != 0) //Ngày học tìm kiếm != null
                                                {
                                                    for (String ngayHoc : khoaHoc.getLichHoc().getNgayHoc()) {
                                                        for (String ngayhoc : kh.getLichHoc().getNgayHoc()) {
                                                            if (ngayhoc.equals(ngayHoc)) {
                                                                dem++;
                                                                if (dem == finalCount) // Độ chính xác lịch học đạt mức chấp nhận được
                                                                {
                                                                    khoaHocGanChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));
                                                                }
                                                            }
                                                        }
                                                    }
                                                    if (dem >= finalCount) // Độ chính xác lịch học chấp nhận được hoặc rất chính xác
                                                    {
                                                        if (khoaHoc.getLichHoc().getThoiGian().size() != 0) // Buổi != null
                                                        {
                                                            for (String buoiHoc : khoaHoc.getLichHoc().getThoiGian()) {
                                                                for (String buoihoc : kh.getLichHoc().getThoiGian()) {
                                                                    if (buoiHoc.equals(buoihoc)) // Buổi
                                                                    {
                                                                        //Học phí
                                                                        if (Double.parseDouble(kh.getHocPhi()) >= finalHocPhiDuoi && Double.parseDouble(kh.getHocPhi()) <= finalHocPhiTren) {
                                                                            //Địa điểm
                                                                            if (kh.getDiaDiem().getQuan().equals(khoaHoc.getDiaDiem().getQuan())) {
                                                                                khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));

                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        } else//Buoi ==null
                                                        {
//Học phí
                                                            if (Double.parseDouble(kh.getHocPhi()) >= finalHocPhiDuoi && Double.parseDouble(kh.getHocPhi()) <= finalHocPhiTren) {
                                                                //Địa điểm
                                                                if (kh.getDiaDiem().getQuan().equals(khoaHoc.getDiaDiem().getQuan())) {
                                                                    khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));

                                                                }
                                                            }
                                                        }
                                                    }
                                                } else//Ngay = null
                                                {
                                                    if (khoaHoc.getLichHoc().getThoiGian().size() != 0) // Buổi != null
                                                    {
                                                        for (String buoiHoc : khoaHoc.getLichHoc().getThoiGian()) {
                                                            for (String buoihoc : kh.getLichHoc().getThoiGian()) {
                                                                if (buoiHoc.equals(buoihoc)) // Buổi
                                                                {
                                                                    //Học phí
                                                                    if (Double.parseDouble(kh.getHocPhi()) >= finalHocPhiDuoi && Double.parseDouble(kh.getHocPhi()) <= finalHocPhiTren) {
                                                                        //Địa điểm
                                                                        if (kh.getDiaDiem().getQuan().equals(khoaHoc.getDiaDiem().getQuan())) {
                                                                            khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));

                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    } else//Buoi ==null
                                                    {
//Học phí
                                                        if (Double.parseDouble(kh.getHocPhi()) >= finalHocPhiDuoi && Double.parseDouble(kh.getHocPhi()) <= finalHocPhiTren) {
                                                            //Địa điểm
                                                            if (kh.getDiaDiem().getQuan().equals(khoaHoc.getDiaDiem().getQuan())) {
                                                                khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));

                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                            ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHocGiaSu(khoaHocChinhXac, khoaHocGanChinhXac);
                                        } catch (UnsupportedEncodingException e) {
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
                        } else //5 Mon == null,HocPhi!=0,GioiTinh==Nam, Nữ,BangCap==null, Quan = "Khác"
                        {
                            final double finalHocPhiDuoi = hocPhiDuoi;
                            final double finalHocPhiTren = hocPhiTren;
                            final int finalCount = count;
                            mData.getReference().child("KhoaHoc").child("KhoaHocTimHocVien").child("ChuaHoanTat").addChildEventListener(new ChildEventListener() {
                                @Override
                                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                    int dem = 0; // Xác định độ chính xác của lịch học (if dem == finaleCount)
                                    KhoaHoc kh = dataSnapshot.getValue(KhoaHoc.class);
                                    for (String linhvuc : kh.getLinhVuc()) {
                                        //LinhVuc
                                        try {
                                            if (URLEncoder.encode(removeDiacriticalMarks(linhvuc), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getLinhVuc().get(0)), "utf-8"))) {
                                                //Lịch học
                                                if (finalCount != 0) //Ngày học tìm kiếm != null
                                                {
                                                    for (String ngayHoc : khoaHoc.getLichHoc().getNgayHoc()) {
                                                        for (String ngayhoc : kh.getLichHoc().getNgayHoc()) {
                                                            if (ngayhoc.equals(ngayHoc)) {
                                                                dem++;
                                                                if (dem == finalCount) // Độ chính xác lịch học đạt mức chấp nhận được
                                                                {
                                                                    khoaHocGanChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));
                                                                }
                                                            }
                                                        }
                                                    }
                                                    if (dem >= finalCount) // Độ chính xác lịch học chấp nhận được hoặc rất chính xác
                                                    {
                                                        if (khoaHoc.getLichHoc().getThoiGian().size() != 0) // Buổi != null
                                                        {
                                                            for (String buoiHoc : khoaHoc.getLichHoc().getThoiGian()) {
                                                                for (String buoihoc : kh.getLichHoc().getThoiGian()) {
                                                                    if (buoiHoc.equals(buoihoc)) // Buổi
                                                                    {
                                                                        //Học phí
                                                                        if (Double.parseDouble(kh.getHocPhi()) >= finalHocPhiDuoi && Double.parseDouble(kh.getHocPhi()) <= finalHocPhiTren) {
                                                                            khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));

                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        } else//Buoi = null
                                                        {
//Học phí
                                                            if (Double.parseDouble(kh.getHocPhi()) >= finalHocPhiDuoi && Double.parseDouble(kh.getHocPhi()) <= finalHocPhiTren) {
                                                                khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));

                                                            }
                                                        }
                                                    }
                                                } else//Ngay == null
                                                {
                                                    if (khoaHoc.getLichHoc().getThoiGian().size() != 0) // Buổi != null
                                                    {
                                                        for (String buoiHoc : khoaHoc.getLichHoc().getThoiGian()) {
                                                            for (String buoihoc : kh.getLichHoc().getThoiGian()) {
                                                                if (buoiHoc.equals(buoihoc)) // Buổi
                                                                {
                                                                    //Học phí
                                                                    if (Double.parseDouble(kh.getHocPhi()) >= finalHocPhiDuoi && Double.parseDouble(kh.getHocPhi()) <= finalHocPhiTren) {
                                                                        khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));

                                                                    }
                                                                }
                                                            }
                                                        }
                                                    } else//Buoi = null
                                                    {
//Học phí
                                                        if (Double.parseDouble(kh.getHocPhi()) >= finalHocPhiDuoi && Double.parseDouble(kh.getHocPhi()) <= finalHocPhiTren) {
                                                            khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));

                                                        }
                                                    }
                                                }
                                            }
                                            ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHocGiaSu(khoaHocChinhXac, khoaHocGanChinhXac);

                                        } catch (UnsupportedEncodingException e) {
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
            } else//2 Mon == null, HocPhi==0
            {
                if (khoaHoc.getGioiTinh() != "Nam, Nữ") //3 Mon == null, HocPhi==0,GioiTinh!=Nam, Nữ
                {
                    if (khoaHoc.getBangCap() != null) //4 Mon == null, HocPhi==0,GioiTinh!=Nam, Nu,BangCap!=null
                    {
                        if (khoaHoc.getDiaDiem().getQuan() != "Khác") //5 Mon == null, HocPhi==0,GioiTinh!=Nam, Nu,BangCap!=null,Quan != "Khác"
                        {
                            final double finalHocPhiDuoi = hocPhiDuoi;
                            final double finalHocPhiTren = hocPhiTren;
                            final int finalCount = count;
                            mData.getReference().child("KhoaHoc").child("KhoaHocTimHocVien").child("ChuaHoanTat").addChildEventListener(new ChildEventListener() {
                                @Override
                                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                    int dem = 0; // Xác định độ chính xác của lịch học (if dem == finaleCount)
                                    KhoaHoc kh = dataSnapshot.getValue(KhoaHoc.class);
                                    for (String linhvuc : kh.getLinhVuc()) {
                                        //LinhVuc
                                        try {
                                            if (URLEncoder.encode(removeDiacriticalMarks(linhvuc), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getLinhVuc().get(0)), "utf-8"))) {
//Lịch học
                                                if (finalCount != 0) //Ngày học tìm kiếm != null
                                                {
                                                    for (String ngayHoc : khoaHoc.getLichHoc().getNgayHoc()) {
                                                        for (String ngayhoc : kh.getLichHoc().getNgayHoc()) {
                                                            if (ngayhoc.equals(ngayHoc)) {
                                                                dem++;
                                                                if (dem == finalCount) // Độ chính xác lịch học đạt mức chấp nhận được
                                                                {
                                                                    khoaHocGanChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));
                                                                }
                                                            }
                                                        }
                                                    }
                                                    if (dem >= finalCount) // Độ chính xác lịch học chấp nhận được hoặc rất chính xác
                                                    {
                                                        if (khoaHoc.getLichHoc().getThoiGian().size() != 0) // Buổi != null
                                                        {
                                                            for (String buoiHoc : khoaHoc.getLichHoc().getThoiGian()) {
                                                                for (String buoihoc : kh.getLichHoc().getThoiGian()) {
                                                                    if (buoiHoc.equals(buoihoc)) // Buổi
                                                                    {
                                                                        //Giới tính
                                                                        if (kh.getGioiTinh().equals(khoaHoc.getGioiTinh())) {
                                                                            for (String bangcap : kh.getBangCap()) {
                                                                                //Bằng cấp
                                                                                if (URLEncoder.encode(removeDiacriticalMarks(bangcap), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getBangCap().get(0)), "utf-8"))) {
                                                                                    //Địa điểm
                                                                                    if (kh.getDiaDiem().getQuan().equals(khoaHoc.getDiaDiem().getQuan())) {
                                                                                        khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));

                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        } else //Buoi == null
                                                        {
//Giới tính
                                                            if (kh.getGioiTinh().equals(khoaHoc.getGioiTinh())) {
                                                                for (String bangcap : kh.getBangCap()) {
                                                                    //Bằng cấp
                                                                    if (URLEncoder.encode(removeDiacriticalMarks(bangcap), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getBangCap().get(0)), "utf-8"))) {
                                                                        //Địa điểm
                                                                        if (kh.getDiaDiem().getQuan().equals(khoaHoc.getDiaDiem().getQuan())) {
                                                                            khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));

                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                } else //Ngay == null
                                                {
                                                    if (khoaHoc.getLichHoc().getThoiGian().size() != 0) // Buổi != null
                                                    {
                                                        for (String buoiHoc : khoaHoc.getLichHoc().getThoiGian()) {
                                                            for (String buoihoc : kh.getLichHoc().getThoiGian()) {
                                                                if (buoiHoc.equals(buoihoc)) // Buổi
                                                                {
                                                                    //Giới tính
                                                                    if (kh.getGioiTinh().equals(khoaHoc.getGioiTinh())) {
                                                                        for (String bangcap : kh.getBangCap()) {
                                                                            //Bằng cấp
                                                                            if (URLEncoder.encode(removeDiacriticalMarks(bangcap), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getBangCap().get(0)), "utf-8"))) {
                                                                                //Địa điểm
                                                                                if (kh.getDiaDiem().getQuan().equals(khoaHoc.getDiaDiem().getQuan())) {
                                                                                    khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));

                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    } else //Buoi == null
                                                    {
//Giới tính
                                                        if (kh.getGioiTinh().equals(khoaHoc.getGioiTinh())) {
                                                            for (String bangcap : kh.getBangCap()) {
                                                                //Bằng cấp
                                                                if (URLEncoder.encode(removeDiacriticalMarks(bangcap), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getBangCap().get(0)), "utf-8"))) {
                                                                    //Địa điểm
                                                                    if (kh.getDiaDiem().getQuan().equals(khoaHoc.getDiaDiem().getQuan())) {
                                                                        khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));

                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                            ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHocGiaSu(khoaHocChinhXac, khoaHocGanChinhXac);

                                        } catch (UnsupportedEncodingException e) {
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
                        } else //5 Mon == null, HocPhi==0,GioiTinh!=Nam, Nu,BangCap!=null, Quan = "Khác"
                        {
                            final double finalHocPhiDuoi = hocPhiDuoi;
                            final double finalHocPhiTren = hocPhiTren;
                            final int finalCount = count;
                            mData.getReference().child("KhoaHoc").child("KhoaHocTimHocVien").child("ChuaHoanTat").addChildEventListener(new ChildEventListener() {
                                @Override
                                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                    int dem = 0; // Xác định độ chính xác của lịch học (if dem == finaleCount)
                                    KhoaHoc kh = dataSnapshot.getValue(KhoaHoc.class);
                                    for (String linhvuc : kh.getLinhVuc()) {
                                        //LinhVuc
                                        try {
                                            if (URLEncoder.encode(removeDiacriticalMarks(linhvuc), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getLinhVuc().get(0)), "utf-8"))) {
//Lịch học
                                                if (finalCount != 0) //Ngày học tìm kiếm != null
                                                {
                                                    for (String ngayHoc : khoaHoc.getLichHoc().getNgayHoc()) {
                                                        for (String ngayhoc : kh.getLichHoc().getNgayHoc()) {
                                                            if (ngayhoc.equals(ngayHoc)) {
                                                                dem++;
                                                                if (dem == finalCount) // Độ chính xác lịch học đạt mức chấp nhận được
                                                                {
                                                                    khoaHocGanChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));
                                                                }
                                                            }
                                                        }
                                                    }
                                                    if (dem >= finalCount) // Độ chính xác lịch học chấp nhận được hoặc rất chính xác
                                                    {
                                                        if (khoaHoc.getLichHoc().getThoiGian().size() != 0) // Buổi != null
                                                        {
                                                            for (String buoiHoc : khoaHoc.getLichHoc().getThoiGian()) {
                                                                for (String buoihoc : kh.getLichHoc().getThoiGian()) {
                                                                    if (buoiHoc.equals(buoihoc)) // Buổi
                                                                    {
                                                                        //Giới tính
                                                                        if (kh.getGioiTinh().equals(khoaHoc.getGioiTinh())) {
                                                                            for (String bangcap : kh.getBangCap()) {
                                                                                //Bằng cấp
                                                                                if (URLEncoder.encode(removeDiacriticalMarks(bangcap), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getBangCap().get(0)), "utf-8"))) {
                                                                                    khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));

                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        } else //Buoi == null
                                                        {
//Giới tính
                                                            if (kh.getGioiTinh().equals(khoaHoc.getGioiTinh())) {
                                                                for (String bangcap : kh.getBangCap()) {
                                                                    //Bằng cấp
                                                                    if (URLEncoder.encode(removeDiacriticalMarks(bangcap), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getBangCap().get(0)), "utf-8"))) {
                                                                        khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));

                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                } else //Ngay == null
                                                {
                                                    if (khoaHoc.getLichHoc().getThoiGian().size() != 0) // Buổi != null
                                                    {
                                                        for (String buoiHoc : khoaHoc.getLichHoc().getThoiGian()) {
                                                            for (String buoihoc : kh.getLichHoc().getThoiGian()) {
                                                                if (buoiHoc.equals(buoihoc)) // Buổi
                                                                {
                                                                    //Giới tính
                                                                    if (kh.getGioiTinh().equals(khoaHoc.getGioiTinh())) {
                                                                        for (String bangcap : kh.getBangCap()) {
                                                                            //Bằng cấp
                                                                            if (URLEncoder.encode(removeDiacriticalMarks(bangcap), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getBangCap().get(0)), "utf-8"))) {
                                                                                khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));

                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    } else //Buoi == null
                                                    {
//Giới tính
                                                        if (kh.getGioiTinh().equals(khoaHoc.getGioiTinh())) {
                                                            for (String bangcap : kh.getBangCap()) {
                                                                //Bằng cấp
                                                                if (URLEncoder.encode(removeDiacriticalMarks(bangcap), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getBangCap().get(0)), "utf-8"))) {
                                                                    khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));

                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                            ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHocGiaSu(khoaHocChinhXac, khoaHocGanChinhXac);

                                        } catch (UnsupportedEncodingException e) {
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
                    } else//4 Mon == null, HocPhi==0,GioiTinh!=Nam, Nu,BangCap==null
                    {
                        if (khoaHoc.getDiaDiem().getQuan() != "Khác") //5 Mon == null, HocPhi==0,GioiTinh!=Nam, Nu,BangCap==null,Quan != "Khác"
                        {
                            final double finalHocPhiDuoi = hocPhiDuoi;
                            final double finalHocPhiTren = hocPhiTren;
                            final int finalCount = count;
                            mData.getReference().child("KhoaHoc").child("KhoaHocTimHocVien").child("ChuaHoanTat").addChildEventListener(new ChildEventListener() {
                                @Override
                                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                    int dem = 0; // Xác định độ chính xác của lịch học (if dem == finaleCount)
                                    KhoaHoc kh = dataSnapshot.getValue(KhoaHoc.class);
                                    for (String linhvuc : kh.getLinhVuc()) {
                                        //LinhVuc
                                        try {
                                            if (URLEncoder.encode(removeDiacriticalMarks(linhvuc), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getLinhVuc().get(0)), "utf-8"))) {
                                                //Lịch học
                                                if (finalCount != 0) //Ngày học tìm kiếm != null
                                                {
                                                    for (String ngayHoc : khoaHoc.getLichHoc().getNgayHoc()) {
                                                        for (String ngayhoc : kh.getLichHoc().getNgayHoc()) {
                                                            if (ngayhoc.equals(ngayHoc)) {
                                                                dem++;
                                                                if (dem == finalCount) // Độ chính xác lịch học đạt mức chấp nhận được
                                                                {
                                                                    khoaHocGanChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));
                                                                }
                                                            }
                                                        }
                                                    }
                                                    if (dem >= finalCount) // Độ chính xác lịch học chấp nhận được hoặc rất chính xác
                                                    {
                                                        if (khoaHoc.getLichHoc().getThoiGian().size() != 0) // Buổi != null
                                                        {
                                                            for (String buoiHoc : khoaHoc.getLichHoc().getThoiGian()) {
                                                                for (String buoihoc : kh.getLichHoc().getThoiGian()) {
                                                                    if (buoiHoc.equals(buoihoc)) // Buổi
                                                                    {
                                                                        //Giới tính
                                                                        if (kh.getGioiTinh().equals(khoaHoc.getGioiTinh())) {
                                                                            //Địa điểm
                                                                            if (kh.getDiaDiem().getQuan().equals(khoaHoc.getDiaDiem().getQuan())) {
                                                                                khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));

                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        } else //Buoi == null
                                                        {
//Giới tính
                                                            if (kh.getGioiTinh().equals(khoaHoc.getGioiTinh())) {
                                                                //Địa điểm
                                                                if (kh.getDiaDiem().getQuan().equals(khoaHoc.getDiaDiem().getQuan())) {
                                                                    khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));

                                                                }
                                                            }
                                                        }
                                                    }
                                                } else // Ngay == null
                                                {
                                                    if (khoaHoc.getLichHoc().getThoiGian().size() != 0) // Buổi != null
                                                    {
                                                        for (String buoiHoc : khoaHoc.getLichHoc().getThoiGian()) {
                                                            for (String buoihoc : kh.getLichHoc().getThoiGian()) {
                                                                if (buoiHoc.equals(buoihoc)) // Buổi
                                                                {
                                                                    //Giới tính
                                                                    if (kh.getGioiTinh().equals(khoaHoc.getGioiTinh())) {
                                                                        //Địa điểm
                                                                        if (kh.getDiaDiem().getQuan().equals(khoaHoc.getDiaDiem().getQuan())) {
                                                                            khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));

                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    } else //Buoi == null
                                                    {
//Giới tính
                                                        if (kh.getGioiTinh().equals(khoaHoc.getGioiTinh())) {
                                                            //Địa điểm
                                                            if (kh.getDiaDiem().getQuan().equals(khoaHoc.getDiaDiem().getQuan())) {
                                                                khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));

                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                            ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHocGiaSu(khoaHocChinhXac, khoaHocGanChinhXac);

                                        } catch (UnsupportedEncodingException e) {
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
                        } else //5 Mon == null, HocPhi==0,GioiTinh!=Nam, Nu,BangCap==null, Quan = "Khác"
                        {
                            final double finalHocPhiDuoi = hocPhiDuoi;
                            final double finalHocPhiTren = hocPhiTren;
                            final int finalCount = count;
                            mData.getReference().child("KhoaHoc").child("KhoaHocTimHocVien").child("ChuaHoanTat").addChildEventListener(new ChildEventListener() {
                                @Override
                                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                    int dem = 0; // Xác định độ chính xác của lịch học (if dem == finaleCount)
                                    KhoaHoc kh = dataSnapshot.getValue(KhoaHoc.class);
                                    for (String linhvuc : kh.getLinhVuc()) {
                                        try {
                                            if (URLEncoder.encode(removeDiacriticalMarks(linhvuc), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getLinhVuc().get(0)), "utf-8"))) {
//Lịch học
                                                if (finalCount != 0) //Ngày học tìm kiếm != null
                                                {
                                                    for (String ngayHoc : khoaHoc.getLichHoc().getNgayHoc()) {
                                                        for (String ngayhoc : kh.getLichHoc().getNgayHoc()) {
                                                            if (ngayhoc.equals(ngayHoc)) {
                                                                dem++;
                                                                if (dem == finalCount) // Độ chính xác lịch học đạt mức chấp nhận được
                                                                {
                                                                    khoaHocGanChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));
                                                                }
                                                            }
                                                        }
                                                    }
                                                    if (dem >= finalCount) // Độ chính xác lịch học chấp nhận được hoặc rất chính xác
                                                    {
                                                        if (khoaHoc.getLichHoc().getThoiGian().size() != 0) // Buổi != null
                                                        {
                                                            for (String buoiHoc : khoaHoc.getLichHoc().getThoiGian()) {
                                                                for (String buoihoc : kh.getLichHoc().getThoiGian()) {
                                                                    if (buoiHoc.equals(buoihoc)) // Buổi
                                                                    {
                                                                        //Giới tính
                                                                        if (kh.getGioiTinh().equals(khoaHoc.getGioiTinh())) {
                                                                            khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));

                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        } else //Buoi == null
                                                        {
//Giới tính
                                                            if (kh.getGioiTinh().equals(khoaHoc.getGioiTinh())) {
                                                                khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));

                                                            }
                                                        }
                                                    }
                                                } else //Ngay == null
                                                {
                                                    if (khoaHoc.getLichHoc().getThoiGian().size() != 0) // Buổi != null
                                                    {
                                                        for (String buoiHoc : khoaHoc.getLichHoc().getThoiGian()) {
                                                            for (String buoihoc : kh.getLichHoc().getThoiGian()) {
                                                                if (buoiHoc.equals(buoihoc)) // Buổi
                                                                {
                                                                    //Giới tính
                                                                    if (kh.getGioiTinh().equals(khoaHoc.getGioiTinh())) {
                                                                        khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));

                                                                    }
                                                                }
                                                            }
                                                        }
                                                    } else //Buoi == null
                                                    {
//Giới tính
                                                        if (kh.getGioiTinh().equals(khoaHoc.getGioiTinh())) {
                                                            khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));

                                                        }
                                                    }
                                                }
                                            }
                                            ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHocGiaSu(khoaHocChinhXac, khoaHocGanChinhXac);

                                        } catch (UnsupportedEncodingException e) {
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
                } else //3 Mon == null, HocPhi==0,GioiTinh==Nam, Nữ
                {
                    if (khoaHoc.getBangCap() != null) //4 Mon == null, HocPhi==0,GioiTinh==Nam, Nữ,BangCap!=null
                    {
                        if (khoaHoc.getDiaDiem().getQuan() != "Khác") //5 Mon == null, HocPhi==0,GioiTinh==Nam, Nữ,BangCap!=null,Quan != "Khác"
                        {
                            final double finalHocPhiDuoi = hocPhiDuoi;
                            final double finalHocPhiTren = hocPhiTren;
                            final int finalCount = count;
                            mData.getReference().child("KhoaHoc").child("KhoaHocTimHocVien").child("ChuaHoanTat").addChildEventListener(new ChildEventListener() {
                                @Override
                                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                    int dem = 0; // Xác định độ chính xác của lịch học (if dem == finaleCount)
                                    KhoaHoc kh = dataSnapshot.getValue(KhoaHoc.class);
                                    for (String linhvuc : kh.getLinhVuc()) {
                                        //LinhVuc
                                        try {
                                            if (URLEncoder.encode(removeDiacriticalMarks(linhvuc), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getLinhVuc().get(0)), "utf-8"))) {
//Lịch học
                                                if (finalCount != 0) //Ngày học tìm kiếm != null
                                                {
                                                    for (String ngayHoc : khoaHoc.getLichHoc().getNgayHoc()) {
                                                        for (String ngayhoc : kh.getLichHoc().getNgayHoc()) {
                                                            if (ngayhoc.equals(ngayHoc)) {
                                                                dem++;
                                                                if (dem == finalCount) // Độ chính xác lịch học đạt mức chấp nhận được
                                                                {
                                                                    khoaHocGanChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));
                                                                }
                                                            }
                                                        }
                                                    }
                                                    if (dem >= finalCount) // Độ chính xác lịch học chấp nhận được hoặc rất chính xác
                                                    {
                                                        if (khoaHoc.getLichHoc().getThoiGian().size() != 0) // Buổi != null
                                                        {
                                                            for (String buoiHoc : khoaHoc.getLichHoc().getThoiGian()) {
                                                                for (String buoihoc : kh.getLichHoc().getThoiGian()) {
                                                                    if (buoiHoc.equals(buoihoc)) // Buổi
                                                                    {
                                                                        for (String bangcap : kh.getBangCap()) {
                                                                            //Bằng cấp
                                                                            if (URLEncoder.encode(removeDiacriticalMarks(bangcap), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getBangCap().get(0)), "utf-8"))) {
                                                                                //Địa điểm
                                                                                if (kh.getDiaDiem().getQuan().equals(khoaHoc.getDiaDiem().getQuan())) {
                                                                                    khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));

                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        } else //Buoi == null
                                                        {
                                                            for (String bangcap : kh.getBangCap()) {
                                                                //Bằng cấp
                                                                if (URLEncoder.encode(removeDiacriticalMarks(bangcap), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getBangCap().get(0)), "utf-8"))) {
                                                                    //Địa điểm
                                                                    if (kh.getDiaDiem().getQuan().equals(khoaHoc.getDiaDiem().getQuan())) {
                                                                        khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));

                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                } else //Ngay == null
                                                {
                                                    if (khoaHoc.getLichHoc().getThoiGian().size() != 0) // Buổi != null
                                                    {
                                                        for (String buoiHoc : khoaHoc.getLichHoc().getThoiGian()) {
                                                            for (String buoihoc : kh.getLichHoc().getThoiGian()) {
                                                                if (buoiHoc.equals(buoihoc)) // Buổi
                                                                {
                                                                    for (String bangcap : kh.getBangCap()) {
                                                                        //Bằng cấp
                                                                        if (URLEncoder.encode(removeDiacriticalMarks(bangcap), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getBangCap().get(0)), "utf-8"))) {
                                                                            //Địa điểm
                                                                            if (kh.getDiaDiem().getQuan().equals(khoaHoc.getDiaDiem().getQuan())) {
                                                                                khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));

                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    } else //Buoi == null
                                                    {
                                                        for (String bangcap : kh.getBangCap()) {
                                                            //Bằng cấp
                                                            if (URLEncoder.encode(removeDiacriticalMarks(bangcap), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getBangCap().get(0)), "utf-8"))) {
                                                                //Địa điểm
                                                                if (kh.getDiaDiem().getQuan().equals(khoaHoc.getDiaDiem().getQuan())) {
                                                                    khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));

                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                            ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHocGiaSu(khoaHocChinhXac, khoaHocGanChinhXac);

                                        } catch (UnsupportedEncodingException e) {
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
                        } else //5 Mon == null, HocPhi==0,GioiTinh==Nam, Nữ,BangCap!=null, Quan = "Khác"
                        {
                            final double finalHocPhiDuoi = hocPhiDuoi;
                            final double finalHocPhiTren = hocPhiTren;
                            final int finalCount = count;
                            mData.getReference().child("KhoaHoc").child("KhoaHocTimHocVien").child("ChuaHoanTat").addChildEventListener(new ChildEventListener() {
                                @Override
                                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                    int dem = 0; // Xác định độ chính xác của lịch học (if dem == finaleCount)
                                    KhoaHoc kh = dataSnapshot.getValue(KhoaHoc.class);
                                    for (String linhvuc : kh.getLinhVuc()) {
                                        //LinhVuc
                                        try {
                                            if (URLEncoder.encode(removeDiacriticalMarks(linhvuc), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getLinhVuc().get(0)), "utf-8"))) {
//Lịch học
                                                if (finalCount != 0) //Ngày học tìm kiếm != null
                                                {
                                                    for (String ngayHoc : khoaHoc.getLichHoc().getNgayHoc()) {
                                                        for (String ngayhoc : kh.getLichHoc().getNgayHoc()) {
                                                            if (ngayhoc.equals(ngayHoc)) {
                                                                dem++;
                                                                if (dem == finalCount) // Độ chính xác lịch học đạt mức chấp nhận được
                                                                {
                                                                    khoaHocGanChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));
                                                                }
                                                            }
                                                        }
                                                    }
                                                    if (dem >= finalCount) // Độ chính xác lịch học chấp nhận được hoặc rất chính xác
                                                    {
                                                        if (khoaHoc.getLichHoc().getThoiGian().size() != 0) // Buổi != null
                                                        {
                                                            for (String buoiHoc : khoaHoc.getLichHoc().getThoiGian()) {
                                                                for (String buoihoc : kh.getLichHoc().getThoiGian()) {
                                                                    if (buoiHoc.equals(buoihoc)) // Buổi
                                                                    {
                                                                        for (String bangcap : kh.getBangCap()) {
                                                                            //Bằng cấp
                                                                            if (URLEncoder.encode(removeDiacriticalMarks(bangcap), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getBangCap().get(0)), "utf-8"))) {
                                                                                khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));

                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        } else //Buoi == null
                                                        {
                                                            for (String bangcap : kh.getBangCap()) {
                                                                //Bằng cấp
                                                                if (URLEncoder.encode(removeDiacriticalMarks(bangcap), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getBangCap().get(0)), "utf-8"))) {
                                                                    khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));

                                                                }
                                                            }
                                                        }
                                                    }
                                                } else //Ngay == null
                                                {
                                                    if (khoaHoc.getLichHoc().getThoiGian().size() != 0) // Buổi != null
                                                    {
                                                        for (String buoiHoc : khoaHoc.getLichHoc().getThoiGian()) {
                                                            for (String buoihoc : kh.getLichHoc().getThoiGian()) {
                                                                if (buoiHoc.equals(buoihoc)) // Buổi
                                                                {
                                                                    for (String bangcap : kh.getBangCap()) {
                                                                        //Bằng cấp
                                                                        if (URLEncoder.encode(removeDiacriticalMarks(bangcap), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getBangCap().get(0)), "utf-8"))) {
                                                                            khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));

                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    } else //Buoi == null
                                                    {
                                                        for (String bangcap : kh.getBangCap()) {
                                                            //Bằng cấp
                                                            if (URLEncoder.encode(removeDiacriticalMarks(bangcap), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getBangCap().get(0)), "utf-8"))) {
                                                                khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));

                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                            ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHocGiaSu(khoaHocChinhXac, khoaHocGanChinhXac);
                                        } catch (UnsupportedEncodingException e) {
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
                    } else//4 Mon == null, HocPhi==0,GioiTinh==Nam, Nữ,BangCap==null
                    {
                        if (khoaHoc.getDiaDiem().getQuan() != "Khác") //5 Mon == null, HocPhi==0,GioiTinh==Nam, Nữ,BangCap==null,Quan != "Khác"
                        {
                            final double finalHocPhiDuoi = hocPhiDuoi;
                            final double finalHocPhiTren = hocPhiTren;
                            final int finalCount = count;
                            mData.getReference().child("KhoaHoc").child("KhoaHocTimHocVien").child("ChuaHoanTat").addChildEventListener(new ChildEventListener() {
                                @Override
                                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                    int dem = 0; // Xác định độ chính xác của lịch học (if dem == finaleCount)
                                    KhoaHoc kh = dataSnapshot.getValue(KhoaHoc.class);
                                    for (String linhvuc : kh.getLinhVuc()) {
                                        //LinhVuc
                                        try {
                                            if (URLEncoder.encode(removeDiacriticalMarks(linhvuc), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getLinhVuc().get(0)), "utf-8"))) {
//Lịch học
                                                if (finalCount != 0) //Ngày học tìm kiếm != null
                                                {
                                                    for (String ngayHoc : khoaHoc.getLichHoc().getNgayHoc()) {
                                                        for (String ngayhoc : kh.getLichHoc().getNgayHoc()) {
                                                            if (ngayhoc.equals(ngayHoc)) {
                                                                dem++;
                                                                if (dem == finalCount) // Độ chính xác lịch học đạt mức chấp nhận được
                                                                {
                                                                    khoaHocGanChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));
                                                                }
                                                            }
                                                        }
                                                    }
                                                    if (dem >= finalCount) // Độ chính xác lịch học chấp nhận được hoặc rất chính xác
                                                    {
                                                        if (khoaHoc.getLichHoc().getThoiGian().size() != 0) // Buổi != null
                                                        {
                                                            for (String buoiHoc : khoaHoc.getLichHoc().getThoiGian()) {
                                                                for (String buoihoc : kh.getLichHoc().getThoiGian()) {
                                                                    if (buoiHoc.equals(buoihoc)) // Buổi
                                                                    {
                                                                        //Địa điểm
                                                                        if (kh.getDiaDiem().getQuan().equals(khoaHoc.getDiaDiem().getQuan())) {
                                                                            khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));

                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        } else //Buoi == null
                                                        {
//Địa điểm
                                                            if (kh.getDiaDiem().getQuan().equals(khoaHoc.getDiaDiem().getQuan())) {
                                                                khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));

                                                            }
                                                        }
                                                    }
                                                } else //Ngay == null
                                                {
                                                    if (khoaHoc.getLichHoc().getThoiGian().size() != 0) // Buổi != null
                                                    {
                                                        for (String buoiHoc : khoaHoc.getLichHoc().getThoiGian()) {
                                                            for (String buoihoc : kh.getLichHoc().getThoiGian()) {
                                                                if (buoiHoc.equals(buoihoc)) // Buổi
                                                                {
                                                                    //Địa điểm
                                                                    if (kh.getDiaDiem().getQuan().equals(khoaHoc.getDiaDiem().getQuan())) {
                                                                        khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));

                                                                    }
                                                                }
                                                            }
                                                        }
                                                    } else //Buoi == null
                                                    {
//Địa điểm
                                                        if (kh.getDiaDiem().getQuan().equals(khoaHoc.getDiaDiem().getQuan())) {
                                                            khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));

                                                        }
                                                    }
                                                }
                                            }
                                            ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHocGiaSu(khoaHocChinhXac, khoaHocGanChinhXac);

                                        } catch (UnsupportedEncodingException e) {
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
                        } else //5 Mon == null, HocPhi==0,GioiTinh==Nam, Nữ,BangCap==null, Quan = "Khác"
                        {
                            final double finalHocPhiDuoi = hocPhiDuoi;
                            final double finalHocPhiTren = hocPhiTren;
                            final int finalCount = count;
                            mData.getReference().child("KhoaHoc").child("KhoaHocTimHocVien").child("ChuaHoanTat").addChildEventListener(new ChildEventListener() {
                                @Override
                                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                    int dem = 0; // Xác định độ chính xác của lịch học (if dem == finaleCount)
                                    KhoaHoc kh = dataSnapshot.getValue(KhoaHoc.class);
                                    for (String linhvuc : kh.getLinhVuc()) {
                                        //LinhVuc
                                        try {
                                            if (URLEncoder.encode(removeDiacriticalMarks(linhvuc), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getLinhVuc().get(0)), "utf-8"))) {
//Lịch học
                                                if (finalCount != 0) //Ngày học tìm kiếm != null
                                                {
                                                    for (String ngayHoc : khoaHoc.getLichHoc().getNgayHoc()) {
                                                        for (String ngayhoc : kh.getLichHoc().getNgayHoc()) {
                                                            if (ngayhoc.equals(ngayHoc)) {
                                                                dem++;
                                                                if (dem == finalCount) // Độ chính xác lịch học đạt mức chấp nhận được
                                                                {
                                                                    khoaHocGanChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));
                                                                }
                                                            }
                                                        }
                                                    }
                                                    if (dem >= finalCount) // Độ chính xác lịch học chấp nhận được hoặc rất chính xác
                                                    {
                                                        if (khoaHoc.getLichHoc().getThoiGian().size() != 0) // Buổi != null
                                                        {
                                                            for (String buoiHoc : khoaHoc.getLichHoc().getThoiGian()) {
                                                                for (String buoihoc : kh.getLichHoc().getThoiGian()) {
                                                                    if (buoiHoc.equals(buoihoc)) // Buổi
                                                                    {
                                                                        khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));

                                                                    }
                                                                }
                                                            }
                                                        } else //Buoi == null
                                                        {
                                                            khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));

                                                        }
                                                    }
                                                } else //Buoi == null
                                                {
                                                    if (khoaHoc.getLichHoc().getThoiGian().size() != 0) // Buổi != null
                                                    {
                                                        for (String buoiHoc : khoaHoc.getLichHoc().getThoiGian()) {
                                                            for (String buoihoc : kh.getLichHoc().getThoiGian()) {
                                                                if (buoiHoc.equals(buoihoc)) // Buổi
                                                                {
                                                                    khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));

                                                                }
                                                            }
                                                        }
                                                    } else //Buoi == null
                                                    {
                                                        khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getAvatar(), kh.getRating(), kh.getHoTen(), kh.getNguoiDang(), kh.getSoBuoiHoc(), kh.getSoLuongHocVien(), kh.getGioiTinh(), kh.getNgayDang(), kh.getGioDang(), kh.getThoiLuongBuoiHoc(), kh.getHocPhi(), kh.getThongTinKhac(), kh.getBangCap(), kh.getMon(), kh.getLinhVuc(), kh.getLichHoc(), kh.getDiaDiem(), kh.getDanhSachYeuCau(), dataSnapshot.getKey()));

                                                    }
                                                }
                                            }
                                            ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHocGiaSu(khoaHocChinhXac, khoaHocGanChinhXac);

                                        } catch (UnsupportedEncodingException e) {
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
            }
        }
    }

    //Tim kiếm gia sư - gia sư ( Get list Gia su)
    //Nhan listKeyID từ phương thức getListGiaSu truyen qua presenter roi lai truyen nguoc ve cho model
    @Override
    public void nhanListKeyGiaSu(ArrayList<String> listKeyGS) {
        for(String key : listKeyGS)
        {
            mData.getReference().child("TaiKhoan").orderByKey().equalTo(key).addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    TaiKhoan tk = dataSnapshot.getValue(TaiKhoan.class);
                    listGiaSu.add(tk);
                    ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListGiaSu(listGiaSu);
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

    //Tìm kiếm gia sư - gia sư (Get list keyID gia su)
    //Chuyen listKeyID lay duoc qua presenter
    @Override
    public void getListGiaSu(final KhoaHoc khoaHoc, Activity activity) {

        if (khoaHoc.getMon() != null) {
            if (khoaHoc.getBangCap() != null) {
                mData.getReference().child("TaiLieuChuyenMon").child(khoaHoc.getLinhVuc().get(0)).addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        TaiLieuChuyenMon tailieu = dataSnapshot.getValue(TaiLieuChuyenMon.class);
                        for (MonTaiLieuChuyenMon Mon : tailieu.getDanhSachMonTaiLieuChuyenMon()) {
                            try {
                                if (URLEncoder.encode(removeDiacriticalMarks(Mon.getIdMon()), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getMon().get(0)), "utf-8"))) {
                                    for (BangCapTaiLieuChuyenMon BangCap : tailieu.getDanhSachBangCapTaiLieuChuyenMon()) {
                                        if (URLEncoder.encode(removeDiacriticalMarks(BangCap.getIdBangCap()), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getBangCap().get(0)), "utf-8"))) {
                                            listKeyGiaSu.add(dataSnapshot.getKey());

                                        }
                                    }
                                }
                                ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKeyGiaSu(listKeyGiaSu);
                            } catch (UnsupportedEncodingException e) {
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
            } else {
                mData.getReference().child("TaiLieuChuyenMon").child(khoaHoc.getLinhVuc().get(0)).addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        TaiLieuChuyenMon tailieu = dataSnapshot.getValue(TaiLieuChuyenMon.class);
                        for (MonTaiLieuChuyenMon Mon : tailieu.getDanhSachMonTaiLieuChuyenMon()) {
                            try {
                                if (URLEncoder.encode(removeDiacriticalMarks(Mon.getIdMon()), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getMon().get(0)), "utf-8"))) {
                                    listKeyGiaSu.add(dataSnapshot.getKey());
                                }
                                ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKeyGiaSu(listKeyGiaSu);
                            } catch (UnsupportedEncodingException e) {
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
        } else {
            if (khoaHoc.getBangCap() != null) {
                mData.getReference().child("TaiLieuChuyenMon").child(khoaHoc.getLinhVuc().get(0)).addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        TaiLieuChuyenMon tailieu = dataSnapshot.getValue(TaiLieuChuyenMon.class);

                        for (BangCapTaiLieuChuyenMon BangCap : tailieu.getDanhSachBangCapTaiLieuChuyenMon()) {
                            try {
                                if (URLEncoder.encode(removeDiacriticalMarks(BangCap.getIdBangCap()), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getBangCap().get(0)), "utf-8"))) {
                                    listKeyGiaSu.add(dataSnapshot.getKey());

                                }
                                ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKeyGiaSu(listKeyGiaSu);
                            } catch (UnsupportedEncodingException e) {
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
            } else {
                mData.getReference().child("TaiLieuChuyenMon").child(khoaHoc.getLinhVuc().get(0)).addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                                    listKeyGiaSu.add(dataSnapshot.getKey());

                        ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKeyGiaSu(listKeyGiaSu);

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


    //Gỡ dấu
    public static String removeDiacriticalMarks(String string) {
        return Normalizer.normalize(string, Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
    }

    //Dem so luong ngay hoc trong lich hoc de lay ty le chinh xac cho viec tim kiem
    public int cout(int x) {
        int count = 0;
        if ((x / 2) < 1) {
            count = 0;
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
