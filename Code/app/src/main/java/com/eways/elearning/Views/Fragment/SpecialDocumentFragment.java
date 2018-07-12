package com.eways.elearning.Views.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;

import com.eways.elearning.Adapter.ImageSpecAdapter;
import com.eways.elearning.Adapter.SpecialAdapter.SpecialAdapter;
import com.eways.elearning.Model.Certificate;
import com.eways.elearning.Model.Image;
import com.eways.elearning.Model.SubjectSpec;
import com.eways.elearning.R;
import com.eways.elearning.Utils.params.GlobalParams;
import com.eways.elearning.Views.Activity.PopUpAddImageActivity;
import com.eways.elearning.Views.Activity.SpecialDocumentActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class SpecialDocumentFragment extends Fragment implements View.OnClickListener {
    /**
     * VIEWS
     */
    RecyclerView rcCertificate;
    View btnAddCerti;
    RecyclerView rcSubject;
    Button btnAddSubject;
    HorizontalScrollView scrollView;

    /**
     * ACCESSORIES
     */
    ArrayList<Certificate> mListCerti;
    SpecialAdapter mSpecialAdapter;

    ArrayList<SubjectSpec> mListSubject;
    ImageSpecAdapter mImageSpecAdapter;

    ImageView ivMiniCerti;

    boolean mOpen;

    public SpecialDocumentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_special_document, container, false);
        declare_views(view);
        handle_views();
        return view;
    }

    private void declare_views(View root) {
        rcCertificate = root.findViewById(R.id.rc_certificate);
        btnAddCerti = root.findViewById(R.id.btn_add_certi);
        rcSubject = root.findViewById(R.id.rc_subject);
        btnAddSubject = root.findViewById(R.id.btn_add_subject);
        ivMiniCerti = root.findViewById(R.id.iv_mini_certi);
        scrollView = root.findViewById(R.id.scr_certi);

    }

    private void handle_views() {
        SetUpListCerti();
        SetUpSubject();

        mOpen = true;

        btnAddCerti.setVisibility(View.GONE);

        btnAddCerti.setOnClickListener(this);
        btnAddSubject.setOnClickListener(this);

        btnAddSubject.setVisibility(View.GONE);

        ivMiniCerti.setOnClickListener(this);


    }

    private void SetUpListCerti() {
        mListCerti = new ArrayList<>();

        mListCerti.add(new Certificate(0, "https://static1.squarespace.com/static/56f5fdc7c2ea5119892e22c2/571a3e70b654f9dd5cc18184/571a47c601dbae832ce3b2f6/1461340111262/DOGFACE-Chase-024AFP.jpg?format=750w", "ielts"));

        mSpecialAdapter = new SpecialAdapter(mListCerti, getActivity());

        rcCertificate.hasFixedSize();

        rcCertificate.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        rcCertificate.setAdapter(mSpecialAdapter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_add_certi:

                Intent i = new Intent(getActivity(), PopUpAddImageActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                break;
            case R.id.btn_add_subject:

                break;

            case R.id.iv_mini_certi:
                if (mOpen){
                    scrollView.setVisibility(View.GONE);
                    mOpen = false;
                }else {
                    scrollView.setVisibility(View.VISIBLE);
                    mOpen = true;
                }
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        if (getActivity().getIntent().getExtras() != null) {
            if (this.getActivity().getIntent().getExtras().get("item_certi_add") != null) {
                mListCerti.add(GlobalParams.getInstance().getGSon().fromJson(getActivity().getIntent().getExtras().get("item_certi_add").toString(), Certificate.class));
            }
        }
    }

    private void SetUpSubject() {
        mListSubject = new ArrayList<>();
        List<Image> images = new ArrayList<>();
        images.add(new Image(0, "https://static1.squarespace.com/static/56f5fdc7c2ea5119892e22c2/571a3e70b654f9dd5cc18184/571a47c601dbae832ce3b2f6/1461340111262/DOGFACE-Chase-024AFP.jpg?format=750w"));
        mListSubject.add(new SubjectSpec(0, "Toan", "http://www.html5videoplayer.net/videos/toystory.mp4", images));

        mImageSpecAdapter = new ImageSpecAdapter(getActivity(), mListSubject);

        rcSubject.setHasFixedSize(true);
        rcSubject.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        rcSubject.setAdapter(mImageSpecAdapter);
    }

}
