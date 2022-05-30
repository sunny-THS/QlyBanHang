package com.example.qlybanhangonline;

import static com.example.qlybanhangonline.Constants.CUSTOM_PATH_PN;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.qlybanhangonline.database.DbHelper;
import com.example.qlybanhangonline.database.tbGioHang;
import com.example.qlybanhangonline.database.tbTaiKhoan;
import com.example.qlybanhangonline.database.tbThongTinTK;
import com.example.qlybanhangonline.fragment.frgApp.SanPhamFragment;
import com.example.qlybanhangonline.fragment.frgMenu.*;
import com.example.qlybanhangonline.obj.TaiKhoan;
import com.example.qlybanhangonline.obj.ThongTinTK;
import com.example.qlybanhangonline.obj.XuLy;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.qlybanhangonline.databinding.ActivityDrawBinding;
import com.makeramen.roundedimageview.RoundedImageView;

public class Draw extends AppCompatActivity implements XuLy {

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

//        binding.appBarDraw.fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, binding.appBarDraw.toolbar, R.string.nav_open, R.string.nav_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_home: {
                        if (currentFragment != R.id.nav_home)
                            replaceFragment(new HomeFragment());
                    }break;
                    case R.id.nav_lienHe: {
                        if (currentFragment != R.id.nav_lienHe)
                            replaceFragment(new LienHeFragment());
                    }break;
                    case R.id.nav_gioHang: {
                        tbGioHang gioHang = new tbGioHang(Draw.this);
                        if (gioHang.getGH().size() == 0)
                        {
                            replaceFragment(new GioHangRongFragment());
                        }else if (currentFragment != R.id.nav_gioHang)
                            replaceFragment(new GioHangFragment());
                    }break;
                    case R.id.nav_hoaDon: {
                        if (currentFragment != R.id.nav_hoaDon)
                            replaceFragment(new DonHangFragment());
                    }break;
                    case R.id.nav_exit: {
                        // onUpgrade db
                        dbHelper.onUpgrade(null, 0, 0);

                        finish();
                        startActivity(getIntent());
                    }break;
                    case R.id.nav_login: {
                        tbTaiKhoan taiKhoan = new tbTaiKhoan(Draw.this);
                        TaiKhoan tk = taiKhoan.getTK();
                        if (tk == null) {
                            Intent intent = new Intent(Draw.this, DangNhap.class);
                            startActivity(intent);
                        }
                    } break;
                    case R.id.nav_info: {
                        startActivity(new Intent(Draw.this, ThongTinTaiKhoan.class));
                    } break;
                }
                binding.appBarDraw.toolbar.setTitle(item.getTitle());
                binding.drawerLayout.closeDrawer(GravityCompat.START); // hiệu ứng đóng
                currentFragment = item.getItemId(); // gán cờ
                return true;
            }
        });

        // tài khoản người dùng
        View nav_header = navigationView.getHeaderView(0);
        img = nav_header.findViewById(R.id.imageViewNguoiDung);
        lbName = nav_header.findViewById(R.id.txtTenNguoiDung);

        MenuItem item_exit = navigationView.getMenu().findItem(R.id.nav_exit);
        MenuItem item_login = navigationView.getMenu().findItem(R.id.nav_login);
        MenuItem item_info = navigationView.getMenu().findItem(R.id.nav_info);
        MenuItem item_donHang = navigationView.getMenu().findItem(R.id.nav_hoaDon);

        tbTaiKhoan taiKhoan = new tbTaiKhoan(Draw.this);

        tbThongTinTK thongTinTK = new tbThongTinTK(Draw.this);
        ThongTinTK tttk = thongTinTK.getTTTK();

        TaiKhoan tk = taiKhoan.getTK();
        if (tk == null) {
            item_exit.setVisible(false);
            item_login.setVisible(true);
            item_info.setVisible(false);
            item_donHang.setVisible(false);
        }else {
            item_exit.setVisible(true);
            item_login.setVisible(false);
            item_donHang.setVisible(true);

            lbName.setText(tttk.getTen());

            // set ảnh đại diện
            Glide.with(Draw.this).load(tk.getImage())
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true).into(img);
        }

        // bắt intent giỏ hàng
        Intent intent = getIntent();
        boolean ckGioHang = intent.getBooleanExtra("messageIntentGioHang", false);
        if (!ckGioHang) {
            currentFragment = R.id.nav_home;
            replaceFragment(new HomeFragment());

        }else {
            currentFragment = R.id.nav_gioHang;
            replaceFragment(new GioHangFragment());
            binding.appBarDraw.toolbar.setTitle("Giỏ hàng");
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = binding.drawerLayout;
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }else
            super.onBackPressed();
    }

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction transition = getSupportFragmentManager().beginTransaction();
        transition.replace(R.id.FrameData, fragment);
        transition.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.draw, menu);

        MenuItem menuItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Tên sản phẩm");

        menuItem.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem menuItem) {
                replaceFragment(new SanPhamFragment(CUSTOM_PATH_PN + "sanphamrong"));
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem menuItem) {
                finish();
                startActivity(getIntent());
                return true;
            }
        });

        // xử lý sự kiện
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(Draw.this, "Tìm kiếm: " + query, Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                replaceFragment(new SanPhamFragment(CUSTOM_PATH_PN + newText));
                return false;
            }
        });


        return true;
    }

    @Override
    public void capNhatTongTien() {
        GioHangFragment gioHangFragment = (GioHangFragment) getSupportFragmentManager().findFragmentById(R.id.FrameData);
        gioHangFragment.capNhatTongTien();
    }

}
