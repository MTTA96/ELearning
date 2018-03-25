package com.eways.elearning.Model.DataModel.LinhVuc;

import java.util.ArrayList;

/**
 * Created by yowin on 03/12/2017.
 */

public class Mon {
    private String TenMon;
    private ArrayList<String> DanhMucBangCap;

    public Mon() {
    }

    public Mon(String tenMon, ArrayList<String> danhMucBangCap) {
        TenMon = tenMon;
        DanhMucBangCap = danhMucBangCap;
    }

    public String getTenMon() {
        return TenMon;
    }

    public void setTenMon(String tenMon) {
        TenMon = tenMon;
    }

    public ArrayList<String> getDanhMucBangCap() {
        return DanhMucBangCap;
    }

    public void setDanhMucBangCap(ArrayList<String> danhMucBangCap) {
        DanhMucBangCap = danhMucBangCap;
    }
}
