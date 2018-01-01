package com.eways.elearning.DataModel.TaiKhoan;

/**
 * Created by ADMIN on 12/6/2017.
 */

public class TaiLieuChuyenMon {
    String idTaiLieuChuyenMon;
    BangCapTaiLieuChuyenMon bangCapTaiLieuChuyenMon;
    MonTaiLieuChuyenMon monTaiLieuChuyenMon;

    public TaiLieuChuyenMon(String idTaiLieuChuyenMon, BangCapTaiLieuChuyenMon bangCapTaiLieuChuyenMon, MonTaiLieuChuyenMon monTaiLieuChuyenMon) {
        this.idTaiLieuChuyenMon = idTaiLieuChuyenMon;
        this.bangCapTaiLieuChuyenMon = bangCapTaiLieuChuyenMon;
        this.monTaiLieuChuyenMon = monTaiLieuChuyenMon;
    }

    public String getIdTaiLieuChuyenMon() {
        return idTaiLieuChuyenMon;
    }

    public void setIdTaiLieuChuyenMon(String idTaiLieuChuyenMon) {
        this.idTaiLieuChuyenMon = idTaiLieuChuyenMon;
    }

    public BangCapTaiLieuChuyenMon getBangCapTaiLieuChuyenMon() {
        return bangCapTaiLieuChuyenMon;
    }

    public void setBangCapTaiLieuChuyenMon(BangCapTaiLieuChuyenMon bangCapTaiLieuChuyenMon) {
        this.bangCapTaiLieuChuyenMon = bangCapTaiLieuChuyenMon;
    }

    public MonTaiLieuChuyenMon getMonTaiLieuChuyenMon() {
        return monTaiLieuChuyenMon;
    }

    public void setMonTaiLieuChuyenMon(MonTaiLieuChuyenMon monTaiLieuChuyenMon) {
        this.monTaiLieuChuyenMon = monTaiLieuChuyenMon;
    }
}
