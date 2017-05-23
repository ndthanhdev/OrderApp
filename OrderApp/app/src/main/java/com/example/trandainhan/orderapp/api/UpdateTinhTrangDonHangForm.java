package com.example.trandainhan.orderapp.api;

import com.example.trandainhan.orderapp.models.QuanLy;
import com.example.trandainhan.orderapp.models.TinhTrangDonHang;


public class UpdateTinhTrangDonHangForm {
    public QuanLy quanLy;
    public int donHangId;
    public TinhTrangDonHang tinhTrangMoi;

    public UpdateTinhTrangDonHangForm(QuanLy quanLy, int donHangId, TinhTrangDonHang tinhTrangMoi) {
        this.quanLy = quanLy;
        this.donHangId = donHangId;
        this.tinhTrangMoi = tinhTrangMoi;
    }
}
