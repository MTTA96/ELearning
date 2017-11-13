package com.eways.elearning.View.Activity;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.eways.elearning.Handler.ImageHandler;
import com.eways.elearning.Model.Database.SharedPreferencesHandler;
import com.eways.elearning.Handler.FragmentHandler;
import com.eways.elearning.View.Fragment.TaiKhoan.DangNhap.DangNhapFragment;
import com.eways.elearning.View.Fragment.TaiKhoan.QuanLyTaiKhoanFragment;
import com.eways.elearning.R;
import com.eways.elearning.Util.SupportKeysList;
import com.eways.elearning.View.Fragment.Home.HomeFragment;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    TextView tvUserName;
    TextView tvUserEmail;
    ImageView imgUser;

    private FragmentHandler fragmentHandler;
    private SharedPreferencesHandler mySharedPref;
    private ImageHandler imageHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_tool_bar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        //Set sự kiện
        navigationView.setNavigationItemSelectedListener(this);

        setUpActionBar(drawer, myToolbar);
        mySharedPref = new SharedPreferencesHandler(this, SupportKeysList.SHARED_PREF_FILE_NAME);
        imageHandler = new ImageHandler(this);
        fragmentHandler = new FragmentHandler(this, getSupportFragmentManager());
        fragmentHandler.ChuyenFragment(new HomeFragment(), false, null);

    }

    private void setUpActionBar(DrawerLayout drawer, Toolbar myToolbar) {
        //Thay Actionbar mặc định bằng toolbar
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        //Đồng bộ toolbar và slide menu
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, myToolbar, 0, 0){
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                if (slideOffset == 0) {
                    // drawer closed
                    Log.d("MainActivity", String.valueOf(slideOffset));
                } else if (slideOffset > 0.3) {
                    // started opening
                    Log.d("MainActivity", String.valueOf(slideOffset));
                    tvUserName = (TextView) findViewById(R.id.user_name_nav_menu);
                    tvUserEmail = (TextView) findViewById(R.id.user_email_nav_menu);
                    imgUser = (ImageView) findViewById(R.id.user_ava_nav_menu);

                    //Set data
                    if (mySharedPref.getDaDangNhap()){
                        if (mySharedPref.getAvatar() != null && mySharedPref.getAvatar().compareTo("") != 0)
                            imageHandler.loadImageRound(mySharedPref.getAvatar(), imgUser);
                        tvUserEmail.setText(mySharedPref.getEmail());
                        if (mySharedPref.getTen().length()==0)
                            tvUserName.setVisibility(View.INVISIBLE);
                        else
                            tvUserName.setText(mySharedPref.getHo() + " " + mySharedPref.getTen());
                    }
                    else {
                        tvUserName.setText(R.string.header_msg_chua_dang_nhap);
                        tvUserEmail.setText("");
                    }
                }
                super.onDrawerSlide(drawerView, slideOffset);
            }

        };
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        drawer.addDrawerListener(toggle);
        toggle.getDrawerArrowDrawable().setColor(Color.WHITE);
        toggle.syncState();
    }


    //Set event cho slide menu
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        fragmentHandler.XoaTatCaFragment();

        if (item.getItemId() == R.id.act_quan_ly_tai_khoan){
            if (mySharedPref.getDaDangNhap())
                fragmentHandler.ChuyenFragment(new QuanLyTaiKhoanFragment(), true, SupportKeysList.TAG_QUAN_LY_TAI_KHOAN_FRAGMENT);
            else
                fragmentHandler.ChuyenFragment(new DangNhapFragment(), true, SupportKeysList.TAG_DANG_NHAP_FRAGMENT);
        }

        ((DrawerLayout)findViewById(R.id.drawer_layout)).closeDrawer(Gravity.START);
        return false;
    }
}