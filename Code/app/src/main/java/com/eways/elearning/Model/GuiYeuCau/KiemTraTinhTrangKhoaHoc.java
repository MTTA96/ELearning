package com.eways.elearning.Model.GuiYeuCau;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by yowin on 05/12/2017.
 */

public class KiemTraTinhTrangKhoaHoc implements KiemTraTinhTrangKhoaHocImp {

    GuiYeuCauModelImp guiYeuCauModelImp;
    DatabaseReference mData = FirebaseDatabase.getInstance().getReference();

    public KiemTraTinhTrangKhoaHoc(GuiYeuCauModelImp guiYeuCauModelImp) {
        this.guiYeuCauModelImp = guiYeuCauModelImp;
    }

    @Override
    public void TinhTrangKhoaHoc(boolean loaiKH, final String keyKhoaHoc) {

        if(loaiKH)
        {
            mData.child("KhoaHoc").child("KhoaHocTimGiaSu").child("ChuaHoanTat").addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    if(dataSnapshot.getKey().equals(keyKhoaHoc))
//                        ketQuaTinhTrangImp.TinhTrangKhoaHoc(true);
                        guiYeuCauModelImp.NhanKQTrangThaiKhoaHoc(true);
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
        else
        {
            mData.child("KhoaHoc").child("KhoaHocTimHocVien").child("ChuaHoanTat").addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    if(dataSnapshot.getKey().equals(keyKhoaHoc))
//                        ketQuaTinhTrangImp.TinhTrangKhoaHoc(true);
                        guiYeuCauModelImp.NhanKQTrangThaiKhoaHoc(true);
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
}
