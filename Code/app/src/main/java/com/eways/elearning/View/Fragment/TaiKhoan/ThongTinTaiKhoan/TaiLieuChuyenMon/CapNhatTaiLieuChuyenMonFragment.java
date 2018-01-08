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
import android.widget.Button;

import com.eways.elearning.DataModel.TaiKhoan.TaiLieu.TaiLieuChuyenMon.MonTaiLieuChuyenMon;
import com.eways.elearning.DataModel.TaiKhoan.TaiLieu.TaiLieuChuyenMon.TaiLieuChuyenMon;
import com.eways.elearning.Handler.Adapter.TaiLieuChuyenMon.DanhSachLinhVucChuyenMonAdapter;
import com.eways.elearning.Handler.FragmentHandler;
import com.eways.elearning.R;
import com.eways.elearning.Util.SupportKeysList;
import com.eways.elearning.View.Dialog.LoadingDialog;
import com.eways.elearning.View.Fragment.TaiKhoan.ThongTinTaiKhoan.CapNhatTaiKhoan.ThemLinhVucChuyenMonFragment;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class CapNhatTaiLieuChuyenMonFragment extends Fragment implements View.OnClickListener {
    RecyclerView rvLinhVuc;
    Button btnThemLinhVuc;

    private FragmentHandler fragmentHandler;
    private ArrayList<TaiLieuChuyenMon> danhSachLinhVucChuyenMon = new ArrayList<>();
    private String type; //Kiểm tra loại request của fragment

    //Key
    private static final String param1 = "type";
    public static final String TYPE_VIEW = "View";
    public static final String TYPE_EDIT = "Edit";

    public CapNhatTaiLieuChuyenMonFragment() {
        // Required empty public constructor
    }

    public static CapNhatTaiLieuChuyenMonFragment newInstance(String type) {
        
        Bundle args = new Bundle();
        args.putString(param1, type);
        CapNhatTaiLieuChuyenMonFragment fragment = new CapNhatTaiLieuChuyenMonFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments()!=null)
            type = getArguments().getString(param1);
        fragmentHandler = new FragmentHandler(getContext(), getActivity().getSupportFragmentManager());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root=inflater.inflate(R.layout.fragment_tai_lieu_chuyen_mon, container, false);
        rvLinhVuc = root.findViewById(R.id.recyclerView_LinhVucChuyenMon_CapNhatTaiLieuChuyenMon);
        btnThemLinhVuc = root.findViewById(R.id.button_ThemLinhVucChuyenMon);

        btnThemLinhVuc.setOnClickListener(this);

        setUpView();
        return root;
    }

    private void setUpView() {
        if (type.compareTo(TYPE_VIEW)==0){
            getActivity().getActionBar().hide();
            btnThemLinhVuc.setVisibility(View.GONE);
        }
        else {
            getActivity().getActionBar().show();
            btnThemLinhVuc.setVisibility(View.VISIBLE);
        }
    }

    public void loadData(){
        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        rvLinhVuc.setLayoutManager(layoutManager);
        rvLinhVuc.setAdapter(new DanhSachLinhVucChuyenMonAdapter(getContext(), danhSachLinhVucChuyenMon));        LoadingDialog.dismissDialog();
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
