package com.eways.elearning.View.Fragment.Home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.eways.elearning.Handler.Adapter.ViewPagerAdapter;
import com.eways.elearning.Model.CustomClasses.CustomViewPager;
import com.eways.elearning.Model.Database.SharedPreferencesHandler;
import com.eways.elearning.R;
import com.eways.elearning.Util.SupportKeysList;
import com.eways.elearning.View.Activity.MainActivity;
import com.eways.elearning.View.Dialog.LoadingDialog;
import com.eways.elearning.View.Fragment.Home.HomeTimGiaSu.HomeTimGiaSuFragment;
import com.eways.elearning.View.Fragment.Home.HomeTimHocVien.HomeTimHocVienFragment;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewHomeFragment extends Fragment {
    private final String titleTab1 = "Tìm gia sư";
    private final String titleTab2 = "Tìm học viên";

    private DatabaseReference mData = FirebaseDatabase.getInstance().getReference();
    private SharedPreferencesHandler sharedPreferencesHandler;

    public NewHomeFragment() {
        // Required empty public constructor
    }

    public static NewHomeFragment newInstance() {
        
        Bundle args = new Bundle();
        
        NewHomeFragment fragment = new NewHomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferencesHandler=new SharedPreferencesHandler(getContext(), SupportKeysList.SHARED_PREF_FILE_NAME);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_new_home, container, false);
        LoadingDialog.showDialog();
        setUpViewPager((CustomViewPager) root.findViewById(R.id.viewPager_PhanMuc_Home));
        return root;
    }

    private void setUpViewPager(CustomViewPager viewPager) {
        viewPager.setPagingEnabled(false);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager());
        viewPagerAdapter.addFragment(new HomeTimGiaSuFragment(), titleTab1);
        viewPagerAdapter.addFragment(new HomeTimHocVienFragment(), titleTab2);

        viewPager.setAdapter(viewPagerAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().supportInvalidateOptionsMenu();
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.menu);
    }
}
