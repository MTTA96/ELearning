package com.eways.elearning.Handler.Other;

import android.util.Log;

import com.eways.elearning.DataModel.KhoaHoc.CustomModelKhoaHoc;
import com.eways.elearning.DataModel.TaiKhoan;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by ADMIN on 12/10/2017.
 */

public class ParseDataTaiKhoan  {
    String data;

    public ParseDataTaiKhoan(String data) {
        this.data = data;
    }

    public ArrayList<TaiKhoan> ParseTaiKhoan(){
        try {
            //Parse data
            JSONObject root = new JSONObject(data);
            Iterator<String> keys = root.keys();

            ArrayList<TaiKhoan> taiKhoanList = new ArrayList<TaiKhoan>();

            while (keys.hasNext())
            {
                String key = keys.next();
                boolean dadangnhapTaiKhoan,dacapnhatTaiKhoan;
                String idTaiKhoan,emailTaiKhoan,hoTaiKhoan,tenTaiKhoan,tentaikhoanTaiKhoan,loaitaikhoanTaiKhoan,passwordTaiKhoan,nghenghiepTaiKhoan,namsinhTaiKhoan,gioitinhTaiKhoan,tailieuxacminh_mt,tailieuxacminh_ms,trinhdoTaiKhoan,diadiemTaiKhoan,sodienthoaiTaiKhoan,avartaTaiKhoan,ratingTaiKhoan;
                if(root.get(key) instanceof JSONObject)
                {
                    JSONObject object = root.getJSONObject(key);
                    if (object.getString("id")!=null)
                        idTaiKhoan = object.getString("id");
                    else
                        idTaiKhoan="null";

                    if (object.getString("email")!=null)
                        emailTaiKhoan=object.getString("email");
                    else
                        emailTaiKhoan="null";

                    if (object.getString("ho")!=null)
                        hoTaiKhoan = object.getString("ho");
                    else
                        hoTaiKhoan="null";

                    if (object.getString("ten")!=null)
                        tenTaiKhoan = object.getString("ten");
                    else
                        tenTaiKhoan="null";

//                    if (object.getString("tentaikhoan")!=null)
//                        tentaikhoanTaiKhoan = object.getString("tentaikhoan");
//                    else
//                        tentaikhoanTaiKhoan="null";

                    if (object.getBoolean("dadangnhap"))
                        dadangnhapTaiKhoan=object.getBoolean("dadangnhap");
                    else
                        dadangnhapTaiKhoan=false;

                    if (object.getString("loaitaikhoan")!=null)
                        loaitaikhoanTaiKhoan=object.getString("loaitaikhoan");
                    else
                        loaitaikhoanTaiKhoan="null";

                    if (object.getString("password")!=null)
                        passwordTaiKhoan=object.getString("password");
                    else
                        passwordTaiKhoan="null";

                    if (object.getString("nghenghiep")!=null)
                        nghenghiepTaiKhoan=object.getString("nghenghiep");
                    else
                        nghenghiepTaiKhoan="null";

                    if (object.getString("namsinh")!=null)
                        namsinhTaiKhoan=object.getString("namsinh");
                    else
                        namsinhTaiKhoan="null";

                    if (object.getString("gioitinh")!=null)
                        gioitinhTaiKhoan=object.getString("gioitinh");
                    else
                        gioitinhTaiKhoan="null";

                    if (object.getString("tailieuxacminh_mt")!=null)
                        tailieuxacminh_mt=object.getString("tailieuxacminh_mt");
                    else
                        tailieuxacminh_mt="null";

                    if (object.getString("tailieuxacminh_ms")!=null)
                        tailieuxacminh_ms=object.getString("tailieuxacminh_ms");
                    else
                        tailieuxacminh_ms="null";


                    if (object.getString("trinhdo")!=null)
                        trinhdoTaiKhoan=object.getString("trinhdo");
                    else
                        trinhdoTaiKhoan="null";

                    if (object.getString("diadiem")!=null)
                        diadiemTaiKhoan = object.getString("diadiem");
                    else
                        diadiemTaiKhoan="null";

                    if (object.getString("sodienthoai")!=null)
                        sodienthoaiTaiKhoan=object.getString("sodienthoai");
                    else
                        sodienthoaiTaiKhoan="null";

                    if (object.getString("avatar")!=null)
                        avartaTaiKhoan=object.getString("avatar");
                    else
                        avartaTaiKhoan="null";
                    if (object.getBoolean("dacapnhat"))
                        dacapnhatTaiKhoan=object.getBoolean("dacapnhat");
                    else
                        dacapnhatTaiKhoan=false;
                    ratingTaiKhoan=object.getString("rating");

                    //Truyền UID vào NODEJS để lấy về Avatar người đăng

//                    JSONArray objectLop = object.getJSONArray("Lop");
//                    ArrayList<String> Lop = new ArrayList<>();
//                    for (int i = 0;i<objectLop.length();i++)
//                    {
//                        Lop.add(objectLop.getString(i));
//                    }
//
//                    JSONArray objectMon = object.getJSONArray("Mon");
//                    ArrayList<String> Mon = new ArrayList<>();
//                    for (int i = 0;i<objectLop.length();i++)
//                    {
//                        Mon.add(objectLop.getString(i));
//                    }



//                    CustomModelKhoaHoc kht = new CustomModelKhoaHoc(TenNguoiDang,UIDNguoiDang,LinkAnh,BuoiHoc,rating,Mon,Lop);
//                    khoaHocArrayList.add(kht);
                    TaiKhoan taiKhoan=new TaiKhoan(idTaiKhoan,emailTaiKhoan,hoTaiKhoan,tenTaiKhoan,"null",dadangnhapTaiKhoan,loaitaikhoanTaiKhoan,passwordTaiKhoan,nghenghiepTaiKhoan,namsinhTaiKhoan,gioitinhTaiKhoan,tailieuxacminh_mt,tailieuxacminh_ms,trinhdoTaiKhoan,diadiemTaiKhoan,sodienthoaiTaiKhoan,avartaTaiKhoan,dacapnhatTaiKhoan,ratingTaiKhoan);
                    taiKhoanList.add(taiKhoan);
                }
            }
            return taiKhoanList;
        } catch (JSONException e) {
            Log.d("zzz", e.toString());
        }
        return null;
    }
}
