package com.example.trandainhan.orderapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.trandainhan.orderapp.R;
import com.example.trandainhan.orderapp.UrlList;
import com.example.trandainhan.orderapp.models.MonAn;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class MonAnAdapter extends ArrayAdapter implements Serializable {

    Context context;
    public List<MonAn> monAnArrayList;

    public MonAnAdapter(@NonNull Context context, @NonNull List<MonAn> objects) {
        super(context, 0, objects);
        this.context = context;
        monAnArrayList = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.layout_item_monan, parent, false);
        }

        MonAn monAn = monAnArrayList.get(position);

        TextView txtTen = (TextView) convertView.findViewById(R.id.txtTenMonAn);
        TextView txtMoTa = (TextView) convertView.findViewById(R.id.txtMoTa);
        TextView txtGia = (TextView) convertView.findViewById(R.id.txtGia);
        ImageView imageView = (ImageView) convertView.findViewById(R.id.imgMonAn);

        txtTen.setText(monAn.tenMonAn);
        txtMoTa.setText(monAn.moTa);
        txtGia.setText(String.valueOf(monAn.gia));

        Picasso.with(context).load(monAn.hinh).into(imageView);

        return convertView;
    }
}
