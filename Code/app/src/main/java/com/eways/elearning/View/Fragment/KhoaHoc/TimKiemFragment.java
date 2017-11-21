package com.eways.elearning.View.Fragment.KhoaHoc;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;

import com.eways.elearning.DataModel.KhoaHoc.DiaDiem;
import com.eways.elearning.DataModel.KhoaHoc.KhoaHoc;
import com.eways.elearning.Handler.FragmentHandler;
import com.eways.elearning.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class TimKiemFragment extends Fragment implements CompoundButton.OnCheckedChangeListener, View.OnClickListener {
    Switch switchTimKiem;
    EditText etLinhVuc, etMon, etDiaDiem, etHocPhi, etBangCap;
    CheckBox cbGioiTinhNam, cbGioiTinhNu;
    CheckBox cbSang, cbChieu, cbToi;
    CheckBox cbThu2, cbThu3, cbThu4, cbThu5, cbThu6, cbThu7, cbChuNhat;

    private FragmentHandler fragmentHandler;

    public TimKiemFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
        fragmentHandler = new FragmentHandler(getActivity(), getActivity().getSupportFragmentManager());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_tim_kiem, container, false);
        switchTimKiem = (Switch) root.findViewById(R.id.switch_tim_kiem);
        etLinhVuc = (EditText) root.findViewById(R.id.editText_LinhVuc_TimKiem);
        etMon = (EditText) root.findViewById(R.id.editText_Mon_TimKiem);
        etDiaDiem = (EditText) root.findViewById(R.id.editText_DiaDiem_TimKiem);
        etHocPhi = (EditText) root.findViewById(R.id.editText_HocPhi_TimKiem);
        etBangCap = (EditText) root.findViewById(R.id.editText_BangCap_TimKiem);
        cbGioiTinhNam = (CheckBox) root.findViewById(R.id.checkBox_Nam_TimKiem);
        cbGioiTinhNu = (CheckBox) root.findViewById(R.id.checkBox_Nu_TimKiem);
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
        root.findViewById(R.id.button_Cancel_TimKiem).setOnClickListener(this);
        root.findViewById(R.id.button_Tim_Kiem).setOnClickListener(this);
        return root;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            buttonView.setBackgroundResource(R.drawable.btn_color_main_corners_shape);
            buttonView.setTextColor(Color.WHITE);
        }
        else {
            buttonView.setBackgroundResource(R.drawable.btn_white_corners_shape);
            buttonView.setTextColor(Color.BLACK);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ((AppCompatActivity)getActivity()).getSupportActionBar().show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_Cancel_TimKiem:
                fragmentHandler.XoaFragment();
                break;
            case R.id.button_Tim_Kiem:
                KhoaHoc requestKhoaHoc = new KhoaHoc();
                setUpDataRequestKhoaHoc(requestKhoaHoc);
                String bangCap = etBangCap.getText()!=null ? etBangCap.getText().toString():null;
                fragmentHandler.ChuyenFragment(KetQuaTimKiemFragment.newInstance(switchTimKiem.isActivated(), requestKhoaHoc, bangCap), false, null);
                break;
        }
    }

    private void setUpDataRequestKhoaHoc(KhoaHoc requestKhoaHoc) {
        ArrayList data = new ArrayList<>();

        //Lĩnh vực
        data.add(etLinhVuc.getText().toString());
        requestKhoaHoc.setLinhVuc(data);

        //Môn
        if (etMon.getText()!=null) {
            data = new ArrayList();
            data.add(etMon.getText().toString());
            requestKhoaHoc.setMon(data);
        }

        //Địa điểm
        if (etDiaDiem.getText()!=null){
            requestKhoaHoc.setDiaDiem(new DiaDiem(etDiaDiem.getText().toString(), null,null));
        }

        //Học phí
        if (etHocPhi.getText()!=null){
            requestKhoaHoc.setHocPhi(etHocPhi.getText().toString());
        }

        //Giới tính
        if(cbGioiTinhNam.isChecked() && cbGioiTinhNu.isChecked())
            requestKhoaHoc.setGioiTinh("Nam, Nu");
        if(cbGioiTinhNam.isChecked() && !cbGioiTinhNu.isChecked())
            requestKhoaHoc.setGioiTinh("Nam");
        if(!cbGioiTinhNam.isChecked() && cbGioiTinhNu.isChecked())
            requestKhoaHoc.setGioiTinh("Nu");

        //Buổi
        data = new ArrayList();
        if (cbSang.isChecked())
            data.add("Sang");
        if (cbChieu.isChecked())
            data.add("Chieu");
        if (cbToi.isChecked())
            data.add("Toi");
        requestKhoaHoc.setBuoi(data);

        //Thứ
        data = new ArrayList();
        if (cbThu2.isChecked())
            data.add("T2");
        if (cbThu3.isChecked())
            data.add("T3");
        if (cbThu4.isChecked())
            data.add("T4");
        if (cbThu5.isChecked())
            data.add("T5");
        if (cbThu6.isChecked())
            data.add("T6");
        if (cbThu7.isChecked())
            data.add("T7");
        if (cbChuNhat.isChecked())
            data.add("CN");
        requestKhoaHoc.setThu(data);
    }
}
