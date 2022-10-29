package com.example.marketinik2022.Adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.marketinik2022.R;

import java.util.List;

import Models.Post;


public class LikesAdapter extends RecyclerView.Adapter<LikesAdapter.ViewHolder>{


    private Context context;
    private List<likes> likes;

    public LikesAdapter(Context context, List<Like> likes) {
        this.context = context;
        this.posts = posts;
    }


    @NonNull
    @Override
    public LikesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_likes, parent, false);
        return new LikesAdapter.ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull LikesAdapter.ViewHolder holder, int position) {
        Like like = posts.get(position);
        holder.bind(post);
    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
