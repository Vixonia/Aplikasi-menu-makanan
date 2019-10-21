package com.universedeveloper.mykopi_user;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.universedeveloper.mykopi_user.Beranda.FragmentBeranda;
import com.universedeveloper.mykopi_user.Nampan.Nampanku;
import com.universedeveloper.mykopi_user.PesanTempat.FragmentPesanTempat;
import com.universedeveloper.mykopi_user.Akun.FragmentAkun;

public class MainActivity extends AppCompatActivity {

    ActionBar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = getSupportActionBar();

        Fragment fragment;
        toolbar.setTitle("Beranda");
        fragment = new FragmentBeranda();
        loadFragment(fragment);
        //return true;

        //mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.navigation_beranda:
                    toolbar.setTitle("Beranda");
                    fragment = new FragmentBeranda();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_pesantempat:
                    toolbar.setTitle("Pesan Tempat");
                    fragment = new FragmentPesanTempat();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_nampanku:
                    toolbar.setTitle("Nampanku");
                    fragment = new Nampanku();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_akun:
                    toolbar.setTitle("Akun");
                    fragment = new FragmentAkun();
                    loadFragment(fragment);
                    return true;

            }
            return false;
        }
    };

    private void loadFragment(Fragment fragment) {
        Bundle bundle = new Bundle();
        // bundle.putString("kode", kode);
        //bundle.putString("key", key);
        fragment.setArguments(bundle);
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        //transaction.addToBackStack(null);
        transaction.commit();

    }
}