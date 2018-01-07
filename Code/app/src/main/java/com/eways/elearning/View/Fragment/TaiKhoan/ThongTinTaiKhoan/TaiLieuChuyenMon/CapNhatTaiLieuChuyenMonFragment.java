package com.eways.elearning.View.Fragment.TaiKhoan.ThongTinTaiKhoan.TaiLieuChuyenMon;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.eways.elearning.DataModel.TaiKhoan.TaiLieu.TaiLieuChuyenMon.MonTaiLieuChuyenMon;
import com.eways.elearning.DataModel.TaiKhoan.TaiLieu.TaiLieuChuyenMon.TaiLieuChuyenMon;
import com.eways.elearning.Handler.Adapter.TaiLieuChuyenMon.DanhSachLinhVucChuyenMonAdapter;
import com.eways.elearning.Handler.FragmentHandler;
import com.eways.elearning.R;
import com.eways.elearning.Util.SupportKeysList;
import com.eways.elearning.View.Fragment.TaiKhoan.ThongTinTaiKhoan.CapNhatTaiKhoan.ThemLinhVucChuyenMonFragment;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class CapNhatTaiLieuChuyenMonFragment extends Fragment implements View.OnClickListener {
    RecyclerView rvLinhVuc;

    private FragmentHandler fragmentHandler;
    private ArrayList<TaiLieuChuyenMon> danhSachLinhVucChuyenMon = new ArrayList<>();

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
        fragmentHandler = new FragmentHandler(getContext(), getActivity().getSupportFragmentManager());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root=inflater.inflate(R.layout.fragment_tai_lieu_chuyen_mon, container, false);
        rvLinhVuc = root.findViewById(R.id.recyclerView_LinhVucChuyenMon_CapNhatTaiLieuChuyenMon);

        root.findViewById(R.id.button_ThemLinhVucChuyenMon).setOnClickListener(this);

        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        rvLinhVuc.setLayoutManager(layoutManager);
        rvLinhVuc.setAdapter(new DanhSachLinhVucChuyenMonAdapter(getContext(), danhSachLinhVucChuyenMon));
        return root;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_ThemLinhVucChuyenMon:
                fragmentHandler.ChuyenFragment(ThemLinhVucChuyenMonFragment.newInstance(null), true, SupportKeysList.TAG_THEM_LINH_VUC_CHUYEN_MON);
                break;
        }
    }
}
