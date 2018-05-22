package com.eways.elearning.Adapter.Favorite;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eways.elearning.Model.Favorite;
import com.eways.elearning.R;
import com.eways.elearning.Utils.params.GlobalParams;

import java.util.ArrayList;

/**
 * Created by ADMIN on 5/17/2018.
 */

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteViewHolder> {
    ArrayList<Favorite> listFavorite ;
    int res;

    public FavoriteAdapter(ArrayList<Favorite> listFavorite, int res) {
        this.listFavorite = listFavorite;
        this.res = res;
    }

    @Override
    public FavoriteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_favorite,parent,false);
        return new FavoriteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final FavoriteViewHolder holder, int position) {
        Favorite favorite = listFavorite.get(position);

        int drawableResId = GlobalParams.getInstance().get_resId_by_name(favorite.getImage(), "drawable");
        holder.image.setImageResource(drawableResId);
        holder.text.setText(favorite.getText());

        holder.touch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.selected.isChecked()){
                    holder.selected.setChecked(false);
                }else {
                    holder.selected.setChecked(true);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return listFavorite.size();
    }
}
