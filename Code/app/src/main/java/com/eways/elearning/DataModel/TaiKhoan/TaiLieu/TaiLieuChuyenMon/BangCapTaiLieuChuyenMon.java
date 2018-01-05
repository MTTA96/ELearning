package com.eways.elearning.DataModel.TaiKhoan.TaiLieu.TaiLieuChuyenMon;

/**
 * Created by ADMIN on 1/5/2018.
 */

public class BangCapTaiLieuChuyenMon {
    String idBangCap;
    String tenBangCap;
    String urlHinhBangCap;

    public BangCapTaiLieuChuyenMon(String idBangCap, String tenBangCap, String urlHinhBangCap) {
        this.idBangCap = idBangCap;
        this.tenBangCap = tenBangCap;
        this.urlHinhBangCap = urlHinhBangCap;
    }

    public String getIdBangCap() {
        return idBangCap;
    }

    public void setIdBangCap(String idBangCap) {
        this.idBangCap = idBangCap;
    }

    public String getTenBangCap() {
        return tenBangCap;
    }

    public void setTenBangCap(String tenBangCap) {
        this.tenBangCap = tenBangCap;
    }

    public String getUrlHinhBangCap() {
        return urlHinhBangCap;
    }

    public void setUrlHinhBangCap(String urlHinhBangCap) {
        this.urlHinhBangCap = urlHinhBangCap;
    }
}
