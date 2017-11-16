package com.eways.elearning.DataModel.KhoaHoc;

import java.util.ArrayList;

/**
 * Created by Tran Tien Phat on 14/11/2017.
 * Description: Custom view model. Dùng cho custom_item_baidang_listview
 */

public class CustomModelKhoaHoc {

    private String TenNguoiDang;
    private String UIDNguoiDang;
    private String LinkAvatar;
    private String BuoiHoc;
    private String Rating;
    private ArrayList<String> MonHoc;
    private ArrayList<String> Lop;

    public String getRating() {
        return Rating;
    }

    public void setRating(String rating) {
        Rating = rating;
    }

    public CustomModelKhoaHoc() {
    }

    public CustomModelKhoaHoc(String tenNguoiDang, String UIDNguoiDang, String linkAvatar, String buoiHoc, String rating, ArrayList<String> monHoc, ArrayList<String> lop) {
        TenNguoiDang = tenNguoiDang;
        this.UIDNguoiDang = UIDNguoiDang;
        LinkAvatar = linkAvatar;
        BuoiHoc = buoiHoc;
        Rating = rating;
        MonHoc = monHoc;
        Lop = lop;
    }

    public String getTenNguoiDang() {
        return TenNguoiDang;
    }

    public void setTenNguoiDang(String tenNguoiDang) {
        TenNguoiDang = tenNguoiDang;
    }

    public String getUIDNguoiDang() {
        return UIDNguoiDang;
    }

    public void setUIDNguoiDang(String UIDNguoiDang) {
        this.UIDNguoiDang = UIDNguoiDang;
    }

    public String getLinkAvatar() {
        return LinkAvatar;
    }

    public void setLinkAvatar(String linkAvatar) {
        LinkAvatar = linkAvatar;
    }

    public String getBuoiHoc() {
        return BuoiHoc;
    }

    public void setBuoiHoc(String buoiHoc) {
        BuoiHoc = buoiHoc;
    }

    public ArrayList<String> getMonHoc() {
        return MonHoc;
    }

    public void setMonHoc(ArrayList<String> monHoc) {
        MonHoc = monHoc;
    }

    public ArrayList<String> getLop() {
        return Lop;
    }

    public void setLop(ArrayList<String> lop) {
        Lop = lop;
    }
}
