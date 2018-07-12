package com.eways.elearning.Adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eways.elearning.R;
import com.eways.elearning.TagVHolder;

import java.util.ArrayList;

/**
 * Created by ADMIN on 7/10/2018.
 */

public class TagAdapter extends RecyclerView.Adapter<TagVHolder> {
    Activity mActivity;
    ArrayList<String> mList;

    public TagAdapter(Activity mActivity, ArrayList<String> mList) {
        this.mActivity = mActivity;
        this.mList = mList;
    }

    @Override
    public TagVHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_tag,parent,false);
        return new TagVHolder(view);
    }

    @Override
    public void onBindViewHolder(TagVHolder holder, int position) {
        final String mText = mList.get(position);

        holder.tvText.setText(mText);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}
