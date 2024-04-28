package com.example.assignment3;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.List;

public class PasswordListActivity extends AppCompatActivity implements PasswordListAdapter.OnItemDeleteClickListener {
    private MyDB myDB;
    private RecyclerView passwordRecyclerView;
    private PasswordListAdapter passwordListAdapter;
    private List<Password> passwordList = new ArrayList<>();
    FloatingActionButton add_password_fab;
    ImageButton backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_list2);

        add_password_fab = findViewById(R.id.add_password_fab);
        backButton = findViewById(R.id.backButton);

        add_password_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PasswordListActivity.this, Add_Details.class);
                startActivity(intent);
                finish();
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PasswordListActivity.this, Manager.class);
                startActivity(intent);
                finish();
            }
        });

        myDB = new MyDB(this);

        passwordRecyclerView = findViewById(R.id.password_recycler_view);
        passwordRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        SharedPreferences sharedPref = getSharedPreferences("MyApp", Context.MODE_PRIVATE);
        int userId = sharedPref.getInt("userId", -1);

        if (userId != -1) {
            passwordList = myDB.getPasswords(userId);
            passwordListAdapter = new PasswordListAdapter(passwordList, this);
            passwordRecyclerView.setAdapter(passwordListAdapter);
        }
    }

    @Override
    public void onItemDelete(int position) {
        Password passwordToDelete = passwordList.get(position);

        Intent intent = new Intent(PasswordListActivity.this, Edit_Details.class);
        intent.putExtra("delete_id", passwordToDelete.getUserId());
        intent.putExtra("delete_site_name", passwordToDelete.getSiteName());
        intent.putExtra("delete_site_url", passwordToDelete.getSiteUrl());
        intent.putExtra("delete_login", passwordToDelete.getLogin());
        intent.putExtra("delete_password", passwordToDelete.getSitePassword());
        startActivity(intent);
    }

}
