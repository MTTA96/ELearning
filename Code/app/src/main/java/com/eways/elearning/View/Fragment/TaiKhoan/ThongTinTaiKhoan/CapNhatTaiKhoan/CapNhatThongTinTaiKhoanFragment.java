package com.eways.elearning.View.Fragment.TaiKhoan.ThongTinTaiKhoan.CapNhatTaiKhoan;


import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.eways.elearning.DataModel.LayHinhModel;
import com.eways.elearning.DataModel.TaiKhoan;
import com.eways.elearning.Handler.Adapter.ChonHinhAdapter;
import com.eways.elearning.Handler.DialogPlusHandler;
import com.eways.elearning.Handler.FragmentHandler;
import com.eways.elearning.Handler.ImageHandler;
import com.eways.elearning.Model.Database.SharedPreferencesHandler;
import com.eways.elearning.Presenter.TaiKhoan.CapNhatTaiKhoan.CapNhatTaiKhoanPresenter;
import com.eways.elearning.Presenter.TaiKhoan.CapNhatTaiKhoan.CapNhatTaiKhoanPresenterImp;
import com.eways.elearning.R;
import com.eways.elearning.Util.SupportKeysList;
import com.eways.elearning.View.Fragment.TaiKhoan.QuanLyTaiKhoanFragment;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class CapNhatThongTinTaiKhoanFragment extends Fragment implements CapNhatTaiKhoanViewImp, View.OnClickListener{
    Spinner spNamsinh, spGiotinh;
    Button btnLuuCapNhat;
    EditText etHoTen, etNgheNghiep;
    ImageView imTaiLieuXacMinh_mt,imTaiLieuXacMinh_ms;
    Calendar calendar;
    ArrayList<String> danhsachNam;
    SharedPreferencesHandler sharedPreferencesHandler;
    FragmentHandler fragmentHandler;
    CapNhatTaiKhoanPresenterImp capNhatTaiKhoanPresenterImp;



    ChonHinhAdapter adDanhSachChonHinh;
    ArrayList<LayHinhModel> danhSachChon;
    DialogPlusHandler dialogPlusHandler;

    int RESULT_LOAD_HINHMT=1;
    int RESULT_LOAD_HINHMS=2;

    public CapNhatThongTinTaiKhoanFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        danhsachNam = new ArrayList<>();
        fragmentHandler = new FragmentHandler(getActivity(), getActivity().getSupportFragmentManager());
        sharedPreferencesHandler = new SharedPreferencesHandler(getContext(), SupportKeysList.SHARED_PREF_FILE_NAME);

        capNhatTaiKhoanPresenterImp = new CapNhatTaiKhoanPresenter(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cap_nhat_thong_tin_tai_khoan, container, false);

        spNamsinh = (Spinner) view.findViewById(R.id.spNamsinh_CNTTTK);
        spGiotinh = (Spinner) view.findViewById(R.id.spGioiTinh);
        etHoTen = (EditText) view.findViewById(R.id.etHoten_CNTTTK);
        etNgheNghiep = (EditText) view.findViewById(R.id.etNghenghiep_CNTTTK);
        btnLuuCapNhat = (Button) view.findViewById(R.id.btnLuuCNTTTK);
        imTaiLieuXacMinh_mt= (ImageView) view.findViewById(R.id.ivTaiLieuXacMinh_mt);
        imTaiLieuXacMinh_ms= (ImageView) view.findViewById(R.id.ivTaiLieuXacMinh_ms);

        btnLuuCapNhat.setOnClickListener(this);

        imTaiLieuXacMinh_mt.setOnClickListener(this);
        imTaiLieuXacMinh_ms.setOnClickListener(this);

        //cai đặt dialogplus
        danhSachChon=new ArrayList<>();
        danhSachChon.add(new LayHinhModel(1,R.drawable.camera_icon,"Máy ảnh"));
        danhSachChon.add(new LayHinhModel(2,R.drawable.gallery_icon,"Bộ sưu tập"));
        adDanhSachChonHinh=new ChonHinhAdapter(getActivity(),R.layout.item_chonhinh_cntttk,danhSachChon);
        dialogPlusHandler=new DialogPlusHandler(getActivity(),adDanhSachChonHinh,this);

        LoadData();
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        return view;
    }

    @Override
    public void KetQuaCapNhat(String ketQuaCapNhat) {
        if (ketQuaCapNhat.compareTo(SupportKeysList.TAG_CAPNHATTHANHCONG) == 0) {
            Toast.makeText(getContext(),"Cập Nhật Thành Công",Toast.LENGTH_SHORT).show();
            fragmentHandler.ChuyenFragment(new QuanLyTaiKhoanFragment(), false, null);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnLuuCNTTTK) {
            capNhatTaiKhoanPresenterImp.NhanDataUpdate(new TaiKhoan(sharedPreferencesHandler.getID(), sharedPreferencesHandler.getEmail(),
                    sharedPreferencesHandler.getHo(), etHoTen.getText().toString(), sharedPreferencesHandler.getTenTaiKhoan(),
                    sharedPreferencesHandler.getDaDangNhap(), sharedPreferencesHandler.getLoaiTaiKhoan(),
                    sharedPreferencesHandler.getMatKhau(), etNgheNghiep.getText().toString(), spNamsinh.getSelectedItem().toString(),
                    spGiotinh.getSelectedItem().toString(),sharedPreferencesHandler.getTaiLieuXacMinh_mt(),sharedPreferencesHandler.getTaiLieuXacMinh_ms()), getActivity(),imTaiLieuXacMinh_mt,imTaiLieuXacMinh_ms);
        }
        if (v.getId() == R.id.ivTaiLieuXacMinh_mt){
            dialogPlusHandler.ShowDialogChonHinh(0);
        }
        if (v.getId() == R.id.ivTaiLieuXacMinh_ms){
            dialogPlusHandler.ShowDialogChonHinh(1);
        }
    }

    //Load Data tài khoản
    public void LoadData() {

        if(sharedPreferencesHandler.getHo().isEmpty() && sharedPreferencesHandler.getTen().isEmpty()){
            etHoTen.setText("");
        }
        else
            etHoTen.setText(sharedPreferencesHandler.getHo().toString()+sharedPreferencesHandler.getTen().toString());
        if (sharedPreferencesHandler.getNamSinh().toString().isEmpty()){
            calendar = Calendar.getInstance();
            int currentYear = calendar.get(Calendar.YEAR);
            for (int i = 1960; i <= currentYear; i++) {
                danhsachNam.add(String.valueOf(i));
            }
            ArrayAdapter adDanhSachNam=new ArrayAdapter(getContext(),android.R.layout.simple_spinner_item,danhsachNam);
            spNamsinh.setAdapter(adDanhSachNam);

        }else{
            calendar = Calendar.getInstance();
            int currentYear = calendar.get(Calendar.YEAR);
            for (int i = 1960; i <= currentYear; i++) {
                danhsachNam.add(String.valueOf(i));
            }
            ArrayAdapter adDanhSachNam=new ArrayAdapter(getContext(),android.R.layout.simple_spinner_item,danhsachNam);
            spNamsinh.setAdapter(adDanhSachNam);
            int spinnerPosition=adDanhSachNam.getPosition(sharedPreferencesHandler.getNamSinh());
            spNamsinh.setSelection(spinnerPosition);
        }
        if (sharedPreferencesHandler.getGioiTinh().isEmpty()) {
            ArrayAdapter adDanhSachGioiTinh = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.ListGioiTinh));
            spGiotinh.setAdapter(adDanhSachGioiTinh);
        }else{
            ArrayAdapter adDanhSachGioiTinh = new ArrayAdapter(getContext(),android.R.layout.simple_spinner_item,getResources().getStringArray(R.array.ListGioiTinh));
            spGiotinh.setAdapter(adDanhSachGioiTinh);
            int spinnerPosition=adDanhSachGioiTinh.getPosition(sharedPreferencesHandler.getGioiTinh());
            spGiotinh.setSelection(spinnerPosition);
        }
        if(sharedPreferencesHandler.getNgheNghiep().isEmpty()){
            etNgheNghiep.setText("");
        }
        else {
            etNgheNghiep.setText(sharedPreferencesHandler.getNgheNghiep().toString());
        }
        String temp=sharedPreferencesHandler.getTaiLieuXacMinh_mt();
        if (sharedPreferencesHandler.getTaiLieuXacMinh_mt()==null){
            imTaiLieuXacMinh_mt.setBackgroundResource(R.drawable.iv_capnhatthongtin_shape);
        }
        else {
            Picasso.with(getContext()).load(sharedPreferencesHandler.getTaiLieuXacMinh_mt()).into(imTaiLieuXacMinh_mt);
        }
        if (sharedPreferencesHandler.getTaiLieuXacMinh_ms()==null){
            imTaiLieuXacMinh_ms.setBackgroundResource(R.drawable.iv_capnhatthongtin_shape);
        }else {
            Picasso.with(getContext()).load(sharedPreferencesHandler.getTaiLieuXacMinh_ms()).into(imTaiLieuXacMinh_ms);
        }
    }
    //Nhân hình từ gallery


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        dialogPlusHandler.onActivityResult(requestCode,resultCode,data,imTaiLieuXacMinh_mt,imTaiLieuXacMinh_ms);


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        dialogPlusHandler.onRequestPermissionResult(requestCode,permissions,grantResults);
    }
}
