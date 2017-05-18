package com.example.trandainhan.orderapp.fragments;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.example.trandainhan.orderapp.MainActivity;
import com.example.trandainhan.orderapp.api.Api;
import com.example.trandainhan.orderapp.R;
import com.example.trandainhan.orderapp.adapter.MonAnAdapter;
import com.example.trandainhan.orderapp.helpers.ViewHelper;
import com.example.trandainhan.orderapp.models.MonAn;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by TranDaiNhan on 5/5/2017.
 */

public class FoodFragment extends Fragment {

    private int danhMucId;

    private MonAnAdapter monAnAdapter;

    @Bind(R.id.lstMonAn)
    ListView listView;

    @Bind(R.id.progress)
    LinearLayout progress;

    @Bind(R.id.btnThemMonAn)
    Button btnAddMonAn;

    MainActivity context;

    public static FoodFragment newInstance(int danhMucId, MainActivity context) {

        FoodFragment fragment = new FoodFragment();
        fragment.danhMucId = danhMucId;
        fragment.context = context;
        fragment.monAnAdapter = new MonAnAdapter(context, new ArrayList<MonAn>());
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_food, container, false);
        ButterKnife.bind(this, rootView);

        listView.setAdapter(monAnAdapter);
        reload();

        btnAddMonAn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater li = LayoutInflater.from(context);
                View promptsView = li.inflate(R.layout.layout_mon_an, null);

                final EditText txtTen = (EditText) promptsView.findViewById(R.id.txtDialogMonAnTen);
                final EditText txtMoTa = (EditText) promptsView.findViewById(R.id.txtDialogMonAnMoTa);
                final EditText txtHinh = (EditText) promptsView.findViewById(R.id.txtDialogMonAnHinh);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        context);

                // set prompts.xml to alertdialog builder
                alertDialogBuilder.setView(promptsView);

                alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // don't put anything here
                    }
                });

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();

                alertDialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (TextUtils.isEmpty(txtTen.getText().toString()) || TextUtils.isEmpty(txtMoTa.getText().toString())
                                || TextUtils.isEmpty(txtHinh.getText().toString())) {
                            Toast.makeText(context, "Không được để trống", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if(!URLUtil.isValidUrl(txtHinh.getText().toString())){
                            Toast.makeText(context, "Không phải url", Toast.LENGTH_SHORT).show();
                            return;
                        }

                    }
                });

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LayoutInflater li = LayoutInflater.from(context);
                View promptsView = li.inflate(R.layout.layout_mon_an, null);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        context);

                // set prompts.xml to alertdialog builder
                alertDialogBuilder.setView(promptsView);

                alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                alertDialogBuilder.setNeutralButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                alertDialogBuilder.setCancelable(false);

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();

            }
        });

        return rootView;
    }

    public void reload(){
        (new UpdateMonAnAdapterTask(danhMucId, monAnAdapter)).execute();
    }

    class UpdateMonAnAdapterTask extends AsyncTask<Object, Object, List<MonAn>> {

        private int danhMucId;
        private MonAnAdapter monAnAdapter;

        public UpdateMonAnAdapterTask(int danhMucId, MonAnAdapter monAnAdapter) {

            this.danhMucId = danhMucId;
            this.monAnAdapter = monAnAdapter;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            ViewHelper.moveToFront(progress, listView);
        }

        @Override
        protected List<MonAn> doInBackground(Object... params) {

            List<MonAn> monAnList = Api.GetMonAn(danhMucId);
            return monAnList;
        }

        @Override
        protected void onPostExecute(List<MonAn> monAnList) {
            super.onPostExecute(monAnList);

            monAnAdapter.clear();
            monAnAdapter.addAll(monAnList);
            monAnAdapter.notifyDataSetChanged();

            ViewHelper.moveToBack(progress, listView);
        }
    }
}




