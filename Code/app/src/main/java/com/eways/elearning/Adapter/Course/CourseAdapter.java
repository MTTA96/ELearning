package com.eways.elearning.Adapter.Course;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.eways.elearning.Model.Course.Course;
import com.eways.elearning.Presenter.Authentication.UserPresenter;
import com.eways.elearning.R;

import java.util.ArrayList;

/**
 * Created by ADMIN on 5/19/2018.
 */

public class CourseAdapter extends RecyclerView.Adapter<CourseVHolder> {
    ArrayList<Course> listCourse ;
    int res;

    public CourseAdapter(Context context, ArrayList<Course> listCourse, int res) {
        this.listCourse = listCourse;
        this.res = res;
    }

    @Override
    public CourseVHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_course_attend,parent,false);
        return new CourseVHolder(view);
    }

    @Override
    public void onBindViewHolder(CourseVHolder holder, int position) {

        holder.studentName.setText(listCourse.get(position).getFirstName().toString() + listCourse.get(position).getLastName().toString());
        holder.subject.setText(listCourse.get(position).getSubjectName().toString());

        //check time and set status
        //do it later

    }

    @Override
    public int getItemCount() {
        return listCourse.size();
    }

}
