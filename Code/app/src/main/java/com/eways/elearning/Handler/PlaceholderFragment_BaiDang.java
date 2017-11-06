package com.eways.elearning.Handler;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.eways.elearning.R;

/**
 * Created by Tien Phat on 05/11/2017.
 * Xử lý sự kiện chọn cho Tablayout của fragment_gia_su từ Adapter của ViewPager là SelectionPagerAdapter_BaiDang
 */

public class PlaceholderFragment_BaiDang extends Fragment {

    private static final String KEY = "key"; //Key nhận diện lệnh của người dùng tác động

    public PlaceholderFragment_BaiDang() {
    }

    // Method static dạng singleton, cho phép tạo fragment mới, lấy tham số đầu vào để làm key nhận diện
    // cài đặt thông tin đổ về fragment (ListView Bài đăng)
    public static PlaceholderFragment_BaiDang newInstance(int section) {
        PlaceholderFragment_BaiDang fragment = new PlaceholderFragment_BaiDang();
        Bundle args = new Bundle();
        args.putInt(KEY, section);
        fragment.setArguments(args);
        return fragment;
    }

    //Nhận key và trả về thông tin tương ứng cho ListView của listview_baidang
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.listview_baidang, container, false);
        //Lấy view chứa ListView sẽ hiển thị thông tin lên ViewPager của fragment_gia_su

        ListView listView = (ListView) rootView.findViewById(R.id.baidang_listview);

        ArrayAdapter<String> adapter = null;

        switch (getArguments().getInt(KEY)) {
            case 0:
                setListTimGiaSu(listView,adapter); //Đổ dữ liệu bài đăng tìm gia sư vào ListView
                break;
            case 1:
                setListTimHocVien(listView,adapter); //Đổ dữ liệu bài đăng tìm học viên vào ListView
                break;
            default:
                setListTimGiaSu(listView,adapter);
                break;
        }

        return rootView;
    }

    //Đổ dữ liệu bài đăng tìm gia sư vào ListView
    public void setListTimGiaSu(ListView list,ArrayAdapter<String> adapter)
    {

    }

    //Đổ dữ liệu bài đăng tìm học viên vào ListView
    public void setListTimHocVien(ListView list,ArrayAdapter<String> adapter)
    {

    }
}
