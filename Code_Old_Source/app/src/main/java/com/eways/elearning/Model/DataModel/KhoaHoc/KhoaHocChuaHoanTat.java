package com.eways.elearning.Model.DataModel.KhoaHoc;

/**
 * Created by yowin on 10/11/2017.
 */

public class KhoaHocChuaHoanTat extends KhoaHoc{

    private DanhSachYeuCau DanhSachYeuCau;

    public KhoaHocChuaHoanTat() {
    }

    public com.eways.elearning.Model.DataModel.KhoaHoc.DanhSachYeuCau getDanhSachYeuCau() {
        return DanhSachYeuCau;
    }

    public void setDanhSachYeuCau(com.eways.elearning.Model.DataModel.KhoaHoc.DanhSachYeuCau danhSachYeuCau) {
        DanhSachYeuCau = danhSachYeuCau;
    }
    
}
