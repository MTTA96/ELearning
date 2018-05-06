package com.eways.elearning.View.Fragment.TaiKhoan;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import com.eways.elearning.Handler.Other.DataCallBack;
import com.eways.elearning.Model.TaiKhoan.User;
import com.eways.elearning.Presenter.TaiKhoan.SignUpPresenter;
import com.eways.elearning.R;
import com.eways.elearning.View.Dialog.LoadingDialog;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignUpFragment extends Fragment implements View.OnClickListener, DataCallBack {
    TextView tvName, tvPhoneNumber;
    RadioButton rbMale, rbFemale;

    private SignUpPresenter signUpPresenter;

    public SignUpFragment() {
        // Required empty public constructor
    }

    public static SignUpFragment newInstance() {
        
        Bundle args = new Bundle();
        
        SignUpFragment fragment = new SignUpFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_thong_tin_dangky, container, false);
        tvName = root.findViewById(R.id.ho_ten_thong_tin_dang_ky);
        tvPhoneNumber = root.findViewById(R.id.sdt_thong_tin_dang_ky);
        rbMale = root.findViewById(R.id.radio_button_nam_thong_tin_dang_ky);
        rbFemale = root.findViewById(R.id.radio_button_nu_thong_tin_dang_ky);

        root.findViewById(R.id.button_next_thong_tin_dang_ky).setOnClickListener(this);

        signUpPresenter = new SignUpPresenter(this);
        LoadingDialog.dismissDialog();

        return root;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button_next_thong_tin_dang_ky) {
            User user = prepareData();
            signUpPresenter.signUp(user);
        }
    }

    /** Prepare data before calling api */
    private User prepareData() {
        User user = new User();

        // Name
        if (!tvName.getText().toString().isEmpty())
            user.setFirstName(tvName.getText().toString());

        // Phone number
        if (!tvPhoneNumber.getText().toString().isEmpty())
            user.setPhone(tvPhoneNumber.getText().toString());

        // Genre
        if (rbMale.isChecked() || rbFemale.isChecked())
            user.setSex(rbMale.isChecked() ? "Male" : "Female");

        return user;
    }

    @Override
    public void dataCallBack(String result, @Nullable Bundle bundle) {
        if (result.compareTo("Success") == 0) {
            dataCallBack(result, null);
        }
    }
}
