package com.example.marketinik2022;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.marketinik2022.Adapters.PostAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

import Models.Post;

public class MyStores extends AppCompatActivity {
    public static final String TAG = "MyStores";
    private RecyclerView rvPosts;
    protected PostAdapter adapter;
    protected List<Post> allPosts;
    Boolean mFirstLoad;
    public BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_stores);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        // Set Home selected
        bottomNavigationView.setSelectedItemId(R.id.action_home);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_compose:
                        startActivity(new Intent(getApplicationContext(), AddProductActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.action_home:
                        return true;
                }
                return false;
            }
        });

    }
}
