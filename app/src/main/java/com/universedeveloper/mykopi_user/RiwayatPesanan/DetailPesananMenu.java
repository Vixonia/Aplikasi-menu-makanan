package com.universedeveloper.mykopi_user.RiwayatPesanan;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.universedeveloper.mykopi_user.Adapter.AdapterRincianMenu;
import com.universedeveloper.mykopi_user.Api.JSONResponse;
import com.universedeveloper.mykopi_user.Api.ModelTransaksiMenu;
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

public class DetailPesananMenu extends AppCompatActivity {
    TextView txt_id_transaksi,txt_waktu_pesan,txt_id_user,txt_nama_user,
            txt_telepon_user,txt_tgl_pesan,txt_jam_pesan,txt_catatan,txt_grand_total;

    String id_transaksi,waktu_pesan,id_user,nama_user,
            telepon_user,tgl_pesan,jam_pesan,catatan,grand_total;

    RecyclerView recycler_transaksi_menu;
    AdapterRincianMenu adapterRincianMenu;
    private ArrayList<ModelTransaksiMenu> mArrayList;
    public static final String BASE_URL = "http://universedeveloper.com/gudangandroid/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_pesanan_menu);
        recycler_transaksi_menu = findViewById(R.id.recycler_transaksi_menu);

        id_transaksi = getIntent().getStringExtra("id_transaksi");
        waktu_pesan = getIntent().getStringExtra("waktu_pesan");
        id_user = getIntent().getStringExtra("id_user");
        nama_user = getIntent().getStringExtra("nama_user");
        telepon_user = getIntent().getStringExtra("telepon_user");
        tgl_pesan = getIntent().getStringExtra("tgl_pesanan");
        jam_pesan = getIntent().getStringExtra("jam_pesanan");
        catatan = getIntent().getStringExtra("catatan");
        grand_total = getIntent().getStringExtra("grand_total");

        txt_id_transaksi = findViewById(R.id.txt_id_transaksi);
        txt_nama_user = findViewById(R.id.txt_nama_akun);
        txt_telepon_user = findViewById(R.id.txt_telepon);
        txt_grand_total = findViewById(R.id.txt_grand_total);
        txt_tgl_pesan = findViewById(R.id.txt_tanggal_pemesanan);
        txt_jam_pesan = findViewById(R.id.txt_jam_pemesanan);
        txt_waktu_pesan = findViewById(R.id.txt_waktu_pesan);
        txt_catatan = findViewById(R.id.txt_catatan);

        txt_id_transaksi.setText(id_transaksi);
        txt_nama_user.setText(nama_user);
        txt_telepon_user.setText(telepon_user);
        txt_grand_total.setText(grand_total);
        txt_tgl_pesan.setText(tgl_pesan);
        txt_jam_pesan.setText(jam_pesan);
        txt_waktu_pesan.setText(waktu_pesan);
        txt_catatan.setText(catatan);

        initViews();
        loadJSON();
    }
    private void initViews(){
        recycler_transaksi_menu = findViewById(R.id.recycler_transaksi_menu);
        recycler_transaksi_menu.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recycler_transaksi_menu.setLayoutManager(layoutManager);
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
        Call<JSONResponse> call = request.getMenu_transaksi(id_transaksi);
        call.enqueue(new Callback<JSONResponse>() {
            @Override
            public void onResponse(Call<JSONResponse> call, Response<JSONResponse> response) {
                JSONResponse jsonResponse = response.body();
                mArrayList = new ArrayList<>(Arrays.asList(jsonResponse.getMenu_transaksi()));
                adapterRincianMenu = new AdapterRincianMenu(DetailPesananMenu.this, mArrayList);
                recycler_transaksi_menu.setAdapter(adapterRincianMenu);
            }

            @Override
            public void onFailure(Call<JSONResponse> call, Throwable t) {
                Log.d("Error",t.getMessage());
                Toast.makeText(DetailPesananMenu.this, "Pesanan menu kosong", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
