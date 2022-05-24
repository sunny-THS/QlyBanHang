package com.example.qlybanhangonline;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.qlybanhangonline.database.DbHelper;
import com.example.qlybanhangonline.databinding.ActivityThongTinDatHangBinding;
import com.example.qlybanhangonline.obj.SanPham;
import com.example.qlybanhangonline.obj.ThongTinTK;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

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
                if (binding.diaChi.getText().toString().trim().length() == 0)
                {
                    Toast.makeText(ThongTinDatHang.this, "Vui lòng nhập địa chỉa giao hàng", Toast.LENGTH_SHORT).show();
                    return;
                }

                // thực hiện thanh toán
                thanhToan(tk, sp);
            }
        });
    }

    private void thanhToan(ThongTinTK pTk, ArrayList<SanPham> pChiTietDonHang) {
        try {
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            String URL = this.getString(R.string.url) + "/products/buy";

            // info user
            JSONObject jsonBody_user = new JSONObject();
            jsonBody_user.put("username", pTk.getUsername());
            jsonBody_user.put("email", pTk.getEmail());
            jsonBody_user.put("phoneNumbers", pTk.getSdt());
            jsonBody_user.put("id", pTk.getId());

            // đơn hàng
            JSONArray jsonArray_donHang = new JSONArray();
            pChiTietDonHang.forEach(donHang -> {
                JSONObject json_donHang = new JSONObject();
                try { // thêm thông tin từng sản phẩm trong giỏ hàng
                    json_donHang.put("tenSP", donHang.getTen());
                    json_donHang.put("soLuong", donHang.getSoLuong());
                    json_donHang.put("gia", donHang.getGia());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                // thêm vào mảng
                jsonArray_donHang.put(json_donHang);
            });

            JSONObject jsonBody = new JSONObject();
            jsonBody.put("user", jsonBody_user);
            jsonBody.put("diaChi", binding.diaChi.getText().toString().trim());
            jsonBody.put("chiTietDonHang", jsonArray_donHang);

            final String requestBody = jsonBody.toString();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                    res -> {
                        try {
                            JSONObject jsonObject = new JSONObject(res);
                            // đặt hàng thành công
                            Toast.makeText(this, "Đặt hàng thành công", Toast.LENGTH_SHORT).show();
                            // xóa giỏ hàng
                            DbHelper dbHelper = new DbHelper(ThongTinDatHang.this);
                            dbHelper.reloadTbGioHang();

                            finish();
                            startActivity(new Intent(ThongTinDatHang.this, Draw.class)); // chuyển về trang chủ
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

                            Toast.makeText(ThongTinDatHang.this, message, Toast.LENGTH_SHORT).show();

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