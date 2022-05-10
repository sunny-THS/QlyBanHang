package com.example.qlybanhangonline.fragment.frgApp;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.qlybanhangonline.databinding.FragmentCauHinhBinding;
import com.example.qlybanhangonline.obj.CauHinh;

import java.util.ArrayList;

public class CauHinhRecyclerViewAdapter extends RecyclerView.Adapter<CauHinhRecyclerViewAdapter.ViewHolder> {

    private final ArrayList<CauHinh> mValues;

    public CauHinhRecyclerViewAdapter(ArrayList<CauHinh> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(FragmentCauHinhBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        CauHinh c = mValues.get(position);

        holder.mIdView.setText(c.getTen());
        holder.mContentView.setText(c.getMoTa());
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView mIdView;
        public final TextView mContentView;

        public ViewHolder(FragmentCauHinhBinding binding) {
            super(binding.getRoot());
            mIdView = binding.itemNumber;
            mContentView = binding.content;
        }

    }
}