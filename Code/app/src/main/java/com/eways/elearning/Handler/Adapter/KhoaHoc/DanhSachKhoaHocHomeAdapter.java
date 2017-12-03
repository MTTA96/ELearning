package com.eways.elearning.Handler.Adapter.KhoaHoc;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eways.elearning.DataModel.KhoaHoc.CustomModelKhoaHoc;
import com.eways.elearning.Handler.FragmentHandler;
import com.eways.elearning.Handler.ImageHandler;
import com.eways.elearning.Handler.ViewHolder.ItemListKhoaHocHomeViewHolder;
import com.eways.elearning.R;
import com.eways.elearning.Util.SupportKeysList;
import com.eways.elearning.View.Fragment.KhoaHoc.ThongTinKhoaHocFragment;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by zzzzz on 12/1/2017.
 */

public class DanhSachKhoaHocHomeAdapter extends RecyclerView.Adapter<ItemListKhoaHocHomeViewHolder> {
    private Context context;
    private ArrayList<CustomModelKhoaHoc> listKhoaHoc = new ArrayList();
    private ImageHandler imageHandler;
    private FragmentHandler fragmentHandler;

    public DanhSachKhoaHocHomeAdapter(Context context, ArrayList<CustomModelKhoaHoc> listKhoaHoc, ImageHandler imageHanlder) {
        this.context = context;
        this.listKhoaHoc = listKhoaHoc;
        this.imageHandler = imageHanlder;
        fragmentHandler = new FragmentHandler(context,((AppCompatActivity)context).getSupportFragmentManager());
    }

    @Override
    public ItemListKhoaHocHomeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(context).inflate(R.layout.item_list_khoa_hoc_home, parent, false);
        return new ItemListKhoaHocHomeViewHolder(root);
    }

    @Override
    public void onBindViewHolder(ItemListKhoaHocHomeViewHolder holder, int position) {
        imageHandler.loadImageRound(listKhoaHoc.get(holder.getLayoutPosition()).LinkAvatar, holder.imgUserImage);
        holder.tvMon.setText(listKhoaHoc.get(holder.getLayoutPosition()).MonHoc.get(0).toString());
        holder.tvGia.setText(chuyenGia(Long.parseLong(listKhoaHoc.get(holder.getLayoutPosition()).HocPhi)));

        holder.imgUserImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentHandler.ChuyenFragment(new ThongTinKhoaHocFragment(), true, SupportKeysList.TAG_THONG_TIN_KHOA_HOC);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listKhoaHoc.size();
    }

    public static String chuyenGia(long gia){
        NumberFormat formatGia = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        return formatGia.format(gia);
    }

}
