package com.eways.elearning.Adapter.User;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.eways.elearning.Interfaces.DataCallBack;
import com.eways.elearning.Interfaces.DataCallback.User.SendRequestCallback;
import com.eways.elearning.Model.Account.User;
import com.eways.elearning.Model.CreateCourse;
import com.eways.elearning.Presenter.Authentication.UserPresenter;
import com.eways.elearning.R;
import com.eways.elearning.Utils.Handler.ImageHandler;
import com.eways.elearning.Utils.SupportKeys;
import com.eways.elearning.Views.Activity.Course.CreateCourseActivity;
import com.eways.elearning.Views.Activity.InfoUserViewerActivity;
import com.eways.elearning.Views.Dialog.LoadingDialog;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by zzzzz on 5/27/2018.
 */

public class UserListAdapter extends RecyclerView.Adapter<UserViewHolder> implements View.OnClickListener, DataCallBack {
    private Context context;
    private LoadingDialog loadingDialog;
    private ArrayList<User> list = new ArrayList<>();
    private ImageHandler imageHandler;
    private User user;
    private UserPresenter userPresenter;
    private String subjectName = "";

    private UserViewHolder mUserviewHolder;


    public UserListAdapter(Context context, ArrayList<User> list, String subjectName) {
        this.context = context;
        this.list = list;
        this.subjectName = subjectName;
        imageHandler = new ImageHandler(context);
        userPresenter = new UserPresenter(context);
    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_tutor_list, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(UserViewHolder holder, int position) {
        user = list.get(position);

        holder.vUser.setOnClickListener(this);
        holder.btnRequest.setOnClickListener(this);

        loadData(holder, user);
    }

    @Override
    public int getItemCount() {
        return list.size();
//        return list.size() <= 5 ? list.size():5;
    }

    public void loadData(UserViewHolder holder, final User user) {
        mUserviewHolder = holder;
        // Avatar
        imageHandler.loadImageSquare(user.getAvatar(), holder.userAvatar);

        // Name
        holder.tvName.setText(user.getFirstName() + " " + user.getLastName());

        holder.btnRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, CreateCourseActivity.class);
                i.putExtra("TUTOR_ID", user.getId());
                context.startActivity(i);
            }
        });

        // Rating
//        if(user.getRating() != null)
//        {
//            try {
//                float rt = Float.parseFloat(course.getRating());
//                holder.rtbDanhGia.setRating(rt);
//            }catch (NumberFormatException e)
//            {
//                float rt = 0;
//                holder.rtbDanhGia.setRating(rt);
//            }
//        }
//        else
//        {
//            float rt = 0;
//            holder.rtbDanhGia.setRating(rt);
//        }

        //Môn
//        if(user.getSubjectName()!=null) {
//            String danhSachMon = "";
//            for (String mon : course.getSubjectName()) {
//                danhSachMon += mon;
//            }
//            String mon = "<b>Môn: </b>" + " " + danhSachMon;
//            holder.tvMon.setText(Html.fromHtml(mon));
//        }
//        else
//        {
//            holder.tvMon.setText(Html.fromHtml("Tùy ý"));
//        }
        String mon = "<b>Môn: </b>" + " " + subjectName;
        holder.tvSubject.setText(Html.fromHtml(mon));

        String skype = "<b>Skype: </b>" + " " + user.getSkype();
        holder.tvSkype.setText(Html.fromHtml(skype));

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.view_item_user_list:
                Intent userInfoIntent = new Intent(context, InfoUserViewerActivity.class);
                userInfoIntent.putExtra(SupportKeys.USER_ID_INTENT_PARAM, user.getId());
                context.startActivity(userInfoIntent);
                break;
        }
    }

    @Override
    public void dataCallBack(int resultCode, @Nullable Bundle bundle) {

    }

}
