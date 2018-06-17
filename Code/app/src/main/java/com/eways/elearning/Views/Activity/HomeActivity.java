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

import com.eways.elearning.Adapter.Search.SearchSuggestionsAdapter;
import com.eways.elearning.Interfaces.DataCallBack;
import com.eways.elearning.Interfaces.OnItemClickListener;
import com.eways.elearning.Model.Search.SearchResults;
import com.eways.elearning.Model.Search.SearchSuggestions;
import com.eways.elearning.Presenter.HomePresenter;
import com.eways.elearning.R;

import com.eways.elearning.Utils.Handler.FragmentHandler;
import com.eways.elearning.Utils.SupportKeys;
import com.eways.elearning.Views.Fragment.HomeFragment;
import com.eways.elearning.Views.Fragment.SearchFragment;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity implements DataCallBack, OnItemClickListener {

    /** VIEWS */
    Toolbar toolbar;
    RelativeLayout content;
    RecyclerView rvSuggestionsList;
    SearchView mSearchView;

    /** MODELS */
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
        searchSuggestionsAdapter = new SearchSuggestionsAdapter(suggestionsList, this, R.layout.item_search_suggestions);

        // Configure suggestions view
        rvSuggestionsList.setLayoutManager(new LinearLayoutManager(getParent(), LinearLayoutManager.VERTICAL, false));
        rvSuggestionsList.hasFixedSize();
        rvSuggestionsList.setAdapter(searchSuggestionsAdapter);

        // Move to home
        fragmentHandler.changeFragment(HomeFragment.newInstance(), SupportKeys.HOME_FRAGMENT_TAG, 0, 0);
        currentSearchType = SupportKeys.SEARCH_SUBJECTS;

    }

    public void setUpToolBar(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    /**
     * Hide suggestions when the query is submitted
     * */
    private void updateSuggestionsViewState() {
        if (shouldSuggestionViewVisible)
            rvSuggestionsList.setVisibility(View.VISIBLE);
        else
            rvSuggestionsList.setVisibility(View.GONE);
    }

    /** EVENTS */

    @Override
    public void onItemClick(Bundle bundle) {
        String keyword = bundle.getString("keyword");
        shouldSuggestionViewVisible = false;
        updateSuggestionsViewState();
        mSearchView.setQuery(keyword, true);
        fragmentHandler.changeFragment(SearchFragment.newInstance(keyword), null, 0, 0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);

        MenuItem mSearch = menu.findItem(R.id.action_search);

        mSearchView = (SearchView) mSearch.getActionView();
        mSearchView.setQueryHint("Search");

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
                if (newText.compareTo("")==0){
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

    /** Handle options menu item selected */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        if (item.getItemId() == R.id.action_search) {
//            fragmentHandler.changeFragment(SearchFragment.newInstance(), SupportKeys.SEARCH_RESULTS_TAG, 0, 0);
//        }
        return super.onOptionsItemSelected(item);
    }

    /** Handle result from server */
    @Override
    public void dataCallBack(int result, @Nullable Bundle bundle) {
        // Handle errors
        if (result == SupportKeys.FAILED_CODE) {
            Log.d(getClass().getName(), "Search failed!");
            return;
        }

        // Get data success
        ArrayList resultsList = (ArrayList<SearchResults>) bundle.getSerializable(null);

        suggestionsList.clear();
        suggestionsList.addAll(resultsList);
        searchSuggestionsAdapter.notifyDataSetChanged();

    }

}

