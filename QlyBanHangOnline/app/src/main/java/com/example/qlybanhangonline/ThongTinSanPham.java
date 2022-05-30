package com.example.qlybanhangonline;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.qlybanhangonline.database.tbGioHang;
import com.example.qlybanhangonline.databinding.ActivityThongTinSanPhamBinding;
import com.example.qlybanhangonline.fragment.frgApp.CauHinhFragment;
import com.example.qlybanhangonline.fragment.frgApp.SanPhamTrongGioFragment;
import com.example.qlybanhangonline.fragment.frgMenu.GioHangFragment;
import com.example.qlybanhangonline.obj.SanPham;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

public class ThongTinSanPham extends AppCompatActivity {

    private ActivityThongTinSanPhamBinding binding;
    private SanPham sanPham;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityThongTinSanPhamBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        sanPham = (SanPham) intent.getSerializableExtra("ThongTinChiTiet");

        binding.txtTenSP.setText(sanPham.getTen());

        Locale localeVN = new Locale("vi", "VN");
        NumberFormat vn = NumberFormat.getInstance(localeVN);
        binding.txtGia.setText(vn.format(sanPham.getGia()) + " VNĐ");

        binding.txtHang.setText(sanPham.getHangSP());

        // lấy hình ảnh từ server
        Glide.with(this).load(sanPham.getHinhAnh()).into(binding.imgSP);
        binding.imgSP.setTag(sanPham.getHinhAnh());


        // set tổng số lượng sản phẩm đang có
        binding.txtTenSP.setTag(sanPham.getSoLuong());

        replaceFragment(new CauHinhFragment(sanPham.getCauHinhs()));

        // xử lý nút quay lại
        binding.btnQuayLai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // thêm vào giỏ hàng
        binding.btnGioHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                themVaoGioHang();

                Toast.makeText(ThongTinSanPham.this, "Thêm vào giỏ hàng thành công!", Toast.LENGTH_SHORT).show();
            }
        });

        // mua sản phẩm x
        binding.btnMua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                themVaoGioHang();

                // chuyển đến giỏ hàng
                Intent intentMessageGioHang = new Intent(ThongTinSanPham.this, Draw.class);
                intentMessageGioHang.putExtra("messageIntentGioHang", true);
                startActivity(intentMessageGioHang);
            }
        });
    }

    private void themVaoGioHang() {
        tbGioHang gioHang = new tbGioHang(ThongTinSanPham.this);

        SanPham sp = new SanPham();
        sp.setTen(binding.txtTenSP.getText().toString());

        sp.setSoLuong(1); // set giá

        sp.setSoLuongTon((int) binding.txtTenSP.getTag());

        String gia = binding.txtGia.getText().toString();
        gia = gia.replace("VNĐ", " ");
        try {
            gia = NumberFormat.getNumberInstance(Locale.FRANCE).parse(gia.trim()).toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        sp.setGia(Double.parseDouble(gia.trim()));

        // hình ảnh
        sp.setHinhAnh(binding.imgSP.getTag().toString());

        gioHang.insert(sp);
    }

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction transition = getSupportFragmentManager().beginTransaction();
        transition.replace(R.id.cauHinh, fragment);
        transition.commit();
    }
}