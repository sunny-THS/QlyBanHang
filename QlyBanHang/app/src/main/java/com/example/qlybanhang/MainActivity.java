package com.example.qlybanhang;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    EditText txtName, txtPass;
    Button btnDangNhap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtName = findViewById(R.id.username);
        txtPass = findViewById(R.id.password);
        btnDangNhap = findViewById(R.id.btndn);
        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(txtName.getText().toString().trim().equalsIgnoreCase("")) {
                    txtName.setError("Vui lòng nhập tên đăng nhập !");
                    txtName.requestFocus();
                    return;
                }
                if(txtPass.getText().toString().trim().equalsIgnoreCase("")) {
                    txtPass.setError("Vui lòng nhập mật khẩu !");
                    txtPass.requestFocus();
                    return;
                }
            }
        });
    }
}