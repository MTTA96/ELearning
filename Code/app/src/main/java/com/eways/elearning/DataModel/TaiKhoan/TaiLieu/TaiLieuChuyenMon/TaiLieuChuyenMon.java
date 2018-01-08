package com.eways.elearning.DataModel.TaiKhoan.TaiLieu.TaiLieuChuyenMon;

import java.util.ArrayList;

/**
 * Created by ADMIN on 1/5/2018.
 */

public class TaiLieuChuyenMon {
    String idTaiLieuChuyenMon;
    String tenLinhVucChuyenMon;
    ArrayList<BangCapTaiLieuChuyenMon> danhSachBangCapTaiLieuChuyenMon;
    ArrayList<MonTaiLieuChuyenMon> danhSachMonTaiLieuChuyenMon;

    public TaiLieuChuyenMon() {

    }

    public TaiLieuChuyenMon(String idTaiLieuChuyenMon, String tenLinhVucChuyenMon, ArrayList<BangCapTaiLieuChuyenMon> danhSachBangCapTaiLieuChuyenMon, ArrayList<MonTaiLieuChuyenMon> danhSachMonTaiLieuChuyenMon) {
        this.idTaiLieuChuyenMon = idTaiLieuChuyenMon;
        this.tenLinhVucChuyenMon = tenLinhVucChuyenMon;
        this.danhSachBangCapTaiLieuChuyenMon = danhSachBangCapTaiLieuChuyenMon;
        this.danhSachMonTaiLieuChuyenMon = danhSachMonTaiLieuChuyenMon;
    }

    public String getIdTaiLieuChuyenMon() {
        return idTaiLieuChuyenMon;
    }

    public void setIdTaiLieuChuyenMon(String idTaiLieuChuyenMon) {
        this.idTaiLieuChuyenMon = idTaiLieuChuyenMon;
    }

    public String getTenLinhVucChuyenMon() {
        return tenLinhVucChuyenMon;
    }

    public void setTenLinhVucChuyenMon(String tenLinhVucChuyenMon) {
        this.tenLinhVucChuyenMon = tenLinhVucChuyenMon;
    }

    public ArrayList<BangCapTaiLieuChuyenMon> getDanhSachBangCapTaiLieuChuyenMon() {
        return danhSachBangCapTaiLieuChuyenMon;
    }

    public void setDanhSachBangCapTaiLieuChuyenMon(ArrayList<BangCapTaiLieuChuyenMon> danhSachBangCapTaiLieuChuyenMon) {
        this.danhSachBangCapTaiLieuChuyenMon = danhSachBangCapTaiLieuChuyenMon;
    }

    public ArrayList<MonTaiLieuChuyenMon> getDanhSachMonTaiLieuChuyenMon() {
        return danhSachMonTaiLieuChuyenMon;
    }

    public void setDanhSachMonTaiLieuChuyenMon(ArrayList<MonTaiLieuChuyenMon> danhSachMonTaiLieuChuyenMon) {
        this.danhSachMonTaiLieuChuyenMon = danhSachMonTaiLieuChuyenMon;
    }
}
