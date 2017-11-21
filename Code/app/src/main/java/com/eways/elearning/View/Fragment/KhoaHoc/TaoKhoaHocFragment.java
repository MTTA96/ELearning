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

import com.eways.elearning.DataModel.KhoaHoc.KhoaHoc;
import com.eways.elearning.Handler.Adapter.KhoaHoc.DanhSachBuoiAdapter;
import com.eways.elearning.Handler.Adapter.KhoaHoc.DanhSachThuAdapter;
import com.eways.elearning.R;
import com.eways.elearning.View.Activity.MainActivity;

/**
 *  Created by Tuấn Anh 11/16/2017
 */
public class TaoKhoaHocFragment extends Fragment implements CompoundButton.OnCheckedChangeListener, View.OnClickListener {
    Button btnTaoKhoaHoc;
    EditText etMon, etDiaDiem, etHocPhi, etBangCap, etSoHocVien, etSoBuoi, etThoiLuong;
    CheckBox cbGioiTinh;
    CheckBox cbSang, cbChieu, cbToi;
    CheckBox cbThu2, cbThu3, cbThu4, cbThu5, cbThu6, cbThu7, cbChuNhat;

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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_tao_khoa_hoc, container, false);
        etMon = (EditText) root.findViewById(R.id.editText_TenMon_TaoKhoaHoc);
        etDiaDiem = (EditText) root.findViewById(R.id.editText_DiaDiem_TaoKhoaHoc);
        etHocPhi = (EditText) root.findViewById(R.id.editText_HocPhi_TaoKhoaHoc);
        etBangCap = (EditText) root.findViewById(R.id.editText_BangCap_TaoKhoaHoc);
        etSoHocVien = (EditText) root.findViewById(R.id.editText_SoHocVien_TaoKhoaHoc);
        etSoBuoi = (EditText) root.findViewById(R.id.editText_SoBuoi_TaoKhoaHoc);
        etThoiLuong = (EditText) root.findViewById(R.id.editText_ThoiLuong_TaoKhoaHoc);
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

        ((MainActivity)getActivity()).tvScreenTitle.setText("Tạo khóa học");
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
        if (v.getId() == R.id.button_TiepTuc_TaoKhoaHoc){
            KhoaHoc khoaHoc = new KhoaHoc();

        }
    }
}
