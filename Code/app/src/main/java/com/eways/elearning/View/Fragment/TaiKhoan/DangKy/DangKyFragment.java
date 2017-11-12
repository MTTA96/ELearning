package com.eways.elearning.View.Fragment.TaiKhoan.DangKy;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.eways.elearning.Handler.FragmentHandler;
import com.eways.elearning.Presenter.TaiKhoan.DangKy.DangKyPresenter;
import com.eways.elearning.Presenter.TaiKhoan.DangKy.DangKyPresenterImp;
import com.eways.elearning.R;
import com.eways.elearning.View.Fragment.TaiKhoan.DangNhap.DangNhapFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class DangKyFragment extends Fragment implements View.OnClickListener,DangKyViewImp {
    Button btnHuy,btnDangKy_DK;
    EditText etEmailDK,etPasswordDK,etCPassword;
    TextView tvLoiEmail,tvLoiPassword,tvLoiCPassword;
    private DangKyPresenterImp dangKyImpPresenter;
    private FragmentHandler fragmentHandler;
    CheckBox cbDieuKhoan;
    boolean trangThaicbDieuKhoan;

    public static final String ERROR_MSG_THIEU_EMAIL_PW = "thieu_email_pw";
    public static final String ERROR_MSG_THIEU_EMAIL = "thieu_email";
    public static final String ERROR_MSG_THIEU_PW = "thieu_pw";
    public static final String ERROR_MSG_THIEU_CPW = "thieu_cpw";
    public static final String ERROR_MSG_THIEU_EMAIL_CPW = "thieu_email_cpw";
    public static final String ERROR_MSG_THIEU_PW_CPW = "thieu_pw_cpw";
    public static final String ERROR_MSG_THIEU_EMAIL_PW_CPW = "thieu_email_pw_cpw";
    public static final String ERROR_MSG_SAI_DINH_DANG_EMAIL = "sai_dinh_dang_email";
    public static final String ERROR_MSG_SAI_CPW = "sai_cpw";
    public static final String ERROR_MSG_PW_SAI_YEU_CAU = "pw_sai_yeu_cau";
    public static final String SIGN_UP_SUCCESS = "sign_up_success";
    public static final String SIGN_UP_FAILED = "sign_up_failed";

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
        cbDieuKhoan= (CheckBox) root.findViewById(R.id.cbDieuKhoan);
        trangThaicbDieuKhoan=cbDieuKhoan.isChecked();
        btnHuy.setOnClickListener(this);
        btnDangKy_DK.setOnClickListener(this);
        if (cbDieuKhoan.isChecked()==false){
            btnDangKy_DK.setEnabled(false);
            btnDangKy_DK.setBackgroundResource(R.drawable.disablebtn_shape);
        }
        cbDieuKhoan.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked==true){
                        btnDangKy_DK.setEnabled(true);
                        btnDangKy_DK.setBackgroundResource(R.drawable.btn_shape);
                    }
                    else{
                        btnDangKy_DK.setEnabled(false);
                        btnDangKy_DK.setBackgroundResource(R.drawable.disablebtn_shape);
                    }
                }
        });
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
        if(result.compareTo(ERROR_MSG_THIEU_EMAIL)==0){
            tvLoiEmail.setText(R.string.emtyEmailDK);
            tvLoiPassword.setText("");
            tvLoiCPassword.setText("");
            etEmailDK.setBackgroundResource(R.drawable.loi_shape);
            etPasswordDK.setBackgroundResource(R.drawable.et_shape);
            etCPassword.setBackgroundResource(R.drawable.et_shape);
            return;
        }
        if (result.compareTo(ERROR_MSG_THIEU_PW)==0){
            tvLoiPassword.setText(R.string.emtyPasswordDK);
            tvLoiEmail.setText("");
            tvLoiCPassword.setText("");
            etPasswordDK.setBackgroundResource(R.drawable.loi_shape);
            etEmailDK.setBackgroundResource(R.drawable.et_shape);
            etCPassword.setBackgroundResource(R.drawable.et_shape);
            return;
        }
        if (result.compareTo(ERROR_MSG_THIEU_CPW)==0){
            tvLoiCPassword.setText(R.string.emtyCPasswordDK);
            tvLoiEmail.setText("");
            tvLoiPassword.setText("");
            etCPassword.setBackgroundResource(R.drawable.loi_shape);
            etEmailDK.setBackgroundResource(R.drawable.et_shape);
            etPasswordDK.setBackgroundResource(R.drawable.et_shape);
            return;
        }
        if (result.compareTo(ERROR_MSG_THIEU_EMAIL_PW)==0){
            tvLoiPassword.setText(R.string.emtyPasswordDK);
            tvLoiEmail.setText(R.string.emtyEmailDK);
            tvLoiCPassword.setText("");
            etEmailDK.setBackgroundResource(R.drawable.loi_shape);
            etPasswordDK.setBackgroundResource(R.drawable.loi_shape);
            etCPassword.setBackgroundResource(R.drawable.et_shape);
            return;
        }
        if (result.compareTo(ERROR_MSG_THIEU_EMAIL_CPW)==0){
            tvLoiEmail.setText(R.string.emtyEmailDK);
            tvLoiCPassword.setText(R.string.emtyCPasswordDK);
            tvLoiPassword.setText("");
            etEmailDK.setBackgroundResource(R.drawable.loi_shape);
            etCPassword.setBackgroundResource(R.drawable.loi_shape);
            etPasswordDK.setBackgroundResource(R.drawable.et_shape);
            return;
        }
        if (result.compareTo(ERROR_MSG_THIEU_PW_CPW)==0){
            tvLoiPassword.setText(R.string.emtyPasswordDK);
            tvLoiCPassword.setText(R.string.emtyCPasswordDK);
            tvLoiEmail.setText("");
            etPasswordDK.setBackgroundResource(R.drawable.loi_shape);
            etCPassword.setBackgroundResource(R.drawable.loi_shape);
            etEmailDK.setBackgroundResource(R.drawable.et_shape);
            return;
        }
        if (result.compareTo(ERROR_MSG_THIEU_EMAIL_PW_CPW)==0){
            tvLoiEmail.setText(R.string.emtyEmailDK);
            tvLoiPassword.setText(R.string.emtyPasswordDK);
            tvLoiCPassword.setText(R.string.emtyCPasswordDK);
            etEmailDK.setBackgroundResource(R.drawable.loi_shape);
            etPasswordDK.setBackgroundResource(R.drawable.loi_shape);
            etCPassword.setBackgroundResource(R.drawable.loi_shape);
            return;
        }
        if (result.compareTo(ERROR_MSG_SAI_DINH_DANG_EMAIL)==0){
            tvLoiEmail.setText(R.string.SaiEmailDK);
            tvLoiPassword.setText("");
            tvLoiCPassword.setText("");
            etEmailDK.setBackgroundResource(R.drawable.loi_shape);
            etPasswordDK.setBackgroundResource(R.drawable.et_shape);
            etCPassword.setBackgroundResource(R.drawable.et_shape);
            return;
        }
        if (result.compareTo(ERROR_MSG_SAI_CPW)==0){
            tvLoiCPassword.setText(R.string.SaiXacNhanMatKhauDK);
            tvLoiEmail.setText("");
            tvLoiPassword.setText("");
            etCPassword.setBackgroundResource(R.drawable.loi_shape);
            etEmailDK.setBackgroundResource(R.drawable.et_shape);
            etPasswordDK.setBackgroundResource(R.drawable.et_shape);
        }
        if (result.compareTo(ERROR_MSG_PW_SAI_YEU_CAU)==0){
            tvLoiPassword.setText(R.string.SaiMatKhauDK);
            tvLoiEmail.setText("");
            tvLoiCPassword.setText("");
            etPasswordDK.setBackgroundResource(R.drawable.loi_shape);
            etEmailDK.setBackgroundResource(R.drawable.et_shape);
            etCPassword.setBackgroundResource(R.drawable.et_shape);
            return;
        }
        if (result.compareTo(SIGN_UP_SUCCESS)==0){

            Toast.makeText(getActivity(),"Đăng ký thành công",Toast.LENGTH_SHORT).show();
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_main,new DangNhapFragment()).commit();
        }else {
            tvLoiEmail.setText("Tài khoản đã tồn tại");
            tvLoiPassword.setText("");
            tvLoiCPassword.setText("");
            etEmailDK.setBackgroundResource(R.drawable.loi_shape);
            etCPassword.setBackgroundResource(R.drawable.et_shape);
            etPasswordDK.setBackgroundResource(R.drawable.et_shape);
            return;
        }

    }
}
