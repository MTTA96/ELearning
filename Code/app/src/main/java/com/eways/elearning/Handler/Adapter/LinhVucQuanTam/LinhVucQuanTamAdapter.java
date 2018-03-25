package com.eways.elearning.Handler.Adapter.LinhVucQuanTam;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.eways.elearning.Model.DataModel.BaiDang.LinhVucBaiDang;
import com.eways.elearning.R;

import java.util.ArrayList;

/**
 * Created by ADMIN on 11/9/2017.
 */

public class LinhVucQuanTamAdapter extends RecyclerView.Adapter<LinhVucQuanTamAdapter.ViewHolder> {
    ArrayList<LinhVucBaiDang> DanhSachLinhVucBD;

    Context context;

//    public LinhVucQuanTamAdapter(ArrayList<LinhVucBaiDang> danhSachLinhVucBD) {
//        DanhSachLinhVucBD = danhSachLinhVucBD;
//    }


    public LinhVucQuanTamAdapter(ArrayList<LinhVucBaiDang> danhSachLinhVucBD, Context context) {
        DanhSachLinhVucBD = danhSachLinhVucBD;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        View root=layoutInflater.inflate(R.layout.linhvuc_quantam_item,parent,false);
        return new ViewHolder(root);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final ArrayList<Integer> DanhSachChon=new ArrayList<>();
        holder.ivHinhLVQT.setBackgroundResource(DanhSachLinhVucBD.get(position).getHinhLinhVuc());
        holder.tvTenLVQT.setText(DanhSachLinhVucBD.get(position).getTenLinhVuc());
        ArrayAdapter<String> adDanhSachChiTietLV=new ArrayAdapter(context,android.R.layout.simple_list_item_1,DanhSachLinhVucBD.get(position).getDanhsachchitiet());
        holder.lsvChiTietLinhVuc.setAdapter(adDanhSachChiTietLV);

        holder.flLVQT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.lsvChiTietLinhVuc.getVisibility() == View.VISIBLE){
                    holder.lsvChiTietLinhVuc.setVisibility(View.GONE);
                    ViewGroup.LayoutParams layoutParams=holder.flLVQT.getLayoutParams();
                    layoutParams.width= ViewGroup.LayoutParams.WRAP_CONTENT;
                    layoutParams.height= ViewGroup.LayoutParams.WRAP_CONTENT;
                    holder.flLVQT.setLayoutParams(layoutParams);
                    notifyDataSetChanged();
                }
                else {
                    holder.lsvChiTietLinhVuc.setVisibility(View.VISIBLE);
                    notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return DanhSachLinhVucBD.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView ivHinhLVQT;
        TextView tvTenLVQT;
        CheckBox cbLVQT;
        FrameLayout flLVQT;
        ListView lsvChiTietLinhVuc;
        Button btnXacNhanLinhVuc;
        public ViewHolder(View itemView) {
            super(itemView);
            ivHinhLVQT= (ImageView) itemView.findViewById(R.id.ivHinh_LVYT);
            tvTenLVQT= (TextView) itemView.findViewById(R.id.tvTen_LVYT);
            cbLVQT= (CheckBox) itemView.findViewById(R.id.cbChon_LVYT);
            flLVQT= (FrameLayout) itemView.findViewById(R.id.flItem_LVQT);
            btnXacNhanLinhVuc=(Button) itemView.findViewById(R.id.btnXacNhanLinhVucQuanTam);
            lsvChiTietLinhVuc=(ListView) itemView.findViewById(R.id.lsvChiTietLinhVuc);

        }
    }
}
