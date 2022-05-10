package com.example.qlybanhangonline.fragment.frgApp;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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

        holder.mId.setText(hd.getId());

        Locale localeVN = new Locale("vi", "VN");
        NumberFormat vn = NumberFormat.getInstance(localeVN);
        holder.mGia.setText(vn.format(hd.getDonGia()) + " VNĐ");


        holder.mNgay.setText(hd.getNgayLap());
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView mId;
        public final TextView mGia;
        public final TextView mNgay;
        public final View v;

        public ViewHolder(FragmentHoaDonBinding binding) {
            super(binding.getRoot());

            mId = binding.itemId;
            mGia = binding.donGia;
            mNgay = binding.itemNgay;
            v = binding.getRoot();
        }
    }
}