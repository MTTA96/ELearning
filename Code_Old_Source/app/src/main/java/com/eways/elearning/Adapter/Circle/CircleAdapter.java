package com.eways.elearning.Adapter.Circle;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eways.elearning.Model.Circle;
import com.eways.elearning.R;

import java.util.ArrayList;

/**
 * Created by ADMIN on 5/5/2018.
 */

public class CircleAdapter extends RecyclerView.Adapter<CircleViewHolder>  {

    private Activity activity;
    private ArrayList<Circle> listCircle ;

    private interface ChangePos{
        public void setCircle(int pos);
    }

    ChangePos changePos;

    public CircleAdapter(Activity activity, ArrayList<Circle> listCircle) {
        this.activity = activity;
        this.listCircle = listCircle;
    }



    @Override
    public CircleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_circle,parent,false);
        return new CircleViewHolder(view);
    }
    @Override
    public void onBindViewHolder(final CircleViewHolder holder, final int position) {
        final Circle circle = listCircle.get(position);

        holder.circle.setBackgroundResource(circle.getNotActive());

        changePos = new ChangePos() {
            @Override
            public void setCircle(int pos) {
                if(pos == position){
                    holder.circle.setBackgroundResource(circle.getActive());
                }
            }
        };
    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
