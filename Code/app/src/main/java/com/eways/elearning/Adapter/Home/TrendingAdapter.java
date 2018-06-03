package com.eways.elearning.Adapter.Home;

import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eways.elearning.Adapter.Search.SearchVHolder;
import com.eways.elearning.Model.Trending;
import com.eways.elearning.R;
import com.eways.elearning.Utils.Handler.ImageHandler;
import com.eways.elearning.Utils.params.GlobalParams;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by ADMIN on 5/28/2018.
 */

public class TrendingAdapter extends RecyclerView.Adapter<HomeVHolder> {
    int res;
    ArrayList<Trending> trendings;

    ImageHandler imageHandler;

    public TrendingAdapter(int res, ArrayList<Trending> trendings) {
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
        final Trending trending = trendings.get(position);

        imageHandler.loadImageRound(trending.getImg(), holder.ivHomeDetail);
        holder.tvTitle.setText(trending.getSubjectName());
    }

    @Override
    public int getItemCount() {
        return trendings.size();
    }
}
