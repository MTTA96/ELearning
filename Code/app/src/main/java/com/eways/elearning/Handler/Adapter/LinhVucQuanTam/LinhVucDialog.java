package com.eways.elearning.Handler.Adapter.LinhVucQuanTam;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;

import com.eways.elearning.DataModel.BaiDang.LinhVucBaiDang;
import com.eways.elearning.R;

import java.util.ArrayList;

/**
 * Created by ADMIN on 11/22/2017.
 */

public class LinhVucDialog {
    Context context;
    ArrayList<LinhVucBaiDang> DanhSachLinhVuc;

    public LinhVucDialog(Context context, ArrayList<LinhVucBaiDang> danhSachLinhVuc) {
        this.context = context;
        DanhSachLinhVuc = danhSachLinhVuc;
    }

    public void ShowDialogLinhVuc(){
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_linhvuc_yeuthich);
        RecyclerView rcDanhSachLVQT = (RecyclerView) dialog.findViewById(R.id.rcLinhVucYeuThich);
        rcDanhSachLVQT.setHasFixedSize(true);

        GridLayoutManager gridLayoutManager=new GridLayoutManager(context,2);

        LinhVucQuanTamAdapter linhVucQuanTamAdapter=new LinhVucQuanTamAdapter(DanhSachLinhVuc,context);

        rcDanhSachLVQT.setLayoutManager(gridLayoutManager);
        rcDanhSachLVQT.setAdapter(linhVucQuanTamAdapter);
        Window window = dialog.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.show();
    }

}
