package com.eways.elearning.Model.TimKiemKhoaHoc;

import com.eways.elearning.DataModel.KhoaHoc.CustomModelKhoaHoc;
import com.eways.elearning.DataModel.KhoaHoc.KhoaHoc;
import com.eways.elearning.Presenter.TimKiemKhoaHoc.KetQuaTimKiemKhoaHocFragmentPresenterImp;
import com.eways.elearning.Util.SupportKeysList;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
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
    DatabaseReference mData = FirebaseDatabase.getInstance().getReference();
    ArrayList<CustomModelKhoaHoc> khoaHocChinhXac = new ArrayList<>();
    ArrayList<CustomModelKhoaHoc> khoaHocGanChinhXac = new ArrayList<>();
    int countTemp =0;



    double hocPhiTren = 0;
    double hocPhiDuoi = 0;
    double hocPhi = 0;
    public KetQuaTimKiemFragmentModel(KetQuaTimKiemKhoaHocFragmentPresenterImp ketQuaTimKiemKhoaHocFragmentPresenterImp) {
        this.ketQuaTimKiemKhoaHocFragmentPresenterImp = ketQuaTimKiemKhoaHocFragmentPresenterImp;
    }

    @Override
    public void getListKhoaHoc(final KhoaHoc khoaHoc, boolean loai) {
        final float hocPhiTimKiem = Float.parseFloat(khoaHoc.getHocPhi());
        final int count = cout(khoaHoc.getLichHoc().getNgayHoc().size());

        if (loai == true) {
            mData.child(SupportKeysList.CHILD_KHOAHOC).child(SupportKeysList.CHILD_KHOAHOC_TIMGIASU).child(SupportKeysList.CHILD_KHOAHOC_CHUAHOANTAT).addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    KhoaHoc kh = dataSnapshot.getValue(KhoaHoc.class);
                    hocPhi = Double.parseDouble(kh.getHocPhi());
                    hocPhiTren = ( hocPhi + ((hocPhi/100)*10) );
                    hocPhiDuoi = ( hocPhi - ((hocPhi/100)*10) );
                    if(khoaHoc.getMon() != null)
                    {
                        for(String mon : kh.getMon())
                        {
                            try {
                                if(URLEncoder.encode(removeDiacriticalMarks(mon).toLowerCase(),"utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getMon().get(0)).toLowerCase(),"utf-8")))
                                {
                                    if(khoaHoc.getLinhVuc() != null)
                                    {
                                        for(String linhVuc : khoaHoc.getLinhVuc())
                                        {
                                            if(URLEncoder.encode(removeDiacriticalMarks(linhVuc).toLowerCase(),"utf-8").contains(URLEncoder.encode(khoaHoc.getLinhVuc().get(0).toLowerCase(),"utf-8")))
                                            {
                                                khoaHocGanChinhXac.add(new CustomModelKhoaHoc(kh.getHoTen(),kh.getNguoiDang(),kh.getAvatar(),kh.getLichHoc().getThoiGian(),kh.getRating(),kh.getHocPhi(),kh.getMon()));
                                                ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHocGanChinhXac(khoaHocGanChinhXac);
                                                if( hocPhiTimKiem>=hocPhiDuoi && hocPhiTimKiem <= hocPhiTren)
                                                {
                                                    for(String buoi : khoaHoc.getLichHoc().getThoiGian())
                                                    {
                                                        for(String b : kh.getLichHoc().getThoiGian())
                                                        {
                                                            if(buoi.equals(b))
                                                            {
                                                                for(String ngay : khoaHoc.getLichHoc().getNgayHoc())
                                                                {
                                                                    for(String ng : kh.getLichHoc().getNgayHoc())
                                                                    {
                                                                        if(ngay.equals(ng))
                                                                        {
                                                                            countTemp++;
                                                                            if(countTemp == count)
                                                                            {
                                                                                khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getHoTen(),kh.getNguoiDang(),kh.getAvatar(),kh.getLichHoc().getThoiGian(),kh.getRating(),kh.getHocPhi(),kh.getMon()));
                                                                                ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHocChinhXac(khoaHocChinhXac);
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
                                        ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHoc(khoaHocChinhXac,khoaHocGanChinhXac);
                                    }
                                    else //Khi chi co mon ma ko co linh vuc
                                    {
                                        khoaHocGanChinhXac.add(new CustomModelKhoaHoc(kh.getHoTen(),kh.getNguoiDang(),kh.getAvatar(),kh.getLichHoc().getThoiGian(),kh.getRating(),kh.getHocPhi(),kh.getMon()));
                                        ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHocGanChinhXac(khoaHocGanChinhXac);
                                        if( hocPhiTimKiem>=hocPhiDuoi && hocPhiTimKiem <= hocPhiTren)
                                        {
                                            for(String buoi : khoaHoc.getLichHoc().getThoiGian())
                                            {
                                                for(String b : kh.getLichHoc().getThoiGian())
                                                {
                                                    if(buoi.equals(b))
                                                    {
                                                        for(String ngay : khoaHoc.getLichHoc().getNgayHoc())
                                                        {
                                                            for(String ng : kh.getLichHoc().getNgayHoc())
                                                            {
                                                                if(ngay.equals(ng))
                                                                {
                                                                    countTemp++;
                                                                    if(countTemp == count)
                                                                    {
                                                                        khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getHoTen(),kh.getNguoiDang(),kh.getAvatar(),kh.getLichHoc().getThoiGian(),kh.getRating(),kh.getHocPhi(),kh.getMon()));
                                                                        ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHocChinhXac(khoaHocChinhXac);
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                        ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHoc(khoaHocChinhXac,khoaHocGanChinhXac);
                                    }
                                }
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    else // Khi ko co mon ma co linh vuc
                    {
                        if(khoaHoc.getLinhVuc() != null)
                        {
                            for(String linhVuc : khoaHoc.getLinhVuc())
                            {
                                try {
                                    if(URLEncoder.encode(removeDiacriticalMarks(linhVuc).toLowerCase(),"utf-8").contains(URLEncoder.encode(khoaHoc.getLinhVuc().get(0).toLowerCase(),"utf-8")))
                                    {
                                        khoaHocGanChinhXac.add(new CustomModelKhoaHoc(kh.getHoTen(),kh.getNguoiDang(),kh.getAvatar(),kh.getLichHoc().getThoiGian(),kh.getRating(),kh.getHocPhi(),kh.getMon()));
                                        ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHocGanChinhXac(khoaHocGanChinhXac);
                                        if( hocPhiTimKiem>=hocPhiDuoi && hocPhiTimKiem <= hocPhiTren)
                                        {
                                            for(String buoi : khoaHoc.getLichHoc().getThoiGian())
                                            {
                                                for(String b : kh.getLichHoc().getThoiGian())
                                                {
                                                    if(buoi.equals(b))
                                                    {
                                                        for(String ngay : khoaHoc.getLichHoc().getNgayHoc())
                                                        {
                                                            for(String ng : kh.getLichHoc().getNgayHoc())
                                                            {
                                                                if(ngay.equals(ng))
                                                                {
                                                                    countTemp++;
                                                                    if(countTemp == count)
                                                                    {
                                                                        khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getHoTen(),kh.getNguoiDang(),kh.getAvatar(),kh.getLichHoc().getThoiGian(),kh.getRating(),kh.getHocPhi(),kh.getMon()));
                                                                        ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHocChinhXac(khoaHocChinhXac);
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                } catch (UnsupportedEncodingException e) {
                                    e.printStackTrace();
                                }
                            }
                            ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHoc(khoaHocChinhXac,khoaHocGanChinhXac);
                        }
                        else
                        {
                            khoaHocGanChinhXac.add(new CustomModelKhoaHoc(kh.getHoTen(),kh.getNguoiDang(),kh.getAvatar(),kh.getLichHoc().getThoiGian(),kh.getRating(),kh.getHocPhi(),kh.getMon()));
                            ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHocGanChinhXac(khoaHocGanChinhXac);
                            if( hocPhiTimKiem>=hocPhiDuoi && hocPhiTimKiem <= hocPhiTren)
                            {
                                for(String buoi : khoaHoc.getLichHoc().getThoiGian())
                                {
                                    for(String b : kh.getLichHoc().getThoiGian())
                                    {
                                        if(buoi.equals(b))
                                        {
                                            for(String ngay : khoaHoc.getLichHoc().getNgayHoc())
                                            {
                                                for(String ng : kh.getLichHoc().getNgayHoc())
                                                {
                                                    if(ngay.equals(ng))
                                                    {
                                                        countTemp++;
                                                        if(countTemp == count)
                                                        {
                                                            khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getHoTen(),kh.getNguoiDang(),kh.getAvatar(),kh.getLichHoc().getThoiGian(),kh.getRating(),kh.getHocPhi(),kh.getMon()));
                                                            ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHocChinhXac(khoaHocChinhXac);
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHoc(khoaHocChinhXac,khoaHocGanChinhXac);
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
                    KhoaHoc kh = dataSnapshot.getValue(KhoaHoc.class);
                    hocPhi = Double.parseDouble(kh.getHocPhi());
                    hocPhiTren = ( hocPhi + ((hocPhi/100)*10) );
                    hocPhiDuoi = ( hocPhi - ((hocPhi/100)*10) );
                    if(khoaHoc.getMon() != null)
                    {
                        for(String mon : kh.getMon())
                        {
                            try {
                                if(URLEncoder.encode(removeDiacriticalMarks(mon).toLowerCase(),"utf-8").contains(URLEncoder.encode(removeDiacriticalMarks(khoaHoc.getMon().get(0)).toLowerCase(),"utf-8")))
                                {
                                    if(khoaHoc.getLinhVuc() != null)
                                    {
                                        for(String linhVuc : khoaHoc.getLinhVuc())
                                        {
                                            if(URLEncoder.encode(removeDiacriticalMarks(linhVuc).toLowerCase(),"utf-8").contains(URLEncoder.encode(khoaHoc.getLinhVuc().get(0).toLowerCase(),"utf-8")))
                                            {
                                                khoaHocGanChinhXac.add(new CustomModelKhoaHoc(kh.getHoTen(),kh.getNguoiDang(),kh.getAvatar(),kh.getLichHoc().getThoiGian(),kh.getRating(),kh.getHocPhi(),kh.getMon()));
                                                ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHocGanChinhXac(khoaHocGanChinhXac);
                                                if( hocPhiTimKiem>=hocPhiDuoi && hocPhiTimKiem <= hocPhiTren)
                                                {
                                                    for(String buoi : khoaHoc.getLichHoc().getThoiGian())
                                                    {
                                                        for(String b : kh.getLichHoc().getThoiGian())
                                                        {
                                                            if(buoi.equals(b))
                                                            {
                                                                for(String ngay : khoaHoc.getLichHoc().getNgayHoc())
                                                                {
                                                                    for(String ng : kh.getLichHoc().getNgayHoc())
                                                                    {
                                                                        if(ngay.equals(ng))
                                                                        {
                                                                            countTemp++;
                                                                            if(countTemp == count)
                                                                            {
                                                                                khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getHoTen(),kh.getNguoiDang(),kh.getAvatar(),kh.getLichHoc().getThoiGian(),kh.getRating(),kh.getHocPhi(),kh.getMon()));
                                                                                ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHocChinhXac(khoaHocChinhXac);
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
                                        ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHoc(khoaHocChinhXac,khoaHocGanChinhXac);
                                    }
                                    else //Khi chi co mon ma ko co linh vuc
                                    {
                                        khoaHocGanChinhXac.add(new CustomModelKhoaHoc(kh.getHoTen(),kh.getNguoiDang(),kh.getAvatar(),kh.getLichHoc().getThoiGian(),kh.getRating(),kh.getHocPhi(),kh.getMon()));
                                        ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHocGanChinhXac(khoaHocGanChinhXac);
                                        if( hocPhiTimKiem>=hocPhiDuoi && hocPhiTimKiem <= hocPhiTren)
                                        {
                                            for(String buoi : khoaHoc.getLichHoc().getThoiGian())
                                            {
                                                for(String b : kh.getLichHoc().getThoiGian())
                                                {
                                                    if(buoi.equals(b))
                                                    {
                                                        for(String ngay : khoaHoc.getLichHoc().getNgayHoc())
                                                        {
                                                            for(String ng : kh.getLichHoc().getNgayHoc())
                                                            {
                                                                if(ngay.equals(ng))
                                                                {
                                                                    countTemp++;
                                                                    if(countTemp == count)
                                                                    {
                                                                        khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getHoTen(),kh.getNguoiDang(),kh.getAvatar(),kh.getLichHoc().getThoiGian(),kh.getRating(),kh.getHocPhi(),kh.getMon()));
                                                                        ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHocChinhXac(khoaHocChinhXac);
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                        ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHoc(khoaHocChinhXac,khoaHocGanChinhXac);
                                    }
                                }
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    else // Khi ko co mon ma co linh vuc
                    {
                        if(khoaHoc.getLinhVuc() != null)
                        {
                            for(String linhVuc : khoaHoc.getLinhVuc())
                            {
                                try {
                                    if(URLEncoder.encode(removeDiacriticalMarks(linhVuc).toLowerCase(),"utf-8").contains(URLEncoder.encode(khoaHoc.getLinhVuc().get(0).toLowerCase(),"utf-8")))
                                    {
                                        khoaHocGanChinhXac.add(new CustomModelKhoaHoc(kh.getHoTen(),kh.getNguoiDang(),kh.getAvatar(),kh.getLichHoc().getThoiGian(),kh.getRating(),kh.getHocPhi(),kh.getMon()));
                                        ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHocGanChinhXac(khoaHocGanChinhXac);
                                        if( hocPhiTimKiem>=hocPhiDuoi && hocPhiTimKiem <= hocPhiTren)
                                        {
                                            for(String buoi : khoaHoc.getLichHoc().getThoiGian())
                                            {
                                                for(String b : kh.getLichHoc().getThoiGian())
                                                {
                                                    if(buoi.equals(b))
                                                    {
                                                        for(String ngay : khoaHoc.getLichHoc().getNgayHoc())
                                                        {
                                                            for(String ng : kh.getLichHoc().getNgayHoc())
                                                            {
                                                                if(ngay.equals(ng))
                                                                {
                                                                    countTemp++;
                                                                    if(countTemp == count)
                                                                    {
                                                                        khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getHoTen(),kh.getNguoiDang(),kh.getAvatar(),kh.getLichHoc().getThoiGian(),kh.getRating(),kh.getHocPhi(),kh.getMon()));
                                                                        ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHocChinhXac(khoaHocChinhXac);
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                } catch (UnsupportedEncodingException e) {
                                    e.printStackTrace();
                                }
                            }
                            ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHoc(khoaHocChinhXac,khoaHocGanChinhXac);
                        }
                        else
                        {
                            khoaHocGanChinhXac.add(new CustomModelKhoaHoc(kh.getHoTen(),kh.getNguoiDang(),kh.getAvatar(),kh.getLichHoc().getThoiGian(),kh.getRating(),kh.getHocPhi(),kh.getMon()));
                            ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHocGanChinhXac(khoaHocGanChinhXac);
                            if( hocPhiTimKiem>=hocPhiDuoi && hocPhiTimKiem <= hocPhiTren)
                            {
                                for(String buoi : khoaHoc.getLichHoc().getThoiGian())
                                {
                                    for(String b : kh.getLichHoc().getThoiGian())
                                    {
                                        if(buoi.equals(b))
                                        {
                                            for(String ngay : khoaHoc.getLichHoc().getNgayHoc())
                                            {
                                                for(String ng : kh.getLichHoc().getNgayHoc())
                                                {
                                                    if(ngay.equals(ng))
                                                    {
                                                        countTemp++;
                                                        if(countTemp == count)
                                                        {
                                                            khoaHocChinhXac.add(new CustomModelKhoaHoc(kh.getHoTen(),kh.getNguoiDang(),kh.getAvatar(),kh.getLichHoc().getThoiGian(),kh.getRating(),kh.getHocPhi(),kh.getMon()));
                                                            ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHocChinhXac(khoaHocChinhXac);
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        ketQuaTimKiemKhoaHocFragmentPresenterImp.nhanListKhoaHoc(khoaHocChinhXac,khoaHocGanChinhXac);
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
    //Gỡ dấu
    public static String removeDiacriticalMarks(String string) {
        return Normalizer.normalize(string, Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
    }
    //
    public int cout(int x)
    {
        int count = 0;
        if( (x/2) < 1 )
        {
            count = 1;
        }
        else
        {
            count = x/2;
        }
        return count;
    }
}
