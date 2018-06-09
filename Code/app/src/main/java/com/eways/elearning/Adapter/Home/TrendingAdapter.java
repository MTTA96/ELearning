package com.eways.elearning.Adapter.Home;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eways.elearning.Model.Subject;
import com.eways.elearning.R;
import com.eways.elearning.Utils.Handler.ImageHandler;
import com.eways.elearning.Utils.params.GlobalParams;

import java.util.ArrayList;

/**
 * Created by ADMIN on 5/28/2018.
 */

public class TrendingAdapter extends RecyclerView.Adapter<HomeVHolder> {
    int res;
    ArrayList<Subject> trendings;

    ImageHandler imageHandler;

    public TrendingAdapter(int res, ArrayList<Subject> trendings) {
        this.res = res;
        this.trendings = trendings;

        imageHandler = new ImageHandler(GlobalParams.getInstance());
    }

    @Override
    public HomeVHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_home_detail,parent,false);
        return new HomeVHolder(view);
    }

    @Override
    public void onBindViewHolder(HomeVHolder holder, int position) {
        final Subject subject = trendings.get(position);

        imageHandler.loadImageRound(subject.getImg(), holder.ivHomeDetail);
        holder.tvTitle.setText(subject.getSubjectName());
    }

    @Override
    public int getItemCount() {
        return trendings.size();
    }
}
