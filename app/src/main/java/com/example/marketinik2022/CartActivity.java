package com.example.marketinik2022;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.marketinik2022.databinding.ActivityCartBinding;

import java.util.ArrayList;

import Models.Post;

public class CartActivity extends AppCompatActivity {

    ActivityCartBinding binding;
  //  CartAdapter adapter;
    ArrayList<Post> posts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityCartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        posts=new ArrayList<>();
       // posts.add(new Post()"","","")
      //  adapter=new CartAdapter(this, posts);

        binding.cartlist.setLayoutManager(new LinearLayoutManager(this));
     //   binding.cartlist.setAdapter(adapter);
    }
}