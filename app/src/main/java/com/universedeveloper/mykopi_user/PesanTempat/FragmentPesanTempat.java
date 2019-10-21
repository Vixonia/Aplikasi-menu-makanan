package com.universedeveloper.mykopi_user.PesanTempat;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.universedeveloper.mykopi_user.Api.JSONResponse;
import com.universedeveloper.mykopi_user.Api.ModelProfilUser;
import com.universedeveloper.mykopi_user.Api.RequestInterface;
import com.universedeveloper.mykopi_user.LoginRegistrasi.LoginUser;
import com.universedeveloper.mykopi_user.R;
import com.universedeveloper.mykopi_user.Utility.AppController;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentPesanTempat.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentPesanTempat#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentPesanTempat extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = FragmentPesanTempat.class.getSimpleName();
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    String tag_json_obj = "json_obj_req";
    private ProgressDialog pDialog;
    String id_pesantempat, tanggal_pesan, jam_pesan, nomor_meja, nama_user,email_user,telepon_user;

    int success;
    private String url = "http://gudangandroid.universedeveloper.com/mykopi/user/pesan_tempat1.php";
    public static final String BASE_URL = "http://universedeveloper.com/gudangandroid/";
    private ArrayList<ModelProfilUser> mArrayListUser;

    String id_user;
    SharedPreferences sharedpreferences;

    DatePicker datePicker1;
    TimePicker spinnerjam;
    Spinner no_meja;
    Button btnordertempat;
    private LinearLayout parent1;
    private TextView tv_namapemesan, tv_telepon, tv_email;

    private int mYear, mMonth, mDay, mHour, mMinute;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public FragmentPesanTempat() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentPesanTempat.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentPesanTempat newInstance(String param1, String param2) {
        FragmentPesanTempat fragment = new FragmentPesanTempat();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);




        }
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragment_pesan_tempat, container, false);

        tv_namapemesan = view.findViewById(R.id.tv_namapemesan);
        tv_telepon = view.findViewById(R.id.tv_telepon);
        tv_email = view.findViewById(R.id.tv_email);
        datePicker1 = view.findViewById(R.id.datePicker1);
        spinnerjam = view.findViewById(R.id.spinnerjam);
        no_meja = view.findViewById(R.id.no_meja);



        sharedpreferences = getActivity().getSharedPreferences(LoginUser.my_shared_preferences, Context.MODE_PRIVATE);
        id_user = sharedpreferences.getString("id_user", "0");
       //// Toast.makeText(getActivity(), "Ini id User "+ id_user, Toast.LENGTH_SHORT).show();

        nama_user = getActivity().getIntent().getStringExtra("nama_user");
        email_user = getActivity().getIntent().getStringExtra("email_user");
        telepon_user = getActivity().getIntent().getStringExtra("telepon_user");



        ambilProfilUser();


        btnordertempat = view.findViewById(R.id.btnordertempat);
        btnordertempat.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                String tanggal_pesan = getDate(datePicker1).toString();
                String jam_pesan = getTime(spinnerjam).toString();
                String nomor_meja = no_meja.getSelectedItem().toString();
                String nama_user = tv_namapemesan.getText().toString();
                String email_user = tv_email.getText().toString();
                String telepon_user = tv_telepon.getText().toString();

                checkRegister(tanggal_pesan, jam_pesan, nomor_meja, nama_user, email_user, telepon_user);

                ambilProfilUser();


            }
        });
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }



    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }



    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
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

                tv_namapemesan.setText(nama_user);
                tv_telepon.setText(telepon_user);
                tv_email.setText(email_user);

            }

            @Override
            public void onFailure(Call<JSONResponse> call, Throwable t) {
                Log.d("Error",t.getMessage());
            }
        });
    }

    private void checkRegister(final String tanggal_pesan, final String jam_pesan, final String nomor_meja, final String nama_user,final String email_user,final String telepon_user) {
        pDialog = new ProgressDialog(getActivity());
        pDialog.setCancelable(false);
        pDialog.setMessage("Silahkan Tunggu ...");
        showDialog();
        StringRequest strReq = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.e(TAG, "Register Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    success = jObj.getInt(TAG_SUCCESS);

                    // Check for error node in json
                    if (success == 1) {

                        Log.e("Sukses!!!" , jObj.toString());

                        Toast.makeText(getActivity(),
                                jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();


                    } else {
                        Toast.makeText(getActivity(),
                                jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();

                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                }

            }
        }, new com.android.volley.Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Login Error: " + error.getMessage());
                Toast.makeText(getActivity(),
                        error.getMessage(), Toast.LENGTH_LONG).show();

                hideDialog();

            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                ///params.put("id_pesantempat", id_pesantempat);
                params.put("tanggal_pesan", tanggal_pesan);
                params.put("jam_pesan", jam_pesan);
                params.put("nomor_meja", nomor_meja);
                params.put("id_user", id_user);
                params.put("nama_user", nama_user);
                params.put("email_user", email_user);
                params.put("telepon_user", telepon_user);
                return params;



            }
        };

        // Adding request to request queue

        AppController.getInstance().addToRequestQueue(strReq, tag_json_obj);
        ///AppController.getInstance().addToRequestQueue(strReq, tag_json_obj);

    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    private String getDate(DatePicker dp)
    {
        int day = dp.getDayOfMonth();
        int month = dp.getMonth()+1;
        int year = dp.getYear();
        return String.valueOf(day)+"-"+ String.valueOf(month)+"-"+ String.valueOf(year);

    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    private String getTime(TimePicker dp)
    {
        int hour = dp.getHour();
        int minutes = dp.getMinute()+1;
        return String.valueOf(hour)+":"+ String.valueOf(minutes)+" WIB";

    }

}
