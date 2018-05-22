package com.eways.elearning.Adapter.Search;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.eways.elearning.R;

/**
 * Created by ADMIN on 5/20/2018.
 */

public class SearchVHolder extends RecyclerView.ViewHolder {
    ImageView avarta;
    TextView nameStudent, subject;

    public SearchVHolder(View itemView) {
        super(itemView);

        avarta = itemView.findViewById(R.id.avarta);
        nameStudent = itemView.findViewById(R.id.student_name);
        subject = itemView.findViewById(R.id.subject);

    }
}
