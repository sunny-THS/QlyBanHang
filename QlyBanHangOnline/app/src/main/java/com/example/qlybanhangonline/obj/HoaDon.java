package com.example.qlybanhangonline.obj;

public class HoaDon {
    private String id;

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    private String trangThai;

    public HoaDon(String id, String ngayLap, double donGia, String trangThai) {
        this.id = id;
        this.ngayLap = ngayLap;
        this.donGia = donGia;
        this.trangThai = trangThai;
    }

    public String getNgayLap() {
        return ngayLap;
    }

    public void setNgayLap(String ngayLap) {
        this.ngayLap = ngayLap;
    }

    private String ngayLap;
    private double donGia;

    public HoaDon() {
    }

    public HoaDon(String id, double donGia) {
        this.id = id;
        this.donGia = donGia;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getDonGia() {
        return donGia;
    }

    public void setDonGia(double donGia) {
        this.donGia = donGia;
    }
}
