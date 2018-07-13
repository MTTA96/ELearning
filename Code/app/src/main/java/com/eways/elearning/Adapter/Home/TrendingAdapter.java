package com.eways.elearning.Adapter.Home;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eways.elearning.Model.Subject.Subject;
import com.eways.elearning.R;
import com.eways.elearning.Utils.Handler.FragmentHandler;
import com.eways.elearning.Utils.Handler.ImageHandler;
import com.eways.elearning.Utils.SupportKeys;
import com.eways.elearning.Utils.params.GlobalParams;
import com.eways.elearning.Views.Activity.HomeActivity;
import com.eways.elearning.Views.Fragment.SearchAndFilter.SearchFragment;

import java.util.ArrayList;

/**
 * Created by ADMIN on 5/28/2018.
 */

public class TrendingAdapter extends RecyclerView.Adapter<HomeVHolder> {

    private Context context;
    private int res;
    private ArrayList<Subject> trendings;
    private ImageHandler imageHandler;
    private FragmentHandler fragmentHandler;

    public TrendingAdapter(Context context, int res, ArrayList<Subject> trending) {
        this.context = context;
        this.res = res;
        this.trendings = trending;

        imageHandler = new ImageHandler(GlobalParams.getInstance());
        fragmentHandler = new FragmentHandler(context, R.id.home_content_view);
    }

    @Override
    public HomeVHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_home_detail,parent,false);
        return new HomeVHolder(view);
    }

    @Override
    public void onBindViewHolder(HomeVHolder holder, int position) {
        final Subject subject = trendings.get(position);

        imageHandler.loadImageRound(subject.getImg(), holder.ivHomeDetail);
        holder.tvTitle.setText(subject.getSubjectName());

        holder.ivHomeDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentHandler.changeFragment(SearchFragment.newInstance(subject.getSubjectName()), SupportKeys.SEARCH_RESULTS_TAG, R.anim.slide_from_left, R.anim.slide_out_top);
                ((HomeActivity) context).HideSearchBar();
            }
        });
    }

    @Override
    public int getItemCount() {
        return trendings.size();
    }

}
