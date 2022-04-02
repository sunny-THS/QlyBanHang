package com.example.qlybanhangonline;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SanPhamAdapter extends RecyclerView.Adapter<SanPhamAdapter.SanPhanViewHolder> {
    Context context;
    ArrayList<SanPham> data;

    public SanPhamAdapter(Context context, ArrayList<SanPham> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public SanPhanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.activity_layout_sp, parent, false);
        return new SanPhanViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull SanPhanViewHolder holder, int position) {
        SanPham sp = data.get(position);

        // thiết lập data lên view tương ứng
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class SanPhanViewHolder extends RecyclerView.ViewHolder {
        public SanPhanViewHolder(@NonNull View itemView) {
            super(itemView);
            // khởi tạo các view tương ứng trong activity_layout_sp

        }
    }
}
