package com.universedeveloper.mykopi_user.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.universedeveloper.mykopi_user.Api.ModelMenu;
import com.universedeveloper.mykopi_user.Api.ModelTransaksiUser;
import com.universedeveloper.mykopi_user.PesanCemilan.DetailPesanCemilan;
import com.universedeveloper.mykopi_user.PesanMakanan.DetailPesanMakanan;
import com.universedeveloper.mykopi_user.R;
import com.universedeveloper.mykopi_user.RiwayatPesanan.DetailPesananMenu;

import java.util.ArrayList;

public class AdapterRiwayatPesanMenu extends RecyclerView.Adapter<AdapterRiwayatPesanMenu.ViewHolder> implements Filterable {
    private ArrayList<ModelTransaksiUser> mArrayList;
    private ArrayList<ModelTransaksiUser> mFilteredList;

    public AdapterRiwayatPesanMenu(ArrayList<ModelTransaksiUser> arrayList) {
        this.mArrayList = arrayList;
        this.mFilteredList = arrayList;

    }

    @Override
    public AdapterRiwayatPesanMenu.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_adapter_riwayat_pesan_menu, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterRiwayatPesanMenu.ViewHolder viewHolder, int i) {
        viewHolder.txt_id_transaksi.setText(mFilteredList.get(i).getId_transaksi());
        viewHolder.txt_waktu_pesan.setText(mFilteredList.get(i).getWaktu_pesan());
        viewHolder.txt_id_user.setText((mFilteredList.get(i).getId_user()));
        viewHolder.txt_nama_user.setText(mFilteredList.get(i).getNama_user());
        viewHolder.txt_telepon_user.setText(mFilteredList.get(i).getTelepon_user());
        viewHolder.txt_tgl_pesan.setText(mFilteredList.get(i).getTgl_pesanan());
        viewHolder.txt_jam_pesan.setText(mFilteredList.get(i).getJam_pesanan());
        viewHolder.txt_catatan.setText(mFilteredList.get(i).getCatatan());
        viewHolder.txt_grand_total.setText("Rp. "+mFilteredList.get(i).getGrand_total());
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

                    ArrayList<ModelTransaksiUser> filteredList = new ArrayList<>();

                    for (ModelTransaksiUser androidVersion : mArrayList) {

                        if (androidVersion.getId_user().toLowerCase().contains(charString)) {
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
                mFilteredList = (ArrayList<ModelTransaksiUser>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView txt_id_transaksi,txt_waktu_pesan,txt_id_user,txt_nama_user,
                txt_telepon_user,txt_tgl_pesan,txt_jam_pesan,txt_catatan,txt_grand_total;

        public ViewHolder(View view) {
            super(view);

            txt_id_transaksi = view.findViewById(R.id.txt_id_transaksi);
            txt_waktu_pesan = view.findViewById(R.id.txt_waktu_pesan);
            txt_id_user = view.findViewById(R.id.txt_id_user);
            txt_nama_user = view.findViewById(R.id.txt_nama_akun);
            txt_telepon_user = view.findViewById(R.id.txt_telepon);
            txt_tgl_pesan = view.findViewById(R.id.txt_tanggal_pesan);
            txt_jam_pesan = view.findViewById(R.id.txt_jam_pemesanan);
            txt_catatan = view.findViewById(R.id.txt_catatan);
            txt_grand_total = view.findViewById(R.id.txt_grand_total);

            view.setOnClickListener(this);


//            txt_grand_total.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Intent detail = new Intent(view.getContext(), DetailPesananMenu.class);
//                    detail.putExtra("id_transaksi", txt_id_transaksi.getText());
//                    detail.putExtra("waktu_pesan", txt_waktu_pesan.getText());
//                    detail.putExtra("id_user", txt_id_user.getText());
//                    detail.putExtra("nama_user", txt_nama_user.getText());
//                    detail.putExtra("telepon_user", txt_telepon_user.getText());
//                    detail.putExtra("tgl_pesanan", txt_tgl_pesan.getText());
//                    detail.putExtra("jam_pesanan", txt_jam_pesan.getText());
//                    detail.putExtra("catatan", txt_catatan.getText());
//                    detail.putExtra("grand_total", txt_grand_total.getText());
//                    view.getContext().startActivity(detail);
//                }
//            });

        }

        @Override
        public void onClick(View view) {
            Intent detail = new Intent(view.getContext(), DetailPesananMenu.class);
            detail.putExtra("id_transaksi", txt_id_transaksi.getText());
            detail.putExtra("waktu_pesan", txt_waktu_pesan.getText());
            detail.putExtra("id_user", txt_id_user.getText());
            detail.putExtra("nama_user", txt_nama_user.getText());
            detail.putExtra("telepon_user", txt_telepon_user.getText());
            detail.putExtra("tgl_pesanan", txt_tgl_pesan.getText());
            detail.putExtra("jam_pesanan", txt_jam_pesan.getText());
            detail.putExtra("catatan", txt_catatan.getText());
            detail.putExtra("grand_total", txt_grand_total.getText());
            view.getContext().startActivity(detail);
        }
    }
}