package com.eways.elearning.Adapter.Course.CourseManager;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eways.elearning.Adapter.Course.CourseMore.CourseMoreVHolder;
import com.eways.elearning.Model.Course.Course;
import com.eways.elearning.R;
import com.eways.elearning.Utils.Handler.ImageHandler;
import com.eways.elearning.Utils.params.GlobalParams;
import com.eways.elearning.Views.Activity.CourseManager;

import java.util.ArrayList;

/**
 * Created by ADMIN on 6/17/2018.
 */

public class CourseManagerAdapter extends RecyclerView.Adapter<CourseManagerVHolder> {
    Activity activity;
    ArrayList<Course> mCourseManagers;

    ImageHandler imageHandler;

    public CourseManagerAdapter(ArrayList<Course> mCourseManagers, Activity activity) {
        this.mCourseManagers = mCourseManagers;
        this.activity = activity;

        imageHandler = new ImageHandler(activity);
    }

    @Override
    public CourseManagerVHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_course_manager,parent,false);
        return new CourseManagerVHolder(view);
    }

    @Override
    public void onBindViewHolder(CourseManagerVHolder holder, int position) {
        final Course mCourseManager = mCourseManagers.get(position);

        holder.tvTuition.setText(mCourseManager.getTuition());
        holder.tvNumberSession.setText(mCourseManager.getNumberOfSessions());
        holder.tvSchedule.setText(mCourseManager.getSchedule());
        holder.tvAddress.setText(mCourseManager.getAddress());
        holder.tvTime.setText(mCourseManager.getTimeStart() + " - " + mCourseManager.getTimeEnd());
        holder.tvNote.setText(mCourseManager.getDescription());

        imageHandler.loadImageRound(mCourseManager.getAvatar(), holder.ivAvarta);

        if (Integer.parseInt(mCourseManager.getStatus()) == 0 ){
            holder.status.setBackgroundResource(R.drawable.no_active_shape);
        }else {
            holder.status.setBackgroundResource(R.drawable.active_shape);
        }

        holder.btnRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //request
            }
        });

        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //edit
            }
        });

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //delete
            }
        });


    }

    @Override
    public int getItemCount() {
        return mCourseManagers.size();
    }
}
