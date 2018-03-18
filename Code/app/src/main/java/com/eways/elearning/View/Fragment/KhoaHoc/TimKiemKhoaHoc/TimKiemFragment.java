package com.eways.elearning.View.Fragment.KhoaHoc.TimKiemKhoaHoc;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.eways.elearning.Data.DataModel.KhoaHoc.DiaDiem;
import com.eways.elearning.Data.DataModel.KhoaHoc.KhoaHoc;
import com.eways.elearning.Data.DataModel.KhoaHoc.LichHoc;
import com.eways.elearning.Data.DataModel.LinhVuc.LinhVuc;
import com.eways.elearning.Data.DataModel.LinhVuc.Mon;
import com.eways.elearning.Data.DataModel.Other.KhuVuc;
import com.eways.elearning.Handler.FragmentHandler;
import com.eways.elearning.Data.Model.Database.SharedPreferencesHandler;
import com.eways.elearning.Presenter.TimKiemKhoaHoc.TimKiemPresenter;
import com.eways.elearning.Presenter.TimKiemKhoaHoc.TimKiemPresenterImp;
import com.eways.elearning.R;
import com.eways.elearning.Util.SupportKeysList;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * <p>
 * Note:
 * 1. Load data cho spinner trong hàm loadData (line 222)
 */
public class TimKiemFragment extends Fragment implements CompoundButton.OnCheckedChangeListener, View.OnClickListener,TimKiemViewImp {
    View root;
    Switch switchTimKiem;
    Spinner spnLinhVuc, spnQuan, spnThanhPho;
    AutoCompleteTextView etMon,etBangCap;
    EditText  etDiaDiem, etHocPhi;
    CheckBox cbGioiTinhNam, cbGioiTinhNu;
    CheckBox cbSang, cbChieu, cbToi;
    CheckBox cbThu2, cbThu3, cbThu4, cbThu5, cbThu6, cbThu7, cbChuNhat;

    private FragmentHandler fragmentHandler;
    private SharedPreferencesHandler sharedPreferencesHandler;
    private TimKiemPresenterImp timKiemPresenterImp;
    ArrayList<Mon> danhSachMon;
    ArrayList<String> danhSachBangCap;

    public TimKiemFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        fragmentHandler = new FragmentHandler(getActivity(), getActivity().getSupportFragmentManager());
        sharedPreferencesHandler = new SharedPreferencesHandler(getActivity(), SupportKeysList.SHARED_PREF_FILE_NAME);
        timKiemPresenterImp = new TimKiemPresenter(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_tim_kiem, container, false);
        switchTimKiem = (Switch) root.findViewById(R.id.switch_tim_kiem);
        spnLinhVuc = (Spinner) root.findViewById(R.id.spinner_LinhVuc_TimKiem);
        spnQuan = (Spinner) root.findViewById(R.id.spinner_Quan_TimKiem);
        spnThanhPho = (Spinner) root.findViewById(R.id.spinner_ThanhPho_TimKiem);
        etMon = (AutoCompleteTextView) root.findViewById(R.id.editText_Mon_TimKiem);
        etDiaDiem = (EditText) root.findViewById(R.id.editText_DiaDiem_TimKiem);
        etHocPhi = (EditText) root.findViewById(R.id.editText_HocPhi_TimKiem);
        etBangCap = (AutoCompleteTextView) root.findViewById(R.id.editText_BangCap_TimKiem);
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

