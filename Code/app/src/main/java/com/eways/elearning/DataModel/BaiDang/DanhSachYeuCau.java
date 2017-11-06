package com.eways.elearning.DataModel.BaiDang;

import java.util.ArrayList;

/**
 * Created by Tien Phat on 02/11/2017.
 */

public class DanhSachYeuCau {
    ArrayList<String> DangCho;
    ArrayList<String> TamDuyet;

    public DanhSachYeuCau() {
    }

    public DanhSachYeuCau(ArrayList<String> dangCho, ArrayList<String> tamDuyet) {
        DangCho = dangCho;
        TamDuyet = tamDuyet;
    }

    public ArrayList<String> getDangCho() {
        return DangCho;
    }

    public void setDangCho(ArrayList<String> dangCho) {
        DangCho = dangCho;
    }

    public ArrayList<String> getTamDuyet() {
        return TamDuyet;
    }

    public void setTamDuyet(ArrayList<String> tamDuyet) {
        TamDuyet = tamDuyet;
    }
}
