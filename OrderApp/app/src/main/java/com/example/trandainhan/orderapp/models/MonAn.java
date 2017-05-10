package com.example.trandainhan.orderapp.models;

/**
 * Created by TranDaiNhan on 5/4/2017.
 */

public class MonAn {
    public int monAnId;
    public String tenMonAn;
    public int gia;
    public String moTa;

    public MonAn(int monAnId, String tenMonAn, int gia, String moTa) {
        this.monAnId = monAnId;
        this.tenMonAn = tenMonAn;
        this.gia = gia;
        this.moTa = moTa;
    }
}
