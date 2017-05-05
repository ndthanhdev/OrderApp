package com.example.trandainhan.orderapp.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.trandainhan.orderapp.R;
import com.example.trandainhan.orderapp.models.MonAn;

import java.io.InputStream;
import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by TranDaiNhan on 5/4/2017.
 */

public class MonAnAdapter extends ArrayAdapter implements Serializable {

    Context context;
    public ArrayList<MonAn> monAnArrayList;

    public MonAnAdapter(@NonNull Context context, @NonNull ArrayList<MonAn> objects) {
        super(context, 0, objects);
        this.context = context;
        monAnArrayList = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        MonAn monAn = monAnArrayList.get(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.layout_item_monan, parent, false);
        }

        TextView txtTen = (TextView) convertView.findViewById(R.id.txtTenMonAn);
        TextView txtMoTa = (TextView) convertView.findViewById(R.id.txtMoTa);
        TextView txtGia = (TextView) convertView.findViewById(R.id.txtGia);
        ImageView imageView = (ImageView) convertView.findViewById(R.id.imgMonAn);

        txtTen.setText(monAn.tenMonAn);
        txtMoTa.setText(monAn.moTo);
        txtGia.setText(String.valueOf(monAn.gia));

        new DownloadImageTask(imageView)
                .execute("http://lorempixel.com/400/400/food/");

        return convertView;
    }
}

class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
    ImageView bmImage;

    public DownloadImageTask(ImageView bmImage) {
        this.bmImage = bmImage;
    }

    protected Bitmap doInBackground(String... urls) {
        String urldisplay = urls[0];
        Bitmap mIcon11 = null;
        try {
            InputStream in = new URL(urldisplay).openStream();
            mIcon11 = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        return mIcon11;
    }

    protected void onPostExecute(Bitmap result) {
        bmImage.setImageBitmap(result);
    }
}
