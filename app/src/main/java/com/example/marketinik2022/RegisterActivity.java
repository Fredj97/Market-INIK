package com.example.marketinik2022;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class RegisterActivity extends AppCompatActivity {
    public static final String TAG = "LoginActivity";
    private EditText etUsername;
    private EditText etPassword;
    private EditText etPassword1;
    private Button back;
    private Button btnLogin;
    private Button btnSignUp;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ParseUser user = new ParseUser();

        back = findViewById(R.id.btnLogin);
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        etPassword1 = findViewById(R.id.etPassword1);
        btnSignUp = findViewById(R.id.btnSignUp);

        btnSignUp.setOnClickListener(v -> {
            if (etPassword.getText().toString().equals(etPassword1.getText().toString()) && !TextUtils.isEmpty(etUsername.getText().toString()))
                SignUp(etUsername.getText().toString(), etPassword.getText().toString());
            else
                Toast.makeText(this, "Make sure that the values you entered are correct.", Toast.LENGTH_SHORT).show();
        });

        back.setOnClickListener(v -> finish());

    }

    private void SignUp(String username, String password) {
        progressDialog.show();
        ParseUser user = new ParseUser();
        // Set the user's username and password, which can be obtained by a forms
        user.setUsername(username);
        user.setPassword(password);
        user.signUpInBackground(e -> {
            progressDialog.dismiss();
            if (e == null) {
                showAlert("Successful Sign Up ! You logged in...\n", "Welcome " + username + " !");
            } else {
                ParseUser.logOut();
                Toast.makeText(RegisterActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void showAlert(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        // don't forget to change the line below with the names of your Activities
                        Intent intent = new Intent(RegisterActivity.this, LogoutActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                });
        AlertDialog ok = builder.create();
        ok.show();
    }
}

