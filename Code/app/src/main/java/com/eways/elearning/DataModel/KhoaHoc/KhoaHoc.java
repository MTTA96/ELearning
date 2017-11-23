package com.eways.elearning.DataModel.KhoaHoc;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by yowin on 10/11/2017.
 */

public class KhoaHoc implements Serializable {

    private String Avatar;
    private String Rating;
    private String HoTen;
    private String NguoiDang;
    private String SoBuoiHoc;
    private String SoLuongHocVien;
    private String GioiTinh;
    private String NgayDang;
    private String GioDang;
    private String ThoiLuongBuoiHoc;
    private String HocPhi;
    private String ThongTinKhac;
    private ArrayList<String> Lop;
    private ArrayList<String> BangCap;
    private ArrayList<String> Mon;
    private ArrayList<String> LinhVuc;
    private LichHoc LichHoc;
    private DiaDiem DiaDiem;

    //Custom

    private ArrayList<String> Buoi;
    private ArrayList<String> Thu;


    public KhoaHoc() {
    }

    public KhoaHoc(String avatar, String rating, String hoTen, String nguoiDang, String soBuoiHoc, String soLuongHocVien, String gioiTinh, String ngayDang, String gioDang, String thoiLuongBuoiHoc, String hocPhi, String thongTinKhac, ArrayList<String> lop, ArrayList<String> bangCap, ArrayList<String> mon, ArrayList<String> linhVuc, com.eways.elearning.DataModel.KhoaHoc.LichHoc lichHoc, com.eways.elearning.DataModel.KhoaHoc.DiaDiem diaDiem, ArrayList<String> buoi, ArrayList<String> thu) {
        Avatar = avatar;
        Rating = rating;
        HoTen = hoTen;
        NguoiDang = nguoiDang;
        SoBuoiHoc = soBuoiHoc;
        SoLuongHocVien = soLuongHocVien;
        GioiTinh = gioiTinh;
        NgayDang = ngayDang;
        GioDang = gioDang;
        ThoiLuongBuoiHoc = thoiLuongBuoiHoc;
        HocPhi = hocPhi;
        ThongTinKhac = thongTinKhac;
        Lop = lop;
        BangCap = bangCap;
        Mon = mon;
        LinhVuc = linhVuc;
        LichHoc = lichHoc;
        DiaDiem = diaDiem;
        Buoi = buoi;
        Thu = thu;
    }

    public String getAvatar() {
        return Avatar;
    }

    public void setAvatar(String avatar) {
        Avatar = avatar;
    }

    public String getRating() {
        return Rating;
    }

    public void setRating(String rating) {
        Rating = rating;
    }

    public String getHoTen() {
        return HoTen;
    }

    public void setHoTen(String hoTen) {
        HoTen = hoTen;
    }

    public String getNguoiDang() {
        return NguoiDang;
    }

    public void setNguoiDang(String nguoiDang) {
        NguoiDang = nguoiDang;
    }

    public String getSoBuoiHoc() {
        return SoBuoiHoc;
    }

    public void setSoBuoiHoc(String soBuoiHoc) {
        SoBuoiHoc = soBuoiHoc;
    }

    public String getSoLuongHocVien() {
        return SoLuongHocVien;
    }

    public void setSoLuongHocVien(String soLuongHocVien) {
        SoLuongHocVien = soLuongHocVien;
    }

    public String getGioiTinh() {
        return GioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        GioiTinh = gioiTinh;
    }

    public String getNgayDang() {
        return NgayDang;
    }

    public void setNgayDang(String ngayDang) {
        NgayDang = ngayDang;
    }

    public String getGioDang() {
        return GioDang;
    }

    public void setGioDang(String gioDang) {
        GioDang = gioDang;
    }

    public String getThoiLuongBuoiHoc() {
        return ThoiLuongBuoiHoc;
    }

    public void setThoiLuongBuoiHoc(String thoiLuongBuoiHoc) {
        ThoiLuongBuoiHoc = thoiLuongBuoiHoc;
    }

    public String getHocPhi() {
        return HocPhi;
    }

    public void setHocPhi(String hocPhi) {
        HocPhi = hocPhi;
    }

    public String getThongTinKhac() {
        return ThongTinKhac;
    }

    public void setThongTinKhac(String thongTinKhac) {
        ThongTinKhac = thongTinKhac;
    }

    public ArrayList<String> getBangCap() {
        return BangCap;
    }

    public void setBangCap(ArrayList<String> bangCap) {
        BangCap = bangCap;
    }

    public ArrayList<String> getMon() {
        return Mon;
    }

    public void setMon(ArrayList<String> mon) {
        Mon = mon;
    }

    public ArrayList<String> getLinhVuc() {
        return LinhVuc;
    }

    public void setLinhVuc(ArrayList<String> linhVuc) {
        LinhVuc = linhVuc;
    }

    public com.eways.elearning.DataModel.KhoaHoc.LichHoc getLichHoc() {
        return LichHoc;
    }

    public void setLichHoc(com.eways.elearning.DataModel.KhoaHoc.LichHoc lichHoc) {
        LichHoc = lichHoc;
    }

    public com.eways.elearning.DataModel.KhoaHoc.DiaDiem getDiaDiem() {
        return DiaDiem;
    }

    public void setDiaDiem(com.eways.elearning.DataModel.KhoaHoc.DiaDiem diaDiem) {
        DiaDiem = diaDiem;
    }

    public ArrayList<String> getLop() {
        return Lop;
    }

    public void setLop(ArrayList<String> lop) {
        Lop = lop;
    }

    public ArrayList<String> getBuoi() {
        return Buoi;
    }

    public void setBuoi(ArrayList<String> buoi) {
        Buoi = buoi;
    }

    public ArrayList<String> getThu() {
        return Thu;
    }

    public void setThu(ArrayList<String> thu) {
        Thu = thu;
    }
}
