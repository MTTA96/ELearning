package com.eways.elearning.View.Fragment.TaiKhoan.DangNhap;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Toast;

import com.eways.elearning.Handler.FragmentHandler;
import com.eways.elearning.Model.TaiKhoan.User;
import com.eways.elearning.Presenter.TaiKhoan.DangNhap.SignInPresenter;
import com.eways.elearning.R;
import com.eways.elearning.Util.SupportKeysList;
import com.eways.elearning.View.Dialog.LoadingDialog;
import com.eways.elearning.View.Fragment.Home.NewHomeFragment;
import com.eways.elearning.View.Fragment.TaiKhoan.SignUpFragment;
import com.google.android.gms.common.SignInButton;

/**
 * A simple {@link Fragment} subclass.
 *
 * Note:
 * 1. Chặn lỗi đăng nhập
 * 2. Chuyển thông báo vào string.xml.
 */
public class DangNhapFragment extends Fragment implements View.OnClickListener,DangNhapViewImp{
    SignInButton btnGmailLogin;
    CheckBox cbDieuKhoan;

    private SignInPresenter signInPresenter;
    private FragmentHandler fragmentHandler;
    public static final String LOGIN_SUCCESS = "login_success";
    public static final String LOGIN_FAILED = "login_failed";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        signInPresenter = new SignInPresenter(getActivity(), this, this);
//        fragmentHandler = new FragmentHandler(getActivity(), getActivity().getSupportFragmentManager());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_dang_nhap, container, false);
        btnGmailLogin= root.findViewById(R.id.btnLoginGmail);

        btnGmailLogin.setOnClickListener(this);
        LoadingDialog.dismissDialog();
        return root;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnLoginGmail){
            signInPresenter.login();
            LoadingDialog.showDialog();
        }

    }

    /** Receive result from google and send it to presenter */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        signInPresenter.onGmailResult(requestCode,resultCode,data);
    }

    @Override
    public void NhanKetQuaDN(String ketqua, User user) {
//        if (ketqua.compareTo(LOGIN_SUCCESS)==0){
//            if (!user.getDateRegisted().isEmpty())
//                fragmentHandler.ChuyenFragment(new NewHomeFragment(), false, SupportKeysList.TAG_HOME_FRAGMENT);
//            else
//                fragmentHandler.ChuyenFragment(SignUpFragment.newInstance(), false, SupportKeysList.TAG_THONG_TIN_DANG_KY);
//        } else {
//            signInPresenter.logoutGmail();
//            Toast.makeText(getActivity(), ketqua, Toast.LENGTH_SHORT).show();
//            LoadingDialog.dismissDialog();
//        }
    }
}
