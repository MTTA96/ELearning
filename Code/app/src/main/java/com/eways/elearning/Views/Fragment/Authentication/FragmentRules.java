package com.eways.elearning.Views.Fragment.Authentication;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.eways.elearning.R;
//<<<<<<< HEAD
//=======
//import com.eways.etutor.Utils.Handler.FragmentHandler;
//import com.eways.etutor.Utils.Handler.ReadTextHandler;
//import com.eways.etutor.Utils.SupportKey;
//>>>>>>> origin/master

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentRules extends Fragment implements View.OnClickListener {

    /** Views */
    TextView rules;

//<<<<<<< HEAD
//
//=======
//    /** Models */
//    private FragmentHandler fragmentHandler;
//    private ReadTextHandler readTextHandler;
//>>>>>>> origin/master

    public FragmentRules() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        fragmentHandler = new FragmentHandler(getContext(), R.id.childSignUpContentView);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_fragment_rules, container, false);
        declare_views(root);
        handle_views();
        return root;
    }

    public void declare_views(View root){
        rules = root.findViewById(R.id.tvRules);
    }

    public void handle_views(){
//        SignupFragment.btnNext.setOnClickListener(this);

        //read and set text for rules
//        readTextHandler = new ReadTextHandler(getActivity());
//        String rulesText = readTextHandler.readTxt("rules.txt");
//        rules.setText(rulesText);
    }

    /** MARK: - EVENTS */
    @Override
    public void onClick(View v) {
//        if (getActivity().getSupportFragmentManager().findFragmentById(R.id.childSignUpContentView) == this) {
//            if (v.getId() == R.id.btn_next) {
////                fragmentHandler.changeFragment(FragmentEnterPhone.newInstance(), SupportKey.ENTER_PHONE_FRAGMENT_TAG, R.anim.slide_from_left, 0);
//            }
//        }
    }


    /** MARK: - METHODS */
}
