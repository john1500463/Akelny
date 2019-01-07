package com.example.john.akelny.Admin;

import android.app.Activity;
import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.john.akelny.R;

public class CategoriesCRUD extends Activity {
    Button AddCategory;
    Button EditCategory;
    Button RemoveCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories_crud);

        AddCategory=(Button) findViewById(R.id.AddCategoryButton);
        EditCategory=(Button)findViewById(R.id.EditCategoryButton);
        RemoveCategory=(Button)findViewById(R.id.RemoveCategoryButton);

        AddCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CategoriesCRUD.this,AddCategory.class);
                startActivity(intent);
            }
        });
        EditCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CategoriesCRUD.this,EditCategory.class);
                startActivity(intent);
            }
        });

        RemoveCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CategoriesCRUD.this,RemoveCategory.class);
                startActivity(intent);
            }
        });
    }
}
