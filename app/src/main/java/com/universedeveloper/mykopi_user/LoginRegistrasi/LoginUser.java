package com.universedeveloper.mykopi_user.LoginRegistrasi;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.universedeveloper.mykopi_user.MainActivity;
import com.universedeveloper.mykopi_user.R;
import com.universedeveloper.mykopi_user.Utility.AppController;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginUser extends AppCompatActivity {
    EditText txtemail, txtpassword;
    Button btn_login, btnregister;
    public final static String TAG_ID_USER = "id_user";
    public final static String TAG_ID_EMAIL = "email_user";

    Intent intent;
    ProgressDialog pDialog;
    int success;
    ConnectivityManager conMgr;

    private String url = "http://www.gudangandroid.universedeveloper.com/mykopi/user/login_user.php";

    private static final String TAG = LoginUser.class.getSimpleName();
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";

    public final static String TAG_ID = "id_user";
    public final static String TAG_NAMA_LENGKAP = "nama_user";
    public final static String TAG_NO_TELEPON = "telepon_user";


    String tag_json_obj = "json_obj_req";
    SharedPreferences sharedpreferences;
    Boolean session = false;
    String id_user, value_nama_lengkap, value_no_telepon;
    public static final String my_shared_preferences = "my_shared_preferences";
    public static final String session_status = "session_status";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_user);

        conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        {
            if (conMgr.getActiveNetworkInfo() != null
                    && conMgr.getActiveNetworkInfo().isAvailable()
                    && conMgr.getActiveNetworkInfo().isConnected()) {
            } else {
                Toast.makeText(getApplicationContext(), "No Internet Connection",
                        Toast.LENGTH_LONG).show();
            }
        }

        txtemail = findViewById(R.id.txtemail);
        txtpassword = findViewById(R.id.txtpassword);
        btn_login = findViewById(R.id.btnlogin);
        btnregister = findViewById(R.id.btnregistrasi);
        btnregister.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                intent = new Intent(LoginUser.this, RegistrasiUser.class);
                finish();
                startActivity(intent);
            }
        });

        // Cek session login jika TRUE maka langsung buka MainActivity
        sharedpreferences = getSharedPreferences(my_shared_preferences, Context.MODE_PRIVATE);
        session = sharedpreferences.getBoolean(session_status, false);
        id_user = sharedpreferences.getString(TAG_ID, null);
        value_nama_lengkap = sharedpreferences.getString(TAG_NAMA_LENGKAP, null);
        value_no_telepon = sharedpreferences.getString(TAG_NO_TELEPON, null);


        if (session) {
            Intent intent = new Intent(LoginUser.this, MainActivity.class);
            intent.putExtra(TAG_ID, id_user);
            intent.putExtra(TAG_NAMA_LENGKAP, value_nama_lengkap);
            intent.putExtra(TAG_NO_TELEPON, value_no_telepon);

            finish();
            startActivity(intent);
        }
        btn_login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                checkLogin();
            }
        });
    }

    private void checkLogin() {
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Logging in ...");
        pDialog.show();

        StringRequest strReq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.e(TAG, "Login Response: " + response.toString());
                pDialog.hide();

                try {
                    JSONObject jObj = new JSONObject(response);
                    success = jObj.getInt(TAG_SUCCESS);

                    // Check for error node in json
                    if (success == 1) {
                        value_nama_lengkap = jObj.getString(TAG_NAMA_LENGKAP);
                        value_no_telepon = jObj.getString(TAG_NO_TELEPON);
                        id_user = jObj.getString(TAG_ID);

                        Log.e("Successfully Login!", jObj.toString());

                        Toast.makeText(getApplicationContext(), jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();

                        // menyimpan login ke session
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putBoolean(session_status, true);
                        editor.putString(TAG_ID, id_user);
                        editor.putString(TAG_NAMA_LENGKAP, value_nama_lengkap);
                        editor.putString(TAG_NO_TELEPON, value_no_telepon);

                        editor.commit();

                        // Memanggil main activity
                        Intent intent = new Intent(LoginUser.this, MainActivity.class);
                        intent.putExtra(TAG_ID, id_user);
                        intent.putExtra(TAG_NAMA_LENGKAP, value_nama_lengkap);
                        intent.putExtra(TAG_NO_TELEPON, value_no_telepon);

                        finish();
                        startActivity(intent);
                    } else {
                        Toast.makeText(getApplicationContext(),
                                jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();

                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Login Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();

                pDialog.hide();

            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("email_user", txtemail.getText().toString());
                params.put("password", txtpassword.getText().toString());

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_json_obj);
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginUser.this);

        builder.setTitle("Keluar ");
        builder.setMessage("Apakah kamu yakin ingin keluar dari aplikasi ?");

        builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                sharedpreferences = getSharedPreferences(LoginUser.my_shared_preferences, Context.MODE_PRIVATE);
               SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putBoolean(LoginUser.session_status, false);
                editor.putString(TAG_ID_USER, null);
                editor.putString(TAG_ID_EMAIL, null);
                editor.apply();
                editor.commit();



                moveTaskToBack(true);
                finish();
                //// new DetailAplikasiSaya.HapusData().execute();
                dialog.dismiss();
            }

        });
        builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                // Do nothing
                dialog.dismiss();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
        //// Toast.makeText(this,"Keluar aplikasi!", Toast.LENGTH_LONG).show();

    }


}
