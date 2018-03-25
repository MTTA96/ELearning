package com.eways.elearning.Model.DataModel.BaiDang;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by ADMIN on 11/9/2017.
 */

public class LinhVucBaiDang {
    int idLinhVuc;
    String tenLinhVuc;
    int hinhLinhVuc;
    ArrayList<String> danhsachchitiet;

    public LinhVucBaiDang(int idLinhVuc, String tenLinhVuc, int hinhLinhVuc, ArrayList<String> danhsachchitiet) {
        this.idLinhVuc = idLinhVuc;
        this.tenLinhVuc = tenLinhVuc;
        this.hinhLinhVuc = hinhLinhVuc;
        this.danhsachchitiet = danhsachchitiet;
    }

    public int getIdLinhVuc() {
        return idLinhVuc;
    }

    public void setIdLinhVuc(int idLinhVuc) {
        this.idLinhVuc = idLinhVuc;
    }

    public String getTenLinhVuc() {
        return tenLinhVuc;
    }

    public void setTenLinhVuc(String tenLinhVuc) {
        this.tenLinhVuc = tenLinhVuc;
    }

    public int getHinhLinhVuc() {
        return hinhLinhVuc;
    }

    public void setHinhLinhVuc(int hinhLinhVuc) {
        this.hinhLinhVuc = hinhLinhVuc;
    }

    public ArrayList<String> getDanhsachchitiet() {
        return danhsachchitiet;
    }

    public void setDanhsachchitiet(ArrayList<String> danhsachchitiet) {
        this.danhsachchitiet = danhsachchitiet;
    }
}

