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

import com.eways.elearning.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class TimKiemFragment extends Fragment implements CompoundButton.OnCheckedChangeListener, View.OnClickListener {
    Switch switchTimKiem;
    EditText etLinhVuc, etMon, etDiaDiem, etHocPhi, etBangCap;
    CheckBox cbGioiTinhNam, cbGioiTinhNu;
    CheckBox cbSang, cbChieu, cbToi;
    CheckBox cbThu2, cbThu3, cbThu4, cbThu5, cbThu6, cbThu7, cbChuNhat;

    public TimKiemFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
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
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_Cancel_TimKiem:
                break;
            case R.id.button_Tim_Kiem:
                break;
        }
    }
}
