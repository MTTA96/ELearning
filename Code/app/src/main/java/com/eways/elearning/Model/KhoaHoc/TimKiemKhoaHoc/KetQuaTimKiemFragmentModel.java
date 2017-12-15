package com.eways.elearning.Model.KhoaHoc.TimKiemKhoaHoc;

import android.app.Activity;

import com.eways.elearning.DataModel.KhoaHoc.CustomModelKhoaHoc;
import com.eways.elearning.DataModel.KhoaHoc.KhoaHoc;
import com.eways.elearning.Presenter.TimKiemKhoaHoc.KetQuaTimKiemKhoaHocFragmentPresenterImp;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.Normalizer;
import java.util.ArrayList;

/**
 * Created by yowin on 25/11/2017.
 */

public class KetQuaTimKiemFragmentModel implements KetQuatimKiemFragmentModelImp {

    KetQuaTimKiemKhoaHocFragmentPresenterImp ketQuaTimKiemKhoaHocFragmentPresenterImp;
    FirebaseDatabase mData;
    ArrayList<CustomModelKhoaHoc> khoaHocChinhXac = new ArrayList<>();
    ArrayList<CustomModelKhoaHoc> khoaHocGanChinhXac = new ArrayList<>();
    int countTemp = 0;

    public KetQuaTimKiemFragmentModel(KetQuaTimKiemKhoaHocFragmentPresenterImp ketQuaTimKiemKhoaHocFragmentPresenterImp) {
        this.ketQuaTimKiemKhoaHocFragmentPresenterImp = ketQuaTimKiemKhoaHocFragmentPresenterImp;
    }

