package com.eways.elearning.Handler.Other;

import android.os.AsyncTask;
import android.os.Bundle;

import com.eways.elearning.DataModel.KhoaHoc.CustomModelKhoaHoc;
import com.eways.elearning.Util.SupportKeysList;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by yowin on 15/11/2017.
 */

public class GetData extends AsyncTask<String, Void, String> {

    String API = null;
    DataCallBack dataCallBack;

    public GetData(DataCallBack dataCallBack) {
        this.dataCallBack = dataCallBack;
    }

    /**
     * strings[0] là API
     * strings[1] là URL API
     * strings[2] là METHOD
     */
    @Override
    protected String doInBackground(String... strings) {
        try {
            URL url = new URL(strings[1]);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            API = strings[0]; // lưu lại api để xử lý kết quả ở postexcute
            switch (strings[2]) {
                case "GET":
                    //Cài đặt các thiết lập gửi server
                    conn.setRequestProperty("Accept", "application/json");
                    conn.setRequestMethod("GET");
                    conn.connect();

                    if (conn.getResponseCode() == HttpURLConnection.HTTP_OK || conn.getResponseCode() == HttpURLConnection.HTTP_CREATED) {
                        InputStream inputStream = conn.getInputStream();
                        ByteArrayOutputStream result = new ByteArrayOutputStream();
                        byte[] buffer = new byte[1024];
                        int length;
                        while ((length = inputStream.read(buffer)) != -1) {
                            result.write(buffer, 0, length);
                        }

                        JSONObject object = new JSONObject(result.toString("UTF-8"));
                        //Data nhận duoc từ server
                        return result.toString("UTF-8");
                    }
                    return SupportKeysList.GET_DATA_LOI;
            }
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        switch (API) {
            case SupportKeysList.API_GET_KHOAHOC:
                //Truyền kết quả về cho class yêu cầu

                //Parse json thành model
//                ArrayList<KhoaHoc> khoaHocArrayList = new ParseKhoaHoc(result).parseData();

                ArrayList<CustomModelKhoaHoc> khoaHocArrayList = new ParseData(result).parseKhoaHocCustom();


                // Chuẩn bị data (bundle, string,... ) trả về cho class gọi
                Bundle dataKhoaHoc = new Bundle();
                dataKhoaHoc.putSerializable(SupportKeysList.API_GET_KHOAHOC, khoaHocArrayList);

                //Dùng interface trả kết quả cho class
                dataCallBack.KetQua(SupportKeysList.GET_DATA_THANHCONG, dataKhoaHoc);
                break;
        }
    }
}
