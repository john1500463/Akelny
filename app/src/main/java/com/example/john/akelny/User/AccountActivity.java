package com.example.john.akelny.User;

import android.Manifest;
import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Environment;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.john.akelny.R;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AccountActivity extends Activity {
    TextView name, mail, number;
    //Button takePictureButton;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    ImageView imageView;
    Uri file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        Button button = (Button) findViewById(R.id.button2);
        imageView = (ImageView) findViewById(R.id.imageView6);

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
