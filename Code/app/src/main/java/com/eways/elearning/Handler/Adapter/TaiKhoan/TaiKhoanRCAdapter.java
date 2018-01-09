package com.eways.elearning.Handler.Adapter.TaiKhoan;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eways.elearning.DataModel.TaiKhoan.TaiKhoan;
import com.eways.elearning.Handler.FragmentHandler;
import com.eways.elearning.Handler.ImageHandler;
import com.eways.elearning.Handler.ViewHolder.ItemListCustomKhoaHocViewHolder;
import com.eways.elearning.R;

import java.util.ArrayList;

/**
 * Created by yowin on 09/01/2018.
 */

public class TaiKhoanRCAdapter extends RecyclerView.Adapter<ItemListCustomKhoaHocViewHolder>{

    private Context context;
    private ArrayList<TaiKhoan> taiKhoanArrayList;
    private ImageHandler imageHandler;
    private FragmentHandler fragmentHandler;
    private TaiKhoan taiKhoan;


    public TaiKhoanRCAdapter(Context context, ArrayList<TaiKhoan> taiKhoanArrayList, ImageHandler imageHandler, FragmentHandler fragmentHandler) {
        this.context = context;
        this.taiKhoanArrayList = taiKhoanArrayList;
        this.imageHandler = imageHandler;
        this.fragmentHandler = fragmentHandler;
    }

    @Override
    public ItemListCustomKhoaHocViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(context).inflate(R.layout.custom_item_khoahoc, parent, false);
        return new ItemListCustomKhoaHocViewHolder(root);
    }

    @Override
    public void onBindViewHolder(ItemListCustomKhoaHocViewHolder holder, int position) {
        taiKhoan = taiKhoanArrayList.get(position);
        //Avartar
        imageHandler.loadImageRound(taiKhoan.getAvatar(),holder.imgDaiDien);

        //Rating
        if(taiKhoan.getRating() != null)
        {
            float rt = Float.parseFloat(taiKhoan.getRating());
            holder.rtbDanhGia.setRating(rt);
        }
        else
        {
            float rt = 0;
            holder.rtbDanhGia.setRating(rt);
        }

        //Họ tên
        if(taiKhoan.getHo()!=null && taiKhoan.getTen()!=null) {
            String ten = "<b>Tên: </b>" + taiKhoan.getHo() + " "+taiKhoan.getTen();
            holder.tvTen.setText(Html.fromHtml(ten));
        }
        else
        {
            holder.tvTen.setText(Html.fromHtml("Unknown"));
        }

        //Môn
        if(taiKhoan.getTrinhdo()!=null) {
            String trinhDo = "<b>Trình độ: </b>" + " " + taiKhoan.getTrinhdo();
            holder.tvMon.setText(Html.fromHtml(trinhDo));
        }
        else
        {
            holder.tvMon.setText(Html.fromHtml("Unknown"));
        }

        //Buổi
            holder.tvBuoi.setText(Html.fromHtml(""));


        //Học phí
            holder.tvHocPhi.setText(Html.fromHtml(""));

    }


    @Override
    public int getItemCount() {
        return taiKhoanArrayList.size();
    }
}
