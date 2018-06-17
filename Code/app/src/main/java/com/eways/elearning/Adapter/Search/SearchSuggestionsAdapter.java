package com.eways.elearning.Adapter.Search;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eways.elearning.Interfaces.OnItemClickListener;
import com.eways.elearning.Model.Search.SearchSuggestions;
import com.eways.elearning.R;

import java.util.ArrayList;

/**
 * Created by ADMIN on 5/20/2018.
 */

public class SearchSuggestionsAdapter extends RecyclerView.Adapter<SearchVHolder> {
    private OnItemClickListener onItemClickListener;
    private ArrayList<SearchSuggestions> list;
    int res;

    public SearchSuggestionsAdapter(ArrayList<SearchSuggestions> list, OnItemClickListener onItemClickListener, int res) {
        this.onItemClickListener = onItemClickListener;
        this.list = list;
        this.res = res;
    }

    @Override
    public SearchVHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_search_suggestions,parent,false);
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
                Bundle itemBundle = new Bundle();
                itemBundle.putString("keyword", item.getSubjectName());
                onItemClickListener.onItemClick(itemBundle);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
