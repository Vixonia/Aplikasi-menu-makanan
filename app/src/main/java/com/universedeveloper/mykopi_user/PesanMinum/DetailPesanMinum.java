package com.universedeveloper.mykopi_user.PesanMinum;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.universedeveloper.mykopi_user.R;
import com.universedeveloper.mykopi_user.Utility.JSONParser;

import org.json.JSONArray;

public class DetailPesanMinum extends AppCompatActivity {

    TextView txtnama_menu, txtharga_menu, txtstok, txtkategori;
    ImageView image;

    Button btnpesanmenu;

    String menu_id, nama_menu, harga, kategori, stok, gambar;

    JSONArray string_json = null;
    private ProgressDialog pDialog;
    JSONParser jsonParser = new JSONParser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_pesan_makanan);

        menu_id = getIntent().getStringExtra("menu_id");
        nama_menu = getIntent().getStringExtra("nama_menu");
        harga = getIntent().getStringExtra("harga");
        kategori = getIntent().getStringExtra("kategori");
        stok = getIntent().getStringExtra("stok");
        gambar = getIntent().getStringExtra("gambar_menu");

        txtnama_menu = findViewById(R.id.txtnama_menu);
        txtharga_menu = findViewById(R.id.txtharga_menu);
        txtstok = findViewById(R.id.txtstok);
        txtkategori = findViewById(R.id.txtkategori);
        image = findViewById(R.id.image1);

        txtnama_menu.setText(nama_menu);
        txtharga_menu.setText(harga);
        txtkategori.setText(kategori);
        txtstok.setText(stok);
        Glide.with(DetailPesanMinum.this)
                .load(gambar)
                .placeholder(R.drawable.noimage)
                .error(R.drawable.noimage)
                .into(image);
    }
}
