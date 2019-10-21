package com.universedeveloper.mykopi_user.Api;

public class JSONResponse {
    private ModelMenu[] menu;

    public ModelMenu[] getMenu() {
        return menu;
    }

    private ModelMenu[] menumakanan;

    public ModelMenu[] getMenuMakanan() {
        return menumakanan;
    }

    private ModelProfilUser[] datauser;

    public ModelProfilUser[] getDatauser() {
        return datauser;
    }

    private ModelTransaksiUser[] transaksi;

    public ModelTransaksiUser[] getTransaksi() {
        return transaksi;
    }

    private ModelTransaksiTempat[] pesantempat;

    public ModelTransaksiTempat[] getPesantempat() {
        return pesantempat;
    }

    private ModelTransaksiMenu[] menu_transaksi;

    public ModelTransaksiMenu[] getMenu_transaksi() {
        return menu_transaksi;
    }
}