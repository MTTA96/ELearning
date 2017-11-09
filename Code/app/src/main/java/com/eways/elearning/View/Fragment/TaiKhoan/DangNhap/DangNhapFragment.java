package com.eways.elearning.View.Fragment.TaiKhoan.DangNhap;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.eways.elearning.Presenter.DangKy.DangNhap.DangNhapImpPresenter;
import com.eways.elearning.Presenter.DangKy.DangNhap.DangNhapPresenter;
import com.eways.elearning.R;
import com.eways.elearning.View.Activity.MainActivity;
import com.eways.elearning.View.Fragment.Home.HomeFragment;
import com.eways.elearning.View.Fragment.TaiKhoan.DangKy.DangKyFragment;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.util.concurrent.Executor;

/**
 * A simple {@link Fragment} subclass.
 */
public class DangNhapFragment extends Fragment implements View.OnClickListener,DangNhapImpView{
    Button btnDangky,btnDangNhap;
    EditText etEmailDN,etPasswordDN;
    DangNhapImpPresenter dangNhapImpPresenter;
    TextView tvLoiDangNhap;

    //Đăng nhập bằng Gmail
    SignInButton btnGsignin;
    private static final int RC_SIGN_IN=1;
    private GoogleApiClient mGoogleApiClient;
    private FirebaseAuth mAuth;
    private String TAG="MAIN_ACTIVITY";
    private FirebaseAuth.AuthStateListener mAuthListener;

    public DangNhapFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        dangNhapImpPresenter=new DangNhapPresenter(this);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_dang_nhap, container, false);
        btnDangky= (Button) root.findViewById(R.id.btnSignup);
        btnDangNhap=(Button) root.findViewById(R.id.btnLogin);
        etEmailDN= (EditText) root.findViewById(R.id.etUsername);
        etPasswordDN= (EditText) root.findViewById(R.id.etPassword);
        tvLoiDangNhap= (TextView) root.findViewById(R.id.tvLoiDN);

//        Đăng nhập bằng Gmail
        btnGsignin= (SignInButton) root.findViewById(R.id.btnLoginGmail);
        mAuth=FirebaseAuth.getInstance();
        mAuthListener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser()!=null){
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_main,new HomeFragment());
                }

            }
        };
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleApiClient=new GoogleApiClient.Builder(getContext())
                .enableAutoManage(getActivity(), new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                        Toast.makeText(getActivity(),"Error",Toast.LENGTH_SHORT).show();

                    }
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .build();
        btnGsignin.setOnClickListener(this);

        btnDangky.setOnClickListener(this);
        btnDangNhap.setOnClickListener(this);

        return root;
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnSignup)
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_main,new DangKyFragment()).commit();
        if (view.getId() == R.id.btnLogin)
            dangNhapImpPresenter.NhanThongTinDN(etEmailDN.getText().toString(),etPasswordDN.getText().toString(),getActivity());
        if (view.getId() == R.id.btnLoginGmail)
            signIn();
    }

    //Đăng nhập với Gmail
    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
            } else {
                // Google Sign In failed, update UI appropriately
                // ...
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
//        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
//                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_main,new HomeFragment()).commit();
                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
//                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(getActivity(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }
                    }
                });
    }

    @Override
    public void NhanKetQuaDN(String ketqua) {
        if (ketqua.compareTo("emtyEmail")==0){
            tvLoiDangNhap.setText(R.string.loi_EmailDN);
            etEmailDN.setBackgroundResource(R.drawable.loi_shape);
            etPasswordDN.setBackgroundResource(R.drawable.et_shape);
            return;
        }
        if (ketqua.compareTo("emtyPassword")==0){
            tvLoiDangNhap.setText(R.string.loi_PasswordDN);
            etPasswordDN.setBackgroundResource(R.drawable.loi_shape);
            etEmailDN.setBackgroundResource(R.drawable.et_shape);
            return;
        }
        if (ketqua.compareTo("emtyEmailPassword")==0){
            tvLoiDangNhap.setText(R.string.loi_EmailDN +"-"+R.string.loi_PasswordDN);
            etEmailDN.setBackgroundResource(R.drawable.loi_shape);
            etPasswordDN.setBackgroundResource(R.drawable.loi_shape);
            return;
        }

        if (ketqua.compareTo("SaiEmail")==0){
            tvLoiDangNhap.setText(R.string.loi_SaiDinhDangEmailDN);
            etEmailDN.setBackgroundResource(R.drawable.loi_shape);
            etPasswordDN.setBackgroundResource(R.drawable.et_shape);
            return;
        }
        if (ketqua.compareTo("SaiPassword")==0){
            tvLoiDangNhap.setText(R.string.loi_SaiMatKhauDN);
            etPasswordDN.setBackgroundResource(R.drawable.loi_shape);
            etEmailDN.setBackgroundResource(R.drawable.et_shape);
            return;
        }
        if (ketqua.compareTo("thanhcong")==0){
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_main,new HomeFragment()).commit();
        }
    }

}
