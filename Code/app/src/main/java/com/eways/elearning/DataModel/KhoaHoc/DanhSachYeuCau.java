package com.eways.elearning.DataModel.KhoaHoc;

import java.util.ArrayList;

/**
 * Created by Tien Phat on 02/11/2017.
 */

public class DanhSachYeuCau {
    public ArrayList<String> DangCho;
    public ArrayList<String> TamDuyet;

    public DanhSachYeuCau() {
    }

    public DanhSachYeuCau(ArrayList<String> dangCho, ArrayList<String> tamDuyet) {
        DangCho = dangCho;
        TamDuyet = tamDuyet;
    }

}
