package com.example.qlybanhangonline.fragment.frgMenu;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qlybanhangonline.DangNhap;
import com.example.qlybanhangonline.Draw;
import com.example.qlybanhangonline.R;
import com.example.qlybanhangonline.ThongTinDatHang;
import com.example.qlybanhangonline.database.tbGioHang;
import com.example.qlybanhangonline.database.tbTaiKhoan;
import com.example.qlybanhangonline.database.tbThongTinTK;
import com.example.qlybanhangonline.fragment.frgApp.SanPhamRecyclerViewAdapter;
import com.example.qlybanhangonline.fragment.frgApp.SanPhamTrongGioFragment;
import com.example.qlybanhangonline.fragment.frgApp.SanPhamTrongGioRecyclerViewAdapter;
import com.example.qlybanhangonline.obj.SanPham;
import com.example.qlybanhangonline.obj.TaiKhoan;
import com.example.qlybanhangonline.obj.ThongTinTK;
import com.example.qlybanhangonline.obj.XuLy;

import org.w3c.dom.Text;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class GioHangFragment extends Fragment implements XuLy {

    private Button btnThanhToan, btnMuaTiep;
    private TextView txtTongTien;

    public GioHangFragment() {
        // Required empty public constructor
    }

    public static GioHangFragment newInstance(String param1, String param2) {
        GioHangFragment fragment = new GioHangFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_gio_hang, container, false);
        btnThanhToan = v.findViewById(R.id.btnThanhToan_gh);
        btnMuaTiep = v.findViewById(R.id.btnMuaTiep);
        txtTongTien = v.findViewById(R.id.txtTongTien);

        tbGioHang tbGioHang = new tbGioHang(getContext());

        Locale localeVN = new Locale("vi", "VN");
        NumberFormat vn = NumberFormat.getInstance(localeVN);
        txtTongTien.setText(vn.format(tbGioHang.TongGia()) + " VNĐ");

        btnMuaTiep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), Draw.class));
            }
        });

        btnThanhToan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tbTaiKhoan taiKhoan = new tbTaiKhoan(view.getContext());
                TaiKhoan tk = taiKhoan.getTK();
                if (tk == null) {
                    Intent intent = new Intent(view.getContext(), DangNhap.class);
                    startActivity(intent);
                }else {
                    tbThongTinTK tbThongTinTK = new tbThongTinTK(view.getContext());
                    ThongTinTK thongTinTK = tbThongTinTK.getTTTK();

                    Bundle data = new Bundle();
                    data.putSerializable("dataTK", thongTinTK);
                    data.putSerializable("dataSP", tbGioHang.getGH());
                    data.putDouble("dataTongTien", tbGioHang.TongGia());

                    Intent intent = new Intent(view.getContext(), ThongTinDatHang.class);
                    intent.putExtras(data);

                    view.getContext().startActivity(intent);
                }
            }
        });
        return v;
    }

    @Override
    public void capNhatTongTien() {
        tbGioHang tbGioHang = new tbGioHang(getContext());

        Locale localeVN = new Locale("vi", "VN");
        NumberFormat vn = NumberFormat.getInstance(localeVN);
        txtTongTien.setText(vn.format(tbGioHang.TongGia()) + " VNĐ");

        SanPhamTrongGioFragment fragment = (SanPhamTrongGioFragment) getChildFragmentManager().findFragmentById(R.id.fragmentContainerView);
        fragment.recyclerView.setAdapter(new SanPhamTrongGioRecyclerViewAdapter(tbGioHang.getGH()));
    }
}