package com.eways.elearning.Views.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.eways.elearning.Adapter.Request.RequestAdapter;
import com.eways.elearning.Model.Requestion;
import com.eways.elearning.R;

import java.util.ArrayList;

public class RequestManagerActivity extends AppCompatActivity {
    RecyclerView rcRequest;

    RequestAdapter mRequestAdapter;
    ArrayList<Requestion> mListRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_manager);

        declare_views();
        handle_views();
    }

    private void declare_views(){
        rcRequest = findViewById(R.id.rc_request);
    }

    private void handle_views(){
        SetUpList();
    }

    private void SetUpList(){
        mListRequest = new ArrayList<>();
        mListRequest.add(new Requestion());

        mRequestAdapter = new RequestAdapter(this, mListRequest);
        rcRequest.setHasFixedSize(true);

        rcRequest.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        rcRequest.setAdapter(mRequestAdapter);
    }
}
