package com.eways.elearning.Adapter;

import android.app.Activity;
import android.net.Uri;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;

import com.eways.elearning.Model.Image;
import com.eways.elearning.Model.SubjectSpec;
import com.eways.elearning.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ADMIN on 7/6/2018.
 */

public class ImageSpecAdapter extends RecyclerView.Adapter<ImageSpecVHolder> {
    Activity mActivity;
    List<SubjectSpec> mList;

    boolean mOpen;

    public ImageSpecAdapter(Activity mActivity, List<SubjectSpec> mList) {
        this.mActivity = mActivity;
        this.mList = mList;
    }

    @Override
    public ImageSpecVHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_subject_spec,parent,false);

        mOpen = true;
        return new ImageSpecVHolder(view);
    }

    @Override
    public void onBindViewHolder(final ImageSpecVHolder holder, int position) {
        final  SubjectSpec mSubjectSPec = mList.get(position);
//        holder.ivEditVideo.setVisibility(View.GONE);
//        holder.ivEditTitle.setVisibility(View.GONE);

        holder.tvTitle.setText(mSubjectSPec.getTitle());

        holder.rcImage.hasFixedSize();
        ImageSucjectAdapter mAdapter = new ImageSucjectAdapter(mActivity, mSubjectSPec.getImage());

        holder.rcImage.setLayoutManager(new LinearLayoutManager(mActivity, LinearLayoutManager.HORIZONTAL, false));
        holder.rcImage.setAdapter(mAdapter);

        MediaController mediaController = new MediaController(mActivity);
        mediaController.setAnchorView(holder.video);
        holder.video.setMediaController(mediaController);
        holder.video.setVideoURI(Uri.parse(mSubjectSPec.getVideo()));
        holder.video.start();

        holder.ivMini.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOpen){
                    holder.areaImage.setVisibility(View.GONE);
                    holder.areaVideo.setVisibility(View.GONE);
                    mOpen = false;
                }else {
                    holder.areaImage.setVisibility(View.VISIBLE);
                    holder.areaVideo.setVisibility(View.VISIBLE);
                    mOpen = true;
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}
