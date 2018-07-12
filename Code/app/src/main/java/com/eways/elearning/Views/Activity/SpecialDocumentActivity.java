package com.eways.elearning.Views.Activity;

import android.app.Activity;
import android.app.Dialog;
import android.app.Notification;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.ScrollView;

import com.eways.elearning.Adapter.ImageChooseAdapter;
import com.eways.elearning.Adapter.ImageSpecAdapter;
import com.eways.elearning.Adapter.SpecialAdapter.SpecialAdapter;
import com.eways.elearning.Model.Certificate;
import com.eways.elearning.Model.Image;
import com.eways.elearning.Model.ImageSelect;
import com.eways.elearning.Model.SubjectSpec;
import com.eways.elearning.R;
import com.eways.elearning.Utils.DialogPlusHandler;
import com.eways.elearning.Utils.FileUtils;
import com.eways.elearning.Utils.Handler.ImageHandler;
import com.eways.elearning.Utils.params.GlobalParams;
import com.google.android.gms.common.internal.GmsLogger;
import com.orhanobut.dialogplus.DialogPlusBuilder;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class SpecialDocumentActivity extends Activity implements View.OnClickListener {
    /**
     * VIEWS
     */
    RecyclerView rcCertificate;
    View btnAddCerti;
    RecyclerView rcSubject;
    Button btnAddSubject;
    HorizontalScrollView scrView;
    ImageView ivMiniCerti;

    /**
     * ACCESSORIES
     */
    ArrayList<Certificate> mListCerti;
    SpecialAdapter mSpecialAdapter;
    boolean mOpen;

    ArrayList<SubjectSpec> mListSubject;
    ImageSpecAdapter mImageSpecAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_special_document);

        declare_views();
        handle_views();

    }

    private void declare_views() {
        rcCertificate = findViewById(R.id.rc_certificate);
        btnAddCerti = findViewById(R.id.btn_add_certi);
        rcSubject = findViewById(R.id.rc_subject);
        btnAddSubject = findViewById(R.id.btn_add_subject);
        scrView = findViewById(R.id.scr_certi);
        ivMiniCerti = findViewById(R.id.iv_mini_certi);
    }

    private void handle_views() {
        SetUpListCerti();
        SetUpSubject();

        btnAddCerti.setVisibility(View.GONE);

        btnAddCerti.setOnClickListener(this);
        btnAddSubject.setOnClickListener(this);

        btnAddSubject.setVisibility(View.GONE);

        ivMiniCerti.setOnClickListener(this);

        mOpen = true;

    }

    private void SetUpListCerti() {
        mListCerti = new ArrayList<>();

        mListCerti.add(new Certificate(0, "https://static1.squarespace.com/static/56f5fdc7c2ea5119892e22c2/571a3e70b654f9dd5cc18184/571a47c601dbae832ce3b2f6/1461340111262/DOGFACE-Chase-024AFP.jpg?format=750w", "ielts"));

        mSpecialAdapter = new SpecialAdapter(mListCerti, this);

        rcCertificate.hasFixedSize();

        rcCertificate.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        rcCertificate.setAdapter(mSpecialAdapter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_add_certi:

                Intent i = new Intent(SpecialDocumentActivity.this, PopUpAddImageActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                break;
            case R.id.btn_add_subject:

                break;

            case R.id.iv_mini_certi:
                if (mOpen) {
                    scrView.setVisibility(View.GONE);
                    mOpen = false;
                }else {
                    scrView.setVisibility(View.VISIBLE);
                    mOpen = true;
                }
                break;
        }
    }

    @Override
    protected void onResume() {
        if (SpecialDocumentActivity.this.getIntent().getExtras() != null) {
            mListCerti.add(GlobalParams.getInstance().getGSon().fromJson(this.getIntent().getExtras().get("item_certi_add").toString(), Certificate.class));

        }
        super.onResume();

    }

    private void SetUpSubject() {
        mListSubject = new ArrayList<>();
        List<Image> images = new ArrayList<>();
        images.add(new Image(0, "https://static1.squarespace.com/static/56f5fdc7c2ea5119892e22c2/571a3e70b654f9dd5cc18184/571a47c601dbae832ce3b2f6/1461340111262/DOGFACE-Chase-024AFP.jpg?format=750w"));
        mListSubject.add(new SubjectSpec(0, "Toan", "http://www.html5videoplayer.net/videos/toystory.mp4", images));

        mImageSpecAdapter = new ImageSpecAdapter(this, mListSubject);

        rcSubject.setHasFixedSize(true);
        rcSubject.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rcSubject.setAdapter(mImageSpecAdapter);
    }
}
