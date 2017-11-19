package com.eways.elearning.Handler.Other;

import android.util.Log;

import com.eways.elearning.DataModel.KhoaHoc.CustomModelKhoaHoc;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by yowin on 15/11/2017.
 */

public class ParseData {
    private String data;

    public ParseData(String data) {
        this.data = data;
    }

    public ArrayList<CustomModelKhoaHoc> parseKhoaHocCustom(){
        try {
            //Parse data
            JSONObject root = new JSONObject(data);
            Iterator<String> keys = root.keys();

            ArrayList<CustomModelKhoaHoc> khoaHocArrayList = new ArrayList<CustomModelKhoaHoc>();

            while (keys.hasNext())
            {
                String key = keys.next();
                if(root.get(key) instanceof JSONObject)
                {
                    JSONObject object = root.getJSONObject(key);
                    String UIDNguoiDang = object.getString("NguoiDang");

                    JSONObject objectLichHoc = object.getJSONObject("LichHoc");
                    String BuoiHoc = objectLichHoc.getString("ThoiGian");

                    String TenNguoiDang = object.getString("HoTen");
                    //Truyền UID vào NODEJS để lấy về tên người đăng
                    String LinkAnh = object.getString("Avatar");
                    //Truyền UID vào NODEJS để lấy về Avatar người đăng

                    JSONArray objectLop = object.getJSONArray("Lop");
                    ArrayList<String> Lop = new ArrayList<>();
                    for (int i = 0;i<objectLop.length();i++)
                    {
                        Lop.add(objectLop.getString(i));
                    }

                    JSONArray objectMon = object.getJSONArray("Mon");
                    ArrayList<String> Mon = new ArrayList<>();
                    for (int i = 0;i<objectLop.length();i++)
                    {
                        Mon.add(objectLop.getString(i));
                    }

                    String rating = object.getString("Rating");

//                    CustomModelKhoaHoc kht = new CustomModelKhoaHoc(TenNguoiDang,UIDNguoiDang,LinkAnh,BuoiHoc,rating,Mon,Lop);
//                    khoaHocArrayList.add(kht);
                }

            }
            return khoaHocArrayList;
        } catch (JSONException e) {
            Log.d("zzz", "Lỗi");
        }
        return null;
    }
}
