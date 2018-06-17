package com.eways.elearning.Adapter;

import android.content.Context;

import com.eways.elearning.Model.Banner;

import java.util.ArrayList;

import ss.com.bannerslider.adapters.SliderAdapter;
import ss.com.bannerslider.viewholder.ImageSlideViewHolder;

/**
 * Created by zzzzz on 6/17/2018.
 */

public class ImageSliderShowAdapter extends SliderAdapter {

    private ArrayList<Banner> bannerList = new ArrayList<>();

    public ImageSliderShowAdapter(Context context, ArrayList<Banner> bannerList) {

        this.bannerList = bannerList;

    }

    @Override
    public void onBindImageSlide(int position, ImageSlideViewHolder viewHolder) {
        viewHolder.bindImageSlide((bannerList.get(position).getImg()));
    }

    @Override
    public int getItemCount() {
        return bannerList.size();
    }
}
