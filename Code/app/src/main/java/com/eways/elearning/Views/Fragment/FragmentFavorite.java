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
import com.eways.elearning.Model.Favorite;
import com.eways.elearning.R;
import com.eways.elearning.Utils.FileUtils;
import com.eways.elearning.Utils.Handler.FragmentHandler;
import com.eways.elearning.Utils.SupportKey;
import com.eways.elearning.Utils.params.GlobalParams;
import com.eways.elearning.Views.Fragment.Authentication.FragmentWelcome;
import com.eways.elearning.Views.Fragment.Authentication.SignupFragment;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentFavorite extends Fragment implements View.OnClickListener {

    /** VIEWS */
    RecyclerView rcFavorite;

    /** MODELS */
    private FragmentHandler fragmentHandler;

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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_favorite, container, false);

        declareViews(root);
        handleViews();

        return root;
    }

    public void declareViews(View root){
        rcFavorite = root.findViewById(R.id.list_favorite);

    }

    public void handleViews(){
        SignupFragment.btnNext.setOnClickListener(this);

        // Configure views
        setUpListFavorite();
        SignupFragment.btnNext.setText("Bỏ qua");
    }

    public void setUpListFavorite(){
        try {
            JSONArray jsonArray = new JSONArray(FileUtils.loadJSONFromAsset(getContext(), "favorite.json"));
            ArrayList<Favorite> listFavorite = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++){
                listFavorite.add(GlobalParams.getInstance().getGSon().fromJson(jsonArray.getJSONObject(i).toString(), Favorite.class));
            }

            FavoriteAdapter favoriteAdapter = new FavoriteAdapter(listFavorite, R.layout.item_favorite);
            rcFavorite.hasFixedSize();
            rcFavorite.setLayoutManager(new GridLayoutManager(getContext(), 2));
            rcFavorite.setAdapter(favoriteAdapter);


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        Fragment currentFragment = getActivity().getSupportFragmentManager().findFragmentById(R.id.content_signup);
        if (currentFragment != null && currentFragment == this) {
            {
                switch (v.getId()) {
                    case R.id.btn_next:
                        fragmentHandler.changeFragment(FragmentWelcome.newInstance(), SupportKey.WELCOME_FRAGMENT_TAG, 0, 0);
                        break;
                }
            }
        }
    }
}
