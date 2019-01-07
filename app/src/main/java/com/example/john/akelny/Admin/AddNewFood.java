package com.example.john.akelny.Admin;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.john.akelny.Model.Category;
import com.example.john.akelny.Model.Food;
import com.example.john.akelny.Model.Resturant;
import com.example.john.akelny.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;

public class AddNewFood extends Activity {
    private static final int PICK_IMAGE_REQUEST=1;
    ImageView image;
    FirebaseDatabase database;
    DatabaseReference myRef;
    Button uploadImage;
    ArrayList<String> ResturantName;
    ArrayList<Resturant> resturants;
    Uri Image;
    StorageReference mStorageRef;
    ArrayList<String> CategoryNames;
    ArrayList<Category> Categories;
    Spinner CategoriesSpinner,ResturantsSpinner;
    ArrayAdapter arrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_food);
        ResturantsSpinner=(Spinner)findViewById(R.id.spinnerResturantNameAddFood);
        image = (ImageView)findViewById(R.id.NextFirstPageInFood);
        uploadImage = (Button) findViewById(R.id.UploadImageForNewFood);
        CategoriesSpinner=(Spinner)findViewById(R.id.spinner1);
        uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChoose();
            }
        });
        ResturantName = new ArrayList<String>();
        resturants= new ArrayList<Resturant>();
        image.setOnClickListener(new View.OnClickListener() {
                                     @Override
                                     public void onClick(View v) {
                                         Intent intent = new Intent(AddNewFood.this, AddNewFood2.class);
                                         startActivity(intent);
                                     }
                                 });

            database = FirebaseDatabase.getInstance();
            myRef = database.getReference("Restaurants");
             myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                        Resturant x = snapshot.getValue(Resturant.class);
                        ResturantName.add(x.ResuturantName);
                        resturants.add(x);

                    }
                    arrayAdapter = new ArrayAdapter<String>(AddNewFood.this, android.R.layout.simple_spinner_item, ResturantName);
                    arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    ResturantsSpinner.setAdapter(arrayAdapter);
                    }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        CategoryNames= new ArrayList<String>();
        Categories = new ArrayList<Category>();
        myRef = database.getReference("Categories");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    Category x = snapshot.getValue(Category.class);
                    CategoryNames.add(x.CategoryName);
                    Categories.add(x);

                }
                arrayAdapter = new ArrayAdapter<String>(AddNewFood.this, android.R.layout.simple_spinner_item, CategoryNames);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                CategoriesSpinner.setAdapter(arrayAdapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void openFileChoose(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,PICK_IMAGE_REQUEST);

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode,  Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==PICK_IMAGE_REQUEST && resultCode==RESULT_OK && data!=null &&data.getData()!=null){

            Image=data.getData();

        }
    }

    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void uploadFile() {
        if (Image != null) {
            StorageReference fileReference = mStorageRef.child(System.currentTimeMillis()
                    + "." + getFileExtension(Image));

            fileReference.putFile(Image)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {


                            Toast.makeText(AddNewFood.this, "Upload successful", Toast.LENGTH_LONG).show();
//                            Resturant upload = new Res(
//                                    taskSnapshot.getDownloadUrl().toString());
//                            String uploadId = mDatabaseRef.push().getKey();
//                            mDatabaseRef.child(uploadId).setValue(upload);

                            String key = myRef.push().getKey();
                            Food food = new Food();
                            myRef.child(key).setValue(food);


                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure( Exception e) {
                            Toast.makeText(AddNewFood.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                        }
                    });
        } else {
            Toast.makeText(this, "No file selected", Toast.LENGTH_SHORT).show();
        }
    }


}
