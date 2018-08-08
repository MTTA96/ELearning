package com.eways.elearning.Views.Activity.Course;

import android.app.Activity;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.eways.elearning.Adapter.User.UserListAdapter;
import com.eways.elearning.ApiNew.apiNew;
import com.eways.elearning.ApiNew.clientNew;
import com.eways.elearning.Interfaces.DataCallback.User.SendRequestCallback;
import com.eways.elearning.Model.CreateCourse;
import com.eways.elearning.Model.Subject.Subject;
import com.eways.elearning.Presenter.Authentication.UserPresenter;
import com.eways.elearning.R;
import com.eways.elearning.Utils.ViewUtils;
import com.eways.elearning.Utils.params.GlobalParams;
import com.google.android.gms.common.api.Api;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CreateCourseActivity extends Activity implements SendRequestCallback {
    EditText  etAddress, etTuition, etTime, etDesc;
    Spinner etSubject;
    private UserPresenter userPresenter;

    apiNew api;
    Retrofit retrofit;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_course);

        declare_views();
        handle_views();
    }

    private void declare_views(){
        etSubject = findViewById(R.id.et_subject);
        etAddress = findViewById(R.id.et_address);
        etTime = findViewById(R.id.et_time);
        etTuition = findViewById(R.id.et_tuition);
        etDesc = findViewById(R.id.et_desc);
    }

    private void handle_views(){

        initRetrofit();

        userPresenter = new UserPresenter(this);
        String tutorId = this.getIntent().getStringExtra("TUTOR_ID");

        Call<String> caller1 = api.getSubjectId(Integer.parseInt(tutorId));
        caller1.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()){
                    Response
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }


    @Override
    public void sendRequestCallback(int resultCode, @Nullable String msg) {

    }

    private void initRetrofit() {
        // Get client as retrofit.
        retrofit = clientNew.getClient();

        // Initiate Api service.
        api = retrofit.create(apiNew.class);
    }
}
