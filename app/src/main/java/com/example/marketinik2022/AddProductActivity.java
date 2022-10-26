package com.example.marketinik2022;

import static android.content.ContentValues.TAG;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.File;

import Models.Post;

public class AddProductActivity extends AppCompatActivity {
    public BottomNavigationView bottomNavigationView;
    private Button takePic;
    private final int STORAGE_PERMISSION_CODE = 23;
    private final int GALLERY_REQUEST_CODE = 42;
    public static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1034;
    private EditText etDescription;
    private EditText tvPrice;
    private ImageView btnCaptureImage;
    private ImageView ivPostImage;
    private Button btnSubmit;
    private ImageView imgView;


    private File photoFile;
    private String photoFileName = "photo.jpg";
    private Post post;

    public void showAlertDialogButtonClicked(View view ) {

        //setup the alert builder
        AlertDialog.Builder builder = new AlertDialog.Builder(AddProductActivity.this);
        //add the buttons
        builder.setPositiveButton("TAKE PICTURE", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                launchCamera();
            }
        });
        //create and show the alert dialog
        //Creating dialog box
        AlertDialog alert = builder.create();
        //Setting the title manually
        //alert.setTitle("AlertDialogExample");
        alert . show ();
    }






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);

        takePic = findViewById(R.id.takePic);
        etDescription = findViewById(R.id.etDescription);
        ivPostImage = findViewById(R.id.ivPostImage);
        btnSubmit = findViewById(R.id.btnSubmit);
        imgView = findViewById(R.id.imgView);
        tvPrice = findViewById(R.id.tvprice);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        // Set Home selected
        bottomNavigationView.setSelectedItemId(R.id.action_compose);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_home:
                        startActivity(new Intent(getApplicationContext(), MyStores.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.action_compose:
                        return true;
                }
                return false;
            }
        });
        takePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlertDialogButtonClicked(view);
                if (imgView != null) {
                    etDescription.setVisibility(View.VISIBLE);
                    tvPrice.setVisibility(View.VISIBLE);
                    btnSubmit.setVisibility(View.VISIBLE);
                }
            }
        });
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Description = etDescription.getText().toString();
                String Price= tvPrice.getText().toString();
                if (Description.isEmpty()) {
                    Toast.makeText(AddProductActivity.this, "Description cannot be empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (Price.isEmpty()){
                    Toast.makeText(AddProductActivity.this, "Price cannot be empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (photoFile == null || imgView.getDrawable() == null) {
                    Toast.makeText(AddProductActivity.this, "There is no image!", Toast.LENGTH_SHORT).show();
                }
                ParseUser currentUser = ParseUser.getCurrentUser();
                savePost(Description, currentUser, photoFile, Price);
            }
        });
    }
    private void launchCamera() {
        // create Intent to take a picture and return control to the calling application
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Create a File reference for future access
        photoFile = getPhotoFileUri(photoFileName);

        // wrap File object into a content provider
        // required for API >= 24
        // See https://guides.codepath.com/android/Sharing-Content-with-Intents#sharing-files-with-api-24-or-higher
        Uri fileProvider = FileProvider.getUriForFile(AddProductActivity.this, "com.codepath.fileprovider", photoFile);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider);

        // If you call startActivityForResult() using an intent that no app can handle, your app will crash.
        // So as long as the result is not null, it's safe to use the intent.
        if (intent.resolveActivity(AddProductActivity.this.getPackageManager()) != null) {
            // Start the image capture intent to take photo
            startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                //by this we have the camera photo on disk
                Bitmap takenImage = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
                //RESIZE BITMAP, see the section below
                //Load the taken image into a preview
                imgView.setImageBitmap(takenImage);

            } else {//Result as failure
                Toast.makeText(AddProductActivity.this, "Picture wasn't taken", Toast.LENGTH_SHORT).show();
            }
        }
    }
    // Returns the File for a photo stored on disk given the fileName
    public File getPhotoFileUri(String fileName) {
        // Get safe storage directory for photos
        // Use `getExternalFilesDir` on Context to access package-specific directories.
        // This way, we don't need to request external read/write runtime permissions.
        File mediaStorageDir = new File(AddProductActivity.this.getExternalFilesDir(Environment.DIRECTORY_PICTURES), TAG);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()){
            Log.d(TAG, "failed to create directory");
        }

        // Return the file target for the photo based on filename
        return new File(mediaStorageDir.getPath() + File.separator + fileName);

    }



    private void savePost(String Description, ParseUser currentUser, File photoFile, String Prix) {
        Post post= new Post();
        post.setDescription(Description);
        post.setPrice(Prix);
        post.setImage(new ParseFile(photoFile));
        post.setUser(currentUser);
        post.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e !=null){
                    Log.e(TAG, "Error while saving", e);
                    Toast.makeText(AddProductActivity.this, "Error while saving!", Toast.LENGTH_SHORT).show();
                }
                Log.i(TAG, "Post save was successful!");
                etDescription.setText("");
                tvPrice.setText("");
                imgView.setImageResource(0);
            }
        });
    }
}