package com.eways.elearning.DataModel;

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

    public TaiKhoan() {
    }

    public TaiKhoan(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public TaiKhoan(String id, String email, String ho, String ten, String tentaikhoan, boolean dadangnhap, String loaitaikhoan, String password, String nghenghiep, String namsinh, String gioitinh) {
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
    }

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
}