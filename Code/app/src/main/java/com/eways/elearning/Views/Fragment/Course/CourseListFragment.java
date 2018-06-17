package com.eways.elearning.Views.Fragment.Course;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eways.elearning.Adapter.Course.CourseListAdapter;
import com.eways.elearning.Adapter.Course.CourseMore.CourseMoreAdapter;
import com.eways.elearning.Interfaces.DataCallback.Subject.CourseCallBack;
import com.eways.elearning.Model.Course.Course;
import com.eways.elearning.Presenter.CourseListPresenter;
import com.eways.elearning.R;
import com.eways.elearning.Utils.SupportKeys;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class CourseListFragment extends Fragment implements CourseCallBack {

    /** VIEWS */
    RecyclerView rcListCourse;

    private CourseListPresenter courseListPresenter;
    private ArrayList<Course> courseList = new ArrayList<>();
    private CourseListAdapter courseListAdapter;

    public static String param1 = "param1";

    public CourseListFragment() {
        // Required empty public constructor
    }

    public static CourseListFragment newInstance(String subjectId) {

        Bundle args = new Bundle();
        args.putString(param1, subjectId);
        CourseListFragment fragment = new CourseListFragment();
        fragment.setArguments(args);
        return fragment;

    }

    /** ----- LIFECYCLE ----- */

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        courseListPresenter = new CourseListPresenter(getContext());
        courseListPresenter.getCourseList(getArguments().getString(param1), this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_course_list, container, false);
        declareViews(root);
        handleViews();
        setUpList();
        return root;
    }


    /** ----- CONFIG ----- */

    public void declareViews(View root){
        rcListCourse = root.findViewById(R.id.rcv_list_course);
    }

    public void handleViews(){
        courseList = new ArrayList<>();
    }

    public void setUpList(){
        courseListAdapter = new CourseListAdapter(getContext(), courseList, true);
        rcListCourse.setHasFixedSize(true);
        rcListCourse.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, true));
        rcListCourse.setAdapter(courseListAdapter);
    }

    /** ----- DATA FROM SERVER -----*/

    @Override
    public void courseCallBack(int errorCode, ArrayList result) {
        if (errorCode == SupportKeys.FAILED_CODE) {
            return;
        }

        this.courseList.clear();
        this.courseList.addAll(result);
        this.courseListAdapter.notifyDataSetChanged();

    }
}
