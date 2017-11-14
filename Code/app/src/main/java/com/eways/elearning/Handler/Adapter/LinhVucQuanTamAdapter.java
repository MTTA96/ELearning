package com.eways.elearning.Handler.Adapter;

import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.eways.elearning.DataModel.BaiDang.LinhVucBaiDang;
import com.eways.elearning.R;

import java.util.ArrayList;

/**
 * Created by ADMIN on 11/9/2017.
 */

public class LinhVucQuanTamAdapter extends RecyclerView.Adapter<LinhVucQuanTamAdapter.ViewHolder>  {
    ArrayList<LinhVucBaiDang> DanhSachLinhVucBD;

    public LinhVucQuanTamAdapter(ArrayList<LinhVucBaiDang> danhSachLinhVucBD) {
        DanhSachLinhVucBD = danhSachLinhVucBD;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View root=layoutInflater.inflate(R.layout.linhvuc_quantam_item,parent,false);
        return new ViewHolder(root);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.ivHinhLVQT.setBackgroundResource(DanhSachLinhVucBD.get(position).getHinhLinhVuc());
        holder.tvTenLVQT.setText(DanhSachLinhVucBD.get(position).getTenLinhVuc());
    }

    @Override
    public int getItemCount() {
        return DanhSachLinhVucBD.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView ivHinhLVQT;
        TextView tvTenLVQT;
        CheckBox cbLVQT;
        public ViewHolder(View itemView) {
            super(itemView);
            ivHinhLVQT= (ImageView) itemView.findViewById(R.id.ivHinh_LVYT);
            tvTenLVQT= (TextView) itemView.findViewById(R.id.tvTen_LVYT);
            cbLVQT= (CheckBox) itemView.findViewById(R.id.cbChon_LVYT);
        }
    }
}
