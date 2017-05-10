package com.example.trandainhan.orderapp;

import android.app.FragmentManager;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.SubMenu;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.trandainhan.orderapp.fragments.FoodFragment;
import com.example.trandainhan.orderapp.fragments.QuanLyDanhMucFragment;
import com.example.trandainhan.orderapp.models.DanhMuc;

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
import java.util.Collections;
import java.util.Comparator;

import static com.example.trandainhan.orderapp.UrlList.GET_DANH_MUC;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    Menu menu;

    // List
    ArrayList<DanhMuc> danhMucs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        menu = navigationView.getMenu();

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

        int id = item.getItemId();

        if (id == R.id.menu_quan_ly_danh_muc) {

            QuanLyDanhMucFragment quanLyDanhMucFragment = QuanLyDanhMucFragment.newInstance(this);
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_frame, quanLyDanhMucFragment).commit();

        } else {
            int groupId = item.getGroupId();
            if (groupId == R.id.menu_danhmuc) {
                String tenDanhMuc = String.valueOf(item.getTitle());

                for (DanhMuc danhMuc : danhMucs) {
                    if (danhMuc.tenDanhMuc.equals(tenDanhMuc)) {
                        // navigate to
                        FoodFragment foodFragment = FoodFragment.newInstance(danhMuc.danhMucId, this);
                        FragmentManager fragmentManager = getFragmentManager();
                        fragmentManager.beginTransaction().replace(R.id.content_frame, foodFragment).commit();
                        break;
                    }
                }
            }
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    class LoadDanhMucTask extends AsyncTask<Void, String, ArrayList<DanhMuc>> {

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

            Collections.sort(danhMucs, new Comparator<DanhMuc>() {
                @Override
                public int compare(DanhMuc o1, DanhMuc o2) {
                    return o1.tenDanhMuc.compareTo(o2.tenDanhMuc);
                }
            });

            MainActivity.this.danhMucs = danhMucs;

            // start update ui
            SubMenu subMenu = menu.findItem(R.id.menu_danhmuc).getSubMenu();
            subMenu.removeGroup(R.id.menu_danhmuc);
            for (DanhMuc danhMuc : danhMucs) {
                new LoadIconForMenuItemTask(subMenu.add(R.id.menu_danhmuc, Menu.NONE, 0, danhMuc.tenDanhMuc), danhMuc.hinh);
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
