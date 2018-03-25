package com.eways.elearning.Model.KhoaHoc.ListKhoaHoc;

import com.eways.elearning.Model.DataModel.KhoaHoc.CustomModelKhoaHoc;
import com.eways.elearning.Model.DataModel.TaiKhoan.TaiKhoan;
import com.eways.elearning.Model.DataModel.KhoaHoc.KhoaHoc;
import com.eways.elearning.Presenter.ListKhoaHoc.ListKhoaHocTimHocVienPresenterImp;
import com.eways.elearning.Util.SupportKeysList;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * Created by yowin on 09/11/2017.
 */

public class ListKhoaHocTimHocVienModel implements ListKhoaHocTimHocVienImpModel {

    ListKhoaHocTimHocVienPresenterImp listKhoaHocTimHocVienPresenterImp;
    DatabaseReference mData = FirebaseDatabase.getInstance().getReference();
    ArrayList<CustomModelKhoaHoc> listKhoaHoc =new ArrayList<>();

    public ListKhoaHocTimHocVienModel(ListKhoaHocTimHocVienPresenterImp listKhoaHocTimHocVienPresenterImp) {
        this.listKhoaHocTimHocVienPresenterImp = listKhoaHocTimHocVienPresenterImp;
    }

    /**Dùng NodeJS
     * implements DataCallBack khi dùng*/
//    @Override
//    public void getDanhSachKhoaHocTimHocVien() {
//        new GetData(this).execute(SupportKeysList.API_GET_KHOAHOC, ServerUrl.ApiGetKhoaHocHV,"GET");
//    }
//
//    @Override
//    public void KetQua(String result, @Nullable Bundle bundle) {
//        ArrayList<CustomModelKhoaHoc> khoaHocArrayList = (ArrayList<CustomModelKhoaHoc>) bundle.getSerializable(SupportKeysList.API_GET_KHOAHOC);
//        listKhoaHocTimHocVienPresenterImp.nhanDanhSachKhoaHoc(khoaHocArrayList);
//    }

/**Dùng code Firebase*/
    @Override
    public void getDanhSachKhoaHocTimHocVien(final String linhVuc) {
        listKhoaHoc.clear();
        mData.child(SupportKeysList.CHILD_KHOAHOC).child(SupportKeysList.CHILD_KHOAHOC_TIMHOCVIEN).child(SupportKeysList.CHILD_KHOAHOC_CHUAHOANTAT).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                final KhoaHoc khoaHoc=dataSnapshot.getValue(KhoaHoc.class);
                for(String linhvuc : khoaHoc.getLinhVuc())
                {
                    if(linhvuc.equals(linhVuc)) {
                        mData.child("TaiKhoan").orderByKey().equalTo(khoaHoc.getNguoiDang().toString().trim()).addChildEventListener(new ChildEventListener() {
                            @Override
                            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                CustomModelKhoaHoc ckh = new CustomModelKhoaHoc(dataSnapshot.getValue(TaiKhoan.class).getAvatar(), dataSnapshot.getValue(TaiKhoan.class).getRating(), dataSnapshot.getValue(TaiKhoan.class).getHo() + dataSnapshot.getValue(TaiKhoan.class).getTen(), khoaHoc.getNguoiDang(), khoaHoc.getSoBuoiHoc(), khoaHoc.getSoLuongHocVien(), khoaHoc.getGioiTinh(), khoaHoc.getNgayDang(), khoaHoc.getGioDang(), khoaHoc.getThoiLuongBuoiHoc(), khoaHoc.getHocPhi(), khoaHoc.getThongTinKhac(), khoaHoc.getBangCap(), khoaHoc.getMon(), khoaHoc.getLinhVuc(), khoaHoc.getLichHoc(), khoaHoc.getDiaDiem(), khoaHoc.getDanhSachYeuCau(), dataSnapshot.getKey());
                                listKhoaHoc.add(ckh);
                                listKhoaHocTimHocVienPresenterImp.nhanDanhSachKhoaHoc(listKhoaHoc);
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
