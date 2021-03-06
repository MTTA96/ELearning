package com.eways.elearning.Presenter.TaiKhoan.CapNhatTaiKhoan;

import android.app.Activity;
import android.widget.ImageView;

import com.eways.elearning.Model.DataModel.TaiKhoan.TaiKhoan;
import com.eways.elearning.Handler.XuLyHinhAnh_FirebaseStorage;
import com.eways.elearning.Model.Database.SharedPreferencesHandler;
import com.eways.elearning.Model.TaiKhoan.ThongTinTaiKhoan.CapNhatTaiKhoan.CapNhatTaiKhoanModel;
import com.eways.elearning.Model.TaiKhoan.ThongTinTaiKhoan.CapNhatTaiKhoan.CapNhatTaiKhoanModelImp;
import com.eways.elearning.Util.SupportKeysList;
import com.eways.elearning.View.Fragment.DieuKhoan.DieuKhoanGiaSuViewImp;
import com.eways.elearning.View.Fragment.TaiKhoan.ThongTinTaiKhoan.CapNhatTaiKhoan.CapNhatThongTinTaiKhoanFragment;

/**
 * Created by ADMIN on 11/19/2017.
 */

public class CapNhatTaiKhoanPresenter implements CapNhatTaiKhoanPresenterImp {
    CapNhatTaiKhoanModelImp capNhatTaiKhoanModelImp=new CapNhatTaiKhoanModel(this);
    CapNhatThongTinTaiKhoanFragment capNhatThongTinTaiKhoanFragment;
    DieuKhoanGiaSuViewImp dieuKhoanGiaSuViewImp;
    SharedPreferencesHandler sharedPreferencesHandler;
    XuLyHinhAnh_FirebaseStorage xuLyHinhAnh_firebaseStorage;

    public CapNhatTaiKhoanPresenter(CapNhatThongTinTaiKhoanFragment capNhatThongTinTaiKhoanFragment) {
        this.capNhatThongTinTaiKhoanFragment = capNhatThongTinTaiKhoanFragment;
        xuLyHinhAnh_firebaseStorage=new XuLyHinhAnh_FirebaseStorage();
    }

    public CapNhatTaiKhoanPresenter(DieuKhoanGiaSuViewImp dieuKhoanGiaSuViewImp) {
        this.dieuKhoanGiaSuViewImp = dieuKhoanGiaSuViewImp;
    }

    @Override
    public void NhanDataUpdate(TaiKhoan taiKhoan, Activity activity, ImageView ivTaiLieuXacMinh_MT, ImageView ivTaiLieuXacMinh_MS,ImageView ivAvarta) {
        capNhatTaiKhoanModelImp.CapNhatTaiKhoan(taiKhoan,activity,xuLyHinhAnh_firebaseStorage.XuLy(ivTaiLieuXacMinh_MT),xuLyHinhAnh_firebaseStorage.XuLy(ivTaiLieuXacMinh_MS),xuLyHinhAnh_firebaseStorage.XuLy(ivAvarta));
    }

    @Override
    public void KetQuaCapNhat(String ketquacapnhat,TaiKhoan taiKhoan,Activity activity) {
        sharedPreferencesHandler=new SharedPreferencesHandler(activity, SupportKeysList.SHARED_PREF_FILE_NAME);
//        sharedPreferencesHandler.DangNhapThanhCong(taiKhoan.getId(), taiKhoan.getEmail(),taiKhoan.getHo(),taiKhoan.getTen(),taiKhoan.getAvatar(), taiKhoan.getTentaikhoan(),true,SupportKeysList.TAI_KHOAN_THUONG,taiKhoan.getNghenghiep(),taiKhoan.getNamsinh(),taiKhoan.getGioitinh(),taiKhoan.getTailieuxacminh_mt(),taiKhoan.getTailieuxacminh_ms(),taiKhoan.getTrinhdo(),taiKhoan.getDiadiem(),taiKhoan.getSodienthoai(),taiKhoan.getDacapnhat(),taiKhoan.getRating(),taiKhoan.getTaiKhoanGiaSu());
        capNhatThongTinTaiKhoanFragment.KetQuaCapNhat(ketquacapnhat);
    }

    @Override
    public void NhanDataCapNhatTaiKhoanGiaSu(String idUser, Activity activity) {
        capNhatTaiKhoanModelImp.CapNhatTaiKhoanGiaSu(idUser,activity);
    }

    @Override
    public void KetQuaCapNhatTaiKhoanGiaSu(String ketQua, Activity activity) {
        //update lại sharePref
        sharedPreferencesHandler=new SharedPreferencesHandler(activity, SupportKeysList.SHARED_PREF_FILE_NAME);
//        sharedPreferencesHandler.setTaiKhoanGiaSu(true);
        dieuKhoanGiaSuViewImp.NhanKetQuaCapNhatTaiKhoanGiaSu(ketQua);
    }
}
