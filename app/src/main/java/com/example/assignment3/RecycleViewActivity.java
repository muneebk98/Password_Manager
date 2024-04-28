package com.example.assignment3;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;



import java.util.List;

public class RecycleViewActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecycleBinAdapter recycleBinAdapter;
    ImageButton backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle_view);

        backButton = findViewById(R.id.backButton);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecycleViewActivity.this, Manager.class);
                startActivity(intent);
                finish();
            }
        });

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        MyDB myDB = new MyDB(this);
        List<RecycleBinItem> recycleBinItems = myDB.getRecycleBinItems();
        if (recycleBinItems.isEmpty()) {
            Toast.makeText(this, "The recycle bin is empty", Toast.LENGTH_SHORT).show();
        } else {
            recycleBinAdapter = new RecycleBinAdapter(recycleBinItems);
            recyclerView.setAdapter(recycleBinAdapter);
        }
    }
}