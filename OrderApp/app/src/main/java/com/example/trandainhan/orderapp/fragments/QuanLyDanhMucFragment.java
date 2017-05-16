package com.example.trandainhan.orderapp.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.text.InputType;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.example.trandainhan.orderapp.api.AddDanhMucForm;
import com.example.trandainhan.orderapp.api.Api;
import com.example.trandainhan.orderapp.R;
import com.example.trandainhan.orderapp.adapter.DanhMucAdapter;
import com.example.trandainhan.orderapp.api.ResponseData;
import com.example.trandainhan.orderapp.helpers.Storage;
import com.example.trandainhan.orderapp.helpers.ViewHelper;
import com.example.trandainhan.orderapp.models.DanhMuc;
import com.example.trandainhan.orderapp.models.QuanLy;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.example.trandainhan.orderapp.api.Api.addDanhMuc;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link QuanLyDanhMucFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link QuanLyDanhMucFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QuanLyDanhMucFragment extends Fragment {

    @Bind(R.id.btnAddDanhMuc)
    Button btnAddDanhMuc;

    @Bind(R.id.lstDanhMuc)
    ListView listView;

    @Bind(R.id.progress)
    LinearLayout progress;

    public Context context;

    DanhMucAdapter danhMucAdapter;

    private OnFragmentInteractionListener mListener;

    public QuanLyDanhMucFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment QuanLyDanhMucFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static QuanLyDanhMucFragment newInstance(Context context) {
        QuanLyDanhMucFragment fragment = new QuanLyDanhMucFragment();
        fragment.context = context;
        fragment.danhMucAdapter = new DanhMucAdapter(fragment.context, new ArrayList<DanhMuc>(), fragment);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_quan_ly_danh_muc, container, false);
        ButterKnife.bind(this, view);

        listView = (ListView) view.findViewById(R.id.lstDanhMuc);
        listView.setAdapter(danhMucAdapter);
        reload();

        btnAddDanhMuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(QuanLyDanhMucFragment.this.context);
                builder.setTitle("Nhập tên mới");
                // Set up the input
                final EditText input = new EditText(QuanLyDanhMucFragment.this.context);
                // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                input.setInputType(InputType.TYPE_CLASS_TEXT);

                builder.setView(input);

                builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String name = input.getText().toString();
                        if (TextUtils.isEmpty(name)) {
                            return;
                        }
                        AddDanhMucForm addDanhMucForm = new AddDanhMucForm(Storage.getQuanLy(), name);
                        new AddDanhMucTask(addDanhMucForm).execute();
                    }
                });

                builder.show();
            }
        });

        return view;
    }

    // TODO: Rename method, reload argument and hook method into UI event
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

    public void reload() {
        new UpdateDanhMucAdapterTask(danhMucAdapter).execute();
    }

    public void delete(DanhMuc danhMuc){
        new DeleteDanhMucTask(danhMuc).execute();
    }

    class UpdateDanhMucAdapterTask extends AsyncTask<Void, Void, List<DanhMuc>> {

        DanhMucAdapter danhMucAdapter;

        public UpdateDanhMucAdapterTask(DanhMucAdapter danhMucAdapter) {
            this.danhMucAdapter = danhMucAdapter;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            ViewHelper.moveToFront(progress, listView);
        }

        @Override
        protected List<DanhMuc> doInBackground(Void... params) {

            List<DanhMuc> danhMucs = Api.GetDanhMuc();

            return danhMucs;
        }

        @Override
        protected void onPostExecute(List<DanhMuc> danhMucs) {
            super.onPostExecute(danhMucs);

            danhMucAdapter.clear();
            danhMucAdapter.addAll(danhMucs);
            danhMucAdapter.notifyDataSetChanged();

            ViewHelper.moveToBack(progress, listView);
        }
    }

    class AddDanhMucTask extends AsyncTask<Void, Void, ResponseData<DanhMuc>> {

        AddDanhMucForm addDanhMucForm;

        public AddDanhMucTask(AddDanhMucForm addDanhMucForm) {
            this.addDanhMucForm = addDanhMucForm;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            ViewHelper.moveToFront(progress, listView);
        }

        @Override
        protected ResponseData<DanhMuc> doInBackground(Void... params) {

            ResponseData<DanhMuc> danhMucResponseData = Api.addDanhMuc(addDanhMucForm);
            return danhMucResponseData;
        }

        @Override
        protected void onPostExecute(ResponseData<DanhMuc> danhMucResponseData) {
            super.onPostExecute(danhMucResponseData);
            if (danhMucResponseData.status == 0) {
                reload();
            } else{
                Toast.makeText(context, danhMucResponseData.message, Toast.LENGTH_LONG).show();
                ViewHelper.moveToBack(progress, listView);
            }
        }
    }

    class DeleteDanhMucTask extends AsyncTask<Void, Void, ResponseData> {

        DanhMuc danhMuc;


        public DeleteDanhMucTask(DanhMuc danhMuc) {
            this.danhMuc = danhMuc;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            ViewHelper.moveToFront(progress, listView);
        }

        @Override
        protected ResponseData doInBackground(Void... params) {
            ResponseData responseData = Api.deleteDanhMuc(danhMuc.danhMucId);
            return responseData;
        }

        @Override
        protected void onPostExecute(ResponseData responseData) {
            super.onPostExecute(responseData);
            if (responseData.status == 0) {
                reload();
            } else {
                Toast.makeText(getContext(), responseData.message, Toast.LENGTH_LONG);
            }
        }
    }
}
