package com.eways.elearning.Handler.Adapter.KhoaHoc;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eways.elearning.DataModel.KhoaHoc.CustomModelKhoaHoc;
import com.eways.elearning.Handler.FragmentHandler;
import com.eways.elearning.Handler.ImageHandler;
import com.eways.elearning.Handler.ViewHolder.ItemListCustomKhoaHocViewHolder;
import com.eways.elearning.R;

import java.util.ArrayList;

/**
 * Created by ADMIN on 11/9/2017.
 */

public class KhoaHocRCAdapter extends RecyclerView.Adapter<ItemListCustomKhoaHocViewHolder> {

    private Context context;
    private ArrayList<CustomModelKhoaHoc> khoaHocArrayList;
    private ImageHandler imageHandler;
    private FragmentHandler fragmentHandler;
    private CustomModelKhoaHoc khoaHoc;
//    private boolean loai;


    public KhoaHocRCAdapter(Context context, ArrayList<CustomModelKhoaHoc> khoaHocArrayList, ImageHandler imageHandler, FragmentHandler fragmentHandler) {
        this.context = context;
        this.khoaHocArrayList = khoaHocArrayList;
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
        khoaHoc = khoaHocArrayList.get(position);
        //Avartar
        imageHandler.loadImageRound(khoaHoc.getAvatar(),holder.imgDaiDien);

        //Rating
        if(khoaHoc.getRating() != null)
        {
            float rt = Float.parseFloat(khoaHoc.getRating());
            holder.rtbDanhGia.setRating(rt);
        }
        else
        {
            float rt = 0;
            holder.rtbDanhGia.setRating(rt);
        }

        //Họ tên
        if(khoaHoc.getHoTen()!=null) {
            String ten = "<b>Tên: </b>" + khoaHoc.getHoTen();
            holder.tvTen.setText(Html.fromHtml(ten));
        }
        else
        {
            holder.tvTen.setText(Html.fromHtml("Unknown"));
        }

        //Môn
        if(khoaHoc.getMon()!=null) {
            String danhSachMon = "";
            for (String mon : khoaHoc.getMon()) {
                danhSachMon += mon;
            }
            String mon = "<b>Môn: </b>" + " " + danhSachMon;
            holder.tvMon.setText(Html.fromHtml(mon));
        }
        else
        {
            holder.tvMon.setText(Html.fromHtml("Tùy ý"));
        }

        //Buổi
        if(khoaHoc.getLichHoc().getThoiGian()!=null) {
            String danhSachBuoi = "";
            for (String buoi : khoaHoc.getLichHoc().getThoiGian()) {
                danhSachBuoi += buoi;
            }
            String buoi = "<b>Buổi: </b>" + " " + danhSachBuoi;
            holder.tvBuoi.setText(Html.fromHtml(buoi));
        }
        else
        {
            holder.tvBuoi.setText(Html.fromHtml("Tùy ý"));
        }

        //Học phí
        if(khoaHoc.getHocPhi() != null) {
            String hocPhi = "<b>Học phí: <b>" + khoaHoc.getHocPhi();
            holder.tvHocPhi.setText(Html.fromHtml(hocPhi));
        }
        else
        {
            holder.tvHocPhi.setText(Html.fromHtml("Thỏa thuận"));
        }

    }


    @Override
    public int getItemCount() {
        return khoaHocArrayList.size();
    }
}
