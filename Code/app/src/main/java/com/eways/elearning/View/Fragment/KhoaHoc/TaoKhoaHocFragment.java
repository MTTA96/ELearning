package com.eways.elearning.View.Fragment.KhoaHoc;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.eways.elearning.DataModel.KhoaHoc.DiaDiem;
import com.eways.elearning.DataModel.KhoaHoc.KhoaHoc;
import com.eways.elearning.DataModel.KhoaHoc.LichHoc;
import com.eways.elearning.Handler.Adapter.KhoaHoc.DanhSachBuoiAdapter;
import com.eways.elearning.Handler.Adapter.KhoaHoc.DanhSachThuAdapter;
import com.eways.elearning.Model.Database.SharedPreferencesHandler;
import com.eways.elearning.R;
import com.eways.elearning.Util.SupportKeysList;
import com.eways.elearning.View.Activity.MainActivity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Tuấn Anh 11/16/2017
 */
public class TaoKhoaHocFragment extends Fragment implements CompoundButton.OnCheckedChangeListener, View.OnClickListener {
    View root;
    Switch switchTaoKhoaHoc;
    Button btnTaoKhoaHoc;
    EditText etLinhVuc, etMon, etDiaDiem, etHocPhi, etBangCap, etSoHocVien, etSoBuoi, etThoiLuong, etThongTinThem;
    CheckBox cbGioiTinhNam, cbGioiTinhNu;
    CheckBox cbSang, cbChieu, cbToi;
    CheckBox cbThu2, cbThu3, cbThu4, cbThu5, cbThu6, cbThu7, cbChuNhat;

    private SharedPreferencesHandler sharedPreferencesHandler;

    public TaoKhoaHocFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment TaoKhoaHocFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TaoKhoaHocFragment newInstance() {
        TaoKhoaHocFragment fragment = new TaoKhoaHocFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
        sharedPreferencesHandler = new SharedPreferencesHandler(getActivity(), SupportKeysList.SHARED_PREF_FILE_NAME);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_tao_khoa_hoc, container, false);
        switchTaoKhoaHoc = (Switch) root.findViewById(R.id.switch_tao_khoa_hoc);
        etMon = (EditText) root.findViewById(R.id.editText_TenMon_TaoKhoaHoc);
        etDiaDiem = (EditText) root.findViewById(R.id.editText_DiaDiem_TaoKhoaHoc);
        etHocPhi = (EditText) root.findViewById(R.id.editText_HocPhi_TaoKhoaHoc);
        etBangCap = (EditText) root.findViewById(R.id.editText_BangCap_TaoKhoaHoc);
        etSoHocVien = (EditText) root.findViewById(R.id.editText_SoHocVien_TaoKhoaHoc);
        etSoBuoi = (EditText) root.findViewById(R.id.editText_SoBuoi_TaoKhoaHoc);
        etThoiLuong = (EditText) root.findViewById(R.id.editText_ThoiLuong_TaoKhoaHoc);
        etThongTinThem = (EditText) root.findViewById(R.id.editText_ThongTinKhac_TaoKhoaHoc);
        cbSang = (CheckBox) root.findViewById(R.id.checkBox_Sang);
        cbChieu = (CheckBox) root.findViewById(R.id.checkBox_Chieu);
        cbToi = (CheckBox) root.findViewById(R.id.checkBox_Toi);
        cbThu2 = (CheckBox) root.findViewById(R.id.checkBox_Thu2);
        cbThu3 = (CheckBox) root.findViewById(R.id.checkBox_Thu3);
        cbThu4 = (CheckBox) root.findViewById(R.id.checkBox_Thu4);
        cbThu5 = (CheckBox) root.findViewById(R.id.checkBox_Thu5);
        cbThu6 = (CheckBox) root.findViewById(R.id.checkBox_Thu6);
        cbThu7 = (CheckBox) root.findViewById(R.id.checkBox_Thu7);
        cbChuNhat = (CheckBox) root.findViewById(R.id.checkBox_Chu_Nhat);

        root.findViewById(R.id.button_TiepTuc_TaoKhoaHoc).setOnClickListener(this);
        switchTaoKhoaHoc.setOnCheckedChangeListener(this);
        cbSang.setOnCheckedChangeListener(this);
        cbChieu.setOnCheckedChangeListener(this);
        cbToi.setOnCheckedChangeListener(this);
        cbThu2.setOnCheckedChangeListener(this);
        cbThu3.setOnCheckedChangeListener(this);
        cbThu4.setOnCheckedChangeListener(this);
        cbThu5.setOnCheckedChangeListener(this);
        cbThu6.setOnCheckedChangeListener(this);
        cbThu7.setOnCheckedChangeListener(this);
        cbChuNhat.setOnCheckedChangeListener(this);

