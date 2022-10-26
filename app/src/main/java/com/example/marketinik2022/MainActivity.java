package com.example.marketinik2022;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.marketinik2022.Adapters.PostAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.slider.Slider;
import com.mancj.materialsearchbar.MaterialSearchBar;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

import Models.Post;
import Models.Stores;

public class MainActivity extends AppCompatActivity {

    public final String TAG = "MainActivity";

    final FragmentManager fragmentManager = getSupportFragmentManager();
    public BottomNavigationView bottomNavigationView;
    Toolbar toolbar;
    NavigationView nvDrawer;
    private DrawerLayout mDrawer;
    private ActionBarDrawerToggle drawerToggle;
    //private FragmentTransaction fragmentTransaction;
    private View recyclerView;
    Slider imageList;
    private RecyclerView rvPosts;
    protected PostAdapter adapter;
    protected List<Post> allPosts;
    Boolean mFirstLoad;
    private MaterialSearchBar searchBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        searchBar=findViewById(R.id.searchBar);
        rvPosts = findViewById(R.id.rvPosts);
        allPosts = new ArrayList<>();
        adapter = new PostAdapter(MainActivity.this, allPosts);
        mFirstLoad=true;
        rvPosts.setAdapter(adapter);
        rvPosts.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        queryPosts();
        //queryPosts1();
        // Initialize and assign variable
        // Set a Toolbar to replace the ActionBar.
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        // This will display an Up icon (<-), we will replace it with hamburger later
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Find our drawer view
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerToggle = setupDrawerToggle();

        // Setup toggle to display hamburger icon with nice animation
        drawerToggle.setDrawerIndicatorEnabled(true);
        drawerToggle.syncState();


        // Find our drawer view
        nvDrawer = (NavigationView) findViewById(R.id.nvView);
        // Setup drawer view
        setupDrawerContent(nvDrawer);
        // Tie DrawerLayout events to the ActionBarToggle
        mDrawer.addDrawerListener(drawerToggle);

        BottomNavigationView bottomNavigationView=findViewById(R.id.bottomNavigation);
        // Set Home selected


        // Perform item selected listener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_favorite:
                        startActivity(new Intent(getApplicationContext(), FavoriteActivity.class));
                        overridePendingTransition(0, 0);
                       return true;

                    case R.id.action_home:
                      return true;

                    case R.id.action_profile:
                        startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
                        overridePendingTransition(0, 0);
                        return true;



                }
               return false;
            }
        });


    }

    //query for the posts
    protected void queryPosts() {
        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
        query.include(Post.KEY_USER);
        query.setLimit(20);
        query.addDescendingOrder(Post.KEY_CREATED_KEY);
        query.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> posts, ParseException e) {
                if (e != null) {
                    Log.e(TAG, "Issue with posts", e);
                    return;
                }
                for (Post post : posts) {
                    Log.i(TAG, "Post:  " + post.getDescription() + "User: " + post.getName()+ "Prix: " + post.getPrice());
                 // stores1.getDescription().toString()
                }
                allPosts.addAll(posts);
                adapter.notifyDataSetChanged();
            }
        });
        // setting grid layout manager to implement grid view.
        // in this method '2' represents number of columns to be displayed in grid view.
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL,false);

        rvPosts.setLayoutManager(layoutManager);
        rvPosts.setAdapter(adapter);
    }
    //Search



        private ActionBarDrawerToggle setupDrawerToggle() {
            // NOTE: Make sure you pass in a valid toolbar reference.  ActionBarDrawToggle() does not require it
            // and will not render the hamburger icon without it.
            return new ActionBarDrawerToggle(this, mDrawer, toolbar, R.string.drawer_open,  R.string.drawer_close);
        }
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_switch,menu);
        return true;

    }
        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            if (item.getItemId() == R.id.switchitem) {
                ParseQuery<Stores> query = ParseQuery.getQuery(Stores.class);
                query.include(Stores.KEY_USER);
                query.findInBackground(new FindCallback<Stores>() {
                    @Override
                    public void done(List<Stores> stores, ParseException e) {
                        for (Stores store : stores) {
                            if (e == null) {
                                if (store.getStatus().equals(true)) {
                                    showAlert("Seller ", "Welcome " + store.getStatus());
                                    Intent intent = new Intent(MainActivity.this, MyStores.class);

                                    startActivity(intent);
                                } else {
                                    showAlert("Seller", "Sorry" + store.getStatus());

                                }
                            }
                        }

                    }
                });
            }
                // The action bar home/up action should open or close the drawer.
             //   switch (item.getItemId()) {

                   // case android.R.id.home:
                      //  mDrawer.openDrawer(GravityCompat.START);





                return super.onOptionsItemSelected(item);

            }

    private void showAlert(String title, String message) {
        AlertDialog.Builder builder= new AlertDialog.Builder(MainActivity.this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("Ok", (dialog, which) -> {
                    dialog.cancel();
                });
                AlertDialog ok= builder.create();
                ok.show();


    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });
    }

    public void selectDrawerItem(MenuItem menuItem) {
        // Create a new fragment and specify the fragment to show based on nav item clicked
        Fragment fragment = null;
        Class fragmentClass;
        switch (menuItem.getItemId()) {
            case R.id.nav_first_fragment:
                fragmentClass = FirstFragment.class;
                break;
            case R.id.nav_second_fragment:
                fragmentClass = SecondFragment.class;
                break;
            default:
                fragmentClass = FirstFragment.class;
        }

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();

        // Highlight the selected item has been done by NavigationView
        menuItem.setChecked(true);
        // Set action bar title
        setTitle(menuItem.getTitle());
        // Close the navigation drawer
        mDrawer.closeDrawers();
    }

    // ...
}

