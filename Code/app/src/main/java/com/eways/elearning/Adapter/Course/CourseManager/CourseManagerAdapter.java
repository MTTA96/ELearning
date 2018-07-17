package com.eways.elearning.Adapter.Course.CourseManager;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eways.elearning.Model.Course.Course;
import com.eways.elearning.R;
import com.eways.elearning.Utils.ActivityUtils;
import com.eways.elearning.Utils.Handler.ImageHandler;
import com.eways.elearning.Views.Activity.RequestManagerActivity;

import java.util.ArrayList;

/**
 * Created by ADMIN on 7/17/2018.
 */

public class CourseManagerAdapter extends RecyclerView.Adapter<CourseManagerVHolder> {
    ArrayList<Course> mListCourse;
    Activity mActivity;

    ImageHandler mImageHandle;

    public CourseManagerAdapter(ArrayList<Course> mListCourse, Activity mActivity) {
        this.mListCourse = mListCourse;
        this.mActivity = mActivity;

        mImageHandle = new ImageHandler(mActivity);
    }

    @Override
    public CourseManagerVHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_course,parent,false);
        return new CourseManagerVHolder(view);
    }

    @Override
    public void onBindViewHolder(CourseManagerVHolder holder, int position) {
        final Course mCourse = mListCourse.get(position);

        holder.tvSubject.setText(mCourse.getSubjectName());
        holder.tvEndDate.setText(mCourse.getTimeEnd());
        holder.tvStartDate.setText(mCourse.getTimeStart());

        holder.btnRequestManager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityUtils.ChangeActivity(mActivity, RequestManagerActivity.class);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mListCourse.size();
    }
}
