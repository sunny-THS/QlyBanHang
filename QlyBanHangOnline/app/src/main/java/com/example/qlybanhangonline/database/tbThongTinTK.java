package com.example.qlybanhangonline.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.example.qlybanhangonline.obj.TaiKhoan;
import com.example.qlybanhangonline.obj.ThongTinTK;

import java.util.ArrayList;

public class tbThongTinTK extends DbHelper {

    public tbThongTinTK(@Nullable Context context) {
        super(context);
    }

    public boolean insert(ThongTinTK tttk) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("HoTen", tttk.getTen());
        contentValues.put("Email", tttk.getEmail());
        contentValues.put("NgaySinh", tttk.getNgaySinh());
        contentValues.put("GioiTinh", tttk.getGioiTinh().toLowerCase().equals("nam") ? 1 : 0);
        contentValues.put("SDT", tttk.getSdt());
        contentValues.put("id_tk", tttk.getId());

        boolean kq = db.insert("tbThongTinTK", null, contentValues) > 0;
        db.close();
        return kq;
    }

    public void delete(int id) {
        String[] _id = {String.valueOf(id)};
        SQLiteDatabase db = getWritableDatabase();
        db.delete("tbTaiKhoan", "id=?", _id);
        db.close();
    }

    public void update(ThongTinTK tttk) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("HoTen", tttk.getTen());
        contentValues.put("Email", tttk.getEmail());
        contentValues.put("NgaySinh", tttk.getNgaySinh());
        contentValues.put("GioiTinh", tttk.getGioiTinh().toLowerCase().equals("nam") ? 1 : 0);
        contentValues.put("SDT", tttk.getSdt());

        String[] _id = {String.valueOf(tttk.getId_tttk())};
        db.update("tbThongTinTK", contentValues,"id=?", _id);
        db.close();
    }

    public ThongTinTK getTTTK() {
        SQLiteDatabase db = getReadableDatabase();
        ThongTinTK tttk = null;

        Cursor cursor = db.rawQuery("select * from tbThongTinTK", null);

        if (cursor.moveToNext()) {
            tttk = new ThongTinTK(
                    cursor.getInt(0), // id
                    cursor.getString(1), // họ tên
                    cursor.getString(2), // email
                    cursor.getString(3), // ngày sinh
                    cursor.getInt(4) == 1 ? "nam" : "nữ", // giới tính
                    cursor.getString(5), // sdt
                    cursor.getString(6) // id_tk
            );
        }
        db.close();
        return tttk;
    }
}
