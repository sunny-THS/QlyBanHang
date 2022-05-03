package com.example.qlybanhangonline.fragment.frgMenu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.qlybanhangonline.R;
import com.example.qlybanhangonline.ViewPagerAdapter_DienThoai;
import com.example.qlybanhangonline.ViewPagerAdapter_PhuKien;
import com.example.qlybanhangonline.databinding.FragmentHomeBinding;
import com.example.qlybanhangonline.fragment.frgApp.SanPhamRecyclerViewAdapter;
import com.example.qlybanhangonline.obj.CauHinh;
import com.example.qlybanhangonline.obj.SanPham;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    private TabLayout tab;
    private ViewPager vp;
    private BottomNavigationView bnv;

    private int flag_bnv;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        tab = binding.tabLayout;
        bnv = binding.bottomNav;
        vp = binding.viewPager;

        flag_bnv = R.id.item_dienThoai;

        load();

        // bottom nav
        bnv.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.item_dienThoai: {
                        ViewPagerAdapter_DienThoai adapterDienThoai = new ViewPagerAdapter_DienThoai(getFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
                        vp.setAdapter(adapterDienThoai);
                        tab.setupWithViewPager(vp);
                    }break;
                    case R.id.item_phuKien: {
                        ViewPagerAdapter_PhuKien adapterPhuKien = new ViewPagerAdapter_PhuKien(getFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
                        vp.setAdapter(adapterPhuKien);
                        tab.setupWithViewPager(vp);
                    }break;
                }
                return true;
            }
        });

        return root;
    }

    private void load() {
        ViewPagerAdapter_DienThoai adapterDienThoai = new ViewPagerAdapter_DienThoai(getFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        vp.setAdapter(adapterDienThoai);
        tab.setupWithViewPager(vp);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}