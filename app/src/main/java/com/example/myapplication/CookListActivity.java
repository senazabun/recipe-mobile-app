package com.example.myapplication;

import static android.app.PendingIntent.getActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

public class CookListActivity extends AppCompatActivity {

    private ListView listView;
    private ArrayList<String> cookList;
    private ArrayAdapter<String> adapter;

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

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, cookList);
        listView = (ListView) findViewById(R.id.cookListView);

        cookList = new ArrayList<>();
        cookList.addAll(List.of("Apple","Banana","Pineapple"));

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, cookList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CharSequence text = cookList.get((int) id);
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(getApplicationContext(), text, duration);
                toast.show();

                Intent intent = new Intent(getApplicationContext(), CookRecipeDetailActivity.class);
                // TODO: ilgili yemeğin ismini yada id'sini bu intente gönder
                startActivity(intent);
            }
        });
    }
}