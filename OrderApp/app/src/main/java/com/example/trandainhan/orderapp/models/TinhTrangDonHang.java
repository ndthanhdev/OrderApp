package com.example.trandainhan.orderapp.models;

import com.google.gson.annotations.SerializedName;

public enum TinhTrangDonHang {
    @SerializedName("0")
    ChoXuLy(0),
    @SerializedName("1")
    DangGiaoHang(1),
    @SerializedName("2")
    DaXuLy(2),
    @SerializedName("3")
    DaHuy(3);

    private final int value;

    public int getValue() {
        return value;
    }

    private TinhTrangDonHang(int value) {
        this.value = value;
    }

}
