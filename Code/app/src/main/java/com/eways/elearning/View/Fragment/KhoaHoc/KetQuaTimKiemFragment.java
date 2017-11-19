package com.eways.elearning.View.Fragment.KhoaHoc;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eways.elearning.DataModel.KhoaHoc.KhoaHoc;
import com.eways.elearning.DataModel.KhoaHoc.KhoaHocChuaHoanTat;
import com.eways.elearning.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class KetQuaTimKiemFragment extends Fragment {
    private static final String paramRequestKhoaHoc = "RequestKhoaHoc";
    private static final String paramRequestGiaSu = "RequestGiaSu";
    private static final String paramRequestBangCap = "RequestBangCap";

    private KhoaHoc requestKhoaHoc;
    private boolean requestGiaSu;
    private String requestBangCap;

    public KetQuaTimKiemFragment() {
        // Required empty public constructor
    }

    /**
     * @param requestKhoaHoc chứa thông tin khóa học user tìm kiếm
     * @param timGiaSu true: tìm gia sư
     *                 false: tìm học viên
     * @param bangCap bằng cấp yêu cầu
     * */
    public static KetQuaTimKiemFragment newInstance(boolean timGiaSu, KhoaHoc requestKhoaHoc, String bangCap) {

        Bundle args = new Bundle();
        args.putSerializable(paramRequestKhoaHoc, requestKhoaHoc);
        args.putBoolean(paramRequestGiaSu, timGiaSu);
        args.putString(paramRequestBangCap, bangCap);
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
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_ket_qua_tim_kiem, container, false);
        return root;
    }

}
