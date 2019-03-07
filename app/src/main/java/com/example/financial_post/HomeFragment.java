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

public class HomeFragment extends Fragment{
    private Button b1;
    private Button b2;
    private Button b3;
    private Button b4;
    private Button b5;
    private Button b6;
    public static HomeFragment newInstance(){
        HomeFragment newFragment = new HomeFragment();
        return  newFragment;
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_frag, container, false);
        return view;
    }
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
        b1 = getView().findViewById(R.id.btn_fin);
        b2 = getView().findViewById(R.id.btn_stock);
        b3 = getView().findViewById(R.id.btn_bank);
        b4 = getView().findViewById(R.id.btn_bond);
        b5 = getView().findViewById(R.id.btn_inv);
        b6 = getView().findViewById(R.id.btn_poli);


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent fin = new Intent(getActivity(), EconomicNews.class);
                startActivity(fin);
                onDestroy();
                getActivity().finish();
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent stock = new Intent(getActivity(), StockNews.class);
                startActivity(stock);
                onDestroy();
                getActivity().finish();
            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent bank = new Intent(getActivity(), BankNews.class);
                startActivity(bank);
                onDestroy();
                getActivity().finish();
            }
        });
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent bond = new Intent(getActivity(), BondNews.class);
                startActivity(bond);
                onDestroy();
                getActivity().finish();
            }
        });
        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent inv = new Intent(getActivity(), InvestNews.class);
                startActivity(inv);
                onDestroy();
                getActivity().finish();
            }
        });
        b6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent poli = new Intent(getActivity(), PolicyNews.class);
                startActivity(poli);
                onDestroy();
                getActivity().finish();
            }
        });

    }
}
