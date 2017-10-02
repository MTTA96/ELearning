package com.eways.elearning.View.Activity.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.eways.elearning.R;
import com.eways.elearning.View.Fragment.DangNhap.DangNhapFragment;
import com.facebook.FacebookSdk;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportFragmentManager().beginTransaction().replace(R.id.LoMain,new DangNhapFragment()).commit();
    }
}
