package com.eways.elearning.Views.Fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.eways.elearning.Adapter.Course.CourseListAdapter;
import com.eways.elearning.Interfaces.DataCallBack;
import com.eways.elearning.Model.Course;
import com.eways.elearning.Model.SearchResults;
import com.eways.elearning.Model.User;
import com.eways.elearning.Presenter.SearchPresenter;
import com.eways.elearning.R;
import com.eways.elearning.Utils.SupportKey;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment implements DataCallBack {

    /** VIEWS */
    LinearLayout tutorResultView, couseResultView; // These views will be hidden if its result is empty
    RecyclerView rvTutorResults, rvCourseResults;

    /** MODELS */
    private SearchPresenter searchPresenter;
    private ArrayList<User> tutorList = new ArrayList();
    private ArrayList<Course> courseList = new ArrayList();
    private CourseListAdapter courseListAdapter;

    /** Params */
    public static final String param1 = "Keyword";

    public SearchFragment() {
        // Required empty public constructor
    }

    public static SearchFragment newInstance(String keyword) {
        
        Bundle args = new Bundle();
        args.putString(param1, keyword);
        SearchFragment fragment = new SearchFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        searchPresenter = new SearchPresenter(getContext(), this);

        if (getArguments() != null) {
            String keyword = getArguments().getString(param1);
            searchPresenter.search(keyword);
        }
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

        tutorResultView.setVisibility(View.GONE);
        couseResultView.setVisibility(View.GONE);

        // Setup results list
        setupResultsListView();
        return root;
    }

    private void setupResultsListView() {
        courseListAdapter = new CourseListAdapter(getContext(), courseList);
        rvCourseResults.setAdapter(courseListAdapter);
    }

    /** EVENTS */
    @Override
    public void dataCallBack(int resultCode, @Nullable Bundle bundle) {
        // Handle errors
        if (resultCode == SupportKey.FAILED_CODE) {
            Log.d(getClass().getName(), "Search failed!");
            return;
        }

        // Get data success
        tutorList.addAll((ArrayList<User>) bundle.getSerializable("param1"));
        courseList.addAll((ArrayList<Course>) bundle.getSerializable("param2"));
        courseListAdapter.notifyDataSetChanged();

    }
}
