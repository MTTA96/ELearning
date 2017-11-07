package com.eways.elearning.View.Fragment.TaiKhoan.DangNhap;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

import java.util.concurrent.Executor;

/**
 * A simple {@link Fragment} subclass.
 *
 * Note:
 * 1. Chăn
 */
public class DangNhapFragment extends Fragment implements View.OnClickListener,DangNhapImpView{
    Button btnDangky,btnDangNhap;
    EditText etEmailDN,etPasswordDN;
    DangNhapImpPresenter dangNhapImpPresenter;

    public DangNhapFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        dangNhapImpPresenter=new DangNhapPresenter(this);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_dang_nhap, container, false);
        btnDangky= (Button) root.findViewById(R.id.btnSignup);
        btnDangNhap=(Button) root.findViewById(R.id.btnLogin);
        etEmailDN= (EditText) root.findViewById(R.id.etUsername);
        etPasswordDN= (EditText) root.findViewById(R.id.etPassword);

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
    }

    @Override
    public void NhanKetQuaDN(String ketqua) {
        if (ketqua.compareTo("thanhcong")==0){
            Toast.makeText(getActivity(),"Đăng Nhập Thành Công",Toast.LENGTH_SHORT).show();
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_main,new HomeFragment()).commit();
        }
        else
            Toast.makeText(getActivity(),"Đăng Nhập Thất Bại",Toast.LENGTH_SHORT).show();
    }

}
