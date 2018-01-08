package com.eways.elearning.View.Fragment.DieuKhoan;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.eways.elearning.Handler.FragmentHandler;
import com.eways.elearning.Model.Database.SharedPreferencesHandler;
import com.eways.elearning.Presenter.TaiKhoan.CapNhatTaiKhoan.CapNhatTaiKhoanPresenter;
import com.eways.elearning.Presenter.TaiKhoan.CapNhatTaiKhoan.CapNhatTaiKhoanPresenterImp;
import com.eways.elearning.R;
import com.eways.elearning.Util.SupportKeysList;
import com.eways.elearning.View.Fragment.TaiKhoan.ThongTinTaiKhoan.CapNhatTaiKhoan.CapNhatThongTinTaiKhoanFragment;
import com.eways.elearning.View.Fragment.TaiKhoan.ThongTinTaiKhoan.TaiLieuChuyenMon.CapNhatTaiLieuChuyenMonFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class DieuKhoanGiaSuFragment extends Fragment implements View.OnClickListener,DieuKhoanGiaSuViewImp {
    TextView tvDieuKhoanGiaSu;

    private SharedPreferencesHandler sharedPreferencesHandler;
    private FragmentHandler fragmentHandler;
    CapNhatTaiKhoanPresenterImp capNhatTaiKhoanPresenterImp;
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
        capNhatTaiKhoanPresenterImp=new CapNhatTaiKhoanPresenter(this);
        getActivity().invalidateOptionsMenu();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_dieu_khoan_gia_su, container, false);
        tvDieuKhoanGiaSu = root.findViewById(R.id.textView_DieuKhoanGiaSu);
        root.findViewById(R.id.button_DongYDieuKhoan_DieuKhoanGiaSu).setOnClickListener(this);
        return root;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.button_DongYDieuKhoan_DieuKhoanGiaSu){
            capNhatTaiKhoanPresenterImp.NhanDataCapNhatTaiKhoanGiaSu(sharedPreferencesHandler.getID(),getActivity());
            //Xử lý cập nhật tài khoản
            //Xử lý sharedpref
            //Xử lý server
        }
    }

    @Override
    public void NhanKetQuaCapNhatTaiKhoanGiaSu(String ketQuaCapNhat) {
        if (ketQuaCapNhat.compareTo("CapNhatTaiKhoanGiaSuThanhCong")==0){
            if (sharedPreferencesHandler.getDaCapNhat())
                fragmentHandler.ChuyenFragment(CapNhatTaiLieuChuyenMonFragment.newInstance(CapNhatTaiLieuChuyenMonFragment.TYPE_EDIT), false, SupportKeysList.TAG_CAP_NHAT_TAI_LIEU_CHUYEN_MON);
            else {
                Toast.makeText(getActivity(), getResources().getString(R.string.msg_cap_nhat_thong_tin), Toast.LENGTH_SHORT).show();
                fragmentHandler.ChuyenFragment(new CapNhatThongTinTaiKhoanFragment(), false, SupportKeysList.TAG_CAP_NHAT_THONG_TIN_CA_NHAN);
            }
        }
        Toast.makeText(getActivity(),"Cập nhật thành công",Toast.LENGTH_SHORT).show();
    }
}
