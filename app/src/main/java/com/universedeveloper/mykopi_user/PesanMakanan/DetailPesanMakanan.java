package com.universedeveloper.mykopi_user.PesanMakanan;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.universedeveloper.mykopi_user.DataHelper;
import com.universedeveloper.mykopi_user.R;
import com.universedeveloper.mykopi_user.Utility.JSONParser;

import org.json.JSONArray;


public class DetailPesanMakanan extends AppCompatActivity {

    TextView txtnama_menu, txtharga_menu, txtstok, txtkategori;
    ImageView image;

    Button btnpesanmenu;

    String menu_id, nama_menu, harga, kategori, stok, gambar, id_transaksi;

    JSONArray string_json = null;
    private ProgressDialog pDialog;
    JSONParser jsonParser = new JSONParser();

    DataHelper dataHelper;
    protected Cursor cek_menu_sama, cek_list_kosong ;

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
        Glide.with(DetailPesanMakanan.this)
                .load(gambar)
                .placeholder(R.drawable.noimage)
                .error(R.drawable.noimage)
                .into(image);

        btnpesanmenu = findViewById(R.id.btnpesanmenu);
        btnpesanmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (stok.equals("Habis")){
                    Toast.makeText(DetailPesanMakanan.this, "Maaf menu habis", Toast.LENGTH_SHORT).show();
                }else {
                    //pakai alert dialog YA ATAU TIDAK untuk keluar dari aplikasi
                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(DetailPesanMakanan.this);

                    builder.setTitle("Pesan Cemilan");
                    builder.setMessage("Apakah anda yakin ingin memesan menu ini ?");

                    builder.setPositiveButton("Pesan", new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int which) {

                            dataHelper = new DataHelper(getApplicationContext());
                            SQLiteDatabase db = dataHelper.getReadableDatabase(); //Penting !! biar kalo baru install app ga ada table nya tidak error
                            cek_list_kosong = db.rawQuery("SELECT * FROM tbl_menu_transaksi", null);
                            cek_menu_sama = db.rawQuery("SELECT * FROM tbl_menu_transaksi where menu_id= "+menu_id, null);

                            if(cek_list_kosong.getCount()<1){ //jika ada menu yg sama
                                String jml="1";
                                id_transaksi = "1" ;

                                dataHelper.insertdata(id_transaksi,menu_id,nama_menu,gambar,harga,jml);
                                Toast.makeText(getApplicationContext(), "Telah dimasukan kedalam nampan", Toast.LENGTH_SHORT).show();

                                dialog.dismiss();
//                                ((DetailMenu)getActivity()).hitung_jumlah_list();
//                                ((DetailMenu)getActivity()).shake();

                            }else if (cek_menu_sama.getCount()>0){
                                Toast.makeText(getApplicationContext(), "Anda telah memasukkan menu ini kedalam list pembelian", Toast.LENGTH_LONG).show();
                                //dialog.dismiss();
                                //Intent intent = new Intent(getApplicationContext(), ListCart.class);
                                //startActivity(intent);
                            } else {
                                String jml="1";
                                id_transaksi = "1" ;

                                dataHelper.insertdata(id_transaksi,menu_id,nama_menu,gambar,harga,jml);
                                Toast.makeText(getApplicationContext(), "Telah dimasukan kedalam list", Toast.LENGTH_SHORT).show();

                                dialog.dismiss();
                            }
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
            }
        });
    }
}
