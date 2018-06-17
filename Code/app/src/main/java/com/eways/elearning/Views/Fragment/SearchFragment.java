package com.eways.elearning.Views.Fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.eways.elearning.Adapter.Course.CourseListAdapter;
import com.eways.elearning.Adapter.User.UserListAdapter;
import com.eways.elearning.Interfaces.DataCallBack;
import com.eways.elearning.Model.Course.Course;
import com.eways.elearning.Model.Account.User;
import com.eways.elearning.Presenter.SearchPresenter;
import com.eways.elearning.R;
import com.eways.elearning.Utils.SupportKeys;
import com.eways.elearning.Views.Activity.HomeActivity;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment implements DataCallBack, View.OnClickListener {

    /** VIEWS */
    LinearLayout tutorResultView, couseResultView; // These views will be hidden if its result is empty
    RecyclerView rvTutorResults, rvCourseResults;

    /** MODELS */
    private SearchPresenter searchPresenter;
    private UserListAdapter userListAdapter;
    private CourseListAdapter courseListAdapter;
    private ArrayList<User> tutorList = new ArrayList();
    private ArrayList<Course> courseList = new ArrayList();

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
        //set button back
        ((HomeActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        super.onCreate(savedInstanceState);
        searchPresenter = new SearchPresenter(getContext(), this);

        if (getArguments() != null) {
            String keyword = getArguments().getString(param1);

            // Call api
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

        root.findViewById(R.id.button_load_more_course_search_results).setOnClickListener(this);
        root.findViewById(R.id.button_load_more_tutor_search_results).setOnClickListener(this);

        // Setup results list
        setupResultsListView();
        updateResultViewVisibility();

        return root;
    }

    private void setupResultsListView() {
        userListAdapter = new UserListAdapter(getContext(), tutorList);
        courseListAdapter = new CourseListAdapter(getContext(), courseList);

        rvTutorResults.setLayoutManager(new GridLayoutManager(getContext(), 1));
        rvTutorResults.hasFixedSize();
        rvTutorResults.setNestedScrollingEnabled(false);
        rvTutorResults.setAdapter(userListAdapter);
        rvCourseResults.setLayoutManager(new GridLayoutManager(getContext(), 1));
        rvCourseResults.hasFixedSize();
        rvCourseResults.setNestedScrollingEnabled(false);
        rvCourseResults.setAdapter(courseListAdapter);
    }

    /** Update result list visibility depend on their data
     *  @Gone when they don't have data
     *  @Visible when they have data
     * */
    private void updateResultViewVisibility() {
        couseResultView.setVisibility(courseList.size() > 0 ? View.VISIBLE : View.GONE);
        tutorResultView.setVisibility(tutorList.size() > 0 ? View.VISIBLE : View.GONE);
    }

    /** EVENTS */
    @Override
    public void dataCallBack(int resultCode, @Nullable Bundle bundle) {
        // Handle errors
        if (resultCode == SupportKeys.FAILED_CODE) {
            Log.d(getClass().getName(), "Search failed!");
            return;
        }

        // Get data success
        tutorList.clear();
        courseList.clear();
        tutorList.addAll((ArrayList<User>) bundle.getSerializable("param1"));
        courseList.addAll((ArrayList<Course>) bundle.getSerializable("param2"));

        updateResultViewVisibility();
        courseListAdapter.notifyDataSetChanged();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_load_more_course_search_results:
                break;
            case R.id.button_load_more_tutor_search_results:
                break;
        }
    }
}
