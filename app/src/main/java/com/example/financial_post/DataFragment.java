package com.example.financial_post;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class DataFragment extends Fragment{
    private Button b1;
    private Button b2;
    private Button b3;
    private Button b4;
    private Button b5;
    public static DataFragment newInstance(){
        DataFragment newFragment = new DataFragment();
        return  newFragment;
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.data_frag, container, false);
        return view;
    }
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
        b1 = getView().findViewById(R.id.btn_sear);
        b2 = getView().findViewById(R.id.btn_year);
        b3 = getView().findViewById(R.id.btn_dim);
        b4 = getView().findViewById(R.id.btn_place);
        b5 = getView().findViewById(R.id.btn_report);


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sear = new Intent(getActivity(), DataSearch.class);
                startActivity(sear);
                onDestroy();
                getActivity().finish();
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent year = new Intent(getActivity(), DataYear.class);
                startActivity(year);
                onDestroy();
                getActivity().finish();
            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent dim = new Intent(getActivity(), DataDimension.class);
                startActivity(dim);
                onDestroy();
                getActivity().finish();
            }
        });
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent place = new Intent(getActivity(), DataPlace.class);
                startActivity(place);
                onDestroy();
                getActivity().finish();
            }
        });
        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent rep = new Intent(getActivity(), DataReport.class);
                startActivity(rep);
                onDestroy();
                getActivity().finish();
            }
        });

    }
}

