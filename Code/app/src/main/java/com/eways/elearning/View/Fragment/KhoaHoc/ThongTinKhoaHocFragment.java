package com.eways.elearning.View.Fragment.KhoaHoc;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.eways.elearning.DataModel.ThongTinChiTietKhoaHoc;
import com.eways.elearning.Presenter.KhoaHoc.ThongTinKhoaHoc.ThongTinKhoaHocPresenter;
import com.eways.elearning.Presenter.KhoaHoc.ThongTinKhoaHoc.ThongTinKhoaHocPresenterImp;
import com.eways.elearning.R;
import com.eways.elearning.Util.SupportKeysList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ThongTinKhoaHocFragment extends Fragment implements ThongTinKhoaHocViewImp{

    private static final String KEY_PARAM1="param1";
    private static final String KEY_PARAM2="param2";
    private ThongTinKhoaHocPresenterImp thongTinKhoaHocPresenterImp;

    public ThongTinKhoaHocFragment() {
        // Required empty public constructor
    }

    public static ThongTinKhoaHocFragment newInstance(String idNguoiDang, String idKhoaHoc) {

        Bundle args = new Bundle();
        args.putString(KEY_PARAM1,idNguoiDang);
        args.putString(KEY_PARAM2,idKhoaHoc);
        ThongTinKhoaHocFragment fragment = new ThongTinKhoaHocFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        thongTinKhoaHocPresenterImp=new ThongTinKhoaHocPresenter(this);
        if (getArguments()!=null) {
            thongTinKhoaHocPresenterImp.YeuCauLayThongTinKhoaHoc(getActivity(), SupportKeysList.GET_DATA_TIMGIASU, getArguments().getString(KEY_PARAM1),getArguments().getString(KEY_PARAM2));
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_thong_tin_khoa_hoc, container, false);

        return root;
    }

    @Override
    public void KetQuaThongTinKhoaHoc(ThongTinChiTietKhoaHoc thongTinChiTietKhoaHoc) {

    }
}
