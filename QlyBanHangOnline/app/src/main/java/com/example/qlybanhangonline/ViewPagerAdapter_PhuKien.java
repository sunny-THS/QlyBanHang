package com.example.qlybanhangonline;

import static com.example.qlybanhangonline.Constants.CUSTOM_PATH_PT;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.qlybanhangonline.fragment.frgApp.SanPhamFragment;

public class ViewPagerAdapter_PhuKien  extends FragmentStatePagerAdapter {

    public ViewPagerAdapter_PhuKien(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title;
        switch (position) {
            case 0: // Pin sạc dự phòng
                title = "Pin sạc dự phòng"; break;
            case 1: // Cáp sạc
                title = "Cáp sạc"; break;
            case 2: // Miếng dán màn hình
                title = "Miếng dán màn hình"; break;
            case 3: // Ốp lưng điện thoại
                title = "Ốp lưng điện thoại"; break;
            case 4: // Gậy tự sướng
                title = "Gậy tự sướng"; break;
            case 5: // Đế móc điện thoại
                title = "Đế móc điện thoại"; break;
            case 6: // Túi chống nướ
                title = "Túi chống nước"; break;
            default: title = "Pin sạc dự phòng"; break;
        }
        return title;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        String loaiSP = null;
        switch (position) {
            case 0: // Pin sạc dự phòng
                loaiSP = CUSTOM_PATH_PT + "Pin sạc dự phòng"; break;
            case 1: // Cáp sạc
                loaiSP = CUSTOM_PATH_PT + "Cáp sạc"; break;
            case 2: // Miếng dán màn hình
                loaiSP = CUSTOM_PATH_PT + "Miếng dán màn hình"; break;
            case 3: // Ốp lưng điện thoại
                loaiSP = CUSTOM_PATH_PT + "Ốp lưng điện thoại"; break;
            case 4: // Gậy tự sướng
                loaiSP = CUSTOM_PATH_PT + "Gậy tự sướng"; break;
            case 5: // Đế móc điện thoại
                loaiSP = CUSTOM_PATH_PT + "Đế móc điện thoại"; break;
            case 6: // Túi chống nướ
                loaiSP = CUSTOM_PATH_PT + "Túi chống nước"; break;
            default: loaiSP = CUSTOM_PATH_PT + "Pin sạc dự phòng"; break;
        }
        return loaiSP == null ? null : new SanPhamFragment(loaiSP);
    }

    @Override
    public int getCount() {
        return 7;
    }
}
