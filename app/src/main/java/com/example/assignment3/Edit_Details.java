package com.example.assignment3;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


public class Edit_Details extends AppCompatActivity {
    EditText et_username, et_sitename, et_siteurl, et_pswd;
    Button btn_delete, saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_details);

        Intent intent = getIntent();
        int deleteId = intent.getIntExtra("delete_id", -1);
        String deleteSiteName = intent.getStringExtra("delete_site_name");
        String deleteSiteUrl = intent.getStringExtra("delete_site_url");
        String deleteLogin = intent.getStringExtra("delete_login");
        String deletePassword = intent.getStringExtra("delete_password");

        et_username = findViewById(R.id.et_username);
        et_sitename = findViewById(R.id.et_sitename);
        et_siteurl = findViewById(R.id.et_siteurl);
        et_pswd = findViewById(R.id.et_pswd);

        // Set the text from the intent extras
        et_sitename.setText(deleteSiteName);
        et_siteurl.setText(deleteSiteUrl);
        et_username.setText(deleteLogin);
        et_pswd.setText(deletePassword);

        btn_delete = findViewById(R.id.btn_delete);
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Use Edit_Details.this to refer to the activity context correctly
                MyDB myDB = new MyDB(Edit_Details.this);
                myDB.deletePassword(deleteId);

                // Redirect back to the PasswordListActivity
                Intent returnIntent = new Intent(Edit_Details.this, PasswordListActivity.class);
                startActivity(returnIntent);
                finish();
            }
        });

        saveButton = findViewById(R.id.saveButton);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = et_username.getText().toString();
                String sitename = et_sitename.getText().toString();
                String siteurl = et_siteurl.getText().toString();
                String password = et_pswd.getText().toString();

                MyDB myDB = new MyDB(Edit_Details.this);
                myDB.updatePassword(deleteId, sitename, siteurl, username, password);

                Intent returnIntent = new Intent(Edit_Details.this, PasswordListActivity.class);
                startActivity(returnIntent);
                finish();
            }
        });
    }


}
