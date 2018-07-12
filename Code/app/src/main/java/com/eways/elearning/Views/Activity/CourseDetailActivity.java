package com.eways.elearning.Views.Activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.eways.elearning.Adapter.TagAdapter;
import com.eways.elearning.R;

import java.util.ArrayList;

public class CourseDetailActivity extends Activity implements View.OnClickListener{
    /** VIEWS */
    TextView tvBirthDay, tvGender, tvJob, tvSubject, tvAddress, tvWeekday, tvSession, tvAmountSession, tvAmountAttender, tvInfoMore, tvSave;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detail);

        declare_views();
        handle_views();
    }

    private void declare_views(){
        tvBirthDay = findViewById(R.id.tv_birthday);
        tvGender = findViewById(R.id.tv_gender);
        tvJob = findViewById(R.id.tv_job);
//        tvSubject = findViewById(R.id.tv_subject);
        tvAddress = findViewById(R.id.tv_address);
//        tvWeekday = findViewById(R.id.tv_weekday);
        tvSession = findViewById(R.id.tv_amount_session);
        tvAmountSession = findViewById(R.id.tv_amount_attender);
        tvAmountAttender = findViewById(R.id.tv_amount_attender);
        tvInfoMore = findViewById(R.id.tv_more);
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
        tvInfoMore.setOnClickListener(this);

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
    }

}
