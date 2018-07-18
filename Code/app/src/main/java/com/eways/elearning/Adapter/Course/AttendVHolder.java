package com.eways.elearning.Adapter.Course;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.eways.elearning.R;

/**
 * Created by ADMIN on 7/18/2018.
 */

public class AttendVHolder extends RecyclerView.ViewHolder {
    ImageView mAvarta;
    TextView tvName, tvDetail;
    Button btnCheck, btnDelete;

    public AttendVHolder(View itemView) {
        super(itemView);

        mAvarta = itemView.findViewById(R.id.img_avarta);
        tvDetail = itemView.findViewById(R.id.detail);
        tvName = itemView.findViewById(R.id.name);
        btnCheck = itemView.findViewById(R.id.btn_check);
        btnDelete = itemView.findViewById(R.id.btn_delete);
    }
}
