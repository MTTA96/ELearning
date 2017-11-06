package com.eways.elearning.DataModel.BaiDang;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;

/**
 * Created by Tien Phat on 02/11/2017.
 */

public class BaiDangDaHoanTat {
    String NguoiDang;
    int SoBuoi;
    int SoLuongHocVien;
    Date NgayDang;
    Time GioDang;
    float ThoiLuong;
    float HocPhi;
    String ThongTinKhac;
    ArrayList<String> Mon;
    ArrayList<String> LinhVuc;
    ArrayList<String> Lop;
    LichHoc LichHoc;
    DiaDiem DiaDiem;
    ArrayList<String> DanhSachNhan;

    public BaiDangDaHoanTat() {
    }

    public BaiDangDaHoanTat(String nguoiDang, int soBuoi, int soLuongHocVien, Date ngayDang, Time gioDang,
                            float thoiLuong, float hocPhi, String thongTinKhac, ArrayList<String> mon,
                            ArrayList<String> linhVuc, ArrayList<String> lop, LichHoc lichHoc, DiaDiem diaDiem,
                            ArrayList<String> danhSachNhan)
    {
        NguoiDang = nguoiDang;
        SoBuoi = soBuoi;
        SoLuongHocVien = soLuongHocVien;
        NgayDang = ngayDang;
        GioDang = gioDang;
        ThoiLuong = thoiLuong;
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

    public int getSoBuoi() {
        return SoBuoi;
    }

    public void setSoBuoi(int soBuoi) {
        SoBuoi = soBuoi;
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

    public float getThoiLuong() {
        return ThoiLuong;
    }

    public void setThoiLuong(float thoiLuong) {
        ThoiLuong = thoiLuong;
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

    public LichHoc getLichHoc() {
        return LichHoc;
    }

    public void setLichHoc(LichHoc lichHoc) {
        LichHoc = lichHoc;
    }

    public DiaDiem getDiaDiem() {
        return DiaDiem;
    }

    public void setDiaDiem(DiaDiem diaDiem) {
        DiaDiem = diaDiem;
    }

    public ArrayList<String> getDanhSachNhan() {
        return DanhSachNhan;
    }

    public void setDanhSachNhan(ArrayList<String> danhSachNhan) {
        DanhSachNhan = danhSachNhan;
    }
}
