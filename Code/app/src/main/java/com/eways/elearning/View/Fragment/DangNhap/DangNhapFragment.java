package com.eways.elearning.View.Fragment.DangNhap;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.eways.elearning.R;
import com.eways.elearning.View.Activity.Activity.MainActivity;
import com.eways.elearning.View.Fragment.DangKy.DangKyFragment;
import com.facebook.FacebookSdk;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * A simple {@link Fragment} subclass.
 */
public class DangNhapFragment extends Fragment implements View.OnClickListener{
    Button btnDangky;
    public DangNhapFragment() {
        // Required empty public constructor
    }

    public void setContentView(int contentView) {

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_dang_nhap, container, false);
        //Phần Ánh xạ
        btnDangky= (Button) root.findViewById(R.id.btnSignup);

        //Phần khởi tạo
        //Phần code
        btnDangky.setOnClickListener(this);
        return root;
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnSignup)
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.LoMain,new DangKyFragment()).commit();
    }
}
