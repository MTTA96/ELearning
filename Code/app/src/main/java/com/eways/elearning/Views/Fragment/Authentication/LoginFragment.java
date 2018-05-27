package com.eways.elearning.Views.Fragment.Authentication;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.eways.elearning.Interfaces.DataCallBack;
import com.eways.elearning.Presenter.Authentication.SignInPresenter;
import com.eways.elearning.R;
import com.eways.elearning.Utils.Handler.FragmentHandler;
import com.eways.elearning.Utils.SupportKey;
import com.eways.elearning.Views.Activity.HomeActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment implements View.OnClickListener, DataCallBack {

    /** ----- VIEWS ------ */
    EditText edtPhone, edtPass;
    Button btnLogin;
    TextView  tvRules;
    View tvSignup;

    /** MODELS */
    private FragmentHandler fragmentHandler;
    private SignInPresenter signInPresenter;
    private String userName, password;

    /** KEYS */
    public static final String KEY_PRE_LOGIN = "id_login";

    /** METHODS */
    public LoginFragment() {
        // Required empty public constructor
    }

    public static LoginFragment newInstance() {

        LoginFragment fragment = new LoginFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        signInPresenter = new SignInPresenter(getContext(), this);
        fragmentHandler = new FragmentHandler(getActivity(), R.id.content_user);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_login, container, false);

        declare(root);
        handle();

        return root;
    }

    /** Declare views */
    public void declare(View root) {

        edtPhone = root.findViewById(R.id.phone);
        edtPass = root.findViewById(R.id.password_login);
        tvSignup = root.findViewById(R.id.sign_up_button);
        btnLogin = root.findViewById(R.id.sign_in_button);
    }

    /** handle views */
    public void handle() {
        btnLogin.setOnClickListener(this);
        tvSignup.setOnClickListener(this);
    }

    private boolean checkInfo() {
        if (userName.isEmpty() || password.isEmpty())
            return false;

        return true;
    }

    /** MARK: - EVENTS */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            // Sign in
            case R.id.sign_in_button:
                userName = edtPhone.getText().toString();
                password = edtPass.getText().toString();
                if (checkInfo())
                    signInPresenter.signIn("+84" + userName, password);
                else
                    Toast.makeText(getContext(), R.string.msg_missing_info, Toast.LENGTH_SHORT).show();
                break;

            // Sign up
            case R.id.sign_up_button:
                fragmentHandler.changeFragment(new SignupFragment(), SupportKey.SIGN_UP_FRAGMENT_TAG,R.anim.slide_from_left, 0);
                break;
        }
    }

    /** Handle result from presenter */
    @Override
    public void dataCallBack(int resultCode, @Nullable Bundle bundle) {
        // Handle error
        if (resultCode == SupportKey.FAILED_CODE) {
            Toast.makeText(getContext(), R.string.msg_sign_in_failed, Toast.LENGTH_SHORT).show();
            return;
        }

        // Check sign in result
        int status = Integer.parseInt(bundle.getString(null));
        if (status == 0) {
            // Move to home
            Intent homeIntent = new Intent(getActivity(), HomeActivity.class);
            startActivity(homeIntent);
            getActivity().finish();
            return;
        }

        // Show msg result to user
        Toast.makeText(getContext(), bundle.getString(SupportKey.BUNDLE_MSG), Toast.LENGTH_SHORT).show();
    }

    /** Handle result from Gmail */
    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

//        gmailHandler.onActivityResult(requestCode, resultCode, data);
    }
}
