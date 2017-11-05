package com.eways.elearning;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class CapNhatThongTinTaiKhoan extends Fragment  {

    Toolbar toolbar;
    Spinner spNamsinh;
    Calendar calendar;
    ArrayList<String> danhsachNam;
    public CapNhatThongTinTaiKhoan() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_cap_nhat_thong_tin_tai_khoan, container, false);
        toolbar=(Toolbar) view.findViewById(R.id.tbCNTTTK);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        toolbar.setTitle(R.string.Title_CNTTTK);
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_revert);
        calendar=Calendar.getInstance();
        int currentYear=calendar.get(Calendar.YEAR);
        for (int i=1960;i<=currentYear;i++){
            danhsachNam.add(String.valueOf(i));
        }
        spNamsinh=(Spinner) view.findViewById(R.id.spNamsinh_CNTTTK);
        ArrayAdapter<String> NSadapter=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,danhsachNam);
        spNamsinh.setAdapter(NSadapter);

        return view;
    }

}
