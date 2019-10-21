package com.universedeveloper.mykopi_user.Api;

import com.google.gson.annotations.SerializedName;

public class ModelTransaksiUser {

    @SerializedName("id_transaksi") //ini yg ada di json
    private String id_transaksi;

    @SerializedName("waktu_pesan") //ini yg ada di json
    private String waktu_pesan;

    @SerializedName("id_user") //ini yg ada di json
    private String id_user;

    @SerializedName("nama_user") //ini yg ada di json
    private String nama_user;

    @SerializedName("telepon_user") //ini yg ada di json
    private String telepon_user;

    @SerializedName("tgl_pesanan") //ini yg ada di json
    private String tgl_pesanan;

    @SerializedName("jam_pesanan") //ini yg ada di json
    private String jam_pesanan;

    @SerializedName("catatan") //ini yg ada di json
    private String catatan;

    @SerializedName("grand_total") //ini yg ada di json
    private String grand_total;



    public String getId_transaksi() {
        return id_transaksi;
    }

    public void setId_transaksi(String id_transaksi) {
        this.id_transaksi = id_transaksi;
    }

    public String getWaktu_pesan() {
        return waktu_pesan;
    }

    public void setWaktu_pesan(String waktu_pesan) {
        this.waktu_pesan = waktu_pesan;
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public String getNama_user() {
        return nama_user;
    }

    public void setNama_user(String nama_user) {
        this.nama_user = nama_user;
    }

    public String getTelepon_user() {
        return telepon_user;
    }

    public void setTelepon_user(String telepon_user) {
        this.telepon_user = telepon_user;
    }

    public String getTgl_pesanan() {
        return tgl_pesanan;
    }

    public void setTgl_pesanan(String tgl_pesanan) {
        this.tgl_pesanan = tgl_pesanan;
    }

    public String getJam_pesanan() {
        return jam_pesanan;
    }

    public void setJam_pesanan(String jam_pesanan) {
        this.jam_pesanan = jam_pesanan;
    }

    public String getCatatan() {
        return catatan;
    }

    public void setCatatan(String catatan) {
        this.catatan = catatan;
    }

    public String getGrand_total() {
        return grand_total;
    }

    public void setGrand_total(String grand_total) {
        this.grand_total = grand_total;
    }
}
