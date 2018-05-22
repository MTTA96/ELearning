package com.eways.elearning.Model.DanhSachMon;

import com.eways.elearning.Model.DataModel.LinhVuc.Mon;
import com.eways.elearning.Model.DataModel.LinhVuc.LinhVuc;
import com.eways.elearning.Presenter.DanhSachMon.DanhSachMonPresenterImp;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * Created by yowin on 14/12/2017.
 */

public class DanhSachMonModel implements DanhSachMonModelImp{

    DatabaseReference mData = FirebaseDatabase.getInstance().getReference();
    DanhSachMonPresenterImp danhSachMonPresenterImp;
    ArrayList<String> dsMon = new ArrayList<>();

    public DanhSachMonModel(DanhSachMonPresenterImp danhSachMonPresenterImp) {
        this.danhSachMonPresenterImp = danhSachMonPresenterImp;
    }

    @Override
    public void GetDanhSachMon() {
        mData.child("DataApp").child("DanhMucLinhVuc").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                LinhVuc lv = dataSnapshot.getValue(LinhVuc.class);
                for(Mon mon : lv.getDanhMucMon())
                {
                    dsMon.add(mon.getTenMon());
                }
                danhSachMonPresenterImp.NhanDanhSachKhoaHoc(dsMon);
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
