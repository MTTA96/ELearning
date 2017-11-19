package com.eways.elearning.DataModel.KhoaHoc;

import java.util.ArrayList;

/**
 * Created by yowin on 10/11/2017.
 */

public class KhoaHocDaHoanTat {
    public String NguoiDang;
    public int SoBuoiHoc;
    public int SoLuongHocVien;
    public String NgayDang;
    public String GioDang;
    public float ThoiLuongBuoiHoc;
    public float HocPhi;
    public String ThongTinKhac;
    public ArrayList<String> Mon;
    public ArrayList<String> LinhVuc;
    public ArrayList<String> Lop;
    public LichHoc LichHoc;
    public DiaDiem DiaDiem;
    public ArrayList<String> DanhSachNhan;

    public KhoaHocDaHoanTat() {
    }

    public KhoaHocDaHoanTat(String nguoiDang, int soBuoiHoc, int soLuongHocVien, String ngayDang, String gioDang, float thoiLuongBuoiHoc, float hocPhi, String thongTinKhac, ArrayList<String> mon, ArrayList<String> linhVuc, ArrayList<String> lop, com.eways.elearning.DataModel.KhoaHoc.LichHoc lichHoc, com.eways.elearning.DataModel.KhoaHoc.DiaDiem diaDiem, ArrayList<String> danhSachNhan) {
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
}
