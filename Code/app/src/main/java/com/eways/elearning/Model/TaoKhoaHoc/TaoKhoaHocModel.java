package com.eways.elearning.Model.TaoKhoaHoc;

import com.eways.elearning.DataModel.KhoaHoc.KhoaHocChuaHoanTat;
import com.eways.elearning.Presenter.TaoKhoaHoc.TaoKhoaHocPresenterImp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by yowin on 19/11/2017.
 */

public class TaoKhoaHocModel implements TaoKhoaHocModelImp {

    TaoKhoaHocPresenterImp taoKhoaHocPresenterImp;
    DatabaseReference mData = FirebaseDatabase.getInstance().getReference();
    FirebaseAuth mAuth;


    public TaoKhoaHocModel(TaoKhoaHocPresenterImp taoKhoaHocPresenterImp) {
        this.taoKhoaHocPresenterImp = taoKhoaHocPresenterImp;
    }

    @Override
    public void postKhoaHoc(KhoaHocChuaHoanTat khoaHoc) {
        String khoahoc = mData.child("KhoaHoc").child("KhoaHocTimGiaSu").child("ChuaHoanTat").push().getKey();
        mData.child("TaiKhoan").child(mAuth.getCurrentUser().getUid()).child("KhoaHoc").child("TimGiaSu").setValue(khoahoc);
    }
}
