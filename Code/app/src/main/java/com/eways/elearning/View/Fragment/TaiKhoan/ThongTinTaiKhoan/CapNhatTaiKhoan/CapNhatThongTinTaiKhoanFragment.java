package com.eways.elearning.View.Fragment.TaiKhoan.ThongTinTaiKhoan.CapNhatTaiKhoan;


import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.eways.elearning.Data.DataModel.Other.LayHinhModel;
import com.eways.elearning.Data.DataModel.TaiKhoan.TaiKhoan;
import com.eways.elearning.Handler.Adapter.ChonHinhAdapter;
import com.eways.elearning.Handler.DialogPlusHandler;
import com.eways.elearning.Handler.FragmentHandler;
import com.eways.elearning.Handler.ImageHandler;
import com.eways.elearning.Data.Model.Database.SharedPreferencesHandler;
import com.eways.elearning.Presenter.TaiKhoan.CapNhatTaiKhoan.CapNhatTaiKhoanPresenter;
import com.eways.elearning.Presenter.TaiKhoan.CapNhatTaiKhoan.CapNhatTaiKhoanPresenterImp;
import com.eways.elearning.R;
import com.eways.elearning.Util.SupportKeysList;
import com.eways.elearning.View.Dialog.LoadingDialog;
import com.eways.elearning.View.Fragment.TaiKhoan.ThongTinTaiKhoan.TaiLieuChuyenMon.CapNhatTaiLieuChuyenMonFragment;

import java.util.ArrayList;
import java.util.Calendar;

import static android.app.Activity.RESULT_OK;
import static com.eways.elearning.Handler.DialogPlusHandler.REQUEST_CODE_CAMERA;
import static com.eways.elearning.Handler.DialogPlusHandler.REQUEST_CODE_GALLERY;
import static com.eways.elearning.Handler.DialogPlusHandler.vitrichon;


/**
 * A simple {@link Fragment} subclass.
 */
public class CapNhatThongTinTaiKhoanFragment extends Fragment implements CapNhatTaiKhoanViewImp, View.OnClickListener {
    View view;
    Spinner spNamsinh, spGiotinh;
    Button btnLuuCapNhat;
    EditText etHoTen, etNgheNghiep, etTrinhDo, etDiaChi, etSoDienThoai, etEmail;
    ImageView imTaiLieuXacMinh_mt, imTaiLieuXacMinh_ms, imAvarta;

    private Calendar calendar;
    private ArrayList<String> danhsachNam;
    private SharedPreferencesHandler sharedPreferencesHandler;
    private FragmentHandler fragmentHandler;
    private CapNhatTaiKhoanPresenterImp capNhatTaiKhoanPresenterImp;
    private ChonHinhAdapter adDanhSachChonHinh;
    private ArrayList<LayHinhModel> danhSachChon;
    private DialogPlusHandler dialogPlusHandler;
    private ImageHandler imageHandler;

    int RESULT_LOAD_HINHMT = 1;
    int RESULT_LOAD_HINHMS = 2;
    private boolean checkHinhMatTruoc = false;
    private boolean checkHinhMatSau = false;
    private boolean checkAvatar = false;

    public CapNhatThongTinTaiKhoanFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        danhsachNam = new ArrayList<>();
        fragmentHandler = new FragmentHandler(getActivity(), getActivity().getSupportFragmentManager());
        sharedPreferencesHandler = new SharedPreferencesHandler(getContext(), SupportKeysList.SHARED_PREF_FILE_NAME);
        imageHandler = new ImageHandler(getActivity());
        capNhatTaiKhoanPresenterImp = new CapNhatTaiKhoanPresenter(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_cap_nhat_thong_tin_tai_khoan, container, false);

        spNamsinh = view.findViewById(R.id.spNamsinh_CNTTTK);
        spGiotinh = view.findViewById(R.id.spGioiTinh);
        etHoTen = (EditText) view.findViewById(R.id.etHoten_CNTTTK);
        etNgheNghiep = (EditText) view.findViewById(R.id.etNghenghiep_CNTTTK);
        btnLuuCapNhat = (Button) view.findViewById(R.id.btnLuuCNTTTK);
        imTaiLieuXacMinh_mt = (ImageView) view.findViewById(R.id.ivTaiLieuXacMinh_mt);
        imTaiLieuXacMinh_ms = (ImageView) view.findViewById(R.id.ivTaiLieuXacMinh_ms);
        imAvarta = (ImageView) view.findViewById(R.id.imageView_UserAvatar_CNTTTK);
        etTrinhDo = (EditText) view.findViewById(R.id.etTrinhDo_CNTTTK);
        etDiaChi = (EditText) view.findViewById(R.id.etDiaChi_CNTTTK);
        etEmail = view.findViewById(R.id.etEmail_CNTTTK);
        etSoDienThoai = (EditText) view.findViewById(R.id.etSDT_CNTTTK);

