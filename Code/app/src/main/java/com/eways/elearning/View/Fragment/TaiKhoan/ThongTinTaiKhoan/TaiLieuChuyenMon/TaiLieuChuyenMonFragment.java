package com.eways.elearning.View.Fragment.TaiKhoan.ThongTinTaiKhoan.TaiLieuChuyenMon;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.eways.elearning.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class TaiLieuChuyenMonFragment extends Fragment {

    RecyclerView rcBangCap,rcChuyenMon;
    Button btnThemMon;
    public TaiLieuChuyenMonFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root=inflater.inflate(R.layout.fragment_tai_lieu_chuyen_mon2, container, false);
        rcBangCap=(RecyclerView) root.findViewById(R.id.rcBangCap_TaiLieuChuyenMon);
        rcChuyenMon=(RecyclerView) root.findViewById(R.id.rcChuyenMon_TaiLieuChuyenMon);

        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);

        rcBangCap.setHasFixedSize(true);

        return root;
    }

}
