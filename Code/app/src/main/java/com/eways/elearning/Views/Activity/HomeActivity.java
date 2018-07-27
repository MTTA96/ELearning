package com.eways.elearning.Views.Activity;

import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.eways.elearning.Adapter.Search.SearchSuggestionsAdapter;
import com.eways.elearning.Interfaces.DataCallBack;
import com.eways.elearning.Interfaces.DataCallback.SearchSuggestion.SearchSuggestionCallBack;
import com.eways.elearning.Interfaces.OnItemClickListener;
import com.eways.elearning.Model.Search.SearchSuggestions;
import com.eways.elearning.Presenter.HomePresenter;
import com.eways.elearning.R;

import com.eways.elearning.Utils.ActivityUtils;
import com.eways.elearning.Utils.Handler.FragmentHandler;
import com.eways.elearning.Utils.SupportKeys;
import com.eways.elearning.Utils.ViewUtils;
import com.eways.elearning.Views.Activity.Account.UserManagerActivity;
import com.eways.elearning.Views.Fragment.HomeFragment;
import com.eways.elearning.Views.Fragment.SearchAndFilter.SearchFragment;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity implements  DataCallBack, OnItemClickListener, View.OnClickListener, SearchSuggestionCallBack {

    /**
     * VIEWS
     */
    Toolbar toolbar;
    RelativeLayout content;
    RecyclerView rvSuggestionsList;
    DrawerLayout mDrawerLayout;
    EditText etSearch;
    ImageView ivMenu, ivFIlter;
    NavigationView mNavigationView;
    ImageView mUserNameAvarta;
    TextView mUserNameHeader, mUserEmailHeader;
    ImageView ivBack;
    View bgToolBar;

    /**
     * MODELS
     */
    private HomePresenter homePresenter;
    private FragmentHandler fragmentHandler;
    private SearchSuggestionsAdapter searchSuggestionsAdapter;
    private ArrayList<SearchSuggestions> suggestionsList = new ArrayList<>();
    private boolean shouldSuggestionViewVisible = true;

    // Identify current search type
    public static int currentSearchType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        declareViews();
        handle();

        homePresenter = new HomePresenter(this, this);
    }

    /** CONFIG */

    public void declareViews(){
        toolbar = findViewById(R.id.toolbar);
        content = findViewById(R.id.content);
        rvSuggestionsList = findViewById(R.id.list_search);

        mDrawerLayout = findViewById(R.id.drawer_layout);
        mNavigationView = findViewById(R.id.nav_view);

        ivFIlter = findViewById(R.id.iv_filter);
        ivMenu = findViewById(R.id.iv_menu);
        etSearch = findViewById(R.id.et_search);
        ivBack = findViewById(R.id.iv_back);
        bgToolBar = findViewById(R.id.bg_toolbar);

//        mUserNameAvarta = findViewById(R.id)

//        mMenu = findViewById(R.id.menu);
    }

    public void handle() {
        fragmentHandler = new FragmentHandler(this, R.id.home_content_view);
        searchSuggestionsAdapter = new SearchSuggestionsAdapter(suggestionsList, this, R.layout.item_search_suggestions);
        SetUpActionBar();
//        mMenu.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                mDrawerLayout.openDrawer(Gravity.LEFT);
//            }
//        });
        // Configure suggestions view
        rvSuggestionsList.setLayoutManager(new LinearLayoutManager(getParent(), LinearLayoutManager.VERTICAL, false));
        rvSuggestionsList.hasFixedSize();
        rvSuggestionsList.setAdapter(searchSuggestionsAdapter);

        ivBack.setOnClickListener(this);

        // Move to home
        fragmentHandler.changeFragment(HomeFragment.newInstance(), SupportKeys.HOME_FRAGMENT_TAG, 0, 0);
        currentSearchType = SupportKeys.SEARCH_SUBJECTS;

        mNavigationView.setBackgroundColor(getResources().getColor(R.color.colorWhite));



//        mNavigationView.setBackgroundColor(GlobalParams.getInstance().getColor(R.color.colorWhite));
        mNavigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        switch (menuItem.getItemId()){
                            case R.id.nav_Home:


                                break;

                            case R.id.nav_quan_ly_tai_khoan:

                                ActivityUtils.ChangeActivity(HomeActivity.this, UserManagerActivity.class);
                                mDrawerLayout.closeDrawers();

                                break;

                            case R.id.nav_tao_khoa_hoc:

                                ActivityUtils.ChangeActivity(HomeActivity.this, CreateActivity.class);
                                break;
                        }

                        return true;
                    }
                });

        ivMenu.setOnClickListener(this);
        ivFIlter.setOnClickListener(this);

    }

    /**
     * Hide suggestions when the query is submitted
     */
    private void updateSuggestionsViewState() {
        if (shouldSuggestionViewVisible)
            rvSuggestionsList.setVisibility(View.VISIBLE);
        else
            rvSuggestionsList.setVisibility(View.GONE);
    }


    /** Search suggestion selected */
    @Override
    public void onItemClick(Bundle bundle) {
        String keyword = bundle.getString("keyword");
        shouldSuggestionViewVisible = false;
        updateSuggestionsViewState();
        fragmentHandler.changeFragment(SearchFragment.newInstance(keyword), null, 0, 0);
    }

    //set up toolbar
    private void SetUpActionBar(){
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.width = (ViewUtils.getScreenWidth(this)*67)/100;
        etSearch.setLayoutParams(lp);

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                shouldSuggestionViewVisible = true;
                updateSuggestionsViewState();
                if (editable.toString().compareTo("") == 0) {
                    suggestionsList.clear();
                    searchSuggestionsAdapter.notifyDataSetChanged();
                } else {
                    // Show loading when inputting
                    SearchSuggestions loadingSuggestions = new SearchSuggestions();
                    suggestionsList.clear();
                    loadingSuggestions.setSubjectName(getResources().getString(R.string.msg_loading));
                    suggestionsList.add(loadingSuggestions);
                    searchSuggestionsAdapter.notifyDataSetChanged();

                    // Call api
                    homePresenter.searchSuggestions(editable.toString());
                }
            }
        });
    }

    /**
     * Handle options menu item selected
     */
    @Override

    public boolean onOptionsItemSelected(MenuItem item) {
//        if (item.getItemId() == R.id.action_search) {
//            fragmentHandler.changeFragment(SearchFragment.newInstance(), SupportKeys.SEARCH_RESULTS_TAG, 0, 0);
//        }



        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.home_content_view);
        if (currentFragment instanceof SearchFragment)
            fragmentHandler.changeFragment(HomeFragment.newInstance(), SupportKeys.HOME_FRAGMENT_TAG, 0, 0);
    }

    /** HANDLE DATA FROM SERVER */
    @Override
    public void dataCallBack(int result, @Nullable Bundle bundle) {

    }

    @Override
    public void searchSuggestionCallBack(int errorCode, ArrayList result) {

        // Handle errors
        if (errorCode == SupportKeys.FAILED_CODE) {
            Log.d(getClass().getName(), "Search failed!");
            return;
        }

        // Get data success
        suggestionsList.clear();

        if (result.size() != 0) {
            suggestionsList.addAll(result);
        } else {
            SearchSuggestions noResults = new SearchSuggestions();
            noResults.setSubjectName("Không có kết quả");
            suggestionsList.add(noResults);
        }

        searchSuggestionsAdapter.notifyDataSetChanged();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_menu:

                mDrawerLayout.openDrawer(GravityCompat.START);
            break;

            case R.id.iv_filter:

                ActivityUtils.ChangeActivity(HomeActivity.this, FilterActivity.class);
                break;

            case R.id.iv_back:

                getSupportFragmentManager().popBackStack();
                ShowSearchBar();

                break;
        }
    }

    public void HideSearchBar(){
        ivFIlter.setVisibility(View.GONE);
        ivMenu.setVisibility(View.GONE);
        etSearch.setVisibility(View.GONE);
        ivBack.setVisibility(View.VISIBLE);
        bgToolBar.setBackgroundColor(getResources().getColor(R.color.colorBlue));
    }

    public void ShowSearchBar(){
        ivFIlter.setVisibility(View.VISIBLE);
        ivMenu.setVisibility(View.VISIBLE);
        etSearch.setVisibility(View.VISIBLE);
        ivBack.setVisibility(View.GONE);
        bgToolBar.setBackgroundResource(R.drawable.search_shape);

    }


}

