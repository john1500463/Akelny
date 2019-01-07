package com.example.john.akelny.Admin;

import android.app.Activity;
import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.john.akelny.MainActivity;
import com.example.john.akelny.R;

public class FoodCRUD extends Activity {
    Button AddFood,EditFood,DeleteFood;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_crud);
        AddFood=(Button) findViewById(R.id.AddFoodButton);
        EditFood=(Button) findViewById(R.id.EditFoodButton);
        DeleteFood = (Button) findViewById(R.id.RemoveFoodButton);
        AddFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FoodCRUD.this, AddNewFood.class);
                startActivity(intent);
            }
        });
        EditFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(FoodCRUD.this, MainActivity.class);
                startActivity(intent);

            }
        });

        DeleteFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FoodCRUD.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }
}
