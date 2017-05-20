package com.example.trandainhan.orderapp.fragments;

import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.example.trandainhan.orderapp.MainActivity;
import com.example.trandainhan.orderapp.R;
import com.example.trandainhan.orderapp.adapter.DonHangAdapter;
import com.example.trandainhan.orderapp.api.Api;
import com.example.trandainhan.orderapp.api.ResponseData;
import com.example.trandainhan.orderapp.helpers.ViewHelper;
import com.example.trandainhan.orderapp.models.DonHang;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ChoXuLyFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ChoXuLyFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChoXuLyFragment extends Fragment {

    @Bind(R.id.lstDonHang)
    ListView listView;

    @Bind(R.id.progress)
    LinearLayout progress;

    MainActivity context;

    DonHangAdapter donHangAdapter;

    private OnFragmentInteractionListener mListener;

    public ChoXuLyFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ChoXuLyFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ChoXuLyFragment newInstance(MainActivity context) {
        ChoXuLyFragment fragment = new ChoXuLyFragment();
        fragment.context = context;
        fragment.donHangAdapter = new DonHangAdapter(context, new ArrayList<DonHang>());
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_cho_xu_ly, container, false);
        ButterKnife.bind(this, rootView);
        listView.setAdapter(donHangAdapter);
        reload();
        return rootView;

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    void reload() {
        new ReloadTask().execute();
    }

    class ReloadTask extends AsyncTask<Void, Void, ResponseData<DonHang[]>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            ViewHelper.moveToFront(progress, listView);
        }


        @Override
        protected ResponseData<DonHang[]> doInBackground(Void... params) {

            ResponseData<DonHang[]> responseData = Api.getDonHangChoXuLy();

            return responseData;
        }


        @Override
        protected void onPostExecute(ResponseData<DonHang[]> listResponseData) {
            super.onPostExecute(listResponseData);

            if (listResponseData.status != 0) {
                Toast.makeText(context, listResponseData.message, Toast.LENGTH_SHORT).show();
            } else {
                donHangAdapter.donHangs.clear();
                donHangAdapter.addAll(listResponseData.data);
                donHangAdapter.notifyDataSetChanged();
                ViewHelper.moveToBack(progress, listView);
            }

        }
    }
}
