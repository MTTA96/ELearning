package com.eways.elearning.DataModel.Other;

import java.util.ArrayList;

/**
 * Created by yowin on 05/12/2017.
 */

public class KhuVuc {
    private String TenThanhPho;
    private ArrayList<String> DanhSachQuan;

    public KhuVuc() {
    }

    public KhuVuc(String tenThanhPho, ArrayList<String> danhSachQuan) {
        TenThanhPho = tenThanhPho;
        DanhSachQuan = danhSachQuan;
    }

    public String getTenThanhPho() {
        return TenThanhPho;
    }

    public void setTenThanhPho(String tenThanhPho) {
        TenThanhPho = tenThanhPho;
    }

    public ArrayList<String> getDanhSachQuan() {
        return DanhSachQuan;
    }

    public void setDanhSachQuan(ArrayList<String> danhSachQuan) {
        DanhSachQuan = danhSachQuan;
    }
}
