package com.eways.elearning.View.Fragment.KhoaHoc;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.eways.elearning.DataModel.KhoaHoc.KhoaHoc;
import com.eways.elearning.DataModel.TaiKhoan.TaiKhoan;
import com.eways.elearning.DataModel.KhoaHoc.ThongTinChiTietKhoaHoc;
import com.eways.elearning.Handler.FragmentHandler;
import com.eways.elearning.Handler.ImageHandler;
import com.eways.elearning.Model.Database.SharedPreferencesHandler;
import com.eways.elearning.Presenter.KhoaHoc.GuiYeuCau.GuiYeuCauPresenter;
import com.eways.elearning.Presenter.KhoaHoc.GuiYeuCau.GuiYeuCauPresenterImp;
import com.eways.elearning.Presenter.KhoaHoc.ThongTinKhoaHoc.ThongTinKhoaHocPresenter;
import com.eways.elearning.Presenter.KhoaHoc.ThongTinKhoaHoc.ThongTinKhoaHocPresenterImp;
import com.eways.elearning.R;
import com.eways.elearning.Util.SupportKeysList;
import com.eways.elearning.View.Dialog.LoadingDialog;
import com.eways.elearning.View.Fragment.KhoaHoc.ThongTinNguoiDang.ThongTinNguoiDangFragment;

import java.util.ArrayList;
import java.util.Map;

/**
 * @author zzzzz
 *         <p>
 *         Note: Tài khoản chưa có hình đại diện
 */
public class ThongTinKhoaHocFragment extends Fragment implements ThongTinKhoaHocViewImp, View.OnClickListener {
    ImageView imgUserAvatar;
    TextView tvTenNguoiDang, tvEmailNguoiDang, tvNamSinh, tvGioiTinh, tvNgheNghiep, tvTrinhDo;
    TextView tvMon, tvDiaDiem, tvThu, tvBuoi, tvSoBuoi, tvSoHocVien, tvThongTinThem, tvHocPhi;
    Button btnGuiYeuCau;

    private SharedPreferencesHandler sharedPreferencesHandler;
    private FragmentHandler fragmentHandler;
    private ImageHandler imageHandler;
    private ThongTinKhoaHocPresenterImp thongTinKhoaHocPresenterImp;
    private GuiYeuCauPresenterImp guiYeuCauPresenterImp;
    private ThongTinChiTietKhoaHoc thongTinChiTietKhoaHoc;

    private static final String param1 = "param1";
    private static final String param2 = "param2";
    private static final String param3 = "param3";

    public ThongTinKhoaHocFragment() {
        // Required empty public constructor
    }

