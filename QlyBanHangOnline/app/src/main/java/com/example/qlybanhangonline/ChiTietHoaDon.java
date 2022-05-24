package com.example.qlybanhangonline;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.qlybanhangonline.databinding.ActivityChiTietHoaDonBinding;
import com.example.qlybanhangonline.fragment.frgApp.ChiTietDanHangFragment;
import com.example.qlybanhangonline.fragment.frgApp.SanPhamRecyclerViewAdapter;
import com.example.qlybanhangonline.obj.CauHinh;
import com.example.qlybanhangonline.obj.SanPham;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class ChiTietHoaDon extends AppCompatActivity {

    private ActivityChiTietHoaDonBinding binding;
    private ArrayList<SanPham> lstSP = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityChiTietHoaDonBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        String idHD = intent.getStringExtra("idHD");

        binding.btnQuayLai.setOnClickListener(e -> finish());

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

                        binding.itemNgay.setText(String.format("Ngày lập: %s", jsonObject.getString("ngayLap")));

                        binding.itemTinhTrang.setText(jsonObject.getString("TrangThai"));
                        switch (jsonObject.getString("TrangThai")) {
                            case "Chưa xác nhận":
                                binding.itemTinhTrang.setBackgroundColor(Color.rgb(200,0,0)); break;
                            case "Đang giao hàng":
                                binding.itemTinhTrang.setBackgroundColor(Color.rgb(200,161,2)); break;
                            case "Hoàn thành":
                                binding.itemTinhTrang.setBackgroundColor(Color.rgb(2,200,91)); break;
                        }

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


    private void replaceFragment(Fragment fragment) {
        FragmentTransaction transition = getSupportFragmentManager().beginTransaction();
        transition.replace(R.id.lstSanPham, fragment);
        transition.commit();
    }
}