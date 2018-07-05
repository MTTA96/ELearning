package com.eways.elearning.Adapter.SpecialAdapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.Toast;

import com.eways.elearning.Adapter.Course.CourseVHolder;
import com.eways.elearning.Adapter.Phone.PhoneCodeVHolder;
import com.eways.elearning.Model.Certificate;
import com.eways.elearning.R;
import com.eways.elearning.Utils.Handler.ImageHandler;
import com.eways.elearning.Utils.params.GlobalParams;
import com.eways.elearning.Views.Activity.PopUpAddImageActivity;
import com.google.android.gms.common.internal.GmsLogger;

import java.util.ArrayList;

/**
 * Created by ADMIN on 7/2/2018.
 */

public class SpecialAdapter extends RecyclerView.Adapter<SpecialVHolder> {

    ArrayList<Certificate> mListCertificate;
    Activity mActivity;
    ImageHandler mImageHandle;

    public SpecialAdapter(ArrayList<Certificate> mListCertificate, Activity mActivity) {
        this.mListCertificate = mListCertificate;
        this.mActivity = mActivity;

        mImageHandle = new ImageHandler(mActivity);
    }

    @Override
    public SpecialVHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_special_certi,parent,false);
        return new SpecialVHolder(view);
    }

    @Override
    public void onBindViewHolder(SpecialVHolder holder, int position) {
        final Certificate mCertificate = mListCertificate.get(position);

        holder.tvName.setText(mCertificate.getName());
        mImageHandle.loadImageSquare(mCertificate.getImage(), holder.ivImage);

        holder.ivImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(mActivity, PopUpAddImageActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.putExtra("item_certi", GlobalParams.getInstance().getGSon().toJson(mCertificate));

                mActivity.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mListCertificate.size();
    }
}