    public static ThongTinKhoaHocFragment newInstance(String idNguoiDang, String idKhoaHoc) {

        Bundle args = new Bundle();
        args.putString(param1, idNguoiDang);
        args.putString(param2, idKhoaHoc);
        ThongTinKhoaHocFragment fragment = new ThongTinKhoaHocFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        thongTinKhoaHocPresenterImp = new ThongTinKhoaHocPresenter(this);
        sharedPreferencesHandler=new SharedPreferencesHandler(getContext(),SupportKeysList.SHARED_PREF_FILE_NAME);
        guiYeuCauPresenterImp=new GuiYeuCauPresenter(this);
        imageHandler = new ImageHandler(getActivity());
        fragmentHandler = new FragmentHandler(getActivity(), getActivity().getSupportFragmentManager());
        if (getArguments() != null) {
            thongTinKhoaHocPresenterImp.YeuCauLayThongTinKhoaHoc(getActivity(), SupportKeysList.GET_DATA_TIMGIASU, getArguments().getString(param1), getArguments().getString(param2));
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_thong_tin_khoa_hoc, container, false);
        imgUserAvatar = root.findViewById(R.id.imageView_UserAvatar_ThongTinKhoaHoc);
        tvTenNguoiDang = root.findViewById(R.id.textView_TenNguoiDang_ThongTinKhoaHoc);
        tvEmailNguoiDang = root.findViewById(R.id.textView_EmailNguoiDang_ThongTinKhoaHoc);
        tvNamSinh = root.findViewById(R.id.textView_NamSinh_ThongTinKhoaHoc);
        tvGioiTinh = root.findViewById(R.id.textView_GioiTinh_ThongTinKhoaHoc);
        tvNgheNghiep = root.findViewById(R.id.textView_NgheNghiep_ThongTinKhoaHoc);
        tvTrinhDo = root.findViewById(R.id.textView_TrinhDo_ThongTin_KhoaHoc);
        tvMon = root.findViewById(R.id.textView_Mon_ThongTinKhoaHoc);
        tvDiaDiem = root.findViewById(R.id.textView_DiaDiem_ThongTinKhoaHoc);
        tvThu = root.findViewById(R.id.textView_Thu_ThongTinKhoaHoc);
        tvBuoi = root.findViewById(R.id.textView_Buoi_ThongTinKhoaHoc);
        tvSoBuoi = root.findViewById(R.id.textView_SoBuoi_ThongTinKhoaHoc);
        tvSoHocVien = root.findViewById(R.id.textView_SoHocVien_ThongTinKhoaHoc);
        tvHocPhi = root.findViewById(R.id.textView_HocPhi_ThongTinKhoaHoc);
        tvThongTinThem = root.findViewById(R.id.textView_ThongTinKhac_ThongTinKhoaHoc);
        btnGuiYeuCau=root.findViewById(R.id.button_YeuCau_ThongTinKhoaHoc);

        btnGuiYeuCau.setOnClickListener(this);
        root.findViewById(R.id.button_XemThemThongTinNguoiDang).setOnClickListener(this);
        root.findViewById(R.id.button_YeuCau_ThongTinKhoaHoc).setOnClickListener(this);

        guiYeuCauPresenterImp.TruyenYeuCau(getArguments().getString(param2),sharedPreferencesHandler.getID(),getActivity(),true);
        return root;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.button_XemThemThongTinNguoiDang){
            fragmentHandler.ChuyenFragment(ThongTinNguoiDangFragment.newInstance(thongTinChiTietKhoaHoc.getTaiKhoan().getId(), thongTinChiTietKhoaHoc.getKhoaHoc().isLoaiKhoaHoc()), true, SupportKeysList.TAG_THONG_TIN_NGUOI_DANG);
        }
    }

    @Override
    public void KetQuaThongTinKhoaHoc(final ThongTinChiTietKhoaHoc thongTinChiTietKhoaHoc) {
        this.thongTinChiTietKhoaHoc = thongTinChiTietKhoaHoc;
        loadView(thongTinChiTietKhoaHoc.getTaiKhoan(), thongTinChiTietKhoaHoc.getKhoaHoc());

    }

    @Override
    public void KetQuaGuiYeuCau(String ketQuaYC,KhoaHoc khoaHoc) {
        if (ketQuaYC.compareTo("GuiYeuCauThanhCong")==0){
            LoadNutGuiYeuCau(khoaHoc);

        }
    }

    @Override
    public void KetQuaHuyYeuCau(String ketQuaHYC, KhoaHoc khoaHoc) {
        if (ketQuaHYC.compareTo("HuyYeuCauThanhCong")==0){
            LoadNutGuiYeuCau(khoaHoc);
        }
    }

    private void loadView(TaiKhoan taiKhoan,KhoaHoc khoaHoc) {
        //User info
        imageHandler.loadImageRound(taiKhoan.getAvatar(), imgUserAvatar);
        tvTenNguoiDang.setText(taiKhoan.getTen());
        tvEmailNguoiDang.setText(taiKhoan.getEmail());
        tvNamSinh.setText(taiKhoan.getNamsinh().compareTo("null") == 0 ? "Chưa cập nhật" : taiKhoan.getNamsinh().toString());
        tvGioiTinh.setText(taiKhoan.getGioitinh().compareTo("null") == 0 ? "Chưa cập nhật" : taiKhoan.getGioitinh().toString());
        tvNgheNghiep.setText(taiKhoan.getNghenghiep().compareTo("null") == 0 ? "Chưa cập nhật" : taiKhoan.getNghenghiep().toString());
        tvTrinhDo.setText(taiKhoan.getTrinhdo().compareTo("null") == 0 ? "Chưa cập nhật" : taiKhoan.getTrinhdo().toString());

        //Course info
        tvMon.setText(khoaHoc.getMon().get(0));
        tvDiaDiem.setText(khoaHoc.getDiaDiem().getDayDu() + ", " + khoaHoc.getDiaDiem().getQuan() + ", " + khoaHoc.getDiaDiem().getTP());
        String thu = "";
        for (int i = 0; i < khoaHoc.getLichHoc().getThoiGian().size(); i++) {
            thu += " " + khoaHoc.getLichHoc().getThoiGian().get(i);
        }
        tvThu.setText(thu);
        String buoi = "";
        for (int i = 0; i < khoaHoc.getLichHoc().getNgayHoc().size(); i++) {
            buoi += " " + khoaHoc.getLichHoc().getNgayHoc().get(i);
        }
        tvBuoi.setText(buoi);
        tvSoBuoi.setText(khoaHoc.getSoBuoiHoc());
        tvSoHocVien.setText(khoaHoc.getSoLuongHocVien());
        tvThongTinThem.setText(khoaHoc.getThongTinKhac() != null ? khoaHoc.getThongTinKhac() : "");
        tvHocPhi.setText(khoaHoc.formatGia(Long.parseLong(khoaHoc.getHocPhi())) + tvHocPhi.getText());

        //Load nút gui yêu cầu
//        LoadNutGuiYeuCau(khoaHoc);
//        //Tắt loading dialog khi hoàn tất load data
        LoadingDialog.dismissDialog();
    }

    public void LoadNutGuiYeuCau(KhoaHoc khoaHoc){
        ArrayList<String> listDanhSachYeuCauDangCho=new ArrayList<>();
        ArrayList<String> listDanhSachYeuCauTamDuyet=new ArrayList<>();
        final ArrayList<String> listDanhSachYeuCauDangChoKey=new ArrayList<>();
        final ArrayList<String> listDanhSachYeuCauTamDuyetKey=new ArrayList<>();
        if (khoaHoc.getDanhSachYeuCau()==null) {
            btnGuiYeuCau.setText("Gửi yêu cầu");
            btnGuiYeuCau.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (btnGuiYeuCau.getText().toString().compareTo("Gửi yêu cầu") == 0) {
                        guiYeuCauPresenterImp.TruyenYeuCau(getArguments().getString(param2), sharedPreferencesHandler.getID(), getActivity(), false);
                    }
                }
            });
        }else {
            if (khoaHoc.getDanhSachYeuCau().getDangCho()!=null && khoaHoc.getDanhSachYeuCau().getTamDuyet()==null){
                for(Map.Entry m:khoaHoc.getDanhSachYeuCau().getDangCho().entrySet()){
                    listDanhSachYeuCauDangCho.add(m.getValue().toString());
                }
                for (Map.Entry q:khoaHoc.getDanhSachYeuCau().getDangCho().entrySet()){
                    listDanhSachYeuCauDangChoKey.add(q.getKey().toString());
                }
                for (int i=0;i<listDanhSachYeuCauDangCho.size();i++){
                    if(listDanhSachYeuCauDangCho.get(i).compareTo(sharedPreferencesHandler.getID())==0){
                        btnGuiYeuCau.setText("Hủy");
                        final int finalI = i;
                        btnGuiYeuCau.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (btnGuiYeuCau.getText().toString().compareTo("Hủy")==0){
                                    guiYeuCauPresenterImp.HuyYeuCau(getArguments().getString(param2),listDanhSachYeuCauDangChoKey.get(finalI),"dangCho",sharedPreferencesHandler.getID(),getActivity());

                                }
                            }
                        });
                        return;
                    }
                }
                btnGuiYeuCau.setText("Gửi yêu cầu");
                btnGuiYeuCau.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (btnGuiYeuCau.getText().toString().compareTo("Gửi yêu cầu") == 0) {
                            guiYeuCauPresenterImp.TruyenYeuCau(getArguments().getString(param2), sharedPreferencesHandler.getID(), getActivity(), false);
                        }
                    }
                });
                return;
            }

            if (khoaHoc.getDanhSachYeuCau().getDangCho()==null && khoaHoc.getDanhSachYeuCau().getTamDuyet()!=null){
                for(Map.Entry n:khoaHoc.getDanhSachYeuCau().getTamDuyet().entrySet()){
                    listDanhSachYeuCauTamDuyet.add(n.getValue().toString());
                }
                for (Map.Entry p:khoaHoc.getDanhSachYeuCau().getTamDuyet().entrySet()){
                    listDanhSachYeuCauTamDuyetKey.add(p.getKey().toString());
                }
                for (int i=0;i<listDanhSachYeuCauDangCho.size();i++){
                    if(listDanhSachYeuCauDangCho.get(i).compareTo(sharedPreferencesHandler.getID())==0){
                        btnGuiYeuCau.setText("Hủy");
                        final int finalI = i;
                        btnGuiYeuCau.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                guiYeuCauPresenterImp.HuyYeuCau(getArguments().getString(param2),listDanhSachYeuCauTamDuyetKey.get(finalI),"tamDuyet",sharedPreferencesHandler.getID(),getActivity());
                            }
                        });
                        return;
                    }
                }
                btnGuiYeuCau.setText("Gửi yêu cầu");
                btnGuiYeuCau.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (btnGuiYeuCau.getText().toString().compareTo("Gửi yêu cầu") == 0) {
                            guiYeuCauPresenterImp.TruyenYeuCau(getArguments().getString(param2), sharedPreferencesHandler.getID(), getActivity(), false);
                        }
                    }
                });
                return;
            }
            if (khoaHoc.getDanhSachYeuCau().getDangCho()!=null && khoaHoc.getDanhSachYeuCau().getDangCho()!=null){
                int count=0;
                int vitri=-1;
                for (Map.Entry q:khoaHoc.getDanhSachYeuCau().getDangCho().entrySet()){
                    listDanhSachYeuCauDangChoKey.add(q.getKey().toString());
                }
                for (Map.Entry p:khoaHoc.getDanhSachYeuCau().getTamDuyet().entrySet()){
                    listDanhSachYeuCauTamDuyetKey.add(p.getKey().toString());
                }
                for(Map.Entry n:khoaHoc.getDanhSachYeuCau().getTamDuyet().entrySet()){
                    listDanhSachYeuCauTamDuyet.add(n.getValue().toString());
                }
                for(Map.Entry m:khoaHoc.getDanhSachYeuCau().getDangCho().entrySet()){
                    listDanhSachYeuCauDangCho.add(m.getValue().toString());
                }

                for (int i=0;i<listDanhSachYeuCauDangCho.size();i++){
                    if (listDanhSachYeuCauDangCho.get(i).compareTo(sharedPreferencesHandler.getID())==0){
                        count++;
                        vitri=i;
                    }
                }
                if (count>0){
                    btnGuiYeuCau.setText("Hủy");
                    if (vitri>-1) {
                        final int vttemp = vitri;
                        btnGuiYeuCau.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (btnGuiYeuCau.getText().toString().compareTo("Hủy") == 0) {

                                    guiYeuCauPresenterImp.HuyYeuCau(getArguments().getString(param2), listDanhSachYeuCauDangChoKey.get(vttemp),"dangCho",sharedPreferencesHandler.getID(),getActivity());

                                }
                            }
                        });
                        return;
                    }
                }else {
                    count =0;
                    vitri=-1;
                    for (int j=0;j<listDanhSachYeuCauTamDuyet.size();j++){
                        if (listDanhSachYeuCauTamDuyet.get(j).compareTo(sharedPreferencesHandler.getID())==0){
                            count++;
                            vitri=j;
                        }
                    }
                    if (count>0){
                        btnGuiYeuCau.setText("Hủy");
                        if (vitri>-1){
                            final int vttemp=vitri;
                            btnGuiYeuCau.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                    guiYeuCauPresenterImp.HuyYeuCau(getArguments().getString(param2), listDanhSachYeuCauTamDuyetKey.get(vttemp),"tamDuyet",sharedPreferencesHandler.getID(),getActivity());
                                }
                            });
                        }
                        return;
                    }else {
                        btnGuiYeuCau.setText("Gửi yêu cầu");
                        btnGuiYeuCau.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (btnGuiYeuCau.getText().toString().compareTo("Gửi yêu cầu") == 0) {
                                    guiYeuCauPresenterImp.TruyenYeuCau(getArguments().getString(param2), sharedPreferencesHandler.getID(), getActivity(), false);
                                }
                            }
                        });
                        return;
                    }
                }
            }
        }
    }

}
