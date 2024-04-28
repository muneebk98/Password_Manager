package com.example.assignment3;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.example.assignment3.MyDB;
import com.example.assignment3.Password;

public class Add_Details extends AppCompatActivity {
    private MyDB myDB;
    private EditText siteNameEditText;
    private EditText siteUrlEditText;
    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_details);

        myDB = new MyDB(this);

        siteNameEditText = findViewById(R.id.et_ad_sname);
        siteUrlEditText = findViewById(R.id.et_ad_surl);
        usernameEditText = findViewById(R.id.et_ad_username);
        passwordEditText = findViewById(R.id.et_ad_pswd);
        addButton = findViewById(R.id.btn_add);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String siteName = siteNameEditText.getText().toString();
                String siteUrl = siteUrlEditText.getText().toString();
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                if (!siteName.isEmpty() && !siteUrl.isEmpty() && !username.isEmpty() && !password.isEmpty()) {

                    SharedPreferences sharedPref = getSharedPreferences("MyApp", Context.MODE_PRIVATE);
                    int userId = sharedPref.getInt("userId", -1);

                    if (userId != -1) {
                        myDB.addPassword(userId, siteName, siteUrl, username, password);

                        Intent intent = new Intent(Add_Details.this, PasswordListActivity.class);
                        startActivity(intent);
                        finish();
                    }
                } else {
                    Toast.makeText(Add_Details.this, "All fields are required", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}