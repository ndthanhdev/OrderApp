package com.example.trandainhan.orderapp.api;

import com.example.trandainhan.orderapp.models.MonAn;
import com.example.trandainhan.orderapp.models.QuanLy;


public class AddMonAnForm {
    public QuanLy quanLy;
    public MonAn monAn;

    public AddMonAnForm(QuanLy quanLy, MonAn monAn) {
        this.quanLy = quanLy;
        this.monAn = monAn;
    }
}
