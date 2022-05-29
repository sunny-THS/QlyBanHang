package com.example.qlybanhang;

public class ChucNang {
    private int imageChucNang;
    private String tenChucNang;

    public ChucNang() {
    }

    public ChucNang(int imageChucNang, String tenChucNang) {
        this.imageChucNang = imageChucNang;
        this.tenChucNang = tenChucNang;
    }

    public int getImageChucNang() {
        return imageChucNang;
    }

    public void setImageChucNang(int imageChucNang) {
        this.imageChucNang = imageChucNang;
    }

    public String getTenChucNang() {
        return tenChucNang;
    }

    public void setTenChucNang(String tenChucNang) {
        this.tenChucNang = tenChucNang;
    }
}
