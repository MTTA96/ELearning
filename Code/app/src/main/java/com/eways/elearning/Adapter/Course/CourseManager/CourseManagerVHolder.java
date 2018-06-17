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

public class CourseManagerVHolder extends RecyclerView.ViewHolder {
    ImageView ivAvarta;
    TextView tvSubject, tvTime, tvSchedule, tvNumberSession, tvTuition, tvNote, tvAddress;
    Button btnEdit, btnDelete, btnRequest;
    View status;

    public CourseManagerVHolder(View itemView) {
        super(itemView);

        ivAvarta = itemView.findViewById(R.id.iv_avarta);
        tvSubject = itemView.findViewById(R.id.tv_subject_name);
        tvTime = itemView.findViewById(R.id.tv_time);
        tvSchedule = itemView.findViewById(R.id.tv_schedule);
        tvNumberSession = itemView.findViewById(R.id.tv_number_session);
        tvTuition = itemView.findViewById(R.id.tv_tuition);
        tvNote = itemView.findViewById(R.id.tv_note);
        tvAddress = itemView.findViewById(R.id.tv_address);
        btnDelete = itemView.findViewById(R.id.btn_delete);
        btnEdit = itemView.findViewById(R.id.btn_edit);
        btnRequest = itemView.findViewById(R.id.btn_manager);
        status = itemView.findViewById(R.id.status);
    }
}
