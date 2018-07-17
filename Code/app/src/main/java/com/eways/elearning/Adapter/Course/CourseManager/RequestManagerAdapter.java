package com.eways.elearning.Adapter.Course.CourseManager;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eways.elearning.Adapter.Request.RequestAdapter;
import com.eways.elearning.Model.Requestion;
import com.eways.elearning.R;
import com.eways.elearning.Utils.ActivityUtils;
import com.eways.elearning.Utils.Handler.ImageHandler;
import com.eways.elearning.Views.Activity.InfoUserViewerActivity;

import java.util.ArrayList;

/**
 * Created by ADMIN on 7/17/2018.
 */

public class RequestManagerAdapter extends RecyclerView.Adapter<RequestManagerVHolder> {
    ArrayList<Requestion> mListRequest;
    Activity mActivity;

    ImageHandler mImageHandle;

    public RequestManagerAdapter(ArrayList<Requestion> mListRequest, Activity mActivity) {
        this.mListRequest = mListRequest;
        this.mActivity = mActivity;

        mImageHandle = new ImageHandler(mActivity);
    }

    @Override
    public RequestManagerVHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_course_attend,parent,false);
        return new RequestManagerVHolder(view);
    }

    @Override
    public void onBindViewHolder(RequestManagerVHolder holder, int position) {
        final Requestion mRequestion = mListRequest.get(position);

        holder.tvStudent.setText(mRequestion.getFirstName() + " " + mRequestion.getLastName());
        holder.tvInfoMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityUtils.ChangeActivity(mActivity, InfoUserViewerActivity.class);
            }
        });
    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
