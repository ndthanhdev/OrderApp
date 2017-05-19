package com.example.trandainhan.orderapp.models;

/**
 * Created by TranDaiNhan on 5/4/2017.
 */

public class MonAn {
    public int monAnId;
    public String tenMonAn;
    public int gia;
    public String moTa;
    public String hinh;
    public int danhMucId;

    public MonAn(String tenMonAn, int gia, String moTa, String hinh, int danhMucId) {
        this.tenMonAn = tenMonAn;
        this.gia = gia;
        this.moTa = moTa;
        this.hinh = hinh;
        this.danhMucId = danhMucId;
    }

    public MonAn(int monAnId, String tenMonAn, int gia, String moTa, String hinh) {
        this.monAnId = monAnId;
        this.tenMonAn = tenMonAn;
        this.gia = gia;
        this.moTa = moTa;
        this.hinh = hinh;
    }
}
