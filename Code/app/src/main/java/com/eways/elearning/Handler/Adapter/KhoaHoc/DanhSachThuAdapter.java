package com.eways.elearning.Handler.Adapter.KhoaHoc;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eways.elearning.R;

/**
 * Created by zzzzz on 11/18/2017.
 */

public class DanhSachThuAdapter extends RecyclerView.Adapter<DanhSachBuoiVaThuViewHolder> {
    private String[] listThu;

    public DanhSachThuAdapter(String[] listBuoiHoc) {
        listThu = listBuoiHoc;
    }

    @Override
    public DanhSachBuoiVaThuViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_danh_sach_buoi_va_thu, parent, false);
        return new DanhSachBuoiVaThuViewHolder(root);
    }

    @Override
    public void onBindViewHolder(DanhSachBuoiVaThuViewHolder holder, int position) {
        holder.btnBuoi.setText(listThu[holder.getLayoutPosition()]);
    }


    @Override
    public int getItemCount() {
        return listThu.length;
    }
}
