package com.eways.elearning.View.Fragment.KhoaHoc.TimKiemKhoaHoc;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eways.elearning.DataModel.KhoaHoc.CustomModelKhoaHoc;
import com.eways.elearning.DataModel.KhoaHoc.KhoaHoc;
import com.eways.elearning.Handler.Adapter.KhoaHoc.KhoaHocRCAdapter;
import com.eways.elearning.Handler.FragmentHandler;
import com.eways.elearning.Handler.ImageHandler;
import com.eways.elearning.Presenter.TimKiemKhoaHoc.KetQuaTimKiemKhoaHocFragmentPresenter;
import com.eways.elearning.Presenter.TimKiemKhoaHoc.KetQuaTimKiemKhoaHocFragmentPresenterImp;
import com.eways.elearning.R;

import java.util.ArrayList;


public class KetQuaKhoaHocFragment extends Fragment implements KetQuaKhoaHocViewImp {

    private RecyclerView rcvKhoaHoc;

    private static final String paramRequestThongTinTimKiem = "RequestThongTinTimKiem";

    KhoaHoc requestKhoaHoc;

    ArrayList<CustomModelKhoaHoc> rsKhoaHocChinhXac;
    ArrayList<CustomModelKhoaHoc> rsKhoaHocGanChinhXac;
    KhoaHocRCAdapter adapterKhoaHoc;
    ImageHandler imageHandler;
    FragmentHandler fragmentHandler;

    KetQuaTimKiemKhoaHocFragmentPresenterImp ketQuaTimKiemKhoaHocFragmentPresenterImp;
    public KetQuaKhoaHocFragment() {
        // Required empty public constructor
    }

    public static KetQuaKhoaHocFragment newInstance(KhoaHoc khoaHoc) {

        Bundle args = new Bundle();
        args.putSerializable(paramRequestThongTinTimKiem,khoaHoc);
        KetQuaKhoaHocFragment fragment = new KetQuaKhoaHocFragment();
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
        View root =  inflater.inflate(R.layout.fragment_ket_qua_khoa_hoc, container, false);
        rcvKhoaHoc = (RecyclerView)root.findViewById(R.id.rcvKhoaHoc);
        ketQuaTimKiemKhoaHocFragmentPresenterImp.guiYeuCauTimKhoaHocGiaSu(requestKhoaHoc,getActivity());
        rsKhoaHocChinhXac = new ArrayList<>();
        rsKhoaHocGanChinhXac = new ArrayList<>();
        imageHandler = new ImageHandler(getActivity());

        return root;
    }

    @Override
    public void nhanListKhoaHoc(ArrayList<CustomModelKhoaHoc> chinhxac, ArrayList<CustomModelKhoaHoc> ganChinhXac) {
        rsKhoaHocChinhXac = chinhxac;
        rsKhoaHocGanChinhXac = ganChinhXac;
        if (rsKhoaHocChinhXac == null || rsKhoaHocChinhXac.size() == 0) {
            if (rsKhoaHocGanChinhXac == null || rsKhoaHocGanChinhXac.size()==0) {
//                Toast.makeText(getActivity(), "Không tìm thấy kết quả phù hợp!", Toast.LENGTH_SHORT).show();
            } else {
                adapterKhoaHoc = new KhoaHocRCAdapter(getActivity(), rsKhoaHocGanChinhXac, imageHandler, fragmentHandler);
                rcvKhoaHoc.setLayoutManager(new GridLayoutManager(getActivity(), 1));
                rcvKhoaHoc.setAdapter(adapterKhoaHoc);
            }
        } else {
            adapterKhoaHoc = new KhoaHocRCAdapter(getActivity(), rsKhoaHocChinhXac, imageHandler, fragmentHandler);
            rcvKhoaHoc.setLayoutManager(new GridLayoutManager(getActivity(), 1));
            rcvKhoaHoc.setAdapter(adapterKhoaHoc);
        }
    }

}
