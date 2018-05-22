package com.eways.elearning.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.eways.elearning.Fragment.FragmentLogin;
import com.eways.elearning.Handler.FragmentHandler;
import com.eways.elearning.Handler.FragmentUtils;
import com.eways.elearning.R;
import com.facebook.login.LoginFragment;

public class MainActivity extends AppCompatActivity {

    /*---- FRAGMENT HANDLE -----*/
    FragmentUtils fragmentUtils;

    /*------ VIEWS ------*/
    View content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Declare();

        Handle();
    }

    //Declare
    public void Declare(){
        content = findViewById(R.id.content_user);
    }

    //Handle
    public void Handle(){
        fragmentUtils = new FragmentUtils(this, R.id.content_user);

        fragmentUtils.changeFragment(new FragmentLogin(), 0, 0);
    }
}
