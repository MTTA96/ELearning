package com.eways.elearning.Adapter.Home;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eways.elearning.Model.Account.User;
import com.eways.elearning.R;
import com.eways.elearning.Utils.Handler.ImageHandler;
import com.eways.elearning.Utils.params.GlobalParams;

import java.util.ArrayList;

/**
 * Created by ADMIN on 5/28/2018.
 */

public class TopTutorAdapter extends RecyclerView.Adapter<HomeVHolder> {

    int res;
    ArrayList<User> tutors;

    ImageHandler imageHandler;

    public TopTutorAdapter(int res, ArrayList<User> tutors) {
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

    }

    @Override
    public int getItemCount() {
        return tutors.size();
    }
}
