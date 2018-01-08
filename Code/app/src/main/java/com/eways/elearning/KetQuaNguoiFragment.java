package com.eways.elearning;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link KetQuaNguoiFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link KetQuaNguoiFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class KetQuaNguoiFragment extends Fragment {

    private RecyclerView rcvGiaSu;

    public KetQuaNguoiFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ket_qua_nguoi, container, false);
    }

}
