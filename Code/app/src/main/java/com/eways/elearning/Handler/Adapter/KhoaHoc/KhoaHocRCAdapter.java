package com.eways.elearning.Handler.Adapter.KhoaHoc;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.eways.elearning.DataModel.KhoaHoc.CustomModelKhoaHoc;
import com.eways.elearning.Handler.FragmentHandler;
import com.eways.elearning.Handler.ImageHandler;
import com.eways.elearning.R;
import com.eways.elearning.Util.SupportKeysList;
import com.eways.elearning.View.Fragment.KhoaHoc.ThongTinKhoaHocFragment;
import com.eways.elearning.View.Fragment.KhoaHoc.ThongTinNguoiDang.ThongTinNguoiDangFragment;

import java.util.ArrayList;

/**
 * Created by ADMIN on 11/9/2017.
 */

public class KhoaHocRCAdapter extends RecyclerView.Adapter<ViewHolder> {
    private Context context;
    private ArrayList<CustomModelKhoaHoc> khoaHocArrayList;
    private ImageHandler imageHandler;
    private FragmentHandler fragmentHandler;

    public KhoaHocRCAdapter(Context context, ArrayList<CustomModelKhoaHoc> khoaHocArrayList, ImageHandler imageHandler, FragmentHandler fragmentHandler) {
        this.context = context;
        this.khoaHocArrayList = khoaHocArrayList;
        this.imageHandler = imageHandler;
        this.fragmentHandler = fragmentHandler;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View root = layoutInflater.inflate(R.layout.custom_item_khoahoc, parent, false);

        return new ViewHolder(root);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.vUserInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentHandler.ChuyenFragment(ThongTinNguoiDangFragment.newInstance(khoaHocArrayList.get(holder.getLayoutPosition()).getNguoiDang()), true, SupportKeysList.TAG_THONG_TIN_NGUOI_DANG);
            }
        });
        holder.vCourseInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentHandler.ChuyenFragment(ThongTinKhoaHocFragment.newInstance(khoaHocArrayList.get(holder.getLayoutPosition()).getNguoiDang(), khoaHocArrayList.get(holder.getLayoutPosition()).KeyKhoaHoc), true, SupportKeysList.TAG_THONG_TIN_KHOA_HOC);
            }
        });

        loadData(holder, position);
    }

    private void loadData(ViewHolder holder, int position) {
        //Hình
        imageHandler.loadImageSquare(khoaHocArrayList.get(position).getAvatar(), holder.imvAvatar);

        //Rating
        if (khoaHocArrayList.get(position).getRating() != null) {
            float rt = Float.parseFloat(khoaHocArrayList.get(position).getRating());
            holder.rtbBaiDang.setRating(rt);
        }

        //Họ tên
        if (khoaHocArrayList.get(position).getHoTen() != null) {
            String ten = "<b>Tên: </b>" + " " + khoaHocArrayList.get(position).getHoTen();
            holder.tvTenNguoiDang.setText(Html.fromHtml(ten));
        }

        //Môn
        if (khoaHocArrayList.get(position).getMon() != null) {
            ArrayList<String> listMon = khoaHocArrayList.get(position).getMon();
            String danhSachMon = "";
            for (String mon : listMon) {
                danhSachMon = danhSachMon + mon;
            }
            String mon = "<b>Môn: </b>" + " " + danhSachMon;
            holder.tvMonHoc.setText(Html.fromHtml(mon));
        }

        if (khoaHocArrayList.get(position).getSoBuoiHoc() != null) {
            ArrayList<String> listBuoi = khoaHocArrayList.get(position).getLichHoc().getNgayHoc();
            String dsbuoi = "";
            for (String buoi : listBuoi) {
                dsbuoi = dsbuoi + buoi;
            }
            String buoi = "<b>Buổi: </b>" + " " + dsbuoi;
            holder.tvBuoiHoc.setText(Html.fromHtml(buoi));
        }

        if (khoaHocArrayList.get(position).getHocPhi() != null) {
            String hocPhi = "<b>Học phí: <b>" + " " + khoaHocArrayList.get(position).getHocPhi();
            holder.tvHocPhi.setText(Html.fromHtml(hocPhi));
        }
    }

    @Override
    public int getItemCount() {
        return khoaHocArrayList.size();
    }
}
