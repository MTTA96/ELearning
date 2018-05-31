package com.eways.elearning.Views.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.eways.elearning.Adapter.Home.SubjectAdapter;
import com.eways.elearning.Adapter.Home.TopTutorAdapter;
import com.eways.elearning.Adapter.Home.TrendingAdapter;
import com.eways.elearning.Model.CourseHome;
import com.eways.elearning.Model.Trending;
import com.eways.elearning.Model.Tutor;
import com.eways.elearning.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

import javax.security.auth.Subject;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    /* VIEWS */
    RecyclerView rcTrending, rcToptutor, rcSubject;
    TextView tvToptutorMore, tvToptutorTitle;

    ArrayList<Trending> trendings;
    ArrayList<Tutor> tutors;
    ArrayList<CourseHome> courseHomes;

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
        courseHomes = new ArrayList<>();

        SetUpTrending();
        SetUpToptutor();
        SetUpSubject();
    }

    public void SetUpTrending(){
        TrendingAdapter trendingAdapter = new TrendingAdapter(R.layout.item_home_detail, trendings);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL,true);
        rcTrending.setHasFixedSize(true);
        rcTrending.setLayoutManager(layoutManager);
        rcTrending.setAdapter(trendingAdapter);
    }

    public void SetUpToptutor(){
        TopTutorAdapter topTutorAdapter = new TopTutorAdapter(R.layout.item_home_detail, tutors);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL,true);
        rcToptutor.setHasFixedSize(true);
        rcToptutor.setLayoutManager(layoutManager);
        rcToptutor.setAdapter(topTutorAdapter);
    }

    public void SetUpSubject(){
        SubjectAdapter subjectAdapter = new SubjectAdapter(R.layout.item_home_detail, courseHomes);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL,true);
        rcSubject.setHasFixedSize(true);
        rcSubject.setLayoutManager(layoutManager);
        rcSubject.setAdapter(subjectAdapter);
    }

}
