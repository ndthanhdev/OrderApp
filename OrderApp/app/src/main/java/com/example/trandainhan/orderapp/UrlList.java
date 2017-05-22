package com.example.trandainhan.orderapp;

/**
 * Created by TranDaiNhan on 5/5/2017.
 */

public class UrlList {
    public static final String Base = "http://api-orderfood.azurewebsites.net";
    public static final String GET_DANH_MUC = Base + "/danhmuc/getdanhmucs";
    public static final String GET_MON_AN = Base + "/monan/getmonans";
    public static final String LOGIN = Base + "/login";

    public static final String UPDATE_DANH_MUC = Base + "/danhmuc/updatedanhmuc";
    public static final String ADD_DANH_MUC = Base + "/danhmuc/adddanhmuc";
    public static final String DELETE_DANH_MUC = Base + "/danhmuc/delete";

    public static final String ADD_MON_AN = Base + "/monan/addmonan";
    public static final String UPDATE_MON_AN = Base + "/monan/updatemonan";
    public static final String DELETE_MON_AN = Base + "/monan/delete";

    public static final String GET_DON_HANG_CHO_XU_LY = Base + "/donhang/choxuly";
    public static final String GET_DON_HANG_DANG_GIAO = Base + "/donhang/danggiao";
    public static final String GET_DON_HANG_DA_XU_LY = Base + "/donhang/daxuly";
    public static final String UPDATE_TINH_TRANG_DON_HANG = Base + "/donhang/UpdateTinhTrangDonHang";
    public static final String DELETE_DON_HANG = Base + "/donhang/delete";

    public static final String LOREMPIXEL_IMAGE = "http://lorempixel.com/400/400/food/";
}
