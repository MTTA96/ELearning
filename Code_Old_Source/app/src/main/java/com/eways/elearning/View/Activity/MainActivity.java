package com.eways.elearning.View.Activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.eways.elearning.Handler.FragmentHandler;
import com.eways.elearning.Handler.ImageHandler;
import com.eways.elearning.Model.Database.SharedPreferencesHandler;
import com.eways.elearning.Presenter.DanhSachMon.DanhSachMonPresenter;
import com.eways.elearning.Presenter.DanhSachMon.DanhSachMonPresenterImp;
import com.eways.elearning.R;
import com.eways.elearning.Util.SupportKeysList;
import com.eways.elearning.View.Dialog.LoadingDialog;
import com.eways.elearning.View.Fragment.Home.NewHomeFragment;
import com.eways.elearning.View.Fragment.KhoaHoc.TaoKhoaHoc.TaoKhoaHocFragment;
import com.eways.elearning.View.Fragment.KhoaHoc.TimKiemKhoaHoc.KetQuaTimKiemFragment;
import com.eways.elearning.View.Fragment.KhoaHoc.TimKiemKhoaHoc.TimKiemFragment;
import com.eways.elearning.View.Fragment.TaiKhoan.DangNhap.DangNhapFragment;
import com.eways.elearning.View.Fragment.TaiKhoan.QuanLyTaiKhoanFragment;
import com.eways.elearning.View.Fragment.TaiKhoan.ThongTinTaiKhoan.CapNhatTaiKhoan.CapNhatThongTinTaiKhoanFragment;

import java.util.ArrayList;

import br.com.mauker.materialsearchview.MaterialSearchView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener, MainActivityImp, AdapterView.OnItemClickListener, MaterialSearchView.OnVoiceClickedListener {
    TextView tvUserName, tvUserEmail;
    ImageView imgUser;
    public TextView tvScreenTitle;
    private MaterialSearchView searchView;

    private FragmentHandler fragmentHandler;
    private LoadingDialog loadingDialog;
    private SharedPreferencesHandler mySharedPref;
    private ImageHandler imageHandler;
    private DanhSachMonPresenterImp danhSachMonPresenterImp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar myToolbar = findViewById(R.id.my_tool_bar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        tvScreenTitle = findViewById(R.id.textView_Title_Actionbar);
        searchView = findViewById(R.id.search_view); //Search bar

        //Set sự kiện
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getHeaderView(0).setOnClickListener(this);
        findViewById(R.id.search_home).setOnClickListener(this);
        findViewById(R.id.nav_menu_home).setOnClickListener(this);
        findViewById(R.id.text_Search_Actionbar).setOnClickListener(this);
        searchView.setOnItemClickListener(this);
        searchView.setOnVoiceClickedListener(this);

        setUpActionBar(drawer, myToolbar);
        loadingDialog = LoadingDialog.getInstance(this);
        danhSachMonPresenterImp = new DanhSachMonPresenter(this);
        danhSachMonPresenterImp.guiYeuCau();
        mySharedPref = new SharedPreferencesHandler(this, SupportKeysList.SHARED_PREF_FILE_NAME);
        imageHandler = new ImageHandler(this);
//        fragmentHandler = new FragmentHandler(this, getSupportFragmentManager());
//        fragmentHandler.ChuyenFragment(new NewHomeFragment(), false, SupportKeysList.TAG_HOME_FRAGMENT);
    }

    private void setUpActionBar(DrawerLayout drawer, Toolbar myToolbar) {
        //Thay Actionbar mặc định bằng toolbar
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        //Đồng bộ toolbar và slide menu
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, myToolbar, 0, 0) {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                if (slideOffset == 0) {
                    // drawer closed
                } else if (slideOffset > 0.3) {
                    // started opening
                    tvUserName = findViewById(R.id.user_name_nav_menu);
                    tvUserEmail = findViewById(R.id.user_email_nav_menu);
                    imgUser = findViewById(R.id.user_ava_nav_menu);

                    //Set data
                    if (mySharedPref.getDaDangNhap()) {
                        imageHandler.loadImageRound(mySharedPref.getAvatar(), imgUser);
                        tvUserEmail.setText(mySharedPref.getEmail());
                        tvUserName.setText(mySharedPref.getLastName() + " " + mySharedPref.getFirstName());
                    } else {
                        tvUserName.setText(R.string.header_msg_chua_dang_nhap);
                        tvUserEmail.setText("");
                        imgUser.setImageBitmap(null);
                    }
                }
                super.onDrawerSlide(drawerView, slideOffset);
            }

        };
        myToolbar.setNavigationOnClickListener(this);

