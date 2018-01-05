package com.eways.elearning.View.Fragment.TaiKhoan.ThongTinTaiKhoan.TaiLieuChuyenMon;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eways.elearning.Handler.Adapter.TaiLieuChuyenMon.DanhSachLinhVucChuyenMonAdapter;
import com.eways.elearning.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CapNhatTaiLieuChuyenMonFragment extends Fragment {
    RecyclerView rvLinhVuc;

    public CapNhatTaiLieuChuyenMonFragment() {
        // Required empty public constructor
    }

    public static CapNhatTaiLieuChuyenMonFragment newInstance() {
        
        Bundle args = new Bundle();
        
        CapNhatTaiLieuChuyenMonFragment fragment = new CapNhatTaiLieuChuyenMonFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root=inflater.inflate(R.layout.fragment_tai_lieu_chuyen_mon, container, false);
        rvLinhVuc = root.findViewById(R.id.recyclerView_LinhVucChuyenMon_CapNhatTaiLieuChuyenMon);

        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        rvLinhVuc.setLayoutManager(layoutManager);
        rvLinhVuc.setAdapter(new DanhSachLinhVucChuyenMonAdapter(getContext()));
        return root;
    }

}
