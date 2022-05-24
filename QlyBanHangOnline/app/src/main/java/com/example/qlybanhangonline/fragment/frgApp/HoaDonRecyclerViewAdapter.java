package com.example.qlybanhangonline.fragment.frgApp;

import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qlybanhangonline.ChiTietHoaDon;
import com.example.qlybanhangonline.Draw;
import com.example.qlybanhangonline.databinding.FragmentHoaDonBinding;
import com.example.qlybanhangonline.obj.HoaDon;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class HoaDonRecyclerViewAdapter extends RecyclerView.Adapter<HoaDonRecyclerViewAdapter.ViewHolder> {

    private final ArrayList<HoaDon> mValues;

    public HoaDonRecyclerViewAdapter(ArrayList<HoaDon> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(FragmentHoaDonBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        HoaDon hd = mValues.get(position);

        holder.mId.setText(String.format("Đơn hàng %d", position + 1));

        Locale localeVN = new Locale("vi", "VN");
        NumberFormat vn = NumberFormat.getInstance(localeVN);
        holder.mGia.setText(vn.format(hd.getDonGia()) + " VNĐ");

        holder.mNgay.setText(hd.getNgayLap());

        holder.mTrangThai.setText(hd.getTrangThai());
        switch (hd.getTrangThai()) {
            case "Chưa xác nhận":
                holder.mTrangThai.setBackgroundColor(Color.rgb(200,0,0)); break;
            case "Đang giao hàng":
                holder.mTrangThai.setBackgroundColor(Color.rgb(200,161,2)); break;
            case "Hoàn thành":
                holder.mTrangThai.setBackgroundColor(Color.rgb(2,200,91)); break;
        }

        holder.v.setOnClickListener(e -> {
            Intent intent = new Intent(e.getContext(), ChiTietHoaDon.class);
            intent.putExtra("idHD", hd.getId());
            e.getContext().startActivity(intent);
            Toast.makeText(e.getContext(), hd.getId(), Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView mId;
        public final TextView mGia;
        public final TextView mNgay;
        public final TextView mTrangThai;
        public final View v;

        public ViewHolder(FragmentHoaDonBinding binding) {
            super(binding.getRoot());

            mId = binding.itemId;
            mGia = binding.donGia;
            mNgay = binding.itemNgay;
            mTrangThai = binding.itemTinhTrang;
            v = binding.getRoot();
        }
    }
}