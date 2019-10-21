package com.universedeveloper.mykopi_user.PesanMakanan;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.universedeveloper.mykopi_user.Adapter.AdapterPesanMakanan;
import com.universedeveloper.mykopi_user.Api.JSONResponse;
import com.universedeveloper.mykopi_user.Api.ModelMenu;
import com.universedeveloper.mykopi_user.Api.RequestInterface;
import com.universedeveloper.mykopi_user.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PesanMakanan extends Fragment {
    FloatingActionButton fab;
    RecyclerView listmakanan;
    SwipeRefreshLayout swipeRefresh;
    ProgressBar progressBar;
    ConnectivityManager conMgr;
    public static final String BASE_URL = "http://universedeveloper.com/gudangandroid/";
    private ArrayList<ModelMenu> mArrayList;
    AdapterPesanMakanan adapterPesanMakanan;
    private RequestInterface requestInterface;
    String kategori = "makanan";

    public PesanMakanan() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pesan_makanan, container, false);
        listmakanan = view.findViewById(R.id.listmakanan);
        swipeRefresh = view.findViewById(R.id.swipeRefresh);
        progressBar = view.findViewById(R.id.progressBar);

        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initViews();
                loadJSON();
            }
        });
        swipeRefresh.setColorScheme(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        initViews();
        loadJSON();

        return view;
    }

    private void initViews() {
        listmakanan.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        listmakanan.setLayoutManager(layoutManager);
    }

    private void loadJSON() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(new OkHttpClient().newBuilder()
                        .connectTimeout(30, TimeUnit.SECONDS)
                        .readTimeout(30, TimeUnit.SECONDS)
                        .writeTimeout(30, TimeUnit.SECONDS)
                        .build())
                .build();
        RequestInterface request = retrofit.create(RequestInterface.class);
        Call<JSONResponse> call = request.getMenu(kategori);
        call.enqueue(new Callback<JSONResponse>() {
            @Override
            public void onResponse(Call<JSONResponse> call, Response<JSONResponse> response) {
                JSONResponse jsonResponse = response.body();
                progressBar.setVisibility(android.view.View.INVISIBLE);
                swipeRefresh.setRefreshing(false);
                mArrayList = new ArrayList<>(Arrays.asList(jsonResponse.getMenu()));
                adapterPesanMakanan = new AdapterPesanMakanan(mArrayList);
                listmakanan.setAdapter(adapterPesanMakanan);
            }

            @Override
            public void onFailure(Call<JSONResponse> call, Throwable t) {
                Log.d("Error", t.getMessage());
            }
        });
    }
}
