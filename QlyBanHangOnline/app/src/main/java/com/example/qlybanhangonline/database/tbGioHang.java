package com.example.qlybanhangonline.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.example.qlybanhangonline.obj.SanPham;

import java.util.ArrayList;

public class tbGioHang extends DbHelper {

    public tbGioHang(@Nullable Context context) {
        super(context);
    }

    public ArrayList<SanPham> getGH() {
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<SanPham> ghs = new ArrayList<>();

        Cursor cursor = db.rawQuery("select * from tbGioHang", null);

        while (cursor.moveToNext()) {
            SanPham sp = new SanPham();
            sp.setIdgh(cursor.getInt(0));
            sp.setTen(cursor.getString(1));
            sp.setSoLuong(cursor.getInt(2));
            sp.setGia(cursor.getInt(3));
            sp.setHinhAnh(cursor.getString(4));
            sp.setSoLuongTon(cursor.getInt(5));

            ghs.add(sp);
        }
        db.close();
        return ghs;
    }
    public double TongGia() {
        ArrayList<SanPham> lst = getGH();
        double tg = 0;
        for (SanPham sp : lst) {
            tg += sp.getGia() * sp.getSoLuong();
        }
        return  tg;
    }
    public SanPham getGH(String pTenSP) {
        SQLiteDatabase db = getReadableDatabase();
        SanPham ghs = null;

        String sql = String.format("select * from tbGioHang where ten = '%s'", pTenSP);
        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.moveToNext()) {
            ghs = new SanPham();
            ghs.setIdgh(cursor.getInt(0));
            ghs.setTen(cursor.getString(1));
            ghs.setSoLuong(cursor.getInt(2));
            ghs.setGia(cursor.getInt(3));
            ghs.setHinhAnh(cursor.getString(4));
            ghs.setSoLuongTon(cursor.getInt(5));

        }
        db.close();
        return ghs;
    }

    public boolean insert(SanPham gh) {
        // kiểm tra trùng
        SanPham spExists = getGH(gh.getTen());
        if (spExists != null) {
            // cập nhật số lượng
            spExists.setSoLuong(spExists.getSoLuong() + gh.getSoLuong());
            update(spExists);
            return true;
        }

        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("ten", gh.getTen());
        contentValues.put("soLuong", gh.getSoLuong());
        contentValues.put("gia", gh.getGia());
        contentValues.put("image", gh.getHinhAnh());
        contentValues.put("soLuongTonKho", gh.getSoLuongTon());

        boolean kq = db.insert("tbGioHang", null, contentValues) > 0;
        db.close();
        return kq;
    }

    public void delete(SanPham sp) {
        String[] _id = {String.valueOf(sp.getIdgh())};
        SQLiteDatabase db = getWritableDatabase();
        db.delete("tbGioHang", "id=?", _id);
        db.close();
    }

    public void update(SanPham gh) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("soLuong", gh.getSoLuong());

        String[] _id = {String.valueOf(gh.getIdgh())};
        db.update("tbGioHang", contentValues,"id=?", _id);
        db.close();
    }
}
