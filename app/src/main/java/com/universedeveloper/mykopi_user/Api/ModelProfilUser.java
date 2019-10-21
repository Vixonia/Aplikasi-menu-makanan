package com.universedeveloper.mykopi_user.Api;

import com.google.gson.annotations.SerializedName;

public class ModelProfilUser {
    @SerializedName("id_user") //ini yg ada di json
    private String id_user;

    @SerializedName("nama_user") //ini yg ada di json
    private String nama_user;

    @SerializedName("email_user") //ini yg ada di json
    private String email_user;

    @SerializedName("password") //ini yg ada di json
    private String password;

    @SerializedName("telepon_user") //ini yg ada di json
    private String telepon_user;

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

    public String getEmail_user() {
        return email_user;
    }

    public void setEmail_user(String email_user) {
        this.email_user = email_user;
    }

    public String getTelepon_user() {
        return telepon_user;
    }

    public void setTelepon_user(String telepon_user) {
        this.telepon_user = telepon_user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
