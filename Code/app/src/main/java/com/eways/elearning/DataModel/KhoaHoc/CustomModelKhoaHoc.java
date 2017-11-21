package com.eways.elearning.DataModel.KhoaHoc;

import java.util.ArrayList;

/**
 * Created by Tran Tien Phat on 14/11/2017.
 * Description: Custom view model. Dùng cho custom_item_baidang_listview
 */

public class CustomModelKhoaHoc {

    public String TenNguoiDang;
    public String UIDNguoiDang;
    public String LinkAvatar;
    public ArrayList<String> BuoiHoc;
    public String Rating;
    public String HocPhi;
    public ArrayList<String> MonHoc;
    public ArrayList<String> Lop;

    public CustomModelKhoaHoc() {
    }

    public CustomModelKhoaHoc(String tenNguoiDang, String UIDNguoiDang, String linkAvatar, ArrayList<String> buoiHoc, String rating, String hocPhi, ArrayList<String> monHoc, ArrayList<String> lop) {
        TenNguoiDang = tenNguoiDang;
        this.UIDNguoiDang = UIDNguoiDang;
        LinkAvatar = linkAvatar;
        BuoiHoc = buoiHoc;
        Rating = rating;
        HocPhi = hocPhi;
        MonHoc = monHoc;
        Lop = lop;
    }
}
