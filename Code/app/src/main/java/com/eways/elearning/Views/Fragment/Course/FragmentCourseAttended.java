package com.eways.elearning.Views.Fragment.Course;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eways.elearning.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentCourseAttended extends Fragment {


    public FragmentCourseAttended() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_course_attended, container, false);
    }

}
