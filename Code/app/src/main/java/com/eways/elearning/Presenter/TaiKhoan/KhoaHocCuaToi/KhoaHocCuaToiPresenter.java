package com.eways.elearning.Presenter.TaiKhoan.KhoaHocCuaToi;

import android.app.Activity;

import com.eways.elearning.Model.DataModel.KhoaHoc.CustomModelKhoaHoc;
import com.eways.elearning.Model.DataModel.KhoaHoc.KhoaHoc;
import com.eways.elearning.Model.KhoaHocCuaToi.KhoaHocCuaToiModel;
import com.eways.elearning.Model.KhoaHocCuaToi.KhoaHocCuaToiModelImp;
import com.eways.elearning.View.Fragment.TaiKhoan.KhoaHocCuaToi.KhoaHocChoDuyetFragment;
import com.eways.elearning.View.Fragment.TaiKhoan.KhoaHocCuaToi.KhoaHocChoDuyetViewImp;
import com.eways.elearning.View.Fragment.TaiKhoan.KhoaHocCuaToi.KhoaHocCuaToiViewImp;
import com.eways.elearning.View.Fragment.TaiKhoan.KhoaHocCuaToi.KhoaHocDangThamGiaFragment;
import com.eways.elearning.View.Fragment.TaiKhoan.KhoaHocCuaToi.KhoaHocDangThamGiaViewImp;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by ADMIN on 1/9/2018.
 */

public class KhoaHocCuaToiPresenter implements KhoaHocCuaToiPresenterImp {
    KhoaHocCuaToiModelImp khoaHocCuaToiModelImp=new KhoaHocCuaToiModel(this);
    KhoaHocChoDuyetViewImp khoaHocChoDuyetViewImp=new KhoaHocChoDuyetFragment();
    KhoaHocCuaToiViewImp khoaHocCuaToiViewImp;
    KhoaHocDangThamGiaViewImp khoaHocDangThamGiaViewImp=new KhoaHocDangThamGiaFragment();
    ArrayList<CustomModelKhoaHoc> danhSachKhoaHocThamGiaDangCho=new ArrayList<>();
    ArrayList<CustomModelKhoaHoc> danhSachKhoaHocThamGiaDaDuyet=new ArrayList<>();
    ArrayList<CustomModelKhoaHoc> danhSachKhoaHocDaTao=new ArrayList<>();

    public KhoaHocCuaToiPresenter(KhoaHocCuaToiViewImp khoaHocCuaToiViewImp) {
        this.khoaHocCuaToiViewImp = khoaHocCuaToiViewImp;
    }

    @Override
    public void YeuCauDataKhoaHocDaDangKy(String idUser, Activity activity,KhoaHocChoDuyetFragment khoaHocChoDuyetFragment,KhoaHocDangThamGiaFragment khoaHocDangThamGiaFragment) {
        khoaHocCuaToiModelImp.NhanYeuCauLayDataDanhSachKhoaHocDangKy(idUser,activity,khoaHocChoDuyetFragment,khoaHocDangThamGiaFragment);
    }

    @Override
    public void NhanDataKhoaHocDaDangKy(ArrayList<KhoaHoc> khoaHoc,String idUser,KhoaHocChoDuyetFragment khoaHocChoDuyetFragment,KhoaHocDangThamGiaFragment khoaHocDangThamGiaFragment) {

        ArrayList<String> listDanhSachYeuCauTamDuyet=new ArrayList<>();
        ArrayList<String> listDanhSachYeuCauDangCho=new ArrayList<>();

        for (int i=0;i<khoaHoc.size();i++){
            int count=0;
            listDanhSachYeuCauDangCho.clear();
            listDanhSachYeuCauTamDuyet.clear();
            for(Map.Entry n:khoaHoc.get(i).getDanhSachYeuCau().getTamDuyet().entrySet()){
                listDanhSachYeuCauTamDuyet.add(n.getValue().toString());
            }
            for(Map.Entry m:khoaHoc.get(i).getDanhSachYeuCau().getDangCho().entrySet()){
                listDanhSachYeuCauDangCho.add(m.getValue().toString());
            }

            if (idUser.compareTo(khoaHoc.get(i).getNguoiDang())==0){
                danhSachKhoaHocDaTao.add(ParseToCustomKhoaHoc(khoaHoc.get(i)));
            }else {
                for (int j=0;j<listDanhSachYeuCauDangCho.size();j++){
                    if (idUser.compareTo(listDanhSachYeuCauDangCho.get(j))==0) {
                        count++;
                    }
                }
                if (count<=0){
                    count=0;
                    for (int m=0;m<listDanhSachYeuCauTamDuyet.size();m++){
                        if (idUser.compareTo(listDanhSachYeuCauTamDuyet.get(m))==0){
                            count++;
                        }
                    }
                    if (count<=0){
                        continue;
                    }else {
                        danhSachKhoaHocThamGiaDaDuyet.add(ParseToCustomKhoaHoc(khoaHoc.get(i)));
                    }
                }else {
                    danhSachKhoaHocThamGiaDangCho.add(ParseToCustomKhoaHoc(khoaHoc.get(i)));
                }
            }
        }
//        khoaHocChoDuyetViewImp.DataKhoaHocDangChoDuyet(danhSachKhoaHocThamGiaDangCho);
        khoaHocCuaToiViewImp.sendData(danhSachKhoaHocThamGiaDangCho, danhSachKhoaHocDaTao,danhSachKhoaHocThamGiaDaDuyet);

    }

    // chuyển model KhoaHoc sang CustomKhoaHoc
    public CustomModelKhoaHoc ParseToCustomKhoaHoc(KhoaHoc khoaHoc){
        CustomModelKhoaHoc customModelKhoaHoc=new CustomModelKhoaHoc();
        customModelKhoaHoc.setAvatar(khoaHoc.getAvatar());
        customModelKhoaHoc.setBangCap(khoaHoc.getBangCap());
        customModelKhoaHoc.setDanhSachYeuCau(khoaHoc.getDanhSachYeuCau());
        customModelKhoaHoc.setDiaDiem(khoaHoc.getDiaDiem());
        customModelKhoaHoc.setGioDang(khoaHoc.getGioDang());
        customModelKhoaHoc.setGioiTinh(khoaHoc.getGioiTinh());
        customModelKhoaHoc.setHocPhi(khoaHoc.getHocPhi());
        customModelKhoaHoc.setHoTen(khoaHoc.getHoTen());
        customModelKhoaHoc.setLichHoc(khoaHoc.getLichHoc());
        customModelKhoaHoc.setLinhVuc(khoaHoc.getLinhVuc());
        customModelKhoaHoc.setLoaiKhoaHoc(khoaHoc.isLoaiKhoaHoc());
        customModelKhoaHoc.setNgayDang(khoaHoc.getNgayDang());
        customModelKhoaHoc.setMon(khoaHoc.getMon());
        customModelKhoaHoc.setNguoiDang(khoaHoc.getNguoiDang());
        customModelKhoaHoc.setRating(khoaHoc.getRating());
        customModelKhoaHoc.setSoBuoiHoc(khoaHoc.getSoBuoiHoc());
        customModelKhoaHoc.setSoLuongHocVien(khoaHoc.getSoLuongHocVien());
        customModelKhoaHoc.setThoiLuongBuoiHoc(khoaHoc.getThoiLuongBuoiHoc());
        customModelKhoaHoc.setThongTinKhac(khoaHoc.getThongTinKhac());
        return customModelKhoaHoc;
    }
}
