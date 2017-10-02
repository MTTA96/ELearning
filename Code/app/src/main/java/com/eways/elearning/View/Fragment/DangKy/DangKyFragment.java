package com.eways.elearning.View.Fragment.DangKy;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.eways.elearning.R;
import com.eways.elearning.View.Fragment.DangNhap.DangNhapFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class DangKyFragment extends Fragment implements View.OnClickListener {

    Button btnHuy;
    public DangKyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root=inflater.inflate(R.layout.fragment_dang_ky, container, false);
        btnHuy=(Button) root.findViewById(R.id.btnHuy_DK);
        btnHuy.setOnClickListener(this);
        return root;
    }

    @Override
    public void onClick(View view) {
        if (view.getId()==R.id.btnHuy_DK)
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.LoMain,new DangNhapFragment()).commit();
    }
}
