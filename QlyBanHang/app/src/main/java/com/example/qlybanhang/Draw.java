package com.example.qlybanhang;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.qlybanhang.database.DbHelper;
import com.example.qlybanhang.database.tbTaiKhoan;
import com.example.qlybanhang.database.tbThongTinTK;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.qlybanhang.databinding.ActivityDrawBinding;
import com.makeramen.roundedimageview.RoundedImageView;

public class Draw extends AppCompatActivity {

    private ActivityDrawBinding binding;
    private int currentFragment;
    private SearchView searchView;
    private RoundedImageView img;
    private TextView lbName;
    private DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dbHelper = new DbHelper(Draw.this);

        binding = ActivityDrawBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarDraw.toolbar);

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, binding.appBarDraw.toolbar, R.string.nav_open, R.string.nav_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_home: case R.id.nav_gallery: {
                        startActivity(new Intent(Draw.this, DonHang.class));
                    }break;
                }
                binding.drawerLayout.closeDrawer(GravityCompat.START); // hiệu ứng đóng
                currentFragment = item.getItemId(); // gán cờ
                return true;
            }
        });
        // tài khoản người dùng
        View nav_header = navigationView.getHeaderView(0);
        lbName = nav_header.findViewById(R.id.txtTenNguoiDung);

        tbTaiKhoan taiKhoan = new tbTaiKhoan(Draw.this);

        tbThongTinTK thongTinTK = new tbThongTinTK(Draw.this);
        ThongTinTK tttk = thongTinTK.getTTTK();

        TaiKhoan tk = taiKhoan.getTK();
        lbName.setText(tttk.getTen());
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = binding.drawerLayout;
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }else
            super.onBackPressed();
    }

}