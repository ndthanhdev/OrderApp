package com.example.trandainhan.orderapp;

import com.example.trandainhan.orderapp.helpers.GsonHelper;
import com.example.trandainhan.orderapp.helpers.OkHttpHelper;
import com.example.trandainhan.orderapp.models.DanhMuc;
import com.example.trandainhan.orderapp.models.MonAn;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Created by duyth on 5/10/2017.
 */

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
}
