package com.example.trandainhan.orderapp.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trandainhan.orderapp.MainActivity;
import com.example.trandainhan.orderapp.R;
import com.example.trandainhan.orderapp.adapter.ChiTietDonHangAdapter;
import com.example.trandainhan.orderapp.adapter.DonHangAdapter;
import com.example.trandainhan.orderapp.api.Api;
import com.example.trandainhan.orderapp.api.ResponseData;
import com.example.trandainhan.orderapp.api.UpdateTinhTrangDonHangForm;
import com.example.trandainhan.orderapp.helpers.Storage;
import com.example.trandainhan.orderapp.helpers.ViewHelper;
import com.example.trandainhan.orderapp.models.DonHang;
import com.example.trandainhan.orderapp.models.TinhTrangDonHang;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DaXuLyFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DaXuLyFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DaXuLyFragment extends Fragment {

    @Bind(R.id.lstDonHang)
    ListView listView;

    @Bind(R.id.progress)
    LinearLayout progress;

    MainActivity context;

    DonHangAdapter donHangAdapter;

    private OnFragmentInteractionListener mListener;

    public DaXuLyFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment DaXuLyFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DaXuLyFragment newInstance(MainActivity context) {
        DaXuLyFragment fragment = new DaXuLyFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_da_xu_ly, container, false);
        ButterKnife.bind(this, rootView);
        listView.setAdapter(donHangAdapter);

        reload();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final DonHang donHang = donHangAdapter.donHangs.get(position);
                LayoutInflater li = LayoutInflater.from(context);
                View promptsView = li.inflate(R.layout.layout_don_hang_chi_tiet, null);

                TextView txtTenKhach = (TextView) promptsView.findViewById(R.id.txtTenKhach);
                TextView txtSdtKhach = (TextView) promptsView.findViewById(R.id.txtSdtKhach);
                TextView txtDiaChiKhach = (TextView) promptsView.findViewById(R.id.txtDiaChiKhach);
                ListView listView = (ListView) promptsView.findViewById(R.id.lstChiTietDonHang);

                txtTenKhach.setText(donHang.khachHang.ten);
                txtSdtKhach.setText(donHang.khachHang.sdt);
                txtDiaChiKhach.setText(donHang.khachHang.diaChi);
                listView.setAdapter(new ChiTietDonHangAdapter(context, donHang.chiTietDonHangs));

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

                // set prompts.xml to alertdialog builder
                alertDialogBuilder.setView(promptsView);

                // set buttons
                alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // leave empty
                    }
                });

                alertDialogBuilder.setNegativeButton("Xóa đơn hàng", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // leave empty
                    }
                });

                // create alert dialog
                final AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();

                final Button postitiveButton = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
                final Button negativeButton = alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE);

                negativeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new DeleteDonHangTask(donHang.donHangId, alertDialog,postitiveButton,negativeButton).execute();
                    }
                });

            }
        });


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

            ResponseData<DonHang[]> responseData = Api.getDonHangDaXuLy();

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

    class GiaoHangTask extends AsyncTask<Void, Void, ResponseData<DonHang>> {
        int id;

        Dialog dialog;

        Button positive;
        Button negative;

        public GiaoHangTask(int id, Dialog dialog, Button positive, Button negative) {
            this.id = id;
            this.dialog = dialog;
            this.positive = positive;
            this.negative = negative;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            positive.setEnabled(false);
            negative.setEnabled(false);
        }

        @Override
        protected ResponseData<DonHang> doInBackground(Void... params) {
            ResponseData<DonHang> responseData = Api.updateTinhTrangDonHang(
                    new UpdateTinhTrangDonHangForm(Storage.getQuanLy(), id, TinhTrangDonHang.DangGiaoHang));

            return responseData;
        }


        @Override
        protected void onPostExecute(ResponseData<DonHang> donHangResponseData) {
            super.onPostExecute(donHangResponseData);
            if (donHangResponseData.status != 0) {
                Toast.makeText(context, donHangResponseData.message, Toast.LENGTH_SHORT).show();
                positive.setEnabled(true);
                negative.setEnabled(true);
            } else {
                dialog.dismiss();
                reload();
            }
        }
    }

    class HuyDonHangTask extends AsyncTask<Void, Void, ResponseData<DonHang>> {
        int id;

        Dialog dialog;

        Button positive;
        Button negative;

        public HuyDonHangTask(int id, Dialog dialog, Button positive, Button negative) {
            this.id = id;
            this.dialog = dialog;
            this.positive = positive;
            this.negative = negative;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            positive.setEnabled(false);
            negative.setEnabled(false);
        }

        @Override
        protected ResponseData<DonHang> doInBackground(Void... params) {
            ResponseData<DonHang> responseData = Api.updateTinhTrangDonHang(
                    new UpdateTinhTrangDonHangForm(Storage.getQuanLy(), id, TinhTrangDonHang.DaHuy));

            return responseData;
        }


        @Override
        protected void onPostExecute(ResponseData<DonHang> donHangResponseData) {
            super.onPostExecute(donHangResponseData);
            if (donHangResponseData.status != 0) {
                Toast.makeText(context, donHangResponseData.message, Toast.LENGTH_SHORT).show();
                positive.setEnabled(true);
                negative.setEnabled(true);
            } else {
                dialog.dismiss();
                reload();
            }
        }
    }

    class DeleteDonHangTask extends AsyncTask<Void, Void, ResponseData<DonHang>> {
        int id;

        Dialog dialog;

        Button positive;
        Button negative;

        public DeleteDonHangTask(int id, Dialog dialog, Button positive, Button negative) {
            this.id = id;
            this.dialog = dialog;
            this.positive = positive;
            this.negative = negative;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            positive.setEnabled(false);
            negative.setEnabled(false);
        }

        @Override
        protected ResponseData<DonHang> doInBackground(Void... params) {
            ResponseData<DonHang> responseData = Api.deleteDonHang(id);

            return responseData;
        }


        @Override
        protected void onPostExecute(ResponseData<DonHang> donHangResponseData) {
            super.onPostExecute(donHangResponseData);
            if (donHangResponseData.status != 0) {
                Toast.makeText(context, donHangResponseData.message, Toast.LENGTH_SHORT).show();
                positive.setEnabled(true);
                negative.setEnabled(true);
            } else {
                dialog.dismiss();
                reload();
            }
        }
    }
}
