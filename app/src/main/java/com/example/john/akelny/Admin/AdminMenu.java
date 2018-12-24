package com.example.john.akelny.Admin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.john.akelny.Model.Category;
import com.example.john.akelny.Model.Resturant;
import com.example.john.akelny.R;

public class AdminMenu extends AppCompatActivity {
    Button ResturantCrudButton;
    Button CategoryCrudButton;
    Button FoodCrudButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_menu);
        ResturantCrudButton = (Button) findViewById(R.id.RestaurantCRUDButton);
        CategoryCrudButton=(Button) findViewById(R.id.CategoryCRUDButton);
        FoodCrudButton= (Button) findViewById(R.id.FoodCRUDButton);

        ResturantCrudButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent expintent = new Intent (AdminMenu.this,ResturantCRUD.class);
                startActivity(expintent);

            }
        });

        CategoryCrudButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent expintent = new Intent (AdminMenu.this, CategoriesCRUD.class);
                startActivity(expintent);

            }
        });

        FoodCrudButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent expintent = new Intent (AdminMenu.this, FoodCRUD.class);
                startActivity(expintent);
            }
        });
    }
}
