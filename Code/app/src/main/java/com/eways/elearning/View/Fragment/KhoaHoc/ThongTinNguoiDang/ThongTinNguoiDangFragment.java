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
import android.widget.Toast;

import com.eways.elearning.Data.DataModel.TaiKhoan.TaiKhoan;
import com.eways.elearning.Handler.ImageHandler;
import com.eways.elearning.Presenter.KhoaHoc.ThongTinNguoiDang.ThongTinNguoiDangPresenter;
import com.eways.elearning.Presenter.KhoaHoc.ThongTinNguoiDang.ThongTinNguoiDangPresenterImp;
import com.eways.elearning.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ThongTinNguoiDangFragment extends Fragment implements ThongTinNguoiDangFragmentImp {    TextView tvHoten,tvGioiTinh,tvNamSinh,tvCongViec,tvTrinhDo,tvSDT,tvEmail;
    ImageView ivTaiLieuXacMinh_mt;
    ImageView ivTaiLieuXacMinh_ms;
    ImageView ivAvarta;

    private final String titleTab1 = "Thông tin cá nhân";
    private final String titleTab2 = "Tài liệu chuyên môn";
    private static final String param1 = "param1";
    private static final String param2 = "param2";

    private ImageHandler imageHandler;
    private ThongTinNguoiDangPresenterImp thongTinNguoiDangPresenterImp;
    private final boolean loaiKhoaHoc = false;

    public ThongTinNguoiDangFragment() {
        // Required empty public constructor
    }

    public static ThongTinNguoiDangFragment newInstance(String idNguoiDang, boolean loaiKhoaHoc) {

        Bundle args = new Bundle();
        args.putString(param1,idNguoiDang);
        ThongTinNguoiDangFragment fragment = new ThongTinNguoiDangFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            thongTinNguoiDangPresenterImp = new ThongTinNguoiDangPresenter(this);
            thongTinNguoiDangPresenterImp.GetThongTinNguoiDangPresenter(getArguments().getString(param1), getActivity());
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
        tvTrinhDo=(TextView) root.findViewById(R.id.tvTrinhDo_ThongTinCaNhan);
        tvSDT=(TextView) root.findViewById(R.id.tvSdt_ThongTinCaNhan);
        tvEmail=(TextView) root.findViewById(R.id.tvEmail_ThongTinCaNhan);

        ivTaiLieuXacMinh_mt=(ImageView) root.findViewById(R.id.ivTaiLieuXacMinhMT_ThongTinCaNhan);
        ivTaiLieuXacMinh_ms=(ImageView) root.findViewById(R.id.ivTaiLieuXacMinhMS_ThongTinCaNhan);
        ivAvarta=(ImageView) root.findViewById(R.id.imageView_UserAvatar_ThongTinTaiKhoan);


        showData();
//        seUpViewpager(viewPager);
        return root;
    }

    private void showData() {
        //Set up action bar
        ((AppCompatActivity)getActivity()).getSupportActionBar().show();
        getActivity().supportInvalidateOptionsMenu();

        //Check loại khóa học
        if (!loaiKhoaHoc)
            Toast.makeText(getContext(), "Tìm gia sư", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(getContext(), "Tìm học viên", Toast.LENGTH_SHORT).show();
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
        if(taiKhoan.getTen().compareTo("null")==0)
            tvHoten.setText(R.string.text_ChuaCapNhat_ThongTinNguoiDang);
        else
            tvHoten.setText(taiKhoan.getTen());
        if(taiKhoan.getGioitinh().compareTo("null")==0)
            tvGioiTinh.setText(R.string.text_ChuaCapNhat_ThongTinNguoiDang);
        else
            tvGioiTinh.setText(taiKhoan.getGioitinh());
        if (taiKhoan.getNamsinh().compareTo("null")==0)
            tvNamSinh.setText(R.string.text_ChuaCapNhat_ThongTinNguoiDang);
        else
            tvNamSinh.setText(taiKhoan.getNamsinh());
        if (taiKhoan.getTrinhdo().compareTo("null")==0)
            tvTrinhDo.setText(R.string.text_ChuaCapNhat_ThongTinNguoiDang);
        else
            tvTrinhDo.setText(taiKhoan.getTrinhdo());
        if (taiKhoan.getNghenghiep().compareTo("null")==0)
            tvCongViec.setText(R.string.text_ChuaCapNhat_ThongTinNguoiDang);
        else
            tvCongViec.setText(taiKhoan.getNghenghiep());
        if (taiKhoan.getSodienthoai().compareTo("null")==0)
            tvSDT.setText(R.string.text_ChuaCapNhat_ThongTinNguoiDang);
        else
            tvSDT.setText(taiKhoan.getSodienthoai());
        if (taiKhoan.getEmail().compareTo("null")==0)
            tvEmail.setText(R.string.text_ChuaCapNhat_ThongTinNguoiDang);
        else
            tvEmail.setText(taiKhoan.getEmail());
        imageHandler.loadImageRound(taiKhoan.getAvatar(),ivAvarta);
        if (taiKhoan.getTailieuxacminh_mt().compareTo("null")==0 && taiKhoan.getTailieuxacminh_ms().compareTo("null")==0){
            return;
        }else{
            if (taiKhoan.getTailieuxacminh_mt().compareTo("null")==0 && taiKhoan.getTailieuxacminh_ms().compareTo("null")!=0){
                imageHandler.loadImageRound(taiKhoan.getTailieuxacminh_ms(),ivTaiLieuXacMinh_ms);
            }else {
                if (taiKhoan.getTailieuxacminh_mt().compareTo("null")==0 && taiKhoan.getTailieuxacminh_ms().compareTo("null")!=0){
                    imageHandler.loadImageRound(taiKhoan.getTailieuxacminh_ms(),ivTaiLieuXacMinh_ms);
                    return;
                }
                if (taiKhoan.getTailieuxacminh_ms().compareTo("null")==0 && taiKhoan.getTailieuxacminh_mt().compareTo("null")!=0){
                    imageHandler.loadImageRound(taiKhoan.getTailieuxacminh_mt(),ivTaiLieuXacMinh_mt);
                    return;
                }
                if (taiKhoan.getTailieuxacminh_mt().compareTo("null")!=0 && taiKhoan.getTailieuxacminh_ms().compareTo("null")!=0){
                    imageHandler.loadImageRound(taiKhoan.getTailieuxacminh_mt(),ivTaiLieuXacMinh_mt);
                    imageHandler.loadImageRound(taiKhoan.getTailieuxacminh_ms(),ivTaiLieuXacMinh_ms);
                }

            }
        }

    }
}
