package com.eways.elearning.Views.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.eways.elearning.Model.Account.User;
import com.eways.elearning.R;
import com.eways.elearning.Utils.ActivityUtils;
import com.eways.elearning.Utils.Handler.FragmentHandler;

public class UserManagerActivity extends Activity implements View.OnClickListener{
    /* VIEWS */

    ImageView avarta;
    TextView userName, userEmail;
    View btnInfo, btnMyCourse, btnCourseTutor, btnLogout;

    FragmentHandler fragmentHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_manager);

        declare_views();
        handle_views();
    }

    public void declare_views(){
        btnInfo = findViewById(R.id.btn_info);
        avarta = findViewById(R.id.avarta);
        userName = findViewById(R.id.userName);
        userEmail = findViewById(R.id.userEmail);
        btnCourseTutor = findViewById(R.id.btn_course_tutor);
        btnLogout = findViewById(R.id.btn_logout);
        btnMyCourse = findViewById(R.id.btn_my_course);
    }

    public void handle_views(){
//        fragmentHandler = new FragmentHandler(getActivity(), R.id.content_course);

        btnInfo.setOnClickListener(this);
        btnCourseTutor.setOnClickListener(this);
        btnMyCourse.setOnClickListener(this);
        btnLogout.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_info:
                ActivityUtils.ChangeActivity(UserManagerActivity.this, UpdateInfoActivity.class);

                break;

            case R.id.btn_course_tutor:

                ActivityUtils.ChangeActivity(UserManagerActivity.this, CourseManager.class);
                break;

            case R.id.btn_my_course:

                break;
        }
    }
}
