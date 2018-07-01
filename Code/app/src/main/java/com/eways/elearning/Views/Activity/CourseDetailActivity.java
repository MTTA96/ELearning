package com.eways.elearning.Views.Activity;

import android.app.Activity;
import android.media.Image;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.eways.elearning.R;

public class CourseDetailActivity extends Activity {
    /** VIEWS */
    TextView tvBirthDay, tvGender, tvJob, tvSubject, tvAddress, tvWeekday, tvSession, tvAmountSession, tvAmountAttender, tvInfoMore, tvSave;
    ImageView ivAvarta, ivBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detail);

        declare_views();
    }

    private void declare_views(){
        tvBirthDay = findViewById(R.id.tv_birthday);
        tvGender = findViewById(R.id.tv_gender);
        tvJob = findViewById(R.id.tv_job);
        tvSubject = findViewById(R.id.tv_subject);
        tvAddress = findViewById(R.id.tv_address);
        tvWeekday = findViewById(R.id.tv_weekday);
        tvSession = findViewById(R.id.tv_amount_session);
        tvAmountSession = findViewById(R.id.tv_amount_attender);
        tvAmountAttender = findViewById(R.id.tv_amount_attender);
        tvInfoMore = findViewById(R.id.tv_info_more);
        tvSave = findViewById(R.id.tv_save);

        ivAvarta = findViewById(R.id.iv_avarta);
    }
}
