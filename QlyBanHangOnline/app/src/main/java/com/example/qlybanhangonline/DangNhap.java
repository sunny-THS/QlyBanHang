package com.example.qlybanhangonline;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.android.material.button.MaterialButton;

public class DangNhap extends AppCompatActivity {

    ImageButton btnExit;
    MaterialButton btnDk, btnDn;
    EditText txtTenDN, txtMatKhau;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap);

        btnExit = findViewById(R.id.btnQuayLai);
        btnDk = findViewById(R.id.btndk);
        txtTenDN = findViewById(R.id.username);
        txtMatKhau = findViewById(R.id.password);
        btnDn = findViewById(R.id.btndn);
        btnDn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(txtTenDN.getText().toString().trim().equalsIgnoreCase("")) {
                    txtTenDN.setError("Vui lòng nhập tên đăng nhập !");
                    txtTenDN.requestFocus();
                    return;
                }
                if(txtMatKhau.getText().toString().trim().equalsIgnoreCase("")) {
                    txtMatKhau.setError("Vui lòng nhập mật khẩu !");
                    txtMatKhau.requestFocus();
                    return;
                }
            }
        });
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DangNhap.this, Draw.class));
            }
        });

        btnDk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DangNhap.this, DangKy.class));
            }
        });

    }
}