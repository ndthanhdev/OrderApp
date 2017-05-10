package com.example.trandainhan.orderapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.trandainhan.orderapp.models.QuanLy;
import com.example.trandainhan.orderapp.helpers.OkHttpHelper;
import com.example.trandainhan.orderapp.helpers.Storage;
import com.squareup.picasso.Picasso;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;

    ProgressDialog progressDialog;

    @Bind(R.id.input_id)
    EditText _idText;
    @Bind(R.id.input_password)
    EditText _passwordText;
    @Bind(R.id.btn_login)
    Button _loginButton;
    @Bind(R.id.login_image_logo)
    ImageView _image;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        _loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login();
            }
        });
        Picasso.with(this).load(UrlList.LOREMPIXEL_IMAGE).into(_image);
    }

    public void login() {
        Log.d(TAG, "Login");

        _loginButton.setEnabled(false);

        progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.AppTheme);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        String id = _idText.getText().toString();
        String password = _passwordText.getText().toString();

        new LoginTask(id, password).execute();
    }

    @Override
    public void onBackPressed() {
        // Disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void onLoginSuccess() {
        _loginButton.setEnabled(true);

        Storage.Username = _idText.getText().toString();
        Storage.Password = _passwordText.getText().toString();

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();
        progressDialog.dismiss();
        _loginButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String email = _idText.getText().toString();
        String password = _passwordText.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _idText.setError("enter a valid email address");
            valid = false;
        } else {
            _idText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }

    public class LoginTask extends AsyncTask<Void, Void, Boolean> {

        private QuanLy quanLy;

        public LoginTask(String id, String password) {
            quanLy = new QuanLy(id, password);
        }

        @Override
        protected Boolean doInBackground(Void... params) {

            try {
                String responseContent = OkHttpHelper.post(UrlList.LOGIN, quanLy);
                Boolean result = Boolean.valueOf(responseContent);
                return result;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if (aBoolean) {
                onLoginSuccess();
            } else {
                onLoginFailed();
            }
        }
    }


}
