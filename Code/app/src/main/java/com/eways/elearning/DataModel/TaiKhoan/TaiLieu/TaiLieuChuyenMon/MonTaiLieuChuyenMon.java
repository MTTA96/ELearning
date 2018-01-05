package com.eways.elearning.DataModel.TaiKhoan.TaiLieu.TaiLieuChuyenMon;

import java.util.ArrayList;

/**
 * Created by ADMIN on 1/5/2018.
 */

public class MonTaiLieuChuyenMon {
    String idMon;
    String tenMon;
    String urlClipMon;
    ArrayList<String> danhSachHinhMon;

    public MonTaiLieuChuyenMon(String idMon, String tenMon, String urlClipMon, ArrayList<String> danhSachHinhMon) {
        this.idMon = idMon;
        this.tenMon = tenMon;
        this.urlClipMon = urlClipMon;
        this.danhSachHinhMon = danhSachHinhMon;
    }

    public String getIdMon() {
        return idMon;
    }

    public void setIdMon(String idMon) {
        this.idMon = idMon;
    }

    public String getTenMon() {
        return tenMon;
    }

    public void setTenMon(String tenMon) {
        this.tenMon = tenMon;
    }

    public String getUrlClipMon() {
        return urlClipMon;
    }

    public void setUrlClipMon(String urlClipMon) {
        this.urlClipMon = urlClipMon;
    }

    public ArrayList<String> getDanhSachHinhMon() {
        return danhSachHinhMon;
    }

    public void setDanhSachHinhMon(ArrayList<String> danhSachHinhMon) {
        this.danhSachHinhMon = danhSachHinhMon;
    }
}
