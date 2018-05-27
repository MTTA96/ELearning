package com.eways.elearning.Views.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.eways.elearning.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment {

    /** VIEWS */
    LinearLayout tutorResultView, couseResultView; // These views will be hidden if its result is empty
    RecyclerView rvTutorResults, rvCourseResults;

    /** MODELS */
    private ArrayList tutorList = new ArrayList();
    private ArrayList courseList = new ArrayList();

    public SearchFragment() {
        // Required empty public constructor
    }

    public static SearchFragment newInstance() {
        
        Bundle args = new Bundle();
        
        SearchFragment fragment = new SearchFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_search_results, container, false);
        tutorResultView = root.findViewById(R.id.tutor_search_result_view);
        couseResultView = root.findViewById(R.id.courses_search_result_view);
        rvTutorResults = root.findViewById(R.id.tutor_search_results_recycler_view);
        rvCourseResults = root.findViewById(R.id.course_search_results_recycler_view);

        return root;
    }

}