//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
        if (currentFragment != null && currentFragment.getTag() != null) {
            switch (currentFragment.getTag()) {
                case SupportKeysList.TAG_HOME_FRAGMENT:
                case SupportKeysList.TAG_DANH_SACH_KHOA_HOC:
                case SupportKeysList.TAG_KET_QUA_TIM_KIEM:
                    findViewById(R.id.search_layout).setVisibility(View.VISIBLE);
                    tvScreenTitle.setVisibility(View.GONE);
                    menu.findItem(R.id.act_search).setVisible(false);
                    menu.findItem(R.id.act_save).setVisible(false);
                    break;

                //Tài khoản
                case SupportKeysList.TAG_CAP_NHAT_TAI_LIEU_CHUYEN_MON:
                case SupportKeysList.TAG_THEM_LINH_VUC_CHUYEN_MON:
                case SupportKeysList.TAG_CAP_NHAT_THONG_TIN_CA_NHAN:
                    findViewById(R.id.search_layout).setVisibility(View.GONE);
                    tvScreenTitle.setVisibility(View.VISIBLE);
                    menu.findItem(R.id.act_search).setVisible(false);
                    menu.findItem(R.id.act_save).setVisible(true);
                    break;
                case SupportKeysList.TAG_DIEU_KHOAN_GIA_SU:
                    tvScreenTitle.setText(getString(R.string.title_dieu_khoan_gia_su));
                case SupportKeysList.TAG_DANG_NHAP_FRAGMENT:
                case SupportKeysList.TAG_DANG_KY_FRAGMENT:
                case SupportKeysList.TAG_QUAN_LY_TAI_KHOAN_FRAGMENT:
                case SupportKeysList.TAG_KHOA_HOC_CUA_TOI:
                case SupportKeysList.TAG_THONG_TIN_KHOA_HOC:
                case SupportKeysList.TAG_THONG_TIN_NGUOI_DANG:
                case SupportKeysList.TAG_TAO_KHOA_HOC:
                    findViewById(R.id.search_layout).setVisibility(View.GONE);
                    tvScreenTitle.setVisibility(View.VISIBLE);
                    menu.findItem(R.id.act_search).setVisible(false);
                    menu.findItem(R.id.act_save).setVisible(false);
                    break;
                default:
//                    findViewById(R.id.search_layout).setVisibility(View.GONE);
//                    tvScreenTitle.setVisibility(View.VISIBLE);
//                    menu.findItem(R.id.act_search).setVisible(true);
//                    menu.findItem(R.id.act_save).setVisible(false);
                    break;
            }
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.act_search:
//                fragmentHandler.ChuyenFragment(new TimKiemFragment(), true, SupportKeysList.TAG_TIM_KIEM);
                searchView.openSearch();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    //Set event cho slide menu
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//        fragmentHandler.XoaTatCaFragment();

        switch (item.getItemId()) {
            case R.id.nav_home:
//                fragmentHandler.ChuyenFragment(new NewHomeFragment(), false, SupportKeysList.TAG_HOME_FRAGMENT);
                break;
            case R.id.nav_quan_ly_tai_khoan:
//                if (mySharedPref.getDaDangNhap())
//                    fragmentHandler.ChuyenFragment(new QuanLyTaiKhoanFragment(), true, SupportKeysList.TAG_QUAN_LY_TAI_KHOAN_FRAGMENT);
//                else
//                    fragmentHandler.ChuyenFragment(new DangNhapFragment(), true, SupportKeysList.TAG_DANG_NHAP_FRAGMENT);
                break;
            case R.id.nav_tao_khoa_hoc:
//                if (!mySharedPref.getDaDangNhap())
//                    fragmentHandler.ChuyenFragment(new DangNhapFragment(), true, SupportKeysList.TAG_DANG_NHAP_FRAGMENT);
//                else {
////                    if (mySharedPref.i())
////                        fragmentHandler.ChuyenFragment(new TaoKhoaHocFragment(), true, SupportKeysList.TAG_TAO_KHOA_HOC);
////                    else {
////                        Toast.makeText(this, getResources().getString(R.string.msg_cap_nhat_thong_tin), Toast.LENGTH_SHORT).show();
////                        fragmentHandler.ChuyenFragment(new CapNhatThongTinTaiKhoanFragment(), true, SupportKeysList.TAG_CAP_NHAT_THONG_TIN_CA_NHAN);
////                    }
//                }
                break;
        }

        ((DrawerLayout) findViewById(R.id.drawer_layout)).closeDrawer(Gravity.START);
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case -1:
                if (!(getSupportFragmentManager().findFragmentById(R.id.content_main) instanceof NewHomeFragment))
                    this.onBackPressed();
                else
                    ((DrawerLayout) findViewById(R.id.drawer_layout)).openDrawer(Gravity.START);
                break;
            case R.id.nav_header:
//                if (mySharedPref.getDaDangNhap())
//                    fragmentHandler.ChuyenFragment(new QuanLyTaiKhoanFragment(), true, SupportKeysList.TAG_QUAN_LY_TAI_KHOAN_FRAGMENT);
//                else
//                    fragmentHandler.ChuyenFragment(new DangNhapFragment(), true, SupportKeysList.TAG_DANG_NHAP_FRAGMENT);
//                ((DrawerLayout) findViewById(R.id.drawer_layout)).closeDrawer(Gravity.START);
                break;
            case R.id.nav_menu_home:
                ((DrawerLayout) findViewById(R.id.drawer_layout)).openDrawer(Gravity.START);
                break;
            case R.id.text_Search_Actionbar:
                searchView.openSearch();
                break;
            case R.id.search_home:
//                fragmentHandler.ChuyenFragment(new TimKiemFragment(), true, SupportKeysList.TAG_TIM_KIEM);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.content_main);
        if (fragment.getTag() != null)
//            if (fragment.getTag().compareTo(SupportKeysList.TAG_KET_QUA_TIM_KIEM) == 0) {
//                fragmentHandler.XoaTatCaFragment();
//                ((TextView) findViewById(R.id.text_Search_Actionbar)).setHint(getResources().getString(R.string.app_name));
//            }
        if (searchView.isOpen()) {
            // Close the search on the back button press.
            searchView.closeSearch();
        } else {
            tvScreenTitle.setText("");
            super.onBackPressed();
        }
    }

    @Override
    public void NhanDanhSachMon(ArrayList<String> dsMon) {
        searchView.addSuggestions(dsMon);
    }

    /**
     * Xử lý seach bar
     * */
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        String suggestion = searchView.getSuggestionAtPosition(position);
//        fragmentHandler.ChuyenFragment(KetQuaTimKiemFragment.newInstance(suggestion), true, SupportKeysList.TAG_KET_QUA_TIM_KIEM);
        searchView.closeSearch();
        ((TextView) findViewById(R.id.text_Search_Actionbar)).setHint(suggestion);
    }

    @Override
    public void onVoiceClicked() {
        Toast.makeText(this, "Comming soon!", Toast.LENGTH_SHORT).show();
    }
}