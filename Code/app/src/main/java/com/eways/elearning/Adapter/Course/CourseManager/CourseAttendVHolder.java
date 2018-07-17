package com.eways.elearning.Adapter.Course.CourseManager;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.eways.elearning.R;

/**
 * Created by ADMIN on 6/17/2018.
 */

public class CourseAttendVHolder extends RecyclerView.ViewHolder {
    TextView tvName, tvSubject, tvStartDate, tvEndDate;
    ImageView ivAvarta;
    View btnCancel;


    public CourseAttendVHolder(View itemView) {
        super(itemView);

        tvName = itemView.findViewById(R.id.tutor_name);
        tvSubject = itemView.findViewById(R.id.subject_name);
        tvStartDate = itemView.findViewById(R.id.start_date);
        tvEndDate = itemView.findViewById(R.id.end_date);

        ivAvarta = itemView.findViewById(R.id.img_avarta);

        btnCancel = itemView.findViewById(R.id.btn_cancel);

    }
}
