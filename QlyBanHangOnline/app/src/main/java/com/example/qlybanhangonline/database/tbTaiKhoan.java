package com.example.qlybanhangonline.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.example.qlybanhangonline.obj.TaiKhoan;
import com.example.qlybanhangonline.obj.ThongTinTK;

import java.util.ArrayList;

public class tbTaiKhoan extends DbHelper {

    public tbTaiKhoan(@Nullable Context context) {
        super(context);
    }

    public TaiKhoan getTK() {
        SQLiteDatabase db = getReadableDatabase();
        TaiKhoan tk = null;

        Cursor cursor = db.rawQuery("select * from tbTaiKhoan", null);

        if (cursor.moveToNext()) {
            tk = new TaiKhoan(
                    cursor.getString(0), // id
                    cursor.getString(1), // username
                    cursor.getString(2), // mật khẩu
                    cursor.getString(3) // iamge
            );
        }
        db.close();
        return tk;
    }

    public boolean insert(TaiKhoan tk) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("id", tk.getId());
        contentValues.put("username", tk.getUsername());
        contentValues.put("password", tk.getPassword());
        contentValues.put("image", tk.getImage());

        boolean kq = db.insert("tbTaiKhoan", null, contentValues) > 0;
        db.close();
        return kq;
    }

    public void delete(String id) {
        String[] _id = {String.valueOf(id)};
        SQLiteDatabase db = getWritableDatabase();
        db.delete("tbTaiKhoan", "id=?", _id);
        db.close();
    }

    public void update(TaiKhoan tk) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("username", tk.getUsername());
        contentValues.put("password", tk.getPassword());
        contentValues.put("image", tk.getImage());

        String[] _id = {String.valueOf(tk.getId())};
        db.update("tbTaiKhoan", contentValues,"id=?", _id);
        db.close();
    }
}
