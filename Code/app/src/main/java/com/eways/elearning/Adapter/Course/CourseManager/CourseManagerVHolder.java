package com.eways.elearning.Adapter.Course.CourseManager;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.eways.elearning.R;

/**
 * Created by ADMIN on 7/17/2018.
 */

public class CourseManagerVHolder extends RecyclerView.ViewHolder {
    TextView tvStartDate, tvEndDate, tvSubject;
    View btnRequestManager;

    public CourseManagerVHolder(View itemView) {
        super(itemView);

        tvStartDate = itemView.findViewById(R.id.start_date);
        tvEndDate = itemView.findViewById(R.id.end_date);
        tvSubject = itemView.findViewById(R.id.tv_subject);
        btnRequestManager = itemView.findViewById(R.id.btn_manager_request);
    }
}
