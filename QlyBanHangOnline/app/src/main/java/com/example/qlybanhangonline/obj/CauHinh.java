package com.example.qlybanhangonline.obj;

public class CauHinh {
    private String ten, moTa;

    public CauHinh() {
    }

    public CauHinh(String ten, String moTa) {
        this.ten = ten;
        this.moTa = moTa;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }
}