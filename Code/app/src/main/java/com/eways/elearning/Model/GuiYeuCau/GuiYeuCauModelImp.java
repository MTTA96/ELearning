package com.eways.elearning.Model.GuiYeuCau;

/**
 * Created by yowin on 05/12/2017.
 */

public interface GuiYeuCauModelImp {
    public void GuiYeuCau(boolean loaiKH,String keyKhoaHoc,String UIDChu,String UIDKhach);
    public void NhanKQTrangThaiKhoaHoc(boolean TrangThai);
    //UIDChu : Chủ bài đăng khóa học || UIDKhach : Người gửi yêu cầu vào bài đăng khóa học đó
    //loaiKH : loại khóa học ( True = tìm gia sư , flase = tìm học viên)
}
