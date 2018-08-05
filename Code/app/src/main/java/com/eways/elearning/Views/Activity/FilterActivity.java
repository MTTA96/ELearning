package com.eways.elearning.Views.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.eways.elearning.R;

public class FilterActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        findViewById(R.id.btn_exit_filter).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_exit_filter:
                finish();
        }
    }
}
