package com.eways.elearning.Adapter.Course;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eways.elearning.Model.Course;
import com.eways.elearning.R;
import com.eways.elearning.Utils.Handler.ImageHandler;

import java.util.ArrayList;

/**
 * Created by zzzzz on 5/27/2018.
 */

public class CourseListAdapter extends RecyclerView.Adapter<CourseViewHolder> {
    private Context context;
    private ArrayList<Course> list = new ArrayList<>();
    private ImageHandler imageHandler;
    private Course course;

    public CourseListAdapter(Context context, ArrayList<Course> list) {
        this.context = context;
        this.list = list;
        imageHandler = new ImageHandler(context);
    }

    @Override
    public CourseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_course_list, parent,false);
        return new CourseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CourseViewHolder holder, int position) {
        holder.vUserInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                fragmentHandler.ChuyenFragment(ThongTinNguoiDangFragment.newInstance(khoaHocArrayList.get(holder.getLayoutPosition()).getNguoiDang(), khoaHoc.isLoaiKhoaHoc()), true, SupportKeysList.TAG_THONG_TIN_NGUOI_DANG);
            }
        });
        holder.vCourseInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                fragmentHandler.ChuyenFragment(ThongTinKhoaHocFragment.newInstance(khoaHocArrayList.get(holder.getLayoutPosition()).getNguoiDang(), khoaHocArrayList.get(holder.getLayoutPosition()).KeyKhoaHoc), true, SupportKeysList.TAG_THONG_TIN_KHOA_HOC);
            }
        });

        loadData(holder, position);
    }

    @Override
    public int getItemCount() {
        return list.size() <= 5 ? list.size():5;
    }

    public void loadData(CourseViewHolder holder, int position) {
        course = list.get(position);

        //Avartar
//        imageHandler.loadImageRound(course.getAvatar(),holder.imgDaiDien);

        //Rating
//        if(course.getRating() != null)
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

        //Họ tên
//        if(course.getHoTen()!=null) {
//            String ten = "<b>Tên: </b>" + course.getHoTen();
//            holder.tvTen.setText(Html.fromHtml(ten));
//        }
//        else
//        {
//            holder.tvTen.setText(Html.fromHtml("Unknown"));
//        }

        //Môn
//        if(course.getSubjectName()!=null) {
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
        holder.tvMon.setText(course.getSubjectName());

        //Buổi
//        if(course.getLichHoc().getThoiGian()!=null) {
//            String danhSachBuoi = "";
//            for (String buoi : course.getLichHoc().getThoiGian()) {
//                danhSachBuoi += buoi;
//            }
//            String buoi = "<b>Buổi: </b>" + " " + danhSachBuoi;
//            holder.tvBuoi.setText(Html.fromHtml(buoi));
//        }
//        else
//        {
//            holder.tvBuoi.setText(Html.fromHtml("Tùy ý"));
//        }
        holder.tvBuoi.setText(course.getSchedule());

        //Học phí
        if(course.getTuition() != null) {
            String hocPhi = "<b>Học phí: <b>" + course.getTuition();
            holder.tvHocPhi.setText(Html.fromHtml(hocPhi));
        }
        else
        {
            holder.tvHocPhi.setText(Html.fromHtml("Thỏa thuận"));
        }

    }
}