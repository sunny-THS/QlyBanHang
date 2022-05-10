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

import com.example.qlybanhangonline.Draw;
import com.example.qlybanhangonline.R;
import com.example.qlybanhangonline.database.tbGioHang;
import com.example.qlybanhangonline.fragment.frgMenu.GioHangFragment;
import com.example.qlybanhangonline.fragment.frgMenu.GioHangRongFragment;

/**
 * A fragment representing a list of Items.
 */
public class SanPhamTrongGioFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    public RecyclerView recyclerView;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public SanPhamTrongGioFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static SanPhamTrongGioFragment newInstance(int columnCount) {
        SanPhamTrongGioFragment fragment = new SanPhamTrongGioFragment();
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
        View view = inflater.inflate(R.layout.fragment_sp_gio_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }

            tbGioHang gioHang = new tbGioHang(context);

            recyclerView.setAdapter(new SanPhamTrongGioRecyclerViewAdapter(gioHang.getGH()));
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        DividerItemDecoration itemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);

        recyclerView.addItemDecoration(itemDecoration);
        return view;
    }
}