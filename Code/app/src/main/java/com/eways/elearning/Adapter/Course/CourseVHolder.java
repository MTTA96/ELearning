package com.eways.elearning.Adapter.Course;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.eways.elearning.R;


/**
 * Created by ADMIN on 5/19/2018.
 */

public class CourseVHolder extends RecyclerView.ViewHolder {
    ImageView avarta, btnGoDetail;
    TextView studentName, subject, status;
    RelativeLayout bgStatus;

    public CourseVHolder(View itemView) {
        super(itemView);

        avarta = itemView.findViewById(R.id.avarta);
        btnGoDetail = itemView.findViewById(R.id.to_detail);
        studentName = itemView.findViewById(R.id.student_name);
        subject = itemView.findViewById(R.id.subject);
        status = itemView.findViewById(R.id.text_status);
        bgStatus = itemView.findViewById(R.id.bg_status);
    }
}
