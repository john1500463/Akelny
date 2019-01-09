package com.example.john.akelny.User;

import android.Manifest;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Environment;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.BottomNavigationView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.john.akelny.Model.Food;
import com.example.john.akelny.Model.User;
import com.example.john.akelny.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AccountActivity extends Activity {
    TextView name, mail, number;
    //Button takePictureButton;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    ImageView imageView;
    Uri file;
    BottomNavigationView navBar;
    FrameLayout mainFrameLayout;
    FirebaseDatabase database;
    DatabaseReference myRef;
    StorageReference mStorageRef;
    User user;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        name = findViewById(R.id.textView5);
        mail= findViewById(R.id.textView8);
        number = findViewById(R.id.textView9);
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Users");

        sharedPreferences =getSharedPreferences("User", Context.MODE_PRIVATE);
        String UEmail = sharedPreferences.getString("Username","");


        myRef.orderByChild("Email").equalTo(UEmail).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot datas: dataSnapshot.getChildren()){
                    user = datas.getValue(User.class);

                }

                name.setText(user.FirstName.toString() + " " + user.LastName.toString());
                mail.setText(user.Email.toString());
                number.setText(user.PhoneNumber);


            }



            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        Button button = (Button) findViewById(R.id.button2);
        imageView = (ImageView) findViewById(R.id.imageView6);
        navBar = (BottomNavigationView) findViewById(R.id.main_nav);
        mainFrameLayout = (FrameLayout) findViewById(R.id.main_frame);
        navBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                switch (menuItem.getItemId())
                {
                    case R.id.nav_restraunts:
                    {
                        FragmentManager fragmentManager = getFragmentManager();;
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.fragment_container, new RestrauntsFragment(), "rest");
                        fragmentTransaction.addToBackStack("rest");
                        fragmentTransaction.commit();
                        return true;
                    }
                    case R.id.nav_cart:
                    {
                        FragmentManager fragmentManager = getFragmentManager();;
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.fragment_container, new CartFragment(), "rest");
                        fragmentTransaction.addToBackStack("rest");
                        fragmentTransaction.commit();
                        return true;
                    }
                    case R.id.nav_orderlist:
                    {
                        FragmentManager fragmentManager = getFragmentManager();;
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.fragment_container, new OrderListFragment(), "rest");
                        fragmentTransaction.addToBackStack("rest");
                        fragmentTransaction.commit();
                        return true;
                    }

                    case R.id.nav_myinfo:
                    {
                        startActivity(new Intent(AccountActivity.this, AccountActivity.class));
                        return true;
                    }

                    case R.id.nav_logout:
                    {
                        startActivity(new Intent(AccountActivity.this, LoginActivity.class));
                        return true;
                    }
                    default: return false;


                }
            }
        });
        //Disable the button if the user has no camera
        if (!hasCamera())
            button.setEnabled(false);
    }

    private boolean hasCamera() {
        return getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY);
    }

    //Launching the camera
    public void launchcamera(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //Take a picture and pass results along to onActivityResult
        startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            //Get the photo
            Bundle extras = data.getExtras();
            Bitmap photo = (Bitmap) extras.get("data");
            imageView.setImageBitmap(photo);
        }
    }
}
