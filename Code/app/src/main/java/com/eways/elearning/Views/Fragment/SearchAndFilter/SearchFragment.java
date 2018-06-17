package com.eways.elearning.Views.Fragment.SearchAndFilter;


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
import com.eways.elearning.Utils.Handler.FragmentHandler;
import com.eways.elearning.Utils.SupportKeys;
import com.eways.elearning.Views.Fragment.Course.CourseListFragment;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment implements DataCallBack, View.OnClickListener {

    /**
     * VIEWS
     */
    LinearLayout tutorResultView, courseResultView; // These views will be hidden if its result is empty
    RecyclerView rvTutorResults, rvCourseResults;
    View divider;

    /**
     * MODELS
     */
    private FragmentHandler fragmentHandler;
    private SearchPresenter searchPresenter;
    private UserListAdapter userListAdapter;
    private CourseListAdapter courseListAdapter;
    private ArrayList<User> tutorList = new ArrayList();
    private ArrayList<Course> courseList = new ArrayList();

    /**
     * Params
     */
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

    /** ----- LIFECYCLE ----- */

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        //set button back
        //((HomeActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        super.onCreate(savedInstanceState);

        fragmentHandler = new FragmentHandler(getContext(), R.id.home_content_view);
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
        courseResultView = root.findViewById(R.id.courses_search_result_view);
        rvTutorResults = root.findViewById(R.id.tutor_search_results_recycler_view);
        rvCourseResults = root.findViewById(R.id.course_search_results_recycler_view);
        divider = root.findViewById(R.id.divider_search_result);

        root.findViewById(R.id.button_load_more_course_search_results).setOnClickListener(this);
        root.findViewById(R.id.button_load_more_tutor_search_results).setOnClickListener(this);

        // Setup results list
        setupResultsListView();
        updateResultViewVisibility();

        return root;
    }

    /** CONFIG */
    private void setupResultsListView() {
        userListAdapter = new UserListAdapter(getContext(), tutorList);
        rvTutorResults.setLayoutManager(new GridLayoutManager(getContext(), 1));
        rvTutorResults.hasFixedSize();
        rvTutorResults.setNestedScrollingEnabled(false);
        rvTutorResults.setAdapter(userListAdapter);

        courseListAdapter = new CourseListAdapter(getContext(), courseList, false);
        rvCourseResults.setLayoutManager(new GridLayoutManager(getContext(), 1));
        rvCourseResults.hasFixedSize();
        rvCourseResults.setNestedScrollingEnabled(false);
        rvCourseResults.setAdapter(courseListAdapter);
    }

    /**
     * Update result list visibility depend on their data
     *
     * @Gone when they don't have data
     * @Visible when they have data
     */
    private void updateResultViewVisibility() {

        courseResultView.setVisibility(courseList.size() > 0 ? View.VISIBLE : View.GONE);
        tutorResultView.setVisibility(tutorList.size() > 0 ? View.VISIBLE : View.GONE);

        if (courseList.size() != 0 && tutorList.size() != 0) {
            divider.setVisibility(View.VISIBLE);
        } else {
            if (courseList.size() == 0) {
                divider.setVisibility(View.GONE);
                return;
            }
            if (tutorList.size() == 0) {
                divider.setVisibility(View.GONE);
                return;
            }
        }

    }

    /**
     * HANDLE DATA FROM SEVER
     */
    @Override
    public void dataCallBack(int resultCode, @Nullable Bundle bundle) {
        // Handle errors
        if (resultCode == SupportKeys.FAILED_CODE) {
            Log.d(getClass().getName(), "Search failed!");
            return;
        }

        // Get data success
        tutorList.clear();
        tutorList.addAll((ArrayList<User>) bundle.getSerializable("param1"));

        courseList.clear();
        courseList.addAll((ArrayList<Course>) bundle.getSerializable("param2"));

        courseListAdapter.notifyDataSetChanged();
        userListAdapter.notifyDataSetChanged();
        updateResultViewVisibility();

    }

    /** ACTIONS */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_load_more_tutor_search_results:
                break;
            case R.id.button_load_more_course_search_results:
                fragmentHandler.changeFragment(CourseListFragment.newInstance(courseList.get(0).getIdSubject()), SupportKeys.COURSE_LIST_FRAGMENT_TAG, R.anim.slide_from_left, R.anim.slide_out_top);
                break;
        }
    }

}
