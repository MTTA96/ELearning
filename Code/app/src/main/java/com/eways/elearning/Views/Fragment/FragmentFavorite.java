package com.eways.elearning.Views.Fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eways.elearning.Adapter.Favorite.FavoriteAdapter;
import com.eways.elearning.Interfaces.DataCallback.Subject.FavSubjectWithCoursesCallBack;
import com.eways.elearning.Interfaces.OnItemClickListener;
import com.eways.elearning.Model.Subject.Favorite;
import com.eways.elearning.Model.Subject.Subject;
import com.eways.elearning.Network.Subject.SubjectListCallBack;
import com.eways.elearning.Presenter.FavoritePresenter;
import com.eways.elearning.R;
import com.eways.elearning.Utils.Handler.FragmentHandler;
import com.eways.elearning.Utils.Handler.ImageHandler;
import com.eways.elearning.Utils.SupportKeys;
import com.eways.elearning.Utils.params.GlobalParams;
import com.eways.elearning.Views.Fragment.Authentication.FragmentWelcome;
import com.eways.elearning.Views.Fragment.Authentication.SignupFragment;

import org.json.JSONArray;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentFavorite extends Fragment implements View.OnClickListener, OnItemClickListener, FavSubjectWithCoursesCallBack, SubjectListCallBack {

    /** VIEWS */
    RecyclerView rcFavorite;

    /** MODELS */
    private FragmentHandler fragmentHandler;
    private FavoritePresenter favoritePresenter;
    private ArrayList<Subject> listFavorite = new ArrayList<>();
    private ArrayList<String> selectedFavoriteList = new ArrayList<>();

    public FragmentFavorite() {
        // Required empty public constructor
    }

    public static FragmentFavorite newInstance() {
        
        Bundle args = new Bundle();
        
        FragmentFavorite fragment = new FragmentFavorite();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentHandler = new FragmentHandler(getContext(), R.id.content_signup);
        favoritePresenter = new FavoritePresenter(getContext());

        // Call api

        favoritePresenter.getSubjectList(this);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_favorite, container, false);

        declareViews(root);
        handleViews();
//        listFavorite.add(new Subject("1", "https://www.google.com.vn/url?sa=i&rct=j&q=&esrc=s&source=images&cd=&cad=rja&uact=8&ved=2ahUKEwiLjM_HgIzcAhWIMI8KHb-mAKUQjRx6BAgBEAU&url=http%3A%2F%2Ftheconversation.com%2Fmathematics-forget-simplicity-the-abstract-is-beautiful-and-important-91757&psig=AOvVaw2HpFTPi_RkzKa18JvgnQL9&ust=1531019000802712", "Toán"));
//        listFavorite.add(new Subject("2","https://www.google.com.vn/url?sa=i&rct=j&q=&esrc=s&source=images&cd=&cad=rja&uact=8&ved=2ahUKEwjMsNiQgozcAhVDuY8KHYiEC5MQjRx6BAgBEAU&url=https%3A%2F%2Fpsiloveyou.xyz%2Fenglish-as-a-second-language-bbf52f997d62&psig=AOvVaw0AvJi0hi3PBiViOps5L9vJ&ust=1531019463919106", "Anh văn"));
//        listFavorite.add(new Subject("3", "https://www.google.com.vn/url?sa=i&rct=j&q=&esrc=s&source=images&cd=&cad=rja&uact=8&ved=2ahUKEwjB3PqtgozcAhWLpY8KHTl0D0oQjRx6BAgBEAU&url=http%3A%2F%2Ftheconversation.com%2Fphysics-is-taught-badly-because-teachers-struggle-with-basic-concepts-86083&psig=AOvVaw0OBjMT4tweKHGfIynh9KLN&ust=1531019499774171", "Lý"));

        return root;
    }

    public void declareViews(View root){
        rcFavorite = root.findViewById(R.id.list_favorite);

    }

    public void handleViews(){
        SignupFragment.btnNext.setOnClickListener(this);

        // Configure views
        setUpListFavorite();
        SignupFragment.btnNext.setText(R.string.skip);
    }

    public void setUpListFavorite(){
        ImageHandler imageHandler = new ImageHandler(getContext());
        FavoriteAdapter favoriteAdapter = new FavoriteAdapter(listFavorite, R.layout.item_favorite, imageHandler, this);
        rcFavorite.hasFixedSize();
        rcFavorite.setLayoutManager(new GridLayoutManager(getContext(), 2));
        rcFavorite.setAdapter(favoriteAdapter);
    }

    /** ACTIONS */

    @Override
    public void onClick(View v) {
        Fragment currentFragment = getActivity().getSupportFragmentManager().findFragmentById(R.id.content_signup);
        if (currentFragment != null && currentFragment == this) {
            {
                switch (v.getId()) {
                    case R.id.btn_next:
                        //if (selectedFavoriteList.size() != 0) {
                            fragmentHandler.changeFragment(FragmentWelcome.newInstance(), SupportKeys.WELCOME_FRAGMENT_TAG, 0, 0);
//                        } else {
//                            favoritePresenter.addFavorite(selectedFavoriteList, this);
//                        }
                        break;
                }
            }
        }
    }

    @Override
    public void onItemClick(Bundle bundle) {

       if (bundle.getBoolean("Selected"))
           selectedFavoriteList.add(bundle.getString("FavoriteId"));
       else
           selectedFavoriteList.remove(bundle.getString("FavoriteId"));

       SignupFragment.btnNext.setText(selectedFavoriteList.size() == 0 ? R.string.skip : R.string.next);

    }

    /** DATA RESULTS */

    @Override
    public void favSubjectsCourseCallBack(int errorCode, ArrayList result) {
        if (errorCode == SupportKeys.FAILED_CODE) {
            return;
        }

        fragmentHandler.changeFragment(FragmentWelcome.newInstance(), SupportKeys.WELCOME_FRAGMENT_TAG, 0, 0);

    }

    @Override
    public void subjectListCallBack(int errorCode, ArrayList<Subject> subjects) {

        if (errorCode == SupportKeys.FAILED_CODE) {

            return;

        }

        listFavorite.addAll(subjects);

    }
}
