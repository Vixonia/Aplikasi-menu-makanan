package com.universedeveloper.mykopi_user.RiwayatPesanan;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.universedeveloper.mykopi_user.Adapter.AdapterRiwayatPesanTempat;
import com.universedeveloper.mykopi_user.Api.JSONResponse;
import com.universedeveloper.mykopi_user.Api.ModelTransaksiTempat;
import com.universedeveloper.mykopi_user.Api.RequestInterface;
import com.universedeveloper.mykopi_user.LoginRegistrasi.LoginUser;
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

public class RiwayatPesananTempat extends AppCompatActivity {
    String id_user;
    RecyclerView recycler_riwayat_tempat;
    AdapterRiwayatPesanTempat adapterRiwayatPesanTempat;
    SharedPreferences sharedpreferences;
    private ArrayList<ModelTransaksiTempat> mArrayList;

    public final static String TAG_ID_USER = "id_user";
    public final static String TAG_ID_EMAIL = "email_user";
    public static final String BASE_URL = "http://universedeveloper.com/gudangandroid/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_riwayat_pesanan_tempat);
        recycler_riwayat_tempat = findViewById(R.id.recycler_riwayat_tempat);

        sharedpreferences = this.getSharedPreferences(LoginUser.my_shared_preferences, Context.MODE_PRIVATE);
        id_user = sharedpreferences.getString("id_user", "0");

        initViews();
        loadJSON();
    }
    private void initViews(){
        recycler_riwayat_tempat = findViewById(R.id.recycler_riwayat_tempat);
        recycler_riwayat_tempat.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recycler_riwayat_tempat.setLayoutManager(layoutManager);
    }
    private void loadJSON(){
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
        Call<JSONResponse> call = request.getPesantempat(id_user);
        call.enqueue(new Callback<JSONResponse>() {
            @Override
            public void onResponse(Call<JSONResponse> call, Response<JSONResponse> response) {
                JSONResponse jsonResponse = response.body();
                mArrayList = new ArrayList<>(Arrays.asList(jsonResponse.getPesantempat()));
                adapterRiwayatPesanTempat = new AdapterRiwayatPesanTempat(mArrayList);
                recycler_riwayat_tempat.setAdapter(adapterRiwayatPesanTempat);
            }

            @Override
            public void onFailure(Call<JSONResponse> call, Throwable t) {
                Log.d("Error",t.getMessage());
            }
        });
    }
}