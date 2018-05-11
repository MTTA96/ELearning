package com.eways.elearning.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.eways.elearning.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentUserInfo extends Fragment implements View.OnClickListener{

    /* VIEWS */
    EditText name,password;
    ImageView clear_name, clear_phone;

    public FragmentUserInfo() {
        // Required empty public constructor
    }

    public static FragmentUserInfo newInstance() {

        Bundle args = new Bundle();

        FragmentUserInfo fragment = new FragmentUserInfo();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_user_info, container, false);

        declare_views(root);
        handle_views();

        return root;
    }

    public void declare_views(View root){
        name = root.findViewById(R.id.name);
        password = root.findViewById(R.id.password);

        clear_name = root.findViewById(R.id.clear_name_text);
        clear_phone = root.findViewById(R.id.clear_phone_text);

    }

    public void handle_views(){
        setUpEditText();

        clear_name.setOnClickListener(this);
        clear_phone.setOnClickListener(this);
    }

    public void setUpEditText(){
        name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (name.getText().toString().compareTo("") != 0){
                    clear_name.setVisibility(View.VISIBLE);
                }else {
                    clear_name.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(password.getText().toString().compareTo("")!=0){
                    clear_phone.setVisibility(View.VISIBLE);
                }else {
                    clear_phone.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.clear_name_text:
                name.setText("");

                break;
            case R.id.clear_phone_text:
                password.setText("");

                break;


        }
    }
}
