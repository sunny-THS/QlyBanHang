package com.example.qlybanhang;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.Dialog;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;

import com.example.qlybanhang.databinding.ActivityDonHangBinding;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DonHang extends AppCompatActivity implements XuLyDonHang, NumberPicker.OnValueChangeListener {

    private  final String[] strState = {
        "Chưa xác nhận",
        "Đang giao hàng",
        "Hoàn thành"
    };

    private static int year;

    ActivityDonHangBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDonHangBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        year = Calendar.getInstance().get(Calendar.YEAR);;

        replaceFragment(new DonHangFragment(year));

        binding.btnQuayLai.setOnClickListener(e -> finish());

        binding.btnFilter.setOnClickListener(e -> {
            show_year();
        });

        // xử lý button state
        binding.CN.setOnClickListener(e -> { // chưa xác nhận
            if (binding.CN.getTag() != null) {
                replaceFragment(new DonHangFragment(year));

                binding.CN.setTypeface(null, Typeface.NORMAL);
                binding.CN.setBackgroundResource(0);

                binding.CN.setTag(null);
                return;
            }

            binding.CN.setTag("cn");
            binding.GH.setTag(null);
            binding.HT.setTag(null);

            binding.GH.setTypeface(null, Typeface.NORMAL);
            binding.HT.setTypeface(null, Typeface.NORMAL);
            binding.CN.setTypeface(null, Typeface.BOLD_ITALIC);

            // xác định border
            binding.CN.setBackground(getDrawable(R.drawable.border_bottom_cn));
            binding.HT.setBackgroundResource(0);
            binding.GH.setBackgroundResource(0);

            replaceFragment(new DonHangStateFragment(strState[0], year));
        });

        binding.GH.setOnClickListener(e -> { // đang giao hàng
            if (binding.GH.getTag() != null) {
                replaceFragment(new DonHangFragment(year));

                binding.GH.setTypeface(null, Typeface.NORMAL);
                binding.GH.setBackgroundResource(0);

                binding.GH.setTag(null);
                return;
            }

            binding.GH.setTag("gh");
            binding.CN.setTag(null);
            binding.HT.setTag(null);

            binding.CN.setTypeface(null, Typeface.NORMAL);
            binding.HT.setTypeface(null, Typeface.NORMAL);
            binding.GH.setTypeface(null, Typeface.BOLD_ITALIC);

            // xác định border
            binding.GH.setBackground(getDrawable(R.drawable.border_bottom_gh));
            binding.HT.setBackgroundResource(0);
            binding.CN.setBackgroundResource(0);

            replaceFragment(new DonHangStateFragment(strState[1], year));
        });

        binding.HT.setOnClickListener(e -> { // hoàn thành
            if (binding.HT.getTag() != null) {
                replaceFragment(new DonHangFragment(year));

                binding.HT.setTypeface(null, Typeface.NORMAL);
                binding.HT.setBackgroundResource(0);

                binding.HT.setTag(null);
                return;
            }

            binding.HT.setTag("ht");
            binding.CN.setTag(null);
            binding.GH.setTag(null);

            binding.GH.setTypeface(null, Typeface.NORMAL);
            binding.CN.setTypeface(null, Typeface.NORMAL);
            binding.HT.setTypeface(null, Typeface.BOLD_ITALIC);

            // xác định border
            binding.HT.setBackground(getDrawable(R.drawable.border_bottom_ht));
            binding.GH.setBackgroundResource(0);
            binding.CN.setBackgroundResource(0);

            replaceFragment(new DonHangStateFragment(strState[2], year));
        });
    }

    @Override
    public void xuLyThongTin(Bundle bundle) {
        ArrayList<Integer> lstSoLuong = bundle.getIntegerArrayList("soLuongDH");

        binding.HT.setText(lstSoLuong.get(0).toString());
        binding.GH.setText(lstSoLuong.get(1).toString());
        binding.CN.setText(lstSoLuong.get(2).toString());

        Locale localeVN = new Locale("vi", "VN");
        NumberFormat vn = NumberFormat.getInstance(localeVN);
        binding.txtTongTien.setText(vn.format(bundle.getDouble("TongGiaTri")) + " VNĐ");
    }


    private void replaceFragment(Fragment fragment) {
        FragmentTransaction transition = getSupportFragmentManager().beginTransaction();
        transition.replace(R.id.viewTT, fragment);
        transition.commit();
    }

    public void show_year() {

        final Dialog d = new Dialog(DonHang.this);
        d.setTitle("NumberPicker");

        /////////////////////-------------------------------------------
        d.setContentView(R.layout.dialog);
        Button btnXacNhan = (Button) d.findViewById(R.id.btnXacNhan);
        Button btnCancel = (Button) d.findViewById(R.id.btnCancel);
        final NumberPicker np = (NumberPicker) d.findViewById(R.id.numberpicker);
        np.setMaxValue(2030);
        np.setMinValue(1990);
        np.setValue(year);
        np.setWrapSelectorWheel(false);
        np.setOnValueChangedListener(this);
        btnXacNhan.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                year = np.getValue();
                replaceFragment(new DonHangFragment(year));

                d.dismiss();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                d.dismiss();
            }
        });
        d.show();
    }

    @Override
    public void onValueChange(NumberPicker numberPicker, int i, int i1) {

    }
}