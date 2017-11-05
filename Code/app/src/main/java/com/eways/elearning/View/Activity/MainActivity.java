package com.eways.elearning.View.Activity;

import android.graphics.Color;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.eways.elearning.CapNhatThongTinTaiKhoan;
import com.eways.elearning.Model.FragmentHandler;
import com.eways.elearning.R;
import com.eways.elearning.View.Fragment.Home.HomeFragment;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity{
    private FragmentHandler fragmentHandler;
    public FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Thay Actionbar mặc định bằng toolbar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_tool_bar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        //Đồng bộ toolbar và slide menu
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, myToolbar, 0, 0);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        drawer.addDrawerListener(toggle);
        toggle.getDrawerArrowDrawable().setColor(Color.WHITE);
        toggle.syncState();

//        mAuth=FirebaseAuth.getInstance();

        fragmentHandler = new FragmentHandler(this, getSupportFragmentManager());
        fragmentHandler.ChuyenFragment(new HomeFragment(), false, null);

    }
}