package com.example.qlybanhang;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.qlybanhang.databinding.ActivityChiTietHoaDonBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class ChiTietHoaDon extends AppCompatActivity {

    private ActivityChiTietHoaDonBinding binding;
    private ArrayList<SanPham> lstSP = new ArrayList<>();

    private static String idHD;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityChiTietHoaDonBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        idHD = intent.getStringExtra("idHD");

        binding.btnQuayLai.setOnClickListener(e -> finish());

        binding.btnXacNhan.setOnClickListener(e -> {
            xacNhanDonHang(binding.btnXacNhan.getText().equals("Xác nhận"));
            finish();
            startActivity(getIntent());
        });

        callInfo(idHD);
    }

    private void callInfo(String idHD) {
        String path = "/bills/id/" + idHD;
        String fetchUrl = this.getString(R.string.url) + path; // url
        RequestQueue queue = Volley.newRequestQueue(ChiTietHoaDon.this);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, fetchUrl,
                res -> {
                    try {
                        JSONArray array = new JSONArray(res);
                        JSONObject jsonObject = array.getJSONObject(0);

                        binding.txtMaHoaDon.setText(jsonObject.getString("_id"));

                        Locale localeVN = new Locale("vi", "VN");
                        NumberFormat vn = NumberFormat.getInstance(localeVN);
                        binding.donGia.setText(vn.format(jsonObject.getDouble("donGia")) + " VNĐ");

                        binding.itemTenKH.setText(String.format("Khách hàng: %s", jsonObject.getString("tenKH")));

                        binding.itemNgay.setText(String.format("Ngày lập: %s", jsonObject.getString("ngayLap")));

                        binding.itemTinhTrang.setText(jsonObject.getString("TrangThai"));
                        switch (jsonObject.getString("TrangThai")) {
                            case "Chưa xác nhận":
                                binding.itemTinhTrang.setBackgroundColor(Color.rgb(200,0,0));
                                break;
                            case "Đang giao hàng":
                                binding.itemTinhTrang.setBackgroundColor(Color.rgb(200,161,2));
                                binding.btnXacNhan.setText("Hoàn tất");
                                binding.btnXacNhan.setBackgroundColor(Color.rgb(4,157,228));
                                break;
                            case "Hoàn thành":
                                binding.itemTinhTrang.setBackgroundColor(Color.rgb(2,200,91));
                                binding.btnXacNhan.setVisibility(View.GONE);
                                break;
                        }

                        //  load chi tiết từng sản phẩm trong hóa đơn
                        JSONArray jsonArray = jsonObject.getJSONArray("chiTietDonHang");

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject objectSP = jsonArray.getJSONObject(i);

                            SanPham sp = new SanPham();
                            sp.setTen(objectSP.getString("tenSP"));
                            sp.setSoLuong(objectSP.getInt("soLuong"));
                            sp.setGia(objectSP.getDouble("gia"));
                            lstSP.add(sp);
                        }


                        replaceFragment(new ChiTietDanHangFragment(lstSP));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                },
                vollyErr -> Toast.makeText(ChiTietHoaDon.this, vollyErr.getMessage(), Toast.LENGTH_SHORT).show()
        );
        queue.add(stringRequest);
    }

    private void xacNhanDonHang(boolean isXacNhan) {
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("id", idHD);
            if (!isXacNhan)
                jsonBody.put("trangThai", "x");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final String requestBody = jsonBody.toString();

        String path = "/bills/xacNhanDon";
        String fetchUrl = this.getString(R.string.url) + path; // url
        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, fetchUrl,
                res -> {
                    Toast.makeText(this, res, Toast.LENGTH_SHORT).show();
                },
                vollyErr -> {
                    if (vollyErr == null || vollyErr.networkResponse == null) {
                        return;
                    }

                    try {
                        String message = new String(vollyErr.networkResponse.data,"UTF-8");

                        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
                    } catch (UnsupportedEncodingException er) {
                        er.printStackTrace();
                    }
                }
        ){
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
        queue.add(stringRequest);
    }

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction transition = getSupportFragmentManager().beginTransaction();
        transition.replace(R.id.lstSanPham, fragment);
        transition.commit();
    }
}