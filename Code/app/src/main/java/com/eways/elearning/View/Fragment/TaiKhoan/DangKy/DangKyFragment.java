package com.eways.elearning.View.Fragment.TaiKhoan.DangKy;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.eways.elearning.Presenter.DangKy.DangKyPresenter;
import com.eways.elearning.R;
import com.eways.elearning.View.Fragment.TaiKhoan.DangNhap.DangNhapFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class DangKyFragment extends Fragment implements View.OnClickListener,DangKyImpView {
    Button btnHuy,btnDangKy_DK;
    EditText etEmailDK,etPasswordDK,etCPassword;
    DangKyPresenter dangKyPresenter;

    public DangKyFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
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
        dangKyPresenter=new DangKyPresenter(getActivity());
        btnHuy.setOnClickListener(this);
        btnDangKy_DK.setOnClickListener(this);
        return root;
    }

    @Override
    public void onClick(View view) {
        if (view.getId()==R.id.btnHuy_DK)
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_main,new DangNhapFragment()).commit();
        if (view.getId()==R.id.btnDK_DK)
            dangKyPresenter.NhanThongTinDangKy(etEmailDK.getText().toString(),etPasswordDK.getText().toString(),etCPassword.getText().toString());

    }

    @Override
    public void NhanKetQuaTuPresenter(String result) {
        if (result == "thanhcong"){
            Toast.makeText(getActivity(),"Đăng ký thành công ",Toast.LENGTH_SHORT).show();
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_main,new DangNhapFragment()).commit();
        }
        else {
            Toast.makeText(getActivity(), "Đăng ký thất bại", Toast.LENGTH_SHORT);
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_main,new DangNhapFragment()).commit();
        }
    }
}
