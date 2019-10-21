package com.universedeveloper.mykopi_user.RiwayatPesanan;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.universedeveloper.mykopi_user.Api.JSONResponse;
import com.universedeveloper.mykopi_user.Api.ModelProfilUser;
import com.universedeveloper.mykopi_user.Api.RequestInterface;
import com.universedeveloper.mykopi_user.R;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetailPesananTempat extends AppCompatActivity {

    SharedPreferences sharedpreferences;
    private ArrayList<ModelProfilUser> mArrayListUser;

    public final static String TAG_ID_USER = "id_user";
    public final static String TAG_ID_EMAIL = "email_user";

    public static final String BASE_URL = "http://universedeveloper.com/gudangandroid/";
    TextView txt_id_user,txt_nama_user,txt_telepon_user,txt_email,txt_nomor_meja,txt_tanggal_pemesanan,
            txt_jam_pemesanan;

    String id_user,nama_user,telepon_user,email_user,nomor_meja,tanggal_pemesanan,jam_pemesanan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_pesanan_tempat);



        tanggal_pemesanan = getIntent().getStringExtra("tanggal_pesan");
        jam_pemesanan = getIntent().getStringExtra("jam_pesan");
        id_user = getIntent().getStringExtra("id_user");
        nama_user = getIntent().getStringExtra("nama_user");
       ///// Toast.makeText(this, "Ini id User "+ id_user, Toast.LENGTH_SHORT).show();
        telepon_user = getIntent().getStringExtra("telepon_user");
        nomor_meja = getIntent().getStringExtra("nomor_meja");
        email_user = getIntent().getStringExtra("email_user");

        txt_nama_user = findViewById(R.id.txt_nama_user);
        txt_telepon_user = findViewById(R.id.txt_telepon_user);
        txt_email = findViewById(R.id.txt_email_user);
        txt_nomor_meja = findViewById(R.id.txt_nomor_meja);
        txt_tanggal_pemesanan = findViewById(R.id.txt_tanggal_pesan_tempat);
        txt_jam_pemesanan = findViewById(R.id.txt_jam_pesan_tempat);

        txt_nama_user.setText(nama_user);
        txt_telepon_user.setText(telepon_user);
        txt_email.setText(email_user);
        txt_nomor_meja.setText(nomor_meja);
        txt_tanggal_pemesanan.setText(tanggal_pemesanan);
        txt_jam_pemesanan.setText(jam_pemesanan);
        ambilProfilUser();
    }

    public void ambilProfilUser(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RequestInterface request = retrofit.create(RequestInterface.class);
        Call<JSONResponse> call = request.getProfilUser(id_user);
        call.enqueue(new Callback<JSONResponse>() {
            @Override
            public void onResponse(Call<JSONResponse> call, Response<JSONResponse> response) {
                JSONResponse jsonResponse = response.body();

                mArrayListUser = new ArrayList<>(Arrays.asList(jsonResponse.getDatauser()));
                String nama_user = mArrayListUser.get(0).getNama_user();
                String telepon_user = mArrayListUser.get(0).getTelepon_user();
                String email_user = mArrayListUser.get(0).getEmail_user();

                txt_nama_user.setText(nama_user);
                txt_telepon_user.setText(telepon_user);
                txt_email.setText(email_user);

            }

            @Override
            public void onFailure(Call<JSONResponse> call, Throwable t) {
                Log.d("Error",t.getMessage());
            }
        });
    }
}
