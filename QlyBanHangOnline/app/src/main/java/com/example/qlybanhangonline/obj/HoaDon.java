package com.example.qlybanhangonline.obj;

public class HoaDon {
    private String id;

    public HoaDon(String id, String ngayLap, double donGia) {
        this.id = id;
        this.ngayLap = ngayLap;
        this.donGia = donGia;
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
