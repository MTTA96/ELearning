package com.eways.elearning.Presenter.TaoKhoaHoc;

import android.app.Activity;

import com.eways.elearning.DataModel.KhoaHoc.DanhSachYeuCau;
import com.eways.elearning.DataModel.KhoaHoc.KhoaHoc;
import com.eways.elearning.DataModel.Other.KhuVuc;
import com.eways.elearning.DataModel.LinhVuc.LinhVuc;
import com.eways.elearning.Model.KhoaHoc.TaoKhoaHoc.TaoKhoaHocModel;
import com.eways.elearning.Model.KhoaHoc.TaoKhoaHoc.TaoKhoaHocModelImp;
import com.eways.elearning.View.Fragment.KhoaHoc.TaoKhoaHoc.TaoKhoaHocFragment;
import com.eways.elearning.View.Fragment.KhoaHoc.TaoKhoaHoc.TaoKhoaHocViewImp;

import java.util.ArrayList;

/**
 * Created by yowin on 19/11/2017.
 */

public class TaoKhoaHocPresenter implements TaoKhoaHocPresenterImp {
    TaoKhoaHocModelImp taoKhoaHocModelImp=new TaoKhoaHocModel(this);
    TaoKhoaHocViewImp taoKhoaHocViewImp=new TaoKhoaHocFragment();

    public static final String RESULT_CODE_SUCCESS = "tao_thanh_cong";

    public TaoKhoaHocPresenter(TaoKhoaHocViewImp taoKhoaHocViewImp) {
        this.taoKhoaHocViewImp = taoKhoaHocViewImp;
    }

    @Override
    public void nhanThongTinKhoaHoc(KhoaHoc khoaHoc,boolean loai ,Activity activity) {
        ArrayList<String> dangcho=new ArrayList<>();
        ArrayList<String> tamduyet=new ArrayList<>();
        khoaHoc.setDanhSachYeuCau(new DanhSachYeuCau(dangcho,tamduyet));
        taoKhoaHocModelImp.postKhoaHoc(khoaHoc,loai,activity);
    }

    @Override
    public void ketQua(String ketQua) {
        taoKhoaHocViewImp.KetQuaTaoKhoaHoc(RESULT_CODE_SUCCESS);
    }

    @Override
    public void loaddataLinhvuc(Activity activity) {
        taoKhoaHocModelImp.loaddataLinhvuc(activity);
    }

    @Override
    public void loaddataKhuVuc(Activity activity) {
        taoKhoaHocModelImp.loadDataKhuVuc(activity);
    }

    @Override
    public void nhanDanhSachLinhVuc(ArrayList<LinhVuc> danhSachLinhVuc) {
        taoKhoaHocViewImp.NhanDanhSachLinhVuc(danhSachLinhVuc);
    }

    @Override
    public void nhanDanhSachKhuVuc(ArrayList<KhuVuc> danhSachKhuVuc) {
        taoKhoaHocViewImp.NhanDanhSachKhuVuc(danhSachKhuVuc);
    }
}
