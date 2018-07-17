package com.eways.elearning.Adapter.Request;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.eways.elearning.R;

/**
 * Created by ADMIN on 6/17/2018.
 */

public class RequestVHolder extends RecyclerView.ViewHolder{
    Button btnConfirm, btnCancel;
    ImageView avarta;
    TextView tutorName, tvMore;

    public RequestVHolder(View itemView) {
        super(itemView);

        btnCancel = itemView.findViewById(R.id.btn_cancel);
        btnConfirm = itemView.findViewById(R.id.btn_confirm);
        avarta = itemView.findViewById(R.id.iv_avarta);
        tutorName = itemView.findViewById(R.id.student_name);
        tvMore = itemView.findViewById(R.id.tv_more);
    }
}
