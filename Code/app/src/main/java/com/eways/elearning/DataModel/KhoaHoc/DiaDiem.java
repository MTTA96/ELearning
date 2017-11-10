package com.eways.elearning.DataModel.KhoaHoc;

/**
 * Created by Tien Phat on 02/11/2017.
 */

public class DiaDiem {
    String DiaChi;
    String Quan;
    String ThanhPho;

    public DiaDiem() {
    }

    public DiaDiem(String diaChi, String quan, String thanhPho) {
        DiaChi = diaChi;
        Quan = quan;
        ThanhPho = thanhPho;
    }

    public String getDiaChi() {
        return DiaChi;
    }

    public void setDiaChi(String diaChi) {
        DiaChi = diaChi;
    }

    public String getQuan() {
        return Quan;
    }

    public void setQuan(String quan) {
        Quan = quan;
    }

    public String getThanhPho() {
        return ThanhPho;
    }

    public void setThanhPho(String thanhPho) {
        ThanhPho = thanhPho;
    }
}
