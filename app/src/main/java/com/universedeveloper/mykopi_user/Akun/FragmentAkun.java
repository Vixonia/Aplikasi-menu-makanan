package com.universedeveloper.mykopi_user.Akun;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.universedeveloper.mykopi_user.Api.JSONResponse;
import com.universedeveloper.mykopi_user.Api.ModelProfilUser;
import com.universedeveloper.mykopi_user.Api.RequestInterface;
import com.universedeveloper.mykopi_user.LoginRegistrasi.LoginUser;
import com.universedeveloper.mykopi_user.R;
import com.universedeveloper.mykopi_user.RiwayatPesanan.RiwayatPesananMenu;
import com.universedeveloper.mykopi_user.RiwayatPesanan.RiwayatPesananTempat;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentAkun extends Fragment {
    String id_user;
    SharedPreferences sharedpreferences;
    private ArrayList<ModelProfilUser> mArrayListUser;

    public final static String TAG_ID_USER = "id_user";
    public final static String TAG_ID_EMAIL = "email_user";

    TextView txt_nama_akun, txt_telepon, txt_email;
    Button btn_logout;
    CardView card_riwayat_menu,card_riwayat_tempat;


    public static final String BASE_URL = "http://universedeveloper.com/gudangandroid/";


    public FragmentAkun() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_fragment_akun2, container, false);
        txt_nama_akun = view.findViewById(R.id.txt_nama_akun);
        txt_telepon = view.findViewById(R.id.txt_telepon);
        txt_email = view.findViewById(R.id.txt_email);

        card_riwayat_menu = view.findViewById(R.id.card_riwayat_menu);
        card_riwayat_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (getActivity(), RiwayatPesananMenu.class);
                startActivity(intent);
            }
        });

        card_riwayat_tempat = view.findViewById(R.id.card_riwayat_tempat);
        card_riwayat_tempat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (getActivity(), RiwayatPesananTempat.class);
                startActivity(intent);
            }
        });

        sharedpreferences = getActivity().getSharedPreferences(LoginUser.my_shared_preferences, Context.MODE_PRIVATE);
        id_user = sharedpreferences.getString("id_user", "0");

        ambilProfilUser();

        btn_logout = view.findViewById(R.id.btn_logout);
        btn_logout.setOnClickListener(new View.OnClickListener() {

              @Override
            public void onClick(View view) {
                 AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                sharedpreferences = getActivity().getSharedPreferences(LoginUser.my_shared_preferences, Context.MODE_PRIVATE);
                   SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putBoolean(LoginUser.session_status, false);
                    editor.putString(TAG_ID_USER, null);
                    editor.putString(TAG_ID_EMAIL, null);
                    editor.commit();
                    editor.apply();

                    Intent intent = new Intent(getActivity(), LoginUser.class);
                      startActivity(intent);


                  }


        });

        return view;
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

                txt_nama_akun.setText(nama_user);
                txt_telepon.setText(telepon_user);
                txt_email.setText(email_user);

            }

            @Override
            public void onFailure(Call<JSONResponse> call, Throwable t) {
                Log.d("Error",t.getMessage());
            }
        });
    }
}
