package com.example.assignment3;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Signup extends AppCompatActivity {
    EditText et_signup_username,et_signup_pswd;
    Button btn_signup;
    TextView tv_tologin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signup);

        MyDB myDB = new MyDB(this);


        et_signup_pswd = findViewById(R.id.et_signup_pswd);
        et_signup_username=findViewById(R.id.et_signup_username);
        btn_signup=findViewById(R.id.btn_signup);
        tv_tologin = findViewById(R.id.tv_tologin);

        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pswd, username;
                pswd = et_signup_pswd.getText().toString().trim();
                username = et_signup_username.getText().toString().trim();
                myDB.addUser(username,pswd);
                Toast.makeText(getApplicationContext(), "Signup Successful", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(Signup.this, Login.class);
                startActivity(intent);
                finish();


            }
        });

        tv_tologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Signup.this, Login.class);
                startActivity(intent);
                finish();
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}