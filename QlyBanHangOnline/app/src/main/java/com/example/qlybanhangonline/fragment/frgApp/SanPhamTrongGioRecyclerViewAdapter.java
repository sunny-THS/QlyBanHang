package com.example.qlybanhangonline.fragment.frgApp;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.qlybanhangonline.Draw;
import com.example.qlybanhangonline.R;
import com.example.qlybanhangonline.database.tbGioHang;
import com.example.qlybanhangonline.databinding.FragmentSpGioBinding;
import com.example.qlybanhangonline.fragment.frgMenu.GioHangFragment;
import com.example.qlybanhangonline.obj.SanPham;
import com.makeramen.roundedimageview.RoundedImageView;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class SanPhamTrongGioRecyclerViewAdapter extends RecyclerView.Adapter<SanPhamTrongGioRecyclerViewAdapter.ViewHolder> {

    private final ArrayList<SanPham> mValues;

    public SanPhamTrongGioRecyclerViewAdapter(ArrayList<SanPham> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(FragmentSpGioBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        SanPham sp = mValues.get(position);

        // tên sản phẩm
        holder.mItemTenSP.setText(sp.getTen());

        // số lượng sản phẩm
        holder.mSoLuong.setText(sp.getSoLuong() + "");
        holder.mItemTenSP.setTag(holder.mSoLuong.getText());

        // giá
        Locale localeVN = new Locale("vi", "VN");
        NumberFormat vn = NumberFormat.getInstance(localeVN);
        holder.mItemGia.setText(vn.format(sp.getGia()));

        // hình ảnh sản phẩm
        Glide.with(holder.v.getContext()).load(sp.getHinhAnh()).into(holder.mImage);

        holder.mSoLuong.setEnabled(false);

        // xử lý nút tăng giảm số lượng
        holder.btnTang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int soLuong = Integer.parseInt(holder.mSoLuong.getText().toString()) + 1;
                if (sp.getSoLuongTon() < soLuong) {
                    Toast.makeText(view.getContext(), "Số lượng tồn kho không đủ", Toast.LENGTH_SHORT).show();
                    return;
                }
                holder.mSoLuong.setText(soLuong + "");

                // cập nhật số lượng
                sp.setSoLuong(soLuong);
                tbGioHang gioHang = new tbGioHang(view.getContext());
                gioHang.update(sp);

                ((Draw)holder.v.getContext()).capNhatTongTien();
            }
        });
        holder.btnGiam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int soLuong = Integer.parseInt(holder.mSoLuong.getText().toString()) - 1;
                if (soLuong < 1)
                    return;
                holder.mSoLuong.setText(soLuong + "");
                sp.setSoLuong(soLuong);
                tbGioHang gioHang = new tbGioHang(view.getContext());
                gioHang.update(sp);

                ((Draw)holder.v.getContext()).capNhatTongTien();
            }
        });

        // xóa sản phẩm khỏi giỏ hàng
        holder.btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tbGioHang gioHang = new tbGioHang(holder.v.getContext());
                gioHang.delete(sp);
                if (gioHang.getGH().size() == 0) // giỏ hàng rỗng
                    view.getContext().startActivity(new Intent(view.getContext(), Draw.class));
                
                ((Draw)holder.v.getContext()).capNhatTongTien();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView mItemTenSP;
        public final TextView mItemGia;
        public final EditText mSoLuong;
        public final ImageView mImage;
        public final View v;
        public final RoundedImageView btnTang, btnGiam, btnClose;

        public ViewHolder(FragmentSpGioBinding binding) {
            super(binding.getRoot());

            mItemTenSP = binding.itemName;
            mItemGia = binding.itemGia;
            mSoLuong = binding.itemSoLuong;
            mImage = binding.imgSP;

            btnGiam = binding.btnGiam;
            btnTang = binding.btnTang;
            btnClose = binding.btnClose;

            v = binding.getRoot();
        }

    }
}