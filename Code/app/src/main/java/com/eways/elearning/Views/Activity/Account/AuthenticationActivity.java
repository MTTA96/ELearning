package com.eways.elearning.Views.Activity.Account;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

//<<<<<<< HEAD
import com.eways.elearning.R;
import com.eways.elearning.Utils.Handler.FragmentHandler;
//=======
//        import com.eways.etutor.R;
//        import com.eways.etutor.Utils.Handler.FragmentHandler;
//        import com.eways.etutor.Utils.SupportKeys;
//>>>>>>> 0ae5b509ad329b50db0111083a03465365a181c2
import com.eways.elearning.Views.Fragment.Authentication.LoginFragment;
import com.google.firebase.FirebaseApp;

public class AuthenticationActivity extends AppCompatActivity {

    /*---- FRAGMENT HANDLE -----*/
    FragmentHandler fragmentHandler;

    /*------ VIEWS ------*/
    View content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Declare();

        Handle();

    }

    //Declare
    public void Declare() {
        content = findViewById(R.id.content_user);
    }

    //handle
    public void Handle() {
        fragmentHandler = new FragmentHandler(this, R.id.content_user);

        fragmentHandler.changeFragment(new LoginFragment(), null, R.anim.slide_from_left, 0);
    }

}
