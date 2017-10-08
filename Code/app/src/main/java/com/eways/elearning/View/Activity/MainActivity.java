package com.eways.elearning.View.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.eways.elearning.Model.Database.FireBaseHandler;
import com.eways.elearning.R;
import com.eways.elearning.View.Fragment.TaiKhoan.DangNhap.DangNhapFragment;

public class MainActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_tool_bar);
        setSupportActionBar(myToolbar);
        
    }
}