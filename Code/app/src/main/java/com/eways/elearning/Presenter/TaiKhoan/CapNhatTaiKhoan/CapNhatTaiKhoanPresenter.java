package com.eways.elearning.Presenter.TaiKhoan.CapNhatTaiKhoan;

import android.app.Activity;

import com.eways.elearning.DataModel.TaiKhoan;
import com.eways.elearning.Model.Database.SharedPreferencesHandler;
import com.eways.elearning.Model.TaiKhoan.ThongTinTaiKhoan.CapNhatTaiKhoan.CapNhatTaiKhoanModel;
import com.eways.elearning.Model.TaiKhoan.ThongTinTaiKhoan.CapNhatTaiKhoan.CapNhatTaiKhoanModelImp;
import com.eways.elearning.Util.SupportKeysList;
import com.eways.elearning.View.Fragment.TaiKhoan.ThongTinTaiKhoan.CapNhatTaiKhoan.CapNhatThongTinTaiKhoanFragment;

/**
 * Created by ADMIN on 11/19/2017.
 */

public class CapNhatTaiKhoanPresenter implements CapNhatTaiKhoanPresenterImp {
    CapNhatTaiKhoanModelImp capNhatTaiKhoanModelImp=new CapNhatTaiKhoanModel(this);
    CapNhatThongTinTaiKhoanFragment capNhatThongTinTaiKhoanFragment;
    SharedPreferencesHandler sharedPreferencesHandler;

    public CapNhatTaiKhoanPresenter(CapNhatThongTinTaiKhoanFragment capNhatThongTinTaiKhoanFragment) {
        this.capNhatThongTinTaiKhoanFragment = capNhatThongTinTaiKhoanFragment;
    }

    @Override
    public void NhanDataUpdate(TaiKhoan taiKhoan, Activity activity) {
        capNhatTaiKhoanModelImp.CapNhatTaiKhoan(taiKhoan,activity);
    }

    @Override
    public void KetQuaCapNhat(String ketquacapnhat,TaiKhoan taiKhoan,Activity activity) {
        capNhatThongTinTaiKhoanFragment.KetQuaCapNhat(ketquacapnhat);
        sharedPreferencesHandler=new SharedPreferencesHandler(activity, SupportKeysList.SHARED_PREF_FILE_NAME);
        sharedPreferencesHandler.DangNhapThanhCong(taiKhoan.getId(), taiKhoan.getEmail(),taiKhoan.getHo(),taiKhoan.getTen(), sharedPreferencesHandler.getAvatar() != null ? sharedPreferencesHandler.getAvatar():null, taiKhoan.getTentaikhoan(),true,SupportKeysList.TAI_KHOAN_THUONG,taiKhoan.getNghenghiep(),taiKhoan.getNamsinh(),taiKhoan.getGioitinh(),taiKhoan.getTailieuxacminh_mt(),taiKhoan.getTailieuxacminh_ms());
    }
}
