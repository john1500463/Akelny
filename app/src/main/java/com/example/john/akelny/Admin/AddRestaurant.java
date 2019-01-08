package com.example.john.akelny.Admin;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.john.akelny.Model.Resturant;
import com.example.john.akelny.R;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.location.places.ui.SupportPlaceAutocompleteFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class AddRestaurant extends Activity {
    private static final int PICK_IMAGE_REQUEST=1;
    EditText RestaurantName;
    EditText DeliveryTime;
    EditText DeliveryFees;
    Button LogoButton;
    EditText StartTime;
    EditText EndTime;
    EditText Longitude;
    EditText Latitude;
    TextView imagename;
    Uri Image;
    StorageReference mStorageRef;
    FirebaseDatabase database;
    DatabaseReference myRef;
    Button Submit;
    double placeLat, placeLong;

    private SupportPlaceAutocompleteFragment placeAutocompleteFragment;


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
        imagename= (TextView)findViewById(R.id.LogoTextView);
        imagename.setText("No Image Selected");
        mStorageRef= FirebaseStorage.getInstance().getReference("Uploads");
        database = FirebaseDatabase.getInstance();

        autoCompletePlaces();

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
    protected void onActivityResult(int requestCode, int resultCode,  Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==PICK_IMAGE_REQUEST && resultCode==RESULT_OK && data!=null &&data.getData()!=null){

            Image=data.getData();
            imagename.setText(Image.getLastPathSegment());

        }
    }

    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    public void autoCompletePlaces(){

        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.autocomplete_fragment);
            //add filter to search in egypt only
            autocompleteFragment.setFilter(new AutocompleteFilter.Builder().setCountry("EG").build());

            autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
                @Override
                public void onPlaceSelected(Place place) {
                    final LatLng latLngLoc = place.getLatLng();


                    placeLat = latLngLoc.latitude;
                    placeLong = latLngLoc.longitude;

                    Log.i("Place", place.getAddress().toString());
                    Log.i("Long", String.valueOf(placeLat));
                    Log.i("Lat", String.valueOf(placeLong));

                }

                @Override
                public void onError(Status status) {
                    Toast.makeText(AddRestaurant.this, "" + status.toString(), Toast.LENGTH_SHORT).show();
                    Log.i("Autocomplete", status.toString());
                }
            });
    }

    private void uploadFile() {
        if (Image != null) {
            myRef = database.getReference("Restaurants");
            StorageReference fileReference = mStorageRef.child(System.currentTimeMillis()
                    + "." + getFileExtension(Image));

             fileReference.putFile(Image)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {


                            Toast.makeText(AddRestaurant.this, "Upload successful", Toast.LENGTH_LONG).show();

                            String key = myRef.push().getKey();
                            Resturant resturant = new Resturant(RestaurantName.getText().toString(),DeliveryTime.getText().toString(),DeliveryFees.getText().toString(),taskSnapshot.getDownloadUrl().toString(),StartTime.getText().toString(),EndTime.getText().toString(),String.valueOf(placeLong),String.valueOf(placeLat));
                            myRef.child(key).setValue(resturant);


                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure( Exception e) {
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
