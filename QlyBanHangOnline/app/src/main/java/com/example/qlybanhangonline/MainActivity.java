package com.example.qlybanhangonline;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String path = "/products";
        String fetchUrl = R.string.url + path;
        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, fetchUrl,
                res -> {
                    ArrayList<SanPham> sanPhams = new ArrayList<>(); // lưu thông tin sản phẩm
                    try {
                        JSONArray jsonArray = new JSONArray(res);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject objectSP = jsonArray.getJSONObject(i);
                            // save cấu hình
                            ArrayList<CauHinh> cauHinhs = new ArrayList<>();
                            JSONArray arrayCauHinh = objectSP.getJSONArray("cauHinh");
                            for (int j = 0; j < arrayCauHinh.length(); j++) {
                                JSONObject objectCauHinh = arrayCauHinh.getJSONObject(j);
                                cauHinhs.add(new CauHinh(
                                        objectCauHinh.getString("tenCH"),
                                        objectCauHinh.getString("moTaCH")
                                ));
                            }

                            // thêm thông tin 1 sp
//                            id, ten, nsx, hinhAnh, hangSP, loaiSP, soLuong, gia, cauHinhs
                            sanPhams.add(new SanPham(
                                    objectSP.getString("_id"),
                                    objectSP.getString("ten"),
                                    "",
                                    objectSP.getString("hinhAnh"),
                                    objectSP.getString("hangSP"),
                                    objectSP.getString("loaiSP"),
                                    objectSP.getInt("soLuong"),
                                    objectSP.getDouble("gia"),
                                    cauHinhs
                            ));
                        }
                        SanPhamAdapter sanPhamAdapter = new SanPhamAdapter(this, sanPhams);
                        // add to RecycleView

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                },
                vollyErr -> Toast.makeText(this, vollyErr.getMessage(), Toast.LENGTH_SHORT).show()
        );
        queue.add(stringRequest);
    }
}