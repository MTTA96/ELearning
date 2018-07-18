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
import com.eways.elearning.Views.Activity.AttendancesManagerActivity;
import com.eways.elearning.Views.Activity.RequestManagerActivity;

import java.util.ArrayList;
import java.util.Calendar;

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
                if (!CheckTimeStart(mCourse.getTimeStart())){
                    ActivityUtils.ChangeActivity(mActivity, RequestManagerActivity.class);
                }else {
                    ActivityUtils.ChangeActivity(mActivity, AttendancesManagerActivity.class);

                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mListCourse.size();
    }

    private boolean CheckTimeStart(String time){
        String[] timeArray = time.split("-");
        int[] timeInt = new int[3];
        for (int i = 0; i < timeArray.length; i++){
            timeInt[i] = Integer.parseInt(timeArray[i]);
        }

        int year = Calendar.getInstance().get(Calendar.YEAR);
        int month = Calendar.getInstance().get(Calendar.MONTH);
        int date = Calendar.getInstance().get(Calendar.DATE);

        if (timeInt[2] >= year){
            if (timeInt[1] > month){
                if (timeInt[0] > date){
                    return true;
                }
            }
        }
        return false;
    }
}
