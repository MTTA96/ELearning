package com.eways.elearning.View.Fragment.KhoaHoc;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eways.elearning.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ThongTinKhoaHocFragment extends Fragment {

    private static final String KEY_PARAM1="param1";

    public ThongTinKhoaHocFragment() {
        // Required empty public constructor
    }

    public static ThongTinKhoaHocFragment newInstance(String idNguoiDang) {

        Bundle args = new Bundle();
        args.putString(KEY_PARAM1,idNguoiDang);
        ThongTinKhoaHocFragment fragment = new ThongTinKhoaHocFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments()!=null)
            getArguments().getString(KEY_PARAM1);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_thong_tin_khoa_hoc, container, false);
        return root;
    }

}
