package com.eways.elearning.View.Fragment.Home;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.eways.elearning.Model.Database.SharedPreferencesHandler;
import com.eways.elearning.Model.FragmentHandler;
import com.eways.elearning.R;
import com.eways.elearning.Util.SupportKeysList;
import com.eways.elearning.View.Fragment.TaiKhoan.DangNhap.DangNhapFragment;

import org.w3c.dom.Text;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements View.OnClickListener {
    private FragmentHandler fragmentHandler;
    private TextView tenHome,gioiThieuHome;
    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fragmentHandler = new FragmentHandler(getActivity(), getActivity().getSupportFragmentManager());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        root.findViewById(R.id.avatar_home).setOnClickListener(this);
        tenHome= (TextView) root.findViewById(R.id.tvTen_Home);
        gioiThieuHome= (TextView) root.findViewById(R.id.tvGioiThieu_Home);
        return root;
    }

    @Override
    public void onResume() {
        ((AppCompatActivity)getActivity()).getSupportActionBar().show();
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.avatar_home:
                fragmentHandler.ChuyenFragment(new DangNhapFragment(), true, SupportKeysList.TAG_DANG_NHAP_FRAGMENT);
                break;
        }
    }
}
