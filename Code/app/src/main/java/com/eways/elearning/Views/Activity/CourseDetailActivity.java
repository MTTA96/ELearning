package com.eways.elearning.Views.Activity;

import android.annotation.SuppressLint;
import android.app.Activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;


import com.eways.elearning.Adapter.TagAdapter;

import com.eways.elearning.Interfaces.DataCallback.Course.CourseDetailsCallBack;
import com.eways.elearning.Interfaces.DataCallback.User.UserCallBack;
import com.eways.elearning.Model.Account.User;
import com.eways.elearning.Model.Course.Course;
import com.eways.elearning.Model.Degree.Degree;
import com.eways.elearning.Presenter.Authentication.UserPresenter;
import com.eways.elearning.Presenter.Course.CoursePresenter;

import com.eways.elearning.R;
import com.eways.elearning.Utils.SupportKeys;
import com.eways.elearning.Utils.params.GlobalParams;
import com.eways.elearning.Views.Dialog.LoadingDialog;

import java.util.ArrayList;

public class CourseDetailActivity extends AppCompatActivity implements View.OnClickListener, CourseDetailsCallBack, UserCallBack {
    /** VIEWS */
    TextView tvName, tvEmail, tvBirthDay, tvGender, tvJob, tvDegree, tvSubject, tvAddress, tvWeekday, tvStartDay, tvSession, tvAmountSession, tvAmountAttender, tvDuration, tvInfoMore, tvSave;
    ImageView ivAvarta, ivBack;
    android.support.v7.widget.Toolbar toolBar;
    RecyclerView rcSubject, rcTime;
    View btnRequest;
    RelativeLayout bgSendRequest;
    TextView tvSendRequest;
    View btnSendRequest;

    //for test
    boolean status = false;

    TagAdapter mSubjectAdapter, mTimeAdapter;

    /** ACCESSORIES */
    ArrayList<String> mListTime;
    ArrayList<String> mListSubject;

    private CoursePresenter coursePresenter;
    private UserPresenter userPresenter;
    private Course course;
    private User user;

    public static String paramCourseId = "CourseId";
    public static String paramUserId = "UserId";

