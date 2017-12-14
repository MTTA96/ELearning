package com.eways.elearning.DataModel.KhoaHoc;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by Tran Tien Phat on 14/11/2017.
 * Description: Custom view model. Dùng cho custom_item_baidang_listview
 */

public class CustomModelKhoaHoc {

    public String KeyKhoaHoc;
    public String TenNguoiDang;
    public String UIDNguoiDang;
    public String LinkAvatar;
    public ArrayList<String> BuoiHoc;
    public String Rating;
    public String HocPhi;
    public ArrayList<String> MonHoc;

    private NumberFormat formatGia = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));

    public CustomModelKhoaHoc() {
    }

    public CustomModelKhoaHoc(String tenNguoiDang, String UIDNguoiDang, String linkAvatar, ArrayList<String> buoiHoc, String rating, String hocPhi, ArrayList<String> monHoc) {
        TenNguoiDang = tenNguoiDang;
        this.UIDNguoiDang = UIDNguoiDang;
        LinkAvatar = linkAvatar;
        BuoiHoc = buoiHoc;
        Rating = rating;
        HocPhi = hocPhi;
        MonHoc = monHoc;
    }

    public CustomModelKhoaHoc(String keyKhoaHoc, String tenNguoiDang, String UIDNguoiDang, String linkAvatar, ArrayList<String> buoiHoc, String rating, String hocPhi, ArrayList<String> monHoc) {
        KeyKhoaHoc = keyKhoaHoc;
        TenNguoiDang = tenNguoiDang;
        this.UIDNguoiDang = UIDNguoiDang;
        LinkAvatar = linkAvatar;
        BuoiHoc = buoiHoc;
        Rating = rating;
        HocPhi = hocPhi;
        MonHoc = monHoc;
    }

    /**
     * Định dạng giá hiển thị
     */
    public String formatGia(long gia){
        return formatGia.format(gia);
    }
}
