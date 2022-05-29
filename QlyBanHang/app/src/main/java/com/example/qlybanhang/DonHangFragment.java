package com.example.qlybanhang;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

/**
 * A fragment representing a list of Items.
 */
public class DonHangFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    RecyclerView recyclerView;
    private int year;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public DonHangFragment(int pYear) {
        year = pYear;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_don_hang_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }

            JSONObject jsonBody = new JSONObject();
            try {
                jsonBody.put("year", year);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            final String requestBody = jsonBody.toString();

            String path = "/bills";
            String fetchUrl = this.getString(R.string.url) + path; // url
            RequestQueue queue = Volley.newRequestQueue(getContext());

            StringRequest stringRequest = new StringRequest(Request.Method.POST, fetchUrl,
                    res -> {
                        ArrayList<HoaDon > hoaDons = new ArrayList<>(); // lưu thông tin sản phẩm
                        try {
                            JSONObject jsonObject = new JSONObject(res);

                            ArrayList<Integer> lstSoLuong = new ArrayList<Integer>();
                            lstSoLuong.add(jsonObject.getInt("HT")); // hoàn thành
                            lstSoLuong.add(jsonObject.getInt("GH")); // ddang giao hàng
                            lstSoLuong.add(jsonObject.getInt("CN")); // chưa xác nhận

                            Bundle pgkData = new Bundle();
                            pgkData.putIntegerArrayList("soLuongDH", lstSoLuong);
                            pgkData.putDouble("TongGiaTri", jsonObject.getDouble("totalBills"));

                            ((DonHang)getContext()).xuLySoLuong(pgkData);


                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject objectHD = jsonArray.getJSONObject(i);
                                hoaDons.add(new HoaDon(
                                        objectHD.getString("_id"),
                                        objectHD.getString("ngayLap"),
                                        objectHD.getDouble("donGia"),
                                        objectHD.getString("TrangThai")
                                ));
                            }

                            // add to RecycleView
                            recyclerView.setAdapter(new DonHangRecyclerViewAdapter(hoaDons));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    },
                    vollyErr -> Toast.makeText(getContext(), vollyErr.getMessage(), Toast.LENGTH_SHORT).show()
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
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        DividerItemDecoration itemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);

        recyclerView.addItemDecoration(itemDecoration);
        return view;
    }
}