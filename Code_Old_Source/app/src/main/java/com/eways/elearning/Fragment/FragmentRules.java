package com.eways.elearning.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.eways.elearning.Handler.ReadTextHandler;
import com.eways.elearning.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentRules extends Fragment {

    TextView rules;

    ReadTextHandler readTextHandler;

    public FragmentRules() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_rules, container, false);
        declare_views(root);
        handle_views();
        return root;
    }

    public void declare_views(View root){
        rules = root.findViewById(R.id.tvRules);
    }

    public void handle_views(){
        //read and set text for rules
        readTextHandler = new ReadTextHandler(getActivity());
        String rulesText = readTextHandler.readTxt("rules.txt");
        rules.setText(rulesText);
    }


}
