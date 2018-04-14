package com.eways.elearning.View.Fragment.TaiKhoan.ThongTinTaiKhoan.TaiLieuChuyenMon;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.eways.elearning.Model.DataModel.LinhVuc.LinhVuc;
import com.eways.elearning.Model.DataModel.LinhVuc.Mon;
import com.eways.elearning.Model.DataModel.TaiKhoan.TaiLieu.TaiLieuChuyenMon.BangCapTaiLieuChuyenMon;
import com.eways.elearning.Model.DataModel.TaiKhoan.TaiLieu.TaiLieuChuyenMon.MonTaiLieuChuyenMon;
import com.eways.elearning.Model.DataModel.TaiKhoan.TaiLieu.TaiLieuChuyenMon.TaiLieuChuyenMon;
import com.eways.elearning.Handler.FragmentHandler;
import com.eways.elearning.Model.Database.SharedPreferencesHandler;
import com.eways.elearning.Presenter.TaiKhoan.ThemTaiLieuChuyenMon.ThemTaiLieuChuyenMonPresenter;
import com.eways.elearning.Presenter.TaiKhoan.ThemTaiLieuChuyenMon.ThemTaiLieuChuyenMonPresenterImp;
import com.eways.elearning.R;
import com.eways.elearning.Util.SupportKeysList;
import com.eways.elearning.View.Dialog.LoadingDialog;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ThemLinhVucChuyenMonFragment extends Fragment implements View.OnClickListener ,ThemLinhVucChuyenMonViewImp{
    Spinner spinner;
    RecyclerView rvBangCap, rvMon;

    private FragmentHandler fragmentHandler;
    private TaiLieuChuyenMon taiLieuChuyenMon;
    private ArrayList<BangCapTaiLieuChuyenMon> danhSachBangCap = new ArrayList<>();
    private ArrayList<MonTaiLieuChuyenMon> danhSachMon = new ArrayList<>();
    private ThemTaiLieuChuyenMonPresenterImp themTaiLieuChuyenMonPresenterImp;
    private SharedPreferencesHandler sharedPreferencesHandler;

    //Phân biệt request tạo hay cập nhật lĩnh vực.
    private String REQUEST;
    private final String CREATE = "CREATE";
    private final String UPDATE = "UPDATE";

    //Key data
    private static final String param1 = "tai_lieu_chuyen_mon";

    public ThemLinhVucChuyenMonFragment() {
        // Required empty public constructor
    }

    public static ThemLinhVucChuyenMonFragment newInstance(TaiLieuChuyenMon taiLieuChuyenMon) {

        Bundle args = new Bundle();
        if (taiLieuChuyenMon!=null)
            args.putSerializable(param1, (Serializable) taiLieuChuyenMon);
        ThemLinhVucChuyenMonFragment fragment = new ThemLinhVucChuyenMonFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        themTaiLieuChuyenMonPresenterImp=new ThemTaiLieuChuyenMonPresenter(this);
        if (getArguments() != null) {
            REQUEST = UPDATE;
            sharedPreferencesHandler=new SharedPreferencesHandler(getActivity(), SupportKeysList.SHARED_PREF_FILE_NAME);
            taiLieuChuyenMon = (TaiLieuChuyenMon) getArguments().getSerializable(param1);
        }
        else
            REQUEST = CREATE;
        fragmentHandler = new FragmentHandler(getContext(), getActivity().getSupportFragmentManager());
        getActivity().invalidateOptionsMenu();
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_them_linh_vuc_chuyen_mon, container, false);
        spinner = root.findViewById(R.id.spinner_ChonLinhVucChuyenMon);
        rvBangCap = root.findViewById(R.id.recyclerView_DanhSachBangCap_ThemLinhVucChuyenMon);
        rvMon = root.findViewById(R.id.recyclerView_DanhSachMon_ThemLinhVucChuyenMon);
        themTaiLieuChuyenMonPresenterImp.LoadLinhVucTaiLieuChuyenMon(getActivity());
        root.findViewById(R.id.button_ThemBangCapLinhVucChuyenMon).setOnClickListener(this);
        root.findViewById(R.id.button_ThemMonLinhVucChuyenMon).setOnClickListener(this);

        setUpData();
        return root;
    }

    private void setUpData() {
//        spinner.setAdapter();

        rvBangCap.setLayoutManager(new GridLayoutManager(getContext(), 3));
        rvMon.setLayoutManager(new GridLayoutManager(getContext(), 3));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.button_ThemBangCapLinhVucChuyenMon:
                Toast.makeText(getContext(), "Comming soon...", Toast.LENGTH_SHORT);
                break;
            case R.id.button_ThemMonLinhVucChuyenMon:
                Toast.makeText(getContext(), "Comming soon...", Toast.LENGTH_SHORT);
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.act_save) {
            LoadingDialog.showDialog();
            //Xử lý lưu lĩnh vưc lên server
            //Chưa xử lý id tài liệu chuyên môn (chưa có id)
            //Chưa có danh sách bằng cấp
            //Chỉ có tên lĩnh vực chưa có id lĩnh vực
            taiLieuChuyenMon = new TaiLieuChuyenMon(null, spinner.getSelectedItem().toString(), danhSachBangCap, danhSachMon);
            themTaiLieuChuyenMonPresenterImp.NhanDataCapNhatTaiLieuChuyenMon(taiLieuChuyenMon,sharedPreferencesHandler.getUID(),getActivity());

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void DataLinhVuc(ArrayList<LinhVuc> listLinhVuc) {
        ArrayList<String> danhSachTenLinhVucChuyenMon=new ArrayList<>();
        for (int i=0;i<listLinhVuc.size();i++){
            danhSachTenLinhVucChuyenMon.add(listLinhVuc.get(i).getTenLinhVuc());
        }
        ArrayList<Mon> danhSachMonLinhVucChuyenMon=new ArrayList<>();

        ArrayAdapter<String> linhVucArrayAdapter=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_dropdown_item,danhSachTenLinhVucChuyenMon);
        spinner.setAdapter(linhVucArrayAdapter);
    }

    @Override
    public void KetQuaThemTaiLieuChuyenMon(String ketQuaCapNhat) {
        if (ketQuaCapNhat.compareTo("CapNhatTaiLieuChuyenMonThanhCong")==0){
            fragmentHandler.XoaFragment();
        }
    }
}
