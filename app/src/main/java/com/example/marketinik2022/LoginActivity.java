package com.example.marketinik2022;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class LoginActivity extends AppCompatActivity {
    public static final String TAG = "LoginActivity";
    private EditText etUsername;
    private EditText etPassword;
    private Button btnLogin;
    private Button btnSignUp;
    View shape;

    private RelativeLayout containerRL;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // on below line we are initializing variables with ids.
        containerRL = findViewById(R.id.style);
        // on below line we are setting background for
        // our relative layout on below line.
        containerRL.setBackground(getResources().getDrawable(R.drawable.back));

        if (ParseUser.getCurrentUser() != null) {
            goMainActivity();

        }
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnSignUp = findViewById(R.id.btnSignUp);
        btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "Click on Login button");
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                loginUser(username, password);
            }


            private void loginUser(String username, String password) {
                Log.i(TAG, "Trying to login user" + username);

                ParseUser.logInInBackground(username, password, new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException e) {
                        if (e != null) {
                            //Todo: Better error handling
                            Log.e(TAG, "Issue with login", e);
                            return;
                        }
                        //Navigate to the main activity if the user has signed properly
                        goMainActivity();
                    }
                });
            }

        });
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username=etUsername.getText().toString();
                String password=etPassword.getText().toString();
                SignUp(username,password);

            }
        private void SignUp(String username, String password) {

            ParseUser user = new ParseUser();
            user.setUsername(username);
            user.setPassword(password);
            user.signUpInBackground(new SignUpCallback() {
                @Override
                public void done(ParseException e) {
                    if(e!=null){
                        Log.e(TAG, "Issue with Signup",e);
                        return;
                    }
                    goMainActivity();
                }
            });
        }

    });
}






    private void goMainActivity() {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();
    }
}
