package com.eways.elearning.Presenter.TimKiemKhoaHoc;

import android.app.Activity;

import com.eways.elearning.DataModel.KhoaHoc.CustomModelKhoaHoc;
import com.eways.elearning.DataModel.KhoaHoc.KhoaHoc;
import com.eways.elearning.DataModel.TaiKhoan.TaiKhoan;
import com.eways.elearning.Model.KhoaHoc.TimKiemKhoaHoc.KetQuaTimKiemFragmentModel;
import com.eways.elearning.Model.KhoaHoc.TimKiemKhoaHoc.KetQuatimKiemFragmentModelImp;
import com.eways.elearning.View.Fragment.KhoaHoc.TimKiemKhoaHoc.KetQuaTimKiemFragmentViewImp;
import com.eways.elearning.View.Fragment.KhoaHoc.TimKiemKhoaHoc.KetQuaKhoaHocViewImp;
import com.eways.elearning.View.Fragment.KhoaHoc.TimKiemKhoaHoc.KetQuaNguoiViewImp;

import java.util.ArrayList;

/**
 * Created by yowin on 25/11/2017.
 */

public class KetQuaTimKiemKhoaHocFragmentPresenter implements KetQuaTimKiemKhoaHocFragmentPresenterImp {

    KetQuatimKiemFragmentModelImp ketQuatimKiemFragmentModelImp = new KetQuaTimKiemFragmentModel(this);
    KetQuaTimKiemFragmentViewImp ketQuaTimKiemFragmentViewImp;

    KetQuaKhoaHocViewImp ketQuaKhoaHocViewImpl;
    KetQuaNguoiViewImp ketQuaNguoiViewImp;

    public KetQuaTimKiemKhoaHocFragmentPresenter(KetQuaTimKiemFragmentViewImp ketQuaTimKiemFragmentViewImp) {
        this.ketQuaTimKiemFragmentViewImp = ketQuaTimKiemFragmentViewImp;
    }

    public KetQuaTimKiemKhoaHocFragmentPresenter(KetQuaKhoaHocViewImp ketQuaKhoaHocViewImpl) {
        this.ketQuaKhoaHocViewImpl = ketQuaKhoaHocViewImpl;
    }

    public KetQuaTimKiemKhoaHocFragmentPresenter(KetQuaNguoiViewImp ketQuaNguoiViewImp) {
        this.ketQuaNguoiViewImp = ketQuaNguoiViewImp;
    }



    // Yeu cau *khoa hoc hoc vien(Khi tim kiem chi tiet) va *khoa hoc gia su khi tim theo *mon
    //*khoa hoc hoc vien: Day la danh cho truong hop gia su dang tim kiem hoc vien (Cac khoa hoc nhanh "tim kiem gia su")
    //*khoa hoc gia su: Day la truong hop danh cho hoc vien dang tim kiem khoa hoc cua gia su (Cac khoa hoc nhanh "tim kiem hoc vien")
    //*mon: phan thanh tim kiem nhanh o man hinh home
    @Override
    public void guiYeuCauListKhoaHoc(KhoaHoc khoaHoc, boolean loai, String mon,Activity activity) {
        ketQuatimKiemFragmentModelImp.getListKhoaHoc(khoaHoc,loai,mon,activity);
    }

    //Lay danh sach khoa hoc tim kiem hoc vien (Hoc vien kiem gia su)
    @Override
    public void guiYeuCauTimKhoaHocGiaSu(KhoaHoc khoaHoc, Activity activity) {
        ketQuatimKiemFragmentModelImp.getListKhoaHocGiaSu(khoaHoc,activity);
    }

    //Lay listKeyID gia su
    @Override
    public void guiYeuCauTimGiaSu(KhoaHoc khoaHoc, Activity activity) {
        ketQuatimKiemFragmentModelImp.getListGiaSu(khoaHoc,activity);
    }

    //Nhan listKeyID gia su roi truyen nguoc lai cho model
    @Override
    public void nhanListKeyGiaSu(ArrayList<String> listKeyGiaSu) {
        ketQuatimKiemFragmentModelImp.nhanListKeyGiaSu(listKeyGiaSu);
    }

    //Truyen list TK gia su qua cho view
    @Override
    public void nhanListGiaSu(ArrayList<TaiKhoan> listGiaSu) {
        ketQuaNguoiViewImp.nhanListNguoi(listGiaSu);
    }

    //Truyen list Khoa hoc Gia su qua cho view
    @Override
    public void nhanListKhoaHocGiaSu(ArrayList<CustomModelKhoaHoc> chinhxac, ArrayList<CustomModelKhoaHoc> ganChinhXac) {
        ketQuaKhoaHocViewImpl.nhanListKhoaHoc(chinhxac,ganChinhXac);
    }

    //Truyen list *khoa hoc hoc vien hoac *khoa hoc gia su cho view
    @Override
    public void nhanListKhoaHoc(ArrayList<CustomModelKhoaHoc> chinhxac, ArrayList<CustomModelKhoaHoc> ganChinhXac) {
        ketQuaTimKiemFragmentViewImp.nhanListKhoaHoc(chinhxac,ganChinhXac);
    }


}
