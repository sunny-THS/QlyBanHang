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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.qlybanhangonline.database.tbTaiKhoan;
import com.example.qlybanhangonline.database.tbThongTinTK;
import com.example.qlybanhangonline.obj.TaiKhoan;
import com.example.qlybanhangonline.obj.ThongTinTK;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DangKy extends AppCompatActivity implements View.OnClickListener {
    ImageButton btnDatePicker;
    EditText txtDate;
    RadioGroup btnRdio;
    EditText txtTenDangNhap, txtEmail, txtMK, txtSoDT, txtHoTen;
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
        txtHoTen = findViewById(R.id.hoTen);
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
                if(txtHoTen.getText().toString().trim().equalsIgnoreCase("")) {
                    txtHoTen.setError("Vui lòng nhập họ tên!");
                    txtHoTen.requestFocus();
                    return;
                }
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
                ThongTinTK thongTinTK = new ThongTinTK();
                thongTinTK.setUsername(txtTenDangNhap.getText().toString().trim());
                thongTinTK.setPassword(txtMK.getText().toString().trim());
                thongTinTK.setTen(txtHoTen.getText().toString().trim());
                thongTinTK.setSdt(txtSoDT.getText().toString().trim());
                thongTinTK.setGioiTinh(((RadioButton)btnRdio.getChildAt(0)).isChecked() ? "nam" : "nữ");
                thongTinTK.setNgaySinh(txtDate.getText().toString().trim());
                thongTinTK.setEmail(txtEmail.getText().toString().trim());

                dangKy(thongTinTK);
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

    private void dangKy(ThongTinTK thongTinTK) {
        try {
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            String URL = this.getString(R.string.url) + "/auth/register";

            JSONObject jsonBody = new JSONObject();
            jsonBody.put("username", thongTinTK.getUsername());
            jsonBody.put("pw", thongTinTK.getPassword());
            jsonBody.put("name", thongTinTK.getTen());
            jsonBody.put("email", thongTinTK.getEmail());
            jsonBody.put("birdthDay", thongTinTK.getNgaySinh());
            jsonBody.put("sex", thongTinTK.getGioiTinh().equals("nam"));
            jsonBody.put("phoneNumbers", thongTinTK.getSdt());

            final String requestBody = String.format("{\"user\":%s}",jsonBody.toString());

            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                    res -> {
                        try {
                            JSONObject jsonObject = new JSONObject(res);
                            Toast.makeText(this, res, Toast.LENGTH_SHORT).show();

                            // lưu thành công
                            finish();
                            startActivity(new Intent(DangKy.this, DangNhap.class));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    },
                    volleyErr -> {
                        if (volleyErr == null || volleyErr.networkResponse == null) {
                            return;
                        }

                        try {
                            String message = new String(volleyErr.networkResponse.data,"UTF-8");

                            Toast.makeText(DangKy.this, message, Toast.LENGTH_SHORT).show();

                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    }) {
                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }

                @Override
                public byte[] getBody() throws AuthFailureError {
                    try {
                        return requestBody == null ? null : requestBody.getBytes("utf-8");
                    } catch (UnsupportedEncodingException uee) {
                        VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                        return null;
                    }
                }
            };

            requestQueue.add(stringRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}