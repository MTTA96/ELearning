package com.eways.elearning.Views.Activity.Course;

import android.annotation.SuppressLint;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.eways.elearning.Adapter.TagAdapter;

import com.eways.elearning.Interfaces.DataCallback.Course.CourseDetailsCallBack;
import com.eways.elearning.Interfaces.DataCallback.User.SendRequestCallback;
import com.eways.elearning.Interfaces.DataCallback.User.UserCallBack;
import com.eways.elearning.Model.Account.User;
import com.eways.elearning.Model.Course.Course;
import com.eways.elearning.Model.Degree.Degree;
import com.eways.elearning.Presenter.Authentication.UserPresenter;
import com.eways.elearning.Presenter.Course.CoursePresenter;

import com.eways.elearning.R;
import com.eways.elearning.Utils.ActivityUtils;
import com.eways.elearning.Utils.Handler.ImageHandler;
import com.eways.elearning.Utils.SupportKeys;
import com.eways.elearning.Views.Activity.InfoUserViewerActivity;
import com.eways.elearning.Views.Dialog.LoadingDialog;

import java.util.ArrayList;

public class CourseDetailActivity extends AppCompatActivity implements View.OnClickListener, CourseDetailsCallBack, UserCallBack, SendRequestCallback {
    /** VIEWS */
    TextView tvName, tvEmail, tvBirthDay, tvPhone, tvGender, tvJob, tvDegree, tvSubject, tvAddress, tvWeekday, tvStartDay, tvSession, tvAmountSession, tvAmountAttender, tvDuration, tvInfoMore, tvSave;
    ImageView ivAvarta, ivBack;
    android.support.v7.widget.Toolbar toolBar;
    RecyclerView rcSubject, rcTime, rcSession;
    View btnRequest;
    RelativeLayout bgSendRequest;
    TextView tvSendRequest;
    View btnSendRequest;

    //for test
    boolean status = false;

    TagAdapter mSubjectAdapter, mTimeAdapter;

    /** ACCESSORIES */
    ArrayList<String> mListTime;
    ArrayList<String> mListSubject, mListSession;
    ImageHandler mImageHandle;

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
        tvSubject = findViewById(R.id.tv_subject_name);
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
        tvSendRequest = findViewById(R.id.text);
        bgSendRequest = findViewById(R.id.bg_send_request);
        tvDegree = findViewById(R.id.tv_level);
        tvSession = findViewById(R.id.tv_amount_session);
        tvPhone = findViewById(R.id.tv_phone);
        rcTime = findViewById(R.id.rc_time);
        rcSession = findViewById(R.id.rc_session);
    }

    private void handle_views(){
        mImageHandle = new ImageHandler(this);

        ivBack.setOnClickListener(this);
        btnRequest.setOnClickListener(this);
        findViewById(R.id.btn_user_info).setOnClickListener(this);

        tvPhone.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_back:
                this.finish();
                break;
            case R.id.btn_request_send:

                if (status){
                    status = false;
                }else {
                    status = true;
                    ActivityUtils.ChangeActivity(this, CourseRequestManagerActivity.class);
                }
                userPresenter.sendRequestToTutor(course.getIdCourse(), null, this);
                break;

            case R.id.tv_more:
                Toast.makeText(this, "learn more", Toast.LENGTH_SHORT).show();
                break;

            case R.id.btn_user_info:

                Intent userInfoIntent = new Intent(this, InfoUserViewerActivity.class);
                userInfoIntent.putExtra(InfoUserViewerActivity.paramUId, user.getId());
                startActivity(userInfoIntent);
                break;

            case R.id.tv_phone:

                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + user.getPhone()));
                startActivity(intent);
                break;
        }
    }

    private void SetUpList(){
        mListSubject = new ArrayList<>();
        String tmp = course.getSubjectName();
        String[] mSubjectArray = course.getSubjectName().split(",");
        for (int i = 0; i < mSubjectArray.length; i++){
            mListSubject.add(mSubjectArray[i]);
        }
        mSubjectAdapter = new TagAdapter(this, mListSubject);
        rcSubject.hasFixedSize();
        rcSubject.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rcSubject.setAdapter(mSubjectAdapter);

        mListTime = new ArrayList<>();
        String[] mTimeArray = course.getSchedule().split(",");
        for (int i = 0; i < mSubjectArray.length; i++){
            mListTime.add(mTimeArray[i]);
        }
        mTimeAdapter = new TagAdapter(this, mListTime);
        rcTime.hasFixedSize();
        rcTime.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rcTime.setAdapter(mTimeAdapter);

        mListSession = new ArrayList<>();
        String[] mSessionArray = course.getTimePerSession().split(",");
        for (int i = 0; i < mSessionArray.length; i++){
            mListSession.add(mTimeArray[i]);
        }
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
//            bgSendRequest.setBackgroundResource(R.drawable.cancel_request);

        }else {
            tvSendRequest.setText("Gửi yêu cầu");
            tvSendRequest.setTextColor(this.getColor(R.color.White));

//            bgSendRequest.setBackgroundResource(R.drawable.send_request);
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

            mImageHandle.loadImageRound(user.getAvatar(), ivAvarta);

            Degree degree = Degree.getInstance();

            tvDegree.setText(degree.getDegreeById(user.getDegree()));

            tvPhone.setText(user.getPhone());


        }

        LoadingDialog.dismissDialog();

    }
    private void loadCourseInfo() {

        if (course != null) {
//            tvSubject.setText(course.getSubjectName());
            tvAddress.setText(course.getAddress());
//            tvWeekday.setText(course.getSchedule());
            tvSession.setText(course.getTimePerSession());
            tvAmountSession.setText(course.getNumberOfSessions());
            tvAmountAttender.setText(course.getStudentNumber());
            tvInfoMore.setText(course.getDescription());
            tvSession.setText(course.getNumberOfSessions());
//            tvStartDay.setText(course.getTimeStart());
//            tvDuration.setText(course.getTimePerSession());

            SetUpList();
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

    @Override
    public void sendRequestCallback(int resultCode, @Nullable String msg) {
        if (resultCode == SupportKeys.FAILED_CODE) {
            return;
        }

        // Request success

    }
}
