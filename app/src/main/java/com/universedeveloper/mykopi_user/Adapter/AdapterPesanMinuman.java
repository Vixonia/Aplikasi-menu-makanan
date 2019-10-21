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
import com.universedeveloper.mykopi_user.Api.ModelMenu;
import com.universedeveloper.mykopi_user.PesanCemilan.DetailPesanCemilan;
import com.universedeveloper.mykopi_user.PesanMinum.DetailPesanMinum;
import com.universedeveloper.mykopi_user.R;

import java.util.ArrayList;

public class AdapterPesanMinuman extends RecyclerView.Adapter<AdapterPesanMinuman.ViewHolder> implements Filterable {

    private ArrayList<ModelMenu> mArrayList;
    private ArrayList<ModelMenu> mFilteredList;
    private Context context;

    public AdapterPesanMinuman(ArrayList<ModelMenu> arrayList) {
        this.mArrayList = arrayList;
        this.mFilteredList = arrayList;

    }


    @Override
    public AdapterPesanMinuman.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_adapter_pesan_minuman, viewGroup, false);
        context = viewGroup.getContext();
        return new AdapterPesanMinuman.ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull AdapterPesanMinuman.ViewHolder viewHolder, int i) {
        viewHolder.txtid_menu.setText(mFilteredList.get(i).getMenu_id());
        viewHolder.txt_kategori.setText((mFilteredList.get(i).getKategori()));
        viewHolder.txt_gambar.setText(mFilteredList.get(i).getGambar_menu());
        viewHolder.txtharga_menu.setText(mFilteredList.get(i).getHarga());
        viewHolder.txtnama_menu.setText(mFilteredList.get(i).getNama_menu()); //untuk mengirim url gambar ke berbagai activity
        Glide.with(context)
                .load(mFilteredList.get(i).getGambar_menu())
                .placeholder(R.drawable.noimage)
                .error(R.drawable.noimage)
                .into(viewHolder.image);
        viewHolder.txt_stok.setText((mFilteredList.get(i).getStok()));
    }

    @Override
    public int getItemCount() {
        return mFilteredList.size();
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

                    ArrayList<ModelMenu> filteredList = new ArrayList<>();

                    for (ModelMenu androidVersion : mArrayList) {

                        if (androidVersion.getKategori().toLowerCase().contains(charString) || androidVersion.getKategori().toLowerCase().contains(charString)) {
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
                mFilteredList = (ArrayList<ModelMenu>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView txtnama_menu, txtharga_menu,txtid_menu,txt_kategori,txt_stok,txt_gambar;
        private ImageView image;

        public ViewHolder(View view) {
            super(view);

            txtnama_menu = view.findViewById(R.id.txtnamamenu);
            txtid_menu = view.findViewById(R.id.txtidmenu);
            txtharga_menu = view.findViewById(R.id.txtharga);
            txt_kategori = view.findViewById(R.id.txtkategori);
            txt_stok = view.findViewById(R.id.txtstok);
            image = view.findViewById(R.id.image1);
            txt_gambar = view.findViewById(R.id.txtgambar);

            view.setOnClickListener(this);

            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent detail = new Intent(view.getContext(), DetailPesanMinum.class);
                    detail.putExtra("menu_id", txtid_menu.getText());
                    detail.putExtra("nama_menu", txtnama_menu.getText());
                    detail.putExtra("gambar_menu", txt_gambar.getText());
                    detail.putExtra("harga", txtharga_menu.getText());
                    detail.putExtra("kategori", txt_kategori.getText());
                    detail.putExtra("stok", txt_stok.getText());
                    view.getContext().startActivity(detail);
                }
            });

        }

        @Override
        public void onClick(View view) {
            Intent detail = new Intent(view.getContext(), DetailPesanMinum.class);
            detail.putExtra("menu_id", txtid_menu.getText());
            detail.putExtra("nama_menu", txtnama_menu.getText());
            detail.putExtra("harga", txtharga_menu.getText());
            detail.putExtra("gambar_menu", txt_gambar.getText());
            detail.putExtra("kategori", txt_kategori.getText());
            detail.putExtra("stok", txt_stok.getText());
            view.getContext().startActivity(detail);
        }
    }
}
