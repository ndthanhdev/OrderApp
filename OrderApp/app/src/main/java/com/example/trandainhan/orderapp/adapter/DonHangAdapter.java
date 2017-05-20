package com.example.trandainhan.orderapp.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.trandainhan.orderapp.R;
import com.example.trandainhan.orderapp.models.DonHang;

import java.util.List;

/**
 * Created by duyth on 5/20/2017.
 */

public class DonHangAdapter extends ArrayAdapter<DonHang> {

    Context context;
    public List<DonHang> donHangs;

    public DonHangAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<DonHang> objects) {
        super(context, resource, objects);
        this.donHangs = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.layout_donhang_item, parent, false);
        }

        return convertView;
    }
}
