package com.example.qlybanhangonline.fragment.frgApp;

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
import com.example.qlybanhangonline.R;
import com.example.qlybanhangonline.database.tbThongTinTK;
import com.example.qlybanhangonline.obj.CauHinh;
import com.example.qlybanhangonline.obj.HoaDon;
import com.example.qlybanhangonline.obj.SanPham;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

/**
 * A fragment representing a list of Items.
 */
public class HoaDonFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;

    public RecyclerView recyclerView;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public HoaDonFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static HoaDonFragment newInstance(int columnCount) {
        HoaDonFragment fragment = new HoaDonFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
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
        View view = inflater.inflate(R.layout.fragment_hoa_don_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }

            tbThongTinTK thongTinTK = new tbThongTinTK(getContext());

            JSONObject jsonBody = new JSONObject();
            try {
                jsonBody.put("username", thongTinTK.getTTTK().getTen());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            final String requestBody = jsonBody.toString();

            String path = "/bills/us";
            String fetchUrl = this.getString(R.string.url) + path; // url
            RequestQueue queue = Volley.newRequestQueue(getContext());

            StringRequest stringRequest = new StringRequest(Request.Method.POST, fetchUrl,
                    res -> {
                        ArrayList<HoaDon> hoaDons = new ArrayList<>(); // lưu thông tin sản phẩm
                        try {
                            JSONArray jsonArray = new JSONArray(res);
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
                            recyclerView.setAdapter(new HoaDonRecyclerViewAdapter(hoaDons));

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