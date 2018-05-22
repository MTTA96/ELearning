package com.eways.elearning.Adapter.Phone;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eways.elearning.Model.PhoneCode;
import com.eways.elearning.R;
import com.eways.elearning.Utils.params.GlobalParams;

import java.util.ArrayList;

/**
 * Created by ADMIN on 5/15/2018.
 */

public class PhoneCodeAdapter extends RecyclerView.Adapter<PhoneCodeVHolder> {

    private int res;
    private ArrayList<PhoneCode> listItem;

    public PhoneCodeAdapter(int res, ArrayList<PhoneCode> listItem) {
        this.res = res;
        this.listItem = listItem;
    }

    @Override
    public PhoneCodeVHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_phone_code,parent,false);
        return new PhoneCodeVHolder(view);
    }

    @Override
    public void onBindViewHolder(PhoneCodeVHolder holder, int position) {
        PhoneCode phoneCode = listItem.get(position);
        int drawableResId = GlobalParams.getInstance().get_resId_by_name(phoneCode.getImage().toLowerCase(), "drawable");
        holder.countryFlag.setImageResource(drawableResId);
    }

    @Override
    public int getItemCount() {
        return listItem.size();
    }

}
