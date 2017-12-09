package com.eways.elearning.View.Fragment.KhoaHoc.TaoKhoaHoc;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.eways.elearning.DataModel.KhoaHoc.DiaDiem;
import com.eways.elearning.DataModel.KhoaHoc.KhoaHoc;
import com.eways.elearning.DataModel.KhoaHoc.LichHoc;
import com.eways.elearning.DataModel.KhuVuc;
import com.eways.elearning.DataModel.LinhVuc.LinhVuc;
import com.eways.elearning.DataModel.LinhVuc.Mon;
import com.eways.elearning.Model.Database.SharedPreferencesHandler;
import com.eways.elearning.Presenter.TaoKhoaHoc.TaoKhoaHocPresenter;
import com.eways.elearning.Presenter.TaoKhoaHoc.TaoKhoaHocPresenterImp;
import com.eways.elearning.R;
import com.eways.elearning.Util.SupportKeysList;
import com.eways.elearning.View.Activity.MainActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Tuấn Anh 11/16/2017
 */
public class TaoKhoaHocFragment extends Fragment implements CompoundButton.OnCheckedChangeListener, View.OnClickListener, TaoKhoaHocViewImp {
    View root;
    Switch switchTaoKhoaHoc;
    Button btnTaoKhoaHoc;
    Spinner spnLinhVuc, spnQuan, spnThanhPho;
    EditText etDiaDiem, etHocPhi, etSoHocVien, etSoBuoi, etThoiLuong, etThongTinThem;
    AutoCompleteTextView etMon, etBangCap;
    CheckBox cbGioiTinhNam, cbGioiTinhNu;
    CheckBox cbSang, cbChieu, cbToi;
    CheckBox cbThu2, cbThu3, cbThu4, cbThu5, cbThu6, cbThu7, cbChuNhat;

    private SharedPreferencesHandler sharedPreferencesHandler;
    private TaoKhoaHocPresenterImp taoKhoaHocPresenterImp;


