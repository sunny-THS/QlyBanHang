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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.qlybanhangonline.R;
import com.example.qlybanhangonline.obj.CauHinh;
import com.example.qlybanhangonline.obj.SanPham;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A fragment representing a list of Items.
 */
public class SanPhamFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;

    private String custom;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public SanPhamFragment() {
    }

    public SanPhamFragment(String custom) {
        this.custom = custom;
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static SanPhamFragment newInstance(int columnCount) {
        SanPhamFragment fragment = new SanPhamFragment();
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
        View view = inflater.inflate(R.layout.fragment_san_pham_list, container, false);

        RecyclerView recyclerView = (RecyclerView) view;
        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }

            String path = "/products/";
            String fetchUrl = this.getString(R.string.url) + path + custom; // url
            RequestQueue queue = Volley.newRequestQueue(getContext());

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
                                // id, ten, nsx, hinhAnh, hangSP, loaiSP, soLuong, gia, cauHinhs
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

                            // add to RecycleView
                            recyclerView.setAdapter(new SanPhamRecyclerViewAdapter( getContext(),sanPhams));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    },
                    vollyErr -> Toast.makeText(getContext(), vollyErr.getMessage(), Toast.LENGTH_SHORT).show()
            );
            queue.add(stringRequest);
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        DividerItemDecoration itemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        itemDecoration.setDrawable(getContext().getDrawable(R.drawable.divider));

        recyclerView.addItemDecoration(itemDecoration);

        return view;
    }
}