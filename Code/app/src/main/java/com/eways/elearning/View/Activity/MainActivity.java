package com.eways.elearning.View.Activity;

import android.graphics.Color;
import android.opengl.Visibility;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.eways.elearning.Handler.ImageHandler;
import com.eways.elearning.Model.Database.SharedPreferencesHandler;
import com.eways.elearning.Handler.FragmentHandler;
import com.eways.elearning.View.Fragment.KhoaHoc.TimKiemFragment;
import com.eways.elearning.View.Fragment.TaiKhoan.DangNhap.DangNhapFragment;
import com.eways.elearning.View.Fragment.TaiKhoan.QuanLyTaiKhoanFragment;
import com.eways.elearning.R;
import com.eways.elearning.Util.SupportKeysList;
import com.eways.elearning.View.Fragment.Home.HomeFragment;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
    TextView tvUserName, tvUserEmail;
    ImageView imgUser;
    public TextView tvScreenTitle;

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
        tvScreenTitle = (TextView) findViewById(R.id.textView_Title_Actionbar);
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
                } else if (slideOffset > 0.3) {
                    // started opening
                    tvUserName = (TextView) findViewById(R.id.user_name_nav_menu);
                    tvUserEmail = (TextView) findViewById(R.id.user_email_nav_menu);
                    imgUser = (ImageView) findViewById(R.id.user_ava_nav_menu);

                    //Set data
                    if (mySharedPref.getDaDangNhap()){
                        if (mySharedPref.getAvatar() != null && mySharedPref.getAvatar().compareTo("") != 0)
                            imageHandler.loadImageRound(mySharedPref.getAvatar(), imgUser);
                        else
                            imgUser.setImageBitmap(null);
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
        myToolbar.setNavigationOnClickListener(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        drawer.addDrawerListener(toggle);
        toggle.getDrawerArrowDrawable().setColor(Color.WHITE);
        toggle.syncState();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_bar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.content_main);
        if(currentFragment!=null && currentFragment.getTag()!=null) {
            switch (currentFragment.getTag()) {
                case SupportKeysList.TAG_DANH_SACH_KHOA_HOC:
                    menu.findItem(R.id.act_search).setVisible(true);
                    break;
                default:
                    menu.findItem(R.id.act_search).setVisible(false);
                    break;
            }
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.act_search:
                fragmentHandler.ChuyenFragment(new TimKiemFragment(), true, SupportKeysList.TAG_TIM_KIEM);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    //Set event cho slide menu
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        fragmentHandler.XoaTatCaFragment();

        switch (item.getItemId()){
            case R.id.nav_home:
                fragmentHandler.ChuyenFragment(new HomeFragment(), false, null);
                break;
            case R.id.nav_quan_ly_tai_khoan:
                if (mySharedPref.getDaDangNhap())
                    fragmentHandler.ChuyenFragment(new QuanLyTaiKhoanFragment(), true, SupportKeysList.TAG_QUAN_LY_TAI_KHOAN_FRAGMENT);
                else
                    fragmentHandler.ChuyenFragment(new DangNhapFragment(), true, SupportKeysList.TAG_DANG_NHAP_FRAGMENT);
                break;
        }

        ((DrawerLayout)findViewById(R.id.drawer_layout)).closeDrawer(Gravity.START);
        return false;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == -1){
            if (!(getSupportFragmentManager().findFragmentById(R.id.content_main) instanceof HomeFragment))
                this.onBackPressed();
            else
                ((DrawerLayout)findViewById(R.id.drawer_layout)).openDrawer(Gravity.START);
        }
    }

    @Override
    public void onBackPressed() {
        tvScreenTitle.setText("");
        super.onBackPressed();
    }
}