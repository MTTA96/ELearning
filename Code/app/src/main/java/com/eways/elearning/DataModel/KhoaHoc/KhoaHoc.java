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

    public KhoaHoc(String avatar, String rating, String hoTen, String nguoiDang, String soBuoiHoc, String soLuongHocVien, String gioiTinh, String ngayDang, String gioDang, String thoiLuongBuoiHoc, String hocPhi, String thongTinKhac, ArrayList<String> lop, ArrayList<String> bangCap, ArrayList<String> mon, ArrayList<String> linhVuc, com.eways.elearning.DataModel.KhoaHoc.LichHoc lichHoc, com.eways.elearning.DataModel.KhoaHoc.DiaDiem diaDiem) {
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
        BangCap = bangCap;
        Mon = mon;
        LinhVuc = linhVuc;
        LichHoc = lichHoc;
        DiaDiem = diaDiem;
    }

    public String getAvatar() {
        return Avatar;
    }

    public String getRating() {
        return Rating;
    }

    public String getHoTen() {
        return HoTen;
    }

    public String getNguoiDang() {
        return NguoiDang;
    }

    public String getSoBuoiHoc() {
        return SoBuoiHoc;
    }

    public String getSoLuongHocVien() {
        return SoLuongHocVien;
    }

    public String getGioiTinh() {
        return GioiTinh;
    }

    public String getNgayDang() {
        return NgayDang;
    }

    public String getGioDang() {
        return GioDang;
    }

    public String getThoiLuongBuoiHoc() {
        return ThoiLuongBuoiHoc;
    }

    public String getHocPhi() {
        return HocPhi;
    }

    public String getThongTinKhac() {
        return ThongTinKhac;
    }

    public ArrayList<String> getBangCap() {
        return BangCap;
    }

    public ArrayList<String> getMon() {
        return Mon;
    }

    public ArrayList<String> getLinhVuc() {
        return LinhVuc;
    }

    public com.eways.elearning.DataModel.KhoaHoc.LichHoc getLichHoc() {
        return LichHoc;
    }

    public com.eways.elearning.DataModel.KhoaHoc.DiaDiem getDiaDiem() {
        return DiaDiem;
    }

    public ArrayList<String> getBuoi() {
        return Buoi;
    }

    public ArrayList<String> getThu() {
        return Thu;
    }

    public void setAvatar(String Avatar) {
        this.Avatar = Avatar;
    }

    public void setRating(String Rating) {
        this.Rating = Rating;
    }

    public void setHoTen(String HoTen) {
        this.HoTen = HoTen;
    }

    public void setNguoiDang(String NguoiDang) {
        this.NguoiDang = NguoiDang;
    }

    public void setSoBuoiHoc(String SoBuoiHoc) {
        this.SoBuoiHoc = SoBuoiHoc;
    }

    public void setSoLuongHocVien(String SoLuongHocVien) {
        this.SoLuongHocVien = SoLuongHocVien;
    }

    public void setGioiTinh(String GioiTinh) {
        this.GioiTinh = GioiTinh;
    }

    public void setNgayDang(String NgayDang) {
        this.NgayDang = NgayDang;
    }

    public void setGioDang(String GioDang) {
        this.GioDang = GioDang;
    }

    public void setThoiLuongBuoiHoc(String ThoiLuongBuoiHoc) {
        this.ThoiLuongBuoiHoc = ThoiLuongBuoiHoc;
    }

    public void setHocPhi(String HocPhi) {
        this.HocPhi = HocPhi;
    }

    public void setThongTinKhac(String ThongTinKhac) {
        this.ThongTinKhac = ThongTinKhac;
    }


    public void setBangCap(ArrayList<String> BangCap) {
        this.BangCap = BangCap;
    }

    public void setMon(ArrayList<String> Mon) {
        this.Mon = Mon;
    }

    public void setLinhVuc(ArrayList<String> LinhVuc) {
        this.LinhVuc = LinhVuc;
    }

    public void setLichHoc(com.eways.elearning.DataModel.KhoaHoc.LichHoc LichHoc) {
        this.LichHoc = LichHoc;
    }

    public void setDiaDiem(com.eways.elearning.DataModel.KhoaHoc.DiaDiem DiaDiem) {
        this.DiaDiem = DiaDiem;
    }

    public void setBuoi(ArrayList<String> Buoi) {
        this.Buoi = Buoi;
    }

    public void setThu(ArrayList<String> Thu) {
        this.Thu = Thu;
    }


}
