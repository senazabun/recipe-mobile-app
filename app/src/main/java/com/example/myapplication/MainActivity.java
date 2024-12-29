package com.example.myapplication;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.VideoView;

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

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private Button btn1;
    private int recordSize = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btn1 = findViewById(R.id.btn1);
        VideoView simpleVideoView = (VideoView) findViewById(R.id.videoView2);

        generateRecipe();

        simpleVideoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.animation));
        simpleVideoView.setOnPreparedListener(mp -> mp.setLooping(true));
        simpleVideoView.start();

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CookListActivity.class);
                startActivity(intent);
            }
        });
    }

    private void generateRecipe() {
        deleteRecipes();
    }

    private void deleteRecipes() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("recipes")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        recordSize = task.getResult().size();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            db.collection("recipes").document(document.getId()).delete();
                        }
                        createRecipes();
                    }
                });
    }

    private void createRecipes() {

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("recipes")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        recordSize = task.getResult().size();
                        if (task.isSuccessful()) {
                            Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_LONG).show();
                            if (recordSize == 0) {
                                for (int i = 0; i < 1; i++) {
                                    Map<String, Object> recipe = new HashMap<>();
                                    recipe.put("name", "Chicken Noodles");
                                    recipe.put("description", "lorem ipsum");
                                    recipe.put("image", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRWcuUShgRtzSQlS88X65oYviWamLirEKi99A&s");
                                    recipe.put("videoUrl", "https://www.youtube.com/watch?v=mhDJNfV7hjk&t=1s&ab_channel=GordonRamsay");

                                    Map<String, Object> recipe2 = new HashMap<>();
                                    recipe2.put("name", "Hamburger");
                                    recipe2.put("description", "hamburger desc");
                                    recipe2.put("image", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQD0_jlLGG0O_c1pPmnhEI-nDYvZY67pd-uOg&s");
                                    recipe2.put("videoUrl", "https://www.youtube.com/watch?v=_6BrRB8VCvo&ab_channel=Epicurious");

                                    db.collection("recipes").add(recipe);
                                    db.collection("recipes").add(recipe2);
                                }
                            }
                            Intent intent = new Intent(getApplicationContext(), CookListActivity.class);
                            startActivity(intent);
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
    }
}