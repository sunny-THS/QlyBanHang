package com.example.qlybanhang;

import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qlybanhang.databinding.FragmentChucNangBinding;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ChucNangRecyclerViewAdapter extends RecyclerView.Adapter<ChucNangRecyclerViewAdapter.ViewHolder> {

    private final ArrayList<ChucNang> mValues;

    public ChucNangRecyclerViewAdapter(ArrayList<ChucNang> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(FragmentChucNangBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        ChucNang cn = mValues.get(position);

        holder.mTen.setText(cn.getTenChucNang());
        holder.mImage.setImageResource(cn.getImageChucNang());

        holder.mView.setOnClickListener(e -> {
            switch (cn.getTenChucNang()) {
                case "Đơn hàng":
                {
                    e.getContext().startActivity(new Intent(e.getContext(), DonHang.class));

                }break;
                default: break;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView mImage;
        private TextView mTen;
        private View mView;
        public ViewHolder(FragmentChucNangBinding binding) {
            super(binding.getRoot());

            mView = binding.getRoot();
            mImage = binding.imageChucNang;
            mTen = binding.content;
        }
    }
}