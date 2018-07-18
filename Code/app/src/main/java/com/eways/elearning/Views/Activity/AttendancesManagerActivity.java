package com.eways.elearning.Views.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.BinderThread;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.eways.elearning.Adapter.Course.AttendAdapter;
import com.eways.elearning.Model.Attend;
import com.eways.elearning.R;

import java.util.ArrayList;

public class AttendancesManagerActivity extends Activity {

    RecyclerView rcAttend;
    ArrayList<Attend> mListAttend;
    AttendAdapter mAttendAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendances_manager);

        declare_views();
        handle_views();
    }

    private void declare_views(){
        rcAttend = findViewById(R.id.rc_attends);
    }

    private void handle_views(){
        SetUpList();
    }

    private void SetUpList(){
        mListAttend = new ArrayList<>();
        mListAttend.add(new Attend());
        mAttendAdapter = new AttendAdapter(mListAttend, this);

        rcAttend.setHasFixedSize(true);
        rcAttend.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        rcAttend.setAdapter(mAttendAdapter);
    }
}
