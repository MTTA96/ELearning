package com.eways.elearning.Views.Fragment.Account;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.eways.elearning.R;
import com.eways.elearning.Utils.Handler.FragmentHandler;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentUserManager extends Fragment implements View.OnClickListener{
    /* VIEWS */

    ImageView avarta;
    TextView userName, userEmail;
    View btnInfo, btnMyCourse, btnHandleRequest, btnLogout;

    FragmentHandler fragmentHandler;

    public FragmentUserManager() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_user_manager, container, false);

        declare_views(root);
        handle_views();

        return root;
    }

    public void declare_views(View root){
        btnInfo = root.findViewById(R.id.btn_info);
        avarta = root.findViewById(R.id.avarta);
        userName = root.findViewById(R.id.userName);
        userEmail = root.findViewById(R.id.userEmail);
        btnHandleRequest = root.findViewById(R.id.btn_handle_request);
        btnLogout = root.findViewById(R.id.btn_logout);
        btnMyCourse = root.findViewById(R.id.btn_my_course);
    }

    public void handle_views(){
//        fragmentHandler = new FragmentHandler(getActivity(), R.id.content_course);

        btnInfo.setOnClickListener(this);
        btnHandleRequest.setOnClickListener(this);
        btnLogout.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_info:
//                fragmentHandler.changeFragment(new FragmentUpdateDetail(), 0 , 0);

                break;

        }
    }
}
