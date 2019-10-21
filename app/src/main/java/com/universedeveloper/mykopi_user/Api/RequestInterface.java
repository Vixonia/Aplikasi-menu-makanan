package com.universedeveloper.mykopi_user.Api;

import android.support.v7.widget.RecyclerView;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RequestInterface {

   @GET("mykopi/user/list_menu.php")
    Call<JSONResponse> getMenu(@Query("kategori") String kategori);

    //insert data transaksi
    @FormUrlEncoded
    @POST("mykopi/user/insert_transaksi.php")
    Call<ValueMessage>insertTransaksi(@Field("id_transaksi") String id_transaksi,
                                      @Field("id_user") String id_user,
                                      @Field("nama_user") String nama_user,
                                      @Field("telepon_user") String telepon_user,
                                      @Field("tgl_pesanan") String tgl_pesanan,
                                      @Field("jam_pesanan") String jam_pesanan,
                                      @Field("catatan") String catatan,
                                      @Field("grand_total") String grand_total);

    //insert data transaksi
    @FormUrlEncoded
    @POST("mykopi/user/insert_menu_transaksi.php")
    Call<ValueMessage>insertMenuTransaksi(@Field("id_transaksi") String id_transaksi,
                                          @Field("menu_id") String menu_id,
                                          @Field("nama_menu") String nama_menu,
                                          @Field("gambar_menu") String gambar_menu,
                                          @Field("harga") String harga,
                                          @Field("jumlah") String jumlah);

 //detail profil
 @GET("mykopi/user/detail_user.php")
 Call<JSONResponse> getProfilUser(@Query("id_user") String id_user);

 //list transaksi user
 @GET("mykopi/user/list_transaksi_user.php")
 Call<JSONResponse> getTransaksi(@Query("id_user") String id_user);

 //list transaksi pesan tempat
 @GET("mykopi/user/list_pesan_tempat.php")
 Call<JSONResponse> getPesantempat(@Query("id_user") String id_user);

 //list transaksi pesan menu
 @GET("mykopi/user/list_menu_transaksi.php")
 Call<JSONResponse> getMenu_transaksi(@Query("id_transaksi") String id_transaksi);
}
