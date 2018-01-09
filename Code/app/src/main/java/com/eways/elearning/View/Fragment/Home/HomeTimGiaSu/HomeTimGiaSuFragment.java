package com.eways.elearning.View.Fragment.Home.HomeTimGiaSu;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eways.elearning.DataModel.KhoaHoc.CustomModelKhoaHoc;
import com.eways.elearning.Handler.Adapter.KhoaHoc.DanhSachKhoaHocHomeAdapter;
import com.eways.elearning.Handler.FragmentHandler;
import com.eways.elearning.Handler.ImageHandler;
import com.eways.elearning.Model.Database.SharedPreferencesHandler;
import com.eways.elearning.Presenter.Home.NewHomeFragmentPresenter;
import com.eways.elearning.R;
import com.eways.elearning.Util.SupportKeysList;
import com.eways.elearning.View.Dialog.LoadingDialog;
import com.eways.elearning.View.Fragment.Home.NewHomeFragmentImp;
import com.eways.elearning.View.Fragment.KhoaHoc.ListKhoaHoc.ListKhoaHocFragment;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeTimGiaSuFragment extends Fragment implements View.OnClickListener, NewHomeFragmentImp, SwipeRefreshLayout.OnRefreshListener {
    RecyclerView rvDanhSachKhoaHocAnhVan, rvDanhSachKhoaHocToan, rvDanhSachKhoaHocKhac;
    SwipeRefreshLayout refreshLayout;

    private FragmentHandler fragmentHandler;
    private NewHomeFragmentPresenter newHomeFragmentPresenter;
    private SharedPreferencesHandler mySharedPref;
    private ImageHandler imageHandler;
    private ArrayList danhSachKhoaHocAnhVan = new ArrayList();
    private ArrayList danhSachKhoaHocToan = new ArrayList();
    private ArrayList danhSachKhoaHocKhac = new ArrayList();

    public final boolean TYPE_TIM_GIA_SU = SupportKeysList.TYPE_TIM_GIA_SU;

    public HomeTimGiaSuFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentHandler = new FragmentHandler(getActivity(), getActivity().getSupportFragmentManager());
        newHomeFragmentPresenter = new NewHomeFragmentPresenter(this);
        mySharedPref = new SharedPreferencesHandler(getActivity(), SupportKeysList.SHARED_PREF_FILE_NAME);
        imageHandler = new ImageHandler(getActivity());
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home_tim_gia_su, container, false);
        rvDanhSachKhoaHocAnhVan = root.findViewById(R.id.recyclerView_DanhSachKhoaHocAnhVan_HomeTimGiaSu);
        rvDanhSachKhoaHocToan = root.findViewById(R.id.recyclerView_DanhSachKhoaHocToan_HomeTimGiaSu);
        rvDanhSachKhoaHocKhac = root.findViewById(R.id.recyclerView_DanhSachKhoaHocKhac_HomeTimGiaSu);
        refreshLayout = root.findViewById(R.id.refreshLayout_HomeTimGiaSu);

        root.findViewById(R.id.textView_XemDanhSachKhoaHocAnhVan_HomeTimGiaSu).setOnClickListener(this);
        root.findViewById(R.id.textView_XemDanhSachKhoaHocToan_HomeTimGiaSu).setOnClickListener(this);
        root.findViewById(R.id.textView_XemDanhSachKhoaHocKhac_HomeTimGiaSu).setOnClickListener(this);

        refreshLayout.setOnRefreshListener(this);
        newHomeFragmentPresenter.guiYeuCau(TYPE_TIM_GIA_SU,"Ngoại ngữ", "Toán", "Other");
        return root;
    }

    private void setUpDanhSach(ArrayList danhSachKhoaHocAnhVan, ArrayList danhSachKhoaHocToan, ArrayList danhSachKhoaHocKhac) {
        rvDanhSachKhoaHocAnhVan.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        rvDanhSachKhoaHocToan.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        rvDanhSachKhoaHocKhac.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        rvDanhSachKhoaHocAnhVan.setAdapter(new DanhSachKhoaHocHomeAdapter(getActivity(), danhSachKhoaHocAnhVan, imageHandler, fragmentHandler, TYPE_TIM_GIA_SU));
        rvDanhSachKhoaHocToan.setAdapter(new DanhSachKhoaHocHomeAdapter(getActivity(), danhSachKhoaHocToan, imageHandler, fragmentHandler, TYPE_TIM_GIA_SU));
        rvDanhSachKhoaHocKhac.setAdapter(new DanhSachKhoaHocHomeAdapter(getActivity(), danhSachKhoaHocKhac, imageHandler, fragmentHandler, TYPE_TIM_GIA_SU));
        LoadingDialog.dismissDialog();
        refreshLayout.setRefreshing(false);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.textView_XemDanhSachKhoaHocKhac_HomeTimGiaSu:
            case R.id.textView_XemDanhSachKhoaHocToan_HomeTimGiaSu:
            case R.id.textView_XemDanhSachKhoaHocAnhVan_HomeTimGiaSu:
                fragmentHandler.ChuyenFragment(new ListKhoaHocFragment(), true, SupportKeysList.TAG_DANH_SACH_KHOA_HOC);
                break;
        }
    }

    @Override
    public void nhanListKhoaHoc(ArrayList<CustomModelKhoaHoc> khoaHocs1, ArrayList<CustomModelKhoaHoc> khoaHocs2, ArrayList<CustomModelKhoaHoc> khoaHocs3) {
        setUpDanhSach(khoaHocs1, khoaHocs2, khoaHocs3);
    }

    @Override
    public void onRefresh() {
        newHomeFragmentPresenter.guiYeuCau(TYPE_TIM_GIA_SU,"Ngoại ngữ", "Toán", "Other");
    }
}
