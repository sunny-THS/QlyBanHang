package com.example.qlybanhangonline;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.qlybanhangonline.databinding.ActivityThongTinDatHangBinding;
import com.example.qlybanhangonline.obj.SanPham;
import com.example.qlybanhangonline.obj.TaiKhoan;
import com.example.qlybanhangonline.obj.ThongTinTK;

import java.util.ArrayList;

public class ThongTinDatHang extends AppCompatActivity {

    private ActivityThongTinDatHangBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityThongTinDatHangBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Bundle bundle = getIntent().getExtras();
        ThongTinTK tk = (ThongTinTK) bundle.getSerializable("dataTK");
        ArrayList<SanPham> sp = (ArrayList<SanPham>) bundle.getSerializable("dataSP");
        double tongTine = (double) bundle.getSerializable("dataTongTien");

        binding.txtTenNguoiNhan.setText(tk.getTen());

        binding.btnQuayLai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        binding.btnXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}