package com.example.trandainhan.orderapp.api;

import com.example.trandainhan.orderapp.UrlList;
import com.example.trandainhan.orderapp.helpers.GsonHelper;
import com.example.trandainhan.orderapp.helpers.OkHttpHelper;
import com.example.trandainhan.orderapp.models.DanhMuc;
import com.example.trandainhan.orderapp.models.MonAn;
import com.google.gson.JsonSyntaxException;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;


public class Api {
    public static List<MonAn> GetMonAn(int danhMucId) {
        try {
            String response = OkHttpHelper.get(UrlList.GET_MON_AN + "/" + danhMucId);
            MonAn[] array = GsonHelper.fromJson(response, MonAn[].class);
            return Arrays.asList(array);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<DanhMuc> GetDanhMuc() {
        try {
            String response = OkHttpHelper.get(UrlList.GET_DANH_MUC);
            DanhMuc[] array = GsonHelper.fromJson(response, DanhMuc[].class);
            return Arrays.asList(array);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static DanhMuc updateDanhMuc(UpdateDanhMucForm updateDanhMucForm) {
        try {
            String response = OkHttpHelper.post(UrlList.UPDATE_DANH_MUC, updateDanhMucForm);
            DanhMuc danhMuc = GsonHelper.fromJson(response, DanhMuc.class);
            return danhMuc;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ResponseData<DanhMuc> addDanhMuc(AddDanhMucForm addDanhMucForm) {
        try {
            String response = OkHttpHelper.post(UrlList.ADD_DANH_MUC, addDanhMucForm);
            ResponseData<DanhMuc> danhMucResponseData = GsonHelper.fromJson(response, ResponseData.class);
            return danhMucResponseData;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ResponseData deleteDanhMuc(int danhMucId) {
        try {
            String response = OkHttpHelper.get(UrlList.DELETE_DANH_MUC+"/"+danhMucId);
            ResponseData danhMucResponseData = GsonHelper.fromJson(response, ResponseData.class);
            return danhMucResponseData;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ResponseData addMonAn(AddMonAnForm addMonAnForm){
        try {
            String response = OkHttpHelper.post(UrlList.ADD_MON_AN, addMonAnForm);
            ResponseData<MonAn> danhMucResponseData = GsonHelper.fromJson(response, ResponseData.class);
            return danhMucResponseData;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ResponseData updateMonAn(AddMonAnForm addMonAnForm){
        try {
            String response = OkHttpHelper.post(UrlList.UPDATE_MON_AN, addMonAnForm);
            ResponseData<MonAn> danhMucResponseData = GsonHelper.fromJson(response, ResponseData.class);
            return danhMucResponseData;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ResponseData deleteMonAn(int monAnId) {
        try {
            String response = OkHttpHelper.get(UrlList.DELETE_MON_AN+"/"+monAnId);
            ResponseData danhMucResponseData = GsonHelper.fromJson(response, ResponseData.class);
            return danhMucResponseData;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}

