package com.eways.elearning.Data.DataModel.KhoaHoc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Tien Phat on 02/11/2017.
 */

public class DanhSachYeuCau {
    Map<String, Object> dangCho = new HashMap<String, Object>();
    Map<String, Object> tamDuyet = new HashMap<String, Object>();

    public DanhSachYeuCau() {
    }

    public DanhSachYeuCau(Map<String, Object> dangCho, Map<String, Object> tamDuyet) {
        this.dangCho = dangCho;
        this.tamDuyet = tamDuyet;
    }

    public Map<String, Object> getDangCho() {
        return dangCho;
    }

    public void setDangCho(Map<String, Object> dangCho) {
        this.dangCho = dangCho;
    }

    public Map<String, Object> getTamDuyet() {
        return tamDuyet;
    }

    public void setTamDuyet(Map<String, Object> tamDuyet) {
        this.tamDuyet = tamDuyet;
    }
}
