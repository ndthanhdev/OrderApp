package com.example.trandainhan.orderapp.api;

import com.example.trandainhan.orderapp.models.QuanLy;


public class AddDanhMucForm {
    public QuanLy quanLy;
    public String tenDanhMuc;

    public AddDanhMucForm(QuanLy quanLy, String tenDanhMuc) {
        this.quanLy = quanLy;
        this.tenDanhMuc = tenDanhMuc;
    }
}
