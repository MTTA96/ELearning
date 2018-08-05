package com.eways.elearning.Views.Fragment.SearchAndFilter;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eways.elearning.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentFilter extends Fragment {


    public FragmentFilter() {
        // Required empty public constructor
    }

    public static FragmentFilter newInstance() {
        
        Bundle args = new Bundle();
        
        FragmentFilter fragment = new FragmentFilter();
        fragment.setArguments(args);
        return fragment;
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_filter, container, false);
    }

}
