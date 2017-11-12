package com.eways.elearning.View.Fragment.ListKhoaHoc;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.eways.elearning.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListKhoaHocTimHocVienFragment extends Fragment implements ListKhoaHocTimHocVienImpView{

    SwipeRefreshLayout srlKhoaHocTimHocVien;
    ListView lvKhoaHocTimHocVien;

    public ListKhoaHocTimHocVienFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_list_khoa_hoc_tim_hoc_vien, container, false);

        srlKhoaHocTimHocVien = (SwipeRefreshLayout)root.findViewById(R.id.srlKhoaHocTimHocVien);
        lvKhoaHocTimHocVien = (ListView)root.findViewById(R.id.lvKhoaHocTimHocVien);

        srlKhoaHocTimHocVien.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

            }
        });

        return root;
    }

}
