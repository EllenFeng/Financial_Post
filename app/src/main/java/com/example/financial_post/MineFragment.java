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

public class MineFragment extends Fragment{
    private Button b1;
    private Button b2;
    private Button b3;
    public static MineFragment newInstance(){
        MineFragment newFragment = new MineFragment();
        return  newFragment;
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.mine_frag, container, false);
        return view;
    }
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
        b1 = getView().findViewById(R.id.btn_fav);
        b2 = getView().findViewById(R.id.btn_follow);
        b3 = getView().findViewById(R.id.btn_note);


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mark = new Intent(getActivity(), MyMark.class);
                startActivity(mark);
                onDestroy();
                getActivity().finish();
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent  follow= new Intent(getActivity(), MyFollow.class);
                startActivity(follow);
                onDestroy();
                getActivity().finish();
            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent note = new Intent(getActivity(), MyNote.class);
                startActivity(note);
                onDestroy();
                getActivity().finish();
            }
        });

    }
}
