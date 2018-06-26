package com.eways.elearning.Views.Activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.eways.elearning.Adapter.Search.SearchSuggestionsAdapter;
import com.eways.elearning.Interfaces.DataCallBack;
import com.eways.elearning.Interfaces.DataCallback.SearchSuggestion.SearchSuggestionCallBack;
import com.eways.elearning.Interfaces.OnItemClickListener;
import com.eways.elearning.Model.Search.SearchResults;
import com.eways.elearning.Model.Search.SearchSuggestions;
import com.eways.elearning.Presenter.HomePresenter;
import com.eways.elearning.R;

import com.eways.elearning.Utils.ActivityUtils;
import com.eways.elearning.Utils.Handler.FragmentHandler;
import com.eways.elearning.Utils.SupportKeys;
import com.eways.elearning.Utils.ViewUtils;
import com.eways.elearning.Utils.params.GlobalParams;
import com.eways.elearning.Views.Fragment.HomeFragment;
import com.eways.elearning.Views.Fragment.SearchAndFilter.SearchFragment;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity implements DataCallBack, OnItemClickListener, SearchSuggestionCallBack {

    /**
     * VIEWS
     */
    Toolbar toolbar;
    RelativeLayout content;
    RecyclerView rvSuggestionsList;
    SearchView mSearchView;
    DrawerLayout mDrawerLayout;
    NavigationView mNavigationView;
    ImageView mUserNameAvarta;
    TextView mUserNameHeader, mUserEmailHeader;

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

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
//
//        MenuItem mSearch = menu.findItem(R.id.action_search);
//
//        mSearchView = (SearchView) mSearch.getActionView();
//        mSearchView.setQueryHint("Search");
//
//        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                shouldSuggestionViewVisible = false;
//                updateSuggestionsViewState();
//                fragmentHandler.changeFragment(SearchFragment.newInstance(query), null, 0, 0);
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                shouldSuggestionViewVisible = true;
//                updateSuggestionsViewState();
//                if (newText.compareTo("")==0){
//                    suggestionsList.clear();
//                    searchSuggestionsAdapter.notifyDataSetChanged();
//                } else {
//                    // Show loading when inputting
//                    SearchSuggestions loadingSuggestions = new SearchSuggestions();
//                    suggestionsList.clear();
//                    loadingSuggestions.setSubjectName(getResources().getString(R.string.msg_loading));
//                    suggestionsList.add(loadingSuggestions);
//                    searchSuggestionsAdapter.notifyDataSetChanged();
//
//                    // Call api
//                    homePresenter.searchSuggestions(newText);
//                }
//                return true;
//            }
//        });
//
//        return super.onCreateOptionsMenu(menu);
//    }

    /** CONFIG */

    public void declareViews(){
        toolbar = findViewById(R.id.toolbar);
        content = findViewById(R.id.content);
        rvSuggestionsList = findViewById(R.id.list_search);

        mDrawerLayout = findViewById(R.id.drawer_layout);
        mNavigationView = findViewById(R.id.nav_view);

//        mUserNameAvarta = findViewById(R.id)

//        mMenu = findViewById(R.id.menu);

    }

    public void handle() {
        fragmentHandler = new FragmentHandler(this, R.id.home_content_view);
        setUpToolBar();
        searchSuggestionsAdapter = new SearchSuggestionsAdapter(suggestionsList, this, R.layout.item_search_suggestions);

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

        // Move to home
        fragmentHandler.changeFragment(HomeFragment.newInstance(), SupportKeys.HOME_FRAGMENT_TAG, 0, 0);
        currentSearchType = SupportKeys.SEARCH_SUBJECTS;

        mNavigationView.setBackgroundColor(getColor(R.color.colorWhite));



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


    }

    public void setUpToolBar() {
        setSupportActionBar(toolbar);
        toolbar.setContentInsetStartWithNavigation(0);
        toolbar.setContentInsetsRelative(0,0);
        if(getSupportActionBar()!=null){
            Drawable drawable= getResources().getDrawable(R.drawable.icn_menu);
            Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
            Drawable newdrawable = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, 60, 60, true));
//            newdrawable.setColorFilter(Color.BLUE, PorterDuff.Mode.SRC_ATOP);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(newdrawable);

        }


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
        mSearchView.setQuery(keyword, true);
        mSearchView.setBackgroundColor(GlobalParams.getInstance().getColor(R.color.colorWhite));
        fragmentHandler.changeFragment(SearchFragment.newInstance(keyword), null, 0, 0);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);

        MenuItem mSearch = menu.findItem(R.id.action_search);
        mSearch.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

        mSearchView = (SearchView) mSearch.getActionView();
        mSearchView.setQueryHint("Search");
        mSearchView.onActionViewExpanded();
        mSearchView.clearFocus();

        mSearchView.setMaxWidth((ViewUtils.getScreenWidth(this)*70)/100);


        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                shouldSuggestionViewVisible = false;
                updateSuggestionsViewState();
                fragmentHandler.changeFragment(SearchFragment.newInstance(query), null, 0, 0);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                shouldSuggestionViewVisible = true;
                updateSuggestionsViewState();
                if (newText.compareTo("") == 0) {
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
                    homePresenter.searchSuggestions(newText);
                }
                return true;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    /**
     * Handle options menu item selected
     */
    @Override

    public boolean onOptionsItemSelected(MenuItem item) {
//        if (item.getItemId() == R.id.action_search) {
//            fragmentHandler.changeFragment(SearchFragment.newInstance(), SupportKeys.SEARCH_RESULTS_TAG, 0, 0);
//        }

        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;

            case R.id.action_filter:
                ActivityUtils.ChangeActivity(HomeActivity.this, FilterActivity.class);

                break;
        }

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

}

