package com.eways.elearning.Adapter.Home.Subject;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.eways.elearning.R;

/**
 * Created by ADMIN on 5/29/2018.
 */

public class SubjectVHolder extends RecyclerView.ViewHolder{
    TextView tvMore, tvTitle;
    RecyclerView rcSubject;
    public SubjectVHolder(View itemView) {
        super(itemView);

        tvTitle = itemView.findViewById(R.id.title_subject);
        tvMore = itemView.findViewById(R.id.tv_more);
        rcSubject = itemView.findViewById(R.id.rc_item_subject);
    }
}
