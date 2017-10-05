package com.eways.elearning.View.Fragment.TaiKhoan.DangNhap;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.eways.elearning.R;
import com.eways.elearning.View.Fragment.TaiKhoan.DangKy.DangKyFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class DangNhapFragment extends Fragment implements View.OnClickListener{
    Button btnDangky;

    public DangNhapFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_dang_nhap, container, false);
        btnDangky= (Button) root.findViewById(R.id.btnSignup);

        btnDangky.setOnClickListener(this);
        return root;
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnSignup)
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.LoMain,new DangKyFragment()).commit();
    }
}
