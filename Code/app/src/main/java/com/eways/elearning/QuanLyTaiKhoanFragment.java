package com.eways.elearning;


import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;

import com.eways.elearning.DataModel.BaiDang.LinhVucBaiDang;
import com.eways.elearning.Handler.LinhVucQuanTamAdapter;
import com.eways.elearning.Model.Database.FireBaseHandler;
import com.eways.elearning.Model.Database.SharedPreferencesHandler;
import com.eways.elearning.Model.FragmentHandler;
import com.eways.elearning.Util.SupportKeysList;
import com.eways.elearning.View.Fragment.Home.HomeFragment;
import com.eways.elearning.View.Fragment.TaiKhoan.DangNhap.DangNhapFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class QuanLyTaiKhoanFragment extends Fragment implements View.OnClickListener {
    private LinhVucQuanTamAdapter linhVucQuanTamAdapter;
    LinearLayout loLinhVucQuanTam,loTaiKhoanKhac;
    private FragmentHandler fragmentHandler;
    private SharedPreferencesHandler sharedPreferencesHandler;
    public QuanLyTaiKhoanFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferencesHandler=new SharedPreferencesHandler(getContext(), SupportKeysList.SHARED_PREF_FILE_NAME);
        fragmentHandler=new FragmentHandler(getContext(),getActivity().getSupportFragmentManager());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root=inflater.inflate(R.layout.fragment_quan_ly_tai_khoan, container, false);
        loLinhVucQuanTam= (LinearLayout) root.findViewById(R.id.LoLinhVucQuanTam);
        loTaiKhoanKhac= (LinearLayout) root.findViewById(R.id.LoTaiKhoanKhac);
        loTaiKhoanKhac.setOnClickListener(this);
        loLinhVucQuanTam.setOnClickListener(this);

        return root;
    }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.LoLinhVucQuanTam){
            Dialog dialog=new Dialog(getActivity());
            dialog.setContentView(R.layout.dialog_linhvuc_yeuthich);
            RecyclerView rcDanhSachLVQT=(RecyclerView) dialog.findViewById(R.id.rcLinhVucYeuThich);
            rcDanhSachLVQT.setHasFixedSize(true);
            GridLayoutManager gridLayoutManager=new GridLayoutManager(getActivity(),2);
            ArrayList<LinhVucBaiDang> DanhSachLVBD=new ArrayList<>();
            DanhSachLVBD.add(new LinhVucBaiDang(1,"Ẩm Thực",R.drawable.at));
            DanhSachLVBD.add(new LinhVucBaiDang(2,"Học Tập",R.drawable.ht));
            linhVucQuanTamAdapter=new LinhVucQuanTamAdapter(DanhSachLVBD);
            DividerItemDecoration dividerItemDecoration=new DividerItemDecoration(getContext(),gridLayoutManager.getOrientation());
            rcDanhSachLVQT.addItemDecoration(dividerItemDecoration);
            rcDanhSachLVQT.setLayoutManager(gridLayoutManager);
            rcDanhSachLVQT.setAdapter(linhVucQuanTamAdapter);
            dialog.show();
            Window window = dialog.getWindow();
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            dialog.show();
        }
        if (v.getId()==R.id.LoTaiKhoanKhac){
            FirebaseAuth.getInstance().signOut();
            fragmentHandler.ChuyenFragment(new HomeFragment(),false,null);
            sharedPreferencesHandler.DangXuat();

        }
    }
}
