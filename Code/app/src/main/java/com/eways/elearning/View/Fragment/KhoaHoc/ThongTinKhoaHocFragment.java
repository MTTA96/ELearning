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
import com.eways.elearning.DataModel.KhoaHoc.KhoaHocChuaHoanTat;
import com.eways.elearning.DataModel.TaiKhoan;
import com.eways.elearning.DataModel.ThongTinChiTietKhoaHoc;
import com.eways.elearning.Handler.ImageHandler;
import com.eways.elearning.Presenter.KhoaHoc.ThongTinKhoaHoc.ThongTinKhoaHocPresenter;
import com.eways.elearning.Presenter.KhoaHoc.ThongTinKhoaHoc.ThongTinKhoaHocPresenterImp;
import com.eways.elearning.R;
import com.eways.elearning.Util.SupportKeysList;
import com.eways.elearning.View.Dialog.LoadingDialog;

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

    private static final String KEY_PARAM1 = "param1";
    private static final String KEY_PARAM2 = "param2";
    private ThongTinKhoaHocPresenterImp thongTinKhoaHocPresenterImp;
    private ImageHandler imageHandler;

    public ThongTinKhoaHocFragment() {
        // Required empty public constructor
    }

    public static ThongTinKhoaHocFragment newInstance(String idNguoiDang, String idKhoaHoc) {

        Bundle args = new Bundle();
        args.putString(KEY_PARAM1, idNguoiDang);
        args.putString(KEY_PARAM2, idKhoaHoc);
        ThongTinKhoaHocFragment fragment = new ThongTinKhoaHocFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        thongTinKhoaHocPresenterImp = new ThongTinKhoaHocPresenter(this);
        imageHandler = new ImageHandler(getActivity());
        if (getArguments() != null) {
            thongTinKhoaHocPresenterImp.YeuCauLayThongTinKhoaHoc(getActivity(), SupportKeysList.GET_DATA_TIMGIASU, getArguments().getString(KEY_PARAM1), getArguments().getString(KEY_PARAM2));
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

        root.findViewById(R.id.button_YeuCau_ThongTinKhoaHoc).setOnClickListener(this);
        return root;
    }

    @Override
    public void KetQuaThongTinKhoaHoc(final ThongTinChiTietKhoaHoc thongTinChiTietKhoaHoc) {
        loadView(thongTinChiTietKhoaHoc.getTaiKhoan(), thongTinChiTietKhoaHoc.getKhoaHoc());

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
        //Load nút gửi yêu cầu
        int count=0;
//        if (khoaHoc.getDanhSachYeuCau().getDangCho()==null){
//            btnGuiYeuCau.setText(R.string.text_GuiYeuCau);
//        }
//        else {
//            if(khoaHoc.getDanhSachYeuCau().getTamDuyet()==null){
//                btnGuiYeuCau.setText(R.string.text_GuiYeuCau);
//            }else {
//                for (int i = 0; i < khoaHoc.getDanhSachYeuCau().getDangCho().size(); i++) {
//                    if (taiKhoan.getId().toString().compareTo(khoaHoc.getDanhSachYeuCau().getDangCho().get(i).toString())==0){
//                        count++;
//                    }
//                }
//                if (count>0){
//                    btnGuiYeuCau.setText(R.string.text_HuyYeuCau);
//                }else {
//                    count=0;
//                    for (int i=0;i<khoaHoc.getDanhSachYeuCau().getTamDuyet().size();i++){
//                        if (taiKhoan.getId().toString().compareTo(khoaHoc.getDanhSachYeuCau().getTamDuyet().get(i).toString())==0)
//                            count++;
//                    }
//                    if (count>0){
//                        btnGuiYeuCau.setText(R.string.text_HuyYeuCau);
//                    }else {
//                        btnGuiYeuCau.setText(R.string.text_GuiYeuCau);
//                    }
//                }
//            }
//        }
        //Tắt loading dialog khi hoàn tất load data
        LoadingDialog.dismissDialog();
    }
    @Override
    public void onClick(View view) {

    }
}
