package com.example.trandainhan.orderapp.models;

import java.util.Date;
import java.util.List;


public class DonHang {
    public int donHangId;
    public KhachHang khachHang;
    public List<ChiTietDonHang> chiTietDonHangs;
    public TinhTrangDonHang tinhTrangDonHang;
    public Date ngay;
}


