package com.example.trandainhan.orderapp.models;

import java.util.ArrayList;


public class DanhMuc {
    public int danhMucId;
    public String tenDanhMuc;
    public ArrayList<MonAn> monAnArrayList;

    public DanhMuc(int danhMucId, String tenDanhMuc, String hinh) {
        this.danhMucId = danhMucId;
        this.tenDanhMuc = tenDanhMuc;
        monAnArrayList = new ArrayList<MonAn>();
    }
}
