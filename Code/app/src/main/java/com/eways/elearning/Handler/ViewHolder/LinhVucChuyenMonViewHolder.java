package com.eways.elearning.Handler.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.eways.elearning.R;

/**
 * Created by zzzzz on 1/1/2018.
 */

public class LinhVucChuyenMonViewHolder extends RecyclerView.ViewHolder {
    public TextView tvTitle;
    public RecyclerView vDanhSachBangCap, vDanhSachMon;

    public LinhVucChuyenMonViewHolder(View itemView) {
        super(itemView);
        tvTitle = itemView.findViewById(R.id.textView_TitleLinhVucChuyenMon);
        vDanhSachBangCap = itemView.findViewById(R.id.recyclerView_DanhSachBangCap_LinhVucChuyenMon);
        vDanhSachMon = itemView.findViewById(R.id.recyclerView_DanhSachMon_LinhVucChuyenMon);
    }
}
