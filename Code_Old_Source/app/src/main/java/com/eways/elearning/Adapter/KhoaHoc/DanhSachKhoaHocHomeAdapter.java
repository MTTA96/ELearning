package com.eways.elearning.Adapter.KhoaHoc;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eways.elearning.Model.DataModel.KhoaHoc.CustomModelKhoaHoc;
import com.eways.elearning.Handler.FragmentHandler;
import com.eways.elearning.Handler.ImageHandler;
import com.eways.elearning.Handler.ViewHolder.ItemListKhoaHocHomeViewHolder;
import com.eways.elearning.R;
import com.eways.elearning.View.Dialog.LoadingDialog;

import java.util.ArrayList;

/**
 * Created by zzzzz on 12/1/2017.
 */

public class DanhSachKhoaHocHomeAdapter extends RecyclerView.Adapter<ItemListKhoaHocHomeViewHolder> {
    private Context context;
    private ArrayList<CustomModelKhoaHoc> listKhoaHoc = new ArrayList();
    private CustomModelKhoaHoc khoaHoc;
    private ImageHandler imageHandler;
    private FragmentHandler fragmentHandler;

    public DanhSachKhoaHocHomeAdapter(Context context, ArrayList<CustomModelKhoaHoc> listKhoaHoc, ImageHandler imageHandler, FragmentHandler fragmentHandler, boolean loaiKhoaHoc) {
        this.context = context;
        this.listKhoaHoc = listKhoaHoc;
        this.imageHandler = imageHandler;
        this.fragmentHandler = fragmentHandler;
    }

    @Override
    public ItemListKhoaHocHomeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(context).inflate(R.layout.item_list_khoa_hoc_home, parent, false);
        return new ItemListKhoaHocHomeViewHolder(root);
    }

    @Override
    public void onBindViewHolder(final ItemListKhoaHocHomeViewHolder holder, int position) {
        khoaHoc = listKhoaHoc.get(position);
        imageHandler.loadImageRound(khoaHoc.getAvatar(), holder.imgUserImage);
        holder.tvMon.setText(khoaHoc.getMon().get(0).toString());
        holder.tvGia.setText(khoaHoc.formatGia(Long.parseLong(khoaHoc.getHocPhi())));

        holder.imgUserImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoadingDialog.showDialog();
//                fragmentHandler.ChuyenFragment(ThongTinKhoaHocFragment.newInstance(listKhoaHoc.get(holder.getLayoutPosition()).getNguoiDang(), listKhoaHoc.get(holder.getLayoutPosition()).KeyKhoaHoc), true, SupportKeysList.TAG_THONG_TIN_KHOA_HOC);
            }
        });

    }

    @Override
    public int getItemCount() {
        return listKhoaHoc.size();
    }

}
