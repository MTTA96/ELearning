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

import com.eways.elearning.Data.DataModel.TaiKhoan.TaiLieu.TaiLieuChuyenMon.TaiLieuChuyenMon;
import com.eways.elearning.Handler.Adapter.TaiLieuChuyenMon.DanhSachLinhVucChuyenMonAdapter;
import com.eways.elearning.Handler.FragmentHandler;
import com.eways.elearning.Data.Model.Database.SharedPreferencesHandler;
import com.eways.elearning.Presenter.TaiKhoan.CapNhatTaiKhoan.CapNhatTaiLieuChuyenMonPresenter;
import com.eways.elearning.Presenter.TaiKhoan.CapNhatTaiKhoan.CapNhatTaiLieuChuyenMonPresenterImp;
import com.eways.elearning.R;
import com.eways.elearning.Util.SupportKeysList;
import com.eways.elearning.View.Dialog.LoadingDialog;
import com.eways.elearning.View.Fragment.TaiKhoan.ThongTinTaiKhoan.CapNhatTaiKhoan.ThemLinhVucChuyenMonFragment;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class CapNhatTaiLieuChuyenMonFragment extends Fragment implements View.OnClickListener ,CapNhatTaiLieuChuyenMonViewImp{
    RecyclerView rvLinhVuc;
    Button btnThemLinhVuc;

    private FragmentHandler fragmentHandler;
    private SharedPreferencesHandler sharedPreferencesHandler;
    private ArrayList<TaiLieuChuyenMon> danhSachLinhVucChuyenMon = new ArrayList<>();
    private CapNhatTaiLieuChuyenMonPresenterImp capNhatTaiLieuChuyenMonPresenterImp;
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
        sharedPreferencesHandler=new SharedPreferencesHandler(getActivity(),SupportKeysList.SHARED_PREF_FILE_NAME);
        capNhatTaiLieuChuyenMonPresenterImp=new CapNhatTaiLieuChuyenMonPresenter(this);
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

        capNhatTaiLieuChuyenMonPresenterImp.YeuCauDataTaiLieuChuyenMon(sharedPreferencesHandler.getID(),getActivity());
        setUpView();
        return root;
    }

    private void setUpView() {
        if (type.compareTo(TYPE_VIEW)==0){
            getActivity().getActionBar().hide();
            btnThemLinhVuc.setVisibility(View.GONE);
        }
        else {
            btnThemLinhVuc.setVisibility(View.VISIBLE);
        }
    }


    public void loadData(ArrayList<TaiLieuChuyenMon> danhSachTaiLieuChuyenMon){
        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        rvLinhVuc.setLayoutManager(layoutManager);
        rvLinhVuc.setAdapter(new DanhSachLinhVucChuyenMonAdapter(getContext(), danhSachTaiLieuChuyenMon));
        LoadingDialog.dismissDialog();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_ThemLinhVucChuyenMon:
                fragmentHandler.ChuyenFragment(ThemLinhVucChuyenMonFragment.newInstance(null), true, SupportKeysList.TAG_THEM_LINH_VUC_CHUYEN_MON);
                break;
        }
    }

    @Override
    public void DataTaiLieuChuyenMon(ArrayList<TaiLieuChuyenMon> danhSachTaiLieuChuyenMon) {
        loadData(danhSachTaiLieuChuyenMon);
    }
}
