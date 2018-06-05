package com.eways.elearning.Adapter.Home;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eways.elearning.Model.Course;
import com.eways.elearning.Model.FavoriteSubjectWithCourses;
import com.eways.elearning.R;
import com.eways.elearning.Utils.params.GlobalParams;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ADMIN on 5/29/2018.
 */

public class SubjectAdapter extends RecyclerView.Adapter<SubjectVHolder> {
    int res;
    ArrayList<FavoriteSubjectWithCourses> courseHomes;

    public SubjectAdapter(int res, ArrayList<FavoriteSubjectWithCourses> courseHomes) {
        this.res = res;
        this.courseHomes = courseHomes;
    }

    @Override
    public SubjectVHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_home,parent,false);
        return new SubjectVHolder(view);
    }

    @Override
    public void onBindViewHolder(SubjectVHolder holder, int position) {
        final FavoriteSubjectWithCourses courseHome = courseHomes.get(position);

        holder.tvTitle.setText(courseHome.getSubject());
        holder.tvMore.setText("Thêm");

        SubjectItemAdapter subjectItemAdapter = new SubjectItemAdapter(R.layout.item_home_detail, courseHome.getListCourses());
        holder.rcSubject.setHasFixedSize(true);
        holder.rcSubject.setLayoutManager(new LinearLayoutManager(GlobalParams.getInstance(), LinearLayoutManager.HORIZONTAL, true));
        holder.rcSubject.setAdapter(subjectItemAdapter);
    }

    @Override
    public int getItemCount() {
        return courseHomes.size();
    }
}
