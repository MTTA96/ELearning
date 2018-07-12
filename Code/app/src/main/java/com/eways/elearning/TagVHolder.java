package com.eways.elearning;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by ADMIN on 7/10/2018.
 */

public class TagVHolder extends RecyclerView.ViewHolder {
    public TextView tvText;

    public TagVHolder(View itemView) {
        super(itemView);

        tvText = itemView.findViewById(R.id.tv_text);
    }
}
