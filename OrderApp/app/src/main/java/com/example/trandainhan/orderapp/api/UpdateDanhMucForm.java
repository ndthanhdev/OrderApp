package com.example.trandainhan.orderapp.api;

import com.example.trandainhan.orderapp.models.DanhMuc;
import com.example.trandainhan.orderapp.models.QuanLy;

public class UpdateDanhMucForm {
    public QuanLy quanLy;
    public DanhMuc danhMuc;

    public UpdateDanhMucForm(QuanLy quanLy, DanhMuc danhMuc) {
        this.quanLy = quanLy;
        this.danhMuc = danhMuc;
    }
}
