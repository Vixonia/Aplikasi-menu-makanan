package com.universedeveloper.mykopi_user.Nampan;


import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.universedeveloper.mykopi_user.Adapter.AdapterListNampan;
import com.universedeveloper.mykopi_user.Api.ModelMenu;
import com.universedeveloper.mykopi_user.Checkout.ActivityCheckOut;
import com.universedeveloper.mykopi_user.DataHelper;
import com.universedeveloper.mykopi_user.R;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class Nampanku extends Fragment {

    DataHelper dataHelper;
    RecyclerView recycler_cart;
    AdapterListNampan adapterListCart;
    List<ModelMenu> modelMenu;
    TextView text_kosong, txt_total_harga, txt_id;
    RelativeLayout layout_checkout;



    public Nampanku() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_nampanku, container, false);

        text_kosong = view.findViewById(R.id.text_kosong);
        txt_total_harga = view.findViewById(R.id.txt_total_harga);
        layout_checkout = view.findViewById(R.id.layout_checkout);

        modelMenu = new ArrayList<ModelMenu>();
        recycler_cart = view.findViewById(R.id.recycler_cart);

        dataHelper = new DataHelper(getContext());
        modelMenu = dataHelper.getData();
        adapterListCart = new AdapterListNampan(this, modelMenu);

        recycler_cart.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager1 = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recycler_cart.setLayoutManager(layoutManager1);
        recycler_cart.setAdapter(adapterListCart);
        recycler_cart.invalidate();

        UpdateTotalHarga();

        layout_checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ActivityCheckOut.class);
                //intent.putExtra("total_harga", totalPrice);
                startActivity(intent);
            }
        });

        return view;
    }

    public void UpdateData(){
        modelMenu = dataHelper.getData();
        adapterListCart = new AdapterListNampan(this, modelMenu);
        adapterListCart.notifyDataSetChanged();
        recycler_cart.setAdapter(adapterListCart);
        recycler_cart.invalidate();
    }

    public void UpdateTotalHarga(){
        Integer totalPrice = 0;
        modelMenu = dataHelper.getData();
        for (int i = 0; i<modelMenu.size(); i++)
        {
            totalPrice += (modelMenu.get(i).getTotal());

        }

        txt_total_harga.setText("Rp. "+totalPrice);

        if (txt_total_harga.getText().toString().equals("Rp. 0")){
            layout_checkout.setVisibility(View.GONE);
            text_kosong.setVisibility(View.VISIBLE);
        }
    }

}
