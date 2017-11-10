package com.eways.elearning.DataModel.KhoaHoc;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;

/**
 * Created by yowin on 10/11/2017.
 */

public class KhoaHocDaHoanTat {
    String NguoiDang;
    int SoBuoiHoc;
    int SoLuongHocVien;
    Date NgayDang;
    Time GioDang;
    float ThoiLuongBuoiHoc;
    float HocPhi;
    String ThongTinKhac;
    ArrayList<String> Mon;
    ArrayList<String> LinhVuc;
    ArrayList<String> Lop;
    com.eways.elearning.DataModel.KhoaHoc.LichHoc LichHoc;
    com.eways.elearning.DataModel.KhoaHoc.DiaDiem DiaDiem;
    ArrayList<String> DanhSachNhan;

    public KhoaHocDaHoanTat() {
    }

    public KhoaHocDaHoanTat(String nguoiDang, int soBuoiHoc, int soLuongHocVien, Date ngayDang, Time gioDang,
                            float thoiLuongBuoiHoc, float hocPhi, String thongTinKhac, ArrayList<String> mon,
                            ArrayList<String> linhVuc, ArrayList<String> lop, com.eways.elearning.DataModel.KhoaHoc.LichHoc lichHoc,
                            com.eways.elearning.DataModel.KhoaHoc.DiaDiem diaDiem, ArrayList<String> danhSachNhan)
    {
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
        DiaDiem = diaDiem;
        DanhSachNhan = danhSachNhan;
    }

    public String getNguoiDang() {
        return NguoiDang;
    }

    public void setNguoiDang(String nguoiDang) {
        NguoiDang = nguoiDang;
    }

    public int getSoBuoiHoc() {
        return SoBuoiHoc;
    }

    public void setSoBuoiHoc(int soBuoiHoc) {
        SoBuoiHoc = soBuoiHoc;
    }

    public int getSoLuongHocVien() {
        return SoLuongHocVien;
    }

    public void setSoLuongHocVien(int soLuongHocVien) {
        SoLuongHocVien = soLuongHocVien;
    }

    public Date getNgayDang() {
        return NgayDang;
    }

    public void setNgayDang(Date ngayDang) {
        NgayDang = ngayDang;
    }

    public Time getGioDang() {
        return GioDang;
    }

    public void setGioDang(Time gioDang) {
        GioDang = gioDang;
    }

    public float getThoiLuongBuoiHoc() {
        return ThoiLuongBuoiHoc;
    }

    public void setThoiLuongBuoiHoc(float thoiLuongBuoiHoc) {
        ThoiLuongBuoiHoc = thoiLuongBuoiHoc;
    }

    public float getHocPhi() {
        return HocPhi;
    }

    public void setHocPhi(float hocPhi) {
        HocPhi = hocPhi;
    }

    public String getThongTinKhac() {
        return ThongTinKhac;
    }

    public void setThongTinKhac(String thongTinKhac) {
        ThongTinKhac = thongTinKhac;
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

    public ArrayList<String> getLop() {
        return Lop;
    }

    public void setLop(ArrayList<String> lop) {
        Lop = lop;
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

    public ArrayList<String> getDanhSachNhan() {
        return DanhSachNhan;
    }

    public void setDanhSachNhan(ArrayList<String> danhSachNhan) {
        DanhSachNhan = danhSachNhan;
    }
}
