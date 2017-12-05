package com.eways.elearning.DataModel;

/**
 * Created by yowin on 05/12/2017.
 */

public class YeuCau {
    private String IDBai;
    private String TinhTrang;

    public YeuCau() {
    }

    public YeuCau(String IDBai, String tinhTrang) {
        this.IDBai = IDBai;
        TinhTrang = tinhTrang;
    }

    public String getIDBai() {
        return IDBai;
    }

    public void setIDBai(String IDBai) {
        this.IDBai = IDBai;
    }

    public String getTinhTrang() {
        return TinhTrang;
    }

    public void setTinhTrang(String tinhTrang) {
        TinhTrang = tinhTrang;
    }
}
