package com.eways.elearning.Adapter.Search;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.eways.elearning.Model.Course;
import com.eways.elearning.Model.SearchResults;
import com.eways.elearning.Model.SearchSuggestions;
import com.eways.elearning.R;
import com.eways.elearning.Utils.Handler.FragmentHandler;
import com.eways.elearning.Utils.SupportKey;
import com.eways.elearning.Views.Fragment.SearchFragment;

import java.util.ArrayList;

/**
 * Created by ADMIN on 5/20/2018.
 */

public class SearchAdapter extends RecyclerView.Adapter<SearchVHolder> {
    private FragmentHandler fragmentHandler;
    private ArrayList<SearchSuggestions> list;
    int res;

    public SearchAdapter(ArrayList<SearchSuggestions> list, FragmentHandler fragmentHandler, int res) {
        this.fragmentHandler = fragmentHandler;
        this.list = list;
        this.res = res;
    }

    @Override
    public SearchVHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_search,parent,false);
        return new SearchVHolder(view);
    }

    @Override
    public void onBindViewHolder(SearchVHolder holder, int position) {
        final SearchSuggestions item = list.get(position);
        //load image with picasso here

        holder.subject.setText(item.getSubjectName());
        holder.subject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                fragmentHandler.changeFragment(SearchFragment.newInstance(item.getSubjectName()), SupportKey.SEARCH_RESULTS_TAG, 0, 0);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
