package com.example.qlybanhangonline;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DangKy extends AppCompatActivity implements View.OnClickListener {
    ImageButton btnDatePicker;
    EditText txtDate;
    RadioGroup btnRdio;
    EditText txtTenDangNhap, txtEmail, txtMK, txtSoDT;
    Button btnDangKy;
    ImageButton btnExit;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ky);
        mapping();
        btnDatePicker.setOnClickListener(this);

        ((RadioButton)btnRdio.getChildAt(0)).setChecked(true);
    }

    private void mapping() {
        btnDatePicker = findViewById(R.id.btn_date);
        txtDate = findViewById(R.id.in_date);
        btnRdio = findViewById(R.id.btnGender);
        txtTenDangNhap = findViewById(R.id.username);
        txtEmail = findViewById(R.id.email);
        txtMK = findViewById(R.id.password);
        txtSoDT = findViewById(R.id.phoned);
        btnDangKy = findViewById(R.id.signupbtn);
        btnExit = findViewById(R.id.btnQuayLai);

        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DangKy.this, DangNhap.class));
            }
        });

        btnDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(txtTenDangNhap.getText().toString().trim().equalsIgnoreCase("")) {
                    txtTenDangNhap.setError("Vui lòng nhập tên đăng nhập !");
                    txtTenDangNhap.requestFocus();
                    return;
                }
                if(txtEmail.getText().toString().trim().equalsIgnoreCase("")) {
                    txtEmail.setError("Vui lòng nhập email !");
                    txtEmail.requestFocus();
                    return;
                }
                //Kiểm tra định dạng email
                if(!Validate.check_Email(txtEmail.getText().toString()))
                {
                    txtEmail.setError("Vui lòng nhập email chính xác !");
                    txtEmail.requestFocus();
                    return;
                }

                if(txtMK.getText().toString().trim().equalsIgnoreCase("")) {
                    txtMK.setError("Vui lòng nhập mật khẩu !");
                    txtMK.requestFocus();
                    return;
                }
                if(txtSoDT.getText().toString().trim().equalsIgnoreCase("")) {
                    txtSoDT.setError("Vui lòng nhập số điện thoại !");
                    txtSoDT.requestFocus();
                    return;
                }
                //Kiểm tra định dạng sdt
                if(!Validate.check_SoDT(txtSoDT.getText().toString()))
                {
                    txtSoDT.setError("Vui lòng nhập số điện thoại chính xác !");
                    txtSoDT.requestFocus();
                    return;
                }

                if(txtDate.getText().toString().trim().equalsIgnoreCase("")) {
                    txtDate.setError("Vui lòng chọn ngày sinh !");
                    txtDate.requestFocus();
                    return;
                }
                //Kiểm tra định dạng ngày
                if(!Validate.check_Ngay(txtDate.getText().toString()))
                {
                    txtDate.setError("Ngày sinh sai định dạng");
                    txtDate.requestFocus();
                    return;
                }
                Toast.makeText(DangKy.this,"Đăng kí",Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onClick(View view) {
        // Get Current Date
        Calendar calendar= Calendar.getInstance();
        int Year = calendar.get(Calendar.YEAR);
        int Month = calendar.get(Calendar.MONTH);
        int Day = calendar.get(Calendar.DAY_OF_MONTH);
        switch (view.getId()) {
            case R.id.btn_date:
                DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyy");
                                Calendar chooseDate = Calendar.getInstance();
                                chooseDate.set(i,i1,i2);
                                String trDate = simpleDateFormat.format(chooseDate.getTime());
                                txtDate.setText(trDate);
                                txtDate.setError(null);
                            }
                        }, Year, Month, Day

                );
                datePickerDialog.show();
                break;
        }

    }
}