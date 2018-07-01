package com.eways.elearning.Adapter.Home;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eways.elearning.Model.Account.User;
import com.eways.elearning.R;
import com.eways.elearning.Utils.Handler.ImageHandler;
import com.eways.elearning.Utils.params.GlobalParams;
import com.eways.elearning.Views.Activity.InfoUserViewerActivity;

import java.util.ArrayList;

/**
 * Created by ADMIN on 5/28/2018.
 */

public class TopTutorAdapter extends RecyclerView.Adapter<HomeVHolder> {
    public static final String ARG_INFO_VIEWER = "info_viewer";

    Activity activity;
    int res;
    ArrayList<User> tutors;

    ImageHandler imageHandler;

    public TopTutorAdapter(Activity activity, int res, ArrayList<User> tutors) {
        this.activity = activity;
        this.res = res;
        this.tutors = tutors;

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
        final User tutor = tutors.get(position);
        //load image
        imageHandler.loadImageRound(tutor.getAvatar(), holder.ivHomeDetail);
        holder.tvTitle.setText(tutor.getFirstName() + " " + tutor.getLastName());

        holder.ivHomeDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(activity, InfoUserViewerActivity.class);
                i.putExtra(ARG_INFO_VIEWER ,GlobalParams.getInstance().getGSon().toJson(tutor));
                activity.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return tutors.size();
    }
}
