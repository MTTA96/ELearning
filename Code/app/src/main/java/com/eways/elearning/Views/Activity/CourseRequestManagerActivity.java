package com.eways.elearning.Views.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.eways.elearning.Adapter.Course.CourseManager.CourseAttendAdapter;
import com.eways.elearning.Model.RequestSend;
import com.eways.elearning.R;

import java.util.ArrayList;

public class CourseRequestManagerActivity extends Activity {
    RecyclerView rcCourseAttend;
    CourseAttendAdapter mCourseAttendAdapter;
    ArrayList<RequestSend> mListRequestSend;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_request_manager);

        declare_views();
        handle_views();
    }

    private void handle_views(){
        SetUpList();
    }

    private void declare_views(){
        rcCourseAttend = findViewById(R.id.rc_course_attend);
    }

    private void SetUpList(){
        mListRequestSend = new ArrayList<>();
        mListRequestSend.add(new RequestSend());
        mListRequestSend.add(new RequestSend());

        mCourseAttendAdapter = new CourseAttendAdapter(this, mListRequestSend);
        rcCourseAttend.hasFixedSize();
        rcCourseAttend.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        rcCourseAttend.setAdapter(mCourseAttendAdapter);
    }


}
