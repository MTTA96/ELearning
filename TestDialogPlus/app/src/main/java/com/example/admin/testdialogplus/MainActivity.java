package com.example.admin.testdialogplus;

import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.DialogPlusBuilder;
import com.orhanobut.dialogplus.GridHolder;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    DialogPlus dialogPlus;
    ArrayList<sinhVienModel> list;
    Adapter adapter;
    Button btnShow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnShow=findViewById(R.id.btnShowDialog);

        btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                list=new ArrayList<>();
                list.add(new sinhVienModel(1,R.drawable.naruto,"Sinh Viên 1"));
                list.add(new sinhVienModel(2,R.drawable.goku,"Sinh Viên 2"));
                adapter=new Adapter(MainActivity.this,R.layout.item,list);

                DialogPlusBuilder dialog = DialogPlus.newDialog(MainActivity.this);
                dialog.setContentHolder(new GridHolder(2));
                dialog.setContentHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.setAdapter(adapter);
                dialog.setExpanded(true);

                DialogPlus dialogPlus=dialog.create();
                dialogPlus.show();
            }
        });
    }
}
