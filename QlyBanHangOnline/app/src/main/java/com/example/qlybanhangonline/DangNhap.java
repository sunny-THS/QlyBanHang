package com.example.qlybanhangonline;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.qlybanhangonline.database.DbHelper;
import com.example.qlybanhangonline.database.tbTaiKhoan;
import com.example.qlybanhangonline.database.tbThongTinTK;
import com.example.qlybanhangonline.obj.TaiKhoan;
import com.example.qlybanhangonline.obj.ThongTinTK;
import com.google.android.material.button.MaterialButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

public class DangNhap extends AppCompatActivity {

    ImageButton btnExit;
    MaterialButton btnDk, btnDn;
    EditText txtTenDN, txtMatKhau;
    TextView fogetpass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap);

        btnExit = findViewById(R.id.btnQuayLai);
        btnDk = findViewById(R.id.btndk);
        txtTenDN = findViewById(R.id.username);
        txtMatKhau = findViewById(R.id.password);
        btnDn = findViewById(R.id.btndn);
        fogetpass = findViewById(R.id.fogetpass);

        fogetpass.setOnClickListener(e -> {

        });

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
                dangNhap(
                        txtTenDN.getText().toString().trim(), // username
                        txtMatKhau.getText().toString().trim() // pw
                );
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

    private void dangNhap(String pUsername, String pPw) {
        try {
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            String URL = this.getString(R.string.url) + "/auth/login";
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("username", pUsername);
            jsonBody.put("pw", pPw);
            final String requestBody = jsonBody.toString();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                res -> {
                    try {
                        JSONObject jsonObject = new JSONObject(res);

                        ThongTinTK thongTinTK = new ThongTinTK();
                        thongTinTK.setTen(jsonObject.getString("name"));
                        thongTinTK.setId(jsonObject.getString("_id"));
                        thongTinTK.setNgaySinh(jsonObject.getString("birdthDay"));
                        thongTinTK.setEmail(jsonObject.getString("email"));
                        thongTinTK.setGioiTinh(jsonObject.getBoolean("sex") ? "nam" : "nữ");
                        thongTinTK.setSdt(jsonObject.getString("phoneNumbers"));
                        thongTinTK.setUsername(jsonObject.getString("username"));
                        thongTinTK.setImage(jsonObject.getString("image"));

                        // lưu vào sqlite
                        // lưu thông tin tài khoản
                        tbTaiKhoan taiKhoan = new tbTaiKhoan(this);
                        taiKhoan.insert(new TaiKhoan(thongTinTK.getId(), thongTinTK.getUsername(), thongTinTK.getImage()));

                        tbThongTinTK tttk = new tbThongTinTK(this);
                        tttk.insert(thongTinTK);

                        // lưu thành công
                        finish();
                        startActivity(new Intent(DangNhap.this, Draw.class));
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

                        Toast.makeText(DangNhap.this, message, Toast.LENGTH_SHORT).show();

                        txtMatKhau.setText("");
                        txtTenDN.requestFocus();
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

//                @Override
//                protected Response<String> parseNetworkResponse(NetworkResponse response) {
//                    String responseString = "";
//                    if (response != null) {
//                        responseString = String.valueOf(response.data,"UTF-8");
//                        // can get more details such as response.headers
//                    }
//                    return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
//                }
            };

            requestQueue.add(stringRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}