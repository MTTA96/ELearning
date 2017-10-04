package com.eways.elearning.View.Activity.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.eways.elearning.Model.Database.FireBaseHandler;
import com.eways.elearning.Model.Database.FirebaseHandlerCallback;
import com.eways.elearning.R;
import com.eways.elearning.View.Fragment.DangNhap.DangNhapFragment;
import com.facebook.FacebookSdk;

public class MainActivity extends AppCompatActivity implements FirebaseHandlerCallback{
    FireBaseHandler fireBaseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportFragmentManager().beginTransaction().replace(R.id.LoMain,new DangNhapFragment()).commit();

    }
    //FirebaseHandlerCallback trả dữ liệu
    @Override
    public void TransData(String data) {
    }
}
