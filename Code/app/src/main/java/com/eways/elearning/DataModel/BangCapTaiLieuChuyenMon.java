package com.eways.elearning.DataModel;

/**
 * Created by ADMIN on 12/6/2017.
 */

public class BangCapTaiLieuChuyenMon {
    String idBangCap;
    String hinhBangCap;
    String tenBangCap;

    public BangCapTaiLieuChuyenMon(String idBangCap, String hinhBangCap, String tenBangCap) {
        this.idBangCap = idBangCap;
        this.hinhBangCap = hinhBangCap;
        this.tenBangCap = tenBangCap;
    }

    public String getHinhBangCap() {
        return hinhBangCap;
    }

    public void setHinhBangCap(String hinhBangCap) {
        this.hinhBangCap = hinhBangCap;
    }

    public String getTenBangCap() {
        return tenBangCap;
    }

    public void setTenBangCap(String tenBangCap) {
        this.tenBangCap = tenBangCap;
    }

    public String getIdBangCap() {
        return idBangCap;
    }

    public void setIdBangCap(String idBangCap) {
        this.idBangCap = idBangCap;
    }
}
