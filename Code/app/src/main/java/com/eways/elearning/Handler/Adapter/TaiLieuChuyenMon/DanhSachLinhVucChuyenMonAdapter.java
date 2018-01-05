package com.eways.elearning.Handler.Adapter.TaiLieuChuyenMon;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eways.elearning.DataModel.TaiKhoan.TaiLieu.LinhVucChuyenMon;
import com.eways.elearning.Handler.ViewHolder.LinhVucChuyenMonViewHolder;
import com.eways.elearning.R;

import java.util.ArrayList;

/**
 * Created by zzzzz on 1/1/2018.
 */

public class DanhSachLinhVucChuyenMonAdapter extends RecyclerView.Adapter<LinhVucChuyenMonViewHolder> {
    private Context context;
    private ArrayList<LinhVucChuyenMon> danhSachLinhVucChuyenMon = new ArrayList<>();

    public DanhSachLinhVucChuyenMonAdapter(Context context) {
        this.context = context;
    }

    @Override
    public LinhVucChuyenMonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View root = inflater.inflate(R.layout.item_danh_sach_linh_vuc_chuyen_mon, parent, false);
        return new LinhVucChuyenMonViewHolder(root);
    }

    @Override
    public void onBindViewHolder(LinhVucChuyenMonViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return danhSachLinhVucChuyenMon.size();
    }
}
