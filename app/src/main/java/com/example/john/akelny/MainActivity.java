package com.example.john.akelny;

import android.app.Activity;
import android.content.Intent;

import android.os.Bundle;

import com.example.john.akelny.Admin.AdminMenu;
import com.example.john.akelny.User.OrdersList;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent(MainActivity.this, AdminMenu.class);
        startActivity(intent);

//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference myRef = database.getReference("message");
//
//        myRef.setValue("Hello, World!");
    }
}
