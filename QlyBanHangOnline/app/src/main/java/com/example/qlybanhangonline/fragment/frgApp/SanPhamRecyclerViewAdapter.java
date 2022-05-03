package com.example.qlybanhangonline.fragment.frgApp;

import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.qlybanhangonline.databinding.FragmentSanPhamBinding;
import com.example.qlybanhangonline.obj.CauHinh;
import com.example.qlybanhangonline.obj.SanPham;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class SanPhamRecyclerViewAdapter extends RecyclerView.Adapter<SanPhamRecyclerViewAdapter.ViewHolder> {

    private final List<SanPham> mValues;
    private final Context mContext;

    public SanPhamRecyclerViewAdapter(Context context, List<SanPham> items) {
        mValues = items;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(FragmentSanPhamBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        SanPham sanPham = mValues.get(position);

        // lấy hình ảnh từ server
        Glide.with(mContext).load(sanPham.getHinhAnh()).into(holder.mImgSP);

        // tên sp
        int textLen = sanPham.getTen().length();
        String tenSP = sanPham.getTen();
        holder.mTenSP.setText(textLen < 30 ? tenSP : String.format("%s...", tenSP.substring(0, 30)));

        // mô tả sản phẩm - 2 mô tả
        if (sanPham.getCauHinhs().size() > 0) {
            CauHinh c = sanPham.getCauHinhs().get(0);
            String nd1 = c.getMoTa();

            c = sanPham.getCauHinhs().get(1);
            String nd2 = c.getMoTa();


            String moTa = String.format("%s, %s", nd1, nd2);

            holder.mMoTa.setText(moTa);
        }

        // giá sản phẩm
        Locale localeVN = new Locale("vi", "VN");
        NumberFormat vn = NumberFormat.getInstance(localeVN);
        holder.mGia.setText(vn.format(sanPham.getGia()) + " VNĐ");
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mTenSP, mMoTa, mGia;
        private ImageView mImgSP;

        public ViewHolder(FragmentSanPhamBinding binding) {
            super(binding.getRoot());

            this.mTenSP = binding.itemName;
            this.mImgSP = binding.imgSP;
            this.mMoTa = binding.itemMoTa;
            this.mGia = binding.itemGia;
        }

    }
}