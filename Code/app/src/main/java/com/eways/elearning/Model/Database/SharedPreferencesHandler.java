package com.eways.elearning.Model.Database;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by zzzzz on 10/8/2017.
 */

public class SharedPreferencesHandler {
    private SharedPreferences sharedPreferences;
    private  SharedPreferences.Editor editor;
    private Context context;

    private final String KEY_FIRST_RUN = "first_run";
    private final String KEY_ID="id";
    private final String KEY_EMAIL="email";
    private final String KEY_TEN="ten";
    private final String KEY_HO="ho";
    private final String KEY_TEN_TAIKHOAN="tentaikhoan";
    private final String KEY_LOAI_TAIKHOAN="loaitaikhoan";
    private final String KEY_DA_DANGNHAP="dadangnhap";
    private final String KEY_MATKHAU="matkhau";
    private final String KEY_NGHENGHIEP="nghenghiep";
    private final String KEY_NGAYSINH="ngaysinh";
    private final String KEY_GIOITINH="gioitinh";
    private final String KEY_AVATAR ="avatar";
    private final String KEY_TAILIEUXACMINH_MT="tailieuxacminh_mt";
    private final String KEY_TAILIEUXACMINH_MS="tailieuxacminh_ms";
    private final String KEY_TRINHDO="trinhdo";
    private final String KEY_DIACHI="diachi";
    private final String KEY_SODIENTHOAI="sodienthoai";
    private final String KEY_DACAPNHAT="dacapnhat";
    private final String KEY_RATING="rating";
    private final String KEY_DANHSACHTAILIEUCHUYENMON="danhsachtailieuchuyenmon";
    private final String KEY_TAIKHOANGIASU="taikhoangiasu";

    public SharedPreferencesHandler(Context context,String tenFile) {
        this.context = context;

        sharedPreferences = context.getSharedPreferences(tenFile,Context.MODE_PRIVATE);
        editor=sharedPreferences.edit();
    }

    //Constructor for the first time using this app
    public SharedPreferencesHandler(){
        setID("");
        setEmail("");
        setHo("");
        setTen("");
        setTenTaiKhoan("");
        setAvatar("null");
        setDaDangNhap(false);
        setLoaiTaiKhoan("");
    }

    public boolean checkFirstRun(){
        return sharedPreferences.contains(KEY_FIRST_RUN);
    }
    public void setFirstRun(){
        editor.putBoolean(KEY_FIRST_RUN, true);
        editor.commit();
    }

    public String getID(){
        return sharedPreferences.getString(KEY_ID,null);
    }
    public void setID(String Id){
        editor.putString(KEY_ID,Id);
        editor.commit();
    }
    //Key tên tài khoản
    public String getTenTaiKhoan(){
        return sharedPreferences.getString(KEY_TEN_TAIKHOAN, null);
    }

    public void setTenTaiKhoan(String ten_tai_khoan){
        editor.putString(KEY_TEN_TAIKHOAN, ten_tai_khoan);
        editor.commit();
    }
    public String getEmail(){
        return sharedPreferences.getString(KEY_EMAIL, null);
    }

    public void setEmail(String email){
        editor.putString(KEY_EMAIL, email);
        editor.commit();
    }
    //Key đăng nhập
    public boolean getDaDangNhap(){
        return sharedPreferences.getBoolean(KEY_DA_DANGNHAP, false);
    }

    public void setDaDangNhap(boolean status){
        editor.putBoolean(KEY_DA_DANGNHAP, status);
        editor.commit();
    }
    //Key loại tài khoản
    public String getLoaiTaiKhoan(){
        return sharedPreferences.getString(KEY_LOAI_TAIKHOAN, "");
    }

    public void setLoaiTaiKhoan(String loaiTK){
        editor.putString(KEY_LOAI_TAIKHOAN, loaiTK);
        editor.commit();
    }
    //Key avarta
    public String getAvatar(){
        return sharedPreferences.getString(KEY_AVATAR, "");
    }

    public void setAvatar(String avarta){
        editor.putString(KEY_AVATAR, avarta);
        editor.commit();
    }

    public String getTen(){
        return sharedPreferences.getString(KEY_TEN, "");
    }

    public void setTen(String loaiTK){
        editor.putString(KEY_TEN, loaiTK);
        editor.commit();
    }

    //Ho
    public String getHo(){
        return sharedPreferences.getString(KEY_HO, "");
    }

    public void setHo(String ho){
        editor.putString(KEY_TEN,ho);
        editor.commit();
    }

    public String getMatKhau(){
        return sharedPreferences.getString(KEY_MATKHAU, "");
    }

    public void setMatKhau(String matKhau){
        editor.putString(KEY_MATKHAU, matKhau);
        editor.commit();
    }
    public String getNgheNghiep(){
        return sharedPreferences.getString(KEY_NGHENGHIEP, "");
    }

