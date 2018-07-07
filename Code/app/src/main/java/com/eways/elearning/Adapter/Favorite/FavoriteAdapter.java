package com.eways.elearning.Adapter.Favorite;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eways.elearning.Interfaces.OnItemClickListener;
import com.eways.elearning.Model.Subject.Favorite;
import com.eways.elearning.Model.Subject.Subject;
import com.eways.elearning.R;
import com.eways.elearning.Utils.Handler.ImageHandler;
import com.eways.elearning.Utils.params.GlobalParams;

import java.util.ArrayList;

/**
 * Created by ADMIN on 5/17/2018.
 */

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteViewHolder> {
    private ArrayList<Subject> listFavorite ;
    private int res;
    private ImageHandler imageHandler;
    private OnItemClickListener onItemClickListener;

    public FavoriteAdapter(ArrayList<Subject> listFavorite, int res, ImageHandler imageHandler, OnItemClickListener onItemClickListener) {
        this.listFavorite = listFavorite;
        this.res = res;
        this.onItemClickListener = onItemClickListener;
        this.imageHandler = imageHandler;
    }

    @Override
    public FavoriteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_favorite,parent,false);
        return new FavoriteViewHolder(view);

    }

    @Override
    public void onBindViewHolder(final FavoriteViewHolder holder, int position) {
        final Subject favorite = listFavorite.get(position);

//        int drawableResId = GlobalParams.getInstance().get_resId_by_name(favorite.getImage(), "drawable");
//        holder.image.setImageResource(drawableResId);
        imageHandler.loadImageSquare(favorite.getImg(), holder.image);
        holder.text.setText(favorite.getSubjectName());

        final Bundle dataBundle = new Bundle();

        holder.touch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.selected.isChecked()){
                    holder.selected.setChecked(false);
                    dataBundle.putBoolean("Selected", false);
                    dataBundle.putString("FavoriteId", favorite.getIdSubject());
                    onItemClickListener.onItemClick(dataBundle);
                }else {
                    holder.selected.setChecked(true);
                    dataBundle.putBoolean("Selected", true);
                    dataBundle.putString("FavoriteId", favorite.getIdSubject());
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
