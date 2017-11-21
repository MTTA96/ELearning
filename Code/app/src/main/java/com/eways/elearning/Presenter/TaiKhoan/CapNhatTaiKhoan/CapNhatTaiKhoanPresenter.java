package com.eways.elearning.Presenter.TaiKhoan.CapNhatTaiKhoan;

import android.app.Activity;

import com.eways.elearning.DataModel.TaiKhoan;
import com.eways.elearning.Model.TaiKhoan.CapNhatTaiKhoan.CapNhatTaiKhoanModel;
import com.eways.elearning.Model.TaiKhoan.CapNhatTaiKhoan.CapNhatTaiKhoanModelImp;
import com.eways.elearning.View.Fragment.TaiKhoan.CapNhatTaiKhoan.CapNhatThongTinTaiKhoanView;

/**
 * Created by ADMIN on 11/19/2017.
 */

public class CapNhatTaiKhoanPresenter implements CapNhatTaiKhoanPresenterImp {
    CapNhatTaiKhoanModelImp capNhatTaiKhoanModelImp=new CapNhatTaiKhoanModel(this);
    CapNhatThongTinTaiKhoanView capNhatThongTinTaiKhoanView;

    public CapNhatTaiKhoanPresenter(CapNhatThongTinTaiKhoanView capNhatThongTinTaiKhoanView) {
        this.capNhatThongTinTaiKhoanView = capNhatThongTinTaiKhoanView;
    }

    @Override
    public void NhanDataUpdate(TaiKhoan taiKhoan, Activity activity) {
        capNhatTaiKhoanModelImp.CapNhatTaiKhoan(taiKhoan,activity);
    }

    @Override
    public void KetQuaCapNhat(String ketquacapnhat) {
        capNhatThongTinTaiKhoanView.KetQuaCapNhat(ketquacapnhat);
    }
}
