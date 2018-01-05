package com.eways.elearning.View.Fragment.KhoaHoc.ThongTinNguoiDang;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.eways.elearning.DataModel.TaiKhoan.TaiKhoan;
import com.eways.elearning.Handler.ImageHandler;
import com.eways.elearning.Presenter.KhoaHoc.ThongTinNguoiDang.ThongTinNguoiDangPresenter;
import com.eways.elearning.Presenter.KhoaHoc.ThongTinNguoiDang.ThongTinNguoiDangPresenterImp;
import com.eways.elearning.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ThongTinNguoiDangFragment extends Fragment implements ThongTinNguoiDangFragmentImp {
    private final String titleTab1 = "Thông tin cá nhân";
    private final String titleTab2 = "Tài liệu chuyên môn";

    private static final String KEY_PARAM2="param2";
    ImageHandler imageHandler;

    TextView tvHoten,tvGioiTinh,tvNamSinh,tvCongViec,tvTrinhDo,tvSDT,tvEmail;
    ImageView ivTaiLieuXacMinh_mt;
    ImageView ivTaiLieuXacMinh_ms;

    ThongTinNguoiDangPresenterImp thongTinNguoiDangPresenterImp;

    public ThongTinNguoiDangFragment() {
        // Required empty public constructor
    }

    public static ThongTinNguoiDangFragment newInstance(String idNguoiDang) {

        Bundle args = new Bundle();
        args.putString(KEY_PARAM2,idNguoiDang);
        ThongTinNguoiDangFragment fragment = new ThongTinNguoiDangFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            thongTinNguoiDangPresenterImp = new ThongTinNguoiDangPresenter(this);
            thongTinNguoiDangPresenterImp.GetThongTinNguoiDangPresenter(getArguments().getString(KEY_PARAM2), getActivity());
            imageHandler = new ImageHandler(getContext());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_thong_tin_tai_khoan, container, false);
//        ViewPager viewPager = (ViewPager) root.findViewById(R.id.viewPager_ThongTinTaiKhoan);

        tvHoten=(TextView) root.findViewById(R.id.tvHoten_ThongTinCaNhan);
        tvGioiTinh=(TextView) root.findViewById(R.id.tvGioiTinh_ThongTinCaNhan);
        tvNamSinh=(TextView) root.findViewById(R.id.tvNamsinh_ThongTinCaNhan);
        tvCongViec=(TextView) root.findViewById(R.id.tvNgheNghiep_ThongTinCaNhan);
        tvTrinhDo=(TextView) root.findViewById(R.id.tvNgheNghiep_ThongTinCaNhan);
        tvSDT=(TextView) root.findViewById(R.id.tvSdt_ThongTinCaNhan);
        tvEmail=(TextView) root.findViewById(R.id.tvEmail_ThongTinCaNhan);

        ivTaiLieuXacMinh_mt=(ImageView) root.findViewById(R.id.ivTaiLieuXacMinhMT_ThongTinCaNhan);
        ivTaiLieuXacMinh_ms=(ImageView) root.findViewById(R.id.ivTaiLieuXacMinhMS_ThongTinCaNhan);


        ((AppCompatActivity)getActivity()).getSupportActionBar().show();
        getActivity().supportInvalidateOptionsMenu();
//        seUpViewpager(viewPager);
        return root;
    }

    private void seUpViewpager(ViewPager viewPager) {
//        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager());
//        viewPagerAdapter.addFragment(new CapNhatThongTinTaiKhoanFragment(), titleTab1);
//        viewPagerAdapter.addFragment(new CapNhatTaiLieuChuyenMonFragment(), titleTab2);
//        viewPager.setAdapter(viewPagerAdapter);


    }

    @Override
    public void NhanThongTinNguoiDang(TaiKhoan taiKhoan) {
        LoadThongTinNguoiDang(taiKhoan);
    }

    //Load thong tin nguoi đăng
    public void LoadThongTinNguoiDang(TaiKhoan taiKhoan) {
        if(taiKhoan.getTen()==null)
            tvHoten.setText("");
        else
            tvHoten.setText(taiKhoan.getTen());
        if(taiKhoan.getGioitinh()==null)
            tvGioiTinh.setText("");
        else
            tvGioiTinh.setText(taiKhoan.getGioitinh());
        if (taiKhoan.getNamsinh()==null)
            tvNamSinh.setText("");
        else
            tvNamSinh.setText(taiKhoan.getNamsinh());
        if (taiKhoan.getTrinhdo()==null)
            tvTrinhDo.setText("");
        else
            tvTrinhDo.setText(taiKhoan.getTrinhdo());
        if (taiKhoan.getNghenghiep()==null)
            tvCongViec.setText("");
        else
            tvCongViec.setText(taiKhoan.getNghenghiep());
        if (taiKhoan.getSodienthoai()==null)
            tvSDT.setText("");
        else
            tvSDT.setText(taiKhoan.getSodienthoai());
        if (taiKhoan.getEmail()==null)
            tvEmail.setText("");
        else
            tvEmail.setText(taiKhoan.getEmail());
        if (taiKhoan.getTailieuxacminh_mt().isEmpty() && taiKhoan.getTailieuxacminh_ms().isEmpty()){
            return;
        }else{
            if (taiKhoan.getTailieuxacminh_mt().isEmpty() && !taiKhoan.getTailieuxacminh_ms().isEmpty()){
                imageHandler.loadImageRound(taiKhoan.getTailieuxacminh_ms(),ivTaiLieuXacMinh_ms);
            }else {
                if (taiKhoan.getTailieuxacminh_mt().isEmpty() && !taiKhoan.getTailieuxacminh_ms().isEmpty()){
                    imageHandler.loadImageRound(taiKhoan.getTailieuxacminh_ms(),ivTaiLieuXacMinh_ms);
                    return;
                }
                if (taiKhoan.getTailieuxacminh_ms().isEmpty() && !taiKhoan.getTailieuxacminh_mt().isEmpty()){
                    imageHandler.loadImageRound(taiKhoan.getTailieuxacminh_mt(),ivTaiLieuXacMinh_mt);
                    return;
                }
                if (!taiKhoan.getTailieuxacminh_mt().isEmpty() && !taiKhoan.getTailieuxacminh_ms().isEmpty()){
                    imageHandler.loadImageRound(taiKhoan.getTailieuxacminh_mt(),ivTaiLieuXacMinh_mt);
                    imageHandler.loadImageRound(taiKhoan.getTailieuxacminh_ms(),ivTaiLieuXacMinh_ms);
                }

            }
        }

    }
}
