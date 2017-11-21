package com.eways.elearning.DataModel.KhoaHoc;

import java.util.ArrayList;

/**
 * Created by Tien Phat on 02/11/2017.
 */

public class LichHoc {
    public ArrayList<String> NgayHoc;
    public ArrayList <String> ThoiGian;

    public LichHoc() {
    }

    public LichHoc(ArrayList<String> ngayHoc, ArrayList<String> thoiGian) {
        NgayHoc = ngayHoc;
        ThoiGian = thoiGian;
    }
}
