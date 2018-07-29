package com.eways.elearning.Adapter.Course;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eways.elearning.Model.Course.Course;
import com.eways.elearning.R;
import com.eways.elearning.Utils.Handler.ImageHandler;
import com.eways.elearning.Views.Activity.Course.CourseDetailActivity;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Currency;

/**
 * Created by zzzzz on 5/27/2018.
 */

public class CourseListAdapter extends RecyclerView.Adapter<CourseListVHolder> {
    private Context context;
    private ArrayList<Course> list = new ArrayList<>();
    private ImageHandler imageHandler;
    private Course course;
    private boolean shouldShowMax = false;

    public CourseListAdapter(Context context, ArrayList<Course> list, boolean shouldShowMax) {
        this.context = context;
        this.list = list;
        this.shouldShowMax = shouldShowMax;
        imageHandler = new ImageHandler(context);
    }

    @Override
    public CourseListVHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_course_list, parent,false);
        return new CourseListVHolder(view);
    }

    @Override
    public void onBindViewHolder(final CourseListVHolder holder, final int position) {
        course = list.get(position);

        holder.vUserInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent courseDetailsIntent = new Intent(((Activity) context), CourseDetailActivity.class);
                courseDetailsIntent.putExtra("CourseId", course.getIdCourse());
                courseDetailsIntent.putExtra("UserId", course.getUid());
                context.startActivity(courseDetailsIntent);

//                fragmentHandler.ChuyenFragment(ThongTinNguoiDangFragment.newInstance(khoaHocArrayList.get(holder.getLayoutPosition()).getNguoiDang(), khoaHoc.isLoaiKhoaHoc()), true, SupportKeysList.TAG_THONG_TIN_NGUOI_DANG);
            }
        });
        holder.vCourseInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent courseDetailsIntent = new Intent(((Activity) context), CourseDetailActivity.class);
                courseDetailsIntent.putExtra("CourseId", course.getIdCourse());
                courseDetailsIntent.putExtra("UserId", course.getUid());
                context.startActivity(courseDetailsIntent);
                 //fragmentHandler.ChuyenFragment(ThongTinKhoaHocFragment.newInstance(khoaHocArrayList.get(holder.getLayoutPosition()).getNguoiDang(), khoaHocArrayList.get(holder.getLayoutPosition()).KeyKhoaHoc), true, SupportKeysList.TAG_THONG_TIN_KHOA_HOC);
            }
        });

        loadData(holder, course);
    }

    @Override
    public int getItemCount() {
        if (!shouldShowMax)
            return list.size() <= 3 ? list.size():3;
        else
            return list.size();
    }

    public void loadData(CourseListVHolder holder, Course course) {

        //Avartar
        imageHandler.loadImageSquare(course.getAvatar(),holder.imgDaiDien);

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
        if(course.getFirstName()!=null) {
            //String ten = "<b>Tên: </b>" + course.getFirstName() + course.getLastName();
            holder.tvTen.setText(Html.fromHtml(course.getFirstName() + course.getLastName()));
        }
        else
        {
            holder.tvTen.setText(Html.fromHtml("Unknown"));
        }

        //Course name
        holder.tvCourseName.setText(course.getCourseName());

        //Môn
        if(course.getSubjectName()!=null) {
            String danhSachMon = "";
//            for (String mon : course.getSubjectName()) {
//                danhSachMon += mon;
//            }
            String mon = "<b>Môn: </b>" + " " + course.getSubjectName();
            holder.tvMon.setText(Html.fromHtml(mon));
        }
        else {
            holder.tvMon.setText(Html.fromHtml("Tùy ý"));
        }

        //Buổi
//        if(course.getLichHoc().getThoiGian()!=null) {
//            String danhSachBuoi = "";
//            for (String buoi : course.getLichHoc().getThoiGian()) {
//                danhSachBuoi += buoi;
//            }
//
//            holder.tvBuoi.setText(Html.fromHtml(buoi));
//        }
//        else
//        {
//            holder.tvBuoi.setText(Html.fromHtml("Tùy ý"));
//        }
        String buoi = "<b>Buổi: </b>" + " " + course.getSchedule();
        holder.tvBuoi.setText(Html.fromHtml(buoi));

        //Học phí
        if(course.getTuition() != null) {
            holder.tvHocPhi.setText(Html.fromHtml(formatter(Double.parseDouble(course.getTuition()), "VND")));
        }
        else {
            holder.tvHocPhi.setText(Html.fromHtml("Thỏa thuận"));
        }

    }

    /** Convert string to currency */
    public static String formatter(double currency, String type) {
        NumberFormat format = NumberFormat.getCurrencyInstance();
        format.setCurrency(Currency.getInstance(type));
        format.setMinimumFractionDigits(0);
        return format.format(currency);
    }
}
