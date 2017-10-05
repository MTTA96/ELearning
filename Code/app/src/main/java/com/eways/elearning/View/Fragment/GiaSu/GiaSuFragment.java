package com.eways.elearning.View.Fragment.GiaSu;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.eways.elearning.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class GiaSuFragment extends Fragment {


    public GiaSuFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_gia_su, container, false);
        return root;
    }

}
