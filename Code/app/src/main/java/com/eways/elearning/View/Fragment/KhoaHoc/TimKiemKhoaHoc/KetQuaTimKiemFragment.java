package com.eways.elearning.View.Fragment.KhoaHoc.TimKiemKhoaHoc;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.eways.elearning.DataModel.KhoaHoc.CustomModelKhoaHoc;
import com.eways.elearning.DataModel.KhoaHoc.KhoaHoc;
import com.eways.elearning.Handler.Adapter.KhoaHoc.KhoaHocRCAdapter;
import com.eways.elearning.Handler.FragmentHandler;
import com.eways.elearning.Handler.ImageHandler;
import com.eways.elearning.Presenter.TimKiemKhoaHoc.KetQuaTimKiemKhoaHocFragmentPresenter;
import com.eways.elearning.Presenter.TimKiemKhoaHoc.KetQuaTimKiemKhoaHocFragmentPresenterImp;
import com.eways.elearning.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class KetQuaTimKiemFragment extends Fragment implements KetQuaTimKiemFragmentViewImp {

    private static final String paramMon = "paramMon";
    private static final String paramRequestKhoaHoc = "RequestKhoaHoc";
    private static final String paramRequestGiaSu = "RequestGiaSu";
    private static final String paramRequestBangCap = "RequestBangCap";

    private KhoaHoc requestKhoaHoc;
    private boolean requestGiaSu;
    private String requestBangCap;
    private String requestMon;


    ArrayList<CustomModelKhoaHoc> rsKhoaHocChinhXac;
    ArrayList<CustomModelKhoaHoc> rsKhoaHocGanChinhXac;

    RecyclerView rcKetQua;
    ImageHandler imageHandler;
    KetQuaTimKiemKhoaHocFragmentPresenterImp ketQuaTimKiemKhoaHocFragmentPresenterImp;
    KhoaHocRCAdapter adapterKhoaHoc;
    private FragmentHandler fragmentHandler;

    public KetQuaTimKiemFragment() {
        // Required empty public constructor
    }

    /**
     * @param requestKhoaHoc chứa thông tin khóa học user tìm kiếm
     * @param timGiaSu       true: tìm gia sư
     *                       false: tìm học viên
     * @param bangCap        bằng cấp yêu cầu
     */
    public static KetQuaTimKiemFragment newInstance(boolean timGiaSu, KhoaHoc requestKhoaHoc, String bangCap) {

        Bundle args = new Bundle();
        args.putSerializable(paramRequestKhoaHoc, requestKhoaHoc);
        args.putBoolean(paramRequestGiaSu, timGiaSu);
        args.putString(paramRequestBangCap, bangCap);
        KetQuaTimKiemFragment fragment = new KetQuaTimKiemFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static KetQuaTimKiemFragment newInstance(String tenMon) {

        Bundle args = new Bundle();
        args.putString(paramMon, tenMon);
        KetQuaTimKiemFragment fragment = new KetQuaTimKiemFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            requestKhoaHoc = (KhoaHoc) getArguments().getSerializable(paramRequestKhoaHoc);
            requestGiaSu = getArguments().getBoolean(paramRequestGiaSu);
            requestBangCap = getArguments().getString(paramRequestBangCap, null);
            fragmentHandler = new FragmentHandler(getActivity(), getChildFragmentManager());
            ketQuaTimKiemKhoaHocFragmentPresenterImp = new KetQuaTimKiemKhoaHocFragmentPresenter(this);

            //Request môn
            requestMon = getArguments().getString(paramMon, "");

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_ket_qua_tim_kiem, container, false);

//        gioiHanLichHoc = (requestKhoaHoc.getThu().size()) / 2;
        rcKetQua = (RecyclerView) root.findViewById(R.id.rcKetQuaTimKiem);
        imageHandler = new ImageHandler(getActivity());
        rsKhoaHocChinhXac = new ArrayList<CustomModelKhoaHoc>();
        rsKhoaHocGanChinhXac = new ArrayList<CustomModelKhoaHoc>();
        ketQuaTimKiemKhoaHocFragmentPresenterImp.guiYeuCauListKhoaHoc(requestKhoaHoc, requestGiaSu,requestMon, getActivity());

        return root;
    }


    @Override
    public void nhanListKhoaHocGanChinhXac(ArrayList<CustomModelKhoaHoc> khoaHocsGanChinhXac) {
//        rsKhoaHocGanChinhXac = khoaHocsGanChinhXac;
    }

    @Override
    public void nhanListKhoaHocChinhXac(ArrayList<CustomModelKhoaHoc> khoaHocsChinhXac) {
//        rsKhoaHocChinhXac = khoaHocsChinhXac;
    }

    @Override
    public void nhanListKhoaHoc(ArrayList<CustomModelKhoaHoc> chinhxac, ArrayList<CustomModelKhoaHoc> ganchinhxac) {
        rsKhoaHocChinhXac = chinhxac;
        rsKhoaHocGanChinhXac = ganchinhxac;
        if (rsKhoaHocChinhXac == null || rsKhoaHocChinhXac.size() == 0) {
            if (rsKhoaHocGanChinhXac == null || rsKhoaHocGanChinhXac.size()==0) {
                Toast.makeText(getActivity(), "Không tìm thấy kết quả!", Toast.LENGTH_SHORT).show();
            } else {
                adapterKhoaHoc = new KhoaHocRCAdapter(getActivity(), rsKhoaHocGanChinhXac, imageHandler, fragmentHandler);
                rcKetQua.setLayoutManager(new GridLayoutManager(getActivity(), 1));
                rcKetQua.setAdapter(adapterKhoaHoc);
            }
        } else {
            adapterKhoaHoc = new KhoaHocRCAdapter(getActivity(), rsKhoaHocChinhXac, imageHandler, fragmentHandler);
            rcKetQua.setLayoutManager(new GridLayoutManager(getActivity(), 1));
            rcKetQua.setAdapter(adapterKhoaHoc);
        }
    }
}
