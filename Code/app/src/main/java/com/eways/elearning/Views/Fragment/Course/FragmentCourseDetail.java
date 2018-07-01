package com.eways.elearning.Views.Fragment.Course;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.eways.elearning.Interfaces.DataCallback.Course.CourseDetailsCallBack;
import com.eways.elearning.Model.Course.Course;
import com.eways.elearning.Presenter.Course.CoursePresenter;
import com.eways.elearning.R;
import com.eways.elearning.Utils.SupportKeys;
import com.eways.elearning.Views.Dialog.LoadingDialog;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentCourseDetail extends Fragment implements CourseDetailsCallBack, View.OnClickListener {

    TextView tvAge, tvGenre, tvCarrer, tvDegree;
    TextView tvSubject, tvLocation, tvStartDay, tvDay, tvSession, tvNumberOfSession, tvDuration, tvNumberOfStudent, tvOtherInfo;

    private CoursePresenter coursePresenter;
    private Course course;

    private static String paramCourseId = "CourseId";

    public FragmentCourseDetail() {
        // Required empty public constructor
    }

    public static FragmentCourseDetail newInstance(String courseId) {

        Bundle args = new Bundle();
        args.putString(paramCourseId, courseId);
        FragmentCourseDetail fragment = new FragmentCourseDetail();
        fragment.setArguments(args);

        return fragment;

    }

    /** ----- LIFECYCLE ----- */

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        coursePresenter = new CoursePresenter(getContext());

        if (getArguments() != null) {
            String courseId = getArguments().getString(paramCourseId);
            coursePresenter.getCourseDetails(courseId, this);
        }
        
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        LoadingDialog.showDialog();
        View root = inflater.inflate(R.layout.fragment_course_detail, container, false);

        tvAge = root.findViewById(R.id.textView_NamSinh_ThongTinKhoaHoc);
        tvGenre = root.findViewById(R.id.textView_GioiTinh_ThongTinKhoaHoc);
        tvCarrer = root.findViewById(R.id.textView_NgheNghiep_ThongTinKhoaHoc);
        tvDegree = root.findViewById(R.id.textView_TrinhDo_ThongTin_KhoaHoc);
        tvSubject = root.findViewById(R.id.textView_Mon_ThongTinKhoaHoc);
        tvLocation = root.findViewById(R.id.textView_DiaDiem_ThongTinKhoaHoc);
        tvStartDay = root.findViewById(R.id.textView_NgayBatDau_ThongTinKhoaHoc);
        tvDay = root.findViewById(R.id.textView_Thu_ThongTinKhoaHoc);
        tvSession = root.findViewById(R.id.textView_Buoi_ThongTinKhoaHoc);
        tvNumberOfSession = root.findViewById(R.id.textView_SoBuoi_ThongTinKhoaHoc);
        tvDuration = root.findViewById(R.id.textView_ThoiLuong_ThongTinKhoaHoc);
        tvNumberOfStudent = root.findViewById(R.id.textView_SoHocVien_ThongTinKhoaHoc);
        tvOtherInfo = root.findViewById(R.id.textView_ThongTinKhac_ThongTinKhoaHoc);

        root.findViewById(R.id.button_XemThemThongTinNguoiDang).setOnClickListener(this);

        return root;

    }

    /** ----- CONFIG ----- */

    private void loadData() {

        if (course != null) {

        }

        LoadingDialog.dismissDialog();

    }

    /** ----- ACTION ----- */

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.button_XemThemThongTinNguoiDang:
                break;
        }

    }

    /** ----- HANDLE RESULTS FROM SERVER ----- */

    @Override
    public void courseDetailsCallBack(int error, Course course) {

        if (error == SupportKeys.FAILED_CODE) {

        }

        this.course = course;
        loadData();

    }

}
