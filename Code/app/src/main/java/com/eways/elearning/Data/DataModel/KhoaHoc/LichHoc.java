package com.eways.elearning.Data.DataModel.KhoaHoc;

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

    public void setNgayHoc(ArrayList<String> NgayHoc) {
        this.NgayHoc = NgayHoc;
    }

    public ArrayList<String> getThoiGian() {
        return ThoiGian;
    }

    public void setThoiGian(ArrayList<String> ThoiGian) {
        this.ThoiGian = ThoiGian;
    }
}
