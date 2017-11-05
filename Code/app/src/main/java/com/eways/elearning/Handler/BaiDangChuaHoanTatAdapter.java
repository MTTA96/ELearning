package com.eways.elearning.Handler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.eways.elearning.DataModel.BaiDang.BaiDangChuaHoanTat;
import com.eways.elearning.R;

import java.util.List;

/**
 * Created by Tien Phat on 02/11/2017.
 */

public class BaiDangChuaHoanTatAdapter extends BaseAdapter {
    Context mContext;
    int mLayout;
    List<BaiDangChuaHoanTat> baiDangChuaHoanTatList;

    public BaiDangChuaHoanTatAdapter(Context mContext, int mLayout, List<BaiDangChuaHoanTat> baiDangChuaHoanTatList) {
        this.mContext = mContext;
        this.mLayout = mLayout;
        this.baiDangChuaHoanTatList = baiDangChuaHoanTatList;
    }

    @Override
    public int getCount() {
        return baiDangChuaHoanTatList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(mLayout,null);

        ImageView imvAvatar = (ImageView)convertView.findViewById(R.id.imvAvatar);
        RatingBar rbBaiDang = (RatingBar) convertView.findViewById(R.id.rbBaiDang);
        TextView tvTenNguoiDang = (TextView) convertView.findViewById(R.id.tvTenNguoiDang);
        TextView tvBuoiHoc = (TextView) convertView.findViewById(R.id.tvBuoiHoc);
        TextView tvMonHoc = (TextView) convertView.findViewById(R.id.tvMonHoc);
        TextView tvXemThem = (TextView) convertView.findViewById(R.id.tvXemThem);

        return convertView;
    }
}
