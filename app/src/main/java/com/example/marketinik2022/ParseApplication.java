package com.example.marketinik2022;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;


import Models.Post;
import Models.Stores;


public class ParseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        ParseObject.registerSubclass(Stores.class);
        ParseObject.registerSubclass(Post.class);
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("cbUgKrx6RNteK23lz8Ci6OfbtbCH4zcOQUD6imgw")
                .clientKey("G1fOTVvXjxj8DGUZdh9pzs5Weqil4ERHjZWfZrvL")
                .server("https://parseapi.back4app.com")
                .build()
        );
    }

}

