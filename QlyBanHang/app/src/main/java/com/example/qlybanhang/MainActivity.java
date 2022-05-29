package com.example.qlybanhang;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.qlybanhang.database.DbHelper;
import com.example.qlybanhang.database.tbTaiKhoan;
import com.example.qlybanhang.database.tbThongTinTK;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class MainActivity extends AppCompatActivity {


    EditText txtName, txtPass;
    Button btnDangNhap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tbTaiKhoan taiKhoan = new tbTaiKhoan(this);
        TaiKhoan tk = taiKhoan.getTK();
        if (tk != null) {
            finish();
            startActivity(new Intent(MainActivity.this, Draw.class));
        }

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
                dangNhap(
                        txtName.getText().toString().trim(), // username
                        txtPass.getText().toString().trim() // pw
                );
            }
        });
    }
    private void dangNhap(String pUsername, String pPw) {
        try {
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            String URL = this.getString(R.string.url) + "/auth/admin";
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("username", pUsername);
            jsonBody.put("pw", pPw);
            final String requestBody = jsonBody.toString();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                    res -> {
                        try {
                            JSONObject jsonObject = new JSONObject(res);

                            ThongTinTK thongTinTK = new ThongTinTK();
                            thongTinTK.setId(jsonObject.getString("_id"));
                            thongTinTK.setUsername(jsonObject.getString("username"));
                            thongTinTK.setTen(jsonObject.getString("name"));

                            // lưu vào sqlite
                            // lưu thông tin tài khoản
                            tbTaiKhoan taiKhoan = new tbTaiKhoan(this);
                            taiKhoan.insert(new TaiKhoan(thongTinTK.getId(), thongTinTK.getUsername(), thongTinTK.getImage()));

                            tbThongTinTK tttk = new tbThongTinTK(this);
                            tttk.insert(thongTinTK);

                            // lưu thành công
                            finish();
                            startActivity(new Intent(MainActivity.this, Draw.class));
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

                            Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();

                            txtPass.setText("");
                            txtName.requestFocus();
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