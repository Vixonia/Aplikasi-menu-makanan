package com.universedeveloper.mykopi_user.Checkout;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.universedeveloper.mykopi_user.Adapter.AdapterCheckOut;
import com.universedeveloper.mykopi_user.Api.JSONResponse;
import com.universedeveloper.mykopi_user.Api.ModelMenu;
import com.universedeveloper.mykopi_user.Api.ModelProfilUser;
import com.universedeveloper.mykopi_user.Api.RequestInterface;
import com.universedeveloper.mykopi_user.Api.ValueMessage;
import com.universedeveloper.mykopi_user.DataHelper;
import com.universedeveloper.mykopi_user.LoginRegistrasi.LoginUser;
import com.universedeveloper.mykopi_user.MainActivity;
import com.universedeveloper.mykopi_user.R;
import com.universedeveloper.mykopi_user.Utility.JSONParser;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ActivityCheckOut extends AppCompatActivity {

    RecyclerView recycler_cart;
    DataHelper dataHelper;
    AdapterCheckOut adapterCheckOut;
    List<ModelMenu> modelMenu;

    SharedPreferences sharedpreferences;
    private ArrayList<ModelProfilUser> mArrayListUser;
    public final static String TAG_ID_USER = "id_user";
    public final static String TAG_ID_NAMA_USER = "nama_user";

    TextView txt_total;

    String String_id_user, String_id_transaksi;
    TextView txt_nama, txt_telepon;

    EditText txt_tanggal_pemesanan, txt_jam_pemesanan;
    String ubah_tanggal_untuk_db;
    //untuk booking tanggal
    int mDay,mMonth, mYear ;
    static final int DATE_DIALOG_ID = 1;
    private String[] arrMonth = {"01","02","03","04","05","06","07","08","09","10","11","12"};

    TextView txt_catatan;

    TextView txt_grand_total;

    Button btn_kirim_pesanan;

    ProgressDialog pDialog;
    public static final String BASE_URL = "http://universedeveloper.com/gudangandroid/";
    private static final String url_ambil_id_transaksi = "http://universedeveloper.com/gudangandroid/mykopi/user/cek_id_transaksi.php";
    JSONArray string_json = null;
    JSONParser jsonParser = new JSONParser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);

        //recyclerview cart
        modelMenu = new ArrayList<ModelMenu>();
        recycler_cart = findViewById(R.id.recycler_cart);

        dataHelper = new DataHelper(ActivityCheckOut.this);
        modelMenu = dataHelper.getData();
        adapterCheckOut = new AdapterCheckOut(modelMenu);

        recycler_cart.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager1 = new LinearLayoutManager(ActivityCheckOut.this, LinearLayoutManager.VERTICAL, false);
        recycler_cart.setLayoutManager(layoutManager1);
        recycler_cart.setAdapter(adapterCheckOut);
        recycler_cart.invalidate();

        //harga totalnya
        txt_total = findViewById(R.id.txt_total);
        UpdateTotalHarga();

        //info pemesan
        txt_nama = findViewById(R.id.txt_nama);
        txt_telepon = findViewById(R.id.txt_telepon);

        sharedpreferences = this.getSharedPreferences(LoginUser.my_shared_preferences, Context.MODE_PRIVATE);
        String_id_user = sharedpreferences.getString("id_user", "0");

        ambilProfilUser();

        //tanggal dan jam pemesanan
        txt_tanggal_pemesanan = findViewById(R.id.txt_tanggal_pemesanan);
        txt_jam_pemesanan = findViewById(R.id.txt_jam_pemesanan);

        //untuk tanggal
        txt_tanggal_pemesanan.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View arg0, MotionEvent arg1) {
                // TODO Auto-generated method stub
                showDialog(DATE_DIALOG_ID);
                return true;
            }
        });
        // get the current date
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        //untuk jam
        txt_jam_pemesanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(ActivityCheckOut.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        txt_jam_pemesanan.setText(selectedHour + ":" + String.format("%02d", selectedMinute));
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Pilih Jam");
                mTimePicker.show();
            }
        });


        //untuk catatan
        txt_catatan = findViewById(R.id.txt_catatan);

        txt_grand_total = findViewById(R.id.txt_grand_total);
        txt_grand_total.setText(txt_total.getText().toString());

        btn_kirim_pesanan = findViewById(R.id.btn_kirim_pesanan);
        btn_kirim_pesanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //pakai alert dialog YA ATAU TIDAK untuk keluar dari aplikasi
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(ActivityCheckOut.this);

                builder.setTitle("Buat Pesanan");
                builder.setMessage("Apakah anda yakin ingin membuat pesanan ?");

                builder.setPositiveButton("Pesan", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        new AmbilIdTransaksiTerakhir().execute();
                    }
                });

                builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        // Do nothing
                        dialog.dismiss();
                    }
                });

                android.app.AlertDialog alert = builder.create();
                alert.show();
            }
        });


    }

    public void UpdateTotalHarga(){
        Integer totalPrice = 0;
        modelMenu = dataHelper.getData();
        for (int i = 0; i<modelMenu.size(); i++)
        {
            totalPrice += (modelMenu.get(i).getTotal());

        }

        txt_total.setText(totalPrice.toString());


    }

    /////////////////////////////untuk tanggal
    protected Dialog onCreateDialog(int id)  //untuk menampilkan tgl drop up
    {
        switch (id) {
            case DATE_DIALOG_ID:
                return new DatePickerDialog(
                        this, mDateSetListener,  mYear, mMonth, mDay  );
        }
        return null;
    }
    private DatePickerDialog.OnDateSetListener mDateSetListener =
            new DatePickerDialog.OnDateSetListener()
            {
                @Override
                public void onDateSet(DatePicker view, int dayOfMonth , int monthOfYear, int year) {
                    mDay = dayOfMonth;
                    mMonth = monthOfYear;
                    mYear = year;

                    String sdate = mYear + "-" + arrMonth[mMonth] + "-" + LPad(mDay + "", "0", 2)   ;  //ini jika habis di klik, format DD-MM-YYYY
                    String sdate_ubah = LPad(mDay + "", "0", 2)  + "-" + arrMonth[mMonth] + "-" + mYear   ; //ini untuk di masukkan ke database, format YYYY-MM-DD

                    txt_tanggal_pemesanan.setText(sdate); //ini tampilan
                    ubah_tanggal_untuk_db = sdate_ubah;  //ini utk database
                }
            };

    private static String LPad(String schar, String spad, int len) {
        String sret = schar;
        for (int i = sret.length(); i < len; i++) {
            sret = spad + sret;
        }
        return new String(sret);
    }
    //untuk database tanggal hari ini
    private String getCurrentDate() {
        Date current = new Date();
        SimpleDateFormat frmt = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = frmt.format(current);
        return dateString;
    }
    //untuk jam hari ini
    private String getCurrentTime() {
        Date current = new Date();
        SimpleDateFormat frmt = new SimpleDateFormat("HH:mm:ss");
        String dateString = frmt.format(current);
        return dateString;
    }
    //untuk transaksi terakhir
    private String getDateId() {
        Date current = new Date();
        SimpleDateFormat frmt = new SimpleDateFormat("ddMMyy");
        String dateString = frmt.format(current);
        return dateString;
    }

    public void ambilProfilUser(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RequestInterface request = retrofit.create(RequestInterface.class);
        Call<JSONResponse> call = request.getProfilUser(String_id_user);
        call.enqueue(new Callback<JSONResponse>() {
            @Override
            public void onResponse(Call<JSONResponse> call, Response<JSONResponse> response) {
                JSONResponse jsonResponse = response.body();

                mArrayListUser = new ArrayList<>(Arrays.asList(jsonResponse.getDatauser()));
                String nama_user = mArrayListUser.get(0).getNama_user();
                String telepon_user = mArrayListUser.get(0).getTelepon_user();

                txt_nama.setText(nama_user);
                txt_telepon.setText(telepon_user);
            }

            @Override
            public void onFailure(Call<JSONResponse> call, Throwable t) {
                Log.d("Error",t.getMessage());
            }
        });
    }

    class AmbilIdTransaksiTerakhir extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            pDialog = new ProgressDialog(ActivityCheckOut.this);
            pDialog.setMessage("Membuat pesanan ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        protected String doInBackground(String... params) {
            try {
                List<NameValuePair> params1 = new ArrayList<NameValuePair>();
                JSONObject json = jsonParser.makeHttpRequest(url_ambil_id_transaksi, "GET", params1);
                string_json = json.getJSONArray("nomor");

                runOnUiThread(new Runnable() {
                    public void run() {


                        try {
                            // ambil objek member pertama dari JSON Array
                            JSONObject ar = string_json.getJSONObject(0);
                            String nomor_id_d = ar.getString("id");
                            Integer tambah = Integer.parseInt(nomor_id_d) + 1;
                            String fix = getDateId() + "-" + String.valueOf(tambah);
                            String_id_transaksi = getDateId() + "-" + String.valueOf(tambah);

                            if (String_id_transaksi != null) {
                                simpanTransaksi();
                            } else {

                            }
                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                });
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String file_url) {
            //simpanTransaksi();
            pDialog.dismiss();
        }
    }


    public void simpanTransaksi() {

        String id_transaksi = String_id_transaksi;
        String id_user = String_id_user;
        String nama_user = txt_nama.getText().toString();
        String telepon_user = txt_telepon.getText().toString();
        String tgl_pesanan = ubah_tanggal_untuk_db;
        String jam_pesanan = txt_jam_pemesanan.getText().toString();
        String catatan = txt_catatan.getText().toString();
        String grand_total = txt_grand_total.getText().toString();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RequestInterface api = retrofit.create(RequestInterface.class);
        Call<ValueMessage> call = api.insertTransaksi(id_transaksi, id_user, nama_user, telepon_user, tgl_pesanan, jam_pesanan, catatan, grand_total);
        call.enqueue(new Callback<ValueMessage>() {
            @Override
            public void onResponse(Call<ValueMessage> call, Response<ValueMessage> response) {
                String value = response.body().getValue();
                String message = response.body().getMessage();
                //progressDialog.dismiss();
                if (value.equals("1")) {
                    Toast.makeText(ActivityCheckOut.this, message, Toast.LENGTH_SHORT).show();
                    simpanMenuTransaksi();

                    Intent intent = new Intent(ActivityCheckOut.this, MainActivity.class);
                    //intent.putExtra("user_id", txt_id_user.getText().toString());
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(ActivityCheckOut.this, message, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ValueMessage> call, Throwable t) {
                t.printStackTrace();
                //progressDialog.dismiss();
                Toast.makeText(ActivityCheckOut.this, "Jaringan Error!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void simpanMenuTransaksi() {
        //membuat progress dialog
        //progressDialog = new ProgressDialog(this);
        //progressDialog.setCancelable(false);
        //progressDialog.setMessage("Proses booking ...");
        //progressDialog.show();

        for (ModelMenu mo : modelMenu) {
            //Toast.makeText(this, "Insert Into tbl_transaksi "+mo.getNama_menu(), Toast.LENGTH_SHORT).show();
            final String id_transaksi = String_id_transaksi;
            String menu_id = mo.getMenu_id();
            String nama_menu = mo.getNama_menu();
            String gambar_menu = mo.getGambar_menu();
            String harga_menu = mo.getHarga();
            String jumlah = mo.getJumlah();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            RequestInterface api = retrofit.create(RequestInterface.class);
            Call<ValueMessage> call = api.insertMenuTransaksi(id_transaksi, menu_id, nama_menu, gambar_menu, harga_menu, jumlah);
            call.enqueue(new Callback<ValueMessage>() {
                @Override
                public void onResponse(Call<ValueMessage> call, Response<ValueMessage> response) {
                    String value = response.body().getValue();
                    String message = response.body().getMessage();
                    //progressDialog.dismiss();
                    if (value.equals("1")) {
                        //Toast.makeText(Checkout.this, message, Toast.LENGTH_SHORT).show();
                        SQLiteDatabase db = dataHelper.getReadableDatabase(); //Penting !! biar kalo baru install app ga ada table nya tidak error
                        db.execSQL("delete from tbl_menu_transaksi"); //reset cart

                        //Log.e(TAG, "Input Response: " + id_transaksi + "" + mitra_id);

                        //kirimNotifSendiri(); //kirim notif menunggu konfirmasi
                        //Intent intent = new Intent(Checkout.this, MainActivity.class);
                        //finish();
                        //startActivity(intent);
                    } else {
                        Toast.makeText(ActivityCheckOut.this, message, Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ValueMessage> call, Throwable t) {
                    t.printStackTrace();
                    //progressDialog.dismiss();
                    Toast.makeText(ActivityCheckOut.this, "Jaringan Error!", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
