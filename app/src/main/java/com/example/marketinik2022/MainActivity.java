package com.example.marketinik2022;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.SearchView;
import android.widget.Toast;

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

import com.example.marketinik2022.Adapters.CategoryAdapter;
import com.example.marketinik2022.Adapters.PostAdapter;
import com.example.marketinik2022.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.slider.Slider;
import com.mancj.materialsearchbar.MaterialSearchBar;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

import Models.Category;
import Models.Post;
import Models.Product;
import Models.Stores;

public class MainActivity extends AppCompatActivity {

    public final String TAG = "MainActivity";

    final FragmentManager fragmentManager = getSupportFragmentManager();
    public BottomNavigationView bottomNavigationView;
    Toolbar toolbar;
    NavigationView nvDrawer;
    private DrawerLayout mDrawer;
    private ActionBarDrawerToggle drawerToggle;
    private View recyclerView;
    Slider imageList;
    private RecyclerView rvPosts;
    private RecyclerView rvLikes;
    protected PostAdapter adapter;
    protected List<Post> allPosts;
    Boolean mFirstLoad;
    private MaterialSearchBar searchBar;
    private SearchView searchView;


    ActivityMainBinding binding;
    CategoryAdapter categoryAdapter;
    ArrayList<Post> posts;
    ArrayAdapter<Post> postArrayAdapter;


    ArrayList<Category> categories;
    ParseQuery query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //  binding= ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_main);

        // initCategories();
        //0  initProducts();


        searchView = findViewById(R.id.searchView);
        rvPosts = findViewById(R.id.rvPosts);
        allPosts = new ArrayList<>();
        adapter = new PostAdapter(MainActivity.this, allPosts);
        mFirstLoad = true;
        rvPosts.setAdapter(adapter);
        rvPosts.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        rvLikes = findViewById(R.id.rvLikes);
        queryLikes();
        queryPosts();

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

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);
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

    private void queryLikes() {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Post");
        query.whereLessThan("likes", 50);
        query.findInBackground((objects, e) -> {
            if(e == null){
                for (ParseObject result : objects) {
                    Log.d("Object found ",result.getObjectId());
                }
            }else{
                Toast.makeText(this, "Error: "+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }



    //query for the posts
    protected void queryPosts() {
        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
        query.include(Post.KEY_USER);
        query.setLimit(20);
        query.addDescendingOrder(Product.KEY_CREATED_KEY);
        query.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> posts, ParseException e) {
                if (e != null) {
                    Log.e(TAG, "Issue with posts", e);
                    return;
                }
                for (Post post : posts) {
                    Log.i(TAG, "Post:  " + post.getDescription() + "User: " + post.getName()+ "Prix: " + post.getPrice());                    post.getDescription().toString();
                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String description) {
                        if (posts.contains(description)) {
                            adapter.getFilter().filter(query);
                        } else {
                            Toast.makeText(MainActivity.this, "ddfgf", Toast.LENGTH_SHORT).show();
                        }
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String description) {
                        adapter.getFilter().filter(query);
                        return false;
                    }
                });

                }
                allPosts.addAll(posts);
                adapter.notifyDataSetChanged();
            }
        });
        // setting grid layout manager to implement grid view.
        // in this method '2' represents number of columns to be displayed in grid view.
        GridLayoutManager layoutManager = new GridLayoutManager(this, 1, GridLayoutManager.HORIZONTAL,false);

        rvPosts.setLayoutManager(layoutManager);
        rvPosts.setAdapter(adapter);
    }





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
                        for (Stores user : stores) {
                            if (e == null) {
                                if (user.getStatus().equals(true)) {
                                    showAlert("Seller ", "Welcome " + user.getStatus());
                                    Intent intent = new Intent(MainActivity.this, MyStores.class);

                                    startActivity(intent);
                                } else {
                                    showAlert("Seller", "Sorry" + user.getStatus());

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

  //  void initCategories(){

    //}
    //void initProducts(){
    //    products= new ArrayList<>();
     //   products.add(new Product(""))
//
     //   productAdapter= new ProductAdapter(this, products);
       // GridLayoutManager layoutManager= new GridLayoutManager(this, 2);
      //  binding.rvPosts.setAdapter(productAdapter);
    //}
}

