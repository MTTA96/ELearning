package com.eways.elearning.Presenter.TaiKhoan.CapNhatTaiKhoan;

import android.app.Activity;

import com.eways.elearning.DataModel.TaiKhoan;
import com.eways.elearning.Model.TaiKhoan.ThongTinTaiKhoan.CapNhatTaiKhoan.CapNhatTaiKhoanModel;
import com.eways.elearning.Model.TaiKhoan.ThongTinTaiKhoan.CapNhatTaiKhoan.CapNhatTaiKhoanModelImp;
import com.eways.elearning.View.Fragment.TaiKhoan.ThongTinTaiKhoan.CapNhatTaiKhoan.CapNhatThongTinTaiKhoanFragment;

/**
 * Created by ADMIN on 11/19/2017.
 */

public class CapNhatTaiKhoanPresenter implements CapNhatTaiKhoanPresenterImp {
    CapNhatTaiKhoanModelImp capNhatTaiKhoanModelImp=new CapNhatTaiKhoanModel(this);
    CapNhatThongTinTaiKhoanFragment capNhatThongTinTaiKhoanFragment;

    public CapNhatTaiKhoanPresenter(CapNhatThongTinTaiKhoanFragment capNhatThongTinTaiKhoanFragment) {
        this.capNhatThongTinTaiKhoanFragment = capNhatThongTinTaiKhoanFragment;
    }

    @Override
    public void NhanDataUpdate(TaiKhoan taiKhoan, Activity activity) {
        capNhatTaiKhoanModelImp.CapNhatTaiKhoan(taiKhoan,activity);
    }

    @Override
    public void KetQuaCapNhat(String ketquacapnhat) {
        capNhatThongTinTaiKhoanFragment.KetQuaCapNhat(ketquacapnhat);
    }
}
