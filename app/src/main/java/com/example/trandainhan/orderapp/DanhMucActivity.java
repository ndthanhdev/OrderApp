package com.example.trandainhan.orderapp;

import android.app.FragmentManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.example.trandainhan.orderapp.adapter.MonAnAdapter;
import com.example.trandainhan.orderapp.models.DanhMuc;
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
import java.security.acl.Group;
import java.util.ArrayList;

import static android.R.attr.id;
import static com.example.trandainhan.orderapp.UrlList.GET_DANH_MUC;
import static com.example.trandainhan.orderapp.UrlList.GET_MON_AN;

public class DanhMucActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    Menu menu;

    // List
    ArrayList<DanhMuc> danhMucs;

    //Adapter
    public MonAnAdapter monAnAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_muc);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        menu = navigationView.getMenu();

        monAnAdapter = new MonAnAdapter(this, new ArrayList<MonAn>());

        new LoadDanhMucTask().execute();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.danh_muc, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        String tenDanhMuc = String.valueOf(item.getTitle());

        for (DanhMuc danhMuc : danhMucs) {
            if (danhMuc.tenDanhMuc.equals(tenDanhMuc)) {
                // navigate to
                FoodFragment foodFragment = FoodFragment.newInstance(String.valueOf(danhMuc.danhMucId), monAnAdapter);
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.mon_ans_frame, foodFragment).commit();
            }
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public class LoadDanhMucTask extends AsyncTask<Void, String, ArrayList<DanhMuc>> {

        @Override
        protected ArrayList<DanhMuc> doInBackground(Void... params) {

            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {

                URL url = new URL(GET_DANH_MUC);
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


                JSONArray jsonArray = new JSONArray(jsonString);

                ArrayList<DanhMuc> danhMucs = new ArrayList<>();

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    int id = jsonObject.getInt("danhMucId");
                    String name = jsonObject.getString("tenDanhMuc");
                    String hinh = jsonObject.getString("hinh");
                    DanhMuc danhMuc = new DanhMuc(id, name, hinh);
                    danhMucs.add(danhMuc);
                }

                return danhMucs;

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
        protected void onPostExecute(ArrayList<DanhMuc> danhMucs) {
            super.onPostExecute(danhMucs);
            DanhMucActivity.this.danhMucs = danhMucs;
            int i = 0;
            for (DanhMuc danhMuc : danhMucs) {
                new LoadIconForMenuItemTask(menu.add(Menu.NONE, Menu.NONE, i++, danhMuc.tenDanhMuc), danhMuc.hinh);
            }
        }
    }

    class LoadIconForMenuItemTask extends AsyncTask<Void, Integer, Drawable> {
        MenuItem menuItem;
        String urldisplay;

        public LoadIconForMenuItemTask(MenuItem menuItem, String urldisplay) {
            this.menuItem = menuItem;
            this.urldisplay = urldisplay;
        }

        protected Drawable doInBackground(Void... urls) {
            Drawable mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = Drawable.createFromStream(in, "src");
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Drawable result) {
            menuItem.setIcon(result);
        }
    }
}
