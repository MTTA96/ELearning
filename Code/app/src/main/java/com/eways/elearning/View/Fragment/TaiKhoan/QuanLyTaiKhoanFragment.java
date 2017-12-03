package com.eways.elearning.View.Fragment.TaiKhoan;


import android.accounts.AccountManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.eways.elearning.DataModel.BaiDang.LinhVucBaiDang;
import com.eways.elearning.Handler.Adapter.LinhVucQuanTam.LinhVucDialog;
import com.eways.elearning.Handler.Adapter.LinhVucQuanTam.LinhVucQuanTamAdapter;
import com.eways.elearning.Handler.ImageHandler;
import com.eways.elearning.Model.Database.SharedPreferencesHandler;
import com.eways.elearning.Handler.FragmentHandler;
import com.eways.elearning.R;
import com.eways.elearning.Util.SupportKeysList;
import com.eways.elearning.View.Fragment.Home.HomeFragment;
import com.eways.elearning.View.Fragment.Home.NewHomeFragment;
import com.eways.elearning.View.Fragment.TaiKhoan.ThongTinTaiKhoan.ThongTinCaNhanFragment;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class QuanLyTaiKhoanFragment extends Fragment implements View.OnClickListener {
    TextView tvTenUser, tvUserEmail;
    ImageView imgUser;

    private LinhVucQuanTamAdapter linhVucQuanTamAdapter;

    LinearLayout loLinhVucQuanTam,loTaiKhoanKhac,loCapNhatThongCN;
    private FragmentHandler fragmentHandler;
    private SharedPreferencesHandler sharedPreferencesHandler;
    private ImageHandler imageHandler;
    private AccountManager accountManager;

    public QuanLyTaiKhoanFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferencesHandler = new SharedPreferencesHandler(getContext(), SupportKeysList.SHARED_PREF_FILE_NAME);
        fragmentHandler = new FragmentHandler(getContext(), getActivity().getSupportFragmentManager());
        imageHandler = new ImageHandler(getActivity());
        accountManager = AccountManager.get(getActivity());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root=inflater.inflate(R.layout.fragment_quan_ly_tai_khoan, container, false);
        loLinhVucQuanTam= (LinearLayout) root.findViewById(R.id.LoLinhVucQuanTam);
        loTaiKhoanKhac= (LinearLayout) root.findViewById(R.id.LoTaiKhoanKhac);
        loCapNhatThongCN=(LinearLayout) root.findViewById(R.id.LoThongTinCaNhan);
		tvTenUser = (TextView) root.findViewById(R.id.tvName_QLTK);
        tvUserEmail = (TextView) root.findViewById(R.id.tvEmail_QLTK);
        imgUser = (ImageView) root.findViewById(R.id.ivAvatar_QLTK);

        loTaiKhoanKhac.setOnClickListener(this);
        loLinhVucQuanTam.setOnClickListener(this);
        loCapNhatThongCN.setOnClickListener(this);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setElevation(0);
        if (sharedPreferencesHandler.getAvatar() != null && sharedPreferencesHandler.getAvatar().compareTo("") != 0)
            imageHandler.loadImageRound(sharedPreferencesHandler.getAvatar(), imgUser);
        tvUserEmail.setText(sharedPreferencesHandler.getEmail());
        if (sharedPreferencesHandler.getTen().length()==0)
            tvTenUser.setVisibility(View.GONE);
        else
            tvTenUser.setText(sharedPreferencesHandler.getHo() + " " + sharedPreferencesHandler.getTen());
        return root;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.LoLinhVucQuanTam) {
            ArrayList<String> listAmThucCT=new ArrayList();
            listAmThucCT.add("At2");
            listAmThucCT.add("At3");
            ArrayList<String> listHocTapCT=new ArrayList<>();
            listHocTapCT.add("HT1");
            listHocTapCT.add("HT2");
            ArrayList<String> listAmNhacCT=new ArrayList<>();
            listAmNhacCT.add("AN1");
            listAmNhacCT.add("AN2");
            ArrayList<String> listVanTai=new ArrayList<>();
            listVanTai.add("VT1");
            listVanTai.add("VT2");
            ArrayList<LinhVucBaiDang> DanhSachLVBD=new ArrayList<>();
            DanhSachLVBD.add(new LinhVucBaiDang(1,"Ẩm Thực",R.drawable.at,listAmThucCT));
            DanhSachLVBD.add(new LinhVucBaiDang(2,"Học Tập",R.drawable.ht,listHocTapCT));
            DanhSachLVBD.add(new LinhVucBaiDang(3,"Âm Nhạc",R.drawable.an,listAmNhacCT));
            DanhSachLVBD.add(new LinhVucBaiDang(4,"Vận Tải",R.drawable.vt,listVanTai));



            LinhVucDialog linhVucDialog=new LinhVucDialog(getContext(),DanhSachLVBD);
            linhVucDialog.ShowDialogLinhVuc();
        }
        if (v.getId() == R.id.LoTaiKhoanKhac) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setMessage(R.string.DiaLogDangXuat);
            builder.setNegativeButton(R.string.BtnDongY_DX, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    FirebaseAuth.getInstance().signOut();
                    fragmentHandler.ChuyenFragment(new NewHomeFragment(), false, null);
                    sharedPreferencesHandler.DangXuat();
                }
            });
            builder.setPositiveButton(R.string.BtnHuy_DX, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            Dialog dialog = new Dialog(getContext());
            dialog = builder.create();
            dialog.show();
        }
        if (v.getId()==R.id.LoThongTinCaNhan){
            fragmentHandler.ChuyenFragment(new ThongTinCaNhanFragment(),true, SupportKeysList.TAG_THONG_TIN_CA_NHAN);
        }
    }
}
