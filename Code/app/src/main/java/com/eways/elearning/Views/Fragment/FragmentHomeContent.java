package com.eways.elearning.Views.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.eways.elearning.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentHomeContent extends Fragment {
    /* VIEWS */
    Toolbar toolbar;
    RelativeLayout content;
    RecyclerView listSearch;

    public FragmentHomeContent() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_fragment_home_content, container, false);
        declare_views(root);
        handle_views();
        return root;
    }

    public void declare_views(View root){
        toolbar = root.findViewById(R.id.toolbar);
        content = root.findViewById(R.id.content);
        listSearch = root.findViewById(R.id.list_search);

    }

    public void handle_views(){

    }

}
