package com.example.admin.testdialogplus;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by ADMIN on 11/29/2017.
 */

public class Adapter extends ArrayAdapter  {
    Activity con;
    int res;
    ArrayList<sinhVienModel> list;
    public Adapter(@NonNull Activity context, int resource, @NonNull ArrayList<sinhVienModel> objects) {
        super(context, resource, objects);

        con=context;
        res=resource;
        list=objects;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = con.getLayoutInflater();
        convertView=inflater.inflate(res,null);
        final sinhVienModel sinhVien=list.get(position);

        TextView textView=(TextView) convertView.findViewById(R.id.tvTitle);
        ImageView imageView=(ImageView) convertView.findViewById(R.id.ivImage);
        LinearLayout loView=(LinearLayout) convertView.findViewById(R.id.loView);

        textView.setText(sinhVien.getContent());
        imageView.setBackgroundResource(sinhVien.getImage());

        loView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(con,"selected " + position,Toast.LENGTH_SHORT).show();
            }
        });

        return convertView;
    }


}
