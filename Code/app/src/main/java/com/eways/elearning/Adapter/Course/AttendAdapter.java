package com.eways.elearning.Adapter.Course;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eways.elearning.Model.Attend;
import com.eways.elearning.R;

import java.util.ArrayList;

/**
 * Created by ADMIN on 7/18/2018.
 */

public class AttendAdapter extends RecyclerView.Adapter<AttendVHolder> {
    ArrayList<Attend> mListAttend;
    Activity mActivity;

    public AttendAdapter(ArrayList<Attend> mListAttend, Activity mActivity) {
        this.mListAttend = mListAttend;
        this.mActivity = mActivity;
    }

    @Override
    public AttendVHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_attends,parent,false);
        return new AttendVHolder(view);
    }

    @Override
    public void onBindViewHolder(AttendVHolder holder, int position) {

        final Attend mAttend = mListAttend.get(position);
        holder.tvName.setText(mAttend.getFirstName()+ " " +mAttend.getLastName());
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        holder.btnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        holder.tvDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return mListAttend.size();
    }
}
