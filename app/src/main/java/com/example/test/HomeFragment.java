package com.example.test;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;


import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;


public class HomeFragment extends Fragment {
    ImageSlider imageSlider;
    private Toolbar toolbar;
    NavigationView nvDrawer;
    private DrawerLayout mDrawer;
    private ActionBarDrawerToggle drawerToggle;
    private TextView idcategory;
    private FirstFragment firstFragment;
    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_home, container, false);
        imageSlider=view.findViewById(R.id.image_slider);
        ArrayList<SlideModel> imagelist=new ArrayList<>();

        imagelist.add(new SlideModel("https://bit.ly/2YoJ77H", null));
        imagelist.add(new SlideModel("https://bit.ly/2YoJ77H", null));
        imagelist.add(new SlideModel("https://bit.ly/2YoJ77H", null));

        imageSlider.setImageList(imagelist);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
       // toolbar = view.findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        // This will display an Up icon (<-), we will replace it with hamburger later
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Find our drawer view




        idcategory = view.findViewById(R.id.idcategory);
        //  cart=findViewById(R.id.cart);

        // Find our drawer view
//        nvDrawer = view.findViewById(R.id.nvView);
        // Setup drawer view

    }

   // private void setupDrawerContent(NavigationView nvDrawer) {
    //}




   // private void setSupportActionBar(Toolbar toolbar) {
  //  }



    }
    
    
