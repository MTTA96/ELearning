package com.eways.elearning.Adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.eways.elearning.Model.ImageSelect;
import com.eways.elearning.R;
import com.eways.elearning.Utils.params.GlobalParams;

import java.util.ArrayList;

/**
 * Created by ADMIN on 5/26/2018.
 */

public class ImageChooseAdapter extends ArrayAdapter {
    Activity activity;
    int res;
    ArrayList<ImageSelect> listImage;

    public ImageChooseAdapter(@NonNull Activity activity, int res, @NonNull ArrayList<ImageSelect> listImage) {
        super(activity, res, listImage);

        this.activity = activity;
        this.res = res;
        this.listImage = listImage;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = activity.getLayoutInflater();
        convertView = inflater.inflate(res, null);

        final ImageSelect imageSelect = listImage.get(position);

        int drawableResId = GlobalParams.getInstance().get_resId_by_name(imageSelect.getImage(), "drawable");

        ImageView imageView = convertView.findViewById(R.id.iv_select);
        TextView textView = convertView.findViewById(R.id.tv_select);

        imageView.setImageResource(drawableResId);
        textView.setText(imageSelect.getText());

        return convertView;
    }
}
