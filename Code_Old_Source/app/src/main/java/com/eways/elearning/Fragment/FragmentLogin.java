package com.eways.elearning.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;


import com.eways.elearning.Handler.FragmentHandler;
import com.eways.elearning.R;
import com.google.android.gms.common.SignInButton;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentLogin extends Fragment implements View.OnClickListener {

    /*----- VIEWS ------*/
    SignInButton btnLoginGmail;
    View btnSDT;
    Button skip;

    /*---- FRAGMENT HANDLE -----*/
    FragmentHandler fragmentHandler;

    public static final String KEY_PRE_LOGIN = "id_login";

    public FragmentLogin() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_login, container, false);

        Declare(root);
        Handle();

        return root;
    }

    public static FragmentLogin newInstance() {

        FragmentLogin fragment = new FragmentLogin();
        return fragment;
    }

    //Handle view
    public void Handle() {

        skip.setOnClickListener(this);
        btnSDT.setOnClickListener(this);
    }

    //Declare views
    public void Declare(View root) {
//        View v = getView();
        //Setup button login Gmail

        skip = root.findViewById(R.id.skip);
        btnSDT = root.findViewById(R.id.btn_sdt);

        //Setup Fragment Handle
        fragmentHandler = new FragmentHandler(getActivity(), R.id.content_user);



    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_sdt:
                fragmentHandler.changeFragment(new FragmentSignUp(), R.anim.slide_from_left, 0);
                break;
        }
    }
}

