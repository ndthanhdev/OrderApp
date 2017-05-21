package com.example.trandainhan.orderapp.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.trandainhan.orderapp.R;
import com.example.trandainhan.orderapp.models.ChiTietDonHang;

import java.util.List;

/**
 * Created by duyth on 5/21/2017.
 */

public class ChiTietDonHangAdapter extends ArrayAdapter<ChiTietDonHang> {

    List<ChiTietDonHang> chiTietDonHangs;

    public ChiTietDonHangAdapter(@NonNull Context context, @NonNull List<ChiTietDonHang> objects) {
        super(context, 0, objects);
        chiTietDonHangs = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.layout_chi_tiet_don_hang_item, parent, false);
        }
        ChiTietDonHang chiTietDonHang = chiTietDonHangs.get(position);

        TextView txtTenMon = (TextView) convertView.findViewById(R.id.txtChiTietDonHangTenMon);
        TextView txtGia = (TextView) convertView.findViewById(R.id.txtChiTietDonHangGia);
        TextView txtSoLuong = (TextView) convertView.findViewById(R.id.txtChiTietDonHangSoLuong);

        txtTenMon.setText(chiTietDonHang.monAn.tenMonAn);
        txtGia.setText(Integer.toString(chiTietDonHang.donGia));
        txtSoLuong.setText(Integer.toString(chiTietDonHang.soLuong));

        return convertView;
    }
}
