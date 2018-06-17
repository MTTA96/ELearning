package com.eways.elearning.Adapter.Course.CourseMore;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eways.elearning.Model.Course.Course;
import com.eways.elearning.R;
import com.eways.elearning.Utils.Handler.ImageHandler;
import com.eways.elearning.Utils.params.GlobalParams;

import java.util.ArrayList;

/**
 * Created by ADMIN on 6/4/2018.
 */

public class CourseMoreAdapter extends RecyclerView.Adapter<CourseMoreVHolder> {
    int res;
    ArrayList<Course> mCourseList;

    ImageHandler imageHandler;

    public CourseMoreAdapter(int res, ArrayList<Course> mCourseList) {
        this.res = res;
        this.mCourseList = mCourseList;

        imageHandler = new ImageHandler(GlobalParams.getInstance());
    }

    @Override
    public CourseMoreVHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_course_more,parent,false);
        return new CourseMoreVHolder(view);
    }

    @Override
    public void onBindViewHolder(CourseMoreVHolder holder, int position) {
        final Course course = mCourseList.get(position);

        imageHandler.loadImageSquare(course.getAvatar(), holder.ivCourse);
        holder.tvCourseName.setText(course.getCourseName());
        holder.tvSubjectName.setText(course.getSubjectName());
        holder.tvTuition.setText(course.getTuition());
        holder.tvSession.setText(course.getTimePerSession());
    }

    @Override
    public int getItemCount() {
        return mCourseList.size();
    }
}
