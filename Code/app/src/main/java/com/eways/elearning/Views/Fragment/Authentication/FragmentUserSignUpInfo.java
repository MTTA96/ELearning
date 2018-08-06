package com.eways.elearning.Views.Fragment.Authentication;


import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.eways.elearning.Interfaces.DataCallBack;
import com.eways.elearning.Model.Account.User;
import com.eways.elearning.Presenter.Authentication.SignUpInfoPresenter;
import com.eways.elearning.R;
import com.eways.elearning.Utils.Handler.FragmentHandler;
import com.eways.elearning.Utils.SupportKeys;
import com.eways.elearning.Views.Fragment.FragmentFavorite;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;

import static android.content.ContentValues.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentUserSignUpInfo extends Fragment implements View.OnClickListener, DataCallBack {

    /**
     * VIEWS
     */
    EditText etFirstName, etLastName, etPassword;
    ImageView clear_name, clear_phone;

    /**
     * Models
     */
    private Activity activity;
    private FragmentHandler fragmentHandler;
    private SignUpInfoPresenter signUpInfoPresenter;
    private PhoneAuthCredential credential;
    private User user;
    private String firstName, lastName, password;

    // Params
    private static final String credentialParam = "credential";

    public static FragmentUserSignUpInfo newInstance() {

        Bundle args = new Bundle();
//        args.putSerializable(credentialParam, (Serializable) credential);
//
        FragmentUserSignUpInfo fragment = new FragmentUserSignUpInfo();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.activity = getActivity();
        fragmentHandler = new FragmentHandler(getContext(), R.id.content_signup);
        signUpInfoPresenter = new SignUpInfoPresenter(this);
        if (getArguments() != null) {
            credential = (PhoneAuthCredential) getArguments().getSerializable(credentialParam);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_user_info, container, false);

        // Configure view
        declare_views(root);
        handle_views();

        return root;
    }

    public void declare_views(View root) {
        etFirstName = root.findViewById(R.id.first_name);
        etLastName = root.findViewById(R.id.last_name);
        etPassword = root.findViewById(R.id.password);
    }

    public void handle_views() {
//        setUpEditText();
        SignupFragment.btnNext.setOnClickListener(this);
    }

    /** This function only need when having clearing content feature (Consider to delete it)*/
    public void setUpEditText() {
        etFirstName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (etFirstName.getText().toString().compareTo("") != 0) {
                    clear_name.setVisibility(View.VISIBLE);
                } else {
                    clear_name.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        etLastName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (etLastName.getText().toString().compareTo("") != 0) {
                    clear_name.setVisibility(View.VISIBLE);
                } else {
                    clear_name.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        etPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (etPassword.getText().toString().compareTo("") != 0) {
                    clear_phone.setVisibility(View.VISIBLE);
                } else {
                    clear_phone.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    /**
     * MARK: - EVENTS
     */
    @Override
    public void onClick(View view) {
        Fragment currentFragment = getActivity().getSupportFragmentManager().findFragmentById(R.id.content_signup);
        if (currentFragment != null && currentFragment == this) {
            {
                switch (view.getId()) {
                    case R.id.btn_next:
                        if (getActivity().getSupportFragmentManager().findFragmentById(R.id.content_signup) == this) {
                            user = prepareData();
                            if (user != null)
                                signInWithPhoneAuthCredential(FragmentVerify.credential);
                        }
                        break;
                }
            }
        }
    }


    /** MARK: - METHODS */

    /**
     * Sign in firebase when verify success and user info has filled
     */
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");

                            FirebaseUser fbUser = task.getResult().getUser();
                            user.setId(fbUser.getUid());
                            signUpInfoPresenter.signUp(user);
                            // ...
                        } else {
                            // Sign in failed, display a message and update the UI
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                            }
                        }
                    }
                });
    }

    /**
     * Prepare data before sign up
     * */
    private User prepareData() {
        User tempUser = new User();
        firstName = etFirstName.getText().toString();
        lastName = etLastName.getText().toString();
        password = etPassword.getText().toString();

        if (!firstName.isEmpty() && !lastName.isEmpty() && !password.isEmpty()) {
            tempUser.setFirstName(firstName);
            tempUser.setLastName(lastName);
            tempUser.setPassword(password);
            tempUser.setPhone("+84" + FragmentEnterPhone.tvPhoneNumber.getText().toString());
            tempUser.setAuthorization(SupportKeys.USER_AUTHORIZATION);
            return tempUser;
        }
        return null;
    }

    /**
     * handle results from presenter
     * */
    @Override
    public void dataCallBack(int resultCode, @Nullable Bundle bundle) {
        // handle error
        if (resultCode == SupportKeys.FAILED_CODE) {
            Toast.makeText(getContext(), getString(R.string.msg_unknow_error), Toast.LENGTH_SHORT).show();
            return;
        }

        // User signed up success
//        fragmentHandler.changeFragment(FragmentFavorite.newInstance(), SupportKeys.ENTER_PHONE_FRAGMENT_TAG, R.anim.slide_from_left, 0);
        fragmentHandler.changeFragment(FragmentWelcome.newInstance(), null, R.anim.slide_from_left, 0);

    }
}
