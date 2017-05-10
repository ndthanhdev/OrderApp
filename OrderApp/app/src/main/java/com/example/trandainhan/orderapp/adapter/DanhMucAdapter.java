package com.example.trandainhan.orderapp.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.example.trandainhan.orderapp.R;
import com.example.trandainhan.orderapp.models.DanhMuc;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by duyth on 5/10/2017.
 */

public class DanhMucAdapter extends ArrayAdapter<DanhMuc> {

    public List<DanhMuc> danhMucs;

    public DanhMucAdapter(@NonNull Context context, @NonNull List<DanhMuc> objects) {
        super(context, 0, objects);
        this.danhMucs = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.layout_item_danhmuc, parent, false);
        }

        DanhMuc item = danhMucs.get(position);

        final TextView name = (TextView) convertView.findViewById(R.id.txtDanhMucHeader);

        name.setText(item.tenDanhMuc);

        name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Nhập tên mới");
                // Set up the input
                final EditText input = new EditText(getContext());
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                input.setInputType(InputType.TYPE_CLASS_TEXT);

                builder.setView(input);

                builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                builder.show();
            }
        });

        return convertView;
    }
}

