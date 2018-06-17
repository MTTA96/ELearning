package com.eways.elearning.Views.Fragment.Course;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eways.elearning.Adapter.Course.CourseMore.CourseMoreAdapter;
import com.eways.elearning.Model.Course.Course;
import com.eways.elearning.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentCourseMore extends Fragment {
    /** VIEWS */
    RecyclerView rcListCourse;
    ArrayList<Course> courses;


    public FragmentCourseMore() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_course_more, container, false);
        declare_views(root);
        return root;
    }


    public void declare_views(View root){
        rcListCourse = root.findViewById(R.id.rcv_list_course);
    }

    public void handle_views(){
        courses = new ArrayList<>();
    }

    public void SetUpList(){
        CourseMoreAdapter courseMoreAdapter = new CourseMoreAdapter(R.layout.item_course_more, courses);
        rcListCourse.setHasFixedSize(true);
        rcListCourse.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, true));
        rcListCourse.setAdapter(courseMoreAdapter);
    }
}
