package com.eways.elearning.View.Fragment.KhoaHoc;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.eways.elearning.DataModel.KhoaHoc.CustomModelKhoaHoc;
import com.eways.elearning.DataModel.KhoaHoc.KhoaHoc;
import com.eways.elearning.DataModel.KhoaHoc.KhoaHocChuaHoanTat;
import com.eways.elearning.Handler.Adapter.KhoaHocRCAdapter;
import com.eways.elearning.Handler.ImageHandler;
import com.eways.elearning.R;
import com.eways.elearning.Util.SupportKeysList;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class KetQuaTimKiemFragment extends Fragment {

    private static final String paramRequestKhoaHoc = "RequestKhoaHoc";
    private static final String paramRequestGiaSu = "RequestGiaSu";
    private static final String paramRequestBangCap = "RequestBangCap";

    private KhoaHoc requestKhoaHoc;
    private boolean requestGiaSu;
    private String requestBangCap;

    int sizeLichHoc = 0;
    int countLichHoc = 0;
    int gioiHanLichHoc = 0;
    DatabaseReference mData = FirebaseDatabase.getInstance().getReference();
    ArrayList<CustomModelKhoaHoc> rsKhoaHocChinhXac;
    ArrayList<CustomModelKhoaHoc> rsKhoaHocGanChinhXac;
    KhoaHocRCAdapter adapterChinhXac;
    KhoaHocRCAdapter adapterGanChinhXac;
    RecyclerView rcKetQua;
    ImageHandler imageHandler;

    public KetQuaTimKiemFragment() {
        // Required empty public constructor
    }

    /**
     * @param requestKhoaHoc chứa thông tin khóa học user tìm kiếm
     * @param timGiaSu       true: tìm gia sư
     *                       false: tìm học viên
     * @param bangCap        bằng cấp yêu cầu
     */
    public static KetQuaTimKiemFragment newInstance(boolean timGiaSu, KhoaHoc requestKhoaHoc, String bangCap) {

        Bundle args = new Bundle();
        args.putSerializable(paramRequestKhoaHoc, requestKhoaHoc);
        args.putBoolean(paramRequestGiaSu, timGiaSu);
        args.putString(paramRequestBangCap, bangCap);
        KetQuaTimKiemFragment fragment = new KetQuaTimKiemFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            requestKhoaHoc = (KhoaHoc) getArguments().getSerializable(paramRequestKhoaHoc);
            requestGiaSu = getArguments().getBoolean(paramRequestGiaSu);
            requestBangCap = getArguments().getString(paramRequestBangCap, null);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_ket_qua_tim_kiem, container, false);

        gioiHanLichHoc = (requestKhoaHoc.getThu().size()) / 2;
        rcKetQua = (RecyclerView) root.findViewById(R.id.rcKetQuaTimKiem);
        imageHandler = new ImageHandler(getActivity());
        rsKhoaHocChinhXac = new ArrayList<CustomModelKhoaHoc>();
        if (rsKhoaHocChinhXac == null) {
            Toast.makeText(getActivity(), "Null", Toast.LENGTH_SHORT).show();

        } else {
            adapterChinhXac = new KhoaHocRCAdapter(
                    rsKhoaHocChinhXac,
                    imageHandler
            );
            rcKetQua.setLayoutManager(new GridLayoutManager(getActivity(), 1));
            rcKetQua.setAdapter(adapterChinhXac);
        }

        if (requestGiaSu == true) {

            mData.child(SupportKeysList.CHILD_KHOAHOC).child(SupportKeysList.CHILD_KHOAHOC_TIMGIASU).child(SupportKeysList.CHILD_KHOAHOC_CHUAHOANTAT).addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    KhoaHocChuaHoanTat kh = dataSnapshot.getValue(KhoaHocChuaHoanTat.class);

                    for (String mon : kh.Mon) {
                        if (mon.equals(requestKhoaHoc.getMon().get(0))) //vì chỉ lấy 1 môn nên get(0)
                        {
                            for (String linhVuc : kh.LinhVuc) {
                                if (linhVuc.equals(requestKhoaHoc.getLinhVuc().get(0))) //Vì chỉ lấy 1 lĩnh vực 1 lần nên get(0)
                                {
                                    for (String rqBuoi : requestKhoaHoc.getBuoi()) {
                                        for (String rsBuoi : kh.LichHoc.ThoiGian) {
                                            if (rqBuoi.equals(rsBuoi)) {
//                                                rsKhoaHocGanChinhXac.add(new CustomModelKhoaHoc(kh.HoTen,kh.NguoiDang,kh.Avatar,kh.LichHoc.ThoiGian,kh.Rating,kh.HocPhi,kh.Mon,kh.Lop));
//                                                adapterGanChinhXac.notifyDataSetChanged();

                                                for (String rqThu : requestKhoaHoc.getThu()) {
                                                    for (String rsThu : kh.LichHoc.NgayHoc) {
                                                        if (rqThu.equals(rsThu)) {
                                                            countLichHoc += 1;
                                                            if (countLichHoc == gioiHanLichHoc) {
                                                                rsKhoaHocChinhXac.add(new CustomModelKhoaHoc(kh.HoTen, kh.NguoiDang, kh.Avatar, kh.LichHoc.ThoiGian, kh.Rating, kh.HocPhi, kh.Mon, kh.Lop));
                                                                adapterChinhXac.notifyDataSetChanged();
                                                            }
                                                        }
                                                    }

                                                }
                                            }
                                        }

                                    }
                                }
                            }
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
        } else {
            mData.child(SupportKeysList.CHILD_KHOAHOC).child(SupportKeysList.CHILD_KHOAHOC_TIMHOCVIEN).child(SupportKeysList.CHILD_KHOAHOC_CHUAHOANTAT).addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    KhoaHocChuaHoanTat kh = dataSnapshot.getValue(KhoaHocChuaHoanTat.class);

                    for (String mon : kh.Mon) {
                        if (mon.equals(requestKhoaHoc.getMon().get(0))) //vì chỉ lấy 1 môn nên get(0)
                        {
                            for (String linhVuc : kh.LinhVuc) {
                                if (linhVuc.equals(requestKhoaHoc.getLinhVuc().get(0))) //Vì chỉ lấy 1 lĩnh vực 1 lần nên get(0)
                                {
                                    for (String rqBuoi : requestKhoaHoc.getBuoi()) {
                                        for (String rsBuoi : kh.LichHoc.ThoiGian) {
                                            if (rqBuoi.equals(rsBuoi)) {
//                                                rsKhoaHocGanChinhXac.add(new CustomModelKhoaHoc(kh.HoTen,kh.NguoiDang,kh.Avatar,kh.LichHoc.ThoiGian,kh.Rating,kh.HocPhi,kh.Mon,kh.Lop));
//                                                adapterGanChinhXac.notifyDataSetChanged();

                                                for (String rqThu : requestKhoaHoc.getThu()) {
                                                    for (String rsThu : kh.LichHoc.NgayHoc) {
                                                        if (rqThu.equals(rsThu)) {
                                                            countLichHoc += 1;
                                                            if (countLichHoc == gioiHanLichHoc) {
                                                                rsKhoaHocChinhXac.add(new CustomModelKhoaHoc(kh.HoTen, kh.NguoiDang, kh.Avatar, kh.LichHoc.ThoiGian, kh.Rating, kh.HocPhi, kh.Mon, kh.Lop));
                                                                adapterChinhXac.notifyDataSetChanged();
                                                            }
                                                        }
                                                    }

                                                }
                                            }
                                        }

                                    }
                                }
                            }
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
        return root;
    }

}
