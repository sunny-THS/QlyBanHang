package com.example.qlybanhangonline.obj;

import java.io.Serializable;

public class TaiKhoan implements Serializable {
    private String id;
    private String username;
    private String password;
    private String image;

    public TaiKhoan(String id, String username, String password, String image) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.image = image;
    }

    public TaiKhoan() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}
