package com.akistd.moneybucket.ui.transaction;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.akistd.moneybucket.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChiTieuFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChiTieuFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    Button btnSave, btnAllJam, btnDatePicker;
    ImageButton imgBtnOut;
    EditText editTotalMoney, editDescribe;

    public ChiTieuFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ChiTieuFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ChiTieuFragment newInstance(String param1, String param2) {
        ChiTieuFragment fragment = new ChiTieuFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chi_tieu, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        /*btnSave = view.findViewById(R.id.btnSave);
        imgBtnOut = view.findViewById(R.id.imgBtnOut);
        btnAllJam = view.findViewById(R.id.btnAllJam);
        btnDatePicker = view.findViewById(R.id.btnDatePicker);
        editTotalMoney = view.findViewById(R.id.editTotalMoney);
        editDescribe = view.findViewById(R.id.editDescribe);

        addEvent();*/
    }

    private void addEvent() {
        btnAllJam.setOnClickListener(v-> {
            FragmentTransaction fr = getFragmentManager().beginTransaction();
            fr.add(R.id.FLAllJam, new ChiTieuChild_AllJamFragment());
            fr.commit();
        });
    }
}