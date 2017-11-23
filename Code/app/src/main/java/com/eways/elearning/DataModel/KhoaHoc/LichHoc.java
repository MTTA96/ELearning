package com.eways.elearning.DataModel.KhoaHoc;

import java.util.ArrayList;

/**
 * Created by Tien Phat on 02/11/2017.
 */

public class LichHoc {
    private ArrayList<String> NgayHoc;
    private ArrayList <String> ThoiGian;

    public LichHoc() {
    }

    public LichHoc(ArrayList<String> ngayHoc, ArrayList<String> thoiGian) {
        NgayHoc = ngayHoc;
        ThoiGian = thoiGian;
    }

    public ArrayList<String> getNgayHoc() {
        return NgayHoc;
    }

    public void setNgayHoc(ArrayList<String> ngayHoc) {
        NgayHoc = ngayHoc;
    }

    public ArrayList<String> getThoiGian() {
        return ThoiGian;
    }

    public void setThoiGian(ArrayList<String> thoiGian) {
        ThoiGian = thoiGian;
    }
}
