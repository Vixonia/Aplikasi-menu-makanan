package com.universedeveloper.mykopi_user.Api;

import com.google.gson.annotations.SerializedName;

public class ModelTransaksiMenu {

    @SerializedName("id_transaksi") //ini yg ada di json
    private String id_transaksi;

    @SerializedName("menu_id") //ini yg ada di json
    private String menu_id;

    @SerializedName("nama_menu") //ini yg ada di json
    private String nama_menu;

    @SerializedName("gambar_menu") //ini yg ada di json
    private String gambar_menu;

    @SerializedName("harga") //ini yg ada di json
    private String harga;

    @SerializedName("jumlah") //ini yg ada di json
    private String jumlah;



    public String getId_transaksi() {
        return id_transaksi;
    }

    public void setId_transaksi(String id_transaksi) {
        this.id_transaksi = id_transaksi;
    }

    public String getMenu_id() {
        return menu_id;
    }

    public void setMenu_id(String menu_id) {
        this.menu_id = menu_id;
    }

    public String getNama_menu() {
        return nama_menu;
    }

    public void setNama_menu(String nama_menu) {
        this.nama_menu = nama_menu;
    }

    public String getGambar_menu() {
        return gambar_menu;
    }

    public void setGambar_menu(String gambar_menu) {
        this.gambar_menu = gambar_menu;
    }

    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }

    public String getJumlah() {
        return jumlah;
    }

    public void setJumlah(String jumlah) {
        this.jumlah = jumlah;
    }
}
