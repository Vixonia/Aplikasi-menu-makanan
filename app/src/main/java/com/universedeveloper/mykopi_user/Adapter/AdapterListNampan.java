package com.universedeveloper.mykopi_user.Adapter;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import com.universedeveloper.mykopi_user.Api.ModelMenu;
import com.universedeveloper.mykopi_user.DataHelper;
import com.universedeveloper.mykopi_user.Nampan.Nampanku;
import com.universedeveloper.mykopi_user.R;

public class AdapterListNampan extends RecyclerView.Adapter<AdapterListNampan.ViewHolder>  {
    private List<ModelMenu> mArrayList;
    private Nampanku fragment;
    private Context context;
    DataHelper dataHelper;

    //private Context mContext;

    public AdapterListNampan(Nampanku fragment,List<ModelMenu> modelMenu) {
        this.fragment = fragment;
        this.mArrayList = modelMenu;
    }


    @Override
    public AdapterListNampan.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_list_nampan, viewGroup, false);
        context = viewGroup.getContext();
        dataHelper = new DataHelper(context);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AdapterListNampan.ViewHolder viewHolder, int position) {
        ModelMenu modelMenu = mArrayList.get(position);
        viewHolder.txt_id_menu.setText(modelMenu.getMenu_id());
        viewHolder.txt_nama_menu.setText(modelMenu.getNama_menu());
        viewHolder.txt_harga.setText(modelMenu.getHarga());
        viewHolder.txt_jumlah.setText(modelMenu.getJumlah());
        Glide.with(context)
                .load(modelMenu.getGambar_menu())
                .placeholder(R.drawable.noimage)
                .error(R.drawable.noimage)
                .into(viewHolder.image);
        viewHolder.txt_jumlah_harga.setText("Rp. "+modelMenu.getTotal().toString());

    }

    @Override
    public int getItemCount() {
        return mArrayList.size();
        //return mFilteredList == null ? 0 : mFilteredList.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView txt_id_menu ,txt_nama_menu,txt_harga, txt_jumlah, txt_jumlah_harga;
        private ImageView image, btn_hapus, btn_ubah, btn_centang;
        private Button btn_tambah, btn_kurang;

        public ViewHolder(View view) {
            super(view);

            txt_id_menu = (TextView)view.findViewById(R.id.txt_id_menu);
            txt_nama_menu = (TextView)view.findViewById(R.id.txt_nama_menu);
            txt_harga = (TextView)view.findViewById(R.id.txt_harga);
            txt_jumlah = (TextView)view.findViewById(R.id.txt_jumlah);
            txt_jumlah_harga = (TextView)view.findViewById(R.id.txt_jumlah_harga);
            image = (ImageView)view.findViewById(R.id.image);
            btn_hapus = (ImageView)view.findViewById(R.id.btn_hapus);
            btn_ubah = (ImageView)view.findViewById(R.id.btn_ubah);
            btn_centang = (ImageView) view.findViewById(R.id.btn_centang);
            btn_tambah = (Button) view.findViewById(R.id.btn_tambah);
            btn_kurang = (Button) view.findViewById(R.id.btn_kurang);


            view.setOnClickListener(this);

            image.setOnClickListener(new View.OnClickListener() {
               @Override
                public void onClick(View view) {

                }
            });

            btn_kurang.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String jml=txt_jumlah.getText().toString();
                    int presentIntVal=Integer.parseInt(jml);
                    if (presentIntVal == 1){
                        Toast.makeText(context, "Jumlah pembelian tidak boleh kosong", Toast.LENGTH_SHORT).show();
                    }else {
                        presentIntVal--;
                    }
                    txt_jumlah.setText(String.valueOf(presentIntVal));

                    int intHarga = Integer.parseInt(txt_harga.getText().toString());
                    int intJumlah = Integer.parseInt(txt_jumlah.getText().toString());
                    int perkalian = intHarga*intJumlah;
                    txt_jumlah_harga.setText("Rp. "+perkalian);

                    ModelMenu modelMenu ;
                    modelMenu = new ModelMenu();
                    modelMenu.setTotal(perkalian);

                    dataHelper.updatedata(perkalian,txt_jumlah.getText().toString(),txt_id_menu.getText().toString());

//                    if(context instanceof Nampanku){
//                        ((Nampanku)context).UpdateTotalHarga();
//                    }

                    fragment.UpdateTotalHarga();

                }
            });



            btn_tambah.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String jml=txt_jumlah.getText().toString();
                    int presentIntVal=Integer.parseInt(jml);
                    presentIntVal++;
                    txt_jumlah.setText(String.valueOf(presentIntVal));

                    int doubHarga = Integer.parseInt(txt_harga.getText().toString());
                    int intJumlah = Integer.parseInt(txt_jumlah.getText().toString());
                    Integer perkalian = doubHarga*intJumlah;
                    txt_jumlah_harga.setText("Rp. "+perkalian);

                    //ModelPencarian modelPencarian ;
                    //modelPencarian = new ModelPencarian();
                    //modelPencarian.setTotal(perkalian);

                    List<ModelMenu> data = new ArrayList();
                    ModelMenu data_total = new ModelMenu();
                    data_total.setTotal(perkalian);
                    data.add(data_total);

                    dataHelper.updatedata(perkalian,txt_jumlah.getText().toString(),txt_id_menu.getText().toString());

//                    if(context instanceof ListCart){
//                        ((ListCart)context).UpdateTotalHarga();
//                    }

                    fragment.UpdateTotalHarga();

                }
            });

            btn_hapus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);

                    builder.setTitle("Hapus Menu");
                    builder.setMessage("Apakah kamu ingin menghapus menu dari daftar pembelian ?");

                    builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int which) {
                            SQLiteDatabase db = dataHelper.getWritableDatabase();
                            db.execSQL("delete from tbl_menu_transaksi where menu_id = '"+txt_id_menu.getText().toString()+"'");
                            Toast.makeText(context, "Data berhasil dihapus", Toast.LENGTH_SHORT).show();

                            mArrayList.remove(getAdapterPosition());
                            notifyItemRemoved(getAdapterPosition());
                            notifyItemRangeChanged(getAdapterPosition(),mArrayList.size());

//                            if(context instanceof ListCart){
//                                ((ListCart)context).UpdateTotalHarga();
//                            }

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
                }
            });
            btn_ubah.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    btn_kurang.setVisibility(View.VISIBLE);
                    btn_tambah.setVisibility(View.VISIBLE);
                    btn_ubah.setVisibility(View.GONE);
                    btn_centang.setVisibility(View.VISIBLE);
                }
            });
            btn_centang.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    btn_kurang.setVisibility(View.INVISIBLE);
                    btn_tambah.setVisibility(View.INVISIBLE);
                    btn_ubah.setVisibility(View.VISIBLE);
                    btn_centang.setVisibility(View.GONE);

                }
            });
        }


        @Override
        public void onClick(View view) {

        }
    }
}