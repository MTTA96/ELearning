package com.eways.elearning.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.eways.elearning.Handler.FragmentHandler;
import com.eways.elearning.R;

public class CourseActivity extends AppCompatActivity {

    FragmentHandler fragmentHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);

        Handle();
    }

    public void Handle(){
        fragmentHandler = new FragmentHandler(this, R.id.content_course);
//        fragmentHandler.changeFragment(new FragmentUpdateDetail(), 0, 0);
    }

    public void Declare(){

    }
}
