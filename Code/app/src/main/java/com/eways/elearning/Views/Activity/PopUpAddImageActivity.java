package com.eways.elearning.Views.Activity;

import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.eways.elearning.Adapter.ImageChooseAdapter;
import com.eways.elearning.Model.Certificate;
import com.eways.elearning.Model.ImageSelect;
import com.eways.elearning.R;
import com.eways.elearning.Utils.DialogPlusHandler;
import com.eways.elearning.Utils.FileUtils;
import com.eways.elearning.Utils.Handler.ImageHandler;
import com.eways.elearning.Utils.params.GlobalParams;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class PopUpAddImageActivity extends Activity implements View.OnClickListener {

    /**
     * VIEWS
     */
    ImageView ivImage;
    EditText tvName;
    Button btnConfirm;

    DialogPlusHandler dialogPlusHandler;
    ImageChooseAdapter imageChooseAdapter;
    ArrayList<ImageSelect> imageSelects;
    ImageHandler mImageHandle;
    Certificate mCerti;
    com.eways.elearning.Model.Image mImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up_add_image);

        declare_views();
        handle_views();
    }

    private void declare_views() {
        ivImage = findViewById(R.id.iv_image_spec);
        tvName = findViewById(R.id.tv_name_spec);
        btnConfirm = findViewById(R.id.btn_confirm);
    }

    private void handle_views() {
        SetUpDialog();

        btnConfirm.setVisibility(View.GONE);
        tvName.setEnabled(false);
        tvName.setFocusable(false);

        mImageHandle = new ImageHandler(this);
        ivImage.setOnClickListener(this);
        btnConfirm.setOnClickListener(this);

        if (this.getIntent().getExtras() != null) {
            if (this.getIntent().getExtras().get("item_certi") != null) {
                mCerti = GlobalParams.getInstance().getGSon().fromJson(this.getIntent().getExtras().get("item_certi").toString(), Certificate.class);
                mImageHandle.loadImageRound(mCerti.getImage(), ivImage);
                tvName.setText(mCerti.getName());
            } else {
                mImage = GlobalParams.getInstance().getGSon().fromJson(this.getIntent().getExtras().get("item_subject").toString(), com.eways.elearning.Model.Image.class);
                mImageHandle.loadImageRound(mImage.getImage(), ivImage);
                tvName.setVisibility(View.GONE);
            }
        }

    }

    public void SetUpDialog() {
        imageSelects = new ArrayList<>();

        try {
            JSONArray jsonArray = new JSONArray(FileUtils.loadJSONFromAsset(this, "image_choose"));
            for (int i = 0; i < jsonArray.length(); i++) {
                imageSelects.add(GlobalParams.getInstance().getGSon().fromJson(jsonArray.get(i).toString(), ImageSelect.class));
            }
            imageChooseAdapter = new ImageChooseAdapter(this, R.layout.item_image_select, imageSelects);
            dialogPlusHandler = new DialogPlusHandler(this, imageChooseAdapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case DialogPlusHandler.REQUEST_CODE_CAMERA:
                Uri captureImage = data.getData();
                mImageHandle.loadImageSquare(String.valueOf(captureImage), ivImage);

                break;

            case DialogPlusHandler.REQUEST_CODE_GALLERY:

                Uri selectedImage = data.getData();
                mImageHandle.loadImageSquare(String.valueOf(selectedImage), ivImage);

                break;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_image_spec:

                dialogPlusHandler.ShowDiglogPlus();
                break;

            case R.id.btn_confirm:

                Intent i = new Intent(PopUpAddImageActivity.this, SpecialDocumentActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.putExtra("item_certi_add", GlobalParams.getInstance().getGSon().toJson(new Certificate(null, "abc", "acc")));
                startActivity(i);
                break;
        }
    }
}
