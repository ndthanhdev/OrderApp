package com.example.trandainhan.orderapp.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.trandainhan.orderapp.R;
import com.example.trandainhan.orderapp.models.DonHang;
import com.example.trandainhan.orderapp.models.*;

import java.util.List;

/**
 * Created by duyth on 5/20/2017.
 */

public class DonHangAdapter extends ArrayAdapter<DonHang> {

    Context context;
    public List<DonHang> donHangs;

    public DonHangAdapter(@NonNull Context context, @NonNull List<DonHang> objects) {
        super(context, 0, objects);
        this.context = context;
        this.donHangs = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.layout_donhang_item, parent, false);
        }

        DonHang donHang = donHangs.get(position);

        TextView txtDonHangId = (TextView) convertView.findViewById(R.id.txtMaDonHang);
        ImageView icon = (ImageView) convertView.findViewById(R.id.imgDonHangIcon);

        txtDonHangId.setText(Integer.toString(donHang.donHangId));
        switch (donHang.tinhTrangDonHang) {
            case ChoXuLy:
                icon.setImageResource(R.drawable.ic_cho_xu_ly);
                break;
            case DangGiaoHang:
                icon.setImageResource(R.drawable.ic_dang_giao_hang);
                break;
            case DaXuLy:
                icon.setImageResource(R.drawable.ic_da_xu_ly);
                break;
            default:
                icon.setImageResource(R.drawable.ic_cancel);
                break;
        }

        return convertView;
    }
}
