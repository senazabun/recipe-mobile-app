package com.example.myapplication;


import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class CookListActivity extends AppCompatActivity {

    List<String> imageUrls = new ArrayList<String>();
    List<String> names = new ArrayList<String>();
    List<String> descriptions = new ArrayList<String>();
    List<String> videoUrls = new ArrayList<String>();

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

        getRecipes();
    }

    private void getRecipes() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("recipes")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(CookListActivity.this, String.valueOf(task.getResult().size()), Toast.LENGTH_SHORT).show();
                            if (!task.getResult().isEmpty()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    String imageUrl = (String) document.getData().get("image");
                                    if (imageUrl == null) {
                                        imageUrl = "https://www.foodandwine.com/thmb/fjNakOY7IcuvZac1hR3JcSo7vzI=/1500x0/filters:no_upscale():max_bytes(150000):strip_icc()/FAW-recipes-pasta-sausage-basil-and-mustard-hero-06-cfd1c0a2989e474ea7e574a38182bbee.jpg";
                                    }

                                    imageUrls.add(imageUrl);
                                    names.add((String) document.getData().get("name"));
                                    descriptions.add((String) document.getData().get("description"));
                                    videoUrls.add((String) document.getData().get("videoUrl"));
                                }
                            }

                        }

                        lView = (ListView) findViewById(R.id.cookListView);
                        lAdapter = new ListAdapter(CookListActivity.this, imageUrls, names, descriptions);
                        lView.setAdapter(lAdapter);

                        lView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                Toast.makeText(CookListActivity.this, names.get(i), Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), CookRecipeDetailActivity.class);
                                intent.putExtra("name", names.get(i));
                                intent.putExtra("description", descriptions.get(i));
                                intent.putExtra("videoUrl", videoUrls.get(i));
                                startActivity(intent);
                            }
                        });
                    }
                });
    }
}