        danhSachMon=new ArrayList<>();
        danhSachBangCap=new ArrayList<>();

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
        switchTimKiem.setOnCheckedChangeListener(this);
        root.findViewById(R.id.button_Cancel_TimKiem).setOnClickListener(this);
        root.findViewById(R.id.button_Tim_Kiem).setOnClickListener(this);
        timKiemPresenterImp.loaddataLinhvuc(getActivity());
        timKiemPresenterImp.loaddataKhuVuc(getActivity());
        return root;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (buttonView.getId() == R.id.switch_tim_kiem) {
            if (isChecked) {
                buttonView.setText("Tìm học viên");
                root.findViewById(R.id.layout_BangCap_TimKiem).setVisibility(View.GONE);
            } else {
                buttonView.setText("Tìm gia sư");
                root.findViewById(R.id.layout_BangCap_TimKiem).setVisibility(View.VISIBLE);
            }
        } else {
            if (isChecked) {
                buttonView.setBackgroundResource(R.drawable.main_color_with_min_corner_rectangle_shape);
                buttonView.setTextColor(Color.WHITE);
            } else {
                buttonView.setBackgroundResource(R.drawable.white_rectangle_corner_main_color_stroke_shape);
                buttonView.setTextColor(Color.BLACK);
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_Cancel_TimKiem:
                fragmentHandler.XoaFragment();
                break;
            case R.id.button_Tim_Kiem:
                if (checkData()) {
                    if(switchTimKiem.isChecked() == true)
                    {
                        KhoaHoc requestKhoaHoc = new KhoaHoc();
                        setUpDataRequestKhoaHoc(requestKhoaHoc);
                        String bangCap = etBangCap.getText() != null ? etBangCap.getText().toString() : null;
                        fragmentHandler.ChuyenFragment(KetQuaTimKiemFragment.newInstance(switchTimKiem.isChecked(), requestKhoaHoc, bangCap), false, null);
                    }
                    else
                    {
                        KhoaHoc requestKhoaHoc = new KhoaHoc();
                        setUpDataRequestKhoaHoc(requestKhoaHoc);
                        String bangCap = etBangCap.getText() != null ? etBangCap.getText().toString() : null;
                        fragmentHandler.ChuyenFragment(KetQuaTimKiemGiaSuFragment.newInstance(switchTimKiem.isChecked(), requestKhoaHoc, bangCap), false, null);
                    }

                } else
                    Toast.makeText(getActivity(), getString(R.string.thieu_thong_tin), Toast.LENGTH_LONG).show();
                break;
        }
    }

    private void setUpDataRequestKhoaHoc(KhoaHoc requestKhoaHoc) {
        ArrayList data = new ArrayList<>();

        //Lĩnh vực
        if (spnLinhVuc.getSelectedItemPosition() != -1) {
            data.add(spnLinhVuc.getSelectedItem().toString());
            requestKhoaHoc.setLinhVuc(data);
        }

        //Môn
        if (!etMon.getText().toString().isEmpty()) {
            data = new ArrayList();
            data.add(etMon.getText().toString());
            requestKhoaHoc.setMon(data);
        }

        //Địa điểm
        requestKhoaHoc.setDiaDiem(new DiaDiem(etDiaDiem.getText().toString(), spnQuan.getSelectedItem().toString(), spnThanhPho.getSelectedItem().toString()));

        //Học phí
//        String x = etHocPhi.getText().toString();
//        if(!x.isEmpty())
//        {
//            requestKhoaHoc.setHocPhi(x);
//        }
//        else
//        {
//            requestKhoaHoc.setHocPhi("0");
//        }
        try {
            requestKhoaHoc.setHocPhi(etHocPhi.getText().toString().isEmpty() ? "0" : etHocPhi.getText().toString());
        } catch (NumberFormatException ex)//bug
        {
            requestKhoaHoc.setHocPhi("0");
        }


        //Bằng Cấp
        if (!etBangCap.getText().toString().isEmpty()) {
            data = new ArrayList();
            data.add(etBangCap.getText().toString());
            requestKhoaHoc.setBangCap(data);
        }
        //Giới tính
        if (cbGioiTinhNam.isChecked() && cbGioiTinhNu.isChecked())
            requestKhoaHoc.setGioiTinh("Nam, Nữ");
        if (cbGioiTinhNam.isChecked() && !cbGioiTinhNu.isChecked())
            requestKhoaHoc.setGioiTinh("Nam");
        if (!cbGioiTinhNam.isChecked() && cbGioiTinhNu.isChecked())
            requestKhoaHoc.setGioiTinh("Nữ");
        if (!cbGioiTinhNam.isChecked() && !cbGioiTinhNu.isChecked())
            requestKhoaHoc.setGioiTinh("Nam, Nữ");

        //ArrayList<String> Buoi;
        ArrayList dataBuoi = new ArrayList();
        if (cbSang.isChecked())
            dataBuoi.add("Sáng");
        if (cbChieu.isChecked())
            dataBuoi.add("Chiều");
        if (cbToi.isChecked())
            dataBuoi.add("Tối");

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
        requestKhoaHoc.setLichHoc(new LichHoc(dataThu, dataBuoi));
    }

