package com.eways.elearning.Views.Activity.Course;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.eways.elearning.R;
import com.eways.elearning.Utils.ViewUtils;

public class CreateCourseActivity extends Activity {
    EditText etSubject, etAddress, etTuition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_course);

        declare_views();
        handle_views();
    }

    private void declare_views(){}

    private void handle_views(){
    }

}
