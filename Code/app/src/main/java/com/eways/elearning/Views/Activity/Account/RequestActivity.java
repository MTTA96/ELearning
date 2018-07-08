package com.eways.elearning.Views.Activity.Account;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.eways.elearning.Adapter.Request.RequestAdapter;
import com.eways.elearning.Model.Course.Course;
import com.eways.elearning.R;

import java.util.ArrayList;

public class RequestActivity extends AppCompatActivity {

    /** VIEWS */
    RecyclerView rcRequestion;

    /** ACCESSORIES */
    private ArrayList<Course> mListRequest;
    private RequestAdapter mRequestAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);

        declare_views();
        handle_views();
    }

    private void declare_views(){
        rcRequestion = findViewById(R.id.rc_request);
    }

    private void handle_views(){
        setUpList();
    }

    private void setUpList(){
        mListRequest = new ArrayList<>();

        rcRequestion.setHasFixedSize(true);

        mRequestAdapter = new RequestAdapter(this, mListRequest);

        LinearLayoutManager lm = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        rcRequestion.setLayoutManager(lm);

        rcRequestion.setAdapter(mRequestAdapter);
    }
}
