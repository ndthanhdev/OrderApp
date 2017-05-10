package com.example.trandainhan.orderapp.fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.trandainhan.orderapp.Api;
import com.example.trandainhan.orderapp.R;
import com.example.trandainhan.orderapp.adapter.MonAnAdapter;
import com.example.trandainhan.orderapp.models.MonAn;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TranDaiNhan on 5/5/2017.
 */

public class FoodFragment extends Fragment {

    private int danhMucId;

    private MonAnAdapter monAnAdapter;


    public static FoodFragment newInstance(int danhMucId, Context context) {

        FoodFragment fragment = new FoodFragment();
        fragment.danhMucId = danhMucId;
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
        ListView listView = (ListView) rootView.findViewById(R.id.lstMonAn);
        listView.setAdapter(monAnAdapter);
        (new UpdateMonAnAdapterTask(danhMucId, monAnAdapter)).execute();
        return rootView;
    }
}

class UpdateMonAnAdapterTask extends AsyncTask<Object, Object, List<MonAn>> {

    private int danhMucId;
    private MonAnAdapter monAnAdapter;

    public UpdateMonAnAdapterTask(int danhMucId, MonAnAdapter monAnAdapter) {
        this.danhMucId = danhMucId;
        this.monAnAdapter = monAnAdapter;
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
    }
}


