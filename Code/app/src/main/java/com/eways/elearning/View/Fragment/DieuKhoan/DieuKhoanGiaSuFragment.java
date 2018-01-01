package com.eways.elearning.View.Fragment.DieuKhoan;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import com.eways.elearning.Handler.FragmentHandler;
import com.eways.elearning.Model.Database.SharedPreferencesHandler;
import com.eways.elearning.R;
import com.eways.elearning.Util.ServerUrl;
import com.eways.elearning.Util.SupportKeysList;
import com.eways.elearning.View.Fragment.KhoaHoc.TaoKhoaHoc.TaoKhoaHocFragment;
import com.eways.elearning.View.Fragment.TaiKhoan.ThongTinTaiKhoan.CapNhatTaiKhoan.CapNhatThongTinTaiKhoanFragment;
import com.google.zxing.pdf417.PDF417Reader;

/**
 * A simple {@link Fragment} subclass.
 */
public class DieuKhoanGiaSuFragment extends Fragment implements View.OnClickListener {
    TextView tvDieuKhoanGiaSu;

    private SharedPreferencesHandler sharedPreferencesHandler;
    private FragmentHandler fragmentHandler;

    public DieuKhoanGiaSuFragment() {
        // Required empty public constructor
    }

    public static DieuKhoanGiaSuFragment newInstance() {
        
        Bundle args = new Bundle();
        
        DieuKhoanGiaSuFragment fragment = new DieuKhoanGiaSuFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentHandler = new FragmentHandler(getActivity(), getActivity().getSupportFragmentManager());
        sharedPreferencesHandler = new SharedPreferencesHandler(getActivity(), SupportKeysList.SHARED_PREF_FILE_NAME);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_dieu_khoan_gia_su, container, false);
        tvDieuKhoanGiaSu = root.findViewById(R.id.textView_DieuKhoanGiaSu);

        root.findViewById(R.id.button_DongYDieuKhoan_DieuKhoanGiaSu).setOnClickListener(this);
        getActivity().supportInvalidateOptionsMenu();
        return root;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.button_DongYDieuKhoan_DieuKhoanGiaSu){
            if (sharedPreferencesHandler.getDaCapNhat())
                fragmentHandler.ChuyenFragment(new TaoKhoaHocFragment(), true, SupportKeysList.TAG_TAO_KHOA_HOC);
            else {
                Toast.makeText(getActivity(), getResources().getString(R.string.msg_cap_nhat_thong_tin), Toast.LENGTH_SHORT).show();
                fragmentHandler.ChuyenFragment(new CapNhatThongTinTaiKhoanFragment(), true, SupportKeysList.TAG_THONG_TIN_CA_NHAN);
            }
        }
    }
}
