package com.example.john.akelny.Admin;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
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
import com.google.firebase.storage.FirebaseStorage;
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
    ProgressDialog progressDialog;
    TextView textView;
    EditText FoodName;
    EditText FoodDescription;
    String FoodCategoryText;
    String ResturantNameText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_food);
        textView=(TextView)findViewById(R.id.ImageNameForNewFood);
        textView.setText("No Image Selected");
        FoodName = (EditText)findViewById(R.id.foodName);
        FoodDescription= (EditText) findViewById(R.id.FoodDescription);
        ResturantsSpinner=(Spinner)findViewById(R.id.spinnerResturantNameAddFood);
        image = (ImageView)findViewById(R.id.NextFirstPageInFood);
        uploadImage = (Button) findViewById(R.id.UploadImageForNewFood);
        CategoriesSpinner=(Spinner)findViewById(R.id.spinner1);
        mStorageRef= FirebaseStorage.getInstance().getReference("Uploads");
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading Information");
        progressDialog.setMessage("Loading");
        progressDialog.setCancelable(false);
        progressDialog.show();
        uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog = new ProgressDialog(AddNewFood.this);
                openFileChoose();
            }
        });
        ResturantName = new ArrayList<String>();
        resturants= new ArrayList<Resturant>();
        image.setOnClickListener(new View.OnClickListener() {
                                     @Override
                                     public void onClick(View v) {
                                         progressDialog.setTitle("Uploading Information");
                                         progressDialog.setMessage("Uploading");
                                         progressDialog.setCancelable(false);
                                         progressDialog.show();
                                      uploadFile();
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
                progressDialog.dismiss();

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
            textView.setText(Image.getLastPathSegment());

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

                            myRef = database.getReference("Food");
                            Toast.makeText(AddNewFood.this, "Upload successful", Toast.LENGTH_LONG).show();
                            FoodCategoryText = CategoriesSpinner.getSelectedItem().toString();
                            ResturantNameText = ResturantsSpinner.getSelectedItem().toString();
                            String key = myRef.push().getKey();
                            Food food = new Food(FoodName.getText().toString(),FoodDescription.getText().toString(),FoodCategoryText,ResturantNameText,taskSnapshot.getDownloadUrl().toString());
                            myRef.child(key).setValue(food);
                            Intent intent = new Intent(AddNewFood.this,AddNewFood2.class);
                            intent.putExtra("FoodID",key);
                            progressDialog.dismiss();
                            startActivity(intent);



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
