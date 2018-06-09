package com.eways.elearning.Views.Fragment.Authentication;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.eways.elearning.Interfaces.DataCallBack;
import com.eways.elearning.Presenter.Authentication.EnterPhonePresenter;
import com.eways.elearning.R;
import com.eways.elearning.Utils.Handler.FragmentHandler;
//import com.eways.etutor.Utils.Handler.SharedPreferencesHandler;
import com.eways.elearning.Utils.SupportKeys;

/**
 * A simple {@link Fragment} subclass.
 */

public class FragmentEnterPhone extends Fragment implements View.OnClickListener, DataCallBack {
    public static TextView tvPhoneNumber;

    /** Models */
    private FragmentHandler fragmentHandler;
    private EnterPhonePresenter enterPhonePresenter;
    private String phoneNumber;

    public FragmentEnterPhone() {
        // Required empty public constructor
    }

    public static FragmentEnterPhone newInstance() {

        Bundle args = new Bundle();

        FragmentEnterPhone fragment = new FragmentEnterPhone();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentHandler = new FragmentHandler(getContext(), R.id.content_signup);
        enterPhonePresenter = new EnterPhonePresenter(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_enter_phone, container, false);
        tvPhoneNumber = root.findViewById(R.id.phone);

        // Events
        SignupFragment.btnNext.setOnClickListener(this);

        return root;
    }

    /** Check phone's status */
    private void checkPhone(String phoneNumber) {
        // handle errors
        if (phoneNumber.isEmpty() || phoneNumber.length() < 9 || phoneNumber.length() > 10) {
            Toast.makeText(getContext(), getString(R.string.msg_wrong_info_or_has_not_signed_up), Toast.LENGTH_SHORT).show();
            return;
        }

        // Check phone number in database
        this.phoneNumber = "+84" + tvPhoneNumber.getText().toString();
        enterPhonePresenter.checkPhoneStatus(this.phoneNumber);

        return;
    }

    /** EVENTS */
    @Override
    public void onClick(View v) {
        // Check if this fragment is the current fragment
        Fragment currentFragment = getActivity().getSupportFragmentManager().findFragmentById(R.id.content_signup);
        if (currentFragment != null && currentFragment == this) {
            switch (v.getId()) {
                case R.id.btn_next:
                    // Check phone number
                    checkPhone(tvPhoneNumber.getText().toString());
                    break;
            }
        }
    }


    /** handle result from presenter */
    @Override
    public void dataCallBack(int resultCode, @Nullable Bundle bundle) {
        // handle error
        if (resultCode == SupportKeys.FAILED_CODE) {
            Toast.makeText(getContext(), R.string.msg_unknow_error, Toast.LENGTH_SHORT).show();
            return;
        }

        // handle result
        int status = bundle.getInt(null);

        // If phone is not available
        if (status == 0) {
            Toast.makeText(getContext(), R.string.msg_existing_user, Toast.LENGTH_LONG).show();
            return;
        }

        // Phone is available to use now
        fragmentHandler.changeFragment(FragmentVerify.newInstance(phoneNumber), SupportKeys.VERIFY_FRAGMENT_TAG, R.anim.slide_from_left, 0);
    }

}

