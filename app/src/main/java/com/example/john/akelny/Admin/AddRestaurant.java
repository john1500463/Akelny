package com.example.john.akelny.Admin;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.john.akelny.MainActivity;
import com.example.john.akelny.Model.Resturant;
import com.example.john.akelny.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class AddRestaurant extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST=1;
    EditText RestaurantName;
    EditText DeliveryTime;
    EditText DeliveryFees;
    Button LogoButton;
    EditText StartTime;
    EditText EndTime;
    Uri Image;
    StorageReference mStorageRef;
    FirebaseDatabase database;
    DatabaseReference myRef;
    Button Submit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_restaurant);
        RestaurantName=(EditText)findViewById(R.id.ResturantName);
        DeliveryTime = (EditText)findViewById(R.id.DeleviryTime);
        DeliveryFees = (EditText)findViewById(R.id.DeleviryFees);
        StartTime = (EditText)findViewById(R.id.StartTime);
        EndTime=(EditText)findViewById(R.id.EndTime);
        LogoButton=(Button)findViewById(R.id.Logo);
        mStorageRef= FirebaseStorage.getInstance().getReference("Uploads");
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Restaurants");

        Submit = (Button)findViewById(R.id.AddRestaurantToDataBase);
        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadFile();
            }
        });
        LogoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    openFileChoose();
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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
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


                            Toast.makeText(AddRestaurant.this, "Upload successful", Toast.LENGTH_LONG).show();
//                            Resturant upload = new Res(
//                                    taskSnapshot.getDownloadUrl().toString());
//                            String uploadId = mDatabaseRef.push().getKey();
//                            mDatabaseRef.child(uploadId).setValue(upload);

                            String key = myRef.push().getKey();
                            Resturant resturant = new Resturant(RestaurantName.getText().toString(),Float.valueOf(DeliveryTime.getText().toString()),Float.valueOf(DeliveryFees.getText().toString()),taskSnapshot.getDownloadUrl().toString(),Float.valueOf(StartTime.getText().toString()),Float.valueOf(EndTime.getText().toString()));
                            myRef.child(key).setValue(resturant);


                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(AddRestaurant.this, e.getMessage(), Toast.LENGTH_SHORT).show();
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