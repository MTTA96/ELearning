package com.eways.elearning.View.Fragment.TaiKhoan.DangNhap;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.eways.elearning.Handler.LoginGmailHandler;
import com.eways.elearning.Handler.FragmentHandler;
import com.eways.elearning.Presenter.TaiKhoan.DangNhap.DangNhapPresenter;
import com.eways.elearning.Presenter.TaiKhoan.DangNhap.DangNhapPresenterImp;
import com.eways.elearning.R;
import com.eways.elearning.Util.SupportKeysList;
import com.eways.elearning.View.Dialog.LoadingDialog;
import com.eways.elearning.View.Fragment.Home.NewHomeFragment;
import com.eways.elearning.View.Fragment.TaiKhoan.DangKy.DangKyFragment;
import com.google.android.gms.common.SignInButton;

/**
 * A simple {@link Fragment} subclass.
 *
 * Note:
 * 1. Chặn lỗi đăng nhập
 * 2. Chuyển thông báo vào string.xml.
 */
public class DangNhapFragment extends Fragment implements View.OnClickListener,DangNhapViewImp{
    Button btnDangky,btnDangNhap;
    EditText etEmailDN,etPasswordDN;
    DangNhapPresenterImp dangNhapImpPresenter;
    TextView tvLoiDangNhap;
    SignInButton btnGmailLogin;
    CheckBox cbDieuKhoan;

    private LoginGmailHandler loginGmailHandler;
    private FragmentHandler fragmentHandler;
    public static final String ERROR_MSG_THIEU_EMAIL_PW = "thieu_email_pw";
    public static final String ERROR_MSG_THIEU_EMAIL = "thieu_email";
    public static final String ERROR_MSG_SAI_EMAIL = "sai_email";
    public static final String ERROR_MSG_THIEU_PW = "thieu_pw";
    public static final String ERROR_MSG_SAI_PW = "sai_pw";
    public static final String LOGIN_SUCCESS = "login_success";
    public static final String LOGIN_FAILED = "login_failed";

    public DangNhapFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dangNhapImpPresenter=new DangNhapPresenter(this);


        loginGmailHandler=new LoginGmailHandler(getActivity(),this,dangNhapImpPresenter);
        fragmentHandler = new FragmentHandler(getActivity(), getActivity().getSupportFragmentManager());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_dang_nhap, container, false);
        btnDangky= (Button) root.findViewById(R.id.btnSignup);
        btnDangNhap=(Button) root.findViewById(R.id.btnLogin);
        etEmailDN= (EditText) root.findViewById(R.id.etUsername);
        etPasswordDN= (EditText) root.findViewById(R.id.etPassword);
        tvLoiDangNhap= (TextView) root.findViewById(R.id.tvLoiDN);
        btnGmailLogin=(SignInButton)root.findViewById(R.id.btnLoginGmail);

        btnGmailLogin.setOnClickListener(this);
        btnDangky.setOnClickListener(this);
        btnDangNhap.setOnClickListener(this);

//        ((AppCompatActivity)getActivity()).getSupportActionBar().setElevation(0);

        AnHienMatKhau(etPasswordDN);
        LoadingDialog.dismissDialog();
        return root;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnSignup)
            fragmentHandler.ChuyenFragment(new DangKyFragment(), true, SupportKeysList.TAG_DANG_KY_FRAGMENT);
        if (view.getId() == R.id.btnLogin){
            LoadingDialog.showDialog();
            dangNhapImpPresenter.NhanThongTinDN(etEmailDN.getText().toString().trim(),etPasswordDN.getText().toString().trim(),getActivity());
        }
        if (view.getId() == R.id.btnLoginGmail){
            LoadingDialog.showDialog();
            loginGmailHandler.ConnectGmail();
            loginGmailHandler.signIn();
        }

    }
    //Xử lý bật tắt mật khẩu
    public void AnHienMatKhau(final EditText etMatKhau){
        etMatKhau.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if (etMatKhau.getText().toString().isEmpty()){
                    return false;
                }
                else {
                    if(event.getAction() == MotionEvent.ACTION_UP) {
                        boolean status=false;
                        if(event.getRawX() >= (etMatKhau.getRight() - etMatKhau.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                            // your action here
                            etMatKhau.setInputType(InputType.TYPE_CLASS_TEXT);
                            return true;
                        }
                    }
                }
                return false;
            }
        });
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        loginGmailHandler.onResult(requestCode,resultCode,data);
    }

    @Override
    public void NhanKetQuaDN(String ketqua) {
        if (ketqua.compareTo(ERROR_MSG_THIEU_EMAIL)==0){
            LoadingDialog.dismissDialog();
            tvLoiDangNhap.setText(R.string.loi_EmailDN);
            etEmailDN.setBackgroundResource(R.drawable.loi_shape);
            etPasswordDN.setBackgroundResource(R.drawable.et_shape);
            return;
        }
        if (ketqua.compareTo(ERROR_MSG_THIEU_PW)==0){
            LoadingDialog.dismissDialog();
            tvLoiDangNhap.setText(R.string.loi_PasswordDN);
            etPasswordDN.setBackgroundResource(R.drawable.loi_shape);
            etEmailDN.setBackgroundResource(R.drawable.et_shape);
            return;
        }
        if (ketqua.compareTo(ERROR_MSG_THIEU_EMAIL_PW)==0){
            LoadingDialog.dismissDialog();
            tvLoiDangNhap.setText(R.string.loi_EmailPasswordDN);
            etEmailDN.setBackgroundResource(R.drawable.loi_shape);
            etPasswordDN.setBackgroundResource(R.drawable.loi_shape);
            return;
        }

        if (ketqua.compareTo(ERROR_MSG_SAI_EMAIL)==0){
            LoadingDialog.dismissDialog();
            tvLoiDangNhap.setText(R.string.loi_SaiDinhDangEmailDN);
            etEmailDN.setBackgroundResource(R.drawable.loi_shape);
            etPasswordDN.setBackgroundResource(R.drawable.et_shape);
            return;
        }
        if (ketqua.compareTo(ERROR_MSG_SAI_PW)==0){
            LoadingDialog.dismissDialog();
            tvLoiDangNhap.setText(R.string.loi_SaiMatKhauDN);
            etPasswordDN.setBackgroundResource(R.drawable.loi_shape);
            etEmailDN.setBackgroundResource(R.drawable.et_shape);
            return;
        }
        if (ketqua.compareTo(LOGIN_SUCCESS)==0){
//            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_main,new HomeFragment()).commit();
            fragmentHandler.ChuyenFragment(new NewHomeFragment(), false, SupportKeysList.TAG_HOME_FRAGMENT);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        loginGmailHandler.onStop(getContext());
    }
}
