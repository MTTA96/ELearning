package com.eways.elearning.Adapter.Course.CourseHome.Subject;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.eways.elearning.R;

/**
 * Created by ADMIN on 5/22/2018.
 */

public class SubjectVHolder extends RecyclerView.ViewHolder {
    TextView tvMore, tvTitle;
    RecyclerView rcCourse;

    public SubjectVHolder(View itemView) {
        super(itemView);

        tvMore = itemView.findViewById(R.id.tv_more);
        tvTitle = itemView.findViewById(R.id.title_subject);
        rcCourse = itemView.findViewById(R.id.rc_item_subject);
    }
}
