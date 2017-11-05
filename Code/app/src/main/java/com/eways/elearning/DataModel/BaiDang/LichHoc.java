package com.eways.elearning.DataModel.BaiDang;

import java.util.ArrayList;

/**
 * Created by Tien Phat on 02/11/2017.
 */

public class LichHoc {
    ArrayList<String> NgayHoc;
    ArrayList<String> BuoiHoc;

    public LichHoc() {
    }

    public LichHoc(ArrayList<String> ngayHoc, ArrayList<String> buoiHoc) {
        NgayHoc = ngayHoc;
        BuoiHoc = buoiHoc;
    }

    public ArrayList<String> getNgayHoc() {
        return NgayHoc;
    }

    public void setNgayHoc(ArrayList<String> ngayHoc) {
        NgayHoc = ngayHoc;
    }

    public ArrayList<String> getBuoiHoc() {
        return BuoiHoc;
    }

    public void setBuoiHoc(ArrayList<String> buoiHoc) {
        BuoiHoc = buoiHoc;
    }
}
