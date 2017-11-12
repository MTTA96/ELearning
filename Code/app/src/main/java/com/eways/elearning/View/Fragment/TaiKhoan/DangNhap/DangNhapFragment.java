package com.eways.elearning.View.Fragment.TaiKhoan.DangNhap;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.eways.elearning.Handler.LoginGmailHandler;
import com.eways.elearning.Model.Database.SharedPreferencesHandler;
import com.eways.elearning.Model.FragmentHandler;
import com.eways.elearning.Presenter.DangKy.DangNhap.DangNhapImpPresenter;
import com.eways.elearning.Presenter.DangKy.DangNhap.DangNhapPresenter;
import com.eways.elearning.R;
import com.eways.elearning.View.Activity.MainActivity;
import com.eways.elearning.View.Fragment.Home.HomeFragment;
import com.eways.elearning.View.Fragment.TaiKhoan.DangKy.DangKyFragment;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.util.concurrent.Executor;

/**
 * A simple {@link Fragment} subclass.
 */
public class DangNhapFragment extends Fragment implements View.OnClickListener,DangNhapImpView{
    Button btnDangky,btnDangNhap;
    EditText etEmailDN,etPasswordDN;
    DangNhapImpPresenter dangNhapImpPresenter;
    TextView tvLoiDangNhap;
    SignInButton btnGmailLogin;

    private LoginGmailHandler loginGmailHandler;
    private FragmentHandler fragmentHandler;

    public DangNhapFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        dangNhapImpPresenter=new DangNhapPresenter(this);
        loginGmailHandler=new LoginGmailHandler(getActivity(),this);
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
        return root;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnSignup)
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_main,new DangKyFragment()).commit();
        if (view.getId() == R.id.btnLogin)
            dangNhapImpPresenter.NhanThongTinDN(etEmailDN.getText().toString(),etPasswordDN.getText().toString(),getActivity());
        if (view.getId() == R.id.btnLoginGmail){
            loginGmailHandler.ConnectGmail();
            loginGmailHandler.signIn();
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        loginGmailHandler.onResult(requestCode,resultCode,data);
    }

    @Override
    public void NhanKetQuaDN(String ketqua) {
        if (ketqua.compareTo("emtyEmail")==0){
            tvLoiDangNhap.setText(R.string.loi_EmailDN);
            etEmailDN.setBackgroundResource(R.drawable.loi_shape);
            etPasswordDN.setBackgroundResource(R.drawable.et_shape);
            return;
        }
        if (ketqua.compareTo("emtyPassword")==0){
            tvLoiDangNhap.setText(R.string.loi_PasswordDN);
            etPasswordDN.setBackgroundResource(R.drawable.loi_shape);
            etEmailDN.setBackgroundResource(R.drawable.et_shape);
            return;
        }
        if (ketqua.compareTo("emtyEmailPassword")==0){
            tvLoiDangNhap.setText(R.string.loi_EmailDN +"-"+R.string.loi_PasswordDN);
            etEmailDN.setBackgroundResource(R.drawable.loi_shape);
            etPasswordDN.setBackgroundResource(R.drawable.loi_shape);
            return;
        }

        if (ketqua.compareTo("SaiEmail")==0){
            tvLoiDangNhap.setText(R.string.loi_SaiDinhDangEmailDN);
            etEmailDN.setBackgroundResource(R.drawable.loi_shape);
            etPasswordDN.setBackgroundResource(R.drawable.et_shape);
            return;
        }
        if (ketqua.compareTo("SaiPassword")==0){
            tvLoiDangNhap.setText(R.string.loi_SaiMatKhauDN);
            etPasswordDN.setBackgroundResource(R.drawable.loi_shape);
            etEmailDN.setBackgroundResource(R.drawable.et_shape);
            return;
        }
        if (ketqua.compareTo("thanhcong")==0){
            //getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_main,new HomeFragment()).commit();
            fragmentHandler.ChuyenFragment(new HomeFragment(), false, null);
        }
    }

}
