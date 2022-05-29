package com.example.qlybanhang;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.qlybanhang.databinding.FragmentChiTietDhBinding;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class ChiTietDanHangRecyclerViewAdapter extends RecyclerView.Adapter<ChiTietDanHangRecyclerViewAdapter.ViewHolder> {

    private final ArrayList<SanPham> mValues;

    public ChiTietDanHangRecyclerViewAdapter(ArrayList<SanPham> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(FragmentChiTietDhBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        SanPham sp = mValues.get(position);

        Locale localeVN = new Locale("vi", "VN");
        NumberFormat vn = NumberFormat.getInstance(localeVN);
        holder.txtGia.setText(vn.format(sp.getGia()) + " VNĐ");

        holder.txtTen.setText(String.format("%s - %d", sp.getTen(), sp.getSoLuong()));
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtTen, txtGia;

        public ViewHolder(FragmentChiTietDhBinding binding) {
            super(binding.getRoot());
            txtTen = binding.txtTenSP;
            txtGia = binding.txtGia;
        }

    }
}