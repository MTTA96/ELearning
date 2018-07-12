package com.eways.elearning.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.eways.elearning.Adapter.Search.SearchVHolder;
import com.eways.elearning.Model.Image;
import com.eways.elearning.R;
import com.eways.elearning.Utils.Handler.ImageHandler;
import com.eways.elearning.Utils.params.GlobalParams;
import com.eways.elearning.Views.Activity.PopUpAddImageActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ADMIN on 7/6/2018.
 */

public class ImageSucjectAdapter extends RecyclerView.Adapter<ImageSubjectVHolder> {
    Activity mActivity;
    List<Image> mListImage;

    boolean mOpen;

    ImageHandler mImageViewHandle;

    public ImageSucjectAdapter(Activity mActivity, List<Image> mListImage) {
        this.mActivity = mActivity;
        this.mListImage = mListImage;

        mImageViewHandle = new ImageHandler(mActivity);
    }

    @Override
    public ImageSubjectVHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_image_subject,parent,false);

        mOpen = true;
        return new ImageSubjectVHolder(view);
    }

    @Override
    public void onBindViewHolder(final ImageSubjectVHolder holder, int position) {
        final Image mImage = mListImage.get(position);

        mImageViewHandle.loadImageSquare(mImage.getImage(), holder.ivSubject);


        holder.ivSubject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(mActivity, PopUpAddImageActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.putExtra("item_subject", GlobalParams.getInstance().getGSon().toJson(mImage));
                mActivity.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mListImage.size();
    }
}
