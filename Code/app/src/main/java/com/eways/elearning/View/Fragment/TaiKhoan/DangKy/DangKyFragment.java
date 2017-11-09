package com.eways.elearning.View.Fragment.TaiKhoan.DangKy;


import android.app.Activity;
import android.app.FragmentManager;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.eways.elearning.Model.FragmentHandler;
import com.eways.elearning.Presenter.DangKy.DangKyImpPresenter;
import com.eways.elearning.Presenter.DangKy.DangKyPresenter;
import com.eways.elearning.R;
import com.eways.elearning.View.Activity.MainActivity;
import com.eways.elearning.View.Fragment.Home.HomeFragment;
import com.eways.elearning.View.Fragment.TaiKhoan.DangNhap.DangNhapFragment;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * A simple {@link Fragment} subclass.
 */
public class DangKyFragment extends Fragment implements View.OnClickListener,DangKyImpView {
    Button btnHuy,btnDangKy_DK;
    EditText etEmailDK,etPasswordDK,etCPassword;
    TextView tvLoiEmail,tvLoiPassword,tvLoiCPassword;
    private DangKyImpPresenter dangKyImpPresenter;
    private FragmentHandler fragmentHandler;

    public DangKyFragment( ) {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        dangKyImpPresenter=new DangKyPresenter(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root=inflater.inflate(R.layout.fragment_dang_ky, container, false);
        btnHuy=(Button) root.findViewById(R.id.btnHuy_DK);
        btnDangKy_DK=(Button) root.findViewById(R.id.btnDK_DK);
        etEmailDK=(EditText)root.findViewById(R.id.etEmail);
        etPasswordDK=(EditText) root.findViewById(R.id.etPasswordDK) ;
        etCPassword=(EditText) root.findViewById(R.id.etConfirmPassDK);
        tvLoiEmail= (TextView) root.findViewById(R.id.tvLoiEmailDK);
        tvLoiPassword= (TextView) root.findViewById(R.id.tvLoiPasswordDK);
        tvLoiCPassword= (TextView) root.findViewById(R.id.tvLoiCPasswordDK);
        btnHuy.setOnClickListener(this);
        btnDangKy_DK.setOnClickListener(this);
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        fragmentHandler=new FragmentHandler(getActivity(), getChildFragmentManager());
    }

    @Override
    public void onClick(View view) {
        if (view.getId()==R.id.btnHuy_DK)
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_main,new DangNhapFragment()).commit();
        if (view.getId()==R.id.btnDK_DK){
            dangKyImpPresenter.NhanThongTinDangKy(etEmailDK.getText().toString(),etPasswordDK.getText().toString(),etCPassword.getText().toString(),getActivity());
        }
    }
    @Override
    public void NhanKetQuaTuPresenter(String result) {
        if(result.compareTo("emtyEmail")==0){
            tvLoiEmail.setText(R.string.emtyEmailDK);
            tvLoiPassword.setText("");
            tvLoiCPassword.setText("");
            etEmailDK.setBackgroundResource(R.drawable.loi_shape);
            etPasswordDK.setBackgroundResource(R.drawable.et_shape);
            etCPassword.setBackgroundResource(R.drawable.et_shape);
            return;
        }
        if (result.compareTo("emtyPassword")==0){
            tvLoiPassword.setText(R.string.emtyPasswordDK);
            tvLoiEmail.setText("");
            tvLoiCPassword.setText("");
            etPasswordDK.setBackgroundResource(R.drawable.loi_shape);
            etEmailDK.setBackgroundResource(R.drawable.et_shape);
            etCPassword.setBackgroundResource(R.drawable.et_shape);
            return;
        }
        if (result.compareTo("emtyCPassword")==0){
            tvLoiCPassword.setText(R.string.emtyCPasswordDK);
            tvLoiEmail.setText("");
            tvLoiPassword.setText("");
            etCPassword.setBackgroundResource(R.drawable.loi_shape);
            etEmailDK.setBackgroundResource(R.drawable.et_shape);
            etPasswordDK.setBackgroundResource(R.drawable.et_shape);
            return;
        }
        if (result.compareTo("emtyEmailPassword")==0){
            tvLoiPassword.setText(R.string.emtyPasswordDK);
            tvLoiEmail.setText(R.string.emtyEmailDK);
            tvLoiCPassword.setText("");
            etEmailDK.setBackgroundResource(R.drawable.loi_shape);
            etPasswordDK.setBackgroundResource(R.drawable.loi_shape);
            etCPassword.setBackgroundResource(R.drawable.et_shape);
            return;
        }
        if (result.compareTo("emtyEmailCPassword")==0){
            tvLoiEmail.setText(R.string.emtyEmailDK);
            tvLoiCPassword.setText(R.string.emtyCPasswordDK);
            tvLoiPassword.setText("");
            etEmailDK.setBackgroundResource(R.drawable.loi_shape);
            etCPassword.setBackgroundResource(R.drawable.loi_shape);
            etPasswordDK.setBackgroundResource(R.drawable.et_shape);
            return;
        }
        if (result.compareTo("emtyPasswordCPassword")==0){
            tvLoiPassword.setText(R.string.emtyPasswordDK);
            tvLoiCPassword.setText(R.string.emtyCPasswordDK);
            tvLoiEmail.setText("");
            etPasswordDK.setBackgroundResource(R.drawable.loi_shape);
            etCPassword.setBackgroundResource(R.drawable.loi_shape);
            etEmailDK.setBackgroundResource(R.drawable.et_shape);
            return;
        }
        if (result.compareTo("emtyEmailPasswordCPassword")==0){
            tvLoiEmail.setText(R.string.emtyEmailDK);
            tvLoiPassword.setText(R.string.emtyPasswordDK);
            tvLoiCPassword.setText(R.string.emtyCPasswordDK);
            etEmailDK.setBackgroundResource(R.drawable.loi_shape);
            etPasswordDK.setBackgroundResource(R.drawable.loi_shape);
            etCPassword.setBackgroundResource(R.drawable.loi_shape);
            return;
        }
        if (result.compareTo("khongdungdinhdangEmail")==0){
            tvLoiEmail.setText(R.string.SaiEmailDK);
            tvLoiPassword.setText("");
            tvLoiCPassword.setText("");
            etEmailDK.setBackgroundResource(R.drawable.loi_shape);
            etPasswordDK.setBackgroundResource(R.drawable.et_shape);
            etCPassword.setBackgroundResource(R.drawable.et_shape);
            return;
        }
        if (result.compareTo("Pass#Cpass")==0){
            tvLoiCPassword.setText(R.string.SaiXacNhanMatKhauDK);
            tvLoiEmail.setText("");
            tvLoiPassword.setText("");
            etCPassword.setBackgroundResource(R.drawable.loi_shape);
            etEmailDK.setBackgroundResource(R.drawable.et_shape);
            etPasswordDK.setBackgroundResource(R.drawable.et_shape);
        }
        if (result.compareTo("pass<6")==0){
            tvLoiPassword.setText(R.string.SaiMatKhauDK);
            tvLoiEmail.setText("");
            tvLoiCPassword.setText("");
            etPasswordDK.setBackgroundResource(R.drawable.loi_shape);
            etEmailDK.setBackgroundResource(R.drawable.et_shape);
            etCPassword.setBackgroundResource(R.drawable.et_shape);
            return;
        }
        if (result=="thanhcong"){
            Toast.makeText(getActivity(),"Đăng ký thành công",Toast.LENGTH_SHORT).show();
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_main,new DangNhapFragment()).commit();
        }

    }
}
