package com.example.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
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

        VideoView simpleVideoView = (VideoView) findViewById(R.id.videoView2);

        simpleVideoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.animation));
        simpleVideoView.setOnPreparedListener(mp -> mp.setLooping(true));
        simpleVideoView.start();

        generateRecipe();
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
                                Map<String, Object> recipe = new HashMap<>();
                                recipe.put("name", "Chicken Noodles");
                                recipe.put("description", "Cook noodles according to package instructions and set aside. In a pan, sauté diced chicken with garlic, ginger, and soy sauce until cooked. Add chopped vegetables like carrots and bell peppers, stir-fry until tender, then mix in the noodles. Toss with more soy sauce, sesame oil, and optional chili flakes. Serve hot!");
                                recipe.put("image", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRWcuUShgRtzSQlS88X65oYviWamLirEKi99A&s");
                                recipe.put("videoUrl", "https://www.youtube.com/watch?v=mhDJNfV7hjk&t=1s&ab_channel=GordonRamsay");

                                Map<String, Object> recipe2 = new HashMap<>();
                                recipe2.put("name", "Hamburger");
                                recipe2.put("description", "Mix ground beef with salt and pepper, shape into patties, and cook on a hot grill for 3-4 minutes per side. Toast buns, add lettuce, tomato, cheese, condiments, and the patty. Assemble and serve!");
                                recipe2.put("image", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQD0_jlLGG0O_c1pPmnhEI-nDYvZY67pd-uOg&s");
                                recipe2.put("videoUrl", "https://www.youtube.com/watch?v=_6BrRB8VCvo&ab_channel=Epicurious");

                                Map<String, Object> recipe3 = new HashMap<>();
                                recipe3.put("name", "Mac n Cheese");
                                recipe3.put("description", " Cook 2 cups of macaroni in boiling water and drain. In a pan, melt 2 tablespoons of butter, whisk in 2 tablespoons of flour, and cook for 1 minute. Gradually add 2 cups of milk, whisking constantly, until the sauce thickens. Stir in 1½ cups of cheddar cheese and season with salt and pepper. Finally, mix the cooked pasta with the cheese sauce and serve hot.");
                                recipe3.put("image", "https://assets.unileversolutions.com/recipes-v2/110418.jpg");
                                recipe3.put("videoUrl", "https://www.youtube.com/watch?v=O504Ezt8NPc&ab_channel=Smokin%27%26GrillinwithAB");

                                Map<String, Object> recipe4 = new HashMap<>();
                                recipe4.put("name", "Tortellini");
                                recipe4.put("description", "Cook tortellini according to package instructions and drain. In a pan, heat olive oil or butter, sauté garlic, and add cream or tomato sauce. Stir in grated cheese, spinach, or mushrooms for extra flavor. Toss the cooked tortellini in the sauce, season with salt, pepper, and herbs, then serve warm!");
                                recipe4.put("image", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRs7YasVe_aZteHuvgM1qLj1W-7L6vGY2UpNg&s");
                                recipe4.put("videoUrl", "https://www.youtube.com/watch?v=j45bt8ojWR0&ab_channel=ForkettiFood");


                                db.collection("recipes").add(recipe);
                                db.collection("recipes").add(recipe2);
                                db.collection("recipes").add(recipe3);
                                db.collection("recipes").add(recipe4);
                            }
                            Intent intent = new Intent(getApplicationContext(), CookListActivity.class);
                            startActivity(intent);
                        }
                    }
                });
    }
}