    public void setNgheNghiep(String ngheNghiep){
        editor.putString(KEY_NGHENGHIEP, ngheNghiep);
        editor.commit();
    }
    public String getNamSinh(){
        return sharedPreferences.getString(KEY_NGAYSINH, "");
    }

    public void setNamSinh(String namSinh){
        editor.putString(KEY_NGAYSINH, namSinh);
        editor.commit();
    }

    public String getGioiTinh(){
        return sharedPreferences.getString(KEY_GIOITINH, "");
    }

    public void setGioiTinh(String gioiTinh){
        editor.putString(KEY_GIOITINH, gioiTinh);
        editor.commit();
    }
    public String getTaiLieuXacMinh_mt(){
        return sharedPreferences.getString(KEY_TAILIEUXACMINH_MT, "");
    }

    public void setTaiLieuXacMinh_mt(String tailieuxacminh_mt){
        editor.putString(KEY_TAILIEUXACMINH_MT,tailieuxacminh_mt);
        editor.commit();
    }

    public String getTaiLieuXacMinh_ms(){
        return sharedPreferences.getString(KEY_TAILIEUXACMINH_MS, "");
    }

    public void setTaiLieuXacMinh_ms(String taiLieuXacMinh_ms){
        editor.putString(KEY_TAILIEUXACMINH_MS,taiLieuXacMinh_ms);
        editor.commit();
    }

    public String getTrinhDo(){
        return sharedPreferences.getString(KEY_TRINHDO, "");
    }

    public void setTrinhDo(String trinhDo){
        editor.putString(KEY_TRINHDO,trinhDo);
        editor.commit();
    }

    public String getDiaChi(){
        return sharedPreferences.getString(KEY_DIACHI, "");
    }

    public void setDiaChi(String diaChi){
        editor.putString(KEY_DIACHI,diaChi);
        editor.commit();
    }

    public String getSoDienThoai(){
        return sharedPreferences.getString(KEY_SODIENTHOAI, "");
    }

    public void setSoDienThoai(String soDienThoai){
        editor.putString(KEY_SODIENTHOAI,soDienThoai);
        editor.commit();
    }

    public boolean getDaCapNhat(){
        return sharedPreferences.getBoolean(KEY_DACAPNHAT, false);
    }

    public void setDaCapNhat(boolean daCapNhat){
        editor.putBoolean(KEY_DACAPNHAT,daCapNhat);
        editor.commit();
    }
    public String getRating(){
        return sharedPreferences.getString(KEY_RATING, "");
    }

    public void setRating(String rating) {
        editor.putString(KEY_RATING, rating);
        editor.commit();
    }

    public boolean getTaiKhoanGiaSu(){
        return sharedPreferences.getBoolean(KEY_TAIKHOANGIASU, false);
    }

    public void setTaiKhoanGiaSu(boolean taiKhoanGiaSu){
        editor.putBoolean(KEY_TAIKHOANGIASU,taiKhoanGiaSu);
        editor.commit();
    }


    public void DangNhapThanhCong(String id, String email, String ho, String ten, String avatar, String tenTK,boolean daDangNhap, String loaiTK,String ngheNghiep,String namSinh,String gioiTinh,String taiLieuXacMinh_mt,String taiLieuXacMinh_ms,String trinhdo,String diachi,String sodienthoai,boolean daCapnNhat,String rating,boolean taiKhoanGiaSu){
        setID(id);
        setEmail(email);
        setHo(ho);
        setTen(ten);
        setAvatar(avatar);
        setTenTaiKhoan(tenTK);
        setDaDangNhap(daDangNhap);
        setLoaiTaiKhoan(loaiTK);
        setNgheNghiep(ngheNghiep);
        setNamSinh(namSinh);
        setGioiTinh(gioiTinh);
        setTaiLieuXacMinh_mt(taiLieuXacMinh_mt);
        setTaiLieuXacMinh_ms(taiLieuXacMinh_ms);
        setTrinhDo(trinhdo);
        setDiaChi(diachi);
        setSoDienThoai(sodienthoai);
        setDaCapNhat(daCapnNhat);
        setRating(rating);
        setTaiKhoanGiaSu(taiKhoanGiaSu);
    }
    public void DangXuat(){
        setID("");
        setEmail("");
        setHo("");
        setTen("");
        setTenTaiKhoan("");
        setDaDangNhap(false);
        setLoaiTaiKhoan("");
        setTaiLieuXacMinh_mt("");
        setTaiLieuXacMinh_ms("");
        setDiaChi("");
        setSoDienThoai("");
        setAvatar("");
        setDaDangNhap(false);
        setRating("");
        setTaiKhoanGiaSu(false);
    }
}
