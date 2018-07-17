package com.eways.elearning.Adapter.Course.CourseManager;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eways.elearning.Model.RequestSend;
import com.eways.elearning.R;
import com.eways.elearning.Utils.Handler.ImageHandler;

import java.util.ArrayList;

/**
 * Created by ADMIN on 6/17/2018.
 */

public class CourseAttendAdapter extends RecyclerView.Adapter<CourseAttendVHolder> {
    Activity activity;
    ArrayList<RequestSend> mCourseManagers;

    ImageHandler imageHandler;

    public CourseAttendAdapter(Activity activity, ArrayList<RequestSend> mCourseManagers) {
        this.activity = activity;
        this.mCourseManagers = mCourseManagers;

        imageHandler = new ImageHandler(activity);
    }

    @Override
    public CourseAttendVHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_course_attend,parent,false);
        return new CourseAttendVHolder(view);
    }

    @Override
    public void onBindViewHolder(CourseAttendVHolder holder, int position) {
        final RequestSend mCourseManager = mCourseManagers.get(position);

        holder.tvName.setText(mCourseManager.getFirstNameReciever() + " " + mCourseManager.getLastNameReciever());
        holder.tvSubject.setText(mCourseManager.getSubjectName());
        holder.tvStartDate.setText(mCourseManager.getTimeStart());
        holder.tvEndDate.setText(mCourseManager.getTimeEnd());

        imageHandler.loadImageSquare(mCourseManager.getAvatar(), holder.ivAvarta);
    }

    @Override
    public int getItemCount() {
        return mCourseManagers.size();
    }
}
