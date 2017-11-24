package com.eways.elearning.View.Fragment.TaiKhoan.ThongTinTaiKhoan.CapNhatTaiKhoan;


import android.content.res.Resources;
import android.os.Bundle;
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
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.eways.elearning.DataModel.TaiKhoan;
import com.eways.elearning.Handler.FragmentHandler;
import com.eways.elearning.Model.Database.SharedPreferencesHandler;
import com.eways.elearning.Presenter.TaiKhoan.CapNhatTaiKhoan.CapNhatTaiKhoanPresenter;
import com.eways.elearning.Presenter.TaiKhoan.CapNhatTaiKhoan.CapNhatTaiKhoanPresenterImp;
import com.eways.elearning.R;
import com.eways.elearning.Util.SupportKeysList;
import com.eways.elearning.View.Fragment.TaiKhoan.QuanLyTaiKhoanFragment;

import java.util.ArrayList;
import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 */
public class CapNhatThongTinTaiKhoanFragment extends Fragment implements CapNhatTaiKhoanViewImp, View.OnClickListener {
    Spinner spNamsinh, spGiotinh;
    Button btnLuuCapNhat;
    EditText etHoTen, etNgheNghiep;
    ImageView imTaiLieuXacMinh_mt,imTaiLieuXacMinh_ms;
    Calendar calendar;
    ArrayList<String> danhsachNam;
    SharedPreferencesHandler sharedPreferencesHandler;
    FragmentHandler fragmentHandler;
    CapNhatTaiKhoanPresenterImp capNhatTaiKhoanPresenterImp;

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

        LoadData();
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        return view;
    }

    @Override
    public void KetQuaCapNhat(String ketQuaCapNhat) {
        if (ketQuaCapNhat.compareTo(SupportKeysList.TAG_CAPNHATTHANHCONG) == 0) {
            Toast.makeText(getActivity(), "Cập nhật thành công ", Toast.LENGTH_SHORT).show();

            //Xóa sau demo
            sharedPreferencesHandler.setTen(etHoTen.getText().toString());

            fragmentHandler.ChuyenFragment(new QuanLyTaiKhoanFragment(), false, null);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnLuuCNTTTK) {
            capNhatTaiKhoanPresenterImp.NhanDataUpdate(new TaiKhoan(sharedPreferencesHandler.getID(), sharedPreferencesHandler.getEmail(),
                    null, etHoTen.getText().toString(), sharedPreferencesHandler.getTenTaiKhoan(),
                    sharedPreferencesHandler.getDaDangNhap(), sharedPreferencesHandler.getLoaiTaiKhoan(),
                    sharedPreferencesHandler.getMatKhau(), etNgheNghiep.getText().toString(), spNamsinh.getSelectedItem().toString(),
                    spGiotinh.getSelectedItem().toString()), getActivity());
//            fragmentHandler.ChuyenFragment(new CapNhatThongTinTaiKhoanFragment(),false,null);
        }
    }

    //Load Data tài khoản
    public void LoadData() {
        etHoTen.setText(sharedPreferencesHandler.getTen().toString());
        calendar = Calendar.getInstance();
        int currentYear = calendar.get(Calendar.YEAR);
        for (int i = 1960; i <= currentYear; i++) {
            danhsachNam.add(String.valueOf(i));
        }
        ArrayAdapter<String> namSinhAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, danhsachNam);
        spNamsinh.setAdapter(namSinhAdapter);
        ArrayAdapter<String> gioiTinhAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.ListGioiTinh));
        spGiotinh.setAdapter(gioiTinhAdapter);
        imTaiLieuXacMinh_mt.setBackgroundResource(R.drawable.iv_capnhatthongtin_shape);
        imTaiLieuXacMinh_mt.setBackgroundResource(R.drawable.iv_capnhatthongtin_shape);
    }

    //Load thông tin tài khoản
    public void LoadAccount(){

    }
}
