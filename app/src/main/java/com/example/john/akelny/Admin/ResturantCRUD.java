package com.example.john.akelny.Admin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.john.akelny.Model.Resturant;
import com.example.john.akelny.R;

public class ResturantCRUD extends AppCompatActivity {
    Button AddRestaurantButton;
    Button EditRestaurantButton;
    Button RemoveRestaurantButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resturant_crud);
        AddRestaurantButton=(Button) findViewById(R.id.AddRestaurantButton);
        EditRestaurantButton = (Button)findViewById(R.id.EditRestaurantButton);
        RemoveRestaurantButton = (Button)findViewById(R.id.RemoveRestaurantButton);
        AddRestaurantButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResturantCRUD.this,AddRestaurant.class);
                startActivity(intent);
            }
        });

        EditRestaurantButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(ResturantCRUD.this,DeleteRestaurant.class);
//                startActivity(intent);

            }
        });

        RemoveRestaurantButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ResturantCRUD.this,DeleteRestaurant.class);
                startActivity(intent);

            }
        });
    }
}
