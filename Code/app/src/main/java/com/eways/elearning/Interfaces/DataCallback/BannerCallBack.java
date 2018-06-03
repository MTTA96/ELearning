package com.eways.elearning.Interfaces.DataCallback;

import com.eways.elearning.Model.Banner;

import java.util.ArrayList;

/**
 * Created by zzzzz on 5/31/2018.
 */

public interface BannerCallBack {
    void bannersCallBack(int resultCode, ArrayList<Banner> banners);
}
