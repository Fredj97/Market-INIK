package com.example.marketinik2022;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.marketinik2022.databinding.ActivityProductDetailBinding;
import com.parse.ParseQuery;

import Models.Post;

public class ProductDetail extends AppCompatActivity {
  ActivityProductDetailBinding binding;
    private ImageView ivImage;
    private TextView tvUsername;
    private TextView tvPrice;
    private TextView name;
    private TextView description;
    private Button add_to_cart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

       binding=ActivityProductDetailBinding.inflate(getLayoutInflater());
       setContentView(binding.getRoot());

        ivImage = findViewById(R.id.ivImage);
        tvUsername=findViewById(R.id.tvUsername);
        tvPrice=findViewById(R.id.tvprice);
        name=findViewById(R.id.name);
        add_to_cart=findViewById(R.id.add_to_cart);


        add_to_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(ProductDetail.this,CartActivity.class);
                startActivity(intent);
            }
        });

        queryPosts();

       String name= getIntent().getStringExtra("name");
        String image= getIntent().getStringExtra("image");
        int id= getIntent().getIntExtra("id",0);
        double price= getIntent().getDoubleExtra("Prix",0);



     //   Glide.with(this)
       //   .load(image)
              //.into(binding.image);

       //getSupportActionBar().setTitle(name);

      getSupportActionBar();
    }
    protected void queryPosts() {
        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
        query.include(Post.KEY_PRICE);
        query.include(Post.KEY_DESCRIPTION);
        query.include(Post.KEY_IMAGE);
    }
    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return onSupportNavigateUp();
    }

}