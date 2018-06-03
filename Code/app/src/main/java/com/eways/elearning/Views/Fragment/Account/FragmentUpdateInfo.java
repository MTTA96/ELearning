package com.eways.elearning.Views.Fragment.Account;


import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.eways.elearning.Adapter.ImageChooseAdapter;
import com.eways.elearning.Model.ImageSelect;
import com.eways.elearning.R;
import com.eways.elearning.Utils.DialogPlusHandler;
import com.eways.elearning.Utils.FileUtils;
import com.eways.elearning.Utils.Handler.ImageHandler;
import com.eways.elearning.Utils.params.GlobalParams;
import com.orhanobut.dialogplus.DialogPlus;

import org.json.JSONArray;
import org.json.JSONException;

import java.security.Permission;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentUpdateInfo extends Fragment{
    /* VIEWS */
    ImageView ivAvarta;


    ImageHandler imageHandler;
    DialogPlusHandler dialogPlusHandler;
    ImageChooseAdapter imageChooseAdapter;
    ArrayList<ImageSelect> imageSelects;

    public FragmentUpdateInfo() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_update_info, container, false);
        declare_views(root);
        handle_views();
        return root;
    }

    public void declare_views(View root){
        ivAvarta = root.findViewById(R.id.avarta);
    }

    public void handle_views(){
        imageHandler = new ImageHandler(getActivity());
        SetUpDialog();
        ivAvarta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogPlusHandler.ShowDiglogPlus();
            }
        });
    }

    public void SetUpDialog(){
        imageSelects = new ArrayList<>();

        try {
            JSONArray jsonArray = new JSONArray(FileUtils.loadJSONFromAsset(getActivity(), "image_choose"));
            for (int i = 0; i < jsonArray.length(); i++){
                imageSelects.add(GlobalParams.getInstance().getGSon().fromJson(jsonArray.get(i).toString(), ImageSelect.class));
            }
            imageChooseAdapter = new ImageChooseAdapter(getActivity(), R.layout.item_image_select, imageSelects);
            dialogPlusHandler = new DialogPlusHandler(getActivity(), imageChooseAdapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case DialogPlusHandler.REQUEST_CODE_CAMERA:
                break;

            case DialogPlusHandler.REQUEST_CODE_GALLERY:

                Uri selectedImage = data.getData();
                imageHandler.loadImageRound(String.valueOf(selectedImage), ivAvarta);

                break;
        }
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == DialogPlusHandler.REQUEST_CODE_CAMERA){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, DialogPlusHandler.REQUEST_CODE_CAMERA);
            }
        }

    }
}
