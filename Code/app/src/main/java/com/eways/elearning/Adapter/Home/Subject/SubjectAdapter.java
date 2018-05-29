package com.eways.elearning.Adapter.Home.Subject;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eways.elearning.Adapter.Home.HomeVHolder;
import com.eways.elearning.Model.CourseHome;
import com.eways.elearning.R;

import java.util.ArrayList;

/**
 * Created by ADMIN on 5/29/2018.
 */

public class SubjectAdapter extends RecyclerView.Adapter<SubjectVHolder> {
    int res;
    ArrayList<CourseHome> courseHomes;

    public SubjectAdapter(int res, ArrayList<CourseHome> courseHomes) {
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

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
