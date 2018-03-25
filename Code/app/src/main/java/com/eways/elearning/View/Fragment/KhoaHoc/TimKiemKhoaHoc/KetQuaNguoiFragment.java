package com.eways.elearning.View.Fragment.KhoaHoc.TimKiemKhoaHoc;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.eways.elearning.Model.DataModel.KhoaHoc.KhoaHoc;
import com.eways.elearning.Model.DataModel.TaiKhoan.TaiKhoan;
import com.eways.elearning.Handler.Adapter.TaiKhoan.TaiKhoanRCAdapter;
import com.eways.elearning.Handler.FragmentHandler;
import com.eways.elearning.Handler.ImageHandler;
import com.eways.elearning.Presenter.TimKiemKhoaHoc.KetQuaTimKiemKhoaHocFragmentPresenter;
import com.eways.elearning.Presenter.TimKiemKhoaHoc.KetQuaTimKiemKhoaHocFragmentPresenterImp;
import com.eways.elearning.R;
import com.eways.elearning.View.Dialog.LoadingDialog;

import java.util.ArrayList;


public class KetQuaNguoiFragment extends Fragment implements KetQuaNguoiViewImp {

    private RecyclerView rcvGiaSu;
    private static final String paramRequestThongTinTimKiem = "RequestThongTinTimKiem";

    KhoaHoc requestKhoaHoc;

    ArrayList<TaiKhoan> listGiaSu;
    TaiKhoanRCAdapter adapterTaiKhoan;
    ImageHandler imageHandler;
    FragmentHandler fragmentHandler;

    KetQuaTimKiemKhoaHocFragmentPresenterImp ketQuaTimKiemKhoaHocFragmentPresenterImp;
    public KetQuaNguoiFragment() {
        // Required empty public constructor
    }

    public static KetQuaNguoiFragment newInstance(KhoaHoc khoaHoc) {

        Bundle args = new Bundle();
        args.putSerializable(paramRequestThongTinTimKiem,khoaHoc);
        KetQuaNguoiFragment fragment = new KetQuaNguoiFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestKhoaHoc = (KhoaHoc) getArguments().getSerializable(paramRequestThongTinTimKiem);
        fragmentHandler = new FragmentHandler(getActivity(), getActivity().getSupportFragmentManager());
        ketQuaTimKiemKhoaHocFragmentPresenterImp = new KetQuaTimKiemKhoaHocFragmentPresenter(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root =  inflater.inflate(R.layout.fragment_ket_qua_nguoi, container, false);
        rcvGiaSu = (RecyclerView)root.findViewById(R.id.rcvGiaSu);
        listGiaSu = new ArrayList<>();
        imageHandler = new ImageHandler(getActivity());
        return root;
    }

    @Override
    public void nhanListNguoi(ArrayList<TaiKhoan> listGiaSu) {
        if(listGiaSu == null || listGiaSu.size() == 0)
        {
            Toast.makeText(getActivity(), "Không tìm thấy kết quả phù hợp!", Toast.LENGTH_SHORT).show();
        }
        else
        {
            adapterTaiKhoan = new TaiKhoanRCAdapter(getActivity(),listGiaSu,imageHandler,fragmentHandler);
            rcvGiaSu.setLayoutManager(new GridLayoutManager(getActivity(),1));
            rcvGiaSu.setAdapter(adapterTaiKhoan);
            LoadingDialog.dismissDialog();
        }
    }
}