        btnLuuCapNhat.setOnClickListener(this);
        imTaiLieuXacMinh_mt.setOnClickListener(this);
        imTaiLieuXacMinh_ms.setOnClickListener(this);
        imAvarta.setOnClickListener(this);

        LoadData();
        //cai đặt dialogplus
        danhSachChon = new ArrayList<>();
        danhSachChon.add(new LayHinhModel(1, R.drawable.camera_icon, "Máy ảnh"));
        danhSachChon.add(new LayHinhModel(2, R.drawable.gallery_icon, "Bộ sưu tập"));
        adDanhSachChonHinh = new ChonHinhAdapter(getActivity(), R.layout.item_chonhinh_cntttk, danhSachChon);
        dialogPlusHandler = new DialogPlusHandler(getActivity(), adDanhSachChonHinh, this);

        ((AppCompatActivity) getActivity()).getSupportActionBar().show();

        return view;
    }

    @Override
    public void KetQuaCapNhat(String ketQuaCapNhat) {
        if (ketQuaCapNhat.compareTo(SupportKeysList.TAG_CAPNHATTHANHCONG) == 0) {
            if (fragmentHandler.getPreviousFragmentTag().compareTo(SupportKeysList.TAG_DIEU_KHOAN_GIA_SU) == 0)
                fragmentHandler.ChuyenFragment(CapNhatTaiLieuChuyenMonFragment.newInstance(CapNhatTaiLieuChuyenMonFragment.TYPE_EDIT), true, SupportKeysList.TAG_CAP_NHAT_TAI_LIEU_CHUYEN_MON);
            else {
//                Toast.makeText(getActivity(), "Cập Nhật Thành Công", Toast.LENGTH_SHORT).show();
                fragmentHandler.XoaFragment();
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnLuuCNTTTK:
                if (checkData()) {
                    LoadingDialog.showDialog();
                    capNhatTaiKhoanPresenterImp.NhanDataUpdate(new TaiKhoan(SetStringNull(sharedPreferencesHandler.getID()),
                            SetStringNull(sharedPreferencesHandler.getEmail()),
                            SetStringNull(sharedPreferencesHandler.getHo()),
                            SetStringNull(etHoTen.getText().toString()),
                            sharedPreferencesHandler.getTenTaiKhoan(),
                            sharedPreferencesHandler.getDaDangNhap(),
                            SetStringNull(sharedPreferencesHandler.getLoaiTaiKhoan()),
                            SetStringNull(sharedPreferencesHandler.getMatKhau()),
                            SetStringNull(etNgheNghiep.getText().toString()),
                            SetStringNull(spNamsinh.getSelectedItem().toString()),
                            SetStringNull(spGiotinh.getSelectedItem().toString()),
                            SetStringNull(sharedPreferencesHandler.getTaiLieuXacMinh_mt()),
                            SetStringNull(sharedPreferencesHandler.getTaiLieuXacMinh_ms()),
                            SetStringNull(etTrinhDo.getText().toString()),
                            SetStringNull(etDiaChi.getText().toString()),
                            SetStringNull(etSoDienThoai.getText().toString()),
                            SetStringNull(sharedPreferencesHandler.getAvatar()),
                            true, SetStringNull(sharedPreferencesHandler.getRating()),sharedPreferencesHandler.getTaiKhoanGiaSu()), getActivity(), imTaiLieuXacMinh_mt, imTaiLieuXacMinh_ms, imAvarta);
                }
                else
                    Toast.makeText(getActivity(), "Chưa điền đủ thông tin!", Toast.LENGTH_LONG).show();
                break;
            case R.id.ivTaiLieuXacMinh_mt:
                dialogPlusHandler.ShowDialogChonHinh(0);
                break;
            case R.id.ivTaiLieuXacMinh_ms:
                dialogPlusHandler.ShowDialogChonHinh(1);
                break;
            case R.id.imageView_UserAvatar_CNTTTK:
                dialogPlusHandler.ShowDialogChonHinh(2);
                break;
        }
    }

    private boolean checkData() {
        if (!etHoTen.getText().toString().isEmpty() && !etDiaChi.getText().toString().isEmpty() && !etNgheNghiep.getText().toString().isEmpty()
                && !etSoDienThoai.getText().toString().isEmpty() && !etTrinhDo.getText().toString().isEmpty())
            if(spNamsinh.getSelectedItemPosition()!=-1)
                if (sharedPreferencesHandler.getTaiLieuXacMinh_mt().toString().trim().compareTo("null") == 0
                        && sharedPreferencesHandler.getTaiLieuXacMinh_ms().toString().trim().compareTo("null") == 0
                        && sharedPreferencesHandler.getAvatar().toString().trim().compareTo("null") == 0) {
                    if (checkHinhMatTruoc && checkHinhMatSau && checkAvatar)
                        return true;
                    else
                        return false;
                } else
                    return true;
        return false;
    }

    //Kiem tra rong va set "null"
    public String SetStringNull(String text) {
        if (text.compareTo("") == 0)
            return "null";
        return text;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    //Load Data tài khoản
    public void LoadData() {
        //Họ tên
        if (sharedPreferencesHandler.getHo().isEmpty() && sharedPreferencesHandler.getTen().isEmpty()) {
            etHoTen.setText("");
        } else
            etHoTen.setText(sharedPreferencesHandler.getHo().toString() + sharedPreferencesHandler.getTen().toString());

        //Năm sinh
        if (sharedPreferencesHandler.getNamSinh().toString().compareTo("null") == 0) {
            calendar = Calendar.getInstance();
            int currentYear = calendar.get(Calendar.YEAR);
            for (int i = 1960; i <= currentYear; i++) {
                danhsachNam.add(String.valueOf(i));
            }
            ArrayAdapter adDanhSachNam = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, danhsachNam);
            spNamsinh.setAdapter(adDanhSachNam);

        } else {
            calendar = Calendar.getInstance();
            int currentYear = calendar.get(Calendar.YEAR);
            for (int i = 1960; i <= currentYear; i++) {
                danhsachNam.add(String.valueOf(i));
            }
            ArrayAdapter adDanhSachNam = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, danhsachNam);
            spNamsinh.setAdapter(adDanhSachNam);
            int spinnerPosition = adDanhSachNam.getPosition(sharedPreferencesHandler.getNamSinh().toString());
            spNamsinh.setSelection(spinnerPosition);
        }

        //Giới tính
        if (sharedPreferencesHandler.getGioiTinh().compareTo("null") == 0) {
            ArrayAdapter adDanhSachGioiTinh = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.ListGioiTinh));
            spGiotinh.setAdapter(adDanhSachGioiTinh);
        } else {
            ArrayAdapter adDanhSachGioiTinh = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.ListGioiTinh));
            spGiotinh.setAdapter(adDanhSachGioiTinh);
            int spinnerPosition = adDanhSachGioiTinh.getPosition(sharedPreferencesHandler.getGioiTinh());
            spGiotinh.setSelection(spinnerPosition);
        }

        //Nghề nghiệp
        if (sharedPreferencesHandler.getNgheNghiep().compareTo("null") == 0) {
            etNgheNghiep.setText("");
        } else {
            etNgheNghiep.setText(sharedPreferencesHandler.getNgheNghiep().toString());
        }

        //Avatar
        imageHandler.loadImageRound(sharedPreferencesHandler.getAvatar(), imAvarta);
        if (sharedPreferencesHandler.getTrinhDo().compareTo("null") == 0)
            etTrinhDo.setText("");
        else
            etTrinhDo.setText(sharedPreferencesHandler.getTrinhDo().toString());

        //Địa chỉ
        if (sharedPreferencesHandler.getDiaChi().compareTo("null") == 0)
            etTrinhDo.setText("");
        else
            etDiaChi.setText(sharedPreferencesHandler.getDiaChi().toString());

        //Số điện thoại
        if (sharedPreferencesHandler.getSoDienThoai().compareTo("null") == 0)
            etSoDienThoai.setText("");
        else
            etSoDienThoai.setText(sharedPreferencesHandler.getSoDienThoai());

        //Email
        etEmail.setText(sharedPreferencesHandler.getEmail());

        //Tài liệu xác minh
        if (sharedPreferencesHandler.getTaiLieuXacMinh_mt().toString().trim().compareTo("null") == 0 && sharedPreferencesHandler.getTaiLieuXacMinh_ms().toString().trim().compareTo("null") == 0) {
            return;
        } else {
            if (sharedPreferencesHandler.getTaiLieuXacMinh_mt().toString().trim().compareTo("null") == 0 && sharedPreferencesHandler.getTaiLieuXacMinh_ms().compareTo("null") != 0) {
                view.findViewById(R.id.textView_HinhXacMinhMatSau_CapNhatThongTinCaNhan).setVisibility(View.GONE);
                imageHandler.loadImageRound(sharedPreferencesHandler.getTaiLieuXacMinh_ms(), imTaiLieuXacMinh_ms);
            } else {
                if (sharedPreferencesHandler.getTaiLieuXacMinh_mt().compareTo("null") == 0 && sharedPreferencesHandler.getTaiLieuXacMinh_ms().compareTo("null") != 0) {
                    view.findViewById(R.id.textView_HinhXacMinhMatSau_CapNhatThongTinCaNhan).setVisibility(View.GONE);
                    imageHandler.loadImageSquare(sharedPreferencesHandler.getTaiLieuXacMinh_ms(), imTaiLieuXacMinh_ms);
                    return;
                }
                if (sharedPreferencesHandler.getTaiLieuXacMinh_ms().compareTo("null") == 0 && sharedPreferencesHandler.getTaiLieuXacMinh_mt().compareTo("null") != 0) {
                    view.findViewById(R.id.textView_HinhXacMinhMatTruoc_CapNhatThongTinCaNhan).setVisibility(View.GONE);
                    imageHandler.loadImageSquare(sharedPreferencesHandler.getTaiLieuXacMinh_mt(), imTaiLieuXacMinh_mt);
                    return;
                }
                if (sharedPreferencesHandler.getTaiLieuXacMinh_mt().compareTo("null") != 0 && sharedPreferencesHandler.getTaiLieuXacMinh_ms().compareTo("null") != 0) {
                    view.findViewById(R.id.textView_HinhXacMinhMatTruoc_CapNhatThongTinCaNhan).setVisibility(View.GONE);
                    view.findViewById(R.id.textView_HinhXacMinhMatSau_CapNhatThongTinCaNhan).setVisibility(View.GONE);
                    imageHandler.loadImageSquare(sharedPreferencesHandler.getTaiLieuXacMinh_mt(), imTaiLieuXacMinh_mt);
                    imageHandler.loadImageSquare(sharedPreferencesHandler.getTaiLieuXacMinh_ms(), imTaiLieuXacMinh_ms);
                }
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        dialogPlusHandler.onActivityResult(requestCode, resultCode, data, imTaiLieuXacMinh_mt, imTaiLieuXacMinh_ms, imAvarta);
        if (requestCode == REQUEST_CODE_CAMERA && resultCode == RESULT_OK && data != null) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            if (vitrichon == 0) {
                view.findViewById(R.id.textView_HinhXacMinhMatTruoc_CapNhatThongTinCaNhan).setVisibility(View.GONE);
                imTaiLieuXacMinh_mt.setImageBitmap(bitmap);
                checkHinhMatTruoc = true;
            }
            if (vitrichon == 1){
                view.findViewById(R.id.textView_HinhXacMinhMatSau_CapNhatThongTinCaNhan).setVisibility(View.GONE);
                imTaiLieuXacMinh_ms.setImageBitmap(bitmap);
                checkHinhMatSau = true;
            }
            if (vitrichon == 2){
                imAvarta.setImageBitmap(bitmap);
                checkAvatar = true;
            }
            dialogPlusHandler.dissMissDialog();
        }
        if (requestCode == REQUEST_CODE_GALLERY && resultCode == RESULT_OK && data != null) {
            if (vitrichon == 0) {
                view.findViewById(R.id.textView_HinhXacMinhMatTruoc_CapNhatThongTinCaNhan).setVisibility(View.GONE);
                imageHandler.loadImageSquare(String.valueOf(data.getData()), imTaiLieuXacMinh_mt);
                checkHinhMatTruoc = true;
            }
            if (vitrichon == 1) {
                view.findViewById(R.id.textView_HinhXacMinhMatSau_CapNhatThongTinCaNhan).setVisibility(View.GONE);
                imageHandler.loadImageSquare(String.valueOf(data.getData()), imTaiLieuXacMinh_ms);
                checkHinhMatSau = true;
            }
            if (vitrichon == 2) {
                imageHandler.loadImageRound(String.valueOf(data.getData()), imAvarta);
                checkAvatar = true;
            }
            dialogPlusHandler.dissMissDialog();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        dialogPlusHandler.onRequestPermissionResult(requestCode, permissions, grantResults);
    }
}

