package com.example.trandainhan.orderapp;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.trandainhan.orderapp.adapter.MonAnAdapter;
import com.example.trandainhan.orderapp.models.MonAn;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by TranDaiNhan on 5/5/2017.
 */

public class FoodFragment extends Fragment {

    public static final String ARG_DANH_MUC_ID = "DMID";
    public static final String ARG_MON_AN_ADAPTER = "MAA";

    private String danhMucId;
    private MonAnAdapter monAnAdapter;

    public FoodFragment() {
    }


    public static FoodFragment newInstance(String danhMucId, MonAnAdapter monAnAdapter) {

        Bundle args = new Bundle();
        args.putString(FoodFragment.ARG_DANH_MUC_ID, danhMucId);
        args.putSerializable(ARG_MON_AN_ADAPTER, monAnAdapter);
        FoodFragment fragment = new FoodFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        danhMucId = getArguments().getString(ARG_DANH_MUC_ID);

        monAnAdapter = (MonAnAdapter) getArguments().getSerializable(ARG_MON_AN_ADAPTER);

        (new UpdateMonAnAdapterTask(danhMucId, monAnAdapter)).execute();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_food, container, false);
        ListView listView = (ListView) rootView.findViewById(R.id.lstMonAn);
        listView.setAdapter(monAnAdapter);
        return rootView;
    }
}

class UpdateMonAnAdapterTask extends AsyncTask<Void, Integer, ArrayList<MonAn>> {

    private String danhMucId;
    private MonAnAdapter monAnAdapter;

    public UpdateMonAnAdapterTask(String danhMucId, MonAnAdapter monAnAdapter) {
        this.danhMucId = danhMucId;
        this.monAnAdapter = monAnAdapter;
    }

    @Override
    protected ArrayList<MonAn> doInBackground(Void... params) {
        HttpURLConnection connection = null;
        BufferedReader reader = null;
        try {

            URL url = new URL(UrlList.GET_MON_AN + "/" + danhMucId);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            connection.setConnectTimeout(10);

            connection.setReadTimeout(10);

            InputStream stream = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(stream));

            StringBuffer buffer = new StringBuffer();


            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }

            String jsonString = buffer.toString();

            ArrayList<MonAn> monAnArrayList = new ArrayList<>();

            JSONArray jsonArray = new JSONArray(jsonString);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                int id = jsonObject.getInt("monAnId");
                String name = jsonObject.getString("tenMonAn");
                int tien = jsonObject.getInt("gia");
                String moTa = jsonObject.getString("moTa");
                MonAn monAn = new MonAn(id, name, tien, moTa);
                monAnArrayList.add(monAn);
            }
            return monAnArrayList;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    @Override
    protected void onPostExecute(ArrayList<MonAn> monen) {
        super.onPostExecute(monen);
        monAnAdapter.monAnArrayList.clear();
        monAnAdapter.monAnArrayList.addAll(monen);
        monAnAdapter.notifyDataSetChanged();
    }
}


