package com.example.qlybanhangonline.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {

    public DbHelper(@Nullable Context context) {
        super(context, "DbQlyBanHang.db", null, 1);
        onCreate(getWritableDatabase());
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) { // tạo db
        sqLiteDatabase.execSQL("Create table if not exists tbTaiKhoan (id text primary key, username text, password text, image text);");
        sqLiteDatabase.execSQL("Create table if not exists tbThongTinTK (id integer primary key autoincrement, HoTen text, Email text, NgaySinh text, GioiTinh integer, SDT text, id_tk text, foreign key(id_tk) references tbTaiKhoan(id));");
        sqLiteDatabase.execSQL("Create table if not exists tbGioHang (id integer primary key autoincrement, ten text, soLuong integer, gia integer, image text, soLuongTonKho integer);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        if (sqLiteDatabase == null)
            sqLiteDatabase = getWritableDatabase();

        sqLiteDatabase.execSQL("Drop table if exists tbTaiKhoan");
        sqLiteDatabase.execSQL("Drop table if exists tbThongTinTK");

        onCreate(sqLiteDatabase);
    }

    public void reloadTbGioHang() {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.execSQL("Drop table if exists tbGioHang");
        onCreate(sqLiteDatabase);
    }
}
