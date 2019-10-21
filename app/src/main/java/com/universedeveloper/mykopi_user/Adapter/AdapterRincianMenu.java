package com.universedeveloper.mykopi_user.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.universedeveloper.mykopi_user.Api.ModelTransaksiMenu;
import com.universedeveloper.mykopi_user.Api.ModelTransaksiUser;
import com.universedeveloper.mykopi_user.R;
import com.universedeveloper.mykopi_user.RiwayatPesanan.DetailPesananMenu;
import com.universedeveloper.mykopi_user.RiwayatPesanan.RiwayatPesananMenu;

import java.util.ArrayList;

public class AdapterRincianMenu extends RecyclerView.Adapter<AdapterRincianMenu.ViewHolder> implements Filterable {
    private ArrayList<ModelTransaksiMenu> mArrayList;
    private ArrayList<ModelTransaksiMenu> mFilteredList;
    private Context context;

    public AdapterRincianMenu(Context context, ArrayList<ModelTransaksiMenu> arrayList) {
        this.context = context;
        this.mArrayList = arrayList;
        this.mFilteredList = arrayList;

    }

    @Override
    public AdapterRincianMenu.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_adapter_rincian_menu, viewGroup, false);
        return new AdapterRincianMenu.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterRincianMenu.ViewHolder viewHolder, int i) {
        viewHolder.txtidtransaksi.setText(mFilteredList.get(i).getId_transaksi());
        viewHolder.txtmenuid.setText(mFilteredList.get(i).getMenu_id());
        viewHolder.txtnamamenu.setText((mFilteredList.get(i).getNama_menu()));
        viewHolder.txtgambar.setText(mFilteredList.get(i).getGambar_menu());
        viewHolder.txtharga.setText("Rp. "+ mFilteredList.get(i).getHarga());
        viewHolder.txtjumlah.setText(mFilteredList.get(i).getJumlah());
        Glide.with(context)
                .load(mFilteredList.get(i).getGambar_menu())
                .placeholder(R.drawable.noimage)
                .error(R.drawable.noimage)
                .into(viewHolder.image1);
    }

    @Override
    public int getItemCount() {
        return mFilteredList.size();
        //return mFilteredList == null ? 0 : mFilteredList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                String charString = charSequence.toString();

                if (charString.isEmpty()) {

                    mFilteredList = mArrayList;
                } else {

                    ArrayList<ModelTransaksiMenu> filteredList = new ArrayList<>();

                    for (ModelTransaksiMenu androidVersion : mArrayList) {

                        if (androidVersion.getId_transaksi().toLowerCase().contains(charString)) {
                            filteredList.add(androidVersion);
                        }
                    }
                    mFilteredList = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = mFilteredList;
                return filterResults;
            }
            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mFilteredList = (ArrayList<ModelTransaksiMenu>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView txtidtransaksi,txtmenuid,txtnamamenu,txtharga,txtjumlah,txtgambar;
        private ImageView image1;

        public ViewHolder(View view) {
            super(view);

            txtidtransaksi = view.findViewById(R.id.txtidtransaksi);
            txtmenuid = view.findViewById(R.id.txtmenuid);
            txtnamamenu = view.findViewById(R.id.txtnamamenu);
            txtgambar = view.findViewById(R.id.txtgambar);
            txtharga = view.findViewById(R.id.txtharga);
            image1 = view.findViewById(R.id.image1);
            txtjumlah = view.findViewById(R.id.txt_jumlah);

            view.setOnClickListener(this);


        }

        @Override
        public void onClick(View view) {

        }
    }
}
