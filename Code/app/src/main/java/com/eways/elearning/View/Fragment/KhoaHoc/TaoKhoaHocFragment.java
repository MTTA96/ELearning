package com.eways.elearning.View.Fragment.KhoaHoc;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.eways.elearning.R;

/**
 *  Created by Tuấn Anh 11/16/2017
 */
public class TaoKhoaHocFragment extends Fragment {
    Button btnTimGiaSu, btnTimHocVien;
    Button btnTaoKhoaHoc, btnHuy;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private final String TAB_TITLE1 = "Tìm gia sư";
    private final String TAB_TITLE2 = "Tìm học viên";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TaoKhoaHocFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TaoKhoaHocFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TaoKhoaHocFragment newInstance(String param1, String param2) {
        TaoKhoaHocFragment fragment = new TaoKhoaHocFragment();
        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_tao_khoa_hoc, container, false);

        return root;
    }

}
