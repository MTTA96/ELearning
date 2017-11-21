package com.eways.elearning.Handler.Adapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.eways.elearning.DataModel.KhoaHoc.CustomModelKhoaHoc;
import com.eways.elearning.Handler.ImageHandler;
import com.eways.elearning.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tran Tien Phat on 14/11/2017.
 */

public class CustomModelKhoaHocAdapter extends BaseAdapter {

    Context mContext;
    int mLayout;
    List<CustomModelKhoaHoc> customModelKhoaHocList;

    public CustomModelKhoaHocAdapter(Context mContext, int mLayout, List<CustomModelKhoaHoc> customModelKhoaHocList) {
        this.mContext = mContext;
        this.mLayout = mLayout;
        this.customModelKhoaHocList = customModelKhoaHocList;
    }

    @Override
    public int getCount() {
        return customModelKhoaHocList.size();
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

        ImageView imvAvatar = (ImageView) convertView.findViewById(R.id.img_KhoaHoc);
//        Picasso.with(mContext).load(customModelKhoaHocList.get(position).getLinkAvatar()).into(imvAvatar);
        ImageHandler imageHandler = new ImageHandler(mContext);
        imageHandler.loadImageRound(customModelKhoaHocList.get(position).LinkAvatar,imvAvatar);
//        imvAvatar.setImageURI(url);
//        lấy UserID của người đăng khóa học => lấy link avatar trong tài khoản

        RatingBar rbBaiDang = (RatingBar) convertView.findViewById(R.id.rtb_KhoaHoc);
        float rt = Float.parseFloat(customModelKhoaHocList.get(position).Rating);
        rbBaiDang.setRating(rt);
//        rbBaiDang.setRating( );
//        lấy UserID của người đăng khóa học => lấy rating trong tài khoản

        TextView tvTenNguoiDang = (TextView) convertView.findViewById(R.id.tvTen_KhoaHoc);
        String x = "<b>"+tvTenNguoiDang.getText()+"</b>"+" " +customModelKhoaHocList.get(position).TenNguoiDang;
        tvTenNguoiDang.setText(Html.fromHtml(x));


        TextView tvBuoiHoc = (TextView) convertView.findViewById(R.id.tvBuoi_KhoaHoc);
        tvBuoiHoc.setText(tvBuoiHoc.getText()+ " " +customModelKhoaHocList.get(position).BuoiHoc);

        TextView tvMonHoc = (TextView) convertView.findViewById(R.id.tvMon_KhoaHoc);
        ArrayList<String> listMon = customModelKhoaHocList.get(position).MonHoc;
        String danhSachMon = "";
        for (String mon : listMon) { danhSachMon = danhSachMon + mon; }
        tvMonHoc.setText(tvMonHoc.getText()+ " " +danhSachMon);

        TextView tvHocPhi = (TextView)convertView.findViewById(R.id.tvGia_KhoaHoc);
        tvHocPhi.setText(tvHocPhi.getText()+ " " +customModelKhoaHocList.get(position).HocPhi);
//        tvTenNguoiDang.setText(Arrays.toString(khoaHocChuaHoanTatList.get(position).getMon().toArray()));
//        non-recommended! Không chắc đúng hay không? Với lại hiện thị kiểu này hông đẹp

        return convertView;
    }


}