    private boolean checkData() {
        if (etMon.getText().toString().equals("") && etDiaDiem.getText().toString().equals("") && etHocPhi.getText().toString().equals("") && etBangCap.getText().toString().equals("")
                && spnLinhVuc.getSelectedItemPosition() == -1 && spnQuan.getSelectedItemPosition() == -1 && spnThanhPho.getSelectedItemPosition() == -1
                && !cbGioiTinhNam.isChecked() && !cbGioiTinhNu.isChecked()
                && !cbSang.isChecked() && !cbChieu.isChecked() && !cbToi.isChecked()
                && !cbThu2.isChecked() && !cbThu3.isChecked() && !cbThu4.isChecked() && !cbThu5.isChecked() && !cbThu6.isChecked()
                && !cbThu7.isChecked() && !cbChuNhat.isChecked())
            return false;
        return true;
    }

    private void loadData() {
//        spnLinhVuc.setAdapter();
//        spnThanhPho.setAdapter();
//        spnQuan.setAdapter();
    }

    @Override
    public void NhanDanhSachLinhVuc(ArrayList<LinhVuc> danhSachLinhVuc) {
        LoadDataLinhVuc(danhSachLinhVuc);
    }

    @Override
    public void NhanDanhSachKhuVuc(ArrayList<KhuVuc> danhSachKhuVuc) {
        LoadDataKhuVuc(danhSachKhuVuc);
    }

    public void LoadDataKhuVuc(final ArrayList<KhuVuc> danhSachKhuVuc){
        ArrayList<String> danhSachTenKhuVuc=new ArrayList<>();
        for (int i=0;i<danhSachKhuVuc.size();i++){
            danhSachTenKhuVuc.add(danhSachKhuVuc.get(i).getTenThanhPho());
        }
        final ArrayAdapter adDanhSachKhuVuc=new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_dropdown_item,danhSachTenKhuVuc);
        spnThanhPho.setAdapter(adDanhSachKhuVuc);

        spnThanhPho.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ArrayList<String> danhSachQuan=new ArrayList<>();
                danhSachQuan=danhSachKhuVuc.get(position).getDanhSachQuan();
                ArrayAdapter adDanhSachQuan=new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_dropdown_item,danhSachQuan);
                spnQuan.setAdapter(adDanhSachQuan);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    //Load data cho Linh Vực
    public void LoadDataLinhVuc(final ArrayList<LinhVuc> danhSachLinhVucView){
        ArrayList<String> danhSachTenLinhVuc= new ArrayList<>();
        for (int i=0;i<danhSachLinhVucView.size();i++){
            danhSachTenLinhVuc.add(danhSachLinhVucView.get(i).getTenLinhVuc());
        }
        ArrayAdapter adLinhVuc=new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_dropdown_item,danhSachTenLinhVuc);
        spnLinhVuc.setAdapter(adLinhVuc);

        spnLinhVuc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                int spinnerPosition=adLinhVuc.getPosition(spnLinhVuc.getSelectedItem().toString());
                danhSachMon=danhSachLinhVucView.get(position).getDanhMucMon();
                ArrayList<String> danhSachTenMon=new ArrayList<>();
                for (int i=0;i<danhSachMon.size();i++){
                    danhSachTenMon.add(danhSachMon.get(i).getTenMon());
                }
                ArrayAdapter adMon=new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_dropdown_item,danhSachTenMon);
                etMon.setAdapter(adMon);
                etMon.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        danhSachBangCap=new ArrayList<>();
                        danhSachBangCap=danhSachMon.get(position).getDanhMucBangCap();
                        ArrayAdapter adDanhSachBangCap=new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_dropdown_item,danhSachBangCap);
                        etBangCap.setAdapter(adDanhSachBangCap);
                    }
                });
            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
