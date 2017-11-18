package com.eways.elearning.DataModel.KhoaHoc;

import java.util.ArrayList;

/**
 * Created by yowin on 10/11/2017.
 */

public class KhoaHocChuaHoanTat {
    public String Avatar;
    public String Rating;
    public String HoTen;
    public String NguoiDang;
    public String SoBuoiHoc;
    public String SoLuongHocVien;
    public String NgayDang;
    public String GioDang;
    public String ThoiLuongBuoiHoc;
    public String HocPhi;
    public String ThongTinKhac;
    public ArrayList<String> Mon;
    public ArrayList<String> LinhVuc;
    public ArrayList<String> Lop;
    public LichHoc LichHoc;
    public DanhSachYeuCau DanhSachYeuCau;
    public DiaDiem DiaChi;



    public KhoaHocChuaHoanTat() {
    }

    public KhoaHocChuaHoanTat(String avatar, String rating, String hoTen, String nguoiDang, String soBuoiHoc, String soLuongHocVien, String ngayDang, String gioDang, String thoiLuongBuoiHoc, String hocPhi, String thongTinKhac, ArrayList<String> mon, ArrayList<String> linhVuc, ArrayList<String> lop, com.eways.elearning.DataModel.KhoaHoc.LichHoc lichHoc, com.eways.elearning.DataModel.KhoaHoc.DanhSachYeuCau danhSachYeuCau, DiaDiem diaChi) {
        Avatar = avatar;
        Rating = rating;
        HoTen = hoTen;
        NguoiDang = nguoiDang;
        SoBuoiHoc = soBuoiHoc;
        SoLuongHocVien = soLuongHocVien;
        NgayDang = ngayDang;
        GioDang = gioDang;
        ThoiLuongBuoiHoc = thoiLuongBuoiHoc;
        HocPhi = hocPhi;
        ThongTinKhac = thongTinKhac;
        Mon = mon;
        LinhVuc = linhVuc;
        Lop = lop;
        LichHoc = lichHoc;
        DanhSachYeuCau = danhSachYeuCau;
        DiaChi = diaChi;
    }
}
