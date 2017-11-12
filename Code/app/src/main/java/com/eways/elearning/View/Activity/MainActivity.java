package com.eways.elearning.View.Activity;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;

import com.eways.elearning.Model.Database.SharedPreferencesHandler;
import com.eways.elearning.Handler.FragmentHandler;
import com.eways.elearning.View.Fragment.TaiKhoan.QuanLyTaiKhoanFragment;
import com.eways.elearning.R;
import com.eways.elearning.Util.SupportKeysList;
import com.eways.elearning.View.Fragment.Home.HomeFragment;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private FragmentHandler fragmentHandler;
    private SharedPreferencesHandler mySharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_tool_bar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        //Set sự kiện
        navigationView.setNavigationItemSelectedListener(this);

        //Thay Actionbar mặc định bằng toolbar
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        //Đồng bộ toolbar và slide menu
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, myToolbar, 0, 0);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        drawer.addDrawerListener(toggle);
        toggle.getDrawerArrowDrawable().setColor(Color.WHITE);
        toggle.syncState();

        mySharedPref = new SharedPreferencesHandler(this, SupportKeysList.SHARED_PREF_FILE_NAME);
        fragmentHandler = new FragmentHandler(this, getSupportFragmentManager());
        fragmentHandler.ChuyenFragment(new HomeFragment(), false, null);
    }


    //Set event cho slide menu
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        fragmentHandler.XoaTatCaFragment();

        if (item.getItemId() == R.id.act_quan_ly_tai_khoan){
            fragmentHandler.ChuyenFragment(new QuanLyTaiKhoanFragment(), true, SupportKeysList.TAG_QUAN_LY_TAI_KHOAN_FRAGMENT);
        }

        ((DrawerLayout)findViewById(R.id.drawer_layout)).closeDrawer(Gravity.START);
        return false;
    }
}