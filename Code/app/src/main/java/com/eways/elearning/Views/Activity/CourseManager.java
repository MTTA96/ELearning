package com.eways.elearning.Views.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.eways.elearning.Adapter.Course.CourseManager.CourseManagerAdapter;
import com.eways.elearning.Model.Course.Course;
import com.eways.elearning.R;

import java.util.ArrayList;

public class CourseManager extends Activity implements View.OnClickListener{
    /** VIEWS */
    TextView tvPending, tvJoined, tvAll;
    RecyclerView rcCourse;

    /** STATICS */

    /** ACCESSORIES */
    private ArrayList<Course> mListCourse ;
    private ArrayList<Course> mListCourseShow;
    private CourseManagerAdapter mCourseAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_manager);


    }

    private void declare_views(){
        tvPending = findViewById(R.id.tv_pending);
        tvJoined = findViewById(R.id.tv_joined);
        tvAll = findViewById(R.id.tv_all);

        declare_views();
        handle_views();

    }

    private void handle_views(){

        tvJoined.setOnClickListener(this);
        tvPending.setOnClickListener(this);
        tvAll.setOnClickListener(this);
        rcCourse = findViewById(R.id.rc_course_manager);

        setUpListCourse();
    }

    //set filter
    public void filter(int mode){
        switch (mode){
            case 0:

                mListCourseShow.clear();
                mListCourseShow = mListCourse;

                mCourseAdapter.notifyDataSetChanged();
                break;

            case 1:

                for (int i = 0; i < mListCourse.size(); i++){
                    if (Integer.parseInt(mListCourse.get(i).getStatus()) == 0){
                        mListCourseShow.clear();
                        mListCourseShow.add(mListCourse.get(i));

                        mCourseAdapter.notifyDataSetChanged();
                    }
                }
                break;

            case 2:

                for (int i = 0; i < mListCourse.size(); i++){
                    if (Integer.parseInt(mListCourse.get(i).getStatus()) == 1){
                        mListCourseShow.clear();
                        mListCourseShow.add(mListCourse.get(i));

                        mCourseAdapter.notifyDataSetChanged();
                    }
                }
                break;
        }

    }

    private void setUpListCourse(){
        mListCourse = new ArrayList<>();
        mListCourseShow = new ArrayList<>();

        rcCourse.setHasFixedSize(true);
        LinearLayoutManager lm = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mCourseAdapter = new CourseManagerAdapter(mListCourse, this);
        rcCourse.setAdapter(mCourseAdapter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_pending:
                filter(1);

                break;

            case R.id.tv_all:
                filter(0);

                break;

            case R.id.tv_joined:
                filter(2);

                break;
        }
    }
}
