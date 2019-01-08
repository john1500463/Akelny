package com.example.john.akelny.User;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;




import com.example.john.akelny.Model.Resturant;
import com.example.john.akelny.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mancj.materialsearchbar.MaterialSearchBar;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

public class RestrauntsActivity extends Activity implements MaterialSearchBar.OnSearchActionListener{
    ListView listView;
    ImageView imageview;
    TextView one;
    TextView two;
    private MaterialSearchBar searchBar;

    FirebaseDatabase database;
    DatabaseReference myRef;
    ArrayList<Resturant>resturants;
    ImageView imageview2;
    BottomNavigationView navBar;
    FrameLayout mainFrameLayout;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restraunts);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        searchBar = (MaterialSearchBar) findViewById(R.id.searchBar);
        searchBar.setHint("Custom hint");
        searchBar.setSpeechMode(true);
        searchBar.setOnSearchActionListener(this);
        progressDialog = new ProgressDialog(RestrauntsActivity.this);
        progressDialog.setTitle("Loading Resturants");
        progressDialog.setMessage("Loading");
        progressDialog.setCancelable(false);
        progressDialog.show();
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
                        startActivity(new Intent(RestrauntsActivity.this, RestrauntsActivity.class));
                        return true;
                    }
                    case R.id.nav_cart:
                    {
                        startActivity(new Intent(RestrauntsActivity.this, CartActivity.class));
                        return true;
                    }
                    case R.id.nav_orderlist:
                    {
                        startActivity(new Intent(RestrauntsActivity.this, OrdersList.class));
                        return true;
                    }

                    case R.id.nav_myinfo:
                    {
                        startActivity(new Intent(RestrauntsActivity.this, AccountActivity.class));
                        return true;
                    }

                    case R.id.nav_logout:
                    {
                        startActivity(new Intent(RestrauntsActivity.this, LoginActivity.class));
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
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            }
        });

    }

    @Override
    public void onSearchStateChanged(boolean b) {
        String state = b ? "enabled" : "disabled";
        Toast.makeText(RestrauntsActivity.this, "Search " + state, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSearchConfirmed(CharSequence text) {
        Toast.makeText(this,"Searching "+ text.toString()+" ......",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onButtonClicked(int buttonCode) {
        switch (buttonCode){
            case MaterialSearchBar.BUTTON_NAVIGATION:
                Toast.makeText(RestrauntsActivity.this, "Button Nav " , Toast.LENGTH_SHORT).show();
                break;
            case MaterialSearchBar.BUTTON_SPEECH:
                Toast.makeText(RestrauntsActivity.this, "Speech " , Toast.LENGTH_SHORT).show();
        }
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
            imageview2 = (ImageView) convertView.findViewById(R.id.imageView2);
            imageview2.setImageDrawable(getResources().getDrawable(R.drawable.icons8));
            if(position == resturants.size()-1){

                progressDialog.dismiss();
            }

            return convertView;
        }
    }
}
