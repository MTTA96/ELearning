package com.eways.elearning.Views.Fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.eways.elearning.Adapter.Home.SubjectAdapter;
import com.eways.elearning.Adapter.Home.TopTutorAdapter;
import com.eways.elearning.Adapter.Home.TrendingAdapter;
import com.eways.elearning.Interfaces.DataCallback.Subject.FavSubjectWithCoursesCallBack;
import com.eways.elearning.Interfaces.DataCallback.Subject.TrendingSubjectCallBack;
import com.eways.elearning.Interfaces.DataCallback.User.TopTutorsCallBack;
import com.eways.elearning.Model.FavoriteSubjectWithCourses;
import com.eways.elearning.Model.Subject;
import com.eways.elearning.Model.User;
import com.eways.elearning.Presenter.HomePresenter;
import com.eways.elearning.R;
import com.eways.elearning.Utils.SupportKey;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements TopTutorsCallBack, TrendingSubjectCallBack, FavSubjectWithCoursesCallBack {
    /* VIEWS */
    RecyclerView rcTrending, rcToptutor, rcSubject;
    TextView tvToptutorMore, tvToptutorTitle;

    /** MODELS */
    private HomePresenter homePresenter;
    private ArrayList<Subject> trendings = new ArrayList<>();
    private ArrayList<User> tutors = new ArrayList<>();
    private ArrayList<FavoriteSubjectWithCourses> favCourses = new ArrayList<>();
    private TopTutorAdapter topTutorAdapter;
    private TrendingAdapter trendingAdapter;
    private SubjectAdapter favSubjectCoursesAdapter;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance() {

        Bundle args = new Bundle();

        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        homePresenter = new HomePresenter();
        //homePresenter.getBanners(this);
        homePresenter.getTopTutors(this);
        homePresenter.getTrendingSubjects(this);
        //homePresenter.getUserFavoriteSubjects(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        declare_views(root);
        handle_views();

        return root;
    }

    public void declare_views(View root){
        rcTrending = root.findViewById(R.id.item_trending);
        rcToptutor = root.findViewById(R.id.item_toptutor);
        rcSubject = root.findViewById(R.id.rc_subject);
    }

    public void handle_views(){
        trendings = new ArrayList<>();
        tutors = new ArrayList<>();
        favCourses = new ArrayList<>();

        SetUpTrending();
        SetUpToptutor();
        SetUpSubject();
    }

    public void SetUpTrending(){
        trendingAdapter = new TrendingAdapter(R.layout.item_home_detail, trendings);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL,true);
        rcTrending.setHasFixedSize(true);
        rcTrending.setLayoutManager(layoutManager);
        rcTrending.setAdapter(trendingAdapter);
    }

    public void SetUpToptutor(){
        topTutorAdapter = new TopTutorAdapter(R.layout.item_home_detail, tutors);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL,true);
        rcToptutor.setHasFixedSize(true);
        rcToptutor.setLayoutManager(layoutManager);
        rcToptutor.setAdapter(topTutorAdapter);
    }

    public void SetUpSubject(){
        favSubjectCoursesAdapter = new SubjectAdapter(R.layout.item_home_detail, favCourses);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL,true);
        rcSubject.setHasFixedSize(true);
        rcSubject.setLayoutManager(layoutManager);
        rcSubject.setAdapter(favSubjectCoursesAdapter);
    }

    /** HANDLE RESULTS FROM SERVER */

    @Override
    public void topTutorCallBack(int errorCode, ArrayList result) {
        // Handle error
        if(errorCode == SupportKey.FAILED_CODE) {
            Log.d(this.getTag(), "Get top tutors failed!");
            return;
        }

        // Get data success
        tutors.clear();
        tutors.addAll(result);
        topTutorAdapter.notifyDataSetChanged();
    }

    @Override
    public void trendingSubjectsCallBack(int errorCode, ArrayList result) {
        // Handle error
        if(errorCode == SupportKey.FAILED_CODE) {
            Log.d(this.getTag(), "Get top tutors failed!");
            return;
        }

        // Get data success
        trendings.clear();
        trendings.addAll(result);
        trendingAdapter.notifyDataSetChanged();
    }

    @Override
    public void favSubjectsCourseCallBack(int errorCode, ArrayList result) {
        // Handle error
        if(errorCode == SupportKey.FAILED_CODE) {
            Log.d(this.getTag(), "Get top tutors failed!");
            return;
        }

        // Get data success
        favCourses.clear();
        favCourses.addAll(result);
        favSubjectCoursesAdapter.notifyDataSetChanged();
    }
}
