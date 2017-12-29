package com.eways.elearning.DataModel;

import com.eways.elearning.DataModel.KhoaHoc.DiaDiem;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by zzzzz on 8/28/2017.
 */

public class TaiKhoan {
    String id;
    String email;
    String ho;
    String ten;
    String tentaikhoan;
    boolean dadangnhap;
    String loaitaikhoan;
    String password;
    String nghenghiep;
    String namsinh;
    String gioitinh;
    String tailieuxacminh_mt;
    String tailieuxacminh_ms;
    String trinhdo;
    String diadiem;
    String sodienthoai;
    String avatar;
    boolean dacapnhat;

    public TaiKhoan() {
    }


    public TaiKhoan(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public TaiKhoan(String email, String password, String ho, String ten) {
        this.email = email;
        this.password = password;
        this.ho = ho;
        this.ten = ten;
    }

    public TaiKhoan(String id, String email, String ho, String ten, String tentaikhoan, boolean dadangnhap, String loaitaikhoan, String password, String nghenghiep, String namsinh, String gioitinh, String tailieuxacminh_mt, String tailieuxacminh_ms, String trinhdo, String diadiem, String sodienthoai, String avatar, boolean dacapnhat) {
        this.id = id;
        this.email = email;
        this.ho = ho;
        this.ten = ten;
        this.tentaikhoan = tentaikhoan;
        this.dadangnhap = dadangnhap;
        this.loaitaikhoan = loaitaikhoan;
        this.password = password;
        this.nghenghiep = nghenghiep;
        this.namsinh = namsinh;
        this.gioitinh = gioitinh;
        this.tailieuxacminh_mt = tailieuxacminh_mt;
        this.tailieuxacminh_ms = tailieuxacminh_ms;
        this.trinhdo = trinhdo;
        this.diadiem = diadiem;
        this.sodienthoai = sodienthoai;
        this.avatar = avatar;
        this.dacapnhat = dacapnhat;
    }


//    public TaiKhoan(String id, String email, String ho, String ten, String tentaikhoan, boolean dadangnhap, String loaitaikhoan, String password, String nghenghiep, String namsinh, String gioitinh, String tailieuxacminh_mt, String tailieuxacminh_ms, String trinhdo, String diadiem, String sodienthoai, String avatar, boolean dacapnhat, String rating) {
//        this.id = id;
//        this.email = email;
//        this.ho = ho;
//        this.ten = ten;
//        this.tentaikhoan = tentaikhoan;
//        this.dadangnhap = dadangnhap;
//        this.loaitaikhoan = loaitaikhoan;
//        this.password = password;
//        this.nghenghiep = nghenghiep;
//        this.namsinh = namsinh;
//        this.gioitinh = gioitinh;
//        this.tailieuxacminh_mt = tailieuxacminh_mt;
//        this.tailieuxacminh_ms = tailieuxacminh_ms;
//        this.trinhdo = trinhdo;
//        this.diadiem = diadiem;
//        this.sodienthoai = sodienthoai;
//        this.avatar = avatar;
//        this.dacapnhat = dacapnhat;
//        this.rating = rating;
//    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHo() {
        return ho;
    }

    public void setHo(String ho) {
        this.ho = ho;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getTentaikhoan() {
        return tentaikhoan;
    }

    public void setTentaikhoan(String tentaikhoan) {
        this.tentaikhoan = tentaikhoan;
    }

    public boolean isDadangnhap() {
        return dadangnhap;
    }

    public void setDadangnhap(boolean dadangnhap) {
        this.dadangnhap = dadangnhap;
    }

    public String getLoaitaikhoan() {
        return loaitaikhoan;
    }

    public void setLoaitaikhoan(String loaitaikhoan) {
        this.loaitaikhoan = loaitaikhoan;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNghenghiep() {
        return nghenghiep;
    }

    public void setNghenghiep(String nghenghiep) {
        this.nghenghiep = nghenghiep;
    }

    public String getNamsinh() {
        return namsinh;
    }

    public void setNamsinh(String namsinh) {
        this.namsinh = namsinh;
    }

    public String getGioitinh() {
        return gioitinh;
    }

    public void setGioitinh(String gioitinh) {
        this.gioitinh = gioitinh;
    }

    public String getTailieuxacminh_mt() {
        return tailieuxacminh_mt;
    }

    public void setTailieuxacminh_mt(String tailieuxacminh_mt) {
        this.tailieuxacminh_mt = tailieuxacminh_mt;
    }

    public String getTailieuxacminh_ms() {
        return tailieuxacminh_ms;
    }

    public void setTailieuxacminh_ms(String tailieuxacminh_ms) {
        this.tailieuxacminh_ms = tailieuxacminh_ms;
    }

    public String getTrinhdo() {
        return trinhdo;
    }

    public void setTrinhdo(String trinhdo) {
        this.trinhdo = trinhdo;
    }

    public String getDiadiem() {
        return diadiem;
    }

    public void setDiadiem(String diadiem) {
        this.diadiem = diadiem;
    }

    public String getSodienthoai() {
        return sodienthoai;
    }

    public void setSodienthoai(String sodienthoai) {
        this.sodienthoai = sodienthoai;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public boolean getDacapnhat() {
        return dacapnhat;
    }

    public void setDacapnhat(boolean dacapnhat) {
        this.dacapnhat = dacapnhat;
    }
    
}

