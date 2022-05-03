package com.example.qlybanhangonline;

import static com.example.qlybanhangonline.Constants.CUSTOM_PATH_PT;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.qlybanhangonline.fragment.frgApp.SanPhamFragment;

public class ViewPagerAdapter_DienThoai extends FragmentStatePagerAdapter {

    public ViewPagerAdapter_DienThoai(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title;
        switch (position) {
            case 0: // Android
                title = "Android"; break;
            case 1: // iPhone(iOS)
                title = "iPhone(iOS)"; break;
            case 2: // Điện thoại phổ thông
                title = "Điện thoại phổ thông"; break;
            default: title = "Android"; break;
        }
        return title;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        String loaiSP = null;
        switch (position) {
            case 0: // Android
                loaiSP = CUSTOM_PATH_PT + "Android"; break;
            case 1: // iPhone(iOS)
                loaiSP = CUSTOM_PATH_PT + "iPhone(iOS)"; break;
            case 2: // Điện thoại phổ thông
                loaiSP = CUSTOM_PATH_PT + "Điện thoại phổ thông"; break;
            default: loaiSP = CUSTOM_PATH_PT + "Android"; break;
        }
        return loaiSP == null ? null : new SanPhamFragment(loaiSP);
    }

    @Override
    public int getCount() {
        return 3;
    }
}
