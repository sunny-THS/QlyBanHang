package com.example.qlybanhang;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;

public class SanPham implements Serializable {
    String id, ten, nsx, hinhAnh, hangSP, loaiSP;
    int soLuong;

    public int getSoLuongTon() {
        return soLuongTon;
    }

    public void setSoLuongTon(int soLuongTon) {
        this.soLuongTon = soLuongTon;
    }

    int soLuongTon;
    double gia;
    int idgh;

    public int getIdgh() {
        return idgh;
    }

    public void setIdgh(int idgh) {
        this.idgh = idgh;
    }

    ArrayList<CauHinh> cauHinhs;

    public SanPham() {
    }

    public SanPham(String id, String ten, String nsx, String hinhAnh, String hangSP, String loaiSP, int soLuong, double gia, ArrayList<CauHinh> cauHinhs) {
        this.id = id;
        this.ten = ten;
        this.nsx = nsx;
        this.hinhAnh = hinhAnh;
        this.hangSP = hangSP;
        this.loaiSP = loaiSP;
        this.soLuong = soLuong;
        this.gia = gia;
        this.cauHinhs = cauHinhs;
    }

    public SanPham(String id, String ten, String hinhAnh, String hangSP, String loaiSP, int soLuong, double gia, ArrayList<CauHinh> cauHinhs) {
        this.id = id;
        this.ten = ten;
        this.hinhAnh = hinhAnh;
        this.hangSP = hangSP;
        this.loaiSP = loaiSP;
        this.soLuong = soLuong;
        this.gia = gia;
        this.cauHinhs = cauHinhs;
    }

    @NonNull
    @Override
    public String toString() {
        return String.format("%s&%.0f", this.ten, this.gia);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getNsx() {
        return nsx;
    }

    public void setNsx(String nsx) {
        this.nsx = nsx;
    }

    public String getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        this.hinhAnh = hinhAnh;
    }

    public String getHangSP() {
        return hangSP;
    }

    public void setHangSP(String hangSP) {
        this.hangSP = hangSP;
    }

    public String getLoaiSP() {
        return loaiSP;
    }

    public void setLoaiSP(String loaiSP) {
        this.loaiSP = loaiSP;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public double getGia() {
        return gia;
    }

    public void setGia(double gia) {
        this.gia = gia;
    }

    public ArrayList<CauHinh> getCauHinhs() {
        return cauHinhs;
    }

    public void setCauHinhs(ArrayList<CauHinh> cauHinhs) {
        this.cauHinhs = cauHinhs;
    }
}