    @Override
    public void getListKhoaHoc(final KhoaHoc khoaHoc, boolean loai, final String mon,Activity activity) {
//        final int count = cout(khoaHoc.getLichHoc().getNgayHoc().size()); //Nếu /2 mà <1 lấy 0 còn lại lấy /2 bt
//        final double hocPhiTimKiem = Double.parseDouble(khoaHoc.getHocPhi());
//        final double hocPhiTren = (hocPhiTimKiem + ((hocPhiTimKiem / 100) * 20));
//        final double hocPhiDuoi = (hocPhiTimKiem - ((hocPhiTimKiem / 100) * 20));
        mData = FirebaseDatabase.getInstance(FirebaseApp.initializeApp(activity));

//            if (loai)//0
//            {
//                //Tìm gia sư là ra bài tìm học viên
//                if (khoaHoc.getMon() != null)//1
//                {
//                    if (hocPhiTimKiem != 0) //2 Mon!=null, HocPhi !=0
//                    {
//                        if (khoaHoc.getGioiTinh() != "Nam, Nữ") //3 Mon!=null, HocPhi !=0,GioiTinh!=Nam, Nữ
//                        {
//                            if (khoaHoc.getBangCap() != null) //4 Mon!=null,HocPhi!=null,GioiTinh!=Nam, Nu,BangCap!=null
//                            {
//                                if (khoaHoc.getDiaDiem().getQuan() != "Khác") //5 Mon!=null,HocPhi!=null,GioiTinh!=Nam, Nu,BangCap!=null,Quan != "Khác"
//                                {
//                                    mData.child("KhoaHoc").child("KhoaHocTimHocVien").child("ChuaHoanTat").addListenerForSingleValueEvent(new ValueEventListener() {
//                                        @Override
//                                        public void onDataChange(DataSnapshot dataSnapshot) {
//                                            KhoaHoc kh = dataSnapshot.getValue(KhoaHoc.class);
//                                            for (String mon : kh.getMon()) {
//                                                try {
//                                                    if (URLEncoder.encode(removeDiacriticalMarks(mon), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getMon().get(0)), "utf-8"))) {
//                                                        khoaHocGanChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
//                                                        if (Double.parseDouble(kh.getHocPhi()) >= hocPhiDuoi && Double.parseDouble(kh.getHocPhi()) <= hocPhiTren) {
//                                                            if (kh.getGioiTinh().equals(khoaHoc.getGioiTinh())) {
//                                                                for (String bangcap : kh.getBangCap()) {
//                                                                    if (URLEncoder.encode(removeDiacriticalMarks(bangcap), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getBangCap().get(0)), "utf-8"))) {
//
//                                                                    }
//                                                                }
//                                                            }
//                                                        }
//                                                    }
//                                                } catch (UnsupportedEncodingException e) {
//                                                    e.printStackTrace();
//                                                }
//                                            }
//                                        }
//
//                                        @Override
//                                        public void onCancelled(DatabaseError databaseError) {
//
//                                        }
//                                    });
//                                } else //5 Mon!=null,HocPhi!=null,GioiTinh!=Nam, Nu,BangCap!=null, Quan = "Khác"
//                                {
//
//                                }
//                            } else//4 Mon!=null,HocPhi!=null,GioiTinh!=Nam, Nu,BangCap==null
//                            {
//                                if (khoaHoc.getDiaDiem().getQuan() != "Khác") //5 Mon!=null,HocPhi!=null,GioiTinh!=Nam, Nu,BangCap==null,Quan != "Khác"
//                                {
//
//                                } else //5 Mon!=null,HocPhi!=null,GioiTinh!=Nam, Nu,BangCap==null, Quan = "Khác"
//                                {
//
//                                }
//                            }
//                        } else //3 Mon!=null,HocPhi!=0,GioiTinh==Nam, Nữ
//                        {
//                            if (khoaHoc.getBangCap() != null) //4 Mon!=null,HocPhi!=0,GioiTinh==Nam, Nữ,BangCap!=null
//                            {
//                                if (khoaHoc.getDiaDiem().getQuan() != "Khác") //5 Mon!=null,HocPhi!=0,GioiTinh==Nam, Nữ,BangCap!=null,Quan != "Khác"
//                                {
//
//                                } else //5 Mon!=null,HocPhi!=0,GioiTinh==Nam, Nữ,BangCap!=null, Quan = "Khác"
//                                {
//
//                                }
//                            } else//4 Mon!=null,HocPhi!=0,GioiTinh==Nam, Nữ,BangCap==null
//                            {
//                                if (khoaHoc.getDiaDiem().getQuan() != "Khác") //5 Mon!=null,HocPhi!=0,GioiTinh==Nam, Nữ,BangCap==null,Quan != "Khác"
//                                {
//
//                                } else //5 Mon!=null,HocPhi!=0,GioiTinh==Nam, Nữ,BangCap==null, Quan = "Khác"
//                                {
//
//                                }
//                            }
//                        }
//                    } else//2 Mon!=null, HocPhi==0
//                    {
//                        if (khoaHoc.getGioiTinh() != "Nam, Nữ") //3 Mon!=null, HocPhi==0,GioiTinh!=Nam, Nữ
//                        {
//                            if (khoaHoc.getBangCap() != null) //4 Mon!=null, HocPhi==0,GioiTinh!=Nam, Nu,BangCap!=null
//                            {
//                                if (khoaHoc.getDiaDiem().getQuan() != "Khác") //5 Mon!=null, HocPhi==0,GioiTinh!=Nam, Nu,BangCap!=null,Quan != "Khác"
//                                {
//
//                                } else //5 Mon!=null, HocPhi==0,GioiTinh!=Nam, Nu,BangCap!=null, Quan = "Khác"
//                                {
//
//                                }
//                            } else//4 Mon!=null, HocPhi==0,GioiTinh!=Nam, Nu,BangCap==null
//                            {
//                                if (khoaHoc.getDiaDiem().getQuan() != "Khác") //5 Mon!=null, HocPhi==0,GioiTinh!=Nam, Nu,BangCap==null,Quan != "Khác"
//                                {
//
//                                } else //5 Mon!=null, HocPhi==0,GioiTinh!=Nam, Nu,BangCap==null, Quan = "Khác"
//                                {
//
//                                }
//                            }
//                        } else //3 Mon!=null, HocPhi==0,GioiTinh==Nam, Nữ
//                        {
//                            if (khoaHoc.getBangCap() != null) //4 Mon!=null, HocPhi==0,GioiTinh==Nam, Nữ,BangCap!=null
//                            {
//                                if (khoaHoc.getDiaDiem().getQuan() != "Khác") //5 Mon!=null, HocPhi==0,GioiTinh==Nam, Nữ,BangCap!=null,Quan != "Khác"
//                                {
//
//                                } else //5 Mon!=null, HocPhi==0,GioiTinh==Nam, Nữ,BangCap!=null, Quan = "Khác"
//                                {
//
//                                }
//                            } else//4 Mon!=null, HocPhi==0,GioiTinh==Nam, Nữ,BangCap==null
//                            {
//                                if (khoaHoc.getDiaDiem().getQuan() != "Khác") //5 Mon!=null, HocPhi==0,GioiTinh==Nam, Nữ,BangCap==null,Quan != "Khác"
//                                {
//
//                                } else //5 Mon!=null, HocPhi==0,GioiTinh==Nam, Nữ,BangCap==null, Quan = "Khác"
//                                {
//
//                                }
//                            }
//                        }
//                    }
//                } else//1 Mon == null
//                {
//                    if (hocPhiTimKiem != 0) //2 Mon == null, HocPhi !=0
//                    {
//                        if (khoaHoc.getGioiTinh() != "Nam, Nữ") //3 Mon == null, HocPhi !=0,GioiTinh!=Nam, Nữ
//                        {
//                            if (khoaHoc.getBangCap() != null) //4 Mon == null,HocPhi!=null,GioiTinh!=Nam, Nu,BangCap!=null
//                            {
//                                if (khoaHoc.getDiaDiem().getQuan() != "Khác") //5 Mon == null,HocPhi!=null,GioiTinh!=Nam, Nu,BangCap!=null,Quan != "Khác"
//                                {
//
//                                } else //5 Mon == null,HocPhi!=null,GioiTinh!=Nam, Nu,BangCap!=null, Quan = "Khác"
//                                {
//
//                                }
//                            } else//4 Mon == null,HocPhi!=null,GioiTinh!=Nam, Nu,BangCap==null
//                            {
//                                if (khoaHoc.getDiaDiem().getQuan() != "Khác") //5 Mon == null,HocPhi!=null,GioiTinh!=Nam, Nu,BangCap==null,Quan != "Khác"
//                                {
//
//                                } else //5 Mon == null,HocPhi!=null,GioiTinh!=Nam, Nu,BangCap==null, Quan = "Khác"
//                                {
//
//                                }
//                            }
//                        } else //3 Mon == null,HocPhi!=0,GioiTinh==Nam, Nữ
//                        {
//                            if (khoaHoc.getBangCap() != null) //4Mon == null,HocPhi!=0,GioiTinh==Nam, Nữ,BangCap!=null
//                            {
//                                if (khoaHoc.getDiaDiem().getQuan() != "Khác") //5 Mon == null,HocPhi!=0,GioiTinh==Nam, Nữ,BangCap!=null,Quan != "Khác"
//                                {
//
//                                } else //5 Mon == null,HocPhi!=0,GioiTinh==Nam, Nữ,BangCap!=null, Quan = "Khác"
//                                {
//
//                                }
//                            } else//4 Mon == null,HocPhi!=0,GioiTinh==Nam, Nữ,BangCap==null
//                            {
//                                if (khoaHoc.getDiaDiem().getQuan() != "Khác") //5 Mon == null,HocPhi!=0,GioiTinh==Nam, Nữ,BangCap==null,Quan != "Khác"
//                                {
//
//                                } else //5 Mon == null,HocPhi!=0,GioiTinh==Nam, Nữ,BangCap==null, Quan = "Khác"
//                                {
//
//                                }
//                            }
//                        }
//                    } else//2 Mon == null, HocPhi==0
//                    {
//                        if (khoaHoc.getGioiTinh() != "Nam, Nữ") //3 Mon == null, HocPhi==0,GioiTinh!=Nam, Nữ
//                        {
//                            if (khoaHoc.getBangCap() != null) //4 Mon == null, HocPhi==0,GioiTinh!=Nam, Nu,BangCap!=null
//                            {
//                                if (khoaHoc.getDiaDiem().getQuan() != "Khác") //5 Mon == null, HocPhi==0,GioiTinh!=Nam, Nu,BangCap!=null,Quan != "Khác"
//                                {
//
//                                } else //5 Mon == null, HocPhi==0,GioiTinh!=Nam, Nu,BangCap!=null, Quan = "Khác"
//                                {
//
//                                }
//                            } else//4 Mon == null, HocPhi==0,GioiTinh!=Nam, Nu,BangCap==null
//                            {
//                                if (khoaHoc.getDiaDiem().getQuan() != "Khác") //5 Mon == null, HocPhi==0,GioiTinh!=Nam, Nu,BangCap==null,Quan != "Khác"
//                                {
//
//                                } else //5 Mon == null, HocPhi==0,GioiTinh!=Nam, Nu,BangCap==null, Quan = "Khác"
//                                {
//
//                                }
//                            }
//                        } else //3 Mon == null, HocPhi==0,GioiTinh==Nam, Nữ
//                        {
//                            if (khoaHoc.getBangCap() != null) //4 Mon == null, HocPhi==0,GioiTinh==Nam, Nữ,BangCap!=null
//                            {
//                                if (khoaHoc.getDiaDiem().getQuan() != "Khác") //5 Mon == null, HocPhi==0,GioiTinh==Nam, Nữ,BangCap!=null,Quan != "Khác"
//                                {
//
//                                } else //5 Mon == null, HocPhi==0,GioiTinh==Nam, Nữ,BangCap!=null, Quan = "Khác"
//                                {
//
//                                }
//                            } else//4 Mon == null, HocPhi==0,GioiTinh==Nam, Nữ,BangCap==null
//                            {
//                                if (khoaHoc.getDiaDiem().getQuan() != "Khác") //5 Mon == null, HocPhi==0,GioiTinh==Nam, Nữ,BangCap==null,Quan != "Khác"
//                                {
//
//                                } else //5 Mon == null, HocPhi==0,GioiTinh==Nam, Nữ,BangCap==null, Quan = "Khác"
//                                {
//
//                                }
//                            }
//                        }
//                    }
//                }
//            } else//0
//            {
//                //Tìm học viên là ra bài tìm gia sư
//                if (khoaHoc.getMon() != null)//1
//                {
//                    if (hocPhiTimKiem != 0) //2 Mon!=null, HocPhi !=0
//                    {
//                        if (khoaHoc.getGioiTinh() != "Nam, Nữ") //3 Mon!=null, HocPhi !=0,GioiTinh!=Nam, Nữ
//                        {
//                            if (khoaHoc.getBangCap() != null) //4 Mon!=null,HocPhi!=null,GioiTinh!=Nam, Nu,BangCap!=null
//                            {
//                                if (khoaHoc.getDiaDiem().getQuan() != "Khác") //5 Mon!=null,HocPhi!=null,GioiTinh!=Nam, Nu,BangCap!=null,Quan != "Khác"
//                                {
//
//                                } else //5 Mon!=null,HocPhi!=null,GioiTinh!=Nam, Nu,BangCap!=null, Quan = "Khác"
//                                {
//
//                                }
//                            } else//4 Mon!=null,HocPhi!=null,GioiTinh!=Nam, Nu,BangCap==null
//                            {
//                                if (khoaHoc.getDiaDiem().getQuan() != "Khác") //5 Mon!=null,HocPhi!=null,GioiTinh!=Nam, Nu,BangCap==null,Quan != "Khác"
//                                {
//
//                                } else //5 Mon!=null,HocPhi!=null,GioiTinh!=Nam, Nu,BangCap==null, Quan = "Khác"
//                                {
//
//                                }
//                            }
//                        } else //3 Mon!=null,HocPhi!=0,GioiTinh==Nam, Nữ
//                        {
//                            if (khoaHoc.getBangCap() != null) //4 Mon!=null,HocPhi!=0,GioiTinh==Nam, Nữ,BangCap!=null
//                            {
//                                if (khoaHoc.getDiaDiem().getQuan() != "Khác") //5 Mon!=null,HocPhi!=0,GioiTinh==Nam, Nữ,BangCap!=null,Quan != "Khác"
//                                {
//
//                                } else //5 Mon!=null,HocPhi!=0,GioiTinh==Nam, Nữ,BangCap!=null, Quan = "Khác"
//                                {
//
//                                }
//                            } else//4 Mon!=null,HocPhi!=0,GioiTinh==Nam, Nữ,BangCap==null
//                            {
//                                if (khoaHoc.getDiaDiem().getQuan() != "Khác") //5 Mon!=null,HocPhi!=0,GioiTinh==Nam, Nữ,BangCap==null,Quan != "Khác"
//                                {
//
//                                } else //5 Mon!=null,HocPhi!=0,GioiTinh==Nam, Nữ,BangCap==null, Quan = "Khác"
//                                {
//
//                                }
//                            }
//                        }
//                    } else//2 Mon!=null, HocPhi==0
//                    {
//                        if (khoaHoc.getGioiTinh() != "Nam, Nữ") //3 Mon!=null, HocPhi==0,GioiTinh!=Nam, Nữ
//                        {
//                            if (khoaHoc.getBangCap() != null) //4 Mon!=null, HocPhi==0,GioiTinh!=Nam, Nu,BangCap!=null
//                            {
//                                if (khoaHoc.getDiaDiem().getQuan() != "Khác") //5 Mon!=null, HocPhi==0,GioiTinh!=Nam, Nu,BangCap!=null,Quan != "Khác"
//                                {
//
//                                } else //5 Mon!=null, HocPhi==0,GioiTinh!=Nam, Nu,BangCap!=null, Quan = "Khác"
//                                {
//
//                                }
//                            } else//4 Mon!=null, HocPhi==0,GioiTinh!=Nam, Nu,BangCap==null
//                            {
//                                if (khoaHoc.getDiaDiem().getQuan() != "Khác") //5 Mon!=null, HocPhi==0,GioiTinh!=Nam, Nu,BangCap==null,Quan != "Khác"
//                                {
//
//                                } else //5 Mon!=null, HocPhi==0,GioiTinh!=Nam, Nu,BangCap==null, Quan = "Khác"
//                                {
//
//                                }
//                            }
//                        } else //3 Mon!=null, HocPhi==0,GioiTinh==Nam, Nữ
//                        {
//                            if (khoaHoc.getBangCap() != null) //4 Mon!=null, HocPhi==0,GioiTinh==Nam, Nữ,BangCap!=null
//                            {
//                                if (khoaHoc.getDiaDiem().getQuan() != "Khác") //5 Mon!=null, HocPhi==0,GioiTinh==Nam, Nữ,BangCap!=null,Quan != "Khác"
//                                {
//
//                                } else //5 Mon!=null, HocPhi==0,GioiTinh==Nam, Nữ,BangCap!=null, Quan = "Khác"
//                                {
//
//                                }
//                            } else//4 Mon!=null, HocPhi==0,GioiTinh==Nam, Nữ,BangCap==null
//                            {
//                                if (khoaHoc.getDiaDiem().getQuan() != "Khác") //5 Mon!=null, HocPhi==0,GioiTinh==Nam, Nữ,BangCap==null,Quan != "Khác"
//                                {
//
//                                } else //5 Mon!=null, HocPhi==0,GioiTinh==Nam, Nữ,BangCap==null, Quan = "Khác"
//                                {
//
//                                }
//                            }
//                        }
//                    }
//                } else//1 Mon == null
//                {
//                    if (hocPhiTimKiem != 0) //2 Mon == null, HocPhi !=0
//                    {
//                        if (khoaHoc.getGioiTinh() != "Nam, Nữ") //3 Mon == null, HocPhi !=0,GioiTinh!=Nam, Nữ
//                        {
//                            if (khoaHoc.getBangCap() != null) //4 Mon == null,HocPhi!=null,GioiTinh!=Nam, Nu,BangCap!=null
//                            {
//                                if (khoaHoc.getDiaDiem().getQuan() != "Khác") //5 Mon == null,HocPhi!=null,GioiTinh!=Nam, Nu,BangCap!=null,Quan != "Khác"
//                                {
//
//                                } else //5 Mon == null,HocPhi!=null,GioiTinh!=Nam, Nu,BangCap!=null, Quan = "Khác"
//                                {
//
//                                }
//                            } else//4 Mon == null,HocPhi!=null,GioiTinh!=Nam, Nu,BangCap==null
//                            {
//                                if (khoaHoc.getDiaDiem().getQuan() != "Khác") //5 Mon == null,HocPhi!=null,GioiTinh!=Nam, Nu,BangCap==null,Quan != "Khác"
//                                {
//
//                                } else //5 Mon == null,HocPhi!=null,GioiTinh!=Nam, Nu,BangCap==null, Quan = "Khác"
//                                {
//
//                                }
//                            }
//                        } else //3 Mon == null,HocPhi!=0,GioiTinh==Nam, Nữ
//                        {
//                            if (khoaHoc.getBangCap() != null) //4Mon == null,HocPhi!=0,GioiTinh==Nam, Nữ,BangCap!=null
//                            {
//                                if (khoaHoc.getDiaDiem().getQuan() != "Khác") //5 Mon == null,HocPhi!=0,GioiTinh==Nam, Nữ,BangCap!=null,Quan != "Khác"
//                                {
//
//                                } else //5 Mon == null,HocPhi!=0,GioiTinh==Nam, Nữ,BangCap!=null, Quan = "Khác"
//                                {
//
//                                }
//                            } else//4 Mon == null,HocPhi!=0,GioiTinh==Nam, Nữ,BangCap==null
//                            {
//                                if (khoaHoc.getDiaDiem().getQuan() != "Khác") //5 Mon == null,HocPhi!=0,GioiTinh==Nam, Nữ,BangCap==null,Quan != "Khác"
//                                {
//
//                                } else //5 Mon == null,HocPhi!=0,GioiTinh==Nam, Nữ,BangCap==null, Quan = "Khác"
//                                {
//
//                                }
//                            }
//                        }
//                    } else//2 Mon == null, HocPhi==0
//                    {
//                        if (khoaHoc.getGioiTinh() != "Nam, Nữ") //3 Mon == null, HocPhi==0,GioiTinh!=Nam, Nữ
//                        {
//                            if (khoaHoc.getBangCap() != null) //4 Mon == null, HocPhi==0,GioiTinh!=Nam, Nu,BangCap!=null
//                            {
//                                if (khoaHoc.getDiaDiem().getQuan() != "Khác") //5 Mon == null, HocPhi==0,GioiTinh!=Nam, Nu,BangCap!=null,Quan != "Khác"
//                                {
//
//                                } else //5 Mon == null, HocPhi==0,GioiTinh!=Nam, Nu,BangCap!=null, Quan = "Khác"
//                                {
//
//                                }
//                            } else//4 Mon == null, HocPhi==0,GioiTinh!=Nam, Nu,BangCap==null
//                            {
//                                if (khoaHoc.getDiaDiem().getQuan() != "Khác") //5 Mon == null, HocPhi==0,GioiTinh!=Nam, Nu,BangCap==null,Quan != "Khác"
//                                {
//
//                                } else //5 Mon == null, HocPhi==0,GioiTinh!=Nam, Nu,BangCap==null, Quan = "Khác"
//                                {
//
//                                }
//                            }
//                        } else //3 Mon == null, HocPhi==0,GioiTinh==Nam, Nữ
//                        {
//                            if (khoaHoc.getBangCap() != null) //4 Mon == null, HocPhi==0,GioiTinh==Nam, Nữ,BangCap!=null
//                            {
//                                if (khoaHoc.getDiaDiem().getQuan() != "Khác") //5 Mon == null, HocPhi==0,GioiTinh==Nam, Nữ,BangCap!=null,Quan != "Khác"
//                                {
//
//                                } else //5 Mon == null, HocPhi==0,GioiTinh==Nam, Nữ,BangCap!=null, Quan = "Khác"
//                                {
//
//                                }
//                            } else//4 Mon == null, HocPhi==0,GioiTinh==Nam, Nữ,BangCap==null
//                            {
//                                if (khoaHoc.getDiaDiem().getQuan() != "Khác") //5 Mon == null, HocPhi==0,GioiTinh==Nam, Nữ,BangCap==null,Quan != "Khác"
//                                {
//
//                                } else //5 Mon == null, HocPhi==0,GioiTinh==Nam, Nữ,BangCap==null, Quan = "Khác"
//                                {
//
//                                }
//                            }
//                        }
//                    }
//                }
//            }

                mData.getReference().child("KhoaHoc").child("KhoaHocTimGiaSu").child("ChuaHoanTat").addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        KhoaHoc kh = dataSnapshot.getValue(KhoaHoc.class);
                        if(kh.getMon()!=null || kh.getMon().size()>=0) {
                            for (String smon : kh.getMon()) {
                                try {
                                    if (URLEncoder.encode(removeDiacriticalMarks(mon), "utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(smon), "utf-8"))) {
                                        khoaHocChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                        khoaHocGanChinhXac.add(new CustomModelKhoaHoc(dataSnapshot.getKey(), kh.getHoTen(), kh.getNguoiDang(), kh.getAvatar(), kh.getLichHoc().getThoiGian(), kh.getRating(), kh.getHocPhi(), kh.getMon()));
                                        ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHoc(khoaHocChinhXac, khoaHocGanChinhXac);
                                    }
                                } catch (UnsupportedEncodingException e) {
                                    e.printStackTrace();
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
//    }

    //Gỡ dấu

    public static String removeDiacriticalMarks(String string) {
        return Normalizer.normalize(string, Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
    }

    //
    public int cout(int x) {
        int count = 0;
        if ((x / 2) < 1) {
            count = 1;
        } else {
            count = x / 2;
        }
        return count;
    }
    //GetKhoaHocTheoLinhVuc
//    private void getListKhoaHocLinhVuc(KhoaHoc khoaHoc,String loai)
//    {
//        mData.child("KhoaHoc").child(loai).child("ChuaHoanTat").orderByChild("LinhVuc").
//    }
}
