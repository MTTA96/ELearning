package com.eways.elearning.Data.DataModel.LinhVuc;

import java.util.ArrayList;

/**
 * Created by yowin on 03/12/2017.
 */

public class LinhVuc {
    private String TenLinhVuc;
    private String LinkAnh;
    private ArrayList<Mon> DanhMucMon;

    public LinhVuc() {
    }

    public LinhVuc(String tenLinhVuc, String linkAnh, ArrayList<Mon> danhMucMon) {
        TenLinhVuc = tenLinhVuc;
        LinkAnh = linkAnh;
        DanhMucMon = danhMucMon;
    }

    public String getLinkAnh() {
        return LinkAnh;
    }

    public void setLinkAnh(String linkAnh) {
        LinkAnh = linkAnh;
    }

    public String getTenLinhVuc() {
        return TenLinhVuc;
    }

    public void setTenLinhVuc(String tenLinhVuc) {
        TenLinhVuc = tenLinhVuc;
    }

    public ArrayList<Mon> getDanhMucMon() {
        return DanhMucMon;
    }

    public void setDanhMucMon(ArrayList<Mon> danhMucMon) {
        DanhMucMon = danhMucMon;
    }

    @Override
    public String toString() {
        return "LinhVuc{" +
                "TenLinhVuc='" + TenLinhVuc + '\'' +
                ", LinkAnh='" + LinkAnh + '\'' +
                ", DanhMucMon=" + DanhMucMon +
                '}';
    }
}