    /** ----- LIFECYCLE ----- */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detail);


        declare_views();
        handle_views();

        coursePresenter = new CoursePresenter(this);
        userPresenter = new UserPresenter(this);

        // Call apis

        String courseId = getIntent().getStringExtra(paramCourseId);
        coursePresenter.getCourseDetails(courseId, this);

        String uId = getIntent().getStringExtra(paramUserId);
        userPresenter.getUserInfo(uId, this);

    }

    private void declare_views(){
        tvName = findViewById(R.id.textView_TenNguoiDang_ThongTinKhoaHoc);
        tvEmail = findViewById(R.id.tv_email);
        tvBirthDay = findViewById(R.id.tv_birthday);
        tvGender = findViewById(R.id.tv_gender);
        tvJob = findViewById(R.id.tv_job);
//        tvSubject = findViewById(R.id.tv_subject);
        tvAddress = findViewById(R.id.tv_address);
//        tvWeekday = findViewById(R.id.tv_weekday);
        tvSession = findViewById(R.id.tv_amount_session);
        tvAmountSession = findViewById(R.id.tv_amount_attender);
        tvAmountAttender = findViewById(R.id.tv_amount_attender);
        tvInfoMore = findViewById(R.id.tv_info_more);
        ivBack = findViewById(R.id.iv_back);
        ivAvarta = findViewById(R.id.iv_avarta);
        toolBar = findViewById(R.id.toolbar);
        btnRequest = findViewById(R.id.btn_request_send);
        rcSubject = findViewById(R.id.rc_subject);
        rcTime  = findViewById(R.id.rc_time);
        tvSendRequest = findViewById(R.id.text);
        bgSendRequest = findViewById(R.id.bg_send_request);
    }

    private void handle_views(){
        ivBack.setOnClickListener(this);
        btnRequest.setOnClickListener(this);
        findViewById(R.id.btn_user_info).setOnClickListener(this);

        SetUpList();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_back:
                this.finish();
                break;
            case R.id.btn_request_send:

                SetStatusSendRQ(!status);
                if (status){
                    status = false;
                }else {
                    status = true;
                }

                break;

            case R.id.tv_more:
                Toast.makeText(this, "learn more", Toast.LENGTH_SHORT).show();
                break;

            case R.id.btn_user_info:

                Intent userInfoIntent = new Intent(this, InfoUserViewerActivity.class);
                userInfoIntent.putExtra(InfoUserViewerActivity.paramUId, user.getUid());
                startActivity(userInfoIntent);
                break;
        }
    }

    private void SetUpList(){
        mListSubject = new ArrayList<>();
        mSubjectAdapter = new TagAdapter(this, mListSubject);
        rcSubject.hasFixedSize();
        rcSubject.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rcSubject.setAdapter(mSubjectAdapter);

        mListTime = new ArrayList<>();
        mTimeAdapter = new TagAdapter(this, mListTime);
        rcTime.hasFixedSize();
        rcTime.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rcTime.setAdapter(mTimeAdapter);
    }

    @SuppressLint("ResourceType")
    private void SetStatusSendRQ(boolean status){
        if (status){
            tvSendRequest.setText("Hủy");
            tvSendRequest.setTextColor(this.getColor(R.color.colorBlue));
            bgSendRequest.setBackgroundResource(R.drawable.cancel_request);

        }else {
            tvSendRequest.setText("Gửi yêu cầu");
            tvSendRequest.setTextColor(this.getColor(R.color.White));

            bgSendRequest.setBackgroundResource(R.drawable.send_request);
        }

        tvInfoMore = findViewById(R.id.tv_info_more);
        tvSave = findViewById(R.id.tv_save);
        tvDegree = findViewById(R.id.tv_level);
        tvStartDay = findViewById(R.id.textView_NgayBatDau_ThongTinKhoaHoc);
        tvDuration = findViewById(R.id.textView_ThoiLuong_ThongTinKhoaHoc);

        ivAvarta = findViewById(R.id.iv_avarta);

        findViewById(R.id.btn_user_info).setOnClickListener(this);

    }
    /** ----- CONFIG ----- */

    private void loadUserInfo() {

        if (user != null) {

            tvName.setText(user.getFirstName() + user.getLastName());
            tvEmail.setText(user.getEmail());
            tvBirthDay.setText(user.getBirthday());
            tvGender.setText(user.getSex());
            tvJob.setText(user.getCareer());

            Degree degree = Degree.getInstance();

            tvDegree.setText(degree.getDegreeById(user.getDegree()));

        }

        LoadingDialog.dismissDialog();

    }
    private void loadCourseInfo() {

        if (course != null) {
            tvSubject.setText(course.getSubjectName());
            tvAddress.setText(course.getAddress());
            tvWeekday.setText(course.getSchedule());
            tvSession.setText(course.getTimePerSession());
            tvAmountSession.setText(course.getNumberOfSessions());
            tvAmountAttender.setText(course.getStudentNumber());
            tvInfoMore.setText(course.getDescription());
            tvStartDay.setText(course.getTimeStart());
            tvDuration.setText(course.getTimePerSession());
        }

        LoadingDialog.dismissDialog();

    }
    /** ----- ACTION ----- */



    /** ----- HANDLE RESULTS FROM SERVER ----- */

    @Override
    public void courseDetailsCallBack(int error, Course course) {

        if (error == SupportKeys.FAILED_CODE) {

        }

        this.course = course;
        loadCourseInfo();

    }

    @Override
    public void userCallBack(int errorCode, User user) {

        if (errorCode == SupportKeys.FAILED_CODE) {

        }

        this.user = user;
        loadUserInfo();
    }

}
