package com.eways.elearning.Adapter.Phone;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.eways.elearning.R;


/**
 * Created by ADMIN on 5/15/2018.
 */

public class PhoneCodeVHolder extends RecyclerView.ViewHolder {
    ImageView countryFlag;
    TextView countryText;

    public PhoneCodeVHolder(View itemView) {
        super(itemView);

        countryFlag = itemView.findViewById(R.id.country_flag);
        countryText = itemView.findViewById(R.id.country_text);

    }


}
