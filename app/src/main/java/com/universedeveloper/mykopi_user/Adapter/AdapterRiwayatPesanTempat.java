package com.universedeveloper.mykopi_user.Adapter;

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
import android.widget.TextView;

import com.universedeveloper.mykopi_user.Api.ModelTransaksiTempat;
import com.universedeveloper.mykopi_user.Api.ModelTransaksiUser;
import com.universedeveloper.mykopi_user.R;
import com.universedeveloper.mykopi_user.RiwayatPesanan.DetailPesananMenu;
import com.universedeveloper.mykopi_user.RiwayatPesanan.DetailPesananTempat;

import java.util.ArrayList;

public class AdapterRiwayatPesanTempat extends RecyclerView.Adapter<AdapterRiwayatPesanTempat.ViewHolder> implements Filterable {
    private ArrayList<ModelTransaksiTempat> mArrayList;
    private ArrayList<ModelTransaksiTempat> mFilteredList;

    public AdapterRiwayatPesanTempat(ArrayList<ModelTransaksiTempat> arrayList) {
        this.mArrayList = arrayList;
        this.mFilteredList = arrayList;

    }

    @Override
    public AdapterRiwayatPesanTempat.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_adapter_riwayat_pesan_tempat, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterRiwayatPesanTempat.ViewHolder viewHolder, int i) {
        viewHolder.txt_tanggal_pesan_tempat.setText(mFilteredList.get(i).getTanggal_pesan());
        viewHolder.txt_jam_pesan_tempat.setText(mFilteredList.get(i).getJam_pesan());
        viewHolder.txt_nomor_meja.setText(mFilteredList.get(i).getNomor_meja());
        viewHolder.txt_id_user.setText((mFilteredList.get(i).getId_user()));
        viewHolder.txt_nama_user.setText(mFilteredList.get(i).getNama_user());
        viewHolder.txt_email_user.setText(mFilteredList.get(i).getEmail_user());
        viewHolder.txt_telepon_user.setText(mFilteredList.get(i).getTelepon_user());
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

                    ArrayList<ModelTransaksiTempat> filteredList = new ArrayList<>();

                    for (ModelTransaksiTempat androidVersion : mArrayList) {

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
                mFilteredList = (ArrayList<ModelTransaksiTempat>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView txt_tanggal_pesan_tempat,txt_jam_pesan_tempat,txt_id_user,txt_nama_user,
                txt_telepon_user,txt_nomor_meja,txt_email_user;

        public ViewHolder(View view) {
            super(view);

            txt_tanggal_pesan_tempat = view.findViewById(R.id.txt_tanggal_pesan_tempat);
            txt_jam_pesan_tempat = view.findViewById(R.id.txt_jam_pesan_tempat);
            txt_id_user = view.findViewById(R.id.txt_id_user);
            txt_nama_user = view.findViewById(R.id.txt_nama_user);
            txt_telepon_user = view.findViewById(R.id.txt_telepon_user);
            txt_nomor_meja = view.findViewById(R.id.txt_nomor_meja);
            txt_email_user = view.findViewById(R.id.txt_email_user);

            view.setOnClickListener(this);


            txt_nomor_meja.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent detail = new Intent(view.getContext(), DetailPesananTempat.class);
                    detail.putExtra("tanggal_pesan", txt_tanggal_pesan_tempat.getText());
                    detail.putExtra("jam_pesan", txt_jam_pesan_tempat.getText());
                    detail.putExtra("id_user", txt_id_user.getText());
                    detail.putExtra("nama_user", txt_nama_user.getText());
                    detail.putExtra("telepon_user", txt_telepon_user.getText());
                    detail.putExtra("nomor_meja", txt_nomor_meja.getText());
                    detail.putExtra("email_user", txt_email_user.getText());
                    view.getContext().startActivity(detail);
                }
            });

        }

        @Override
        public void onClick(View view) {
            Intent detail = new Intent(view.getContext(), DetailPesananTempat.class);
            detail.putExtra("tanggal_pesan", txt_tanggal_pesan_tempat.getText());
            detail.putExtra("jam_pesan", txt_jam_pesan_tempat.getText());
            detail.putExtra("id_user", txt_id_user.getText());
            detail.putExtra("nama_user", txt_nama_user.getText());
            detail.putExtra("telepon_user", txt_telepon_user.getText());
            detail.putExtra("nomor_meja", txt_nomor_meja.getText());
            detail.putExtra("email_user", txt_email_user.getText());
            view.getContext().startActivity(detail);
        }
    }
}
