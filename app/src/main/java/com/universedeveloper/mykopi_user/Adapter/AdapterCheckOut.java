package com.universedeveloper.mykopi_user.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import com.universedeveloper.mykopi_user.Api.ModelMenu;
import com.universedeveloper.mykopi_user.DataHelper;
import com.universedeveloper.mykopi_user.R;

public class AdapterCheckOut extends RecyclerView.Adapter<AdapterCheckOut.ViewHolder>  {
    private List<ModelMenu> mArrayList;

    private Context context;
    DataHelper dataHelper;

    //private Context mContext;

    String nama ;

    public AdapterCheckOut(List<ModelMenu> modelMenu) {
        this.mArrayList = modelMenu;

    }


    @Override
    public AdapterCheckOut.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_checkout, viewGroup, false);
        context = viewGroup.getContext();
        dataHelper = new DataHelper(context);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AdapterCheckOut.ViewHolder viewHolder, int position) {
        ModelMenu modelMenu = mArrayList.get(position);
        viewHolder.txt_id_transaksi.setText(modelMenu.getId_transaksi());
        viewHolder.txt_id_menu.setText(modelMenu.getMenu_id());
        viewHolder.txt_nama_menu.setText(modelMenu.getNama_menu());
        viewHolder.txt_harga.setText(modelMenu.getHarga());
        viewHolder.txt_jumlah.setText(modelMenu.getJumlah());

        viewHolder.txt_jumlah_harga.setText("Rp. "+modelMenu.getTotal().toString());



    }

    @Override
    public int getItemCount() {
        return mArrayList.size();
        //return mFilteredList == null ? 0 : mFilteredList.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txt_id_transaksi, txt_id_menu ,txt_nama_menu,txt_harga, txt_jumlah, txt_jumlah_harga;

        public ViewHolder(View view) {
            super(view);

            txt_id_transaksi = view.findViewById(R.id.txt_id_transaksi);
            txt_id_menu = view.findViewById(R.id.txt_id_menu);
            txt_nama_menu = view.findViewById(R.id.txt_nama_menu);
            txt_harga = view.findViewById(R.id.txt_harga);
            txt_jumlah = view.findViewById(R.id.txt_jumlah);
            txt_jumlah_harga = view.findViewById(R.id.txt_total);

        }


    }
}