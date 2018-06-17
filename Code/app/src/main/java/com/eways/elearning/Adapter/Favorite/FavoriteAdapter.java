package com.eways.elearning.Adapter.Favorite;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eways.elearning.Interfaces.OnItemClickListener;
import com.eways.elearning.Model.Subject.Favorite;
import com.eways.elearning.R;
import com.eways.elearning.Utils.params.GlobalParams;

import java.util.ArrayList;

/**
 * Created by ADMIN on 5/17/2018.
 */

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteViewHolder> {
    private ArrayList<Favorite> listFavorite ;
    private int res;
    private OnItemClickListener onItemClickListener;

    public FavoriteAdapter(ArrayList<Favorite> listFavorite, int res, OnItemClickListener onItemClickListener) {
        this.listFavorite = listFavorite;
        this.res = res;
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public FavoriteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_favorite,parent,false);
        return new FavoriteViewHolder(view);

    }

    @Override
    public void onBindViewHolder(final FavoriteViewHolder holder, int position) {
        final Favorite favorite = listFavorite.get(position);

        int drawableResId = GlobalParams.getInstance().get_resId_by_name(favorite.getImage(), "drawable");
        holder.image.setImageResource(drawableResId);
        holder.text.setText(favorite.getText());

        final Bundle dataBundle = new Bundle();

        holder.touch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.selected.isChecked()){
                    holder.selected.setChecked(false);
                    dataBundle.putBoolean("Selected", false);
                    dataBundle.putString("FavoriteId", favorite.getId());
                    onItemClickListener.onItemClick(dataBundle);
                }else {
                    holder.selected.setChecked(true);
                    dataBundle.putBoolean("Selected", true);
                    dataBundle.putString("FavoriteId", favorite.getId());
                    onItemClickListener.onItemClick(dataBundle);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return listFavorite.size();
    }
}
