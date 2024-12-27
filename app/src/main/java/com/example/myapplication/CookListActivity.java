package com.example.myapplication;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class CookListActivity extends AppCompatActivity {

    int[] images = { R.drawable.cook, R.drawable.cook};

    String[] version = {"Cook", "vook 2"};

    String[] versionNumber = {"1.0", "2"};

    ListView lView;

    ListAdapter lAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cook_list);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        lView = (ListView) findViewById(R.id.cookListView);
        lAdapter = new ListAdapter(CookListActivity.this, version, versionNumber, images);
        lView.setAdapter(lAdapter);

        lView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(CookListActivity.this, version[i]+" "+versionNumber[i], Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), CookRecipeDetailActivity.class);
                // TODO: ilgili yemeğin ismini yada id'sini bu intente gönder
                startActivity(intent);
            }
        });

    }
}