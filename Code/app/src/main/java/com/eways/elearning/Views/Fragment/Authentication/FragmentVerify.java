package com.eways.elearning.Views.Fragment.Authentication;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.eways.elearning.R;
import com.eways.elearning.Utils.Handler.FragmentHandler;
//import com.eways.etutor.Utils.Handler.SharedPreferencesHandler;
import com.eways.elearning.Views.VerificationCodeView;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

import static android.content.ContentValues.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentVerify extends Fragment implements View.OnClickListener, VerificationCodeView.CodeCompleteListener {

    /** MODELS */
    private String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private String phoneNumber;
    private FragmentHandler fragmentHandler;
    private VerificationCodeView code;
    private Activity activity;
    public static PhoneAuthCredential credential;

    /** PARAMS */
    public static final String PHONE_PARAM = "PhoneNumber";

    /** METHODS */
    public FragmentVerify() {
        // Required empty public constructor
    }

    public static FragmentVerify newInstance(String phoneNumber) {

        Bundle args = new Bundle();
        args.putString(PHONE_PARAM, phoneNumber);
        FragmentVerify fragment = new FragmentVerify();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentHandler = new FragmentHandler(getContext(), R.id.content_signup);
        activity = getActivity();

        phoneNumber = "+84" + FragmentEnterPhone.tvPhoneNumber.getText().toString();
        if (getArguments() != null) {
            phoneNumber = getArguments().getString(PHONE_PARAM);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_verify, container, false);

        // Configure views
        declareViews(root);

        // Events
        root.findViewById(R.id.resent_code_text_view).setOnClickListener(this);

        //listen when completing enter code
        code.setCodeCompleteListener(this);

        SignupFragment.btnNext.setVisibility(View.INVISIBLE);
        verifyPhoneNumber();
        return root;
    }

    public void declareViews(View root){
        code = root.findViewById(R.id.code);
    }

    /** Verify phone number */
    private void verifyPhoneNumber() {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                30,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                getActivity(),               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks
    }

    /** This will trigger when verification state changed */
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onVerificationCompleted(PhoneAuthCredential credential) {
            // This callback will be invoked in two situations:
            // 1 - Instant verification. In some cases the phone number can be instantly
            //     verified without needing to send or enter a verification code.
            // 2 - Auto-retrieval. On some devices Google Play services can automatically
            //     detect the incoming verification SMS and perform verification without
            //     user action.
            Log.d(TAG, "onVerificationCompleted:" + credential);
            FragmentVerify.credential = credential;

            // Move to next step
            fragmentHandler.changeFragment(FragmentUserSignUpInfo.newInstance(), null, R.anim.slide_from_left, 0);
            SignupFragment.btnNext.setVisibility(View.VISIBLE);
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            // This callback is invoked in an invalid request for verification is made,
            // for instance if the the phone number format is not valid.
            Log.w(TAG, "onVerificationFailed", e);

            if (e instanceof FirebaseAuthInvalidCredentialsException) {
                // Invalid request
                // ...
            } else if (e instanceof FirebaseTooManyRequestsException) {
                // The SMS quota for the project has been exceeded
                // ...
            }

            // Show a message and update the UI
            Toast.makeText(getContext(), getString(R.string.msg_phone_verifying_failed), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCodeSent(String verificationId,
                PhoneAuthProvider.ForceResendingToken token) {
            // The SMS verification code has been sent to the provided phone number, we
            // now need to ask the user to enter the code and then construct a credential
            // by combining the code with a verification ID.
            Log.d(TAG, "onCodeSent:" + verificationId);

            // Save verification ID and resending token so we can use them later
            mVerificationId = verificationId;
            mResendToken = token;

            // ...
        }
    };

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.resent_code_text_view) {
            verifyPhoneNumber();
        }
    }

    @Override
    public void complete(boolean complete) {
        if (complete) {
            // Create credential with verificationId and code
            credential = PhoneAuthProvider.getCredential(mVerificationId, code.getTextString());
        }
    }
}
