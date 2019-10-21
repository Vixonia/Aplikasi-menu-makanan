package com.universedeveloper.mykopi_user;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.universedeveloper.mykopi_user.Api.ModelMenu;

import java.util.ArrayList;
import java.util.List;

public class DataHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "cart.db";
    private static final String TABLE_NAME = "tbl_menu_transaksi";
    private static final String NO = "no";
    private static final String ID_TRANSAKSI = "id_transaksi";
    private static final String MENU_ID = "menu_id";
    private static final String NAMA_MENU = "nama_menu";
    private static final String GAMBAR_MENU = "gambar_menu";
    private static final String HARGA = "harga";
    private static final String JUMLAH = "jumlah";
    private static final String TOTAL = "total";
    String sql;
    private static final int DATABASE_VERSION = 1;


    public DataHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        sql = "create table "+TABLE_NAME+"("+NO+" integer primary key AUTOINCREMENT, "+ID_TRANSAKSI+" text null, "+MENU_ID+" text null, "+NAMA_MENU+" text null, "+GAMBAR_MENU+" text null, "+HARGA+" text null, "+JUMLAH+" text null, "+TOTAL+" integer null);";
        Log.d("Table_list", "onCreate: " + sql);
        db.execSQL(sql);
        //sql = "INSERT INTO mahasiswa (no, nama, tgl, jk, jurusan) VALUES ('1001', 'codeborneo', '1994-13-11', 'Laki-laki','teknik sipil');";
        //db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME+";");
        onCreate(db);
    }

    public void insertdata(String id_transaksi, String menu_id, String nama_menu, String gambar_menu, String harga, String jumlah){
        System.out.print("Hello " + sql);
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(ID_TRANSAKSI, id_transaksi);
        contentValues.put(MENU_ID, menu_id);
        contentValues.put(NAMA_MENU, nama_menu);
        contentValues.put(GAMBAR_MENU, gambar_menu);
        contentValues.put(HARGA, harga);
        contentValues.put(JUMLAH, jumlah);
        contentValues.put(String.valueOf(TOTAL), Double.parseDouble(harga) * Integer.parseInt(jumlah));
        db.insert(TABLE_NAME,null,contentValues);
    }

    public List<ModelMenu> getData(){
        List<ModelMenu> data = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from "+TABLE_NAME+" ;", null);
        StringBuffer stringBuffer = new StringBuffer();
        ModelMenu modelMenu = null;
        while (cursor.moveToNext()){
            modelMenu = new ModelMenu();
            String id_transaksi = cursor.getString(cursor.getColumnIndexOrThrow("id_transaksi"));
            String menu_id = cursor.getString(cursor.getColumnIndexOrThrow("menu_id"));
            String nama_menu = cursor.getString(cursor.getColumnIndexOrThrow("nama_menu"));
            String gambar_menu = cursor.getString(cursor.getColumnIndexOrThrow("gambar_menu"));
            String harga = cursor.getString(cursor.getColumnIndexOrThrow("harga"));
            String jumlah = cursor.getString(cursor.getColumnIndexOrThrow("jumlah"));
            Integer total = cursor.getInt(cursor.getColumnIndexOrThrow("total"));
            modelMenu.setId_transaksi(id_transaksi);
            modelMenu.setMenu_id(menu_id);
            modelMenu.setNama_menu(nama_menu);
            modelMenu.setGambar_menu(gambar_menu);
            modelMenu.setHarga(harga);
            modelMenu.setJumlah(jumlah);
            modelMenu.setTotal(total);
            stringBuffer.append(modelMenu);
            data.add(modelMenu);
        }
        for (ModelMenu mo:data){
            Log.i("Menunya",""+mo.getNama_menu());
        }
        return data;
    }
    public void updatedata(Integer total, String jumlah, String menu_id){
        System.out.print("Hello " + sql);
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        db.execSQL("update "+ TABLE_NAME + " set  total='" +
                total + "', jumlah='" +
                jumlah + "' where menu_id='" +
                menu_id+"'");
    }

}
