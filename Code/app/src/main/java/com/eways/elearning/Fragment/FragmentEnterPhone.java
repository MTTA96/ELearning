package com.eways.elearning.Fragment;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.eways.elearning.Handler.SharedPreferencesHandler;
import com.eways.elearning.Handler.SupportKey;
import com.eways.elearning.R;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentEnterPhone extends Fragment {
    public static TextView tvPhoneNumber;

    // Data variable
    private SharedPreferencesHandler preferencesHandler;

    public FragmentEnterPhone() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferencesHandler = new SharedPreferencesHandler(getContext(), SupportKey.SHARED_PREF_FILE_NAME);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_enter_phone, container, false);
        tvPhoneNumber = root.findViewById(R.id.phone);

        if (!tvPhoneNumber.getText().toString().isEmpty()) {
            preferencesHandler.setPhoneNumber(tvPhoneNumber.getText().toString());
        }
        return root;
    }


}