        ((MainActivity) getActivity()).tvScreenTitle.setText("Tạo khóa học");
        return root;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (buttonView.getId() == R.id.switch_tao_khoa_hoc){
            if (isChecked) {
                buttonView.setText("Tìm học viên");
                root.findViewById(R.id.layout_BangCap_TaoKhoaHoc).setVisibility(View.GONE);
            }
            else {
                buttonView.setText("Tìm gia sư");
                root.findViewById(R.id.layout_BangCap_TaoKhoaHoc).setVisibility(View.VISIBLE);
            }
        }
        else {
            if (isChecked) {
                buttonView.setBackgroundResource(R.drawable.btn_color_main_corners_shape);
                buttonView.setTextColor(Color.WHITE);
            } else {
                buttonView.setBackgroundResource(R.drawable.btn_white_corners_shape);
                buttonView.setTextColor(Color.BLACK);
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button_TiepTuc_TaoKhoaHoc) {
            if (checkData()) {
                KhoaHoc khoaHoc = setUpData();

            } else
                Toast.makeText(getActivity(), "Thiếu thông tin!", Toast.LENGTH_LONG).show();

        }
    }

    private KhoaHoc setUpData() {
        KhoaHoc khoaHoc = new KhoaHoc();
        ArrayList dataKhoaHoc = new ArrayList<String>();

        //NguoiDang;
        khoaHoc.setNguoiDang(sharedPreferencesHandler.getID());

        //SoBuoiHoc;
        khoaHoc.setSoBuoiHoc(etSoBuoi.getText().toString());

        //SoLuongHocVien;
        khoaHoc.setSoLuongHocVien(etSoHocVien.getText().toString());

        //ArrayList<String> Buoi;
        ArrayList dataBuoi = new ArrayList();
        if (cbSang.isChecked())
            dataBuoi.add("Sang");
        if (cbChieu.isChecked())
            dataBuoi.add("Chieu");
        if (cbToi.isChecked())
            dataBuoi.add("Toi");

        //ArrayList<String> Thu;
         ArrayList dataThu = new ArrayList();
        if (cbThu2.isChecked())
            dataThu.add("T2");
        if (cbThu3.isChecked())
            dataThu.add("T3");
        if (cbThu4.isChecked())
            dataThu.add("T4");
        if (cbThu5.isChecked())
            dataThu.add("T5");
        if (cbThu6.isChecked())
            dataThu.add("T6");
        if (cbThu7.isChecked())
            dataThu.add("T7");
        if (cbChuNhat.isChecked())
            dataThu.add("CN");
        khoaHoc.setLichHoc(new LichHoc());

        //GioiTinh;
        if (cbGioiTinhNam.isChecked() && cbGioiTinhNu.isChecked())
            khoaHoc.setGioiTinh("Nam, Nu");
        if (cbGioiTinhNam.isChecked() && !cbGioiTinhNu.isChecked())
            khoaHoc.setGioiTinh("Nam");
        if (!cbGioiTinhNam.isChecked() && cbGioiTinhNu.isChecked())
            khoaHoc.setGioiTinh("Nu");

        //NgayDang;
        String formatDateTime = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(DateFormat.getDateTimeInstance());
        khoaHoc.setNgayDang(formatDateTime.split(" ")[0]);
        //GioDang;
        khoaHoc.setNgayDang(formatDateTime.split(" ")[1]);

        //ThoiLuongBuoiHoc;
        khoaHoc.setThoiLuongBuoiHoc(etThoiLuong.getText().toString());

        //HocPhi;
        khoaHoc.setHocPhi(etHocPhi.getText().toString());

        //ThongTinKhac;
        khoaHoc.setThongTinKhac(etThongTinThem.getText() != null ? etThongTinThem.getText().toString() : "");

        //ArrayList<String> BangCap;
        if (etBangCap.getText() != null) {
            dataKhoaHoc = new ArrayList();
            dataKhoaHoc.add(etMon.getText().toString());
            khoaHoc.setMon(dataKhoaHoc);
        }

        //ArrayList<String> Mon;
        dataKhoaHoc = new ArrayList();
        dataKhoaHoc.add(etMon.getText().toString());
        khoaHoc.setMon(dataKhoaHoc);

        //ArrayList<String> LinhVuc;
//        dataKhocHoc.add(etLinhVuc.getText().toString());
//        khoaHoc.setLinhVuc(dataKhocHoc);

//                LichHoc LichHoc;
        //DiaDiem DiaDiem;
        khoaHoc.setDiaDiem(new DiaDiem(etDiaDiem.getText().toString(), null, null));
        return khoaHoc;
    }

    private boolean checkData() {
        if (etMon.getText() != null && etDiaDiem.getText() != null && etHocPhi != null
                && etSoBuoi.getText() != null && etSoHocVien.getText() != null && etThoiLuong.getText() != null)
            if (cbGioiTinhNam.isChecked() || cbGioiTinhNu.isChecked()
                    && cbSang.isChecked() || cbChieu.isChecked() || cbToi.isChecked()
                    && cbThu2.isChecked() || cbThu3.isChecked() || cbThu4.isChecked() || cbThu5.isChecked() || cbThu6.isChecked() || cbThu7.isChecked() || cbChuNhat.isChecked())
                return true;
        return false;
    }
}
