package com.eways.elearning.Handler.Adapter.KhoaHoc;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eways.elearning.Handler.ViewHolder.ItemListKhoaHocHomeViewHolder;
import com.eways.elearning.R;

import java.util.ArrayList;

/**
 * Created by zzzzz on 12/1/2017.
 */

public class DanhSachKhoaHocHomeAdapter extends RecyclerView.Adapter<ItemListKhoaHocHomeViewHolder> {
    private Context context;
    private ArrayList listKhoaHoc = new ArrayList();

    public DanhSachKhoaHocHomeAdapter(Context context) {
        this.context = context;
//        this.listKhoaHoc = listKhoaHoc;
    }

    @Override
    public ItemListKhoaHocHomeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(context).inflate(R.layout.item_list_khoa_hoc_home, parent, false);
        return new ItemListKhoaHocHomeViewHolder(root);
    }

    @Override
    public void onBindViewHolder(ItemListKhoaHocHomeViewHolder holder, int position) {
    }

    @Override
    public int getItemCount() {
        return 10;
    }
}
