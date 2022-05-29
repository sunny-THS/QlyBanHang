package com.example.qlybanhang;

import java.io.Serializable;

public class ThongTinTK extends TaiKhoan implements Serializable {
    private String ten, email, ngaySinh, gioiTinh, sdt;
    private int id_tttk;

    public int getId_tttk() {
        return id_tttk;
    }

    public void setId_tttk(int id_tttk) {
        this.id_tttk = id_tttk;
    }

    public ThongTinTK() {
    }


    public ThongTinTK(int id_tttk, String ten, String email, String ngaySinh, String gioiTinh, String sdt, String id_tk) {
        super(id_tk, "", "", "");
        this.id_tttk = id_tttk;
        this.ten = ten;
        this.email = email;
        this.ngaySinh = ngaySinh;
        this.gioiTinh = gioiTinh;
        this.sdt = sdt;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(String ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public String getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }
}
