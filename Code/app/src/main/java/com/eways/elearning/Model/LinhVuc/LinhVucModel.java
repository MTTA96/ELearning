package com.eways.elearning.Model.LinhVuc;

import com.eways.elearning.DataModel.LinhVuc.LinhVuc;
import com.eways.elearning.Presenter.LinhVuc.LinhVucPresenterImp;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * Created by yowin on 03/12/2017.
 */

public class LinhVucModel implements LinhVucModelImp{

    DatabaseReference mData = FirebaseDatabase.getInstance().getReference();
    LinhVucPresenterImp linhVucPresenterImp;
    ArrayList<LinhVuc> linhVucs;

    public LinhVucModel(LinhVucPresenterImp linhVucPresenterImp) {
        this.linhVucPresenterImp = linhVucPresenterImp;
    }

    @Override
    public void getListLinhVuc() {
        mData.child("DataApp").child("DanhMucLinhVuc").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                linhVucs.add(dataSnapshot.getValue(LinhVuc.class));
                linhVucPresenterImp.nhanListLinhVuc(linhVucs);
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
}
