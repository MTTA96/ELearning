package com.eways.elearning.Views.Fragment.Authentication;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.eways.elearning.Interfaces.CallParentFragment;
import com.eways.elearning.R;
import com.eways.elearning.Utils.Handler.FragmentHandler;

/**
 * A simple {@link Fragment} subclass.
 */

public class SignupFragment extends Fragment implements View.OnClickListener, CallParentFragment {

    /** Views */
    public static Button btnNext, btnBack;

    /** Models */
    private FragmentHandler fragmentHandler;
    private int curPosition = 0;

    /** Methods */
    public SignupFragment() {
        // Required empty public constructor

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fragmentHandler = new FragmentHandler(getContext(), R.id.content_signup);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_signup, container, false);

        declare_views(root);
        handle_views();
        return root;
    }

    private void handle_views() {

        btnNext.setOnClickListener(this);
        btnBack.setOnClickListener(this);

        //Setup Fragment handle
        fragmentHandler.changeFragment(FragmentEnterPhone.newInstance(), null,0, 0);

    }

    private void declare_views(View root) {
        btnBack = root.findViewById(R.id.btn_back);
        btnNext = root.findViewById(R.id.btn_next);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_next:
                break;

            case R.id.btn_back:
                clickBack();
                break;
        }
    }


    public void clickBack(){
        fragmentHandler.deleteCurrentFragment();

//        getFragmentManager().popBackStack();
//
//        if (curPosition - 1 == 1){
//            btnNext.setVisibility(View.INVISIBLE);
//        }else {
//            btnNext.setVisibility(View.VISIBLE);
//        }
//
//        curPosition--;
    }

    @Override
    public void callParent(boolean result) {
        if (result){
//            clickNext();
        }
    }
}
