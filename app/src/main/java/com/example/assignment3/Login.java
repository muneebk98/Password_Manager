package com.example.assignment3;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Login extends AppCompatActivity {

    EditText et_login_username, et_login_pswd;
    Button btn_login;
    TextView tv_tosignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        MyDB myDB = new MyDB(this);

        et_login_pswd = findViewById(R.id.et_login_pswd);
        et_login_username= findViewById(R.id.et_login_username);
        btn_login = findViewById(R.id.btn_login);
        tv_tosignup = findViewById(R.id.tv_tosignup);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = et_login_username.getText().toString().trim();
                String password = et_login_pswd.getText().toString().trim();

                if (myDB.checkUser(username, password)) {

                    int userId = myDB.getUserId(username, password);
                    SharedPreferences sharedPref = getSharedPreferences("MyApp", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putInt("userId", userId);
                    editor.apply();

                    Intent intent = new Intent(Login.this, Manager.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(Login.this, "Invalid username or password", Toast.LENGTH_LONG).show();
                }
            }
        });

        tv_tosignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, Signup.class);
                startActivity(intent);
                finish();
            }
        });

    }
}
