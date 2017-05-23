package com.example.trandainhan.orderapp.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trandainhan.orderapp.R;
import com.example.trandainhan.orderapp.api.Api;
import com.example.trandainhan.orderapp.api.ResponseData;
import com.example.trandainhan.orderapp.api.UpdateDanhMucForm;
import com.example.trandainhan.orderapp.fragments.QuanLyDanhMucFragment;
import com.example.trandainhan.orderapp.helpers.Storage;
import com.example.trandainhan.orderapp.models.DanhMuc;
import com.example.trandainhan.orderapp.models.QuanLy;

import java.util.List;


public class DanhMucAdapter extends ArrayAdapter<DanhMuc> {

    public List<DanhMuc> danhMucs;

    QuanLyDanhMucFragment quanLyDanhMucFragment;

    public DanhMucAdapter(@NonNull Context context, @NonNull List<DanhMuc> objects, QuanLyDanhMucFragment quanLyDanhMucFragment) {
        super(context, 0, objects);
        this.danhMucs = objects;
        this.quanLyDanhMucFragment = quanLyDanhMucFragment;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.layout_item_danhmuc, parent, false);
        }

        final DanhMuc item = danhMucs.get(position);

        final TextView name = (TextView) convertView.findViewById(R.id.txtDanhMucHeader);
        Button btnDelete = (Button) convertView.findViewById(R.id.btnDeleteDanhMuc);

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
                        String newName = input.getText().toString();
                        if (TextUtils.isEmpty(newName)) {
                            return;
                        }
                        item.tenDanhMuc = newName;
                        new UpdateDanhMucTask(item).execute();
                    }
                });

                builder.show();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quanLyDanhMucFragment.delete(item);
            }
        });


        return convertView;
    }

    class UpdateDanhMucTask extends AsyncTask<Void, Void, Void> {

        DanhMuc danhMuc;


        public UpdateDanhMucTask(DanhMuc danhMuc) {
            this.danhMuc = danhMuc;
        }

        @Override
        protected Void doInBackground(Void... params) {
            Api.updateDanhMuc(new UpdateDanhMucForm(new QuanLy(Storage.Username, Storage.Password), danhMuc));
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            quanLyDanhMucFragment.reload();
        }
    }


}

