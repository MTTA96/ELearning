package com.eways.elearning.Adapter.Course.CourseManager;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.eways.elearning.R;

/**
 * Created by ADMIN on 7/17/2018.
 */

public class RequestManagerVHolder extends RecyclerView.ViewHolder {
    TextView tvStudent, tvInfoMore;

    View btnAccept, btnCancel;

    public RequestManagerVHolder(View itemView) {
        super(itemView);

        btnAccept = itemView.findViewById(R.id.btn_accept);
        btnCancel = itemView.findViewById(R.id.btn_cancel);

        tvInfoMore = itemView.findViewById(R.id.info_more);
        tvStudent = itemView.findViewById(R.id.student_name);
    }
}
