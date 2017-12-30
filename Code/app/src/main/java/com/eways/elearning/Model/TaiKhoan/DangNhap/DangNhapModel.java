package com.eways.elearning.Model.TaiKhoan.DangNhap;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.eways.elearning.DataModel.TaiKhoan;
import com.eways.elearning.Handler.Other.ParseDataTaiKhoan;
import com.eways.elearning.Presenter.TaiKhoan.DangNhap.DangNhapPresenterImp;
import com.eways.elearning.Util.SupportKeysList;
import com.eways.elearning.View.Fragment.TaiKhoan.DangNhap.DangNhapFragment;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.ArrayList;

/**
 * Created by ADMIN on 11/5/2017.
 */

public class DangNhapModel implements DangNhapImpModel{
    DangNhapPresenterImp dangNhapImpPresenter;
    ParseDataTaiKhoan parseDataTaiKhoan;

    public DangNhapModel(DangNhapPresenterImp dangNhapImpPresenter) {
        this.dangNhapImpPresenter = dangNhapImpPresenter;

    }

    @Override
    public void NhanTaiKhoanDN(final TaiKhoan taiKhoan, final Activity activity) {
        final FirebaseAuth mAuth;
        final FirebaseDatabase mDataNhanTaiKhoanDN;
        mDataNhanTaiKhoanDN=FirebaseDatabase.getInstance(FirebaseApp.initializeApp(activity));
        mAuth=FirebaseAuth.getInstance(FirebaseApp.initializeApp(activity));
        mAuth.signInWithEmailAndPassword(taiKhoan.getEmail().toString(), taiKhoan.getPassword().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    final FirebaseUser user=mAuth.getCurrentUser();
                    mDataNhanTaiKhoanDN.getReference().child("TaiKhoan").orderByKey().equalTo(user.getUid().toString()).addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                            dangNhapImpPresenter.KetQuaDangNhap(DangNhapFragment.LOGIN_SUCCESS,user,null,activity,dataSnapshot.getValue(TaiKhoan.class));
                        }

                        @Override
                        public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                        }

                        @Override
                        public void onChildRemoved(DataSnapshot dataSnapshot) {

                        }

                        @Override
                        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                } else
                    dangNhapImpPresenter.KetQuaDangNhap(DangNhapFragment.LOGIN_FAILED,null,null,activity,null);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("loidn", e.toString());
            }
        });
    }

    @Override
    public void DangNhapGmail(final GoogleSignInAccount account, final Activity activity) {
        final FirebaseDatabase mDataDangNhapGmail;
        mDataDangNhapGmail=FirebaseDatabase.getInstance(FirebaseApp.initializeApp(activity));
        RequestQueue requestQueue= Volley.newRequestQueue(activity);
        StringRequest stringRequest=new StringRequest(Request.Method.GET, "https://us-central1-elearning-da847.cloudfunctions.net/getDataGmail", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                parseDataTaiKhoan=new ParseDataTaiKhoan(response);
                ArrayList<TaiKhoan> listTaiKhoan=new ArrayList<>();
                listTaiKhoan=parseDataTaiKhoan.ParseTaiKhoan();

                for (int i=0;i<listTaiKhoan.size();i++){
                    if (listTaiKhoan.get(i).getId().compareTo(account.getId().toString().trim())==0){
                        dangNhapImpPresenter.KetQuaDangNhap(DangNhapFragment.LOGIN_SUCCESS,null,account,activity,listTaiKhoan.get(i));
                        return;
                    }
                }
                mDataDangNhapGmail.getReference().child("TaiKhoan").child(account.getId().trim().toString()).setValue(new TaiKhoan(account.getId(),account.getEmail(),account.getFamilyName(),account.getGivenName(),"null",true, SupportKeysList.TAI_KHOAN_GMAIL,"null","null","null","null","null","null","null","null","null","null",false,"5"));
                mDataDangNhapGmail.getReference().child("TaiKhoan").orderByKey().equalTo(account.getId().toString()).addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        dangNhapImpPresenter.KetQuaDangNhap(DangNhapFragment.LOGIN_SUCCESS,null,account,activity,dataSnapshot.getValue(TaiKhoan.class));
                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("loiGmail",error.toString());
            }
        });

        requestQueue.add(stringRequest);
    }
}
