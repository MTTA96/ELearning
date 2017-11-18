package com.eways.elearning.View.Fragment.Home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.eways.elearning.Handler.ImageHandler;
import com.eways.elearning.Model.Database.SharedPreferencesHandler;
import com.eways.elearning.Handler.FragmentHandler;
import com.eways.elearning.R;
import com.eways.elearning.Util.SupportKeysList;
import com.eways.elearning.View.Fragment.KhoaHoc.TaoKhoaHocFragment;
import com.eways.elearning.View.Fragment.ListKhoaHoc.ListKhoaHocFragment;
import com.eways.elearning.View.Fragment.TaiKhoan.DangNhap.DangNhapFragment;
import com.eways.elearning.View.Fragment.TaiKhoan.QuanLyTaiKhoanFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements View.OnClickListener {
    TextView tenHome,gioiThieuHome;
    ImageView imgUserAvatar;

    private FragmentHandler fragmentHandler;
    private SharedPreferencesHandler mySharedPref;
    private ImageHandler imageHandler;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentHandler = new FragmentHandler(getActivity(), getActivity().getSupportFragmentManager());
        mySharedPref = new SharedPreferencesHandler(getActivity(), SupportKeysList.SHARED_PREF_FILE_NAME);
        imageHandler = new ImageHandler(getActivity());
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        root.findViewById(R.id.avatar_home).setOnClickListener(this);
        tenHome = (TextView) root.findViewById(R.id.tvTen_Home);
        gioiThieuHome = (TextView) root.findViewById(R.id.tvGioiThieu_Home);
        imgUserAvatar = (ImageView) root.findViewById(R.id.avatar_home);

        root.findViewById(R.id.btn_xem_danh_sach_khoa_hoc).setOnClickListener(this);
        root.findViewById(R.id.btn_tao_khoa_hoc).setOnClickListener(this);

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity)getActivity()).getSupportActionBar().show();
        ((AppCompatActivity)getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.menu);
        if (mySharedPref.getDaDangNhap()){
            if (mySharedPref.getAvatar() != null && mySharedPref.getAvatar().compareTo("") != 0)
                imageHandler.loadImageRound(mySharedPref.getAvatar(), imgUserAvatar);
            gioiThieuHome.setText(mySharedPref.getEmail());
            if (mySharedPref.getTen().length()==0)
                tenHome.setVisibility(View.INVISIBLE);
            else
                tenHome.setText(mySharedPref.getHo() + " " + mySharedPref.getTen());
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.avatar_home:
                if (!mySharedPref.getDaDangNhap())
                    fragmentHandler.ChuyenFragment(new DangNhapFragment(), true, SupportKeysList.TAG_DANG_NHAP_FRAGMENT);
                else
                    fragmentHandler.ChuyenFragment(new QuanLyTaiKhoanFragment(), true, SupportKeysList.TAG_QUAN_LY_TAI_KHOAN_FRAGMENT);
                break;
            case R.id.btn_xem_danh_sach_khoa_hoc:
                fragmentHandler.ChuyenFragment(new ListKhoaHocFragment(), true, SupportKeysList.TAG_DANH_SACH_KHOA_HOC);
                break;
            case R.id.btn_tao_khoa_hoc:
                fragmentHandler.ChuyenFragment(new TaoKhoaHocFragment(), true, SupportKeysList.TAG_TAO_KHOA_HOC);
                break;
        }
    }
}
