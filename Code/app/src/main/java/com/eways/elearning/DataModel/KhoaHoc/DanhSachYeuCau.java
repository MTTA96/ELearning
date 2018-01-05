package com.eways.elearning.DataModel.KhoaHoc;

import java.util.ArrayList;

/**
 * Created by Tien Phat on 02/11/2017.
 */

public class DanhSachYeuCau {
    private ArrayList<String> dangCho;
    private ArrayList<String> tamDuyet;

    public DanhSachYeuCau() {
    }

    public DanhSachYeuCau(ArrayList<String> dangCho, ArrayList<String> tamDuyet) {
        this.dangCho = dangCho;
        this.tamDuyet = tamDuyet;
    }

    public ArrayList<String> getDangCho() {
        return dangCho;
    }

    public void setDangCho(ArrayList<String> dangCho) {
        this.dangCho = dangCho;
    }

    public ArrayList<String> getTamDuyet() {
        return tamDuyet;
    }

    public void setTamDuyet(ArrayList<String> tamDuyet) {
        this.tamDuyet = tamDuyet;
    }
}