    ArrayList<Mon> danhSachMon;
    ArrayList<String> danhSachBangCap;

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
        taoKhoaHocPresenterImp = new TaoKhoaHocPresenter(this);
        sharedPreferencesHandler = new SharedPreferencesHandler(getActivity(), SupportKeysList.SHARED_PREF_FILE_NAME);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_tao_khoa_hoc, container, false);
        switchTaoKhoaHoc = (Switch) root.findViewById(R.id.switch_tao_khoa_hoc);
        spnLinhVuc = (Spinner) root.findViewById(R.id.spinner_LinhVuc_TaoKhoaHoc);
        spnQuan = (Spinner) root.findViewById(R.id.spinner_Quan_TaoKhoaHoc);
        spnThanhPho = (Spinner) root.findViewById(R.id.spinner_ThanhPho_TaoKhoaHoc);
        etDiaDiem = (EditText) root.findViewById(R.id.editText_DiaDiem_TaoKhoaHoc);
        etMon = (AutoCompleteTextView) root.findViewById(R.id.editText_TenMon_TaoKhoaHoc);
        etHocPhi = (EditText) root.findViewById(R.id.editText_HocPhi_TaoKhoaHoc);
        etBangCap = (AutoCompleteTextView) root.findViewById(R.id.editText_BangCap_TaoKhoaHoc);
        etSoHocVien = (EditText) root.findViewById(R.id.editText_SoHocVien_TaoKhoaHoc);
        etSoBuoi = (EditText) root.findViewById(R.id.editText_SoBuoi_TaoKhoaHoc);
        etThoiLuong = (EditText) root.findViewById(R.id.editText_ThoiLuong_TaoKhoaHoc);
        etThongTinThem = (EditText) root.findViewById(R.id.editText_ThongTinKhac_TaoKhoaHoc);
        cbGioiTinhNam = (CheckBox) root.findViewById(R.id.cbGioiTinhNam);
        cbGioiTinhNu = (CheckBox) root.findViewById(R.id.cbGioiTinhNu);
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
        btnTaoKhoaHoc = (Button) root.findViewById(R.id.btn_tao_khoa_hoc);

        danhSachMon=new ArrayList<>();
        danhSachBangCap=new ArrayList<>();

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
        taoKhoaHocPresenterImp.loaddataLinhvuc(getActivity());
        taoKhoaHocPresenterImp.loaddataKhuVuc(getActivity());
        ((MainActivity) getActivity()).tvScreenTitle.setText("Tạo khóa học");
        getActivity().supportInvalidateOptionsMenu();
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
                buttonView.setBackgroundResource(R.drawable.main_color_with_min_corner_rectangle_shape);
                buttonView.setTextColor(Color.WHITE);
            } else {
                buttonView.setBackgroundResource(R.drawable.white_rectangle_corner_main_color_stroke_shape);
                buttonView.setTextColor(Color.BLACK);
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button_TiepTuc_TaoKhoaHoc) {
            if (checkData()) {
                KhoaHoc khoaHoc = setUpData();
                taoKhoaHocPresenterImp.nhanThongTinKhoaHoc(khoaHoc, switchTaoKhoaHoc.isChecked() ,getActivity());

            } else
                Toast.makeText(getActivity(), "Thiếu thông tin!", Toast.LENGTH_LONG).show();

        }
    }

    private KhoaHoc setUpData() {
        KhoaHoc khoaHoc = new KhoaHoc();
        ArrayList dataKhoaHoc = new ArrayList<>();

        //NguoiDang;
        khoaHoc.setNguoiDang(sharedPreferencesHandler.getID());

        //SoBuoiHoc;
        khoaHoc.setSoBuoiHoc(etSoBuoi.getText().toString());

        //SoLuongHocVien;
        khoaHoc.setSoLuongHocVien(etSoHocVien.getText().toString());

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
        khoaHoc.setLichHoc(new LichHoc(dataThu,dataBuoi));

        //GioiTinh;
        if (cbGioiTinhNam.isChecked() && cbGioiTinhNu.isChecked())
            khoaHoc.setGioiTinh("Nam, Nữ");
        if (cbGioiTinhNam.isChecked() && !cbGioiTinhNu.isChecked())
            khoaHoc.setGioiTinh("Nam");
        if (!cbGioiTinhNam.isChecked() && cbGioiTinhNu.isChecked())
            khoaHoc.setGioiTinh("Nữ");

        //NgayDang;
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Calendar tempCalHienTai = Calendar.getInstance();
        String formatDateTime = dateFormat.format(tempCalHienTai.getTime());
        khoaHoc.setNgayDang(formatDateTime.split(" ")[0]);
        //GioDang;
        khoaHoc.setGioDang(formatDateTime.split(" ")[1]);

        //ThoiLuongBuoiHoc;
        khoaHoc.setThoiLuongBuoiHoc(etThoiLuong.getText().toString());

        //HocPhi;
        khoaHoc.setHocPhi(etHocPhi.getText().toString());

        //ThongTinKhac;
        khoaHoc.setThongTinKhac(etThongTinThem.getText() != null ? etThongTinThem.getText().toString() : "");

        //ArrayList<String> BangCap;
        if (etBangCap.getText() != null) {
            dataKhoaHoc = new ArrayList();
            dataKhoaHoc.add(etBangCap.getText().toString());
            khoaHoc.setBangCap(dataKhoaHoc);
        }

        //ArrayList<String> Mon;
        dataKhoaHoc = new ArrayList();
        dataKhoaHoc.add(etMon.getText().toString());
        khoaHoc.setMon(dataKhoaHoc);

        dataKhoaHoc=new ArrayList();
        dataKhoaHoc.add(spnLinhVuc.getSelectedItem().toString());
        khoaHoc.setLinhVuc(dataKhoaHoc);
//                LichHoc LichHoc;
        //DiaDiem DiaDiem;
        khoaHoc.setDiaDiem(new DiaDiem(etDiaDiem.getText().toString(), spnQuan.getSelectedItem().toString(), spnThanhPho.getSelectedItem().toString()));
        return khoaHoc;
    }

    private boolean checkData() {
        if (etMon.getText() != null && etDiaDiem.getText() != null && etHocPhi.getText() != null
                && etSoBuoi.getText() != null && etSoHocVien.getText() != null && etThoiLuong.getText() != null)
            if (cbGioiTinhNam.isChecked() || cbGioiTinhNu.isChecked())
                if (cbSang.isChecked() || cbChieu.isChecked() || cbToi.isChecked())
                    if (cbThu2.isChecked() || cbThu3.isChecked() || cbThu4.isChecked() || cbThu5.isChecked() || cbThu6.isChecked() || cbThu7.isChecked() || cbChuNhat.isChecked())
                        return true;
        return false;
    }
    @Override
    public void KetQuaTaoKhoaHoc(String result) {

    }

    @Override
    public void NhanDanhSachLinhVuc(ArrayList<LinhVuc> danhSachLinhVuc) {
        LoadDataLinhVuc(danhSachLinhVuc);
    }

    @Override
    public void NhanDanhSachKhuVuc(ArrayList<KhuVuc> danhSachKhuVuc) {
        LoadDataKhuVuc(danhSachKhuVuc);
    }

    //Load data cho khu vực
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
