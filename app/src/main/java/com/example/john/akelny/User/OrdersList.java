package com.example.john.akelny.User;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.john.akelny.Admin.AddNewFood;
import com.example.john.akelny.Model.Resturant;
import com.example.john.akelny.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class OrdersList extends Activity {
    ListView listView;
    ImageView imageview;
    TextView one;
    TextView two;
    BottomNavigationView navBar;
    FirebaseDatabase database;
    DatabaseReference myRef;
    ArrayList<Resturant>resturants;
    FrameLayout mainFrameLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders_list);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        resturants= new ArrayList<Resturant>();
        navBar = (BottomNavigationView) findViewById(R.id.main_nav);
        mainFrameLayout = (FrameLayout) findViewById(R.id.main_frame);
        navBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                switch (menuItem.getItemId())
                {
                    case R.id.nav_restraunts:
                    {
                        startActivity(new Intent(OrdersList.this, RestrauntsActivity.class));
                        return true;
                    }
                    case R.id.nav_cart:
                    {
                        startActivity(new Intent(OrdersList.this, CartActivity.class));
                        return true;
                    }
                    case R.id.nav_orderlist:
                    {
                        startActivity(new Intent(OrdersList.this, OrdersList.class));
                        return true;
                    }

                    case R.id.nav_myinfo:
                    {
                        startActivity(new Intent(OrdersList.this, AccountActivity.class));
                        return true;
                    }

                    case R.id.nav_logout:
                    {
                        startActivity(new Intent(OrdersList.this, LoginActivity.class));
                        return true;
                    }
                    default: return false;


                }
            }
        });
        listView = (ListView) findViewById(R.id.listview);
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Restaurants");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    Resturant x = snapshot.getValue(Resturant.class);

                    resturants.add(x);

                }
                CustomerAdapter customerAdapter = new CustomerAdapter();
                listView.setAdapter(customerAdapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    private class CustomerAdapter extends BaseAdapter{


        @Override
        public int getCount() {

            return resturants.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {

            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = getLayoutInflater().inflate(R.layout.customlisthistory,null);
            imageview = convertView.findViewById(R.id.imageView);
            InputStream srt = null;
            Bitmap bitmap = null;
            try {
                srt = new URL(resturants.get(position).Logo).openStream();
                bitmap = BitmapFactory.decodeStream(srt);
            } catch (IOException e) {
                e.printStackTrace();
            }

            imageview.setImageBitmap(bitmap);
            one=(TextView) convertView.findViewById(R.id.textView);
            one.setText(resturants.get(position).ResuturantName);
            two=(TextView) convertView.findViewById(R.id.textView2);
            two.setText(String.valueOf(resturants.get(position).DeliveryFees));


            return convertView;
        }
    }
}
