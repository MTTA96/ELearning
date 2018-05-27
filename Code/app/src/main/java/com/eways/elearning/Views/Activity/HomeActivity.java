package com.eways.elearning.Views.Activity;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

import com.eways.elearning.Adapter.Search.SearchAdapter;
import com.eways.elearning.Interfaces.DataCallBack;
import com.eways.elearning.Model.Course;
import com.eways.elearning.Model.SearchResults;
import com.eways.elearning.Model.SearchSuggestions;
import com.eways.elearning.Presenter.HomePresenter;
import com.eways.elearning.R;

import com.eways.elearning.Utils.Handler.FragmentHandler;
import com.eways.elearning.Utils.SupportKey;
import com.eways.elearning.Views.Fragment.HomeFragment;
import com.eways.elearning.Views.Fragment.SearchFragment;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener, DataCallBack {

    /** VIEWS */
    Toolbar toolbar;
    RelativeLayout content;
    RecyclerView rvSuggestionsList;

    /** MODELS */
    private HomePresenter homePresenter;
    private FragmentHandler fragmentHandler;
    private SearchAdapter searchAdapter;
    private ArrayList<SearchSuggestions> suggestionsList = new ArrayList<>();

    // Identify current search type
    public static int currentSearchType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        declareViews();
        handle();

        homePresenter = new HomePresenter(this);
    }

    public void declareViews(){
        toolbar = findViewById(R.id.toolbar);
        content = findViewById(R.id.content);
        rvSuggestionsList = findViewById(R.id.list_search);

    }

    public void handle(){
        fragmentHandler = new FragmentHandler(this, R.id.home_content_view);
        setUpToolBar();
        searchAdapter = new SearchAdapter(suggestionsList, fragmentHandler, R.layout.item_search);

        // Configure suggestions view
        rvSuggestionsList.setLayoutManager(new LinearLayoutManager(getParent(), LinearLayoutManager.VERTICAL, false));
        rvSuggestionsList.hasFixedSize();
        rvSuggestionsList.setAdapter(searchAdapter);

        // Move to home
        fragmentHandler.changeFragment(HomeFragment.newInstance(), SupportKey.HOME_FRAGMENT_TAG, 0, 0);
        currentSearchType = SupportKey.SEARCH_SUBJECTS;

    }

    public void setUpToolBar(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    /** EVENTS */

    @Override
    public void onClick(View view) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);

        MenuItem mSearch = menu.findItem(R.id.action_search);

        SearchView mSearchView = (SearchView) mSearch.getActionView();
        mSearchView.setQueryHint("Search");

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                fragmentHandler.changeFragment(SearchFragment.newInstance(query), SupportKey.SEARCH_RESULTS_TAG, 0, 0);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.compareTo("")==0){
                    suggestionsList.clear();
                    searchAdapter.notifyDataSetChanged();
                } else
                    homePresenter.searchSuggestions(newText);
                return true;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    /** Handle options menu item selected */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        if (item.getItemId() == R.id.action_search) {
//            fragmentHandler.changeFragment(SearchFragment.newInstance(), SupportKey.SEARCH_RESULTS_TAG, 0, 0);
//        }
        return super.onOptionsItemSelected(item);
    }

    /** Handle result from server */
    @Override
    public void dataCallBack(int result, @Nullable Bundle bundle) {
        // Handle errors
        if (result == SupportKey.FAILED_CODE) {
            Log.d(getClass().getName(), "Search failed!");
            return;
        }

        // Get data success
        ArrayList resultsList = (ArrayList<SearchResults>) bundle.getSerializable(null);

        suggestionsList.addAll(resultsList);
        searchAdapter.notifyDataSetChanged();

    }
}

