package com.universedeveloper.mykopi_user.Api;

import com.google.gson.annotations.SerializedName;

public class ModelTransaksiTempat {
    @SerializedName("tanggal_pesan") //ini yg ada di json
    private String tanggal_pesan;

    @SerializedName("jam_pesan") //ini yg ada di json
    private String jam_pesan;

    @SerializedName("nomor_meja") //ini yg ada di json
    private String nomor_meja;

    @SerializedName("id_user") //ini yg ada di json
    private String id_user;

    @SerializedName("nama_user") //ini yg ada di json
    private String nama_user;

    @SerializedName("email_user") //ini yg ada di json
    private String email_user;

    @SerializedName("telepon_user") //ini yg ada di json
    private String telepon_user;


    public String getTanggal_pesan() {
        return tanggal_pesan;
    }

    public void setTanggal_pesan(String tanggal_pesan) {
        this.tanggal_pesan = tanggal_pesan;
    }

    public String getJam_pesan() {
        return jam_pesan;
    }

    public void setJam_pesan(String jam_pesan) {
        this.jam_pesan = jam_pesan;
    }

    public String getNomor_meja() {
        return nomor_meja;
    }

    public void setNomor_meja(String nomor_meja) {
        this.nomor_meja = nomor_meja;
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public String getTelepon_user() {
        return telepon_user;
    }

    public void setTelepon_user(String telepon_user) {
        this.telepon_user = telepon_user;
    }

    public String getEmail_user() {
        return email_user;
    }

    public void setEmail_user(String email_user) {
        this.email_user = email_user;
    }

    public String getNama_user() {
        return nama_user;
    }

    public void setNama_user(String nama_user) {
        this.nama_user = nama_user;
    }

}
