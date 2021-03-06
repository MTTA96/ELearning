package com.eways.elearning.Adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.eways.elearning.Model.DataModel.Other.LayHinhModel;
import com.eways.elearning.R;

import java.util.ArrayList;

/**
 * Created by ADMIN on 11/30/2017.
 */

public class ChonHinhAdapter extends ArrayAdapter{
    Activity ac;
    ArrayList<LayHinhModel> listChonHinh;
    int res;
    public ChonHinhAdapter(@NonNull Activity activity, int resource, @NonNull ArrayList<LayHinhModel> objects) {
        super(activity, resource, objects);
        ac=activity;
        res=resource;
        listChonHinh=objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater=ac.getLayoutInflater();
        convertView=inflater.inflate(res,null);
        final LayHinhModel layHinhModel=listChonHinh.get(position);
        ImageView ivHinhChon=(ImageView) convertView.findViewById(R.id.ivItemChonHinhCNTTTK);
        TextView tvTitleChon=(TextView) convertView.findViewById(R.id.tvTitleChon);
        LinearLayout loChonHinhCNTTTK=(LinearLayout) convertView.findViewById(R.id.loChonHinh_CNTTTK);
        ivHinhChon.setBackgroundResource(layHinhModel.getHinh());
        tvTitleChon.setText(layHinhModel.getContent());
        return convertView;
    }
}
