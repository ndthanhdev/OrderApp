package com.example.trandainhan.orderapp.models;

import java.util.ArrayList;

/**
 * Created by TranDaiNhan on 5/4/2017.
 */

public class DanhMuc {
    public int danhMucId;
    public String tenDanhMuc;
    public String hinh;
    public ArrayList<MonAn> monAnArrayList;

    public DanhMuc(int danhMucId, String tenDanhMuc, String hinh) {
        this.danhMucId = danhMucId;
        this.tenDanhMuc = tenDanhMuc;
        this.hinh = hinh;
        monAnArrayList = new ArrayList<MonAn>();
    }
}
