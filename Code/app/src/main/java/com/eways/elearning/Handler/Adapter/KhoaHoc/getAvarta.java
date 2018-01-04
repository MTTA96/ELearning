package com.eways.elearning.Handler.Adapter.KhoaHoc;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by yowin on 15/12/2017.
 */

public class getAvarta implements getAvartaImp{
    KhoaHocRCAdapterImp khoaHocRCAdapterImp;
    DatabaseReference mData = FirebaseDatabase.getInstance().getReference();

    public getAvarta(KhoaHocRCAdapterImp khoaHocRCAdapterImp) {
        this.khoaHocRCAdapterImp = khoaHocRCAdapterImp;
    }

    @Override
    public void layAvatar(String UID) {
        mData.child("TaiKhoan").child(UID).child("avarta").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                khoaHocRCAdapterImp.NhanAvarta(dataSnapshot.getValue(String.class));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
