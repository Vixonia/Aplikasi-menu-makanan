package com.universedeveloper.mykopi_user.Beranda;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


import com.universedeveloper.mykopi_user.PesanCemilan.PesanCemilan;
import com.universedeveloper.mykopi_user.PesanMakanan.PesanMakanan;
import com.universedeveloper.mykopi_user.PesanMinum.PesanMinum;
import com.universedeveloper.mykopi_user.R;

public class FragmentBeranda extends Fragment {

    CardView btn_makanan, btn_minuman, btn_cemilan;

    public FragmentBeranda() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragment_beranda, container, false);

        btn_makanan = view.findViewById(R.id.btn_makanan);
        btn_makanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FragmentTransaction ft = getFragmentManager().beginTransaction().replace(R.id.frame_container, new PesanMakanan());
                ft.commit();
            }

        });

        btn_minuman = view.findViewById(R.id.btn_minuman);
        btn_minuman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FragmentTransaction ft = getFragmentManager().beginTransaction().replace(R.id.frame_container, new PesanMinum());
                ft.commit();
            }

        });

        btn_cemilan = view.findViewById(R.id.btn_cemilan);
        btn_cemilan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FragmentTransaction ft = getFragmentManager().beginTransaction().replace(R.id.frame_container, new PesanCemilan());
                ft.commit();
            }

        });

        return view;
    }
}
