package com.eways.elearning.Handler.Adapter.KhoaHoc;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.eways.elearning.R;

/**
 * Created by zzzzz on 11/18/2017.
 */

public class DanhSachBuoiVaThuViewHolder extends RecyclerView.ViewHolder {
    public Button btnBuoi;

    public DanhSachBuoiVaThuViewHolder(View itemView) {
        super(itemView);
        btnBuoi = (Button) itemView.findViewById(R.id.button_Buoi_ItemDanhSachBuoi);
    }
}
