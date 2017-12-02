package com.eways.elearning.Handler.Adapter.TaiLieuChuyenMon;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

/**
 * Created by ADMIN on 12/3/2017.
 */

public class BangCap_TaiLieuChuyenMonAdapter extends ArrayAdapter{
    Context con;
    int res;

    public BangCap_TaiLieuChuyenMonAdapter(@NonNull Context context, int resource, @NonNull Object[] objects) {
        super(context, resource, objects);
    }
}
