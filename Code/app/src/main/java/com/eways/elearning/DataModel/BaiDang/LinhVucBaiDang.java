package com.eways.elearning.DataModel.BaiDang;

/**
 * Created by ADMIN on 11/9/2017.
 */

public class LinhVucBaiDang {
    int idLinhVuc;
    String tenLinhVuc;
    int hinhLinhVuc;

    public LinhVucBaiDang(int idLinhVuc, String tenLinhVuc, int hinhLinhVuc) {
        this.idLinhVuc = idLinhVuc;
        this.tenLinhVuc = tenLinhVuc;
        this.hinhLinhVuc = hinhLinhVuc;
    }

    public int getIdLinhVuc() {
        return idLinhVuc;
    }

    public void setIdLinhVuc(int idLinhVuc) {
        this.idLinhVuc = idLinhVuc;
    }

    public String getTenLinhVuc() {
        return tenLinhVuc;
    }

    public void setTenLinhVuc(String tenLinhVuc) {
        this.tenLinhVuc = tenLinhVuc;
    }

    public int getHinhLinhVuc() {
        return hinhLinhVuc;
    }

    public void setHinhLinhVuc(int hinhLinhVuc) {
        this.hinhLinhVuc = hinhLinhVuc;
    }
}

