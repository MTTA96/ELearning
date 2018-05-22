package com.eways.elearning.Model.GuiYeuCau;

//import com.eways.elearning.Presenter.GuiYeuCau.GuiYeuCauPresenterImp;


/**
 * Created by yowin on 05/12/2017.
 */

//public class GuiYeuCauModel implements GuiYeuCauModelImp {
//
//    private GuiYeuCauPresenterImp guiYeuCauPresenterImp;
//    private KiemTraTinhTrangKhoaHocImp kiemTraTinhTrangKhoaHocImp = new KiemTraTinhTrangKhoaHoc(this);
//    private DatabaseReference mData = FirebaseDatabase.getInstance().getReference();
//
//    private boolean loaiKH = false;
//    private String keyKhoaHoc, uidChu,uidKhach;
//    public GuiYeuCauModel(GuiYeuCauPresenterImp guiYeuCauPresenterImp) {
//        this.guiYeuCauPresenterImp = guiYeuCauPresenterImp;
//    }
//
//    @Override
//    public void GuiYeuCau(boolean loaiKH,String keyKhoaHoc, String uidChu, String uidKhach) {
//        kiemTraTinhTrangKhoaHocImp.TinhTrangKhoaHoc(loaiKH,keyKhoaHoc);
//        this.loaiKH = loaiKH;
//        this.keyKhoaHoc = keyKhoaHoc;
//        this.uidChu = uidChu; //Chủ khóa học
//        this.uidKhach = uidKhach; //Người yêu cầu
//    }
//
//    @Override
//    public void NhanKQTrangThaiKhoaHoc(boolean TrangThai) {
//        if(TrangThai == true)
//        {
//            GuiYeuCauVaoTaiKhoan(loaiKH,keyKhoaHoc,uidKhach);
//            GuiYeuCauVaoKhoaHoc(loaiKH,keyKhoaHoc,uidKhach);
//            guiYeuCauPresenterImp.KetQuaGuiYeuCau("Success");
//        }
//        else
//        {
//            guiYeuCauPresenterImp.KetQuaGuiYeuCau("Failed");
//        }
//    }
//
//
//    private void GuiYeuCauVaoTaiKhoan(boolean loaiKH,String keyKhoaHoc, String uid)
//    {
//        if(loaiKH) //True : Gửi gia sư || flase : gửi học viên
//        {
//            YeuCau yc = new YeuCau(keyKhoaHoc,"Đang chờ");
//            mData.child("TaiKhoan").child(uid).child("YeuCau").child("YeuCauGuiGiaSu").push().setValue(yc);
//        }
//        else
//        {
//            YeuCau yc = new YeuCau(keyKhoaHoc,"Đang chờ");
//            mData.child("TaiKhoan").child(uid).child("YeuCau").child("YeuCauGuiHocVien").push().setValue(yc);
//        }
//    }
//
//    private void GuiYeuCauVaoKhoaHoc(boolean loaiKH,String keyKhoaHoc,String uid)
//    {
//        if(loaiKH) //True : Gửi gia sư || flase : gửi học viên
//        {
////            YeuCau yc = new YeuCau(keyKhoaHoc,"Đang chờ");
//            mData.child("KhoaHoc").child("KhoaHocTimHocVien").child("ChuaHoanTat").child(keyKhoaHoc).child("DanhSachYeuCau").child("DangCho").push().setValue(uid);
//        }
//        else
//        {
////            YeuCau yc = new YeuCau(keyKhoaHoc,"Đang chờ");
//            mData.child("KhoaHoc").child("KhoaHocTimGiaSu").child("ChuaHoanTat").child(keyKhoaHoc).child("DanhSachYeuCau").child("DangCho").push().setValue(uid);
//        }
//    }
//}
