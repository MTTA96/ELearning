package com.eways.elearning.DataModel.TaiKhoan;

/**
 * Created by ADMIN on 12/6/2017.
 */

public class MonTaiLieuChuyenMon {
    String idMonTaiLieuChuyenMon;
    String tenMonTaiLieuChuyenMon;
    String clipXacMinh;
    String fileXacMinh;

    public MonTaiLieuChuyenMon(String idMonTaiLieuChuyenMon, String tenMonTaiLieuChuyenMon, String clipXacMinh, String fileXacMinh) {
        this.idMonTaiLieuChuyenMon = idMonTaiLieuChuyenMon;
        this.tenMonTaiLieuChuyenMon = tenMonTaiLieuChuyenMon;
        this.clipXacMinh = clipXacMinh;
        this.fileXacMinh = fileXacMinh;
    }

    public String getIdMonTaiLieuChuyenMon() {
        return idMonTaiLieuChuyenMon;
    }

    public void setIdMonTaiLieuChuyenMon(String idMonTaiLieuChuyenMon) {
        this.idMonTaiLieuChuyenMon = idMonTaiLieuChuyenMon;
    }

    public String getTenMonTaiLieuChuyenMon() {
        return tenMonTaiLieuChuyenMon;
    }

    public void setTenMonTaiLieuChuyenMon(String tenMonTaiLieuChuyenMon) {
        this.tenMonTaiLieuChuyenMon = tenMonTaiLieuChuyenMon;
    }

    public String getClipXacMinh() {
        return clipXacMinh;
    }

    public void setClipXacMinh(String clipXacMinh) {
        this.clipXacMinh = clipXacMinh;
    }

    public String getFileXacMinh() {
        return fileXacMinh;
    }

    public void setFileXacMinh(String fileXacMinh) {
        this.fileXacMinh = fileXacMinh;
    }
}
