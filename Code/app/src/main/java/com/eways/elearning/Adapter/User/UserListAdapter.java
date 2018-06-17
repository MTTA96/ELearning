package com.eways.elearning.Adapter.User;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eways.elearning.Model.Account.User;
import com.eways.elearning.R;
import com.eways.elearning.Utils.Handler.ImageHandler;

import java.util.ArrayList;

/**
 * Created by zzzzz on 5/27/2018.
 */

public class UserListAdapter extends RecyclerView.Adapter<UserViewHolder> {
    private Context context;
    private ArrayList<User> list = new ArrayList<>();
    private ImageHandler imageHandler;
    private User user;

    public UserListAdapter(Context context, ArrayList<User> list) {
        this.context = context;
        this.list = list;
        imageHandler = new ImageHandler(context);
    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_tutor_list, parent,false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(UserViewHolder holder, int position) {
        holder.vUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                fragmentHandler.ChuyenFragment(ThongTinNguoiDangFragment.newInstance(khoaHocArrayList.get(holder.getLayoutPosition()).getNguoiDang(), khoaHoc.isLoaiKhoaHoc()), true, SupportKeysList.TAG_THONG_TIN_NGUOI_DANG);
            }
        });

        loadData(holder, position);
    }

    @Override
    public int getItemCount() {
        return list.size() <= 5 ? list.size():5;
    }

    public void loadData(UserViewHolder holder, int position) {
        user = list.get(position);

        // Avatar
        imageHandler.loadImageSquare(user.getAvatar(), holder.userAvatar);

        // Name
        holder.tvName.setText(user.getFirstName() + " " + user.getLastName());

        // Rating
//        if(user.getRating() != null)
//        {
//            try {
//                float rt = Float.parseFloat(course.getRating());
//                holder.rtbDanhGia.setRating(rt);
//            }catch (NumberFormatException e)
//            {
//                float rt = 0;
//                holder.rtbDanhGia.setRating(rt);
//            }
//        }
//        else
//        {
//            float rt = 0;
//            holder.rtbDanhGia.setRating(rt);
//        }

        //Môn
//        if(user.getSubjectName()!=null) {
//            String danhSachMon = "";
//            for (String mon : course.getSubjectName()) {
//                danhSachMon += mon;
//            }
//            String mon = "<b>Môn: </b>" + " " + danhSachMon;
//            holder.tvMon.setText(Html.fromHtml(mon));
//        }
//        else
//        {
//            holder.tvMon.setText(Html.fromHtml("Tùy ý"));
//        }
//        String mon = "<b>Môn: </b>" + " " + course.getSubjectName();
//        holder.tvMon.setText(Html.fromHtml(mon));

    }
}
