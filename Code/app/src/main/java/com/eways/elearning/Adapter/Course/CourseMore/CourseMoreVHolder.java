package com.eways.elearning.Adapter.Course.CourseMore;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.eways.elearning.R;

/**
 * Created by ADMIN on 6/4/2018.
 */

public class CourseMoreVHolder extends RecyclerView.ViewHolder {
    ImageView ivCourse;
    TextView tvCourseName, tvSubjectName, tvSession, tvDetail, tvTuition;


    public CourseMoreVHolder(View itemView) {
        super(itemView);

        ivCourse = itemView.findViewById(R.id.iv_course);
        tvCourseName = itemView.findViewById(R.id.tv_course_name);
        tvSubjectName = itemView.findViewById(R.id.tv_subject_name);
        tvSession = itemView.findViewById(R.id.tv_session);
        tvDetail = itemView.findViewById(R.id.tv_detail_course);
        tvTuition = itemView.findViewById(R.id.tv_tuition);
    }
}
